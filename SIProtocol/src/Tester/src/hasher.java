import java.io.*;
import java.security.*;
import java.util.Arrays;
public class hasher {
	public static void main(String[] args){
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String text = "abc";
			
			md.update(text.getBytes("ASCII"));
			System.out.println(text.getBytes("UTF-8"));
			byte[] digest = md.digest();
			
			System.out.println(Arrays.toString(digest));
			System.out.println(digest.length);
			//for(int i = 0; i < 6; i++){
			//	System.out.println(digest[i]);
			//}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
