package se.ltu.pmc.smsgateway;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
public class HTTPHandler {
	private final static int CONNECTION_TIMEOUT = 3000;
	String USER_AGENT = "Mozilla/5.0";
	 String method="POST";
    //String contentType="application/xml";
    //String contentType="application/json";
	public String sendrequest(String urlStr, String request) throws IOException{
		String url = "http://192.168.8.1/api/sms/sms-count";
		url=urlStr;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

		return response.toString();
		
	}
}
