import java.io.*;
import java.net.Socket;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import HelperClass.FileReader;

/**
 * 
 */
public class Prover {
	 private static Socket socket;
	 static int[][] matrix;
	 
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
	         //Send the message to the server
	         OutputStream os = socket.getOutputStream();
	         OutputStreamWriter osw = new OutputStreamWriter(os);
	         BufferedWriter bw = new BufferedWriter(osw);
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
	         bw.write(sendMessage);
	         bw.flush();
	         System.out.println("Message sent to the verifier : "+sendMessage);
	         //Get the return message from the server
	         InputStream is = socket.getInputStream();
	         InputStreamReader isr = new InputStreamReader(is);
	         BufferedReader br = new BufferedReader(isr);
	         int bit = Integer.parseInt(br.readLine());
	         System.out.println("Message received from the verifier : " + bit);
	         if(bit == 0){
	        	 
	         }
	         else{
	        	 
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