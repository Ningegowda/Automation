package com.pg.acquirer.requestbuilder;

import java.util.Map;

import com.google.gson.JsonObject;
import com.pg.acquirer.util.Constants;
import com.pg.acquirer.util.Util;

public class RequestBuilders {


	/**
	 * Building a JSON request for only changes in the isomessage
	 * @param isoMessage
	 * @return
	 */
	public static JsonObject buildRequest(Map<String, String>testData) {
		JsonObject jsonObject = new JsonObject();
		if(null != testData.get("txnAmount")){
		jsonObject= Util.buildJson(jsonObject,"txnAmount", testData.get("txnAmount"));
		}
		if(null != testData.get("invoiceNumber")){
		jsonObject= Util.buildJson(jsonObject,"invoiceNumber", testData.get("invoiceNumber"));
		}	if(null != testData.get("sysTraceNum")){
		jsonObject= Util.buildJson(jsonObject,"sysTraceNum", testData.get("sysTraceNum") );
		}
		JsonObject cardData = new JsonObject();
		if(null != testData.get("cardType")){
		cardData= Util.buildJson(cardData,"cardType", testData.get("cardType"));
		}if(null != testData.get("cardNumber")){
		cardData= Util.buildJson(cardData,"cardNumber", testData.get("cardNumber"));
		}if(null != testData.get("expDate")){
		cardData= Util.buildJson(cardData,"expDate", testData.get("expDate"));
		}if(null != testData.get("cvv")){
		cardData= Util.buildJson(cardData,"cvv", testData.get("cvv"));
		}if(null != testData.get("track2")){
		cardData= Util.buildJson(cardData,"track2", testData.get("track2"));
		}if(null != testData.get("emv")){
		cardData= Util.buildJson(cardData,"emv", testData.get("emv"));
		}
		if(cardData.toString().equals("{}")){
		   // jsonObject.add("cardData", cardData);
		}
		else if (null != cardData) {
		    jsonObject.add("cardData", cardData);
		}if(null != testData.get("terminalId")){
		jsonObject= Util.buildJson(jsonObject,"terminalId", testData.get("terminalId"));
		}if(null != testData.get("merchantCode")){
		jsonObject= Util.buildJson(jsonObject,"merchantCode", testData.get("merchantCode"));
		}if(null != testData.get("transactionType")){
		jsonObject= Util.buildJson(jsonObject,"transactionType", testData.get("transactionType") );
		}if(null != testData.get("nii")){
		jsonObject= Util.buildJson(jsonObject,"nii", testData.get("nii"));
		}if(null != testData.get("posConditionCode")){
		jsonObject= Util.buildJson(jsonObject,"posConditionCode", testData.get("posConditionCode"));
		}if(null != testData.get("currencyCode")){
		jsonObject= Util.buildJson(jsonObject,"currencyCode", testData.get("currencyCode") );
		}if(null != testData.get("mti")){
		jsonObject= Util.buildJson(jsonObject,"mti", testData.get("mti"));
		}if(null != testData.get("processingCode")){
		jsonObject= Util.buildJson(jsonObject,"processingCode", testData.get("processingCode"));
		}if(null != testData.get("posEntryMode")){
		jsonObject= Util.buildJson(jsonObject,"posEntryMode", testData.get("posEntryMode") );
		}if(null != testData.get("timeZoneOffset")){
		jsonObject= Util.buildJson(jsonObject,"timeZoneOffset", testData.get("timeZoneOffset"));
		}if(null != testData.get("timeZoneRegion")){
		jsonObject= Util.buildJson(jsonObject,"timeZoneRegion", testData.get("timeZoneRegion"));
		}if(null != testData.get("merchantType")){
		    jsonObject= Util.buildJson(jsonObject,"merchantType", testData.get("merchantType"));
		}
		if(null != testData.get("cashBackAmount")){
		    jsonObject= Util.buildJson(jsonObject,"cashBackAmount", testData.get("cashBackAmount"));
		}
		if(null != testData.get("txnRefNumber")){
		    jsonObject= Util.buildJson(jsonObject,"txnRefNumber", testData.get("txnRefNumber"));
		}
		if(null != testData.get("authTxnRefNum")){
		    jsonObject= Util.buildJson(jsonObject,"authTxnRefNum", testData.get("authTxnRefNum"));
		}
		if(null != testData.get("privateField")){
		    jsonObject= Util.buildJson(jsonObject,"privateField", testData.get("privateField"));
		}
		if(null != testData.get("tipAmount")){
		    jsonObject= Util.buildJson(jsonObject,"tipAmount", testData.get("tipAmount"));
		}
		return jsonObject;
	}

}
