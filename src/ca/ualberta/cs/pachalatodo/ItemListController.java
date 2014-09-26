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

import android.content.Context;


// This is the class that controls and implements the itemList and both
// todoLists and archiveLists. I created two string lists to make it easier 
// to deal with the list adapters. I should have split this into two different
// classes one as an itemList data type class and one that controls the list but
// I've run out of time. So this one does both.

public class ItemListController
{


	private static ArrayList<Item> itemList=null;
	private static ArrayList<String> todoList=null;
	private static ArrayList<String> archiveList=null;

	// Initializes itemList and returns list
	public static ArrayList<Item> getItemListInstance(){
		if (itemList==null){
			itemList=new ArrayList<Item>();
		}
		return itemList;
	}
	// Initializes todoList and returns list
	public static ArrayList<String> getTodoListInstance(){
		if (todoList==null){
			todoList=new ArrayList<String>();
		}
		return todoList;
	}
	// Initializes archiveList and returns list
	public static ArrayList<String> getArchiveListInstance(){
		if (archiveList==null){
			archiveList = new ArrayList<String>();
		}
		return archiveList;
	}
	// This one adds a new item to the itemList and adds  the name to the todoList.
	// Saves!
	public static void addNewItem(String itemName,Context context){
		Item item=new Item();
		item.updateName(itemName);
		item.updateCheckmark(false);
		item.updateArchived(false);
		ItemListController.getItemListInstance().add(item);
		todoList.add(itemName);
		ItemListManager.saveListItem(context);
	}
	// This populates the the string list from the itemlist.
	public static void populateList(ArrayList<String> list,boolean archive){
		list.clear();
		if (itemList!=null){
			for(int i=0;i<itemList.size();i++){
				if (itemList.get(i).getArchived()==archive){
					list.add(ItemListController.getItemListInstance().get(i).getName());
				}
			}
		}
	}
	// Gets the items position in the itemList.
	private static int getItemPosition(String name){
		for(int i=0;i<ItemListController.getItemListInstance().size();i++){
			if (ItemListController.getItemListInstance().get(i).getName()==name){
				return i;
			}
		}
		return -1;
	}
	// This one is used in the statactivity. Finds how many checkmarked and
	// archived items exist given the inputs. Ie. getTotalCheck(true,true)
	// returns the total of archived items that are checked.
	public static int getTotalCheck(boolean archive,boolean checked){
		int checks=0;
		for (int i=0 ; i<ItemListController.getItemListInstance().size();i++){
				if (ItemListController.getItemListInstance().get(i).getArchived()==archive &&
							ItemListController.getItemListInstance().get(i).getCheckmark()==checked){
							checks++;
				}
		}
		return checks;
	}
	// This one just updates the item's check mark in the itemList.
	public static void updateItemCheckmark(String item){
		int saveFilePosition=getItemPosition(item);
		itemList.get(saveFilePosition).updateCheckmark(
				!itemList.get(saveFilePosition).getCheckmark());
		
	}
	// Deletes the item from the itemList and populates the lists again.
	public static void deleteItem(String itemToDelete){
		int saveFilePosition=getItemPosition(itemToDelete);
		itemList.remove(saveFilePosition);
		populateList(todoList,false);
		populateList(archiveList,true);

	}
	// Switches the name of the item from one list to the other. Then it updates the listItem array.
	public static void listSwitchItem(String itemToSwitch,ArrayList<String> switchFrom,ArrayList<String> switchTo){
		int saveFilePosition=getItemPosition(itemToSwitch);
			switchFrom.remove(itemToSwitch);
			switchTo.add(itemToSwitch);
			itemList.get(saveFilePosition).updateArchived(
							!itemList.get(saveFilePosition).getArchived());
		}
	// Checks to see if the item is checked or not.
	public static boolean isItemChecked(String item){
		int itemPosition=getItemPosition(item);
		return itemList.get(itemPosition).getCheckmark();
	}
	public static int getItemListSize(){
		return itemList.size();
	}

}
