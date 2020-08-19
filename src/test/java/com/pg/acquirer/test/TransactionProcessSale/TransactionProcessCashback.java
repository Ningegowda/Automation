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

public class TransactionProcessCashback {
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
	masterRequestData.put("transactionType", "CASHBACK");
	masterRequestData.put("nii", "000");
	masterRequestData.put("merchantType", "3000");
	masterRequestData.put("posConditionCode", "00");
	masterRequestData.put("currencyCode", "840");
	masterRequestData.put("mti", "0200");
	masterRequestData.put("processingCode", "090000");
	masterRequestData.put("posEntryMode", "011");
	masterRequestData.put("timeZoneOffset", "GMT+0530");
	masterRequestData.put("timeZoneRegion", "Asia/Kolkata");
	masterRequestData.put("cashBackAmount", "10");
    }

    @BeforeMethod
    public void init() {
	Reporter.log("<br>");
    }

    @AfterMethod
    public void reportUpdate(ITestResult result) {
	Reporter.log(result.getMethod().getDescription());
    }

    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing valid details.")
    public void TC_CASHBACK_TC_001() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }

    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Invalid details for terminalId field")
    public void TC_CASHBACK_TC_003() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "9155031@");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Invalid details for invoiceNumber field")
    public void TC_CASHBACK_TC_006() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "785676576575675$%^$%6");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Invalid details for cardNumber field")
    public void TC_CASHBACK_TC_009() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cardNumber", "!@#$%^&*(*&^%$@#$%^");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V08"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("ApInvalid card numberproved"));
    }
    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Invalid details for expDate field")
    public void TC_CASHBACK_TC_010() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "210@");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }


    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Invalid details for cvv field")
    public void TC_CASHBACK_TC_011() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "@#@");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Invalid details for merchantCode field")
    public void TC_CASHBACK_TC_012() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "59143159155031$");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Invalid details for currencyCode field")
    public void TC_CASHBACK_TC_013() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "84#");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Invalid details for sysTraceNum field")
    public void TC_CASHBACK_TC_016() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "00001#");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }

    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Invalid details for posEntryMode field")
    public void TC_CASHBACK_TC_017() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posEntryMode", "#$#");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos entry mode"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the invoiceNumber parameter is not passed in Request parameter")
    public void TC_CASHBACK_TC_023() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("invoiceNumber");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the expDate parameter is not passed in Request parameter")
    public void TC_CASHBACK_TC_027() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cardType");
	requestData.remove("cardNumber");
	requestData.remove("expDate");
	requestData.remove("cvv");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when the posEntryMode parameter is not passed in Request parameter")
    public void TC_CASHBACK_TC_033() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("posEntryMode");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the sysTraceNum parameter is not passed in Request parameter")
    public void TC_CASHBACK_TC_038() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("sysTraceNum");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing AMEX card details in Request parameter")
    public void TC_CASHBACK_TC_039() {
        Map<String, String> requestData = new HashMap<String, String>();
    	requestData.putAll(masterRequestData);
    	requestData.replace("cardType", "AX");
    	requestData.replace("cardNumber","374352963445140");
    	requestData.replace("cvv","3457");
    	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
    	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
    	System.out.println(response.asString());
    	Reporter.log("Response : " + response.asString());
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing VISA card details in Request parameter")
    public void TC_CASHBACK_TC_040() {
    	Map<String, String> requestData = new HashMap<String, String>();
    	requestData.putAll(masterRequestData);
    	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
    	System.out.println(cashbackJsonObj.toString());
    	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
    	System.out.println(response.asString());
    	Reporter.log("Response : " + response.asString());
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing MasterCard details in Request parameter")
    public void TC_CASHBACK_TC_041() {
        Map<String, String> requestData = new HashMap<String, String>();
    	requestData.putAll(masterRequestData);
    	requestData.replace("cardType", "MC");
    	requestData.replace("cardNumber", "5105105105105100");
    	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
    	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
    	System.out.println(response.asString());
    	Reporter.log("Response : " + response.asString());
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Diner's Club card details in Request parameter")
    public void TC_CASHBACK_TC_042() {
        Map<String, String> requestData = new HashMap<String, String>();
    	requestData.putAll(masterRequestData);
    	requestData.replace("cardType", "DC");
    	requestData.replace("cardNumber","30000000000004");
    	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
    	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
    	System.out.println(response.asString());
    	Reporter.log("Response : " + response.asString());
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing Discover card details in Request parameter")
    public void TC_CASHBACK_TC_043() {
        Map<String, String> requestData = new HashMap<String, String>();
    	requestData.putAll(masterRequestData);
    	requestData.replace("cardType", "DC");
    	requestData.replace("cardNumber","6011000000000004");
    	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
    	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
    	System.out.println(response.asString());
    	Reporter.log("Response : " + response.asString());
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify whether user is able to perform Cashback Transaction by passing JCB  Card details in Request parameter")
    public void TC_CASHBACK_TC_044() {
        Map<String, String> requestData = new HashMap<String, String>();
    	requestData.putAll(masterRequestData);
    	requestData.replace("cardType", "JCB");
    	requestData.replace("cardNumber","3088000000000009");
    	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
    	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
    	System.out.println(response.asString());
    	Reporter.log("Response : " + response.asString());
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
    	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the txnAmount value is passed more than maximum length (>12) as request parameter.")
    public void TC_CASHBACK_TC_045() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "143534534453455");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
    }
    @Test(description = "Verify the server throws appropriate error message, when the invoiceNumber value is passed more than maximum length (>20) as request parameter.")
    public void TC_CASHBACK_TC_046() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "785676576575675546456546");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }
    @Test(description = "Verify the server throws appropriate error message, when the expDate value is passed more than maximum length (>4) as request parameter.")
    public void TC_CASHBACK_TC_048() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "21099");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }
    
    @Test(description = "Verify the server throws appropriate error message, when the expDate value is passed more than maximum length (>4) as request parameter.")
    public void TC_CASHBACK_TC_049() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "2109");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the merchantCode value is passed more than maximum length as request parameter.")
    public void TC_CASHBACK_TC_050() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("merchantCode", "591431591550313324234234");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when the currencyCode value is passed more than maximum length (>3) as request parameter.")
    public void TC_CASHBACK_TC_051() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode", "8401");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with invalid value")
    public void TC_CASHBACK_TC_055() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "#$");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos pondition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with more than maximum length value as a part of request")
    public void TC_CASHBACK_TC_056() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "011");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos pondition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with less than minimum length value as a part of request")
    public void TC_CASHBACK_TC_057() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode", "0");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid pos pondition code"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter posConditionCode as a part of request")
    public void TC_CASHBACK_TC_058() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("posConditionCode");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter posConditionCode as a part of request")
    public void TC_CASHBACK_TC_059() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("posConditionCode","Blank");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass txnAmount with less than minimum length value as a part of request")
    public void TC_CASHBACK_TC_060() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("txnAmount", "1");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass sysTraceNum with less than minimum length value as a part of request")
    public void TC_CASHBACK_TC_063() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum", "1");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter sysTraceNum as a part of request")
    public void TC_CASHBACK_TC_064() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("sysTraceNum");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass sysTraceNum with more than maximum length value as a part of request")
    public void TC_CASHBACK_TC_065() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("sysTraceNum","45738478328453784178463745724");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass invoiceNumber with less than minimum length value as a part of request")
    public void TC_CASHBACK_TC_066() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("invoiceNumber", "1");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid invoiceNumber."));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter invoiceNumber as a part of request")
    public void TC_CASHBACK_TC_067() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("invoiceNumber");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not enter value for expDate as part of the request.")
    public void TC_CASHBACK_TC_068() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "Blank");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user pass expDate with less than minimum length value as a part of request")
    public void TC_CASHBACK_TC_074() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("expDate", "21");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(
		HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid card expiry."));
    }

    

    @Test(description = "Verify the server throws appropriate error message, when user does not pass expDate parameter as part of the request.")
    public void TC_CASHBACK_TC_075() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("expDate");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user enters the expDate which is not associated to the given card number")
    public void TC_CASHBACK_TC_076() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("expDate","675");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0009"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid card expiry."));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass cvv with less than minimum length value as a part of request")
    public void TC_CASHBACK_TC_077() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("cvv", "1");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0147"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Decline CVV2/CID Fail"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter cvv as a part of request")
    public void TC_CASHBACK_TC_078() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("cvv");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }


    @Test(description = "Verify the server throws appropriate error message, when user pass terminalId with more than maximum length value as a part of request")
    public void TC_CASHBACK_TC_080() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "915507257257257345235313");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid terminalId"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass terminalId with less than minimum length value as a part of request")
    public void TC_CASHBACK_TC_082() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("terminalId", "7564");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	System.out.println(cashbackJsonObj.toString());
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Invalid terminalId"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user does not pass parameter merchantCode as a part of request\r\n" + 
    	"")
    public void TC_CASHBACK_TC_083() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.remove("merchantCode");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Mandatory fields missing"));
    }
    @Test(description = "Verify the server throws appropriate error message, when user pass merchantCode with less than minimum length value as a part of request")
	    public void TC_CASHBACK_TC_084() {
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.putAll(masterRequestData);
		requestData.replace("merchantCode","5678");
		JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
		Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
		System.out.println(response.asString());
		Reporter.log("Response : " + response.asString());
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
		Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
			.equalsIgnoreCase("Invalid merchantId"));
	    }
    
    @Test(description = "Verify the server throws appropriate error message, when user pass currencyCode with less than minimum length value as a part of request")
    public void TC_CASHBACK_TC_089() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode","8");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Merchant currency doesn't match"));
    }

    @Test(description = "Verify the server throws appropriate error message, when user enters the currencyCode which is not existed")
    public void TC_CASHBACK_TC_090() {
	Map<String, String> requestData = new HashMap<String, String>();
	requestData.putAll(masterRequestData);
	requestData.replace("currencyCode","765");
	JsonObject cashbackJsonObj = RequestBuilders.buildRequest(requestData);
	Response response = HitService.getJSONResponse(cashbackJsonObj.toString(), endURL);
	System.out.println(response.asString());
	Reporter.log("Response : " + response.asString());
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
	Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE)
		.equalsIgnoreCase("Merchant currency doesn't match"));
    }
}