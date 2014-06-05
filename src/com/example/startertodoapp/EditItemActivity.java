package com.example.startertodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	int index;
	EditText etName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		etName = (EditText) findViewById(R.id.editItemText);
		etName.setText(getIntent().getStringExtra("oldText"));
		index = getIntent().getIntExtra("index", 0);
	}
	public void onSubmit(View v) {
	  // Prepare data intent 
	  Intent data = new Intent(EditItemActivity.this, TodoActivity.class);
	  // Pass relevant data back as a result
	  data.putExtra("edited", etName.getText().toString());
	  data.putExtra("index", index);
	  // Activity finished ok, return the data
	  setResult(RESULT_OK, data); // set result code and bundle data for response
	  finish(); // closes the activity, pass data to parent
	} 
}
