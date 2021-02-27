package javafx.ex02;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label label;

    public void initialize() {
    	String mediaTitle = MediaPlayerExample.getMediaTitle();
    	if(mediaTitle == null) {
    		return;
    	}
        label.setText("Now Playing: " + mediaTitle);
    }
}
