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
	if ((""+window.location).indexOf("actionMethod=doQuery")<1){
		window.location.replace("demo.do?actionMethod=doQuery");
	}
//-->
</script>

<jsp:include page="common_head.jsp" flush="true" />

</head>


<body style="margin:10px;" >

<ec:table items="recordList" var="record" 
action="${pageContext.request.contextPath}/demo.do?actionMethod=doQuery"


updateAction="${pageContext.request.contextPath}/demo.do?actionMethod=doAjaxUpdateUsers" 
insertAction="${pageContext.request.contextPath}/demo.do?actionMethod=doAjaxInsertUsers" 
deleteAction="${pageContext.request.contextPath}/demo.do?actionMethod=doAjaxDeleteUsers" 

batchUpdate="true" 
editable="true" 

retrieveRowsCallback="limit" 
sortRowsCallback="limit" 
filterRowsCallback="limit"


title="用户列表" 

xlsFileName="用户列表.xls" 
csvFileName="用户列表.csv"

width="100%" 
listWidth="100%" 
height="280px" 



sortable="true" 
filterable="true" 

style="table-layout:fixed;"
>
<ec:row recordKey="${record.USERID}">
	<ec:column width="50" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false"  editable="false" />
	<ec:column width="80" property="USERROLE" title="角色"  mappingItem="USERROLE_MAP" editTemplate="ecs_t_role" />
	<ec:column width="120" property="USERNAME" title="用户名"  editable="false" />
	<ec:column width="100" property="PASSWD" title="密码"  filterable="false"  />
	<ec:column width="120" property="EMAIL" title="电子信箱" filterable="false"  />
	<ec:column width="70" property="GENDER" title="性别"  mappingItem="GENDER_MAP" editTemplate="ecs_t_gender" />
</ec:row>
</ec:table>


<!-- 编辑和过滤所使用的 通用的文本框模板 -->
<textarea id="ecs_t_input" rows="" cols="" style="display:none">
	<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
	 style="width:100%;" name="" />
</textarea>

<!-- 编辑性别所用模板 -->
<textarea id="ecs_t_gender" rows="" cols="" style="display:none" >
	<select onblur="ECSideUtil.updateEditCell(this)"
	style="width:100%;" name="GENDER" >
<ec:options items="GENDER_MAP" />
	</select>
</textarea>

<!-- 编辑角色所用模板 -->
<textarea id="ecs_t_role" rows="" cols="" style="display:none" >
	<select onblur="ECSideUtil.updateEditCell(this)"
	style="width:100%;" name="USERROLE" >
<ec:options items="USERROLE_MAP" />
	</select>
</textarea>


<!-- 新建记录所用模板 -->
<textarea id="add_template" rows="" cols="" style="display:none">
	&#160;
	<tpsp />
		<select style="width:100%;" name="USERROLE" >
	<ec:options items="USERROLE_MAP" />
		</select>
	<tpsp />
	<input type="text" name="USERNAME" class="inputtext" value="" />
	<tpsp />
	<input type="text" name="PASSWD" class="inputtext" value="" />
	<tpsp />
	<input type="text" name="EMAIL" class="inputtext" value="" />
	<tpsp />
		<select style="width:100%;" name="GENDER" >
	<ec:options items="GENDER_MAP" />
		</select>
</textarea>


</body>


</html>