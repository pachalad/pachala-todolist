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
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

// This class shows the stats of your todo items.
public class StatActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat);
		// Show the Up button in the action bar.
		setupActionBar();
		// This part calculates the stats.
		int totalCount= ItemListController.getItemListSize();
		TextView textView = (TextView) findViewById(R.id.total);
		textView.setTextSize(35);
		textView.setText(Integer.toString(totalCount));
		
		int totalTodoCount= ItemListController.getTodoListInstance().size();
		textView = (TextView) findViewById(R.id.todo_total);
		textView.setTextSize(35);
		textView.setText(Integer.toString(totalTodoCount));
		
		int totalArchiveCount= ItemListController.getArchiveListInstance().size();
		textView = (TextView) findViewById(R.id.archive_total);
		textView.setTextSize(35);
		textView.setText(Integer.toString(totalArchiveCount));
		
		int totalTodoCheck = ItemListController.getTotalCheck(false,true);
		textView = (TextView) findViewById(R.id.todo_total_checked);
		textView.setTextSize(35);
		textView.setText(Integer.toString(totalTodoCheck));
		
		int totalArchiveCheck = ItemListController.getTotalCheck(true,true);
		textView = (TextView) findViewById(R.id.archive_total_checked);
		textView.setTextSize(35);
		textView.setText(Integer.toString(totalArchiveCheck));
		
		int totalTodoUncheck = ItemListController.getTotalCheck(false,false);
		textView = (TextView) findViewById(R.id.todo_total_unchecked);
		textView.setTextSize(35);
		textView.setText(Integer.toString(totalTodoUncheck));
		
		int totalArchiveUncheck = ItemListController.getTotalCheck(true,false);
		textView = (TextView) findViewById(R.id.archive_total_unchecked);
		textView.setTextSize(35);
		textView.setText(Integer.toString(totalArchiveUncheck));
		
		
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
		getMenuInflater().inflate(R.menu.stat, menu);
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