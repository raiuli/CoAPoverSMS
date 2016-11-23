package se.ltu.pmc.smsgateway;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;

public class TestCodeMain {

	public static void main(String[] args) {
		//bitoperationex();
		TestCode tCode=new TestCode();
		tCode.main1(args);

	}
	public static void bitoperationex() {
		Hex hex= new Hex();
		String s="5003";
		Object d;
		try {
			d=hex.decode(s);
			System.out.println(d);
		} catch (DecoderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	      byte a = 80;	/* 60 = 0011 1100 */
	      byte b = 3;	/* 13 = 0000 1101 */
	      byte c = 0;

	      c = (byte) (a & b);        /* 12 = 0000 1100 */
	      System.out.println(Integer.toBinaryString(a));
	      System.out.println(Integer.toBinaryString(b));
	      System.out.println(Integer.toBinaryString(c));
	      
	      System.out.println("a:"+Integer.toString(a,2));
	      System.out.println("b:"+Integer.toString(b,2));
	      System.out.println("c:"+Integer.toString(c,2));
	      
	      System.out.println(Integer.toBinaryString(a & 255 | 256).substring(1));
	      System.out.println(Integer.toBinaryString(b & 255 | 256).substring(1));
	      System.out.println(Integer.toBinaryString(c & 255 | 256).substring(1));
	      System.out.println();
	      
	      s="0101000000000011";
	      s="01010000";
	      BinaryCodec bc= new BinaryCodec(); 
	      byte[]b_array=bc.toByteArray(s);
	      for(byte b1:b_array){
	    	  System.out.println(b1);
	    	  System.out.println(Integer.toBinaryString(b1 & 255 | 256).substring(1));
	      }
	      String test="50-3-0-1-FFFFFFB8-75-72-69-5F-74-65-73-74-4D-4-61-70-69-6B-65-79-3D-66-64-32-30-35-65-64-35-38-39-6-6E-6F-64-65-3D-32-FFFFFFFF-73-65-6E-73-6F-72-31-3B-74-65-6D-70-3B-31-30-3B-68-75-6D-69-64-3B-33-30-3B";
	     

	      //String test="40-3-41-A7-B5-6C-69-67-68-74-FF-31-2E-0-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-FF-0-9-9F-3-A0-3-A0-0-0-F-21";
	      String[] test_split=test.split("-");
	      for(int i=0;i<test_split.length;i++){
	    	  if(test_split[i].length()==1){
	    		  test_split[i]="0"+ test_split[i];
	    	  }
	      }
	      for(int i=0;i<test_split.length;i++){
	    		System.out.print(test_split[i]);  
	    	
	      }
	      
	      /*  System.out.println("a & b = " + c );

	      c = a | b;         61 = 0011 1101 
	      System.out.println("a | b = " + c );

	      c = a ^ b;         49 = 0011 0001 
	      System.out.println("a ^ b = " + c );

	      c = ~a;           -61 = 1100 0011 
	      System.out.println("~a = " + c );

	      c = a << 2;        240 = 1111 0000 
	      System.out.println("a << 2 = " + c );

	      c = a >> 2;       /* 15 = 1111 */
	      //System.out.println("a >> 2  = " + c );

	      /*c = a >>> 2;*/     
	      /* 15 = 0000 1111 */
	      /*System.out.println("a >>> 2 = " + c );
	      */
	   }

}
