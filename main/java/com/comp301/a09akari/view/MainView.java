package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;

import static javafx.geometry.Pos.CENTER;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class MainView implements FXComponent, ModelObserver {
  private final FXComponent controlView;
  private final FXComponent puzzleView;
  private final FXComponent messageView;
  private final Scene scene;
  private final Model model;
  private final AlternateMvcController controller;
  private BorderPane pane;

  private Button resetButton;

  public MainView(Model model, AlternateMvcController controller) {
    this.model = model;
    this.controller = controller;
    this.controlView = new ControlView(model, controller);
    this.puzzleView = new PuzzleView(model, controller);
    this.messageView = new MessageView(model, controller);
    this.scene = new Scene(render());
    this.scene.getStylesheets().add("main.css");
    model.addObserver(this);
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public Parent render() {
    pane = new BorderPane();
    pane.setTop(messageView.render());
    pane.setCenter(puzzleView.render());
    pane.setBottom(controlView.render());

    if (model.isSolved()) {
      StackPane stack = new StackPane();
      resetButton = new Button("You solved it!" + "\n Click to go back to your puzzle.");
      resetButton.setTextAlignment(TextAlignment.CENTER);

      resetButton.setOnAction((ActionEvent event) -> resetButton.setVisible(false));
      resetButton.setPrefSize(220, 80);
      resetButton.getStyleClass().add("resetbutton");

      stack.getChildren().addAll(puzzleView.render(), resetButton);
      pane.setCenter(stack);
    }
    return pane;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }
}
