import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LecturaJava {

	public static void main(String[] args) {
	
	        try(BufferedReader br=new BufferedReader(new FileReader("C:\\MIH\\SALIDA\\log_20171017194612.log"));
	            ){
	        		        	       	
            
	            //Leemos el fichero y lo mostramos por pantalla
	            String linea=br.readLine();
	            while(linea!=null){
	                System.out.println(linea);
	                linea=br.readLine();
	            }
	        }catch(IOException e){
	            System.out.println("Error E/S: "+e);
	        }
	        
	        
	        
	    

	}

}
