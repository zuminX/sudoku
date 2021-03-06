package utils;

import static org.mockito.Mockito.when;

import com.sudoku.game.model.bo.SudokuRecordBO;
import com.sudoku.game.utils.sudoku.GameUtils;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class GameRecordUtils {

  public static final int SPEND_TIME = 600_000;

  public static final LocalDateTime START_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);

  public static final LocalDateTime END_TIME = START_TIME.plus(SPEND_TIME, ChronoUnit.MILLIS);


  private GameRecordUtils() {
  }

  public static SudokuRecordBO getSudokuRecordBO() {
    return SudokuRecordBO.builder()
        .id(1)
        .sudokuDataBO(SudokuDataUtils.getSudokuData())
        .startTime(START_TIME)
        .endTime(END_TIME)
        .sudokuLevelId(1)
        .isRecord(true)
        .build();
  }

  /**
   * 模拟当前的游戏记录
   */
  public static void mockSudokuRecord(GameUtils gameUtils) {
    when(gameUtils.getSudokuRecord()).thenReturn(getSudokuRecordBO());
  }
}
