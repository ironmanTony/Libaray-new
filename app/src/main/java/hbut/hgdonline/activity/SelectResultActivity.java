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
			case 0 ://��ѯ�ɹ�
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
					Toast.makeText(SelectResultActivity.this, "û������", Toast.LENGTH_SHORT).show();
				}
				break;
			case 1 ://�����쳣
				Toast.makeText(SelectResultActivity.this, "�����쳣���ѯ����", Toast.LENGTH_SHORT).show();
				break;
			case 2://��ѯ�ˣ�����û������
				Toast.makeText(SelectResultActivity.this, "û�����ݲ�ѯʧ��", Toast.LENGTH_SHORT).show();
				break;
			}
			proDialog.dismiss();
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���ر�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_result);
		listSelectedBook = (ListView) this.findViewById(R.id.list_books);
		//��listSelectedBook ���õ�����Ӧ����ʾ�鱾��ϸ��Ϣ
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
		//����spinner����ѡ���ǲ�ѯȫ���Ļ��ǲ�ѯ�ɽ�ġ�
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		String[] selectOption = {"�ɽ�","ȫ��"};
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
		
		//����TabHost��
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
		//�õ���ť�����Ҹ���ť�����Ӧ�������ť��ѯ
		Button buttonSearch = (Button) findViewById(R.id.button_search);
		buttonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = editName.getText().toString().trim();
				if(name.equals("")){
					Toast.makeText(SelectResultActivity.this, "���Ʋ���Ϊ��", Toast.LENGTH_SHORT).show();
				}else{
					//�������߳�ȥ����������
					selectBook = new SelectCanBorrow(name,handler,SelectResultActivity.this);
					selectBook.startThread(isSelectAll);
					
					//���������û��ȴ��ĶԻ���
					proDialog = ProgressDialog.show(SelectResultActivity.this,"���ݼ�����","���������У����Ժ󡣡���");
					proDialog.setCancelable(true);
				}
			}
		});
		listMyStoredBook = (ListView) findViewById(R.id.list_my_stored_book);
		//��tabHost�����л�����
		//��ȡTabWeiget����
		final TabWidget tw = tabHost.getTabWidget();
		//��ȡTabWidget��Tab����
		final int tabSize = tw.getChildCount();
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				//�������ת���ղ�ҳ��
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
					//��������
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
		
		//������ListView��ӳ����������ղ�
		this.registerForContextMenu(listSelectedBook);
		//���ղ�ListView���ContextMenu
		this.registerForContextMenu(listMyStoredBook);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.select_result, menu);
		menu.add(Menu.NONE,1,1,"����");
		menu.add(Menu.NONE,2,2,"�˳�");
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//����ǵڼ��鰴ť���Ӷ��������ղػ���ɾ��
		int groupId = item.getGroupId();
		AdapterView.AdapterContextMenuInfo info ;
		try{
			info = (AdapterContextMenuInfo) item.getMenuInfo();
		}catch(ClassCastException e){
			e.printStackTrace();
			return false;
		}
		switch(groupId){
		case 0://�ղ�ѡ�е�book//��ʾ�ǵ�0�鰴ť������ֻ���ղ�
			if(selectBook != null){
				List<Book> books = selectBook.getCanBorrowBook();
				Book book = books.get(info.position);
				OperateData operate = new OperateData(SelectResultActivity.this);
				Long judge = operate.insertData(book);
				if(judge == -1){
					Toast.makeText(SelectResultActivity.this, "�ղ�ʧ�ܣ�", Toast.LENGTH_SHORT).show();
				}else if(judge == -3){
					Toast.makeText(SelectResultActivity.this, "�����Ѵ��ڣ�", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(SelectResultActivity.this, "�ղسɹ���", Toast.LENGTH_SHORT).show();
				}
				operate.closeDB();
				operate = null;
				books = null;
				book = null;
			}else{
				Toast.makeText(SelectResultActivity.this, "�ղ�ʧ�ܣ�", Toast.LENGTH_SHORT).show();
			}
			break;
		case 1://��ʾ��ɾ����ѡ��
			OperateData oper = new OperateData(SelectResultActivity.this);
			List<Book> books = oper.selectData();
			if(oper.deleteData(books.get(info.position)) > 0){
				books.remove(info.position);
				//����ˢ��listMyStoredBook
				List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
				for(int i =0;i<books.size();i++){
					HashMap<String,String> map = new HashMap<String, String>();
					map.put("name", "<b>" + (i+1) + ". " + books.get(i).getName().substring(2)+"</b><font color='red'>"+books.get(i).getBookId()+"</font>");
					map.put("place", books.get(i).getPlace()+" "+books.get(i).getCanBorrowNum());
					map.put("author", books.get(i).getEditorAndPublic());
					data.add(map);
				}
				//���������ListView
				updateListView(data);
				Toast.makeText(SelectResultActivity.this, "ɾ���ɹ� ��", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(SelectResultActivity.this, "ɾ��ʧ�� ��", Toast.LENGTH_SHORT).show();
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
			menu.add(0,1,0,"�ղ�");
		}else{
			menu.add(1,0,0,"ɾ��");
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	//����ListView����
	private void updateListView(List<HashMap<String, String>> data){
		listMyStoredBook.setAdapter(new ListBookAdapter(SelectResultActivity.this, data));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case 1://����
			Intent intent = new Intent(SelectResultActivity.this, SettingsActivity.class);
			startActivity(intent);
			break;
		case 2://�˳�
			AlertDialog.Builder builder = new AlertDialog.Builder(SelectResultActivity.this);
			builder.setTitle("�˳�");
			builder.setMessage("ȷ���˳���");
			builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
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
