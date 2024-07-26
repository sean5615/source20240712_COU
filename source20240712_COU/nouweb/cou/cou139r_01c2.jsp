<%/*
----------------------------------------------------------------------------------
File Name		: cou139r_01c2.jsp
Author			: maggie
Description		: COU139R_新開設課程科目檢核表 - 控制頁面(JSP)
Modification Log    :

Vers			Date			By				Notes
--------------	--------------	--------------	----------------------------------
0.0.1			111/12/23		maggie				Code Generate Create
----------------------------------------------------------------------------------
*/%>
<%@ page errorPage="/utility/ajaxerrorpage.jsp" pageEncoding="MS950"%>
<%@page import="com.nou.aut.*"%>
<%@ include file="/utility/header.jsp"%>
<%@ include file="/utility/controlpageinit.jsp"%>
<%@ include file="cou139r_01m1.jsp"%>

<%
int	logFlag	=	-1;
try
{
	/** 起始 Log */
	logger		=	new MyLogger(request.getRequestURI().toString() + "(" + control_type + ")");
	logger.iniUserInfo(Log4jInit.getIP(request));

	/** 起始 DBManager Container */
	dbManager	=	new DBManager(logger);


	/** 匯出查詢資料 */
	if (control_type.equals("EXPORT_ALL_MODE"))
	{
		logFlag	=	6;
		doExportAll(response, out, dbManager, requestMap, session);
	}	
}
catch(Exception ex)
{
	logErrMessage(ex, logger);
	throw ex;
}
finally
{
	try
	{
		/** 異動註記 */
		if (logFlag != -2)
		{
			com.nou.aut.AUTLOG	autlog	=	new com.nou.aut.AUTLOG(dbManager);
			autlog.setUSER_ID((String)session.getAttribute("USER_ID"));
			autlog.setPROG_CODE("cou139r");
			autlog.setUPD_MK(String.valueOf(logFlag));
			autlog.setIP_ADDR(Log4jInit.getIP(request));
			autlog.execute();
		}
		
		dbManager.close();
	}
	catch(Exception ex)
	{
		logErrMessage(ex, logger);
		throw ex;
	}

	if (logger != null)
	{
		long	endTime	=	System.currentTimeMillis();
		logger.append("全部執行時間：" + String.valueOf(endTime - startTime) + " ms");
		logger.log();
	}

	requestMap	=	null;
	logger		=	null;
}
%>