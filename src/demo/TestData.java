package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.support.WebApplicationContextUtils;

import demo.classic.dao.UserInfoDAO;

public class TestData {
	
	
    public static Object getBean(HttpServletRequest request,String beanName){
    	Object bean=null;
    	try{
    		bean=WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext()).getBean(beanName);
    	}catch(Exception e){
    		return null;
    	}
    	return bean;
    }
    
	public static List getTestData(HttpServletRequest request){
		List records =null;
		
		UserInfoDAO dao;
		dao=(UserInfoDAO)getBean(request,"userInfoDAO");
		if (dao==null){
			dao=new UserInfoDAO();
		}
		records=dao.getAllUserInfo();
		
		return records;
	}
	
	public static Set getTestDataSet(HttpServletRequest request){
		Set records =null;
		
		UserInfoDAO dao;
		dao=(UserInfoDAO)getBean(request,"userInfoDAO");
		if (dao==null){
			dao=new UserInfoDAO();
		}
		records=dao.getAllUserInfoSet();
		
		return records;
	}
	
	public static List getTestDate(int rowc ){

		List presidents =new ArrayList();
		for (int i=0;i<rowc/6;i++){

			java.util.Map president = new java.util.HashMap(); 
			president.put("name", "George Washington"+"_"+i); 
			president.put("nickname", "Father of His Country"+"_"+i);  
			president.put("term", "1789-1797"); 
			president.put("no", ""+i); 
			president.put("no2", "2"+i);
			president.put("date", "2007-01-30 11:43:12" );
			presidents.add(president); 

			president = new java.util.HashMap(); 
			president.put("name", "John Adams"+"_"+i); 
			president.put("nickname", "Atlas of Independence"+"_"+i); 
			president.put("term", "1797-1801"); 
			president.put("no", ""+i); 
			president.put("no2", "2"+i);
			president.put("date", "2007-01-30 11:43:12" );
			presidents.add(president); 

			president = new java.util.HashMap(); 
			president.put("name", "Thomas Jefferson"+"_"+i); 
			president.put("nickname", "Man of the People, Sage of Monticello"+"_"+i);  
			president.put("term", "1801-09"); 
			president.put("no", "2342.1213"); 
			president.put("no2", "2"+i);
			president.put("date", "2007-01-30 11:43:12" );
			presidents.add(president); 

			president = new java.util.HashMap(); 
			president.put("name", "James Madison"+"_"+i); 
			president.put("nickname", "Father of the Constitution"+"_"+i); 
			president.put("term", "1809-17"); 
			president.put("no", "123"+i); 
			president.put("no2", "2"+i);
			president.put("date", "2007-01-30 11:43:12" );
			presidents.add(president); 

			president = new java.util.HashMap(); 
			president.put("name", "James Monroe"+"_"+i); 
			president.put("nickname", "The Last Cocked Hat, Era-of-Good-Feelings President"+"_"+i);  
			president.put("term", "1817-25"); 
			president.put("no", ""+i); 
			president.put("no2", "2"+i);
			president.put("date", "2007-01-30 11:43:12" );
			presidents.add(president); 

			president = new java.util.HashMap(); 
			president.put("name", "John Adams"); 
			president.put("nickname", "Old Man Eloquent"+"_"+i); 
			president.put("term", "1825-29"); 
			president.put("no", ""+i); 
			president.put("no2", "2"+i);
			president.put("date", "2007-01-30 11:43:12" );
			presidents.add(president); 
			
		}
		
		return presidents;

		
	}
}
