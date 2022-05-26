package com.example.minesweeper;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class MineSweeperGame {

    private MineGrid mineGrid;
    private boolean clearMode;
    private boolean isGameOver;
    private boolean timeExpired;
    private boolean flagMode;
    private int flagCount;
    private int numBombs;

    public MineSweeperGame(int size, int numberOfBombs){
        flagMode = false;
        timeExpired = false;
        clearMode = true;
        isGameOver = false;
        flagCount = 0;
        numBombs = numberOfBombs;
        mineGrid = new MineGrid(size);
        mineGrid.generateGrid(numberOfBombs);
    }

    public void handleCellClick(Cell cell) {
        if(!isGameOver && !isGameWon() && !timeExpired){
            if (clearMode) {
                clear(cell);
            } else if(flagMode){
                flag(cell);
            }
        }
    }

    public void clear(Cell cell){
        int index = getMineGrid().getCells().indexOf(cell);
        getMineGrid().getCells().get(index).setRevealed(true);

        if(cell.getValue() == Cell.BLANK){
            List<Cell> toClear = new ArrayList<>();
            List<Cell> toCheckAdjacent = new ArrayList<>();

            toCheckAdjacent.add(cell);

            while(toCheckAdjacent.size() > 0){
                Cell c = toCheckAdjacent.get(0);
                int cellIndex = getMineGrid().getCells().indexOf(c);
                int [] cellPos = getMineGrid().toXY(cellIndex);

                for(Cell adjacent : getMineGrid().adjacentCells(cellPos[0], cellPos[1])){
                    if(adjacent.getValue() == Cell.BLANK) {
                        if (!toClear.contains(adjacent)) { // Das man nicht selber sich selbst nochmal prueft
                            if (!toCheckAdjacent.contains(adjacent)) {
                                toCheckAdjacent.add(adjacent); // Alle Zellen adden, die Blank sind, ausser sich selbst
                            }
                        }
                    } else {
                            if (!toClear.contains(adjacent)){
                                toClear.add(adjacent);
                            }
                        }
                    }
                    toCheckAdjacent.remove(c);
                    toClear.add(c);
                }
                for(Cell c : toClear){
                c.setRevealed(true);
            }
        } else if(cell.getValue() == Cell.BOMB){
            isGameOver = true;
        }
    }

    public boolean isGameWon(){
        int numbersUnrevealed = 0;
        for( Cell c : getMineGrid().getCells()){
            if(c.getValue() != Cell.BOMB && c.getValue() != Cell.BLANK && !c.isRevealed()){
                numbersUnrevealed++;
            }
        }

        if (numbersUnrevealed == 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean isGameOver(){
        return isGameOver;
    }

    public MineGrid getMineGrid(){
        return mineGrid;
    }

    public void outOfTime(){
        timeExpired = true;
    }

    public void flag(Cell cell){
        if(!cell.isRevealed()){
            cell.setFlaged((!cell.isFlaged()));  // Status invertieren
            int count = 0;
            for(Cell c: getMineGrid().getCells()){
                if(c.isFlaged()){
                    count++;
                }
            }
            flagCount = count;
        }
    }

    public void toggleMode(){
        clearMode = !clearMode;
        flagMode = !flagMode;
    }

    public boolean getClearMode(){
        return clearMode;
    }

    public boolean getFlagMode(){
        return flagMode;
    }

    public int getNumBombs(){
        return numBombs;
    }

    public int getFlagCount(){
        return flagCount;
    }

}
