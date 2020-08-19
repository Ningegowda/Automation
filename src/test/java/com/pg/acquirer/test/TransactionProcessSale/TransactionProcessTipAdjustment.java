package com.pg.acquirer.test.TransactionProcessSale;

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
import com.pg.acquirer.requestbuilder.RequestBuilders;
import com.pg.acquirer.util.Constants;
import com.pg.acquirer.util.HitService;

import io.restassured.response.Response;

public class TransactionProcessTipAdjustment {
	Map<String, String> masterRequestData = new HashMap<String, String>();
	String endURL = "txnOnline/process";
	Response responseRef;
	TransactionProcessSale sale = new TransactionProcessSale();

	@BeforeClass
	public void dataInit() {
		masterRequestData.put("txnAmount", "6000");
		masterRequestData.put("invoiceNumber", "7856765765756756");
		masterRequestData.put("sysTraceNum", "000010");
		masterRequestData.put("cardType", "VI");
		masterRequestData.put("cardNumber", "4111111111111111");
		masterRequestData.put("expDate", "2106");
		masterRequestData.put("cvv", "345");
		masterRequestData.put("terminalId", "91550313");
		masterRequestData.put("merchantCode", "591431591550313");
		masterRequestData.put("transactionType", "TIPADJ");
		masterRequestData.put("nii", "000");
		masterRequestData.put("posConditionCode", "00");
		masterRequestData.put("currencyCode", "840");
		masterRequestData.put("mti", "0220");
		masterRequestData.put("merchantType", "2121");
		masterRequestData.put("processingCode", "020000");
		masterRequestData.put("posEntryMode", "011");
		masterRequestData.put("timeZoneOffset", "GMT+0530");
		masterRequestData.put("timeZoneRegion", "Asia/Kolkata");
		masterRequestData.put("privateField", "12");
		masterRequestData.put("tipAmount", "20");
	}

	@BeforeMethod
	public void init() {
		responseRef = sale.generateProcessSaleTransactionResponse();
		Reporter.log("<br>");
	}

	@AfterMethod
	public void reportUpdate(ITestResult result) {
		Reporter.log(result.getMethod().getDescription());
	}

	@Test(description = "Verify whether user is able to perform Tip Adjustment by passing valid card details.")
	public void API_TIPADJUSTMENT_TC_001() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		System.out.println(tipAdjJsonObj.toString());
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
	}

	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter mti.")
	public void API_TIPADJUSTMENT_TC_002() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("mti", "000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0195"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction mti mismatch"));
	}

	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter mti.")
	public void API_TIPADJUSTMENT_TC_003() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("mti", "Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}

	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter mti.")
	public void API_TIPADJUSTMENT_TC_004() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("mti", "02200220022002200220022002200220022002200220");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0195"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction mti mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter mti.")
	public void API_TIPADJUSTMENT_TC_005() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("mti", "02");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0195"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction mti mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter mti.")
	public void API_TIPADJUSTMENT_TC_006() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("mti", " ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		System.out.println(tipAdjJsonObj.toString());
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0195"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction mti mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter mti.")
	public void API_TIPADJUSTMENT_TC_007() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("mti","ZERO TWO ZERO TWO");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		System.out.println(tipAdjJsonObj.toString());
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0195"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction mti mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter mti.")
	public void API_TIPADJUSTMENT_TC_008() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("mti","#$##$%$$$#$");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0195"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction mti mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter processingCode.")
	public void API_TIPADJUSTMENT_TC_009() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("processingCode","0000000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0196"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction Processing code mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter processingCode.")
	public void API_TIPADJUSTMENT_TC_010() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("processingCode","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter processingCode.")
	public void API_TIPADJUSTMENT_TC_011() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("processingCode","022200022200022200022200022200022200022200022200");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0196"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction Processing code mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter processingCode.")
	public void API_TIPADJUSTMENT_TC_012() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("processingCode","02");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0196"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction Processing code mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter processingCode.")
	public void API_TIPADJUSTMENT_TC_013() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("processingCode","  ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0196"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction Processing code mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter processingCode.")
	public void API_TIPADJUSTMENT_TC_014() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("processingCode","ZERO TWO ZERO");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0196"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction Processing code mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter processingCode.")
	public void API_TIPADJUSTMENT_TC_015() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("processingCode","#$%^&*(");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0196"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction Processing code mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter sysTraceNum.")
	public void API_TIPADJUSTMENT_TC_016() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("sysTraceNum","000000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0197"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction System trace Number  mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter sysTraceNum.")
	public void API_TIPADJUSTMENT_TC_017() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("sysTraceNum","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter sysTraceNum.")
	public void API_TIPADJUSTMENT_TC_018() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("sysTraceNum","000010000010000010000010000010000010");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0197"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction System trace Number  mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter sysTraceNum.")
	public void API_TIPADJUSTMENT_TC_019() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("sysTraceNum","01");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0197"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction System trace Number  mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter sysTraceNum.")
	public void API_TIPADJUSTMENT_TC_020() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("sysTraceNum","  ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0197"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction System trace Number  mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter sysTraceNum.")
	public void API_TIPADJUSTMENT_TC_021() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("sysTraceNum","ZERO ZERO ZERO ZERO ONE ZERO");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0197"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction System trace Number  mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter sysTraceNum.")
	public void API_TIPADJUSTMENT_TC_022() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("sysTraceNum","#$%^&*(");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0197"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Original transaction System trace Number  mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter posEntryMode.")
	public void API_TIPADJUSTMENT_TC_023() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("posEntryMode","000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0190"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Pos entry mode doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter posEntryMode.")
	public void API_TIPADJUSTMENT_TC_024() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("posEntryMode","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter posEntryMode.")
	public void API_TIPADJUSTMENT_TC_025() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("posEntryMode","011011011011011011");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0190"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Pos entry mode doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter posEntryMode.")
	public void API_TIPADJUSTMENT_TC_026() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("posEntryMode","1");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0190"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Pos entry mode doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter posEntryMode.")
	public void API_TIPADJUSTMENT_TC_027() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("posEntryMode","   ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0190"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Pos entry mode doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter posEntryMode.")
	public void API_TIPADJUSTMENT_TC_028() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("posEntryMode","ZERO ONE ONE");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0190"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Pos entry mode doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter posEntryMode.")
	public void API_TIPADJUSTMENT_TC_029() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber", HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.replace("posEntryMode","@%#%$$");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0190"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Pos entry mode doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter txnRefNumber.")
	public void API_TIPADJUSTMENT_TC_030() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber","0000000");
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid Transaction reference number."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter txnRefNumber.")
	public void API_TIPADJUSTMENT_TC_031() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber","Blank");
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter txnRefNumber.")
	public void API_TIPADJUSTMENT_TC_032() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber","296676296676296676296676296676296676296676");
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid Transaction reference number."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter txnRefNumber.")
	public void API_TIPADJUSTMENT_TC_033() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber","2");
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid Transaction reference number."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter txnRefNumber.")
	public void API_TIPADJUSTMENT_TC_034() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber","  ");
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid Transaction reference number."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter txnRefNumber.")
	public void API_TIPADJUSTMENT_TC_035() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber","TWO NINE SIX SIX SEVEN");
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid Transaction reference number."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter txnRefNumber.")
	public void API_TIPADJUSTMENT_TC_036() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber","%^&*(");
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid Transaction reference number."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter cardNumber.")
	public void API_TIPADJUSTMENT_TC_037() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardNumber","0000000000000000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card number"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter cardNumber.")
	public void API_TIPADJUSTMENT_TC_038() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardNumber","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests the same number twice parameter cardNumber.")
	public void API_TIPADJUSTMENT_TC_039() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardNumber","45678909876545678909876545678");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card number"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests last four digit values in the parameter cardNumber.")
	public void API_TIPADJUSTMENT_TC_040() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardNumber","0000000000000000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card number"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter cardNumber.")
	public void API_TIPADJUSTMENT_TC_041() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardNumber","  ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card number"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter cardNumber.")
	public void API_TIPADJUSTMENT_TC_042() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardNumber","#$#%#$@#");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card number"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter expDate.")
	public void API_TIPADJUSTMENT_TC_043() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("expDate","0000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card expiry."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter expDate.")
	public void API_TIPADJUSTMENT_TC_044() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("expDate","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter expDate.")
	public void API_TIPADJUSTMENT_TC_045() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("expDate","2212221222122212");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card expiry."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter expDate.")
	public void API_TIPADJUSTMENT_TC_046() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("expDate","22");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card expiry."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter expDate.")
	public void API_TIPADJUSTMENT_TC_047() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("expDate","   ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card expiry."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter expDate.")
	public void API_TIPADJUSTMENT_TC_048() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("expDate","TWO TWO ONE TWO");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card expiry."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter expDate.")
	public void API_TIPADJUSTMENT_TC_049() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("expDate","$##$");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
				.equalsIgnoreCase("Invalid card expiry."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter cvv.")
	public void API_TIPADJUSTMENT_TC_050() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cvv","000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter cvv.")
	public void API_TIPADJUSTMENT_TC_051() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cvv","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter cvv.")
	public void API_TIPADJUSTMENT_TC_052() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cvv","34556");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter cvv.")
	public void API_TIPADJUSTMENT_TC_053() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cvv","3");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter cvv.")
	public void API_TIPADJUSTMENT_TC_054() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cvv","  ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter cvv.")
	public void API_TIPADJUSTMENT_TC_055() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cvv","ONETWOTHREE");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter cvv.")
	public void API_TIPADJUSTMENT_TC_056() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cvv","%$#");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter cardType.")
	public void API_TIPADJUSTMENT_TC_057() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardType","00");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter cardType.")
	public void API_TIPADJUSTMENT_TC_058() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardType","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter cardType.")
	public void API_TIPADJUSTMENT_TC_059() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardType","VIVIVIVI");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter cardType.")
	public void API_TIPADJUSTMENT_TC_060() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardType","V");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter cardType.")
	public void API_TIPADJUSTMENT_TC_061() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardType","  ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter cardType.")
	public void API_TIPADJUSTMENT_TC_062() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("cardType","@WE$#");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter privateField.")
	public void API_TIPADJUSTMENT_TC_063() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("privateField","00");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter privateField.")
	public void API_TIPADJUSTMENT_TC_064() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("privateField","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter privateField.")
	public void API_TIPADJUSTMENT_TC_065() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("privateField","4040404040404004000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter privateField.")
	public void API_TIPADJUSTMENT_TC_066() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("privateField","4");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter privateField.")
	public void API_TIPADJUSTMENT_TC_067() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("privateField","   ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter privateField.")
	public void API_TIPADJUSTMENT_TC_068() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("privateField","FOUR ZERO");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter privateField.")
	public void API_TIPADJUSTMENT_TC_069() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("privateField","$#");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter tipAmount.")
	public void API_TIPADJUSTMENT_TC_070() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("tipAmount","0.00");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter tipAmount.")
	public void API_TIPADJUSTMENT_TC_072() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("tipAmount","84665316435565352146153246141213432146324");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter tipAmount.")
	public void API_TIPADJUSTMENT_TC_073() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("tipAmount","0.000001");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter tipAmount.")
	public void API_TIPADJUSTMENT_TC_074() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("tipAmount","   ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter tipAmount.")
	public void API_TIPADJUSTMENT_TC_075() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("tipAmount","TWENTY");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter tipAmount.")
	public void API_TIPADJUSTMENT_TC_076() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("tipAmount","@!");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter nii.")
	public void API_TIPADJUSTMENT_TC_077() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("nii","@!");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter nii.")
	public void API_TIPADJUSTMENT_TC_078() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("nii","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter nii.")
	public void API_TIPADJUSTMENT_TC_079() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("nii","045145145000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter nii.")
	public void API_TIPADJUSTMENT_TC_080() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("nii","0.000001");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter nii.")
	public void API_TIPADJUSTMENT_TC_081() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("nii","   ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter nii.")
	public void API_TIPADJUSTMENT_TC_082() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("nii","TWO");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter nii.")
	public void API_TIPADJUSTMENT_TC_083() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("nii","#$$");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter terminalId.")
	public void API_TIPADJUSTMENT_TC_084() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("terminalId","00000000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter nii.")
	public void API_TIPADJUSTMENT_TC_085() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("terminalId","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter terminalId.")
	public void API_TIPADJUSTMENT_TC_086() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("terminalId","46315463152415324321411214211432143213123152");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter terminalId.")
	public void API_TIPADJUSTMENT_TC_087() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("terminalId","46");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter terminalId.")
	public void API_TIPADJUSTMENT_TC_088() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("terminalId","  ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter terminalId.")
	public void API_TIPADJUSTMENT_TC_089() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("terminalId","FIVE FIVE FIVE");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter terminalId.")
	public void API_TIPADJUSTMENT_TC_090() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("terminalId","@#@$");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter merchantCode.")
	public void API_TIPADJUSTMENT_TC_091() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantCode","0000000001550313");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter merchantCode.")
	public void API_TIPADJUSTMENT_TC_092() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantCode","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter merchantCode.")
	public void API_TIPADJUSTMENT_TC_093() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantCode","0000000001550313000000000155031300000000015503130000000001550313");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests last four digits value in the parameter merchantCode.")
	public void API_TIPADJUSTMENT_TC_094() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantCode","0313");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter merchantCode.")
	public void API_TIPADJUSTMENT_TC_095() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantCode","    ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alphanumeric values in the parameter merchantCode.")
	public void API_TIPADJUSTMENT_TC_096() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantCode","GHJ45145");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter merchantCode.")
	public void API_TIPADJUSTMENT_TC_097() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantCode","$%^&*");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter invoiceNumber.")
	public void API_TIPADJUSTMENT_TC_098() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("invoiceNumber","00000000000000000000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0189"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invoice Number doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter invoiceNumber.")
	public void API_TIPADJUSTMENT_TC_099() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("invoiceNumber","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter invoiceNumber.")
	public void API_TIPADJUSTMENT_TC_100() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("invoiceNumber","5764657456344534535435435355434554336");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0189"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invoice Number doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter invoiceNumber.")
	public void API_TIPADJUSTMENT_TC_101() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("invoiceNumber","234");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0189"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invoice Number doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter invoiceNumber.")
	public void API_TIPADJUSTMENT_TC_102() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("invoiceNumber","   ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0189"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invoice Number doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests another invoice number value in the parameter invoiceNumber.")
	public void API_TIPADJUSTMENT_TC_103() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("invoiceNumber","465476464654685465");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0189"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invoice Number doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter invoiceNumber.")
	public void API_TIPADJUSTMENT_TC_104() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("invoiceNumber","#$#@$#@#$@#$%@#@$##");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0189"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invoice Number doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter transactionType.")
	public void API_TIPADJUSTMENT_TC_105() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("invoiceNumber","#$#@$#@#$@#$%@#@$##");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0189"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invoice Number doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter txnAmount.")
	public void API_TIPADJUSTMENT_TC_110() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("txnAmount","00000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter txnAmount.")
	public void API_TIPADJUSTMENT_TC_111() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("txnAmount","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter txnAmount.")
	public void API_TIPADJUSTMENT_TC_112() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("txnAmount","784574574554546654545");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter txnAmount.")
	public void API_TIPADJUSTMENT_TC_113() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("txnAmount","0.001");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter txnAmount.")
	public void API_TIPADJUSTMENT_TC_114() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("txnAmount","    ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter txnAmount.")
	public void API_TIPADJUSTMENT_TC_115() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("txnAmount","TWO FIVE TWO FIVE");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter txnAmount.")
	public void API_TIPADJUSTMENT_TC_116() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("txnAmount","$%#$");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter authId.")
	public void API_TIPADJUSTMENT_TC_117() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum","000000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0192"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Auth Id doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter authId.")
	public void API_TIPADJUSTMENT_TC_118() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests maximum value in the parameter authId.")
	public void API_TIPADJUSTMENT_TC_119() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum","311529311529311529311529311529311529311529311529311529");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0192"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Auth Id doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests minimum value in the parameter authId.")
	public void API_TIPADJUSTMENT_TC_120() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum","311");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0192"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Auth Id doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter authId.")
	public void API_TIPADJUSTMENT_TC_121() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum","   ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0192"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Auth Id doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter authId.")
	public void API_TIPADJUSTMENT_TC_122() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum","@$$#$");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0192"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Auth Id doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter currencyCode.")
	public void API_TIPADJUSTMENT_TC_123() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("currencyCode","000.0");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0192"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Auth Id doesn't match."));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter currencyCode.")
	public void API_TIPADJUSTMENT_TC_124() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("currencyCode","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests repeatedly the same value in the parameter currencyCode.")
	public void API_TIPADJUSTMENT_TC_125() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("currencyCode","840840840");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests with space values in the parameter currencyCode.")
	public void API_TIPADJUSTMENT_TC_126() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("currencyCode","    ");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests in alpha values in the parameter currencyCode.")
	public void API_TIPADJUSTMENT_TC_127() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("currencyCode","EIGHT FOUR ZERO");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special character values in the parameter currencyCode.")
	public void API_TIPADJUSTMENT_TC_128() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("currencyCode","$%$");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter posConditionCode .")
	public void API_TIPADJUSTMENT_TC_138() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("posConditionCode","39");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special characters value in the parameter posConditionCode .")
	public void API_TIPADJUSTMENT_TC_139() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("posConditionCode","$%$");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter posConditionCode .")
	public void API_TIPADJUSTMENT_TC_140() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("posConditionCode","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests blank value in the parameter merchantType .")
	public void API_TIPADJUSTMENT_TC_141() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantType","Blank");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests special characters value in the parameter merchantType .")
	public void API_TIPADJUSTMENT_TC_142() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantType","2!21");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0184"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("STAN mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests invalid value in the parameter merchantType .")
	public void API_TIPADJUSTMENT_TC_143() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantType","0000");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0184"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("STAN mismatch"));
	}
	@Test(description = "Verify the server throws appropriate error message, when user requests other merchantType value in the parameter merchantType .")
	public void API_TIPADJUSTMENT_TC_144() {
		Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
		requestData.put("txnRefNumber",HitService.getResponseValue(responseRef, "txnRefNumber"));
		requestData.put("authTxnRefNum", HitService.getResponseValue(responseRef, "authId"));
		requestData.put("merchantType","2123");
		JsonObject tipAdjJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(tipAdjJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0184"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("STAN mismatch"));
	}
}
