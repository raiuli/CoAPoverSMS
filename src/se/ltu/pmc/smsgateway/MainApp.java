package se.ltu.pmc.smsgateway;

import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
public class MainApp {

	public static void main(String[] args) {
		String orig = "iot_sensor_9c9d00cb3163485da17ef8fc9c7e5822:dc4de808a9a0475da237bfa26c7afd2d";

        //encoding  byte array into base 64
        byte[] encoded = Base64.encodeBase64(orig.getBytes());     
      
        System.out.println("Original String: " + orig );
        System.out.println("Base64 Encoded String : " + new String(encoded)+" !");
      
        //decoding byte array into base64
        byte[] decoded = Base64.decodeBase64(encoded);      
        System.out.println("Base 64 Decoded  String : " + new String(decoded));
        
        Thread t2= new Thread(new SMSGW());
        t2.start();
        
		// TODO Auto-generated method stub
		/*HTTPHandler hTTPHandler= new HTTPHandler();
		try {
			hTTPHandler.sendrequest("http://192.168.8.1/api/monitoring/status", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
