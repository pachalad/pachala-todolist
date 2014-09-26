//Copyright [2014] [Dominik Pachala]
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package ca.ualberta.cs.pachalatodo;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EmailActivity extends Activity
{
	private ArrayAdapter<String> adapter;
	private ListView listView;
	private ArrayList<String> totalItems= new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
		// Show the Up button in the action bar.
		totalItems.addAll(ItemListController.getTodoListInstance());
		totalItems.addAll(ItemListController.getArchiveListInstance());
		setupActionBar();
		adapter=new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_multiple_choice,
	            totalItems);
		listView=(ListView) findViewById(R.id.email_list);
	    listView.setAdapter(adapter);
	    listView.setTextFilterEnabled(true);
	    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email, menu);
		
		return true;
	}
	public void onSelectClick(View view){
		AlertDialog.Builder choiceDialog = new AlertDialog.Builder(EmailActivity.this);
    	choiceDialog.setTitle("Select...");
    	choiceDialog.setPositiveButton("All", new DialogInterface.OnClickListener()
    	{
    		public void onClick(DialogInterface dialog,int id)
    		{
    			for (int i=0;i<totalItems.size();i++){
    				listView.setItemChecked(i,true);
    			}
    		}
    	});
    	choiceDialog.setNeutralButton("All Todo", new DialogInterface.OnClickListener()
    	{
        	public void onClick(DialogInterface dialog,int id)
        	{
        		// In retrospect I should've made a method for this. It goes through
        		// the list view and makes all the todos checked and all the archived
        		// unchecked. Since this is ordered we know that the archives start
        		// after the todos. The positionInflater just adds to the position.
        		// So the proper items are chosen.
        		int positionInflater=ItemListController.getTodoListInstance().size();
        		for (int i=0;i<ItemListController.getTodoListInstance().size();i++){
        			listView.setItemChecked(i, true);
        		}
        		for (int i=0;i<ItemListController.getArchiveListInstance().size();i++){
        			listView.setItemChecked(positionInflater+i, false);
        		}
        	}
    	});
    	choiceDialog.setNegativeButton("All Archive",new DialogInterface.OnClickListener()
    	{
        	public void onClick(DialogInterface dialog,int id)
        	{
        		// In retrospect I should've made a method for this. It goes through
        		// the list view and makes all the archives checked and all the todos
        		// unchecked. Since this is ordered we know that the archives start
        		// after the todos. The positionInflater just adds to the position.
        		// So the proper items are chosen.
        		int positionInflater=ItemListController.getTodoListInstance().size();
        		for (int i=0;i<ItemListController.getArchiveListInstance().size();i++){
        			listView.setItemChecked(positionInflater+i, true);
        		}
        		for (int i=0;i<ItemListController.getTodoListInstance().size();i++){
        			listView.setItemChecked(i, false);
        		}
        	}
        });
    	choiceDialog.create().show();
    	
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void onEmail(View view){
		String bodyText=null;
		SparseBooleanArray chosenItems=listView.getCheckedItemPositions();
		// This below code was adapted from
		// http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
		// by fiXedd.
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_SUBJECT, "My list of todos!");
		// Goes through the list of chosen items and adds them to a
		// string with a new line. So they appear as a list in the email.
		for (int i=0;i<chosenItems.size();i++){
		       if (chosenItems.valueAt(i)) {
		            String item = listView.getAdapter().getItem(
		                                 chosenItems.keyAt(i)).toString();
		            bodyText=bodyText+item+"\n";	            
		       }
		}
		intent.putExtra(Intent.EXTRA_TEXT,bodyText);
		startActivity(Intent.createChooser(intent, "Email"));
	}

}
