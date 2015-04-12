package hbut.hgdonline.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Book implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name;  //书本名称
	private String editorAndPublic;  //主编，出版社
	private String bookId; //索书号
	private String barcode; //条码号
	private String place; //馆藏地
	private String canBorrowNum; //可借副本
	private ArrayList<HashMap<String,String>> placeDetail; //馆藏地可借本数详细信息
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getEditorAndPublic() {
		return editorAndPublic;
	}
	public void setEditorAndPublic(String editorAndPublic) {
		this.editorAndPublic = editorAndPublic;
	}
	public String getCanBorrowNum() {
		return canBorrowNum;
	}
	public void setCanBorrowNum(String canBorrowNum) {
		this.canBorrowNum = canBorrowNum;
	}
	public ArrayList<HashMap<String, String>> getPlaceDetail() {
		return placeDetail;
	}
	public void setPlaceDetail(ArrayList<HashMap<String, String>> placeDetail) {
		this.placeDetail = placeDetail;
	}
	
	

}
