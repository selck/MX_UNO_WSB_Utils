package mx.com.amx.uno.util.utils;

import java.util.Properties;

import org.apache.log4j.Logger;

import mx.com.amx.uno.util.dto.ParametrosDTO;

public class Utileria {
	
	private static Logger logger=Logger.getLogger(Utileria.class);
	
	public static ParametrosDTO getProperties(){
		ParametrosDTO parametros = new ParametrosDTO();
		try {
			Properties propsTmp = new Properties();
			propsTmp.load(Utileria.class.getResourceAsStream( "/general.properties" ));
			String ambiente=propsTmp.getProperty("ambiente");
			//String rutaProperties = propsTmp.getProperty(rutaPropiedades.replace("ambiente", ambiente));			
			//Properties props = new Properties();
			//props.load(new FileInputStream(new File(rutaProperties)));				
			parametros.setAmbiente(ambiente);
			parametros.setPathSaveJsonWeb02(propsTmp.getProperty(ambiente+".pathSaveJsonWeb02"));
			parametros.setPathShell(propsTmp.getProperty("pathShell"));
		}catch (Exception e) {
			logger.error("Error getProperties: ",e);
		}
		return parametros;
	}
	
	public static boolean transfiereWebServer(String rutaShell, String pathLocal, String pathRemote) {
		boolean success = false;

		String comando = "";
		  
		if(pathLocal.equals("") && pathRemote.equals("")){
			  comando = rutaShell;
		} else{
			  comando = rutaShell + " " + pathLocal+ "* " + pathRemote;
		}
		
		logger.info("Comando:  " + comando);
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(comando);
		
			//Para validar la ejecuci�n del shell
	/*		String line;
			BufferedReader input = new BufferedReader(new InputStreamReader(r.exec(comando).getErrorStream()));
			LOG.debug("*****");
			while ((line = input.readLine()) != null) {
				LOG.debug(line);
			}
			input.close();
			LOG.debug("*****"); */
			
			success = true;
		} catch(Exception e) {
			success = false;
			logger.error("Ocurrio un error al ejecutar el Shell " + comando + ": ", e);
		}
		return success;
	}
	
	public static String cambiaCaracteresReverse(String texto) {
		
		texto = texto.replaceAll("&#225;", "á");
        texto = texto.replaceAll("&#233;", "é");
        texto = texto.replaceAll("&#237;", "í");
        texto = texto.replaceAll("&#243;", "ó");
        texto = texto.replaceAll("&#250;", "ú");  
        texto = texto.replaceAll("&#193;", "Á");
        texto = texto.replaceAll("&#201;", "É");
        texto = texto.replaceAll("&#205;", "Í");
        texto = texto.replaceAll("&#211;", "Ó");
        texto = texto.replaceAll("&#218;", "Ú");
        texto = texto.replaceAll("&#241;", "ñ");
        texto = texto.replaceAll("&#209;", "Ñ");
        
        texto = texto.replaceAll("&#170;", "ª");          
        texto = texto.replaceAll("&#228;", "ä");
        texto = texto.replaceAll("&#235;", "ë");
        texto = texto.replaceAll("&#239;", "ï");
        texto = texto.replaceAll("&#246;", "ö");
        texto = texto.replaceAll("&#252;", "ü");    
        texto = texto.replaceAll("&#196;", "Ä");
        texto = texto.replaceAll("&#203;", "Ë");
        texto = texto.replaceAll("&#207;", "Ï");
        texto = texto.replaceAll("&#214;", "Ö");
        texto = texto.replaceAll("&#220;", "Ü");
        
        texto = texto.replaceAll("&#191;",  "¿");
        texto = texto.replaceAll("&#8220;", "“");        
        texto = texto.replaceAll("&#8221;", "”");
        texto = texto.replaceAll("&#8216;", "‘");
        texto = texto.replaceAll("&#8217;", "’");
        texto = texto.replaceAll("&#161;", "¡");
        texto = texto.replaceAll("&#191;", "¿");
        texto = texto.replaceAll("&#176;", "°");
        
        texto = texto.replaceAll("&#199;", "Ç");
        texto = texto.replaceAll("&#231;", "ç");
        
        texto = texto.replaceAll("&#8211;", "–");
        texto = texto.replaceAll("&#8212;", "—"); 
        
		return texto;
	}
}
