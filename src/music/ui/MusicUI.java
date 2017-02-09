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
		/*out.println("5) Add Artist");
		out.println("6) Modify Artist information");
		out.println("7) Delete an Artist from Database");*/
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
		/*case 5: // Add an artist
			addArtist();
			break;
		case 6: // Modify Artist information   Cases 5 - 7 not ready for use yet
			updateArtist();
			break;
		case 7: // Delete an artist
			deleteArtist();*/
		}
		return true;
	}

	public void listAllArtists() throws SQLException {
		List<Artist> result = dao.listArtistsAndID();
		displayArtistPage(result);

	}

	public void searchOnlyArtist() throws SQLException {
		out.println("Displays artist's name and years active");
		out.println("Search for: ");
		String query = keyboard.nextLine();
		List<Artist> result = dao.searchArtistOnly(query);
		if (result.isEmpty()) {
			out.println("No matches.\n");
		} else {
			displayArtistPage(result);
		}
	}

	public void searchArtistForAlbums() throws SQLException {
		out.println("Displays artist's name and the studio Albums they produced");
		out.println("Search for: ");
		String query = keyboard.nextLine();
		List<Album> result = dao.searchArtistForAlbum(query);
		if (result.isEmpty()) {
			out.println("No matches.\n");
		} else {
			displayAlbumPage(result);
		}
	}

	public void searchByAlbumForArtist() throws SQLException {
		out.println("Displays only the searched for album and the artist who produced it");
		out.println("Search for: ");
		String query = keyboard.nextLine();
		List<Artist> result = dao.searchAlbumForArtist(query);
		if (result.isEmpty()) {
			out.println("No matches.\n");
		} else {
			displayArtistPage(result);
		}
	}

	public void addArtist() throws SQLException {
		MusicDAO anArtist = new MusicDAO();
		out.println("Type the Name of the Artist you wish to add.\n");
		out.println("> ");
		String text = keyboard.nextLine();
		Artist a = new Artist(text);
		boolean isDuplicate = anArtist.checkArtistName(text);
		if ( isDuplicate ){
		out.println("Artist already exists in database.");
		}
		
		else{
		
			dao.saveArtist(a);
		out.println("Artist saved as #" + a.getId() + ".\n");
		}
		
		
	}
	
	public void updateArtist() throws SQLException{
		out.println("Please type the database id of the Artist you want to modify.");
		out.println("This information is available via the list artist function.");
		out.println("the id is? >");
		Integer id = readUserChoice();
		
		Artist a = dao.getArtistId(id);
		if (a == null){
			out.println("There is no Artist with id = " + id + ". Returning to the main menu.");
			return;
			}
		out.println("Here is the existing info about the Artist.");
		out.println(a);
		out.println("Please enter the Artist's name, the year they became active," + "\n"
				+ "and the year they concluded working if applicable." + "\n" +  "If the artist is "
				+ "still active, enter null");
		out.print("> ");
		
		String text = keyboard.nextLine();
		a.setName(text);
		a.setStart(text);
		a.setEnd(text);
		dao.saveArtist(a);
	}
	
	public void deleteArtist() throws SQLException{
		
		out.println("Please type the database id for the artist you wish to delete.");
		out.println("The id is available via the list artist function.");
		out.println("Please note deletion cannot take place without first removing the Artist's works from the database");
		out.println("The id is? ");
		Integer id = readUserChoice();
		
		Artist a = dao.getArtistId(id);
		if (a == null){
			out.println("There was no Artist with id = " + id + "Returning to the main menu.");
			return;
		}
		else{
			dao.searchAlbumByArtistId(id);
			
		}
	}

	public void displayArtistPage(List<Artist> result) {

		for (Artist a : result) {

			out.println(a);

		}

	}

	public void displayAlbumPage(List<Album> result) {

		for (Album a : result) {
			out.print(a);

		}
	}
}
