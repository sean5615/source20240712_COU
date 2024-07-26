<%/*
----------------------------------------------------------------------------------
File Name		: cou139r_01c1
Author			: gtu
Description		: COU139R_�s�}�]�ҵ{����ˮ֪� - ����� (javascript)
Modification Log	:

Vers		Date       	By            	Notes
--------------	--------------	--------------	----------------------------------
0.0.1		096/09/03	gtu    	Code Generate Create
----------------------------------------------------------------------------------
*/%>
<%@ page contentType="text/html; charset=UTF-8" errorPage="/utility/errorpage.jsp" pageEncoding="MS950"%>
<%@ include file="/utility/header.jsp"%>
<%@ include file="/utility/jspageinit.jsp"%>

/** �פJ javqascript Class */
doImport ("ErrorHandle.js, LoadingBar_0_2.js, Form.js");

/** ��l�]�w������T */
var	printPage		=	"cou139r_01p1.jsp";	//�C�L����
var	_privateMessageTime	=	-1;			//�T����ܮɶ�(���ۭq�� -1)
var	controlPage		=	"cou139r_01c2.jsp";
var	noPermissAry		=	new Array();		//�S���v�����}�C

/** ������l�� */
function page_init()
{
	page_init_start();

	/** �v���ˮ� */
	securityCheck();

	/** === ��l���]�w === */
	iniMasterKeyColumn();
	/** ��l�C�L��� */
	Form.iniFormSet('QUERY', 'AYEAR', 'M',  3, 'A', 'F', 3, 'S', 3);
	Form.iniFormSet('QUERY', 'SMS', 'M',  1, 'A');

	/** ================ */

	/** === �]�w�ˮֱ��� === */
	/** �C�L��� */
	Form.iniFormSet('QUERY', 'AYEAR', 'AA', 'chkForm', '�Ǧ~');
	Form.iniFormSet('QUERY', 'FACULTY_CODE', 'AA', 'chkForm', '�}�]�Ǩt');

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
	_i("QUERY", "TCHIDNO").options[0].text = "�п��";
}


/** �B�z�C�L�ʧ@ */
function doPrint()
{
	doPrint_start();

	/** === �۩w�ˬd === */
	/* === LoadingBar === */
	/** ����ˮ֤γ]�w, �����~�B�z�覡�� Form.errAppend(Message) �֭p���~�T�� */
	//if (Form.getInput("QUERY", "SYS_CD") == "")
	//	Form.errAppend("�t�νs�����i�ť�!!");
	/** ================ */

	doPrint_end();
}

/** �B�z�ץX�ʧ@ */
function doExport() {
	/** �}�l�B�z */
	Message.showProcess();

	/** �ˮֳ]�w���*/
	Form.startChkForm("QUERY");
	
	/** ��ֿ��~�B�z */
	if (!queryObj.valideMessage (Form)){
		Message.hideProcess();
		return;
	}
	
	Form.setInput('QUERY', 'control_type',	'EXPORT_ALL_MODE');
	Form.doSubmit('QUERY',controlPage,'post','');
	Message.hideProcess();
}


/** ============================= ���ץ��{����m�� ======================================= */
/** �]�w�\���v�� */
function securityCheck()
{
	try
	{
		/** �C�L */
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

/** �ˬd�v�� - ���v��/�L�v��(true/false) */
function chkSecure(secureType)
{
	if (noPermissAry.toString().indexOf(secureType) != -1)
		return false;
	else
		return true
}
/** ====================================================================================== */
/** ��l�W�h�a�Ӫ� Key ��� */
function iniMasterKeyColumn()
{
	/** �D Detail �������B�z */
	if (typeof(keyObj) == "undefined")
		return;
	/** ��� */
	for (keyName in keyObj)
	{
		try {Form.iniFormSet("QUERY", keyName, "V", keyObj[keyName], "R", 0);}catch(ex){};
		try {Form.iniFormSet("EDIT", keyName, "V", keyObj[keyName], "R", 0);}catch(ex){};
		try {Form.iniFormSet("RESULT", keyName, "V", keyObj[keyName], "R", 0);}catch(ex){};
	}
	Form.iniFormColor();
}