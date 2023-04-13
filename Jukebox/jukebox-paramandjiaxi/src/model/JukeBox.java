/**
 * @AUTHOR: Param Patel & Jiaxi Huang
 * @FILE: Jukebox.java
 * @Instructor: Rick Mercer
 * @ASSIGNMENT: Project 12 - Jukebox
 * @COURSE: CSc 335; Spring 2023
 * @Purpose: This code defines a JukeBox class with several methods to manage a jukebox.
 * It has an ArrayList to store the available songs, another ArrayList to store the
 * songs currently queued up for playback, and a CheckUsers object to validate user
 * credentials. It also has a JukeboxAccount object to represent the current player
 * and a Song object to represent the currently playing song.
 */

package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class JukeBox implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final CheckUsers userValidator;
    private final ArrayList<Song> songQueue;
    private final ArrayList<Song> songArrayList;
    private JukeboxAccount currPlayer;
    private Song currSong;


    public JukeBox() {
        this.userValidator = new CheckUsers();
        songQueue = new ArrayList<>();
        currSong = null;
        currPlayer = null;
        LocalDate date = LocalDate.now();
        songArrayList = new ArrayList<>();
        // adds 7 songs to the ArrayList
        songArrayList.add(new Song("Pokemon Capture", 5, "Pikachu", "Capture.mp3"));
        songArrayList.add(new Song("Danse Macabre", 34, "Kevin MacLeod", "DanseMacabreViolinHook.mp3"));
        songArrayList.add(new Song("Determined Tumbao", 20, "FreePlay Music", "DeterminedTumbao.mp3"));
        songArrayList.add(new Song("LopingSting", 5, "Kevin MacLeod", "LopingSting.mp3"));
        songArrayList.add(new Song("Swing Cheese", 15, "FreePlay Music", "SwingCheese.mp3"));
        songArrayList.add(new Song("The Curtain Rises", 28, "Kevin MacLeod", "TheCurtainRises.mp3"));
        songArrayList.add(new Song("UntameableFire", 282, "Pierre Langer", "UntameableFire.mp3"));
    }

    /**
     * Returns the userValidator object used to validate JukeboxAccount credentials.
     *
     * @return the userValidator object
     */
    public CheckUsers valid() {
        return userValidator;
    }

    /**
     * Returns the songQueue ArrayList containing the songs currently queued up for playback.
     *
     * @return the songQueue ArrayList
     */
    public ArrayList<Song> getSongQueue() {
        return songQueue;
    }

    /**
     * Returns the JukeboxAccount object representing the current player.
     *
     * @return the current player JukeboxAccount object
     */
    public JukeboxAccount getCurrentPlayer() {
        return currPlayer;
    }

    /**
     * Sets the current player JukeboxAccount object to the given object.
     *
     * @param jukeboxPlayer object to set as the current player
     */
    public void setCurrentPlayer(JukeboxAccount jukeboxPlayer) {
        currPlayer = jukeboxPlayer;
    }

    /**
     * Returns the Song object representing the currently playing song.
     *
     * @return the current song
     */
    public Song getCurrentSong() {
        return currSong;
    }

    /**
     * Sets the current song to the given Song object.
     *
     * @param song the Song object to set as the current song
     */
    public void setCurrentSong(Song song) {
        currSong = song;
    }

    /**
     * Returns the songArrayList ArrayList containing
     * all the available songs in the Jukebox.
     *
     * @return the songArrayList ArrayList
     */
    public ArrayList<Song> getSongArrayList() {
        return songArrayList;
    }

    /**
     * Verifies the credentials of a student user and sets them as
     * the current player if successful.
     *
     * @param username the username of the student user
     * @param password the password of the student user
     * @return true if the credentials are valid and the user
     * is set as the current player, false otherwise
     */
    public boolean verifyStudent(String username, String password) {
        JukeboxAccount user = userValidator.checkForUserInfo(username, password);
        if (user != null) {
            currPlayer = user;
            return true;
        }
        return false;
    }


    /**
     * Returns a formatted string indicating the remaining time in the
     * Jukebox session and the number of songs played by the current player.
     *
     * @return a string in the format "{songsPlayed} selected, {HH}:{MM}:{SS}"
     */
    public String getSessionText() {
        // Initialize the remaining time and number of songs played variables
        long time = 90000;
        int songsPlayed = 0;
        // If a player is currently active, calculate the remaining time and number of songs played variables based on the player's activity
        if (currPlayer != null) {
            time = 90000 - currPlayer.getTotalTimes();
            songsPlayed = currPlayer.getNumSongsToday();
        }
        // Convert the remaining time to hours, minutes, and seconds
        long hours = TimeUnit.SECONDS.toHours(time);
        time -= TimeUnit.HOURS.toSeconds(hours);
        long minutes = TimeUnit.SECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toSeconds(minutes);
        long seconds = TimeUnit.SECONDS.toSeconds(time);
        // Format and return the session text string
        return String.format("%d selected, %02d:%02d:%02d", songsPlayed, hours, minutes, seconds);
    }

    /**
     * Removes the first song from the song queue and updates the current song if necessary.
     *
     * @return the removed song, or null if the queue is empty
     */
    public Song dequeue() {
        Song toRemove = songQueue.remove(0);
        if (!songQueue.isEmpty()) {
            currSong = songQueue.get(0);
            return toRemove;
        }
        currSong = null;
        return toRemove;
    }

    /**
     * Adds a new song to the song queue if it satisfies the daily limit
     * condition and can be played by the current player.
     *
     * @param newSong the song to be added to the queue
     * @return true if the song was added successfully, false otherwise
     */
    public boolean enqueue(Song newSong) {
        // no player is currently active, return false
        if (currPlayer == null) {
            return false;
        } else if (newSong.dailyLimit() && currPlayer.canPlaySong(newSong)) {
            if (songQueue.isEmpty()) {
                currSong = newSong;
            }
            songQueue.add(newSong);
            currPlayer.dayTotal();
            currPlayer.trackSeconds(newSong.getPlaytime());
            return true;
        }
        return false;
    }
}
