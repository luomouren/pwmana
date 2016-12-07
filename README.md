# pwmana  框架：SpringMVC+Spring+mybatis+maven

一、添加微信支付功能
参照的是https://github.com/Seanid/wechatPay，感谢Seanid的分享~


+++++++++++++此功能因为需要公众号id等信息，暂时还未测试++++++++++++++

####1.关于
这是一个基于java的简易的微信支付的后台，集成了包括微信公众号支付，网页原生支付，和APP支付接口三个功能<br/>

####2.项目功能
该项目开发基于一个使用了SpringMVC+Spring+mybatis+maven，这类项目的配置在此不多赘述。下面说下其中的几个主要的配置文件和工具类。
#####2.1 配置文件
本项目主要关于微信支付的配置文件为src/wechat.properties,所有关于微信支付所需要的账号都通过该配置文件设置
#####2.2 主要工具类
项目中，对微信支付的一些操作进行了封装，主要包括如下几个：<br/>
* src/com/sean/util/SignatureUtils.java<br/>
该类包含了微信支付所需要的一些加密算法,最主要的函数为：
* src/com/sean/util/WechatOrderUtils.java<br/>
该类实现了微信同一下单的一个函数，通过该函数可以实现三种方式的统一下单

#####3.实例
项目中，包含部分已经实现了的实例，如js页面调用和native调用。<br/>
control层主要用WechatPayControler实现了页面请求和接口请求的功能。<br/>
页面包括两个，web.jsp实现的是微信公众号调起支付的功能，native.jsp是实现了PC端扫二维码的功能
