import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	private static ServerSocket serverSocket;
	public static final int SERVERPORT = 6077;
	Thread serverThread = null;
	class ServerThread implements Runnable{
		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(SERVERPORT);
				while(true){
					Socket socket = serverSocket.accept();
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public int returnBit(){
		System.out.println("Choose 0 or 1");
		Scanner in = new Scanner(System.in);
		return in.nextInt();
	}
	
	public boolean checkValidity(){
		
		return false;
	}
	public static void main(String args[]){
		
		
	}
	
	
	
}
