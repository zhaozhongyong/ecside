package demo.classic.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ecside.common.QueryResult;
import org.ecside.core.TableConstants;
import org.ecside.resource.MimeUtils;
import org.ecside.util.RequestUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import demo.classic.dao.TestDAO;
import demo.classic.dao.UserInfoDAO;
import demo.common.CommonDictionary;



public class DemoPAction extends BaseDispatchAction {

	//默认每页显示的记录条数
	protected static int DEFAULT_PAGE_SIZE = 20;

	
	
	
	
	
	// 应用服务器端分页,适合数据量不大的情况
	// 此时排序 过滤全部使用ECSide内置的缺省方式.
	public ActionForward doQuery2(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		UserInfoDAO UserInfoDAO=(UserInfoDAO)getBean("UserInfoDAO");


		List rslist = UserInfoDAO.getAllUserInfo();
		
		int totalRows=rslist!=null?rslist.size():0;
		
		//RequestUtil.setTotalRows: 总行数写入request.
		//如果ec:table的tableId不是ec或者不是默认的,那么请使用下面这个方法:
		//RequestUtil.initLimit(request, "ec:table的tableId" ,totalRows,DEFAULT_PAGE_SIZE);
		// DEFAULT_PAGE_SIZE ==0 时, 每页记录数会使用 properties文件内的默认设置
		// DEFAULT_PAGE_SIZE ==-1 时, 每页记录数会等于全部记录数
		RequestUtils.initLimit(request, totalRows,DEFAULT_PAGE_SIZE);
		
		request.setAttribute("recordList", rslist);
		
		request.setAttribute("GENDER_MAP", CommonDictionary.GENDER);
		request.setAttribute("USERROLE_MAP", CommonDictionary.USERROLE);
		
		return mapping.findForward("listPage");

	}
	
	
	
	
	
	
	// 直接导出
//	public ActionForward doDirectExport(ActionMapping mapping, ActionForm form,
//	HttpServletRequest request, HttpServletResponse response)
//	throws Exception {
//		UserInfoDAO UserInfoDAO=(UserInfoDAO)getBean("UserInfoDAO");
//
//		Map parameterMap=new HashMap();
//		Map mappingItems=new HashMap();
//		mappingItems.put("GENDER", CommonDictionary.GENDER);
//		mappingItems.put("USERROLE", CommonDictionary.USERROLE);
//		
//		RequestUtils.beforeExport(request, response);
//		
//		UserInfoDAO.exportUserListText(parameterMap, mappingItems, response.getOutputStream());
//		
//		RequestUtils.afterExport(request, response);
//		
//		return null;
//
//	}
	
	
	// 直接导出
	public ActionForward doDirectExportXLS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		String exportFileName="text.xls";
		
		try{
			

		int num=Integer.parseInt(request.getParameter("num"));
		
		String mimeType = MimeUtils.getFileMimeType(exportFileName);

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
		
		if ("1".equals(methodName) ){
			testDAO.getAllUserInfo1(num,response.getOutputStream() );
		}else if("2".equals(methodName) ){
			testDAO.getAllUserInfo2(num,response.getOutputStream() );
		}else if("3".equals(methodName) ){
			testDAO.getAllUserInfo2(num,response.getOutputStream() );
		}else{
			testDAO.getAllUserInfo(num,response.getOutputStream() );
		}
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		}catch(Throwable e){
			
		}
		return null;
	}
	
	
	
	
	// 直接输出ECSideTable,将QueryResult传入request,交给ecside处理
	public ActionForward doDirectTable(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		UserInfoDAO UserInfoDAO=(UserInfoDAO)getBean("UserInfoDAO");
		
		QueryResult queryResult=UserInfoDAO.getUserInfoQueryResult();


		int totalRows = RequestUtils.getTotalRowsFromRequest(request);
		if (totalRows < 0) {
			// TODO ： UserInfoDAO.getAllUserInfoNumber()为能够取得总行数的方法，请替换为实际的实现。
			totalRows = UserInfoDAO.getUserInfoNumber(null);
		}

		
		//RequestUtil.setTotalRows: 总行数写入request.
		//如果ec:table的tableId不是ec或者不是默认的,那么请使用下面这个方法:
		//RequestUtil.initLimit(request, "ec:table的tableId" ,totalRows,DEFAULT_PAGE_SIZE);
		
		RequestUtils.initLimit(request, totalRows,totalRows);
		
		request.setAttribute("recordList", queryResult);
		request.setAttribute("GENDER_MAP", CommonDictionary.GENDER);
		request.setAttribute("USERROLE_MAP", CommonDictionary.USERROLE);
		
		return mapping.findForward("listPage");
	}
	
	

	

//	
//	public ActionForward doAjaxUpdate(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//				Map formMap=request.getParameterMap();
//				UserInfoDAO UserInfoDAO=(UserInfoDAO)getBean("UserInfoDAO");
//				int opresult=UserInfoDAO.doUpdateUserInfo(formMap);
//				response.setContentType("text/html");
//				PrintWriter out=response.getWriter();
//				out.println(opresult);
//				out.println(((String[])formMap.get(TableConstants.RECORDKEY_NAME))[0]);
//				out.print(((String[])formMap.get("USERNAME"))[0]);
//				out.flush();
//				out.close();				
//				return null;
//
//	}
	

    public Object getBean(String beanName){
    	Object bean=null;
    	bean=WebApplicationContextUtils.getRequiredWebApplicationContext(getServlet().getServletContext()).getBean(beanName);
    	return bean;
    }
    
	public ActionForward defaultMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return doQuery2(mapping, form, request, response);
//		response.setContentType("text/html;charset=GBK");
//		PrintWriter out=response.getWriter();
//		out.println("没有指明欲调用的 DispatchAction 方法");
//		return null;
	}
}
