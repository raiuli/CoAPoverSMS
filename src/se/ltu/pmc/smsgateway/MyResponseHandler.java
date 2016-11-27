package se.ltu.pmc.smsgateway;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class MyResponseHandler implements ResponseHandler<String>{
	String cookie;
	String csrf_token;
	
	@Override
	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		  int status = response.getStatusLine().getStatusCode();
          Header[] headers=response.getAllHeaders();
          //System.out.println("Response Header Begin================================================");
          for(Header s:headers){
          	//System.out.println(s.toString());
          	if (s.getName().equals("Set-Cookie")){
          		cookie=s.getValue();
          	}
          	if(s.getName().equals("__RequestVerificationToken")){
          		csrf_token=s.getValue();
          	}
          }
          
          //System.out.println(headers.length);
          //System.out.println("Response Header End================================================");
          if (status >= 200 && status < 300) {
              HttpEntity entity = response.getEntity();
              return entity != null ? EntityUtils.toString(entity) : null;
          } else {
              throw new ClientProtocolException("Unexpected response status: " + status);
          }
		
	}


	public String getCookie() {
		return cookie;
	}


	public void setCookie(String cookie) {
		this.cookie = cookie;
	}


	public String getCsrf_token() {
		return csrf_token;
	}


	public void setCsrf_token(String csrf_token) {
		this.csrf_token = csrf_token;
	}
	

}
