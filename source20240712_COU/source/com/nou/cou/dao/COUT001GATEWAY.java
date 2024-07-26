//~ Formatted in Sun Code Convention on 07/12/20 
package com.nou.cou.dao;

//~--- non-JDK imports --------------------------------------------------------

import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.mail.Session;

import com.acer.apps.Page;
import com.acer.db.DBManager;
import com.acer.db.query.DBResult;
import com.acer.log.MyLogger;
import com.acer.util.Utility;
import com.nou.UtilityX;
import com.nou.sys.dao.SYST001DAO;
import com.nou.sys.dao.SYST008DAO;

/*
* (COUT001) Gateway/*
*-------------------------------------------------------------------------------*
* Author    : 國長      2007/05/04
* Modification Log :
* Vers     Date           By             Notes
*--------- -------------- -------------- ----------------------------------------
* V0.0.1   2007/05/04     國長           建立程式
*                                        新增 getCout001ForUse(Hashtable ht)
* VO.0.2   2007/05/21     俊賢           新增 getCout001Cout002Syst001Syst003ForUse(Hashtable ht,String SMSSD)
*                                             getCout001Cout002ForUse(Hashtable ht)
* VO.0.3   2007/07/11     俊賢           新增 getCout001Cout010Cout011Cout002ForUse(Hashtable ht)
*
* V0.0.4   2007/08/09     純毓           新增 getCout001Cou002Cout100ForUse(Hashtable ht)
*          2007/08/09     純毓           新增 getCout001Cou002Cout100ForUse_2(Hashtable ht)
*          2007/08/09     純毓           新增 getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForUse(Hashtable ht)
* VO.0.5   2007/08/20     文忠           新增 getCout001Cout002Syst001Syst003ForUse(Hashtable ht)
* V0.0.6   2007/08/30     tonny              新增 tra014m(Vector vt, Hashtable ht)
* V0.0.7   2007/09/05     gtu            新增 getCout001Cou002Cout103ForUse(Hashtable ht)
*          2007/08/18     gtu            修改 getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForUse(Hashtable ht)
                                         新增 getCout002Trat001ForUse(Hashtable ht)
*                                        修改 getCout001Cou002Cout100ForUse_2(Hashtable ht)
* VO.0.8   2007/09/19     俊賢           修改 getCout001Cout010Cout011Cout002ForUse(Hashtable ht)
* V0.0.9   2007/09/19     俊賢           新增 getCout001Cout003ForUse(Hashtable ht)
* V0.0.9   2007/09/19     gtu            新增 getCout022Cout028ForUse(Hashtable ht)
* V0.0.9   2007/09/19     gtu            修改 getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForUse(Hashtable ht)
* V0.0.9   2007/09/19     gtu            新增 getCout001Trat006Trat001ForUse(Hashtable ht)
* V0.0.9   2007/09/19     gtu            修改 getCout001Cou002Cout103ForUse(Hashtable ht)
* V0.0.10   2007/10/14     sorge           新增 getReg145r_Reg154_CRSNO(Vector vt, Hashtable ht)
* VO.0.11  2007/10/29     文忠           新增 getCout001Cout002Cout005Syst001Syst003ForUse(Hashtable ht)
                                                                                                            修改 getCout001Cout010Cout011Cout002ForUse(Hashtable ht)
* V0.0.12  2008/01/07     WEN            修改  getCout001REGT013ForUse (Hashtable ht)
* V0.0.13  2008/01/08     WEN            新增getCout001Cout002ForGetCrs(Hashtable ht)
* V0.0.14  2008/01/17     WEN            新增getCout001Cout002Cout004Syst001ForUse(Hashtable ht)
* V0.0.15  2008/03/01     lin            新增 getPer028mQuery_2(Hashtable ht)
* V0.0.16  2008/04/11     barry            新增 getCout003NamesForCou109r(DBManager dbManager, Connection conn, String AYEAR, String SMS, String CRSNO)
* V0.0.17	2008/04/12		sRu			修改getReg145r_Reg154_CRSNO(Vector vt, Hashtable ht)
* V0.0.18	2009/08/14		teno	修改getCout003NamesForCou109r 加依等級排序
* V0.0.19	2009/08/26		teno	修改getCout003NamesForCou109r 加依new_rework續新重製欄判斷學科委員之聘任學年期及換系科目從cout010查詢舊科目代碼
* V0.0.20	2010/03/19	  PEILING	修改「先修課程」由COUT001的FIRSTCOU取得
* 										-getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForCOU128R(Hashtable ht, DBManager dbm01, Connection conn01)
* 										-getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForUse(Hashtable ht, DBManager dbm01, Connection conn01)
* 										-getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForUseCou105R(Hashtable ht, DBManager dbm01
* V0.0.21   2010/03/23	  PEILING	新增 cou029forQuery(Hashtable ht, DBManager dbm01, Connection conn01)
* V0.0.22	2010/03/26     PEILING   新增 cou029forPrint(Hashtable ht, Vector result,DBManager dbm01, Connection conn01)
* V0.0.23	2010/05/31	  PEILING   在getCout001Cou002Cout100ForUse(Hashtable ht)增加欄位C8.SUPTECH_CRS_RMK
*--------------------------------------------------------------------------------
 */
public class COUT001GATEWAY
{
    private Connection	conn		= null;
    private DBManager	dbmanager	= null;
    
    /** 資料排序方式 */
    private String	orderBy	= "";

    /* 頁數 */
    private int	pageNo	= 0;

    /** 每頁筆數 */
    private int	pageSize	= 0;

    /** 記錄是否分頁 */
    private boolean	pageQuery	= false;

    /** 用來存放 SQL 語法的物件 */
    private StringBuffer	sql	= new StringBuffer();

    /** 不允許建立空的物件 */
    private COUT001GATEWAY()
    {
    }

    /** 建構子，查詢全部資料用 */
    public COUT001GATEWAY(DBManager dbmanager, Connection conn)
    {
        this.dbmanager	= dbmanager;
        this.conn	= conn;
    }

    /** 建構子，查詢分頁資料用 */
    public COUT001GATEWAY(DBManager dbmanager, Connection conn, int pageNo, int pageSize)
    {
        this.dbmanager	= dbmanager;
        this.conn	= conn;
        this.pageNo	= pageNo;
        this.pageSize	= pageSize;
        pageQuery	= true;
    }

    /**
     * <pre>
     *  設定資料排序方式.
     *  Ex: "AYEAR, SMS DESC"
     *      先以 AYEAR 排序再以 SMS 倒序序排序
     *  </pre>
     */
    public void setOrderBy(String orderBy)
    {
        if (orderBy == null)
        {
            orderBy	= "";
        }

        this.orderBy	= orderBy;
    }

    /** 取得總筆數 */
    public int getTotalRowCount()
    {
        return Page.getTotalRowCount();
    }

    /**
     *
     * @param ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *         若該欄位有中文名稱，則其 KEY 請加上 _NAME, EX: SMS 其中文欄位請設為 SMS_NAME
     * @throws Exception
     */
    public Vector getCout001ForUse(Hashtable ht) throws Exception
    {
        if (ht == null)
        {
            ht	= new Hashtable();
        }

        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT * FROM COUT001 C01 " + "WHERE 1 = 1 ");

        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            sql.append("AND C01.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append("AND C01.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append("AND C01.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append("AND C01.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRS_SEQ")).equals(""))
        {
            sql.append("AND C01.CRS_SEQ = '" + Utility.nullToSpace(ht.get("CRS_SEQ")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
        {
            sql.append("AND C01.NEW_REWORK = '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("DISCIPLINE_CODE")).equals(""))
        {
            sql.append("AND C01.DISCIPLINE_CODE = '" + Utility.nullToSpace(ht.get("DISCIPLINE_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRD")).equals(""))
        {
            sql.append("AND C01.CRD = '" + Utility.nullToSpace(ht.get("CRD")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("PRODUCE_TYPE")).equals(""))
        {
            sql.append("AND C01.PRODUCE_TYPE = '" + Utility.nullToSpace(ht.get("PRODUCE_TYPE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("REQOPTION_TYPE")).equals(""))
        {
            sql.append("AND C01.REQOPTION_TYPE = '" + Utility.nullToSpace(ht.get("REQOPTION_TYPE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("PREREQ_CRS_MK")).equals(""))
        {
            sql.append("AND C01.PREREQ_CRS_MK = '" + Utility.nullToSpace(ht.get("PREREQ_CRS_MK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRS_ITEM")).equals(""))
        {
            sql.append("AND C01.CRS_ITEM = '" + Utility.nullToSpace(ht.get("CRS_ITEM")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("LAB_TIMES")).equals(""))
        {
            sql.append("AND C01.LAB_TIMES = '" + Utility.nullToSpace(ht.get("LAB_TIMES")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("TUT_TIMES")).equals(""))
        {
            sql.append("AND C01.TUT_TIMES = '" + Utility.nullToSpace(ht.get("TUT_TIMES")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("MEDIA_REQUEST")).equals(""))
        {
            sql.append("AND C01.MEDIA_REQUEST = '" + Utility.nullToSpace(ht.get("MEDIA_REQUEST")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("TUT_TARGET")).equals(""))
        {
            sql.append("AND C01.TUT_TARGET = '" + Utility.nullToSpace(ht.get("TUT_TARGET")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRS_OUTLINE")).equals(""))
        {
            sql.append("AND C01.CRS_OUTLINE = '" + Utility.nullToSpace(ht.get("CRS_OUTLINE")) + "' ");
        }
        
        if (!Utility.nullToSpace(ht.get("CRS_GUTLINE")).equals(""))
        {
            sql.append("AND C01.CRS_GUTLINE = '" + Utility.nullToSpace(ht.get("CRS_GUTLINE")) + "' ");
        }
        
        if (!Utility.nullToSpace(ht.get("BIBLIOGRAPHY")).equals(""))
        {
            sql.append("AND C01.BIBLIOGRAPHY = '" + Utility.nullToSpace(ht.get("BIBLIOGRAPHY")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("EVAL_MANNER")).equals(""))
        {
            sql.append("AND C01.EVAL_MANNER = '" + Utility.nullToSpace(ht.get("EVAL_MANNER")) + "' ");
        }
        
        if (!Utility.nullToSpace(ht.get("BASIC_KNWLDG")).equals(""))
        {
            sql.append("AND C01.BASIC_KNWLDG = '" + Utility.nullToSpace(ht.get("BASIC_KNWLDG")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRS_BOOK")).equals(""))
        {
            sql.append("AND C01.CRS_BOOK = '" + Utility.nullToSpace(ht.get("CRS_BOOK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("PRO_ACADFIELD")).equals(""))
        {
            sql.append("AND C01.PRO_ACADFIELD = '" + Utility.nullToSpace(ht.get("PRO_ACADFIELD")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("RESERVE_ITEM")).equals(""))
        {
            sql.append("AND C01.RESERVE_ITEM = '" + Utility.nullToSpace(ht.get("RESERVE_ITEM")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("REWORK_ITEM")).equals(""))
        {
            sql.append("AND C01.REWORK_ITEM = '" + Utility.nullToSpace(ht.get("REWORK_ITEM")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("J_CRS_MK")).equals(""))
        {
            sql.append("AND C01.J_CRS_MK = '" + Utility.nullToSpace(ht.get("J_CRS_MK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("EST_RESULT_MK")).equals(""))
        {
            sql.append("AND C01.EST_RESULT_MK = '" + Utility.nullToSpace(ht.get("EST_RESULT_MK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRS_TYPE")).equals(""))
        {
            sql.append("AND C01.CRS_TYPE = '" + Utility.nullToSpace(ht.get("CRS_TYPE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("TAKE_CRS")).equals(""))
        {
            sql.append("AND C01.TAKE_CRS = '" + Utility.nullToSpace(ht.get("TAKE_CRS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("EXAM_MK")).equals(""))
        {
            sql.append("AND C01.EXAM_MK = '" + Utility.nullToSpace(ht.get("EXAM_MK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("NEW_REWORK_TREK2")).equals(""))
        {
            sql.append("AND C01.NEW_REWORK_TREK2 = '" + Utility.nullToSpace(ht.get("NEW_REWORK_TREK2")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("EST_SMS")).equals(""))
        {
            sql.append("AND C01.EST_SMS = '" + Utility.nullToSpace(ht.get("EST_SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("LAB_RMK")).equals(""))
        {
            sql.append("AND C01.LAB_RMK = '" + Utility.nullToSpace(ht.get("LAB_RMK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("RMK")).equals(""))
        {
            sql.append("AND C01.RMK = '" + Utility.nullToSpace(ht.get("RMK")) + "' ");
        }
        
        if (!Utility.nullToSpace(ht.get("ROWSTAMP")).equals(""))
        {
            sql.append("AND C01.ROWSTAMP = '" + Utility.nullToSpace(ht.get("ROWSTAMP")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("UPD_USER_ID")).equals(""))
        {
            sql.append("AND C01.UPD_USER_ID = '" + Utility.nullToSpace(ht.get("UPD_USER_ID")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("UPD_DATE")).equals(""))
        {
            sql.append("AND C01.UPD_DATE = '" + Utility.nullToSpace(ht.get("UPD_DATE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("UPD_TIME")).equals(""))
        {
            sql.append("AND C01.UPD_TIME = '" + Utility.nullToSpace(ht.get("UPD_TIME")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("UPD_MK")).equals(""))
        {
            sql.append("AND C01.UPD_MK = '" + Utility.nullToSpace(ht.get("UPD_MK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("BOOK_INVNTRY")).equals(""))
        {
            sql.append("AND C01.BOOK_INVNTRY = '" + Utility.nullToSpace(ht.get("BOOK_INVNTRY")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("INVNTRY_DATE")).equals(""))
        {
            sql.append("AND C01.INVNTRY_DATE = '" + Utility.nullToSpace(ht.get("INVNTRY_DATE")) + "' ");
        }

        if (!orderBy.equals(""))
        {
            String[]	orderByArray	= orderBy.split(",");

            orderBy	= "";

            for (int i = 0; i < orderByArray.length; i++)
            {
                orderByArray[i]	= "C01." + orderByArray[i].trim();

                if (i == 0)
                {
                    orderBy	+= "ORDER BY ";
                }
                else
                {
                    orderBy	+= ", ";
                }

                orderBy	+= orderByArray[i].trim();
            }

            sql.append(orderBy.toUpperCase());
            orderBy	= "";
        }

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    /**
     * JOIN 其它 Cout001,Cout002,Syst001,Syst003 將欄位的中文化
     * @Hashtable ht 和 SMS_SD 學期的開始日期 做為條件值
     *
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *
     */
    public Vector getCout001Cout002Syst001Syst003ForUse(Hashtable ht, String SMSSD) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            " SELECT COUT001.AYEAR, COUT001.SMS, SYS_ASYS.CODE_NAME AS SET_TYPE1 , SYS_ASYS.CODE AS SET_TYPE, "
            + " SYS_FACULTY.F_CODE AS F_CODE, SYS_FACULTY.CODE_NAME AS FACULTY_CODE1, "
            + " SYS_CHAR.CODE_NAME AS CRS_CHAR1, SYS_CHAR.CODE AS CRS_CHAR, "
            + " COUT001.CRSNO, COUT002.CRS_NAME, COUT002.CRD, COUT001.NEW_REWORK AS N_CODE, N_NAME.CODE_NAME AS NEW_REWORK , "
            + " COUT001.PRODUCE_TYPE AS P_CODE, P_NAME.CODE_NAME AS PRODUCE_TYPE "
            + " FROM   COUT001 JOIN COUT002 ON COUT002.CRSNO= COUT001.CRSNO, "
            + " (SELECT CODE, CODE_NAME FROM SYST001 WHERE KIND= 'NEW_REWORK') N_NAME, "
            + " (SELECT CODE, CODE_NAME FROM SYST001 WHERE KIND= 'PRODUCE_TYPE') P_NAME, "
            + " (SELECT CODE, CODE_NAME FROM SYST001 WHERE KIND= 'SMS') SYS_SMS, "
            + " (SELECT CODE, CODE_NAME FROM SYST001 WHERE KIND='ASYS') SYS_ASYS, "
            + " (SELECT CODE, CODE_NAME FROM SYST001 WHERE KIND='CRS_CHAR') SYS_CHAR, "
            + " (SELECT ASYS AS CODE, FACULTY_CODE AS F_CODE, FACULTY_NAME AS CODE_NAME FROM SYST003) SYS_FACULTY "
            + " WHERE  1    =       1   " + " AND COUT001.NEW_REWORK= N_NAME.CODE  "
            + " AND COUT001.PRODUCE_TYPE= P_NAME.CODE  " + " AND COUT001.SMS = SYS_SMS.CODE  "
            + " AND COUT001.SET_TYPE = SYS_ASYS.CODE " + " AND COUT001.CRS_CHAR = SYS_CHAR.CODE "
            + " AND COUT001.FACULTY_CODE = SYS_FACULTY.F_CODE  " + " AND COUT001.SET_TYPE = SYS_FACULTY.CODE  ");

        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            sql.append("AND COUT001.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append("AND COUT001.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        /** == 查詢條件 ED == */
        if (!orderBy.equals(""))
        {
            String[]	orderByArray	= orderBy.split(",");

            orderBy	= "";

            for (int i = 0; i < orderByArray.length; i++)
            {
                orderByArray[i]	= "COUT001." + orderByArray[i].trim();

                if (i == 0)
                {
                    orderBy	+= "ORDER BY ";
                }
                else
                {
                    orderBy	+= ", ";
                }

                orderBy	+= orderByArray[i].trim();
            }

            sql.append(orderBy.toUpperCase());
            orderBy	= "";
        }

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                rowHt.put("SMSSD", SMSSD);
                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    private String getCPRODUCE_TYPE(DBManager dbManager, Connection conn, String PRODUCE_TYPE) throws Exception
    {
	String[]	PRODUCE_TYPE_ARY	= Utility.split(PRODUCE_TYPE, ",");

	String		CPRODUCE_TYPE	= "";

	DBResult					rs 	= null;

        try
        {
            SYST001DAO	SYST001	= new SYST001DAO(dbManager, conn);

            for (int i = 0; i < PRODUCE_TYPE_ARY.length; i++)
            {
                if (i > 0)
                {
                    CPRODUCE_TYPE	+= ",";
                }

                SYST001.setResultColumn("CODE_NAME ");
                SYST001.setKIND("PRODUCE_CHOOSE");
                SYST001.setCODE(PRODUCE_TYPE_ARY[i]);

                rs	= SYST001.query();

                if (rs.next())
                {
                    CPRODUCE_TYPE	+= rs.getString("CODE_NAME");
                }
                else
                {
                    CPRODUCE_TYPE	+= PRODUCE_TYPE_ARY[i];
                }
            }

            return CPRODUCE_TYPE;
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

    
    /**
     * JOIN 其它 Cout001,Cout002 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout001Cout002ForGetCrs(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "  SELECT  A.CRSNO AS CRSNO , B.CRS_NAME  AS CCRSNO  " +
        	"  FROM COUT001 A  "  +
            "  JOIN COUT002 B ON A.CRSNO = B.CRSNO " +
            "  WHERE 1=1 AND A.LAB_TIMES >0  AND A.EST_RESULT_MK = 'Y' AND A.OPEN1='Y' "); // 排除二軌  2008.11.20 ,2014.07.01更改OPEN1 = 'Y'

        /** == 查詢條件 ST == */
        if (!Utility.checkNull(ht.get("AYEAR"), "").equals(""))
        {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.checkNull(ht.get("SMS"),"").equals(""))
        {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
        
        sql.append("ORDER BY A.CRSNO ");
        
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    /**
     * JOIN 其它 Cout001,Cout002 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout001Cout010Cout011Cout002ForUse(Hashtable ht, DBManager dbm01, Connection conn01)
            throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT DISTINCT COUT001.AYEAR||S1.CODE_NAME AS AYEARSMS,COUT001.DATA_LOCK,COUT001.AYEAR,COUT001.SMS,S1.CODE_NAME AS SMS_NAME,COUT001.CRSNO,COUT002.CRS_NAME,COUT002.CRD,COUT002.FACULTY_CODE, " +
            "COUT001.PRODUCE_TYPE,COUT001.NEW_REWORK,SYST003.FACULTY_NAME,S2.CODE_NAME AS NEW_REWORK1, COUT001.CRS_STATUS, S3.CODE_NAME AS CRS_STATUS_NAME, " +
            "(SELECT COUNT(1) " +
    		"FROM COUT003 C03 " +
    		"WHERE C03.RESULT_MK = 'Y' " +
    		"AND C03.JOB_TYPE IN ('11','12') " +
    		"AND C03.AYEAR = COUT001.AYEAR " +
    		"AND C03.SMS = COUT001.SMS " +
    		"AND C03.CRSNO = COUT001.CRSNO ) AS RESULT_NUM " +
            "FROM COUT001 JOIN COUT002 ON COUT001.CRSNO = COUT002.CRSNO " +
            "JOIN SYST001 S2 ON S2.KIND='NEW_REWORK' AND S2.CODE = COUT001.NEW_REWORK " +
            "JOIN SYST001 S1 ON S1.KIND='SMS' AND S1.CODE=COUT001.SMS " +
            "JOIN SYST001 S3 ON S3.KIND = 'CRS_STATUS' AND S3.CODE = COUT001.CRS_STATUS " +
            "LEFT JOIN SYST003 ON SYST003.FACULTY_CODE = COUT002.FACULTY_CODE ");

        /** == 查詢條件 ST == */

        // 科目相同資料
        if (!Utility.nullToSpace(ht.get("CODE")).equals("COUT010"))
        {
            sql.append("LEFT JOIN COUT010 ON COUT002.CRSNO = COUT010.CRSNO " + "WHERE 1  =  1 ");
        }
        else if (!Utility.nullToSpace(ht.get("CODE")).equals("COUT011"))
        {
            sql.append("LEFT JOIN COUT011 ON COUT002.CRSNO = COUT011.CRSNO " + "WHERE 1  =  1 ");
        }
        else if (!Utility.nullToSpace(ht.get("CODE")).equals("COUT003"))
        {
            sql.append("LEFT JOIN COUT003 ON COUT001.CRSNO = COUT003.CRSNO " + "WHERE 1  =  1 ");
        }

        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            sql.append("AND COUT001.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append("AND COUT001.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append("AND COUT001.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append("AND COUT001.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
        {
        	sql.append("AND COUT001.NEW_REWORK != '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("TRACK_CTRL")).equals(""))
        {        
	        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1")){
	            sql.append("AND COUT001.open1 = 'Y' ");
	        }
	        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2")){
	            sql.append("AND COUT001.open3 = 'Y' AND COUT001.open1 = 'N' ");
	        }
        }
        
        /** == 查詢條件 ED == */
        if (!orderBy.equals(""))
        {
            String[]	orderByArray	= orderBy.split(",");

            orderBy	= "";

            for (int i = 0; i < orderByArray.length; i++)
            {
                orderByArray[i]	= "COUT001." + orderByArray[i].trim();

                if (i == 0)
                {
                    orderBy	+= "ORDER BY ";
                }
                else
                {
                    orderBy	+= ", ";
                }

                orderBy	+= orderByArray[i].trim();
            }

            sql.append(orderBy.toUpperCase());
            orderBy	= "";
        }

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                    {
                        rowHt.put(rs.getColumnName(i), getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i)));
                    }
                    else
                    {
                        rowHt.put(rs.getColumnName(i), rs.getString(i));
                    }
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    
    
    /**
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout011Query(Hashtable ht, DBManager dbm01, Connection conn01)
            throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append("SELECT DISTINCT COUT001.DATA_LOCK,COUT001.AYEAR,COUT001.SMS,S1.CODE_NAME AS SMS_NAME,COUT002.CRSNO,COUT002.CRS_NAME,COUT002.CRD,COUT002.FACULTY_CODE, ");
        sql.append("COUT001.PRODUCE_TYPE,COUT001.NEW_REWORK,SYST003.FACULTY_NAME,S2.CODE_NAME AS NEW_REWORK1,  ");
        sql.append("(SELECT COUNT(1)  ");
        sql.append("FROM COUT003 C03  ");
        sql.append("WHERE C03.RESULT_MK = 'Y'  ");
        sql.append("AND C03.JOB_TYPE IN ('11','12')  ");
        sql.append("AND C03.AYEAR = COUT001.AYEAR  ");
        sql.append("AND C03.SMS = COUT001.SMS  ");
        sql.append("AND C03.CRSNO = COUT001.CRSNO ) AS RESULT_NUM  ");
        sql.append("FROM COUT002 ");
        sql.append("LEFT JOIN COUT001 ON COUT001.CRSNO = COUT002.CRSNO  ");
        sql.append("LEFT JOIN SYST001 S2 ON S2.KIND='NEW_REWORK' AND S2.CODE = COUT001.NEW_REWORK  ");
        sql.append("LEFT JOIN SYST001 S1 ON S1.KIND='SMS' AND S1.CODE=COUT001.SMS  ");
        sql.append("LEFT JOIN SYST003 ON SYST003.FACULTY_CODE = COUT002.FACULTY_CODE  ");
        sql.append("LEFT JOIN COUT011 ON COUT002.CRSNO = COUT011.CRSNO  ");
        sql.append("WHERE  ");
        sql.append("( ");
        sql.append("		COUT001.AYEAR = '"+Utility.nullToSpace(ht.get("AYEAR"))+"'  ");
        sql.append("	AND COUT001.SMS = '"+Utility.nullToSpace(ht.get("SMS"))+"'  ");
        if( !"".equals(Utility.nullToSpace(ht.get("CRSNO")))){
        	sql.append("	AND COUT001.CRSNO = '"+Utility.nullToSpace(ht.get("CRSNO"))+"'  ");
        }
        if( !"".equals(Utility.nullToSpace(ht.get("FACULTY_CODE")))){
        	sql.append("	AND COUT001.FACULTY_CODE = '"+Utility.nullToSpace(ht.get("FACULTY_CODE"))+"'  ");
        }
        sql.append(")  ");
        sql.append("OR  ");
        sql.append("(  ");
        sql.append("		COUT002.CRSNO IN ( 'I10001','J10001','K10001')  ");
        if( !"".equals(Utility.nullToSpace(ht.get("CRSNO")))){
        	sql.append("	AND COUT002.CRSNO = '"+Utility.nullToSpace(ht.get("CRSNO"))+"'  ");
        }
        sql.append(") ");
        //if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        //{
        //    sql.append(" AND COUT001.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        //}
        sql.append("ORDER BY COUT002.CRSNO ");

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                    {
                        rowHt.put(rs.getColumnName(i), getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i)));
                    }
                    else
                    {
                        rowHt.put(rs.getColumnName(i), rs.getString(i));
                    }
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

	
    /**
     * JOIN 其它 Cout001,Cout002,Cout100 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception       //COUT008完整
     */
    public Vector getCout001Cou002Cout100ForUse(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        boolean	usecount	= false;

        sql.append(
            "SELECT B.FACULTY_CODE,D.FACULTY_NAME,A.AYEAR,A.SMS,Z2.CODE_NAME AS CSMS,B.CRS_NAME,A.CRSNO,A.CRD,A.OPEN_YN "
			+ "     ,DECODE(A.SMS, '1', '1','2', '2','3', '0') AS SMS2, A.NEW_REWORK NEW_REWORK_CODE "
            + "     ,A.PRODUCE_TYPE,Z1.CODE_NAME AS CRS_CHAR,C.DISCIPLINE_NAME,Z3.CODE_NAME AS NEW_REWORK "
            + "     ,A.TUT_TIMES,A.LAB_TIMES,A.RESERVE_ITEM,A.REWORK_ITEM ,E.CRS_GUTLINE,E.CRS_OUTLINE,E.TUT_TARGET  ");

        if ((!Utility.nullToSpace(ht.get("UseCount")).equals(""))
                && (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
                && (!Utility.nullToSpace(ht.get("AYEAR")).equals("")))
        {
            sql.append(" ,ZA.COUNT_CRSNO,ZB.COUNT_DISCIP ");
            usecount	= true;
        }

        if (!Utility.nullToSpace(ht.get("get_cout008")).equals(""))
        {
            sql.append(
                " ,C8.HW_REQUEST, C8.SW_REQUEST,C8.CLSSRM_TIMES,C8.DATE_NAME1,C8.DATE_NAME2,C8.HW_REQUEST,C8.IS_ST_LAB "
                + " ,C8.TEL,C8.ITEM_CONTACT_TCH,C8.PER_HOURS,C8.RMK,C8.SOFTWARE_NAME1,C8.SOFTWARE_NAME2,C8.SUPTECH_CRS_TYPE,C8.SUPTECH_CRS_RMK,C8.SUPTECH_CRS_RMK "
                + " ,C8.SW_REQUEST,C8.USER_CLSSRM ");
        }

        sql.append("FROM COUT001 A JOIN COUT002 B ON A.CRSNO=B.CRSNO   "
                   + "    JOIN COUT100 C ON C.FACULTY_CODE=A.FACULTY_CODE AND C.DISCIPLINE_CODE=A.DISCIPLINE_CODE   "
                   + "    JOIN SYST003 D ON C.FACULTY_CODE=D.FACULTY_CODE  "
                   + "    LEFT JOIN COUT005 E ON A.AYEAR=E.AYEAR AND A.CRSNO=E.CRSNO AND A.SMS=E.SMS   "
                   + "    JOIN SYST001 Z1 ON Z1.KIND='CRS_CHAR' AND Z1.CODE=A.CRS_CHAR   "
                   + "    JOIN SYST001 Z2 ON Z2.KIND='SMS' AND Z2.CODE=A.SMS   "
                   + "    JOIN SYST001 Z3 ON Z3.KIND='NEW_REWORK' AND Z3.CODE=A.NEW_REWORK  "
        );
        
        if (usecount == true)
        {
            sql.append(
                "JOIN ( SELECT B.DISCIPLINE_CODE,B.CRSNO,COUNT(A.CRSNO) AS COUNT_CRSNO "
                + "       FROM COUT001 A JOIN COUT002 B ON A.CRSNO=B.CRSNO " + "       WHERE B.FACULTY_CODE= '"
                + ht.get("FACULTY_CODE").toString() + "' " + "             AND A.AYEAR BETWEEN '" + ht.get("AYEAR")
                + "' AND '0" + (Integer.parseInt(ht.get("AYEAR").toString()) + 4) + "' "
                + "       GROUP BY B.DISCIPLINE_CODE,B.CRS_NAME,B.CRSNO ) ZA ON ZA.CRSNO=A.CRSNO AND ZA.DISCIPLINE_CODE=B.DISCIPLINE_CODE "
                + "JOIN ( SELECT B.DISCIPLINE_CODE,COUNT(B.CRSNO) AS COUNT_DISCIP FROM COUT002 B JOIN COUT001 A ON A.CRSNO=B.CRSNO WHERE A.FACULTY_CODE= '"
                + ht.get("FACULTY_CODE") + "' " + "             AND A.AYEAR BETWEEN '" + ht.get("AYEAR") + "' AND '0"
                + (Integer.parseInt(ht.get("AYEAR").toString()) + 4) + "' "
                + "       GROUP BY DISCIPLINE_CODE ) ZB ON ZB.DISCIPLINE_CODE=B.DISCIPLINE_CODE ");
        }

        if (!Utility.nullToSpace(ht.get("get_cout008")).equals(""))
        {
            sql.append("        JOIN COUT008 C8 ON A.AYEAR=C8.AYEAR AND A.SMS=C8.SMS AND A.CRSNO=C8.CRSNO   ");
        }

        // COU102/COU103 CRS_STATUS<>'3' <>'4'   其他則為持原來的<>'5'
        if (!Utility.nullToSpace(ht.get("get_cout008")).equals(""))
        {
        	sql.append(" WHERE A.CRS_STATUS IN ('2','5') ");
        	
        }else{
        	sql.append(" WHERE "+(Utility.nullToSpace(ht.get("PRO_CODE")).equals("")?"A.CRS_STATUS='5' AND A.EST_RESULT_MK='Y' ":"A.CRS_STATUS NOT IN ('3','4') "));
        }
        

        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            if (!Utility.nullToSpace(ht.get("single_year")).equals(""))
            {
                sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
            }
            else
            {
                int ayear = (Integer.parseInt(Utility.nullToSpace(ht.get("AYEAR"))) + 3);  // 含當學年度共4年計畫
                String year = String.valueOf(ayear);
                if(ayear < 100){
                	year = "0" + ayear;
                }
            	sql.append(" AND A.AYEAR BETWEEN  '" + Utility.nullToSpace(ht.get("AYEAR")) + "' AND '"
                           + year + "' ");
            }
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }
        
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1"))
        {
            sql.append(" AND A.open1 = 'Y' ");
        }
        
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2"))
        {
            sql.append(" AND A.open1 <>  'Y' AND A.open3 = 'Y' ");
        }
        sql.append(" ORDER BY C.DISCIPLINE_NAME, A.AYEAR, SMS2, A.CRSNO ");

        DBResult	rs	= null;
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    if (rs.getColumnName(i).equals("AYEAR"))
                    {
                        rowHt.put("AYEAR1", String.valueOf(Integer.parseInt(rs.getString(i)) + 4));
                    }

                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    /**
     * JOIN 其它 Cout001,Cout002,Cout100 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout001Cou002Cout100ForUse_cou103r(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT DECODE(A.OPEN1, 'Y','四次面授','N','多次面授') AS GHOST, A.OPEN3, A.AYEAR,A.SMS,Z2.CODE_NAME AS CSMS,C.DISCIPLINE_NAME,C.DISCIPLINE_ENG,C.DISCIPLINE_CODE,D.FACULTY_CODE,D.FACULTY_NAME,D.FACULTY_ENG_NAME "
            + ",B.CRSNO,B.CRS_NAME,B.CRS_ENG,A.CRD  "
            + ",A.PRODUCE_TYPE,Z3.CODE_NAME AS CPRODUCE_TYPE  ,Z1.CODE_NAME AS CRS_CHAR,A.CRS_CHAR AS CRS_CHAR_CODE "
            + ",A.NEW_REWORK ,Z4.CODE_NAME AS CNEW_REWORK " 
            + "FROM COUT001 A " 
            + "JOIN COUT002 B ON A.CRSNO=B.CRSNO "
            + "JOIN COUT100 C ON C.FACULTY_CODE=A.FACULTY_CODE AND C.DISCIPLINE_CODE=A.DISCIPLINE_CODE "
            + "JOIN SYST003 D ON C.FACULTY_CODE=D.FACULTY_CODE "
            + "JOIN SYST001 Z1 ON Z1.KIND='CRS_CHAR' AND Z1.CODE=A.CRS_CHAR "
            + "JOIN SYST001 Z2 ON Z2.KIND='SMS' AND Z2.CODE=A.SMS "
            + "LEFT JOIN SYST001 Z3 ON Z3.KIND='PRODUCE_CHOOSE' AND Z3.CODE=A.PRODUCE_TYPE "
            + "JOIN SYST001 Z4 ON Z4.KIND='NEW_REWORK' AND Z4.CODE=A.NEW_REWORK " + "WHERE "+(Utility.nullToSpace(ht.get("PRO_CODE")).equals("")?"A.CRS_STATUS='5' AND A.EST_RESULT_MK='Y' ":"A.CRS_STATUS NOT IN ('3','4') ")
        );

        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
        	// 原103r會在最後一個else裡頭,現在改成不用4年
			if("COU103R".equals(Utility.nullToSpace(ht.get("PRO_CODE"))))
			{
				sql.append(" AND A.AYEAR='" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
			}else{
				if (!Utility.nullToSpace(ht.get("single_year")).equals("")||!Utility.nullToSpace(ht.get("PRO_CODE")).equals("COU103R"))
	            {
	                sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
	            }
	            else if (!Utility.nullToSpace(ht.get("AYEAR2")).equals(""))
	            {
	                sql.append(" AND A.AYEAR BETWEEN  '" + Utility.nullToSpace(ht.get("AYEAR")) + "' AND '"
	                           + Utility.nullToSpace(ht.get("AYEAR2")) + "' ");
	            }
	            else
	            {
	            	int ayear = (Integer.parseInt(Utility.nullToSpace(ht.get("AYEAR"))) + 3);
	                String year = String.valueOf(ayear);
	                if(ayear < 100){
	                	year = "0" + ayear;
	                }
	            	sql.append(" AND A.AYEAR BETWEEN  '" + Utility.nullToSpace(ht.get("AYEAR")) + "' AND '" + year + "' ");
	            }
			}
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
        //20100325 aleck 加軌利條件
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1"))
        {
            sql.append(" AND A.open1 = 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2"))
        {
            sql.append(" AND A.open1 <> 'Y' ");
        }

        
		//原本條件加在這     2008/12/13 by barry
        // sql.append(" and a.open3 <> 'Y' ");
        if (Utility.nullToSpace(ht.get("SQL")).equals(""))
        {	// order by 學類
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),C.DISCIPLINE_CODE,D.FACULTY_CODE,B.CRSNO,A.NEW_REWORK ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("1"))
        {	// order by 學系
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),D.FACULTY_CODE,C.DISCIPLINE_CODE,B.CRSNO,A.NEW_REWORK ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("2"))
        {
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),D.FACULTY_CODE,A.NEW_REWORK,B.CRSNO ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("3"))
        {	// 學類 cou101r專用
            sql.append(" ORDER BY C.DISCIPLINE_CODE,D.FACULTY_CODE,A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),A.NEW_REWORK,B.CRSNO ");
        }

        DBResult	rs	= null;
        System.out.println(sql.toString());
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }    

    /**
     * JOIN 其它 Cout001,Cout002,Cout100 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout001Cou002Cout100ForUse_cou120r(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT A.OPEN3, A.AYEAR,A.SMS,Z2.CODE_NAME AS CSMS,C.DISCIPLINE_NAME,C.DISCIPLINE_ENG,C.DISCIPLINE_CODE,D.FACULTY_CODE,D.FACULTY_NAME,D.FACULTY_ENG_NAME "
            + ",B.CRSNO,B.CRS_NAME,B.CRS_ENG,A.CRD  "
            + ",A.PRODUCE_TYPE,Z3.CODE_NAME AS CPRODUCE_TYPE  ,Z1.CODE_NAME AS CRS_CHAR,A.CRS_CHAR AS CRS_CHAR_CODE "
            + ",A.NEW_REWORK ,Z4.CODE_NAME AS CNEW_REWORK " 
            + "FROM COUT001 A " 
            + "JOIN COUT002 B ON A.CRSNO=B.CRSNO "
            + "JOIN COUT100 C ON C.FACULTY_CODE=A.FACULTY_CODE AND C.DISCIPLINE_CODE=A.DISCIPLINE_CODE "
            + "JOIN SYST003 D ON C.FACULTY_CODE=D.FACULTY_CODE "
            + "JOIN SYST001 Z1 ON Z1.KIND='CRS_CHAR' AND Z1.CODE=A.CRS_CHAR "
            + "JOIN SYST001 Z2 ON Z2.KIND='SMS' AND Z2.CODE=A.SMS "
            + "LEFT JOIN SYST001 Z3 ON Z3.KIND='PRODUCE_TYPE' AND Z3.CODE=A.PRODUCE_TYPE "
            + "JOIN SYST001 Z4 ON Z4.KIND='NEW_REWORK' AND Z4.CODE=A.NEW_REWORK " + "WHERE "+(Utility.nullToSpace(ht.get("PRO_CODE")).equals("")?"A.CRS_STATUS='5' AND A.EST_RESULT_MK='Y' ":"A.CRS_STATUS NOT IN ('3','4') ")
        );

        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
        	// 原103r會在最後一個else裡頭,現在改成不用4年
			if("COU103R".equals(Utility.nullToSpace(ht.get("PRO_CODE"))))
			{
				sql.append(" AND A.AYEAR='" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
			}else{
				if (!Utility.nullToSpace(ht.get("single_year")).equals("")||!Utility.nullToSpace(ht.get("PRO_CODE")).equals("COU103R"))
	            {
	                sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
	            }
	            else if (!Utility.nullToSpace(ht.get("AYEAR2")).equals(""))
	            {
	                sql.append(" AND A.AYEAR BETWEEN  '" + Utility.nullToSpace(ht.get("AYEAR")) + "' AND '"
	                           + Utility.nullToSpace(ht.get("AYEAR2")) + "' ");
	            }
	            else
	            {
	            	int ayear = (Integer.parseInt(Utility.nullToSpace(ht.get("AYEAR"))) + 3);
	                String year = String.valueOf(ayear);
	                if(ayear < 100){
	                	year = "0" + ayear;
	                }
	            	sql.append(" AND A.AYEAR BETWEEN  '" + Utility.nullToSpace(ht.get("AYEAR")) + "' AND '" + year + "' ");
	            }
			}
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
       

        //20100325 aleck 加軌利條件
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1"))
        {
            //sql.append(" AND A.open3 <> 'Y' ");
        	sql.append(" AND A.open1 = 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2"))
        {
            sql.append(" AND A.open3 = 'Y' ");
        }
        
		//原本條件加在這     2008/12/13 by barry
        // sql.append(" and a.open3 <> 'Y' ");
        if (Utility.nullToSpace(ht.get("SQL")).equals(""))
        {	// order by 學類
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),C.DISCIPLINE_CODE,D.FACULTY_CODE,B.CRSNO,A.NEW_REWORK ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("1"))
        {	// order by 學系
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),D.FACULTY_CODE,C.DISCIPLINE_CODE,B.CRSNO,A.NEW_REWORK ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("2"))
        {
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),D.FACULTY_CODE,A.NEW_REWORK,B.CRSNO ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("3"))
        {	// 學類 cou101r專用
            sql.append(" ORDER BY C.DISCIPLINE_CODE,D.FACULTY_CODE,A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),A.NEW_REWORK,B.CRSNO ");
        }

        DBResult	rs	= null;
        System.out.println(sql.toString());
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }
    
 
    /**
     * JOIN 其它 Cout001,Cout002,Cout100 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout001Cou002Cout100ForUse_2(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT A.OPEN3, A.AYEAR,A.SMS,Z2.CODE_NAME AS CSMS,C.DISCIPLINE_NAME,C.DISCIPLINE_ENG,C.DISCIPLINE_CODE,D.FACULTY_CODE,D.FACULTY_NAME,D.FACULTY_ENG_NAME "
            + ",B.CRSNO,B.CRS_NAME,B.CRS_ENG,A.CRD  "
            + ",A.PRODUCE_TYPE,Z3.CODE_NAME AS CPRODUCE_TYPE  ,Z1.CODE_NAME AS CRS_CHAR,A.CRS_CHAR AS CRS_CHAR_CODE "
            + ",A.NEW_REWORK ,Z4.CODE_NAME AS CNEW_REWORK " 
            + "FROM COUT001 A JOIN COUT002 B ON A.CRSNO=B.CRSNO "
            + "JOIN COUT100 C ON C.FACULTY_CODE=B.FACULTY_CODE AND C.DISCIPLINE_CODE=B.DISCIPLINE_CODE "
            + "JOIN SYST003 D ON C.FACULTY_CODE=D.FACULTY_CODE "
            + "JOIN SYST001 Z1 ON Z1.KIND='CRS_CHAR' AND Z1.CODE=A.CRS_CHAR "
            + "JOIN SYST001 Z2 ON Z2.KIND='SMS' AND Z2.CODE=A.SMS "
            + "LEFT JOIN SYST001 Z3 ON Z3.KIND='PRODUCE_TYPE' AND Z3.CODE=A.PRODUCE_TYPE "
            + "JOIN SYST001 Z4 ON Z4.KIND='NEW_REWORK' AND Z4.CODE=A.NEW_REWORK " + "WHERE "+(Utility.nullToSpace(ht.get("PRO_CODE")).equals("")?"A.CRS_STATUS='5' AND A.EST_RESULT_MK='Y' ":"A.CRS_STATUS NOT IN ('3','4') ")
        );

        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
        	// 原103r會在最後一個else裡頭,現在改成不用4年
			if("COU103R".equals(Utility.nullToSpace(ht.get("PRO_CODE"))))
			{
				sql.append(" AND A.AYEAR='" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
			}else{
				if (!Utility.nullToSpace(ht.get("single_year")).equals("")||!Utility.nullToSpace(ht.get("PRO_CODE")).equals("COU103R"))
	            {
	                sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
	            }
	            else if (!Utility.nullToSpace(ht.get("AYEAR2")).equals(""))
	            {
	                sql.append(" AND A.AYEAR BETWEEN  '" + Utility.nullToSpace(ht.get("AYEAR")) + "' AND '"
	                           + Utility.nullToSpace(ht.get("AYEAR2")) + "' ");
	            }
	            else
	            {
	            	int ayear = (Integer.parseInt(Utility.nullToSpace(ht.get("AYEAR"))) + 3);
	                String year = String.valueOf(ayear);
	                if(ayear < 100){
	                	year = "0" + ayear;
	                }
	            	sql.append(" AND A.AYEAR BETWEEN  '" + Utility.nullToSpace(ht.get("AYEAR")) + "' AND '" + year + "' ");
	            }
			}
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
       

        if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
        {
            if (ht.get("NEW_REWORK_STR").toString().equals("新開修訂"))
            {
                //sql.append("AND (A.NEW_REWORK = '1' OR A.NEW_REWORK = '2') ");
				//上面為原條件，新條件如下      2008/12/20  by barry
				sql.append("AND (A.NEW_REWORK = '1' OR A.NEW_REWORK = '6') ");
				//COU115R改成依一軌二軌判斷     2008/12/13 by barry
				if(ht.get("PRINT_TYPE").toString().equals("1"))
					sql.append(" and a.open1 = 'Y' ");	//一軌
				else
					sql.append(" and a.open3 = 'Y' ");	//二軌
            }
            else
            {
                //非COU115R的部份將條件加回     2008/12/13 by barry
				sql.append("AND A.NEW_REWORK = '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
				//sql.append(" and a.open3 <> 'Y' ");
				sql.append(" AND A.open1 = 'Y' ");
            }
        }else{
			//非COU115R的部份將條件加回     2008/12/13 by barry
			//sql.append(" and a.open3 <> 'Y' ");
			sql.append(" AND A.open1 = 'Y' ");
		}
		//原本條件加在這     2008/12/13 by barry
        // sql.append(" and a.open3 <> 'Y' ");
        if (Utility.nullToSpace(ht.get("SQL")).equals(""))
        {	// order by 學類
            
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("1"))
        {	// order by 學系
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),D.FACULTY_CODE,C.DISCIPLINE_CODE,DECODE(A.NEW_REWORK,'1','1','6','2','2','3','4','4','5','5','3','6'),B.CRSNO ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("2"))
        {
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),D.FACULTY_CODE,DECODE(A.NEW_REWORK,'1','1','6','2','2','3','4','4','5','5','3','6'),B.CRSNO ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("3"))
        {	// 學類 cou101r專用
            sql.append(" ORDER BY C.DISCIPLINE_CODE,D.FACULTY_CODE,A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),DECODE(A.NEW_REWORK,'1','1','6','2','2','3','4','4','5','5','3','6'),B.CRSNO ");
        }

        DBResult	rs	= null;        
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    /**
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCou115rQuery(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT " +
            "A.OPEN3, A.AYEAR,A.SMS,Z2.CODE_NAME AS CSMS,C.DISCIPLINE_NAME,C.DISCIPLINE_ENG,C.DISCIPLINE_CODE," +
            "D.FACULTY_CODE,D.FACULTY_NAME,D.FACULTY_ENG_NAME, " +
            "B.CRSNO,B.CRS_NAME,B.CRS_ENG,A.CRD,  " +
            "A.PRODUCE_TYPE,Z3.CODE_NAME AS CPRODUCE_TYPE  ,Z1.CODE_NAME AS CRS_CHAR,A.CRS_CHAR AS CRS_CHAR_CODE, " +
            "A.NEW_REWORK ,Z4.CODE_NAME AS CNEW_REWORK "  +
            "FROM COUT001 A " +
            "JOIN COUT002 B ON A.CRSNO=B.CRSNO " +
            "JOIN COUT100 C ON C.FACULTY_CODE=B.FACULTY_CODE AND C.DISCIPLINE_CODE=A.FIELD_CODE " +
            "JOIN SYST003 D ON C.FACULTY_CODE=D.FACULTY_CODE " +
            "JOIN SYST001 Z1 ON Z1.KIND='CRS_CHAR' AND Z1.CODE=A.CRS_CHAR " +
            "JOIN SYST001 Z2 ON Z2.KIND='SMS' AND Z2.CODE=A.SMS " +
            "LEFT JOIN SYST001 Z3 ON Z3.KIND='PRODUCE_TYPE' AND Z3.CODE=A.PRODUCE_TYPE " +
            "JOIN SYST001 Z4 ON Z4.KIND='NEW_REWORK' AND Z4.CODE=A.NEW_REWORK " + 
            "WHERE A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' " +
            "AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' AND A.NEW_REWORK IN ('1','6') "  
        );
        
        if (!Utility.nullToSpace(ht.get("PASS_MK")).equals("")){
			sql.append(" AND A.CRS_STATUS='5' AND A.EST_RESULT_MK='Y' ");
		}else{
			sql.append(" AND A.CRS_STATUS IN ('1','2','5') ");
		}
        
        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }        
      	
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1"))
        {
            sql.append(" AND A.open1 = 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2"))
        {
            sql.append(" AND A.open3 = 'Y' ");
        }  	
        
        sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),C.DISCIPLINE_CODE,D.FACULTY_CODE,DECODE(A.NEW_REWORK,'1','1','6','2','2','3','4','4','5','5','3','6'),B.CRSNO ");

        DBResult	rs	= null;
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }    
    
	//專給COU122R使用  2008/12/05  by barry
	/**
     * JOIN 其它 Cout001,Cout002,Cout100 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout001Cou002Cout100ForUse_3(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT A.OPEN3, A.AYEAR,A.SMS,Z2.CODE_NAME AS CSMS,C.DISCIPLINE_NAME,C.DISCIPLINE_ENG,C.DISCIPLINE_CODE,D.FACULTY_CODE,D.FACULTY_NAME,D.FACULTY_ENG_NAME "
            + ",B.CRSNO,B.CRS_NAME,B.CRS_ENG,A.CRD  "
            + ",A.PRODUCE_TYPE,Z3.CODE_NAME AS CPRODUCE_TYPE  ,Z1.CODE_NAME AS CRS_CHAR,A.CRS_CHAR AS CRS_CHAR_CODE "
            + ",A.NEW_REWORK ,Z4.CODE_NAME AS CNEW_REWORK " 
            + "FROM COUT001 A JOIN COUT002 B ON A.CRSNO=B.CRSNO "
            + "JOIN COUT100 C ON C.FACULTY_CODE=B.FACULTY_CODE AND C.DISCIPLINE_CODE=B.DISCIPLINE_CODE "
            + "JOIN SYST003 D ON C.FACULTY_CODE=D.FACULTY_CODE "
            + "JOIN SYST001 Z1 ON Z1.KIND='CRS_CHAR' AND Z1.CODE=A.CRS_CHAR "
            + "JOIN SYST001 Z2 ON Z2.KIND='SMS' AND Z2.CODE=A.SMS "
            + "LEFT JOIN SYST001 Z3 ON Z3.KIND='PRODUCE_TYPE' AND Z3.CODE=A.PRODUCE_TYPE "
            + "JOIN SYST001 Z4 ON Z4.KIND='NEW_REWORK' AND Z4.CODE=A.NEW_REWORK " 
            + "WHERE A.CRS_STATUS='5' AND A.EST_RESULT_MK='Y' ");

        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            sql.append(" AND A.AYEAR BETWEEN  '" + Utility.nullToSpace(ht.get("AYEAR")) + "' AND '"
                   + Utility.nullToSpace(ht.get("AYEAR2")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
        {
            if (ht.get("NEW_REWORK_STR").toString().equals("新開重製"))
            {
                sql.append("AND (A.NEW_REWORK = '1' OR A.NEW_REWORK = '2') ");
            }
            else
            {
                sql.append("AND A.NEW_REWORK = '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
            }
        }
        
        //20100325 aleck 加軌利條件
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1"))
        {
            sql.append(" AND A.open1 = 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2"))
        {
            sql.append(" AND A.open1 <> 'Y' AND A.open3 = 'Y' ");
        }
        
        if (Utility.nullToSpace(ht.get("SQL")).equals(""))
        {	// order by 學類
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),C.DISCIPLINE_CODE,D.FACULTY_CODE,B.CRSNO,A.NEW_REWORK ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("1"))
        {	// order by 學系
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),D.FACULTY_CODE,C.DISCIPLINE_CODE,B.CRSNO,A.NEW_REWORK ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("2"))
        {
            sql.append(" ORDER BY A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),D.FACULTY_CODE,A.NEW_REWORK,B.CRSNO ");
        }
        else if (Utility.nullToSpace(ht.get("SQL")).equals("3"))
        {	// 學類 cou101r專用
            sql.append(" ORDER BY C.DISCIPLINE_CODE,D.FACULTY_CODE,A.AYEAR,DECODE(A.SMS, '1', '2', '2', '3', '3', '1'),A.NEW_REWORK,B.CRSNO ");
        }

        DBResult	rs	= null;
        System.out.println(sql.toString());
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    /**
     * 此欄位能中文化的皆中文化了
     * JOIN 其它 Cout001,Cout002,Syst001,Syst003 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout001Cout002Syst001Syst003ForUse(Hashtable ht, DBManager dbm01, Connection conn01)
            throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            " SELECT A.AYEAR,A.SMS,A.CRSNO,A.CRD,A.FACULTY_CODE,A.CRS_BOOK,A.CRS_CHAR,A.EST_RESULT_MK,A.EXAM_MK,A.LAB_TIMES,A.NEW_REWORK,A.OPEN1,A.OPEN2,A.OPEN3,A.PRO_ACADFIELD,A.PRODUCE_TYPE,A.REQOPTION_TYPE,A.RESERVE_ITEM,A.REWORK_ITEM,A.TUT_TIMES,A.CLASS_YN,A.OPEN_YN,A.TIME_GROUP,A.TCH_ACADFIELD,A.ROWSTAMP,A.CRS_TIMES,C.CRS_NAME AS CCRSNO,D.CODE_NAME AS CSMS,E.CODE_NAME AS CNEW_REWORK,F.FACULTY_NAME AS CFACULTY_CODE,G.CODE_NAME AS CREQOPTION_TYPE,H.CODE_NAME AS CCRS_CHAR " + " FROM COUT001 A " + " JOIN COUT002 C ON A.CRSNO=C.CRSNO " + " JOIN SYST001 D ON A.SMS=D.CODE AND D.KIND='SMS' " + " JOIN SYST001 E ON A.NEW_REWORK=E.CODE AND E.KIND='NEW_REWORK' " + " JOIN SYST003 F ON A.FACULTY_CODE = F.FACULTY_CODE AND F.ASYS='1' " + " JOIN SYST001 G ON A.REQOPTION_TYPE=G.CODE AND G.KIND='REQOPTION' " + " JOIN SYST001 H ON A.CRS_CHAR=H.CODE AND H.KIND='CRS_CHAR' WHERE 0=0 ");

        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append("AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append("AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }

		//sql.append("AND A.OPEN1 = 'N' ");
		sql.append("AND A.OPEN3 = 'Y' ");

        /** == 查詢條件 ED == */
        sql.append(" ORDER BY A.AYEAR,A.SMS,A.CRSNO,A.FACULTY_CODE");

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                    {
                        rowHt.put(rs.getColumnName(i), getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i)));
                    }
                    else
                    {
                        rowHt.put(rs.getColumnName(i), rs.getString(i));
                    }
                }

		/**統計班級數*/
		rowHt.put("CLA_NUM", getCout022(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO")));


                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    private String getCout022(DBManager dbManager, Connection conn, String AYEAR, String SMS, String CRSNO) throws Exception
    {
    	String CNT 	= "";

	StringBuffer sqlgogogo = new StringBuffer();

	DBResult	rs	= null;

	try
	{
		sqlgogogo.append
		(
			"SELECT COUNT(1) AS CNT FROM (" +
            		" SELECT A.AYEAR,A.SMS,A.CRSNO,A.S_CLASS_TYPE,A.S_CLASS_NUM,A.S_CLASS_ID,A.S_CLASS_NAME,A.WEEK_YN,A.TUT_TIMES, "+
            		" A.S_CLASS_ABRCODE_CODE,A.S_CLASS_CMPS_CODE,A.S_CLASS_CLASS_CODE,A.S_CLASS_TCH_IDNO,A.OPEN_YN,A.DECISION_MK,A.ROWSTAMP , "+
            		" B.CODE_NAME AS CSMS , C.CRS_NAME AS CCRSNO, D.CODE_NAME AS CS_CLASS_TYPE ,E.CODE_NAME AS CWEEK_YN,F.CENTER_NAME AS CS_CLASS_ABRCODE_CODE,G.NAME AS CS_CLASS_TCH_IDNO, "+
            		" H.CODE_NAME AS COPEN_YN,I.CODE_NAME AS CDECISION_MK "+
            		" FROM COUT022 A  "+
            		" JOIN SYST001 B ON A.SMS=B.CODE AND B.KIND='SMS' "+
            		" JOIN COUT002 C ON A.CRSNO=C.CRSNO  "+
            		" JOIN SYST001 D ON A.S_CLASS_TYPE=D.CODE AND D.KIND='SPCLASS_TYPE' "+
            		" JOIN SYST001 E ON A.WEEK_YN=E.CODE AND E.KIND='YN' "+
            		" JOIN SYST002 F ON A.S_CLASS_ABRCODE_CODE=F.CENTER_ABRCODE  "+
            		" JOIN TRAT001 G ON A.S_CLASS_TCH_IDNO=G.IDNO  "+
            		" JOIN SYST001 H ON A.OPEN_YN=H.CODE AND H.KIND='YN' "+
            		" JOIN SYST001 I ON A.DECISION_MK=I.CODE AND I.KIND='YN' "+
            		" WHERE 0=0 "+
                    " AND A.S_CLASS_TYPE != 'A' "
        	);

		sqlgogogo.append("AND A.AYEAR = '" + AYEAR + "' ");

		sqlgogogo.append("AND A.SMS = '" + SMS + "' ");

		sqlgogogo.append("AND A.CRSNO = '" + CRSNO + "' ");

		sqlgogogo.append(") ");

		rs	= dbmanager.getSimpleResultSet(conn);

		rs.open();

		rs.executeQuery(sqlgogogo.toString());

		if (rs.next())
		{
			CNT = rs.getString("CNT");
		}

		return CNT;

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

    /**
     * 登錄各科教師擬聘人數 ()
     * @param ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *         若該欄位有中文名稱，則其 KEY 請加上 _NAME, EX: SMS 其中文欄位請設為 SMS_NAME
     * @throws Exception
     */
    public Vector tra014m(Vector vt, Hashtable ht) throws Exception
    {
        Vector		result	= null;
        DBResult	rs	= null;

        try
        {
            result	= new Vector();

            if (Utility.nullToSpace(ht.get("HAVE_REGISTER")).equals("1"))
            {
                // 未登人數
                sql.append(
                		"SELECT decode(c01.SMS,'3','71','51') AS JOB_TYPE ,C02.CRSNO, C02.CRS_NAME, C01.PLAN_FACULTY_CODE AS FACULTY_CODE, S03.FACULTY_NAME,NVL(R01.SEL_NUM,0) AS SEL_NUM, C01.OPEN3  " +
                		"FROM COUT001 C01, COUT002 C02, SYST003 S03,"+
                		"(SELECT REGT007.CRSNO ,COUNT(REGT007.STNO) AS SEL_NUM "+
                		" FROM REGT007,STUT003 "+
                		" WHERE REGT007.AYEAR = '"+Utility.nullToSpace(ht.get("AYEAR"))+"' "+
                		" AND   REGT007.SMS = '"+Utility.nullToSpace(ht.get("SMS"))+"' "+
                		" AND   REGT007.UNQUAL_TAKE_MK = 'N' AND  REGT007.UNTAKECRS_MK = 'N' AND REGT007.PAYMENT_STATUS  != '1' "+
                		" AND   STUT003.STNO = REGT007.STNO "+
                		" AND   STUT003.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' "+
                		" GROUP BY REGT007.CRSNO ) R01 "+
                		"WHERE C01.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' "+
                		"AND   C01.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' "+
                		"AND C01.CRS_STATUS = '5' "+
                		"AND   C01.EST_RESULT_MK = 'Y' "+
                		"AND   R01.CRSNO(+) = C01.CRSNO "
                );

                if (!Utility.checkNull(ht.get("FACULTY_CODE"), "").equals(""))
                	sql.append("AND C01.PLAN_FACULTY_CODE = '" + Utility.dbStr(ht.get("FACULTY_CODE")) + "' ");
                if (!Utility.checkNull(ht.get("CRSNO"), "").equals(""))
                	sql.append("AND C02.CRSNO = '" + Utility.dbStr(ht.get("CRSNO")) + "' ");
                
                sql.append("AND C01.OPEN1 = 'Y' "); 
                //因應教學創新移除面授次數條件20170607maggie
               	//sql.append("AND (( C01.SMS < '3' AND C01.TUT_TIMES > 0) OR (C01.SMS = '3' AND C01.TUT_TIMES = 0)) ");

                sql.append(" AND C02.CRSNO = C01.CRSNO ");
                sql.append(" AND S03.FACULTY_CODE = C01.PLAN_FACULTY_CODE ");
                sql.append(" AND S03.ASYS = '1' ");
                sql.append("AND 0 = (SELECT COUNT(*) FROM TRAT008 T08 WHERE T08.AYEAR = C01.AYEAR AND T08.SMS = C01.SMS AND T08.CRSNO = C01.CRSNO AND T08.JOB_TYPE = decode(c01.SMS,'3','71','51')  AND T08.CENTER_CODE = '"+ Utility.nullToSpace(ht.get("CENTER_CODE")) + "') ");
                
                sql.append(" UNION ALL ");
                
                sql.append(
                		"SELECT '52' AS JOB_TYPE ,C02.CRSNO, C02.CRS_NAME || '(實習)' AS CRS_NAME, C01.PLAN_FACULTY_CODE AS FACULTY_CODE, S03.FACULTY_NAME,NVL(R01.SEL_NUM,0) AS SEL_NUM, C01.OPEN3  " +
                		"FROM COUT001 C01, COUT002 C02, SYST003 S03,"+
                		"(SELECT REGT007.CRSNO ,COUNT(REGT007.STNO) AS SEL_NUM "+
                		" FROM REGT007,STUT003 "+
                		" WHERE REGT007.AYEAR = '"+Utility.nullToSpace(ht.get("AYEAR"))+"' "+
                		" AND   REGT007.SMS = '"+Utility.nullToSpace(ht.get("SMS"))+"' "+
                		" AND   REGT007.UNQUAL_TAKE_MK = 'N' AND  REGT007.UNTAKECRS_MK = 'N' AND REGT007.PAYMENT_STATUS  != '1' "+
                		" AND   STUT003.STNO = REGT007.STNO "+
                		" AND   STUT003.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' "+
                		" GROUP BY REGT007.CRSNO ) R01 "+
                		"WHERE C01.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' "+
                		"AND   C01.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' "+
                		"AND   C01.CRS_STATUS = '5' "+
                		"AND   C01.EST_RESULT_MK = 'Y' "+
                		"AND   R01.CRSNO(+) = C01.CRSNO "
                );

                if (!Utility.checkNull(ht.get("FACULTY_CODE"), "").equals(""))
                	sql.append("AND C01.PLAN_FACULTY_CODE = '" + Utility.dbStr(ht.get("FACULTY_CODE")) + "' ");
                if (!Utility.checkNull(ht.get("CRSNO"), "").equals(""))
                	sql.append("AND C02.CRSNO = '" + Utility.dbStr(ht.get("CRSNO")) + "' ");
              
               	sql.append("AND C01.OPEN1 = 'Y' ");  
                sql.append("AND C01.LAB_TIMES > 0 ");
                

                sql.append(" AND C02.CRSNO = C01.CRSNO ");
                sql.append(" AND S03.FACULTY_CODE = C01.PLAN_FACULTY_CODE ");
                sql.append(" AND S03.ASYS = '1' ");
                sql.append("AND 0 = (SELECT COUNT(*) FROM TRAT008 T08 WHERE T08.AYEAR = C01.AYEAR AND T08.SMS = C01.SMS AND T08.CRSNO = C01.CRSNO AND T08.JOB_TYPE = '52'  AND T08.CENTER_CODE = '"+ Utility.nullToSpace(ht.get("CENTER_CODE")) + "') ");
                
                sql.append(" UNION ALL ");
                
                sql.append(
                		"SELECT decode(C01.SMS,'3','71','51') AS JOB_TYPE ,C02.CRSNO, C02.CRS_NAME || '("+com.nou.UtilityX.getCouName(2)+")' AS CRS_NAME, C01.PLAN_FACULTY_CODE AS FACULTY_CODE, S03.FACULTY_NAME,NVL(R01.SEL_NUM,0) AS SEL_NUM, C01.OPEN3  " +
                		"FROM COUT001 C01, COUT002 C02, SYST003 S03,"+
                		"(SELECT REGT007.CRSNO ,COUNT(REGT007.STNO) AS SEL_NUM "+
                		" FROM REGT007,STUT003 "+
                		" WHERE REGT007.AYEAR = '"+Utility.nullToSpace(ht.get("AYEAR"))+"' "+
                		" AND   REGT007.SMS = '"+Utility.nullToSpace(ht.get("SMS"))+"' "+
                		" AND   REGT007.UNQUAL_TAKE_MK = 'N' AND  REGT007.UNTAKECRS_MK = 'N' AND REGT007.PAYMENT_STATUS  != '1' "+
                		" AND   STUT003.STNO = REGT007.STNO "+
                		" AND   STUT003.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' "+
                		" GROUP BY REGT007.CRSNO ) R01 "+
                		"WHERE C01.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' "+
                		"AND   C01.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' "+
                		"AND   C01.CRS_STATUS = '5' "+
                		"AND   C01.EST_RESULT_MK = 'Y' "+
                		"AND   R01.CRSNO(+) = C01.CRSNO "
                );

                if (!Utility.checkNull(ht.get("FACULTY_CODE"), "").equals(""))
                	sql.append("AND C01.PLAN_FACULTY_CODE = '" + Utility.dbStr(ht.get("FACULTY_CODE")) + "' ");
                if (!Utility.checkNull(ht.get("CRSNO"), "").equals(""))
                	sql.append("AND C02.CRSNO = '" + Utility.dbStr(ht.get("CRSNO")) + "' ");
                
                	
               	sql.append("AND C01.OPEN1 != 'Y' "); 
               
               
                sql.append(" AND C02.CRSNO = C01.CRSNO ");
                sql.append(" AND S03.FACULTY_CODE = C01.PLAN_FACULTY_CODE ");
                sql.append(" AND S03.ASYS = '1' ");
                sql.append("AND 0 = (SELECT COUNT(*) FROM TRAT008 T08 WHERE T08.AYEAR = C01.AYEAR AND T08.SMS = C01.SMS AND T08.CRSNO = C01.CRSNO AND T08.JOB_TYPE = '51'  AND T08.CENTER_CODE = '"+ Utility.nullToSpace(ht.get("CENTER_CODE")) + "') ");                
              
                
                sql.append("ORDER BY 2,1 ");
            }
            else
            {
                // 已登人數
                sql.append(
                		"SELECT T08.JOB_TYPE,T08.CRSNO, C02.CRS_NAME || DECODE(T08.JOB_TYPE,'52','(實習)','') AS CRS_NAME, T08.PLAN_EMP_NUM, C01.PLAN_FACULTY_CODE AS FACULTY_CODE, " +
                			"T08.TOTAL_STU_NUM, T08.TOTAL_CLASS_NUM, S03.FACULTY_NAME,NVL(R01.SEL_NUM,0) AS SEL_NUM, C01.OPEN3, T08.CRSNO_KIND, R02.RMK, CASE WHEN C01.OPEN1='Y' THEN '1' WHEN T08.CRSNO_KIND='' OR T08.CRSNO_KIND IS NULL THEN '2' ELSE '3' END AS ORDER_SELECT "
								+
                		"FROM COUT002 C02, TRAT008 T08, SYST003 S03,COUT001 C01," +
                		"(SELECT REGT007.CRSNO ,COUNT(REGT007.STNO) AS SEL_NUM "+
                		" FROM REGT007,STUT003 "+
                		" WHERE REGT007.AYEAR = '"+Utility.nullToSpace(ht.get("AYEAR"))+"' "+
                		" AND   REGT007.SMS = '"+Utility.nullToSpace(ht.get("SMS"))+"' "+
                		" AND   REGT007.UNQUAL_TAKE_MK = 'N' AND  REGT007.UNTAKECRS_MK = 'N' AND REGT007.PAYMENT_STATUS  != '1' "+
                		" AND   STUT003.STNO = REGT007.STNO "+
                		" AND   STUT003.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' "+
                		" GROUP BY REGT007.CRSNO ) R01, "+
                		"(SELECT TRAT009.CRSNO ,TO_CHAR(WMSYS.WM_CONCAT(TRAT009.RECOMM_UNIT_RMK)) AS RMK "+
                		" FROM TRAT009 "+
                		" WHERE TRAT009.AYEAR = '"+Utility.nullToSpace(ht.get("AYEAR"))+"' "+
                		" AND   TRAT009.SMS = '"+Utility.nullToSpace(ht.get("SMS"))+"' "+
                		" AND   TRAT009.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' "+
                		" GROUP BY TRAT009.CRSNO ) R02, "+
                		" SYST001 S01 "+
                		"WHERE 1=1  AND S01.KIND(+)='CRSNO_KIND'  AND S01.CODE(+)=T08.CRSNO_KIND "
                );

                // == 查詢條件 ST == //
                if (!Utility.checkNull(ht.get("AYEAR"), "").equals(""))
                    sql.append("AND T08.AYEAR = '" + Utility.dbStr(ht.get("AYEAR")) + "' ");
                if (!Utility.checkNull(ht.get("SMS"), "").equals(""))
                    sql.append("AND T08.SMS = '" + Utility.dbStr(ht.get("SMS")) + "' ");
                if (!Utility.checkNull(ht.get("CENTER_CODE"), "").equals(""))
                    sql.append("AND T08.CENTER_CODE = '" + Utility.dbStr(ht.get("CENTER_CODE")) + "' ");
                if (!Utility.checkNull(ht.get("FACULTY_CODE"), "").equals(""))
                    sql.append("AND C01.PLAN_FACULTY_CODE = '" + Utility.dbStr(ht.get("FACULTY_CODE")) + "' ");
                if (!Utility.checkNull(ht.get("CRSNO"), "").equals(""))
                    sql.append("AND T08.CRSNO = '" + Utility.dbStr(ht.get("CRSNO")) + "' ");

                sql.append("AND C02.CRSNO (+)= T08.CRSNO ");
                sql.append("AND S03.FACULTY_CODE= C01.PLAN_FACULTY_CODE ");
                sql.append("AND S03.ASYS='1' ");
                sql.append("AND R01.CRSNO(+) = T08.CRSNO ");
                sql.append("AND C01.AYEAR = T08.AYEAR ");
                sql.append("AND C01.SMS = T08.SMS ");
                sql.append("AND C01.CRSNO = T08.CRSNO ");
                sql.append("AND C01.OPEN1 = 'Y' ");
                sql.append("AND R02.CRSNO(+) = T08.CRSNO ");
                
                sql.append(" UNION ALL ");
                
                sql.append(
                		"SELECT T08.JOB_TYPE,T08.CRSNO, C02.CRS_NAME || '("+com.nou.UtilityX.getCouName(2)+")' AS CRS_NAME, T08.PLAN_EMP_NUM, C01.PLAN_FACULTY_CODE AS FACULTY_CODE, " +
                			"T08.TOTAL_STU_NUM, T08.TOTAL_CLASS_NUM, S03.FACULTY_NAME,NVL(R01.SEL_NUM,0) AS SEL_NUM, C01.OPEN3, T08.CRSNO_KIND, R02.RMK, CASE WHEN C01.OPEN1='Y' THEN '1' WHEN T08.CRSNO_KIND='' OR T08.CRSNO_KIND IS NULL THEN '2' ELSE '3' END AS ORDER_SELECT "
								+
                		"FROM COUT002 C02, TRAT008 T08, SYST003 S03,COUT001 C01," +
                		"(SELECT REGT007.CRSNO ,COUNT(REGT007.STNO) AS SEL_NUM "+
                		" FROM REGT007,STUT003 "+
                		" WHERE REGT007.AYEAR = '"+Utility.nullToSpace(ht.get("AYEAR"))+"' "+
                		" AND   REGT007.SMS = '"+Utility.nullToSpace(ht.get("SMS"))+"' "+
                		" AND   REGT007.UNQUAL_TAKE_MK = 'N' AND  REGT007.UNTAKECRS_MK = 'N' AND REGT007.PAYMENT_STATUS  != '1' "+
                		" AND   STUT003.STNO = REGT007.STNO "+
                		" AND   STUT003.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' "+
                		" GROUP BY REGT007.CRSNO ) R01, "+
                		"(SELECT TRAT009.CRSNO ,TO_CHAR(WMSYS.WM_CONCAT(TRAT009.RECOMM_UNIT_RMK)) AS RMK "+
                		" FROM TRAT009 "+
                		" WHERE TRAT009.AYEAR = '"+Utility.nullToSpace(ht.get("AYEAR"))+"' "+
                		" AND   TRAT009.SMS = '"+Utility.nullToSpace(ht.get("SMS"))+"' "+
                		" AND   TRAT009.CENTER_CODE = '" + Utility.nullToSpace(ht.get("CENTER_CODE")) + "' "+
                		" GROUP BY TRAT009.CRSNO ) R02 "+
                		"WHERE 1=1 "
                );

                // == 查詢條件 ST == //
                if (!Utility.checkNull(ht.get("AYEAR"), "").equals(""))
                    sql.append("AND T08.AYEAR = '" + Utility.dbStr(ht.get("AYEAR")) + "' ");
                if (!Utility.checkNull(ht.get("SMS"), "").equals(""))
                    sql.append("AND T08.SMS = '" + Utility.dbStr(ht.get("SMS")) + "' ");
                if (!Utility.checkNull(ht.get("CENTER_CODE"), "").equals(""))
                    sql.append("AND T08.CENTER_CODE = '" + Utility.dbStr(ht.get("CENTER_CODE")) + "' ");
                if (!Utility.checkNull(ht.get("FACULTY_CODE"), "").equals(""))
                    sql.append("AND C01.PLAN_FACULTY_CODE = '" + Utility.dbStr(ht.get("FACULTY_CODE")) + "' ");
                if (!Utility.checkNull(ht.get("CRSNO"), "").equals(""))
                    sql.append("AND T08.CRSNO = '" + Utility.dbStr(ht.get("CRSNO")) + "' ");
              
                sql.append("AND C02.CRSNO (+)= T08.CRSNO ");
                sql.append("AND S03.FACULTY_CODE= C01.PLAN_FACULTY_CODE ");
                sql.append("AND S03.ASYS='1' ");
                sql.append("AND R01.CRSNO(+) = T08.CRSNO ");
                sql.append("AND C01.AYEAR = T08.AYEAR ");
                sql.append("AND C01.SMS = T08.SMS ");
                sql.append("AND C01.CRSNO = T08.CRSNO ");
                sql.append("AND C01.OPEN1 != 'Y' ");
                sql.append("AND R02.CRSNO(+) = T08.CRSNO ");
                
                
                sql.append("ORDER BY 5,12, 2, 1 ");
            }
System.out.println(sql);
            // 依分頁取出資料
            if (pageQuery)
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);

            // 取出所有資料
            else
            {
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            MyLogger			logger	= new MyLogger("TRA014M");
            DBManager			db	= new DBManager(logger);            
            Hashtable	rowHt	= null;
            while (rs.next())
            {
                rowHt	= new Hashtable();
                System.out.println("1");

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                rowHt.put("AYEAR", Utility.dbStr(ht.get("AYEAR")));
                rowHt.put("SMS", Utility.dbStr(ht.get("SMS")));
                rowHt.put("CENTER_CODE", Utility.dbStr(ht.get("CENTER_CODE")));               

                // 擬聘人數---未登
                if (Utility.nullToSpace(ht.get("HAVE_REGISTER")).equals("1"))
                    rowHt.put("PLAN_EMP_NUM", "");

                // 編班人數,編班數...已登/未登時顯示來源不同
                Vector plaNum = new Vector();
                // 未登時,至plat012 regt007 join取得
                //if (Utility.nullToSpace(ht.get("HAVE_REGISTER")).equals("1"))
                	plaNum = getPlaNum(db, ht, rs);
                // 已登則至TRAT008取得
                //else{
                //	plaNum.add(rs.getString("TOTAL_STU_NUM"));
                //	plaNum.add(rs.getString("TOTAL_CLASS_NUM"));
               // }

                rowHt.put("TOTAL_STU_NUM", plaNum.get(0).toString());
                rowHt.put("TOTAL_CLASS_NUM", plaNum.get(1).toString());
                // 不論已登未登均不可變
                rowHt.putAll(getTraNum(db, ht, rs));
System.out.println(rowHt);
                vt.add(rowHt);
            }

            return result;
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
                rs.close();
        }
    }

    /**
     * 取得該中心某科的編班人數與編班數
     *
     * 傳入值  ht:前端條件  rs:主method所查詢出來的rs
     */
    private Vector getPlaNum(DBManager dbmanage, Hashtable ht, DBResult rs) throws Exception
    {
        Vector result = new Vector();  // 只會有兩個值: 編班人數與編班數

        String getPlaNum =
        	"SELECT NVL(SUM(e.stu_num),'0') AS TOTAL_STU_NUM, COUNT (1) AS TOTAL_CLASS_NUM " +
        	"FROM (" +
        		"SELECT   COUNT (1) AS stu_num " +
        		"FROM plat012 a, regt007 b, syst002 c, cout001 d " +
        		"WHERE " +
        			"a.ayear = '"+Utility.dbStr(ht.get("AYEAR"))+"' AND " +
        			"a.sms = '"+Utility.dbStr(ht.get("SMS"))+"' AND " +
        			"a.crsno = '"+rs.getString("CRSNO")+"' AND " +
        			"b.ayear = a.ayear AND " +
        			"b.sms = a.sms AND " +
        			"b.crsno = a.crsno AND " +
        			//"B.TUT_CLASS_CODE LIKE  a.CENTER_ABRCODE || '%' AND "+
        			"c.CENTER_ABRCODE=a.CENTER_ABRCODE AND " +
        			"c.CENTER_CODE='"+Utility.dbStr(ht.get("CENTER_CODE"))+"' AND " +
        			"d.AYEAR=a.AYEAR AND " +
        			"d.SMS=a.SMS AND " +
        			"d.CRSNO=A.CRSNO AND " +
        			"d.PLAN_FACULTY_CODE='"+rs.getString("FACULTY_CODE")+"' ";
        	   
        
            if (Utility.checkNull(rs.getString("JOB_TYPE"), "").equals("52")){
            	getPlaNum += "AND a.CLASS_KIND = '7' ";
            	getPlaNum += "AND b.LAB_CLASS_CODE = A.CLASS_CODE ";
            }else{
            	getPlaNum += "AND a.CLASS_KIND <> '7' ";
            	//getPlaNum += "AND SUBSTR(a.class_code,3,1) NOT IN ('Z','A','X') ";
            	getPlaNum += "AND b.ass_class_code = a.class_code ";     
            }
            
            getPlaNum += "GROUP BY a.class_code ) e ";
        	

        DBResult rs2 = null;
        try
        {
	        rs2	= dbmanager.getSimpleResultSet(conn);
	        rs2.open();
	        rs2.executeQuery(getPlaNum);

	        if(rs2.next()){
	        	result.add(rs2.getString("TOTAL_STU_NUM"));
	        	result.add(rs2.getString("TOTAL_CLASS_NUM"));
	        }else{
	        	result.add("0");
	        	result.add("0");
	        }
	        rs2.close();
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs2 != null)
            	rs2.close();
        }

        return result;
    }

    /**
     * 取得該中心某科的已聘教師人數
     *
     * 傳入值  ht:前端條件  rs:主method所查詢出來的rs
     */
    private Hashtable getTraNum(DBManager dbmanage, Hashtable ht, DBResult rs) throws Exception
    {
    	Hashtable htt = new Hashtable();
    	sql = new StringBuffer();
    	sql.append("select ");
    	sql.append("sum(case  when a.hr_audit_mk = '1' then 1  else 0 end ) as TOTAL_TEACHER_NUM_1, ");
    	sql.append("sum(case  when a.deliver_mk = 'Y' then 1  else 0  end ) as TOTAL_TEACHER_NUM_2, ");
    	sql.append("sum(case  when a.tch_audit_result = '2' then 1 else 0  end )as TOTAL_TEACHER_NUM_3, ");
    	sql.append("sum(case  when a.send_audit_mk = 'Y' then 1  else 0  end )as TOTAL_TEACHER_NUM_4 ");
    	sql.append("from trat009 a ");
    	sql.append("join cout001 b on b.ayear = a.ayear and b.sms = a.sms and b.crsno = a.crsno and b.plan_faculty_code = '"+rs.getString("FACULTY_CODE")+"' ");
    	sql.append("where a.ayear = '"+ht.get("AYEAR")+"' ");
    	sql.append("and a.sms = '"+ht.get("SMS")+"' ");
    	sql.append("and a.center_code = '"+ht.get("CENTER_CODE")+"' ");
    	sql.append("and a.crsno = '"+rs.getString("CRSNO")+"' ");
    	sql.append("and ((a.duty_code = '05' and a.sms < '3') or (a.duty_code = '07' and a.sms = '3')) ");
    	sql.append("and a.job_type = '"+rs.getString("JOB_TYPE")+"' ");
          
            
        DBResult rs1 = null;
        try
        {
        	rs1	= dbmanager.getSimpleResultSet(conn);
        	rs1.open();
        	rs1.executeQuery(sql.toString());

	        if(rs1.next()){
	        	 /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs1.getColumnCount(); i++){
                	htt.put(rs1.getColumnName(i), rs1.getString(i));
                }	
	        }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs1 != null)
            	rs1.close();
        }

        return htt;
    }

    /**
     * cout003m教策小組名單
     */
    public Vector getCout001Cout003ForUse(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT I.CODE_NAME AS CSMS,D.FACULTY_NAME,E.FACULTY_NAME AS FACULTY_NAME_COUNT,B.CRS_NAME,A.CRD,H.CODE_NAME AS CREQOPTION  "
            + ",A.PRODUCE_TYPE,G.CODE_NAME AS CPRODUCE_TYPE,F.CODE_NAME AS CNEW_REWORK " 
            + "FROM COUT002 B "
            + "JOIN COUT001 A ON A.CRSNO=B.CRSNO " 
            + "JOIN COUT103 C ON A.CRSNO=C.CRSNO "
            + "JOIN SYST003 D ON D.FACULTY_CODE=a.PLAN_FACULTY_CODE " 
            + "JOIN SYST003 E ON E.FACULTY_CODE=C.FACULTY_CODE "
            + "JOIN SYST001 F ON F.KIND='NEW_REWORK' AND F.CODE=A.NEW_REWORK "
            + "JOIN SYST001 H ON H.KIND='REQOPTION' AND H.CODE=C.REQOPTION "
            + "JOIN SYST001 I ON I.KIND='SMS' AND I.CODE=A.SMS "
            + "LEFT JOIN SYST001 G ON G.KIND='PRODUCE_TYPE' AND G.CODE=A.PRODUCE_TYPE WHERE 0=0 ");

        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (Utility.nullToSpace(ht.get("SQL")).equals(""))
        {
            sql.append(" ORDER BY A.PLAN_FACULTY_CODE,A.NEW_REWORK,A.CRSNO ");
        }

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    /**
     * JOIN 其它 Cout001,Cout002 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout002Trat001ForUse(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append("SELECT B.AYEAR,B.SMS,B.CRSNO,C.CRS_NAME,A.IDNO,A.NAME,B.JOB_TYPE,Z1.CODE_NAME AS CJOB_TYPE "
                   + ",B.TUT_DUTY_TYPE,Z2.CODE_NAME AS CTUT_DUTY_TYPE "
                   + "FROM TRAT001 A JOIN COUT003 B ON A.IDNO=B.IDNO " + "JOIN COUT002 C ON B.CRSNO=C.CRSNO "
                   + "JOIN SYST001 Z1 ON Z1.KIND='JOB_TYPE' AND Z1.CODE=B.JOB_TYPE "
                   + "JOIN SYST001 Z2 ON Z2.KIND='TUT_DUTY_TYPE' AND Z2.CODE=B.TUT_DUTY_TYPE WHERE 0=0 ");

        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {		// 有學年直
            if (!Utility.nullToSpace(ht.get("AYEAR2")).equals(""))
            {		// 有第二個學年直則設定成 起迄學年範圍
                sql.append(" AND B.AYEAR BETWEEN  " + Utility.nullToSpace(ht.get("AYEAR")) + " AND "
                           + Utility.nullToSpace(ht.get("AYEAR2")) + " ");
            }
            else
            {
                if (!Utility.nullToSpace(ht.get("single_year")).equals(""))
                {	// single_year有值使用單一年份
                    sql.append(" AND B.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
                }
                else
                {	// single_year沒值 則範圍為輸入直到後四年
                    sql.append(" AND B.AYEAR BETWEEN  " + Utility.nullToSpace(ht.get("AYEAR")) + " AND 0"
                               + (Integer.parseInt(Utility.nullToSpace(ht.get("AYEAR"))) + 3) + " ");
                }
            }
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append("AND B.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND C.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append("AND B.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("IDNO")).equals(""))
        {
            sql.append("AND A.IDNO = '" + Utility.nullToSpace(ht.get("IDNO")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("JOB_TYPE")).equals(""))
        {
            sql.append("AND B.JOB_TYPE = '" + Utility.nullToSpace(ht.get("JOB_TYPE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("TUT_DUTY_TYPE")).equals(""))
        {
            sql.append("AND B.TUT_DUTY_TYPE = '" + Utility.nullToSpace(ht.get("TUT_DUTY_TYPE")) + "' ");
        }

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    /**
     *             二軌   使用報表：cou109r抓上課時間 cou113r抓授課老師
     */
    public Vector getCout022Cout028ForUse(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append("SELECT H.AYEAR,H.SMS,H.CRSNO,J.CRS_NAME,J.CRD,K.FACULTY_NAME,K.FACULTY_CODE,L.NAME "
                   + ",H.S_CLASS_TYPE,H.S_CLASS_NUM,H.S_CLASS_ID,H.S_CLASS_NAME,H.WEEK_YN,H.TUT_TIMES " +

        // 專班類別       班級流水號     班級編號     班級名稱     隔週上     上課次數
        ",H.S_CLASS_ABRCODE_CODE,H.S_CLASS_CMPS_CODE,H.S_CLASS_CLASS_CODE,H.S_CLASS_TCH_IDNO,H.OPEN_YN,H.DECISION_MK " +

        // 上課中心名        校區代                   教室代           教師代碼          成班註記    審查註記
        ",I.S_CLASS_SECTION,I.S_CLASS_WEEK " +

        // 上課時段           上課週次
        " FROM COUT022 H LEFT JOIN COUT028 I ON H.CRSNO=I.CRSNO AND H.AYEAR=I.AYEAR AND H.SMS=I.SMS " + " AND H.S_CLASS_TYPE=I.S_CLASS_TYPE AND H.S_CLASS_NUM=I.S_CLASS_NUM "
                + " JOIN COUT002 J ON H.CRSNO=J.CRSNO " + " JOIN SYST003 K ON J.FACULTY_CODE=K.FACULTY_CODE "
                + " JOIN TRAT001 L ON L.IDNO=H.S_CLASS_TCH_IDNO WHERE 0=0 ");

        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {		// 有學年直
            if (!Utility.nullToSpace(ht.get("AYEAR2")).equals(""))
            {		// 有第二個學年直則設定成 起迄學年範圍
                sql.append(" AND H.AYEAR BETWEEN  " + Utility.nullToSpace(ht.get("AYEAR")) + " AND "
                           + Utility.nullToSpace(ht.get("AYEAR2")) + " ");
            }
            else
            {
                if (!Utility.nullToSpace(ht.get("single_year")).equals(""))
                {	// single_year有值使用單一年份
                    sql.append(" AND H.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
                }
                else
                {	// single_year沒值 則範圍為輸入直到後四年
                    sql.append(" AND H.AYEAR BETWEEN  " + Utility.nullToSpace(ht.get("AYEAR")) + " AND 0"
                               + (Integer.parseInt(Utility.nullToSpace(ht.get("AYEAR"))) + 3) + " ");
                }
            }
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND H.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND K.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append(" AND H.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("ASYS")).equals(""))
        {
            sql.append(" AND K.ASYS='" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    /**
     * JOIN 其它 Cout001,Cout002,Cout005,Cout008,Cout011,Cout100,Cout103 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception              //COUT022完整    COUT004完整
     */
    public Vector getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForUse(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

    	sql.append
		(
			//原先「先修課程」Z4.CRS_NAME AS PREREQ_CRS_NAME ，改為 A.FIRSTCOU AS PREREQ_CRS_NAME 
			"SELECT A.PLAN_FACULTY_CODE, FUCK.FACULTY_NAME AS PLAN_FACULTY_NAME, FUCK.FACULTY_ABBRNAME AS FACULTY_ABBRNAME, A.CRS_STATUS, A.CRSNO,B.CRS_ENG,B.CRS_NAME " +
			",Z2.FACULTY_NAME,C.DISCIPLINE_NAME,C1.DISCIPLINE_NAME as FIELD_NAME,A.CRD,Z3.CODE_NAME AS REQOPTION " +
			",Z7.CODE_NAME AS NEW_REWORK, A.NEW_REWORK AS NEW_REWORK_TEMP, Z1.CODE_NAME AS CRS_CHAR,A.AYEAR " +
			",Z6.CODE_NAME AS CSMS,A.SMS,A.FIRSTCOU AS PREREQ_CRS_NAME ,A.PRODUCE_TYPE,Z9.CODE_NAME AS CPRODUCE_TYPE " +
			",A.CRS_TIMES,A.TUT_TIMES,NVL(A.ON_PC_NUM,A.LAB_TIMES) AS LAB_TIMES,F.HW_REQUEST,G.TUT_TARGET,G.CRS_GUTLINE " +
			",G.CRS_OUTLINE,G.BASIC_KNWLDG, A.DISCIPLINE_TARGET, A.OPENED_DESCRIPTION, A.OTHER, A.EVAL_ST_NUM  " +
			",Z5.CODE_NAME AS CRS_BOOK,A.PRO_ACADFIELD,A.RESERVE_ITEM,A.REWORK_ITEM,SW_REQUEST "+
			",(SELECT CODE_NAME FROM SYST001 WHERE KIND='NEW_REWORK' AND CODE=A.NEW_REWORK) AS NEW_REWORK_NAME "+
			",A.FACULTY_CODE, DECODE(S03.FACULTY_NAME,'','','、'||S03.FACULTY_NAME) as COMMON_FACULTY_NAME,G.EVAL_MANNER  "+
			",L1.CODE_NAME AS TEACHING_TYPE,L2.CODE_NAME AS TEACHING_TYPE_NAME, substr(L3.CODE_NAME,1,4) AS CRS_BOOK, L4.CODE_NAME AS CRS_STATUS_NAME "
		);

        if (!Utility.nullToSpace(ht.get("getCout102")).equals(""))
        {
            sql.append(",J.CRS_GROUP_CODE,J.CRS_GROUP_CODE_NAME,J.CRS_GROUP_CRD ");
        }

        // 科目群帶碼       科目群名              科目群總學分
        if (!Utility.nullToSpace(ht.get("getCout004")).equals(""))
        {	// 判斷是否引用_TABLE COUT004 課程媒體管控資料
            sql.append(
                ",C4.AFFAIRS_MEETING,C4.AFFAIRS_DATE,C4.UCPC,C4.UCPC_DATE,C4.COMMON_CRS,C4.COMMON_DATE,C4.FEC,C4.FEC_DATE,C4.UFEC,C4.UFEC_DATE,C4.CRSTEAM,C4.CRSTEAM_DATE,C4.CONTRACT_DATE,C4.CONTRACT_PLAN_DATE,C4.DEADLINE_DATE,C4.PRJ_MEETING,C4.PRJ_DATE,C4.PRODUCE_MEETING,C4.PRODUCE_DATE,C4.S_RECORD_DATE,C4.RMK ");
        }

        // 系務會議                           校課策會             共同課程委員會               系教評會           校教評會             教學策劃小組座談會         繳合約書日       繳教科書計畫日        書稿截止日       企劃會議                   製作會議                           開始錄製日
        if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
        {
            sql.append(
                ",H.S_CLASS_TYPE,Z10.CODE_NAME AS CS_CLASS_TYPE,H.S_CLASS_NUM,H.S_CLASS_ID,H.S_CLASS_NAME,H.WEEK_YN,H.TUT_TIMES,H.S_CLASS_ABRCODE_CODE,Z8.CENTER_NAME,H.S_CLASS_CMPS_CODE,H.S_CLASS_CLASS_CODE,Z11.CODE_NAME AS CS_CLASS_CLASS_CODE,I.NAME,H.S_CLASS_TCH_IDNO,H.OPEN_YN,H.DECISION_MK, WEEK, STIME, ETIME, NVL(H.TUT_TIMES_P, 0) AS TUT_TIMES_P, NVL(H.LAB_TIMES_P, 0) AS LAB_TIMES_P ");
        }

        // 專班類別                       專班類別中文    班級流水號     班級編號     班級名稱     隔週上     上課次數        上課中心代碼        中心名稱          校區代                   教室代                        教室類型中文        教師姓名   代碼        成班註記    審查註記
        sql.append(
            "FROM COUT001 A JOIN COUT002 B ON A.CRSNO=B.CRSNO "
            + "JOIN SYST001 Z1 ON A.CRS_CHAR=Z1.CODE AND Z1.KIND='CRS_CHAR' "
            + "LEFT JOIN SYST003 Z2 ON Z2.FACULTY_CODE=A.FACULTY_CODE "
            + "LEFT JOIN SYST003 FUCK ON FUCK.FACULTY_CODE=A.PLAN_FACULTY_CODE "
            + "LEFT JOIN SYST003 S03 ON S03.FACULTY_CODE=A.COMMON_FACULTY_CODE AND S03.ASYS='1' "
            + "LEFT JOIN COUT100 C ON A.FACULTY_CODE=C.FACULTY_CODE AND A.DISCIPLINE_CODE=C.DISCIPLINE_CODE "
            + "LEFT JOIN COUT100 C1 ON A.FACULTY_CODE=C1.FACULTY_CODE AND A.FIELD_CODE=C1.DISCIPLINE_CODE "
            //+ "LEFT JOIN COUT103 D ON B.CRSNO=D.CRSNO AND D.FACULTY_CODE=A.FACULTY_CODE AND A.AYEAR=D.AYEAR AND A.SMS=D.SMS "
            + "LEFT JOIN SYST001 Z3 ON Z3.KIND='REQOPTION' AND A.REQOPTION_TYPE=Z3.CODE "
            + "LEFT JOIN SYST001 Z6 ON Z6.KIND='SMS' AND Z6.CODE=A.SMS "
            + "LEFT JOIN SYST001 Z7 ON Z7.KIND='NEW_REWORK' AND Z7.CODE=A.NEW_REWORK "
            + "LEFT JOIN COUT011 E ON B.CRSNO=E.CRSNO " + "LEFT JOIN COUT002 Z4 ON Z4.CRSNO=E.PREREQ_CRSNO "
            + "LEFT JOIN SYST001 Z5 ON Z5.KIND='CRS_BOOK' AND A.CRS_BOOK=Z5.CODE "
            + "LEFT JOIN COUT008 F ON F.AYEAR=A.AYEAR AND F.SMS=A.SMS AND F.CRSNO=A.CRSNO "
            + "LEFT JOIN COUT005 G ON A.AYEAR=G.AYEAR AND A.SMS=G.SMS AND A.CRSNO=G.CRSNO "
            + "LEFT JOIN SYST001 Z9 ON Z9.KIND='PRODUCE_TYPE' AND Z9.CODE=A.PRODUCE_TYPE "
        	+ "LEFT JOIN SYST001 L1 ON L1.KIND='TEACHING_TYPE' AND A.TEACHING_TYPE=L1.CODE " 
        	+ "LEFT JOIN SYST001 L2 ON L2.KIND='TEACHING_TYPE_NAME' AND A.TEACHING_TYPE_NAME=L2.CODE " 
        	+ "LEFT JOIN SYST001 L3 ON L3.KIND='CRS_BOOK' AND A.CRS_BOOK=L3.CODE " 
        	+ "LEFT JOIN SYST001 L4 ON L4.KIND='CRS_STATUS' AND A.CRS_STATUS=L4.CODE " );

        if (!Utility.nullToSpace(ht.get("getCout102")).equals(""))
        {
            sql.append(
                "LEFT JOIN COUT102 J ON J.FACULTY_CODE=D.FACULTY_CODE AND J.TOTAL_CRS_NO=D.TOTAL_CRS_NO AND J.CRS_GROUP_CODE=D.CRS_GROUP_CODE ");
        }

        if (!Utility.nullToSpace(ht.get("getCout004")).equals(""))
        {
            sql.append("LEFT JOIN COUT004 C4 ON A.AYEAR=C4.AYEAR AND A.SMS=C4.SMS AND A.CRSNO=C4.CRSNO ");
        }

        if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
        {
            sql.append("LEFT JOIN COUT022 H ON A.AYEAR=H.AYEAR AND A.SMS=H.SMS AND A.CRSNO=H.CRSNO "
                       + "LEFT JOIN TRAT001 I ON H.S_CLASS_TCH_IDNO=I.IDNO "
                       + "LEFT JOIN SYST002 Z8 ON Z8.CENTER_ABRCODE=H.S_CLASS_ABRCODE_CODE "
                       + "LEFT JOIN SYST001 Z10 ON Z10.KIND='S_CLASS_TYPE' AND Z10.CODE=H.S_CLASS_TYPE "
                       + "LEFT JOIN SYST001 Z11 ON Z10.KIND='CLSSRM_KIND' AND Z11.CODE=H.S_CLASS_CLASS_CODE ");
        }

        
        sql.append("WHERE 1=1 ");
        
        //20100325 曾加軌制條件
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1"))
        {
            //sql.append(" AND A.open3 <> 'Y' ");
        	sql.append(" AND A.open1 = 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2"))
        {
            sql.append(" AND A.open1 <>  'Y' AND A.open3 = 'Y' ");
        }

        // cou104r不下這個條件
        if(!Utility.nullToSpace(ht.get("PRO_CODE")).equals("COU104R")&&!Utility.nullToSpace(ht.get("PRO_CODE")).equals("COU110R"))
        	sql.append("AND A.CRS_STATUS='5' AND A.EST_RESULT_MK='Y' ");
        
        if(Utility.nullToSpace(ht.get("PRO_CODE")).equals("COU110R"))
        	sql.append("AND A.CRS_STATUS NOT IN ('1','3','4') ");
        
        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {		// 有學年直
            sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("OPEN2")).equals(""))
        {
            sql.append(" AND A.OPEN2 = '" + Utility.nullToSpace(ht.get("OPEN2")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
        {
            sql.append(" AND A.NEW_REWORK = '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("ASYS")).equals(""))
        {
            sql.append(" AND Z2.ASYS='" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }

        
    	//sql.append(" ORDER BY DECODE(A.OPEN3,'Y','2','1'),DECODE(A.FACULTY_CODE, '80', '01', '90', '02', A.FACULTY_CODE),DECODE(A.NEW_REWORK,'1','1','6','2','2','3','4','4','5','5','3','6'), A.CRSNO ");
        sql.append(" ORDER BY  DECODE(A.OPEN1,'Y','1','2'),DECODE(A.FACULTY_CODE, '80', '01', '90', '02', '00', '99', A.FACULTY_CODE),DECODE(A.NEW_REWORK,'1','1','6','2','3','3','4','3','5','5','2','6'), A.CRSNO ");
        
        
            
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

				String x= "";

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                	{
                		x = getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i));
                	}
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

				rowHt.put("CPRODUCE_TYPE", x);
				rowHt.put("NAMES1", getCout003Names1(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO")));
				rowHt.put("NAMES", getCout003Names(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO")));

				if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
				{
					if ("3".equals((String)rowHt.get("NEW_REWORK_TEMP")))
					{
						rowHt.put("四次面授", getHistoryCRS(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "1"));
						rowHt.put("多次面授", getHistoryCRS(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "2"));
					}
					else
					{
						rowHt.put("四次面授","");
						rowHt.put("多次面授","");
					}
				}

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }


    /**
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception              
     */
    public Vector getCou114rQuery(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append
		(			 
			"SELECT " +
			"A.PLAN_FACULTY_CODE, FUCK.FACULTY_NAME AS PLAN_FACULTY_NAME, A.CRS_STATUS, A.CRSNO,B.CRS_ENG,B.CRS_NAME,Z2.FACULTY_NAME," +
			"C.DISCIPLINE_NAME,A.CRD,Z3.CODE_NAME AS REQOPTION,Z7.CODE_NAME AS NEW_REWORK, A.NEW_REWORK AS NEW_REWORK_TEMP, " +
			"Z1.CODE_NAME AS CRS_CHAR,A.AYEAR,Z6.CODE_NAME AS CSMS,A.SMS,A.FIRSTCOU AS PREREQ_CRS_NAME ," +
			"A.PRODUCE_TYPE,Z9.CODE_NAME AS CPRODUCE_TYPE, " +
			"A.CRS_TIMES,A.TUT_TIMES,A.LAB_TIMES,F.HW_REQUEST," +
			"G.TUT_TARGET,G.CRS_GUTLINE,G.CRS_OUTLINE,G.BASIC_KNWLDG, " +
			"A.DISCIPLINE_TARGET, A.OPENED_DESCRIPTION, A.OTHER, A.EVAL_ST_NUM,  " +
			"Z5.CODE_NAME AS CRS_BOOK,A.PRO_ACADFIELD,A.RESERVE_ITEM,A.REWORK_ITEM,SW_REQUEST, (SELECT CODE_NAME FROM SYST001 WHERE KIND='NEW_REWORK' AND CODE=A.NEW_REWORK) AS NEW_REWORK_NAME, A.FACULTY_CODE, DECODE(S03.FACULTY_NAME,'','','、'||S03.FACULTY_NAME) as COMMON_FACULTY_NAME  "
		);

      
        sql.append(
            "FROM COUT001 A JOIN COUT002 B ON A.CRSNO=B.CRSNO "+
            "JOIN SYST001 Z1 ON A.CRS_CHAR=Z1.CODE AND Z1.KIND='CRS_CHAR' "+
            "LEFT JOIN SYST003 Z2 ON Z2.FACULTY_CODE=A.FACULTY_CODE "+
            "LEFT JOIN SYST003 FUCK ON FUCK.FACULTY_CODE=A.PLAN_FACULTY_CODE "+
            "LEFT JOIN SYST003 S03 ON S03.FACULTY_CODE=A.COMMON_FACULTY_CODE AND S03.ASYS='1' "+
            "LEFT JOIN COUT100 C ON A.FACULTY_CODE=C.FACULTY_CODE AND B.DISCIPLINE_CODE=C.DISCIPLINE_CODE "+
            "LEFT JOIN SYST001 Z3 ON Z3.KIND='REQOPTION' AND A.REQOPTION_TYPE=Z3.CODE "+
            "LEFT JOIN SYST001 Z6 ON Z6.KIND='SMS' AND Z6.CODE=A.SMS "+
            "LEFT JOIN SYST001 Z7 ON Z7.KIND='NEW_REWORK' AND Z7.CODE=A.NEW_REWORK "+
            "LEFT JOIN COUT011 E ON B.CRSNO=E.CRSNO " + "LEFT JOIN COUT002 Z4 ON Z4.CRSNO=E.PREREQ_CRSNO "+
            "LEFT JOIN SYST001 Z5 ON Z5.KIND='CRS_BOOK' AND A.CRS_BOOK=Z5.CODE "+
            "LEFT JOIN COUT008 F ON F.AYEAR=A.AYEAR AND F.SMS=A.SMS AND F.CRSNO=A.CRSNO "+
            "LEFT JOIN COUT005 G ON A.AYEAR=G.AYEAR AND A.SMS=G.SMS AND A.CRSNO=G.CRSNO "+
            "LEFT JOIN SYST001 Z9 ON Z9.KIND='PRODUCE_TYPE' AND Z9.CODE=A.PRODUCE_TYPE ");
        
        sql.append("WHERE A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        
        //20100325 曾加軌制條件
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1"))
        {
            sql.append(" AND A.open1 = 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2"))
        {
            sql.append(" AND A.open1 = 'N' AND A.open3 = 'Y' ");
        }            
   
        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }      
        
        if (!Utility.nullToSpace(ht.get("PASS_MK")).equals("")){
			sql.append(" AND A.CRS_STATUS='5' AND A.EST_RESULT_MK='Y' ");
		}else{
			sql.append(" AND A.CRS_STATUS IN ('1','2','5') ");
		} 
        
        sql.append(" ORDER BY ");
        sql.append("DECODE(A.OPEN1,'Y','1','2'),DECODE(A.FACULTY_CODE, '80', '01', '90', '02', A.FACULTY_CODE), ");
		sql.append("DECODE(A.NEW_REWORK,'1','1','6','2','3','3','4','3','5','5','2','6'), A.CRSNO ");
        
        
            
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

				String x= "";

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                    	{
                    		x = getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i));
                    	}

                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

				rowHt.put("CPRODUCE_TYPE", x);

				rowHt.put("NAMES", getCout003Names(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO")));

				if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
				{
					if ("3".equals((String)rowHt.get("NEW_REWORK_TEMP")))
					{
						rowHt.put("一軌", getHistoryCRS(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "1"));
						rowHt.put("二軌", getHistoryCRS(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "2"));
					}
					else
					{
						rowHt.put("一軌","");
						rowHt.put("二軌","");
					}
				}

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }    
    
  //開新的方法，專給COU105R使用，避免影響其他條件不同  2008/12/19 by barry
  /**
     * JOIN 其它 Cout001,Cout002,Cout005,Cout008,Cout011,Cout100,Cout103 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception              //COUT022完整    COUT004完整
     */
    public Vector getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForUseCou105R(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        	sql.append
		(   //原先「先修課程」Z4.CRS_NAME AS PREREQ_CRS_NAME ，改為 A.FIRSTCOU AS PREREQ_CRS_NAME
			"SELECT A.PLAN_FACULTY_CODE, FUCK.FACULTY_NAME AS PLAN_FACULTY_NAME, A.CRS_STATUS, A.CRSNO,B.CRS_ENG,B.CRS_NAME,Z2.FACULTY_NAME,C.DISCIPLINE_NAME,A.CRD,Z3.CODE_NAME AS REQOPTION,Z7.CODE_NAME AS NEW_REWORK, A.NEW_REWORK AS NEW_REWORK_TEMP " +
			",Z1.CODE_NAME AS CRS_CHAR,A.AYEAR,Z6.CODE_NAME AS CSMS,A.SMS,A.FIRSTCOU AS PREREQ_CRS_NAME,A.PRODUCE_TYPE,Z9.CODE_NAME AS CPRODUCE_TYPE, A.ORI_TEXT_NUM, A.MOD_TEXT_NUM, A.MOD_RMK " +
			",A.CRS_TIMES,A.TUT_TIMES,A.LAB_TIMES,F.HW_REQUEST,G.TUT_TARGET,G.CRS_GUTLINE,G.CRS_OUTLINE,G.BASIC_KNWLDG, A.DISCIPLINE_TARGET, A.OPENED_DESCRIPTION, A.OTHER, A.EVAL_ST_NUM  " +
			",Z5.CODE_NAME AS CRS_BOOK,A.PRO_ACADFIELD,A.RESERVE_ITEM,A.REWORK_ITEM,SW_REQUEST, (SELECT CODE_NAME FROM SYST001 WHERE KIND='NEW_REWORK' AND CODE=A.NEW_REWORK) AS NEW_REWORK_NAME, A.FACULTY_CODE, DECODE(S03.FACULTY_NAME,'','','、'||S03.FACULTY_NAME) as COMMON_FACULTY_NAME, G.CRS_BOOK_CHAPTER  "+
			",C1.DISCIPLINE_NAME as FIELD_NAME, G.EVAL_MANNER "
		);

        if (!Utility.nullToSpace(ht.get("getCout102")).equals(""))
        {
            sql.append(",J.CRS_GROUP_CODE,J.CRS_GROUP_CODE_NAME,J.CRS_GROUP_CRD ");
        }

        // 科目群帶碼       科目群名              科目群總學分
        if (!Utility.nullToSpace(ht.get("getCout004")).equals(""))
        {	// 判斷是否引用_TABLE COUT004 課程媒體管控資料
            sql.append(
                ",C4.AFFAIRS_MEETING,C4.AFFAIRS_DATE,C4.UCPC,C4.UCPC_DATE,C4.COMMON_CRS,C4.COMMON_DATE,C4.FEC,C4.FEC_DATE,C4.UFEC,C4.UFEC_DATE,C4.CRSTEAM,C4.CRSTEAM_DATE,C4.CONTRACT_DATE,C4.CONTRACT_PLAN_DATE,C4.DEADLINE_DATE,C4.PRJ_MEETING,C4.PRJ_DATE,C4.PRODUCE_MEETING,C4.PRODUCE_DATE,C4.S_RECORD_DATE,C4.RMK ");
        }

        // 系務會議                           校課策會             共同課程委員會               系教評會           校教評會             教學策劃小組座談會         繳合約書日       繳教科書計畫日        書稿截止日       企劃會議                   製作會議                           開始錄製日
        if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
        {
            sql.append(
                ",H.S_CLASS_TYPE,Z10.CODE_NAME AS CS_CLASS_TYPE,H.S_CLASS_NUM,H.S_CLASS_ID,H.S_CLASS_NAME,H.WEEK_YN,H.TUT_TIMES,H.S_CLASS_ABRCODE_CODE,Z8.CENTER_NAME,H.S_CLASS_CMPS_CODE,H.S_CLASS_CLASS_CODE,Z11.CODE_NAME AS CS_CLASS_CLASS_CODE,I.NAME,H.S_CLASS_TCH_IDNO,H.OPEN_YN,H.DECISION_MK, WEEK, STIME, ETIME, NVL(H.TUT_TIMES_P, 0) AS TUT_TIMES_P, NVL(H.LAB_TIMES_P, 0) AS LAB_TIMES_P ");
        }

        // 專班類別                       專班類別中文    班級流水號     班級編號     班級名稱     隔週上     上課次數        上課中心代碼        中心名稱          校區代                   教室代                        教室類型中文        教師姓名   代碼        成班註記    審查註記
        sql.append(
            "FROM COUT001 A JOIN COUT002 B ON A.CRSNO=B.CRSNO "
            + "JOIN SYST001 Z1 ON A.CRS_CHAR=Z1.CODE AND Z1.KIND='CRS_CHAR' "
            + "LEFT JOIN SYST003 Z2 ON Z2.FACULTY_CODE=A.FACULTY_CODE "
            + "LEFT JOIN SYST003 FUCK ON FUCK.FACULTY_CODE=A.PLAN_FACULTY_CODE "
            + "LEFT JOIN SYST003 S03 ON S03.FACULTY_CODE=A.COMMON_FACULTY_CODE AND S03.ASYS='1' "
            + "LEFT JOIN COUT100 C ON A.FACULTY_CODE=C.FACULTY_CODE AND A.DISCIPLINE_CODE=C.DISCIPLINE_CODE "
            //+ "LEFT JOIN COUT103 D ON B.CRSNO=D.CRSNO AND D.FACULTY_CODE=A.FACULTY_CODE AND A.AYEAR=D.AYEAR AND A.SMS=D.SMS "
            + "LEFT JOIN SYST001 Z3 ON Z3.KIND='REQOPTION' AND A.REQOPTION_TYPE=Z3.CODE "
            + "LEFT JOIN SYST001 Z6 ON Z6.KIND='SMS' AND Z6.CODE=A.SMS "
            + "LEFT JOIN SYST001 Z7 ON Z7.KIND='NEW_REWORK' AND Z7.CODE=A.NEW_REWORK "
            + "LEFT JOIN COUT011 E ON B.CRSNO=E.CRSNO " + "LEFT JOIN COUT002 Z4 ON Z4.CRSNO=E.PREREQ_CRSNO "
            + "LEFT JOIN SYST001 Z5 ON Z5.KIND='CRS_BOOK' AND A.CRS_BOOK=Z5.CODE "
            + "LEFT JOIN COUT008 F ON F.AYEAR=A.AYEAR AND F.SMS=A.SMS AND F.CRSNO=A.CRSNO "
            + "LEFT JOIN COUT005 G ON A.AYEAR=G.AYEAR AND A.SMS=G.SMS AND A.CRSNO=G.CRSNO "
            + "LEFT JOIN SYST001 Z9 ON Z9.KIND='PRODUCE_CHOOSE' AND Z9.CODE=A.PRODUCE_TYPE "
        	+ "LEFT JOIN COUT100 C1 ON A.FACULTY_CODE=C1.FACULTY_CODE AND A.FIELD_CODE = C1.DISCIPLINE_CODE ");

        if (!Utility.nullToSpace(ht.get("getCout102")).equals(""))
        {
            sql.append(
                "LEFT JOIN COUT102 J ON J.FACULTY_CODE=D.FACULTY_CODE AND J.TOTAL_CRS_NO=D.TOTAL_CRS_NO AND J.CRS_GROUP_CODE=D.CRS_GROUP_CODE ");
        }

        if (!Utility.nullToSpace(ht.get("getCout004")).equals(""))
        {
            sql.append("LEFT JOIN COUT004 C4 ON A.AYEAR=C4.AYEAR AND A.SMS=C4.SMS AND A.CRSNO=C4.CRSNO ");
        }

        if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
        {
            sql.append("LEFT JOIN COUT022 H ON A.AYEAR=H.AYEAR AND A.SMS=H.SMS AND A.CRSNO=H.CRSNO "
                       + "LEFT JOIN TRAT001 I ON H.S_CLASS_TCH_IDNO=I.IDNO "
                       + "LEFT JOIN SYST002 Z8 ON Z8.CENTER_ABRCODE=H.S_CLASS_ABRCODE_CODE "
                       + "LEFT JOIN SYST001 Z10 ON Z10.KIND='S_CLASS_TYPE' AND Z10.CODE=H.S_CLASS_TYPE "
                       + "LEFT JOIN SYST001 Z11 ON Z10.KIND='CLSSRM_KIND' AND Z11.CODE=H.S_CLASS_CLASS_CODE ");
        }

        // 排除二軌 2008.11.20  north
        sql.append("WHERE A.OPEN3='N' ");

        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {		// 有學年直
            sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("OPEN2")).equals(""))
        {
            sql.append(" AND A.OPEN2 = '" + Utility.nullToSpace(ht.get("OPEN2")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }

        // if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
        // {
            // sql.append(" AND A.NEW_REWORK = '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
        // }
		//改固定條件    2008/12/19  by barry
		sql.append(" AND A.NEW_REWORK = '6' ");
		
        if (!Utility.nullToSpace(ht.get("ASYS")).equals(""))
        {
            sql.append(" AND Z2.ASYS='" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }

        //if (Utility.nullToSpace(ht.get("ORDERBY")).equals("CRS_GROUP_CODE"))
        //{
            sql.append(" ORDER BY A.FACULTY_CODE, A.CRSNO ");
        //}
        System.out.println("getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForUse:"+sql.toString());
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

				String x= "";

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                    	{
                    		x = getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i));
                    	}

                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

				rowHt.put("CPRODUCE_TYPE", x);
				rowHt.put("NAMES", getCout003NamesForCou109r(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), rs.getString("NEW_REWORK")));
				rowHt.put("NAMES1", getCout003NamesForCou105r(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), rs.getString("NEW_REWORK")));
				

				if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
				{
					if ("3".equals((String)rowHt.get("NEW_REWORK_TEMP")))
					{
						rowHt.put("四次面授", getHistoryCRS(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "1"));
						rowHt.put("多次面授", getHistoryCRS(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "2"));
					}
					else
					{
						rowHt.put("四次面授","");
						rowHt.put("多次面授","");
					}
				}

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }
    
    /**
     * 列印多次面授新開課程計畫表
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception              //COUT022完整    COUT004完整
     */
    public Vector getCou136rPrint(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append("SELECT A.PLAN_FACULTY_CODE, ");
        sql.append("	   FUCK.FACULTY_NAME AS PLAN_FACULTY_NAME, ");
        sql.append("	   A.CRS_STATUS, ");
        sql.append("	   A.CRSNO, ");
        sql.append("	   B.CRS_ENG, ");
        sql.append("	   B.CRS_NAME, ");
        sql.append("	   Z2.FACULTY_NAME, ");
        sql.append("	   C.DISCIPLINE_NAME, ");
        sql.append("	   A.CRD, ");
        sql.append("	   Z3.CODE_NAME AS REQOPTION, ");
        sql.append("	   Z7.CODE_NAME AS NEW_REWORK, ");
        sql.append("	   A.NEW_REWORK AS NEW_REWORK_TEMP, ");
        sql.append("	   Z1.CODE_NAME AS CRS_CHAR, ");
        sql.append("	   A.AYEAR, ");
        sql.append("	   Z6.CODE_NAME AS CSMS, ");
        sql.append("	   A.SMS, ");
        sql.append("	   A.FIRSTCOU AS PREREQ_CRS_NAME, ");
        sql.append("	   A.PRODUCE_TYPE, ");
        sql.append("	   Z9.CODE_NAME AS CPRODUCE_TYPE, ");
        sql.append("	   A.CRS_TIMES, ");
        sql.append("	   A.TUT_TIMES, ");
        sql.append("	   A.LAB_TIMES, ");
        sql.append("	   F.HW_REQUEST, ");
        sql.append("	   G.TUT_TARGET, ");
        sql.append("	   G.CRS_GUTLINE, ");
        sql.append("	   G.CRS_OUTLINE, ");
        sql.append("	   G.BASIC_KNWLDG, ");
        sql.append("	   A.DISCIPLINE_TARGET, ");
        sql.append("	   A.OPENED_DESCRIPTION, ");
        sql.append("	   A.OTHER, ");
        sql.append("	   A.EVAL_ST_NUM, ");
        sql.append("	   Z5.CODE_NAME AS CRS_BOOK, ");
        sql.append("	   A.PRO_ACADFIELD, ");
        sql.append("	   A.RESERVE_ITEM, ");
        sql.append("	   A.REWORK_ITEM, ");
        sql.append("	   SW_REQUEST, ");
        sql.append("	   (SELECT CODE_NAME ");
        sql.append("		  FROM SYST001 ");
        sql.append("		 WHERE KIND = 'NEW_REWORK' ");
        sql.append("		   AND CODE = A.NEW_REWORK) AS NEW_REWORK_NAME, ");
        sql.append("	   A.FACULTY_CODE, ");
        sql.append("	   DECODE(S03.FACULTY_NAME, '', '', '、' || S03.FACULTY_NAME) as COMMON_FACULTY_NAME, ");
        sql.append("	   C1.DISCIPLINE_NAME as FIELD_NAME, ");
        sql.append("	   G.EVAL_MANNER, ");
        sql.append("	   H.S_CLASS_TYPE, ");
        sql.append("	   Z10.CODE_NAME AS CS_CLASS_TYPE, ");
        sql.append("	   H.S_CLASS_NUM, ");
        sql.append("	   H.S_CLASS_ID, ");
        sql.append("	   H.S_CLASS_NAME, ");
        sql.append("	   H.WEEK_YN, ");
        sql.append("	   H.TUT_TIMES, ");
        sql.append("	   H.S_CLASS_ABRCODE_CODE, ");
        sql.append("	   Z8.CENTER_NAME, ");
        sql.append("	   H.S_CLASS_CMPS_CODE, ");
        sql.append("	   H.S_CLASS_CLASS_CODE, ");
        sql.append("	   Z11.CODE_NAME AS CS_CLASS_CLASS_CODE, ");
        sql.append("	   I.NAME, ");
        sql.append("	   H.S_CLASS_TCH_IDNO, ");
        sql.append("	   H.OPEN_YN, ");
        sql.append("	   H.DECISION_MK, ");
        sql.append("	   WEEK, ");
        sql.append("	   STIME, ");
        sql.append("	   ETIME, ");
        sql.append("	   NVL(H.TUT_TIMES_P, A.TUT_TIMES) AS TUT_TIMES_P, ");
        sql.append("	   NVL(H.LAB_TIMES_P, A.LAB_TIMES) AS LAB_TIMES_P, ");
        sql.append("	   H.BACKGROUND, ");
        sql.append("	   H.OPEN_NU, ");
        sql.append("	   H.CRS_OTHER AS COUT022_CRS_OTHER, ");
        sql.append("	   H.CRS_GUTLINE AS COUT022_CRS_GUTLINE, ");
        sql.append("	   H.CRS_OUTLINE AS COUT022_CRS_OUTLINE, ");
        sql.append("	   H.TUT_TARGET AS COUT022_TUT_TARGET, ");
        sql.append("	   H.CRS_RMK AS COUT022_CRS_RMK, ");
        sql.append("	   H.FAC_RELATION_RMK AS COUT022_FAC_RELATION_RMK, ");
        sql.append("	   H.CRS_RELATION_RMK AS COUT022_CRS_RELATION_RMK ");
        sql.append("  FROM COUT001 A ");
        sql.append("  JOIN COUT002 B ");
        sql.append("	ON A.CRSNO = B.CRSNO ");
        sql.append("  JOIN SYST001 Z1 ");
        sql.append("	ON A.CRS_CHAR = Z1.CODE ");
        sql.append("   AND Z1.KIND = 'CRS_CHAR' ");
        sql.append("  LEFT JOIN SYST003 Z2 ");
        sql.append("	ON Z2.FACULTY_CODE = A.FACULTY_CODE ");
        sql.append("  LEFT JOIN SYST003 FUCK ");
        sql.append("	ON FUCK.FACULTY_CODE = A.PLAN_FACULTY_CODE ");
        sql.append("  LEFT JOIN SYST003 S03 ");
        sql.append("	ON S03.FACULTY_CODE = A.COMMON_FACULTY_CODE ");
        sql.append("   AND S03.ASYS = '1' ");
        sql.append("  LEFT JOIN COUT100 C ");
        sql.append("	ON A.FACULTY_CODE = C.FACULTY_CODE ");
        sql.append("   AND A.DISCIPLINE_CODE = C.DISCIPLINE_CODE ");
        sql.append("  LEFT JOIN COUT100 C1 ");
        sql.append("	ON A.FACULTY_CODE = C1.FACULTY_CODE ");
        sql.append("   AND A.FIELD_CODE = C1.DISCIPLINE_CODE ");
        sql.append("  LEFT JOIN SYST001 Z3 ");
        sql.append("	ON Z3.KIND = 'REQOPTION' ");
        sql.append("   AND A.REQOPTION_TYPE = Z3.CODE ");
        sql.append("  LEFT JOIN SYST001 Z6 ");
        sql.append("	ON Z6.KIND = 'SMS' ");
        sql.append("   AND Z6.CODE = A.SMS ");
        sql.append("  LEFT JOIN SYST001 Z7 ");
        sql.append("	ON Z7.KIND = 'NEW_REWORK' ");
        sql.append("   AND Z7.CODE = A.NEW_REWORK ");
        sql.append("  LEFT JOIN COUT011 E ");
        sql.append("	ON E.CRSNO = A.CRSNO ");
        sql.append("  LEFT JOIN SYST001 Z5 ");
        sql.append("	ON Z5.KIND = 'CRS_BOOK' ");
        sql.append("   AND A.CRS_BOOK = Z5.CODE ");
        sql.append("  LEFT JOIN COUT008 F ");
        sql.append("	ON F.AYEAR = A.AYEAR ");
        sql.append("   AND F.SMS = A.SMS ");
        sql.append("   AND F.CRSNO = A.CRSNO ");
        sql.append("  LEFT JOIN COUT005 G ");
        sql.append("	ON A.AYEAR = G.AYEAR ");
        sql.append("   AND A.SMS = G.SMS ");
        sql.append("   AND A.CRSNO = G.CRSNO ");
        sql.append("  LEFT JOIN SYST001 Z9 ");
        sql.append("	ON Z9.KIND = 'PRODUCE_TYPE' ");
        sql.append("   AND Z9.CODE = A.PRODUCE_TYPE ");
        sql.append("  LEFT JOIN COUT022 H ");
        sql.append("	ON A.AYEAR = H.AYEAR ");
        sql.append("   AND A.SMS = H.SMS ");
        sql.append("   AND A.CRSNO = H.CRSNO ");
        sql.append("   AND H.s_class_type in ('1','2','3') ");
        sql.append("  LEFT JOIN TRAT001 I ");
        sql.append("	ON H.S_CLASS_TCH_IDNO = I.IDNO ");
        sql.append("  LEFT JOIN SYST002 Z8 ");
        sql.append("	ON Z8.CENTER_ABRCODE = H.S_CLASS_ABRCODE_CODE ");
        sql.append("  LEFT JOIN SYST001 Z10 ");
        sql.append("	ON Z10.KIND = 'S_CLASS_TYPE' ");
        sql.append("   AND Z10.CODE = H.S_CLASS_TYPE ");
        sql.append("  LEFT JOIN SYST001 Z11 ");
        sql.append("	ON Z10.KIND = 'CLSSRM_KIND' ");
        sql.append("   AND Z11.CODE = H.S_CLASS_CLASS_CODE ");
        sql.append(" WHERE A.OPEN3 = 'Y' ");
        sql.append("   AND A.NEW_REWORK = '1' ");
        

        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {		
            sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }
        
        sql.append(" ORDER BY Z8.CENTER_NAME, A.CRSNO ");
        
        
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

				String x= "";

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                	{
                		x = getCPRODUCE_TYPE(dbmanager, conn, rs.getString(i));
                	}
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

				rowHt.put("CPRODUCE_TYPE", x);

				rowHt.put("NAMES", getCout003Names(dbmanager, conn, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO")));

				if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
				{
					if ("3".equals((String)rowHt.get("NEW_REWORK_TEMP")))
					{
						rowHt.put("一軌", getHistoryCRS(dbmanager, conn, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "1"));
						rowHt.put("二軌", getHistoryCRS(dbmanager, conn, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "2"));
					}
					else
					{
						rowHt.put("一軌","");
						rowHt.put("二軌","");
					}
				}
				
                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }
    
    
    /**
     * JOIN 其它 Cout001,Cout002,Cout005,Cout008,Cout011,Cout100,Cout103 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception              //COUT022完整    COUT004完整
     */
    public Vector getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForCOU128R(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        	sql.append
		(	//原先「先修課程」來源→(SELECT X.CRS_NAME FROM COUT002 X WHERE E.CRSNO=X.CRSNO) AS PREREQ_CRS_NAME
			//             改為→由COUT001抓取FIRSTCOU資料 
			"SELECT A.PLAN_FACULTY_CODE, FUCK.FACULTY_NAME AS PLAN_FACULTY_NAME, A.CRS_STATUS, A.CRSNO,B.CRS_ENG,B.CRS_NAME,Z2.FACULTY_NAME,C.DISCIPLINE_NAME,A.CRD,Z3.CODE_NAME AS REQOPTION,Z7.CODE_NAME AS NEW_REWORK, A.NEW_REWORK AS NEW_REWORK_TEMP " +
			",Z1.CODE_NAME AS CRS_CHAR,A.AYEAR,Z6.CODE_NAME AS CSMS,A.SMS,A.FIRSTCOU AS PREREQ_CRS_NAME,A.PRODUCE_TYPE,Z9.CODE_NAME AS CPRODUCE_TYPE " +
			",A.CRS_TIMES,A.TUT_TIMES,A.LAB_TIMES,F.HW_REQUEST,G.TUT_TARGET,G.CRS_GUTLINE,G.CRS_OUTLINE,G.BASIC_KNWLDG, A.DISCIPLINE_TARGET, A.OPENED_DESCRIPTION, A.OTHER, A.EVAL_ST_NUM  " +
			",Z5.CODE_NAME AS CRS_BOOK,A.PRO_ACADFIELD,A.RESERVE_ITEM,A.REWORK_ITEM,SW_REQUEST, (SELECT CODE_NAME FROM SYST001 WHERE KIND='NEW_REWORK' AND CODE=A.NEW_REWORK) AS NEW_REWORK_NAME, A.FACULTY_CODE, DECODE(S03.FACULTY_NAME,'','','、'||S03.FACULTY_NAME) as COMMON_FACULTY_NAME  "+
			", C1.DISCIPLINE_NAME as FIELD_NAME, nvl(h.EVAL_MANNER,g.eval_manner) as eval_manner "
		);

        if (!Utility.nullToSpace(ht.get("getCout102")).equals(""))
        {
            sql.append(",J.CRS_GROUP_CODE,J.CRS_GROUP_CODE_NAME,J.CRS_GROUP_CRD ");
        }

        // 科目群帶碼       科目群名              科目群總學分
        if (!Utility.nullToSpace(ht.get("getCout004")).equals(""))
        {	// 判斷是否引用_TABLE COUT004 課程媒體管控資料
            sql.append(
                ",C4.AFFAIRS_MEETING,C4.AFFAIRS_DATE,C4.UCPC,C4.UCPC_DATE,C4.COMMON_CRS,C4.COMMON_DATE,C4.FEC,C4.FEC_DATE,C4.UFEC,C4.UFEC_DATE,C4.CRSTEAM,C4.CRSTEAM_DATE,C4.CONTRACT_DATE,C4.CONTRACT_PLAN_DATE,C4.DEADLINE_DATE,C4.PRJ_MEETING,C4.PRJ_DATE,C4.PRODUCE_MEETING,C4.PRODUCE_DATE,C4.S_RECORD_DATE,C4.RMK ");
        }

        // 系務會議                           校課策會             共同課程委員會               系教評會           校教評會             教學策劃小組座談會         繳合約書日       繳教科書計畫日        書稿截止日       企劃會議                   製作會議                           開始錄製日
        if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
        {
            sql.append(
                ",H.S_CLASS_TYPE,Z10.CODE_NAME AS CS_CLASS_TYPE,H.S_CLASS_NUM,H.S_CLASS_ID,H.S_CLASS_NAME,H.WEEK_YN,H.TUT_TIMES,H.S_CLASS_ABRCODE_CODE,Z8.CENTER_NAME,H.S_CLASS_CMPS_CODE,H.S_CLASS_CLASS_CODE,Z11.CODE_NAME AS CS_CLASS_CLASS_CODE,I.NAME,H.S_CLASS_TCH_IDNO,H.OPEN_YN,H.DECISION_MK, WEEK, STIME, ETIME, NVL(H.TUT_TIMES_P, A.TUT_TIMES) AS TUT_TIMES_P, NVL(H.LAB_TIMES_P, A.LAB_TIMES) AS LAB_TIMES_P, H.BACKGROUND, H.OPEN_NU "+
        		",H.CRS_OTHER AS COUT022_CRS_OTHER "+
        		",H.CRS_GUTLINE AS COUT022_CRS_GUTLINE "+
        		",H.CRS_OUTLINE AS COUT022_CRS_OUTLINE "+
        		",H.TUT_TARGET AS COUT022_TUT_TARGET "+
        		",H.CRS_RMK AS COUT022_CRS_RMK "
            		);
        }

        // 專班類別                       專班類別中文    班級流水號     班級編號     班級名稱     隔週上     上課次數        上課中心代碼        中心名稱          校區代                   教室代                        教室類型中文        教師姓名   代碼        成班註記    審查註記
        sql.append(
            "FROM COUT001 A JOIN COUT002 B ON A.CRSNO=B.CRSNO "
            + "JOIN SYST001 Z1 ON A.CRS_CHAR=Z1.CODE AND Z1.KIND='CRS_CHAR' "
            + "LEFT JOIN SYST003 Z2 ON Z2.FACULTY_CODE=A.FACULTY_CODE "
            + "LEFT JOIN SYST003 FUCK ON FUCK.FACULTY_CODE=A.PLAN_FACULTY_CODE "
            + "LEFT JOIN SYST003 S03 ON S03.FACULTY_CODE=A.COMMON_FACULTY_CODE AND S03.ASYS='1' "
            + "LEFT JOIN COUT100 C ON A.FACULTY_CODE=C.FACULTY_CODE AND A.DISCIPLINE_CODE=C.DISCIPLINE_CODE "
            + "LEFT JOIN COUT100 C1 ON A.FACULTY_CODE=C1.FACULTY_CODE AND A.FIELD_CODE=C1.DISCIPLINE_CODE "
            //+ "LEFT JOIN COUT103 D ON B.CRSNO=D.CRSNO AND D.FACULTY_CODE=A.FACULTY_CODE AND A.AYEAR=D.AYEAR AND A.SMS=D.SMS "
            + "LEFT JOIN SYST001 Z3 ON Z3.KIND='REQOPTION' AND A.REQOPTION_TYPE=Z3.CODE "
            + "LEFT JOIN SYST001 Z6 ON Z6.KIND='SMS' AND Z6.CODE=A.SMS "
            + "LEFT JOIN SYST001 Z7 ON Z7.KIND='NEW_REWORK' AND Z7.CODE=A.NEW_REWORK "
            + "LEFT JOIN COUT011 E ON E.CRSNO=A.CRSNO "
            + "LEFT JOIN SYST001 Z5 ON Z5.KIND='CRS_BOOK' AND A.CRS_BOOK=Z5.CODE "
            + "LEFT JOIN COUT008 F ON F.AYEAR=A.AYEAR AND F.SMS=A.SMS AND F.CRSNO=A.CRSNO "
            + "LEFT JOIN COUT005 G ON A.AYEAR=G.AYEAR AND A.SMS=G.SMS AND A.CRSNO=G.CRSNO "
            + "LEFT JOIN SYST001 Z9 ON Z9.KIND='PRODUCE_TYPE' AND Z9.CODE=A.PRODUCE_TYPE ");

        if (!Utility.nullToSpace(ht.get("getCout102")).equals(""))
        {
            sql.append(
                "LEFT JOIN COUT102 J ON J.FACULTY_CODE=D.FACULTY_CODE AND J.TOTAL_CRS_NO=D.TOTAL_CRS_NO AND J.CRS_GROUP_CODE=D.CRS_GROUP_CODE ");
        }

        if (!Utility.nullToSpace(ht.get("getCout004")).equals(""))
        {
            sql.append("LEFT JOIN COUT004 C4 ON A.AYEAR=C4.AYEAR AND A.SMS=C4.SMS AND A.CRSNO=C4.CRSNO ");
        }

        if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
        {
            sql.append("LEFT JOIN COUT022 H ON A.AYEAR=H.AYEAR AND A.SMS=H.SMS AND A.CRSNO=H.CRSNO AND H.S_CLASS_TYPE != 'A' "
                       + "LEFT JOIN TRAT001 I ON H.S_CLASS_TCH_IDNO=I.IDNO "
                       + "LEFT JOIN SYST002 Z8 ON Z8.CENTER_ABRCODE=H.S_CLASS_ABRCODE_CODE "
                       + "LEFT JOIN SYST001 Z10 ON Z10.KIND='S_CLASS_TYPE' AND Z10.CODE=H.S_CLASS_TYPE "
                       + "LEFT JOIN SYST001 Z11 ON Z10.KIND='CLSSRM_KIND' AND Z11.CODE=H.S_CLASS_CLASS_CODE ");
        }

        sql.append("WHERE 0=0 ");

        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {		// 有學年直
            sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("OPEN2")).equals(""))
        {
            sql.append(" AND A.OPEN2 = '" + Utility.nullToSpace(ht.get("OPEN2")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
        {
            sql.append(" AND A.NEW_REWORK = '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("ASYS")).equals(""))
        {
            sql.append(" AND Z2.ASYS='" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }
        
        if (!Utility.nullToSpace(ht.get("S_CLASS_NUM")).equals(""))
        {
            sql.append(" AND H.S_CLASS_NUM ='" + Utility.nullToSpace(ht.get("S_CLASS_NUM")) + "' ");
        }
        
		
		if("Y".equals(Utility.nullToSpace(ht.get("cout128r"))))
			sql.append("AND A.OPEN3='Y' AND A.NEW_REWORK != '1' ");

        //if (Utility.nullToSpace(ht.get("ORDERBY")).equals("CRS_GROUP_CODE"))
        //{
            sql.append(" ORDER BY Z8.CENTER_NAME, A.CRSNO ");
        //}
        System.out.println("getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForUse:"+sql.toString());
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

				String x= "";

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                    	{
                    		x = getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i));
                    	}

                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

				rowHt.put("CPRODUCE_TYPE", x);

				rowHt.put("NAMES", getCout003Names(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO")));

				if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
				{
					if ("3".equals((String)rowHt.get("NEW_REWORK_TEMP")))
					{
						rowHt.put("一軌", getHistoryCRS(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "1"));
						rowHt.put("二軌", getHistoryCRS(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "2"));
					}
					else
					{
						rowHt.put("一軌","");
						rowHt.put("二軌","");
					}
				}
				
                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }
    
    public Vector getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForCOU138R(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        	sql.append
		(	//原先「先修課程」來源→(SELECT X.CRS_NAME FROM COUT002 X WHERE E.CRSNO=X.CRSNO) AS PREREQ_CRS_NAME
			//             改為→由COUT001抓取FIRSTCOU資料 
			"SELECT A.PLAN_FACULTY_CODE, FUCK.FACULTY_NAME AS PLAN_FACULTY_NAME, A.CRS_STATUS, A.CRSNO,B.CRS_ENG,B.CRS_NAME,Z2.FACULTY_NAME,C.DISCIPLINE_NAME,A.CRD,Z3.CODE_NAME AS REQOPTION,Z7.CODE_NAME AS NEW_REWORK, A.NEW_REWORK AS NEW_REWORK_TEMP " +
			",Z1.CODE_NAME AS CRS_CHAR,A.AYEAR,Z6.CODE_NAME AS CSMS,A.SMS,A.FIRSTCOU AS PREREQ_CRS_NAME,A.PRODUCE_TYPE,Z9.CODE_NAME AS CPRODUCE_TYPE " +
			",A.CRS_TIMES,A.TUT_TIMES,A.LAB_TIMES,F.HW_REQUEST,G.TUT_TARGET,G.CRS_GUTLINE,G.CRS_OUTLINE,G.BASIC_KNWLDG, A.DISCIPLINE_TARGET, A.OPENED_DESCRIPTION, A.OTHER, A.EVAL_ST_NUM  " +
			",Z5.CODE_NAME AS CRS_BOOK,A.PRO_ACADFIELD,A.RESERVE_ITEM,A.REWORK_ITEM,SW_REQUEST, (SELECT CODE_NAME FROM SYST001 WHERE KIND='NEW_REWORK' AND CODE=A.NEW_REWORK) AS NEW_REWORK_NAME, A.FACULTY_CODE, DECODE(S03.FACULTY_NAME,'','','、'||S03.FACULTY_NAME) as COMMON_FACULTY_NAME  "+
			", C1.DISCIPLINE_NAME as FIELD_NAME, nvl(h.EVAL_MANNER,g.eval_manner) as eval_manner "
		);

        if (!Utility.nullToSpace(ht.get("getCout102")).equals(""))
        {
            sql.append(",J.CRS_GROUP_CODE,J.CRS_GROUP_CODE_NAME,J.CRS_GROUP_CRD ");
        }

        // 科目群帶碼       科目群名              科目群總學分
        if (!Utility.nullToSpace(ht.get("getCout004")).equals(""))
        {	// 判斷是否引用_TABLE COUT004 課程媒體管控資料
            sql.append(
                ",C4.AFFAIRS_MEETING,C4.AFFAIRS_DATE,C4.UCPC,C4.UCPC_DATE,C4.COMMON_CRS,C4.COMMON_DATE,C4.FEC,C4.FEC_DATE,C4.UFEC,C4.UFEC_DATE,C4.CRSTEAM,C4.CRSTEAM_DATE,C4.CONTRACT_DATE,C4.CONTRACT_PLAN_DATE,C4.DEADLINE_DATE,C4.PRJ_MEETING,C4.PRJ_DATE,C4.PRODUCE_MEETING,C4.PRODUCE_DATE,C4.S_RECORD_DATE,C4.RMK ");
        }

        // 系務會議                           校課策會             共同課程委員會               系教評會           校教評會             教學策劃小組座談會         繳合約書日       繳教科書計畫日        書稿截止日       企劃會議                   製作會議                           開始錄製日
        if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
        {
            sql.append(
                ",H.S_CLASS_TYPE,Z10.CODE_NAME AS CS_CLASS_TYPE,H.S_CLASS_NUM,H.S_CLASS_ID,H.S_CLASS_NAME,H.WEEK_YN,H.TUT_TIMES,H.S_CLASS_ABRCODE_CODE,Z8.CENTER_NAME,H.S_CLASS_CMPS_CODE,H.S_CLASS_CLASS_CODE,Z11.CODE_NAME AS CS_CLASS_CLASS_CODE,I.NAME,H.S_CLASS_TCH_IDNO,H.OPEN_YN,H.DECISION_MK, WEEK, STIME, ETIME, NVL(H.TUT_TIMES_P, A.TUT_TIMES) AS TUT_TIMES_P, NVL(H.LAB_TIMES_P, A.LAB_TIMES) AS LAB_TIMES_P, H.BACKGROUND, H.OPEN_NU "+
        		",H.CRS_OTHER AS COUT022_CRS_OTHER "+
        		",H.CRS_GUTLINE AS COUT022_CRS_GUTLINE "+
        		",H.CRS_OUTLINE AS COUT022_CRS_OUTLINE "+
        		",H.TUT_TARGET AS COUT022_TUT_TARGET "+
        		",H.CRS_RMK AS COUT022_CRS_RMK "
            		);
        }

        // 專班類別                       專班類別中文    班級流水號     班級編號     班級名稱     隔週上     上課次數        上課中心代碼        中心名稱          校區代                   教室代                        教室類型中文        教師姓名   代碼        成班註記    審查註記
        sql.append(
            "FROM COUT001 A JOIN COUT002 B ON A.CRSNO=B.CRSNO "
            + "JOIN SYST001 Z1 ON A.CRS_CHAR=Z1.CODE AND Z1.KIND='CRS_CHAR' "
            + "LEFT JOIN SYST003 Z2 ON Z2.FACULTY_CODE=A.FACULTY_CODE "
            + "LEFT JOIN SYST003 FUCK ON FUCK.FACULTY_CODE=A.PLAN_FACULTY_CODE "
            + "LEFT JOIN SYST003 S03 ON S03.FACULTY_CODE=A.COMMON_FACULTY_CODE AND S03.ASYS='1' "
            + "LEFT JOIN COUT100 C ON A.FACULTY_CODE=C.FACULTY_CODE AND A.DISCIPLINE_CODE=C.DISCIPLINE_CODE "
            + "LEFT JOIN COUT100 C1 ON A.FACULTY_CODE=C1.FACULTY_CODE AND A.FIELD_CODE=C1.DISCIPLINE_CODE "
            //+ "LEFT JOIN COUT103 D ON B.CRSNO=D.CRSNO AND D.FACULTY_CODE=A.FACULTY_CODE AND A.AYEAR=D.AYEAR AND A.SMS=D.SMS "
            + "LEFT JOIN SYST001 Z3 ON Z3.KIND='REQOPTION' AND A.REQOPTION_TYPE=Z3.CODE "
            + "LEFT JOIN SYST001 Z6 ON Z6.KIND='SMS' AND Z6.CODE=A.SMS "
            + "LEFT JOIN SYST001 Z7 ON Z7.KIND='NEW_REWORK' AND Z7.CODE=A.NEW_REWORK "
            + "LEFT JOIN COUT011 E ON E.CRSNO=A.CRSNO "
            + "LEFT JOIN SYST001 Z5 ON Z5.KIND='CRS_BOOK' AND A.CRS_BOOK=Z5.CODE "
            + "LEFT JOIN COUT008 F ON F.AYEAR=A.AYEAR AND F.SMS=A.SMS AND F.CRSNO=A.CRSNO "
            + "LEFT JOIN COUT005 G ON A.AYEAR=G.AYEAR AND A.SMS=G.SMS AND A.CRSNO=G.CRSNO "
            + "LEFT JOIN SYST001 Z9 ON Z9.KIND='PRODUCE_TYPE' AND Z9.CODE=A.PRODUCE_TYPE ");

        if (!Utility.nullToSpace(ht.get("getCout102")).equals(""))
        {
            sql.append(
                "LEFT JOIN COUT102 J ON J.FACULTY_CODE=D.FACULTY_CODE AND J.TOTAL_CRS_NO=D.TOTAL_CRS_NO AND J.CRS_GROUP_CODE=D.CRS_GROUP_CODE ");
        }

        if (!Utility.nullToSpace(ht.get("getCout004")).equals(""))
        {
            sql.append("LEFT JOIN COUT004 C4 ON A.AYEAR=C4.AYEAR AND A.SMS=C4.SMS AND A.CRSNO=C4.CRSNO ");
        }

        if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
        {
            sql.append("LEFT JOIN COUT022 H ON A.AYEAR=H.AYEAR AND A.SMS=H.SMS AND A.CRSNO=H.CRSNO "
                       + "LEFT JOIN TRAT001 I ON H.S_CLASS_TCH_IDNO=I.IDNO "
                       + "LEFT JOIN SYST002 Z8 ON Z8.CENTER_ABRCODE=H.S_CLASS_ABRCODE_CODE "
                       + "LEFT JOIN SYST001 Z10 ON Z10.KIND='S_CLASS_TYPE' AND Z10.CODE=H.S_CLASS_TYPE "
                       + "LEFT JOIN SYST001 Z11 ON Z10.KIND='CLSSRM_KIND' AND Z11.CODE=H.S_CLASS_CLASS_CODE ");
        }

        sql.append("WHERE 0=0 ");

        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {		// 有學年直
            sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("OPEN2")).equals(""))
        {
            sql.append(" AND A.OPEN2 = '" + Utility.nullToSpace(ht.get("OPEN2")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
        {
            sql.append(" AND A.NEW_REWORK = '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("ASYS")).equals(""))
        {
            sql.append(" AND Z2.ASYS='" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }
        
        if (!Utility.nullToSpace(ht.get("S_CLASS_NUM")).equals(""))
        {
            sql.append(" AND H.S_CLASS_NUM ='" + Utility.nullToSpace(ht.get("S_CLASS_NUM")) + "' ");
        }
        
		
		if("Y".equals(Utility.nullToSpace(ht.get("cout128r"))))
			sql.append("AND A.OPEN3='Y' AND A.NEW_REWORK != '1' ");

        //if (Utility.nullToSpace(ht.get("ORDERBY")).equals("CRS_GROUP_CODE"))
        //{
            sql.append(" ORDER BY Z8.CENTER_NAME, A.CRSNO ");
        //}
        System.out.println("getCout001Cout002Cout005Cout008Cout011Cout100Cout103ForUse:"+sql.toString());
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

				String x= "";

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                    	{
                    		x = getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i));
                    	}

                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

				rowHt.put("CPRODUCE_TYPE", x);

				rowHt.put("NAMES", getCout003Names(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO")));

				if (!Utility.nullToSpace(ht.get("getCout022")).equals(""))
				{
					if ("3".equals((String)rowHt.get("NEW_REWORK_TEMP")))
					{
						rowHt.put("一軌", getHistoryCRS(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "1"));
						rowHt.put("二軌", getHistoryCRS(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), "2"));
					}
					else
					{
						rowHt.put("一軌","");
						rowHt.put("二軌","");
					}
				}
				
                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    private String getHistoryCRS(DBManager dbManager, Connection conn, String AYEAR, String SMS, String CRSNO, String FLAG) throws Exception {
		String Names = "";
		String temp = "";

		DBResult rs = null;

		try {
			COUT001DAO COUT001 = new COUT001DAO(dbManager, conn);

			COUT001.setResultColumn("TO_NUMBER(COUT001.AYEAR)||(SELECT CODE_NAME FROM SYST001 WHERE KIND='SMS' AND CODE=COUT001.SMS) AS AYEARSMS ");

			if ("1".equals(FLAG)) {
				temp = "AND COUT001.OPEN1='Y' AND COUT001.OPEN3='N' ";
			} else {
				temp = "AND COUT001.OPEN1='N' AND COUT001.OPEN3='Y' ";
			}

			COUT001.setWhere("COUT001.CRSNO='" + CRSNO + "' " + "AND COUT001.AYEAR||COUT001.SMS < '" + AYEAR + "'||'" + SMS + "' " + "AND COUT001.CRS_STATUS='5' " + "AND COUT001.EST_RESULT_MK='Y' " + temp + "ORDER BY cout001.ayear || cout001.sms desc ");

			rs = COUT001.query();

			int i = 0;

			while (rs.next()) {
				
				if (i > 0) {
					Names += "，";
				}

				Names += rs.getString("AYEARSMS");
				i++;
				
				if ("1".equals(FLAG)) {
					break;
				}
				
			}

			if (!"".equals(Names)) {
				if ("1".equals(FLAG)) {
					Names = "(" + com.nou.UtilityX.getCouName(1) + "已開過，已開學期別：" + Names + ")";
				}
			}

			return Names;

		} catch (Exception ex) {
			throw ex;
		} finally {
			rs.close();
		}
	}

    /**
     * for--COU107R_課程管控表用
     * JOIN 其它 Cout001,Cout002,Cout004,syst001 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception              //COUT022完整    COUT004完整
     */
    public Vector getCout001Cout002Cout004Syst001ForUse(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT AYEAR, SUBSTR(CODE_NAME ,1,1) AS CSMS , A.SMS AS SMS ,A.CRSNO AS CRSNO , C.CRS_NAME AS CCRSNO,D.CRD,D.PRODUCE_TYPE, AFFAIRS_MEETING AS AFFAIRS, AFFAIRS_DATE, "+
            "       UCPC, UCPC_DATE, FEC, FEC_DATE, UFEC, UFEC_DATE,  CRSTEAM, CRSTEAM_DATE, " +
            "       CONTRACT_DATE, CONTRACT_PLAN_DATE , DEADLINE_DATE , PRJ_MEETING AS PRJ, PRJ_DATE,  PRODUCE_MEETING AS FED, PRODUCE_DATE,  S_RECORD_DATE,  RMK  " +
            " FROM COUT004 A " +
            " JOIN SYST001 B ON A.SMS=B.CODE AND B.KIND='SMS' " +
            " JOIN COUT002 C ON A.CRSNO=C.CRSNO "+
            " JOIN COUT001 D ON A.AYEAR=D.AYEAR AND A.SMS=D.SMS AND A.CRSNO=D.CRSNO WHERE 0=0 "
        );

        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            sql.append(" AND A.AYEAR >= '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }
        if (!Utility.nullToSpace(ht.get("AYEAR2")).equals(""))
        {
            sql.append(" AND A.AYEAR <= '" + Utility.nullToSpace(ht.get("AYEAR2")) + "' ");
        }
        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND  D.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1"))
        {
            sql.append(" AND D.open3 <> 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2"))
        {
            sql.append(" AND D.open3 = 'Y' ");
        }


      //  sql.append(" ORDER BY J.CRS_GROUP_CODE ");


        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    /**
     * JOIN 其它 Cout001,Cout002,Cout100 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception           *****採計學系
     */
    public Vector getCout001Cou002Cout103ForUse(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT I.CODE_NAME AS CSMS,B.CRS_NAME,A.CRD,A.EST_RESULT_MK,A.PRODUCE_TYPE,M.CODE_NAME AS CRS_BOOK," +
            	"F.CODE_NAME AS CNEW_REWORK,A.AYEAR,A.SMS,A.CRSNO,A.FACULTY_CODE,J.CODE_NAME AS REQOPTION_TYPE_NAME, " +
            	"S3.FACULTY_NAME AS FACULTY_NAME,K.CODE_NAME AS PRODUCE_NAME,L.CODE_NAME AS TEACHING_TYPE,M.CODE_NAME AS CRS_BOOK "+
            "FROM COUT002 B " +
            "JOIN COUT001 A ON A.CRSNO=B.CRSNO " +
            "JOIN SYST001 F ON F.KIND='NEW_REWORK' AND F.CODE=A.NEW_REWORK " +
            "JOIN SYST001 I ON I.KIND='SMS' AND I.CODE=A.SMS " +
            "JOIN SYST001 J ON J.KIND='REQOPTION' AND J.CODE=A.REQOPTION_TYPE " +
            "JOIN SYST001 K ON K.KIND = 'PRODUCE_CHOOSE' AND K.CODE = A.PRODUCE_TYPE " + 
            "JOIN SYST001 M ON M.KIND='CRS_BOOK' AND A.CRS_BOOK=M.CODE "+ 
            "JOIN SYST001 L ON L.KIND='TEACHING_TYPE_NAME' AND A.TEACHING_TYPE_NAME=L.CODE " +
        	"JOIN SYST003 S3 ON S3.ASYS ='1' AND A.FACULTY_CODE = S3.FACULTY_CODE  " +
            "WHERE 1=1 "
            );
        
        if (!Utility.nullToSpace(ht.get("PASS_MK")).equals("")){
			sql.append(" AND A.CRS_STATUS='5' AND A.EST_RESULT_MK='Y' ");
		}else{
			sql.append(" AND A.CRS_STATUS IN('1','2','5') ");
		}
        
		if (!Utility.nullToSpace(ht.get("AYEAR")).equals("")){
			sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		}
		if (!Utility.nullToSpace(ht.get("SMS")).equals("")){
			sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		}
		//if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals("")){
		//	sql.append(" AND A.PLAN_FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
		//}        
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1")){
            sql.append(" AND A.open1 = 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2")){
            sql.append(" AND A.open3 = 'Y' AND A.open1 = 'N' ");
        }

        sql.append("ORDER BY DECODE(A.OPEN1,'Y','1','2'),DECODE(A.FACULTY_CODE, '80', '01', '90', '02', A.FACULTY_CODE),DECODE(A.NEW_REWORK,'1','1','6','2','3','3','4','3','5','5','2','6'), A.CRSNO ");

        DBResult	rs	= null;		
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable	rowHt	= null;
            while (rs.next())
            {
                rowHt	= new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					rowHt.put(rs.getColumnName(i), rs.getString(i));
                }
                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }
        return result;
    }

	/**
     * JOIN 其它 Cout001,Cout002,Cout100 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception           *****採計學系
     */
	/**cou116r有用到 2008/05/27 by barry*/
    public Vector getCout001Cou002Cout103ForUse2(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        Vector	result	= new Vector();

        // 判斷共有多少學系
        SYST008DAO syst008 = new SYST008DAO(dbmanager, conn);
    	syst008.setResultColumn("COUNT(1) as NUM");
    	syst008.setASYS("2");
    	DBResult rs = syst008.query();
    	
    	int totalFacultyNum = 0;
    	if(rs.next())
    		totalFacultyNum = rs.getInt("NUM");
		rs.close();
        
        if (sql.length() > 0)
            sql.delete(0, sql.length());

        sql.append(
            "SELECT UNIQUE I.CODE_NAME AS CSMS,B.CRS_NAME,A.CRD,A.EST_RESULT_MK,A.PRODUCE_TYPE," +
            	"F.CODE_NAME AS CNEW_REWORK,A.AYEAR,A.SMS,A.CRSNO,A.FACULTY_CODE, " +
            	"(SELECT FACULTY_NAME FROM SYST003 WHERE ASYS='1' AND FACULTY_CODE=A.FACULTY_CODE) AS FACULTY_NAME, " +
            	"D.CRS_GROUP_CODE_NAME,D.CRS_GROUP_CODE, DECODE(A.OPEN1,'Y','1','2') as ORDER_SELECT " +
            "FROM COUT002 B " +
	            "JOIN COUT001 A ON A.CRSNO=B.CRSNO " +
	            "JOIN SYST001 F ON F.KIND='NEW_REWORK' AND F.CODE=A.NEW_REWORK " +
	            "JOIN SYST001 I ON I.KIND='SMS' AND I.CODE=A.SMS " +
	            "LEFT JOIN (SELECT DISTINCT a.total_crs_no, a.crs_group_code, a.crs_group_code_name " +
	            "			FROM cout102 a " +
	            "			JOIN syst008 b ON a.faculty_code = b.faculty_code AND a.total_crs_no = b.total_crs_no " +
	            "			WHERE b.asys = '2' " +
	            "     ) d ON 0 = 0 " +
            "WHERE 0=0 "
        );

		if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
			sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
			sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");

        sql.append("AND A.CRS_STATUS IN ('2','5') ");
        sql.append("ORDER BY ORDER_SELECT, DECODE(A.FACULTY_CODE, '90', '00'), A.CRSNO, D.CRS_GROUP_CODE_NAME ");
        
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Vector tmp = new Vector();
            Hashtable	rowHt	= null;
            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++){
					if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                        		rowHt.put("CPRODUCE_TYPE", getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i)));

					rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

				/**塞入採計學系*/
				rowHt.put("FACULTY_NAME_COUNT", getCout103TotalCrsNames(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), (String)rowHt.get("CRS_GROUP_CODE")));
				
				// 原只要有採計學系就放進來,現改成
			//	if (!rowHt.get("FACULTY_NAME_COUNT").equals("")){
					// 1.所有系都有的類別要放進來   2.相關學系如不是所有學系,則不顯示,但其他學系則需顯示  3.如該科並無設定學系時
					if(rowHt.get("FACULTY_NAME_COUNT").equals("")||rowHt.get("FACULTY_NAME_COUNT").toString().split("，").length==totalFacultyNum||!rowHt.get("CRS_GROUP_CODE").toString().equals("004"))
						tmp.add(rowHt);
			//	}
            }
            
            // 2008.12.6 處理當同一科有多種類別時要合併成一筆
            String nextCheck = ""; // 下一筆資料的比較變數
	        String totalCombineData = ""; // 所欲組合的資料
	        // 一個迴圈表示同一個同一種類別---ex:實用英文---校必修            如有實用英文---科必修  則為下一筆           有依照科目排序
	        for(int i=0; i<tmp.size(); i++){
	        	Hashtable content = (Hashtable)tmp.get(i);
	        	String check = content.get("CRSNO").toString(); // 所有要比較的key的組合
	        	String facultyNames = content.get("FACULTY_NAME_COUNT").toString(); // 該科的所有學系
	        	String crsGroupCodeName = content.get("CRS_GROUP_CODE_NAME").toString(); // 該科的該學系的類別
	        	
	        	// 檢核所要比較的欄位是否改變
	        	if(i!=tmp.size()-1)
	        		nextCheck = ((Hashtable)tmp.get(i+1)).get("CRSNO").toString(); // 下一筆所有要比較的key的組合
	        	else
	        		nextCheck = "";

	        	if(!totalCombineData.equals(""))
	    			totalCombineData+="<br>";
	        	
	        	
	        	// 將專業選修-->專修    科必修--->必修     適用於不是所有學系共同的科目
	        	String tmpCrsGroupCodeName = crsGroupCodeName.replaceAll("專業選修", "專修").replaceAll("科必修", "必修"); 
	        	// 因相同科目會有多個學系是相同類別
	        	totalCombineData+=(facultyNames.replaceAll("，", tmpCrsGroupCodeName+"<br>")+tmpCrsGroupCodeName);  
	        	
	        	// 如為所有學系共同的,則僅顯示校必修/相關選修...
	        	if(facultyNames.split("，").length==totalFacultyNum)
	        		totalCombineData = crsGroupCodeName;
	        		
	        	// 如和下筆資料相同則累計考試科目相關資料
	        	if(!check.equals(nextCheck)){
	        		Hashtable tempHt = content;
	        		tempHt.put("COMBINE_DATA", totalCombineData);  
	        		result.add(tempHt);
	        		
	        		// 清空檢核變數
	        		totalCombineData="";
	        	}
	        }        
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    private String getCout003Names1(DBManager dbManager, Connection conn, String AYEAR, String SMS, String CRSNO) throws Exception
    {
    	String Names1 	= "";
    	DBResult	rs			= null;

    	try
    	{
			COUT003DAO	COUT003	= new COUT003DAO(dbManager, conn);
	
			COUT003.setResultColumn("(SELECT TRAT001.NAME FROM TRAT001 WHERE TRAT001.IDNO=COUT003.IDNO AND ROWNUM=1) AS NAME ");
	
			COUT003.setWhere
			(
				"COUT003.AYEAR='" + AYEAR + "' " +
				"AND COUT003.SMS='" + SMS + "' " +
				"AND COUT003.CRSNO='" + CRSNO + "' " +
				"AND JOB_TYPE IN ('11','12') "
			);

			rs	= COUT003.query();
	
			int i = 0;

			while (rs.next())
			{
				if (i > 0)
				{
					Names1 += "，";
				}
	
				Names1 += rs.getString("NAME");
	
				i++;
			}
			return Names1;

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
    
    private String getCout003Names(DBManager dbManager, Connection conn, String AYEAR, String SMS, String CRSNO) throws Exception
    {
    	String Names 	= "";
    	DBResult	rs			= null;

    	try
    	{
			COUT003DAO	COUT003	= new COUT003DAO(dbManager, conn);
	
			COUT003.setResultColumn("(SELECT TRAT001.NAME FROM TRAT001 WHERE TRAT001.IDNO=COUT003.IDNO AND ROWNUM=1) AS NAME ");
	
			COUT003.setWhere
			(
				"COUT003.AYEAR='" + AYEAR + "' " +
				"AND COUT003.SMS='" + SMS + "' " +
				"AND COUT003.CRSNO='" + CRSNO + "' " +
				"AND JOB_TYPE IN ('13','14','15') "
			);

			rs	= COUT003.query();
	
			int i = 0;

			while (rs.next())
			{
				if (i > 0)
				{
					Names += "，";
				}
	
				Names += rs.getString("NAME");
	
				i++;
			}
			return Names;

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
    
    /**
     * JOIN 其它 Cout001,Cout002,Cout100 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception           *****採計學系
     */
    public Vector getCout001Cou002Cout103ForCou108r(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT I.CODE_NAME AS CSMS,B.CRS_NAME,A.CRD,A.EST_RESULT_MK,A.PRODUCE_TYPE,M.CODE_NAME AS CRS_BOOK," +
            	"F.CODE_NAME AS CNEW_REWORK,A.AYEAR,A.SMS,A.CRSNO,A.FACULTY_CODE,J.CODE_NAME AS REQOPTION_TYPE_NAME, " +
            	"S3.FACULTY_NAME AS FACULTY_NAME,K.CODE_NAME AS PRODUCE_NAME,L.CODE_NAME AS TEACHING_TYPE,M.CODE_NAME AS CRS_BOOK, "+
            	"DECODE(A.OPEN1, 'Y', '四次授課', 'N', '多次面授') AS GHOST "+
            "FROM COUT002 B " +
            "JOIN COUT001 A ON A.CRSNO=B.CRSNO " +
            "JOIN SYST001 F ON F.KIND='NEW_REWORK' AND F.CODE=A.NEW_REWORK " +
            "JOIN SYST001 I ON I.KIND='SMS' AND I.CODE=A.SMS " +
            "JOIN SYST001 J ON J.KIND='REQOPTION' AND J.CODE=A.REQOPTION_TYPE " +
            "JOIN SYST001 K ON K.KIND = 'PRODUCE_CHOOSE' AND K.CODE = A.PRODUCE_TYPE " + 
            "JOIN SYST001 M ON M.KIND='CRS_BOOK' AND A.CRS_BOOK=M.CODE "+ 
            "JOIN SYST001 L ON L.KIND='TEACHING_TYPE_NAME' AND A.TEACHING_TYPE_NAME=L.CODE " +
        	"JOIN SYST003 S3 ON S3.ASYS ='1' AND A.FACULTY_CODE = S3.FACULTY_CODE  " +
            "WHERE 1=1 "
            );
        
        if (Utility.nullToSpace(ht.get("PASS_MK")).equals("1")){
			sql.append(" AND A.CRS_STATUS ='5' AND A.EST_RESULT_MK='Y' ");
		}else if (Utility.nullToSpace(ht.get("PASS_MK")).equals("2")) {
			sql.append(" AND A.CRS_STATUS ='2' ");
		}else if (Utility.nullToSpace(ht.get("PASS_MK")).equals("3")) {
			sql.append(" AND A.CRS_STATUS ='1' ");
		}else{
			sql.append(" AND A.CRS_STATUS IN('1','2','5') ");
		}
        
		if (!Utility.nullToSpace(ht.get("AYEAR")).equals("")){
			sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		}
		if (!Utility.nullToSpace(ht.get("SMS")).equals("")){
			sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		}
		if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals("")){
			sql.append(" AND A.PLAN_FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
		}        
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1")){
            sql.append(" AND A.open1 = 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2")){
            sql.append(" AND A.open3 = 'Y' AND A.open1 = 'N' ");
        }

        sql.append("ORDER BY DECODE(A.OPEN1,'Y','1','2'),DECODE(A.FACULTY_CODE, '80', '01', '90', '02', A.FACULTY_CODE),DECODE(A.NEW_REWORK,'1','1','6','2','3','3','4','3','5','5','2','6'), A.CRSNO ");

        DBResult	rs	= null;		
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable	rowHt	= null;
            while (rs.next())
            {
                rowHt	= new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					rowHt.put(rs.getColumnName(i), rs.getString(i));
                }
                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }
        return result;
    }
    
    /**
     * JOIN 其它 Cout001,Cout002,Cout100 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception           *****採計學系
     */
    public Vector getCout001Cou002Cout103ForCou108r_Export(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT I.CODE_NAME AS CSMS,B.CRS_NAME,A.CRD,A.EST_RESULT_MK,A.PRODUCE_TYPE,M.CODE_NAME AS CRS_BOOK," +
            	"F.CODE_NAME AS CNEW_REWORK,A.AYEAR,A.SMS,A.CRSNO,A.FACULTY_CODE,J.CODE_NAME AS REQOPTION_TYPE_NAME, " +
            	"S3.FACULTY_NAME AS FACULTY_NAME,K.CODE_NAME AS PRODUCE_NAME,L.CODE_NAME AS TEACHING_TYPE,M.CODE_NAME AS CRS_BOOK, "+
            	"DECODE(A.OPEN1, 'Y', '四次授課', 'N', '多次面授') AS GHOST "+
            "FROM COUT002 B " +
            "JOIN COUT001 A ON A.CRSNO=B.CRSNO " +
            "JOIN SYST001 F ON F.KIND='NEW_REWORK' AND F.CODE=A.NEW_REWORK " +
            "JOIN SYST001 I ON I.KIND='SMS' AND I.CODE=A.SMS " +
            "JOIN SYST001 J ON J.KIND='REQOPTION' AND J.CODE=A.REQOPTION_TYPE " +
            "JOIN SYST001 K ON K.KIND = 'PRODUCE_CHOOSE' AND K.CODE = A.PRODUCE_TYPE " + 
            "JOIN SYST001 M ON M.KIND='CRS_BOOK' AND A.CRS_BOOK=M.CODE "+ 
            "JOIN SYST001 L ON L.KIND='TEACHING_TYPE_NAME' AND A.TEACHING_TYPE_NAME=L.CODE " +
        	"JOIN SYST003 S3 ON S3.ASYS ='1' AND A.FACULTY_CODE = S3.FACULTY_CODE  " +
            "WHERE 1=1 "
            );
        
        if (Utility.nullToSpace(ht.get("PASS_MK")).equals("1")){
			sql.append(" AND A.CRS_STATUS ='5' AND A.EST_RESULT_MK='Y' ");
		}else if (Utility.nullToSpace(ht.get("PASS_MK")).equals("2")) {
			sql.append(" AND A.CRS_STATUS ='2' ");
		}else if (Utility.nullToSpace(ht.get("PASS_MK")).equals("3")) {
			sql.append(" AND A.CRS_STATUS ='1' ");
		}else{
			sql.append(" AND A.CRS_STATUS IN('1','2','5') ");
		}
        
		if (!Utility.nullToSpace(ht.get("AYEAR")).equals("")){
			sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		}
		if (!Utility.nullToSpace(ht.get("SMS")).equals("")){
			sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		}
		if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals("")){
			sql.append(" AND A.PLAN_FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
		}        
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1")){
            sql.append(" AND A.open1 = 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2")){
            sql.append(" AND A.open3 = 'Y' AND A.open1 = 'N' ");
        }

        sql.append("ORDER BY DECODE(A.OPEN1,'Y','1','2'),DECODE(A.FACULTY_CODE, '80', '01', '90', '02', A.FACULTY_CODE),DECODE(A.NEW_REWORK,'1','1','6','2','3','3','4','3','5','5','2','6'), A.CRSNO ");

        DBResult	rs	= null;		
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable	rowHt	= null;
            while (rs.next())
            {
                rowHt	= new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					rowHt.put(rs.getColumnName(i), rs.getString(i));
                }
                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }
        return result;
    }
    
	private String getCout003NamesForCou109r(DBManager dbManager, Connection conn, String AYEAR, String SMS, String CRSNO, String NEW_REWORK) throws Exception
    {
    	String Names 	= "";

	DBResult	rs			= null;
	// modify by teno 2009/08/26 
	DBResult	rs2			= null; 
	DBResult	rs3			= null;
	// end of mod
      try{
    	  SMS = SMS.equals("3")?"0":SMS;  // 要做比較,因此需將學期轉碼(暑期為第一學期)
    	  
     	 // modify by teno 2009/08/26 先查詢該科目是否有NEW_REWORK等於 1.新開 2.重製 6.修訂
     	 String querySql2 = 
     		  "SELECT COUNT(CRSNO) as CRSNO FROM COUT001 "+
           "WHERE NEW_REWORK IN ('1','2','6') and CRSNO ='" + CRSNO + "' ";

     	  rs2 = dbmanager.getSimpleResultSet(conn);
           rs2.open();
           rs2.executeQuery(querySql2);
         //如在COUT001查無該科目資料,改以該科目舊代號查詢
           while(rs2.next()){
	         if (rs2.getInt("CRSNO") == 0)
	         {
	           String querySql3 = 
	     		    "SELECT CRSNO_OLD FROM COUT010 WHERE CRSNO = '" + CRSNO + "' ";
	
	     	    rs3 = dbmanager.getSimpleResultSet(conn);
	           rs3.open();
	           rs3.executeQuery(querySql3);
	           if (rs3.next()){
	        	   CRSNO = rs3.getString("CRSNO_OLD");
	           }
	         }
           }
    	 // end of mod
         
    	  String querySql = 
    		  "SELECT B.NAME "+
    		  //"FROM COUT003 A, TRAT001 B "+
    		  "FROM COUT003 A, TRAT001 B LEFT JOIN "+
              // 加入當學年期或最近一學期的聘任等級資料來作排序    		  
              "(SELECT A.EMP_GRADE AS EMP_GRADE, A.IDNO "+
              "FROM TRAT007 A "+
              "WHERE A.EMP_GRADE_AYEAR <= '" + AYEAR+SMS + "' "+
              "AND SUBSTR(A.EMP_GRADE_AYEAR,1,3) || DECODE(SUBSTR(A.EMP_GRADE_AYEAR,4,1),'3','0',SUBSTR(A.EMP_GRADE_AYEAR,4,1)) "+
              "= (SELECT MAX(SUBSTR(B.EMP_GRADE_AYEAR,1,3)||DECODE(SUBSTR(B.EMP_GRADE_AYEAR,4,1),'3','0',SUBSTR(B.EMP_GRADE_AYEAR,4,1))) "+
              "FROM TRAT007 B WHERE B.IDNO = A.IDNO AND B.EMP_GRADE_AYEAR <= '" + AYEAR+SMS + "') "+
              ") C "+
              "ON B.IDNO = C.IDNO "+
    		  "WHERE "+
    		  // 如為修訂/新開 時,找當學年期的教師,其他則找尋最近的學年期的老師
  				// modify by teno 2009/08/26 (NEW_REWORK.equals("1")||NEW_REWORK.equals("6")?"A.AYEAR='"+AYEAR+"' AND DECODE(A.SMS,'3','0',A.SMS)='"+SMS+"' ":"A.AYEAR||DECODE(A.SMS,'3','0',A.SMS) = (SELECT MAX(E.AYEAR||DECODE(E.SMS,'3','0',E.SMS)) FROM COUT003 E WHERE E.AYEAR||DECODE(E.SMS,'3','0',E.SMS) <= '" + AYEAR+SMS + "' AND E.CRSNO=A.CRSNO) ") +
  				(NEW_REWORK.equals("1")||NEW_REWORK.equals("6")?"A.AYEAR='"+AYEAR+"' AND DECODE(A.SMS,'3','0',A.SMS)='"+SMS+"' ":"A.AYEAR||DECODE(A.SMS,'3','0',A.SMS) = (SELECT MAX(E.AYEAR||DECODE(E.SMS,'3','0',E.SMS)) FROM COUT003 E LEFT JOIN COUT001 D ON E.AYEAR = D.AYEAR AND E.SMS = D.SMS AND E.CRSNO = D.CRSNO WHERE E.AYEAR||DECODE(E.SMS,'3','0',E.SMS) <= '" + AYEAR+SMS + "' AND E.CRSNO=A.CRSNO AND D.NEW_REWORK in ('1','2','6'))") +
  				// end of mod
  				"AND A.CRSNO='" + CRSNO + "' " +
  			  "AND A.JOB_TYPE IN ('11','12') "+
    		  "AND B.IDNO=A.IDNO "+
    		  "ORDER BY A.PRD_ID,A.JOB_TYPE,DECODE(C.EMP_GRADE,'B','2.5',C.EMP_GRADE)  ";

    	  rs = dbmanager.getSimpleResultSet(conn);
          rs.open();
          rs.executeQuery(querySql);

		int i = 0;

		while (rs.next())
		{
			if (i > 0)
			{
				if(Names.indexOf(rs.getString("NAME")) == -1)
					Names += "，" + rs.getString("NAME");
			}else{
				Names += rs.getString("NAME");
				i++;
			}
		}
       	}
    catch (Exception ex)
    {
    	Names="";
    	throw ex;
    }
	finally
	{
		rs.close();
		rs2.close();
        if (rs3 != null)
        	rs3.close();
	}
	return Names;
    }
	
	private String getCout003NamesForCou105r(DBManager dbManager, Connection conn, String AYEAR, String SMS, String CRSNO, String NEW_REWORK) throws Exception
    {
    	String Names1 	= "";

	DBResult	rs			= null;
	// modify by teno 2009/08/26 
	DBResult	rs2			= null; 
	DBResult	rs3			= null;
	// end of mod
      try{
    	  SMS = SMS.equals("3")?"0":SMS;  // 要做比較,因此需將學期轉碼(暑期為第一學期)
    	  
     	 // modify by teno 2009/08/26 先查詢該科目是否有NEW_REWORK等於 1.新開 2.重製 6.修訂
     	 String querySql2 = 
     		  "SELECT COUNT(CRSNO) as CRSNO FROM COUT001 "+
           "WHERE NEW_REWORK IN ('1','2','6') and CRSNO ='" + CRSNO + "' ";

     	  rs2 = dbmanager.getSimpleResultSet(conn);
           rs2.open();
           rs2.executeQuery(querySql2);
         //如在COUT001查無該科目資料,改以該科目舊代號查詢
           while(rs2.next()){
	         if (rs2.getInt("CRSNO") == 0)
	         {
	           String querySql3 = 
	     		    "SELECT CRSNO_OLD FROM COUT010 WHERE CRSNO = '" + CRSNO + "' ";
	
	     	    rs3 = dbmanager.getSimpleResultSet(conn);
	           rs3.open();
	           rs3.executeQuery(querySql3);
	           if (rs3.next()){
	        	   CRSNO = rs3.getString("CRSNO_OLD");
	           }
	         }
           }
    	 // end of mod
         
    	  String querySql = 
    		  "SELECT B.NAME "+
    		  //"FROM COUT003 A, TRAT001 B "+
    		  "FROM COUT003 A, TRAT001 B LEFT JOIN "+
              // 加入當學年期或最近一學期的聘任等級資料來作排序    		  
              "(SELECT A.EMP_GRADE AS EMP_GRADE, A.IDNO "+
              "FROM TRAT007 A "+
              "WHERE A.EMP_GRADE_AYEAR <= '" + AYEAR+SMS + "' "+
              "AND SUBSTR(A.EMP_GRADE_AYEAR,1,3) || DECODE(SUBSTR(A.EMP_GRADE_AYEAR,4,1),'3','0',SUBSTR(A.EMP_GRADE_AYEAR,4,1)) "+
              "= (SELECT MAX(SUBSTR(B.EMP_GRADE_AYEAR,1,3)||DECODE(SUBSTR(B.EMP_GRADE_AYEAR,4,1),'3','0',SUBSTR(B.EMP_GRADE_AYEAR,4,1))) "+
              "FROM TRAT007 B WHERE B.IDNO = A.IDNO AND B.EMP_GRADE_AYEAR <= '" + AYEAR+SMS + "') "+
              ") C "+
              "ON B.IDNO = C.IDNO "+
    		  "WHERE "+
    		  // 如為修訂/新開 時,找當學年期的教師,其他則找尋最近的學年期的老師
  				// modify by teno 2009/08/26 (NEW_REWORK.equals("1")||NEW_REWORK.equals("6")?"A.AYEAR='"+AYEAR+"' AND DECODE(A.SMS,'3','0',A.SMS)='"+SMS+"' ":"A.AYEAR||DECODE(A.SMS,'3','0',A.SMS) = (SELECT MAX(E.AYEAR||DECODE(E.SMS,'3','0',E.SMS)) FROM COUT003 E WHERE E.AYEAR||DECODE(E.SMS,'3','0',E.SMS) <= '" + AYEAR+SMS + "' AND E.CRSNO=A.CRSNO) ") +
  				(NEW_REWORK.equals("1")||NEW_REWORK.equals("6")?"A.AYEAR='"+AYEAR+"' AND DECODE(A.SMS,'3','0',A.SMS)='"+SMS+"' ":"A.AYEAR||DECODE(A.SMS,'3','0',A.SMS) = (SELECT MAX(E.AYEAR||DECODE(E.SMS,'3','0',E.SMS)) FROM COUT003 E LEFT JOIN COUT001 D ON E.AYEAR = D.AYEAR AND E.SMS = D.SMS AND E.CRSNO = D.CRSNO WHERE E.AYEAR||DECODE(E.SMS,'3','0',E.SMS) <= '" + AYEAR+SMS + "' AND E.CRSNO=A.CRSNO AND D.NEW_REWORK in ('1','2','6'))") +
  				// end of mod
  				"AND A.CRSNO='" + CRSNO + "' " +
  			  "AND A.JOB_TYPE IN ('13','14','15') "+
    		  "AND B.IDNO=A.IDNO "+
    		  "ORDER BY A.PRD_ID,A.JOB_TYPE,DECODE(C.EMP_GRADE,'B','2.5',C.EMP_GRADE)  ";

    	  rs = dbmanager.getSimpleResultSet(conn);
          rs.open();
          rs.executeQuery(querySql);

		int i = 0;

		while (rs.next())
		{
			if (i > 0)
			{
				if(Names1.indexOf(rs.getString("NAME")) == -1)
					Names1 += "，" + rs.getString("NAME");
			}else{
				Names1 += rs.getString("NAME");
				i++;
			}
		}
       	}
    catch (Exception ex)
    {
    	Names1="";
    	throw ex;
    }
	finally
	{
		rs.close();
		rs2.close();
        if (rs3 != null)
        	rs3.close();
	}
	return Names1;
    }

    private String getCout103TotalCrsNames(DBManager dbManager, Connection conn, String AYEAR, String SMS, String CRSNO, String CRS_GROUP_CODE) throws Exception
    {
    	String TotalCrsNames 	= "";

	DBResult	rs			= null;

        	try
	{
		COUT103DAO	COUT103	= new COUT103DAO(dbManager, conn);

		COUT103.setResultColumn("(SELECT SYST008.TOTAL_CRS_NAME FROM SYST008 WHERE SYST008.FACULTY_CODE=COUT103.FACULTY_CODE AND SYST008.TOTAL_CRS_NO=COUT103.TOTAL_CRS_NO AND SYST008.ASYS='2' AND ROWNUM=1) AS TOTALCRSNAME, (SELECT SYST008.FACULTY_CODE||SYST008.TOTAL_CRS_NO FROM SYST008 WHERE SYST008.FACULTY_CODE=COUT103.FACULTY_CODE AND SYST008.TOTAL_CRS_NO=COUT103.TOTAL_CRS_NO AND SYST008.ASYS='2' AND ROWNUM=1) AS TOTALCRSCODE ");

		COUT103.setWhere
		(
			"COUT103.AYEAR= '" + AYEAR + "' " +
			"AND COUT103.SMS= '" + SMS + "' " +
			"AND COUT103.CRSNO= '" + CRSNO + "' " +
			"AND COUT103.CRS_GROUP_CODE= '" + CRS_GROUP_CODE + "' " +
			"AND (SELECT 1 FROM SYST008 WHERE SYST008.FACULTY_CODE=COUT103.FACULTY_CODE AND SYST008.TOTAL_CRS_NO=COUT103.TOTAL_CRS_NO AND SYST008.ASYS='2' AND ROWNUM=1) IS NOT NULL "
		);

		rs	= COUT103.query();

		int i = 0;

		while (rs.next()) {
				if (i > 0) {
					TotalCrsNames += "，";
				}

				// TotalCrsNames += rs.getString("TOTALCRSNAME");
				//抱歉,懶人寫死法,之後請在SYST008加欄位儲存縮寫,否則以後增加科別就爆啦!!!
				String code = rs.getString("TOTALCRSCODE");
				if ("2002".equals(code)) {
					TotalCrsNames += "社福";
				} else if ("3002".equals(code)) {
					TotalCrsNames += "綜商";
				} else if ("4002".equals(code)) {
					TotalCrsNames += "行管";
				} else if ("5002".equals(code)) {
					TotalCrsNames += "家典";
				} else if ("5003".equals(code)) {
					TotalCrsNames += "殯管";
				} else if ("6002".equals(code)) {
					TotalCrsNames += "企資";
				}

				i++;
			}

		return TotalCrsNames;

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
    public void getReg145r_Reg154_CRSNO(Vector vt, Hashtable ht) throws Exception
    {
        DBResult	rs	= null;

        try
        {
            if (sql.length() > 0)
            {
                sql.delete(0, sql.length());
            }

            sql.append("SELECT DISTINCT a.CRSNO, a.FACULTY_CODE, b.CRS_NAME " + "FROM COUT001 a, COUT002 b "
                       + "WHERE '1'='1' AND a.OPEN_YN='Y' AND a.CRSNO=b.CRSNO ");
            sql.append("AND a.AYEAR = '" + Utility.dbStr(ht.get("AYEAR")) + "' ");
            sql.append("AND a.SMS = '" + Utility.dbStr(ht.get("SMS")) + "' ");

            if (!Utility.checkNull(ht.get("FACULTY_CODE"), "").equals(""))
            {
                sql.append("AND b.FACULTY_CODE = '" + Utility.dbStr(ht.get("FACULTY_CODE")) + "' ");
            }

            if (!Utility.checkNull(ht.get("CRSNO"), "").equals(""))
            {
                sql.append("AND b.CRSNO = '" + Utility.dbStr(ht.get("CRSNO")) + "' ");
            }

            sql.append("ORDER BY a.CRSNO ASC");

            if (pageQuery)
            {
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                vt.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }
    }

    public void getScd410q_Scd411q_CRSNO(Vector vt, Hashtable ht) throws Exception
    {
        DBResult	rs	= null;

        try
        {
            if (sql.length() > 0)
            {
                sql.delete(0, sql.length());
            }

            sql.append("SELECT " +
            		   "UNIQUE a.CRSNO, b.FACULTY_CODE, b.CRS_NAME " + 
            		   "FROM COUT001 a " +
            		   "JOIN COUT002 b ON a.CRSNO=b.CRSNO "+
                       "WHERE a.crs_status='5' ");
            sql.append("AND a.AYEAR = '" + Utility.dbStr(ht.get("AYEAR")) + "' ");
            sql.append("AND a.SMS = '" + Utility.dbStr(ht.get("SMS")) + "' ");
            sql.append("AND EXISTS( SELECT 1 FROM SCDT004 X WHERE a.ayear = X.ayear and  a.sms = X.sms and  a.crsno = X.crsno) ");

            if (!Utility.checkNull(ht.get("FACULTY_CODE"), "").equals("")){
                sql.append("AND a.FACULTY_CODE = '" + Utility.dbStr(ht.get("FACULTY_CODE")) + "' ");
            }

            sql.append("ORDER BY a.CRSNO ASC");

            if (pageQuery)
            {
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                vt.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }
    }

    /**
     * JOIN 其它 Cout001,Cout002,Syst001,Syst003 將欄位的中文化
     * @Hashtable ht 和 SMS_SD 學期的開始日期 做為條件值
     *
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *
     */
    public Vector getCout001Cout002Cout005Syst001Syst003ForUse(Hashtable ht, DBManager dbm01, Connection conn01)
            throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            " SELECT A.DATA_LOCK,A.CRD_HR,A.FIRSTCOU,A.PLAN_FACULTY_CODE, A.COMMON_FACULTY_CODE, A.AYEAR, A.SMS, A.CRSNO, A.CRD, A.FACULTY_CODE,A.CRS_BOOK, A.CRS_CHAR, A.EST_RESULT_MK,   "
            + " A.EXAM_MK_MID, A.EXAM_MK, A.LAB_TIMES, A.NEW_REWORK, A.OPEN1, A.OPEN2, A.OPEN3, A.PRO_ACADFIELD, A.PRODUCE_TYPE, A.REQOPTION_TYPE, A.RESERVE_ITEM,  "
            + " A.REWORK_ITEM, A.TUT_TIMES, A.CLASS_YN, A.OPEN_YN, A.TIME_GROUP, A.TCH_ACADFIELD, A.ROWSTAMP, A.UPD_DATE, A.UPD_USER_ID, A.UPD_TIME, A.UPD_MK, A.CRS_TIMES, "
            + " B.CRS_NAME AS CCRSNO ,B.CRS_ENG AS ECRSNO ,C.CODE_NAME AS CSMS ,D.FACULTY_NAME AS CFACULTY_CODE,E.CODE_NAME AS CCRS_BOOK,F.CODE_NAME AS CCRS_CHAR,G.CODE_NAME AS CNEW_REWORK, "
            + " I.CODE_NAME AS CREQOPTION_TYPE, "
            + " K.CODE_NAME AS CCRS_STATUS, "
            + " L.CODE_NAME AS TEACHING_TYPE, "
            + " J.BASIC_KNWLDG,J.CRS_GUTLINE,J.CRS_OUTLINE ,J.TUT_TARGET,A.PRODUCE_LOCK,A.FACULTY_LOCK, "
            + " A.CRS_STATUS, M.CODE_NAME AS DEP_CODE, "
            + " ( " 
            + " SELECT "
            + " S001.CODE_NAME " 
            + " FROM AUTT005 A005 " 
            + " JOIN SYST001 S001 ON S001.KIND = 'DEP_CODE' and S001.CODE = A005.DEP_CODE AND ID_TYPE = '3' " 
            + " WHERE A005.USER_ID = '" + Utility.nullToSpace(ht.get("USER_ID")) + "' "
            + " AND ROWNUM < 2  "
            + " ) AS CODE_NAME ,A.IS_CTROREG, A.CTROREG_MK, A.REG_MK, "
            + " NVL((SELECT AYEAR FROM COUT103 WHERE AYEAR=A.AYEAR AND SMS=A.SMS AND CRSNO=A.CRSNO AND ROWNUM=1), 'NA') AS COUT103_DATA, "
            + " NVL((SELECT AYEAR FROM COUT003 WHERE AYEAR=A.AYEAR AND SMS=A.SMS AND CRSNO=A.CRSNO AND RESULT_MK='Y' AND ROWNUM=1), 'NA') AS COUT003_DATA "
            + " FROM COUT001 A  " 
            + " JOIN COUT002 B ON A.CRSNO=B.CRSNO "
            + " LEFT JOIN SYST001 C ON C.KIND='SMS' AND A.SMS=C.CODE "
            + " LEFT JOIN SYST003 D ON D.FACULTY_CODE = A.FACULTY_CODE AND D.ASYS='1' "
            + " LEFT JOIN SYST001 E ON E.KIND='CRS_BOOK' AND A.CRS_BOOK=E.CODE "
            + " LEFT JOIN SYST001 F ON F.KIND='CRS_CHAR' AND A.CRS_CHAR=F.CODE "
            + " LEFT JOIN SYST001 G ON G.KIND='NEW_REWORK' AND A.NEW_REWORK=G.CODE "
            + " LEFT JOIN SYST001 I ON I.KIND='REQOPTION' AND A.REQOPTION_TYPE=I.CODE "
            + " LEFT JOIN SYST001 K ON K.KIND='CRS_STATUS' AND A.CRS_STATUS=K.CODE "
            + " LEFT JOIN SYST001 L ON L.KIND='TEACHING_TYPE' AND A.TEACHING_TYPE=L.CODE "
            + " LEFT JOIN SYST001 M ON M.KIND='DEP_CODE' AND M.CODE = a.DEP_CODE "
            + " LEFT JOIN COUT005 J ON A.AYEAR=J.AYEAR AND A.SMS=J.SMS AND A.CRSNO=J.CRSNO " + " WHERE 0=0  ");
        	

        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("OPEN1")).equals(""))
        {
            sql.append(" AND A.OPEN1 = '" + Utility.nullToSpace(ht.get("OPEN1")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }
		
		if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
        {
            sql.append(" AND A.NEW_REWORK = '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
        }
		
		if (!Utility.nullToSpace(ht.get("PRODUCE_TYPE")).equals(""))
        {
            sql.append(" AND A.PRODUCE_TYPE LIKE '%" + Utility.nullToSpace(ht.get("PRODUCE_TYPE")) + "%' ");
        }
		
		if (!Utility.nullToSpace(ht.get("CRS_STATUS")).equals(""))
        {
            sql.append(" AND A.CRS_STATUS = '" + Utility.nullToSpace(ht.get("CRS_STATUS")) + "' ");
        }
		
		if (!Utility.nullToSpace(ht.get("DATA_LOCK")).equals(""))
		{
		    if (Utility.nullToSpace(ht.get("DATA_LOCK")).equals("Y"))
            {
                sql.append(" AND A.DATA_LOCK = '" + Utility.nullToSpace(ht.get("DATA_LOCK")) + "' ");
            }else{
        	    sql.append(" AND (A.DATA_LOCK <> 'Y' OR A.DATA_LOCK IS NULL) ");
        	
            }
		}

        sql.append(" ORDER BY A.AYEAR,decode(A.SMS,'3','0',A.SMS),A.FACULTY_CODE,A.CRSNO ");

        DBResult	rs	= null;
        DBResult rs2 = null;
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                    {
                        rowHt.put("CPRODUCE_TYPE", getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i)));
                    }

                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                    
                    sql = new StringBuffer();
                    
                    String crsno = rs.getString("CRSNO");
                    sql.append("select distinct a.crsno from regt007 a where a.ayear = '108' and a.sms = '3' and a.crsno = '" + crsno + "'");
                    sql.append(" union ");
                    sql.append("select distinct b.crsno from plat012 b where b.ayear = '108' and b.sms = '3' and b.crsno = '" + crsno + "'");
                    sql.append(" union ");
                    sql.append("select distinct c.crsno from cout022 c where c.ayear = '108' and c.sms = '3' and c.crsno = '" + crsno + "'");
                    
                    
                    rs2	= dbmanager.getSimpleResultSet(conn);
                    rs2.open();
                    rs2.executeQuery(sql.toString());
                    boolean noData = true;
                    while(rs2.next()) {
                    	noData = false;
                    }
                    //若未關閉在此迴圈會發生maximum open cursors exceeded 所以後來新增的用完就先關閉
                    if(rs2 != null) {
                    	rs2.close();
                    }
                    if(noData) {
                    	//N=沒資料
                    	rowHt.put("queryCheck", "N");
                    } else {
                    	//Y=有資料
                    	rowHt.put("queryCheck", "Y");
                    }
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
            if(rs2 != null) {
            	rs2.close();
            }
        }

        return result;
    }

    public DBResult getForCou011m(Hashtable ht) throws Exception
    {
        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append("SELECT TO_CHAR (c.crsno) as crsno_, TO_CHAR (c.crs_name) as crs_name_, TO_CHAR (c.crsno)||' '||TO_CHAR (c.crs_name) as crs "
                   + "FROM " + "(SELECT * FROM cout002 WHERE crsno = '" + Utility.nullToSpace(ht.get("CRSNO"))
                   + "') b," + "(SELECT * FROM cout002 WHERE crsno <> '" + Utility.nullToSpace(ht.get("CRSNO"))
                   + "' AND TRIM(crsno) IS NOT NULL) c " + "WHERE 1=1 " + "AND TRIM(b.crs_name) = TRIM(c.crs_name) "
                   + "AND c.crsno NOT IN (SELECT crsno_old FROM COUT010 WHERE crsno=b.crsno) ");
        sql.append("union ");
        sql.append(
            "SELECT TO_CHAR (a.crsno) as crsno_, TO_CHAR (b.crs_name) as crs_name_, TO_CHAR (a.crsno)||' '||TO_CHAR (b.crs_name) as crs "
            + "FROM " + "COUT010 a," + "COUT002 b " + "WHERE 1=1 " + "AND a.crsno = b.crsno " + "AND a.crsno_old = '"
            + Utility.nullToSpace(ht.get("CRSNO")) + "' "
            + "AND a.crsno_old||a.crsno NOT IN (SELECT crsno||crsno_old FROM COUT010 WHERE crsno=a.crsno_old) ");
        sql.append("union ");
        sql.append("SELECT TO_CHAR (a.crsno_old) as crsno_, TO_CHAR (b.crs_name) as crs_name_, TO_CHAR (a.crsno_old)||' '||TO_CHAR (b.crs_name) as crs " + "FROM "
                   + "COUT010 a," + "COUT002 b " + "WHERE 1=1 " + "AND a.crsno_old = b.crsno "
                   + "AND a.crsno in (SELECT crsno_old FROM COUT010 WHERE crsno='"
                   + Utility.nullToSpace(ht.get("CRSNO")) + "') " + "AND '" + Utility.nullToSpace(ht.get("CRSNO"))
                   + "'||a.crsno_old NOT IN (SELECT crsno||crsno_old FROM COUT010 WHERE crsno='"
                   + Utility.nullToSpace(ht.get("CRSNO")) + "') "
                   + "AND '" + Utility.nullToSpace(ht.get("CRSNO")) +"'||a.crsno_old != '"
                   + Utility.nullToSpace(ht.get("CRSNO")) + "'||'"
                   + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        sql.append("ORDER BY 1, 2, 3");

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            return rs;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public DBResult getDataForCou002mRework(Hashtable ht) throws Exception
    {
        if (sql.length() > 0)        
            sql.delete(0, sql.length());        

        sql.append(
        	"SELECT A.CRS_BOOK,A.CRS_CHAR,A.PRO_ACADFIELD, "+  //,A.EXAM_MK,A.LAB_TIMES,A.TUT_TIMES
        		"trim(A.PRODUCE_TYPE) as PRODUCE_TYPE,A.REQOPTION_TYPE,A.TCH_ACADFIELD," +
        		"A.CRS_TIMES,B.BASIC_KNWLDG,B.CRS_GUTLINE,B.CRS_OUTLINE,B.TUT_TARGET,B.CRS_BOOK_CHAPTER, B.TUT_TARGET_INTRODUCTION, " +
        		"A.FIRSTCOU, "+
        		"A.DISCIPLINE_TARGET, "+
		        "A.OPENED_DESCRIPTION, "+
		        "A.Other,A.RMK,A.MOD_RMK,NVL(a.MAP_EXPORT_MK,'Y') AS MAP_EXPORT_MK, "+
		        "B.BIBLIOGRAPHY,B.EVAL_MANNER "+
        	"FROM COUT001 a " +
        	"LEFT JOIN COUT005 B ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.CRSNO=B.CRSNO "+
        	"WHERE a.OPEN1 = 'Y' " +
        	"AND a.CRSNO='"+Utility.nullToSpace(ht.get("CRSNO")) + "' "+
        	"AND a.AYEAR||DECODE(a.SMS,'3',0,a.SMS) = (SELECT MAX(AYEAR||DECODE(C.SMS,'3',0,C.SMS)) FROM COUT001 c WHERE c.CRSNO=a.CRSNO AND c.AYEAR||DECODE(c.SMS,'3','0',c.SMS)<'"+ht.get("AYEAR")+ht.get("SMS")+"' and c.OPEN1 = 'Y' ) "
        );

        DBResult	rs	= null;
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            return rs;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public DBResult getDataForCou002mInfo3(Hashtable ht) throws Exception
    {
        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append("SELECT " + "b.crs_outline " + "FROM cout001 a JOIN cout005 b "
                   + "       ON a.ayear = b.ayear AND a.sms = b.sms AND a.crsno = b.crsno " + "WHERE 1=1 "
                   + "AND a.ayear || a.sms || a.crsno IN ( "
                   + "       SELECT MAX (ayear || sms || crsno) FROM cout001 " + "       WHERE ayear || sms < '"
                   + Utility.nullToSpace(ht.get("AYEAR")) + Utility.nullToSpace(ht.get("SMS")) + "' "
                   + "       AND crsno = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' "
                   + "       AND est_result_mk = 'Y' " + ") ");

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            return rs;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Vector getDataForCou010m(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
    	Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(" SELECT ");
        sql.append(" R1.CRS_NAME, ");
        sql.append(" R1.CRS_ENG, ");
        sql.append(" R1.CRS_NAME, ");
        sql.append(" (SELECT FACULTY_NAME FROM SYST003 WHERE ASYS='1' AND FACULTY_CODE=R1.FACULTY_CODE) AS CFACULTY_CODE, ");
        sql.append(" (SELECT DISCIPLINE_NAME FROM COUT100 WHERE FACULTY_CODE=M.FACULTY_CODE AND DISCIPLINE_CODE=M.DISCIPLINE_CODE) AS CDISCIPLINE_CODE, ");
        sql.append(" (SELECT CODE_NAME FROM SYST001 WHERE KIND='REQOPTION' AND CODE=M.REQOPTION_TYPE AND ROWNUM=1) AS CREQOPTION_TYPE, ");
        sql.append(" (SELECT CODE_NAME FROM SYST001 WHERE KIND='CRS_CHAR' AND CODE=M.CRS_CHAR AND ROWNUM=1) AS CCRS_CHAR, ");
        sql.append(" M.AYEAR||(SELECT CODE_NAME FROM SYST001 WHERE KIND='SMS' AND CODE=M.SMS AND ROWNUM=1) AS AYEARSMS, ");
        sql.append(" M.CRD, ");
        sql.append(" (SELECT CODE_NAME FROM SYST001 WHERE KIND='NEW_REWORK' AND CODE=M.NEW_REWORK AND ROWNUM=1) AS CNEW_REWORK, ");
        sql.append(" (SELECT CODE_NAME FROM SYST001 WHERE KIND='CRS_STATUS' AND CODE=M.CRS_STATUS AND ROWNUM=1) AS CCRS_STATUS, ");
        sql.append(" M.PRODUCE_TYPE, ");
        sql.append(" M.CRS_TIMES, ");
        sql.append(" M.LAB_TIMES, ");
        sql.append(" M.TUT_TIMES, ");
        sql.append(" M.CRSNO, ");
        sql.append(" M.FIRSTCOU, ");
        sql.append("(SELECT CODE_NAME FROM SYST001 WHERE KIND='CRS_BOOK' AND CODE=M.CRS_BOOK AND ROWNUM=1) AS CCRS_BOOK, ");
        sql.append(" M.PRO_ACADFIELD, ");
        sql.append(" (SELECT HW_REQUEST FROM COUT008 WHERE M.AYEAR=AYEAR AND M.SMS = SMS AND M.CRSNO=CRSNO) AS HW_REQUEST, ");
        sql.append(" (SELECT SW_REQUEST FROM COUT008 WHERE M.AYEAR=AYEAR AND M.SMS = SMS AND M.CRSNO=CRSNO) AS SW_REQUEST, ");
        sql.append(" (SELECT CRS_OUTLINE FROM COUT005 WHERE M.AYEAR=AYEAR AND M.SMS = SMS AND M.CRSNO=CRSNO) AS CRS_OUTLINE, ");
        sql.append(" (");
        sql.append(" 	SELECT d.CRS_OUTLINE FROM COUT001 c, COUT005 d WHERE c.AYEAR=d.AYEAR AND c.SMS=d.SMS AND c.CRSNO=d.CRSNO ");
        sql.append(" 	AND c.AYEAR||c.SMS||c.CRSNO IN (");
        sql.append(" 		SELECT MAX(AYEAR||SMS||CRSNO) FROM COUT001 WHERE ");
        sql.append(" 		AYEAR||SMS < M.AYEAR||M.SMS AND CRSNO=M.CRSNO AND EST_RESULT_MK='Y' ");
        sql.append(" 	) ");
        sql.append(" ) AS OLD_CRS_OUTLINE, ");
        sql.append(" R2.DISCIPLINE_NAME , ");
        sql.append(" R3.EVAL_MANNER  ");
        sql.append(" FROM ");
        sql.append(" 	COUT001 M  ");
        sql.append(" 		LEFT JOIN COUT002 R1  ");
        sql.append(" 		ON R1.CRSNO = M.CRSNO  ");
        sql.append(" 			LEFT JOIN COUT100 R2  ");
        sql.append(" 			 ON M.FIELD_CODE = R2.DISCIPLINE_CODE ");
        sql.append(" 			 AND M.FACULTY_CODE = R2.FACULTY_CODE ");
        sql.append(" 				LEFT JOIN COUT005 R3 ");
        sql.append(" 				 ON R3.AYEAR = M.AYEAR ");
        sql.append(" 				 AND R3.SMS = M.SMS ");
        sql.append(" 				 AND R3.CRSNO = M.CRSNO ");        
        sql.append(" WHERE M.AYEAR='").append( Utility.nullToSpace(ht.get("AYEAR")) ).append("' ");
        sql.append(" AND  M.SMS='").append( Utility.nullToSpace(ht.get("SMS")) ).append("' ");
        sql.append(" AND  M.CRSNO='").append( Utility.nullToSpace(ht.get("CRSNO")) ).append("' ");

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            if (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                	{
                    		rowHt.put("PRODUCE_TYPE", getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i)));
                	}
				  	else
				  	{
						rowHt.put(rs.getColumnName(i), rs.getString(i));
				  	}

					if ("CRSNO".equals(rs.getColumnName(i)))
                	{
                    		rowHt.put("CRS_NAME_5E", getCRS_NAME_5E(dbm01, conn01, rs.getString(i)));
                    		rowHt.put("CRSNO_CODE", rs.getString(i));
                	}
                }
                
                result.add(rowHt);
            }

	return result;

        }
        catch (Exception e)
        {
            throw e;
        }
    }
     /**
     * JOIN 其它 Cout001,Cout002,Syst001,Syst003 將欄位的中文化
     * @Hashtable ht 和 SMS_SD 學期的開始日期 做為條件值
     *
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *
     */
    public Vector getCout001REGT013ForUse(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

           sql.append(
                   " SELECT unique  C.LOC_X||C.LOC_Y AS SN ,A.CRSNO, B.CRS_NAME , "
                 + " case when f.CLASS_NAME is not null then '是' else '否' end AS  CLASS_KIND "
                 + " FROM COUT001 A JOIN COUT002 B ON A.CRSNO = B.CRSNO   "
                 + "      JOIN REGT013 C ON A.AYEAR=C.AYEAR AND A.SMS=C.SMS AND A.CRSNO=C.CRSNO   "
                 + " LEFT JOIN PERT004 F ON A.AYEAR = F.AYEAR   AND A.SMS = F.SMS AND A.CRSNO = F.CRSNO "
           );


        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            sql.append("WHERE A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }



        sql.append( "ORDER BY SN " );

        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    /**
     * JOIN 其它 Cout001,Cout002,Syst001,Syst003 將欄位的中文化
     * @Hashtable ht 和 SMS_SD 學期的開始日期 做為條件值
     *
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *
     */
    public Vector getCout001Cout002ForUse(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

           sql.append(
                   " SELECT C.LOC_X||C.LOC_Y AS SN ,A.CRSNO, B.CRS_NAME , "
                 + " case when f.CLASS_NAME is null then '是' else '否' end AS  CLASS_KIND "
                 + " FROM COUT001 A JOIN COUT002 B ON A.CRSNO = B.CRSNO   "
                 + "      JOIN REGT013 C ON A.AYEAR=C.AYEAR AND A.SMS=C.SMS AND A.CRSNO=C.CRSNO   "
                 + " LEFT JOIN PERT004 F ON A.AYEAR = F.AYEAR   AND A.SMS = F.SMS AND A.CRSNO = F.CRSNO "
           );


        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        {
            sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        {
            sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }



        sql.append( "ORDER BY SN " );
        System.out.println("sql : " + sql );


        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }
    private String getCRS_NAME_5E(DBManager dbManager, Connection conn, String CRSNO) throws Exception
    {
        String		CRS_NAME_5E		= "";

        try
        {
		String sql_5e	=	"SELECT b.CRSNO||' '||b.CRS_NAME AS PREREQ_CRSNO " +
						"FROM COUT011 a, COUT002 b " +
						"WHERE a.PREREQ_CRSNO=b.CRSNO  " +
						"AND a.CRSNO='" + CRSNO + "' " +
						"ORDER BY b.CRSNO ";

		DBResult	rs	= null;

		rs	= dbmanager.getSimpleResultSet(conn);
                	rs.open();
                	rs.executeQuery(sql_5e);

		int i = 0;

		while (rs.next())
		{
			if (i > 0)
				CRS_NAME_5E += ",";

			CRS_NAME_5E += rs.getString("PREREQ_CRSNO");
			i++;
		}

		return CRS_NAME_5E;

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    public Vector getCout102Cout103ForUse(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
		Vector	result	= new Vector();

        	if (sql.length() > 0)
        	{
            	sql.delete(0, sql.length());
        	}

        	sql.append
		(
			"SELECT " +
			"b.ayear, " +
			"b.sms, " +
			"a.faculty_code, " +
			"a.total_crs_no, " +
			"a.crs_group_code, " +
			"b.crsno, " +
			"b.ccrsno, " +
			"b.cfaculty_code, " +
			"b.crd, " +
			"b.produce_type, " +
			"NVL(a.crs_group_crd,0) as crs_group_crd, " +
			"NVL(a.crs_group_crd_max,0) as crs_group_crd_max, " +
			"a.crs_group_code_name, " +
			"a.rmk " +
			"FROM COUT102 a LEFT JOIN ( " +
			"	SELECT " +
			"	a.ayear, " +
			"	a.sms, " +
			"	a.faculty_code, " +
			"	a.total_crs_no, " +
			"	a.crs_group_code, " +
			"	a.crsno, " +
			"	d.crs_name AS ccrsno, " +
			"	f.faculty_name AS cfaculty_code, " +
			"	e.crd, e.produce_type, " +
			"	b.crs_group_crd, " +
			"	b.crs_group_crd_max " +
			"	FROM cout103 a " +
			"		JOIN cout102 b ON a.faculty_code = b.faculty_code AND a.total_crs_no = b.total_crs_no AND a.crs_group_code = b.crs_group_code " +
			"		JOIN syst008 c ON a.faculty_code = c.faculty_code AND a.total_crs_no = c.total_crs_no " +
			"		JOIN cout002 d ON a.crsno = d.crsno " +
			"		JOIN cout001 e ON a.ayear = e.ayear AND a.sms = e.sms AND a.crsno = e.crsno and e.crs_status = '5' " +
			"		JOIN syst003 f ON e.faculty_code = f.faculty_code AND f.asys = '1' " +
			"		WHERE c.asys = '2' "
		);
		sql.append("	AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		sql.append("	AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		sql.append("	AND a.FACULTY_CODE||a.TOTAL_CRS_NO = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
		sql.append(") b ON a.faculty_code = b.faculty_code AND a.total_crs_no = b.total_crs_no AND a.crs_group_code = b.crs_group_code ");
		sql.append
		(
			"WHERE a.FACULTY_CODE||a.TOTAL_CRS_NO = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' " +
			"ORDER BY a.crs_group_code,b.crsno "
		);


        	DBResult	rs	= null;

		try
        	{
			if (pageQuery)
            	{
				// 依分頁取出資料
                	rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            	}
            	else
            	{
                	// 取出所有資料
                	rs	= dbmanager.getSimpleResultSet(conn);
                	rs.open();
                	rs.executeQuery(sql.toString());
            	}

            	Hashtable	rowHt	= null;

			while (rs.next())
            	{
                	rowHt	= new Hashtable();

                	/** 將欄位抄一份過去 */
                	for (int i = 1; i <= rs.getColumnCount(); i++)
                	{
					if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                    	{
						rowHt.put("CPRODUCE_TYPE", getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i)));
                    	}

					rowHt.put(rs.getColumnName(i), rs.getString(i));
                	}

                	result.add(rowHt);
            	}
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

	//教務處用
    public Vector getDataForCou111r(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        	Vector	result	= new Vector();

			String crsno = "";

		if (sql.length() > 0)
			sql.delete(0, sql.length());

		// 因本程式有從其他程式連結過來,但是那個FORM的學年期是DISABLED所以要用TMP
		String ayear = Utility.nullToSpace(ht.get("AYEAR_TMP")).equals("")? Utility.nullToSpace(ht.get("AYEAR")):Utility.nullToSpace(ht.get("AYEAR_TMP"));
		String sms = Utility.nullToSpace(ht.get("SMS_TMP")).equals("")? Utility.nullToSpace(ht.get("SMS")):Utility.nullToSpace(ht.get("SMS_TMP"));
				
        	sql.append
		(
			"SELECT " +
			"(select code_name from SYST001 where kind='SMS' AND code=a.sms) as csms, " +
			"a.ayear, a.sms, a.crsno, b.crs_name AS ccrsno, a.crd, c.faculty_name AS cfaculty_code " +
			"FROM COUT002 b " +
			"JOIN COUT001 a ON a.CRSNO=b.CRSNO " +
			"JOIN syst003 c ON a.faculty_code = c.faculty_code AND c.asys = '1' " +
			"WHERE 0=0 "
		);

		// if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        	// {
			// sql.append(" AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		// }

		// if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
		// {
			// sql.append(" AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		// }
        
		sql.append(" AND( (a.ayear = '" + ayear + "' AND a.sms = '" + sms + "') OR a.ayear < '" + ayear + "' )");

		sql.append("AND a.EST_RESULT_MK='Y' ");

		sql.append("ORDER BY a.faculty_code, a.crsno ");
System.out.println(sql);
        	DBResult	rs	= null;

        	try
        	{
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

				if(!crsno.equals(rowHt.get("CRSNO").toString()))
				{
					/**塞入採計學系*/
					rowHt.put("FACULTY_NAME_COUNT", getAdoptFaculty(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO")));
	                result.add(rowHt);
					crsno = rowHt.get("CRSNO").toString();
				}
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

	//各學系用
	public Vector getDataForCou111r_v2(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        	Vector	result	= new Vector();

			String crsno = "";

        	if (sql.length() > 0)
        	{
			sql.delete(0, sql.length());
		}

        	sql.append
		(
			"SELECT " +
			"(select code_name from SYST001 where kind='SMS' AND code=a.sms) as csms, " +
			"a.ayear, a.sms, a.crsno, b.crs_name AS ccrsno, a.crd, c.faculty_name AS cfaculty_code " +
			"FROM COUT002 b " +
			"JOIN COUT001 a ON a.CRSNO=b.CRSNO " +
			"JOIN syst003 c ON a.faculty_code = c.faculty_code AND c.asys = '1' " +
			"WHERE 0=0 "
		);

		// if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        	// {
			// sql.append(" AND a.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		// }

		// if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
		// {
			// sql.append(" AND a.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		// }

		sql.append(" AND( (a.ayear = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' AND a.sms = '" + Utility.nullToSpace(ht.get("SMS")) + "') OR a.ayear < " + Utility.nullToSpace(ht.get("AYEAR")) + " )");

		sql.append("AND a.EST_RESULT_MK='Y' ");

		sql.append("AND a.faculty_code <> '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");

		sql.append("ORDER BY a.faculty_code, a.crsno, a.ayear desc, a.sms ");

        	DBResult	rs	= null;

        	try
        	{
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

				if(!crsno.equals(rowHt.get("CRSNO").toString()))
				{
					/**塞入採計學系*/
					if(!getAdoptFaculty2(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), Utility.nullToSpace(ht.get("FACULTY_CODE"))).equals(""))
					{
						rowHt.put("FACULTY_NAME_COUNT", getAdoptFaculty2(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), Utility.nullToSpace(ht.get("FACULTY_CODE"))));
						result.add(rowHt);
						crsno = rowHt.get("CRSNO").toString();
					}
				}
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    private String getAdoptFaculty(DBManager dbManager, Connection conn, String AYEAR, String SMS, String CRSNO) throws Exception
	{
		String AdoptFaculty 	= "";

		DBResult	rs			= null;

        	if (sql.length() > 0)
        	{
			sql.delete(0, sql.length());
		}

        	try
		{
        		sql.append
			(
				"SELECT " +
				"a.crsno, d.faculty_name AS ADOPT_FACULTY " +
				"FROM cout103 a " +
				"JOIN syst008 b ON a.faculty_code = b.faculty_code AND a.total_crs_no = b.total_crs_no AND b.asys = '1' " +
				"JOIN cout001 c ON a.ayear = c.ayear AND a.sms = c.sms AND a.crsno = c.crsno " +
				"JOIN syst003 d ON a.faculty_code = d.faculty_code AND d.asys = '1' " +
				"WHERE 0 = 0 " +
				"AND a.ayear || a.sms || a.faculty_code || a.crsno <> c.ayear || c.sms || c.faculty_code || c.crsno "
			);

			sql.append("AND a.AYEAR = '" + AYEAR + "' ");
			sql.append("AND a.SMS = '" + SMS + "' ");
			sql.append("AND a.CRSNO = '" + CRSNO + "' ");
			
			sql.append("ORDER BY a.crsno, d.faculty_code ");

			// 取出所有資料
			rs	= dbManager.getSimpleResultSet(conn);
			rs.open();
			rs.executeQuery(sql.toString());

			int i = 0;

			while (rs.next())
			{
				if (i > 0)
				{
					AdoptFaculty += "，";
				}

				AdoptFaculty += rs.getString("ADOPT_FACULTY");

				i++;
			}

			return AdoptFaculty;

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

	private String getAdoptFaculty2(DBManager dbManager, Connection conn, String AYEAR, String SMS, String CRSNO, String FACULTY_CODE) throws Exception
	{
		String AdoptFaculty 	= "";

		DBResult	rs			= null;

        	if (sql.length() > 0)
        	{
			sql.delete(0, sql.length());
		}

        	try
		{
        		sql.append
			(
				"SELECT " +
				"a.crsno, d.faculty_name AS ADOPT_FACULTY " +
				"FROM cout103 a " +
				"JOIN syst008 b ON a.faculty_code = b.faculty_code AND a.total_crs_no = b.total_crs_no AND b.asys = '1' " +
				"JOIN cout001 c ON a.ayear = c.ayear AND a.sms = c.sms AND a.crsno = c.crsno " +
				"JOIN syst003 d ON a.faculty_code = d.faculty_code AND d.asys = '1' " +
				"WHERE 0 = 0 " +
				"AND a.ayear || a.sms || a.faculty_code || a.crsno <> c.ayear || c.sms || c.faculty_code || c.crsno "
			);

			sql.append("AND a.AYEAR = '" + AYEAR + "' ");
			sql.append("AND a.SMS = '" + SMS + "' ");
			sql.append("AND a.CRSNO = '" + CRSNO + "' ");
			sql.append("AND a.FACULTY_CODE = '" + FACULTY_CODE + "' ");

			sql.append("ORDER BY a.crsno, d.faculty_code ");

			// 取出所有資料
			rs	= dbManager.getSimpleResultSet(conn);
			rs.open();
			rs.executeQuery(sql.toString());

			

			if (rs.next())
				AdoptFaculty = rs.getString("ADOPT_FACULTY");

			return AdoptFaculty;

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


    public Vector getForCOU129R(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
    	Vector	result	= new Vector();

    	if (sql.length() > 0)
    	{
    		sql.delete(0, sql.length());
    	}

    	sql.append("SELECT ");
    	sql.append("	M.AYEAR, ");
    	sql.append("	M.SMS, ");
    	sql.append("	M.CRSNO, ");
    	sql.append("	R1.CRS_NAME, ");
    	sql.append("	M.CRD, ");
    	sql.append("	(	SELECT CODE_NAME FROM SYST001 WHERE KIND='CRS_CHAR' AND CODE=M.CRS_CHAR ) ");
    	sql.append("	AS CRS_CHAR_NAME, ");
    	sql.append("	M.FIRSTCOU, ");
    	sql.append("	M.PRODUCE_TYPE, ");
    	sql.append("	M.TUT_TIMES, ");
    	sql.append("	M.LAB_TIMES, ");
    	sql.append("	R2.BASIC_KNWLDG, ");
    	sql.append("	R2.TUT_TARGET, ");
    	sql.append("	R2.TUT_TARGET_INTRODUCTION, ");
    	sql.append("	R2.CRS_OUTLINE, ");
    	sql.append("	M.CRS_TIMES, ");
    	sql.append("	M.RMK, ");
    	sql.append("	M.NEW_REWORK, ");
    	sql.append("	M.CRSNO,  ");
    	sql.append("    R3.BIBLIOGRAPHY, ");
    	sql.append("    R3.EVAL_MANNER, ");
    	sql.append("    M.FACULTY_CODE ");
    	sql.append("FROM ");
    	sql.append("	COUT001 M  ");
    	sql.append("		LEFT JOIN COUT002 R1  ");
    	sql.append("		  ON R1.CRSNO = M.CRSNO  ");
    	sql.append("			LEFT JOIN COUT005 R2  ");
    	sql.append("			  ON R2.CRSNO = M.CRSNO  ");
    	sql.append("			  AND R2.SMS = M.SMS  ");
    	sql.append("			  AND R2.AYEAR = M.AYEAR  ");
    	sql.append("				LEFT JOIN cout005 R3  ");
    	sql.append("				  ON R3.AYEAR = M.AYEAR  ");
    	sql.append("				  AND R3.SMS = M.SMS  ");
    	sql.append("				  AND R3.CRSNO = M.CRSNO ");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND M.OPEN1='Y' ");
		sql.append(" AND M.CRS_STATUS NOT IN ('3','4') ");
		
        /** == 查詢條件 ST == */
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
            sql.append(" AND M.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
            sql.append(" AND M.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))      
            sql.append(" AND M.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
            sql.append(" AND M.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        
        if (Utility.nullToSpace(ht.get("PROG_CODE")).equals("")){
        	
        	if (Utility.nullToSpace(ht.get("OPEN")).equals("OPEN1"))
    			sql.append(" AND M.OPEN1 = 'Y' ");        	
    		else		
    			sql.append(" AND M.OPEN2 = 'Y' ");
        	
        }

        sql.append(" ORDER BY ");
        sql.append("	DECODE(M.OPEN1,'Y','1','2'), ");
        sql.append("	DECODE(M.FACULTY_CODE, '90', '00', M.FACULTY_CODE), ");
        sql.append("	DECODE(M.NEW_REWORK,'1','1','6','2','2','3','4','4','5','5','3','6'), ");
        sql.append("	M.CRSNO ");
		 
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

				String x= "";

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
                    	{
                    		x = getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i));
                    	}

                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

			rowHt.put("CPRODUCE_TYPE", x);

			rowHt.put("NAMES", getCout003NamesForCou109r(dbm01, conn01, (String)rowHt.get("AYEAR"), (String)rowHt.get("SMS"), (String)rowHt.get("CRSNO"), rs.getString("NEW_REWORK")));

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }

    public Vector getForCOU109R(Hashtable ht) throws Exception
    {
        	Vector	result	= new Vector();

        	if (sql.length() > 0)
        	{
			sql.delete(0, sql.length());
        	}

		sql.append
		(
			"SELECT e.WEEK_YN, " +
			"b.CRS_NAME,a.CRSNO, " +
			"a.CRD, " +
			"(SELECT CODE_NAME FROM SYST001 WHERE KIND='CRS_CHAR' AND CODE=a.CRS_CHAR) AS CRS_CHAR_NAME, " +
			"(SELECT NAME FROM TRAT001 WHERE IDNO=e.S_CLASS_TCH_IDNO) AS S_CLASS_TCH_IDNO_NAME, " +
			"(SELECT CENTER_ABBRNAME FROM SYST002 WHERE ROWNUM=1 AND CENTER_ABRCODE=e.S_CLASS_ABRCODE_CODE) AS S_CLASS_ABRCODE_CODE_NAME, " +
			"NVL(e.TUT_TIMES, 0) AS TUT_TIMES, " +
			"NVL(e.LAB_TIMES_P, 0) AS LAB_TIMES_P, " +
			"NVL(e.TUT_TIMES_P, 0) AS TUT_TIMES_P, " +
			"e.STIME, " +
			"e.ETIME, " +
			"NVL(TO_NUMBER(SUBSTR(REPLACE(e.ETIME, ' ', ''), 0, 2)), 0)-NVL(TO_NUMBER(SUBSTR(REPLACE(e.STIME, ' ', ''), 0, 2)), 0) AS DIFFTIME," +
			"(SELECT CRS_NAME FROM COUT002 WHERE d.PREREQ_CRSNO=CRSNO) AS PREREQ_CRSNO_NAME, " +
			"c.BASIC_KNWLDG, " +
			"c.TUT_TARGET, " +
			"c.CRS_OUTLINE, " +
			"(SELECT CODE_NAME FROM SYST001 WHERE KIND='CRS_BOOK' AND CODE=a.CRS_BOOK) AS CRS_BOOK_NAME, " +
			"(SELECT CODE_NAME FROM SYST001 WHERE KIND='WEEK' AND CODE=e.WEEK) AS WEEK_NAME " +
			"FROM COUT001 a, COUT002 b, COUT005 c, COUT011 d, COUT022 e "+
			"WHERE 0=0 " +
			"AND a.CRSNO=b.CRSNO " +
			"AND a.AYEAR=c.AYEAR (+) " +
			"AND a.SMS=c.SMS (+) " +
			"AND a.CRSNO=c.CRSNO (+) " +
			"AND a.CRSNO=d.CRSNO (+) " +
			"AND a.AYEAR=e.AYEAR " +
			"AND a.SMS=e.SMS " +
			"AND a.CRSNO=e.CRSNO "
        	);

        	if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
        	{
            	sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        	}

        	if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
        	{
            	sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        	}

        	if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        	{
			sql.append("AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        	}

        	if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        	{
			sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        	}

		sql.append("AND a.OPEN3='Y' ");

		sql.append(" ORDER BY a.CRSNO ");


        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }


    /** per028m 暑期作業題目 - 未上傳 */
    public Vector getPer028mQuery_2(Hashtable ht) throws Exception
    {
        DBResult    rs  =   null;
        Vector      vt  =   new Vector();

        try
        {
            //條件
            String  AYEAR           =   Utility.checkNull(ht.get("AYEAR"), "");             //學年
            String  SMS             =   Utility.checkNull(ht.get("SMS"), "");               //學期
            String  FACULTY_CODE    =   Utility.checkNull(ht.get("FACULTY_CODE"), "");      //學系代號
            String  HAND_NUM    =   Utility.checkNull(ht.get("HAND_NUM"), "");      // 繳交作業次數


            if (sql.length() > 0)
                sql.delete(0, sql.length());

            sql.append
            (
                "SELECT B.CRSNO, B.CRS_NAME, A.FACULTY_CODE, '' AS SUBJECT_TYPE " +
                "FROM COUT001 A, COUT002 B " +
                "WHERE " +
                "A.SMS = '3' AND " +
                "A.CRSNO = B.CRSNO AND " +
                "A.CRS_STATUS = '5' AND " +
                "A.EST_RESULT_MK = 'Y' "
            );

            //加入條件 - 學年
            if (!"".equals(AYEAR))
                sql.append("AND A.AYEAR = '" + AYEAR + "' ");

            //加入條件 - 學期 因為暑期作業題目故無須判斷
            //if (!"".equals(SMS))
            //    sql.append("AND A.SMS = '" + SMS + "' ");

            //加入條件 - 學系代號
            if (!"".equals(FACULTY_CODE))
                sql.append("AND A.FACULTY_CODE = '" + FACULTY_CODE + "' ");

            // 排除該學年期該次已選定的科目
            if (!"".equals(HAND_NUM))
                sql.append("AND 0 = (SELECT COUNT(*) FROM PERT015 C WHERE C.AYEAR=A.AYEAR AND C.SMS=A.SMS AND C.FACULTY_CODE=A.FACULTY_CODE AND C.CRSNO=A.CRSNO AND C.HAND_NUM='"+HAND_NUM+"')");

            //排序
            if (!"".equals(orderBy))
                sql.append("ORDER BY " + orderBy);

            if(pageQuery)
            {
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }


            Hashtable rowHt = null;

            while (rs.next())
            {
                rowHt = new Hashtable();
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                vt.add(rowHt);
            }

            return vt;
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
                rs.close();
        }
    }

    /**
     * 取得畢業條件
     * @param ht
     * @return
     * @throws Exception
     */
    public Map getGraCondition(Hashtable ht) throws Exception {
		DBResult rs = null;
		Map map = new HashMap();

		try {
			//條件
			String ayear = Utility.checkNull(ht.get("AYEAR"), ""); //學年
			String sms = Utility.checkNull(ht.get("SMS"), ""); //學期

			if (sql.length() > 0)
				sql.delete(0, sql.length());

			sql.append("SELECT TO_NUMBER(A.START_AYEAR) AS START_AYEAR, A.START_SMS, B.CODE_NAME AS START_SMS_NAME, TO_NUMBER(A.EXPIRE_AYEAR) AS EXPIRE_AYEAR, A.EXPIRE_SMS, C.CODE_NAME AS EXPIRE_SMS_NAME,\n");
			sql.append("A.CRSNO, A.FACULTY_CODE, D.FACULTY_NAME, A.IS_GRAD_APPLY                                                                                        \n");
			sql.append("FROM GRAT008 A                                                                                                                 \n");
			sql.append("LEFT JOIN SYST001 B ON B.KIND = 'SMS' AND B.CODE = A.START_SMS                                                                 \n");
			sql.append("LEFT JOIN SYST001 C ON C.KIND = 'SMS' AND C.CODE = A.EXPIRE_SMS                                                                \n");
			sql.append("JOIN SYST003 D ON A.FACULTY_CODE = D.FACULTY_CODE                                                                              \n");
			sql.append("WHERE A.AYEAR = '" + ayear + "' AND A.SMS = '" + sms + "' AND A.IS_SEND_OUT = 'Y'                                                                  \n");

			if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals("")) {
				sql.append("AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
			}

			rs = dbmanager.getSimpleResultSet(conn);
			rs.open();
			rs.executeQuery(sql.toString());

			while (rs.next()) {
				StringBuffer str = new StringBuffer();
				if (rs.getString("START_AYEAR") == null || "".equals(rs.getString("START_AYEAR"))) {
					str.append(rs.getString("FACULTY_NAME"));
					str.append("採計至");
					str.append(rs.getString("EXPIRE_AYEAR"));
					str.append("學年度");
					str.append(rs.getString("EXPIRE_SMS_NAME"));
					str.append("止");
					//str.append("Y".equals(rs.getString("IS_GRAD_APPLY")) ? "申請畢業者" : "修讀抵免者");//2013/12/20 拿掉此文字Maggie
					str.append("Y".equals(rs.getString("IS_GRAD_APPLY")) ? "申請畢業者" : " ");
				} else {
					str.append(rs.getString("FACULTY_NAME"));
					str.append("自");
					str.append(rs.getString("START_AYEAR"));
					str.append("學年度");
					str.append(rs.getString("START_SMS_NAME"));
					str.append("採計至");
					str.append(rs.getString("EXPIRE_AYEAR"));
					str.append("學年度");
					str.append(rs.getString("EXPIRE_SMS_NAME"));
					str.append("止");
					//str.append("Y".equals(rs.getString("IS_GRAD_APPLY")) ? "申請畢業者" : "修讀抵免者");//2013/12/20 拿掉此文字Maggie
					str.append("Y".equals(rs.getString("IS_GRAD_APPLY")) ? "申請畢業者" : " ");
				}

				if(map.get(rs.getString("CRSNO")) != null){
					String temp = (String) map.get(rs.getString("CRSNO"));
					map.put(rs.getString("CRSNO"), temp + "<br>" +str.toString());
				}else{
					map.put(rs.getString("CRSNO"), str.toString());
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}

		return map;
	}

    /**
     * COU001M的新增頁面點選檢核後的查詢
     * @param ht
     * @return
     * @throws Exception
     */
    public Vector checkDataForCou001m(Hashtable ht) throws Exception {
		DBResult rs = null;
		Vector result = new Vector();

		try {
			//條件
			String ayear = Utility.checkNull(ht.get("AYEAR"), ""); //學年
			String sms = Utility.checkNull(ht.get("SMS"), ""); //學期

			if (sql.length() > 0)
				sql.delete(0, sql.length());

			sql.append(
				"select count(1) as TOTAL_CRSNO, sum(a.crd) as TOTAL_CRD, a.faculty_code, c.faculty_name " +
				"from cout001 a, syst003 c "+
				"WHERE "+
				"a.ayear='"+ayear+"' and "+
				"a.sms='"+sms+"' and "+
				"c.faculty_code=a.faculty_code "+
				"group by a.faculty_code,c.faculty_name	"+
				"order by a.faculty_code"
			);

			 if(pageQuery)
			 {
	                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
			 }
			 else
			 {
	                rs = dbmanager.getSimpleResultSet(conn);
	                rs.open();
	                rs.executeQuery(sql.toString());
			 }

			Hashtable rowHt = null;
            while (rs.next())
            {
                rowHt = new Hashtable();
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                result.add(rowHt);
            }
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}

		return result;
	}
    
    /**
     * COU116R 列印所有學系的資料\
     * @param ht
     * @return
     * @throws Exception
     */
    public Vector getDataForCou116rPrint1(Hashtable ht) throws Exception {
		Vector result = new Vector();
		if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT F.CODE_NAME AS CSMS,D.CRS_NAME,A.CRD,A.EST_RESULT_MK,A.PRODUCE_TYPE,A.LAB_TIMES,G.CODE_NAME AS CNEW_REWORK, " +
            "		A.AYEAR,A.SMS,A.CRSNO,A.FACULTY_CODE,C.faculty_abbrname||'_'||E.CRS_GROUP_CODE_NAME as CRS_GROUP_CODE_NAME, " +
            "		H.CODE_NAME AS CPRODUCE_TYPE, DECODE(A.OPEN3,'Y','2','1') as ORDER_SELECT "+
            "FROM COUT001 A " +
            "LEFT JOIN COUT103 B ON A.AYEAR=B.AYEAR AND A.SMS=B.SMS AND A.CRSNO=B.CRSNO  " +
            "JOIN SYST008 C ON C.FACULTY_CODE=B.FACULTY_CODE AND C.TOTAL_CRS_NO=B.TOTAL_CRS_NO AND C.ASYS='2' " +
            "JOIN COUT002 D ON D.CRSNO=A.CRSNO " +
            "LEFT JOIN COUT102 E ON B.FACULTY_CODE=E.FACULTY_CODE AND B.TOTAL_CRS_NO=E.TOTAL_CRS_NO AND B.CRS_GROUP_CODE=E.CRS_GROUP_CODE " +
            "JOIN SYST001 F ON F.KIND='SMS' AND F.CODE=A.SMS " + 
            "JOIN SYST001 G ON G.KIND='NEW_REWORK' AND G.CODE=A.NEW_REWORK "+ 
            "JOIN SYST001 H ON H.KIND='PRODUCE_CHOOSE' AND H.CODE=A.PRODUCE_TYPE " +
            "WHERE 1=1 "
            );
        
		sql.append(" AND A.CRS_STATUS IN('2','5') ");
        
		if (!Utility.nullToSpace(ht.get("AYEAR")).equals("")){
			sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		}
		if (!Utility.nullToSpace(ht.get("SMS")).equals("")){
			sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		}     
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1")){
            sql.append(" AND A.open1 = 'Y' ");
        }
        if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2")){
            sql.append(" AND A.open3 = 'Y' AND A.open1 = 'N' ");
        }

        sql.append("ORDER BY DECODE(A.OPEN1,'Y','1','2'),DECODE(A.FACULTY_CODE, '80', '01', '90', '02', A.FACULTY_CODE),DECODE(A.NEW_REWORK,'1','1','6','2','3','3','4','3','5','5','2','6'), A.CRSNO ");

		DBResult rs = null;
		try {
			rs = dbmanager.getSimpleResultSet(conn);
			rs.open();
			rs.executeQuery(sql.toString());

			Vector tmp = new Vector();
            while (rs.next())
            {
            	Hashtable rowHt = new Hashtable();
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                tmp.add(rowHt);
            }
            
            // 因同一科有可能有多個採計學系,因此需組起來,  ex:詩選----人文學系,資管系...  組成一筆
            Vector pk = new Vector();
            pk.add("CRSNO");
            
            Vector tmp2 = new Vector();
            UtilityX.combinCompareNextTheSameData(pk, "CRS_GROUP_CODE_NAME", ",", tmp, tmp2);
            
            // 處理資料,將相同科目的課程類別組起來(僅顯示一筆,且如有除相選外的類別則相選不顯示)
            // 一次迴圈表示一個科目
			for(int i=0; i<tmp2.size(); i++){
				Hashtable content = (Hashtable)tmp2.get(i);
				content.put("COMBINE_DATA", getCrsGroupName(content));				
				result.add(content);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}

		return result;
	}
    
    // 處理某一科的所有課程類別
    private String getCrsGroupName(Hashtable ht){
    	String result = "";
    	
    	String[] crsGroupCodeNameArray = Utility.split(ht.get("CRS_GROUP_CODE_NAME").toString(), ",");
    	
    	// 判斷該科共有哪些類別
    	Set distinctCrsGroupSet = new LinkedHashSet();
		for(int i=0; i<crsGroupCodeNameArray.length; i++){			
			distinctCrsGroupSet.add(Utility.split(crsGroupCodeNameArray[i], "_")[1]);
		}
		
		// 組出要顯示的字串		
		if(distinctCrsGroupSet.size()==1){
			Iterator it = distinctCrsGroupSet.iterator();
			
			if(it.hasNext())
				result=it.next().toString();
		}else{	
			for(int i=0; i<crsGroupCodeNameArray.length; i++){
				String crsGroupCodeName = crsGroupCodeNameArray[i].replaceAll("_", "").replaceAll("專業選修", "專修").replaceAll("科必修", "必修");
								
				// 有多種類別時,相關選修則不顯示
				if(crsGroupCodeName.endsWith("相關選修"))
					continue;
				
				result+=(result.equals("")?"":"<br>")+crsGroupCodeName;				
			}
		}			
    	
    	return result;
    }
    
    /**
     * 查詢資料供COU11R之用
     * @param ht
     * @return
     * @throws Exception
     */
    public Vector getDataForCou111rToPrint(Hashtable ht) throws Exception {		
		Vector result = new Vector();
		
		DBResult rs = null;
		try {
			// 因本程式有從其他程式連結過來,但是那個FORM的學年期是DISABLED所以要用TMP
			String ayear = Utility.nullToSpace(ht.get("AYEAR_TMP")).equals("")? Utility.nullToSpace(ht.get("AYEAR")):Utility.nullToSpace(ht.get("AYEAR_TMP"));
			String sms = Utility.nullToSpace(ht.get("SMS_TMP")).equals("")? Utility.nullToSpace(ht.get("SMS")):Utility.nullToSpace(ht.get("SMS_TMP"));
			String facultyCode = Utility.nullToSpace(ht.get("FACULTY_CODE"));
			String type = Utility.nullToSpace(ht.get("TYPE"));
			
			if (sql.length() > 0)
				sql.delete(0, sql.length());
//			 至COUT001取某個學年期前的所有科目(取最大學年期...同一科會有很多個學年期)
			if("1".equals(type)){
				sql.append(
					"SELECT B.CRSNO,D.CRS_NAME,C.CRD,E.FACULTY_NAME,F.FACULTY_NAME AS FACULTY_NAME2,B.AYEAR,B.SMS "+
					"FROM COUT103 B "+
					"JOIN COUT001 C ON C.AYEAR = B.AYEAR AND C.SMS = B.SMS AND C.CRSNO = B.CRSNO "+
					"JOIN COUT002 D ON D.CRSNO = B.CRSNO "+
					"JOIN SYST003 E ON C.FACULTY_CODE = E.FACULTY_CODE AND E.ASYS = '1' "+
					"JOIN SYST003 F ON B.FACULTY_CODE = F.FACULTY_CODE AND F.ASYS = '1' "+
					"WHERE "+					
					"  b.ayear||decode(b.sms,'3','0',b.sms)||b.crsno in "+ 
						"( "+
							"select max(a.ayear||decode(a.sms,'3','0',a.sms))||a.crsno "+
							"from cout001 a "+
							"where a.CRS_STATUS = '5' "+
							"and a.ayear||decode(a.sms,'3','0',a.sms)<='"+ayear+(sms.equals("3")?"0":sms)+"' "+
							"and a.faculty_code like '"+facultyCode+"%' "+
							"group by a.crsno "+
						") "+
					"and b.crs_group_code='003' "+ // 被採計的學系
					"and b.total_crs_no='01' "+ // 空大
					"and c.CRS_STATUS = '5' " +//確定開課
					"order by e.faculty_code, b.crsno "
				);						
				
			}else{
				sql.append(
						"SELECT B.CRSNO,D.CRS_NAME,C.CRD,E.FACULTY_NAME,F.FACULTY_NAME AS FACULTY_NAME2,B.AYEAR,B.SMS "+
						"FROM COUT103 B "+
						"JOIN COUT001 C ON C.AYEAR = B.AYEAR AND C.SMS = B.SMS AND C.CRSNO = B.CRSNO "+
						"JOIN COUT002 D ON D.CRSNO = B.CRSNO "+
						"JOIN SYST003 E ON C.FACULTY_CODE = E.FACULTY_CODE AND E.ASYS = '1' "+
						"JOIN SYST003 F ON B.FACULTY_CODE = F.FACULTY_CODE AND F.ASYS = '1' "+
						"WHERE "+						
						"  b.ayear||decode(b.sms,'3','0',b.sms)||b.crsno in "+ 
							"( "+
								"select max(a.ayear||decode(a.sms,'3','0',a.sms))||a.crsno "+
								"from cout001 a "+
								"where a.CRS_STATUS = '5'  "+
								"and  a.ayear||decode(a.sms,'3','0',a.sms)<='"+ayear+(sms.equals("3")?"0":sms)+"' "+
								"group by a.crsno "+
							") "+
						"and b.crs_group_code='003' "+ // 被採計的學系
						"and b.total_crs_no='01' "+ // 空大
						"AND B.FACULTY_CODE like '"+facultyCode+"%' "+
						"and c.CRS_STATUS = '5' " +//確定開課
						"order by b.faculty_code, b.crsno "
					);
				
			}
			
			

			rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());

			Vector tmp = new Vector();
            while (rs.next())
            {
            	Hashtable rowHt = new Hashtable();
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                tmp.add(rowHt);
            }
            
            // 因同一科有可能有多個採計學系,因此需組起來,  ex:詩選----人文學系,資管系...  組成一筆
            Vector pk = new Vector();
            pk.add("CRSNO");
            if("1".equals(type)||"".equals(facultyCode)){
            	UtilityX.combinCompareNextTheSameData(pk, "FACULTY_NAME2", "，", tmp, result);
            }else{
            	UtilityX.combinCompareNextTheSameData(pk, "FACULTY_NAME", "，", tmp, result);
            }         
                        
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}

		return result;
	}
    
   // 主開學系檢查
    public Vector checkDataForScd900m(Hashtable ht) throws Exception {		
		Vector result = new Vector();
		
		DBResult rs = null;
		try {
			if (sql.length() > 0)
				sql.delete(0, sql.length());

			sql.append(
				"select a.ayear, a.sms, a.crsno, b.crs_name "+
				"from cout001 a "+
				"join cout002 b on a.crsno=b.crsno "+
				"where "+
				"     a.CRS_STATUS='5' "+
				"AND a.EST_RESULT_MK='Y' "+
				"AND a.ayear||decode(a.sms,'3','0',a.sms) <= '"+ht.get("AYEAR")+ht.get("SMS")+"' "+
				"and not exists (select 1 from cout103 c where a.ayear=c.ayear and a.sms=c.sms and a.crsno=c.crsno and c.crs_group_code='002' and c.total_crs_no='01') "+
				"order by a.ayear, a.sms, a.crsno "
			);
			
			rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());

            while (rs.next())
            {
            	Hashtable rowHt = new Hashtable();
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                result.add(rowHt);
            }                           
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}

		return result;
	}
	
	public Vector getGRA036Q(Hashtable ht, DBManager dbm01, Connection conn01)
    throws Exception
	{
		Vector	result	= new Vector();
		
		if (sql.length() > 0)
		{
		    sql.delete(0, sql.length());
		}
		
		sql.append(
				" SELECT "+ 
				" TO_NUMBER(A.AYEAR) AS AYEAR_NAME,A.AYEAR,A.SMS,B.CRS_NAME,S01.CODE_NAME AS SMS_NAME,S03.FACULTY_NAME,A.CRSNO,B.CRD "+
				" FROM COUT001 A "+
				" JOIN COUT002 B ON A.CRSNO = B.CRSNO "+
				" LEFT JOIN SYST001 S01 ON S01.KIND = 'SMS' AND A.SMS = S01.CODE "+
				" LEFT JOIN SYST003 S03 ON S03.FACULTY_CODE = A.FACULTY_CODE AND S03.ASYS='1' "+
				" WHERE 0 = 0 " +
				" AND A.EST_RESULT_MK = 'Y' "+
				" AND A.CRS_STATUS = '5' ");
		
		/** == 查詢條件 ST == */
		if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
		{
		    sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		}
		
		if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
		{
		    sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		}
		
		if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
		{
		    sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
		}
		
		if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
		{
		    sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
		}
		
		if (!Utility.nullToSpace(ht.get("CRS_NAME_Q")).equals(""))
		{
		    sql.append(" AND B.CRS_NAME like '%" + Utility.nullToSpace(ht.get("CRS_NAME_Q")) + "%' ");
		}
		
		sql.append(" ORDER BY A.AYEAR, A.SMS, A.FACULTY_CODE, A.CRSNO ");
		
		DBResult rs	= null;
		
		try
		{
		    if (pageQuery)
		    {
		        // 依分頁取出資料
		        rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
		    }
		    else
		    {
		        // 取出所有資料
		        rs	= dbmanager.getSimpleResultSet(conn);
		        rs.open();
		        rs.executeQuery(sql.toString());
		    }
		
		    Hashtable rowHt	= null;
		
		    while (rs.next())
		    {
		        rowHt	= new Hashtable();
		
		        /** 將欄位抄一份過去 */
		        for (int i = 1; i <= rs.getColumnCount(); i++)
		        {	
		            rowHt.put(rs.getColumnName(i), rs.getString(i));
		        }
		        
		        // 取得採計系所清單
		        if (sql.length() > 0)
        		{
        		    sql.delete(0, sql.length());
        		}
        		
		        sql.append(
		        		" SELECT SUBSTR(A.FACULTY_CODE,1,1) as FACULTY_CODE "+
        				" FROM COUT103 A "+
        				" WHERE 0=0 "+
        				" AND A.TOTAL_CRS_NO = '01' "+
        				" AND A.CRS_GROUP_CODE = '003' "+
        				" AND A.CRSNO = '"+rs.getString("CRSNO")+"' "+
		        		" AND A.AYEAR = '"+rs.getString("AYEAR")+"' "+
		        		" AND A.SMS = '"+rs.getString("SMS")+"' ");
		        
                COUT103DAO COUT103 = new COUT103DAO(dbmanager, conn);
                DBResult rs1 = COUT103.query(sql.toString());
                String FACULTY_NAME_CAL_LIST = "";
                for(int j=0 ;rs1.next();j++) {
                	if(j==0){
                		FACULTY_NAME_CAL_LIST += rs1.getString("FACULTY_CODE");	
                	}else{
                		FACULTY_NAME_CAL_LIST += ","+rs1.getString("FACULTY_CODE");
                	} 
                }
                rowHt.put("FACULTY_NAME_CAL_LIST", FACULTY_NAME_CAL_LIST);
                
                // 取得主開系所
                if (sql.length() > 0)
        		{
        		    sql.delete(0, sql.length());
        		}
        		
		        sql.append(
		        		" SELECT SUBSTR(A.FACULTY_CODE,1,1) as FACULTY_CODE "+
        				" FROM COUT103 A "+
        				" WHERE 0=0 "+
        				" AND A.TOTAL_CRS_NO = '01' "+
        				" AND A.CRS_GROUP_CODE = '002' "+
        				" AND A.CRSNO = '"+rs.getString("CRSNO")+"'	"+
        				" AND A.AYEAR = '"+rs.getString("AYEAR")+"' "+
		        		" AND A.SMS = '"+rs.getString("SMS")+"' ");
		        
                rs1 = COUT103.query(sql.toString());
                String FACULTY_NAME_MAINOPEN = "";
                if(rs1.next()){
                	FACULTY_NAME_MAINOPEN = rs1.getString("FACULTY_CODE");
                }    
                rs1.close();
                rowHt.put("FACULTY_NAME_MAINOPEN", FACULTY_NAME_MAINOPEN);
                
		        result.add(rowHt);
		    }
		}
		catch (Exception e)
		{
		    throw e;
		}
		finally
		{
		    if (rs != null)
		    {
		        rs.close();
		    }
		}
		
		return result;
	}
	
	
	
	
	public Vector getDataForreg176r_01v1(Hashtable ht) throws Exception {		
		Vector result = new Vector();
		
		DBResult rs = null;
		try {
			if (sql.length() > 0)
				sql.delete(0, sql.length());

			sql.append(
				"SELECT A.AYEAR,D.CODE_NAME AS SMS_NAME,B.CRS_NAME,B.CRD,C.NAME AS TCH_NAME,E.CODE_NAME AS PRODUCE_TYPE_NAME,A.CRSNO, "+
				"	   COUNT(UNIQUE M.CRSNO) OVER (PARTITION BY M.AYEAR,M.SMS) AS SUBJECT_COUNT, "+
				"	   DECODE(A.INTERVAL_WEEK,'01','單週','02','雙週')||DECODE(A.WEEK,'1','一','2','二','3','三','4','四','5','五','6','六','7','日')||' '||SUBSTR(A.STIME,1,2)||':'||SUBSTR(A.STIME,3,2)||'至'||SUBSTR(A.ETIME,1,2)||':'||SUBSTR(A.ETIME,3,2) AS CLASS_TIME "+
				"	   ,F.CENTER_ABBRNAME||DECODE(A.S_CLASS_NAME,'','','('||A.S_CLASS_NAME||')') AS SCH_NAME, "+
				"	   (SELECT RMK_CONTENT FROM PERT053 WHERE PRO_CODE = 'REG176R') AS RMK_CONTENT "+
				"FROM COUT001 M "+
				"JOIN COUT022 A ON M.AYEAR = A.AYEAR AND M.SMS = A.SMS AND M.CRSNO = A.CRSNO "+ 
				"JOIN COUT002 B ON M.CRSNO = B.CRSNO "+
				"JOIN TRAT001 C ON A.S_CLASS_TCH_IDNO = C.IDNO "+
				"JOIN SYST001 D ON M.SMS = D.CODE AND D.KIND = 'SMS' "+   
				"LEFT JOIN SYST001 E ON M.PRODUCE_TYPE = E.CODE AND E.KIND = 'PRODUCE_CHOOSE' "+
				"JOIN SYST002 F ON A.S_CLASS_ABRCODE_CODE = F.CENTER_ABRCODE "+
				"WHERE 1 = 1 "+
				"AND A.AYEAR = '"+Utility.nullToSpace(ht.get("AYEAR"))+"' "+
				"AND A.SMS = '"+Utility.nullToSpace(ht.get("SMS"))+"' "+
				"ORDER BY  A.S_CLASS_ABRCODE_CODE,A.CRSNO"
			);
			
			rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());

            while (rs.next())
            {
            	Hashtable rowHt = new Hashtable();
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                result.add(rowHt);
            }                           
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}

		return result;
	}
	
	// 表頭,科目數
	public int getDataForCou130r_0(Hashtable ht) throws Exception {		
		int result = 0;
		
		DBResult rs = null;
		try {
			COUT001DAO cout001 = new COUT001DAO(dbmanager, conn);
			cout001.setResultColumn("count(1) as TOTAL_COUNT");
			cout001.setAYEAR(ht.get("AYEAR").toString());
			cout001.setSMS(ht.get("SMS").toString());
			rs = cout001.query();

            if (rs.next())
            	result = rs.getInt("TOTAL_COUNT");
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}

		return result;
	}
	
	// 第一部分,已登錄之新舊關係科目	
	public Vector getDataForCou130r_1(Hashtable ht) throws Exception {		
		Vector result = new Vector();
		
		DBResult rs = null;
		try {
			String sql = 
				"-- A:某個學年期的科目代碼   COUT001.CRSNO \n"+
				"-- 查詢COUT010以A當做CRSNO的條件,去查詢CRSNO_OLD的結果 \n"+
				"select a.crsno, c.CRS_NAME, b.CRSNO_OLD as CRSNO_OLD, d.CRS_NAME as CRS_NAME_OLD, e.CODE_NAME AS CRS_STATUS_NAME \n"+
				"from cout001 a \n"+
				"join cout010 b on a.CRSNO=b.CRSNO \n"+
				"join cout002 c on a.CRSNO=c.CRSNO \n"+
				"join cout002 d on b.CRSNO_OLD=d.CRSNO \n"+
				"join syst001 e on e.KIND='CRS_STATUS' and a.CRS_STATUS=e.CODE "+
				"where \n"+
				"    a.ayear='"+ht.get("AYEAR")+"' \n"+
				"and a.sms='"+ht.get("SMS")+"' \n"+
				"order by a.crsno \n";
			
			rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());

            while (rs.next())
            {
            	Hashtable rowHt = new Hashtable();
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                result.add(rowHt);
            }                           
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}

		return result;
	}
	
	// 第二部分,新發現之新舊關係科目
	public Vector getDataForCou130r_2(Hashtable ht) throws Exception {		
		Vector result = new Vector();
		
		DBResult rs = null;
		try {
			String sql = 
				// 排除第一部分已存在的相同資料
				"select x.* \n"+
				"from \n"+
				"( \n"+
				"	-- A:某個學年期的科目代碼   COUT001.CRSNO  \n"+
				"	-- D:查詢COUT010以A當做CRSNO_OLD的條件,去查詢COUT010.CRSNO的結果 \n"+
				"	select a.crsno, c.CRS_NAME, b.CRSNO as CRSNO_OLD, d.CRS_NAME as CRS_NAME_OLD, e.CODE_NAME as CRS_STATUS_NAME \n"+
				"	from cout001 a \n"+
				"	join cout010 b on a.CRSNO=b.CRSNO_OLD \n"+
				"	join cout002 c on a.CRSNO=c.CRSNO \n"+
				"	join cout002 d on b.CRSNO=d.CRSNO \n"+
				"	join syst001 e on e.KIND='CRS_STATUS' and a.CRS_STATUS=e.CODE "+
				"	where \n"+
				"	    a.ayear='"+ht.get("AYEAR")+"' \n"+
				"	and a.sms='"+ht.get("SMS")+"' \n"+
				"	union \n"+
				"	-- C:查詢COUT010以B當做CRSNO的條件,去查詢CRSNO_OLD的結果 \n"+
				"	select a.crsno, d.CRS_NAME, c.CRSNO_OLD as CRSNO_OLD, e.CRS_NAME AS CRS_NAME_OLD, f.CODE_NAME as CRS_STATUS_NAME \n"+
				"	from cout001 a \n"+
				"	join cout010 b on a.CRSNO=b.CRSNO \n"+
				"	join cout010 c on b.CRSNO_OLD=c.CRSNO and c.CRSNO_OLD<>a.CRSNO \n"+ 
				"	join cout002 d on a.CRSNO=d.CRSNO \n"+
				"	join cout002 e on c.CRSNO_OLD=e.CRSNO \n"+
				"	join syst001 f on f.KIND='CRS_STATUS' and a.CRS_STATUS=f.CODE "+
				"	where \n"+
				"	    a.ayear='"+ht.get("AYEAR")+"' \n"+
				"	and a.sms='"+ht.get("SMS")+"' \n"+
				"	-- E:查詢COUT002以A當做CRSNO的條件,判斷是否有相同名稱,所查詢出來的相同名稱的科目代碼為舊科目(排除當學期的科目代碼) \n"+
				"	union \n"+
				"	select a.crsno,b.crs_name,g.crsno as crsno_old,g.crs_name as crs_name_old, c.code_name as crs_status_name \n"+
				"	from cout001 a \n"+
				"	join cout002 b on a.crsno=b.crsno \n"+
				"	join syst001 c on c.KIND='CRS_STATUS' and a.CRS_STATUS=c.CODE "+
				"	join  \n"+
				"	( \n"+
				"   	select c.CRSNO, c.CRS_NAME \n"+
				"   	from cout002 c \n"+    
				"   	where \n"+
				"       	exists \n"+
				"        	( \n"+
				"           	select 1 \n"+
				"            	from cout002 d \n"+
				"            	where  \n"+
				"               	exists \n"+ 
				"               	( \n"+
				"                   	select 1  \n"+
				"                    	from cout002 e \n"+
				"                    	where d.CRS_NAME=e.CRS_NAME \n"+
				"                    	and exists (select 1 from cout001 f where f.ayear='"+ht.get("AYEAR")+"' and f.sms='"+ht.get("SMS")+"' and e.crsno=f.crsno) \n"+
				"                	) \n"+
				"                	and c.CRS_NAME=d.CRS_NAME \n"+
				"            	group by d.CRS_NAME \n"+
				"            	having count(1)>1 \n"+
				"        	) \n"+
				"	) g on b.CRS_NAME=g.CRS_NAME and a.CRSNO<>g.CRSNO \n"+
				"	where \n"+
				"    	a.AYEAR='"+ht.get("AYEAR")+"' \n"+
				"	and a.sms='"+ht.get("SMS")+"' \n"+
				") x "+
				"where \n"+
				// 排除第一部份的資料
				"not exists \n"+ 
				"( \n"+
				"    select 1 \n"+
				"    from cout001 x1 \n"+
				"    join cout010 x2 on x1.CRSNO=x2.CRSNO \n"+    
				"    where \n"+
				"        x1.ayear='"+ht.get("AYEAR")+"' \n"+
				"    and x1.sms='"+ht.get("SMS")+"' \n"+
				"    and x.CRSNO=x1.CRSNO \n"+
				"    and x.CRSNO_OLD=x2.CRSNO_OLD \n"+
				") \n"+
				"order by crsno,crsno_old \n";
			
			rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());

            while (rs.next())
            {
            	Hashtable rowHt = new Hashtable();
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                result.add(rowHt);
            }                           
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}

		return result;
	}
	
	// 第三部分,不符合先修關係科目
	public Vector getDataForCou130r_3(Hashtable ht) throws Exception {		
		Vector result = new Vector();
		
		DBResult rs = null;
		try {
			String sql = 
				"-- A:某個學年期的科目代碼   COUT001.CRSNO \n"+
				"-- 查詢COUT010以A當做CRSNO的條件,去查詢CRSNO_OLD的結果 \n"+
				"select a.crsno, c.CRS_NAME, b.PREREQ_CRSNO as CRSNO_OLD, d.CRS_NAME as CRS_NAME_OLD, e.CODE_NAME as CRS_STATUS_NAME \n"+
				"from cout001 a \n"+
				"join cout011 b on a.CRSNO=b.CRSNO \n"+
				"join cout002 c on a.CRSNO=c.CRSNO \n"+
				"join cout002 d on b.PREREQ_CRSNO=d.CRSNO \n"+
				"join syst001 e on e.KIND='CRS_STATUS' and a.CRS_STATUS=e.CODE "+
				"where \n"+
				"    a.ayear='"+ht.get("AYEAR")+"' \n"+
				"and a.sms='"+ht.get("SMS")+"' \n"+
				"order by a.crsno \n";
			
			rs = dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());

            while (rs.next())
            {
            	Hashtable rowHt = new Hashtable();
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                result.add(rowHt);
            }                           
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}

		return result;
	}
//COU029M查詢	
public Vector cou029forQuery(Hashtable ht, DBManager dbm01, Connection conn01)throws Exception
	{
		Vector	result	= new Vector();

	if (sql.length() > 0)
	{
		sql.delete(0, sql.length());
	}

	sql.append(
			" SELECT A.CRD_HR,A.FIRSTCOU,A.PLAN_FACULTY_CODE, A.COMMON_FACULTY_CODE, A.AYEAR, A.SMS, A.CRSNO, B.CRD, A.FACULTY_CODE,A.CRS_BOOK, A.CRS_CHAR, A.EST_RESULT_MK,   "
			+ " A.EXAM_MK, A.LAB_TIMES, A.NEW_REWORK, A.OPEN1, A.OPEN2, A.OPEN3, A.PRO_ACADFIELD, A.PRODUCE_TYPE, A.REQOPTION_TYPE, A.RESERVE_ITEM,  "
			+ " A.REWORK_ITEM, A.TUT_TIMES, A.CLASS_YN, A.OPEN_YN, A.TIME_GROUP, A.TCH_ACADFIELD, A.ROWSTAMP, A.UPD_DATE, A.UPD_USER_ID, A.UPD_TIME, A.UPD_MK, A.CRS_TIMES, "
			+ " B.CRS_NAME AS CCRSNO ,B.CRS_ENG AS ECRSNO ,C.CODE_NAME AS CSMS ,D.FACULTY_NAME AS CFACULTY_CODE,E.CODE_NAME AS CCRS_BOOK,F.CODE_NAME AS CCRS_CHAR,G.CODE_NAME AS CNEW_REWORK, "
			+ " I.CODE_NAME AS CREQOPTION_TYPE, "
			+ " K.CODE_NAME AS CCRS_STATUS, "
			+ " J.BASIC_KNWLDG,J.CRS_GUTLINE,J.CRS_OUTLINE ,J.TUT_TARGET,A.PRODUCE_LOCK,A.FACULTY_LOCK, "
			+ " A.CRS_STATUS, "
			+ " NVL((SELECT AYEAR FROM COUT103 WHERE AYEAR=A.AYEAR AND SMS=A.SMS AND CRSNO=A.CRSNO AND ROWNUM=1), 'NA') AS COUT103_DATA, "
			+ " NVL((SELECT AYEAR FROM COUT003 WHERE AYEAR=A.AYEAR AND SMS=A.SMS AND CRSNO=A.CRSNO AND RESULT_MK='Y' AND ROWNUM=1), 'NA') AS COUT003_DATA "
			+ " FROM COUT001 A  " + " JOIN COUT002 B ON A.CRSNO=B.CRSNO "
			+ " LEFT JOIN SYST001 C ON C.KIND='SMS' AND A.SMS=C.CODE "
			+ " LEFT JOIN SYST003 D ON D.FACULTY_CODE = A.FACULTY_CODE AND D.ASYS='1' "
			+ " LEFT JOIN SYST001 E ON E.KIND='CRS_BOOK' AND A.CRS_BOOK=E.CODE "
  	  		+ " LEFT JOIN SYST001 F ON F.KIND='CRS_CHAR' AND A.CRS_CHAR=F.CODE "
  	  		+ " LEFT JOIN SYST001 G ON G.KIND='NEW_REWORK' AND A.NEW_REWORK=G.CODE "
  	  		+ " LEFT JOIN SYST001 I ON I.KIND='REQOPTION' AND A.REQOPTION_TYPE=I.CODE "
  	  		+ " LEFT JOIN SYST001 K ON K.KIND='CRS_STATUS' AND A.CRS_STATUS=K.CODE "
  	  		+ " LEFT JOIN COUT005 J ON A.AYEAR=J.AYEAR AND A.SMS=J.SMS AND A.CRSNO=J.CRSNO " + " WHERE 0=0  ");

/** == 查詢條件 ST == */
	if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
	{
		sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
	{
		sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
	{
		sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
	{
		sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
	{
		sql.append(" AND A.NEW_REWORK = '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("PRODUCE_TYPE")).equals(""))
	{
		sql.append(" AND A.PRODUCE_TYPE LIKE '%" + Utility.nullToSpace(ht.get("PRODUCE_TYPE")) + "%' ");
	}
	
	if (!Utility.nullToSpace(ht.get("CRS_NAME_Q")).equals(""))
	{
		sql.append(" AND B.CRS_NAME LIKE '%" + Utility.nullToSpace(ht.get("CRS_NAME_Q")) + "%' ");
	}

	if (!Utility.nullToSpace(ht.get("CRS_STATUS")).equals(""))
	{
		sql.append(" AND A.CRS_STATUS = '" + Utility.nullToSpace(ht.get("CRS_STATUS")) + "' ");
	}

	sql.append(" ORDER BY A.AYEAR,decode(A.SMS,'3','0',A.SMS),A.FACULTY_CODE,A.CRSNO ");

	DBResult	rs	= null;

	try
	{
		if (pageQuery)
		{
			// 依分頁取出資料
			rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
		}
		else
		{
			// 取出所有資料
			rs	= dbmanager.getSimpleResultSet(conn);
			rs.open();
			rs.executeQuery(sql.toString());
		}

		Hashtable	rowHt	= null;

		while (rs.next())
		{
			rowHt	= new Hashtable();

			/** 將欄位抄一份過去 */
			for (int i = 1; i <= rs.getColumnCount(); i++)
			{
				if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
				{
					rowHt.put("CPRODUCE_TYPE", getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i)));
				}

				rowHt.put(rs.getColumnName(i), rs.getString(i));

			}

			result.add(rowHt);
		}
	}
	catch (Exception e)
	{
		throw e;
	}
	finally
	{
		if (rs != null)
		{
			rs.close();
		}
	}

	return result;
	}



//COU029M-詳	
public Vector cou029forPrint(Hashtable ht, Vector result,DBManager dbm01, Connection conn01)throws Exception
	{
	
	DBResult	rs	= null;


	if (sql.length() > 0)
	{
		sql.delete(0, sql.length());
	}
	try
	{
	sql.append(
				"SELECT DISTINCT "+
				"A.AYEAR,A.SMS,C.CODE_NAME AS CSMS,B.CRS_NAME AS CCRSNAME,B.CRS_ENG AS ECRSNAME,A.CRSNO,B.CRD,A.EVAL_ST_NUM, "+ 
				"D.CODE_NAME AS CCRS_STATUS,A.CRS_STATUS,A.FACULTY_CODE,A.PLAN_FACULTY_CODE,A.COMMON_FACULTY_CODE,A.NEW_REWORK,A.REQOPTION_TYPE,  "+
			    "A.CRS_CHAR,A.PRODUCE_TYPE,A.CRS_TIMES,S2.CODE_NAME AS TEACHING_TYPE, S3.CODE_NAME AS TEACHING_TYPE_NAME,A.MOD_RMK, A.TCH_ACADFIELD,  "+
		    	"A.RESERVE_ITEM,A.REWORK_ITEM,A.CRD_HR,S4.CODE_NAME AS OTHER_MEDIA_TYPE,DECODE(A.MAP_EXPORT_MK,'Y','是','否') AS MAP_EXPORT_MK, "+
				"A.TUT_TIMES,A.LAB_TIMES,A.ON_PC_NUM,F.CRSNO_OLD,A.CRS_BOOK,A.DISCIPLINE_TARGET,E.CRS_OUTLINE,E.TUT_TARGET_INTRODUCTION, "+
				"A.ORI_TEXT_NUM,A.MOD_TEXT_NUM,E.CRS_BOOK_CHAPTER,E.BASIC_KNWLDG,A.OPENED_DESCRIPTION,A.PRO_ACADFIELD,E.TUT_TARGET,  "+
				"E.CRS_GUTLINE,A.RMK,G.FACULTY_NAME,DECODE(A.NEW_REWORK,'3','3-續開','4','4-續開　' || S1.CODE_NAME || A.BEFORE_CRSNO,H.CODE_NAME) AS CNEW_REWORK,  "+
				"I.FACULTY_NAME AS PLAN_FACULTY_NAME,E.BIBLIOGRAPHY,  "+
				"J.FACULTY_NAME AS COMMON_FACULTY_NAME,  "+
				"K.DISCIPLINE_NAME,K.DISCIPLINE_ENG, "+
				"A.OPEN1,A.OPEN2,A.OPEN3,DECODE(A.OPEN1||A.OPEN3,'YY','四次暨多次面授開班','NY','多次面授','四次面授')AS OPEN, "+
				"X.CRS_GROUP_CODE, "+
				"Z.CRS_GROUP_CODE_NAME, "+
				"(B.CRD*(A.TUT_TIMES+A.LAB_TIMES))AS HR, "+
				"N.PREREQ_CRSNO, "+
				"O.CRS_NAME AS PREREQ_CRSNO_NAME, "+
				"A.FIRSTCOU, "+
				"P.SW_REQUEST,P.HW_REQUEST,A.OTHER, "+
				"Y.faculty_abbrname||'_'||Z.CRS_GROUP_CODE_NAME as CRS_GROUP_CODE_NAME2,"+
				"A.OPEN1,A.OPEN3, "+
				"T.CODE_NAME AS CRS_CHAR_NAME,U.CODE_NAME AS CRS_BOOK_NAME, "+
				"W.NAME AS DISCIPLINE_CHARGE, K1.DISCIPLINE_NAME as FIELD_NAME,A.MOD_RMK, "+
				"E.EVAL_MANNER, '期中考：'||DECODE(A.EXAM_MK_MID,'N','非統一命題集中考試','統一命題集中考試') as EXAM_MK_MID, '期末考：'||DECODE(A.EXAM_MK,'N','非統一命題集中考試','統一命題集中考試') AS EXAM_MK "+
				"FROM COUT001 A "+
				"JOIN COUT002 B ON A.CRSNO=B.CRSNO  "+
				"LEFT JOIN SYST001 C ON C.KIND='SMS' AND A.SMS=C.CODE  "+
				"LEFT JOIN SYST001 D ON D.KIND='CRS_STATUS' AND A.CRS_STATUS=D.CODE "+ 
				"LEFT JOIN COUT005 E ON A.AYEAR=E.AYEAR AND A.SMS=E.SMS AND A.CRSNO=E.CRSNO "+ 
				"LEFT JOIN "+
					"(SELECT F1.CRSNO,TO_CHAR(WMSYS.WM_CONCAT(F1.CRSNO_OLD)) AS CRSNO_OLD  FROM COUT010 F1  "+
					"WHERE F1.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' "+
					"GROUP BY F1.CRSNO  "+
					") F ON A.CRSNO = F.CRSNO  "+
				"LEFT JOIN SYST003 G ON G.FACULTY_CODE=A.FACULTY_CODE AND G.ASYS='1' "+
				"LEFT JOIN SYST001 H ON H.KIND='NEW_REWORK' AND A.NEW_REWORK=H.CODE  "+
				"LEFT JOIN SYST003 I ON I.FACULTY_CODE=A.PLAN_FACULTY_CODE AND I.ASYS='1' "+
				"LEFT JOIN SYST003 J ON J.FACULTY_CODE=A.COMMON_FACULTY_CODE AND J.ASYS='1' "+
				"LEFT JOIN COUT100 K ON K.FACULTY_CODE=A.FACULTY_CODE AND K.DISCIPLINE_CODE=A.DISCIPLINE_CODE "+
				"LEFT JOIN COUT100 K1 ON K1.FACULTY_CODE=A.FACULTY_CODE AND K1.DISCIPLINE_CODE=A.FIELD_CODE "+	//領域

				"LEFT JOIN COUT011 N ON N.AYEAR=A.AYEAR AND N.SMS=A.SMS AND N.CRSNO=A.CRSNO  "+
				"LEFT JOIN COUT002 O ON N.PREREQ_CRSNO=O.CRSNO "+
        		"LEFT JOIN COUT008 P ON P.AYEAR=A.AYEAR AND P.SMS=A.SMS AND P.CRSNO=A.CRSNO "+
        		"LEFT JOIN COUT103 X ON A.AYEAR=X.AYEAR AND A.SMS=X.SMS AND A.CRSNO=X.CRSNO AND A.CRSNO = X.FACULTY_CODE  "+
        		"LEFT JOIN SYST008 Y ON Y.FACULTY_CODE=X.FACULTY_CODE AND Y.TOTAL_CRS_NO=X.TOTAL_CRS_NO AND Y.ASYS='2'  " +
        		"LEFT JOIN COUT102 Z ON X.FACULTY_CODE=Z.FACULTY_CODE AND X.TOTAL_CRS_NO=Z.TOTAL_CRS_NO AND X.CRS_GROUP_CODE=Z.CRS_GROUP_CODE  "+
        	    "LEFT JOIN SYST001 T ON T.KIND='CRS_CHAR' AND T.CODE=A.CRS_CHAR "+
        		"LEFT JOIN syst001 U ON U.kind='CRS_BOOK' AND U.CODE=A.CRS_BOOK "+   
        		"LEFT JOIN PERT017 V on V.FACULTY_CODE=B.FACULTY_CODE AND V.DISCIPLINE_CODE=B.DISCIPLINE_CODE  "+ //移除 AND V.AYEAR=A.AYEAR and V.SMS=A.SMS
        		"LEFT JOIN TRAT001 W on W.IDNO=V.TCH_IDNO "+
        		"LEFT JOIN SYST001 S1 ON S1.KIND='NEW_REWORK_KIND' AND S1.CODE=A.NEW_REWORK_KIND "+
        		"LEFT JOIN SYST001 S2 ON S2.KIND='TEACHING_TYPE' AND S2.CODE=A.TEACHING_TYPE "+
        		"LEFT JOIN SYST001 S3 ON S3.KIND='TEACHING_TYPE_NAME' AND S3.CODE=A.TEACHING_TYPE_NAME "+
        		"LEFT JOIN SYST001 S4 ON S4.KIND='OTHER_MEDIA_TYPE' AND S4.CODE=A.OTHER_MEDIA_TYPE "+
				"WHERE 0=0 "
			   );

/** == 查詢條件 ST == */
	if (!Utility.nullToSpace(ht.get("AYEAR")).equals(""))
	{
		sql.append(" AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("SMS")).equals(""))
	{
		sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
	{
		sql.append(" AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
	{
		sql.append(" AND A.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("NEW_REWORK")).equals(""))
	{
		sql.append(" AND A.NEW_REWORK = '" + Utility.nullToSpace(ht.get("NEW_REWORK")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("PRODUCE_TYPE")).equals(""))
	{
		sql.append(" AND A.PRODUCE_TYPE = '" + Utility.nullToSpace(ht.get("PRODUCE_TYPE")) + "' ");
	}

	if (!Utility.nullToSpace(ht.get("CRS_NAME_Q")).equals(""))
	{
		sql.append(" AND B.CRS_NAME LIKE '%" + Utility.nullToSpace(ht.get("CRS_NAME_Q")) + "%' ");
	}
	
	if (!Utility.nullToSpace(ht.get("CRS_STATUS")).equals(""))
	{
		sql.append(" AND A.CRS_STATUS = '" + Utility.nullToSpace(ht.get("CRS_STATUS")) + "' ");
	}

	sql.append(" ORDER BY A.AYEAR,A.SMS,A.CRSNO,A.FACULTY_CODE ");

	
	


		if (pageQuery)
		{
			// 依分頁取出資料
			rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
		}
		else
		{
			// 取出所有資料
			rs	= dbmanager.getSimpleResultSet(conn);
			rs.open();
			rs.executeQuery(sql.toString());
		}

		
		Vector tmp = new Vector();
		while (rs.next())
		{
			Hashtable rowHt = new Hashtable();
			rowHt	= new Hashtable();

			/** 將欄位抄一份過去 */
			for (int i = 1; i <= rs.getColumnCount(); i++)
			{
				if ("PRODUCE_TYPE".equals(rs.getColumnName(i)))
				{
					rowHt.put("CPRODUCE_TYPE", getCPRODUCE_TYPE(dbm01, conn01, rs.getString(i)));
				}

				rowHt.put(rs.getColumnName(i), rs.getString(i));

			}
			
			
			// 取得採計系所清單-------------------------開始----------------------------
	        if (sql.length() > 0)
    		{
    		    sql.delete(0, sql.length());
    		}
    		
	        sql.append(
	        		" SELECT B.FACULTY_NAME "+
    				" FROM COUT103 A "+
    				" JOIN SYST003 B on B.FACULTY_CODE=A.FACULTY_CODE AND B.ASYS='1' "+
    				" WHERE 0=0 "+
    				" AND A.TOTAL_CRS_NO = '01' "+
    				" AND A.CRS_GROUP_CODE = '003' "+
    				" AND A.CRSNO = '"+rs.getString("CRSNO")+"' "+
	        		" AND A.AYEAR = '"+rs.getString("AYEAR")+"' "+
	        		" AND A.SMS = '"+rs.getString("SMS")+"' ");
	        
            COUT103DAO COUT103 = new COUT103DAO(dbmanager, conn);
            DBResult rs1 = COUT103.query(sql.toString());
            String FACULTY_NAME2 = "";

            for(int j=0 ;rs1.next();j++) {
            	if(j==0){
            		FACULTY_NAME2 += rs1.getString("FACULTY_NAME");	
            	}else{
            		FACULTY_NAME2 += ","+rs1.getString("FACULTY_NAME");
            	} 
            }
            rs1.close();
            rowHt.put("FACULTY_NAME2", FACULTY_NAME2);
         // 取得採計系所清單-------------------------結束----------------------------
			tmp.add(rowHt);
		}
		// 因同一科有可能有多個採計學系,因此需組起來,  ex:詩選----人文學系,資管系...  組成一筆
		Vector pk = new Vector();
        pk.add("CRSNO");
        
        Vector tmp2 = new Vector();
        UtilityX.combinCompareNextTheSameData(pk, "CRS_GROUP_CODE_NAME2", ",", tmp, tmp2);
        
        // 處理資料,將相同科目的課程類別組起來(僅顯示一筆,且如有除相選外的類別則相選不顯示)
        // 一次迴圈表示一個科目
        for(int i=0; i<tmp2.size(); i++){
			Hashtable content = (Hashtable)tmp2.get(i);
			content.put("COMBINE_DATA", getCrsGroupName2(content));				
			result.add(content);
		}
	}
	catch (Exception e)
	{
		throw e;
	}
	finally
	{
		if (rs != null)
		{
			rs.close();
		}
	}

	return result;
	}	

	// 處理某一科的所有課程類別
	private String getCrsGroupName2(Hashtable ht){
		String result = "";
		
		String[] crsGroupCodeNameArray = Utility.split(ht.get("CRS_GROUP_CODE_NAME2").toString(), ",");
		
		// 判斷該科共有哪些類別
		Set distinctCrsGroupSet = new LinkedHashSet();
		for(int i=0; i<crsGroupCodeNameArray.length; i++){			
			distinctCrsGroupSet.add(Utility.split(crsGroupCodeNameArray[i], "_")[1]);
		}
		
		// 組出要顯示的字串		
		if(distinctCrsGroupSet.size()==1){
			Iterator it = distinctCrsGroupSet.iterator();
			
			if(it.hasNext())
				result=it.next().toString();
		}else{	
			for(int i=0; i<crsGroupCodeNameArray.length; i++){
				String crsGroupCodeName = crsGroupCodeNameArray[i].replaceAll("_", "").replaceAll("專業選修", "專修").replaceAll("科必修", "必修");
								
				// 有多種類別時,相關選修則不顯示
				if(crsGroupCodeName.endsWith("相關選修"))
					continue;
				
				result+=(result.equals("")?"":"<br>")+crsGroupCodeName;				
			}
		}			
		
		return result;
	}	
	
	/**
     * JOIN 其它 Cout001,Cout002 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getTra026mQuery(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }


        sql.append("select  ");
		sql.append("a.ayear,a.sms , a.crsno,");
		sql.append("a.crs_status,");
		sql.append("s3.CODE_NAME as crs_status_name,");
		sql.append("nvl(a.DATA_LOCK,'N') as DATA_LOCK, ");
		sql.append("b.CRS_NAME,a.FACULTY_CODE, ");		
		sql.append("to_number(a.ayear)||s1.CODE_NAME as ayearsms_name, ");		
		sql.append("s2.faculty_name, ");
		sql.append("( select count(1) from cout003 x where x.ayear = a.ayear and x.sms = a.sms and x.crsno = a.crsno and x.result_mk = 'Y' ) as result_mk_check, ");
		sql.append("(select count(1)  ");
		sql.append("  from cout003 x ");
		sql.append("  join trat006 y on x.ayear = y.ayear ");
		sql.append("				and x.sms = y.sms ");
		sql.append("				and x.crsno = y.crsno ");
		sql.append("				and x.idno = y.idno ");
		sql.append("				and x.job_type = y.job_type ");
		sql.append(" where x.ayear = a.ayear ");
		sql.append("   and x.sms = a.sms ");
		sql.append("   and x.crsno = a.crsno ");
		sql.append("   and nvl(y.emp_result,'0') = '1' ");
		sql.append(") as EMP_RESULT_CHECK  ");
		sql.append("from cout001 a ");
		sql.append("join cout002 b on a.crsno = b.crsno  ");
		sql.append("join syst001 s1 on s1.KIND = 'SMS' and a.SMS = s1.CODE ");
		sql.append("join syst003 s2 on s2.asys = '1' and a.faculty_code = s2.faculty_code ");
		sql.append("left join syst001 s3 on s3.KIND = 'CRS_STATUS' and a.CRS_STATUS = s3.CODE   ");
		sql.append("where a.ayear = '"+Utility.checkNull(ht.get("AYEAR"),"")+"' ");
		sql.append("and a.sms = '"+Utility.checkNull(ht.get("SMS"),"")+"' ");
		sql.append("and a.new_rework in ('1','2','4','6') ");
		sql.append("and a.crs_status not in ('3','4') ");
		
		if (!Utility.checkNull(ht.get("CRSNO"), "").equals("")){
            sql.append("and a.crsno  = '" + Utility.checkNull(ht.get("CRSNO"),"") + "' ");        
        }
		
		if (!Utility.checkNull(ht.get("FACULTY_CODE"), "").equals("")){
            sql.append("and a.faculty_code  = '" + Utility.checkNull(ht.get("FACULTY_CODE"),"") + "' ");        
        }		
		
		sql.append("and exists ");
		sql.append("( ");
		sql.append("   select 1  ");
		sql.append("   from cout003 x  ");
		sql.append("   join trat006 y on x.ayear = y.ayear and x.sms = y.sms and x.crsno = y.crsno and x.idno = y.idno and x.job_type = y.job_type  ");
		sql.append("   where x.ayear = a.ayear and x.sms = a.sms and x.crsno = a.crsno  ");
		if("Y".equals(Utility.checkNull(ht.get("EMP_RESULT"), ""))){
			sql.append("and nvl(y.emp_result,'0') = '1'  ");				
		}else if("N".equals(Utility.checkNull(ht.get("EMP_RESULT"), ""))){
			sql.append("and nvl(y.emp_result,'0') != '1' ");
		}else if("".equals(Utility.checkNull(ht.get("EMP_RESULT"), ""))){
			
		}
		sql.append(") ");        
        
		
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }	

    /**
     * JOIN 其它 Cout001,Cout002 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCou131rMain(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }


        sql.append("SELECT " +
        		   "C01.AYEAR, C01.SMS, C01.CRSNO, C01.NEW_REWORK , S2.CODE AS NEW_REWORK_NAME," +
        		   "S1.FACULTY_CODE,S1.FACULTY_NAME  " +
        		   "FROM COUT001 C01 " +
        		   "JOIN SYST003 S1 ON S1.ASYS = '1' AND S1.FACULTY_CODE = C01.FACULTY_CODE " + 
        		   "JOIN SYST001 S2 ON S2.KIND = 'NEW_REWORK' AND S2.CODE = C01.NEW_REWORK " +
        		   "WHERE C01.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' "+
        		   "AND C01.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' "+
        		   //"AND C01.new_rework IN ('3','4') "+
        		   "");
        
        
        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals(""))
        {
            sql.append("AND C01.FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
        }

        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append("AND C01.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }
        if (!Utility.nullToSpace(ht.get("TRACK_CTRL")).equals(""))
        {
            if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("1")){
                sql.append("AND C01.open1 = 'Y' ");
            }
            if (Utility.nullToSpace(ht.get("TRACK_CTRL")).equals("2")){
                sql.append("AND C01.open3 = 'Y' AND C01.open1 = 'N' ");
            }
        }        
        sql.append("ORDER BY C01.FACULTY_CODE , C01.CRSNO ");
        
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }	 

    /**
     * JOIN 其它 Cout001,Cout002 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getAut027mQuery(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append("select \n");
        sql.append("a.new_rework, \n");
        sql.append("e.faculty_code, \n");
        sql.append("e.faculty_abbrname, \n");
        sql.append("d.crsno, \n");
        sql.append("d.crs_name, \n");
        sql.append("b.ayear as ayear_1,b.sms as sms_1, \n");
        sql.append("c.ayear as ayear_2,c.sms as sms_2, \n");
        sql.append("count(unique f.idno) as num, \n");
        sql.append("count(unique g.user_id) as autnum \n");
        sql.append("from cout001 a \n");
        sql.append("left join cout001 b on a.crsno = b.crsno and b.crs_status = '5' and b.new_rework in ('1','2','6') \n");
        sql.append("left join cout001 c on a.crsno = c.crsno and c.crs_status = '5' and c.new_rework in ('3','4') \n");
        sql.append("join cout002 d on a.crsno = d.crsno \n");
        sql.append("join syst003 e on e.faculty_code = a.faculty_code    \n");
        sql.append("left join trat006 f on (						     \n");	
        sql.append("	 ( f.ayear = b.ayear and f.sms = b.sms )         \n");
        sql.append("	 and							                 \n");
        sql.append("	 ( 							                     \n");
        sql.append("	   ( f.ayear = c.ayear and f.sms = c.sms )       \n");
        sql.append("	   or 							                 \n");
        sql.append("	   ( c.ayear||c.sms > b.ayear||b.sms )           \n");
        sql.append("	 ) 							                     \n");
        sql.append(") 								                     \n");
        sql.append("and f.duty_code = '01' and f.job_type in('11','12') and f.crsno = a.crsno  \n");
        sql.append("left join autt001 g on g.user_idno = f.idno \n");
        sql.append("where 1=1  \n");
        sql.append("and a.ayear = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' \n");
        sql.append("and a.sms = '" + Utility.nullToSpace(ht.get("SMS")) + "' \n");
        
        if (!Utility.nullToSpace(ht.get("CRSNO")).equals(""))
        {
            sql.append("and a.crsno = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' \n");
        }
        if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals("") && !Utility.nullToSpace(ht.get("FACULTY_CODE")).equals("%"))
        {
            sql.append("and a.faculty_code = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' \n");
        }        
        
        sql.append("and a.new_rework in('3','4') \n");
        sql.append("and (  \n");
        sql.append("	b.ayear||b.sms = ( select max(r1.ayear||r1.sms) from cout001 r1 where r1.crsno = b.crsno and r1.new_rework in ('1','2','6') and r1.crs_status ='5' ) \n");
        sql.append("	or \n");
        sql.append("	b.ayear||b.sms is null  \n");
        sql.append("	) \n");
        sql.append("and (  \n");
        sql.append("	c.ayear||c.sms = ( select max(r2.ayear||r2.sms) from cout001 r2 where r2.crsno = c.crsno and r2.new_rework in ('3','4') and r2.crs_status ='5' ) \n");
        sql.append("	or \n");
        sql.append("	c.ayear||c.sms is null  \n");
        sql.append("	) \n");
        sql.append("and exists ( select 1 from cout003 c03 where a.crsno = c03.crsno  )");
        sql.append("group by e.faculty_code,e.faculty_abbrname,d.crsno,d.crs_name,b.ayear,b.sms,c.ayear,c.sms,a.new_rework \n");
        sql.append("order by e.faculty_code,d.crsno \n");
     
        DBResult	rs	= null;

        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }
    
    public Vector getDataForCou0512ToQuery(Hashtable ht) throws Exception {
    	Vector result = new Vector();
    	
    	String sql = 
    		"select DISTINCT \n"+
    	    "       R1.CRSNO,--科目代號 \n"+
    	    "       R1.CRS_NAME,--科目名稱 \n"+
    	    "       R1.CRD --學分 \n"+
    	    "FROM COUT001 M \n"+
    	    "JOIN COUT002 R1 \n"+
    	    "  ON R1.CRSNO = M.CRSNO \n"+
            "--排除已被引用的科目 \n"+
            "WHERE not exists (SELECT 1 \n"+
            "                  FROM COUT019 A \n"+
            "                  WHERE A.TOTAL_CRS_NO = '"+ht.get("TOTAL_CRS_NO")+"' \n"+
            "                  AND   A.CRSNO = M.CRSNO) \n"+
            "--排除已選為多選科目 \n"+
            "AND   not exists (SELECT 1 \n"+
            "                  FROM COUT020 B \n"+
            "                  WHERE B.TOTAL_CRS_NO = '"+ht.get("TOTAL_CRS_NO")+"' \n"+
            "                  AND   B.MULTI_CRSNO= M.CRSNO) \n"+
            (ht.get("QUERY_FACULTY_CODE").equals("")?"":"and M.FACULTY_CODE='"+ht.get("QUERY_FACULTY_CODE")+"' \n")+
    		(ht.get("CRSNO").equals("")?"":"and (M.crsno='"+ht.get("CRSNO")+"' or R1.crs_name like '%"+ht.get("CRSNO")+"%') \n")+
    		
    		"union \n"+
    		"--取沒有開課計畫但學生有修過的科目 \n"+
    		"select DISTINCT \n"+
    	    "       R1.CRSNO,--科目代號 \n"+
    	    "       R1.CRS_NAME,--科目名稱 \n"+
    	    "       R1.CRD --學分 \n"+	
    	    "FROM STUT010 M \n"+
    	    "JOIN COUT002 R1 \n"+
    	    "  ON R1.CRSNO = M.CRSNO \n"+
            "--排除已被引用的科目 \n"+
            "WHERE not exists (SELECT 1 \n"+
            "                  FROM COUT019 A \n"+
            "                  WHERE A.TOTAL_CRS_NO = '"+ht.get("TOTAL_CRS_NO")+"' \n"+
            "                  AND   A.CRSNO = M.CRSNO) \n"+
            "--排除已選為多選科目 \n"+
            "AND   not exists (SELECT 1 \n"+
            "                  FROM COUT020 B \n"+
            "                  WHERE B.TOTAL_CRS_NO = '"+ht.get("TOTAL_CRS_NO")+"' \n"+
            "                  AND   B.MULTI_CRSNO= M.CRSNO) \n"+
    	    "--排除有計畫的科目 \n"+
    	    "AND   not exists (SELECT 1 \n"+
    	    "                  FROM COUT001 C \n"+
    	    "                  WHERE C.CRSNO = M.CRSNO) "+
            (ht.get("QUERY_FACULTY_CODE").equals("")?"":"and R1.FACULTY_CODE='"+ht.get("QUERY_FACULTY_CODE")+"' \n")+
    		(ht.get("CRSNO").equals("")?"":"and (R1.crsno='"+ht.get("CRSNO")+"' or R1.crs_name like '%"+ht.get("CRSNO")+"%') \n")+
            "ORDER BY 1 \n";

    	    
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }
    
    /**
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout001Cou002Cout100ForUse_cou135r(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        /**學年學期初始*/
        java.text.SimpleDateFormat dateTimeFormat = new java.text.SimpleDateFormat("yyyyMMdd");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        String today = dateTimeFormat.format(cal.getTime());
        com.acer.log.MyLogger logger = new com.acer.log.MyLogger("PER050M");
        com.acer.db.DBManager dbManager = new com.acer.db.DBManager(logger);  
     	com.nou.sys.SYSGETSMSDATA sys = new com.nou.sys.SYSGETSMSDATA(dbManager);
        sys.setSYS_DATE(today);
        // 1.當期 2.前期 3.後期 4.前學年 5.後學年	
        sys.setSMS_TYPE("1");
        int AA = sys.execute();
        String AYEAR="";
        String SMS="";
  	
  	    if(AA == 1) 
  	    {
  		  AYEAR = sys.getAYEAR();
  		  SMS = sys.getSMS();     		
  	    }
  	  
  	    if (("3").equals("SMS")){
  		    SMS = "0";
  	    }        
        
        sql.append(" SELECT ");
        sql.append(" 	M.FACULTY_CODE, ");
        sql.append(" 	M.TOTAL_CRS_NO, ");
        sql.append(" 	M.TOTAL_CRS_CH, ");
        sql.append(" 	R2.CRSNO, ");
        sql.append(" 	R2.CRS_NAME, ");
        sql.append(" 	R7.AYEAR, ");
        sql.append(" 	R7.SMS, \n");
        sql.append(" 	R6.CODE_NAME AS SMS_NAME \n");
        sql.append(" FROM COUT016 M  ");
        sql.append(" 		JOIN COUT019 R1  ");
        sql.append(" 		  ON R1.FACULTY_CODE = M.FACULTY_CODE  ");
        sql.append(" 		  AND R1.TOTAL_CRS_NO = M.TOTAL_CRS_NO   \n");
        sql.append(" 		JOIN COUT002 R2  ");
        sql.append(" 		  ON R2.CRSNO = R1.CRSNO   \n");
        sql.append(" LEFT JOIN COUT001 R7 "+
	               " ON R7.CRSNO = R1.CRSNO "+
	               " AND R7.CRS_STATUS = '5' "+
	               " AND R7.AYEAR || DECODE(R7.SMS,'3','0',R7.SMS) >= '" + AYEAR + SMS + "' ");
	    sql.append(" LEFT JOIN SYST001 R6 ON R6.KIND = 'SMS'  AND R6.CODE = R7.SMS ");
        sql.append(" WHERE 1=1 ");
        
        if(Utility.nullToSpace(ht.get("FACULTY_CODE")).trim().length()>0){
        	  sql.append(" 	AND M.FACULTY_CODE = '"+ Utility.dbStr( Utility.nullToSpace(ht.get("FACULTY_CODE")).trim() ) +"'  ");
        }
        
        if(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")).trim().length()>0){
	    	  sql.append(" 	AND M.TOTAL_CRS_NO = '"+ Utility.dbStr( Utility.nullToSpace(ht.get("TOTAL_CRS_NO")).trim() ) +"'  ");
	    }
        sql.append("   AND ((R7.CRSNO IS NOT NULL AND "+
  	               "         R7.AYEAR || DECODE(R7.SMS,'3','0',R7.SMS) = "+
  	               "         (SELECT MIN(T01.AYEAR || DECODE(T01.SMS,'3','0',T01.SMS)) FROM COUT001 T01 "+
  	               "          WHERE T01.CRSNO = R7.CRSNO "+
  	               "          AND T01.CRS_STATUS = R7.CRS_STATUS "+
  	               "          AND T01.AYEAR || DECODE(T01.SMS,'3','0',T01.SMS) >='"+AYEAR + SMS + "' ) "+
  	               "          ) OR "+
  	               "         R7.CRSNO IS NULL) ");
        
        sql.append(" ORDER BY ");
        sql.append(" 	M.FACULTY_CODE, ");
        sql.append(" 	M.TOTAL_CRS_NO, ");
        sql.append(" 	R1.CLASS_GROUP_CODE, ");
        sql.append(" 	R1.ROWSTAMP, ");
        sql.append(" 	R2.CRSNO ");

        DBResult	rs	= null;
        //System.out.println(sql.toString());
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();           

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }
                rowHt.put("AYEAR_AT", AYEAR);
                rowHt.put("SMS_AT", SMS);
                
                rowHt.put("CLASS_STATUS", this.getCout135ClassStatus(rowHt));

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }
    
    /**
     * 取得未來四年課程狀態
     * @param ht
     * @return
     * @throws Exception
     */
    public String getCout135ClassStatus(Hashtable ht) throws Exception
    {
        String CRSNO = Utility.nullToSpace(ht.get("CRSNO")).trim();
        String AYEAR = Utility.nullToSpace(ht.get("AYEAR_AT")).trim();
        String SMS = Utility.nullToSpace(ht.get("SMS_AT")).trim();
        
        if(CRSNO.length()==0 || AYEAR.length()==0 || SMS.length()==0){
        	return "";
        }

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(" SELECT ");
        sql.append(" 	TO_CHAR(WMSYS.WM_CONCAT(TO_CHAR(AYEAR||SMS_NAME||'('||CRS_STATUS_NAME||')')) ");
        sql.append(" 	) AS CLASS_STATUS  ");
        sql.append(" FROM ");
        sql.append(" 	(	SELECT ");
        sql.append(" 			M.AYEAR, ");
        sql.append(" 			R1.CODE_NAME AS SMS_NAME, ");
        sql.append(" 			R2.CODE_NAME AS CRS_STATUS_NAME  ");
        sql.append(" 		FROM ");
        sql.append(" 			COUT001 M  ");
        sql.append(" 			LEFT JOIN SYST001 R1  ");
        sql.append(" 			  ON R1.KIND = 'SMS'  ");
        sql.append(" 			  AND R1.CODE = M.SMS  ");
        sql.append(" 			LEFT JOIN SYST001 R2  ");
        sql.append(" 			  ON R2.KIND = 'CRS_STATUS'  ");
        sql.append(" 			  AND R2.CODE = M.CRS_STATUS  ");
        sql.append(" 		WHERE ");
        sql.append(" 			M.CRSNO = '").append( Utility.dbStr(CRSNO) ).append("'  ");
        sql.append(" 			AND M.AYEAR||DECODE(M.SMS,'3','0',M.SMS) >= ");
        sql.append("				'").append( Utility.dbStr(AYEAR) ).append("' ");
        sql.append("					||DECODE('").append( Utility.dbStr(SMS) ).append("','3', '0','").append( Utility.dbStr(SMS) ).append("')  ");
        sql.append("            AND TO_NUMBER(M.AYEAR) - TO_NUMBER("+Utility.dbStr(AYEAR)+") <= 4 ");
        sql.append(" 		ORDER BY AYEAR||DECODE(SMS,'3','0',SMS) ");
        sql.append(" 	) ");
        sql.append(" WHERE ROWNUM < 5 ");

        DBResult	rs	= null;
        //System.out.println(sql.toString());
        try
        {
        	rs	= dbmanager.getSimpleResultSet(conn);
            rs.open();
            rs.executeQuery(sql.toString());
            
            if (rs.next())
            {
            	return rs.getString("CLASS_STATUS");
            }
            return "";
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }
    }

    /**
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
    public Vector getCout001Cou002Cout100ForUse_cou051r(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(" SELECT ");
        sql.append("    M.FACULTY_CODE, ");
        sql.append(" 	R1.TOTAL_CRS_NO, ");
        sql.append(" 	R1.TOTAL_CRS_CH, ");
        sql.append(" 	R2.STNO, ");
        sql.append(" 	R3.NAME, ");
        sql.append(" 	R2.NOU_EMAIL, ");
        sql.append(" 	R3.AREACODE_HOME, ");
        sql.append(" 	R3.TEL_HOME, ");
        sql.append(" 	R3.CRRSADDR_ZIP, ");
        sql.append(" 	R3.CRRSADDR ");
        sql.append(" FROM COUT021 M  ");
        sql.append(" 		JOIN COUT016 R1  ");
        sql.append(" 		  ON R1.FACULTY_CODE = M.FACULTY_CODE  ");
        sql.append(" 		  AND R1.TOTAL_CRS_NO = M.TOTAL_CRS_NO  ");
        sql.append(" 		JOIN STUT003 R2 ");
        sql.append(" 		  ON R2.STNO = M.STNO ");
        sql.append(" 		JOIN STUT002 R3 ");
        sql.append(" 		  ON R3.IDNO = R2.IDNO ");
        sql.append(" WHERE 1=1 ");
        
        
        if(Utility.nullToSpace(ht.get("FACULTY_CODE")).trim().length()>0){
        	  sql.append(" 	AND M.FACULTY_CODE = '"+ Utility.dbStr( Utility.nullToSpace(ht.get("FACULTY_CODE")).trim() ) +"'  ");
        }
        
        if(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")).trim().length()>0){
	    	  sql.append(" 	AND M.TOTAL_CRS_NO = '"+ Utility.dbStr( Utility.nullToSpace(ht.get("TOTAL_CRS_NO")).trim() ) +"'  ");
	    }
        
        sql.append("    AND NOT EXISTS( ");
        sql.append("         SELECT 1 ");
        sql.append("         FROM GRAT034 X WHERE X.STNO = M.STNO AND X.FACULTY_CODE = M.FACULTY_CODE ");
        sql.append("         AND X.PROGRAM = M.TOTAL_CRS_NO AND X.STATUS = '2' ");
        sql.append("     ) ");
        sql.append(" ORDER BY ");
        sql.append(" 	M.FACULTY_CODE, ");
        sql.append(" 	M.TOTAL_CRS_NO, ");
        sql.append(" 	M.STNO ");

        DBResult	rs	= null;
        //System.out.println(sql.toString());
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }
    
    public Vector getCout001Cout002cou002rForUse(Hashtable ht) throws Exception {

        DBResult rs = null;

        Vector result = new Vector();

        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        
        sql.append
		(
        	"SELECT " +
        	"a.AYEARSMS,a.crsno,a.crsno1,a.produce_type,a.crd,a.faculty_name,a.produce_type_code, " +
        	"sum(a.stno_num_1) as stno_num_1,sum(a.stno_num_2) as stno_num_2,sum(a.stno_num_3) as stno_num_3, "+
        	"sum(a.stno_num_1)+sum(a.stno_num_2)+sum(a.stno_num_3) as stno_num_ALL " +
        	"from "+
        	"( "+
        	"SELECT A.ayear||A.sms as AYEARSMS,a.crsno,c.crs_name AS crsno1,d.code_name AS produce_type,a.crd, "+
        	"e.faculty_name,a.produce_type AS produce_type_code,decode(g.STTYPE,'1',g.stno_num,'0') as stno_num_1, "+
    		"decode(g.STTYPE,'2',g.stno_num,'0') as stno_num_2,decode(g.STTYPE,'3',g.stno_num,'0') as stno_num_3 " +
    		"FROM cout001 a "+
    		"join cout002 c on a.crsno = c.crsno "+
    		"left join syst001 d on TRIM(a.produce_type) = d.code AND d.kind = 'PRODUCE_CHOOSE'  "+
    		"join syst003 e on a.faculty_code = e.faculty_code "+
			"join  "+
    		"( SELECT f.crsno, f.ayear ||f.sms AS ayearsms,C.STTYPE,COUNT (1) AS stno_num "+
			"FROM cout001 f "+
    		"JOIN scdt004 b on f.ayear = b.ayear AND f.sms = b.sms AND f.crsno = b.crsno "+
    		"JOIN STUT003 C on c.stno = b.stno  "+
    		"WHERE 1=1 "+
    		"AND f.crsno = '"+ht.get("CRSNO")+"' AND f.OPEN1='Y' AND f.CRS_STATUS  = '5' "+
			"GROUP BY f.crsno,f.ayear,f.sms,C.STTYPE  "+
			") g " +
			"on A.ayear||A.sms=g.ayearsms and a.crsno = g.crsno "+
			"WHERE 1=1 " +
			"AND a.CRS_STATUS  not in ('3','4') AND a.open1 = 'Y' AND e.asys = '1' AND a.crsno = '"+ht.get("CRSNO")+"' "+
			")a "
		);

        sql.append(" GROUP BY a.AYEARSMS,a.crsno,a.crsno1,a.produce_type,a.crd,a.faculty_name,a.produce_type_code ");
		sql.append(" ORDER BY a.AYEARSMS DESC ");
     
        //== 查詢條件 ED ==


        //System.out.println(sql.toString());
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }

            Hashtable	rowHt	= null;

            while (rs.next())
            {
                rowHt	= new Hashtable();

                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }

        return result;
    }
    

	public Hashtable getCrsnoSelePla(String AYEAR ,String SMS ,String STNO ,String WhereCrsno) throws Exception
    {
		Hashtable seleHt = new Hashtable();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ");
		sql.append("M.CRSNO,M.TEACHING_TYPE, '1' AS TYPE, TO_CHAR('') AS PERVALUE  ");
		sql.append("FROM COUT001 M  ");
		sql.append("JOIN SYST001 R1 ON R1.KIND = 'TEACHING_TYPE' AND R1.CODE = M.TEACHING_TYPE ");
		sql.append("WHERE M.AYEAR = '" + AYEAR + "' ");
		sql.append("AND M.SMS = '" + SMS + "' ");
		sql.append("AND M.CRSNO IN ('" + Utility.replace(WhereCrsno, ",", "','") + "') ");
		sql.append("AND M.OPEN1 = 'Y' ");
		sql.append("AND M.TEACHING_TYPE IN ('1','2') ");
		sql.append("AND ( ");
		sql.append("	SELECT X.OPEN_SELPER ");
		sql.append("	FROM PLAT001 X  ");
		sql.append("	JOIN SYST002 Y ON X.CENTER_ABRCODE = Y.CENTER_ABRCODE  ");
		sql.append("	WHERE Y.CENTER_CODE = (SELECT Z.CENTER_CODE FROM STUT003 Z WHERE Z.STNO = '" + STNO + "') ");
		sql.append("	AND X.AYEAR = '" + AYEAR + "' ");
		sql.append("	AND X.SMS = '" + SMS + "' ");
		sql.append(") = 'Y' ");
		sql.append("UNION ");
		sql.append("SELECT  ");
		sql.append("M.CRSNO,M.NETWK_CLASS ,'2' AS TYPE, TO_CHAR(R1.CODE_NAME) AS PERVALUE ");
		sql.append("FROM PLAT033 M ");
		sql.append("JOIN SYST001 R1 ON R1.KIND = 'NETWK_CLASS' AND R1.CODE = M.NETWK_CLASS ");
		sql.append("WHERE M.AYEAR = '" + AYEAR + "' ");
		sql.append("AND M.SMS = '" + SMS + "' ");
		sql.append("AND M.CRSNO IN ('" + Utility.replace(WhereCrsno, ",", "','") + "') ");
		sql.append("AND ( ");
		sql.append("	SELECT X.OPEN_SELPER ");
		sql.append("	FROM PLAT001 X  ");
		sql.append("	JOIN SYST002 Y ON X.CENTER_ABRCODE = Y.CENTER_ABRCODE  ");
		sql.append("	WHERE Y.CENTER_CODE = (SELECT Z.CENTER_CODE FROM STUT003 Z WHERE Z.STNO = '"+STNO+"') ");
		sql.append("	AND X.AYEAR = '" + AYEAR + "' ");
		sql.append("	AND X.SMS = '" + SMS + "' ");
		sql.append(") = 'Y' ");
		sql.append("ORDER BY 1,3 ");
		
		Hashtable nmHt = new Hashtable();
		nmHt.put("01", "視訊夜間班");
		nmHt.put("02", "視訊下午班");
		nmHt.put("03", "視訊上午班");
		nmHt.put("2" , "實體面授班");  //實體+未達人數網路教學
		nmHt.put("1" , "實體面授班");  //實體教室教學
		
		try
		{
			Vector gpVt = UtilityX.getVtGroupData(dbmanager, conn, sql.toString(),new String[]{"CRSNO"});
			for (int i = 0; i < gpVt.size(); i++) {
				Vector vt = (Vector) gpVt.get(i);
				String crsno = "";
				Vector vt1 = new Vector();
				for (int j = 0; j < vt.size(); j++) {
					Hashtable ht = (Hashtable) vt.get(j);
					crsno = Utility.nullToSpace(ht.get("CRSNO"));
					
					Hashtable ht1 = new Hashtable();
					ht1.put("value", Utility.nullToSpace(ht.get("PERVALUE")));
					ht1.put("name", Utility.nullToSpace(nmHt.get(Utility.nullToSpace(ht.get("TEACHING_TYPE")))));
					vt1.add(ht1);
				}
				seleHt.put(crsno, vt1);
			}	
		
	    } catch (Exception e) {
	    	throw e;
		}
		return seleHt;
    }
	
    /**
     * JOIN 其它 Cout001,Cout002,Cout003,Regt007 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception           *****採計學系
     */
    public Vector getCout001Cou002Cout003Regt007ForCou139r(Hashtable ht, DBManager dbm01, Connection conn01) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
            "SELECT FUCK.FACULTY_NAME AS PLAN_FACULTY_NAME, A.AYEAR , A.AYEAR||SUBSTR(Z6.CODE_NAME,1,1) AS CSMS, A.CRSNO, B.CRS_NAME AS CRS_NAME, A.CRD, Z7.CODE_NAME AS CRSSTATUS, Z1.CODE_NAME AS CRS_CHAR " +
            	", L2.CODE_NAME AS TEACHING_TYPE_NAME " +
            	", substr(L3.CODE_NAME,1,4) AS CRS_BOOK "+
            	", K.CODE_NAME AS PRODUCE_NAME "+
                ", ( " +
                "  SELECT WMSYS.WM_CONCAT(TO_CHAR(C1.CODE_NAME||'-'||B1.NAME)) " +
                "  FROM COUT003 A1 " +
                "  JOIN TRAT001 B1 ON B1.IDNO = A1.IDNO " +
                "  JOIN SYST001 C1 ON C1.KIND = 'JOB_TYPE' AND C1.CODE = A1.JOB_TYPE " +
                "  WHERE A1.AYEAR = A.AYEAR  AND A1.SMS = A.SMS AND A1.CRSNO= A.CRSNO " +
                "  AND A1.JOB_TYPE IN ('11','12') " +
                "  GROUP BY A1.CRSNO " +
                "  ) as cout003 " +
                ", ( " +
                "  SELECT WMSYS.WM_CONCAT(TO_CHAR(A2.AYEAR||A2.SMS||substr(b2.code_name,1,1)||'-'||TO_CHAR(COUNT(1)||'人'))) AS CNT " +
                "  FROM REGT007 A2 " +
                "  JOIN SYST001 B2 ON B2.KIND = 'SMS' AND B2.CODE = A2.SMS " +
                "  WHERE A2.AYEAR >= '" + Utility.nullToSpace(ht.get("AYEAR")) + "' " +
                "  AND A2.CRSNO=A.CRSNO AND A2.UNQUAL_TAKE_MK = 'N' AND A2.UNTAKECRS_MK = 'N' AND A2.PAYMENT_STATUS != '1' " +
                "  GROUP BY A2.AYEAR||A2.SMS||substr(b2.code_name,1,1) " +
                " ) as regayearsms " +
                ", ( " +
                "  SELECT TO_CHAR(COUNT(1)) AS TAL " +
                "  FROM REGT007 A3 " +
                "  WHERE A3.AYEAR >= '" + Utility.nullToSpace(ht.get("AYEAR")) + "' " +
                "  AND A3.CRSNO=A.CRSNO AND A3.UNQUAL_TAKE_MK = 'N' AND A3.UNTAKECRS_MK = 'N' AND A3.PAYMENT_STATUS != '1' " +
                "  group by a3.crsno " +
                ") as REGT007TAL " +
            "FROM COUT001 A " +
            "JOIN COUT002 B ON A.CRSNO=B.CRSNO " +
            "LEFT JOIN SYST003 Z2 ON Z2.FACULTY_CODE=A.FACULTY_CODE " +
            "LEFT JOIN SYST003 FUCK ON FUCK.FACULTY_CODE=A.PLAN_FACULTY_CODE " +
            "JOIN SYST001 Z1 ON A.CRS_CHAR=Z1.CODE AND Z1.KIND='CRS_CHAR' " +
            "LEFT JOIN SYST001 Z6 ON Z6.KIND='SMS' AND Z6.CODE=A.SMS " + 
            "LEFT JOIN SYST001 Z7 ON Z7.KIND='CRS_STATUS' AND Z7.CODE=A.CRS_STATUS "+ 
            "LEFT JOIN SYST001 L2 ON L2.KIND='TEACHING_TYPE_NAME' AND A.TEACHING_TYPE_NAME=L2.CODE " +
        	"LEFT JOIN SYST001 L3 ON L3.KIND='CRS_BOOK' AND A.CRS_BOOK=L3.CODE  " +
            "LEFT JOIN SYST001 K ON K.KIND = 'PRODUCE_CHOOSE' AND K.CODE = A.PRODUCE_TYPE  " +
            "WHERE A.NEW_REWORK = '1'  " +
            " "
            );

        
		if (!Utility.nullToSpace(ht.get("AYEAR")).equals("")){
			sql.append(" AND A.AYEAR >= '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		}
		if (!Utility.nullToSpace(ht.get("SMS")).equals("")){
			sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		}
		if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals("")){
			sql.append(" AND A.PLAN_FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
		}        
		if (!Utility.nullToSpace(ht.get("NOU_VOCATION_TYPE")).equals("")){
	        if (Utility.nullToSpace(ht.get("NOU_VOCATION_TYPE")).equals("1")){
	            sql.append(
	            		" AND A.CRSNO IN ( " +
	            		" SELECT A4.CRSNO " +
	            		"       FROM COUT003 A4 " +
	            		"       JOIN TRAT001 B4 ON B4.IDNO = A4.IDNO " +
	            		"       JOIN SYST001 C4 ON C4.KIND = 'JOB_TYPE' AND C4.CODE = A4.JOB_TYPE " +
	            		"       WHERE A4.AYEAR = A.AYEAR AND A4.SMS = A.SMS AND A4.CRSNO= A.CRSNO   AND A4.JOB_TYPE IN ('11','12') " +
	            		"		AND B4.NOU_VOCATION_TYPE = '1' " +
	            		"		) "
	            		);
	        }
	        if (Utility.nullToSpace(ht.get("NOU_VOCATION_TYPE")).equals("2")){
	        	sql.append(
	            		" AND A.CRSNO IN ( " +
	            		" SELECT A4.CRSNO " +
	            		"       FROM COUT003 A4 " +
	            		"       JOIN TRAT001 B4 ON B4.IDNO = A4.IDNO " +
	            		"       JOIN SYST001 C4 ON C4.KIND = 'JOB_TYPE' AND C4.CODE = A4.JOB_TYPE " +
	            		"       WHERE A4.AYEAR = A.AYEAR AND A4.SMS = A.SMS AND A4.CRSNO= A.CRSNO   AND A4.JOB_TYPE IN ('11','12') " +
	            		"		AND B4.NOU_VOCATION_TYPE != '1' " +
	            		"		) "	
	            		);
	        }
		}
        if (!Utility.nullToSpace(ht.get("TCHIDNO")).equals("")){
            sql.append(
            		" AND A.CRSNO IN ( " +
            		" SELECT A4.CRSNO " +
            		"       FROM COUT003 A4 " +
            		"       JOIN TRAT001 B4 ON B4.IDNO = A4.IDNO " +
            		"       JOIN SYST001 C4 ON C4.KIND = 'JOB_TYPE' AND C4.CODE = A4.JOB_TYPE " +
            		"       WHERE A4.AYEAR = A.AYEAR AND A4.SMS = A.SMS AND A4.CRSNO= A.CRSNO   AND A4.JOB_TYPE IN ('11','12') " +
            		"		AND B4.NOU_VOCATION_TYPE = '1' " +
            		"		AND B4.IDNO = '" + Utility.nullToSpace(ht.get("TCHIDNO")) + "' " +
            		"		) "
            		);
        }

        sql.append("ORDER BY A.AYEAR, A.SMS, A.CRSNO ");

        DBResult	rs	= null;		
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable	rowHt	= null;
            while (rs.next())
            {
                rowHt	= new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					rowHt.put(rs.getColumnName(i), rs.getString(i));
                }
                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }
        return result;
    }
    
    /**
     * JOIN 其它 Cout001,Cout002,Cout003,Regt007 將欄位中文化的值抓出來
     * @Hashtable ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception           *****採計學系
     */
    public Vector getCout001Cou002Cout003Regt007ForCou139r_Export(Hashtable ht) throws Exception
    {
        Vector	result	= new Vector();

        if (sql.length() > 0)
        {
            sql.delete(0, sql.length());
        }

        sql.append(
        		"SELECT FUCK.FACULTY_NAME AS PLAN_FACULTY_NAME, A.AYEAR , A.AYEAR||SUBSTR(Z6.CODE_NAME,1,1) AS CSMS, A.CRSNO, B.CRS_NAME AS CRS_NAME, A.CRD, Z7.CODE_NAME AS CRSSTATUS, Z1.CODE_NAME AS CRS_CHAR " +
                    	", L2.CODE_NAME AS TEACHING_TYPE_NAME " +
                    	", substr(L3.CODE_NAME,1,4) AS CRS_BOOK "+
                    	", K.CODE_NAME AS PRODUCE_NAME "+
                        ", ( " +
                        "  SELECT WMSYS.WM_CONCAT(TO_CHAR(C1.CODE_NAME||'-'||B1.NAME)) " +
                        "  FROM COUT003 A1 " +
                        "  JOIN TRAT001 B1 ON B1.IDNO = A1.IDNO " +
                        "  JOIN SYST001 C1 ON C1.KIND = 'JOB_TYPE' AND C1.CODE = A1.JOB_TYPE " +
                        "  WHERE A1.AYEAR = A.AYEAR  AND A1.SMS = A.SMS AND A1.CRSNO= A.CRSNO " +
                        "  AND A1.JOB_TYPE IN ('11','12') " +
                        "  GROUP BY A1.CRSNO " +
                        "  ) as cout003 " +
                        ", ( " +
                        "  SELECT WMSYS.WM_CONCAT(TO_CHAR(A2.AYEAR||A2.SMS||substr(b2.code_name,1,1)||'-'||TO_CHAR(COUNT(1))||'人')) AS CNT " +
                        "  FROM REGT007 A2 " +
                        "  JOIN SYST001 B2 ON B2.KIND = 'SMS' AND B2.CODE = A2.SMS " +
                        "  WHERE A2.AYEAR >= '" + Utility.nullToSpace(ht.get("AYEAR")) + "' " +
                        "  AND A2.CRSNO=A.CRSNO AND A2.UNQUAL_TAKE_MK = 'N' AND A2.UNTAKECRS_MK = 'N' AND A2.PAYMENT_STATUS != '1' " +
                        "  GROUP BY A2.AYEAR||A2.SMS||substr(b2.code_name,1,1) " +
                        " ) as regayearsms " +
                        ", ( " +
                        "  SELECT TO_CHAR(COUNT(1)) AS TAL " +
                        "  FROM REGT007 A3 " +
                        "  WHERE A3.AYEAR >= '" + Utility.nullToSpace(ht.get("AYEAR")) + "' " +
                        "  AND A3.CRSNO=A.CRSNO AND A3.UNQUAL_TAKE_MK = 'N' AND A3.UNTAKECRS_MK = 'N' AND A3.PAYMENT_STATUS != '1' " +
                        "  group by a3.crsno " +
                        ") as REGT007TAL " +
                    "FROM COUT001 A " +
                    "JOIN COUT002 B ON A.CRSNO=B.CRSNO " +
                    "LEFT JOIN SYST003 Z2 ON Z2.FACULTY_CODE=A.FACULTY_CODE " +
                    "LEFT JOIN SYST003 FUCK ON FUCK.FACULTY_CODE=A.PLAN_FACULTY_CODE " +
                    "JOIN SYST001 Z1 ON A.CRS_CHAR=Z1.CODE AND Z1.KIND='CRS_CHAR' " +
                    "LEFT JOIN SYST001 Z6 ON Z6.KIND='SMS' AND Z6.CODE=A.SMS " + 
                    "LEFT JOIN SYST001 Z7 ON Z7.KIND='CRS_STATUS' AND Z7.CODE=A.CRS_STATUS "+ 
                    "LEFT JOIN SYST001 L2 ON L2.KIND='TEACHING_TYPE_NAME' AND A.TEACHING_TYPE_NAME=L2.CODE " +
                	"LEFT JOIN SYST001 L3 ON L3.KIND='CRS_BOOK' AND A.CRS_BOOK=L3.CODE  " +
                    "LEFT JOIN SYST001 K ON K.KIND = 'PRODUCE_CHOOSE' AND K.CODE = A.PRODUCE_TYPE  " +
                    "WHERE A.NEW_REWORK = '1'  " +
                    " "
                    );
        
        if (!Utility.nullToSpace(ht.get("AYEAR")).equals("")){
			sql.append(" AND A.AYEAR >= '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
		}
		if (!Utility.nullToSpace(ht.get("SMS")).equals("")){
			sql.append(" AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
		}
		if (!Utility.nullToSpace(ht.get("FACULTY_CODE")).equals("")){
			sql.append(" AND A.PLAN_FACULTY_CODE = '" + Utility.nullToSpace(ht.get("FACULTY_CODE")) + "' ");
		}  
		if (!Utility.nullToSpace(ht.get("NOU_VOCATION_TYPE")).equals("")){
	        if (Utility.nullToSpace(ht.get("NOU_VOCATION_TYPE")).equals("1")){
	            sql.append(
	            		" AND A.CRSNO IN ( " +
	            		" SELECT A4.CRSNO " +
	            		"       FROM COUT003 A4 " +
	            		"       JOIN TRAT001 B4 ON B4.IDNO = A4.IDNO " +
	            		"       JOIN SYST001 C4 ON C4.KIND = 'JOB_TYPE' AND C4.CODE = A4.JOB_TYPE " +
	            		"       WHERE A4.AYEAR = A.AYEAR AND A4.SMS = A.SMS AND A4.CRSNO= A.CRSNO   AND A4.JOB_TYPE IN ('11','12') " +
	            		"		AND B4.NOU_VOCATION_TYPE = '1' " +
	            		"		) "
	            		);
	        }
	        if (Utility.nullToSpace(ht.get("NOU_VOCATION_TYPE")).equals("2")){
	        	sql.append(
	            		" AND A.CRSNO IN ( " +
	            		" SELECT A4.CRSNO " +
	            		"       FROM COUT003 A4 " +
	            		"       JOIN TRAT001 B4 ON B4.IDNO = A4.IDNO " +
	            		"       JOIN SYST001 C4 ON C4.KIND = 'JOB_TYPE' AND C4.CODE = A4.JOB_TYPE " +
	            		"       WHERE A4.AYEAR = A.AYEAR AND A4.SMS = A.SMS AND A4.CRSNO= A.CRSNO   AND A4.JOB_TYPE IN ('11','12') " +
	            		"		AND B4.NOU_VOCATION_TYPE != '1' " +
	            		"		) "	
	            		);
	        }
		}
		if (!Utility.nullToSpace(ht.get("TCHIDNO")).equals("")){
            sql.append(
            		" AND A.CRSNO IN ( " +
            		" SELECT A4.CRSNO " +
            		"       FROM COUT003 A4 " +
            		"       JOIN TRAT001 B4 ON B4.IDNO = A4.IDNO " +
            		"       JOIN SYST001 C4 ON C4.KIND = 'JOB_TYPE' AND C4.CODE = A4.JOB_TYPE " +
            		"       WHERE A4.AYEAR = A.AYEAR AND A4.SMS = A.SMS AND A4.CRSNO= A.CRSNO   AND A4.JOB_TYPE IN ('11','12') " +
            		"		AND B4.NOU_VOCATION_TYPE = '1' " +
            		"		AND B4.IDNO = '" + Utility.nullToSpace(ht.get("TCHIDNO")) + "' " +
            		"		) "
            		);
        }
        sql.append("ORDER BY A.AYEAR, A.SMS, A.CRSNO ");

        DBResult	rs	= null;		
        try
        {
            if (pageQuery)
            {
                // 依分頁取出資料
                rs	= Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            }
            else
            {
                // 取出所有資料
                rs	= dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable	rowHt	= null;
            while (rs.next())
            {
                rowHt	= new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                {
					rowHt.put(rs.getColumnName(i), rs.getString(i));
                }
                result.add(rowHt);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }
        return result;
    }    
	
}