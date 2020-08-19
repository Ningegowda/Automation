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

public class TransactionProcessRefund {

    Map<String, String> masterRequestData = new HashMap<String, String>();
    String endURL = "txnOnline/process";
    public static String txnRefNumber;

    public String generateProcessSaleTransactionResponse() {
	dataInit();
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	Assert.assertEquals(HitService.getResponseValue(response, Constants.ERRORCODE), "00",
		"Expected response code as 00");
	Assert.assertEquals(HitService.getResponseValue(response, Constants.ERRORMESSAGE), "Approved",
		"Expected response messages as Approved");
	txnRefNumber = HitService.getResponseValue(response, "txnRefNumber");

	return txnRefNumber;
    }

    @BeforeClass
    public void dataInit() {

	masterRequestData.put("txnAmount", "12");
	masterRequestData.put("invoiceNumber", "7856765765756756");
	masterRequestData.put("sysTraceNum", "000010");
	masterRequestData.put("cardType", "VI");
	masterRequestData.put("cardNumber", "4111111111111111");
	masterRequestData.put("expDate", "2106");
	masterRequestData.put("cvv", "345");
	masterRequestData.put("terminalId", "91550313");
	masterRequestData.put("merchantCode", "591431591550313");
	masterRequestData.put("transactionType", "REFUND");
	masterRequestData.put("nii", "000");
	masterRequestData.put("posConditionCode", "00");
	masterRequestData.put("currencyCode", "840");
	masterRequestData.put("mti", "0200");
	masterRequestData.put("processingCode", "200000");
	masterRequestData.put("posEntryMode", "011");
	masterRequestData.put("timeZoneOffset", "GMT+0530");
	masterRequestData.put("timeZoneRegion", "Asia/Kolkata");
	masterRequestData.put("merchantType", "3000");
    }

    @BeforeMethod
    public void init() {
	Reporter.log("<br>");
    }

    @AfterMethod
    public void reportUpdate(ITestResult result) {
	Reporter.log(result.getMethod().getDescription());
    }

    @Test(description = "Verify whether user is able to perform REFUND Transaction by passing valid VISA card details.")
    public void API_REFUND_TC_001() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing valid MasterCard card details.")
    public void API_REFUND_TC_002() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC");
	requestData.replace("cardNumber", "5105105105105100");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing valid American Express card details.")
    public void API_REFUND_TC_003() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "374352963445140");
	requestData.replace("cvv", "3457");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing valid Diner's Club card details.")
    public void API_REFUND_TC_004() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "30000000000004");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing valid Discover card details.")
    public void API_REFUND_TC_005() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "6011000000000004");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing valid JCB card details.")
    public void API_REFUND_TC_006() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "3088000000000009");
	System.out.println(endURL);
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing Visa Card type with Card Number more than maximum length ")
    public void API_REFUND_TC_007() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "VI");
	requestData.replace("cardNumber", "41111111111111111234");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing Visa Card type with Card Number less than minimum length ")
    public void API_REFUND_TC_008() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "VI");
	requestData.replace("cardNumber", "41111111");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing MasterCard Card type with Card Number more than maximum length ")
    public void API_REFUND_TC_009() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC");
	requestData.replace("cardNumber", "5500000000000002342434");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing MasterCard Card type with Card Number less than minimum length ")
    public void API_REFUND_TC_010() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC");
	requestData.replace("cardNumber", "555555");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing American Express Card type with Card Number more than maximum length ")
    public void API_REFUND_TC_011() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "3400000000000093235");
	requestData.replace("cvv", "3457");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing American Express Card type with Card Number less than minimum length ")
    public void API_REFUND_TC_012() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "340000");
	requestData.replace("cvv", "3457");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing Diner's Club Card type with Card Number more than maximum length ")
    public void API_REFUND_TC_013() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "300000000000042334234");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing Diner's Club Card type with Card Number less than minimum length ")
    public void API_REFUND_TC_014() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "300045");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing Discover Card type with Card Number more than maximum length  ")
    public void API_REFUND_TC_015() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "6011000000000004234234");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing Discover Card type with Card Number less than minimum length ")
    public void API_REFUND_TC_016() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "60110");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing JCB Card type with Card Number more than maximum length")
    public void API_REFUND_TC_017() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "3088000000000009345345");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL REFUND Transaction by passing JCB Card type with Card Number less than minimum length ")
    public void API_REFUND_TC_018() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "308809");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid posEntryMode as part of the request.")
    public void API_REFUND_TC_019() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "#$#");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for posEntryMode as part of the request.")
    public void API_REFUND_TC_020() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in posEntryMode as part of the request.")
    public void API_REFUND_TC_021() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "01");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in posEntryMode as part of the request.")
    public void API_REFUND_TC_022() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "0111");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters all Zero's as part of the request.")
    public void API_REFUND_TC_023() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass posEntryMode  parameter as part of the request.")
    public void API_REFUND_TC_024() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("posEntryMode");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enter posEntryMode with all zero's   as part of the request.")
    public void API_REFUND_TC_025() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for txnAmount as part of the request.")
    public void API_REFUND_TC_026() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server shows success message, when user enters less than minimum length data in txnAmount as part of the request.")
    public void API_REFUND_TC_027() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "1");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in txnAmount as part of the request.")
    public void API_REFUND_TC_028() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "1435345344534");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass txnAmount  parameter as part of the request.")
    public void API_REFUND_TC_029() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("txnAmount");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass txnAmount  value as zero as part of the request.")
    public void API_REFUND_TC_030() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "0");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid terminalId as part of the request.")
    public void API_REFUND_TC_031() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "9155031@");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for terminalId as part of the request.")
    public void API_REFUND_TC_032() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in terminalId as part of the request.")
    public void API_REFUND_TC_033() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "1111");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in terminalId as part of the request.")
    public void API_REFUND_TC_034() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "91550311223534");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass terminalId  parameter as part of the request.")
    public void API_REFUND_TC_035() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("terminalId");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass terminalId  value as zero as part of the request.")
    public void API_REFUND_TC_036() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "00000000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid timeZoneOffset as part of the request.")
    public void API_REFUND_TC_037() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneOffset", "#MT+0530");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for timeZoneOffset as part of the request.")
    public void API_REFUND_TC_038() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneOffset", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass timeZoneOffset  parameter as part of the request.")
    public void API_REFUND_TC_039() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneOffset", "NO_PARAM");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString()); //
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid timeZoneRegion as part of the request.")
    public void API_REFUND_TC_040() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneRegion", "Asia$%");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for timeZoneRegion as part of the request.")
    public void API_REFUND_TC_041() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneRegion", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(refundJsonObj.toString());
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass timeZoneRegion  parameter as part of the request.")
    public void API_REFUND_TC_042() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("timeZoneRegion");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid invoiceNumber as part of the request.")
    public void API_REFUND_TC_043() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "785676576575675$%^$%6");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for invoiceNumber as part of the request.")
    public void API_REFUND_TC_044() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in invoiceNumber as part of the request.")
    public void API_REFUND_TC_045() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "1");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in invoiceNumber as part of the request.")
    public void API_REFUND_TC_046() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "785676576575675546456546");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass invoiceNumber  parameter as part of the request.")
    public void API_REFUND_TC_047() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("invoiceNumber");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass invoiceNumber  value as zero as part of the request.")
    public void API_REFUND_TC_048() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "000000000000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass cardData block as part of the request.")
    public void API_REFUND_TC_049() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardType");
	requestData.remove("cardNumber");
	requestData.remove("expDate");
	requestData.remove("cvv");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString()); //
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  empty cardData  block as part of the request.")
    public void API_REFUND_TC_050() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "Blank");
	requestData.replace("cardNumber", "Blank");
	requestData.replace("expDate", "Blank");
	requestData.replace("cvv", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid cardType as part of the request.")
    public void API_REFUND_TC_051() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "@#");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for cardType as part of the request.")
    public void API_REFUND_TC_052() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in cardType as part of the request.")
    public void API_REFUND_TC_053() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "V");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in cardType as part of the request.")
    public void API_REFUND_TC_054() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "VISACARDCARDCARDCRADVISACARDCARDCARDCRAD");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass cardType  parameter as part of the request.")
    public void API_REFUND_TC_055() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardType");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid cardNumber as part of the request.")
    public void API_REFUND_TC_056() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "41111111111111@1");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for cardNumber as part of the request.")
    public void API_REFUND_TC_057() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in cardNumber as part of the request.")
    public void API_REFUND_TC_058() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "42323");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in cardNumber as part of the request.")
    public void API_REFUND_TC_059() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "4111111111111111234234");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass cardNumber parameter as part of the request.")
    public void API_REFUND_TC_060() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardNumber");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass cardNumber value as zero as part of the request.")
    public void API_REFUND_TC_061() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "0000000000000000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass VISA cardNumber value for Master card type  as part of the request.")
    public void API_REFUND_TC_062() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC");
	requestData.replace("cardNumber", "4111111111111111");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for VISA card type  as part of the request.")
    public void API_REFUND_TC_063() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "VI");
	requestData.replace("cardNumber", "5500000000000004");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("V08"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for Amex card type  as part of the request.")
    public void API_REFUND_TC_064() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "5500000000000004");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString()); //
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("V08"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for Discover card type  as part of the request.")
    public void API_REFUND_TC_065() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DISCOVER");
	requestData.replace("cardNumber", "5500000000000004");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("V08"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for JCB card type as part of the request.")
    public void API_REFUND_TC_066() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "5500000000000004");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString()); //
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("V08"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid expDate as part of the request.")
    public void API_REFUND_TC_067() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "210@");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for expDate as part of the request.")
    public void API_REFUND_TC_068() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in expDate as part of the request.")
    public void API_REFUND_TC_069() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "21");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in expDate as part of the request.")
    public void API_REFUND_TC_070() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "21099");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass expDate parameter as part of the request.")
    public void API_REFUND_TC_071() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("expDate");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass expDate value as zero as part of the request.")
    public void API_REFUND_TC_072() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "0000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters expDate MMDD as part of the request.")
    public void API_REFUND_TC_073() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "0621");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters expDate with past date")
    public void API_REFUND_TC_074() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "0403");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters expDate with current date")
    public void API_REFUND_TC_075() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "0508");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify the server throws appropriate error message, when the entryMode value is passed MANUAL and CVV and Expiry date parameter is not passed as request parameter.")
    public void API_REFUND_TC_076() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("expDate");
	requestData.remove("cvv");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when the entryMode value is passed MANUAL and CVV and Expiry date parameter  value is not passed as request parameter.")
    public void API_REFUND_TC_077() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "Blank");
	requestData.replace("cvv", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid cvv as part of the request.")
    public void API_REFUND_TC_078() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "@#@");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for cvv as part of the request.")
    public void API_REFUND_TC_079() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing\""));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in cvv as part of the request.")
    public void API_REFUND_TC_080() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "1");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in cvv as part of the request.")
    public void API_REFUND_TC_081() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "12345");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass cvv parameter as part of the request.")
    public void API_REFUND_TC_082() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cvv");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as zero as part of the request.")
    public void API_REFUND_TC_083() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as 3 digits for Amex card type as part of the request.")
    public void API_REFUND_TC_084() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "374352963445140");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as more than 3 digits for VISA card type as part of the request.")
    public void API_REFUND_TC_085() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "VI");
	requestData.replace("cardNumber", "4111111111111111");
	requestData.replace("cvv", "3453");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid merchantCode as part of the request.")
    public void API_REFUND_TC_086() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "59143159155031$");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for merchantCode as part of the request.")
    public void API_REFUND_TC_087() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in merchantCode as part of the request.")
    public void API_REFUND_TC_088() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "5914");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in merchantCode as part of the request.")
    public void API_REFUND_TC_089() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "591431591550313324234234");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass merchantCode parameter as part of the request.")
    public void API_REFUND_TC_090() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("merchantCode");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing merchantCode  as part of the request.")
    public void API_REFUND_TC_091() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "591431591550312");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  merchantCode as zeros as part of the request.")
    public void API_REFUND_TC_092() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "000000000000000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid MTI as part of the request.")
    public void API_REFUND_TC_093() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "020$");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// merchantId"));

    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for MTI as part of the request.")
    public void API_REFUND_TC_094() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response :" + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in MTI as part of the request.")
    public void API_REFUND_TC_095() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "020");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in MTI as part of the request.")
    public void API_REFUND_TC_096() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "0200000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass MTI parameter as part of the request.")
    public void API_REFUND_TC_097() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("mti");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing MTI  as part of the request.")
    public void API_REFUND_TC_098() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "1200");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid
	// merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  MTI as zeros as part of the request.")
    public void API_REFUND_TC_099() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "0000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid processingCode as part of the request.")
    public void API_REFUND_TC_100() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode", "0000*0");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for processingCode as part of the request.")
    public void API_REFUND_TC_101() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in processingCode as part of the request.")
    public void API_REFUND_TC_102() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode", "00000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in processingCode as part of the request.")
    public void API_REFUND_TC_103() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode", "20000000000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass processingCode parameter as part of the request.")
    public void API_REFUND_TC_104() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("processingCode");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing processingCode  as part of the request.")
    public void API_REFUND_TC_105() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode", "123456");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass transactionType as VOID or AUTH for posentrymode 011, MTI 0200, processingcode 000000 as part of the request.")
    public void API_REFUND_TC_106() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("transactionType", "AUTH");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid currencyCode as part of the request.")
    public void API_REFUND_TC_107() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "84#");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	 Assert.assertTrue(HitService.getResponseValue(response,
	 Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	 Assert.assertTrue(HitService.getResponseValue(response,
	 Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for currencyCode as part of the request.")
    public void API_REFUND_TC_108() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	 Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
		 Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in currencyCode as part of the request.")
    public void API_REFUND_TC_109() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "84");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	 Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
		 Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in currencyCode as part of the request.")
    public void API_REFUND_TC_110() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "8401");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	 Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
		 Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass currencyCode parameter as part of the request.")
    public void API_REFUND_TC_111() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("currencyCode");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	 Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORCODE).equalsIgnoreCase("Z6"));
		 Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields misssing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  currencyCode vaue as zeros as part of the request.")
    public void API_REFUND_TC_112() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
		 Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing currencyCode as part of the request.")
    public void API_REFUND_TC_113() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "123");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
		 Assert.assertTrue(HitService.getResponseValue(response,
		 Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid sysTraceNum as part of the request.")
    public void API_REFUND_TC_114() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "00001#");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for sysTraceNum as part of the request.")
    public void API_REFUND_TC_115() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in sysTraceNum as part of the request.")
    public void API_REFUND_TC_116() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "1");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in sysTraceNum as part of the request.")
    public void API_REFUND_TC_117() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "000010222222");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass sysTraceNum parameter as part of the request.")
    public void API_REFUND_TC_118() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("sysTraceNum");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  sysTraceNum vaue as zeros as part of the request.")
    public void API_REFUND_TC_119() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "000000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid merchantType as part of the request.")
    public void API_REFUND_TC_120() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType", "*000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for merchantType as part of the request.")
    public void API_REFUND_TC_121() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in merchantType as part of the request.")
    public void API_REFUND_TC_122() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType", "1");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in merchantType as part of the request.")
    public void API_REFUND_TC_123() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType", "300034234");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass merchantType parameter as part of the request.")
    public void API_REFUND_TC_124() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("merchantType");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass merchantType value as zeros as part of the request.")
    public void API_REFUND_TC_125() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType", "0000");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter transactionType as a part of request")
    public void API_REFUND_TC_126() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("transactionType");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter transactionType as a part of request")
    public void API_REFUND_TC_127() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("transactionType","Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with invalid value")
    public void API_REFUND_TC_128() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "#$");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos pondition code"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with more than maximum length value as a part of request\r\n" + 
    	"")
    public void API_REFUND_TC_129() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "00864633");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos pondition code"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with less than minimum length value as a part of request")
    public void API_REFUND_TC_130() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "3");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos condition code"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter posConditionCode as a part of request")
    public void API_REFUND_TC_131() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("posConditionCode");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter posConditionCode as a part of request")
    public void API_REFUND_TC_132() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid NII as part of the request.")
    public void API_REFUND_TC_133() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("nii", "$%^");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid nii"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass NII with more than maximum length value as a part of request")
    public void API_REFUND_TC_134() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("nii", "0048393");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid nii"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass NII with less than minimum length value as a part of request")
    public void API_REFUND_TC_135() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("nii", "0");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid nii"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter NII as a part of request")
    public void API_REFUND_TC_136() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("nii");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter NII as a part of request")
    public void API_REFUND_TC_137() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("nii", "Blank");
	JsonObject refundJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(refundJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
}
