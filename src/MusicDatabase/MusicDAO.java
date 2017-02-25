package MusicDatabase;

import java.sql.*;
import java.util.*;

import music.models.*;

public class MusicDAO {

	// searches artist by name based on user input. #2 on the menu

	public List<Artist> searchArtistOnly(String text) throws SQLException {
		List<Artist> artist = new ArrayList<Artist>();

		String sql = "SELECT * FROM artist" + " WHERE Artist_Name LIKE ? ORDER BY Artist_Id";
		Driver driver = new Driver();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {

			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + text + "%");
			res = pstmt.executeQuery();
			while (res.next()) {
				artist.add(new Artist(res.getInt("Artist_Id"), res.getString("Artist_Name"),
						res.getString("Start_Year_Active"), res.getString("End_Year_Active")));
			}
			return artist;
		}

		finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}

	}

	// lists all the Artists and their IDs from the database. Action #1 on the
	// main menu

	public List<Artist> listArtistsAndID() throws SQLException {
		List<Artist> artist = new ArrayList<Artist>();
		Driver driver = new Driver();
		String sql = "SELECT * FROM artist " + "ORDER BY Artist_ID";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			res = pstmt.executeQuery();
			while (res.next()) {
				artist.add(new Artist(res.getInt("Artist_Id"), res.getString("Artist_Name"),
						res.getString("Start_Year_Active"), res.getString("End_Year_Active")));
			}
			return artist;
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

	// lists artist and the albums they produced. Action #3 on main menu

	public List<Album> searchArtistForAlbum(String text) throws SQLException {
		List<Album> artistWork = new ArrayList<Album>();
		Driver driver = new Driver();

		String sql = "SELECT Album.* FROM artist,album "
				+ " WHERE artist.Artist_ID = album.Artist_ID AND Artist_Name Like ? "
				+ "ORDER BY artist.Artist_ID, Year_Released";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + text + "%");
			res = pstmt.executeQuery();
			while (res.next()) {
				artistWork.add(new Album(res.getInt("Album_Id"), res.getString("Album_Name"),
						res.getString("Year_Released"), res.getInt("Artist_Id")));
			}
			return artistWork;
		}

		finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);

		}
	}

	// lists the album and the artist who produced it. Action #4 on main menu

	public List<Artist> searchAlbumForArtist(String text) throws SQLException {
		List<Artist> albumsArtist = new ArrayList<Artist>();
		Driver driver = new Driver();
		String sql = "SELECT Artist.*, Album_Name, Year_Released FROM artist,album"
				+ " WHERE artist.Artist_ID = album.Artist_ID AND Album_Name Like ? "
				+ "ORDER BY artist.Artist_ID, Year_Released";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + text + "%");
			res = pstmt.executeQuery();
			while (res.next()) {
				albumsArtist.add(new Artist(res.getInt("Artist_Id"), res.getString("Artist_Name"),
						res.getString("Start_Year_Active"), res.getString("End_Year_Active")));
			}
			return albumsArtist;
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

	public List<Album> searchAlbumByArtistId(Integer id) throws SQLException {

		List<Album> albumByArtistId = new ArrayList<Album>();
		Driver driver = new Driver();
		String sql = "SELECT Artist.*, Album_Name, Year_Released FROM artist,album"
				+ " WHERE artist.Artist_ID = album.Artist_ID AND Album_ID = ? "
				+ "ORDER BY artist.Artist_ID, Year_Released";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + id + "%");
			res = pstmt.executeQuery();
			while (res.next()) {
				albumByArtistId.add(new Album(res.getInt("Album_Id"), res.getString("Album_Name"),
						res.getString("Year_Released"), res.getInt("Artist_Id")));
			}
			return albumByArtistId;
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

	public void saveArtist(Artist a) throws SQLException {
		if (a.getId() == null) {
			insertArtist(a);
		} else {
			updateArtist(a);
		}
	}

	public boolean checkArtistName(String text) throws SQLException {

		Driver driver = new Driver();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		String sql = "SELECT Artist_Name FROM artist WHERE Artist_Name = ?";

		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, text);
			res = pstmt.executeQuery();
			if (res.next()) {

				return true;

			}

			else {
				return false;
			}
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

	public void updateArtist(Artist modifiedArtist) throws SQLException {

		String sql = "UPDATE artist SET Artist_Name = ?, Start_Year_Active = ?, End_Year_Active = ?"
				+ "WHERE Artist_Id = ?";

		Driver driver = new Driver();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(4, modifiedArtist.getId());
			pstmt.setString(1, modifiedArtist.getName());
			pstmt.setString(2, modifiedArtist.getStart());
			if (modifiedArtist.getEnd() == null || "".equals(modifiedArtist.getEnd())) {
				pstmt.setNull(3, java.sql.Types.INTEGER);
			} else {
				pstmt.setString(3, modifiedArtist.getEnd());

			}

			
			pstmt.executeUpdate();

		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

	public int insertArtist(Artist addedArtistInfo) throws SQLException {

		String sql = "INSERT INTO artist (Artist_Name, Start_Year_Active, End_Year_Active) VALUES (?,?,?)";

		Driver driver = new Driver();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		int id = 0;
		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, addedArtistInfo.getName());
			pstmt.setString(2, addedArtistInfo.getStart());

			if (addedArtistInfo.getEnd() == null || "".equals(addedArtistInfo.getEnd())) {
				pstmt.setNull(3, java.sql.Types.INTEGER);
			} else {
				pstmt.setString(3, addedArtistInfo.getEnd());

			}
			pstmt.executeUpdate();
			res = pstmt.getGeneratedKeys();

			if (res.next()) {
				id = res.getInt(1);

			}

			return id;
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

	public void deleteArtist(Integer id) throws SQLException {

		String sql = "DELETE FROM artist WHERE Artist_Id = ?";
		Driver driver = new Driver();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

	public Artist getArtistId(Integer id) throws SQLException {
		String sql = "SELECT * FROM artist where Artist_Id = ?";
		Driver driver = new Driver();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			res = pstmt.executeQuery();
			if (res.next()) {
				return new Artist(res.getInt("Artist_ID"), res.getString("Artist_Name"),
						res.getString("Start_Year_Active"), res.getString("End_Year_Active"));
			} else {
				return null;
			}
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

	public void saveAlbum(Album albumInfo) throws SQLException {
		if (albumInfo.getAlbumId() == null) {
			insertAlbum(albumInfo);
		} else {
			updateAlbum(albumInfo);
		}

	}

	public void updateAlbum(Album albumInfo) throws SQLException {
		String sql = "UPDATE album SET Album_Name = ?, Year_Released = ? " + "WHERE Album_ID = ?";

		Driver driver = new Driver();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(3, albumInfo.getAlbumId());
			pstmt.setString(1, albumInfo.getName());
			pstmt.setString(2, albumInfo.getReleaseDate());
			pstmt.executeUpdate();
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

	public int insertAlbum(Album albumInfo) throws SQLException {
		String sql = "INSERT INTO album (Album_Name, Year_Released, Artist_ID) VALUES (?,?,?)";
		Driver driver = new Driver();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		int id = 0;
		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, albumInfo.getName());
			pstmt.setString(2, albumInfo.getReleaseDate());
			pstmt.setInt(3, albumInfo.getArtistId());
			pstmt.executeUpdate();
			res = pstmt.getGeneratedKeys();

			if (res.next()) {
				id = res.getInt(1);

			}

			return id;
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}

	}

	public Album getAlbumId(Integer albumId) throws SQLException {
		String sql = "SELECT * FROM album where Album_ID = ?";

		Driver driver = new Driver();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, albumId);

			res = pstmt.executeQuery();
			if (res.next()) {
				return new Album(res.getInt("Album_ID"), res.getString("Album_Name"), res.getString("Year_Released"),
						res.getInt("Artist_ID"));
			} else {
				return null;
			}
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

}
