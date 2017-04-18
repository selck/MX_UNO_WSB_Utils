package mx.com.amx.uno.util.bo.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import mx.com.amx.uno.util.bo.IUtilBO;
import mx.com.amx.uno.util.dto.ParametroDTO;
import mx.com.amx.uno.util.dto.ParametrosDTO;
import mx.com.amx.uno.util.exception.UtilBOException;
import mx.com.amx.uno.util.utils.Utileria;


@Component
@Qualifier("utilBoImpl")
public class UtilBOImpl implements IUtilBO {
	
	private static Logger logger = Logger.getLogger(UtilBOImpl.class);
	private final Properties props = new Properties();
	
	String URL_WS_BASE = "";

	private RestTemplate restTemplate;
	HttpHeaders headers = new HttpHeaders();

	
	public UtilBOImpl() {
		super();
		restTemplate = new RestTemplate();
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
		
		if ( factory instanceof SimpleClientHttpRequestFactory) {
			((SimpleClientHttpRequestFactory) factory).setConnectTimeout( 35 * 1000 );
			((SimpleClientHttpRequestFactory) factory).setReadTimeout( 35 * 1000 );
		} else if ( factory instanceof HttpComponentsClientHttpRequestFactory) {
			((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout( 35 * 1000);
			((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout( 35 * 1000);
		}
		
		restTemplate.setRequestFactory( factory );
		headers.setContentType(MediaType.APPLICATION_JSON);
	      
		try {
			props.load( this.getClass().getResourceAsStream( "/general.properties" ) );						
		} catch(Exception e) {
			logger.error("[ConsumeWS:init]Error al iniciar y cargar arhivo de propiedades." + e.getMessage());
		}		
		URL_WS_BASE = props.getProperty(props.getProperty( "ambiente" )+".urlws");
	}

	@Override
	public boolean saveOrUpdateParameter(String idParameter, String value) throws UtilBOException {
		boolean success = false;
		try {	
			String URL_WS=URL_WS_BASE+"saveOrUpdateParameter";
			restTemplate=new RestTemplate();
			MultiValueMap<String, Object> parts;
			parts = new LinkedMultiValueMap<String, Object>();
			parts.add("idParameter", idParameter);
			parts.add("value", value);
			success=restTemplate.postForObject(URL_WS, parts, Boolean.class);
				
		} catch(Exception e) {
			logger.error("Error saveOrUpdateParameter [BO]: ",e);
		}		
		return success;
	}

	@Override
	public String getParameter(String idParameter) throws UtilBOException {
		String respuesta="";
		try {	
			String URL_WS=URL_WS_BASE+"getParameter";
			HttpEntity<String> entity = new HttpEntity<String>( idParameter );
			respuesta=restTemplate.postForObject(URL_WS, entity, String.class);
				
		} catch(Exception e) {
			logger.error("Error getParameter [BO]: ",e);
		}		
		return respuesta;
	}

	@Override
	public boolean saveJSONWeb02(String json, String nameJson) throws UtilBOException {
		boolean respuesta=false;
		File file;
		try {
			ParametrosDTO parametrosDTO=Utileria.getProperties();
			JSONObject jsonObject=new JSONObject(Utileria.cambiaCaracteresReverse(json));
			
			logger.debug("Json Recibido: "+jsonObject.toString());
			logger.debug("nameJson: "+nameJson);
			
			file = new File(parametrosDTO.getPathSaveJsonWeb02() + nameJson+".json");
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "UTF8"));
			out.append(jsonObject.toString());
			out.flush();
			out.close();
			respuesta=true;
			if(parametrosDTO.getAmbiente().equals("desarrollo")){
				Utileria.transfiereWebServer(parametrosDTO.getPathShell(), parametrosDTO.getPathSaveJsonWeb02() , "/var/www/unotv/json/");
			}
			/*FileWriter fw = new FileWriter(parametrosDTO.getPathSaveJsonWeb02() + nameJson+".json");
			fw.write(jsonObject.toString());
			fw.flush();
			fw.close();*/
		} catch (Exception e) {
			logger.error("Error saveJSONWeb02 [BO]: ",e);
		}
		
		return respuesta;
	}

	@Override
	public ArrayList<ParametroDTO> getParameters() throws UtilBOException {
		ArrayList<ParametroDTO> listParams = null;
		try {	
			
			String URL_WS=URL_WS_BASE+"getParameters";
			ParametroDTO[] arrayParametros=restTemplate.getForObject(URL_WS, ParametroDTO[].class);
			listParams=new ArrayList<ParametroDTO>(Arrays.asList(arrayParametros));
			
		} catch(Exception e) {
			logger.error("Error getParameters [BO]: ",e);
		}		
		return listParams;
	}

	@Override
	public boolean sendEmail(String subject, String bodyMsg,
			String[] recipients, String[] recipientesCC, String smtpsender) throws UtilBOException {
		
		ParametrosDTO parametrosDTO=Utileria.getProperties();
		Properties props = new Properties();
		
		String smtpserver=parametrosDTO.getSmtpserver();
		//String smtpsender=parametrosDTO.getSmtpsender();
		String smtpAutenUserName=parametrosDTO.getSmtpAutenUserName();
		String smtpAutenPassword=parametrosDTO.getSmtpAutenPassword();
		String from = smtpsender;
	    
	    boolean salida=false;
	    String to[] =recipients;
	    String mensajeOrigen=bodyMsg;
	    
	    for(String quien:to){
	    	logger.debug("to        : "+quien);
	    }
	    
		String protocol = "smtp";
	    props.put("mail.smtp.host", smtpserver);
	    props.put("mail.from", from);
	    props.put("mail." + protocol + ".auth", "true");
	    
	    Session session = Session.getInstance(props, null);
	    
	    try {
	        MimeMessage msg = new MimeMessage(session);
	        msg.setFrom();
	        //msg.setSender(new InternetAddress("contacto@heroesporlavida.org"));
	        InternetAddress[] addressTo = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++){
                addressTo[i] = new InternetAddress(to[i]);
            }
            
            InternetAddress[] recipientesCCArray=null;
            
            if(recipientesCC!=null && recipientesCC.length>0){
            	recipientesCCArray= new InternetAddress[recipientesCC.length];
                for (int i = 0; i < recipientesCC.length; i++){
                	recipientesCCArray[i] = new InternetAddress(recipientesCC[i]);
                }
            }
            
	        msg.setRecipients(Message.RecipientType.TO,addressTo);
	        if(recipientesCC!=null && recipientesCC.length>0){
	        	msg.setRecipients(Message.RecipientType.CC, recipientesCCArray);
	        }
	        msg.setSubject(subject,"ISO-8859-1");
	        msg.setSentDate(new Date());
	        msg.setContent(mensajeOrigen, "text/html");
	        
	        // Create the message part 
	         //BodyPart messageBodyPart = new MimeBodyPart();
	         // Fill the message
	         //messageBodyPart.setContent(mensajeOrigen, "text/html");
	         // Create a multipar message
	         //Multipart multipart = new MimeMultipart();
	         // Set text message part
	         //multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         //MimeBodyPart attachPart = new MimeBodyPart();
	         //String attachFile = filePathPDF;
	         //attachPart.attachFile(attachFile);
	         //multipart.addBodyPart(attachPart);

	         // Send the complete message parts
	         //msg.setContent(multipart );
	        Transport t = session.getTransport(protocol);
	        t.connect(smtpserver,smtpAutenUserName, smtpAutenPassword);
	        t.sendMessage(msg, msg.getAllRecipients());
	        salida=true;
	    } catch (MessagingException mex) {
	    	logger.error("Error sendMail: ",mex);
	    } 
	    return salida;

	}

}
