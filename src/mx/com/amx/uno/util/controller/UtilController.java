package mx.com.amx.uno.util.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.com.amx.uno.util.bo.IUtilBO;
import mx.com.amx.uno.util.dto.ParametroDTO;

@Component
@RequestMapping("utilController")
public class UtilController {
	
	private Logger logger=Logger.getLogger(UtilController.class);
	private IUtilBO utilBOImpl;

	@RequestMapping(value={"getParameter"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	String existParameter(@RequestBody String idParameter, HttpServletResponse response){
		logger.info("getParameter [Controller]");
		String msj="OK";
		String codigo="0";
		String causa_error="";
		int status_peticion=HttpServletResponse.SC_OK;
		String res="";
		try {
			res=utilBOImpl.getParameter(idParameter);
		} catch (Exception e) {
			logger.error("Error getParameter [Controller]: ",e);
			codigo="-1";
			msj=e.getMessage();
			causa_error=e.toString();
			status_peticion=HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		response.setHeader("codigo", codigo);
		response.setHeader("mensaje", msj);
		response.setHeader("causa_error", causa_error);
		response.setStatus(status_peticion);
		return res;
	}
	
	@RequestMapping(value={"saveOrUpdateParameter"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	Boolean saveOrUpdateParameter(@RequestParam("idParameter") String idParameter, @RequestParam("value") String value, HttpServletResponse response){
		logger.info("saveOrUpdateParameter [Controller]");
		String msj="OK";
		String codigo="0";
		String causa_error="";
		int status_peticion=HttpServletResponse.SC_OK;
		//String res="";
		Boolean res=false;
		try {
			res=utilBOImpl.saveOrUpdateParameter(idParameter, value);
		} catch (Exception e) {
			logger.error("Error saveOrUpdateParameter [Controller]: ",e);
			codigo="-1";
			msj=e.getMessage();
			causa_error=e.toString();
			status_peticion=HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		response.setHeader("codigo", codigo);
		response.setHeader("mensaje", msj);
		response.setHeader("causa_error", causa_error);
		response.setStatus(status_peticion);
		return res;
	}
	
	@RequestMapping(value={"saveJSONWeb02"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	Boolean saveJSONWeb02(@RequestParam("json") String json, @RequestParam("nameJson") String nameJson, HttpServletResponse response){
		logger.info("saveJSONWeb02 [Controller]");
		String msj="OK";
		String codigo="0";
		String causa_error="";
		int status_peticion=HttpServletResponse.SC_OK;
		Boolean res=false;
		try {
			res=utilBOImpl.saveJSONWeb02(json, nameJson);
		} catch (Exception e) {
			logger.error("Error saveJSONWeb02 [Controller]: ",e);
			codigo="-1";
			msj=e.getMessage();
			causa_error=e.toString();
			status_peticion=HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		response.setHeader("codigo", codigo);
		response.setHeader("mensaje", msj);
		response.setHeader("causa_error", causa_error);
		response.setStatus(status_peticion);
		return res;
	}
	
	@RequestMapping(value={"getParameters"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, headers={"Accept=application/json"})
	@ResponseBody
	ArrayList<ParametroDTO> getParameters(HttpServletResponse response){
		logger.info("getParameters [Controller]");
		String msj="OK";
		String codigo="0";
		String causa_error="";
		int status_peticion=HttpServletResponse.SC_OK;
		ArrayList<ParametroDTO> res=new ArrayList<ParametroDTO>();
		try {
			res=utilBOImpl.getParameters();
		} catch (Exception e) {
			logger.error("Error getParameters [Controller]: ",e);
			codigo="-1";
			msj=e.getMessage();
			causa_error=e.toString();
			status_peticion=HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		response.setHeader("codigo", codigo);
		response.setHeader("mensaje", msj);
		response.setHeader("causa_error", causa_error);
		response.setStatus(status_peticion);
		return res;
	}
	
	@RequestMapping(value={"sendEmail"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	Boolean sendEmail(@RequestParam("subject") String subject, @RequestParam("bodyMsg")String bodyMsg,
			@RequestParam("recipients")String[] recipients, @RequestParam("recipientsCC")String[] recipientesCC, 
			@RequestParam("smtpsender")String smtpsender,HttpServletResponse response) {
		logger.info("sendEmail [Controller]");
		String msj="OK";
		String codigo="0";
		String causa_error="";
		int status_peticion=HttpServletResponse.SC_OK;
		Boolean res=false;
		try {
			res=utilBOImpl.sendEmail(subject, bodyMsg, recipients, recipientesCC, smtpsender);
		} catch (Exception e) {
			logger.error("Error sendEmail [Controller]: ",e);
			codigo="-1";
			msj=e.getMessage();
			causa_error=e.toString();
			status_peticion=HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		response.setHeader("codigo", codigo);
		response.setHeader("mensaje", msj);
		response.setHeader("causa_error", causa_error);
		response.setStatus(status_peticion);
		return res;
	}
	/**
	 * @return the utilBOImpl
	 */
	public IUtilBO getUtilBOImpl() {
		return utilBOImpl;
	}

	/**
	 * @param utilBOImpl the utilBOImpl to set
	 */
	@Autowired
	public void setUtilBOImpl(IUtilBO utilBOImpl) {
		this.utilBOImpl = utilBOImpl;
	}
	
	
}
