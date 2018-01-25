package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class UserList implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "data", storeFile = "Benutzers.dat";

	private ArrayList<User> users;

	public UserList() {
		users = new ArrayList<User>();
	}

	/**
	 * addUser() augments the UserList
	 * @param user the user object desired to be added
	 */
	public void addUser(User user) {
		users.add(user);
	}

	/**
	 * getUsers() gives access to UserList's internal ArrayList
	 * @return the list of users
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * removeUser() removes a specified user from the UserList
	 * @param user the user object
	 */
	public void removeUser(User user) {
		users.remove(user);
	}

	/**
	 * getUserByUserName() searches the UserList for a specified user
	 * @param username the name of the user requested
	 * @return the User object if found, or null if not
	 */
	public User getUserByUsername(String username) {
		try {
			return users.stream().filter(x -> 
				x.getUsername().trim().equals(username)).findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * isUsernameTaken() enforces the unique username rule
	 * @param username String for what to check
	 * @return true if the username is taken, false if not
	 */
	public boolean isUsernameTaken(String username) {
		return (getUserByUsername(username) != null);
	}

	/**
	 * userExists() checks if a user with a specified name exists
	 * @param username the String for the username to check
	 * @return true if the username is taken, false if not.
	 */
	public boolean userExists(String username) {
		return isUsernameTaken(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {		
		if (users == null) {
			return "No users exist.";
		}
		
		StringBuilder ulist = new StringBuilder();
	    users.forEach(l -> ulist.append(l.getUsername() + "\n"));
		return ulist.toString();
	}
	
	/***
	 * read() loads the serialized UserList class from the preset StoreDir + StoreFile location
	 * @return the loaded UserList class from the file
	 * @throws IOException if there are problems with finding the file or reading from it
	 * @throws ClassNotFoundException if the class is at the wrong version
	 */

	public static UserList read() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		UserList listOfAllUsers = (UserList) ois.readObject();
		ois.close();
		return listOfAllUsers;
	}
	
	/***
	 * write() saves the changes made locally on the program to disk.
	 * @param listOfAllUsers is a UserList containing the latest users with their albums that contain photos and so on.
	 * @throws IOException if there are operating-system level problems with writing
	 */

	public static void write(UserList listOfAllUsers) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(listOfAllUsers);
		oos.close();
	}
}