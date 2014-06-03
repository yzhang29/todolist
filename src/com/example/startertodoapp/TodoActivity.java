package com.example.startertodoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

public class TodoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private final int REQUEST_CODE = 20;
	String edited;
	int editedIndex;
	boolean backFromChild = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems =(ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(getBaseContext(),
        		android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }
    
    public void addTodoItem(View v){
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	saveItems();
    }
    
    public void setupListViewListener(){
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
    		@Override
    		public boolean onItemLongClick(AdapterView<?> parent,View view, int position, long rowId){
    			items.remove(position);
    			itemsAdapter.notifyDataSetChanged();
    			saveItems();
    			return true;
    		}
    	});
    	lvItems.setOnItemClickListener(new OnItemClickListener(){
    		@Override
    		public void onItemClick(AdapterView<?> parent,View view, int position, long rowId){
    			Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
    			TextView txt = (TextView) parent.getChildAt(position);
    			i.putExtra("oldText", txt.getText().toString());
    			i.putExtra("index", position);
    			startActivity(i);
    		}
    	});
    }
    
    private void readItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try{
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	}
    	catch(IOException e){
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    
    private void saveItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try{
    		FileUtils.writeLines(todoFile, items);
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // code for result
                edited = getIntent().getExtras().getString("edited");
                editedIndex = getIntent().getExtras().getInt("index");
                backFromChild = true;
            }
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        if (backFromChild){
             backFromChild = false;
             //do something with aString here
             items.set(editedIndex, edited);
             saveItems();        
             }
    }

}
