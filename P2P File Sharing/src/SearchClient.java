import java.io.*;
import java.net.*;

public class SearchClient {

	    public void clientSearch() throws IOException {
	    	
	        DatagramSocket socket = new DatagramSocket();

	        String searchRequest = "CLIENT SEARCH";                          //networkteki userlari aramak icin
	        InetAddress address = InetAddress.getByName("192.168.1.255");    // network'umun broadcast adresi
	        int port = 8888; 
	        DatagramPacket packet = new DatagramPacket(searchRequest.getBytes(), searchRequest.length(), address, port);
	        socket.send(packet);   
	       
	        byte[] buffer = new byte[1024];
	        packet = new DatagramPacket(buffer, buffer.length);
	        socket.setSoTimeout(5000);
	        while (true) {
	            try {
	                socket.receive(packet);                                                   //receive edilen pakette networkteki userlarin ip adresleri bulunur
	                String userIP = new String(packet.getData(), 0, packet.getLength());      //user's ip address, bu ip'ye data gonderilecek
	            } catch (SocketTimeoutException e) {
	                break; //timeout olursa break
	            }
	        }
	        socket.close();
	    }
}
