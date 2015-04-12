package hbut.hgdonline.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlHelper extends SQLiteOpenHelper{
	//数据库名称
	public final static String DATA_BASE_NAME = "mylibrary";
	public final static int DATA_BASE_VERSION = 1;
	//表名
	public final static String TABLE_NAME = "mylib";
	//字段名称
	public final static String BOOK_NAME = "book_name";  //书本名称
	public final static String EDITOR_AND_PUBLIC = "editor_and_public";  //主编，出版社
	public final static String BOOK_ID = "book_id"; //索书号
	public final static String BARCODE = "barcode"; //条码号
	public final static String PLACE = "place"; //馆藏地
	public final static String CAN_BORROW_NUM = "can_borrow_num"; //可借副本

	public MySqlHelper(Context context) {
		super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String createTableSql = "create table if not exists " +
				TABLE_NAME+
				"(_id integer primary key autoincrement," +
				BOOK_NAME + " text," +
				EDITOR_AND_PUBLIC + " text," +
				BOOK_ID + " text," +
				BARCODE + " text," +
				PLACE + " text," +
				CAN_BORROW_NUM + " text)";
		db.execSQL(createTableSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String dropTable = "drop table if exists "+TABLE_NAME;
		db.execSQL(dropTable);
	}

}
