package com.danielme.blog.demo.listviewcheckbox;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author danielme.com
 * 
 */
public class MainActivity extends ListActivity
{
	List<Row> rows;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		rows = new ArrayList<Row>(30);
		Row row = null;
		for (int i = 1; i < 31; i++)
		{
			row = new Row();
			row.setTitle("Title " + i);
			row.setSubtitle("Subtitle " + i);
			rows.add(row);
		}
		
		rows.get(3).setChecked(true);
		rows.get(6).setChecked(true);
		rows.get(9).setChecked(true);
		 
		setListAdapter(new CustomArrayAdapter(this, rows));
		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Toast.makeText(MainActivity.this, rows.get(position).getTitle(), Toast.LENGTH_SHORT).show();				
			}
		});
	}
}