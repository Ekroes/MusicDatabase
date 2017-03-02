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

	// public User trackAttempts(loginInfo) throws SQLException {

	// }

	public boolean loginAttempt(String userName, String password) throws SQLException {

		String sql = "SELECT * FROM useraccount WHERE UserName = ? AND login = ?";

		Driver driver = new Driver();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;

		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			res = pstmt.executeQuery();
			if (res.next()) {
				return true;
			} else {
				return false;

			}
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

	public List<Album> listAlbumAndIDs() throws SQLException{
		List<Album> album = new ArrayList<Album>();
		Driver driver = new Driver();
		String sql = "SELECT * FROM album " + "ORDER BY Artist_ID AND Year_Released";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			conn = driver.openConnection();
			pstmt = conn.prepareStatement(sql);
			res = pstmt.executeQuery();
			while (res.next()) {
				album.add(new Album(res.getInt("Album_ID"), res.getString("Album_Name"),
						res.getString("Year_Released"), res.getInt("Artist_ID")));
			}
			return album;
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}
	}


