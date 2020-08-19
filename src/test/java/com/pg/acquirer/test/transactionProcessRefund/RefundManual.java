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

public class RefundManual {
	Map<String, String> masterRequestData = new HashMap<String, String>();
	Map<String, String> testData = new HashMap<String, String>();
	String endURL = "processTransaction";

	@BeforeClass
	public void dataInit() {

		masterRequestData.put(Constants.PAN, AcquirerServicesTestProperties.getPropertyValue("pan"));
		masterRequestData.put(Constants.PAN_EXPIRY, AcquirerServicesTestProperties.getPropertyValue("pan_expiry"));
		masterRequestData.put(Constants.PAN_CVV, AcquirerServicesTestProperties.getPropertyValue("pan_cvv"));
		masterRequestData.put(Constants.PIN, AcquirerServicesTestProperties.getPropertyValue("pin"));
		masterRequestData.put(Constants.TRACK2, AcquirerServicesTestProperties.getPropertyValue("track2"));
		masterRequestData.put(Constants.KSN, AcquirerServicesTestProperties.getPropertyValue("ksn"));
		masterRequestData.put(Constants.IPEK, AcquirerServicesTestProperties.getPropertyValue("ipek"));
		masterRequestData.put(Constants.BDK, AcquirerServicesTestProperties.getPropertyValue("bdk"));
		masterRequestData.put(Constants.TID, AcquirerServicesTestProperties.getPropertyValue("tid"));
		masterRequestData.put(Constants.MID, AcquirerServicesTestProperties.getPropertyValue("mid"));
		masterRequestData.put(Constants.POS_ENTRYMODE, "011");
		masterRequestData.put(Constants.MERCHANT_TYPE,
				AcquirerServicesTestProperties.getPropertyValue("merchant_type"));
		masterRequestData.put(Constants.NII, AcquirerServicesTestProperties.getPropertyValue("nii"));
		masterRequestData.put(Constants.CURRENCY_CODE,
				AcquirerServicesTestProperties.getPropertyValue("currency_code"));
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing valid card details.")
	public void API_Refund_Manual_TC001() {
		Map<String, String> requestData = new HashMap<String, String>();
		System.out.println(endURL);
		requestData.putAll(masterRequestData);
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(endURL);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequests(iso8583Message);
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without PAN parameter.")
	public void API_Refund_Manual_TC002() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing invalid PAN value")
	public void API_Refund_Manual_TC003() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN, "$%#76765$#$35");
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing PAN value with less than minimum length")
	public void API_Refund_Manual_TC004() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN, "456765656565");
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing PAN value with all zero's")
	public void API_Refund_Manual_TC005() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN, "0000000000000000");

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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing PAN value")
	public void API_Refund_Manual_TC006() {
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));

	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction with invalid Expiry Date")
	public void API_Refund_Manual_TC011() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "$#%#");
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without Expiry Date value")
	public void API_Refund_Manual_TC012() {
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without Expiry Date parameter")
	public void API_Refund_Manual_TC013() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing the Expiry Date with past date")
	public void API_Refund_Manual_TC014() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_EXPIRY, "1904");
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing the Expiry Date with all zero's")
	public void API_Refund_Manual_TC015() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_CVV, "0000");
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing the Expiry Date with less than minimum length value.")
	public void API_Refund_Manual_TC016() {
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Merchant Type parameter value")
	public void API_Refund_Manual_TC017() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid Merchant Type"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing invalid Merchant Type parameter value")
	public void API_Refund_Manual_TC018() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid Merchant Type"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing less than minimum length value for  Merchant Type parameter ")
	public void API_Refund_Manual_TC019() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.MERCHANT_TYPE, Util.getRandomGenerateNumber(3));
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid Merchant Type"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Merchant Type parameter")
	public void API_Refund_Manual_TC020() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.remove(Constants.MERCHANT_TYPE, "");
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing POS enty Mode parameter value.")
	public void API_Refund_Manual_TC021() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid pos entry mode"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing POS enty Mode parameter.")
	public void API_Refund_Manual_TC022() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing POS enty Mode with less than minimum length value.")
	public void API_Refund_Manual_TC023() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid pos entry mode"));

	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing POS enty Mode with invalid value")
	public void API_Refund_Manual_TC024() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid pos entry mode"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Card Acceptor Terminal Identification parameter value.")
	public void API_Refund_Manual_TC025() {
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Card Acceptor Terminal Identification parameter.")
	public void API_Refund_Manual_TC026() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Card Acceptor Terminal Identification with less than minimum length value.")
	public void API_Refund_Manual_TC027() {
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Card Acceptor Terminal Identification with invalid value")
	public void API_Refund_Manual_TC028() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TID, "#%564");
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Card Acceptor Terminal Identification with all Zero's")
	public void API_Refund_Manual_TC029() {
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Card Acceptor Identification Code parameter value.")
	public void API_Refund_Manual_TC030() {
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Card Acceptor Identification Code parameter.")
	public void API_Refund_Manual_TC031() {
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
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Card Acceptor Identification Code with less than minimum length value.")
	public void API_Refund_Manual_TC032() {
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
		Assert.assertTrue(
				HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Card Acceptor Identification Code with less than minimum length value.")
	public void API_Refund_Manual_TC033() {
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Card Acceptor Identification Code with all Zero's")
	public void API_Refund_Manual_TC034() {
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Card Acceptor Name / Location parameter value.")
	public void API_Refund_Manual_TC035() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CARD_ACCEPTOR_NAME, "");
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Card Acceptor Name / Location parameter.")
	public void API_Refund_Manual_TC036() {
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Card Acceptor Name / Location with less than minimum length value.")
	public void API_Refund_Manual_TC037() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CARD_ACCEPTOR_NAME, "rt");
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Card Acceptor Name / Location with invalid value.")
	public void API_Refund_Manual_TC038() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CARD_ACCEPTOR_NAME, "%$#&$^#$%^%&");
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing PIN Data parameter value.")
	public void API_Refund_Manual_TC039() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PIN,"");
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pin"));
	}

	/*
	 * @Test(description =
	 * "Verify whether user is able to perform  Refund  Transaction by passing PIN Data with less than minimum length value."
	 * ) public void API_Refund_Manual_TC040() { Map<String, String> requestData =
	 * new HashMap<String, String>(); requestData.putAll(masterRequestData);
	 * requestData.replace(Constants.PIN, "12"); String iso8583Message =
	 * JarExecutor.getBase64EncodedValue(requestData);
	 * System.out.println(iso8583Message); JsonObject saleRequest =
	 * RequestBuilder.buildRequest(iso8583Message); Reporter.log("Request : " +
	 * saleRequest.toString()); Response response =
	 * HitService.getJSONResponse(saleRequest.toString(), endURL);
	 * System.out.println(response.asString()); Reporter.log("Response : " +
	 * response.asString());
	 * System.out.println(HitService.getResponseValue(response,
	 * Constants.ERRORCODE));
	 * System.out.println(HitService.getResponseValue(response,
	 * Constants.ERRORMESSAGE));
	 * //Assert.assertTrue(HitService.getResponseValue(response,
	 * Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	 * Assert.assertTrue(HitService.getResponseValue(response,
	 * Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pin")); }
	 */

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing PIN Data with invalid value")
	public void API_Refund_Manual_TC041() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PIN, "$#%");
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
				Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pin"));
	}

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Currency Code value.")
	public void API_Refund_Manual_TC042() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CURRENCY_CODE,"");
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Currency Code   parameter.")
	public void API_Refund_Manual_TC043() {
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Currency Code with less than minimum length value.")
	public void API_Refund_Manual_TC044() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CURRENCY_CODE,Util.getRandomGenerateNumber(2));
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Currency Code with non existing code value")
	public void API_Refund_Manual_TC045() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.CURRENCY_CODE, "987");
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Transaction Amount value.")
	public void API_Refund_Manual_TC046() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_AMOUNT, null);
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing Transaction Amount parameter.")
	public void API_Refund_Manual_TC047() {
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Transaction Amount with less than minimum length value.")
	public void API_Refund_Manual_TC048() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_AMOUNT,Util.getRandomGenerateNumber(2));
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing Transaction Amount with all zero's")
	public void API_Refund_Manual_TC049() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_AMOUNT, "0000");
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing CVV value.")
	public void API_Refund_Manual_TC050() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_CVV,"");
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction without passing CVV parameter.")
	public void API_Refund_Manual_TC051() {
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing CVV with less than minimum length value.")
	public void API_Refund_Manual_TC052() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_CVV,Util.getRandomGenerateNumber(2));
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing CVV with invalid value")
	public void API_Refund_Manual_TC053() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_CVV, "%^^");
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

	@Test(description = "Verify whether user is able to perform  Refund  Transaction by passing CVV with all zero's")
	public void API_Refund_Manual_TC054() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.PAN_CVV, "000");
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

	@Test(description = "Verify whether user is able to perform  refund Transaction without passing Card Acceptor Terminal Identification parameter value.")
	public void API_Refund_Manual_TC055() {
		Map<String, String> requestData = new HashMap<String, String>();
		Map<String, String> isoData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		isoData.putAll(testData);
		isoData.replace(Constants.CATID, "");
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,isoData);
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
	public void API_Refund_Manual_TC056() {
		Map<String, String> requestData = new HashMap<String, String>();
		Map<String, String> isoData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		isoData.putAll(testData);
		isoData.remove(Constants.CATID);
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,isoData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}

	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Card Acceptor Terminal Identification with less than minimum length value.")
	public void API_Refund_Manual_TC057() {
		Map<String, String> requestData = new HashMap<String, String>();
		Map<String, String> isoData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		isoData.putAll(testData);
		isoData.replace(Constants.CATID, Util.getRandomGenerateNumber(7));
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,isoData);
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
	public void API_Refund_Manual_TC058() {
		Map<String, String> requestData = new HashMap<String, String>();
		Map<String, String> isoData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		isoData.putAll(testData);
		isoData.replace(Constants.CAID, "");
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,isoData);
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
	public void API_Refund_Manual_TC059() {
		Map<String, String> requestData = new HashMap<String, String>();
		Map<String, String> isoData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		isoData.putAll(testData);
		isoData.remove(Constants.CAID);
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,isoData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify whether user is able to perform  refund Transaction by passing Card Acceptor Identification Code with less than minimum length value.")
	public void API_Refund_Manual_TC060() {
		Map<String, String> requestData = new HashMap<String, String>();
		Map<String, String> isoData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		isoData.putAll(testData);
		isoData.replace(Constants.CAID,Util.getRandomGenerateNumber(5));
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message,isoData);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
	}

	@Test(description = "Verify whether user is able to perform  Refund Transaction by passing transaction type parameter with invalid value.")
	public void API_Refund_Manual_TC061() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace(Constants.TRANSACTION_TYPE, "^&%$f");
		String iso8583Message = JarExecutor.getBase64EncodedValue(requestData);
		System.out.println(iso8583Message);
		JsonObject saleRequest = RequestBuilder.buildRequest(iso8583Message);
		Reporter.log("Request : " + saleRequest.toString());
		Response response = HitService.getJSONResponse(saleRequest.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		System.out.println(HitService.getResponseValue(response, Constants.ERRORCODE));
		System.out.println(HitService.getResponseValue(response, Constants.ERRORMESSAGE));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid transaction type"));
	}
}
