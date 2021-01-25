package com.sudoku.common.tools.functional;

@FunctionalInterface
public interface QuadrPredicate<T, U, V, R> {

  boolean test(T t, U u, V v, R r);
}