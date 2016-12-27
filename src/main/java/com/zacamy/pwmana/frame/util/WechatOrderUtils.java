package com.zacamy.pwmana.frame.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zacamy.pwmana.bean.WechatOrder;

/**
 * Created by seanid on 2016/1/15.
 * 微信统一下单工具类
 */
public class WechatOrderUtils {
	/* 微信支付步骤：
	 * 第一步 需要获取用户授权；
	 * 第二步 调用统一下单接口获取预支付prepay_id；
	 * 第三步 H5调起微信支付的内置的js。*/

	/**
	 * 获取用户的openid
	 * @return openid
	 */
	private static String getOpenId(){
		String openId="";
		
		String appid = PropertiesUtil.getValue("wechat.properties", "mchappid");
		String secret = PropertiesUtil.getValue("wechat.properties", "mchappsecret");
		String grantType = PropertiesUtil.getValue("wechat.properties", "grant_type");
		String accessTokenURL = PropertiesUtil.getValue("wechat.properties", "access_tokenURL");
		
		//获取openId,参照微信官方文档https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_4
		//1 第一步：用户同意授权，获取code
		String code="";
		/*获取用户授权有两种方式:1.scope=snsapi_base;
		 * 				   2.scope=snsapi_userinfo.我使用的是snsapi_base
		Scope为snsapi_base
		https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f417810387&redirect_uri=http%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60&response_type=code&scope=snsapi_base&state=123#wechat_redirect
		Scope为snsapi_userinfo
		https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf0e81c3bee622d60&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
		微信的官方文档也有对各个参数的详细说明，我就关键的参数仔细的说明一下。
		首先appid就不多说了就是你微信公众号的appid固定写死的，
		redirect_uri这个参数是最重要的，这个地址是访问你处理的接口地址。
		你可以在这个链接上拼接上你所需要的参数，一般你是要把订单的金额传到这个接口里的，访问这个链接的时候微信会给你code你需要用它去获取openid，
		记得要对其进行urlencode处理。state参数可以理解为扩展字段，其他的参数都是固定写法就不在多做介绍了
		*/
		/*参数	是否必须	说明
		appid	是	公众号的唯一标识
		redirect_uri	是	授权后重定向的回调链接地址，请使用urlencode对链接进行处理
		response_type	是	返回类型，请填写code
		scope	是	应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
		state	否	重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
		#wechat_redirect	是	无论直接打开还是做页面302重定向时候，必须带此参数*/
		
		
		//2 第二步：通过code换取网页授权access_token
		
		/*参数	是否必须	说明
		appid	是	公众号的唯一标识
		secret	是	公众号的appsecret
		code	是	填写第一步获取的code参数
		grant_type	是	填写为authorization_code*/
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", appid);
        map.put("secret", secret);
        map.put("code", code);
        map.put("grant_type", grantType);
        String returnStr;
		try {
			returnStr = HttpUtils.post(accessTokenURL, map);
			Log.info("accessToken,returnStr:[" + returnStr + "]");
			/*2.1 正确结果
	         * {
	        	   "access_token":"ACCESS_TOKEN",
	        	   "expires_in":7200,
	        	   "refresh_token":"REFRESH_TOKEN",
	        	   "openid":"OPENID",
	        	   "scope":"SCOPE",
	        	   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
	        	}
	           2.2 错误结果
	           {"errcode":40029,"errmsg":"invalid code"}
	        	 */ 
			if(returnStr.contains("errcode")){
				Log.error("获取openid失败，第二步access_token失败");
			}else{
				AccessToken at = JSON.parseObject(returnStr, AccessToken.class);
				openId = at.getOpenid();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("获取openid失败，第二步access_token失败", e);
		}
		return openId;
	}
	
    /**
     * 统一下单
     * @param detail    订单详情，必填
     * @param desc      商品或订单描述，必填
     * @param openid    公众号调起时需要的OPENID，选填，不填传“”
     * @param ip        下订单时的IP，必填
     * @param goodSn    业务系统商品编号，必填
     * @param orderSn   业务系统订单编号，必填
     * @param amount    金额，必填
     * @param type      支付类型，分为三种，JSAPI表示公众号调起的支付，NATIVE用于PC端网页调起的扫码支付，APP用于APP端调起的支付
     * @return          返回对象中封装了网页和APP调起支付控件需要的参数，根据不同的支付类型，有不同的返回参数
     */
    public static synchronized JSONObject createOrder(String detail, String desc, String openid, String ip, String goodSn, String orderSn, String amount, String type) {
        JSONObject result = new JSONObject();
        // 1、参数校验
        if (StringUtils.isBlank(detail) || StringUtils.isBlank(desc) || StringUtils.isBlank(ip)
                || StringUtils.isBlank(goodSn) || StringUtils.isBlank(orderSn) || StringUtils.isBlank(amount)
                || StringUtils.isBlank(type)) {
            Log.error("微信支付统一下单请求错误：请求参数不足", null);
            result.put("status", "error");
            result.put("msg", "请求参数不足");
            result.put("obj", null);
            return result;
        }

        double relAmount = 0;// 对应微信支付的真实数目
        try {//进行格式转换异常获取，保证数目正确
            relAmount = Double.parseDouble(amount) * 100;
        } catch (Exception e) {
            Log.error("微信支付统一下单请求错误：请求金额格式错误", e);
            result.put("status", "error");
            result.put("msg", "请求金额格式错误");
            result.put("obj", null);
            return result;
        }

        if (relAmount == 0) {//微信支付的支付金额必须为大于0的int类型，单位为分
            Log.error("微信支付统一下单请求错误：请求金额不能为0", null);
            result.put("status", "error");
            result.put("msg", "请求金额不能为0");
            result.put("obj", null);
            return result;
        }

        if (!("JSAPI".equalsIgnoreCase(type) || "NATIVE".equalsIgnoreCase(type) || "APP".equalsIgnoreCase(type))) {
            Log.error("微信支付统一下单请求错误：支付类型为空", null);
            result.put("status", "error");
            result.put("msg", "支付类型为空");
            result.put("obj", null);
            return result;
        }

        //获取openId
        String getOpenId = getOpenId();
        if(!StringUtils.isBlank(getOpenId)){
        	openid = getOpenId;
        }
        
        /* 公众号调起微信支付的时候，必须要有openID*/
        if ("JSAPI".equalsIgnoreCase(type) && StringUtils.isBlank(openid)) {
            Log.error("微信支付统一下单请求错误：请求参数不足", null);
            result.put("status", "error");
            result.put("msg", "请求参数不足");
            result.put("obj", null);
            return result;
        }


        // 2、获取系统配置信息
        String wx_order = PropertiesUtil.getValue("wechat.properties", "wx_order");//获取统一下单接口地址
        String mchappid = PropertiesUtil.getValue("wechat.properties", "mchappid");// 商户appid
        String mchid = PropertiesUtil.getValue("wechat.properties", "mchid");// 商户ID
        String wx_callback = PropertiesUtil.getValue("wechat.properties", "wx_callback");// 获取微信支付回调接口
        String wx_key = PropertiesUtil.getValue("wechat.properties", "wx_key");//微信商户后台设置的key
        String app_mchid = PropertiesUtil.getValue("wechat.properties", "app_mchid");//APP调起微信支付的商户ID
        String app_mchappid = PropertiesUtil.getValue("wechat.properties", "app_mchappid");//APP调起微信的APPID


        if (StringUtils.isBlank(wx_order) || StringUtils.isBlank(mchappid)
                || StringUtils.isBlank(mchid) || StringUtils.isBlank(wx_callback)) {
            Log.error("微信支付统一下单请求错误：系统配置信息缺失", null);
            result.put("status", "error");
            result.put("msg", "系统配置信息缺失");
            result.put("obj", null);
            return result;
        }


        // 发送报文模板,其中部分字段是可选字段
        String xml = "" +
                "<xml>" +
                "<appid>APPID</appid>" +//公众号ID
                "<device_info>WEB</device_info>" +//设备信息
                "<detail>DETAIL</detail>" +//商品详情
                "<body>BODY</body>" +//商品描述
                "<mch_id>MERCHANT</mch_id>" +//微信给的商户ID
                "<nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>" +//32位随机字符串,不改
                "<notify_url><![CDATA[URL_TO]]></notify_url>" +//信息通知页面
                "<openid>UserFrom</openid>" +//支付的用户ID
                "<fee_type>CNY</fee_type>" +//支付货币，不改
                "<spbill_create_ip>IP</spbill_create_ip>" +//用户IP
                "<time_start>START</time_start>" +//订单开始时间
                "<time_expire>STOP</time_expire>" +//订单结束时间
                "<goods_tag>WXG</goods_tag>" +//商品标记，不改
                "<product_id>GOODID</product_id>" +//商品ID
                "<limit_pay>no_credit</limit_pay>" +//支付范围，默认不支持信用卡支付，不改
                "<out_trade_no>PAY_NO</out_trade_no>" +//商城生成的订单号
                "<total_fee>TOTAL</total_fee>" +//总金额
                "<trade_type>TYPE</trade_type>" +//交易类型，JSAPI，NATIVE，APP，WAP
                "<sign>SIGN</sign>" +//加密字符串
                "</xml>";

        //生成订单起始时间，订单7天内有效
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        String start_time = df.format(new Date());
        String stop_time = df.format(new Date().getTime() + 7 * 24 * 60 * 60 * 1000);

        //3、xml数据封装


        //APP调起的时候，可能和公众号调起的商户号是不同的，所以需要分开设置
        if ("APP".equalsIgnoreCase(type)) {
            xml = xml.replace("MERCHANT", app_mchid);
            xml = xml.replace("APPID", app_mchappid);
        } else {
            xml = xml.replace("MERCHANT", mchid);
            xml = xml.replace("APPID", mchappid);
        }


        xml = xml.replace("DETAIL", detail);
        xml = xml.replace("BODY", desc);
        xml = xml.replace("URL_TO", wx_callback);
        xml = xml.replace("IP", ip);
        xml = xml.replace("START", start_time);
        xml = xml.replace("STOP", stop_time);
        xml = xml.replace("GOODID", goodSn);
        xml = xml.replace("PAY_NO", orderSn);
        xml = xml.replace("TOTAL", (int) relAmount + "");
        xml = xml.replace("TYPE", type);
        if ("NATIVE".equalsIgnoreCase(type) || "APP".equalsIgnoreCase(type)) {
            xml = xml.replace("<openid>UserFrom</openid>", openid);
        } else {
            xml = xml.replace("UserFrom", openid);
        }
        // 4、加密
        Map<String, String> map = new HashMap<String, String>();
        map.put("device_info", "WEB");
        map.put("detail", detail);
        map.put("body", desc);
        if ("APP".equalsIgnoreCase(type)) {
            map.put("mch_id", app_mchid);
            map.put("appid", app_mchappid);
        } else {

            map.put("mch_id", mchid);
            map.put("appid", mchappid);
        }


        map.put("nonce_str", "1add1a30ac87aa2db72f57a2375d8fec");
        map.put("notify_url", wx_callback);
        map.put("fee_type", "CNY");
        map.put("spbill_create_ip", ip);
        map.put("time_start", start_time);
        map.put("time_expire", stop_time);
        map.put("goods_tag", "WXG");
        map.put("product_id", goodSn);
        map.put("limit_pay", "no_credit");
        map.put("out_trade_no", orderSn);
        map.put("total_fee", (int) relAmount + "");
        map.put("trade_type", type);
        if (("JSAPI".equalsIgnoreCase(type))) {
            map.put("openid", openid);
        }
        //签名
        String sign = SignatureUtils.signature(map, wx_key);
        xml = xml.replace("SIGN", sign);


        // 5、请求
        String response = "";
        try {//注意，此处的httputil一定发送请求的时候一定要注意中文乱码问题，中文乱码问题会导致在客户端加密是正确的，可是微信端返回的是加密错误
            response = HttpUtils.post(wx_order, xml);
        } catch (Exception e) {
            Log.error("微信支付统一下单失败:http请求失败", e);
            result.put("status", "error");
            result.put("msg", "http请求失败");
            result.put("obj", null);
            return result;
        }


        //6、处理请求结果--获得   【预支付交易会话标识prepay_id】
        XStream s = new XStream(new DomDriver());
        s.alias("xml", WechatOrder.class);
        WechatOrder order = (WechatOrder) s.fromXML(response);

        if ("SUCCESS".equals(order.getReturn_code()) && "SUCCESS".equals(order.getResult_code())) {
            Log.error("微信支付统一下单请求成功：" + order.getPrepay_id(), null);
        } else {
            Log.error("微信支付统一下单请求错误：" + order.getReturn_msg() + order.getErr_code(), null);
            result.put("status", "error");
            result.put("msg", "http请求失败");
            result.put("obj", null);
            return result;
        }

        HashMap<String, String> back = new HashMap<String, String>();

        //生成客户端调时需要的信息对象
        if ("JSAPI".equalsIgnoreCase(type)) {
            //网页调起的时候
            String time = Long.toString(System.currentTimeMillis());
            back.put("appId", mchappid);
            back.put("timeStamp", time);
            back.put("nonceStr", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");
            back.put("package", "prepay_id=" + order.getPrepay_id());
            back.put("signType", "MD5");
            String sign2 = SignatureUtils.signature(back, wx_key);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appId", mchappid);
            jsonObject.put("timeStamp", time);
            jsonObject.put("nonceStr", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");
            jsonObject.put("package", "prepay_id=" + order.getPrepay_id());
            jsonObject.put("signType", "MD5");
            jsonObject.put("paySign", sign2);

            result.put("status", "success");
            result.put("msg", "下单成功");
            result.put("obj", jsonObject);
            return result;


        } else if ("NATIVE".equalsIgnoreCase(type)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", order.getCode_url());
            result.put("status", "success");
            result.put("msg", "下单成功");
            result.put("obj", jsonObject);
            return result;


        } else if ("APP".equalsIgnoreCase(type)) {


            //APP调起的时候,请注意，安卓端不能用驼峰法，所有的key必须使用小写
            String time = Long.toString(System.currentTimeMillis());
            back.put("appid", app_mchappid);
            back.put("timestamp", time);
            back.put("partnerid", app_mchid);
            back.put("noncestr", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");
            back.put("prepayid", order.getPrepay_id());
            back.put("package", "Sign=WXPay");
            String sign2 = SignatureUtils.signature(back, wx_key);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appid", app_mchappid);
            jsonObject.put("timestamp", time);
            jsonObject.put("partnerid", app_mchid);
            jsonObject.put("noncestr", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");
            jsonObject.put("prepayid", order.getPrepay_id());
            //jsonObject.put("package", "Sign=WXPay");
            jsonObject.put("sign", sign2);
            result.put("status", "success");
            result.put("msg", "下单成功");
            result.put("obj", jsonObject);
            return result;

        }
        return result;
    }

    
    
    
    
    
    
    
    

}
