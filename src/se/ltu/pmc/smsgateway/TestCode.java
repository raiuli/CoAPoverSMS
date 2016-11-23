package se.ltu.pmc.smsgateway;

import static org.eclipse.californium.core.coap.CoAP.MessageFormat.TOKEN_LENGTH_BITS;
import static org.eclipse.californium.core.coap.CoAP.MessageFormat.TYPE_BITS;
import static org.eclipse.californium.core.coap.CoAP.MessageFormat.VERSION_BITS;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.californium.core.coap.Message;
import org.eclipse.californium.core.network.serialization.DataParser;

import org.eclipse.californium.core.network.serialization.MessageHeader;
import org.eclipse.californium.core.network.serialization.UdpDataParser;
import org.eclipse.californium.elements.RawData;
import org.eclipse.californium.elements.util.DatagramReader;

public class TestCode extends DataParser{
	String s1="";
	public void main1(String[] args) {
		int x = 5;
		System.out.println("B:"+Integer.toString(x,2));
		s1="500302FFFFFFB87572695F746573744D46170696B65793D6664323035656435383966E6F64653D32FFFFFFFF73656E736F72313B74656D703B31303B68756D69643B33303";
		s1="400341A7B56C69676874FF312E00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00099F03A003A000000F21";
		s1="400341a7b56c69676874ff73656e736f72313b74656d703b31303b68756d69643b33303b68";
		for (byte b : s1.getBytes()) {
		    System.out.println(Integer.toBinaryString(b & 255 | 256).substring(1));
		}
		
    	//s1="556435383966E6F64653D32FFFFFFFF73656E736F72313B74656D703B31303B68756D69643B33303B";
    	System.out.println(s1.length());
    	//byte[]bytedata=DatatypeConverter.parseHexBinary(s1);
    	String s_t=convertHexToString(s1);
    	//DataParser dataParser 
    	//Message m=DataParser.parseMessage(s1.getBytes());
    	//Message m=super.parseMessage(s1.getBytes());
    	String test="50-3-0-1-FFFFFFB8-75-72-69-5F-74-65-73-74-4D-4-61-70-69-6B-65-79-3D-66-64-32-30-35-65-64-35-38-39-6-6E-6F-64-65-3D-32-FFFFFFFF-73-65-6E-73-6F-72-31-3B-74-65-6D-70-3B-31-30-3B-68-75-6D-69-64-3B-33-30-3B";
	     test="40-3-41-A7-B5-6C-69-67-68-74-FF-31-64-32-30-35-65-64-35-38-39-0-6E-6F-64-65-3D-32-0-20-0-0-0-0-0-96-8-63-A-EB-0-9-A6-3-A0-3-A0-0-0-F-21";
	     test="40-3-41-A7-B5-6C-69-67-68-74-FF-31";
	     test="40-3-41-A7-B5-6C-69-67-68-74-FF-73-65-6E-73-6F-72-31-3B-74-65-6D-70-3B-31-30-3B-68-75-6D-69-64-3B-33-30-3B";
	      //String test="40-3-41-A7-B5-6C-69-67-68-74-FF-31-2E-0-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-0-9-9F-3-A0-3-A0-0-0-F-21";
	      String[] test_split=test.split("-");
	      for(int i=0;i<test_split.length;i++){
	    	  if(test_split[i].length()==1){
	    		  test_split[i]="0"+ test_split[i];
	    	  }
	      }
	      test="";
	      for(int i=0;i<test_split.length;i++){
	    		System.out.print(test_split[i]);  
	    		test=test+test_split[i];
	      }
	    	test=s1;
	      System.out.println(test);
	    	System.out.println(test.length());
	    	
    	UdpDataParser udpDataParser =new UdpDataParser();
    	byte[]b_array=DatatypeConverter.parseHexBinary(test);
    	Message m=udpDataParser.parseMessage(b_array);
    
    	String payload=m.getPayloadString();
    	System.out.println(payload);
	}
	
	public static String convertHexToString(String hex){

	  	  StringBuilder sb = new StringBuilder();
	  	  StringBuilder temp = new StringBuilder();

	  	  //49204c6f7665204a617661 split into two characters 49, 20, 4c...
	  	  for( int i=0; i<hex.length()-1; i+=2 ){

	  	      //grab the hex in pairs
	  	      String output = hex.substring(i, (i + 2));
	  	      System.out.println("output:"+output);
	  	      
	  	      //convert hex to decimal
	  	      int decimal = Integer.parseInt(output, 16);
	  	    String binary = Integer.toString(decimal, 2);
	  	    System.out.println("decimal:"+decimal);
	  	  System.out.println("binary:"+binary);
	  	      //convert the decimal to character
	  	      sb.append((char)decimal);

	  	      temp.append(decimal);
	  	  }
	  	  //System.out.println("Decimal : " + temp.toString());

	  	  return sb.toString();
	    }
	

	@Override
	protected MessageHeader parseHeader(DatagramReader reader) {
		int version = reader.read(VERSION_BITS);
		
		int type = reader.read(TYPE_BITS);
		int tokenLength = reader.read(TOKEN_LENGTH_BITS);
		RawData rd;
		// TODO Auto-generated method stub
		byte[] b=s1.getBytes();
		super.parseMessage(b);
		return null;
	}
}
