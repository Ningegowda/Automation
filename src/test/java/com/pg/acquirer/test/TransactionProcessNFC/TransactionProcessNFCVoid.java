package com.pg.acquirer.test.TransactionProcessNFC;

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
import com.pg.acquirer.requestbuilder.RequestBuilders;
import com.pg.acquirer.util.Constants;
import com.pg.acquirer.util.HitService;

import io.restassured.response.Response;

public class TransactionProcessNFCVoid {

	 Map<String, String> masterRequestData = new HashMap<String, String>();
	    String endURL = "txnOnline/process";
	    String txnRefNumber;
	    Response response;
	    TransactionProcessNFCSale Nfcsale = new TransactionProcessNFCSale();

	    @BeforeClass
		public void dataInit() {
			masterRequestData.put(Constants.TXNAMOUNT, "12");
			masterRequestData.put(Constants.INVOICENUM, "7856765765756756");
			masterRequestData.put(Constants.SYSTRACENUM,
					AcquirerServicesTestProperties.getPropertyValue(Constants.SYSTRACENUM));
			masterRequestData.put(Constants.TRACK2, AcquirerServicesTestProperties.getPropertyValue(Constants.TRACK2));
			masterRequestData.put(Constants.EMV, AcquirerServicesTestProperties.getPropertyValue(Constants.EMV));
			masterRequestData.put(Constants.TID, "91550313");
			masterRequestData.put(Constants.MID, "591431591550313");
			masterRequestData.put(Constants.NII, "000");
			masterRequestData.put(Constants.POSCONDITIONCODE, "00");
			masterRequestData.put(Constants.CURRENCY_CODE, "840");
			masterRequestData.put(Constants.MTI, "0200");
			masterRequestData.put(Constants.PROCESSINGCODE, "020000");
			masterRequestData.put(Constants.POS_ENTRYMODE, "071");
		}

		@BeforeMethod
		public void init() {
			response= Nfcsale.generateProcessSaleNFCTransactionResponse();
			txnRefNumber=HitService.getResponseValue(response, Constants.TXNREFNUM);
			Reporter.log("<br>");
		}

		@AfterMethod
		public void reportUpdate(ITestResult result) {
			Reporter.log(result.getMethod().getDescription());
		}

		@Test(description = "Verify whether user is able to perform NFC VOID ONLINE Transaction by passing valid VISA card details.")
		public void API_VOIDONLINENFC_TC_001() {
			Map<String, String> requestData = new HashMap<String, String>();
			requestData.putAll(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing valid MasterCard card details.")
		public void API_VOIDONLINENFC_TC_002() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "5500000000000004=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing valid American Express card details.")
		public void API_VOIDONLINENFC_TC_003() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "340000000000009=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing valid Diner's Club card details.")
		public void API_VOIDONLINENFC_TC_004() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "30000000000004=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing valid Discover card details.")
		public void API_VOIDONLINENFC_TC_005() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "6011000000000004=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing valid JCB card details.")
		public void API_VOIDONLINENFC_TC_006() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.replace(Constants.TRACK2, "3088000000000009=210656789");
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("00"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Approved"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing Track2 data with Card Number more than maximum length ")
		public void API_VOIDONLINENFC_TC_007() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.replace(Constants.TRACK2, "41111111111111114111111111111111=210656789");
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing Visa Card Track2 data with Card Number less than minimum length ")
		public void API_VOIDONLINENFC_TC_008() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.replace(Constants.TRACK2, "41111111=210656789");
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing MasterCard Track2 data with Card Number more than maximum length ")
		public void API_VOIDONLINENFC_TC_009() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "55000000000000045500000000000004=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing MasterCard Track2 data with Card Number less than minimum length")
		public void API_VOIDONLINENFC_TC_010() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "5500004=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing American Express Track2 data with Card Number more than maximum length ")
		public void API_VOIDONLINENFC_TC_011() {
			Map<String, String> requestData = new HashMap<String, String>();
			requestData.putAll(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "340000000000009340000000000009=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing American Express Track2 data with Card Number less than minimum length ")
		public void API_VOIDONLINENFC_TC_012() {
			Map<String, String> requestData = new HashMap<String, String>();
			requestData.putAll(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "3409=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}

		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing Diner's Club Track2 data with Card Number more than maximum length ")
		public void API_VOIDONLINENFC_TC_013() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "3000000000000430000000000004=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}
		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing Diner's Club Track2 data with Card Number less than minimum length")
		public void API_VOIDONLINENFC_TC_014() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "3000004=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}
		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing Discover Track2 data with Card Number more than maximum length ")
		public void API_VOIDONLINENFC_TC_015() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "60110000000000046011000000000004=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}
		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing Discover Track2 data with Card Number less than minimum length ")
		public void API_VOIDONLINENFC_TC_016() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "6=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}
		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing JCB Track2 data type with Card Number more than maximum length ")
		public void API_VOIDONLINENFC_TC_017() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "30880000000000093088000000000009=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}
		@Test(description = "Verify whether user is able to perform  NFC VOID ONLINE Transaction by passing JCB Track2 data with Card Number less than minimum length ")
		public void API_VOIDONLINENFC_TC_018() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2, "30889=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("V17"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track2 data"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid posEntryMode as part of the request.")
		public void API_VOIDONLINENFC_TC_019() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.POS_ENTRYMODE, "$$$");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for posEntryMode as part of the request.")
		public void API_VOIDONLINENFC_TC_020() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.POS_ENTRYMODE, "Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in posEntryMode as part of the request.")
		public void API_VOIDONLINENFC_TC_021() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.POS_ENTRYMODE, "0");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in posEntryMode as part of the request.")
		public void API_VOIDONLINENFC_TC_022() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.POS_ENTRYMODE, "0111");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters all Zero's as part of the request.")
		public void API_VOIDONLINENFC_TC_023() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.POS_ENTRYMODE, "000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass posEntryMode  parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_024() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.POS_ENTRYMODE);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enter posEntryMode with all zero's   as part of the request.")
		public void API_VOIDONLINENFC_TC_025() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.POS_ENTRYMODE,"000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0140"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos entry mode"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for txnAmount as part of the request.")
		public void API_VOIDONLINENFC_TC_026() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TXNAMOUNT,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server shows success message, when user enters less than minimum length data in txnAmount as part of the request.")
		public void API_VOIDONLINENFC_TC_027() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TXNAMOUNT,"0");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in txnAmount as part of the request.")
		public void API_VOIDONLINENFC_TC_028() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TXNAMOUNT,"1435345344534");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass txnAmount  parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_029() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.TXNAMOUNT);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass txnAmount  value as zero as part of the request.")
		public void API_VOIDONLINENFC_TC_030() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TXNAMOUNT,"0000000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0003"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid amount."));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid terminalId as part of the request.")
		public void API_VOIDONLINENFC_TC_031() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TID,"9155031@");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for terminalId as part of the request.")
		public void API_VOIDONLINENFC_TC_032() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TID,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in terminalId as part of the request.")
		public void API_VOIDONLINENFC_TC_033() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TID,"1111");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in terminalId as part of the request.")
		public void API_VOIDONLINENFC_TC_034() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TID,"9155031122");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass terminalId  parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_035() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.TID);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass terminalId  value as zero as part of the request.")
		public void API_VOIDONLINENFC_TC_036() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TID,"00000000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0130"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid terminalId"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid invoiceNumber as part of the request.")
		public void API_VOIDONLINENFC_TC_043() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.INVOICENUM,"785676576575675$%^$%6");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for invoiceNumber as part of the request.")
		public void API_VOIDONLINENFC_TC_044() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.INVOICENUM,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in invoiceNumber as part of the request.")
		public void API_VOIDONLINENFC_TC_045() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.INVOICENUM,"1");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in invoiceNumber as part of the request.")
		public void API_VOIDONLINENFC_TC_046() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.INVOICENUM,"785676576575675546456546");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass invoiceNumber  parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_047() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.INVOICENUM);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass invoiceNumber  value as zero as part of the request.")
		public void API_VOIDONLINENFC_TC_048() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.INVOICENUM,"000000000000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0131"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid invoiceNumber."));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass cardData block as part of the request.")
		public void API_VOIDONLINENFC_TC_049() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.TRACK2);
			requestData.remove(Constants.EMV);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass  empty cardData  block as part of the request.")
		public void API_VOIDONLINENFC_TC_050() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2,"Blank");
			requestData.replace(Constants.EMV,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid track2 data as part of the request.")
		public void API_VOIDONLINENFC_TC_051() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2,"!@#$%^&*()_=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("96"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track 2 data"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for track2 data as part of the request.")
		public void API_VOIDONLINENFC_TC_052() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in track2 data as part of the request.")
		public void API_VOIDONLINENFC_TC_053() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2,"1=2");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("96"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track 2 data"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in track2 data as part of the request.")
		public void API_VOIDONLINENFC_TC_054() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.TRACK2,"374352963445140=210656789374352963445140=210656789");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("96"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid track 2 data"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass track2 data  parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_055() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.TRACK2);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(
					HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid emv as part of the request.")
		public void API_VOIDONLINENFC_TC_056() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.EMV,"@#$%^&*)(*&@#$%^&*())(*&$#$%^&*()(*&^%$$%^&*(");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for emv as part of the request.")
		public void API_VOIDONLINENFC_TC_057() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.EMV,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in emv as part of the request.")
		public void API_VOIDONLINENFC_TC_058() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.EMV,"17AF4");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in emv as part of the request.")
		public void API_VOIDONLINENFC_TC_059() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.EMV,"17AF4AC59D3B14AC16B0DC4C899912C7A1A81DA764D777DEBF38A4871CC80463A1122FCA4EB2532A46JKJFK63846FGG8734GFT46G37HET36GE64TEGE63TGE637WGT3GEFG363G");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass emv parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_060() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.EMV);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass emv value as zero as part of the request.")
		public void API_VOIDONLINENFC_TC_061() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.EMV,"0000000000000000000000000000000000000000000000000000000000000000000000000000000000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid merchantCode as part of the request.")
		public void API_VOIDONLINENFC_TC_062() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MID,"59143159155031$");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for merchantCode as part of the request.")
		public void API_VOIDONLINENFC_TC_063() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MID,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in merchantCode as part of the request.")
		public void API_VOIDONLINENFC_TC_064() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MID,"5914");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in merchantCode as part of the request.")
		public void API_VOIDONLINENFC_TC_065() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MID,"591431591550313324234234");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass merchantCode parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_066() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.MID);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass  non-existing merchantCode  as part of the request.")
		public void API_VOIDONLINENFC_TC_067() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MID,"591431591550312");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass  merchantCode as zeros as part of the request.")
		public void API_VOIDONLINENFC_TC_068() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MID,"000000000000000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid MTI as part of the request.")
		public void API_VOIDONLINENFC_TC_069() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MTI,"020@");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0129"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid merchantId"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for MTI as part of the request.")
		public void API_VOIDONLINENFC_TC_070() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MTI,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in MTI as part of the request.")
		public void API_VOIDONLINENFC_TC_071() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MTI,"020");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in MTI as part of the request.")
		public void API_VOIDONLINENFC_TC_072() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MTI,"0200000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass MTI parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_073() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.MTI);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass  non-existing MTI  as part of the request.")
		public void API_VOIDONLINENFC_TC_074() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MTI,"1200");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass  MTI as zeros as part of the request.")
		public void API_VOIDONLINENFC_TC_075() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.MTI,"0000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid processingCode as part of the request.")
		public void API_VOIDONLINENFC_TC_076() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.PROCESSINGCODE,"0000*0");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for processingCode as part of the request.")
		public void API_VOIDONLINENFC_TC_077() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.PROCESSINGCODE,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in processingCode as part of the request.")
		public void API_VOIDONLINENFC_TC_078() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.PROCESSINGCODE,"00");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in processingCode as part of the request.")
		public void API_VOIDONLINENFC_TC_079() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.PROCESSINGCODE,"000000000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass processingCode parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_080() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.PROCESSINGCODE);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass  non-existing processingCode  as part of the request.")
		public void API_VOIDONLINENFC_TC_081() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.PROCESSINGCODE,"123456");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			//Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid currencyCode as part of the request.")
		public void API_VOIDONLINENFC_TC_083() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.CURRENCY_CODE,"84#");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for currencyCode as part of the request.")
		public void API_VOIDONLINENFC_TC_084() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.CURRENCY_CODE,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in currencyCode as part of the request.")
		public void API_VOIDONLINENFC_TC_085() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.CURRENCY_CODE,"84");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in currencyCode as part of the request.")
		public void API_VOIDONLINENFC_TC_086() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.CURRENCY_CODE,"8401");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass currencyCode parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_087() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.CURRENCY_CODE);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields misssing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass  currencyCode vaue as zeros as part of the request.")
		public void API_VOIDONLINENFC_TC_088() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.CURRENCY_CODE,"000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass  non-existing currencyCode as part of the request.")
		public void API_VOIDONLINENFC_TC_089() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.CURRENCY_CODE,"123");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0160"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Merchant currency doesn't match"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid sysTraceNum as part of the request.")
		public void API_VOIDONLINENFC_TC_090() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.SYSTRACENUM,"00001#");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not enter value for sysTraceNum as part of the request.")
		public void API_VOIDONLINENFC_TC_091() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.SYSTRACENUM,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters less than minimum length data in sysTraceNum as part of the request.")
		public void API_VOIDONLINENFC_TC_092() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.SYSTRACENUM,"1");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString() + "Expected Result :" + "Invalid stan");
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters more than maximum length data in sysTraceNum as part of the request.")
		public void API_VOIDONLINENFC_TC_093() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.SYSTRACENUM,"1");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass sysTraceNum parameter as part of the request.")
		public void API_VOIDONLINENFC_TC_094() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.SYSTRACENUM);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass  sysTraceNum vaue as zeros as part of the request.")
		public void API_VOIDONLINENFC_TC_095() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.SYSTRACENUM,"000000");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0141"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid stan"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with invalid value")
		public void API_VOIDONLINENFC_TC_096() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.POSCONDITIONCODE,"#$");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with more than maximum length value as a part of request")
		public void API_VOIDONLINENFC_TC_097() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.POSCONDITIONCODE,"00864633");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass posConditionCode with less than minimum length value as a part of request")
		public void API_VOIDONLINENFC_TC_098() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.POSCONDITIONCODE,"3");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0206"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid pos condition code"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass parameter posConditionCode as a part of request")
		public void API_VOIDONLINENFC_TC_099() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.POSCONDITIONCODE);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter posConditionCode as a part of request")
		public void API_VOIDONLINENFC_TC_100() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.POSCONDITIONCODE,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid NII as part of the request.")
		public void API_VOIDONLINENFC_TC_101() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.NII,"#$%");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass NII with more than maximum length value as a part of request")
		public void API_VOIDONLINENFC_TC_102() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.NII,"000008665567");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass NII with less than minimum length value as a part of request")
		public void API_VOIDONLINENFC_TC_103() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.NII,"0");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0205"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid nii"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass parameter NII as a part of request")
		public void API_VOIDONLINENFC_TC_104() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.remove(Constants.NII);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter NII as a part of request")
		public void API_VOIDONLINENFC_TC_105() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, txnRefNumber);
			requestData.replace(Constants.NII,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user enters invalid txnRefNumber as part of the request.")
		public void API_VOIDONLINENFC_TC_111() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, "!@#$%^");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Transaction reference number."));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass txnRefNumber with more than maximum length value as a part of request")
		public void API_VOIDONLINENFC_TC_112() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, "4567845637463783");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Transaction reference number."));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass txnRefNumber with less than minimum length value as a part of request")
		public void API_VOIDONLINENFC_TC_113() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM, "2");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0023"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Transaction reference number."));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass parameter txnRefNumber as a part of request")
		public void API_VOIDONLINENFC_TC_114() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user does not pass value for parameter txnRefNumber as a part of request")
		public void API_VOIDONLINENFC_TC_115() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM,"Blank");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("Z6"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Mandatory fields missing"));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass the txnRefNumber which is already voided")
		public void API_VOIDONLINENFC_TC_116() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM,"298381");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0118"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Invalid Transaction Id or transaction already Voided "));
		}
		@Test(description = "Verify the server throws appropriate error message, when user pass the Duplicate or Invalid txnRefNumberto the request")
		public void API_VOIDONLINENFC_TC_117() {
			Map<String, String> requestData = new HashMap<String, String>(masterRequestData);
			requestData.put(Constants.TXNREFNUM,"298381");
			JsonObject voidNfcJsonObj = RequestBuilders.buildRequest(requestData);
			Response response = HitService.getJSONResponse(voidNfcJsonObj.toString(), endURL);
			System.out.println(response.asString());
			Reporter.log("Response : " + response.asString());
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORCODE).equalsIgnoreCase("TXN_0149"));
			Assert.assertTrue(HitService.getResponseValue(response, Constants.ERRORMESSAGE).equalsIgnoreCase("Duplicate Record or Invalid Transaction Number"));
		}
	}
