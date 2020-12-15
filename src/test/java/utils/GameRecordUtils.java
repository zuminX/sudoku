package utils;

import static org.powermock.api.mockito.PowerMockito.when;

import com.sudoku.common.utils.sudoku.GameUtils;
import com.sudoku.project.model.bo.GameRecordBO;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class GameRecordUtils {

  public static final int SPEND_TIME = 600_000;

  public static final LocalDateTime START_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);

  public static final LocalDateTime END_TIME = START_TIME.plus(SPEND_TIME, ChronoUnit.MILLIS);


  private GameRecordUtils() {
  }

  public static GameRecordBO getGameRecordBO() {
    return GameRecordBO.builder()
        .id(1)
        .sudokuDataBO(SudokuDataUtils.getSudokuData())
        .startTime(START_TIME)
        .endTime(END_TIME)
        .correct(true)
        .sudokuLevelId(1)
        .userId(1)
        .isRecord(true)
        .build();
  }

  /**
   * 模拟当前的游戏记录
   */
  public static void mockGameRecord(GameUtils gameUtils) {
    when(gameUtils.getGameRecord()).thenReturn(getGameRecordBO());
  }
}
