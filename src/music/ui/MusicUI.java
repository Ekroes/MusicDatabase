package music.ui;

import static java.lang.System.out;
import java.util.*;
import java.sql.*;
import MusicDatabase.MusicDAO;
import music.models.*;

public class MusicUI {

	Scanner keyboard = new Scanner(System.in);
	MusicDAO dao = new MusicDAO();

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
		out.println("5) Add Artist");
		out.println("6) Modify Artist information");
		out.println("7) Delete an Artist from Database");
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
			addArtist();
			break;
		case 6: // Modify Artist information
			updateArtist();
			break;
		case 7: // Delete an artist
			deleteArtist();
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

	public void addArtist() throws SQLException {
		MusicDAO artistDAO = new MusicDAO();
		out.println("Type the Name of the Artist you wish to add.\n");
		out.println("> ");
		String artistName = keyboard.nextLine();
		out.println("Type the year the artist officialy began performing. \n");
		out.println("> ");
		String yearStart = keyboard.nextLine();
		out.println("Type the year the artist ended their career under the name above."
				+ " If they are still active type null. \n");
		out.println("> ");
		String yearEnd = keyboard.nextLine();
		Artist addedArtist = new Artist(artistName, yearStart, yearEnd);
		boolean isDuplicate = artistDAO.checkArtistName(artistName);
		if (isDuplicate) {
			out.println("Artist already exists in database.");
		}

		else {

			dao.saveArtist(addedArtist);
			out.println("Artist saved as #" + addedArtist.getId() + ".\n");
		}

	}

	public void updateArtist() throws SQLException {
		out.println("Please type the database id of the Artist you want to modify.");
		out.println("This information is available via the list artist function.");
		out.println("the id is? >");
		Integer id = readUserChoice();

		Artist enteredId = dao.getArtistId(id);
		if (enteredId == null) {
			out.println("There is no Artist with id = " + id + ". Returning to the main menu.");
			return;
		}
		out.println("Here is the existing info about the Artist.");
		out.println(enteredId);
		out.println("Modifying an artist's information requires their name, the year they became active," + "\n"
				+ "and the year they concluded working if applicable.");
		out.println("Please enter the Artist's name");
		out.print("> ");

		String artistName = keyboard.nextLine();

		out.println("Enter the year they became active.");
		out.print("> ");

		String startYear = keyboard.nextLine();

		out.println("Enter the year they ended their career under the name entered above. "
				+ " If they are still active, enter null.");
		out.print("> ");

		String endYear = keyboard.nextLine();
		Artist updatedArtist = new Artist(id, artistName, startYear, endYear);
		dao.saveArtist(updatedArtist);

		out.println("updated info added to the database");
	}

	public void deleteArtist() throws SQLException {

		out.println("Please type the database id for the artist you wish to delete.");
		out.println("The id is available via the list artist function.");
		out.println(
				"Please note deletion cannot take place without first removing the Artist's works from the database");
		out.println("The id is? ");
		Integer id = readUserChoice();

		Artist a = dao.getArtistId(id);
		if (a == null) {
			out.println("There was no Artist with id = " + id + "Returning to the main menu.");
			return;
		} else {
			dao.searchAlbumByArtistId(id);

		}
	}

	public void addAlbum() throws SQLException {

		out.println("Type the name of the album you wish to add.");
		out.print("> ");
		String albumName = keyboard.nextLine();
		out.println("Type the year the album was released.");
		out.print("> ");
		String yearReleased = keyboard.nextLine();
		out.println("Type the ID number of the artist who produced the album");
		out.print("> ");
		Integer artistId = keyboard.nextInt();
		Album addedAlbum = new Album(albumName, yearReleased, artistId);
		dao.saveAlbum(addedAlbum);
		out.println("Album saved as #" + addedAlbum.getAlbumId() + ".\n");

	}

	public void updateAlbum() throws SQLException {

		out.println("Updating requires the id of the album.  ");
		out.println("This is available by searching for the artist's various albums.");
		out.print("id = ?");
		Integer albumId = readUserChoice();

		Album enteredId = dao.getAlbumId(albumId);
		if (enteredId == null) {
			out.println("There is no Artist with id = " + enteredId + ". Returning to the main menu.");
			return;
		}
		out.println("Modifying an Album requires the name of the album and the year it was released.");
		out.println(" What is the name of the album?");
		out.print("> ");
		String albumName = keyboard.nextLine();
		out.println("What year was the album released?");
		out.print("> ");
		String yearReleased = keyboard.nextLine();
		Album modifiedAlbum = new Album(albumId, albumName, yearReleased);
		dao.saveAlbum(modifiedAlbum);
	}

}
