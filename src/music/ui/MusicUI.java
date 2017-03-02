package music.ui;

import static java.lang.System.out;
import java.util.*;
import java.sql.*;
import MusicDatabase.MusicDAO;
import music.models.*;
import music.ui.EditUI;

public class MusicUI {

	Scanner keyboard = new Scanner(System.in);
	MusicDAO dao = new MusicDAO();
	EditUI eUI = new EditUI();

	public void mainMenu() throws SQLException {

		System.out.println("Welcome to As the Crow Flies Music Database.");
		boolean keepRunning = true;
		while (keepRunning) {
			printMainMenu();
			int choice = readUserChoice();
			keepRunning = callMenuItem(choice);
		}
		out.println("Thanks for visiting!");
	}

	public void printMainMenu() {
		out.println("1) List Artists in the Database");
		out.println("2) Search for only the Artist");
		out.println("3) Search by Artist for their Albums");
		out.println("4) Search by Album for the Artist");
		out.println("5) Edit");
		out.println("0) Quit\n");
		out.print("? ");
	}

	public int readUserChoice() {

		String input = keyboard.nextLine().trim();
		Integer id = Integer.parseInt(input);
		return id;
	}

	public boolean callMenuItem(int choice) throws SQLException {
		switch (choice) {
		case 0:
			return false;
		case 1: // list all artist
			listAllArtists();
			break;
		case 2: // Search for Artist only
			searchOnlyArtist();
			break;
		case 3: // Search Artist for Album
			searchArtistForAlbums();
			break;
		case 4: // Search by Album for the Artist who produced it
			searchByAlbumForArtist();
			break;
		case 5: // Add an artist
			signIn();
			break;

		}
		return true;
	}

	public void listAllArtists() throws SQLException {
		List<Artist> result = dao.listArtistsAndID();
		for (Artist myArtist : result) {

			out.println(myArtist.getId() + " " + myArtist.getName());

		}

	}

	public void searchOnlyArtist() throws SQLException {
		out.println("Displays artist's name and years active");
		out.println("Search for: ");
		String query = keyboard.nextLine();
		List<Artist> result = dao.searchArtistOnly(query);
		if (result.isEmpty()) {
			out.println("No matches.\n");
		} else {
			for (Artist searchedArtist : result) {

				out.println(searchedArtist.getName() + " " + searchedArtist.getStart() + " - " + searchedArtist.getEnd()
						+ "\n");
			}
		}
	}

	public void searchArtistForAlbums() throws SQLException {
		out.println("Displays artist's name and the studio Albums they produced");
		out.println("Search for: ");
		String query = keyboard.nextLine();
		List<Album> albumResult = dao.searchArtistForAlbum(query);
		List<Artist> artistResult = dao.searchArtistOnly(query);
		if (albumResult.isEmpty()) {
			out.println("No matches.\n");
		} else {
			for (Artist searchedArtist : artistResult) {
				out.println(searchedArtist.getName() + "\n");
				for (Album allAlbums : albumResult) {
					out.println(allAlbums.getAlbumId() + " " + allAlbums.getName() + " " + allAlbums.getReleaseDate());
				}
			}
		}
	}

	public void searchByAlbumForArtist() throws SQLException {
		out.println("Displays only the searched for album and the artist who produced it.");
		out.println("Search for: ");
		String query = keyboard.nextLine();
		List<Artist> result = dao.searchAlbumForArtist(query);
		if (result.isEmpty()) {
			out.println("No matches.\n");
		} else {
			for (Artist albumsMade : result) {
				out.println(albumsMade.getName());
			}
		}
	}

	public void signIn() throws SQLException {

		MusicDAO signInDAO = new MusicDAO();
		out.println("You must have an account before you can edit the database");
		out.print("Please enter user name:  ");
		String userName = keyboard.nextLine();
		out.println("Please enter password:  ");
		String password = keyboard.nextLine();

		dao.loginAttempt(userName, password);
		// User loginAttempt = new User(userName, password);
		boolean isSuccessful = signInDAO.loginAttempt(userName, password);
		if (isSuccessful) {
			eUI.editMainMenu();
		}
		// else{
		// dao.trackAttempts(loginInfo)
		// }
	}

}
