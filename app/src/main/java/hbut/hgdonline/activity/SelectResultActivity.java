package hbut.hgdonline.activity;

import hbut.hgdonline.entity.Book;
import hbut.hgdonline.net.SelectCanBorrow;
import hbut.hgdonline.sqlite.OperateData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.Toast;

import com.hbut.library.BookDetailActivity;
import com.hbut.library.R;
import com.hbut.library.SettingsActivity;

public class SelectResultActivity extends Activity{
	
	private SelectCanBorrow selectBook;
	private ProgressDialog proDialog;
	private ListView listSelectedBook;
	private boolean isSelectAll = false;
	private ListView listMyStoredBook;
	
	@SuppressLint("HandlerLeak")
	private final Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case 0 ://查询成功
				List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
				if(selectBook != null){
					List<Book> books = selectBook.getCanBorrowBook();
					if(books != null && books.size()>0){
						for(Book book : books){
							HashMap<String,String> map = new HashMap<String, String>();
							map.put("name", "<b>" + book.getName()+"</b><font color='red'>"+book.getBookId()+"</font>");
							map.put("place", book.getPlace()+" "+book.getCanBorrowNum());
							map.put("author", book.getEditorAndPublic());
							data.add(map);
						}
						books = null;
					}
					listSelectedBook.setAdapter(new ListBookAdapter(SelectResultActivity.this, data));
				}else{
					Toast.makeText(SelectResultActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
				}
				break;
			case 1 ://网络异常
				Toast.makeText(SelectResultActivity.this, "网络异常或查询出错", Toast.LENGTH_SHORT).show();
				break;
			case 2://查询了，但是没有数据
				Toast.makeText(SelectResultActivity.this, "没有数据查询失败", Toast.LENGTH_SHORT).show();
				break;
			}
			proDialog.dismiss();
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_result);
		listSelectedBook = (ListView) this.findViewById(R.id.list_books);
		//给listSelectedBook 设置单击响应，显示书本详细信息
		listSelectedBook.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int positon,
					long arg3) {
				// TODO Auto-generated method stub
				if(selectBook != null){
					List<Book> books = selectBook.getCanBorrowBook();
					Intent intent = new Intent(SelectResultActivity.this, BookDetailActivity.class);
					intent.putExtra("book", books.get(positon));
					startActivity(intent);
				}
			}
		});
		//设置spinner用来选择是查询全部的还是查询可借的。
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		String[] selectOption = {"可借","全部"};
		ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,selectOption);
		adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setFocusable(true);
		spinner.setAdapter(adap);
		spinner.setVisibility(View.VISIBLE);
		spinner.setSelection(0);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch(arg2){
				case 0:isSelectAll = false;break;
				case 1:isSelectAll = true;break;
				default:isSelectAll = false;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		//设置TabHost来
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("")
				.setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2")
				.setIndicator("")
				.setContent(R.id.tab2));
		tabHost.getCurrentTabView().setBackgroundResource(R.drawable.store_2);
		tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.hart_1);
		final EditText editName = (EditText)findViewById(R.id.edit_book_name);
		//得到按钮，并且给按钮添加响应，点击按钮查询
		Button buttonSearch = (Button) findViewById(R.id.button_search);
		buttonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = editName.getText().toString().trim();
				if(name.equals("")){
					Toast.makeText(SelectResultActivity.this, "名称不能为空", Toast.LENGTH_SHORT).show();
				}else{
					//开启新线程去网络拿数据
					selectBook = new SelectCanBorrow(name,handler,SelectResultActivity.this);
					selectBook.startThread(isSelectAll);
					
					//启动提醒用户等待的对话框
					proDialog = ProgressDialog.show(SelectResultActivity.this,"数据加载中","玩命加载中，请稍后。。。");
					proDialog.setCancelable(true);
				}
			}
		});
		listMyStoredBook = (ListView) findViewById(R.id.list_my_stored_book);
		//给tabHost设置切换监听
		//获取TabWeiget对象
		final TabWidget tw = tabHost.getTabWidget();
		//获取TabWidget的Tab数量
		final int tabSize = tw.getChildCount();
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				//如果是跳转到收藏页面
				if(tabId.equals("tab2")){
					OperateData oper = new OperateData(SelectResultActivity.this);
					List<Book> list = oper.selectData();
					oper.closeDB();
					List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
					for(int i =0;i<list.size();i++){
						HashMap<String,String> map = new HashMap<String, String>();
						map.put("name", "<b>" + (i+1) + ". " + list.get(i).getName().substring(2)+"</b><font color='red'>"+list.get(i).getBookId()+"</font>");
						map.put("place", list.get(i).getPlace()+" "+list.get(i).getCanBorrowNum());
						map.put("author", list.get(i).getEditorAndPublic());
						data.add(map);
					}
					updateListView(data);
					//跟换背景
					for(int i = 0;i<tabSize;i++){
						View v = tw.getChildAt(i);
						if(i==1){
							v.setBackgroundResource(R.drawable.hart_2);
						}else{
							v.setBackgroundResource(R.drawable.store_1);
						}
					}
				}else{
					for(int i = 0;i<tabSize;i++){
						View v = tw.getChildAt(i);
						if(i == 0){
							v.setBackgroundResource(R.drawable.store_2);
						}else{
							v.setBackgroundResource(R.drawable.hart_1);
						}
					}
				}
			}
		});
		
		//给搜索ListView添加长按，长按收藏
		this.registerForContextMenu(listSelectedBook);
		//给收藏ListView添加ContextMenu
		this.registerForContextMenu(listMyStoredBook);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.select_result, menu);
		menu.add(Menu.NONE,1,1,"设置");
		menu.add(Menu.NONE,2,2,"退出");
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//获得是第几组按钮，从而区分是收藏还是删除
		int groupId = item.getGroupId();
		AdapterView.AdapterContextMenuInfo info ;
		try{
			info = (AdapterContextMenuInfo) item.getMenuInfo();
		}catch(ClassCastException e){
			e.printStackTrace();
			return false;
		}
		switch(groupId){
		case 0://收藏选中的book//表示是第0组按钮，就是只有收藏
			if(selectBook != null){
				List<Book> books = selectBook.getCanBorrowBook();
				Book book = books.get(info.position);
				OperateData operate = new OperateData(SelectResultActivity.this);
				Long judge = operate.insertData(book);
				if(judge == -1){
					Toast.makeText(SelectResultActivity.this, "收藏失败！", Toast.LENGTH_SHORT).show();
				}else if(judge == -3){
					Toast.makeText(SelectResultActivity.this, "数据已存在！", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(SelectResultActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
				}
				operate.closeDB();
				operate = null;
				books = null;
				book = null;
			}else{
				Toast.makeText(SelectResultActivity.this, "收藏失败！", Toast.LENGTH_SHORT).show();
			}
			break;
		case 1://表示是删除被选中
			OperateData oper = new OperateData(SelectResultActivity.this);
			List<Book> books = oper.selectData();
			if(oper.deleteData(books.get(info.position)) > 0){
				books.remove(info.position);
				//用来刷新listMyStoredBook
				List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
				for(int i =0;i<books.size();i++){
					HashMap<String,String> map = new HashMap<String, String>();
					map.put("name", "<b>" + (i+1) + ". " + books.get(i).getName().substring(2)+"</b><font color='red'>"+books.get(i).getBookId()+"</font>");
					map.put("place", books.get(i).getPlace()+" "+books.get(i).getCanBorrowNum());
					map.put("author", books.get(i).getEditorAndPublic());
					data.add(map);
				}
				//在这里更新ListView
				updateListView(data);
				Toast.makeText(SelectResultActivity.this, "删除成功 ！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(SelectResultActivity.this, "删除失败 ！", Toast.LENGTH_SHORT).show();
			}
			oper.closeDB();
			break;
		default:break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		if(v.getId() == listSelectedBook.getId()){
			menu.add(0,1,0,"收藏");
		}else{
			menu.add(1,0,0,"删除");
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	//更新ListView数据
	private void updateListView(List<HashMap<String, String>> data){
		listMyStoredBook.setAdapter(new ListBookAdapter(SelectResultActivity.this, data));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case 1://设置
			Intent intent = new Intent(SelectResultActivity.this, SettingsActivity.class);
			startActivity(intent);
			break;
		case 2://退出
			AlertDialog.Builder builder = new AlertDialog.Builder(SelectResultActivity.this);
			builder.setTitle("退出");
			builder.setMessage("确定退出？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			builder.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
