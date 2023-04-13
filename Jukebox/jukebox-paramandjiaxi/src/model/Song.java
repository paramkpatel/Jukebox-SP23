/**
 * @AUTHOR: Param Patel & Jiaxi Huang
 * @FILE: Song.java
 * @Instructor: Rick Mercer
 * @ASSIGNMENT: Project 12 - Jukebox
 * @COURSE: CSc 335; Spring 2023
 * @Purpose: This Song class takes care of all the information
 * about the song such as time, artist, path, and name. Also,
 * many methods that track time played, reset play etc..
 */

package model;

import java.io.Serializable;

public class Song implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String title;
    private final String artist;
    private final int playtime;
    private final String fileName;
    private int playCounter;

    public Song(String title, int playtime, String artist, String fileName) {
        this.title = title;
        this.artist = artist;
        this.playtime = playtime;
        this.fileName = fileName;
        this.playCounter = 0;
    }
    // getters

    /**
     * Gets the title of song
     *
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the artist of song
     *
     * @return artist
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * Gets the file name of song
     *
     * @return file name
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Gets the playtime of song
     *
     * @return playtime
     */
    public int getPlaytime() {
        return this.playtime;
    }

    /**
     * Gets the total played time of song
     *
     * @return total played time
     */
    public int getTotalTimePlayed() {
        return playCounter;
    }

    /**
     * Gets the time of the song and formats it into a string
     *
     * @return title
     */
    public String getSecondsToString() {
        return String.format("%d:%02d", playtime / 60, playtime % 60);
    }

    /**
     * Gets the limit of a song in a day.
     *
     * @return title
     */
    public boolean dailyLimit() {
        return playCounter < 3;
    }

    /**
     * Counts how much played
     */
    public void playSong() {
        playCounter++;
    }

    /**
     * To reset for new day.
     */
    public void resetPlays() {
        playCounter = 0;
    }

    /**
     * Gets the path of the song
     *
     * @return path of the file name
     */
    public String getSongPath() {
        return "songfiles/" + fileName;
    }

    /**
     * To display the title and artist.
     *
     * @return toString
     */
    public String toString() {
        return title + " - " + artist;
    }

}