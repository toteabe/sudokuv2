package org.iesvdm.sudoku;

//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

public class SudokuTest {

    @Test
    void failTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.fillBoardBasedInCluesRandomlySolvable();
        //sudoku.fillBoardBasedInCluesRandomly();
        sudoku.printBoard();

        System.out.println("XXXXXXXXX");
        sudoku.solveBoard();
        sudoku.printBoard();
    }

    @Test
    void fillBoardBasedInCluesRandomlyTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.fillSolvable((NotPureConsumer<Sudoku>) Sudoku::fillBoardBasedInCluesRandomly);
        sudoku.printBoard();
        System.out.println("XXXXXXXXX");
        boolean solved = sudoku.solveBoard();
        System.out.println(solved);
        sudoku.printBoard();
    }

    @Test
    void fillBoardBasedInCluesFirstValidTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.fillSolvable((NotPureConsumer<Sudoku>) Sudoku::fillBoardBasedInCluesFirstValid);
        sudoku.printBoard();
        System.out.println("XXXXXXXXX");
        sudoku.solveBoard();
        sudoku.printBoard();
    }

    @Test
    void fillBoardBasedInCluesRandomlyValidFromSetsTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.fillSolvable((Function<Sudoku, Boolean>) Sudoku::fillBoardBasedInCluesRandomlyValidFromSets);
        sudoku.printBoard();
        System.out.println("XXXXXXXXX");
        sudoku.solveBoard();
        sudoku.printBoard();
    }

    @Test
    void fillBoardBasedInClues50Ramdonly50FirstValidTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.fillSolvable((NotPureConsumer<Sudoku>) Sudoku::fillBoardBasedInClues50Ramdonly50FirstValid);
        sudoku.printBoard();
        System.out.println("XXXXXXXXX");
        sudoku.solveBoard();
        sudoku.printBoard();
    }
}
