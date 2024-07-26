<%/*
----------------------------------------------------------------------------------
File Name		: cou139r_01c1
Author			: gtu
Description		: COU139R_新開設課程科目檢核表 - 控制頁面 (javascript)
Modification Log	:

Vers		Date       	By            	Notes
--------------	--------------	--------------	----------------------------------
0.0.1		096/09/03	gtu    	Code Generate Create
----------------------------------------------------------------------------------
*/%>
<%@ page contentType="text/html; charset=UTF-8" errorPage="/utility/errorpage.jsp" pageEncoding="MS950"%>
<%@ include file="/utility/header.jsp"%>
<%@ include file="/utility/jspageinit.jsp"%>

/** 匯入 javqascript Class */
doImport ("ErrorHandle.js, LoadingBar_0_2.js, Form.js");

/** 初始設定頁面資訊 */
var	printPage		=	"cou139r_01p1.jsp";	//列印頁面
var	_privateMessageTime	=	-1;			//訊息顯示時間(不自訂為 -1)
var	controlPage		=	"cou139r_01c2.jsp";
var	noPermissAry		=	new Array();		//沒有權限的陣列

/** 網頁初始化 */
function page_init()
{
	page_init_start();

	/** 權限檢核 */
	securityCheck();

	/** === 初始欄位設定 === */
	iniMasterKeyColumn();
	/** 初始列印欄位 */
	Form.iniFormSet('QUERY', 'AYEAR', 'M',  3, 'A', 'F', 3, 'S', 3);
	Form.iniFormSet('QUERY', 'SMS', 'M',  1, 'A');

	/** ================ */

	/** === 設定檢核條件 === */
	/** 列印欄位 */
	Form.iniFormSet('QUERY', 'AYEAR', 'AA', 'chkForm', '學年');
	Form.iniFormSet('QUERY', 'FACULTY_CODE', 'AA', 'chkForm', '開設學系');

	/** ================ */
	
		/** ================ */
	_i("QUERY","FACULTY_CODE").onchange = function() { doGet_TCHIDNO_ChangeSelect(); };
	/** ================ */
	doGet_TCHIDNO_ChangeSelect();

	page_init_end();
}

function doGet_TCHIDNO_ChangeSelect()
{
	Form.getDynSelectFromPhrase(_i("QUERY", "TCHIDNO"), 
		"COU139R_TCH_DYNSELECT", 
		true, 
		"FACULTY_CODE", 
		[_i("QUERY", "FACULTY_CODE").value]);
	_i("QUERY", "TCHIDNO").options[0].text = "請選擇";
}


/** 處理列印動作 */
function doPrint()
{
	doPrint_start();

	/** === 自定檢查 === */
	/* === LoadingBar === */
	/** 資料檢核及設定, 當有錯誤處理方式為 Form.errAppend(Message) 累計錯誤訊息 */
	//if (Form.getInput("QUERY", "SYS_CD") == "")
	//	Form.errAppend("系統編號不可空白!!");
	/** ================ */

	doPrint_end();
}

/** 處理匯出動作 */
function doExport() {
	/** 開始處理 */
	Message.showProcess();

	/** 檢核設定欄位*/
	Form.startChkForm("QUERY");
	
	/** 減核錯誤處理 */
	if (!queryObj.valideMessage (Form)){
		Message.hideProcess();
		return;
	}
	
	Form.setInput('QUERY', 'control_type',	'EXPORT_ALL_MODE');
	Form.doSubmit('QUERY',controlPage,'post','');
	Message.hideProcess();
}


/** ============================= 欲修正程式放置區 ======================================= */
/** 設定功能權限 */
function securityCheck()
{
	try
	{
		/** 列印 */
		if (!<%=AUTICFM.securityCheck (session, "PRT")%>)
		{
			noPermissAry[noPermissAry.length]	=	"PRT";
			try{Form.iniFormSet("QUERY", "PRT_ALL_BTN", "D", 1);}catch(ex){}
		}
	}
	catch (ex)
	{
	}
}

/** 檢查權限 - 有權限/無權限(true/false) */
function chkSecure(secureType)
{
	if (noPermissAry.toString().indexOf(secureType) != -1)
		return false;
	else
		return true
}
/** ====================================================================================== */
/** 初始上層帶來的 Key 資料 */
function iniMasterKeyColumn()
{
	/** 非 Detail 頁面不處理 */
	if (typeof(keyObj) == "undefined")
		return;
	/** 塞值 */
	for (keyName in keyObj)
	{
		try {Form.iniFormSet("QUERY", keyName, "V", keyObj[keyName], "R", 0);}catch(ex){};
		try {Form.iniFormSet("EDIT", keyName, "V", keyObj[keyName], "R", 0);}catch(ex){};
		try {Form.iniFormSet("RESULT", keyName, "V", keyObj[keyName], "R", 0);}catch(ex){};
	}
	Form.iniFormColor();
}