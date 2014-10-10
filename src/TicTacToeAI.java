import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TicTacToeAI {
    // Game field constants for easy access
    private static int[][] triples = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };
    private static int[] corners = {0, 2, 6, 8};
    private static int[] oppositeCorners = {6, 8, 0, 2};
    private static int[] sides = {1, 3, 5, 7};

    // Random number generator for not very important decisions. (To confuse the player)
    private static Random rand = new Random();

    static int process(Game.Marking[] field, boolean cross_is_ai) {
        // Determine markers for player and the opponent
        Game.Marking me = cross_is_ai ? Game.Marking.CROSS : Game.Marking.CIRCLE;
        Game.Marking opponent = cross_is_ai ? Game.Marking.CIRCLE : Game.Marking.CROSS;

        // 8 steps from http://en.wikipedia.org/wiki/Tic-tac-toe#Strategy

        // 1. Win
        int winningPos = canWin(field, me);
        if (winningPos != -1) {
            return winningPos;
        }

        // 2. Block
        int blockingPos = canWin(field, opponent);
        if (blockingPos != -1) {
            return blockingPos;
        }

        // 3. Fork
        List<Integer> possibleForks = findForks(field, me);
        if (!possibleForks.isEmpty()) {
            return possibleForks.get(0);
        }

        // 4. Block fork
        possibleForks = findForks(field, opponent);
        if (possibleForks.size() == 1) {
            return possibleForks.get(0); // Just block the single fork
        } else if (possibleForks.size() > 1) {
            // Create a two-in-a-row to a place where the opponent is forced to make a move so that
            // it will not enable them to create a new fork.
            // This is achieved by trying out all empty squares and seeing if two-in-a-row is
            // possible. If it is, then check that the third square can't be used as a fork for
            // the opponent's advantage.
            Game.Marking[] clonedField = Arrays.copyOf(field, field.length);
            for (int i = 0; i < clonedField.length; i++) {
                if (clonedField[i] == Game.Marking.EMPTY) {
                    clonedField[i] = me;
                    List<Integer> possibleForks2 = findForks(clonedField, opponent);
                    int win = canWin(clonedField, me);
                    if (win != -1 && !possibleForks2.contains(win)) {
                        return i;
                    }
                    clonedField[i] = Game.Marking.EMPTY;
                }
            }
        }

        // 5. Center
        if (field[4] == Game.Marking.EMPTY) {
            return 4;
        }

        // 6. Opposite corner
        // Add all available opposite corners of occupied corners to a list and then pick one
        List<Integer> empty_opposite_corner = new ArrayList<>();
        for (int i = 0; i < corners.length; i++) {
            if (field[corners[i]] == opponent && field[oppositeCorners[i]] == Game.Marking.EMPTY) {
                empty_opposite_corner.add(oppositeCorners[i]);
            }
        }

        if (!empty_opposite_corner.isEmpty()) {
            return empty_opposite_corner.get(rand.nextInt(empty_opposite_corner.size()));
        }

        // 7. Empty corner
        int emptyCorner = anyAvailable(field, corners);
        if (emptyCorner != -1) {
            return emptyCorner;
        }

        // 8. Empty side - there are no other options
        return anyAvailable(field, sides);
    }

    // Checks all triples and determines in how many ways one can make a winning move.
    private static int countWins(Game.Marking[] field, Game.Marking marker) {
        int n = 0;
        for (int[] triple : triples) {
            if (field[triple[0]] == field[triple[1]] && field[triple[2]] == Game.Marking.EMPTY
                && field[triple[0]] == marker) {
                n++;
                continue;
            }
            if (field[triple[0]] == field[triple[2]] && field[triple[1]] == Game.Marking.EMPTY
                && field[triple[0]] == marker) {
                n++;
                continue;
            }
            if (field[triple[1]] == field[triple[2]] && field[triple[0]] == Game.Marking.EMPTY
                && field[triple[1]] == marker) {
                n++;
            }
        }
        return n;
    }

    // Pick any square that can be used for winning
    private static int canWin(Game.Marking[] field, Game.Marking marker) {
        for (int[] triple : triples) {
            if (field[triple[0]] == field[triple[1]] && field[triple[2]] == Game.Marking.EMPTY
                && field[triple[0]] == marker) {
                return triple[2];
            }
            if (field[triple[0]] == field[triple[2]] && field[triple[1]] == Game.Marking.EMPTY
                && field[triple[0]] == marker) {
                return triple[1];
            }
            if (field[triple[1]] == field[triple[2]] && field[triple[0]] == Game.Marking.EMPTY
                && field[triple[1]] == marker) {
                return triple[0];
            }
        }
        return -1;
    }

    // Determines all places where a move would create a fork (2 or more winning possibilities)
    private static List<Integer> findForks(Game.Marking[] field, Game.Marking marker) {
        Game.Marking[] clonedField = Arrays.copyOf(field, field.length);
        List<Integer> forkPositions = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (clonedField[i] == Game.Marking.EMPTY) {
                clonedField[i] = marker;
                if (countWins(clonedField, marker) >= 2) {
                    forkPositions.add(i);
                }
                clonedField[i] = Game.Marking.EMPTY;
            }
        }
        return forkPositions;
    }

    // Pick an empty square from given indexes, keep it randomized for confusion
    private static int anyAvailable(Game.Marking[] field, int[] indexes) {
        List<Integer> empty = new ArrayList<>();
        for (int index : indexes) {
            if (field[index] == Game.Marking.EMPTY) {
                empty.add(index);
            }
        }

        return empty.isEmpty() ? -1 : empty.get(rand.nextInt(empty.size()));
    }
}
