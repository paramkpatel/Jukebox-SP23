/**
 * @AUTHOR: Param Patel & Jiaxi Huang
 * @FILE: CheckUsers.java
 * @Instructor: Rick Mercer
 * @ASSIGNMENT: Project 12 - Jukebox
 * @COURSE: CSc 335; Spring 2023
 * @Purpose:
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.JukeboxAccount;

class JukeboxAccountTest {

    @Test
    void testGetters() {
        JukeboxAccount a = new JukeboxAccount("Param", "1234");
        assertEquals(a.getUsername(), "Param");
        assertNotEquals(a.getUsername(), "Jiaxi");
        assertEquals(a.getPassword(), "1234");
        assertNotEquals(a.getPassword(), "4321");
    }

}
