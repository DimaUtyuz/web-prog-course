package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        for (String parameter : request.getParameterMap().keySet()) {
            if (parameter.startsWith("cell_")) {
                int row = Integer.parseInt(String.valueOf(parameter.charAt(5)));
                int col = Integer.parseInt(String.valueOf(parameter.charAt(6)));
                state.onMove(row, col);
                break;
            }
        }
        view.put("state", state);
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State(3);
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        if (Objects.isNull(state)) {
            newGame(request, view);
        } else {
            view.put("state", state);
        }
    }

    public static class State {
        private final int size;
        private String phase = "RUNNING";
        private final Character[][] cells;
        private int empty;
        private boolean crossesMove = true;

        public State(int size) {
            this.size = size;
            cells = new Character[size][size];
            empty = size * size;
        }

        public void onMove(int row, int col) {
            cells[row][col] = getTurn();
            empty--;
            checkWin(col, row);
            crossesMove ^= true;
        }

        private void checkWin(int x, int y) {
            if (count(x, y, 0, 1) + count(x, y, 0, -1) + 1 >= size ||
                    count(x, y, 1, 0) + count(x, y, -1, 0) + 1 >= size ||
                    count(x, y, 1, 1) + count(x, y, -1, -1) + 1 >= size ||
                    count(x, y, 1, -1) + count(x, y, -1, 1) + 1 >= size) {
                phase = "WON_" + getTurn();
            } else if (empty == 0) {
                phase = "DRAW";
            }
        }

        private char getTurn() {
            return crossesMove ? 'X' : '0';
        }

        private int count(int x, int y, int dx, int dy) {
            char turn = cells[y][x];
            int ans = 0;
            x += dx;
            y += dy;
            while (0 <= x && x < size && 0 <= y && y < size && !Objects.isNull(cells[y][x]) && cells[y][x] == turn) {
                ans++;
                x += dx;
                y += dy;
            }
            return ans;
        }

        public int getSize() {
            return size;
        }

        public String getPhase() {
            return phase;
        }

        public Character[][] getCells() {
            return cells;
        }

        public int getEmpty() {
            return empty;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }
    }
}
