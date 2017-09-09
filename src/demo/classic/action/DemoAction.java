package demo.classic.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ecside.table.limit.FilterSet;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.RequestUtils;
import org.ecside.util.ServletUtils;

import demo.classic.dao.UserInfoDAO;
import demo.common.CommonDictionary;



public class DemoAction extends BaseDispatchAction {

	//默认每页显示的记录条数
	protected static int DEFAULT_PAGE_SIZE = 20;

	
	// 数据库端分页,适合数据量较大的情况
	public ActionForward doQuery(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		UserInfoDAO userInfoDAO=(UserInfoDAO)getBean("userInfoDAO");
		
		
		
		//当列表的分页 过滤 排序等操作是基于数据库时,必须要用到Limit对象.
		// 注意,当页面有多个ec的时候,需要使用带 tableId参数的同名方法.
		//Limit limit=RequestUtils.getLimit(request,"ecGird的Id");
				
		Limit limit=RequestUtils.getLimit(request);
//		 基于数据库的排序.
//		 ECSide会帮助开发人员取得排序的相关信息:当前是按哪个(目前只支持单列排序)column排序的,以及排序的方式desc或asc,
//		 这个信息以key-value方式存放在map里.
//		 但是至于如果处理这些信息(如组装成sql语句),则是由开发人员自己在DAO里完成的.
				Sort sort=limit.getSort();
				Map sortValueMap = sort.getSortValueMap();
				
//		 基于数据库过滤.
//		 ECSide会帮助开发人员取得过滤的相关信息:当前是对哪些column进行过滤,以及过滤的内容是什么,这个信息以key-value方式存放在map里.
//		 但是至于如果处理这些信息(如组装成sql语句),则是由开发人员自己在DAO里完成的.
				FilterSet filterSet =limit.getFilterSet();
				Map filterPropertyMap=filterSet.getPropertyValueMap();
				
// 在本例中, sort 和 filter 相关信息将被传入 DAO,用于拼装sql语句.
// 其实,此时的排序 过滤就和我们以前的传统的查询非常类似:从查询页面取得查询条件,传入DAO.				
				

// RequestUtils.getTotalRowsFromRequest(request);是一个工具类,用来从ECSIDE的列表中取得上次计算出的总行数
// 如果您不希望每次翻页都重新计算总行数,那么建议参考下面几行代码的做法.		
		int totalRows = RequestUtils.getTotalRowsFromRequest(request);
		if (totalRows < 0) {
			// TODO ： userInfoDAO.getUserInfoNumber()为能够取得总行数的方法，请替换为实际的实现。
			// 取得记录总条数时,不要忘了把filter作为参数传入,因为要取得的总行数也是要接受条件限制的.
			totalRows = userInfoDAO.getUserInfoNumber(filterPropertyMap);
		}

		
		// DEFAULT_PAGE_SIZE ==0 时, 每页记录数会使用 properties文件内的默认设置
		// DEFAULT_PAGE_SIZE <0 时, 每页记录数会等于全部记录数	
		limit.setRowAttributes(totalRows, DEFAULT_PAGE_SIZE);
	
		
		//取得当前要查询的页面的记录起止行号
		// offset表示数据编号的起始号. ORACLE数据库一般是从1开始的,HSQLDB是从0开始,默认是从0开始计数,在这里我们使用从0开始.
		int offset=0;
		int[] rowStartEnd =new int[] { limit.getRowStart() + offset, limit.getRowEnd() + offset };
		


		// TODO ：  userInfoDAO.getSomeUserInfo(rowStartEnd[0], rowStartEnd[1])
		// 为查询记录的方法，请替换为实际的实现。rowStartEnd[0], rowStartEnd[1]为起止行
		// rowStartEnd[0], rowStartEnd[1] 左闭 右开
		List rslist = userInfoDAO.getSomeUserInfo(rowStartEnd[0], rowStartEnd[1],sortValueMap,filterPropertyMap);

		request.setAttribute("recordList", rslist);
		
		// 字典数据. 一个Map,存放的是  "编号" 到 "显示的文字" 的映射 
		request.setAttribute("GENDER_MAP", CommonDictionary.GENDER);
		request.setAttribute("USERROLE_MAP", CommonDictionary.USERROLE);
		
		return mapping.findForward("listPage");

	}

	
	
	
	public ActionForward doAjaxInsertUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserInfoDAO userInfoDAO=(UserInfoDAO)getBean("userInfoDAO");
		List userInfoList=ServletUtils.getParameterMaps(request);
		int[] results=userInfoDAO.doInsertUsers(userInfoList);
		ServletUtils.defaultAjaxResopnse(userInfoList,results,request,response);
		return null;
	}

	public ActionForward doAjaxUpdateUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserInfoDAO userInfoDAO=(UserInfoDAO)getBean("userInfoDAO");
		List userInfoList=ServletUtils.getParameterMaps(request);
		int[] results=userInfoDAO.doUpdateUsers(userInfoList);
		ServletUtils.defaultAjaxResopnse(userInfoList,results,request,response);
		return null;
	}

	public ActionForward doAjaxDeleteUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserInfoDAO userInfoDAO=(UserInfoDAO)getBean("userInfoDAO");
		List userInfoList=ServletUtils.getParameterMaps(request);
		int[] results=userInfoDAO.doDeleteUsers(userInfoList);
		ServletUtils.defaultAjaxResopnse(userInfoList,results,request,response);
		return null;
	}
	
	

    
	public ActionForward defaultMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return doQuery(mapping, form, request, response);
//		response.setContentType("text/html;charset=GBK");
//		PrintWriter out=response.getWriter();
//		out.println("没有指明欲调用的 DispatchAction 方法");
//		return null;
	}
}
