/**
 * @AUTHOR: Param Patel & Jiaxi Huang
 * @FILE: JukeboxGUI.java
 * @Instructor: Rick Mercer
 * @ASSIGNMENT: Project 12 - Jukebox
 * @COURSE: CSc 335; Spring 2023
 * @Purpose: This is a GUI for the Jukebox.
 */

package controller_view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import model.JukeBox;
import model.JukeboxAccount;
import model.Song;
import model.UserDataHandler;

import java.io.*;
import java.net.URI;
import java.util.Optional;

public class JukeboxGUI extends Application {

    private final static String fileName = "jukebox-info.ser";
    private final GridPane songGridPane = new GridPane();
    private final Button buttonQ = new Button("Queue Song(s)");
    private final BorderPane border = new BorderPane();
    private final Button signupButton = new Button("Sign Up!");
    private final Button loginButton = new Button("Login");
    private final Button logoutButton = new Button("Logout");
    private final Label usernameLabel = new Label("Username");
    private final Label passwordLabel = new Label("     Password");
    private final GridPane userPane = new GridPane();
    private final TableView<Song> songTableView = new TableView<>();
    private final ListView<Song> songListView = new ListView<>();
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Alert alert = new Alert(AlertType.INFORMATION);
    private JukeBox JUKEBOX;
    private Label loginLabel = new Label("Login!");
    private ObservableList<Song> songObservableList;
    private ObservableList<Song> songObservableList1 = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Center JukeBox");
        Scene scene = new Scene(border, 750, 650);
        JUKEBOX = new JukeBox();
        JukeboxAccount account = UserDataHandler.readUserDataFromFile();
        credentialsPanel();
        startUpOption();
        songObservableList = FXCollections.observableArrayList(JUKEBOX.getSongArrayList());
        songListViewer();
        buttonQ.setOnAction(new SongButtonHandler());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new WritePersistentObjectOrNot());
    }

    /**
     * This method plays the current song in the JUKEBOX by creating a MediaPlayer object
     * and playing the media file associated with the current song. It also sets an EndOfMedia
     * handler to be called when the song finishes playing.
     */
    public void playSong() {
        File file = new File(JUKEBOX.getCurrentSong().getSongPath());
        URI uri = file.toURI();
        Media media = new Media(uri.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new EndOfSongHandler());
        mediaPlayer.play();
    }

    /**
     * Displays the song list and song queue in a JavaFX TableView and ListView respectively.
     * Adds columns for number of times played, title, artist, and time for the song list.
     * Also sets up a label for each section and adds them to a GridPane layout, which is then
     * added to the top of the border. Uses ObservableList to populate the table and list with
     * data from the Jukebox's Song and SongQueue objects.
     */
    @SuppressWarnings("unchecked")
    public void songListViewer() {
        TableColumn<Song, String> plays = new TableColumn<>("Plays");
        TableColumn<Song, String> title = new TableColumn<>("Title");
        TableColumn<Song, String> artist = new TableColumn<>("Artist");
        TableColumn<Song, String> time = new TableColumn<>("Time");
        songTableView.getColumns().addAll(plays, title, artist, time);
        plays.setCellValueFactory(new PropertyValueFactory<>("totalTimePlayed"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        time.setCellValueFactory(new PropertyValueFactory<>("SecondsToString"));

        songTableView.setItems(songObservableList);
        songTableView.setMinWidth(365);
        songGridPane.add(songTableView, 0, 1);

        buttonQ.setPadding(new Insets(10));
        buttonQ.setMinWidth(365);

        songGridPane.add(buttonQ, 0, 2);

        Label songListLabel = new Label("Song List");
        songListLabel.setFont(Font.font("Corbel", 20));

        songGridPane.add(songListLabel, 0, 0);

        songGridPane.setHgap(15);
        songGridPane.setVgap(15);
        songGridPane.setPadding(new Insets(10));

        songObservableList1 = FXCollections.observableArrayList(JUKEBOX.getSongQueue());
        songListView.setItems(songObservableList1);
        songListView.setMinWidth(300);

        Label songQueueLabel = new Label("Song Queue");
        songQueueLabel.setFont(Font.font("Century Gothic", 20));

        songGridPane.add(songQueueLabel, 1, 0);
        songGridPane.add(songListView, 1, 1);

        border.setTop(songGridPane);

    }


    /**
     * Sets up the login panel containing UI elements for user login, sign up, and logout.
     * Provides functionality to verify user credentials and display appropriate messages based on login status.
     * Adds the login panel to the bottom of the main UI border.
     */
    public void credentialsPanel() {
        loginButton.setMinWidth(60);
        loginButton.setMinHeight(30);
        loginButton.setStyle("-fx-background-radius:50px;");
        signupButton.setStyle("-fx-background-radius:50px;");
        usernameLabel.setFont(Font.font("Arial", 13));
        passwordLabel.setFont(Font.font("Arial", 13));
        loginLabel = new Label("Please login to play a song!");

        userPane.add(loginButton, 2, 1);
        userPane.add(signupButton, 3, 1);
        userPane.add(logoutButton, 2, 2);
        userPane.add(usernameLabel, 0, 1);
        userPane.add(passwordLabel, 0, 2);
        userPane.add(passwordField, 1, 2);
        userPane.add(usernameField, 1, 1);
        userPane.add(loginLabel, 1, 0);
        signupButton.setOnAction((event) -> addNewUser());
        loginButton.setOnAction((event) -> {
            if (JUKEBOX.verifyStudent(usernameField.getText(), passwordField.getText())) {
                loginLabel.setText(JUKEBOX.getSessionText());
                usernameField.clear();
                passwordField.clear();
            } else {
                loginLabel.setText("Invalid Username/Password");
            }
        });

        logoutButton.setMinHeight(30);
        logoutButton.setMinWidth(62);
        logoutButton.setStyle("-fx-background-radius:50px");

        logoutButton.setOnAction((event) -> {
            JUKEBOX.setCurrentPlayer(null);
            loginLabel.setText("Please login to play a song!");
        });

        userPane.setHgap(10);
        userPane.setVgap(10);
        userPane.setPadding(new Insets(0, 10, 20, 150));
        border.setBottom(userPane);

    }

    /**
     * Adds a new user by creating a dialog box to prompt the user to enter a new username and password.
     * If the user enters a valid username and password and clicks "Sign Up", a new JukeboxAccount is
     * created with the provided username and password, and the account is added to the list of valid
     * JukeboxAccounts. The user's information is also written to a file using UserDataHandler. If the
     * user cancels the dialog box, nothing is added or written to file.
     */
    private void addNewUser() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Signing up");
        dialog.setHeaderText("Enter new Username and Password");
        ButtonType loginButtonType = new ButtonType("Sign Up", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField username = new TextField();
        username.setPromptText("username");
        PasswordField password = new PasswordField();
        password.setPromptText("password");
        grid.add(new Label("Username: "), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password: "), 0, 1);
        grid.add(password, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
        	// check for unique name 
            if (isUsernameTaken(usernamePassword.getKey())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Username already taken");
                alert.setContentText("Please choose a different username");
                alert.showAndWait();
                return;
            }
            System.out.println("New Username: " + usernamePassword.getKey() + "- New Password: " + usernamePassword.getValue());
            JukeboxAccount newAccount = new JukeboxAccount(usernamePassword.getKey(), usernamePassword.getValue());
            JUKEBOX.valid().addStudent(newAccount);
            UserDataHandler.writeUserDataToFile(newAccount);
        });
    }
    
    /**
     * This method takes care of checking if the the username 
     * that was used to sign up already exists or not. Searches 
     * through the array. 
     * @param username name to check for 
     * @return true if taken, else returns false. 
     */
    private boolean isUsernameTaken(String username) {
        for (JukeboxAccount account : JUKEBOX.valid().getAccountNames()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets up the Jukebox by prompting the user to choose between using an old queue or starting a new one.
     * If the user chooses to use the old queue by pressing "OK", the JukeBox object is deserialized from a
     * file using ObjectInputStream, and the JukeBox's current player and current song are set if they exist.
     * If the user chooses to start a new queue by pressing "CANCEL", nothing happens.
     */
    private void startUpOption() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Start up option");
        alert.setHeaderText("Read saved data?");
        alert.setContentText("Press cancel while system testing.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                FileInputStream fileInput = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileInput);
                JUKEBOX = (JukeBox) in.readObject();
                in.close();
                if (JUKEBOX.getCurrentPlayer() != null) {
                    loginLabel.setText(JUKEBOX.getSessionText());
                } else {
                    loginLabel.setText("Login first!");
                }
                if (JUKEBOX.getCurrentSong() != null) {
                    playSong();
                }
            } catch (FileNotFoundException ignored) {
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private class EndOfSongHandler implements Runnable {
        /**
         * When song ends, and it transitions to the next one in the queue.
         */
        @Override
        public void run() {
            songObservableList1.remove(0);
            JUKEBOX.dequeue();
            if (!JUKEBOX.getSongQueue().isEmpty()) {
                JUKEBOX.setCurrentSong(JUKEBOX.getSongQueue().get(0));
                playSong();
            }
        }
    }

    /**
     * A class that handles an ActionEvent for when a song button is clicked. The selected song from the
     * songTableView is retrieved, and if it is not null, the JukeBox checks if the current player can play
     * the song. If the player can play the song, the song is added to the queue, added to the
     * songObservableList1, and played. The message label is updated with the JukeBox's session text.
     * If the song cannot be added to the queue, an alert is shown indicating that the maximum number of
     * plays has been reached for the song. If the player cannot play the song, an alert is shown indicating
     * that the player has reached their limit. If there is no current player, an alert is shown indicating
     * that the user must log in to play music.
     */
    private class SongButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent arg) {
            Song songToBePlayed = songTableView.getSelectionModel().getSelectedItem();
            if (songToBePlayed != null) {
                if (JUKEBOX.getCurrentPlayer() != null) {
                    if (JUKEBOX.getCurrentPlayer().canPlaySong(songToBePlayed)) {
                        if (JUKEBOX.enqueue(songToBePlayed)) {
                            if (songObservableList1.isEmpty()) {
                                playSong();
                            }
                            songObservableList1.add(songToBePlayed);
                            loginLabel.setText(JUKEBOX.getSessionText());
                            songToBePlayed.playSong();
                            songTableView.refresh();
                            songListView.refresh();
                            songListView.getSelectionModel().selectFirst();
                        } else {
                            alert.setHeaderText(songToBePlayed.getTitle() + " max plays reached");
                            alert.showAndWait();
                        }
                    } else {
                        alert.setHeaderText(JUKEBOX.getCurrentPlayer().getUsername() + " has reached the limit");
                        alert.showAndWait();
                    }
                } else {
                    alert.setHeaderText("Login to play music!");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * A class that handles a window event, which prompts the user to confirm whether they want to save
     * data before shutting down the system. If the user confirms by pressing "OK", the JukeBox object is
     * serialized and written to a file using ObjectOutputStream. If the user cancels by pressing "CANCEL",
     * nothing happens.
     */
    private class WritePersistentObjectOrNot implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent event) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Shut down Option");
            alert.setHeaderText("Save data?");
            alert.setContentText("Press cancel while system testing.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    FileOutputStream fileOutput = new FileOutputStream(fileName);
                    ObjectOutputStream out = new ObjectOutputStream(fileOutput);
                    out.writeObject(JUKEBOX);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}