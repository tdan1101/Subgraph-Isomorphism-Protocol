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
import HelperClass.FileReader;

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
	         System.out.println("Message received from the verifier : " + bitStr);
	         int bit = Integer.parseInt(bitStr);
	         if(bit == 0){
	        	 // Sending a and Q
	        	 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	             out.println(perm);
	             out.println(Q);	
	         }
	         else{
	        	 //TODO Compute the subgraph of Q 
	        	 // Sending pi and Q'
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