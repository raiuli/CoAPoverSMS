package se.ltu.pmc.smsgateway;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;



public class SendDataToSSC implements Runnable{
	String data="";
	CloseableHttpClient httpclient=null;
	MyResponseHandler myResponseHandler =new MyResponseHandler();
	
	
	public SendDataToSSC(String data) {
		super();
		this.data = data;
	}


	@Override
	public void run() {
		try {
			String [] data_fileds=data.split(";");
			/*String s="param=%7B%22measurements%22%3A+%5B%7B%22info%22%3A+%22%22%2C+%22serial%22%3A+%22testSensor01%22%2C+%22temperature%22%3A+";
			String result = java.net.URLDecoder.decode(s, "UTF-8");
			  String body1 = "param=%7B%22measurements%22%3A+%5B%7B%22info%22%3A+%22%22%2C+%22serial%22%3A+%22";
			  result = java.net.URLDecoder.decode(body1, "UTF-8");
			  System.out.println(result);
			  String body2 = "%22%2C+%22temperature%22%3A+";
			  result = java.net.URLDecoder.decode(body2, "UTF-8");
			  System.out.println(result);
			  String body3 = "%2C+%22data_time%22%3A+%22";
			  result = java.net.URLDecoder.decode(body3, "UTF-8");
			  System.out.println(result);
			System.out.println(result);*/
			//%Y-%m-%d %H:%M:%S
			httpclient= HttpClients.createDefault();
			String body1="measurement =  ";
			String  body2= "{\'measurements\':" 
	                    +"[{" 
	                        +"'serial':'testSensor01'," 
	                        +"'temperature':'the sensor data'," 
	                        +"'info':''," 
	                        +"'data_time':''" 
	                     +"}]" 
	                     +"}";
			 JSONObject jSONObject= new JSONObject(body2);
			 JSONArray jsonArray= jSONObject.getJSONArray("measurements");
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			System.out.println(timeStamp);

			 Object jSONDateTime=((JSONObject)jsonArray.get(0)).put("data_time",timeStamp);
			 jSONDateTime=((JSONObject)jsonArray.get(0)).put("serial","brbwsn01");
			 jSONDateTime=((JSONObject)jsonArray.get(0)).put("temperature",Integer.valueOf(data_fileds[2]));
			 JSONObject jSONObject2= new JSONObject();
			 jSONObject2.put("serial","brbwsn02");
			 jSONObject2.put("soil_moisture",data_fileds[4]);
			 jSONObject2.put("info","");
			 jSONObject2.put("data_time",timeStamp);
			 //jsonArray.put(jSONObject2);
			 //jSONDateTime.put("data_time", timeStamp);
			 //("data_time", timeStamp);

			 HttpPost httppost = new HttpPost(new URI("http://130.240.134.30/ssc/api/basic/temperature/submit"));
			 httppost.addHeader("Authorization","Basic dGVzdGFwaTpwYXNzdzByZA==");
			 httppost.addHeader("Content-Type","application/x-www-form-urlencoded");
				body1="param="+jSONObject.toString();
				System.out.println(body1);
				StringEntity entity = new StringEntity(body1);
				httppost.setEntity(entity);
	            String responseBody = httpclient.execute(httppost, myResponseHandler);
	            System.out.println(responseBody);
			 //httppost.addHeader("Authorization","Basic dGVzdGFwaTpwYXNzdzByZA==");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
}
