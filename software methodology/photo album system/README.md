AlbumManagenController.java

	/**
	 * handleMouseClick opens the selected album when listview is doubleclicked
	 * @param e The mouse event whose clicks are counted
	 * @throws ClassNotFoundException see openAlbum
	 * @throws IOException see openAlbum
	 */

	/**
	 * AlbumManagen's initData fills the listview with albums
	 * @throws ClassNotFoundException see UserList.read
	 * @throws IOException see UserList.read
	 */

	/**
	 * setCurrentUser lets AlbumManagen know whose albums to show
	 * @param username the username of the relevant user
	 */

	/**
	 * createNewAlbum lets the user choose a new, unique name for an album of photos
	 * @param event the buttonpress for "New album"
	 * @throws ClassNotFoundException
	 */

	/**
	 * See who the album manager thinks is the current user
	 * @return The user object
	 */

	/**
	 * renameAlbum will rename the selected album to a unique, different name
	 * @param event The mouse click or button press from the user as a signal
	 * @throws ClassNotFoundException
	 */

	/**
	 * removeAlbum Removes an album from the user's list of albums
	 * @param event the user click or press
	 * @throws ClassNotFoundException
	 */

	/**
	 * openAlbum opens the PhotoviewController with the current album selection
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	/**
	 * loggingOut opens up the login window again so different users can switch, for e.g. copy/move
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	/**
	 * saveData saves the local session data to disk
	 * @throws ClassNotFoundException
	 */

AdministrierenController.java

	/**
	 * initialize creates stock users "admin" and "stock" if data file is empty
	 * @param location default from overriden
	 * @param resources default from overriden
	 */

	/**
	 * initData fills the listview with user's data from disk
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	/**
	 * removeUserAction removes the selected user from the listview, underlying list, and disk
	 * @param event
	 * @throws ClassNotFoundException
	 */

	/**
	 * loggingOut returns to the login page
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	/**
	 * saveData writes everything down to disk
	 * @throws ClassNotFoundException
	 */

	/**
	 * @param stage
	 */

EinloggenController.java

	/**
	 * loadpresets creates admin and stock users if necessary
	 */

	/**
	 * createNewUserAction allows the user to select a new, unique name for a new user
	 * @param event
	 * @throws ClassNotFoundException
	 */

	/**
	 * login() sends the admin to the user management window and a regular user to the album management window
	 * @param event
	 * @throws ClassNotFoundException
	 */

	/**
	 * closeLoginWindow() closes the login windows
	 */

PhotoviewController.java:

	/**
	 * handleMouseClick displays the photo that was clicked on
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	/**
	 * initData populates the list of photos with the album's photos
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	/**
	 * displayImage scales the image to the window's allocated size then displays it
	 * @throws IOException
	 */

	/**
	 * setCurrentUser informs PhotoviewController whose album it deals with
	 * @param username
	 */

	/**
	 * renamePhoto allows the user to choose a unique name for their photo, and also renames the photo on disk
	 * @param event
	 * @throws ClassNotFoundException
	 */

	/**
	 * backToAlbum returns the user to the album management view
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	/**
	 * addPhoto opens the FileChooser to allow a user to choose a jpg or png file to load into the album
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	/**
	 * movePhoto() is a combined copy and delete
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	/**
	 * deletePhoto removes the photo from the album
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	/**
	 * copyPhoto copies the selected photo from one album to (a user is prompted for the name) another's
	 * @return true if successful, false if not
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	/**
	 * playSlideshow starts a slideshow of viewing photos with previous and next options
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

SlideshowController.java:

	/**
	 * displayImage displays the current image in the slideshow
	 * @throws IOException
	 */

	/**
	 * nextPic uses a seniority-based index to decide which picture to display next in the slideshow
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	/**
	 * backToPhotoView switches the user view from Slideshow view to photo view
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */