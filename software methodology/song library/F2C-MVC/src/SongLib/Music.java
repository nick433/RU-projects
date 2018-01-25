package SongLib;

public class Music {
	private String name;
	private String artist;
	private String album;
	private String year;
	
	public Music(String name, String artist, String album, String year){
		super();
		this.setName(name);
		this.setArtist(artist);
		this.setAlbum(album);
		this.setyear(year);
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getArtist() { return artist; }
	public void setArtist(String artist) { this.artist = artist; }


	public String getAlbum() {
		return album;
	}

	public String getYear() {
		return year;
	}

	public void setAlbum(String album) { this.album = album; }
	
	public String getyear() { return year; }
	public void setyear(String year) { this.year = year; }
	
	public String toString() {
		return getName() + "," + getArtist() + "," + getAlbum() + "," + getYear() + "\n";
	}

}