package mx.com.amx.uno.util.bo;

import java.util.ArrayList;

import mx.com.amx.uno.util.dto.ParametroDTO;
import mx.com.amx.uno.util.exception.UtilBOException;

public interface IUtilBO {
	/*
	 * Método que devuelve un booleano true para confirmar que se guardo el parametro
	 *@author: Jesús Vicuña 
	 * @version: 1.0
	 * @return: Boolean
	 * 
	*/
	boolean saveOrUpdateParameter(String idParameter, String value) throws UtilBOException;
	
	/*
	 * Método que devuelve el valor de un id parametro
	 *@author: Jesús Vicuña 
	 * @version: 1.0
	 * @return: String
	 * 
	*/
	String getParameter(String idParameter) throws UtilBOException;
	
	/*
	 * Método que devuelve un booleano true para confirmar que se guardo el JSON en el WEB02
	 *@author: Jesús Vicuña 
	 * @version: 1.0
	 * @return: Boolean
	 * 
	*/
	boolean saveJSONWeb02(String json, String nameJson) throws UtilBOException;
	/*
	 * Método que devuelve una lista de Parametros a mostrar en el backOffice
	 *@author: Jesús Vicuña 
	 * @version: 1.0
	 * @return: List<ParametroDTO> 
	 * 
	*/
	ArrayList<ParametroDTO> getParameters() throws UtilBOException;
}
