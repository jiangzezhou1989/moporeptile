package com.mopo.web;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.activation.URLDataSource;





public class HttpUtil {
	
	
	
	
	/**
	 * 模拟get提交数据
	 * @param params
	 */
	public static String sendGet(String params) {
		return null;
		
	}
	
	/**
	 * 模拟post提交
	 * @param params
	 * @return
	 */
	public static String sendPost(String urlAddress,String jsonStr,Map<String,String> headers) {
		URL url = null;
        HttpURLConnection con  =null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            url = new URL(urlAddress);
            con  = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            
            con.setDoOutput(true);
            con.setDoInput(true);
            
            for(Map.Entry<String, String> entry: headers.entrySet()) {
            	con.setRequestProperty(entry.getKey(), entry.getValue());
            	
            }
       
            con.setRequestMethod("POST");           
            byte[] b  = jsonStr.getBytes("utf-8");
            System.out.println("content-length" + jsonStr.getBytes().length);
            con.getOutputStream().write(b, 0, b.length);
            con.getOutputStream().flush();
            con.getOutputStream().close();
            int code = con.getResponseCode();
            System.out.println("CODE----------" + code);
            if( code == 200 ) {
            	
            	//CmmnDecode.decrypt(new String(bytes), Config.key1, Config.key2);
            	
            	in = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
                
            	String line = "";
            	while((line = in.readLine()) != null) {
            		result.append(line);
            		
            	}
            	
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(in!=null){
                    in.close();
                }
                if(con!=null){
                    con.disconnect();
                }
            } catch (IOException e) {
            	System.out.println("---------------ERROR-------------");
                e.printStackTrace();
            }
        }
        return result.toString();
	
		
		
	}
	
	
	
	
}
