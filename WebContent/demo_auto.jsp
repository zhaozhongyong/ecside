<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.ecside.org" prefix="ec" %>
<%@ page import="demo.TestData"  %>
<%@ page import="demo.common.CommonDictionary"  %>
<%

String webapp=request.getContextPath();
// begin: 下面这几步通常在action/servlet里完成,这个例子偷懒了,直接取了 :) 





// 动态的生成  columnTitles  columnWidths columnPropertys 就可以动态的生成列表的各个列
String columnTitles="编号,用户名,密码,EMAIL";
String columnWidths="10%,30%,30%,30%";

String[] columnPropertys=new String[]{"USERID","USERNAME","PASSWD","EMAIL"};
// "FIELD_NAMES" 是关键字 不能改变 ,如果想使用不同的 
// 你可以自己编写 org.ecside.core.bean.AutoGenerateColumnsImpl实现 
request.setAttribute("FIELD_NAMES",columnPropertys);


request.setAttribute("records", TestData.getTestData(request));

request.setAttribute("GENDER_MAP", CommonDictionary.GENDER);
request.setAttribute("USERROLE_MAP", CommonDictionary.USERROLE);
// end.

%>
<html>

<head>
<jsp:include page="common_head.jsp" flush="true" />
</head>


<body style="margin:10px;" >


<ec:table items="records" var="record" retrieveRowsCallback="process" 
action="${pageContext.request.contextPath}/demo_auto.jsp" 
title="用户列表" 
width="100%" 

listWidth="100%" 

height="280px" 
>
<ec:row >
    <ec:columns autoGenerateColumns="org.ecside.core.bean.AutoGenerateColumnsImpl" 
	titles="<%=columnTitles %>" widths="<%=columnWidths %>" />
</ec:row>
</ec:table>


</body>


</html>

