/**
 * @AUTHOR: Param Patel & Jiaxi Huang
 * @FILE: CheckUsers.java
 * @Instructor: Rick Mercer
 * @ASSIGNMENT: Project 12 - Jukebox
 * @COURSE: CSc 335; Spring 2023
 * @Purpose: This keeps track of the account that are
 * either already in or added later in the jukebox.
 */

package model;

import java.io.Serializable;

public class JukeboxAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String userName;
    private final String password;
    private int totalTime;
    private int playedToday;

    public JukeboxAccount(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.totalTime = 0;
    }

    /**
     * Get the username of an account
     *
     * @return username
     */
    public String getUsername() {
        return this.userName;
    }

    /**
     * Get the username of an account
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Times played today
     *
     * @return played number
     */
    public int getNumSongsToday() {
        return playedToday;
    }

    /**
     * Gets the total time
     *
     * @return total time
     */
    public int getTotalTimes() {
        return totalTime;
    }

    /**
     * New day so set them to 0
     */
    public void updateDay() {
        playedToday = 0;
        totalTime = 0;
    }

    /**
     * Keeps track of the total seconds
     *
     * @param seconds seconds
     */
    public void trackSeconds(int seconds) {
        totalTime += seconds;
    }

    /**
     * Times it was played
     */
    public void dayTotal() {
        playedToday += 1;
    }

    /**
     * This function checks if the song was played 3 times
     * in the day or not.
     *
     * @param song song to check
     * @return boolean
     */
    public boolean canPlaySong(Song song) {
        if (playedToday >= 3) {
            return false;
        }
        return totalTime + song.getPlaytime() <= 90000;
    }
}
