package Project_2;

import java.util.ArrayList;
import java.util.Vector;

public class BoardState {
    
    public String[][] board;
    public int size;

    public int depth = 1;
    
    public int evaluate() {
        if(isTerminalState()) {
            if(isBoardWinner("X")) {
                return 1;
            }

            if(isBoardWinner("O")) {
                return -1;
            }
        }

        return 0;
    }

    public BoardState(int size) {
        this.size = size;
        board = new String[size][size];
    }

    public String getTile(int x, int y) {
        return board[x][y];
    }

    public void setTile(int x, int y, String character) {
        board[x][y] = character;
    }

    public BoardState copyBoard() {
        BoardState copy = new BoardState(size);
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                copy.board[x][y] = board[x][y];
            }
        }
        copy.depth = this.depth;

        return copy;
    }

    public boolean isTerminalState() {
        if(isBoardWinner("X") || isBoardWinner("O")) {
            return true;
        }

        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                if(board[x][y] == null) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printBoard() {
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                if(board[x][y] == null) {
                    System.out.print("_");
                } else {
                    System.out.print(board[x][y]);
                }
            }
            System.out.print('\n');
        }
    }

    public int getCountBackDiag(String character) {
        int count = 0;

        //Diagonal top corner
        for(int i = 0; i < size; i++) {
            if(board[i][i] != null && board[i][i].equals(character)) {
                count++;
            }
        }

        return count;
    }

    public int getCountForwardDiag(String character) {
        int count = 0;
        //Diagonal bottom corner
        for(int i = 0; i < size; i++) {
            if(board[i][(size-1) - i] != null && board[i][(size-1) - i].equals(character)) {
                count++;
            }
        }

        return count;
    }

    public boolean isBoardWinner(String character) {
        int count = 0;

        //Diagonal top corner
        for(int i = 0; i < size; i++) {
            if(board[i][i] != null && board[i][i].equals(character)) {
                count++;
            }
        }

        if(count == size) {
            return true;
        }

        count = 0;

        //Diagonal bottom corner
        for(int i = 0; i < size; i++) {
            if(board[i][(size-1) - i] != null && board[i][(size-1) - i].equals(character)) {
                count++;
            }
        }

        if(count == size) {
            return true;
        }

        //vertical
        for(int x = 0; x < size; x++) {
            count = 0;
            for(int y = 0; y < size; y++) {
                if(board[x][y] != null && board[x][y].equals(character)) {
                    count++;
                }
            }

            if(count == size) {
                return true;
            }
        }

        count = 0;

        //Horizontal
        for(int y = 0; y < size; y++) {
            count = 0;
            for(int x = 0; x < size; x++) {
                if(board[x][y] != null && board[x][y].equals(character)) {
                    count++;
                }
            }

            if(count == size) {
                return true;
            }
        }

        return false;
    }

}
