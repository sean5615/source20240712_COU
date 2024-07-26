<%/*
----------------------------------------------------------------------------------
File Name		: cou139r_01m1.jsp
Author			: gtu
Description		: COU139R_�s�}�]�ҵ{����ˮ֪� - �B�z�޿譶��
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
/** �B�z�C�L�\�� */
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
				
			rptFile.add(x);		//�Ǹ�
			x++;
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CSMS"));		//�Ǧ~��
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CRSNO"));		//��إN�X
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CRS_NAME"));		//��ئW��
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CRD"));		    //�Ǥ�
			
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "PRODUCE_NAME"));  //�Ч����A
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CRS_BOOK"));		//�Ь�ѫ��A
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "CRSSTATUS"));		//�}�]���A
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "TEACHING_TYPE_NAME"));   //���¤覡
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "COUT003"));		//�Ǭ�e��
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "REGAYEARSMS"));		//�U�Ǧ~���}�]��ҤH��
			rptFile.add(UtilityX.getHashString4Report(result.get(i), "REGT007TAL"));		//�`��ҤH��
			rptFile.add("&nbsp;");
		}

		if (rptFile.size() == 0)
		{
			out.println("<script>top.close();alert(\"�L�ŦX��ƥi�ѦC�L!!\");window.close();</script>");
			return;
		}
		/** ��l�Ƴ����� */
		report		report_	=	new report(dbManager, conn, out, "cou139r_01r1", report.onlineHtmlMode);
		/** �R�A�ܼƳB�z */
		Hashtable	ht	=	new Hashtable();
		
        String pass_mk = Utility.checkNull(requestMap.get("PASS_MK"), "");
		if(pass_mk.equals("1"))			pass_mk = "�T�w�}��";
		else if(pass_mk.equals("2"))	pass_mk = "�p�e�q�L";
		else if(pass_mk.equals("3"))	pass_mk = "�p�e��";
		else							pass_mk = "����";	
		
        ht.put("AYEAR",String.valueOf(Integer.parseInt(requestMap.get("AYEAR").toString())));
		//ht.put("SMS",((Hashtable)result.get(0)).get("CSMS").toString()+"&nbsp;"+F_NAME);
		ht.put("SMS",F_NAME);
        ht.put("CRS_COUNT",String.valueOf(x-1));
        //ht.put("NOTE","".equals(requestMap.get("PASS_MK").toString())?"(�W���q�L�[�T�w�}�ҽҵ{)":"");//20200409
        ht.put("NOTE",pass_mk);	
        
		report_.setDynamicVariable(ht);
		/** �}�l�C�L */
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

/** �ץX�d�߸�� */
public void doExportAll(HttpServletResponse response, JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
{
	// �ץXEXCEL��l�]�w
	String path = APConfig.getProperty("SHARED_TMP_PATH") + File.separator + "work" + File.separator + "aut";
	String filenm = "COU139R_" + DateUtil.getNowDate() + DateUtil.getNowTimeMs() + ".xls";
	response.setHeader("Content-disposition","attachment; filename=" + filenm);
	response.setContentType("application/x-download");
	response.setHeader("Cache-Control", "max-age=60");
	out.clear();
	
	// �צ�EXCEL��
	jxl.write.WritableWorkbook wb = null;
    jxl.write.WritableSheet ws = null;
	
	/** �B�z Excel ����� */
	try
	{
		Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("COU", session));	
		
		Vector result = new Vector();
		
        COUT001GATEWAY  cout001gat  =    new COUT001GATEWAY(dbManager, conn);
      	result =  cout001gat.getCout001Cou002Cout003Regt007ForCou139r_Export(requestMap);

     	// ��JEXCEL��-------------------------------�}�l
     	wb = jxl.Workbook.createWorkbook(response.getOutputStream());						
     	ws = wb.createSheet("�w�p�s�ҵ{�}�]����ˮ֪�",0);	

		// �����Y
		String[] colinfo = {"�}�]�Ǩt","�Ǧ~��","��إN�X","��ئW��","�Ǥ�","�Ч����A","�Ь�ѫ��A","�}�]���A","���¤覡","�Ǭ�e��","�Ǧ~����ҤH��","�`��ҤH��"};
		for (int i=0;i<colinfo.length;i++)
			ws.addCell(new jxl.write.Label(i,0,colinfo[i]));
			
		// ��J���
		int rowNum = 1; // �bEXCEL�������,��0�欰���Y
		for (int i=0;i<result.size();i++) {
			
			ws.addCell(new jxl.write.Label(0,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("PLAN_FACULTY_NAME"),"")));	//�Ǩt
			ws.addCell(new jxl.write.Label(1,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CSMS"),"")));		//�Ǵ�
			ws.addCell(new jxl.write.Label(2,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CRSNO"),"")));	//��إN�X
			ws.addCell(new jxl.write.Label(3,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CRS_NAME"),"")));	//��ئW��
			ws.addCell(new jxl.write.Label(4,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CRD"),"")));		//�Ǥ���
			ws.addCell(new jxl.write.Label(5,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("PRODUCE_NAME"),"")));	//�Ч����A
			ws.addCell(new jxl.write.Label(6,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CRS_BOOK"),"")));		//�Ь�ѫ��A
			ws.addCell(new jxl.write.Label(7,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("CRSSTATUS"),"")));	//�}�]���A
			ws.addCell(new jxl.write.Label(8,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("TEACHING_TYPE_NAME"),"")));	//���¤覡 
			ws.addCell(new jxl.write.Label(9,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("COUT003"),"")));	//�Ǭ�e��
			ws.addCell(new jxl.write.Label(10,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("REGAYEARSMS"),"")));	//�Ǧ~����ҤH�� 
			ws.addCell(new jxl.write.Label(11,rowNum,Utility.checkNull(((Hashtable)result.get(i)).get("REGT007TAL"),"")));	//�`��ҤH�� 
			rowNum++;
		}
		// ��JEXCEL��-------------------------------����
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