package com.pk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class HappyServicesUtil {
	
	public static String getHashedPassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(passwordToHash.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}	
	
	public static void main(String[] args) {
		System.out.println(getHashedPassword("user"));
	}

	public static String generateJson(Object message, String root) {
		Gson json = new Gson();
		JsonElement je = json.toJsonTree(message);
	    JsonObject jo = new JsonObject();
	    jo.add(root, je);
		String response = jo.toString();
		return response;
	}
}
