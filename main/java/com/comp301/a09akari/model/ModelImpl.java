package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private int index;
  private Puzzle activePuzzle;
  private List<ModelObserver> observers;
  private boolean[][] lamps; // stores locations of lamps
  private int height;
  private int width;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException();
    }
    this.library = library;
    this.index = 0;
    this.activePuzzle = library.getPuzzle(index);
    this.lamps = new boolean[activePuzzle.getHeight()][activePuzzle.getWidth()];
    this.observers = new ArrayList<>();
    this.height = getActivePuzzle().getHeight();
    this.width = getActivePuzzle().getWidth();
  }
  // Clue: 1-4, Wall: 5, Corridor: 6

  @Override
  public void addLamp(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Your entered row and column aren't within bounds.");
    }

    if (this.getActivePuzzle().getCellType(r, c) != (CellType.CORRIDOR)) {
      throw new IllegalArgumentException("Lamps aren't allowed in this kind cell!");
    }
    if (!lamps[r][c]) {
      lamps[r][c] = true;
      notifyObservers();
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Your entered row and column aren't within bounds.");
    }
    if (this.getActivePuzzle().getCellType(r, c) != (CellType.CORRIDOR)) {
      throw new IllegalArgumentException("Lamps aren't allowed in this kind cell!");
    }
    if (lamps[r][c]) {
      lamps[r][c] = false;
      notifyObservers();
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Your entered row and column aren't within bounds.");
    }
    if (this.getActivePuzzle().getCellType(r, c) != (CellType.CORRIDOR)) {
      throw new IllegalArgumentException("This cell can't be lit!");
    }

    if (lamps[r][c]) {
      return true;
    } else {
      // check cells above
      for (int i = r - 1; i >= 0; i--) {
        if (this.getActivePuzzle().getCellType(i, c) != (CellType.CORRIDOR)) { // is clue or wall
          break;
        }
        if (lamps[i][c]) { // has lamp
          return true;
        }
      }

      // Check cells below
      for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
        if (this.getActivePuzzle().getCellType(i, c) != (CellType.CORRIDOR)) { // is clue or wall
          break;
        }
        if (lamps[i][c]) { // has lamp
          return true;
        }
      }
      // Check cells to left
      for (int i = c - 1; i >= 0; i--) {
        if (this.getActivePuzzle().getCellType(r, i) != (CellType.CORRIDOR)) { // is clue or wall
          break;
        }
        if (lamps[r][i]) { // has lamp
          return true;
        }
      }
      // Check cells to right
      for (int i = c + 1; i < getActivePuzzle().getWidth(); i++) {
        if (this.getActivePuzzle().getCellType(r, i) != (CellType.CORRIDOR)) { // is clue or wall
          break;
        }
        if (lamps[r][i]) { // has lamp
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Your entered row and column aren't within bounds.");
    }
    if (this.getActivePuzzle().getCellType(r, c) != (CellType.CORRIDOR)) {
      throw new IllegalArgumentException("Lamps aren't allowed in this kind cell!");
    }
    // lamps = new boolean[this.getActivePuzzle().getHeight()][this.getActivePuzzle().getWidth()];
    return lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r >= width || c >= this.getActivePuzzle().getHeight() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Your entered row and column aren't within bounds.");
    }
    if (this.getActivePuzzle().getCellType(r, c) != (CellType.CORRIDOR)) {
      throw new IllegalArgumentException("Lamps aren't allowed in this kind cell!");
    }

    // check cells above
    for (int i = r - 1; i >= 0; i--) {
      if (this.getActivePuzzle().getCellType(i, c) != (CellType.CORRIDOR)) {
        break;
      }
      if (lamps[i][c]) { // has lamp
        return true;
      }
    }
    // Check cells below
    for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
      if (this.getActivePuzzle().getCellType(i, c) != (CellType.CORRIDOR)) {
        break;
      }
      if (lamps[i][c]) { // has lamp
        return true;
      }
    }
    // Check cells to left
    for (int i = c - 1; i >= 0; i--) {
      if (this.getActivePuzzle().getCellType(r, i) != (CellType.CORRIDOR)) {
        break;
      }
      if (lamps[r][i]) { // has lamp
        return true;
      }
    }

    // Check cells to right
    for (int i = c + 1; i < getActivePuzzle().getWidth(); i++) {
      if (this.getActivePuzzle().getCellType(r, i) != (CellType.CORRIDOR)) {
        break;
      }
      if (lamps[r][i]) { // has lamp
        return true;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(index);
  }

  @Override
  public int getActivePuzzleIndex() {
    return index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= getPuzzleLibrarySize()) {
      throw new IndexOutOfBoundsException("Your entered index isn't within bounds.");
    }

    this.index = index;
    this.activePuzzle = library.getPuzzle(index);
    this.resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    this.lamps = new boolean[this.getActivePuzzle().getHeight()][this.getActivePuzzle().getWidth()];
    this.height = getActivePuzzle().getHeight();
    this.width = getActivePuzzle().getWidth();
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    for (int i = 0; i < getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < getActivePuzzle().getWidth(); j++) {
        if (this.getActivePuzzle().getCellType(i, j) == (CellType.CLUE)) {
          if (!isClueSatisfied(i, j)) {
            return false;
          }
        } else if (getActivePuzzle().getCellType(i, j) == (CellType.CORRIDOR)) {
          if (!isLit(i, j)) {
            return false;
          }
          if (isLamp(i, j) && isLampIllegal(i, j)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Your entered row and column aren't within bounds.");
    }
    if (!getActivePuzzle().getCellType(r, c).equals(CellType.CLUE)) {
      throw new IllegalArgumentException("Cell type not clue");
    }
    int count = 0;
    int clue = getActivePuzzle().getClue(r, c);

    if (r - 1 >= 0 && getActivePuzzle().getCellType(r - 1, c).equals(CellType.CORRIDOR)) {
      // Check top cell
      if (isLamp(r - 1, c)) {
        count++;
      }
    }
    if (r + 1 < getActivePuzzle().getHeight()
        && getActivePuzzle().getCellType(r + 1, c).equals(CellType.CORRIDOR)) {
      // check bottom cell
      if (isLamp(r + 1, c)) {
        count++;
      }
    }
    if (c - 1 >= 0 && getActivePuzzle().getCellType(r, c - 1).equals(CellType.CORRIDOR)) {
      // Check left cell
      if (isLamp(r, c - 1)) {
        count++;
      }
    }
    if (c + 1 < getActivePuzzle().getWidth()
        && getActivePuzzle().getCellType(r, c + 1).equals(CellType.CORRIDOR)) {
      // Check right cell
      if (isLamp(r, c + 1)) {
        count++;
      }
    }
    return count == clue;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Can't add null observer");
    }
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Can't remove null observer");
    }
    observers.remove(observer);
  }

  public void notifyObservers() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }
}
