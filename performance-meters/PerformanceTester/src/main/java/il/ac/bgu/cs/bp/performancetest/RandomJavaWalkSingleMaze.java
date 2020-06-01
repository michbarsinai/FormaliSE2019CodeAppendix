package il.ac.bgu.cs.bp.performancetest;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Creates a maze simulation b-program, and makes the maze walker randomly walk
 * through the maze.
 * 
 * @author michael
 */
public class RandomJavaWalkSingleMaze {
    
    static final int[][] MOVES = {
        {0,1,0,-1},
        {-1,0,1,0}
    };
    
    int mazeSize = 7;
    boolean log = false;
    boolean explode = false;
    
    public void startWalking() {
        final int maxMazeCoord = mazeSize-1;
        int curX=0;
        int curY=0;
        Random rand = new Random();
        while ( ! (curX==maxMazeCoord && curY==maxMazeCoord) ) {
            int nextStepIdx = rand.nextInt(4);
            int pCurX = curX + MOVES[0][nextStepIdx];
            int pCurY = curY + MOVES[1][nextStepIdx];
            if ( pCurX>=0 && pCurY >=0 && pCurX<mazeSize && pCurY < mazeSize ) {
                curX = pCurX;
                curY = pCurY;
            }
            if ( log ) {
                System.out.println("At: " + curX + ", " + curY );
            }
        }
        if ( explode ) {
            throw new RuntimeException("Fell into trap");
        }
    }
    
    public static void main(String[] argArr) throws IOException {
        Set<String> args = new HashSet<>(Arrays.asList(argArr));
        
        RandomJavaWalkSingleMaze rwsm = new RandomJavaWalkSingleMaze();
        rwsm.log = args.contains("log");
        args.remove("log");
        rwsm.explode = args.contains("explode");
        args.remove("explode");
        
        rwsm.mazeSize = 7;
        if ( ! args.isEmpty() ) {
            rwsm.mazeSize = Integer.parseInt(args.iterator().next());
        }        
        
        rwsm.startWalking();
    }
}