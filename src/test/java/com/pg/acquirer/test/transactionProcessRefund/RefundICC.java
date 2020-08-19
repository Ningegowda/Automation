package com.pg.acquirer.test.transactionProcessRefund;

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

public class RefundICC {
	Map<String, String> masterRequestData = new HashMap<String, String>();
	Map<String, String> testData= new HashMap<String, String>();
	String endURL = "processTransaction";

	@BeforeClass
	public void dataInit() {
		
		masterRequestData.put(Constants.PAN, AcquirerServicesTestProperties.getPropertyValue("pan"));
		masterRequestData.put(Constants.PAN_EXPIRY, AcquirerServicesTestProperties.getPropertyValue("pan_expiry"));
		masterRequestData.put(Constants.PAN_CVV,AcquirerServicesTestProperties.getPropertyValue("pan_cvv"));
		masterRequestData.put(Constants.PIN, AcquirerServicesTestProperties.getPropertyValue("pin"));
		masterRequestData.put(Constants.TRACK2, AcquirerServicesTestProperties.getPropertyValue("track2"));
		masterRequestData.put(Constants.KSN, AcquirerServicesTestProperties.getPropertyValue("ksn"));
		masterRequestData.put(Constants.IPEK, AcquirerServicesTestProperties.getPropertyValue("ipek"));
		masterRequestData.put(Constants.BDK, AcquirerServicesTestProperties.getPropertyValue("bdk"));
		masterRequestData.put(Constants.TID, AcquirerServicesTestProperties.getPropertyValue("tid"));
		masterRequestData.put(Constants.MID, AcquirerServicesTestProperties.getPropertyValue("mid"));
		masterRequestData.put(Constants.POS_ENTRYMODE, "055");
		masterRequestData.put(Constants.MERCHANT_TYPE, AcquirerServicesTestProperties.getPropertyValue("merchant_type"));
		masterRequestData.put(Constants.NII, AcquirerServicesTestProperties.getPropertyValue("nii"));
		masterRequestData.put(Constants.CURRENCY_CODE, AcquirerServicesTestProperties.getPropertyValue("currency_code"));
		masterRequestData.put(Constants.DE55_ENABLE, "True");
		masterRequestData.put(Constants.TRANSACTION_TYPE, "auth");
		masterRequestData.put(Constants.TRANSACTION_AMOUNT, "100");
			masterRequestData.put(Constants.CARD_ACCEPTOR_NAME,AcquirerServicesTestProperties.getPropertyValue("can")); 
		
		testData.put(Constants.KSN, AcquirerServicesTestProperties.getPropertyValue("ksn"));
		testData.put(Constants.STAN, AcquirerServicesTestProperties.getPropertyValue("stan"));
		testData.put(Constants.CATID, AcquirerServicesTestProperties.getPropertyValue("catid"));
		testData.put(Constants.CAID, AcquirerServicesTestProperties.getPropertyValue("caid"));
		System.out.println(testData);

	}
	
	@BeforeMethod
	public void init() {
		Reporter.log("<br>");
	}
	
	@AfterMethod
	public void reportUpdate(ITestResult result) {
		Reporter.log(result.getMethod().getDescription());
	}
	
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing valid card details.")
	public void API_Refund_ICC_TC001() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
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
	
	@Test(description = "Verify whether user is able to perform  refund Transaction without card number parameter.")
	public void API_Refund_ICC_TC002() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.PAN);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing invalid Card Number value")
	public void API_Refund_ICC_TC003() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.PAN, "@#$%^&*@#");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card numbe"));
	}
	
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Card Number value with less than minimum length")
	public void API_Refund_ICC_TC004() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.PAN, Util.getRandomGenerateNumber(4));	
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card numbe"));
	}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing card number value")
	public void API_Refund_ICC_TC005() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.PAN,"");	
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card numbe"));
	}
	
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Transaction  Amount value")
	public void API_Refund_ICC_TC008() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.TRANSACTION_AMOUNT,"");			
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Transaction  Amount parameter")
	public void API_Refund_ICC_TC009() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.TRANSACTION_AMOUNT);			
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
}
	
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Transaction  Amount as zero.")
	public void API_Refund_ICC_TC010() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.TRANSACTION_AMOUNT,"0000");				
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Transaction Amount less than minimum length. ")
	public void API_Refund_ICC_TC011() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.TRANSACTION_AMOUNT,"1");		
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
}
	
	
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing STAN value")
	public void API_Refund_ICC_TC012() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.STAN,"");			
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing STAN parameter")
	public void API_Refund_ICC_TC013() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.remove(Constants.STAN);			
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing STAN with more than maximum length value")
	public void API_Refund_ICC_TC014() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.STAN,"000000001");			
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing STAN with less than minimum length value")
	public void API_Refund_ICC_TC015() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.STAN,"0001");			
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing STAN with invalid value value")
	public void API_Refund_ICC_TC016() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.STAN,"*&65456659");			
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
}

	@Test(description = "Verify whether user is able to perform  refund Transaction without passing KSN value")
	public void API_Refund_ICC_TC017() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.KSN,"");			
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}

	@Test(description = "Verify whether user is able to perform  refund Transaction without passing KSN parameter")
	public void API_Refund_ICC_TC018() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.remove(Constants.KSN);			
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing KSN with more than maximum length value")
	public void API_Refund_ICC_TC019() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.remove(Constants.KSN,"FFFF9876543210E00010E00001");			
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0142"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid ksn"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing KSN with less than minimum length value")
	public void API_Refund_ICC_TC020() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.remove(Constants.KSN,"F1");		
		requestData.putAll(testData);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0142"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid ksn"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing KSN with invalid value")
	public void API_Refund_ICC_TC021() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.remove(Constants.KSN,"&^%$TGF5432qa");	
		requestData.putAll(testData);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0142"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid ksn"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing iso8583 value")
	public void API_Refund_ICC_TC022() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		JsonObject saleRequest = RequestBuilder.buildRequest("",requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing iso8583 parameter")
	public void API_Refund_ICC_TC023() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		JsonObject saleRequest = RequestBuilder.buildRequest(null,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	
	@Test(description = "Verify whether user is able to perform  refund Transaction with invalid Expiry Date format")
	public void API_Refund_ICC_TC024() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.PAN_EXPIRY,"1802");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without Expiry Date value")
	public void API_Refund_ICC_TC025() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.PAN_EXPIRY,"");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without Expiry Date parameter")
	public void API_Refund_ICC_TC026() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.PAN_EXPIRY);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing the Expiry Date as MMYY format.")
	public void API_Refund_ICC_TC027() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.PAN_EXPIRY,"1220");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing the Expiry Date with past date")
	public void API_Refund_ICC_TC028() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.PAN_EXPIRY,"1912");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Merchant Type parameter")
	public void API_Refund_ICC_TC029() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.MERCHANT_TYPE);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Merchant Type parameter value")
	public void API_Refund_ICC_TC030() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.MERCHANT_TYPE,"");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Merchant Type"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing invalid Merchant Type parameter value")
	public void API_Refund_ICC_TC031() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.MERCHANT_TYPE,"#@565564##");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Merchant Type"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing less than minimum length value for  Merchant Type parameter")
	public void API_Refund_ICC_TC032() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.MERCHANT_TYPE,"12");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Merchant Type"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing POS enty Mode parameter value.")
	public void API_Refund_ICC_TC033() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.POS_ENTRYMODE,"");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing POS enty Mode parameter.")
	public void API_Refund_ICC_TC034() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.POS_ENTRYMODE);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing POS enty Mode with less than minimum length value.")
	public void API_Refund_ICC_TC035() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.POS_ENTRYMODE,"0");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing POS enty Mode 051 & de55.enable=false")
	public void API_Refund_ICC_TC036() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.DE55_ENABLE,"false");
		isoMessageData.replace(Constants.POS_ENTRYMODE,"051");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("96"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid EMV Data Format"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Card Acceptor Terminal Identification parameter value.")
	public void API_Refund_ICC_TC037() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.CATID,"");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Card Acceptor Terminal Identification parameter.")
	public void API_Refund_ICC_TC038() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.remove(Constants.CATID);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Card Acceptor Terminal Identification with more than maximum length.")
	public void API_Refund_ICC_TC039() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.CATID,"201909068877787");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Card Acceptor Terminal Identification with less than minimum length value.")
	public void API_Refund_ICC_TC040() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.CATID,"201");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Card Acceptor Identification Code parameter value.")
	public void API_Refund_ICC_TC041() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.CAID,"");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
}
	
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Card Acceptor Identification Code parameter.")
	public void API_Refund_ICC_TC042() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.CAID);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Card Acceptor Identification Code with more than maximum length.")
	public void API_Refund_ICC_TC043() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.CAID,"591431591550313591431591550313591431591550313591431591550313");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Card Acceptor Identification Code with less than minimum length value.")
	public void API_Refund_ICC_TC044() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.CAID,"59143159155");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Card Acceptor Name parameter value.")
	public void API_Refund_ICC_TC045() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace("card_acceptor_name","");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Card Acceptor Name parameter.")
	public void API_Refund_ICC_TC046() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove("card_acceptor_name");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Card Acceptor Name with less than minimum length value.")
	public void API_Refund_ICC_TC047() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace("card_acceptor_name","TU");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0138"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid CardHolderName"));
		
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing PIN Data parameter value.")
	public void API_Refund_ICC_TC048() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.PIN,"");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0138"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pin"));
	}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Track2 Data value.")
	public void API_Refund_ICC_TC050() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.TRACK2,"");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0138"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Track2 Data"));
	}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Track2 Data   parameter.")
	public void API_Refund_ICC_TC051() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.TRACK2);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Currency Code value.")
	public void API_Refund_ICC_TC053() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.CURRENCY_CODE,"");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Currency Code   parameter.")
	public void API_Refund_ICC_TC054() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.CURRENCY_CODE);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Currency Code with less than minimum length value.")
	public void API_Refund_ICC_TC055() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.CURRENCY_CODE,"1");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("invalid currency code"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Currency Code with non existing code value")
	public void API_Refund_ICC_TC056() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.CURRENCY_CODE,"480");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("invalid currency code"));
}
	
	@Test(description = "Verify whether user is able to perform  refund Transaction without Card Acceptor Identification Code parameter in json request.")
	public void API_Refund_ICC_TC057() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.remove(Constants.CAID);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Card Acceptor Identification Code parameter value in json request.")
	public void API_Refund_ICC_TC058() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.CAID,"");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing MID value with less than minimum length")
	public void API_Refund_ICC_TC059() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.MID,"59143");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing invalid MID value")
	public void API_Refund_ICC_TC060() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.MID,"%66#$55546566545");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing invalid MID value")
	public void API_Refund_ICC_TC061() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.MID,"%66#$55546566545");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing invalid MID value")
	public void API_Refund_ICC_TC068() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.TRANSACTION_TYPE,"%hg%%4");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid transaction type"));
}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing without Card Acceptor Terminal Identification Code parameter")
	public void API_Refund_ICC_TC069() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.CATID);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing Card Acceptor Terminal Identification Code value with all zeros")
	public void API_Refund_ICC_TC070() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.CATID,"00000000");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing Card Acceptor Terminal Identification Code value with less than minimum length ")
	public void API_Refund_ICC_TC071() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.CATID,"201");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing Card Acceptor Terminal Identification Code value with invalid value")
	public void API_Refund_ICC_TC072() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.CATID,"#4$6767$");
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,requestData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
}
}