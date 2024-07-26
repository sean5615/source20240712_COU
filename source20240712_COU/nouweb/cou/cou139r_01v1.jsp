<%/*
----------------------------------------------------------------------------------
File Name		: cou139r_01v1
Author			: gtu
Description		: COU139R_新開設課程科目檢核表 - 顯示頁面
Modification Log	:

Vers		Date       	By            	Notes
--------------	--------------	--------------	----------------------------------
0.0.1		096/09/03	gtu    	Code Generate Create
----------------------------------------------------------------------------------
*/%>
<%
session.setAttribute("COU139R_01_SELECT", "COU#SELECT FACULTY_CODE AS SELECT_VALUE,FACULTY_NAME AS SELECT_TEXT FROM SYST003 WHERE ASYS='1' ORDER BY DECODE(FACULTY_CODE,'00','99',FACULTY_CODE)");     //學系
session.setAttribute("COU139R_02_SELECT", "COU#SELECT CODE AS SELECT_VALUE, CODE_NAME AS SELECT_TEXT FROM SYST001 WHERE KIND='SMS' ORDER BY CODE");     //學期
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
		<p>您的瀏覽器不支援JavaScript語法，但是並不影響您獲取本網站的內容</p>
	</noscript>
</head>
<body background="<%=vr%>images/ap_index_bg.jpg" alt="背景圖" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-- 定義查詢的 Form 起始 -->
<form name="QUERY" method="post" style="margin:0,0,5,0;">
	<input type=hidden name="control_type">


	<!-- 查詢全畫面起始 -->
	<TABLE id="QUERY_DIV" width="96%" border="0" align="center" cellpadding="0" cellspacing="0" summary="排版用表格">
		<tr>
			<td width="13"><img src="<%=vr%>images/ap_search_01.jpg" alt="排版用圖示" width="13" height="12"></td>
			<td width="100%"><img src="<%=vr%>images/ap_search_02.jpg" alt="排版用圖示" width="100%" height="12"></td>
			<td width="13"><img src="<%=vr%>images/ap_search_03.jpg" alt="排版用圖示" width="13" height="12"></td>
		</tr>
		<tr>
			<td width="13" background="<%=vr%>images/ap_search_04.jpg" alt="排版用圖示">&nbsp;</td>
			<td width="100%" valign="top" bgcolor="#C5E2C3">
				<!-- 按鈕畫面起始 -->
				<table width="100%" border="0" align="center" cellpadding="2" cellspacing="0" summary="排版用表格">
					<tr class="mtbGreenBg">
						<td align=left>【列印畫面】</td>
						<td align=right>
							<div id="serach_btn">
								<input type=button class="btn" value='清  除' onclick='doReset();' onkeypress='doReset();'>
								<input type=submit class="btn" name="PRT_ALL_BTN" value='列  印' onclick='doPrint();' onkeypress='doPrint();'>
								<input type=button class="btn" value='匯  出' name="EXPORT_ALL_BTN" onkeypress="doExport();"onclick="doExport();">
							</div>
						</td>
					</tr>
				</table>
				<!-- 按鈕畫面結束 -->

				<!-- 查詢畫面起始 -->
				<table id="table1" width="100%" border="0" align="center" cellpadding="2" cellspacing="1" summary="排版用表格">
					<tr>
						<td align='right'>學年<font color=red>＊</font>：</td>
						<td colspan='1'>自<input type=text name='AYEAR'>學年起之新開課程
                            <select name='SMS' STYLE='display:none'>
                            <option value=''>請選擇</option>
                            <script>Form.getSelectFromPhrase("COU139R_02_SELECT", null, null);</script>
                            </select>
                        </td>
						<td align='right' >學系<font color=red>＊</font>：</td>
						<td><select name='FACULTY_CODE'>
							<option value=''>請選擇</option>
                            <script>Form.getSelectFromPhrase("COU139R_01_SELECT", null, null);</script>
                            </select>
                        </td>
					</tr>
					<tr>					
                        <td align='right'>專兼教師別：</td>
						<td><select name='NOU_VOCATION_TYPE'>
							  <option value=''>請選擇</option>
							  <option value='1'>專任教師</option>
							  <option value='2'>非專任教師</option>
							</select>
						</td>
						<td align='right'>專任教師姓名<font color=red></font>：</td>
						<td align='left'>
							<select name='TCHIDNO'>
							<option value=''>請選擇</option>
							<script>Form.getSelectFromPhrase("COU139R_TCH_DYNSELECT", null, null);</script>
							</select>
						</td>
					</tr>
				</table>
				<!-- 查詢畫面結束 -->
			</td>
			<td width="13" background="<%=vr%>images/ap_search_06.jpg" alt="排版用圖示">&nbsp;</td>
		</tr>
		<tr>
			<td width="13"><img src="<%=vr%>images/ap_search_07.jpg" alt="排版用圖示" width="13" height="13"></td>
			<td width="100%"><img src="<%=vr%>images/ap_search_08.jpg" alt="排版用圖示" width="100%" height="13"></td>
			<td width="13"><img src="<%=vr%>images/ap_search_09.jpg" alt="排版用圖示" width="13" height="13"></td>
		</tr>
	</table>
	<!-- 查詢全畫面結束 -->
</form>
<!-- 定義查詢的 Form 結束 -->

<!-- 標題畫面起始 -->
<table width="96%" border="0" align="center" cellpadding="4" cellspacing="0" summary="排版用表格">
	<tr>
		<td>
			<table width="500" height="27" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td background="<%=vr%>images/ap_index_title.jpg" alt="排版用圖示">
						　　<span class="title">COU139R_列印預計新開課程檢核表</span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- 標題畫面結束 -->

<script>
	document.write ("<font color=\"white\">" + document.lastModified + "</font>");
	window.attachEvent("onload", page_init);
	window.attachEvent("onload", onloadEvent);
</script>
</body>
</html>