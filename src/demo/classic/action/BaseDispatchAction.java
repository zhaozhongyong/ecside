package demo.classic.action;

import org.apache.struts.actions.DispatchAction;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseDispatchAction extends DispatchAction {
	

    public Object getBean(String beanName){
    	Object bean=null;
    	bean=WebApplicationContextUtils.getRequiredWebApplicationContext(getServlet().getServletContext()).getBean(beanName);
    	return bean;
    }

    
}
