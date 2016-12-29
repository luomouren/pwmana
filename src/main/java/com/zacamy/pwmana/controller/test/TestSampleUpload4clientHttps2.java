package com.zacamy.pwmana.controller.test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.client.ClientProtocolException;
/*
 * 测试类
 * 测试Https接口 post
 * 接收下属客户端上传样本,保存样本文件
 */
public class TestSampleUpload4clientHttps2 {
 private static class TrustAnyTrustManager implements X509TrustManager {
  public void checkClientTrusted(X509Certificate[] chain, String authType)
    throws CertificateException {
  }
  public void checkServerTrusted(X509Certificate[] chain, String authType)
    throws CertificateException {
  }
  public X509Certificate[] getAcceptedIssuers() {
   return new X509Certificate[] {};
  }
 }
 private static class TrustAnyHostnameVerifier implements HostnameVerifier {
  public boolean verify(String hostname, SSLSession session) {
   return true;
  }
 }
 /**
  * post方式请求服务器(https协议)
  * 
  * @param url
  *            请求地址
  * @param content
  *            参数
  * @param charset
  *            编码
  * @return
  * @throws NoSuchAlgorithmException
  * @throws KeyManagementException
  * @throws IOException
  * @throws NoSuchProviderException
  */
 public static byte[] post(String url, String content, String charset)
   throws NoSuchAlgorithmException, KeyManagementException,
   IOException, NoSuchProviderException {
  try {
   TrustManager[] tm = { new TrustAnyTrustManager() };
   // SSLContext sc = SSLContext.getInstance("SSL");
   SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");
   sc.init(null, tm,new java.security.SecureRandom());
   URL console = new URL(url);
   
   HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
   conn.setSSLSocketFactory(sc.getSocketFactory());
   conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
   conn.setDoOutput(true); 
   conn.setDoInput(true);  
   conn.setRequestMethod("POST");
   conn.connect();
   DataOutputStream out = new DataOutputStream(conn.getOutputStream());
   out.write(content.getBytes(charset));
   // 刷新、关闭
   out.flush();
   out.close();
   InputStream is = conn.getInputStream();
   if (is != null) {
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len = 0;
    while ((len = is.read(buffer)) != -1) {
     outStream.write(buffer, 0, len);
    }
    is.close();
    return outStream.toByteArray();
   }
  } catch (KeyManagementException e) {
   e.printStackTrace();
  }catch (NoSuchAlgorithmException e) {
   e.printStackTrace();
  }catch (NoSuchProviderException e) {
   e.printStackTrace();
  }catch (IOException e) {
   e.printStackTrace();
  }
  
  
  
  return null;
 }
 
 public static void main(String[] args) throws ClientProtocolException,IOException {
  try {
   post("https://service.guangyuanbj.com:8443","aaaaaaaaaaaaaaa","UTF-8");
  } catch (KeyManagementException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } catch (NoSuchAlgorithmException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } catch (NoSuchProviderException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 }
 
}



