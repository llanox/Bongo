package info.llanox.mobile.model;

public class Song {
	
	private String name;
	private long duration;
	private String artist;
	private String album;
	private String path;
	
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getNombre() {
		return name;
	}
	public void setName(String nombre) {
		this.name = nombre;
	}


	
	public String toString(){
	   return name;
	 }
	

}
