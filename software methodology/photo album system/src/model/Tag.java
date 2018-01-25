package model;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.Serializable;

public class Tag implements Serializable{
		public ArrayList<String> tagnames = new ArrayList<String>();
		public ArrayList<String> tagvalues = new ArrayList<String>();
		
		/**
		 * Create a Tag object from a user input list
		 * @param tagstringtoparse example: "location=london,fruit=apple"
		 */
		
		public Tag(String tagstringtoparse) {
			if (tagstringtoparse != null) {
				String[] BetweenCommas = tagstringtoparse.split(",");
				for (String BC : BetweenCommas) {
					String[] BetweenEquals = BC.split("=");
					if (BetweenEquals.length == 2) {
						add(BetweenEquals[0], BetweenEquals[1]);
					} else {
					   	Alert alert = new Alert(AlertType.ERROR);
				    	alert.setTitle("Error Dialog");
				    	alert.setHeaderText("Your tag format is wrong");
				    	alert.setContentText("The correct tag format is name=value,name=value");
				    	alert.showAndWait();
					}
				}
			}
		}
		
		/**
		 * Create a Tag object with an initial name-value pair
		 * @param name the key
		 * @param value the value
		 */
		public Tag(String name, String value) {
			add(name, value);
		}
		
		/**
		 * Add a tag to the Tag object
		 * @param name the key to add
		 * @param value the value to add
		 * @return true if it could be added, false if not 
		 */
		public boolean add(String name, String value) {
			if ((name != null) && (value != null)) {
				//System.out.println(name + " " + value);
				this.tagnames.add(name);
				this.tagvalues.add(value);
				return true;
			}
			
			return false;
		}
		
		/**
		 * Remove a particular name-value tag.
		 * It assumes that the indexes are aligned correctly.
		 * @param name the key to remove
		 * @param value the corresponding value
		 * @return true if removed, false if not found
		 */
		public boolean remove(String name, String value) {
			if ((name != null) && (value != null)) {
				for (String itername : this.tagnames)
				{
					if (itername.equals(name)) {
						int idx = tagnames.indexOf(itername);
						if (idx != -1) {
							tagnames.remove(idx);
							tagvalues.remove(idx);
							return true;
						}
					}
				}
			}
			
			return false;
		}
		public String getTags(){
			
			return "" + this.tagnames + "__" +  this.tagvalues;
		}
		public String toString()
		{
			String result = "";
			
			for(int i = 0; i < tagnames.size(); i++)
			{
				result += tagnames.get(i) + "=" + tagvalues.get(i) + ",";
			}
			
			return result.substring(0, result.length()-1);
		}
}