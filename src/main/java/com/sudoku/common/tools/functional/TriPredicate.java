package com.sudoku.common.tools.functional;

@FunctionalInterface
public interface TriPredicate<T, U, V> {

  boolean test(T t, U u, V v);
}