package com.hbut.library;

import hbut.hgdonline.entity.Book;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BookDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_book_detail);
		Intent intent = getIntent();
		Book book = (Book) intent.getSerializableExtra("book");
		ListView listView = (ListView)findViewById(R.id.list_book_detail);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		adapter.add(book.getName().substring(1));
		adapter.add(book.getBookId());
		String str = book.getCanBorrowNum().replaceAll("<(.|\n)*?>","");
		adapter.add(str);
		for(HashMap<String, String> map : book.getPlaceDetail()){
			adapter.add(map.get("place")+"  : "+map.get("status"));
		}
		adapter.add(book.getEditorAndPublic());
		listView.setAdapter( adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.book_detail, menu);
		return false;
	}

}
