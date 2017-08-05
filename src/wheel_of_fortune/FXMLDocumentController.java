/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheel_of_fortune;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Karol
 */
public class FXMLDocumentController implements Initializable {
  final private int fullRotation = 360;
  final private int oneStep = 20;
  private int previousRotation = 0;
  
  private Media media;
  private MediaPlayer mp;
  
  private boolean isRotating = false;
  
  private List<Integer> drawedPositions;
  
  private List<String> questions;
  
  private Scene scene;
  
  @FXML
  private Circle wheel;
  
  @FXML
  private Circle rightWheel;
  
  @FXML
    private Circle leftWheel;
  
  @FXML
  private ImageView pointer;
   
  @FXML
  private void handleOnKeyPressed(KeyEvent event) {
    if (event.getCode().equals(KeyCode.SPACE) && !isRotating) {
      isRotating = true;
      draw();
    } else if (event.getCode().equals(KeyCode.DIGIT1) && !isRotating) {
      Image img = new Image(getClass().getResource("/left_disabled.png").toString());
      leftWheel.setFill(new ImagePattern(img));
    } else if (event.getCode().equals(KeyCode.DIGIT2) && !isRotating) {
      Image img = new Image(getClass().getResource("/right_disabled.png").toString());
      rightWheel.setFill(new ImagePattern(img));
    }
  }
  
  public void setScene(Scene scene) {   
    this.scene = scene;
    this.scene.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
          double center = newSceneWidth.doubleValue() / 2.0;
          wheel.setLayoutX(center);
          pointer.setLayoutX(center - pointer.getFitWidth() / 2.0);
          rightWheel.setLayoutX(newSceneWidth.doubleValue() - 2.0 * rightWheel.getRadius() - 30.0);
          leftWheel.setLayoutX(rightWheel.getLayoutX() - 2.0 * rightWheel.getRadius() - 30.0);
      }
    });
    this.scene.heightProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
            double center = newSceneHeight.doubleValue() / 2.0;
            wheel.setLayoutY(center);
            pointer.setLayoutY(center - wheel.getRadius());
            leftWheel.setLayoutY(30.0 + leftWheel.getRadius());
            rightWheel.setLayoutY(30.0 + leftWheel.getRadius());
        }
    });
  }
  
  @FXML
  private void draw() {
    RotateTransition rt = new RotateTransition(Duration.seconds(11.6), wheel);
    int rotateTimes = 15 * fullRotation;
    
    if (drawedPositions.size() == 18) {
      media = new Media(getClass().getResource("/win.mp3").toString());  
      mp = new MediaPlayer(media);
      mp.play();
      showNewWindow("theend.png");
//      ((Stage) scene.getWindow()).close();
      return;
    }
      
    int drawedPosition =  -1;
    do {
        drawedPosition = drawPosition();
      
    } while (drawedPositions.contains(drawedPosition));
    
    drawedPositions.add(drawedPosition);
    System.out.println(drawedPosition + " -- " + getRotation(drawedPosition)+ " -- " + previousRotation );
    
    mp = new MediaPlayer(media);
    mp.play();
    
    int rotation = getRotation(drawedPosition);
    rt.setByAngle(rotateTimes + fullRotation - rotation + previousRotation);
    previousRotation = rotation;
    final int drawed = drawedPosition;
    rt.setOnFinished((ActionEvent e) -> {
      try {
        Thread.sleep(1 * 1000);
      } catch (InterruptedException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
      }
      showNewWindow(questions.get(drawed - 1));
      isRotating = false;
    });

    rt.play();
  }
  
  private void showNewWindow(final String image) {
    Parent root;
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("questionView.fxml"));
//        root = FXMLLoader.load(getClass().getResource("questionView.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.initOwner(scene.getWindow());
        root.requestFocus();
        root.setStyle("-fx-background-image: url('" + image + "'); " +
           "-fx-background-position: center center; " +
           "-fx-background-repeat: stretch;");
        ((QuestionViewController) loader.getController()).setStage(stage);
        stage.show();
      } catch (IOException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    URL url = getClass().getResource("/drawIcon.png");
    Image img = new Image(getClass().getResource("/wheel_of_fortune.png").toString());
    wheel.setFill(new ImagePattern(img));
    img = new Image(getClass().getResource("/pointer.png").toString());
    pointer.setImage(img);
    
    img = new Image(getClass().getResource("/left_enabled.png").toString());
    leftWheel.setFill(new ImagePattern(img));
    
    img = new Image(getClass().getResource("/right_enabled.png").toString());
    rightWheel.setFill(new ImagePattern(img));
    
    drawedPositions = new ArrayList<>();
    
    questions = new ArrayList<>();
    for (int i = 1; i <= 18; i++) {
     questions.add(getClass().getResource("/(" + i + ").png").toString()); 
    }
    
    media = new Media(getClass().getResource("/drum_roll.mp3").toString());  
    
//    wheel.setRotate((drawPosition() * oneStep) + (oneStep/2.0));
     
  }
  
  private int drawPosition() {
    Random random = new Random() ;
    return random.nextInt(18) + 1;
  }
  
  private int getRotation(int number) {
    switch(number) {
      case 1:
        return 1 * oneStep;
      case 2:
        return 12 * oneStep;
      case 3:
        return 6 * oneStep;
      case 4:
        return 18  * oneStep;
      case 5:
        return 9 * oneStep;
      case 6:
        return 15 * oneStep;
      case 7:
        return 11 * oneStep;
      case 8:
        return 2 * oneStep;
      case 9:
        return 17 * oneStep;
      case 10:
        return 7 * oneStep;
      case 11:
        return 3 * oneStep;
      case 12:
        return 14 * oneStep;
      case 13:
        return 16 * oneStep;
      case 14:
        return 5 * oneStep;
      case 15:
        return 13 * oneStep;
      case 16:
        return 4 * oneStep;
      case 17:
        return 8 * oneStep;
      case 18:
        return 10 * oneStep;
      default:
        return 0;
    }
  }

  
}
