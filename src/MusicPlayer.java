import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
 
public class MusicPlayer {

	static int currentSong = 0;
	static boolean shuffled = false;
	static String currentSongName;
	static List<String> songs = new ArrayList<>();
	static List<String> shuffledSongs = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("Loading Songs..");

		try {
			songs = readCSV("songs.csv");
			shuffledSongs.addAll(songs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Collections.sort(songs);
		System.out.println("Songs " + songs.toString());
		userInterface();

	}

	private static void userInterface() {
		System.out.println("My Music Player");
		System.out.println("<====================>");
		System.out.println("1) Play");
		System.out.println("2) Shuffle ");
		System.out.println("3) Previous");
		System.out.println("4) Next");
		if(shuffled) {
			System.out.println("5) Unshuffle");
		}
		Scanner userInput = new Scanner(System.in);
		int input = userInput.nextInt();
		playerOptions(input);
	}

	private static void playerOptions(int input) {
		switch (input) {
		case 1:
			play();
			userInterface();
			break;
		case 2:
			shuffle();
			userInterface();
			break;
		case 3:
			playPrev();
			userInterface();
			break;
		case 4:
			playNext();
			userInterface();
			break;
		case 5:
			unshuffle();
			userInterface();
			break;
		}
	}

	public static ArrayList<String> readCSV(String fileName) throws FileNotFoundException, IOException {
		ArrayList<String> songs = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");
			String songName = fields[0];
			songs.add(songName);
		}
		br.close();
		return songs;
	}

	public static void play() {
		if (!shuffled) {
			System.out.println("Playing := " + songs.get(currentSong));
		} else {
			System.out.println("Playing := " + shuffledSongs.get(currentSong));
		}

	}

	/*
	 * Description: Suffling playlist
	 */
	public static void shuffle() {
		shuffled = true;
		currentSongName = songs.get(currentSong);
		int i = songs.size();
/*		System.out.println("Length of the arrayList : " + i + currentSongName);*/
		int j;
		String temp;
		while (--i >= 0) {
			double ran = Math.random();
			j = (int) Math.floor(ran * (i + 1));
			// System.out.println("j" + j);
			temp = shuffledSongs.get(j);
			shuffledSongs.set(j, shuffledSongs.get(i));
			shuffledSongs.set(i, temp);
		}
		System.out.println("ShuffledSongs " + shuffledSongs.toString());
		currentSong = shuffledSongs.indexOf(currentSongName);
		play();
	}

	/*
	 * Description: playing previous song
	 */
	public static void playPrev() {
		currentSong--;
		if (currentSong < 0) {
			currentSong = songs.size() - 1;
		}
		play();
	}

	/*
	 * Description: playing next song
	 */
	public static void playNext() {
		currentSong++;
		if (currentSong > songs.size() - 1) {
			currentSong = 0;
		}
		play();
	}

	/*
	 * Description: Unsuffling the playlist
	 */
	public static void unshuffle() {
		if(shuffled) {
			shuffled = false;
			currentSongName = shuffledSongs.get(currentSong);
			currentSong = songs.indexOf(currentSongName);
			play();
		}
	}
}
