package il.ac.bgu.cs.bp.performancetest;

import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.execution.listeners.BProgramRunnerListenerAdapter;
import il.ac.bgu.cs.bp.bpjs.model.BEvent;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.ResourceBProgram;
import java.util.Arrays;

/**
 * Runs the walker in an empty floor 1000 steps. Times it.
 * 
 * @author michael
 */
public class ExecutionPerformanceTest {
   
    public long testSingleRun( int mazeSize ) {
        
        final BProgram bprog = new ResourceBProgram("MazesNegative.js");
        bprog.prependSource( new PerformanceTest().makeMazeSnippet(mazeSize, mazeSize).replaceAll("t"," ") );
        
        final BProgramRunner rnr = new BProgramRunner();
        rnr.addListener(new BProgramRunnerListenerAdapter() {
            int stepCount = 0;

            @Override
            public void eventSelected(BProgram bp, BEvent theEvent) {
                if ( theEvent.name.contains("Enter") ) {
                    stepCount++;
                    if (stepCount == 1000 ) {
                        rnr.halt();
                    }
                }
            }
            
        });
        
        rnr.setBProgram(bprog);
        long startTime = System.currentTimeMillis();
        rnr.run();
        long endTime = System.currentTimeMillis();
        
        return endTime-startTime;
    }
    
    public static void main(String[] args) {
        ExecutionPerformanceTest ept = new ExecutionPerformanceTest();
        
        System.out.println("#warmup");
        System.out.println(ept.testSingleRun(10));
        System.out.println("#warmup done");
        
        int repetitions = 10;
        Arrays.asList(5,10,20,50,100).forEach( size -> {
            for ( int i=00; i<repetitions; i++ ) {
                System.out.println( size + "," + ept.testSingleRun(size) );
            }
        });
        
    }
    
}
