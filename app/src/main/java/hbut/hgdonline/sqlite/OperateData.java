package hbut.hgdonline.sqlite;

import hbut.hgdonline.entity.Book;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OperateData {
	
	private SQLiteDatabase db = null;
	private MySqlHelper  myHelper = null;
	
	public OperateData(Context context){
		myHelper = new MySqlHelper(context);
	}
	
	//插入数据
	public Long insertData(Book book){
		if(book.getBarcode() != null){
			if(findByBarcode(book.getBarcode())){
				return (long) -3;//表示数据已经存在了
			}
		}
		if(myHelper != null){
			try{
				db = myHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				if(book.getBarcode() != null){
					values.put(MySqlHelper.BARCODE, book.getBarcode());
				}
				if(book.getBookId() != null){
					values.put(MySqlHelper.BOOK_ID, book.getBookId());
				}
				if(book.getCanBorrowNum() != null){
					values.put(MySqlHelper.CAN_BORROW_NUM, book.getCanBorrowNum());
				}
				if(book.getEditorAndPublic() != null){
					values.put(MySqlHelper.EDITOR_AND_PUBLIC, book.getEditorAndPublic());
				}
				if(book.getName() != null){
					values.put(MySqlHelper.BOOK_NAME, book.getName());
				}
				if(book.getPlace() != null){
					values.put(MySqlHelper.PLACE, book.getPlace());
				}
				return db.insert(MySqlHelper.TABLE_NAME, null, values);
			}catch(Exception e){
				e.printStackTrace();
				return (long) -1;
			}
			
		}
		return (long) -1;
	}
	
	public List<Book> selectData(){
		List<Book> list =new ArrayList<Book>();
		String[] columns = {
				MySqlHelper.BARCODE,
				MySqlHelper.BOOK_ID,
				MySqlHelper.BOOK_NAME,
				MySqlHelper.CAN_BORROW_NUM,
				MySqlHelper.EDITOR_AND_PUBLIC,
				MySqlHelper.PLACE
		};
		String orderBy = "_id desc";
		try{
			db = myHelper.getReadableDatabase();
			Cursor cursor = db.query(MySqlHelper.TABLE_NAME, columns, null, null, null, null, orderBy);
			int bookName = cursor.getColumnIndex(MySqlHelper.BOOK_NAME);
			int bookId = cursor.getColumnIndex(MySqlHelper.BOOK_ID);
			int place = cursor.getColumnIndex(MySqlHelper.PLACE);
			int editor = cursor.getColumnIndex(MySqlHelper.EDITOR_AND_PUBLIC);
			int canBorrowNum = cursor.getColumnIndex(MySqlHelper.CAN_BORROW_NUM);
			int barcode = cursor.getColumnIndex(MySqlHelper.BARCODE);
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				Book book = new Book();
				book.setName(cursor.getString(bookName));
				book.setBarcode(cursor.getString(barcode));
				book.setBookId(cursor.getString(bookId));
				book.setPlace(cursor.getString(place));
				book.setEditorAndPublic(cursor.getString(editor));
				book.setCanBorrowNum(cursor.getString(canBorrowNum));
				cursor.moveToNext();
				list.add(book);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	//删除数据
	public int deleteData(Book book){
		try{
			db = myHelper.getWritableDatabase();
			if(book.getBarcode() != null){
				String whereClause = MySqlHelper.BARCODE + "=?";
				String[] whereArgs = {book.getBarcode()};
				return db.delete(MySqlHelper.TABLE_NAME, whereClause, whereArgs);
			}else if(book.getName() != null){
				String whereClause = MySqlHelper.BOOK_NAME + "=?";
				String[] whereArgs = {book.getName()};
				return db.delete(MySqlHelper.TABLE_NAME, whereClause, whereArgs);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	//根据barcode 来查找数据
	public boolean findByBarcode(String barcode){
		String selection = MySqlHelper.BARCODE + "= ?";
		String[] selectionArgs = {barcode};
		try{
			db = myHelper.getReadableDatabase();
			Cursor cursor = db.query( MySqlHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null);
			if(cursor.moveToFirst()){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	//关闭数据库
	public void closeDB(){
		if(db != null){
			if(db.isOpen()){
				db.close();
			}
			db = null;
		}
	}

}
