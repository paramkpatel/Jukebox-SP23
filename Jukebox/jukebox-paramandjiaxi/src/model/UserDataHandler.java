/**
 * @AUTHOR: Param Patel & Jiaxi Huang
 * @FILE: UserDataHandler.java
 * @Instructor: Rick Mercer
 * @ASSIGNMENT: Project 12 - Jukebox
 * @COURSE: CSc 335; Spring 2023
 * @Purpose: The purpose of this file is to keep track of
 * user login info and writing and reading.
 */


package model;

import java.io.Serializable;

import java.io.*;

public class UserDataHandler implements Serializable {
    /**
     * The function uses an ObjectOutputStream to write the JukeboxAccount object to the file.
     * ObjectOutputStream is a class that allows objects to be written to a stream and later
     * read back from that stream. The FileOutputStream class is used to create a new file
     * output stream to write data to the file "users.ser".
     *
     * @param account account that signed up and save data for
     */
    public static void writeUserDataToFile(JukeboxAccount account) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("users.ser"));
            outputStream.writeObject(account);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the data if they user decided to save it to the disk.
     *
     * @return account
     */
    public static JukeboxAccount readUserDataFromFile() {
        JukeboxAccount account = null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("users.ser"));
            account = (JukeboxAccount) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return account;
    }
}

