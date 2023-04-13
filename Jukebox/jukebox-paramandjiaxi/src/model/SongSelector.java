/**
 * @AUTHOR: Param Patel & Jiaxi Huang
 * @FILE: SongSelector.java
 * @Instructor: Rick Mercer
 * @ASSIGNMENT: Project 12 - Jukebox
 * @COURSE: CSc 335; Spring 2023
 * @Purpose: 
 */

package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class SongSelector {

	private ArrayList<String> songList;
	
	public SongSelector() {
		this.songList = new ArrayList<String>();
	}
	
	
	private void getAllSongs() {
		// wont work !
		String songPath = "/jukebox-paramandjiaxi/songfiles";
		File folder = new File(songPath);
		File[] listOfFiles = folder.listFiles();

		assert listOfFiles != null;
		for (File listOfFile : listOfFiles) {
			if (listOfFile.isFile()) {
				// System.out.println("File " + listOfFiles[i].getName());
				songList.add(listOfFile.getName());
			}
		}
	}
	
	public ArrayList<String> getSongList() {
		Collections.sort(songList);
		return this.songList;
	}
	
	// for gui
	private void setupTableView() {
		// to be implemented 
		// all you param
	}
}
