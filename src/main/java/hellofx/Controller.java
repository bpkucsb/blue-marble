package hellofx;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Controller implements Initializable {
	
	private BlueMarble blueMarble = new BlueMarble();    // create blue marble class from which images can be loaded
	private boolean isEnhanced = false;                  // if image should be enhanced
	private boolean isBW = false;                        // if image should be black and white (grayscale)
	
    @FXML
    private ImageView epicImage;      // displays image of the earth
	
    /**
     * this method initializes the layout white today's image of the earth and disables the enhance button
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        epicImage.setImage(new Image(BlueMarble.getMostRecentImage()));
        enhanceButton.setDisable(true);
    }

    @FXML
    private DatePicker datePicker;      // date for which we want to display image

    @FXML
    private Button enhanceButton;       // whether to enhance the image

    @FXML
    private Button bwButton;            // whether to make image black and white

    @FXML
    private Text warnText;

    /**
     * If black and white option is selected change image to grayscale
     * @param event black and white option selected by user
     */
    @FXML
    void bwClick(ActionEvent event) {
    	ColorAdjust desaturate = new ColorAdjust();
    	this.isBW = !this.isBW;
    	if (isBW) {
    		desaturate.setSaturation(-1);
    		epicImage.setEffect(desaturate);
    		
    	} else {
    		desaturate.setSaturation(0);
    		epicImage.setEffect(desaturate);
    	}
    }

    /**
     * If enhance option is selected then apply it in BlueMarble class
     * @param event enhance option selected by user
     */
    @FXML
    void enhanceClick(ActionEvent event) {
    	this.isEnhanced = !this.isEnhanced;
    	blueMarble.setEnhanced(isEnhanced);
    	epicImage.setImage(new Image(blueMarble.getImage()));
    }

    /**
     * Gets date input from user. If date is before June 2018 allow for enhance option
     * If date is on or after today's date return error message
     * @param event
     */
    @FXML
    void setDate(ActionEvent event) {
    	String date = datePicker.getValue().toString();
    	String today = LocalDate.now().toString();
    	String enhanceDate = "2018-06-30";
    	// check if enhance option available
    	if (date.compareTo(enhanceDate) > 0) {
    		enhanceButton.setDisable(true);
    	} else {
    		enhanceButton.setDisable(false);
    	}
    	// check if input date is less than today's date
    	// return error message otherwise.
    	if (today.compareTo(date) > 0) {
    		blueMarble.setDate(date);
    		epicImage.setImage(new Image(blueMarble.getImage()));
    		warnText.setText("");
    	} else {
    		warnText.setText("Must be before today's date!");
    	}
    		
    }

}
