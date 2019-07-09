import java.io.*;
import java.util.*;
import java.math.*;

public class Add128 implements SymCipher{

	public static byte[] key = new byte[128];

	public Add128(){
		new Random().nextBytes(key); //Creates random byte array
		for(int i = 0; i < key.length; i ++){
			key[i] = (byte)Math.abs(key[i]);
		}
	}

	public Add128(byte[] bytes){
		key = bytes; //Sets key equal to given byte array
	}

	public byte [] getKey(){
		return key;
	}

	public byte [] encode(String S){
		byte [] bytes = S.getBytes(); //Gets bytes from the given String
		byte [] code = new byte [bytes.length];
		int lengthCheck = 0;
		for(int i = 0; i < bytes.length; i++){
			code[i] = (byte)(bytes[i] + key[lengthCheck]);
			if(i == key.length){
				lengthCheck = 0;
			}
			lengthCheck++;
		}
		return code;
	}

	public String decode(byte [] bytes){
		byte [] decode = new byte [bytes.length];
		int lengthCheck = 0;
		for(int i = 0; i < bytes.length; i++){
			decode[i] = (byte)(bytes[i] - key[lengthCheck]);
			if(i == key.length){
				lengthCheck = 0;
			}
			lengthCheck++;
		}
		String s = new String(decode);
		return s;

	}

}