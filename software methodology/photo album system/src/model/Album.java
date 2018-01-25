package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable {
	private static final long serialVersionUID = 1L;
	private String albumName;
	private List<Photo> photos;
	private int size = 0;

	public Album() {
		photos = new ArrayList<Photo>();
	}
	
	/**
	 * Create an album with the specified albumName
	 * @param albumName the name of the album
	 */

	public Album(String albumName) {
		photos = new ArrayList<Photo>();
		this.albumName = albumName;
	}

	/**
	 * @return the name of the album
	 */
	public String getAlbumName() {
		return albumName;
	}

	/**
	 * Sets the current album name
	 * @param albumName the new album name
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	/**
	 * Adds a photo to the album
	 * @param p the Photo desired to be added
	 */
	public void addPhoto(Photo p){
		photos.add(p);
	}

	/**
	 * Provides access to the private List of photos 
	 * @return the List of photos
	 */
	public List<Photo> getPhotos() {
		return photos;
	}
	
	public Photo getPhotoByName(String name) {
		for (Photo a : photos) {
			if (name.equals(a.getPhotoName())) {
				return a;
			}
		}
		return null;
	}

	/**
	 * Removes a photo from the album
	 * @param photo to be removed
	 */
	public void removePhoto(Photo photo) {
		photos.remove(photo);
		size--;
	}

	/**
	 * Tests whether the photo exists in the album
	 * @param photo The photo to be tested 
	 * @return true if the photo is in the album, false if not
	 */
	public boolean photoExists(Photo photo) {
		return photos.contains(photo);
	}

	/**
	 * Gets the size of the album
	 * @return the number of photos in the album
	 */
	public int getSize() {
		return photos.size();
	}

	/**
	 * Changes the size of the album, but does not delete or add any photo.
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	public Photo getPhotoByIndex(int index){

		return photos.get(index);
	}

}