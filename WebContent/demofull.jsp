<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.ecside.org" prefix="ec" %>
<%@ page import="demo.TestData"  %>
<%@ page import="demo.common.CommonDictionary"  %>


<%

String webapp=request.getContextPath();

request.setAttribute("records", TestData.getTestData(request));

request.setAttribute("GENDER_MAP", CommonDictionary.GENDER);
request.setAttribute("USERROLE_MAP", CommonDictionary.USERROLE);

%>

<html>

<head>
<jsp:include page="common_head.jsp" flush="true" />

<script type="text/javascript">

function init(){

top.document.title=top.document.title+" how long : "+(new Date().getTime()-startTime);

}
var startTime=new Date().getTime();


</script>

  <link rel="stylesheet" type="text/css" media="all" href="<%=webapp%>/ecside/calendar/calendar-blue.css"  />

  <script type="text/javascript" src="<%=webapp%>/ecside/calendar/calendar.js"></script>

  <script type="text/javascript" src="<%=webapp%>/ecside/calendar/calendar-cn-utf8.js"></script>

  <script type="text/javascript" src="<%=webapp%>/ecside/calendar/calendar-setup.js"></script>

</head>

<body style="margin:15px;" onload="init()">


<ec:table items="records" var="record" 

useAjax="true"
doPreload="false"

action="${pageContext.request.contextPath}/demofull.jsp"

shadowRowAction="${pageContext.request.contextPath}/ecside/ajaxtemplate/getMemo.jsp?easyDataAccess=myEasyDA.getUserMemo" 


updateAction="${pageContext.request.contextPath}/ecside/ajaxtemplate/updateResult.jsp?easyDataAccess=myEasyDA.updateUser" 
deleteAction="${pageContext.request.contextPath}/ecside/ajaxtemplate/updateResult.jsp?easyDataAccess=myEasyDA.deleteUser" 
insertAction="${pageContext.request.contextPath}/ecside/ajaxtemplate/updateResult.jsp?easyDataAccess=myEasyDA.addUser" 

editable="true" 
batchUpdate="false" 


title="用户列表" 

xlsFileName="用户列表.xls" 
csvFileName="用户列表.csv" 
minColWidth="80" 
maxRowsExported="10000000" 
pageSizeList="10,20,50,100,500,1000" 
rowsDisplayed="10" 



retrieveRowsCallback="process" 
sortable="true" 
filterable="true" 

rowsDisplayed="20" 

generateScript="true" 

resizeColWidth="true" 

classic="true" 



width="100%" 

height="277px" 

minHeight="200"  


>
<ec:row  recordKey="${record.USERID}" rowId="rowid_${GLOBALROWCOUNT}" >
		<ec:attribute>title="${record.USERNAME}"</ec:attribute>
<ec:column width="50" property="_0" title="序号"  editable="false" resizeColWidth="false" >${GLOBALROWCOUNT}</ec:column>
<ec:column width="26" title="&#160;"  property="_1" viewsAllowed="html" resizeColWidth="false"
cell="shadowRow" tipTitle="点击查看个人简介" 
 />

	<ec:column width="80" property="USERROLE" title="角色" editTemplate="ecs_t_role" mappingItem="USERROLE_MAP" />
	<ec:column width="120" property="USERNAME" title="用户名" editable="false"  cellValue="${record.USERNAME}"
		style=" #_EX:VAR.USERID>5 && VAR.USERID<15?'color:red':'' " />
	<ec:column width="100" property="PASSWD" title="密码"  />
	<ec:column width="120" property="EMAIL" title="电子信箱" />
	<ec:column width="150" property="REGDATE" title="注册时间" editTemplate="ecs_t_date"  />
	
	<ec:column width="70" property="GENDER" title="性别"  editTemplate="ecs_t_gender" mappingItem="GENDER_MAP"   />
	<ec:column  width="70"  property="USERID" title="编号" editable="false" resizeColWidth="false" format="0.00" calc="average,total" calcTitle= "平均,合计" calcSpan="2" >
#_EX:
// 新特性,支持动态脚本语言(js):
// USERID大于 5 小于 15的 使用红色显示
// VAR是关键字,代表迭代中的当前记录.
// 您可以和 property="USERNAME" 列的 style做下对比,两种方式都可以实现类似的效果,您可以自由选择.
// 动态脚本语言的加入,可以帮助用户非常方便的实现横向统计.
// 例如 想在当前列显示 其他某几列的和 可以使用 return VAR.某列1的名字+VAR.某列2的名字+VAR.某列1的名字.....

if ( VAR.USERID>5 && VAR.USERID<15) {
	return '<font color=red>'+VAR.USERID+'</font>'
} else {
	return VAR.USERID;
}
	</ec:column>

</ec:row>

</ec:table>
	


<!-- 编辑和过滤所使用的 通用的文本框模板 -->
<textarea id="ecs_t_date" rows="" cols="" style="display:none">
	<input type="text" class="inputtext" value="" 
	 style="width:100px;" name="" /><input class="calendarImgButton" 
	 onclick="ECSideUtil.showCalendar(this)" type="button"  id="date_button" />
</textarea>

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
<tpsp />
&#160;
</textarea>





</body>
</html>