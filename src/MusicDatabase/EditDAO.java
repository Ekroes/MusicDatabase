package MusicDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import music.models.Album;
import music.models.Artist;

public class EditDAO {

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
				addedArtistInfo.setId(id);
			}

			return id;
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
		}
	}

	public void updateArtist(Artist modifiedArtist) throws SQLException {

		String sql = "UPDATE artist SET Artist_Name = ?, Start_Year_Active = ?, End_Year_Active = ? "
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
				albumInfo.setAlbumId(id);

			}

			return id;
		} finally {
			Driver.closeConnection(conn);
			Driver.closeStatement(pstmt);
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

	public void saveArtist(Artist a) throws SQLException {
		if (a.getId() == null) {
			insertArtist(a);
		} else {
			updateArtist(a);
		}
	}

	public void saveAlbum(Album albumInfo) throws SQLException {
		if (albumInfo.getAlbumId() == null) {
			insertAlbum(albumInfo);
		} else {
			updateAlbum(albumInfo);
		}

	}

}
