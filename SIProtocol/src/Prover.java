/**
 * In Subgraph Isomorphism Protocol, prover's instruction:
 * 
 * 	1. For each round, create a random permutation
 *  2. Generate commitment(Q), where Q = a(G2)
 *  2. Send a random bit to the Prover(Client)
 *  	0: request permutation a
 *      1: request pi and Q' such that Q' = pi(G1)
 *  3. 	Verify the data received from Prover
 */

import java.io.*;
import java.net.Socket;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import HelperClass.*;


public class Prover {
	 private static Socket socket;
	 static int[][] matrix;
	 /* TODO: change Q to the "real" adjacency matrix( OR
	  * any graph structure based on isomorphism implementations)
	  * variable called "perm" is the permutation, which doesn't 
	  * need to be String type
	  *  Here's one possible solution: 
	  *     1. A function of computing the permutation, which 
	  *        uses passed parameters in computation. This 
	  *        function is like an one-to-one mapping(Same pair 
	  *        of parameters should give the same permuting result),
	  *        otherwise Prover may have issues of verification. 
	  *        This function is available to both prover can verifier 
	  *        
	  *     2. After receiving either 0 or 1 from the verifier, 
	  *        prover can send a string which contains those parameters.
	  *        With the knowledge of those parameters, verifier can 
	  *        obtain Q     
	  *        
	  */
	 static int[][] Q = new int[10][10]; //TODO
	 static String perm = "Way of generating a random permutation"; //TODO
	 static String pi = "random stuff..."; //TODO pi for computing Q'
	 static String subQ = "subgraph of Q"; //TODO
	 
	 
	 /**
	  * Convert an adjacency matrix to string before sending
	  * it to the verifier
	  * @param matrix
	  * @return string
	  */
	 public static String convertToString(int[][] matrix){
		 String buffer = "";
		 int length = matrix[0].length;
		 for(int i = 0; i < length; i++){
			 for(int j = 0; j < length; j++)
				 buffer += matrix[i][j];
		 }
		 return buffer;
	 }
	 
	 
	 
	 public static void main(String args[]){
	     try {
	         String host = "localhost";
	         /* Get the server address from a dialog box.
	            If prover and verifier are running on the 
	            same iOS machine, leave IPAddress empty*/
	         String serverAddress = JOptionPane.showInputDialog(
	                    "Enter IP Address of a machine that is\n" +
	                    "If it's running on iOS terminal, leave it empty");
	         socket = new Socket(serverAddress, 6077);
	        /* JFrame f = new JFrame();
	         JFileChooser fc = new JFileChooser();
	         int ret = fc.showOpenDialog(f);
			 if(ret==JFileChooser.APPROVE_OPTION){
				 File file = fc.getSelectedFile();
				 String name = file.getName();
				 System.out.println(name);
				 matrix = FileReader.readGraph(name);
				 f.setVisible(true);
				 
			 }*/
	         
	         /* Taylor's additions on 11/19 Here I will add in the functionality to generate the matrices G2, G1, as well as the permutations necessary to convert between them
	          *
	          */
//===========================================================================================
	         
	         //Intake size n from user
	         int n = 10;
	         
//============================================================================================	         
	         // Generate G2
	         int[][] pre_G2 = new int[n][n];
	         int[][] G2 = matrixops.fill(pre_G2,0.7);
	         
//============================================================================================
	         // Store G2
	         File G2F = new File("/graph2.txt");
	         try{
	        	 if(!G2F.exists()){
	        		 G2F.createNewFile();
	        	 }
	         catch(IOException e){
	        	 e.printStackTrace();
	         }
	         
	         PrintWriter pw = new PrintWriter(G2F.getName());
	         String G2_string = MatrixOps.convertToString(G2);
	         pw.println(G2_string);
	         pw.close();
	         
	         
//===============================================================================================
	        /*
	         * Generate G1 by generating the reduction matrices R and P1 a permutation
	         * Currently we do not need to store these as we will not ever send them directly
	         * 
	         * Naturally however we store P1
	         */
	         
//===============================================================================================	         
	         // Send G1 G2 to Verifier for storage.
	         //
	         
//=================================================================================================	         
	         // Generate G3, the permuted version of G2
	         int[][] pre_G3 = new int[][];
	         int[][] P3 = MatrixOps.perm_mat(n);
	         int[][] G3 = MatrixOps.permute(pre_G3, P3);
	         
//================================================================================================
	         // Store G3 and P3
	         File G3F = new File("/graph3.txt");
	         try{
	        	 if(!G3F.exists()){
	        		 G3F.createNewFile();
	        	 }
	         catch(IOException e){
	        	 e.printStackTrace();
	         }
	         
	         PrintWriter pw = new PrintWriter(G3F.getName());
	         String G3_string = MatrixOps.convertToString(G3);
	         pw.println(G3_string);
	         pw.close();
	         
	         //================== Same for Permutation 3
	         File P3F = new File("/P3.txt");
	         try{
	        	 if(!P3F.exists()){
	        		 P3F.createNewFile();
	        	 }
	         catch(IOException e){
	        	 e.printStackTrace();
	         }
	         PrintWriter pw = new PrintWriter(P3F.getName());
	         String P3_string = MatrixOps.convertToString(P3);
	         pw.println(P3_string);
	         pw.close();
	         
//==================================================================================================
	         // Commit to G3
	         File G3Commit = new File("/graphcommit.txt");
	         try{
	        	 if(!G3Commit.exists()){
	        		 G3Commit.createNewFile();
	        	 }
	         catch(IOException e){
	        	 e.printStackTrace();
	         }
	         HelperClass.graph_hash.hash_to_file(G3,"/graphcommit.txt");
//===================================================================================================	         
	         //Commit to the permuted subgraph of G3 isomorphic to G1
	         
	         
	         /* Repeat the above essentially but do subgraph commit
	          * 
	          */
//==================================================================================================
	         // Send commits of G3, subgraph
	         
	         
//==================================================================================================
	         //Receive the challenge
	         
//==================================================================================================
	         /*
	          * If the challenge = 0, then we will send the files containing G3, P3
	          */
	         
	         /*
	          * If the challenge = 1, then we will compute the subgraph of G3 isomorphic to G1 as follows:
	          * Send Two matrices P3^{-1}RP1 as above, as described in the "ideas" document.
	          * 
	          */
//==================================================================================================
	         // I shouldn't mess with this part
	         matrix = FileReader.readGraph("/g1");
	         String buffer = convertToString(matrix);
	         String sendMessage = buffer + "\n";
	         OutputStream os = socket.getOutputStream();
	         OutputStreamWriter osw = new OutputStreamWriter(os);
	         BufferedWriter bw = new BufferedWriter(osw);
	         bw.write(sendMessage);
	         bw.flush();
	         System.out.println("Message sent to the verifier : "+sendMessage);
	         //Get the return message from the server
	         InputStream is = socket.getInputStream();
	         InputStreamReader isr = new InputStreamReader(is);
	         BufferedReader br = new BufferedReader(isr);
	         String bitStr = br.readLine();
	         
	         // Getting the Challenge
	         System.out.println("Message received from the verifier : " + bitStr);
	         int bit = Integer.parseInt(bitStr);
	         if(bit == 0){
	        	 // Sending P3^-1
	        	 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	             out.println(perm);
	             out.println(Q);	
	         }
	         else{
	        	 // Sending P3^-1 P1
	        	 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	             out.println(pi);
	             out.println(subQ);
	         }
	      }
	      catch (Exception exception){
	          exception.printStackTrace();
	      }
	      finally{
	          //Closing the socket
	          try{
	                socket.close();
	            }
	          catch(Exception e){
	                e.printStackTrace();
	          }
	     }
	}
}