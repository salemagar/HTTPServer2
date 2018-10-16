
import java.util.*;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.net.InetAddress;


public class HttpServer1{

	public static void main(String[] args) throws IOException{
		int portnumber = 9000;		//Initializing port number
		ServerSocket httpserver = new ServerSocket(portnumber);		//Creating a ServerSocket object called httpserver
		String ip = httpserver.getInetAddress().getLocalHost().getHostAddress();	//Storing the IP address in a String variable
		
		//
		//Printing the server side message. Displays server IP Address, port number and a message
		//
		System.out.println("Server IP address is: " + ip);
		System.out.println("Server port number is: " + portnumber);
		System.out.println("Server has started successfully!");
		
		try {
			while(true) {
				Socket clientSocket = httpserver.accept();		//Listens and waits for the client connection and accepts it
				
				//
				//Receives the client request message, converts the bytecode message into characters and stores it as stream object  
				//
				InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
				BufferedReader reader = new BufferedReader(in);		//Reads text from character inputStream object
				String requestMsg = reader.readLine();		//Reads the first line and stores it in a String variable
				
				//
				//Displays the messages read from the inputStream one line at a time until there are no characters left to be read
				//
				while(!requestMsg.isEmpty()) {
					System.out.println(requestMsg);
					requestMsg = reader.readLine();
					}
				
				try {
					//
					//A String message to be displayed on the client screen after the connection has been established
					//
					String httpResponse = "HTTP/1.1 200 OK\r\nKeep-Alive: timeout=10, max=100\r\nContent-Type:text/html\r\n\r\n "
							+ "<html> <title> Http Server </title> <body> The connection to the server was made on "
							+ "<script> var today = new Date(); document.write(today); </script> </body> <html>";
					
					//Converts the above string message into bytes and sends it to the client
					clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
				} finally {
					clientSocket.close();		//closes the client socket
				}
			}
		} finally {
			httpserver.close();		//closes the server socket
		}

	}

}
