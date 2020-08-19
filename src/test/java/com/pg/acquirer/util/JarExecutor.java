package com.pg.acquirer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.pg.acquirer.config.AcquirerServicesTestProperties;

public class JarExecutor{

	public static String executor (Map<String, String > requestMap) throws IOException, InterruptedException {
		String command = buildJarExecutableRequestSaleManual(requestMap);
		String response= new String();
		System.out.println(command);
		Process proc = Runtime.getRuntime().exec(command);
		Thread.sleep(50000);

		InputStream in = proc.getInputStream();
		//InputStream err = proc.getErrorStream();

		byte b[]=new byte[in.available()];
		in.read(b,0,b.length);
		response = new String(b);
		System.out.println(response);

		/*
	    byte c[]=new byte[err.available()];
	    err.read(c,0,c.length);
	    System.out.println(new String(c));
	    System.out.println("execution done");*/
		proc.destroy();
		return response;
	}

	public static String getBase64EncodedValue (Map<String, String > requestMap) {
		try {
			String response = executor(requestMap);

			String[] responseArray = response.split("\n");

			for(String str :  responseArray) {
				System.out.println(str);

				if(str.startsWith("Base64 encoded Data >>")) {
					return (str.split(">>")[1].trim());
				}
			}
		}catch(Exception e) {

		}
		return null;
	}


	public static String buildJarExecutableRequestSaleManual(Map<String, String> parameters) {

		String command = new String("java -jar");

		if(null!=parameters.get("pan")) {
			command = command + " -Dpan="+ parameters.get("pan");
		}
		if(null!= parameters.get("pan_expiry")) {
			command = command + " -Dexp="+ parameters.get("pan_expiry");
		}
		if(null!= parameters.get("pan_cvv")) {
			command = command + " -Dcvv="+ parameters.get("pan_cvv");
		}
		if(null!= parameters.get("pin")) {
			command = command + " -Dpin="+ parameters.get("pin");
		}
		if(null!= parameters.get("track2")) {
			command = command + " -Dtrack2="+ parameters.get("track2");
		}
		if(null!= parameters.get("ksn")) {
			command = command + " -Dksn="+ parameters.get("ksn");
		}
		if(null!= parameters.get("ipek")) {
			command = command + " -Dipek="+ parameters.get("ipek");
		}
		if(null!= parameters.get("bdk")) {
			command = command + " -Dbdk="+ parameters.get("bdk");
		}
		if(null!= parameters.get("tid")) {
			command = command + " -Dtid="+ parameters.get("tid");
		}
		if(null!= parameters.get("mid")) {
			command = command + " -Dmid="+ parameters.get("mid");
		}
		if(null!= parameters.get("pos_entrymode")) {
			command = command + " -Dpos.entry.mode="+ parameters.get("pos_entrymode");
		}
		if(null!= parameters.get("merchant_type")) {
			command = command + " -Dmerchant.type="+ parameters.get("merchant_type");
		}
		if(null!= parameters.get("nii")) {
			command = command + " -Dnii="+ parameters.get("nii");
		}
		if(null!= parameters.get("currency_code")) {
			command = command + " -Dcurrency.code="+ parameters.get("currency_code");
		}
		if(null!= parameters.get("de55_enable")) {
			command = command + " -Dde55.enable="+ parameters.get("de55_enable");
		}
		if(null!= parameters.get("transaction_type")) {
			command = command + " -Dtransaction.type="+ parameters.get("transaction_type");
		}
		if(null!= parameters.get("transaction_amount")) {
			command = command + " -Damount="+ parameters.get("transaction_amount");
		}
		if(null!= parameters.get("card_acceptor_name")) {
			command = command + " -Dcard.acceptor.name="+ parameters.get("card_acceptor_name");
		}
		command = command + " "+ AcquirerServicesTestProperties.getJarLocation();

		return command;
	}

}
