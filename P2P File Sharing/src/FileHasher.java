import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//ayni dosya olup olmadigini anlamak icin dosya hashlerini karsilastiyorum

public class FileHasher {

    private MessageDigest md;

    public FileHasher() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("SHA-256");
    }

    public String calculateHash(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);                         //dosyayi okur ve md'yi update eder
            }
        }

        byte[] hashBytes = md.digest();                                  //son hash degeri

        // Byte arrayini hexadecimal stringe donusturur
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));                
        }

        
        return sb.toString();    
    }

    
    //Directory ve subdirectoryler icerisindeki tum dosyalarin hashini bulur
    public Map<String, List<String>> calculateHashes(File file) throws IOException {
    	
        Map<String, List<String>> fileHashes = new HashMap<>();                                    //key, file'in hashi. value da o hashe sahip dosya pathleri

        if (file.isDirectory()) {                                                                  //File objesi bir dizinse, recursive sekilde cagirir
            for (File child : file.listFiles()) {
                fileHashes.putAll(calculateHashes(child));
            }
        } 
        else {
            String hash = calculateHash(file);
            fileHashes.computeIfAbsent(hash, k -> new ArrayList<>()).add(file.getPath());          //ayni hash degerine birden fazla dosya pathi ekleyebilmek icin computeIfAbsent kullandim 
        }

        return fileHashes;
    }

    
    public boolean isFileDuplicate(Map<String, List<String>> fileHashes, File file) throws IOException {
    	
        String hash = calculateHash(file);                                                        //input dosyanin hashi alinir
        return fileHashes.containsKey(hash);                                                      //bu dosyanin hashi mapin icerisinde mi degil mi diye kontrol eder
    } 
    
    /*
    //Test etmek icin
     
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
	    FileHasher hasher = new FileHasher();
	    File path = new File("/home/sevgi/Music/dosyalar");
	    Map<String, List<String>> fileHashes = hasher.calculateHashes(path);
	
	    File fileToCheck = new File("/home/sevgi/Music/5");
	    boolean isDuplicate = hasher.isFileDuplicate(fileHashes, fileToCheck);
	
	    if (isDuplicate) {
	        System.out.println("The file has a duplicate.");
	    } 
	    else {
	        System.out.println("The file doesn't have a duplicate.");
	    }
    }
    */
    
}
