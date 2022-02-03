package core;

import java.util.ArrayList;
import java.util.List;

/**
 * Action Enum.
 * Contains all the possible actions that exists.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public enum Action {
    /**
     * Move right action.
     */
    MOVE_RIGHT(0, 1),
    /**
     * Move left action.
     */
    MOVE_LEFT(0, -1),
    /**
     * Move up action.
     */
    MOVE_UP(-1, 0),
    /**
     * Move down action.
     */
    MOVE_DOWN(1, 0),
    /**
     * Move up and right action.
     */
    MOVE_UP_RIGHT(-1, 1),
    /**
     * Move up and left action.
     */
    MOVE_UP_LEFT(-1, -1),
    /**
     * Move down and right action.
     */
    MOVE_DOWN_RIGHT(1, 1),
    /**
     * Move down and left action.
     */
    MOVE_DOWN_LEFT(1, -1);

    private final int rowMove;
    private final int colMove;

    /**
     * Constructor specifying the amount to rows and columns to move by.
     *
     * @param rowMove number of rows to move
     * @param colMove number of columns to move
     */
    Action(int rowMove, int colMove) {
        this.rowMove = rowMove;
        this.colMove = colMove;
    }

    /**
     * Apply an action to a state
     *
     * @param currentState state to apply an action
     * @return new state
     */
    public Coord move(Coord currentState) {
        return new Coord(currentState.getR() + rowMove, currentState.getC() + colMove);
    }

    /**
     * Gets all valid actions for a state.
     *
     * @param state              current state
     * @param useAdditionalMoves option to allow advacned moves
     * @return valid moves that a state can use to transition into another state
     */
    public static ArrayList<Action> getAllowedActions(Coord state, boolean useAdditionalMoves) {
        ArrayList<Action> moves;
        boolean upwardsFacingTriangle = (state.getR() + state.getC()) % 2 == 0;
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
