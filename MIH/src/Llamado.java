import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class Llamado {
	
	private String lectura;


	
	public String getLectura() {
		return lectura;
	}



	public void setLectura(String lectura) {
		this.lectura = lectura;
	}



	public void llenarMensaje() {
		 //Lectura del archivo log
        String log_text="C:\\MIH\\SALIDA\\log_20171018150239.log";
         try(BufferedReader br=new BufferedReader(new FileReader(log_text));
	            ){
	        		        	       	
            
	           //Leemos el fichero y lo mostramos por pantalla
	          String linea=br.readLine();		          
	           while(linea!=null){
	              System.out.println(linea);
	              this.lectura=this.lectura+linea+"\n";
	              linea=br.readLine();
	            }
	           
	        }catch(IOException e){
	            System.out.println("Error E/S: "+e);
	        }	        
              
	}	

}
