package music.models;

//Entity model; representing an album

//@author Elizabeth

public class Album {

	private Integer albumId = null;
	private String name = "";
	private String releaseDate = "";
	private Integer artistId = null;

	public Album(String name) {
		this.name = name;

	}

	public Album(String name, String releaseDate, Integer artistId) {

		this.name = name;
		this.releaseDate = releaseDate;
		this.artistId = artistId;
	}

	public Album(Integer albumId, String name, String releaseDate) {

		this.albumId = albumId;
		this.name = name;
		this.releaseDate = releaseDate;

	}

	public Album(Integer albumId, String name, String releaseDate, Integer artistId) {

		this.albumId = albumId;
		this.name = name;
		this.releaseDate = releaseDate;
		this.artistId = artistId;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;

	}

	public Integer getArtistId() {
		return artistId;
	}

	/*
	 * public String toString(){ return name + " " + releaseDate + "\n"; }
	 */
}
