package music.ui;

import static java.lang.System.out;

import java.sql.SQLException;
import java.util.Scanner;
import MusicDatabase.EditDAO;
import MusicDatabase.MusicDAO;
import music.models.Album;
import music.models.Artist;

public class EditUI {

	Scanner keyboard = new Scanner(System.in);
	EditDAO edao = new EditDAO();
	MusicDAO mdao = new MusicDAO();

	public void editMainMenu() throws SQLException {

		System.out.println("Welcome to the Edit Screen.");
		boolean keepRunning = true;
		while (keepRunning) {
			printEditMainMenu();
			int choice = readUserChoice();
			keepRunning = callEditMenuItem(choice);
		}
		out.println("Thanks for visiting!");
	}

	public void printEditMainMenu() {
		out.println("1) Add a new Artist");
		out.println("2) Modify an existing Artist's information");
		out.println("3) Delete an existing Artist");
		out.println("4) Add a new Album");
		out.println("5) Modify an existing Album's information");
		out.println("6) Delete all existing Albums attributed to an Artist");
		out.println("0) Quit\n");
		out.print("? ");
	}

	public int readUserChoice() {

		String input = keyboard.nextLine().trim();
		Integer id = Integer.parseInt(input);
		return id;
	}

	public boolean callEditMenuItem(int choice) throws SQLException {
		switch (choice) {
		case 0:
			return false;
		case 1: // list all artist
			addArtist();
			break;
		case 2: // Search for Artist only
			updateArtist();
			break;
		case 3: // Search Artist for Album
			deleteArtist();
			break;
		case 4: // Search by Album for the Artist who produced it
			addAlbum();
			break;
		case 5: // Add an artist
			updateAlbum();
			break;
		// case 6:
		// deleteAllAlbums();

		}
		return true;
	}

	public void addArtist() throws SQLException {
		MusicDAO artistDAO = new MusicDAO();
		out.println("Type the Name of the Artist you wish to add.\n");
		out.print("> ");
		String artistName = keyboard.nextLine().trim();
		out.println("Type the year the artist officialy began performing. \n");
		out.print("> ");
		String yearStart = keyboard.nextLine().trim();
		out.println("Type the year the artist ended their career under the name above."
				+ " If they are still active hit enter. \n");
		out.print("> ");
		String yearEnd = keyboard.nextLine().trim();
		if (yearEnd == "") {
			yearEnd = null;
		}
		Artist addedArtist = new Artist(artistName, yearStart, yearEnd);
		boolean isDuplicate = artistDAO.checkArtistName(artistName);
		if (isDuplicate) {
			out.println("Artist already exists in database.");
		}

		else {

			edao.saveArtist(addedArtist);
			out.println("Artist saved as #" + addedArtist.getId() + ".\n");
		}

	}

	public void updateArtist() throws SQLException {
		out.println("Please type the database id of the Artist you want to modify.");
		out.println("This information is available via the list artist function.");
		out.println("the id is? >");
		Integer id = readUserChoice();

		Artist enteredId = mdao.getArtistId(id);
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

		String artistName = keyboard.nextLine().trim();

		out.println("Enter the year they became active.");
		out.print("> ");

		String startYear = keyboard.nextLine().trim();

		out.println("Enter the year they ended their career under the name entered above. "
				+ " If they are still active, enter null.");
		out.print("> ");

		String endYear = keyboard.nextLine().trim();
		if (endYear == "") {
			endYear = null;
		}
		Artist updatedArtist = new Artist(id, artistName, startYear, endYear);
		edao.saveArtist(updatedArtist);

		out.println("updated info added to the database");
	}

	public void deleteArtist() throws SQLException {

		out.println("Please type the database id for the artist you wish to delete.");
		out.println("The id is available via the list artist function.");
		out.println(
				"Please note deletion cannot take place without first removing the Artist's works from the database");
		out.println("The id is? ");
		Integer id = readUserChoice();

		Artist a = mdao.getArtistId(id);
		if (a == null) {
			out.println("There was no Artist with id = " + id + "Returning to the main menu.");
			return;
		} else {
			mdao.searchAlbumByArtistId(id);

		}
	}

	public void addAlbum() throws SQLException {

		out.println("Type the name of the album you wish to add.");
		out.print("> ");
		String albumName = keyboard.nextLine().trim();
		out.println("Type the year the album was released.");
		out.print("> ");
		String yearReleased = keyboard.nextLine().trim();
		out.println("Type the ID number of the artist who produced the album");
		out.print("> ");
		Integer artistId = keyboard.nextInt();
		Album addedAlbum = new Album(albumName, yearReleased, artistId);
		edao.saveAlbum(addedAlbum);
		out.println("Album saved as #" + addedAlbum.getAlbumId() + ".\n");

	}

	public void updateAlbum() throws SQLException {

		out.println("Updating requires the id of the album.  ");
		out.println("This is available by searching for the artist's various albums.");
		out.print("id = ?");
		Integer albumId = readUserChoice();

		Album enteredId = mdao.getAlbumId(albumId);
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
		edao.saveAlbum(modifiedAlbum);
	}
	
	public void deleteAllAlbums() throws SQLException {
		
		out.println("The purpose of this selection is to remove all albums for a particular artist.");
		out.println("This will require the artist's id number.  Do you wish to proceed?");
		out.println("Type y to continue.");
		out.print("> ");
		String input = keyboard.nextLine().trim();
		if (input.equalsIgnoreCase("y")){
			out.println("What is the id of the artist?");
			out.print("> ");
		Integer artistId = readUserChoice();
		
		Artist a = mdao.getArtistId(artistId);
		if(a == null){
			out.println("there is no artist with id = " + artistId + "Returning to main menu.");
			return;
		}
		else{
			edao.deleteAllAlbums(artistId);
		}
		
		}
		else{
			out.println("Returning to edit menu");
			return;
		}
	}
}
