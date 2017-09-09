package demo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecside.core.ECSideConstants;
import org.ecside.easyda.DataAccessInterceptor;
import org.ecside.easyda.DataAccessModel;
import org.ecside.util.ServletUtils;

public class MyDataAccessModel extends DataAccessModel {

	public void registerInterceptors(){
		addInterceptor(new DataAccessInterceptor("updateUser"){

			public void before(Map parameterMap, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
			}

			public void after(Map parameterMap, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setAttribute(ECSideConstants.C_UPDATE_RESULT_MESSAGE,request.getParameter("USERNAME"));
				ServletUtils.writeDefaultTextToClient(parameterMap, request, response);
			}
			
		});
		
		
		addInterceptor(new DataAccessInterceptor("deleteUser"){

			public void before(Map parameterMap, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
			}

			public void after(Map parameterMap, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setAttribute(ECSideConstants.C_UPDATE_RESULT_MESSAGE,request.getParameter("USERNAME"));
				ServletUtils.writeDefaultTextToClient(parameterMap, request, response);
			}
			
		});
	}
}
