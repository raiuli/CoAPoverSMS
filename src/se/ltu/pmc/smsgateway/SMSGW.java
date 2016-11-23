package se.ltu.pmc.smsgateway;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.eclipse.californium.core.coap.Message;
import org.eclipse.californium.core.network.serialization.UdpDataParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class SMSGW implements Runnable{
	public static String cookie;
	public static String csrf_token;
	CloseableHttpClient httpclient=null;
	MyResponseHandler myResponseHandler =null;
	SMSGW(){
		
	}
	public void run(){
		 httpclient = HttpClients.createDefault();
         
	        try {
	        	// Create a local instance of cookie store
	            CookieStore cookieStore = new BasicCookieStore();

	            // Create local HTTP context
	            HttpContext localContext = new BasicHttpContext();
	            // Bind custom cookie store to the local context
	            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	            //HttpGet httpget = new HttpGet("http://192.168.8.1/api/sms/sms-count");
	            HttpGet httpget = new HttpGet("http://192.168.8.1/html/index.html");
	            
	            System.out.println("Executing request " + httpget.getRequestLine());
	           
	           /* // Create a custom response handler
	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

	                @Override
	                public String handleResponse(
	                        final HttpResponse response) throws ClientProtocolException, IOException {
	                	
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

	            };*/
	            myResponseHandler =new MyResponseHandler();
	            String responseBody = httpclient.execute(httpget, myResponseHandler);
	            cookie=myResponseHandler.getCookie();
	            csrf_token=getCsrf_token(responseBody);
	            System.out.println("----------------------------------------");
	           // httpget.setURI(new URI("http://192.168.8.1/api/sms/sms-list"));
	           String body= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	            +"<request><PageIndex>1</PageIndex><ReadCount>20</ReadCount><BoxType>1</BoxType><SortType>0</SortType>"
	            +"<Ascending>0</Ascending><UnreadPreferred>0</UnreadPreferred></request>";
	           StringEntity entity = new StringEntity(body);
	            HttpPost httppost = new HttpPost(new URI("http://192.168.8.1/api/sms/sms-list"));

	            httppost.addHeader("Cookie",cookie);
	            httppost.addHeader("__RequestVerificationToken",csrf_token);
	            httppost.setEntity(entity);
	            responseBody = httpclient.execute(httppost, myResponseHandler);
	            
	            //System.out.println(responseBody);
	            org.jsoup.nodes.Document doc2 = Jsoup.parse(responseBody, "", Parser.xmlParser());
	            Elements links=doc2.select("Message");
	            for (Element link : links) {
	            	Element e=link.getElementsByTag("Index").first();
	            	String s_t=e.text();
	            	e=link.getElementsByTag("Date").first();
	            	s_t=s_t+" : "+e.text();
	            	e=link.getElementsByTag("Phone").first();
	            	s_t=s_t+" : "+e.text();
	            	e=link.getElementsByTag("Content").first();
	            	//e.text();
	            	/*String s1="5656435383966E6F64653D32FFFFFFFF73656E736F72313B74656D703B31303B68756D69643B33303B";
	            	
	            	System.out.println(s1.length());
	            	byte[]bytedata=DatatypeConverter.parseHexBinary(s1);
	            	s_t=s_t+" : "+convertHexToString(s1);
	            	byte[]bytedata1=hexStringToByteArray(s1);
	            	hexStringToByteArray(e.text());*/
	            	//CoAP coap = new CoAP();
	            	System.out.println("Content:"+e.text());
	            	System.out.println("Content-Length:"+e.text().length());
	            	try {
	            		UdpDataParser udpDataParser =new UdpDataParser();
		            	byte[]b_array=DatatypeConverter.parseHexBinary(e.text());
		            	Message m=udpDataParser.parseMessage(b_array);
		            	System.out.println(m.getPayloadString());
	            	}catch(Exception e1){
	            		e1.printStackTrace();
	            	}
	            	
	            	System.out.println(s_t);
	        	//e.attr(attributeKey)
	        	 }
	        
	            //System.out.println(doc2.toString());
	        } 
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	        finally {
	            try {
					httpclient.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	        }
		
	}
	public void getSMS(){
		 String body= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		            +"<request><PageIndex>1</PageIndex><ReadCount>20</ReadCount><BoxType>1</BoxType><SortType>0</SortType>"
		            +"<Ascending>0</Ascending><UnreadPreferred>0</UnreadPreferred></request>";
		           StringEntity entity = new StringEntity(body);
		            HttpPost httppost = new HttpPost(new URI("http://192.168.8.1/api/sms/sms-list"));

		            httppost.addHeader("Cookie",cookie);
		            httppost.addHeader("__RequestVerificationToken",csrf_token);
		            httppost.setEntity(entity);
		            responseBody = httpclient.execute(httppost, myResponseHandler);
		            
		            //System.out.println(responseBody);
		            org.jsoup.nodes.Document doc2 = Jsoup.parse(responseBody, "", Parser.xmlParser());
		            Elements links=doc2.select("Message");
		            for (Element link : links) {
		            	Element e=link.getElementsByTag("Index").first();
		            	String s_t=e.text();
		            	e=link.getElementsByTag("Date").first();
		            	s_t=s_t+" : "+e.text();
		            	e=link.getElementsByTag("Phone").first();
		            	s_t=s_t+" : "+e.text();
		            	e=link.getElementsByTag("Content").first();
		            	System.out.println("Content:"+e.text());
		            	System.out.println("Content-Length:"+e.text().length());
		            	try {
		            		UdpDataParser udpDataParser =new UdpDataParser();
			            	byte[]b_array=DatatypeConverter.parseHexBinary(e.text());
			            	Message m=udpDataParser.parseMessage(b_array);
			            	System.out.println(m.getPayloadString());
		            	}catch(Exception e1){
		            		e1.printStackTrace();
		            	}
		            	
		            	System.out.println(s_t);
		        	//e.attr(attributeKey)
		        	 }
		        
	}
	public void deleteSMS(String message_id) throws Exception{
		// httpget.setURI(new URI("http://192.168.8.1/api/sms/sms-list"));
        String body= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><Index>"+message_id+"</Index></request>";
        StringEntity entity = new StringEntity(body);
         HttpPost httppost = new HttpPost(new URI("http://192.168.8.1/api/sms/delete-sms"));

         httppost.addHeader("Cookie",cookie);
         httppost.addHeader("__RequestVerificationToken",csrf_token);
         httppost.setEntity(entity);
         String responseBody = httpclient.execute(httppost, myResponseHandler);
	}
	public static String convertHexToString(String hex){

	  	  StringBuilder sb = new StringBuilder();
	  	  StringBuilder temp = new StringBuilder();

	  	  //49204c6f7665204a617661 split into two characters 49, 20, 4c...
	  	  for( int i=0; i<hex.length()-1; i+=2 ){

	  	      //grab the hex in pairs
	  	      String output = hex.substring(i, (i + 2));
	  	      //convert hex to decimal
	  	      int decimal = Integer.parseInt(output, 16);
	  	      //convert the decimal to character
	  	      sb.append((char)decimal);

	  	      temp.append(decimal);
	  	  }
	  	  //System.out.println("Decimal : " + temp.toString());

	  	  return sb.toString();
	    }
	public  byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    
	    for (int i = 0; i < len; i += 2) {
	    	
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	public String getCsrf_token(String responseBody){
		/*String beginTag="<meta name=\"csrf_token\" content=\"";
        String endTag="\"/>";
        int a=responseBody.indexOf(beginTag);
        System.out.println("a:"+a);
        String s=responseBody.substring(a);
        System.out.println("s:"+s);
        int b=s.indexOf(endTag);
        s=s.substring(beginTag.length(), b);
        System.out.println("csrf_token:"+s);*/
        
        String tmp_csrf_token;
        org.jsoup.nodes.Document doc1 = Jsoup.parse(responseBody);
        Elements links=doc1.getElementsByTag("meta");
        tmp_csrf_token=links.get(0).attr("content");
        System.out.println("csrf_token:"+csrf_token);
        return tmp_csrf_token;
	}
	public String coapParser(String coap_data){
		return coap_data;
		
	}

}
