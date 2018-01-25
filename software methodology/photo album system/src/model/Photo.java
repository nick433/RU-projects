package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Photo implements Comparable<Photo>, Serializable {

	String name;
	File fileLocation;
	public Tag tags;
	public String caption;
	
	
	public Photo(File fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public Photo(File fileLocation, String name) {
		this.fileLocation = fileLocation;
		this.name = name;
	}
	public void setCaption(String cap){
		this.caption = cap;
	}
	public String getCaption(){
		return this.caption;
	}
	public File getImage() {
		return fileLocation;
	}
	
	public String getPhotoName(){
		return name;
	}

	public void setPhotoName(String name){
		this.name = name;
	}
	public File getFileLocation(){
		return fileLocation;
	}

	public void setFileLocation(File fileLocation){
		this.fileLocation = fileLocation;
	}
	@Override
	//Not ideal, to put it mildly.
	public int compareTo(Photo o) {
		if (o instanceof Photo) {
			Photo p = (Photo) o;
			if (this.getImage().equals(p.getImage()))
					return 0;
		}
		
		return -1;
	}
	public void updateTags(Tag tags){
		this.tags = tags;
	}
	
	private Calendar dateTaken;
	
	public String setName(){
		this.name = fileLocation.toString().substring(fileLocation.toString().lastIndexOf('\\')+1,
				fileLocation.toString().length());
		
		return this.name;
	}
	
	public boolean changeName(String newName){
	
		// File (or directory) with new name
		File file2 = new File(this.fileLocation.getParent() + '\\' + newName);

		if (file2.exists()) return false;

		// Rename file (or directory)
		boolean success = this.fileLocation.renameTo(file2);

		if (!success) {
		   return false;
		}
		this.name = newName;
		this.fileLocation = file2;
		return true;
	}
	
	private boolean setDate()
	{
		return true;
	}
	
	private boolean getDate()
	{
		return false;
	}



	public String getFileLastModified(){

		//System.out.println("Before Format : " + fileLocation.lastModified());

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		//System.out.println("After Format : " + sdf.format(fileLocation.lastModified()));
	    return sdf.format(fileLocation.lastModified());
	    
	}
	private class Tags {
		ArrayList<String> tagname, tagvalue;
		
		public boolean add(String name, String value) {
			if ((name != null) && (value != null)) {
				this.tagname.add(name);
				this.tagname.add(value);
				return true;
			}
			
			return false;
		}
		
		public boolean remove(String name, String value) {
			if ((name != null) && (value != null)) {
				for (String itername : this.tagname)
				{
					if (itername.equals(name)) {
						int idx = tagname.indexOf(itername);
						if (idx != -1) {
							tagname.remove(idx);
							tagvalue.remove(idx);
							return true;
						}
					}
				}
			}
			
			return false;
		}
	}

}