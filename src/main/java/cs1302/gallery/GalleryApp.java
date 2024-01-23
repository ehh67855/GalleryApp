package cs1302.gallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URLEncoder;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.control.ProgressBar;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import com.google.gson.JsonArray;
import java.net.URL;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import javafx.scene.control.Separator;
import javafx.animation.KeyFrame;

import java.time.LocalTime;
import javafx.util.Duration;
import javafx.animation.Timeline;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


/**
 * Represents an iTunes GalleryApp.
 */
public class GalleryApp extends Application {

    private VBox main = new VBox();
    private JsonArray urlList;
    private boolean playBoolean = false;

    // menu
    HBox mainMenu = new HBox();
    MenuBar menuBar = new MenuBar();
    Menu file = new Menu("File");
    MenuItem exit = new MenuItem("Exit");
    Menu help = new Menu("Help");
    MenuItem aboutMe = new MenuItem("About EVAN-HAMMAM");
    HBox hStatus = new HBox();
    ProgressBar progressBar = new ProgressBar();
    ToolBar toolBarMenu = new ToolBar(mainMenu, new Separator(), progressBar);
    //Toolbar
    Button pause = new Button("Play");
    Button update = new Button("Update");
    Text search = new Text("Search Query:");
    TextField textField = new TextField("Evan");
    ToolBar toolBar = new ToolBar(pause,search,textField, update);
    //swapping
    Timeline timeline;
    Random rand = new Random();
    ImageView[] otherImgViews;
    int width;
    //imageview
    TilePane tilePane = new TilePane();
    ImageView[] imgViews = new ImageView[20];
    //alert
    Alert alert = new Alert(AlertType.ERROR);
    //aboutme
    Alert about = new Alert(AlertType.INFORMATION);
    ImageView aboutImage = new ImageView( new Image("file:resources/Evan.jpg"));
    
    /*
    VBox aboutMain = new VBox( 10.0, name, version, aboutImage, email);
    Scene aboutScene = new Scene(aboutMain);
    Stage aboutStage = new Stage();
*/
    /**
     * Sets the behavior and charectaristics of the different features.
     {@inheritdoc} */
    @Override
    public void init() {
        //menu bar
        file.getItems().add(exit);
        exit.setOnAction( (e) -> {
            Platform.exit();
            System.exit(0);
        });
        help.getItems().add(aboutMe);
        help.setOnAction( (e) -> alertAboutMe());
        menuBar.getMenus().addAll(file, help);
        mainMenu.getChildren().add(menuBar);
        main.getChildren().addAll(toolBarMenu,toolBar);
        //toolbar
        update.setOnAction(this::updateImages);
        pause.setOnAction(this::play);
        //center view
        tilePane.setPrefColumns(5);
        for ( int i = 0 ; i < imgViews.length ; i++ ) {
            imgViews[i] = new ImageView();
            imgViews[i].setPreserveRatio(true);
            tilePane.getChildren().add(imgViews[i]);
        }
        main.getChildren().addAll(tilePane);
        hStatus.getChildren().add(progressBar);
        main.getChildren().add(hStatus);
        
        //timeline
        EventHandler<ActionEvent> handler = event -> swap();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), handler);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        
        //aboutMe

/*
        aboutImage.setFitWidth(100);
        aboutImage.setPreserveRatio(true);
        Platform.runLater( () -> aboutStage.setScene(aboutScene));
        */
        
    }

    /** Fires the update button and sets the size of the main window.
        {@inheritdoc} */
    @Override
    public void start(Stage stage) {

        update.fire(); //for intitial query
        Scene scene = new Scene(main);
        stage.setMinWidth(510);
        stage.setMinHeight(500.00);
        //stage.setMaxHeight(480);
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.sizeToScene();

        stage.show();
        pause.fire();

    } // start

    /** Sets the progress of the progress bar on the
     * java application thread.
     * @param progress the incriment to increase
     */

    private void setProgress(final double progress) {
        Platform.runLater(() -> progressBar.setProgress(progress));
    } // setProgress

    /**
     *Sets the new images on the javafx application thread.
     *@param img the image to add
     *@param tp the tilepane to add on to
     *@param i the index to add on to
     */
    private void setImage (final Image img, final TilePane tp, final int i) {
        Platform.runLater( () -> tp.getChildren().set(i, new ImageView(img)) );
    }

    /**
     * Creates an alert box when there are less then 21
     *items in a query.
     */

    private void alertUnder21() {

        Thread alertThread = new Thread ( () -> {
            alert.setTitle("Error!");
            alert.setHeaderText("Unable to Complete Query");// line 3
            String message = ("There were less then 21 unique images found." +
                " \nPlease enter different query." +
                "\nPlease press OK to continue.");
            alert.setContentText(message);
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(480, 320);
            Platform.runLater(() -> alert.show()); //showing thr alert
        });
        alertThread.setDaemon(true); //run thread
        alertThread.start();

        /** Sets the aboutMe stage to showAndWait */
    }

    /** Sets the aboutMe stage to showAndWait. */
    private void alertAboutMe() {
        //creating about me
        about.setTitle("About Evan");
        about.setHeaderText("About Evan");
        aboutImage.setFitWidth(100);
        aboutImage.setPreserveRatio(true);
        aboutImage.setRotate(90); //updise image


        String aboutMessage = ("Name: Evan Hammam\n" +
                    "Version: 1.0\n" + 
                    "Email: ehh67855@uga.edu");
        about.setContentText(aboutMessage); //add image
        about.setGraphic(aboutImage);
        about.setResizable(true);
        about.getDialogPane().setPrefWidth(250);
        about.showAndWait();
    }
    


    /** Updates the images in the TilePane.
     * @param e the action even to be used
     */

    public void updateImages(ActionEvent e) {
        boolean updateBoolean = playBoolean;
        if (playBoolean) {
            pause.fire();
        } //change play button
        Thread thread = new Thread( () -> {
            update.setDisable(true); //disable button
            try { //creating array pof images
                String keyPair = URLEncoder.encode(textField.getText(), "UTF-8");
                String sUrl = "https://itunes.apple.com/search?term=" + keyPair + "&limit=200&media=music";
                URL url = new URL(sUrl);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                JsonElement je = JsonParser.parseReader(reader);
                JsonObject root = je.getAsJsonObject();
                JsonArray results = root.getAsJsonArray("results");
                this.urlList = results;
                HashSet<String> set = new HashSet<>(); //ignoring all duplicates
                for (int i = 0 ; i < results.size() ; i++) {
                    JsonObject result = results.get(i).getAsJsonObject();
                    if (results != null  && //cannot be null
                        !result.get("artworkUrl100").getAsString().equals("") ) {       
                        set.add(result.get("artworkUrl100").getAsString());
                    }
                }
                ArrayList<String> urlArray = new ArrayList<>(set); //unique array
                if (urlArray.size() < 21) {
                    alertUnder21();
                } else {
                    setProgress(0); //progress bar 0
                    int index = 0;
                    otherImgViews = new ImageView[urlArray.size() - 20];
                    for (int i = 0 ; i < urlArray.size() ; i++) {
                        Image albumImage = new Image(urlArray.get(i), 100, 100, false, false);
                        if ( i < 20 ) { //adding to tilepane
                            setProgress(1.0 * i / 20);
                            setImage(albumImage, tilePane, i);
                            imgViews[i] = new ImageView(albumImage);
                        } else {
                            otherImgViews[index] = new ImageView(albumImage);
                            index++;
                        } //creating array of remaining images
                    }
                    setProgress(1);
                }
                update.setDisable(false);
            } catch ( Exception updateException) {
                System.out.println(updateException.getMessage());
            }
            Platform.runLater( () -> { //update play button bool
                if (updateBoolean) {
                    pause.fire();
                }
            });
        });
        thread.setDaemon(true);
        thread.start(); //start thread
    }


    /** Performs a swap between a used and unused image. */
    public void swap() {
        int currentRand = rand.nextInt(imgViews.length - 1); //random indecies
        int remainingRand = rand.nextInt(otherImgViews.length - 1);
        ImageView item = imgViews[currentRand];
        ImageView otherItem = otherImgViews[remainingRand];
        Platform.runLater( () -> tilePane.getChildren().set(currentRand, otherItem) ); //update
        imgViews[currentRand] = otherItem; //swap
        otherImgViews[remainingRand] = item; 
    }

    /** Uses a boolean to switch between the play/button actions.
     * @param playEvent the action event for the play/pause functionality
     */
    public void play(ActionEvent playEvent) {
        if (playBoolean) { //button switcing
            playBoolean = false;
            pause.setText("Play");
        } else {
            playBoolean = true;
            pause.setText("Pause");
        }
        try { //action switching
            if (playBoolean) {
                Thread playThread = new Thread ( () -> {
                    timeline.play();
                });
                playThread.setDaemon(true);
                playThread.start();
            } else {
                timeline.pause();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
} // GalleryApp
