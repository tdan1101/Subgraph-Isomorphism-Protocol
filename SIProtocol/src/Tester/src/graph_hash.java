import java.io.*;
import java.security.*;
import java.util.Arrays;
import java.math.*;

public class graph_hash {
	public static String convertToString(int[][] matrix){
		 String buffer = "";
		 int length = matrix[0].length;
		 for(int i = 0; i < length; i++){
			 for(int j = 0; j < length; j++)
				 buffer += matrix[i][j];
		 }
		 return buffer;
	 }

// If we just want a Bit Int for our graph
	public static BigInteger hash(int[][] G){
		try{
		// Get the string for G's entries
		String G_string = convertToString(G);
		
		// Add the size "n" to the front
		int G_size = G[0].length;
		String G_size_string = Integer.toString(G_size);
		G_string = G_size_string + G_string;
		
		// do the SHA-256 hash
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(G_string.getBytes("UTF-8"));
		byte[] H = md.digest();
		
		// Make it a big int
		BigInteger Hash = new BigInteger(H);
		return Hash;
		}
		catch(Exception e){
			e.printStackTrace();
			BigInteger failure =  BigInteger.ZERO;
			return failure;
		}
	}
	
// If we want to hash the graph and save to file
	public static void hash_to_file(int[][] G, String filename){
		try{
		BigInteger H = hash(G);
		PrintWriter pw = new PrintWriter(filename);
		pw.println(new String(H.toByteArray()));
		pw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		;
	}
}
