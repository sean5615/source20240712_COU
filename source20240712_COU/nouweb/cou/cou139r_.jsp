<%/*
----------------------------------------------------------------------------------
File Name		: cou139r
Author			: gtu
Description		: COU139R_�s�}�]�ҵ{����ˮ֪� - �D�n����
Modification Log	:

Vers		Date       	By            	Notes
--------------	--------------	--------------	----------------------------------
0.0.1		096/09/03	gtu    	Code Generate Create
----------------------------------------------------------------------------------
*/%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="MS950"%>
<%@ include file="/utility/header.jsp"%>
<%@ include file="/utility/titleSetup.jsp"%>
<%
String FACULTY_CODE = com.nou.UtilityX.getAutDepCode((String) session.getAttribute("USER_ID"), "2","3");
String	keyParam	=	com.acer.util.Utility.checkNull(request.getParameter("keyParam"), "");
if(!"".equals(FACULTY_CODE)){
	if(keyParam==null || keyParam.length()==0){
		keyParam = "?FACULTY_CODE="+FACULTY_CODE;
	}else{
		keyParam = "&FACULTY_CODE="+FACULTY_CODE;
	}
}
%>
<script>
	top.hideView();
	/** �ɦV�Ĥ@�ӳB�z������ */
	top.mainFrame.location.href	=	'cou139r_01v1.jsp<%=keyParam %>';
</script>