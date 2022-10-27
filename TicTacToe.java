package Project_2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

public class TicTacToe {

    BoardState gameboard;

    Scanner scan;

    public Vector<Integer> findBestMove(BoardState board, boolean isMax) {
        int bestVal = Integer.MIN_VALUE;

        int bestMoveX = 0;
        int bestMoveY = 0;

        Vector<Integer> move = new Vector<Integer>();

        for(int x = 0; x < board.size; x++) {
            for(int y = 0; y < board.size; y++) {
                if(board.board[x][y] == null) {
                    board.board[x][y] = isMax ? "X" : "O";
                    int moveVal = MinimaxSearch(board, 0, isMax);
                    board.board[x][y] = null;

                    if(moveVal > bestVal) {
                        bestVal = moveVal;
                        bestMoveX = x;
                        bestMoveY = y;
                    }
                }
            }
        }

        move.add(bestMoveX);
        move.add(bestMoveY);

        return move;

    }

    //Returns an integer, which will allow us to determine the correct path to take each turn
    int MinimaxSearch(BoardState state, int depth, boolean isMax) {
        int score = state.evaluate();

        if(score == 10) {
            return score;
        }

        if(score == -10) {
            return score;
        }

        if(state.isTerminalState()) {
            return 0;
        }

        if(isMax) {
            int best = Integer.MIN_VALUE;

            for(int x = 0; x < state.size; x++) {
                for(int y = 0; y < state.size; y++) {
                    if(state.board[x][y] == null) {
                        state.board[x][y] = "X";

                        best = Math.max(best, MinimaxSearch(state, depth + 1, !isMax));
                    
                        state.board[x][y] = null;
                    }
                }
            }

            return best;
        } else {
            int best = Integer.MAX_VALUE;

            for(int x = 0; x < state.size; x++) {
                for(int y = 0; y < state.size; y++) {
                    if(state.board[x][y] == null) {
                        state.board[x][y] = "O";

                        best = Math.min(best, MinimaxSearch(state, depth + 1, !isMax));
                    
                        state.board[x][y] = null;
                    }
                }
            }

            return best;
        }
    }

    public boolean takeTurn(boolean isMax) {
        System.out.println("Current Board State:");
        gameboard.printBoard();

        int x = 0, y = 0;

        boolean moveMade = false;
        scan.reset();

        while(!moveMade) {
            System.out.println("What column do you want to play? type HELP for an expert reccomendation");
            String input = scan.nextLine();
            if(input.equals("HELP")) {
                Vector<Integer> solution = findBestMove(gameboard, isMax);
                System.out.println("The most optimal move is: (" + solution.get(0) + ", " + solution.get(1) + ")");
            } else if(input.matches("\\d")) {
                if(((Integer.parseInt(input)) < 0) || (Integer.parseInt(input) - 1) > gameboard.size) {
                    System.out.println("WARNING: input exceeds map range");
                } else {
                    x = (Integer.parseInt(input));
                    moveMade = true;
                }
            }
        }

        
        moveMade = false;

        while(!moveMade) {
            System.out.println("What Row do you want to play in?");
            String input = scan.nextLine();

            if(input.equals("HELP")) {
                Vector<Integer> solution = findBestMove(gameboard, isMax);
                System.out.println("The most optimal move is: (" + solution.get(0) + ", " + solution.get(1) + ")");
            } else if(input.matches("\\d")) {
                if(((Integer.parseInt(input)) < 0) || (Integer.parseInt(input) - 1) > gameboard.size) {
                    System.out.println("WARNING: input exceeds map range");
                } else {
                    y = (Integer.parseInt(input));
                    moveMade = true;
                }
            }
        }

        if(gameboard.board[x][y] == null) {
            gameboard.setTile(x, y, (gameboard.depth % 2 != 0) ? "X" : "O");
        } else {
            System.out.println("WARNING: NOT A VALID PLAY");
            return takeTurn(isMax);
        }

        return true;

    }

    public TicTacToe() {
        System.out.println("How big do you want the board to be?");
        scan = new Scanner(System.in);
        int size = scan.nextInt();

        gameboard = new BoardState(size);

        System.out.println("Which player would you like to be? 1 or 2?");
        int player = scan.nextInt();

        if(player == 1) {
            while(gameboard.isTerminalState() == false) {
                //System.out.println("Current minmax score: " + MAX_VALUE(gameboard));
                boolean yield = takeTurn(true);
                System.out.println("Finding best move for Computer...");
    
                if(gameboard.isTerminalState() == false) {
                    Vector<Integer> solution = findBestMove(gameboard, false);
                    int x = solution.get(0), y = solution.get(1);

                    gameboard.setTile(x, y, "O");
                    
                }
            }

            System.out.println("Final board state: ");
            gameboard.printBoard();

            if(gameboard.isBoardWinner("X")) {
                System.out.println("A winner is you!");
            }else if(gameboard.isBoardWinner("O")) {
                System.out.println("Better luck next time");
            } else {
                System.out.println("There was a Tie!");
            }
        } else {
            while(gameboard.isTerminalState() == false) {
            
    
                System.out.println("Finding best move for Computer...");
                Vector<Integer> solution = findBestMove(gameboard, false);
                int x = solution.get(0), y = solution.get(1);

                gameboard.setTile(x, y, "O");
                


                if(gameboard.isTerminalState() == false) {
                    //System.out.println("Current minmax score: " + MIN_VALUE(gameboard));
                    takeTurn(false);
                }
            }

            System.out.println("Final board state: ");
            gameboard.printBoard();

            if(gameboard.isBoardWinner("O")) {
                System.out.println("A winner is you!");
            }else if(gameboard.isBoardWinner("X")) {
                System.out.println("Better luck next time");
            } else {
                System.out.println("There was a Tie!");
            }
        }

        

    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
