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

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity 
{
	ArrayAdapter<String> adapter;
	ListView listView;
	// This variable will keep track of the chosen todo item position.
	int todoListItemPosition;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		ItemListManager.loadListItem(this);
		adapter=new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_checked,
	            ItemListController.getTodoListInstance());
	    setListAdapter(adapter);
	    listView = (ListView) findViewById(android.R.id.list);
	    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    listView.setTextFilterEnabled(true);
		setCheckedItems();
		// The following item long click listener code was adapted from
		// http://stackoverflow.com/questions/8846707/how-to-implement-a-long-click-listener-on-a-listview
		// by Dinesh Sharma
	    listView.setOnItemLongClickListener(new OnItemLongClickListener() 
	    {

            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    						int position, long id) {
            	//This is so that the Dialog Interface will know the position of the item.
            	todoListItemPosition=position;
            	// The android code was used from
            	// http://developer.android.com/guide/topics/ui/dialogs.html
            	AlertDialog.Builder choiceDialog = new AlertDialog.Builder(MainActivity.this);
            	choiceDialog.setTitle("Do you want to...");
            	choiceDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener()
            	{
            		public void onClick(DialogInterface dialog,int id)
            		{
            			// Tells the list controller to delete the item from the itemList.
            			// Save the new itemList.
            			String item=listView.getItemAtPosition(todoListItemPosition).toString();
            			ItemListController.deleteItem(item);
            			setCheckedItems();
            			ItemListManager.saveListItem(MainActivity.this);
            			adapter.notifyDataSetChanged();
            		}
            	});
            	choiceDialog.setNeutralButton("Archive", new DialogInterface.OnClickListener()
            	{
                	public void onClick(DialogInterface dialog,int id)
                	{
            			// Tells the list controller to switch the item from the todolist to the
                		// archive list.
            			// Save the new itemList.
                		String item=listView.getItemAtPosition(todoListItemPosition).toString();
                		ItemListController.listSwitchItem(item,ItemListController.getTodoListInstance(),
                									ItemListController.getArchiveListInstance());
                		setCheckedItems();
                		ItemListManager.saveListItem(MainActivity.this);
                		adapter.notifyDataSetChanged();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	//Code taken and modified from https://developer.android.com/training/basics/actionbar/adding-buttons.html
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_archive:
	            openArchive();
	            return true;
	        case R.id.action_email:
	        	openEmail();
	            return true;
	        case R.id.action_stats:
	        	openStats();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public void openArchive(){
		Intent intent = new Intent(this,ArchiveActivity.class);
		startActivity(intent);
	}
	public void openStats(){
		Intent intent = new Intent(this,StatActivity.class);
		startActivity(intent);
	}
	public void openEmail(){
		Intent intent = new Intent(this,EmailActivity.class);
		startActivity(intent);
	}
	public void addItem(View view)
	{
		Intent intent = new Intent(this,AddItemActivity.class);
		startActivity(intent);
	}
	public void onListItemClick(ListView parent, View view, int position, long id)
     {
		String item=listView.getItemAtPosition(position).toString();
		ItemListController.updateItemCheckmark(item);
		ItemListManager.saveListItem(this);
	}
	public void onResume(){
		super.onResume();
		setCheckedItems();
		adapter.notifyDataSetChanged();
	}
	public void onDestruct(){
		ItemListManager.saveListItem(MainActivity.this);
	}
	
	// Goes through the itemList and lets the list view know which items should be be checked and which
	// ones shoudln't. 
	public void setCheckedItems(){
		for (int position = 0; position<ItemListController.getTodoListInstance().size();position++){
				String item= listView.getItemAtPosition(position).toString();
				listView.setItemChecked(position,ItemListController.isItemChecked(item));
			}		
		}

}

