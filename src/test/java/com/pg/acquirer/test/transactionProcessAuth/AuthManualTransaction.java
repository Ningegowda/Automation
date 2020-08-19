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

public class AuthManualTransaction {
	
	Map<String, String> masterRequestData = new HashMap<String, String>();
	String endURL = "processTransaction";

	@BeforeClass
	public void dataInit() {
		
		masterRequestData.put(Constants.PAN, AcquirerServicesTestProperties.getPropertyValue(Constants.PAN));
		masterRequestData.put(Constants.PAN_EXPIRY, AcquirerServicesTestProperties.getPropertyValue(Constants.PAN_EXPIRY));
		masterRequestData.put(Constants.PAN_CVV,AcquirerServicesTestProperties.getPropertyValue(Constants.PAN_CVV));
		masterRequestData.put(Constants.PIN, AcquirerServicesTestProperties.getPropertyValue(Constants.PIN));
		masterRequestData.put(Constants.KSN, AcquirerServicesTestProperties.getPropertyValue(Constants.KSN));
		masterRequestData.put(Constants.IPEK, AcquirerServicesTestProperties.getPropertyValue(Constants.IPEK));
		masterRequestData.put(Constants.BDK, AcquirerServicesTestProperties.getPropertyValue(Constants.BDK));
		masterRequestData.put(Constants.TID, AcquirerServicesTestProperties.getPropertyValue(Constants.TID));
		masterRequestData.put(Constants.MID, AcquirerServicesTestProperties.getPropertyValue(Constants.MID));
		masterRequestData.put(Constants.POS_ENTRYMODE, "011");
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
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing valid card details.")
	public void API_Auth_Manual_TC_001() {
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
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without PAN parameter.")
	public void API_Auth_Manual_TC_002() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.PAN);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing invalid PAN value")
	public void API_Auth_Manual_TC_003() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN, "@#$%^&*@#");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing PAN value with less than minimum length")
	public void API_Auth_Manual_TC_004() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN, Util.getRandomGenerateNumber(4));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing PAN value with all zero's")
	public void API_Auth_Manual_TC_005() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN, "0000000000");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing PAN value")
	public void API_Auth_Manual_TC_006() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction with invalid Expiry Date.")
	public void API_Auth_Manual_TC_011() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "@#$%");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without Expiry Date value")
	public void API_Auth_Manual_TC_012() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without Expiry Date parameter")
	public void API_Auth_Manual_TC_013() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.PAN_EXPIRY);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing the Expiry Date with past date")
	public void API_Auth_Manual_TC_014() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "0910");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing the Expiry Date with all zero's")
	public void API_Auth_Manual_TC_015() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "0000");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing the Expiry Date with less than minimum length value.")
	public void API_Auth_Manual_TC_016() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "012");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Merchant Type parameter value")
	public void API_Auth_Manual_TC_017() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MERCHANT_TYPE, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(""));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Merchant Type"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing invalid Merchant Type parameter value")
	public void API_Auth_Manual_TC_018() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MERCHANT_TYPE, "@#$%");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(" "));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Merchant Type"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing less than minimum length value for  Merchant Type parameter ")
	public void API_Auth_Manual_TC_019() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MERCHANT_TYPE, Util.getRandomGenerateNumber(2));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(" "));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Merchant Type"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Merchant Type parameter.")
	public void API_Auth_Manual_TC_020() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.MERCHANT_TYPE);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing POS enty Mode parameter value.")
	public void API_Auth_Manual_TC_021() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.POS_ENTRYMODE, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing POS enty Mode parameter.")
	public void API_Auth_Manual_TC_022() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.POS_ENTRYMODE);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing POS enty Mode with less than minimum length value.")
	public void API_Auth_Manual_TC_023() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.POS_ENTRYMODE, "1");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing POS enty Mode with invalid value")
	public void API_Auth_Manual_TC_024() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.POS_ENTRYMODE, "@#$");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Card Acceptor Terminal Identification parameter value.")
	public void API_Auth_Manual_TC_025() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TID, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Card Acceptor Terminal Identification parameter.")
	public void API_Auth_Manual_TC_026() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.TID);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Card Acceptor Terminal Identification with less than minimum length value.")
	public void API_Auth_Manual_TC_027() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TID, Util.getRandomGenerateNumber(3));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Card Acceptor Terminal Identification with invalid value")
	public void API_Auth_Manual_TC_028() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TID, "@#$%^");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Card Acceptor Terminal Identification with all Zero's")
	public void API_Auth_Manual_TC_029() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TID, "00000000");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Card Acceptor Identification Code parameter value.")
	public void API_Auth_Manual_TC_030() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MID, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Card Acceptor Identification Code parameter.")
	public void API_Auth_Manual_TC_031() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.MID);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Card Acceptor Identification Code with less than minimum length value.")
	public void API_Auth_Manual_TC_032() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MID, Util.getRandomGenerateNumber(3));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Card Acceptor Identification Code with invalid value")
	public void API_Auth_Manual_TC_033() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MID, "@#$%^&*");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Card Acceptor Identification Code with all Zero's")
	public void API_Auth_Manual_TC_034() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MID, "000000000000000");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Card Acceptor Name / Location parameter value.")
	public void API_Auth_Manual_TC_035() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CARD_ACCEPTOR_NAME, null);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Card Acceptor Name / Location parameter.")
	public void API_Auth_Manual_TC_036() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.CARD_ACCEPTOR_NAME);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Card Acceptor Name / Location with less than minimum length value.")
	public void API_Auth_Manual_TC_037() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CARD_ACCEPTOR_NAME, "AI");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0138"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid CardHolderName"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Card Acceptor Name / Location with invalid value.")
	public void API_Auth_Manual_TC_038() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CARD_ACCEPTOR_NAME, "!@#$%&");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0138"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid CardHolderName"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing PIN Data parameter value.")
	public void API_Auth_Manual_TC_039() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PIN, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing PIN Data with invalid value")
	public void API_Auth_Manual_TC_041() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PIN, "@#$");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(""));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid PIN Data"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Currency Code value.")
	public void API_Auth_Manual_TC_042() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CURRENCY_CODE, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(""));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Currency Code"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Currency Code   parameter.")
	public void API_Auth_Manual_TC_043() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.CURRENCY_CODE);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Currency Code with less than minimum length value.")
	public void API_Auth_Manual_TC_044() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CURRENCY_CODE, Util.getRandomGenerateNumber(2));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(""));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Currency Code"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Currency Code with invalid value")
	public void API_Auth_Manual_TC_045() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CURRENCY_CODE, "!@#");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase(""));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Currency Code"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Transaction Amount value.")
	public void API_Auth_Manual_TC_046() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_AMOUNT, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing Transaction Amount parameter.")
	public void API_Auth_Manual_TC_047() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.TRANSACTION_AMOUNT);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Transaction Amount with less than minimum length value.")
	public void API_Auth_Manual_TC_048() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_AMOUNT, Util.getRandomGenerateNumber(2));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing Transaction Amount with all zero's")
	public void API_Auth_Manual_TC_049() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_AMOUNT, "000");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing CVV value.")
	public void API_Auth_Manual_TC_050() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_CVV, "");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction without passing CVV parameter.")
	public void API_Auth_Manual_TC_051() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.PAN_CVV);
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing CVV with less than minimum length value.")
	public void API_Auth_Manual_TC_052() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_CVV, Util.getRandomGenerateNumber(2));
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing CVV with invalid value")
	public void API_Auth_Manual_TC_053() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_CVV, "!@#");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
	}
	
	@Test(description = "Verify whether user is able to perform  Auth  Transaction by passing transaction Type with invalid value")
	public void API_Auth_Manual_TC_057() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_TYPE, "!@#$");
		
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid transaction Type"));
	}
	
	@AfterMethod
	public void reportUpdate(ITestResult result) {
		Reporter.log(result.getMethod().getDescription());
	}

}
