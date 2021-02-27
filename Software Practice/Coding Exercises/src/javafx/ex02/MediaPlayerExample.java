package javafx.ex02;

import java.net.URL;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.media.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This MediaPlayer uses third-party JavaFX jars which you will need to install.
 * One possible source for the jars is below. So far as videos go, this
 * MediaPlayer only supports mp3 files.
 * 
 * @see <a href="https://gluonhq.com/products/javafx/">JavaFX Download</a>
 * 
 * @author Beppe Sabatini
 *
 */
public class MediaPlayerExample extends Application {

	private static String mediaTitle;
	private static String mediaFilename;
	private static String fxmlFilename = "/javafx/ex02/mediaplayer.fxml";

	public static void main(String[] args) {
		if (args.length == 2) {
			mediaTitle = args[0];
			mediaFilename = args[1];
		} else {
			System.out.println("Usage: MediaPlayerExample <mediaTitle> <mediaFilename>");
			return;
		}
		launch(args);
	}

	public static String getMediaTitle() {
		if (MediaPlayerExample.mediaTitle == null) {
			System.out.println("Media Title is not initialized");
			return (null);
		}
		return (MediaPlayerExample.mediaTitle);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start(Stage primaryStage) throws Exception {

		URL fxmlUrl = MediaPlayerExample.class.getResource(fxmlFilename);
		Parent rootFxml = FXMLLoader.load(fxmlUrl);

		Media media = null;
		try {
			media = new Media(mediaFilename);
			if (media.getError() == null) {
				media.setOnError(new Runnable() {
					public void run() {
						System.out.println("Asynchronous error in Media object.");
					}
				});
			}
		} catch (Exception mediaException) {
			mediaException.printStackTrace();
		}

		MediaPlayer mediaPlayer = null;
		try {
			mediaPlayer = new MediaPlayer(media);
		} catch (Exception mediaPlayerException) {
			mediaPlayerException.printStackTrace();
		}
		if (mediaPlayer.getError() == null) {
			mediaPlayer.setOnError(new Runnable() {
				public void run() {
					System.out.println("Asynchronous error in MediaPlayer object.");
				}
			});
		}

		// Add a mediaView, to display the media.
		// This mediaView is added to a Pane.
		MediaView mediaView = null;
		try {
			mediaView = new MediaView(mediaPlayer);
		} catch (Exception mediaViewException) {
			mediaViewException.printStackTrace();
		}
		mediaView.setOnError(new MediaViewErrorEventHandler());

		// Add to scene
		Group rootGroup = new Group(rootFxml, mediaView);
		Scene scene = new Scene(rootGroup, 650, 500);

		// Show the stage
		primaryStage.setTitle("Media Player");
		primaryStage.setScene(scene);
		primaryStage.show();

		// Play the media once the stage is shown
		mediaPlayer.play();
	}
}

@SuppressWarnings("hiding")
final class MediaViewErrorEventHandler<MediaErrorEvent> implements EventHandler<Event> {
	public void handle(Event t) {
		System.out.println("Asynchronous error in MediaView initialization.");
	}
}
