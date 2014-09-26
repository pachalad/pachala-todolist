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

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


// This is the activity that runs when the user chooses to go into the archive,
// shows all archived items. From here you can delete and unarchive items.
public class ArchiveActivity extends Activity
{
	ArrayAdapter<String> adapter;
	ListView listView;
	// This variable will keep track of the chosen todo item position.
	// The dialog loses the position of the item that was long clicked
	// but the methods it calls need the position.
	int todoListItemPosition;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive);
		
		adapter=new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1,
	            ItemListController.getArchiveListInstance());
		listView=(ListView) findViewById(R.id.archivelist);
	    listView.setAdapter(adapter);
	    listView.setTextFilterEnabled(true);
	    // The code for this used and adapted from Dinesh Sharma from
	    // http://stackoverflow.com/questions/8846707/how-to-implement-a-long-click-listener-on-a-listview
	    // I just used the two lines to set up the long click listener.
	    listView.setOnItemLongClickListener(new OnItemLongClickListener() 
	    {

            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    						int position, long id) {
            	//This is so that the Dialog Interface will know the position of the item.
            	todoListItemPosition=position;
            	// The dialog template and some code taken from
            	// http://developer.android.com/guide/topics/ui/dialogs.html
            	AlertDialog.Builder choiceDialog = new AlertDialog.Builder(ArchiveActivity.this);
            	choiceDialog.setTitle("Do you want to...");
            	choiceDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener()
            	{
            		public void onClick(DialogInterface dialog,int id)
            		{
               			// Tells the list controller to delete the item from the itemList.
            			// Save the new itemList.
            			String item=listView.getItemAtPosition(todoListItemPosition).toString();
            			ItemListController.deleteItem(item);
            			adapter.notifyDataSetChanged();
            			ItemListManager.saveListItem(ArchiveActivity.this);
            		}
            	});
            	choiceDialog.setNeutralButton("Unarchive", new DialogInterface.OnClickListener()
            	{
                	public void onClick(DialogInterface dialog,int id)
                	{
               			// Tells the list controller to switch the item from the archive to the
                		// todo list.
            			// Save the new itemList.
                		String item=listView.getItemAtPosition(todoListItemPosition).toString();
                		ItemListController.listSwitchItem(item,ItemListController.getArchiveListInstance(),
                									ItemListController.getTodoListInstance());
                		adapter.notifyDataSetChanged();
                		ItemListManager.saveListItem(ArchiveActivity.this);
                	}
                });
            	choiceDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
            	{
                	public void onClick(DialogInterface dialog,int id)
                	{
                		dialog.dismiss();
                	}
                });
            	choiceDialog.create().show();
            	
            	return true;
            }
	    });
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archive, menu);
		return true;
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

}
