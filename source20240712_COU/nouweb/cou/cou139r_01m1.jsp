<%/*
----------------------------------------------------------------------------------
File Name		: cou139r_01m1.jsp
Author			: gtu
Description		: COU139R_新開設課程科目檢核表 - 處理邏輯頁面
Modification Log	:

Vers		Date       	By            	Notes
--------------	--------------	--------------	----------------------------------
0.0.1		096/09/03	gtu    	Code Generate Create
----------------------------------------------------------------------------------
*/%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="MS950"%>
<%@ include file="/utility/header.jsp"%>
<%@ include file="/utility/modulepageinit.jsp"%>
<%@page import="com.nou.*, com.nou.cou.dao.*, com.nou.sys.dao.*"%>
<%@page import="java.util.*"%>

<%!
/** 處理列印功能 */
private void doPrint(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session, MyLogger logger) throws Exception
{
	DBManager	dbm01		=	new DBManager(logger);
	Connection	conn01		=	dbm01.getConnection(AUTCONNECT.mapConnect("COU", session));
	try
	{
		Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("COU", session));
		
       	COUT001GATEWAY  cout001gat  =    new COUT001GATEWAY(dbManager, conn);
      	Vector result =  cout001gat.getCout001Cou002Cout003Regt007ForCou139r(requestMap, dbm01, conn01);

		RptFile		rptFile	=	new RptFile(session.getId());
       	rptFile.setColumn("NUM,CSMS,CRSNO,CRS_NAME,CRD,PRODUCE_NAME,CRS_BOOK,CRSSTATUS,TEACHING_TYPE_NAME,COUT003, REGAYEARSMS, REGT007TAL, CHECK");		
		
		String F_NAME = getSyst003Name(dbManager,conn,Utility.nullToSpace(requestMap.get("FACULTY_CODE")));
		int x = 1;
		for(int i=0;i<result.size();i++)
		{
			Hashtable ht = new Hashtable();
			ht.put("AYEAR", UtilityX.getHashString4Report(result.get(i), "AYEAR"));
			ht.put("SMS", UtilityX.getHashString4Report(result.get(i), "SMS"));
			ht.put("CRSNO", UtilityX.getHashString4Report(result.get(i), "CRSNO"));
			ht.put("FACULTY_CODE", UtilityX.getHashString4Report(result.get(i), "FACULTY_CODE"));
			
			Hashtable ht_rtn = getCout103ForSomething(dbManager, conn, ht);
				
			rptFile.add(x);		//序號
			x++;
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CSMS"));		//學年期
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CRSNO"));		//科目代碼
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CRS_NAME"));		//科目名稱
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CRD"));		    //學分
			
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "PRODUCE_NAME"));  //教材型態
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CRS_BOOK"));		//教科書型態
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CRSSTATUS"));		//開設狀態
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "TEACHING_TYPE_NAME"));   //面授方式
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "COUT003"));		//學科委員
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "REGAYEARSMS"));		//各學年期開設選課人數
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "REGT007TAL"));		//總選課人數
			rptFile.add("&nbsp;");
		}

		if (rptFile.size() == 0)
		{
			out.println("<script>top.close();alert(\"無符合資料可供列印!!\");window.close();</script>");
			return;
		}
		/** 初始化報表物件 */
		report		report_	=	new report(dbManager, conn, out, "cou139r_01r1", report.onlineHtmlMode);
		/** 靜態變數處理 */
		Hashtable	ht	=	new Hashtable();
		
        String pass_mk = Utility.checkNull(requestMap.get("PASS_MK"), "");
		if(pass_mk.equals("1"))			pass_mk = "確定開課";
		else if(pass_mk.equals("2"))	pass_mk = "計畫通過";
		else if(pass_mk.equals("3"))	pass_mk = "計畫中";
		else							pass_mk = "全部";	
		
        ht.put("AYEAR",String.valueOf(Integer.parseInt(requestMap.get("AYEAR").toString())));
		//ht.put("SMS",((Hashtable)result.get(0)).get("CSMS").toString()+"&nbsp;"+F_NAME);
		ht.put("SMS",F_NAME);
        ht.put("CRS_COUNT",String.valueOf(x-1));
        //ht.put("NOTE","".equals(requestMap.get("PASS_MK").toString())?"(規劃通過暨確定開課課程)":"");//20200409
        ht.put("NOTE",pass_mk);	
        
		report_.setDynamicVariable(ht);
		/** 開始列印 */
		report_.genReport(rptFile);
	}
	catch (Exception ex)
	{
		throw ex;
	}
	finally
	{
		dbManager.close();
		dbm01.close();
	}
}

private Hashtable getCout103ForSomething(DBManager dbManager, Connection conn, Hashtable requestMap) throws Exception
{
	DBResult rs = null;	
	String FACULTY_NAME_COUT103 = "";	
	String REQOPTION_NAME = "";
	String FACULTY_CODE_COUT001 = Utility.nullToSpace(requestMap.get("FACULTY_CODE"));
	Hashtable ht = new Hashtable();
	try
	{
       	COUT103GATEWAY  cout103gateway  =    new COUT103GATEWAY(dbManager, conn);
       	rs =  cout103gateway.getForCou108r(requestMap);
		int i = 0;
		while (rs.next())
		{			
			if("003".equals(rs.getString("CRS_GROUP_CODE"))){
				if(!"".equals(FACULTY_NAME_COUT103)){
					FACULTY_NAME_COUT103 += ",";
				}	
				FACULTY_NAME_COUT103 += rs.getString("FACULTY_NAME");
			}

			if (FACULTY_CODE_COUT001.equals(rs.getString("FACULTY_CODE"))){
				REQOPTION_NAME = rs.getString("REQOPTION_NAME");
			}		
			i++;
		}
		ht.put("FACULTY_NAME_COUT103", FACULTY_NAME_COUT103);
		ht.put("REQOPTION_NAME", REQOPTION_NAME);
		return ht;
	}
	catch (Exception ex)
	{
		throw ex;
	}
	finally
	{
		rs.close();
	}
}

private String getSyst003Name(DBManager dbManager, Connection conn, String FACULTY_CODE) throws Exception
{
	DBResult rs = null;	
	String Name = "";
	try
	{
       	SYST003DAO S03  =  new SYST003DAO(dbManager, conn);
		S03.setResultColumn(" FACULTY_NAME ");
		S03.setWhere(" ASYS='1' AND FACULTY_CODE= '"+FACULTY_CODE+"'");
		rs = S03.query();
		if(rs.next()){
			Name = rs.getString("FACULTY_NAME");
		}
	}
	catch (Exception ex)
	{
		throw ex;
	}
	finally
	{
		rs.close();
	}
	return Name;
}

/** 匯出查詢資料 */
public void doExportAll(HttpServletResponse response, JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	// 匯出EXCEL初始設定
	String path = APConfig.getProperty("SHARED_TMP_PATH") + File.separator + "work" + File.separator + "aut";
	String filenm = "COU139R_" + DateUtil.getNowDate() + DateUtil.getNowTimeMs() + ".xls";
	response.setHeader("Content-disposition","attachment; filename=" + filenm);
	response.setContentType("application/x-download");
	response.setHeader("Cache-Control", "max-age=60");
	out.clear();
	
	// 匯成EXCEL用
	jxl.write.WritableWorkbook wb = null;
    jxl.write.WritableSheet ws = null;
	
	/** 處理 Excel 表身資料 */
	try
	{
		Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("COU", session));	
		
		Vector result = new Vector();
		
        COUT001GATEWAY  cout001gat  =    new COUT001GATEWAY(dbManager, conn);
      	result =  cout001gat.getCout001Cou002Cout003Regt007ForCou139r_Export(requestMap);

     	// 放入EXCEL中-------------------------------開始
     	wb = jxl.Workbook.createWorkbook(response.getOutputStream());						
     	ws = wb.createSheet("預計新課程開設科目檢核表",0);	

		// 欄位表頭
		String[] colinfo = {"開設學系","學年期","科目代碼","科目名稱","學分","教材型態","教科書型態","開設狀態","面授方式","學科委員","學年期選課人數","總選課人數"};
		for (int i=0;i<colinfo.length;i++)
			ws.addCell(new jxl.write.Label(i,0,colinfo[i]));
			
		// 放入資料
		int rowNum = 1; // 在EXCEL中的行數,第0行為表頭
		for (int i=0;i<result.size();i++) {
			
			ws.addCell(new jxl.write.Label(0,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("PLAN_FACULTY_NAME"),"")));	//學系
			ws.addCell(new jxl.write.Label(1,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CSMS"),"")));		//學期
			ws.addCell(new jxl.write.Label(2,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CRSNO"),"")));	//科目代碼
			ws.addCell(new jxl.write.Label(3,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CRS_NAME"),"")));	//科目名稱
			ws.addCell(new jxl.write.Label(4,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CRD"),"")));		//學分數
			ws.addCell(new jxl.write.Label(5,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("PRODUCE_NAME"),"")));	//教材型態
			ws.addCell(new jxl.write.Label(6,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CRS_BOOK"),"")));		//教科書型態
			ws.addCell(new jxl.write.Label(7,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CRSSTATUS"),"")));	//開設狀態
			ws.addCell(new jxl.write.Label(8,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("TEACHING_TYPE_NAME"),"")));	//面授方式 
			ws.addCell(new jxl.write.Label(9,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("COUT003"),"")));	//學科委員
			ws.addCell(new jxl.write.Label(10,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("REGAYEARSMS"),"")));	//學年期選課人數 
			ws.addCell(new jxl.write.Label(11,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("REGT007TAL"),"")));	//總選課人數 
			rowNum++;
		}
		// 放入EXCEL中-------------------------------結束
	}
	catch (Exception ex)
	{
		throw ex;
	}
	finally
	{
		
		try {			
				wb.write();
				wb.close();
			} catch (Exception e) {
			
			}
	}
}

%>