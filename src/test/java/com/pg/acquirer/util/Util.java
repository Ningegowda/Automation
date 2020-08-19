package com.pg.acquirer.util;

import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pg.acquirer.config.AcquirerServicesTestProperties;

public class Util {

	public static Gson gsonObj = new Gson();

	/**
	 * extract parameter from jsonObject and build jsonrequest with test data 
	 * @param jsonObject
	 * @param paramter
	 * @param jsonRequest
	 * @return
	 */
	public static JsonObject generateDataForJSONRequest(JsonObject jsonObject, String paramter , JsonObject jsonRequest) {
		JsonElement jsonElement = jsonObject.get(paramter);

		if(jsonElement != null){
			String jsonElementValue = gsonObj.toJson(jsonElement);
			if(jsonElementValue.toString().trim().equalsIgnoreCase("\"\"")) {
				jsonElementValue= AcquirerServicesTestProperties.getPropertyValue(paramter);
			}else if(jsonElementValue.equalsIgnoreCase("\"Blank\"")) {
				jsonElementValue="";
			}else {
				jsonElementValue = jsonElement.toString().replace("\"", "");
			}
			jsonRequest.add(paramter, gsonObj.toJsonTree(jsonElementValue));
		}
		return jsonRequest;
	}
	
	/**
	 * get the property value using property parameter
	 * @param jsonObject
	 * @param paramter
	 * @param propertyParameter
	 * @param jsonRequest
	 * @return
	 */
	public static JsonObject generateDataForJSONRequest(JsonObject jsonObject, String paramter , String propertyParameter,JsonObject jsonRequest) {
		JsonElement jsonElement = jsonObject.get(paramter);

		if(jsonElement != null){
			String jsonElementValue = gsonObj.toJson(jsonElement);
			if(jsonElementValue.toString().trim().equalsIgnoreCase("\"\"")) {
				jsonElementValue= AcquirerServicesTestProperties.getPropertyValue(propertyParameter);
			}else if(jsonElementValue.equalsIgnoreCase("\"Blank\"")) {
				jsonElementValue="";
			}else {
				jsonElementValue = jsonElement.toString().replace("\"", "");
			}
			jsonRequest.add(paramter, gsonObj.toJsonTree(jsonElementValue));
		}
		return jsonRequest;
	}

	/**
	 * extract parameter from jsonObject and build jsonrequest with test data 
	 * @param jsonObject
	 * @param paramter
	 * @param jsonRequest
	 * @return
	 */
	public static JsonObject generateDataForJSONRequest(JsonObject jsonObject, String paramter , JsonObject jsonRequest,String responseValue) {
		JsonElement jsonElement = jsonObject.get(paramter);

		if(jsonElement != null){
			String jsonElementValue = gsonObj.toJson(jsonElement);
			if(jsonElementValue.toString().trim().equalsIgnoreCase("\"\"")) {
				jsonElementValue= responseValue;
			}else if(jsonElementValue.equalsIgnoreCase("\"Blank\"")) {
				jsonElementValue="";
			}else {
				jsonElementValue = jsonElement.toString().replace("\"", "");
			}
			jsonRequest.add(paramter, gsonObj.toJsonTree(jsonElementValue));
		}
		return jsonRequest;
	}


	public static JsonObject generateRandomDataForJSONRequest(String randomNumber, String paramter , JsonObject jsonRequest) {
		jsonRequest.add(paramter, gsonObj.toJsonTree(randomNumber));
		return jsonRequest;
	}

	/**
	 * 
	 * @return - root directory path
	 */
	public static String getRootDirectoryPath() {
		return System.getProperty("user.dir");
	}

	/**
	 * generate random Number
	 * @param length
	 * @return
	 */
	public static String getRandomGenerateNumber(int length) {
		Random random = new Random();
		String randomNumber = new String();
		for(int size=0;size<length;size++) {
			randomNumber = randomNumber +random.nextInt(9);
		}
		return randomNumber;
	}
	public static JsonObject buildJson(JsonObject jsonRequest, String parameter, String parameterValue){
		String paramValue;

		if(null!=parameterValue && parameterValue.length()>0){
			parameterValue=parameterValue.trim().toString();
		}

		if(parameterValue==null ||parameterValue.equalsIgnoreCase(" ")){
			paramValue=" ";
		}else if(null!=parameterValue &&  parameterValue.equalsIgnoreCase("NO_PARAM")){
			return jsonRequest;
		}else if(null!=parameterValue &&parameterValue.equalsIgnoreCase("Blank")){
			//paramValue="\"\"";
			paramValue="";
		}else if(null!=parameterValue && parameterValue.equalsIgnoreCase("null")){
			paramValue=null;
		}else{
			paramValue=parameterValue.trim().toString();
		}
		jsonRequest.addProperty(parameter, paramValue);
		return jsonRequest;
	}

}
