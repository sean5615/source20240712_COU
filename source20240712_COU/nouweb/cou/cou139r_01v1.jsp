<%/*
----------------------------------------------------------------------------------
File Name		: cou139r_01v1
Author			: gtu
Description		: COU139R_�s�}�]�ҵ{����ˮ֪� - ��ܭ���
Modification Log	:

Vers		Date       	By            	Notes
--------------	--------------	--------------	----------------------------------
0.0.1		096/09/03	gtu    	Code Generate Create
----------------------------------------------------------------------------------
*/%>
<%
session.setAttribute("COU139R_01_SELECT", "COU#SELECT FACULTY_CODE AS SELECT_VALUE,FACULTY_NAME AS SELECT_TEXT FROM SYST003 WHERE ASYS='1' ORDER BY DECODE(FACULTY_CODE,'00','99',FACULTY_CODE)");     //�Ǩt
session.setAttribute("COU139R_02_SELECT", "COU#SELECT CODE AS SELECT_VALUE, CODE_NAME AS SELECT_TEXT FROM SYST001 WHERE KIND='SMS' ORDER BY CODE");     //�Ǵ�
session.setAttribute("COU139R_DYNSELECT", "NOU#SELECT  a.FACULTY_CODE AS SELECT_VALUE, a.FACULTY_NAME AS SELECT_TEXT FROM SYST003 a WHERE 1=1 AND a.FACULTY_CODE='[FACULTY_CODE]' ORDER BY SELECT_VALUE");
session.setAttribute("COU139R_TCH_DYNSELECT", "NOU#SELECT DISTINCT Y.IDNO AS SELECT_VALUE, Y.NAME AS SELECT_TEXT FROM TRAT012 X JOIN TRAT001 Y ON X.IDNO = Y.IDNO WHERE X.FACULTY_CODE='[FACULTY_CODE]' AND Y.NOU_VOCATION_TYPE = '1' ORDER BY SELECT_VALUE");

%>
<%@ page contentType="text/html; charset=UTF-8" errorPage="/utility/errorpage.jsp" pageEncoding="MS950"%>
<%@ include file="/utility/header.jsp"%>
<html>
<head>
	<%@ include file="/utility/viewpageinit.jsp"%>
	<script src="<%=vr%>script/framework/query3_1_0_2.jsp"></script>
	<script src="cou139r_01c1.jsp"></script>
	<noscript>
		<p>�z���s�������䴩JavaScript�y�k�A���O�ä��v�T�z��������������e</p>
	</noscript>
</head>
<body background="<%=vr%>images/ap_index_bg.jpg" alt="�I����" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-- �w�q�d�ߪ� Form �_�l -->
<form name="QUERY" method="post" style="margin:0,0,5,0;">
	<input type=hidden name="control_type">


	<!-- �d�ߥ��e���_�l -->
	<TABLE id="QUERY_DIV" width="96%" border="0" align="center" cellpadding="0" cellspacing="0" summary="�ƪ��Ϊ��">
		<tr>
			<td width="13"><img src="<%=vr%>images/ap_search_01.jpg" alt="�ƪ��ιϥ�" width="13" height="12"></td>
			<td width="100%"><img src="<%=vr%>images/ap_search_02.jpg" alt="�ƪ��ιϥ�" width="100%" height="12"></td>
			<td width="13"><img src="<%=vr%>images/ap_search_03.jpg" alt="�ƪ��ιϥ�" width="13" height="12"></td>
		</tr>
		<tr>
			<td width="13" background="<%=vr%>images/ap_search_04.jpg" alt="�ƪ��ιϥ�">&nbsp;</td>
			<td width="100%" valign="top" bgcolor="#C5E2C3">
				<!-- ���s�e���_�l -->
				<table width="100%" border="0" align="center" cellpadding="2" cellspacing="0" summary="�ƪ��Ϊ��">
					<tr class="mtbGreenBg">
						<td align=left>�i�C�L�e���j</td>
						<td align=right>
							<div id="serach_btn">
								<input type=button class="btn" value='�M  ��' onclick='doReset();' onkeypress='doReset();'>
								<input type=submit class="btn" name="PRT_ALL_BTN" value='�C  �L' onclick='doPrint();' onkeypress='doPrint();'>
								<input type=button class="btn" value='��  �X' name="EXPORT_ALL_BTN" onkeypress="doExport();"onclick="doExport();">
							</div>
						</td>
					</tr>
				</table>
				<!-- ���s�e������ -->

				<!-- �d�ߵe���_�l -->
				<table id="table1" width="100%" border="0" align="center" cellpadding="2" cellspacing="1" summary="�ƪ��Ϊ��">
					<tr>
						<td align='right'>�Ǧ~<font color=red>��</font>�G</td>
						<td colspan='1'>��<input type=text name='AYEAR'>�Ǧ~�_���s�}�ҵ{
                            <select name='SMS' STYLE='display:none'>
                            <option value=''>�п��</option>
                            <script>Form.getSelectFromPhrase("COU139R_02_SELECT", null, null);</script>
                            </select>
                        </td>
						<td align='right' >�Ǩt<font color=red>��</font>�G</td>
						<td><select name='FACULTY_CODE'>
							<option value=''>�п��</option>
                            <script>Form.getSelectFromPhrase("COU139R_01_SELECT", null, null);</script>
                            </select>
                        </td>
					</tr>
					<tr>					
                        <td align='right'>�M�ݱЮv�O�G</td>
						<td><select name='NOU_VOCATION_TYPE'>
							  <option value=''>�п��</option>
							  <option value='1'>�M���Юv</option>
							  <option value='2'>�D�M���Юv</option>
							</select>
						</td>
						<td align='right'>�M���Юv�m�W<font color=red></font>�G</td>
						<td align='left'>
							<select name='TCHIDNO'>
							<option value=''>�п��</option>
							<script>Form.getSelectFromPhrase("COU139R_TCH_DYNSELECT", null, null);</script>
							</select>
						</td>
					</tr>
				</table>
				<!-- �d�ߵe������ -->
			</td>
			<td width="13" background="<%=vr%>images/ap_search_06.jpg" alt="�ƪ��ιϥ�">&nbsp;</td>
		</tr>
		<tr>
			<td width="13"><img src="<%=vr%>images/ap_search_07.jpg" alt="�ƪ��ιϥ�" width="13" height="13"></td>
			<td width="100%"><img src="<%=vr%>images/ap_search_08.jpg" alt="�ƪ��ιϥ�" width="100%" height="13"></td>
			<td width="13"><img src="<%=vr%>images/ap_search_09.jpg" alt="�ƪ��ιϥ�" width="13" height="13"></td>
		</tr>
	</table>
	<!-- �d�ߥ��e������ -->
</form>
<!-- �w�q�d�ߪ� Form ���� -->

<!-- ���D�e���_�l -->
<table width="96%" border="0" align="center" cellpadding="4" cellspacing="0" summary="�ƪ��Ϊ��">
	<tr>
		<td>
			<table width="500" height="27" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td background="<%=vr%>images/ap_index_title.jpg" alt="�ƪ��ιϥ�">
						�@�@<span class="title">COU139R_�C�L�w�p�s�}�ҵ{�ˮ֪�</span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- ���D�e������ -->

<script>
	document.write ("<font color=\"white\">" + document.lastModified + "</font>");
	window.attachEvent("onload", page_init);
	window.attachEvent("onload", onloadEvent);
</script>
</body>
</html>