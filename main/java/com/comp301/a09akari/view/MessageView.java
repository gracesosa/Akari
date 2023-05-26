package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static javafx.geometry.Pos.CENTER;

public class MessageView implements FXComponent {
  private final Model model;
  private final AlternateMvcController controller;

  public MessageView(Model model, AlternateMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox pane = new VBox();
    pane.getChildren().clear();
    int puzzNum = model.getActivePuzzleIndex() + 1;
    Text message1 = new Text("Akari Light Up Puzzle " + puzzNum);
    pane.setAlignment(CENTER);
    Text message2 =
        new Text("You have " + model.getPuzzleLibrarySize() + " puzzles in your library");
    pane.getStyleClass().add("messagebox");
    pane.getChildren().addAll(message1, message2);

    return pane;
  }
}
