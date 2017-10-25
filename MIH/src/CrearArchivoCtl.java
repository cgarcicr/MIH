import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CrearArchivoCtl {

	public static void main(String[] args) throws IOException {
		
		String ruta="C:\\lecturajava\\act.ctl";
	    File archivo = new File(ruta);
        BufferedWriter bw;
        if(archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("El fichero de texto ya estaba creado.");
            System.out.println("El fichero ya existe y no es necesario crearlo.");
        } else {
        	String tabla="SYSTEM.LOCAL_USUARIO";
        	String campos="(\n" + 
        			"ID INTEGER EXTERNAL TERMINATED BY \";\",\n" + 
        			"NOMBRE CHAR TERMINATED BY \";\",\n" + 
        			"APELLIDO CHAR TERMINATED BY \";\"\n" + 
        			") ";
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("load data append\ninto table "+tabla+"\n"+campos);            
            System.out.println("El fichero no existe y se acaba de crear.");
        }
        bw.close();
		
	
	    }

	}
