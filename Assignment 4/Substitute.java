import java.util.*;
import java.io.*;

public class Substitute implements SymCipher{
		
	public byte [] key = new byte[256];
	public byte [] solution = new byte[256];

	public Substitute(){
		for(int i = 0; i < key.length; i ++){
			key[i] = (byte)Math.abs(i);
		}
		Random rand = new Random();

		for(int i = 0; i < key.length; i++){  //Used to make a random permutation of values
			int newPos = rand.nextInt(key.length); //Random position
			byte old = key[i]; //storing valuing
			key[i] = key[newPos]; 
			key[newPos] = old; //replacing value
			solution[i] = (byte)newPos;
		}
		for(int i = 0; i < key.length; i ++){
			key[i] = (byte)Math.abs(key[i]);
		}
		for(int i = 0; i < solution.length; i ++){
			solution[i] = (byte)Math.abs(solution[i]);
		}
	}
	public Substitute(byte[] bytes){
		key = bytes;
	}
	public byte [] getKey(){
		return key;		}

	public byte [] encode(String S){
		byte [] bytes = S.getBytes();
		int lengthCheck = 0; //Placeholder for key length
		for(int i = 0; i < bytes.length; i++){
			solution[lengthCheck] = bytes[i];
			bytes[i] = key[lengthCheck]; //Substitute
			if(i == key.length){ //Rest if key has reached max value
				lengthCheck = 0;
			}
			lengthCheck++;
		}
		return bytes;
	}
	
	public String decode(byte [] bytes){
		int lengthCheck = 0;
		for(int i = 0; i < bytes.length; i++){
			bytes[i] = solution[lengthCheck];
			if(i == key.length){
				lengthCheck = 0;
			}
			lengthCheck++;
		}
		String s = new String(bytes);
		return s;
	}
}