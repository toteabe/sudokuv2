package org.iesvdm.sudoku;

//import static org.junit.jupiter.api.Assertions.*;
import static java.time.Duration.ofMinutes;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

public class SudokuTest {

    @Test
    void fillBoardBasedInCluesRandomlySolvableTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.setNumClues(43);
        assertTimeoutPreemptively(
                ofSeconds(10), () -> {
                    sudoku.fillBoardBasedInCluesRandomlySolvable();
                    //sudoku.fillBoardBasedInCluesRandomly();
                });
        sudoku.printBoard();
        System.out.println("XXXXXXXXXSOLUCIÓNXXXXXXXXX");
        sudoku.solveBoard();
        sudoku.printBoard();
    }

    @Test
    void fillBoardBasedInCluesRandomlyTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.fillSolvable((NotPureConsumer<Sudoku>) Sudoku::fillBoardBasedInCluesRandomly);
        assertTimeoutPreemptively(
                ofSeconds(10), () -> {
                    sudoku.printBoard();
                });
        System.out.println("XXXXXXXXXSOLUCIÓNXXXXXXXXX");
        boolean solved = sudoku.solveBoard();
        System.out.println(solved);
        sudoku.printBoard();
    }

    @Test
    void fillBoardBasedInCluesFirstValidTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.setNumClues(73);
        assertTimeoutPreemptively(
                ofSeconds(10), () -> {
                    sudoku.fillSolvable((NotPureConsumer<Sudoku>) Sudoku::fillBoardBasedInCluesFirstValid);
                });
        sudoku.printBoard();
        System.out.println("XXXXXXXXXSOLUCIÓNXXXXXXXXX");
        sudoku.solveBoard();
        sudoku.printBoard();
    }

    @Test
    void fillBoardBasedInCluesRandomlyValidFromSetsTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.setNumClues(53);
        assertTimeoutPreemptively(
                ofSeconds(10), () -> {
                    sudoku.fillSolvable((Function<Sudoku, Boolean>) Sudoku::fillBoardBasedInCluesRandomlyValidFromSets);
                });
        sudoku.printBoard();
        System.out.println("XXXXXXXXXSOLUCIÓNXXXXXXXXX");
        sudoku.solveBoard();
        sudoku.printBoard();
    }

    @Test
    void fillBoardBasedInClues50Ramdonly50FirstValidTest() {
        Sudoku sudoku = new Sudoku();
        sudoku.setNumClues(53);
        assertTimeoutPreemptively(
                ofSeconds(10), () -> {
                    sudoku.fillSolvable((NotPureConsumer<Sudoku>) Sudoku::fillBoardBasedInClues50Ramdonly50FirstValid);
                });
        sudoku.printBoard();
        System.out.println("XXXXXXXXXSOLUCIÓNXXXXXXXXX");
        sudoku.solveBoard();
        sudoku.printBoard();
    }
}
