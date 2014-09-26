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


// This class is to contain all information about a todo item
// that the user will be adding,updating and deleting.
public class Item 
{

	String itemName;
	Boolean checkMark;
	Boolean archived;
	
	public String getName(){
		return itemName;
	}
	public Boolean getCheckmark(){
		return checkMark;
	}
	public void updateName(String name){
		itemName=name;		
	}
	public void updateCheckmark(Boolean check){
		checkMark=check;
	}

	public Boolean getArchived()
	
	{
		return archived;
	}
	public void updateArchived(Boolean archive){
		archived=archive;
	}
}
