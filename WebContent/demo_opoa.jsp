<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.ecside.org" prefix="ec"%>
<%@ page import="demo.common.CommonDictionary"%>
<%
	String webapp = request.getContextPath();

	request.setAttribute("GENDER_MAP", CommonDictionary.GENDER);
	request.setAttribute("USERROLE_MAP", CommonDictionary.USERROLE);
%>

<html>

<head>
<script type="text/javascript">
<!--
	if (("" + window.location).indexOf("easyList=userInfoList") < 1) {
		window.location.replace("demo_opoa.jsp?easyList=userInfoList");
	}
//-->
</script>

<jsp:include page="common_head.jsp" flush="true" />

<script type="text/javascript">
	//======================  查询 相关 ======================== //
	function doQuery(queryFormName, listFormName) {
		var queryForm = $(queryFormName);
//with(document.forms[0]){
		var queryPara = {
			"USERNAME" : queryForm["USERNAME"].value,
			"USERROLE" : queryForm["USERROLE"].value,
			"PASSWD" : queryForm["PASSWD"].value,
			"GENDER" : queryForm["GENDER"].value
		};
		ECSideUtil.queryECForm(listFormName, queryPara, true);
}
//	}
</script>

</head>


<body style="margin: 10px;">

	<form name="queryForm"  id="queryForm">
		<table border="0" cellpadding="0" cellspacing="0"
			class="simpleFormTable" width="100%">
			<tr>
				<td class="tableTitle3" style="cursor: pointer;">用户查询</td>
			</tr>
			<tr id="queryUserZone">
				<td class="formTableC">
					<table align="center" border="0" cellpadding="0" cellspacing="3"
						class="formTableCore">
						<tr>
							<td width="15%">用户名</td>
							<td width="35%"><input type="text" name="USERNAME" id="USERNAME"></td>
							<td width="15%">角色</td>
							<td width="35%"><select style="width: 100px" name="USERROLE">
									<option value=""></option>
									<ec:options items="USERROLE_MAP" />
							</select></td>
						</tr>
						<tr>
							<td>密码</td>
							<td><input type="text" name="PASSWD"></td>
							<td>性别</td>
							<td><select style="width: 100px" name="GENDER">
									<option value=""></option>
									<ec:options items="GENDER_MAP" />
							</select></td>
						</tr>
						<tr>
							<td colspan="4" class="formButtonTD"><input type="button"
								class="formButton" value="查询"
								onclick="doQuery('queryForm','ec')">&#160;&#160;&#160;&#160;<input
								class="formButton" type="reset"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>

	<center>
		<ec:table items="recordList" 
							var="record" 
							retrieveRowsCallback="limit"
							action="${pageContext.request.contextPath}/demo_opoa.jsp?easyList=userInfoList"
							title="用户列表" 
							xlsFileName="用户列表.xls" 
							csvFileName="用户列表.csv"
							width="100%" 
							listWidth="100%" height="280px">
			<ec:row>
				<ec:column width="50" property="_0" title="序号"
					value="${GLOBALROWCOUNT}" />
				<ec:column width="80" property="USERROLE" title="角色"
					mappingItem="USERROLE_MAP" />
				<ec:column width="120" property="USERNAME" title="用户名" />
				<ec:column width="100" property="PASSWD" title="密码" />
				<ec:column width="120" property="EMAIL" title="电子信箱" />
				<ec:column width="70" property="GENDER" title="性别"
					mappingItem="GENDER_MAP" />
			</ec:row>
		</ec:table>
	</center>


</body>


</html>