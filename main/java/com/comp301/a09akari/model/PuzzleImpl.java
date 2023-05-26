package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private int[][] board;
  private int width;
  private int height;

  public PuzzleImpl(int[][] board) {
    this.board = board;
    this.width = board[0].length;
    this.height = board.length;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r >= this.getHeight() || c >= this.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (board[r][c] <= 4 && board[r][c] >= 0) {
      return CellType.CLUE;
    } else if (board[r][c] == 5) {
      return CellType.WALL;
    } else {
      return CellType.CORRIDOR;
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (board[r][c] < 0 || board[r][c] > 6) {
      throw new IndexOutOfBoundsException();
    }
    if (board[r][c] > 4) {
      throw new IllegalArgumentException();
    } else {
      return board[r][c];
    }
  }

  public int[][] getBoard() {
    return board;
  }
}
