package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;

import static com.comp301.a09akari.SamplePuzzles.PUZZLE_01;
import static com.comp301.a09akari.SamplePuzzles.PUZZLE_02;
import static com.comp301.a09akari.SamplePuzzles.PUZZLE_03;
import static com.comp301.a09akari.SamplePuzzles.PUZZLE_04;
import static com.comp301.a09akari.SamplePuzzles.PUZZLE_05;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // TODO: Create your Model, View, and Controller instances and launch your GUI
    PuzzleLibrary library = new PuzzleLibraryImpl();

    library.addPuzzle(new PuzzleImpl(PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(PUZZLE_05));

    Model model = new ModelImpl(library);
    AlternateMvcController controller = new ControllerImpl(model);
    MainView view = new MainView(model, controller);

    stage.setScene(view.getScene());
    stage.setTitle("Controls");
    stage.show();
  }
}
