package com.comp301.a09akari;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.comp301.a09akari.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
// import org.junit.jupiter.api.BeforeEach;

/** Unit test for simple App. */
public class AppTest extends SamplePuzzles {
  /** Rigorous Test :-) */
  private Model model;

  private PuzzleLibrary library;

  @Before
  public void setUp() {
    library = new PuzzleLibraryImpl();
    library.addPuzzle(new PuzzleImpl(PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(PUZZLE_05));
    library.addPuzzle(new PuzzleImpl(PUZZLE_06));
    library.addPuzzle(new PuzzleImpl(PUZZLE_07));

    model = new ModelImpl(library);
  }

  @Test
  public void addLamp() {
    model.addLamp(0, 0);
    assertTrue(model.isLit(0, 0));
    assertTrue(model.isLamp(0, 0));
    assertTrue(model.isLit(0, 1));
  }

  @Test
  public void isLitTest() {
    model.addLamp(3, 4);
    model.addLamp(3, 6);
    model.addLamp(5, 2);
    model.addLamp(5, 4);

    assertTrue(model.isLit(3, 4));
    assertTrue(model.isLit(5, 0));
    assertTrue(model.isLit(5, 5));

    assertFalse(model.isLit(0, 0));
    assertFalse(model.isLit(6, 3));
  }

  @Test
  public void isSolved() {
    /// For puzzle 1, lamps should be placed in [1][1],[0]
    assertFalse(model.isSolved());
    model.addLamp(3, 4); // yep
    model.addLamp(3, 6); // yep
    model.addLamp(5, 2); // yep
    model.addLamp(4, 5); // yep
    assertFalse(model.isSolved());
    model.addLamp(2, 5); // yep
    model.addLamp(6, 3); // yep
    model.addLamp(0, 3); // yep
    assertFalse(model.isSolved());
    model.addLamp(6, 0); // yep
    model.addLamp(5, 6); // yep
    model.addLamp(1, 1); // yep
    assertTrue(model.isSolved());

    model.resetPuzzle();
    model.setActivePuzzleIndex(5);
    assertArrayEquals(model.getActivePuzzle().getBoard(), PUZZLE_06);
    model.addLamp(0, 1);
    assertTrue(model.isSolved());
    model.resetPuzzle();

    model.setActivePuzzleIndex(6);
    assertArrayEquals(model.getActivePuzzle().getBoard(), PUZZLE_07);
    model.resetPuzzle();
    model.addLamp(2, 0);
    assertTrue(model.isClueSatisfied(1, 0));
    assertFalse(model.isSolved());
    model.addLamp(0, 1);
    assertTrue(model.isSolved());
  }

  @Test
  public void illegalLamp() {
    model.addLamp(1, 1);
    assertFalse(model.isLampIllegal(1, 1));
    model.addLamp(1, 2);
    assertTrue(model.isLampIllegal(1, 2)); // placed next to it
    model.removeLamp(1, 2);
    model.addLamp(5, 1);
    assertFalse(model.isLampIllegal(5, 1));
    model.addLamp(6, 0);
    assertFalse(model.isLampIllegal(6, 0));
  }
}
