package demo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.ECSideConstants;
import org.ecside.easylist.DefaultEasyListModel;
import org.ecside.util.ServletUtils;

import demo.common.CommonDictionary;

public class MyEasyListDemo extends DefaultEasyListModel {


	public void beforeSelect(Map parameterMap,HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException  {
		String name=(String)parameterMap.get("USERNAME");
		if (StringUtils.isNotBlank(name)){
			parameterMap.put("USERNAME","%"+name+"%");
		}
		
	}
	
	public void afterSelect(Map parameterMap,HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		request.setAttribute("GENDER_MAP", CommonDictionary.GENDER);
		request.setAttribute("USERROLE_MAP", CommonDictionary.USERROLE);
		
//		DefaultEasyListModel delm=TDExportFilter.getBean(applicationName, beanName);
//		List rs=delm.executeQuery("sqlSelect", parameterMap);
//		
//		request.setAttribute("CONFIRM_INFO", rs.get(0));
	}

	public void afterUpdate(Map parameterMap,HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		request.setAttribute(ECSideConstants.C_UPDATE_RESULT_MESSAGE,request.getParameter("USERNAME"));
		ServletUtils.writeDefaultTextToClient(parameterMap, request, response);
	}

	public void afterInsert(Map parameterMap,HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		request.setAttribute(ECSideConstants.C_UPDATE_RESULT_MESSAGE,request.getParameter("USERNAME"));
		ServletUtils.writeDefaultTextToClient(parameterMap, request, response);
	}
	
	public void afterDelete(Map parameterMap,HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		request.setAttribute(ECSideConstants.C_UPDATE_RESULT_MESSAGE,request.getParameter("USERNAME"));
		ServletUtils.writeDefaultTextToClient(parameterMap, request, response);
	}
	
}
