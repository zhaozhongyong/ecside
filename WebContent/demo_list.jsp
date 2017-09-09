<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.ecside.org" prefix="ec" %>
<%@ page import="demo.TestData"  %>
<%@ page import="demo.common.CommonDictionary"  %>
<%
String webapp=request.getContextPath();

request.setAttribute("GENDER_MAP", CommonDictionary.GENDER);
request.setAttribute("USERROLE_MAP", CommonDictionary.USERROLE);
%>

<html>

<head>
<script type="text/javascript">
<!--
	if ((""+window.location).indexOf("easyList=userInfoList")<1){
		window.location.replace("demo_list.jsp?easyList=userInfoList");
	}
//-->
</script>

<jsp:include page="common_head.jsp" flush="true" />

</head>


<body style="margin:10px;" >

<ec:table items="recordList" var="record" retrieveRowsCallback="limit" 
action="${pageContext.request.contextPath}/demo_list.jsp?easyList=userInfoList"
title="用户列表" 

xlsFileName="用户列表.xls" 
csvFileName="用户列表.csv"

width="100%" 
listWidth="100%" 
height="280px" 

sortable="true"
>
<ec:row>
	<ec:column width="50" property="_0" title="序号" value="${GLOBALROWCOUNT}" />
	<ec:column width="80" property="USERROLE" title="角色"  mappingItem="USERROLE_MAP" />
	<ec:column width="120" property="USERNAME" title="用户名" />
	<ec:column width="100" property="PASSWD" title="密码"  />
	<ec:column width="120" property="EMAIL" title="电子信箱" />
	<ec:column width="70" property="GENDER" title="性别"  mappingItem="GENDER_MAP" />
	<ec:column width="70" property="USERID" title="编号" cell="number" />
</ec:row>
</ec:table>
	

</body>


</html>