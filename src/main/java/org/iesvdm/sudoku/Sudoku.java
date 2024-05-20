package org.iesvdm.sudoku;

import java.util.*;
import java.util.function.Function;

public class Sudoku {
    private int gridSize = 9;
    private int numClues = 33;
    private int[][] board;
    record IJ(int i, int j) {}
    private Map<IJ, Set<Integer>> mapIJNumbers = new HashMap<>();
    private Map<Integer, Set<Integer>> mapINumbers = new HashMap();
    private Map<Integer, Set<Integer>> mapJNumbers = new HashMap();

    public int getNumClues() {
        return numClues;
    }

    public void setNumClues(int numClues) {
        this.numClues = numClues;
    }
    public int[][] getBoard() {
        return board;
    }
    public void setBoard(int[][] board) {
        this.board = board;
    }
    public int getGridSize() {
        return gridSize;
    }
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    void fillBoardRandomly() {
        board = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                board[i][j] = (int)(Math.random()*(gridSize+1));
            }
        }
    }

    void fillBoardBasedInCluesRandomly(){
        board = new int[gridSize][gridSize];
        int cont = 0;
        int i = 0;
        int j = 0;
        while (cont < numClues) {
            i = (int) (Math.random() * gridSize);
            j = (int) (Math.random() * gridSize);
            if (board[i][j] == 0) {
                int number = 1 + (int)(Math.random() * (gridSize));
                if (isValidPlacement(number, i, j)) {
                    board[i][j] = number;
                    cont++;
                }
            }
        }
    }

    void fillBoardBasedInCluesFirstValid() throws Exception {
        board = new int[gridSize][gridSize];
        int cont = 0;
        int i = 0;
        int j = 0;
        while (cont < numClues) {
            i = (int) (Math.random() * gridSize);
            j = (int) (Math.random() * gridSize);
            if (board[i][j] == 0) {
                    board[i][j] = getFirstValidNumber(i, j);
                    cont++;
            }
        }
    }

    void fillBoardBasedInClues50Ramdonly50FirstValid() throws Exception {
        board = new int[gridSize][gridSize];
        int cont = 0;
        int i = 0;
        int j = 0;
        while (cont < numClues/2) {
            i = (int) (Math.random() * gridSize);
            j = (int) (Math.random() * gridSize);
            if (board[i][j] == 0) {
                int number = 1 + (int)(Math.random() * (gridSize));
                if (isValidPlacement(number, i, j)) {
                    board[i][j] = number;
                    cont++;
                }
            }
        }
        while (cont < numClues) {
            i = (int) (Math.random() * gridSize);
            j = (int) (Math.random() * gridSize);
            if (board[i][j] == 0) {
                board[i][j] = getFirstValidNumber(i, j);
                cont++;
            }
        }
    }

    int getFirstValidNumber(int i, int j) throws Exception {
        int number = 0;
        do {
            number++;
        } while (number <= gridSize && !isValidPlacement(number, i, j));
        if (number > gridSize) {
            throw new Exception("Sin solución");
        }
        return number;
    }

    boolean fillBoardBasedInCluesRandomlyValidFromSets() {
        Map<Integer, Set<Integer>> iSetMap = fillMapValid();
        Map<Integer, Set<Integer>> jSetMap = fillMapValid();
        Map<Integer, Set<Integer>> ijSetMap = fillMapValid();

        board = new int[gridSize][gridSize];
        int cont = 0;
        int i = 0;
        int j = 0;
        while (cont < numClues) {
            i = (int) (Math.random() * gridSize);
            j = (int) (Math.random() * gridSize);
            if (board[i][j] == 0) {
                board[i][j] = getValidFromSets(i, j, iSetMap, jSetMap, ijSetMap);
                if (board[i][j] == -1) {
                    return false;
                }
                cont++;
            }
        }
        return true;
    }

    Map<Integer, Set<Integer>> fillMapValid() {
        Map<Integer, Set<Integer>> setMap = new HashMap<>();
        for (int i = 1; i <= gridSize; i++) {
            Set<Integer> integerSet = new HashSet<>();
            for (int j = 1; j <= gridSize ; j++) {
                integerSet.add(j);
            }
            setMap.put(i, integerSet);
        }
        return setMap;
    }


    int getValidFromSets(int i, int j, Map<Integer, Set<Integer>> iSetMap
            , Map<Integer, Set<Integer>> jSetMap
            , Map<Integer, Set<Integer>> ijSetMap  ) {
        Set<Integer> copySet = new HashSet<>(iSetMap.get(i+1));
        copySet.retainAll(jSetMap.get(j+1));
        int localBoxRow = i/3;
        int localBoxColumn = j/3;
        int ij = localBoxRow*3 + localBoxColumn;
        copySet.retainAll(ijSetMap.get(ij+1));
        int seleccion = (int) (Math.random()* copySet.size());
        Iterator<Integer> it = copySet.iterator();
        if (it.hasNext()) {
            int valor = it.next();
            int indice = 0;
            while (indice < seleccion && it.hasNext()) {
                valor = it.next();
                indice++;
            }
            iSetMap.get(i+1).remove(valor);
            jSetMap.get(j+1).remove(valor);
            ijSetMap.get(ij+1).remove(valor);
            return valor;
        } else {
           return -1;
        }
    }

    void fillBoardBasedInCluesRandomlySolvable() {
        Sudoku sudoku = null;
        do {
                fillBoardBasedInCluesRandomly();
                sudoku = new Sudoku();
                sudoku.gridSize=gridSize;
                sudoku.copyBoard(board);
        }while(!sudoku.solveBoard());
    }

    void fillSolvable(NotPureConsumer<Sudoku> consumer) {
        Sudoku sudoku = null;
        boolean continuarPorEx;
        do {
            continuarPorEx = false;
            try {
                consumer.accept(this);
            } catch (Exception e) {
                continuarPorEx = true;
                continue;
            }
            sudoku = new Sudoku();
            sudoku.gridSize=gridSize;
            sudoku.copyBoard(board);
        }while( continuarPorEx || !sudoku.solveBoard());
    }

    void fillSolvable(Function<Sudoku, Boolean> function) {
        Sudoku sudoku = null;
        boolean filled;
        do {
            filled = function.apply(this);
            if (!filled) {
                continue;
            }
            sudoku = new Sudoku();
            sudoku.gridSize=gridSize;
            sudoku.copyBoard(board);
        }while(!filled || !sudoku.solveBoard());
    }

    void fillBoardRandomlySolvable() {
        Sudoku sudokuAux = new Sudoku();
        sudokuAux.setGridSize(gridSize);
        do {
            sudokuAux.fillBoardRandomly();
            copyBoard(sudokuAux.board);
        } while(!sudokuAux.solveBoard());
    }

    void fillBoardRandomlyUnsolvable() {
        Sudoku sudokuAux = new Sudoku();
        sudokuAux.setGridSize(gridSize);
        do {
            sudokuAux.fillBoardRandomly();
            copyBoard(sudokuAux.board);
        } while(sudokuAux.solveBoard());
    }

    void copyBoard(int[][] boardSrc) {
        this.board = new int[gridSize][gridSize];
        for (int i = 0; i < boardSrc.length; i++) {
            System.arraycopy(boardSrc[i], 0, this.board[i], 0, gridSize);
        }
    }

    void putNumberInBoard(int number, int row, int column) {
        this.board[row][column] = number;
    }

    void printBoard() {
        for (int row = 0; row < gridSize; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("-----------");
            }
            for (int column = 0; column < gridSize; column++) {
                if (column % 3 == 0 && column != 0) {
                    System.out.print("|");
                }
                System.out.print(board[row][column]);
            }
            System.out.println();
        }
    }


    boolean isNumberInRow(int number, int row) {
        for (int i = 0; i < gridSize; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    boolean isNumberInColumn(int number, int column) {
        for (int i = 0; i < gridSize; i++) {
            if (board[i][column] == number) {
                return true;
            }
        }
        return false;
    }

    boolean isNumberInBox(int number, int row, int column) {
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;

        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isValidPlacement(int number, int row, int column) {
        return !isNumberInRow(number, row) &&
                !isNumberInColumn(number, column) &&
                !isNumberInBox(number, row, column);
    }

    boolean solveBoard() {
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                if (board[row][column] == 0) {
                    for (int numberToTry = 1; numberToTry <= gridSize; numberToTry++) {
                        if (isValidPlacement( numberToTry, row, column)) {
                            board[row][column] = numberToTry;

                            if (solveBoard()) {
                                return true;
                            }
                            else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

}

