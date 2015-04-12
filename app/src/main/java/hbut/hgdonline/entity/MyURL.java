package hbut.hgdonline.entity;

public class MyURL {
	/**
	 *ѧУ���ţ�
	 *1.���������ҵ��ѧ
	 *2.�����人�Ƽ���ѧ
	 *3.�����人���̴�ѧ
	 */
	public final static int HBUT = 1;//������ҵ��ѧ
	public final static int WUST = 2;//�人�Ƽ���ѧ
	public final static int WIT = 3;//�人���̴�ѧ
	
	/**
	 * ������ҵ��ѧͼ���
	 */
	//����ȫ����Ŀ
	private final static String ALL_URL_HBUT = "http://202.114.181.3:8080/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&doctype=ALL&showmode=list&sort=CATA_DATE&orderby=desc&location=ALL";
	//�ɽ���Ŀ
	private final static String KEJIE_URL_HBUT = "http://202.114.181.3:8080/opac/openlink.php?location=ALL&doctype=ALL&lang_code=ALL&match_flag=forward&showmode=list&orderby=DESC&sort=CATA_DATE&onlylendable=yes&with_ebook=&with_ebook=";
	//��ѯ��ϸ��Ϣ
	private final static String BOOK_DETAIL_HBUT = "http://202.114.181.3:8080/opac/ajax_item.php?marc_no=";
	
	/**
	 * �人�Ƽ���ѧͼ��ݵ�����
	 * @return
	 */
	//��ѯ���е���
	private final static String ALL_URL_WUST = "http://opac.lib.wust.edu.cn:8080/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&doctype=ALL&showmode=list&sort=CATA_DATE&orderby=desc&dept=ALL";
	//��ѯ�ɽ�ͼ��
	private final static String KEJIE_URL_WUST = "http://opac.lib.wust.edu.cn:8080/opac/openlink.php?dept=ALL&doctype=ALL&lang_code=ALL&match_flag=forward&showmode=list&orderby=DESC&sort=CATA_DATE&onlylendable=yes&with_ebook=&with_ebook=";
	//��ѯ�ص㼰��ϸ��Ϣ
	private final static String BOOK_DETAIL_WUST = "http://opac.lib.wust.edu.cn:8080/opac/item.php?marc_no=";
	/**
	 * �人���̴�ѧ
	 * @return
	 */
	//���е���Ŀ
	private final static String ALL_URL_WIT = "http://218.199.187.110/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&doctype=ALL&showmode=list&sort=CATA_DATE&orderby=desc&dept=ALL";
	//�ɽ���Ŀ
	private final static String KEJIE_URL_WIT = "http://218.199.187.110/opac/openlink.php?dept=ALL&doctype=ALL&lang_code=ALL&match_flag=forward&showmode=list&orderby=DESC&sort=CATA_DATE&onlylendable=yes&with_ebook=&with_ebook=";
	//��ѯ�ص㼰��ϸ��Ϣ
	private final static String BOOK_DETAIL_WIT = "http://218.199.187.110/opac/item.php?marc_no=";
	
	//�õ���ѯȫ���������
	public static String selectAllURL(int n){
		switch(n){
		case HBUT:return ALL_URL_HBUT;
		case WUST:return ALL_URL_WUST;
		case WIT:return ALL_URL_WIT;
		default :return ALL_URL_HBUT;
		}
	}
	
	//�õ���ѯ�ɽ��������
	public static String selectKejieURL(int n){
		switch(n){
		case HBUT:return KEJIE_URL_HBUT;
		case WUST:return KEJIE_URL_WUST;
		case WIT:return KEJIE_URL_WIT;
		default :return KEJIE_URL_HBUT;
		}
	}
	
	//��ѯ��ϸ��Ϣ
	public static String selectDetail(String bookNum,int n){
		switch(n){
		case HBUT:return BOOK_DETAIL_HBUT + bookNum;
		case WUST:return BOOK_DETAIL_WUST + bookNum;
		case WIT:return BOOK_DETAIL_WIT + bookNum;
		default :return BOOK_DETAIL_HBUT + bookNum;
		}
	}

}
