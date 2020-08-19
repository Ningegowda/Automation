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

public class TransactionProcessAuth {
    Map<String, String> masterRequestData = new HashMap<String, String>();
    String endURL = "txnOnline/process";

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
	masterRequestData.put("transactionType", "AUTH");
	masterRequestData.put("nii", "000");
	masterRequestData.put("posConditionCode", "06");
	masterRequestData.put("currencyCode", "840");
	masterRequestData.put("mti", "0100");
	masterRequestData.put("processingCode", "000000");
	masterRequestData.put("posEntryMode", "011");
	masterRequestData.put("timeZoneOffset", "GMT+0530");
	masterRequestData.put("timeZoneRegion", "Asia/Kolkata");
	//masterRequestData.put("merchantType", "3000");
    }

    @BeforeMethod
    public void init() {
	Reporter.log("<br>");
    }

    @AfterMethod
    public void reportUpdate(ITestResult result) {
	Reporter.log(result.getMethod().getDescription());
    }

    @Test(description = "Verify whether user is able to perform AUTH Transaction by passing valid VISA card details.")
    public void API_AUTH_TC_001() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing valid MasterCard card details.")
    public void API_AUTH_TC_002() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC");
	requestData.replace("cardNumber", "5105105105105100");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing valid American Express card details.")
    public void API_AUTH_TC_003() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "374352963445140");
	requestData.replace("cvv", "3454");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(authJsonObj.toString());
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing valid Diner's Club card details.")
    public void API_AUTH_TC_004() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "30000000000004");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing valid Discover card details.")
    public void API_AUTH_TC_005() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "6011000000000004");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing valid JCB card details.")
    public void API_AUTH_TC_006() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "3088000000000009");
	System.out.println(endURL);
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing Visa Card type with Card Number more than maximum length")
    public void API_AUTH_TC_007() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "VI");
	requestData.replace("cardNumber", "41111111111111111234");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing Visa Card type with Card Number less than minimum length ")
    public void API_AUTH_TC_008() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "VI");
	requestData.replace("cardNumber", "41111111");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing MasterCard Card type with Card Number more than maximum length ")
    public void API_AUTH_TC_009() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC");
	requestData.replace("cardNumber", "5500000000000002342434");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing MasterCard Card type with Card Number less than minimum length ")
    public void API_AUTH_TC_010() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "MC");
	requestData.replace("cardNumber", "555555");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing American Express Card type with Card Number more than maximum length ")
    public void API_AUTH_TC_011() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "3400000000000093235");
	requestData.replace("cvv", "3457");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing American Express Card type with Card Number less than minimum length ")
    public void API_AUTH_TC_012() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "AX");
	requestData.replace("cardNumber", "340000");
	requestData.replace("cvv", "3457");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing Diner's Club Card type with Card Number more than maximum length ")
    public void API_AUTH_TC_013() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "300000000000042334234");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing Diner's Club Card type with Card Number less than minimum length ")
    public void API_AUTH_TC_014() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "300045");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing Discover Card type with Card Number more than maximum length ")
    public void API_AUTH_TC_015() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "6011000000000004234234");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing Discover Card type with Card Number less than minimum length ")
    public void API_AUTH_TC_016() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "DC");
	requestData.replace("cardNumber", "60110");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing JCB Card type with Card Number more than maximum length")
    public void API_AUTH_TC_017() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "3088000000000009345345");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify whether user is able to perform MANUAL AUTH Transaction by passing JCB Card type with Card Number less than minimum length ")
    public void API_AUTH_TC_018() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType", "JCB");
	requestData.replace("cardNumber", "308809");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters invalid posEntryMode as part of the request.")
    public void API_AUTH_TC_019() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "#$#");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for posEntryMode as part of the request.")
    public void API_AUTH_TC_020() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in posEntryMode as part of the request.")
    public void API_AUTH_TC_021() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "01");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in posEntryMode as part of the request.")
    public void API_AUTH_TC_022() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "0111");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass posEntryMode  parameter as part of the request.")
    public void API_AUTH_TC_023() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("posEntryMode");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enter posEntryMode with all zero's   as part of the request.")
    public void API_AUTH_TC_024() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode","000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }

    /*
     * @Test(description =
     * "Verify the server throws appropriate error message, when user does not enter value for txnAmount as part of the request."
     * ) public void API_AUTH_TC_025() { Map<String, String> requestData = new
     * HashMap<String, String>(); requestData.putAll(masterRequestData);
     * requestData.replace("txnAmount","Blank"); JsonObject authJsonObj =
     * RequestBuilders.buildRequest(requestData); Response response =
     * HitService.getJSONResponse(authJsonObj.toString(), endURL);
     * System.out.println(response.asString()); Reporter.log("Response : " +
     * response.asString()); Assert.assertTrue(HitService.getResponseValue(response,
     * Constants.ERRORCODE).equalsIgnoreCase("Z6"));
     * Assert.assertTrue(HitService.getResponseValue(response,
     * Constants.ERRORMESSAGE) .equalsIgnoreCase("Mandatory fields missing")); }
     */
    @Test(description = "Verify the server shows success message, when user enters less than minimum length data in txnAmount as part of the request.")
    public void API_AUTH_TC_026() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount","1");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in txnAmount as part of the request.")
    public void API_AUTH_TC_027() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount","122222222222222222");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid amount."));
    }

    /*
     * @Test(description =
     * "Verify the server throws appropriate error message, when user does not pass txnAmount  parameter as part of the request."
     * ) public void API_AUTH_TC_028() { Map<String, String> requestData = new
     * HashMap<String, String>(); requestData.putAll(masterRequestData);
     * requestData.remove("txnAmount"); JsonObject authJsonObj =
     * RequestBuilders.buildRequest(requestData); Response response =
     * HitService.getJSONResponse(authJsonObj.toString(), endURL);
     * System.out.println(response.asString()); Reporter.log("Response : " +
     * response.asString()); Assert.assertTrue(HitService.getResponseValue(response,
     * Constants.ERRORCODE).equalsIgnoreCase("Z6"));
     * Assert.assertTrue(HitService.getResponseValue(response,
     * Constants.ERRORMESSAGE) .equalsIgnoreCase("Mandatory fields missing")); }
     */
    @Test(description = "Verify the server throws appropriate error message, when user pass txnAmount  value as zero as part of the request.")
    public void API_AUTH_TC_029() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount","0");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid amount."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid terminalId as part of the request.")
    public void API_AUTH_TC_030() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId","9155031@");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for terminalId as part of the request.")
    public void API_AUTH_TC_031() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in terminalId as part of the request.")
    public void API_AUTH_TC_032() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId","915503");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in terminalId as part of the request.")
    public void API_AUTH_TC_033() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId","915503131111");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass terminalId  parameter as part of the request.")
    public void API_AUTH_TC_034() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("terminalId");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass terminalId  value as zero as part of the request.")
    public void API_AUTH_TC_035() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId","00000000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid timeZoneOffset as part of the request.")
    public void API_AUTH_TC_036() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneOffset","GMT@0530");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for timeZoneOffset as part of the request.")
    public void API_AUTH_TC_037() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneOffset","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass timeZoneOffset  parameter as part of the request.")
    public void API_AUTH_TC_038() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("timeZoneOffset");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid timeZoneRegion as part of the request.")
    public void API_AUTH_TC_039() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneRegion","Asia@Kolkata");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for timeZoneRegion as part of the request.")
    public void API_AUTH_TC_040() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneRegion","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass timeZoneRegion  parameter as part of the request.")
    public void API_AUTH_TC_041() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("timeZoneRegion");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid invoiceNumber as part of the request.")
    public void API_AUTH_TC_042() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber","78567@");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for invoiceNumber as part of the request.")
    public void API_AUTH_TC_043() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in invoiceNumber as part of the request.")
    public void API_AUTH_TC_044() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber","1");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in invoiceNumber as part of the request.")
    public void API_AUTH_TC_045() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber","7856765765756756335345");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass invoiceNumber  parameter as part of the request.")
    public void API_AUTH_TC_046() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("invoiceNumber");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass invoiceNumber  value as zero as part of the request.")
    public void API_AUTH_TC_047() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber","000000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass cardData block as part of the request.")
    public void API_AUTH_TC_048() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardType");
	requestData.remove("cardNumber");
	requestData.remove("expDate");
	requestData.remove("cvv");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  empty cardData  block as part of the request.")
    public void API_AUTH_TC_049() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","Blank");
	requestData.replace("cardNumber","Blank");
	requestData.replace("expDate","Blank");
	requestData.replace("cvv","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid cardType as part of the request.")
    public void API_AUTH_TC_050() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","V@");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for cardType as part of the request.")
    public void API_AUTH_TC_051() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in cardType as part of the request.")
    public void API_AUTH_TC_052() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","V");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass cardType  parameter as part of the request.")
    public void API_AUTH_TC_053() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardType");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid cardNumber as part of the request.")
    public void API_AUTH_TC_054() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber","411111111111111@");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for cardNumber as part of the request.")
    public void API_AUTH_TC_055() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass cardNumber parameter as part of the request.")
    public void API_AUTH_TC_056() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardNumber");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory field missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cardNumber value as zero as part of the request.")
    public void API_AUTH_TC_057() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber","0000000000000000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass VISA cardNumber value for Master card type  as part of the request.")
    public void API_AUTH_TC_058() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","MC");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for VISA card type  as part of the request.")
    public void API_AUTH_TC_059() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber","5500000000000004");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for Amex card type  as part of the request.")
    public void API_AUTH_TC_060() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","AX");
	requestData.replace("cardNumber","5500000000000004");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for Discover card type  as part of the request.")
    public void API_AUTH_TC_061() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","DISCOVER");
	requestData.replace("cardNumber","5500000000000004");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for JCB card type as part of the request.")
    public void API_AUTH_TC_062() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","JCB");
	requestData.replace("cardNumber","5500000000000004");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid expDate as part of the request.")
    public void API_AUTH_TC_063() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","210#");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for expDate as part of the request.")
    public void API_AUTH_TC_064() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in expDate as part of the request.")
    public void API_AUTH_TC_065() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","1");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in expDate as part of the request.")
    public void API_AUTH_TC_066() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","21045");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass expDate parameter as part of the request.")
    public void API_AUTH_TC_067() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("expDate");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass expDate value as zero as part of the request.")
    public void API_AUTH_TC_068() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","0000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters expDate MMYY as part of the request.")
    public void API_AUTH_TC_069() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","0421");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters expDate with past date")
    public void API_AUTH_TC_070() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","2005");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters expDate with current date")
    public void API_AUTH_TC_071() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","0820");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the entryMode value is passed MANUAL and CVV and Expiry date parameter is not passed as request parameter.")
    public void API_AUTH_TC_072() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("expDate");
	requestData.remove("cvv");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the entryMode value is passed MANUAL and CVV and Expiry date parameter  value is not passed as request parameter.")
    public void API_AUTH_TC_073() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","Blank");
	requestData.replace("cvv","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid cvv as part of the request.")
    public void API_AUTH_TC_074() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv","34@");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for cvv as part of the request.")
    public void API_AUTH_TC_075() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in cvv as part of the request.")
    public void API_AUTH_TC_076() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv","34");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass cvv parameter as part of the request.")
    public void API_AUTH_TC_078() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cvv");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as zero as part of the request.")
    public void API_AUTH_TC_079() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv","000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as 3 digits for Amex card type as part of the request.")
    public void API_AUTH_TC_080() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","AX");
	requestData.replace("cardNumber","374352963445140");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as more than 3 digits for VISA card type as part of the request.")
    public void API_AUTH_TC_081() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cvv","3454");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid merchantCode as part of the request.")
    public void API_AUTH_TC_082() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantCode","59143159155031@");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for merchantCode as part of the request.")
    public void API_AUTH_TC_083() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantCode","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in merchantCode as part of the request.")
    public void API_AUTH_TC_084() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantCode","91550313");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in merchantCode as part of the request.")
    public void API_AUTH_TC_085() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantCode","591431591550315646577");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass merchantCode parameter as part of the request.")
    public void API_AUTH_TC_086() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("merchantCode");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing merchantCode  as part of the request.")
    public void API_AUTH_TC_087() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantCode","591431591550323");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  merchantCode as zeros as part of the request.")
    public void API_AUTH_TC_088() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantCode","000000000000000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid MTI as part of the request.")
    public void API_AUTH_TC_089() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("mti","@100");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for MTI as part of the request.")
    public void API_AUTH_TC_090() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("mti","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in MTI as part of the request.")
    public void API_AUTH_TC_091() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("mti","0");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in MTI as part of the request.")
    public void API_AUTH_TC_092() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("mti","01000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass MTI parameter as part of the request.")
    public void API_AUTH_TC_093() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("mti");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing MTI  as part of the request.")
    public void API_AUTH_TC_094() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("mti","1234");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  MTI as zeros as part of the request.")
    public void API_AUTH_TC_095() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("mti","0000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid processingCode as part of the request.")
    public void API_AUTH_TC_096() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("processingCode","0@0000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        //Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for processingCode as part of the request.")
    public void API_AUTH_TC_097() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("processingCode","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        //Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in processingCode as part of the request.")
    public void API_AUTH_TC_098() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("processingCode","1");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        //Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in processingCode as part of the request.")
    public void API_AUTH_TC_099() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("processingCode","000000000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        //Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass processingCode parameter as part of the request.")
    public void API_AUTH_TC_100() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("processingCode");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        //Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing processingCode  as part of the request.")
    public void API_AUTH_TC_101() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("processingCode","123456");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        //Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass transactionType as SALE or VOID for posentrymode 011, MTI 0100, processingcode 000000 as part of the request.")
    public void API_AUTH_TC_102() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("transactionType","VOID");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
        //Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid MTI"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid currencyCode as part of the request.")
    public void API_AUTH_TC_103() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("currencyCode","84@");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for currencyCode as part of the request.")
    public void API_AUTH_TC_104() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("currencyCode","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in currencyCode as part of the request.")
    public void API_AUTH_TC_105() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("currencyCode","84");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in currencyCode as part of the request.")
    public void API_AUTH_TC_106() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("currencyCode","8411");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass currencyCode parameter as part of the request.")
    public void API_AUTH_TC_107() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("currencyCode");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields misssing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  currencyCode vaue as zeros as part of the request.")
    public void API_AUTH_TC_108() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("currencyCode","000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing currencyCode as part of the request.")
    public void API_AUTH_TC_109() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("currencyCode","845");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid sysTraceNum as part of the request.")
    public void API_AUTH_TC_110() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("sysTraceNum","00001@");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for sysTraceNum as part of the request.")
    public void API_AUTH_TC_111() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("sysTraceNum","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in sysTraceNum as part of the request.")
    public void API_AUTH_TC_112() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("sysTraceNum","00001");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in sysTraceNum as part of the request.")
    public void API_AUTH_TC_113() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("sysTraceNum","0000111");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass sysTraceNum parameter as part of the request.")
    public void API_AUTH_TC_114() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("sysTraceNum");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields misssing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  sysTraceNum value as zeros as part of the request.")
    public void API_AUTH_TC_115() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("sysTraceNum","000000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid merchantType as part of the request.")
    public void API_AUTH_TC_116() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantType","212@");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0178"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant type doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for merchantType as part of the request.")
    public void API_AUTH_TC_117() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantType","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0178"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant type doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in merchantType as part of the request.")
    public void API_AUTH_TC_118() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantType","212");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0178"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant type doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in merchantType as part of the request.")
    public void API_AUTH_TC_119() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantType","21211");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0178"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant type doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass merchantType parameter as part of the request.")
    public void API_AUTH_TC_120() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("merchantType");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass merchantType value as zeros as part of the request.")
    public void API_AUTH_TC_121() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantType","0000");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0178"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant type doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with invalid value")
    public void API_AUTH_TC_122() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("posConditionCode","0@");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with more than maximum length value as a part of request")
    public void API_AUTH_TC_123() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("posConditionCode","066");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with less than minimum length value as a part of request")
    public void API_AUTH_TC_124() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("posConditionCode","0");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter posConditionCode as a part of request")
    public void API_AUTH_TC_125() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("posConditionCode");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter posConditionCode as a part of request")
    public void API_AUTH_TC_126() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("posConditionCode","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass value as Zero's for parameter posConditionCode as a part of request")
    public void API_AUTH_TC_127() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("posConditionCode","00");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter transactionType as a part of request")
    public void API_AUTH_TC_128() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("transactionType");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter transactionType as a part of request")
    public void API_AUTH_TC_129() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("transactionType","Blank");
	JsonObject authJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(authJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
        Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
}
