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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.content.Context;

//This is used to load and save files. I couldn't figure out 
// serialization so I decided to go with a simple file I/O.
public class ItemListManager
{
	static final String filename="pachalatodolist.txt";
	
	
	public static void saveListItem(Context context){
		// Used android code
		// http://developer.android.com/training/basics/data-storage/files.html
		FileOutputStream fos;
		try{
			fos=context.openFileOutput(filename,Context.MODE_PRIVATE);
			// Goes through the whole itemList and writes to a file.
			for (int i=0; i<ItemListController.getItemListSize();i++){
				String itemName=ItemListController.getItemListInstance().get(i).getName();
				String checkmark=ItemListController.getItemListInstance().get(i).getCheckmark().toString();
				String archived=ItemListController.getItemListInstance().get(i).getArchived().toString();
				fos.write(itemName.getBytes());
				fos.write("\n".getBytes());
				fos.write(checkmark.getBytes());
				fos.write("\n".getBytes());
				fos.write(archived.getBytes());
				fos.write("\n".getBytes());
			}
			fos.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void loadListItem(Context context){
			FileInputStream is = null;
			try
			{
				is = context.openFileInput(filename);
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br= new BufferedReader(isr);
				String input;
				// Since I created the saving file, I know that each item saved comes with 
				// 3 pieces of data. So every three readLines() leads to a new saved item.
				while ((input = br.readLine())!=null){
					Item newItem=new Item();
					newItem.updateName(input);
					input=br.readLine();
					newItem.updateCheckmark(Boolean.valueOf(input));
					input=br.readLine();
					newItem.updateArchived(Boolean.valueOf(input));
					ItemListController.getItemListInstance().add(newItem);
				}
				// Once it finishes loading, it populates the string lists in ItemListController
				// with the proper items.
				ItemListController.populateList(ItemListController.getTodoListInstance(),false);
				ItemListController.populateList(ItemListController.getArchiveListInstance(),true);
				is.close();	
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
