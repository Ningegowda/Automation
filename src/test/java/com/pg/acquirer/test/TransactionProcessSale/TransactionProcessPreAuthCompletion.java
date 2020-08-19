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

public class TransactionProcessPreAuthCompletion {
    Map<String, String> masterRequestData = new HashMap<String, String>();
    String endURL = "txnOnline/process";
    Response txnRefNumber;
    TransactionProcessSale sale = new TransactionProcessSale();

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
	masterRequestData.put("transactionType", "PREAUTHCOMPLETION");
	masterRequestData.put("nii", "000");
	masterRequestData.put("posConditionCode", "00");
	masterRequestData.put("currencyCode", "840");
	masterRequestData.put("mti", "0220");
	masterRequestData.put("merchantType", "2121");
	masterRequestData.put("processingCode", "000000");
	masterRequestData.put("posEntryMode", "011");
	masterRequestData.put("timeZoneOffset", "GMT+0530");
	masterRequestData.put("timeZoneRegion", "Asia/Kolkata");
    }

    @BeforeMethod
    public void init() {
	txnRefNumber = sale.generateProcessSaleTransactionResponse();

	Reporter.log("<br>");
    }

    @AfterMethod
    public void reportUpdate(ITestResult result) {
	Reporter.log(result.getMethod().getDescription());
    }

    @Test(description = "Verify whether user is able to perform PREAUTHCOMPLETION Transaction by passing valid VISA card details.")
    public void API_PREAUTHCOMPLETION_TC_001() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(preAuthCompleteJsonObj.toString());
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing valid MasterCard card details.")
    public void API_PREAUTHCOMPLETION_TC_002() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC");
	requestData.replace("cardNumber", "5500000000000004");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(preAuthCompleteJsonObj.toString());
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing valid American Express card details.")
    public void API_PREAUTHCOMPLETION_TC_003() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "374352963445140");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	requestData.put("cvv", "3455");
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(preAuthCompleteJsonObj.toString());
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing valid Diner's Club card details.")
    public void API_PREAUTHCOMPLETION_TC_004() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "30000000000004");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing valid Discover card details.")
    public void API_PREAUTHCOMPLETION_TC_005() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DISCOVER");
	requestData.replace("cardNumber", "6011000000000004");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing valid JCB card details.")
    public void API_PREAUTHCOMPLETION_TC_006() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "3088000000000009");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing Visa Card type with Card Number more than maximum length ")
    public void API_PREAUTHCOMPLETION_TC_007() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "41111111111111111234");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing Visa Card type with Card Number less than minimum length ")
    public void API_PREAUTHCOMPLETION_TC_008() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "4111111");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing MasterCard Card type with Card Number more than maximum length ")
    public void API_PREAUTHCOMPLETION_TC_009() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC");
	requestData.replace("cardNumber", "5500000000000004123");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing MasterCard Card type with Card Number less than minimum length ")
    public void API_PREAUTHCOMPLETION_TC_010() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "VI");
	requestData.replace("cardNumber", "5500004123");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing American Express Card type with Card Number more than maximum length ")
    public void API_PREAUTHCOMPLETION_TC_011() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "340000000000009123");
	requestData.replace("cvv", "3457");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing American Express Card type with Card Number less than minimum length ")
    public void API_PREAUTHCOMPLETION_TC_012() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "3400009123");
	requestData.replace("cvv", "3457");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing Diner's Club Card type with Card Number more than maximum length ")
    public void API_PREAUTHCOMPLETION_TC_013() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "30000000000004");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing Diner's Club Card type with Card Number less than minimum length ")
    public void API_PREAUTHCOMPLETION_TC_014() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "3400009123");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing Discover Card type with Card Number more than maximum length ")
    public void API_PREAUTHCOMPLETION_TC_015() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DISCOVER");
	requestData.replace("cardNumber", "6011000000000004123");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing Discover Card type with Card Number less than minimum length ")
    public void API_PREAUTHCOMPLETION_TC_016() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DISCOVER");
	requestData.replace("cardNumber", "60110004123");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing JCB Card type with Card Number more than maximum length ")
    public void API_PREAUTHCOMPLETION_TC_017() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "3088000000000009123");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL PREAUTHCOMPLETION Transaction by passing JCB Card type with Card Number less than minimum length ")
    public void API_PREAUTHCOMPLETION_TC_018() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "30880009123");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid posEntryMode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_019() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "01@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for posEntryMode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_020() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in posEntryMode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_021() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "0");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in posEntryMode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_022() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "0111");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters all Zero's for Posentrymode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_023() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass posEntryMode  parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_024() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("posEntryMode");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for txnAmount as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_025() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server shows success message, when user enters less than minimum length data in txnAmount as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_026() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "1");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in txnAmount as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_027() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "1435345344534");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(preAuthCompleteJsonObj.toString());
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass txnAmount  parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_028() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("txnAmount");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(preAuthCompleteJsonObj.toString());
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass txnAmount  value as zero as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_029() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid terminalId as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_030() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "9155031@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for terminalId as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_031() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in terminalId as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_032() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "9");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in terminalId as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_033() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "9");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass terminalId  parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_034() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("terminalId");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass terminalId  value as zero as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_035() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "00000000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid timeZoneOffset as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_036() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneOffset", "GMT@0530");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for timeZoneOffset as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_037() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneOffset", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass timeZoneOffset  parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_038() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("timeZoneOffset");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid timeZoneRegion as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_039() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneRegion", "Asia@Kolkata");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for timeZoneRegion as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_040() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneRegion", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass timeZoneRegion  parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_041() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("timeZoneRegion");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid invoiceNumber as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_042() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "7856765765756@e");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for invoiceNumber as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_043() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in invoiceNumber as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_044() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "1");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in invoiceNumber as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_045() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "7856765765756755222222222222222222222222");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass invoiceNumber  parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_046() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("invoiceNumber");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass invoiceNumber  value as zero as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_047() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "000000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass cardData block as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_048() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardType");
	requestData.remove("cardNumber");
	requestData.remove("expDate");
	requestData.remove("cvv");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  empty cardData  block as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_049() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "Blank");
	requestData.replace("cardNumber", "Blank");
	requestData.replace("expDate", "Blank");
	requestData.replace("cvv", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid cardType as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_050() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for cardType as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_051() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in cardType as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_052() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "M");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in cardType as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_053() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMCMC");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass cardType  parameter as part of the request")
    public void API_PREAUTHCOMPLETION_TC_054() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardType");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid cardNumber as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_055() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "411111111111111@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for cardNumber as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_056() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass cardNumber parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_057() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardNumber");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("V08"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass cardNumber value as zero as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_058() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "0000000000000000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass VISA cardNumber value for Master card type  as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_059() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for VISA card type  as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_060() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "5500000000000004");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("V08"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for Amex card type  as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_061() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "5500000000000004");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(preAuthCompleteJsonObj.toString());
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("V08"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for Discover card type  as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_062() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DISCOVER");
	requestData.replace("cardNumber", "5500000000000004");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(preAuthCompleteJsonObj.toString());
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("V08"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for JCB card type as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_063() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "5500000000000004");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("V08"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid expDate as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_064() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "210@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for expDate as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_065() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in expDate as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_066() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "21");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in expDate as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_067() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "2106111");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass expDate parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_068() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("expDate");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass expDate value as zero as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_069() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "0000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters expDate MMYY as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_070() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "0421");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters expDate with past date")
    public void API_PREAUTHCOMPLETION_TC_071() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "2004");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters expDate with current date")
    public void API_PREAUTHCOMPLETION_TC_072() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "0208");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify the server throws appropriate error message, when the entryMode value is passed MANUAL and CVV and Expiry date parameter is not passed as request parameter.")
    public void API_PREAUTHCOMPLETION_TC_073() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("expDate");
	requestData.remove("cvv");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when the entryMode value is passed MANUAL and CVV and Expiry date parameter  value is not passed as request parameter.")
    public void API_PREAUTHCOMPLETION_TC_074() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "Blank");
	requestData.replace("cvv", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid cvv as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_075() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "34@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for cvv as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_076() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in cvv as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_077() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "23");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in cvv as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_078() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "2313");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass cvv parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_079() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cvv");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as zero as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_080() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as 3 digits for Amex card type as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_081() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "340000000000009");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as more than 3 digits for VISA card type as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_082() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "3451");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid merchantCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_083() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "59143159155031@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for merchantCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_084() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in merchantCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_085() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "591");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in merchantCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_086() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "5914315915503112");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass merchantCode parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_087() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("merchantCode");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory Field Missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing merchantCode  as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_088() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "591431591550311");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  merchantCode as zeros as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_089() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "000000000000000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid MTI as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_090() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "020@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for MTI as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_091() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in MTI as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_092() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "0");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in MTI as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_093() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "020000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass MTI parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_094() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("mti");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing MTI  as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_095() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "0123");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  MTI as zeros as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_096() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti", "0000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid processingCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_097() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode", "@00000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for processingCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_098() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in processingCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_099() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode", "00");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in processingCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_100() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode", "0000000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass processingCode parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_101() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("processingCode");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing processingCode  as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_102() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode", "123456");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass transactionType as SALE or AUTH for posentrymode 011, MTI 0200, processingcode 020000 as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_103() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("transactionType", "SALE");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid currencyCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_104() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "84@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for currencyCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_105() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in currencyCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_106() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "8");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in currencyCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_107() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "8411");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass currencyCode parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_108() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("currencyCode");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory Field missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  currencyCode vaue as zeros as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_109() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing currencyCode as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_110() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "867");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid sysTraceNum as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_111() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "00001@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for sysTraceNum as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_112() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in sysTraceNum as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_113() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "0001");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in sysTraceNum as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_114() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "000010111");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass sysTraceNum parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_115() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("sysTraceNum");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass  sysTraceNum vaue as zeros as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_116() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "000000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid merchantType as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_117() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType", "@000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for merchantType as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_118() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in merchantType as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_119() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType", "212");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in merchantType as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_120() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType", "30056456");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass merchantType parameter as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_121() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("merchantType");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass merchantType value as zeros as part of the request.")
    public void API_PREAUTHCOMPLETION_TC_122() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType", "0000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	// Assert.assertTrue(HitService.getResponseValue(response,
	// Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with invalid value")
    public void API_PREAUTHCOMPLETION_TC_123() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "0@");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos condition code"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with more than maximum length value as a part of request")
    public void API_PREAUTHCOMPLETION_TC_124() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "0000");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos condition code"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with less than minimum length value as a part of request")
    public void API_PREAUTHCOMPLETION_TC_125() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "0");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos condition code"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter posConditionCode as a part of request")
    public void API_PREAUTHCOMPLETION_TC_126() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("posConditionCode");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos condition code"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter posConditionCode as a part of request")
    public void API_PREAUTHCOMPLETION_TC_127() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "Blank");
	requestData.put("txnRefNumber", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos condition code"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass txnRefNumber with invalid value")
    public void API_PREAUTHCOMPLETION_TC_128() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.put("txnRefNumber", "29798@");
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid Transaction reference number."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass txnRefNumber with more than maximum length value as a part of request")
    public void API_PREAUTHCOMPLETION_TC_129() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.put("txnRefNumber", "297986111111111111111111111111111111111");
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid Transaction reference number."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass txnRefNumber with less than minimum length value as a part of request")
    public void API_PREAUTHCOMPLETION_TC_130() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.put("txnRefNumber", "2");
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid Transaction reference number."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter txnRefNumber as a part of request")
    public void API_PREAUTHCOMPLETION_TC_131() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter txnRefNumber as a part of request")
    public void API_PREAUTHCOMPLETION_TC_132() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.put("txnRefNumber", "Blank");
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass txnRefNumber with all zero's as a part of request")
    public void API_PREAUTHCOMPLETION_TC_133() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.put("txnRefNumber", "000000");
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0118"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid Transaction Id or transaction already Voided "));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass txnAmount with different value for which AUTH txn is not performed")
    public void API_PREAUTHCOMPLETION_TC_134() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "1");
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "txnRefNumber"));
	requestData.put("authTxnRefNum", HitService.getResponseValue(txnRefNumber, "authId"));
	JsonObject preAuthCompleteJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(preAuthCompleteJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid Transaction reference number."));
    }

}