package ArtificialIntelligenceProject4;
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

        //We win
        if(score == 1) {
            return score;
        }

        if(score == -1) {
            return score;
        }

        //The game is a tie
        if(state.isTerminalState()) {
            return 0;
        }

        //Check for best move if we're player X
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
        } else { //We're player O, find the WORST move to leave X in
            int best = Integer.MAX_VALUE;

            for(int x = 0; x < state.size; x++) {
                for(int y = 0; y < state.size; y++) {
                    if(state.board[x][y] == null) {
                        state.board[x][y] = "O";

                        //Find the step the next guy would probably be left in
                        best = Math.min(best, MinimaxSearch(state, depth + 1, !isMax));
                    
                        state.board[x][y] = null;
                    }
                }
            }

            return best;
        }
    }

    //Handle player input, find where they want to go (or if they want to get a hint)
    public boolean takeTurn(boolean isMax) {
        System.out.println("Current Board State:");
        gameboard.printBoard();

        int x = 0, y = 0;

        boolean moveMade = false;
        scan.reset();

        //Keep trying until they get it right
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
            gameboard.setTile(x, y, isMax ? "X" : "O");
        } else {
            System.out.println("WARNING: NOT A VALID PLAY");
            return takeTurn(isMax);
        }

        return true;

    }

    //Main game logic, do all the things
    public TicTacToe() {
        System.out.println("How big do you want the board to be?");
        scan = new Scanner(System.in);
        int size = scan.nextInt();

        gameboard = new BoardState(size);

        System.out.println("Which player would you like to be? 1 or 2?");
        int player = scan.nextInt();

        //We want to be player 1, X
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
        } else { // we want to be player 2, O
            while(gameboard.isTerminalState() == false) {
            
                //Find the best solution for this move
                System.out.println("Finding best move for Computer...");
                Vector<Integer> solution = findBestMove(gameboard, true);
                int x = solution.get(0), y = solution.get(1);

                gameboard.setTile(x, y, "X");
                


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
