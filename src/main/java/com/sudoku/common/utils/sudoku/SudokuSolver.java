package com.sudoku.common.utils.sudoku;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 数独游戏解决器
 */
public class SudokuSolver {

  private static final int THRESHOLD = 324;
  @Getter
  private final List<int[][]> solutions = new ArrayList<>();
  private final int[] size = new int[1074];
  private final DLXNode[] row = new DLXNode[4146];
  private final DLXNode[] col = new DLXNode[1074];
  private final int[][] data = new int[9][9];
  private int maxDepth;
  private DLXNode head;

  /**
   * 公有构造方法
   *
   * @param topic    数独题目数组
   * @param maxDepth 最大深度
   */
  public SudokuSolver(int[][] topic, int maxDepth) {
    init(topic, maxDepth);
  }

  /**
   * 公有构造方法，默认最大深度为2
   *
   * @param topic 数独题目数组
   */
  public SudokuSolver(int[][] topic) {
    init(topic, 2);
  }

  public int solutionCount() {
    return solutions.size();
  }

  /**
   * 初始化该类，并解决数独题目
   *
   * @param topic    数独题目
   * @param maxDepth 最大深度
   */
  private void init(int[][] topic, int maxDepth) {
    this.maxDepth = maxDepth;
    initDLX();
    initData(topic);
    solve(0);
  }

  private void initDLX() {
    int r = 730, c = 324;
    head = new DLXNode(r, c);
    head.U = head.D = head.L = head.R = head;
    for (int i = 0; i < c; ++i) {
      col[i] = new DLXNode(r, i);
      col[i].L = head;
      col[i].R = head.R;
      col[i].L.R = col[i].R.L = col[i];
      col[i].U = col[i].D = col[i];
      size[i] = 0;
    }

    for (int i = r - 1; i > -1; --i) {
      row[i] = new DLXNode(i, c);
      row[i].U = head;
      row[i].D = head.D;
      row[i].U.D = row[i].D.U = row[i];
      row[i].L = row[i].R = row[i];
    }
  }

  private void addNode(int r, int c) {
    DLXNode p = new DLXNode(r, c);
    p.R = row[r];
    p.L = row[r].L;
    p.L.R = p.R.L = p;
    p.U = col[c];
    p.D = col[c].D;
    p.U.D = p.D.U = p;
    ++size[c];
  }

  private void addNode(int i, int j, int k) {
    int r = (i * 9 + j) * 9 + k;
    addNode(r, i * 9 + k - 1);
    addNode(r, 9 * 9 + j * 9 + k - 1);
    addNode(r, 2 * 9 * 9 + block(i, j) * 9 + k - 1);
    addNode(r, 3 * 9 * 9 + i * 9 + j);
  }

  private int block(int x, int y) {
    return x / 3 * 3 + y / 3;
  }

  private void cover(int c) {
    if (c == THRESHOLD) {
      return;
    }

    col[c].delLR();
    DLXNode R, C;
    for (C = col[c].D; C != col[c]; C = C.D) {
      if (C.c == THRESHOLD) {
        continue;
      }
      for (R = C.L; R != C; R = R.L) {
        if (R.c == THRESHOLD) {
          continue;
        }
        --size[R.c];
        R.delUD();
      }
      C.delLR();
    }
  }

  private void resume(int c) {
    if (c == THRESHOLD) {
      return;
    }

    DLXNode R, C;
    for (C = col[c].U; C != col[c]; C = C.U) {
      if (C.c == THRESHOLD) {
        continue;
      }
      C.resumeLR();
      for (R = C.R; R != C; R = R.R) {
        if (R.c == THRESHOLD) {
          continue;
        }
        ++size[R.c];
        R.resumeUD();
      }
    }
    col[c].resumeLR();
  }

  private boolean solve(int depth) {
    if (head.L == head) {
      int[][] solution = new int[9][9];
      for (int i = 0; i < 9; ++i) {
        System.arraycopy(data[i], 0, solution[i], 0, 9);
      }
      solutions.add(solution);

      return solutions.size() >= this.maxDepth;
    }
    int minSize = 1 << 30;
    int c = -1;
    DLXNode p;
    for (p = head.L; p != head; p = p.L) {
      if (size[p.c] < minSize) {
        minSize = size[p.c];
        c = p.c;
      }
    }
    cover(c);

    for (p = col[c].D; p != col[c]; p = p.D) {
      DLXNode cell;
      p.R.L = p;
      for (cell = p.L; cell != p; cell = cell.L) {
        cover(cell.c);
      }
      p.R.L = p.L;
      int rr = p.r - 1;
      data[rr / (9 * 9)][rr / 9 % 9] = rr % 9 + 1;
      if (solve(depth + 1)) {
        return true;
      }

      p.L.R = p;
      for (cell = p.R; cell != p; cell = cell.R) {
        resume(cell.c);
      }
      p.L.R = p.R;
    }

    resume(c);
    return false;
  }

  private void initData(int[][] topic) {
    int i, j, k;
    for (i = 0; i < 9; ++i) {
      for (j = 0; j < 9; ++j) {
        if (topic[i][j] > 0) {
          addNode(i, j, topic[i][j]);
        } else {
          for (k = 1; k <= 9; ++k) {
            addNode(i, j, k);
          }
        }
      }
    }
  }

  private static class DLXNode {

    private final int r;
    private final int c;
    private DLXNode U, D, L, R;

    public DLXNode(int r, int c) {
      this.r = r;
      this.c = c;
    }

    public void delLR() {
      L.R = R;
      R.L = L;
    }

    public void delUD() {
      U.D = D;
      D.U = U;
    }

    public void resumeLR() {
      L.R = R.L = this;
    }

    public void resumeUD() {
      U.D = D.U = this;
    }
  }
}

