package hbut.hgdonline.entity;

public class MyURL {
	/**
	 *学校代号：
	 *1.代表湖北工业大学
	 *2.代表武汉科技大学
	 *3.代表武汉工程大学
	 */
	public final static int HBUT = 1;//湖北工业大学
	public final static int WUST = 2;//武汉科技大学
	public final static int WIT = 3;//武汉工程大学
	
	/**
	 * 湖北工业大学图书馆
	 */
	//检索全部数目
	private final static String ALL_URL_HBUT = "http://202.114.181.3:8080/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&doctype=ALL&showmode=list&sort=CATA_DATE&orderby=desc&location=ALL";
	//可借书目
	private final static String KEJIE_URL_HBUT = "http://202.114.181.3:8080/opac/openlink.php?location=ALL&doctype=ALL&lang_code=ALL&match_flag=forward&showmode=list&orderby=DESC&sort=CATA_DATE&onlylendable=yes&with_ebook=&with_ebook=";
	//查询详细信息
	private final static String BOOK_DETAIL_HBUT = "http://202.114.181.3:8080/opac/ajax_item.php?marc_no=";
	
	/**
	 * 武汉科技大学图书馆的链接
	 * @return
	 */
	//查询所有的书
	private final static String ALL_URL_WUST = "http://opac.lib.wust.edu.cn:8080/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&doctype=ALL&showmode=list&sort=CATA_DATE&orderby=desc&dept=ALL";
	//查询可借图书
	private final static String KEJIE_URL_WUST = "http://opac.lib.wust.edu.cn:8080/opac/openlink.php?dept=ALL&doctype=ALL&lang_code=ALL&match_flag=forward&showmode=list&orderby=DESC&sort=CATA_DATE&onlylendable=yes&with_ebook=&with_ebook=";
	//查询地点及详细信息
	private final static String BOOK_DETAIL_WUST = "http://opac.lib.wust.edu.cn:8080/opac/item.php?marc_no=";
	/**
	 * 武汉工程大学
	 * @return
	 */
	//所有的书目
	private final static String ALL_URL_WIT = "http://218.199.187.110/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&doctype=ALL&showmode=list&sort=CATA_DATE&orderby=desc&dept=ALL";
	//可借数目
	private final static String KEJIE_URL_WIT = "http://218.199.187.110/opac/openlink.php?dept=ALL&doctype=ALL&lang_code=ALL&match_flag=forward&showmode=list&orderby=DESC&sort=CATA_DATE&onlylendable=yes&with_ebook=&with_ebook=";
	//查询地点及详细信息
	private final static String BOOK_DETAIL_WIT = "http://218.199.187.110/opac/item.php?marc_no=";
	
	//得到查询全部书的链接
	public static String selectAllURL(int n){
		switch(n){
		case HBUT:return ALL_URL_HBUT;
		case WUST:return ALL_URL_WUST;
		case WIT:return ALL_URL_WIT;
		default :return ALL_URL_HBUT;
		}
	}
	
	//得到查询可借书的链接
	public static String selectKejieURL(int n){
		switch(n){
		case HBUT:return KEJIE_URL_HBUT;
		case WUST:return KEJIE_URL_WUST;
		case WIT:return KEJIE_URL_WIT;
		default :return KEJIE_URL_HBUT;
		}
	}
	
	//查询详细信息
	public static String selectDetail(String bookNum,int n){
		switch(n){
		case HBUT:return BOOK_DETAIL_HBUT + bookNum;
		case WUST:return BOOK_DETAIL_WUST + bookNum;
		case WIT:return BOOK_DETAIL_WIT + bookNum;
		default :return BOOK_DETAIL_HBUT + bookNum;
		}
	}

}
