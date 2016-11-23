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
	
	
	@Override
	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		  int status = response.getStatusLine().getStatusCode();
          Header[] headers=response.getAllHeaders();
          for(Header s:headers){
          	System.out.println(s.toString());
          	if (s.getName().equals("Set-Cookie")){
          		cookie=s.getValue();
          	}
          }
          
          System.out.println(headers.length);
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
	

}
