package com.pg.acquirer.test.transactionProcessAuth;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.pg.acquirer.config.AcquirerServicesTestProperties;
import com.pg.acquirer.requestbuilder.RequestBuilder;
import com.pg.acquirer.util.Constants;
import com.pg.acquirer.util.HitService;
import com.pg.acquirer.util.JarExecutor;
import com.pg.acquirer.util.Util;

import io.restassured.response.Response;

public class AuthMagstripe {
	
	Map<String, String> masterRequestData = new HashMap<String, String>();
	String endURL = "processTransaction";
	

	@BeforeClass
	public void dataInit() {
		
		masterRequestData.put(Constants.PAN, AcquirerServicesTestProperties.getPropertyValue(Constants.PAN));
		masterRequestData.put(Constants.PAN_EXPIRY, AcquirerServicesTestProperties.getPropertyValue(Constants.PAN_EXPIRY));
		masterRequestData.put(Constants.PIN, AcquirerServicesTestProperties.getPropertyValue(Constants.PIN));
		masterRequestData.put(Constants.TRACK2, AcquirerServicesTestProperties.getPropertyValue(Constants.TRACK2));
		masterRequestData.put(Constants.KSN, AcquirerServicesTestProperties.getPropertyValue(Constants.KSN));
		masterRequestData.put(Constants.IPEK, AcquirerServicesTestProperties.getPropertyValue(Constants.IPEK));
		masterRequestData.put(Constants.BDK, AcquirerServicesTestProperties.getPropertyValue(Constants.BDK));
		masterRequestData.put(Constants.TID, AcquirerServicesTestProperties.getPropertyValue(Constants.TID));
		masterRequestData.put(Constants.MID, AcquirerServicesTestProperties.getPropertyValue(Constants.MID));
		masterRequestData.put(Constants.POS_ENTRYMODE, "021");
		masterRequestData.put(Constants.MERCHANT_TYPE, AcquirerServicesTestProperties.getPropertyValue(Constants.MERCHANT_TYPE));
		masterRequestData.put(Constants.NII, AcquirerServicesTestProperties.getPropertyValue(Constants.NII));
		masterRequestData.put(Constants.CURRENCY_CODE, AcquirerServicesTestProperties.getPropertyValue(Constants.CURRENCY_CODE));
		masterRequestData.put(Constants.DE55_ENABLE, "false");
		masterRequestData.put(Constants.TRANSACTION_TYPE, "auth");
		masterRequestData.put(Constants.TRANSACTION_AMOUNT, "100");
		masterRequestData.put(Constants.CARD_ACCEPTOR_NAME, AcquirerServicesTestProperties.getPropertyValue(Constants.CARD_ACCEPTOR_NAME));

	}
	
	@BeforeMethod
	public void init() {
		Reporter.log("<br>");
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing valid card details.")
	public void API_Auth_Magstrip_TC_001() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without card number parameter.")
	public void API_Auth_Magstrip_TC_002() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.PAN);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing invalid Card Number value")
	public void API_Auth_Magstrip_TC_003() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN, "!@#$%^&*$#");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing Card Number value with less than minimum length")
	public void API_Auth_Magstrip_TC_004() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN, Util.getRandomGenerateNumber(7));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing card number value")
	public void API_Auth_Magstrip_TC_005() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Transaction  Amount value")
	public void API_Auth_Magstrip_TC_010() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_AMOUNT, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Transaction  Amount parameter")
	public void API_Auth_Magstrip_TC_011() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.TRANSACTION_AMOUNT);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing Transaction  Amount as zero.")
	public void API_Auth_Magstrip_TC_012() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_AMOUNT, "000");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing Transaction Amount less than minimum length.")
	public void API_Auth_Magstrip_TC_013() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_AMOUNT, "0.1");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing STAN value")
	public void API_Auth_Magstrip_TC_014() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.replace(Constants.STAN, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing STAN parameter")
	public void API_Auth_Magstrip_TC_015() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.remove(Constants.STAN);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing STAN with more than maximum length value")
	public void API_Auth_Magstrip_TC_016() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.replace(Constants.STAN ,Util.getRandomGenerateNumber(18));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing STAN with less than minimum length value")
	public void API_Auth_Magstrip_TC_017() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.replace(Constants.STAN,Util.getRandomGenerateNumber(2));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction with invalid Expiry Date format")
	public void API_Auth_Magstrip_TC_018() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "!@#$");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without Expiry Date value")
	public void API_Auth_Magstrip_TC_019() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without Expiry Date parameter")
	public void API_Auth_Magstrip_TC_020() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.PAN_EXPIRY);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing the Expiry Date with past date")
	public void API_Auth_Magstrip_TC_021() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "1012");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing the Expiry Date as MMYY format.")
	public void API_Auth_Magstrip_TC_022() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "0120");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Merchant Type parameter")
	public void API_Auth_Magstrip_TC_023() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.MERCHANT_TYPE);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Merchant Type parameter value")
	public void API_Auth_Magstrip_TC_024() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MERCHANT_TYPE, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing invalid Merchant Type parameter value")
	public void API_Auth_Magstrip_TC_025() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MERCHANT_TYPE, "!@#$%^&");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(""));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchant type"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing less than minimum length value for  Merchant Type parameter")
	public void API_Auth_Magstrip_TC_026() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MERCHANT_TYPE, Util.getRandomGenerateNumber(2));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(""));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchant type"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing POS enty Mode parameter value.")
	public void API_Auth_Magstrip_TC_027() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.POS_ENTRYMODE, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing POS entry Mode parameter.")
	public void API_Auth_Magstrip_TC_028() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.POS_ENTRYMODE);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing POS enty Mode with less than minimum length value.")
	public void API_Auth_Magstrip_TC_029() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.POS_ENTRYMODE, "1");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing invalid POSentrymode parameter value.")
	public void API_Auth_Magstrip_TC_030() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.POS_ENTRYMODE, "@#$");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Card Acceptor Terminal Identification parameter value.")
	public void API_Auth_Magstrip_TC_031() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.replace(Constants.CATID, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Card Acceptor Terminal Identification parameter.")
	public void API_Auth_Magstrip_TC_032() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.remove(Constants.CATID);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing Card Acceptor Terminal Identification with more than maximum length.")
	public void API_Auth_Magstrip_TC_033() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.replace(Constants.CATID, Util.getRandomGenerateNumber(30));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing Card Acceptor Terminal Identification with less than minimum length value.")
	public void API_Auth_Magstrip_TC_034() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.replace(Constants.CATID, Util.getRandomGenerateNumber(2));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing invalid Card Acceptor Terminal Identification .")
	public void API_Auth_Magstrip_TC_035() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.replace(Constants.CATID, "%$#@!%^&");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Card Acceptor Identification Code parameter value.")
	public void API_Auth_Magstrip_TC_036() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.replace(Constants.CAID, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Card Acceptor Identification Code parameter.")
	public void API_Auth_Magstrip_TC_037() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.remove(Constants.CAID);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing Card Acceptor Identification Code with more than maximum length.")
	public void API_Auth_Magstrip_TC_038() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.replace(Constants.CAID, Util.getRandomGenerateNumber(20));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing Card Acceptor Identification Code with less than minimum length value.")
	public void API_Auth_Magstrip_TC_039() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		
		Map<String, String> requestBuildData = new HashMap<String, String>();
		requestBuildData.putAll(RequestBuilder.masterRequestParamData);
		requestBuildData.replace(Constants.CAID, Util.getRandomGenerateNumber(2));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message, requestBuildData);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Card Acceptor Name / Location parameter value.")
	public void API_Auth_Magstrip_TC_040() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CARD_ACCEPTOR_NAME, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Card Acceptor Name / Location parameter.")
	public void API_Auth_Magstrip_TC_041() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.CARD_ACCEPTOR_NAME);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing PIN Data parameter value.")
	public void API_Auth_Magstrip_TC_042() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PIN, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing invalid PIN.")
	public void API_Auth_Magstrip_TC_043() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PIN, "!@#$");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(" "));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid PIN Data"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Track2 Data value.")
	public void API_Auth_Magstrip_TC_044() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRACK2, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(" "));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Track2 Data"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Track2 Data   parameter.")
	public void API_Auth_Magstrip_TC_045() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.TRACK2);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing invalid Track2 Data value.")
	public void API_Auth_Magstrip_TC_047() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRACK2, "!@#$%^&*");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(" "));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase(" Invalid track2 Data"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Currency Code value.")
	public void API_Auth_Magstrip_TC_048() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CURRENCY_CODE, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction without passing Currency Code   parameter.")
	public void API_Auth_Magstrip_TC_049() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.CURRENCY_CODE);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing Currency Code with less than minimum length value.")
	public void API_Auth_Magstrip_TC_050() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.CURRENCY_CODE, Util.getRandomGenerateNumber(2));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(" "));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Currency Code"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth Transaction by passing Currency Code with non existing code value")
	public void API_Auth_Magstrip_TC_051() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.CURRENCY_CODE, Util.getRandomGenerateNumber(3));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(" "));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Currency Code"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing invalid PAN in Track2 parameter")
	public void API_Auth_Magstrip_TC_052() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRACK2, "!@#$%^&*"+"=20121230000080F");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing Invalid expiry date in track2 parameter")
	public void API_Auth_Magstrip_TC_053() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRACK2, "5000000000000009="+"!@#$"+"1230000080F");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing Invalid service Code in track2 parameter")
	public void API_Auth_Magstrip_TC_054() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRACK2, "5000000000000009=2012"+"!@#"+"0000080F");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(" "));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Service Code"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction without Card Acceptor Identification Code parameter.")
	public void API_Auth_Magstrip_TC_056() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.MID);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing invalid Card Acceptor Identification Code  value")
	public void API_Auth_Magstrip_TC_057() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MID, "!@#$%^&");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction without passing Card Acceptor Identification Code  value")
	public void API_Auth_Magstrip_TC_058() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MID, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction without Terminal ID parameter.")
	public void API_Auth_Magstrip_TC_065() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.TID);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing invalid Terminal ID  value")
	public void API_Auth_Magstrip_TC_066() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TID, "!@#$%^&*!@#");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction without passing Terminal ID  value")
	public void API_Auth_Magstrip_TC_067() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TID, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	
	@Test(description = "Verify whether user is able to perform  AUTH Transaction by passing invalid Transaction Type value.")
	public void API_Auth_Magstrip_TC_068() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_TYPE, "!@#$");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		System.out.println(saleRequest.toString());
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(""));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Transaction Type"));
	}
	
	@AfterMethod
	public void reportUpdate(ITestResult result) {
		Reporter.log(result.getMethod().getDescription());
	}

}
