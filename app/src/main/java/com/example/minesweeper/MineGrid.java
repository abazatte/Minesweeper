package com.example.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineGrid {
    private List<Cell> cells;

    private int size;


    public MineGrid(int size) {
        this.size = size;
        cells = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            cells.add(new Cell(Cell.BLANK));
        }
    }

    public void generateGrid(int numBombs){
        int bomsPlaced = 0;

        while (bomsPlaced < numBombs){
            int x = new Random().nextInt(size);
            int y = new Random().nextInt(size);

            int index = toIndex(x,y);
            if(cells.get(index).getValue() == Cell.BLANK){
                cells.set(index, new Cell(Cell.BOMB));
                bomsPlaced++;
            }
        }


        // Zaehlen der Bomben in der Nachbarschaft
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                if(cellAt(x,y).getValue() != Cell.BOMB){
                    List<Cell> adjacentCell = adjacentCells(x,y); // fuer jede Zelle legen wir ein Array aus nachbarn an
                    int countBombs = 0;
                    for(Cell cell : adjacentCell){
                        if(cell.getValue() == Cell.BOMB){
                            countBombs++;
                        }
                    }

                    if(countBombs > 0) {
                        cells.set(x + (y*size), new Cell(countBombs)); // Wenn Bombe in Nachbarschaft existiert dann value auf die anzahl der bomben setzen, ansonsten blank lassen
                    }
                }
            }
        }

    }

    public List<Cell> getCells() {
        return cells;
    }

    public int toIndex(int x, int y) {
        return x + (y*size);
    }

    public int [] toXY(int index) {
        int y = index / size;
        int x = index - (y*size);
        return new int [] {x, y};

    }

    public Cell cellAt(int x, int y ) {
        if( x < 0 || x >= size || y < 0 || y >= size) {
            return null; // Fehler abfangen
        }
        return cells.get(toIndex(x,y));
    }

    public List<Cell> adjacentCells(int x, int y){
        List<Cell> adjacentCells = new ArrayList<>();

        List<Cell> cellsList = new ArrayList<>();

        cellsList.add(cellAt(x-1,y));   // Links von uns
        cellsList.add(cellAt(x+1,y));   // Rechts von uns
        cellsList.add(cellAt(x-1,y-1)); // unten links
        cellsList.add(cellAt(x,y-1));   // ueber uns


        cellsList.add(cellAt(x+1,y-1)); // oben rechts
        cellsList.add(cellAt(x-1,y+1)); // links unten
        cellsList.add(cellAt(x,y+1));       // unter uns
        cellsList.add(cellAt(x+1,y+1)); // unten rechts

        for( Cell cell : cellsList){
            if( cell != null){
                adjacentCells.add(cell);
            }
        }
        return adjacentCells;
    }


    public void revealAllBombs(){
        for(Cell c : cells){
            if(c.getValue() == Cell.BOMB){
                c.setRevealed(true);
            }
        }
    }

}
