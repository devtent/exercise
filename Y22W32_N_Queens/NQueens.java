package Y22W32_N_Queens;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author pnn
 * @since 2022/8/13
 */
public class NQueens {
    @Test
    public void test() {
        assert 1 == solveNQueens(1).size();
        assert 0 == solveNQueens(2).size();
        assert 0 == solveNQueens(3).size();
        assert 92 == solveNQueens(8).size();
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        // 第 i 个皇后所在列为 queens[i]
        int[] queens = new int[n];

        // 以下初始化可以删掉，相应的每次回溯完成后，也没必要修正为 -1
        // Arrays.fill(queens, -1);
        backtrack(res, queens, 0,
                new HashSet<>(), new HashSet<>(), new HashSet<>()
        );
        return res;
    }

    /**
     * @param res
     * @param queens
     * @param rowNum  当前所在行
     * @param columns 已遍历过的列
     * @param s1      已遍历过的从左向右的斜线
     * @param s2      已遍历过的从右向左的斜线
     */
    void backtrack(List<List<String>> res, int[] queens, int rowNum,
                   Set<Integer> columns, Set<Integer> s1, Set<Integer> s2) {
        //
        if (rowNum == queens.length) {
            res.add(asBoard(queens));
            return;
        }
        // 遍历每一列
        for (int i = 0; i < queens.length; i++) {
            if (columns.contains(i)
                    || s1.contains(rowNum - i)
                    || s2.contains(rowNum + i)
            ) {
                continue;
            }
            // 设置状态
            queens[rowNum] = i;
            columns.add(i);
            s1.add(rowNum - i);
            s2.add(rowNum + i);

            backtrack(res, queens, rowNum + 1, columns, s1, s2);

            // 恢复本轮状态
            // queens[rowNum] = -1;
            columns.remove(i);
            s1.remove(rowNum - i);
            s2.remove(rowNum + i);
        }
    }

    List<String> asBoard(int[] queens) {
        List<String> board = new ArrayList<>();
        for (int queen : queens) {
            char[] row = new char[queens.length];
            Arrays.fill(row, '.');
            row[queen] = 'Q';
            board.add(String.valueOf(row));
        }
        return board;
    }

}
