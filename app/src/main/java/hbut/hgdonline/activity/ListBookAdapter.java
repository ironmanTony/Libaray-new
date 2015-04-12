package hbut.hgdonline.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbut.library.R;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListBookAdapter extends BaseAdapter{
	
	private List<HashMap<String, String>> data = null;
	private Context context;
	
	public ListBookAdapter(Context context, List<HashMap<String, String>> data){
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return  data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder  holder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.book_list_item, null);
			holder = new ViewHolder();
			holder.textBookName = (TextView) convertView.findViewById(R.id.text_book_name);
			holder.textPlace = (TextView) convertView.findViewById(R.id.text_book_place);
			holder.textAuthor = (TextView) convertView.findViewById(R.id.text_book_author);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//设置数据
		Map<String, String> item = data.get(position);
		holder.textBookName.setText(Html.fromHtml(item.get("name")));
		holder.textPlace.setText(Html.fromHtml(item.get("place")));
		holder.textAuthor.setText(Html.fromHtml(item.get("author")));
		//设置不同的背景色
		if(position%2 == 0){
			holder.textBookName.setBackgroundColor(Color.rgb(245,245,245));
			holder.textPlace.setBackgroundColor(Color.rgb(245,245,245));
			holder.textAuthor.setBackgroundColor(Color.rgb(245,245,245));
		}else{
			holder.textBookName.setBackgroundColor(Color.rgb(255,255,255));
			holder.textPlace.setBackgroundColor(Color.rgb(255,255,255));
			holder.textAuthor.setBackgroundColor(Color.rgb(255,255,255));
		}
		return convertView;
	}
	
	/**
	 * 用来装item中的组件
	 * @author litao
	 *
	 */
	
	static class ViewHolder{
		TextView textBookName;
		TextView textPlace;
		TextView textAuthor;
	}

}
