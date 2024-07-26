<%/*
----------------------------------------------------------------------------------
File Name		: cou139r_01m1.jsp
Author			: gtu
Description		: COU139R_新開設課程科目檢核表 - 列印頁面
Modification Log	:

Vers		Date       	By            	Notes
--------------	--------------	--------------	----------------------------------
0.0.1		096/09/03	gtu    	Code Generate Create
----------------------------------------------------------------------------------
*/%>
<%@ page errorPage="/utility/errorpage.jsp" pageEncoding="MS950"%>
<%@ include file="/utility/header.jsp"%>
<%@ include file="/utility/printpageinit.jsp"%>
<%@ include file="cou139r_01m1.jsp"%>

<%
try
{
	/** 起始 Log */
	logger		=	new MyLogger(request.getRequestURI().toString() + "(PRINT_MODE)");
	logger.iniUserInfo(Log4jInit.getIP(request));
	
	/** 起始 DBManager Container */
	dbManager	=	new DBManager(logger);
	
	/** 處理列印 */
	doPrint(out, dbManager, requestMap, session, logger);
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
		com.nou.aut.AUTLOG	autlog	=	new com.nou.aut.AUTLOG(dbManager);
		autlog.setUSER_ID((String)session.getAttribute("USER_ID"));
		autlog.setPROG_CODE("cou139r");
		autlog.setUPD_MK("5");
		autlog.setIP_ADDR(Log4jInit.getIP(request));
		autlog.execute();
		
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