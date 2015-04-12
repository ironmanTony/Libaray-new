package hbut.hgdonline.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Book implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name;  //�鱾����
	private String editorAndPublic;  //���࣬������
	private String bookId; //�����
	private String barcode; //�����
	private String place; //�ݲص�
	private String canBorrowNum; //�ɽ踱��
	private ArrayList<HashMap<String,String>> placeDetail; //�ݲصؿɽ豾����ϸ��Ϣ
	
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
