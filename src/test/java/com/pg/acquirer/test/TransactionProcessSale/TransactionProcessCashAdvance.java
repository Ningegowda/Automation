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

public class TransactionProcessCashAdvance {
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
	masterRequestData.put("transactionType", "CASH");
	masterRequestData.put("nii", "000");
	masterRequestData.put("posConditionCode", "00");
	masterRequestData.put("currencyCode", "840");
	masterRequestData.put("mti", "0200");
	masterRequestData.put("merchantType", "6010");
	masterRequestData.put("processingCode", "010000");
	masterRequestData.put("posEntryMode", "011");
	masterRequestData.put("timeZoneOffset", "GMT+0530");
	masterRequestData.put("timeZoneRegion", "Asia/Kolkata");
	masterRequestData.put("cashBackAmount", "100");
    }

    @BeforeMethod
    public void init() {
	Reporter.log("<br>");
    }

    @AfterMethod
    public void reportUpdate(ITestResult result) {
	Reporter.log(result.getMethod().getDescription());
    }

    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing valid details.")
    public void TC_CASHADVANCE_TC_001() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for txnAmount field")
    public void TC_CASHADVANCE_TC_002() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "!@#$%^&*(");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for terminalId field")
    public void TC_CASHADVANCE_TC_003() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "!@#$%^&*(##");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for timeZoneOffset field")
    public void TC_CASHADVANCE_TC_004() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneOffset", "!@#$%^&");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for timeZoneRegion field")
    public void TC_CASHADVANCE_TC_005() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("timeZoneRegion", "!@#$%^&");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for invoiceNumber field")
    public void TC_CASHADVANCE_TC_006() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber","!@#$%^&*())(*&^%");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for cardType field")
    public void TC_CASHADVANCE_TC_008() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","!@#$%");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for cardNumber field")
    public void TC_CASHADVANCE_TC_009() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber","!@#$%^&*(*&^%$@#$%^");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for expDate field")
    public void TC_CASHADVANCE_TC_010() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","!@#^");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for cvv field")
    public void TC_CASHADVANCE_TC_011() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv","!@#");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for merchantCode field")
    public void TC_CASHADVANCE_TC_012() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode","!@#$%^&*");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for currencyCode field")
    public void TC_CASHADVANCE_TC_013() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode","!@#");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for mti field")
    public void TC_CASHADVANCE_TC_014() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti","!@#");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for processingCode field")
    public void TC_CASHADVANCE_TC_015() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode","!@#$%^");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for sysTraceNum field")
    public void TC_CASHADVANCE_TC_016() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum","!@#$%^");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0197"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Original transaction System trace Number  mismatch"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for posEntryMode field")
    public void TC_CASHADVANCE_TC_017() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode","!@#");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for transactionType field")
    public void TC_CASHADVANCE_TC_018() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("transactionType","!@#$%^&*DFGH");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for merchantType field")
    public void TC_CASHADVANCE_TC_019() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantType","!@#$%^");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Invalid details for Cash CashbackAmount field")
    public void TC_CASHADVANCE_TC_020() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cashBackAmount","!@#$%^");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the timeZoneOffset parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_021() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("timeZoneOffset");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the timeZoneRegion parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_022() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("timeZoneRegion");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the invoiceNumber parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_023() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("invoiceNumber");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the cardType parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_024() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardType");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the cardNumber parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_025() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardNumber");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the expDate parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_026() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("expDate");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the cvv parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_027() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cvv");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the merchantCode parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_028() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("merchantCode");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the currencyCode parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_029() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("currencyCode");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the mti parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_030() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("mti");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the processingCode parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_031() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("processingCode");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the posEntryMode parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_032() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("posEntryMode");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the transactionType parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_033() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("transactionType");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the merchantType parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_034() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("merchantType");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the Cash AdvanceAmount parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_035() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cashBackAmount");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the terminalId parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_036() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("terminalId");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the sysTraceNum parameter is not passed in Request parameter")
    public void TC_CASHADVANCE_TC_037() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("sysTraceNum");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0197"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Original transaction System trace Number  mismatch"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing AMEX card details in Request parameter")
    public void TC_CASHADVANCE_TC_038() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","AX");
	requestData.replace("cardNumber","374352963445140");
	requestData.replace("cvv","3456");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing VISA card details in Request parameter")
    public void TC_CASHADVANCE_TC_039() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashAdvanceJsonObj.toString());
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing MasterCard details in Request parameter")
    public void TC_CASHADVANCE_TC_040() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","MC");
	requestData.replace("cardNumber","5500000000000004");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Diner's Club card details in Request parameter")
    public void TC_CASHADVANCE_TC_041() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","DC");
	requestData.replace("cardNumber","30000000000004");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Discover card details in Request parameter")
    public void TC_CASHADVANCE_TC_042() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","DI");
	requestData.replace("cardNumber","6011000000000004");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing JCB  Card details in Request parameter")
    public void TC_CASHADVANCE_TC_043() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","JCB");
	requestData.replace("cardNumber","3088000000000009");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the txnAmount value is passed more than maximum length (>12) as request parameter.")
    public void TC_CASHADVANCE_TC_044() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount","1234567898765123456");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
    }
    @Test(description = "Verify the server throws appropriate error message, when the invoiceNumber value is passed more than maximum length (>20) as request parameter.")
    public void TC_CASHADVANCE_TC_045() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber","12345678909876543223456");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }
    @Test(description = "Verify the server throws appropriate error message, when the cardNumber value is passed more than maximum length (>32) as request parameter.")
    public void TC_CASHADVANCE_TC_046() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber","374352963234567890761234567897654323452345678445140");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the expDate value is passed more than maximum length (>4) as request parameter.")
    public void TC_CASHADVANCE_TC_047() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","37435");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when the cvv value is passed more than maximum length (>3) as request parameter.")
    public void TC_CASHADVANCE_TC_048() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv","3743");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the merchantCode value is passed more than maximum length (>16) as request parameter.")
    public void TC_CASHADVANCE_TC_049() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode","591431234567891591550313");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the currencyCode value is passed more than maximum length (>3) as request parameter.")
    public void TC_CASHADVANCE_TC_050() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode","5914");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    } 
    @Test(description = "Verify the server throws appropriate error message, when the mti value is passed more than maximum length (>4) as request parameter.")
    public void TC_CASHADVANCE_TC_051() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("mti","0456200");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the processingCode value is passed more than maximum length (>4) as request parameter.")
    public void TC_CASHADVANCE_TC_052() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("processingCode","09004556700");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the posEntryMode value is passed more than maximum length (>3) as request parameter.")
    public void TC_CASHADVANCE_TC_053() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode","0156771");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with invalid value")
    public void TC_CASHADVANCE_TC_054() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode","#$");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with more than maximum length value as a part of request")
    public void TC_CASHADVANCE_TC_055() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode","00864633");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_056() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode","3");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter posConditionCode as a part of request")
    public void TC_CASHADVANCE_TC_057() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("posConditionCode");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter posConditionCode as a part of request")
    public void TC_CASHADVANCE_TC_058() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode","Blank");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass txnAmount with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_059() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount","0");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter txnAmount as a part of request")
    public void TC_CASHADVANCE_TC_060() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("txnAmount");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter txnAmount as a part of request")
    public void TC_CASHADVANCE_TC_061() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount","Blank");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass sysTraceNum with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_062() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum","011");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter sysTraceNum as a part of request")
    public void TC_CASHADVANCE_TC_063() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum","Blank");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass sysTraceNum with more than maximum length value as a part of request")
    public void TC_CASHADVANCE_TC_064() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum","45738478328453784178463745724");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass invoiceNumber with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_065() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber","765");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter invoiceNumber as a part of request")
    public void TC_CASHADVANCE_TC_066() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber","Blank");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter cardData as a part of request")
    public void TC_CASHADVANCE_TC_067() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardType");
	requestData.remove("cardNumber");
	requestData.remove("expDate");
	requestData.remove("cvv");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cardType with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_068() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","V");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter cardType as a part of request")
    public void TC_CASHADVANCE_TC_069() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardType");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cardType with more than maximum length value as a part of request")
    public void TC_CASHADVANCE_TC_070() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardType","AXAXAXAXAXAAXAXAGJSDGJSDGFJSGFHJXAAXAX");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0010"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card type."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter cardNumber as a part of request")
    public void TC_CASHADVANCE_TC_071() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardNumber");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cardNumber with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_072() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber","374");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass expDate with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_073() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","2");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter expDate as a part of request")
    public void TC_CASHADVANCE_TC_074() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("expDate");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters the expDate which is not associated to the given card number")
    public void TC_CASHADVANCE_TC_075() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate","1906");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cvv with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_076() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv","3");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter cvv as a part of request")
    public void TC_CASHADVANCE_TC_077() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cvv");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters the cvv which is not associated to the given card number")
    public void TC_CASHADVANCE_TC_078() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv","456");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass terminalId with more than maximum length value as a part of request")
    public void TC_CASHADVANCE_TC_079() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId","915507257257257345235313");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter terminalId as a part of request")
    public void TC_CASHADVANCE_TC_080() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("terminalId");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass terminalId with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_081() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId","7564");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter merchantCode as a part of request")
    public void TC_CASHADVANCE_TC_082() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("merchantCode");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass merchantCode with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_083() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode","5678");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass transactionType with more than maximum length value as a part of request")
    public void TC_CASHADVANCE_TC_084() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("transactionType","CASHBACKCASHBACKCASHBACKCASHBACKCASHBACKCASHBACK");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter transactionType as a part of request")
    public void TC_CASHADVANCE_TC_085() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("transactionType");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass transactionType with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_086() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("transactionType","CA");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter currencyCode as a part of request")
    public void TC_CASHADVANCE_TC_087() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("currencyCode");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass currencyCode with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_088() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("currencyCode","8");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters the currencyCode which is not existed")
    public void TC_CASHADVANCE_TC_089() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("currencyCode","111");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter mti as a part of request")
    public void TC_CASHADVANCE_TC_090() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("mti");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass mti with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_091() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("mti","02");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters the mti which is not associated with Cash advance transaction")
    public void TC_CASHADVANCE_TC_092() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("mti","0100");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter processingCode as a part of request")
    public void TC_CASHADVANCE_TC_093() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("processingCode");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass processingCode with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_094() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("processingCode","0100");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters the processingCode which is not associated with Cash advance transaction")
    public void TC_CASHADVANCE_TC_095() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("processingCode","000000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters the processingCode which is not associated with Cash advance transaction")
    public void TC_CASHADVANCE_TC_096() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("posEntryMode","1");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter posEntryMode as a part of request")
    public void TC_CASHADVANCE_TC_097() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("posEntryMode");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass merchantType with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_098() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantType","6");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass merchantType with more than maximum length value as a part of request")
    public void TC_CASHADVANCE_TC_099() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantType","30046275472720");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter merchantType as a part of request")
    public void TC_CASHADVANCE_TC_100() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("merchantType");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cashBackAmount with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_101() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cashBackAmount","0");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cashBackAmount with more than sale amount")
    public void TC_CASHADVANCE_TC_102() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cashBackAmount","10000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter cashBackAmount as a part of request")
    public void TC_CASHADVANCE_TC_103() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("cashBackAmount");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cashBackAmount with more than maximum length value as a part of request")
    public void TC_CASHADVANCE_TC_104() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cashBackAmount","1981683168423523525868325");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Visa Card type with Card Number more than maximum length ")
    public void TC_CASHADVANCE_TC_105() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardNumber","411111111111111137475748387474838");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Visa Card type with Card Number less than minimum length ")
    public void TC_CASHADVANCE_TC_106() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardNumber","4111111111");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing MasterCard Card type with Card Number more than maximum length ")
    public void TC_CASHADVANCE_TC_107() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","MC");
	requestData.replace("cardNumber","55000000000000045500000000000004");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing MasterCard Card type with Card Number less than minimum length ")
    public void TC_CASHADVANCE_TC_108() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","MC");
	requestData.replace("cardNumber","5500000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing American Express Card type with Card Number more than maximum length ")
    public void TC_CASHADVANCE_TC_109() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","AX");
	requestData.replace("cardNumber","374352963445140374352963445140374352963445140");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing American Express Card type with Card Number less than minimum length ")
    public void TC_CASHADVANCE_TC_110() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","AX");
	requestData.replace("cardNumber","3743529634");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Diner's Club Card type with Card Number more than maximum length ")
    public void TC_CASHADVANCE_TC_111() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","DINERS");
	requestData.replace("cardNumber","3000000000000430000000000004");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Diner's Club Card type with Card Number less than minimum length")
    public void TC_CASHADVANCE_TC_112() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","DINERS");
	requestData.replace("cardNumber","300000000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Discover Card type with Card Number more than maximum length ")
    public void TC_CASHADVANCE_TC_113() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","DC");
	requestData.replace("cardNumber","60110000000000046011000000000004");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing Discover Card type with Card Number less than minimum length")
    public void TC_CASHADVANCE_TC_114() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","DC");
	requestData.replace("cardNumber","60110000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing JCB Card type with Card Number more than maximum length")
    public void TC_CASHADVANCE_TC_115() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","JCB");
	requestData.replace("cardNumber","30880000000000093088000000000009");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify whether user is able to perform Cash Advance Transaction by passing JCB Card type with Card Number less than minimum length")
    public void TC_CASHADVANCE_TC_116() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","JCB");
	requestData.replace("cardNumber","308800000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enter posEntryMode with all zero's   as part of the request.")
    public void TC_CASHADVANCE_TC_117() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("posEntryMode","000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass txnAmount  value as zero as part of the request.")
    public void TC_CASHADVANCE_TC_118() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("txnAmount","000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass terminalId  value as zero as part of the request.")
    public void TC_CASHADVANCE_TC_119() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("terminalId","00000000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass invoiceNumber  value as zero as part of the request.")
    public void TC_CASHADVANCE_TC_120() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("invoiceNumber","00000000000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cardNumber value as zero as part of the request.")
    public void TC_CASHADVANCE_TC_121() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardNumber","0000000000000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass VISA cardNumber value for Master card type  as part of the request.")
    public void TC_CASHADVANCE_TC_122() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","MC");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for Amex card type  as part of the request.")
    public void TC_CASHADVANCE_TC_123() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","AX");
	requestData.replace("cardNumber","5500000000000004");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for Discover card type  as part of the request.")
    public void TC_CASHADVANCE_TC_124() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","DISCOVER");
	requestData.replace("cardNumber","5500000000000004");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass Master cardNumber value for JCB card type as part of the request.")
    public void TC_CASHADVANCE_TC_125() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","JCB");
	requestData.replace("cardNumber","5500000000000004");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Card Number"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass expDate value as zero as part of the request.")
    public void TC_CASHADVANCE_TC_126() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("expDate","0000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters expDate MMYY as part of the request.")
    public void TC_CASHADVANCE_TC_127() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("expDate","0621");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters expDate with past date")
    public void TC_CASHADVANCE_TC_128() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("expDate","1906");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters expDate with current date")
    public void TC_CASHADVANCE_TC_129() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("expDate","2008");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as zero as part of the request.")
    public void TC_CASHADVANCE_TC_130() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cvv","000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as 3 digits for Amex card type as part of the request.")
    public void TC_CASHADVANCE_TC_131() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cardType","AX");
	requestData.replace("cardNumber","374352963445140");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cvv value as more than 3 digits for VISA card type as part of the request.")
    public void TC_CASHADVANCE_TC_132() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("cvv","3456");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing merchantCode  as part of the request.")
    public void TC_CASHADVANCE_TC_133() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantCode","9876543219087654");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  merchantCode as zeros as part of the request.")
    public void TC_CASHADVANCE_TC_134() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantCode","0000000000000000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing MTI  as part of the request.")
    public void TC_CASHADVANCE_TC_135() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("mti","0100");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  MTI as zeros as part of the request.")
    public void TC_CASHADVANCE_TC_136() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("mti","0000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing processingCode  as part of the request.")
    public void TC_CASHADVANCE_TC_137() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("processingCode","010000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass transactionType as SALE or VOID for posentrymode 011, MTI 0100, processingcode 000000 as part of the request.")
    public void TC_CASHADVANCE_TC_138() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("transactionType","SALE");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  currencyCode vaue as zeros as part of the request.")
    public void TC_CASHADVANCE_TC_139() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("currencyCode","000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  non-existing currencyCode as part of the request.")
    public void TC_CASHADVANCE_TC_140() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("currencyCode","365");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass  sysTraceNum value as zeros as part of the request.")
    public void TC_CASHADVANCE_TC_141() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("sysTraceNum","000000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass merchantType value as zeros as part of the request.")
    public void TC_CASHADVANCE_TC_142() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("merchantType","0000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
	//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass value as Zero's for parameter posConditionCode as a part of request")
    public void TC_CASHADVANCE_TC_143() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("posConditionCode","000");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters invalid NII as part of the request.")
    public void TC_CASHADVANCE_TC_144() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("nii","$%$");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass NII with more than maximum length value as a part of request")
    public void TC_CASHADVANCE_TC_145() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("nii","000687685875");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass NII with less than minimum length value as a part of request")
    public void TC_CASHADVANCE_TC_146() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("nii","0");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter NII as a part of request")
    public void TC_CASHADVANCE_TC_147() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.remove("nii");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter NII as a part of request")
    public void TC_CASHADVANCE_TC_148() {
	Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
	requestData.replace("nii","Blank");
	JsonObject cashAdvanceJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashAdvanceJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
}
