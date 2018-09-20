import java.util.ArrayList;

public class Board {
    private int[][] board;
    private int[] posRow, posCol;
    private int n;
    public Board(int[][] blocks){
        n = blocks.length;
        board = new int[n][n];
        posRow = new int[n*n];
        posCol = new int[n*n];
        for (int i=0;i<n;i++)
            for (int j=0;j<n;j++) {
                board[i][j] = blocks[i][j];
                posRow[board[i][j]] = i+1;
                posCol[board[i][j]] = j+1;
            }
    }           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public int dimension(){
        return n;
    }                 // board dimension n
    public int hamming(){
        int hammingCounting=0;
        for (int i=1;i<n*n;i++){
            if ((posRow[i]-1)*n + posCol[i] != i)
                hammingCounting++;
        }
        return hammingCounting;
    }                   // number of blocks out of place
    public int manhattan(){
        int manhattanCounting=0;
        for (int i=1;i<n*n;i++){
            int row = ((i-1)/n+1);
            int col = i - (row-1)*n;
            manhattanCounting+= Math.abs(posRow[i] - row) + Math.abs(posCol[i] - col);
        }
        return manhattanCounting;
    }                 // sum of Manhattan distances between blocks and goal
    public boolean isGoal(){
        return hamming() == 0;
    }               // is this board the goal board?
    private void swap(int x1,int y1,int x2,int y2){
        //System.out.println(x1 + " " + y1 + "       " + x2 +" " +y2);
        int tmp = board[x1-1][y1-1];
        board[x1-1][y1-1]= board[x2-1][y2-1];
        board[x2-1][y2-1]= tmp;
    }
    public Board twin() {
        for (int i = 1 ;i<= n ; i++)
            for (int j = 1 ; j < n; j++){
                if (board[i-1][j-1]!=0 && board[i-1][j]!=0){
                    swap(i,j,i,j+1);
                    Board twinBoard = new Board(board);
                    swap(i,j,i,j+1);
                    return twinBoard;
                }
            }
            return null;
    }                   // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y){
        if (this == y) return true;
        if (((Board)y).n != n) return false;
        if ((!(y instanceof Board))) return false;
        for (int i=0; i < n;i++)
            for(int j=0; j < n;j++)
                if (((Board)y).board[i][j] != board[i][j]) return false;
                return true;
    }        // does this board equal y?
    public Iterable<Board> neighbors(){
        ArrayList<Board> neightborList = new ArrayList<Board>();
        if (posRow[0] > 1){
            swap(posRow[0],posCol[0],posRow[0] - 1,posCol[0]);
            neightborList.add(new Board(board));
            swap(posRow[0],posCol[0],posRow[0] - 1,posCol[0]);
        }
        if (posCol[0] > 1){
            swap(posRow[0],posCol[0],posRow[0],posCol[0] - 1);
            neightborList.add(new Board(board));
            swap(posRow[0],posCol[0],posRow[0],posCol[0] - 1);
        }
        if (posRow[0] < n){
            swap(posRow[0],posCol[0],posRow[0] + 1,posCol[0]);
            neightborList.add(new Board(board));
            swap(posRow[0],posCol[0],posRow[0] + 1,posCol[0]);
        }
        if (posCol[0] < n){
            swap(posRow[0],posCol[0],posRow[0],posCol[0] + 1);
            neightborList.add(new Board(board));
            swap(posRow[0],posCol[0],posRow[0],posCol[0] + 1);
        }
        return neightborList;
    }     // all neighboring boards
    public String toString(){
        StringBuilder stringOfBoard = new StringBuilder();
        stringOfBoard.append(n + "\n");
        for (int i = 0; i < n;i++) {
            for (int j = 0; j < n; j++)
                stringOfBoard.append(board[i][j] +" ");
            stringOfBoard.append("\n");
        }
        return  stringOfBoard.toString();
    }               // string representation of this board (in the output format specified below)

    public static void main(String[] args){

    } // unit tests (not graded)
}
