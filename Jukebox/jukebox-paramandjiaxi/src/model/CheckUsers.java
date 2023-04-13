/**
 * @AUTHOR: Param Patel & Jiaxi Huang
 * @FILE: CheckUsers.java
 * @Instructor: Rick Mercer
 * @ASSIGNMENT: Project 12 - Jukebox
 * @COURSE: CSc 335; Spring 2023
 * @Purpose: The purpose of this file is to checks if the
 * users exist and if not they get added after they sign up.
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckUsers implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<JukeboxAccount> jukeboxAccounts = new ArrayList<>();

	/**
	 * default accounts for owners of jukebox
	 */
	public CheckUsers() {
		jukeboxAccounts.add(new JukeboxAccount("Jiaxi", "11"));
		jukeboxAccounts.add(new JukeboxAccount("Param", "18"));
	}

	/**
	 * The function is designed to search for an existing user's information in a list
	 * of JukeboxAccount objects. The JukeboxAccount class likely contains information
	 * about a user's account, such as their username, password, and possibly other
	 * relevant information. The function checks if the given name and password match
	 * with the information stored in each JukeboxAccount object in the list.
	 *
	 */
	public JukeboxAccount checkForUserInfo(String username, String password) {
		for (JukeboxAccount player : jukeboxAccounts) {
			if (player.getUsername().equals(username) && player.getPassword().equals(password)) {
				return player;
			}
		}
		return null;
	}

	/**
	 * This function adds a JukeboxAccount to the list of
	 * JukeboxAccounts in the JukeBox class.
	 *
	 * @param jukeboxAccount Account
	 */
	public void addStudent(JukeboxAccount jukeboxAccount) {
		jukeboxAccounts.add(jukeboxAccount);
	}
	
	/**
	 * This gets the usernames of account that are in
	 * the system. 
	 * 
	 * @return array of names of accounts 
	 */
	public ArrayList<JukeboxAccount> getAccountNames() {
	    return jukeboxAccounts;
	}

	


}