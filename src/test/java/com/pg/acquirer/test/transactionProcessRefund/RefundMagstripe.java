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

import io.restassured.response.Response;

public class RefundMagstripe {
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
		masterRequestData.put(Constants.POS_ENTRYMODE, "021");
		masterRequestData.put(Constants.MERCHANT_TYPE, AcquirerServicesTestProperties.getPropertyValue("merchant_type"));
		masterRequestData.put(Constants.NII, AcquirerServicesTestProperties.getPropertyValue("nii"));
		masterRequestData.put(Constants.CURRENCY_CODE, AcquirerServicesTestProperties.getPropertyValue("currency_code"));
		masterRequestData.put(Constants.DE55_ENABLE, "false");
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
	
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing valid card details.")
	public void API_Refund_Magstrip_TC001() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		String iso8583Message = JarExecutor.getBase64EncodedValue(isoMessageData);
		//String iso8583Message="NjAwMDc3MDAwMDAyMDA3NDNDNDU4MDAwRTg5MDhERDg0QUFBMjRCRUJEMDRFM0UzNUJGRENCM0U5MEQxQUFDOUFBNEQ2NENDNkIxMUMxMDlFRjUyMjA3NTExNjU2NzRFNzBENzVCRkY3ODc4MkE4NzI5ODFGMzIwQjU2RTIwMTkwQjEzNTdBQUUyRUY4OUUwNjUzMDExNzkwN0YyOTNCNTZCRjEyODk0RTM0MTdDMkZBOEExMkVEQzA4Rjc0MkQ1NjkxRkMxQTU0REQ5OUVBRTY4QTk5OTMyMUJFRTgyMzkyOTI5NEZGMTExNzkzMTM4REE1MkY2Mjg0MkEyMDA5NzA0MDFDMzBCQzAwRUUyQTIyQTlENjEyQjQ5MTNERTJBOUQ2RkFBQzgyNkJFRDU4NUVGRkYzRTg1QUFGRjIzMkZFM0Y0NURBQzdCQjk4NkE1RDFBMTg1NEQ1Rjg0RjkyQTEzNEZFNUM4M0ZDNzM3Mjc2OTM5MjE4RjU4RURDMjE2NjhEMTdENUM2RjAzQkREMzBGRTJGNTEzNEVCQjdGMDkwOTc5QjhEQTIzMDkxOEVDODgzMkYzNjg3OERBRDg4NzRDOEExRkRFRkQwN0UxQTE1NERFMjFBNDhDMjhDQQ==";
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
	
	@Test(description = "Verify whether user is able to perform  Refund Transaction without card number parameter.")
	public void API_Refund_Magstrip_TC002() {
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
	
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing invalid Card Number value")
	public void API_Refund_Magstrip_TC003() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.PAN,"#$#$7326523#");	
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));

	}
	
	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing PAN value with less than minimum length")
	public void API_Refund_Magstrip_TC004() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.PAN,"456765656565");	
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));

	}
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing card number value")
	public void API_Refund_Magstrip_TC005() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.PAN,"");	
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));

	}
	
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Transaction  Amount value")
	public void API_Refund_Magstrip_TC010() {
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Transaction  Amount parameter")
	public void API_Refund_Magstrip_TC011() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));

}
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing STAN value")
	public void API_Refund_Magstrip_TC012() {
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing STAN value")
	public void API_Refund_Magstrip_TC013() {
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing STAN with less than minimum length value")
	public void API_Refund_Magstrip_TC014() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.STAN,"343");			
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
	
	@Test(description = "Verify whether user is able to perform  Refund Transaction with invalid Expiry Date format")
	public void API_Refund_Magstrip_TC015() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.PAN_EXPIRY,"3$#4");			
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction without Expiry Date value")
	public void API_Refund_Magstrip_TC016() {
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
	
	@Test(description = "Verify whether user is able to perform  Refund Transaction without Expiry Date parameter")
	public void API_Refund_Magstrip_TC017() {
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing the Expiry Date with past date")
	public void API_Refund_Magstrip_TC018() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		isoMessageData.replace(Constants.PAN_EXPIRY,"1902");	
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Merchant Type parameter")
	public void API_Refund_Magstrip_TC019() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		isoMessageData.remove(Constants.MERCHANT_TYPE);	
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Merchant Type parameter value")
	public void API_Refund_Magstrip_TC020() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		isoMessageData.replace(Constants.MERCHANT_TYPE,"");	
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
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchant Type"));
	}
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing invalid Merchant Type parameter value")
	public void API_Refund_Magstrip_TC021() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		isoMessageData.replace(Constants.MERCHANT_TYPE,"$#$5465");	
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
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchant Type"));
	}
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing less than minimum length value for  Merchant Type parameter ")
	public void API_Refund_Magstrip_TC022() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		isoMessageData.replace(Constants.MERCHANT_TYPE,"3");	
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
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchant Type"));
	}
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing POS enty Mode parameter value.")
	public void API_Refund_Magstrip_TC023() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		isoMessageData.replace(Constants.POS_ENTRYMODE,"");	
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
	}
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing POS enty Mode parameter.")
	public void API_Refund_Magstrip_TC024() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		isoMessageData.remove(Constants.POS_ENTRYMODE);
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing POS enty Mode with less than minimum length value.")
	public void API_Refund_Magstrip_TC025() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		isoMessageData.replace(Constants.POS_ENTRYMODE,"0");
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Card Acceptor Terminal Identification parameter value.")
	public void API_Refund_Magstrip_TC026() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		isoMessageData.replace(Constants.CATID,"");
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
}
	
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Card Acceptor Terminal Identification parameter.")
	public void API_Refund_Magstrip_TC027() {
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing Card Acceptor Terminal Identification with less than minimum length value.")
	public void API_Refund_Magstrip_TC028() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.CATID,"5914315");
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
	
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Card Acceptor Identification Code parameter value.")
	public void API_Refund_Magstrip_TC029() {
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Card Acceptor Identification Code parameter.")
	public void API_Refund_Magstrip_TC030() {
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing Card Acceptor Identification Code with less than minimum length value.")
	public void API_Refund_Magstrip_TC031() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		requestData.replace(Constants.CAID,"5914");
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Card Acceptor Name / Location parameter value.")
	public void API_Refund_Magstrip_TC032() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.CARD_ACCEPTOR_NAME,"");
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Card Acceptor Name / Location parameter.")
	public void API_Refund_Magstrip_TC033() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.CARD_ACCEPTOR_NAME);
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing Card Acceptor Name / Location with less than minimum length value.")
	public void API_Refund_Magstrip_TC034() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.CARD_ACCEPTOR_NAME,"Tu");
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing PIN Data parameter value.")
	public void API_Refund_Magstrip_TC035() {
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Track2 Data value.")
	public void API_Refund_Magstrip_TC038() {
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
	
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Track2 Data   parameter.")
	public void API_Refund_Magstrip_TC039() {
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
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Currency Code value.")
	public void API_Refund_Magstrip_TC041() {
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
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid currency code"));
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction without passing Currency Code   parameter.")
	public void API_Refund_Magstrip_TC042() {
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
				Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid currency code"));
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing Currency Code with less than minimum length value.")
	public void API_Refund_Magstrip_TC043() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.CURRENCY_CODE,"1");
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid currency code"));
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing Currency Code with non existing code value")
	public void API_Refund_Magstrip_TC044() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.remove(Constants.CURRENCY_CODE,"1988");
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
				Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid currency code"));
}
	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing transaction type parameter with invalid value.")
	public void API_Refund_Magstrip_TC045() {
		Map<String, String> isoMessageData = new HashMap<String, String>();
		Map<String, String> requestData = new HashMap<String, String>();
		isoMessageData.putAll(masterRequestData);
		requestData.putAll(testData);
		isoMessageData.replace(Constants.TRANSACTION_TYPE,"%$^GHF");
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid transaction type"));
}
}