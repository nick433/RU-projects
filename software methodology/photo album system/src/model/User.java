package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	private String username;
	private ArrayList<Album> albums;
	
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "data";
	public static final String storeFile = "Benutzers.dat";

	public User() {
		albums = new ArrayList<Album>();
	}
	
	/**
	 * Creates a new user object with a set username
	 * @param username The name of the user
	 */

	public User(String username) {
		albums = new ArrayList<Album>();
		this.username = username;
	}

	/**
	 * @return the name of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the name for the user to be changed to
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the ArrayList of the user's albums
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/**
	 * Updates the list of the user's albums
	 * @param albums the newest albums
	 */
	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

	/**
	 * Add an album to the user's collection
	 * @param album what to add
	 */
	public void addAlbum(Album album) {
		albums.add(album);
	}

	/**
	 * Remove an album from the user's collection
	 * @param album
	 */
	public void removeAlbum(Album album) {
		albums.remove(album);
	}

	/**
	 * @param album to test if it exists or not
	 * @return true if the album exists, false if not
	 */
	public boolean albumExists(Album album) {
		return albums.contains(album);
	}

	/**
	 * @param name of the album to find
	 * @return the album if it is found, or null on failure
	 */
	public Album getAlbumByName(String name) {
		for (Album a : albums) {
			if (name.equals(a.getAlbumName())) {
				return a;
			}
		}
		return null;
	}

}