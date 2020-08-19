package com.pg.acquirer.util;

import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import com.pg.acquirer.config.AcquirerServicesTestProperties;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HitService {

	static private RestAssuredConfig config =null;

	static {

		RestAssured.baseURI = AcquirerServicesTestProperties.getPropertyValue("base.url");
		String port =AcquirerServicesTestProperties.getPropertyValue("port");
		if(port!=null && !port.trim().equalsIgnoreCase("")) {
			RestAssured.port = Integer.parseInt(AcquirerServicesTestProperties.getPropertyValue("port"));
		}
		RestAssured.basePath=AcquirerServicesTestProperties.getPropertyValue("base.acquirer.path");

		int timeout = Integer.parseInt(AcquirerServicesTestProperties.getPropertyValue("response.timeout"));

		config = RestAssured.config()
				.httpClient(HttpClientConfig.httpClientConfig()
						.setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout)
						.setParam(CoreConnectionPNames.SO_TIMEOUT, timeout))
				.newConfig().httpClient(new HttpClientConfig().reuseHttpClientInstance());
	}

	/**
	 *  hit the service with out access token
	 * @param jsonRequestBody
	 * @param endURL
	 * @return
	 */
	public static Response getJSONResponse(String jsonRequestBody, String endURL) {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(jsonRequestBody);
		Response response = request.given().config(config).post(endURL);
		Assert.assertEquals(response.getStatusCode(), 200,"Response is not 200");
		return response;
	}

	/**
	 * hit the service with access token
	 * @param jsonRequestBody
	 * @param endURL
	 * @param accessToken
	 * @return
	 */
	public static Response getJSONResponse(String jsonRequestBody, String endURL, String accessToken) {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("Authorization", "bearer "+accessToken);
		request.body(jsonRequestBody);
		Response response = request.given().config(config).post(endURL);
		Assert.assertEquals(response.getStatusCode(), 200,"Response is not 200");
		return response;
	}

	/**
	 * hit the service with get method
	 * @param endURL
	 * @param accessToken
	 * @return
	 */
	public static Response getJSONResponseGet( String endURL, String accessToken) {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("Authorization", "bearer "+accessToken);
		Response response = request.given().config(config).get(endURL);
		Assert.assertEquals(response.getStatusCode(), 200,"Response is not 200");
		return response;
	}
	
	/**
	 * return response for parameter
	 * @param response
	 * @param responseParam
	 * @return
	 */
	public static String getResponseValue(Response response, String responseParam) {
		String responseParamValue = response.jsonPath().get(responseParam);
		return responseParamValue;
	}

	/**
	 * return json Object for parameter
	 * @param response
	 * @param responseParam
	 * @return
	 */
	/*public static Map<String, String> getResponseJsonObjectInMap(Response response, String responseParam) {
		try {
			Map<String, String>	jsonObjectMap = new HashMap<>( response.jsonPath().getJsonObject(responseParam));
			return jsonObjectMap;
		}catch(Exception e) {
			return null;
		}
	}*/
}
