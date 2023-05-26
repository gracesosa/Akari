package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ControlView implements FXComponent {
  private final Model model;
  private final AlternateMvcController controller;
  private Button nextPuzzleButton;
  private Button prevPuzzleButton;
  private Button resetButton;
  private Button randomButton;
  private Button solvedButton;

  public ControlView(Model model, AlternateMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox outer = new VBox();
    outer.getChildren().clear();
    outer.getStyleClass().add("vbox");
    outer.setAlignment(Pos.CENTER);

    HBox hbox = new HBox();
    hbox.getChildren().clear();
    hbox.getStyleClass().add("hbox");
    hbox.setAlignment(Pos.CENTER);

    // Previous puzzle button
    prevPuzzleButton = new Button("Previous" + "\n" + "puzzle");
    prevPuzzleButton.setOnAction((ActionEvent event) -> controller.clickPrevPuzzle());
    prevPuzzleButton.setPrefSize(80, 50);
    prevPuzzleButton.getStyleClass().add("prevbutton");

    // Next puzzle button
    nextPuzzleButton = new Button("Next" + "\n" + "puzzle");
    nextPuzzleButton.setOnAction((ActionEvent event) -> controller.clickNextPuzzle());
    nextPuzzleButton.setPrefSize(80, 50);
    nextPuzzleButton.getStyleClass().add("nextbutton");

    // Reset button
    resetButton = new Button("Reset" + "\n" + "puzzle");
    resetButton.setOnAction((ActionEvent event) -> controller.clickResetPuzzle());
    resetButton.setPrefSize(80, 50);
    resetButton.getStyleClass().add("resetbutton");

    // Randomize button
    randomButton = new Button("Randomize" + "\n" + "puzzle");
    randomButton.setOnAction((ActionEvent event) -> controller.clickRandPuzzle());
    randomButton.setPrefSize(100, 50);
    randomButton.getStyleClass().add("randombutton");

    hbox.getChildren().addAll(prevPuzzleButton, nextPuzzleButton, resetButton);

    Label random = new Label("Not sure what puzzle to do? Try a random one!" + "\n");
    random.getStyleClass().add("label");
    hbox.getChildren().add(random);
    hbox.getChildren().add(randomButton);
    outer.getChildren().add(hbox);

    return outer;
  }
}
