package hbut.hgdonline.net;

import hbut.hgdonline.entity.Book;
import hbut.hgdonline.entity.MyURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SelectCanBorrow implements Runnable{
	
	private String name;
	private List<Book> books = null;
	private Handler handler = null;
	private boolean isSelectAll = true;
	private Context context = null;
	

	public SelectCanBorrow(String name,Handler handler,Context context) {
		this.name = name;
		this.handler = handler;
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			books = new ArrayList<Book>();
			Document doc =null;
			if(isSelectAll){//查询所有的书
				doc  = Jsoup.connect(MyURL.selectAllURL(getSchoolNum())).data("strText",name).data("displaypg", getLimitNum()).timeout(20000).get();
			}else{//查询可借的书
				doc  = Jsoup.connect(MyURL.selectKejieURL(getSchoolNum())).data("title",name).data("displaypg", getLimitNum()).timeout(20000).get();
			}
			Element bookList = doc.getElementById("search_book_list");
			if(bookList == null){
				handler.sendEmptyMessage(2);//表示查询的数据为空
			}else{
				Elements bookNames = bookList.getElementsByClass("book_list_info");
				for(int i = 0;i < bookNames.size(); i++){
					Book book = new Book();
					Elements names = bookNames.get(i).getElementsByTag("a");
					String num = names.get(0).attr("href").split("=")[1];
					String str = bookNames.get(i).getElementsByTag("p").get(0).text();
					String[] strs = str.split(" ");
					String kejie = strs[0] +" <font color='red'>" + strs[1]+"</font>";
					StringBuilder publish = new StringBuilder();
					for(int x = 2;x<strs.length - 2;x++){
						publish.append(strs[x]).append(" ");
					}
					book.setEditorAndPublic(publish.toString());
					book.setCanBorrowNum(kejie);
					book.setName(names.get(0).text());
					setBookDetail(num,book);
					books.add(book);
				}
				handler.sendEmptyMessage(0);//表示查询的数据为空
			}
		} catch (IOException e) {
			handler.sendEmptyMessage(1);//表示网络或者查询出错
			e.printStackTrace();
		}
	}
	//得到返回数据
	public List<Book> getCanBorrowBook(){
		return books;
	}
	
	//开始联网获得数据
	public void startThread(boolean isSelectAll){
		this.isSelectAll = isSelectAll;
		new Thread(this).start();
	}
	
	//得到book的详细信息
	private void setBookDetail(String num, Book book ){
		try{
			Document doc = Jsoup.connect(MyURL.selectDetail(num, getSchoolNum())).timeout(1000).get();
			Elements details = doc.getElementsByClass("whitetext");
			Elements elements = details.get(0).getElementsByTag("td");
			book.setBookId(elements.get(0).text());
			book.setBarcode(elements.get(1).text());
			book.setPlace(elements.get(3).text());
			ArrayList<HashMap<String,String>> placeDetail = new ArrayList<HashMap<String,String>>();
			for(int i = 0;i <details.size();i++){
				HashMap<String,String> map = new HashMap<String,String>();
				Elements els = details.get(i).getElementsByTag("td");
				map.put("place",els.get(3).text());
				map.put("status", els.get(4).text());
				placeDetail.add(map);
			}
			book.setPlaceDetail(placeDetail);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * 在这里读取用户设置的学校代码
	 * @return
	 */
	private int getSchoolNum(){
		SharedPreferences prefrence = PreferenceManager.getDefaultSharedPreferences(context);
		String school = prefrence.getString("school","1");
		int sch = 1;
		try{
			sch = Integer.parseInt(school);
		}catch(Exception e){
			sch = 1;
			e.printStackTrace();
		}
		return sch;
	}
	private String getLimitNum(){
		SharedPreferences prefrence = PreferenceManager.getDefaultSharedPreferences(context);
		String num = prefrence.getString("limit", "20");
		return num;
	}

}
