package com.jkgroup.kingkaid.web.comm.Api;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

public class ApiUtils {

	public static final Map<String,String> userMap = new HashMap<String, String>();
	public static SimpleDateFormat yymmddsdf = new SimpleDateFormat("yyMMdd");
	
	static {
		userMap.put("username", "vistot");
		userMap.put("password", "umz4aea6g97skeect0jtxigvjkrimd0o");
	}
	
	/**
	 * 【网贷天眼】返回获取token失败json
	 * @return
	 */
	private static String errorToken(){
		return "{\"result\":\"-1\",\"data\":{\"token\":\"null\"}}";
	}
	
	
	/**
	 * 【网贷天眼】返回获取标地信息失败
	 * @param msg
	 * @return
	 */
	public static String errorLoans(String code,String msg){
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"result_code\":\""+code+"\",");
		json.append("\"result_msg\":\""+msg+"\",");
		json.append("\"page_count\":\"null\",");
		json.append("\"page_index\":\"null\",");
		json.append("\"loans\":null");
		json.append("}");
		return json.toString();
	}
	/**
	 * 查询用户名密码是否正确
	 * @param username
	 * @param password
	 * @return
	 */
	public static String _getToken(String username,String password){
		//用户名或密码为空
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			StringBuilder json = new StringBuilder();
			json.append("{");
			json.append("\"return\":\"0\",");
			json.append("\"data\":{\"token\":\"\"");
			json.append("}}");
			return json.toString();
		}
		//用户名或密码错误
		if(username.equals(userMap.get("username")) && password.equals(userMap.get("password"))){
			StringBuilder json = new StringBuilder();
			json.append("{");
			json.append("\"return\":\"1\",");
			json.append("\"data\":{\"token\":\""+myMD5(StringUtils.trimToEmpty(userMap.get("username"))
					+StringUtils.trimToEmpty(userMap.get("password")+yymmddsdf.format(new Date()))));
			json.append("\"}}");
			return json.toString();
		}else {
			StringBuilder json = new StringBuilder();
			json.append("{");
			json.append("\"return\":\"0\",");
			json.append("\"data\":{\"token\":\"\"");
			json.append("}}");
			return json.toString();
		}
	}
	/**
	 * 【网贷之家】
	 * @param msg
	 * @return
	 */
	public static String errorHome(String code,String msg){   
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"return\":\""+code+"\",");
		json.append("\"data\":{\"error\":\""+msg+"\"");
		json.append("}}");
		return json.toString();
	}
	
	
	/**
	 * 查询用户名密码是否正确
	 * @param username
	 * @param password
	 * @return
	 */
	public static String getToken(String username,String password){
		
		
		
		//用户名或密码为空
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			return errorToken();
		}
		//用户名或密码错误
		if(!username.equals(userMap.get("username")) || !password.equals(userMap.get("password"))){
			return errorToken();
		}
		
		String token = myMD5(StringUtils.trimToEmpty(userMap.get("username"))+StringUtils.trimToEmpty(userMap.get("password")
				+yymmddsdf.format(new Date())));
		return "{\"result\":\"1\",\"data\":{\"token\":\""+token+"\"}}";
	}
	
	
	/**
	 * MD5加密密码
	 * @param content
	 * @return
	 */
	public static String myMD5(String content) {

		return DigestUtils.md5Hex(content.getBytes());
		
	}
	
	/**
	 * 验证签名是否正确
	 * @param token
	 * @return
	 */
	public static boolean signToken(String token){
		String sign = myMD5(StringUtils.trimToEmpty(userMap.get("username"))+StringUtils.trimToEmpty(userMap.get("password")
				+yymmddsdf.format(new Date())));
		if(sign.equals(token)){
			return true;
		}
		return false;
	}

	
	public static boolean isDate(String dttm, String format) {
	    boolean retValue = false;
	    if (dttm != null) {
	        SimpleDateFormat formatter = new SimpleDateFormat(format);
	        try {
	            formatter.parse(dttm);
	            retValue = true;
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }
	    return retValue;
	}
	
	/**
	 * 查询用户名密码是否正确
	 * @param username
	 * @param password
	 * @return
	 */
	public static String getTokenForRong(String username,String password){
		//用户名或密码为空
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			return "{\"result\":\"0\",\"data\":{\"token\":\"null\"}}";
		}
		//用户名或密码错误
		if(!username.equals(userMap.get("username")) || !password.equals(userMap.get("password"))){
			 return "{\"result\":\"0\",\"data\":{\"token\":\"null\"}}";
		}
		
		String token = myMD5(StringUtils.trimToEmpty(userMap.get("username"))+StringUtils.trimToEmpty(userMap.get("password")
				+yymmddsdf.format(new Date())));
		return "{\"result\":\"1\",\"data\":{\"token\":\""+token+"\"}}";
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
