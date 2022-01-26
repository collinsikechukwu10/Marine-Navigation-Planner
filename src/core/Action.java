package core;

import java.util.ArrayList;
import java.util.List;

public enum Action {
    MOVE_RIGHT(0, 1),
    MOVE_LEFT(0, -1),
    MOVE_UP(-1, 0),
    MOVE_DOWN(1, 0),
    MOVE_UP_RIGHT(-1, 1),
    MOVE_UP_LEFT(-1, -1),
    MOVE_DOWN_RIGHT(1, 1),
    MOVE_DOWN_LEFT(1, -1);

    private final int rowMove;
    private final int colMove;

    Action(int rowMove, int colMove) {
        this.rowMove = rowMove;
        this.colMove = colMove;
    }

    public Coord move(Coord currentState) {
        return new Coord(currentState.getR() + rowMove, currentState.getC() + colMove);
    }

    public static ArrayList<Action> getAllowedActions(boolean upwardsFacingTriangle, boolean useAdditionalMoves) {
        ArrayList<Action> moves;
        if (upwardsFacingTriangle) {
            // upwards facing triangle
            if (useAdditionalMoves) {
                moves = new ArrayList<>(List.of(MOVE_RIGHT, MOVE_DOWN_RIGHT, MOVE_DOWN, MOVE_DOWN_LEFT, MOVE_LEFT));
            } else {
                moves = new ArrayList<>(List.of(MOVE_RIGHT, MOVE_DOWN, MOVE_LEFT));
            }
        } else {
            if (useAdditionalMoves) {
                moves = new ArrayList<>(List.of(MOVE_RIGHT, MOVE_LEFT, MOVE_UP_LEFT, MOVE_UP, MOVE_UP_RIGHT));
            } else {
                //downward facing triangle
                moves = new ArrayList<>(List.of(MOVE_RIGHT, MOVE_LEFT, MOVE_UP));
            }
        }
        return moves;
    }

}
