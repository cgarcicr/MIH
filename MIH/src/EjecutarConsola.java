
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class EjecutarConsola {
	
	public void enConsola() {
		try
        {
			
			String comando="sqlldr system/d3c0ntr0lA@xe control='C:\\Users\\RC\\Desktop\\sqlloader\\carga\\control.ctl' data='C:\\Users\\RC\\Desktop\\sqlloader\\usuarios.txt' log='C:\\Users\\RC\\Desktop\\sqlloader\\logs\\usuarioslog.log'\r\n" + 
					"";
			
            // Se lanza el ejecutable.
            Process p=Runtime.getRuntime().exec ("cmd /c "+comando);
            
            // Se obtiene el stream de salida del programa
            InputStream is = p.getInputStream();
            
            /* Se prepara un bufferedReader para poder leer la salida más comodamente. */
            BufferedReader br = new BufferedReader (new InputStreamReader (is));
            
            // Se lee la primera linea
            String aux = br.readLine();
            
            // Mientras se haya leido alguna linea
            while (aux!=null)
            {
                // Se escribe la linea en pantalla
                System.out.println (aux);
                
                // y se lee la siguiente.
                aux = br.readLine();
            }
        } 
        catch (Exception e)
        {
            // Excepciones si hay algún problema al arrancar el ejecutable o al leer su salida.*/
            e.printStackTrace();
        } 
		
	}

}
