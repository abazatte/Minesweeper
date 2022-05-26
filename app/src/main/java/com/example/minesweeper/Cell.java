package com.example.minesweeper;

public class Cell {
    public static final int BOMB = -1;
    public static final int BLANK = 0;


    private int value;
    private boolean isRevealed;
    private boolean isFlaged;


    public Cell(int value){
        this.value = value;
        this.isRevealed = false;
        this.isFlaged = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlaged() {
        return isFlaged;
    }

    public void setFlaged(boolean flaged) {
        isFlaged = flaged;
    }
}
