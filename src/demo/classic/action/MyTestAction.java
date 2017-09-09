package demo.classic.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.ecside.util.RequestUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import demo.classic.dao.TestDAO;
import demo.classic.dao.UserInfoDAO;
import demo.common.CommonDictionary;



public class MyTestAction extends DispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		return doDirectExportXLS(mapping, form, request, response);
	}
	
	private static int DEFAULT_PAGE_SIZE = 15;
	// 数据库端分页
	public ActionForward doQuery(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		int num=Integer.parseInt(request.getParameter("num"));
		
		TestDAO testDAO=(TestDAO)getBean("testDAO");
		int totalRows = RequestUtils.getTotalRowsFromRequest(request);
		if (totalRows < 0) {
			totalRows = num;
		}

		int[] rowStartEnd = RequestUtils.getRowStartEnd(request, totalRows,DEFAULT_PAGE_SIZE);

		List rslist = testDAO.getSomeUserInfo(num,rowStartEnd[0], rowStartEnd[1]);

		request.setAttribute("recordList", rslist);
		
		request.setAttribute("GENDER_MAP", CommonDictionary.GENDER);
		request.setAttribute("USERROLE_MAP", CommonDictionary.USERROLE);
		
		return new ActionForward("/tdt.jsp");

	}
	
	
	// 直接导出XLS
	public ActionForward doDirectExportXLS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		String exportFileName="text.xls";
		
		try{
			
			int num=Integer.parseInt(request.getParameter("num"));

		
		String mimeType = "application/vnd.ms-excel";

		if (StringUtils.isNotBlank(mimeType)) {
			response.setContentType(mimeType);
		}
		response.setHeader("Content-Disposition", "attachment;filename=\"" + exportFileName + "\"");
		response.setHeader("Content-Transfer-Encoding","binary");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
		
		TestDAO testDAO=(TestDAO)getBean("testDAO");
		
		String methodName=request.getParameter("mn");
		
		switch (methodName.charAt(0)){


		case '1':
			testDAO.getAllUserInfo1(num,response.getOutputStream() );
			break;
		case '2':
			testDAO.getAllUserInfo2(num,response.getOutputStream() );
			break;
		case '3':
			testDAO.getAllUserInfo3(num,response.getOutputStream() );
			break;
		case '4':
			testDAO.getAllUserInfo4(num,response.getOutputStream() );
			break;
		case 'c':
			testDAO.getAllUserInfoCSV(num,response.getOutputStream() );
			break;
		default:
			testDAO.getAllUserInfo(num,response.getOutputStream() );		
		}
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		}catch(Throwable e){
			
		}
		return null;
	}
	
	
	
	public ActionForward doDirectExportCSV(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		String exportFileName="text.csv";
		
		try{
			
			int num=Integer.parseInt(request.getParameter("num"));

			response.setBufferSize(2048);
		
		String mimeType = "text/csv";

		if (StringUtils.isNotBlank(mimeType)) {
			response.setContentType(mimeType);
		}
		response.setHeader("Content-Disposition", "attachment;filename=\"" + exportFileName + "\"");
		response.setHeader("Content-Transfer-Encoding","binary");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
		
		response.flushBuffer();
		
		TestDAO testDAO=(TestDAO)getBean("testDAO");
		
		String methodName=request.getParameter("mn");
		
		switch (methodName.charAt(0)){


		case '1':
			testDAO.getAllUserInfoCSV(num,response.getOutputStream() );
			break;
		default:
			testDAO.getAllUserInfoCSV(num,response.getOutputStream() );		
		}
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		}catch(Throwable e){
			e.printStackTrace();
		}
		return null;
	}

//	// 数据库端分页
//	public ActionForward doQuery(ActionMapping mapping, ActionForm form,
//	HttpServletRequest request, HttpServletResponse response)
//	throws Exception {
//		UserInfoDAO userInfoDAO=(UserInfoDAO)getBean("userInfoDAO");
//		int totalRows = RequestUtil.getTotalRowsFromRequest(request);
//		if (totalRows < 0) {
//			// TODO ： userInfoDAO.getAllUserInfoNumber()为能够取得总行数的方法，请替换为实际的实现。
//			totalRows = userInfoDAO.getAllUserInfoNumber();
//		}
//		// 取得当前要查询的页面的记录起止行号。
//		// 也可以使用 getRowStartEnd(HttpServletRequest request, int totalRows,int defautPageSize,int offset)
//		// 下面这个方法从0开始计算行数 上面的方法可以指定从几开始 ORACLE数据库一般是从1开始的,HSQLDB是从0开始.
//		int[] rowStartEnd = RequestUtil.getRowStartEnd(request, totalRows,DEFAULT_PAGE_SIZE);
//		
////		Limit limit=RequestUtil.getLimit(request, "ec", totalRows, DEFAULT_PAGE_SIZE);
////		Sort sort=limit.getSort();
////		sort.getProperty();
////		sort.getSortOrder();
////		
//
//		// TODO ：  userInfoDAO.getAllUserInfo(rowStartEnd[0], rowStartEnd[1])
//		// 为查询记录的方法，请替换为实际的实现。rowStartEnd[0], rowStartEnd[1]为起止行
//		// rowStartEnd[0], rowStartEnd[1] 左闭 右开
//		List rslist = userInfoDAO.getSomeUserInfo(rowStartEnd[0], rowStartEnd[1]);
//
//		request.setAttribute("recordList", rslist);
//		
//		request.setAttribute("GENDER_MAP", CommonDictionary.GENDER);
//		request.setAttribute("USERROLE_MAP", CommonDictionary.USERROLE);
//		
//		return mapping.findForward("listPage");
//
//	}

    public Object getBean(String beanName){
    	Object bean=null;
    	bean=WebApplicationContextUtils.getRequiredWebApplicationContext(getServlet().getServletContext()).getBean(beanName);
    	return bean;
    }

}
