import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
    private Config finalConfig;
    private boolean solutionFound = false;
    private class Config implements Comparable<Config>{
        private Board board;
        private Config predecessor;
        private int cost = 0;
        private int manhattan = 0;
        public Config(Board _board, Config _predecessor, int _cost){
            board = _board;
            predecessor = _predecessor;
            cost = _cost;
            manhattan = board.manhattan();
        }
        public Config (Config _config){
            board = _config.board;
            predecessor = _config.predecessor;
            cost = _config.cost;
            manhattan = _config.manhattan;
        }
        public int compareTo(Config _config){
            return (manhattan + cost) - (_config.manhattan + _config.cost);
        }
    }
    public Solver(Board initial){
        MinPQ<Config> gameTree = new MinPQ<Config>();
        gameTree.insert(new Config(initial,null,0));
        MinPQ<Config> gameTreeTwin = new MinPQ<Config>();
        gameTreeTwin.insert((new Config(initial.twin(),null,0)));
        //int step = 1;
        while (true){
            if (gameTree.isEmpty()){
                break;
            }
            Config top = gameTree.delMin();
            //System.out.println(top.board);
            if (top.board.isGoal()){
                finalConfig = top;
                solutionFound = true;
                break;
            }
            for (Board neibors : top.board.neighbors()){
                if (top.predecessor == null || (!neibors.equals(top.predecessor.board))){
                    Config next = new Config(neibors,top,top.cost+1);
                    gameTree.insert(next);
                }
            }
            if (!gameTreeTwin.isEmpty()) {
                top = gameTreeTwin.delMin();
                //System.out.println(top.board);
                if (top.board.isGoal()){
                   // System.out.println(1);
                    break;
                }
                for (Board neibors : top.board.neighbors()) {
                    if (top.predecessor == null || (!neibors.equals(top.predecessor.board))) {
                        Config next = new Config(neibors, top, top.cost + 1);
                        gameTreeTwin.insert(next);
                    }
                }
            }
            //step++;
        }
        //System.out.println(step);
    }           // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable(){
        return solutionFound;
    }            // is the initial board solvable?
    public int moves(){
        if (!solutionFound) return -1;
        return finalConfig.cost;
    }                     // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution(){
        if (!solutionFound) return null;
        List<Board> solutionList = new ArrayList<>();
        Config config = new Config(finalConfig);
        while (config != null){
            solutionList.add((Board)(config.board));
            config = config.predecessor;
        }
        Collections.reverse(solutionList);
        return solutionList;
    }      // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args){

    } // solve a slider puzzle (given below)
}
