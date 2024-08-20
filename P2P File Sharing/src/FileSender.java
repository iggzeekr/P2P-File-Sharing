import java.io.*;
import java.net.*;

//dosyayi 256kb chunklar halinde alici ip adres ve port numarasina(destination) yollar

public class FileSender {
	
    private static final int CHUNK_SIZE = 256 * 1024; // 256KB

    public void sendFile(File file, SocketAddress destination) throws IOException {
    	
        byte[] buffer = new byte[CHUNK_SIZE];
        DatagramSocket socket = new DatagramSocket();

        try (FileInputStream fis = new FileInputStream(file)) {
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, destination);   //dosyayi chunklar halinde destination'a yollar
                socket.send(packet);
            }
        } 
        finally {                                                                             //try blogunda ne olursa olsun soketin kapanmasi icin finally blogunda soket kapama islemini yapiyorum
            socket.close();
        }
    }
}
