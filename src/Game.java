import java.util.Arrays;

// Main game logic.
public class Game {

    private GameUI ui;
    private Turn current_turn;
    private Marking[] game_field = new Marking[9];
    private Turn cross_player;

    Game(boolean player_starts) {
        // Initialize game field and the starting player
        cross_player = current_turn = player_starts ? Turn.PLAYER : Turn.AI;
        for (int i = 0; i < game_field.length; ++i) {
            game_field[i] = Marking.EMPTY;
        }

        // Open a new UI
        ui = new GameUI(this);

        // Take turns until game is over
        while (ui.isOpen()) {
            Turn winner = isGameOver();
            if (winner == null) {
                if (current_turn == Turn.PLAYER) {
                    int move;
                    // Do not continue before a valid move by user
                    do {
                        move = ui.getMove();
                    } while (!(move == -1 || game_field[move] == Marking.EMPTY));

                    if (move == -1) {
                        break; // UI has been closed
                    }
                    doMove(move);
                } else if (current_turn == Turn.AI) {
                    int move = TicTacToeAI.process(getField(), cross_player == Turn.AI);
                    doMove(move);
                }

                current_turn = current_turn == Turn.PLAYER ? Turn.AI : Turn.PLAYER;
            } else { // Game has ended
                ui.announceOutcome(winner);
                break;
            }
        }

        // Wait till Game UI window is closed
        while (ui.isOpen()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Turn isGameOver() {
        int triples[][] = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
        };

        // Check all triples
        for (int[] triple : triples) {
            if (game_field[triple[0]] != Marking.EMPTY
                && game_field[triple[0]] == game_field[triple[1]]
                && game_field[triple[1]] == game_field[triple[2]]) {
                if (game_field[triple[0]] == Marking.CROSS) {
                    return cross_player;
                } else {
                    return cross_player == Turn.PLAYER ? Turn.AI : Turn.PLAYER;
                }
            }
        }

        // Check if it is still possible to make a move
        for (Marking square : game_field) {
            if (square == Marking.EMPTY) {
                return null;
            }
        }

        // Otherwise it has to be a draw
        return Turn.NOONE;
    }

    private void doMove(int move) {
        if (game_field[move] == Marking.EMPTY) {
            game_field[move] = current_turn == cross_player ? Marking.CROSS : Marking.CIRCLE;
            ui.repaint();
        } else {
            // Both UI and the AI shouldn't be able to do this.
            System.err.println("Invalid move attempted by " + current_turn + " to " + move + ".");
        }
    }

    // Return a copy of the current game field
    public Marking[] getField() {
        return Arrays.copyOf(game_field, game_field.length);
    }

    // Decides winner or whose turn it is
    enum Turn {
        PLAYER,
        AI,
        NOONE
    }

    // The state of a square in field
    enum Marking {
        EMPTY,
        CROSS,
        CIRCLE
    }
}
