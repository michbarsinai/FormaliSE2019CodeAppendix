package il.ac.bgu.cs.bp.performancetest;

import il.ac.bgu.cs.bp.bpjs.analysis.BThreadSnapshotVisitedStateStore;
import il.ac.bgu.cs.bp.bpjs.analysis.DfsBProgramVerifier;
import il.ac.bgu.cs.bp.bpjs.analysis.ExecutionTraceInspections;
import il.ac.bgu.cs.bp.bpjs.analysis.HashVisitedStateStore;
import il.ac.bgu.cs.bp.bpjs.analysis.VerificationResult;
import il.ac.bgu.cs.bp.bpjs.analysis.VisitedStateStore;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.ResourceBProgram;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.joining;

/**
 * Simple class running a BPjs program that selects "hello world" events.
 * @author michael
 */
public class PerformanceTest {
    
    private VisitedStateStore vss;
    private int repetitions = 10;
    
    public void runTest() throws Exception {
        System.out.println("Using: " + vss.toString() );
        System.out.println("# Warmup");
        VerificationResult res = runSingleTest(10);
        System.out.println("# Warmup done (" + res.getTimeMillies() +  ")");
        vss.clear();
        
        System.out.println("# maze size, repetition, time (msec), states, edges, memory");
        for ( int mazeSize : Arrays.asList(5,7,10,15,20)) {
            for ( int rep=1; rep<=repetitions; rep++ ) {
                res = runSingleTest(mazeSize);
                System.gc();
                long usedMem = Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory();
                System.out.printf("%d, %02d, %d, %d, %d, %d\n", mazeSize, rep, res.getTimeMillies(), res.getScannedStatesCount(), res.getScannedEdgesCount(), usedMem);
            }
        }
    }

    private VerificationResult runSingleTest( int mazeSize ) throws Exception {
        final BProgram bprog = new ResourceBProgram("MazesNegative.js");
        bprog.prependSource( makeMazeSnippet(mazeSize, mazeSize) );
        
        DfsBProgramVerifier vfr = new DfsBProgramVerifier();
        vfr.setVisitedStateStore(vss);
        vfr.addInspection( ExecutionTraceInspections.FAILED_ASSERTIONS );
        vfr.setProgressListener(new ContinueDfsListener());
        
        return vfr.verify(bprog);
    }
    
    /**
     * Returns a JavaScript code snippet containing a maze with 
     * @param colCount
     * @param rowCount
     * @return JavaScript source with a maze map.
     */
    String makeMazeSnippet( int colCount, int rowCount ) {
        char[] rowTemplate = new char[colCount+2];
        List<String> rows = new ArrayList<>(rowCount);
        Arrays.fill(rowTemplate, ' ');
        rowTemplate[0]='"';
        rowTemplate[colCount+1]='"';
        
        String mostRows = new String(rowTemplate);
        rowTemplate[1]='s';
        rowTemplate[colCount]='h';
        rows.add( new String(rowTemplate) );
        for ( int i=1; i<rowCount-1; i++ ) {
            rows.add(mostRows);
        }
        rowTemplate[1]='h';
        rowTemplate[colCount]='t';
        rows.add( new String(rowTemplate) );
        
        return rows.stream().collect( joining(",\n", "var maze=[", "];" ));
    }
    
    public static void main(String[] args) throws Exception {
        
        PerformanceTest tst = new PerformanceTest();
        
        System.out.println(tst.makeMazeSnippet(5, 5));
        
        tst.vss = new HashVisitedStateStore();
        System.out.println("# Hash state store");
        tst.runTest();
        
        tst.vss = new BThreadSnapshotVisitedStateStore();
        System.out.println("# Full state store");
        tst.runTest();
        
    }
    
    
}
