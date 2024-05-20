package org.iesvdm.sudoku;

public interface NotPureConsumer<T> {
    void accept(T t) throws Exception;
}
