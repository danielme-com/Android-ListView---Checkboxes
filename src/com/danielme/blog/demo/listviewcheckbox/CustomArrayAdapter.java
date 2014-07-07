package com.danielme.blog.demo.listviewcheckbox;

import java.util.List;
import com.danielme.blog.demo.listviewcheckbox.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * Custom adapter - "View Holder Pattern".
 * 
 * @author danielme.com
 * 
 */
public class CustomArrayAdapter extends ArrayAdapter<Row>
{
	private LayoutInflater layoutInflater;

	public CustomArrayAdapter(Context context, List<Row> objects)
	{
		super(context, 0, objects);
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// holder pattern
		Holder holder = null;
		if (convertView == null)
		{
			holder = new Holder();

			convertView = layoutInflater.inflate(R.layout.listview_row, null);
			holder.setTextViewTitle((TextView) convertView.findViewById(R.id.textViewTitle));
			holder.setTextViewSubtitle((TextView) convertView.findViewById(R.id.textViewSubtitle));
			holder.setCheckBox((CheckBox) convertView.findViewById(R.id.checkBox));
			convertView.setTag(holder);
		}
		else
		{
			holder = (Holder) convertView.getTag();
		}

		final Row row = getItem(position);
		holder.getTextViewTitle().setText(row.getTitle());
		holder.getTextViewSubtitle().setText(row.getSubtitle());
		
		holder.getCheckBox().setTag(row.getTitle());
		holder.getCheckBox().setChecked(row.isChecked());	
		final View fila= convertView;
		changeBackground(getContext(), fila, row.isChecked());		
		holder.getCheckBox().setOnCheckedChangeListener(new OnCheckedChangeListener()
		{ 
			@Override
			public void onCheckedChanged(CompoundButton view, boolean isChecked)
			{
				//asegura que se modifica la Row originalmente asociado a este checkbox
				//para evitar que al reciclar la vista se reinicie el row que antes se mostraba en esta
				//fila. Es imprescindible tagear el Row antes de establecer el valor del checkbox
				if (row.getTitle().equals(view.getTag().toString()))
				{
					row.setChecked(isChecked);
					changeBackground(CustomArrayAdapter.this.getContext(), fila, isChecked);
					//desmarca todas los demás (si sólo se permite un item marcado a la vez).
					/*Row row = null;
					for(int i=0 ; i<getCount() ; i++)
					{						
						if (i != position && isChecked)   
						{
							row = (Row) getItem(i);
							row.setChecked(false);								
						}
					}
					notifyDataSetChanged();*/
				}
			}
		});
		
		
		return convertView;
	}
	
	/**
	 * Set the background of a row based on the value of its checkbox value. Checkbox has its own style.
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void changeBackground(Context context, View row, boolean checked)
	{
		if (row != null)
		{
			Drawable drawable = context.getResources().getDrawable(R.drawable.listview_selector_checked);
			if (checked)
			{
				drawable = context.getResources().getDrawable(R.drawable.listview_selector_checked);
			}
			else
			{
				drawable = context.getResources().getDrawable(R.drawable.listview_selector);
			}
			int sdk = android.os.Build.VERSION.SDK_INT;
			if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			    row.setBackgroundDrawable(drawable);
			} else {
			    row.setBackground(drawable);
			}
		}
	}

}

class Holder
{
	TextView textViewTitle;
	TextView textViewSubtitle;
	CheckBox checkBox;

	public TextView getTextViewTitle()
	{
		return textViewTitle;
	}

	public void setTextViewTitle(TextView textViewTitle)
	{
		this.textViewTitle = textViewTitle;
	}

	public TextView getTextViewSubtitle()
	{
		return textViewSubtitle;
	}

	public void setTextViewSubtitle(TextView textViewSubtitle)
	{
		this.textViewSubtitle = textViewSubtitle;
	}

	public CheckBox getCheckBox()
	{
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox)
	{
		this.checkBox = checkBox;
	}	

}
