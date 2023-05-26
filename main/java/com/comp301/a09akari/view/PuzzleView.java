package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;

import static javafx.geometry.Pos.CENTER;
import static javafx.scene.paint.Color.*;

public class PuzzleView implements FXComponent {
  private final Model model;
  private final AlternateMvcController controller;

  private GridPane puzzle;
  private Button prevPuzzleButton;
  private Button nextPuzzleButton;

  public PuzzleView(Model model, AlternateMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox pane = new VBox();
    pane.setMaxSize(600, 600);
    pane.setMinSize(600, 600);
    pane.getChildren().clear();
    puzzle = makePuzzle();
    puzzle.setAlignment(CENTER);
    pane.getChildren().add(puzzle);

    return pane;
  }

  private GridPane makePuzzle() {
    GridPane puzzle = new GridPane();

    puzzle.setHgap(5);
    puzzle.setVgap(5);

    for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < model.getActivePuzzle().getWidth(); j++) {
        Rectangle box = new Rectangle(50, 50);
        StackPane stack = new StackPane();
        com.comp301.a09akari.model.CellType cellType = model.getActivePuzzle().getCellType(i, j);
        int finalJ = j;
        int finalI = i;
        ImageView lamp = makeLamp(i, j);

        box.setOnMouseClicked(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent mouseEvent) {
                controller.clickCell(finalI, finalJ);
              }
            });
        lamp.setOnMouseClicked(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent mouseEvent) {
                controller.clickCell(finalI, finalJ);
              }
            });
        switch (cellType) {
          case CORRIDOR:
            box.setFill(WHITE);
            stack.getChildren().add(box);

            if (model.isLamp(i, j)) {
              stack.getChildren().add(lamp);
            }
            if (!model.isLamp(i, j)) {
              stack.getChildren().remove(lamp);
            }
            if (model.isLit(i, j)) {
              box.setFill(YELLOW);
              if (model.isLamp(i, j) && model.isLampIllegal(i, j)) {
                box.setFill(RED);
              }
            }
            puzzle.add(stack, i, j);
            break;

          case CLUE:
            box.setFill(BLACK);
            if (model.isClueSatisfied(i, j)) {
              box.setFill(rgb(30, 100, 80));
            }
            int clueNum = model.getActivePuzzle().getClue(i, j);
            box.getStyleClass().add("clue");
            Text text = new Text(Integer.toString(clueNum));
            text.setFill(WHITE);
            stack.getChildren().addAll(box, text);
            puzzle.add(stack, i, j);
            break;

          case WALL:
            box.setFill(BLACK);
            puzzle.add(box, i, j);
            break;
        }
      }
    }

    puzzle.getStyleClass().add("puzzle");
    return puzzle;
  }

  private ImageView makeLamp(int r, int c) {
    Image image;
    image = new Image("light-bulb.png");
    ImageView imageView = new ImageView();
    imageView.setImage(image);
    imageView.setFitWidth(25);
    imageView.setPreserveRatio(true);
    return imageView;
  }
}
