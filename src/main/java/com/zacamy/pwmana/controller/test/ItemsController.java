package com.zacamy.pwmana.controller.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/itemsController")
public class ItemsController {
    private static final long serialVersionUID = 1601507150278487538L;
    private static final String ATTR_CER = "javax.servlet.request.X509Certificate";
    private static final String CONTENT_TYPE = "text/plain;charset=UTF-8";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String SCHEME_HTTPS = "https";
  
	@RequestMapping(value="/queryItems")
	public  String queryItems(HttpServletRequest request,Model model){
		/*List<ItemsCustom> itemsList = new ArrayList<ItemsCustom>();
		ModelAndView modelAndView =  new ModelAndView();
		modelAndView.addObject("itemsList", null);
		modelAndView.setViewName("items/itemsList");*/
		
		model.addAttribute("username", "admin");
		return "demo";
	}
	
    @RequestMapping(value = "/index")
    public String index() {
      //new HttpsClient().testIt();
      try {
        //InstallCert2.doInstall("service.guangyuanbj.com:8443", "changeit".toCharArray());
        //InstallCert.doInstall("service.guangyuanbj.com:8443", "changeit".toCharArray());
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      try {
        URL url = new URL("https://service.guangyuanbj.com:8443");
        //System.setProperty("javax.Net.ssl.trustStore", "G:\\server_store");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
       // System.setProperty("javax.Net.ssl.trustStore", "G://server_store");//注意是绝对路径
       // System.setProperty("javax.Net.ssl.keyStorePassword", "guangYuan@#2016"); 
        con.setRequestMethod("POST");
        con.connect();
        StringBuffer sb = new StringBuffer();
        String line = "";
        BufferedReader URLinput = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while ((line = URLinput.readLine()) != null) {
          sb.append(line);
        }
        con.disconnect();
        System.out.println("====="+sb.toString().toLowerCase());
      } catch (Exception e) {
         System.out.println(e);
      }
      
        return "demo";
    }
    
    /**
     * 手机登录方法
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/app/loginApp", method = RequestMethod.POST)
    public String getMonery(String username,String password) {
    	System.out.println(password);
        return "success,username="+username+",password="+password;
    }
    
    
    /**
     * 手机拍照上传图片
     * @param file 图片文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/app/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            File f = new File("G:\\uploadFile.jpg");
            
            try {
				file.transferTo(f);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            return "success";
        }
        return "fail";
    }
    
    /**
     * 页面请求时获取证书信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/getSSLCerts")
    public void getSSLCerts(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      response.setContentType(CONTENT_TYPE);
      response.setCharacterEncoding(DEFAULT_ENCODING);
      PrintWriter out = response.getWriter();
      X509Certificate[] certs = (X509Certificate[]) request.getAttribute(ATTR_CER);
      if (certs != null) {
          int count = certs.length;
          out.println("共检测到[" + count + "]个客户端证书 ");
          for (int i = 0; i < count; i++) {
              out.println("客户端证书 [" + (++i) + "]： ");
              out.println("校验结果：" + verifyCertificate(certs[--i]));
              out.println("证书详细：\r" + certs[i].toString());
          }
      } else {
          if (SCHEME_HTTPS.equalsIgnoreCase(request.getScheme())) {
              out.println("这是一个HTTPS请求，但是没有可用的客户端证书 ");
          } else {
              out.println("这不是一个HTTPS请求，因此无法获得客户端证书列表  ");
          }
      }
      out.close();
  }
    /**
     * <p>
     * 校验证书是否过期
     * </p>
     * 
     * @param certificate
     * @return
     */
    private boolean verifyCertificate(X509Certificate certificate) {
        boolean valid = true;
        try {
            certificate.checkValidity();
        } catch (Exception e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }      
    
    
}
