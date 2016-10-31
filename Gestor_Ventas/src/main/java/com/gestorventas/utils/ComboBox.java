package com.gestorventas.utils;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;

public class ComboBox extends LinearLayout{
	private AutoCompleteTextView _text;
	private ImageButton _button;

	public ComboBox(Context context) {
	       super(context);
	       this.createChildControls(context);
	}

	public ComboBox(Context context, AttributeSet attrs) {
	       super(context, attrs);
	       this.createChildControls(context);
	}
	
	private void createChildControls(Context context) {
	    this.setOrientation(HORIZONTAL);
	    this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
	                   LayoutParams.WRAP_CONTENT));

	   _text = new AutoCompleteTextView(context);
	   _text.setSingleLine();
	   _text.setInputType(InputType.TYPE_CLASS_TEXT
	                   | InputType.TYPE_TEXT_VARIATION_NORMAL
	                   | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
	                   | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
	                   | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
	   _text.setRawInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
	   this.addView(_text, new LayoutParams(LayoutParams.WRAP_CONTENT,
	                   LayoutParams.WRAP_CONTENT, 1));

	   _button = new ImageButton(context);
	   _button.setImageResource(android.R.drawable.arrow_down_float);
	   _button.setOnClickListener(new OnClickListener() {
	           @Override
	           public void onClick(View v) {
	                   _text.showDropDown();
	           }
	   });
	   this.addView(_button, new LayoutParams(LayoutParams.WRAP_CONTENT,
	                   LayoutParams.WRAP_CONTENT));
	 }
	
	public void setSuggestionSource(Cursor source, String column) {
	    String[] from = new String[] { column };
	    int[] to = new int[] { android.R.id.text1 };
	    SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this.getContext(),
	                   android.R.layout.simple_dropdown_item_1line, source, from, to);
	    // this is to ensure that when suggestion is selected
	    // it provides the value to the textbox
	    cursorAdapter.setStringConversionColumn(source.getColumnIndex(column));
	    _text.setAdapter(cursorAdapter);
	 }

	public String getText() {
	    return _text.getText().toString();
	}

	public void setText(String text) {
	    _text.setText(text);
    }

	class UnconditionalArrayAdapter<T> extends ArrayAdapter<T> {
	    final List<T> items;
	    public UnconditionalArrayAdapter(Context context, int textViewResourceId, List<T> items) {
	        super(context, textViewResourceId, items);
	        this.items = items;
	    }

	    public Filter getFilter() {
	        return new NullFilter();
	    }

	    class NullFilter extends Filter {
	        protected Filter.FilterResults performFiltering(CharSequence constraint) {
	            final FilterResults results = new FilterResults();
	            results.values = items;
	            return results;
	        }

	        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
	            items.clear(); // `items` must be final, thus we need to copy the elements by hand.
	            for (Object item : (List) results.values) {
	                items.add((T) item);
	            }
	            if (results.count > 0) {
	                notifyDataSetChanged();
	            } else {
	                notifyDataSetInvalidated();
	            }
	        }
	    }
	}

}
