package il.ac.bgu.cs.bp.performancetest;

import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.execution.listeners.PrintBProgramRunnerListener;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.StringBProgram;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A random walk of a maze.
 * @author michael
 */
public class RandomBPjsWalkSingleMaze {
    
    int mazeSize = 7;
    
    public void run() {
        final BProgram bprog = new StringBProgram(CODE);
        bprog.prependSource( new PerformanceTest().makeMazeSnippet(mazeSize, mazeSize) );
        
        BProgramRunner rnr = new BProgramRunner(bprog);
        
        rnr.addListener(new PrintBProgramRunnerListener());
        
        rnr.run();
    }
    
    public static void main(String[] argArr) {
        Set<String> args = new HashSet<>(Arrays.asList(argArr));
        
        RandomBPjsWalkSingleMaze rwsm = new RandomBPjsWalkSingleMaze();
//        rwsm.log = args.contains("log");
//        args.remove("log");
//        rwsm.explode = args.contains("explode");
//        args.remove("explode");
        
        rwsm.mazeSize = 7;
        if ( ! args.isEmpty() ) {
            rwsm.mazeSize = Integer.parseInt(args.iterator().next());
        }        
        
        rwsm.run();
    }
    
    static final String CODE = "var ROBOT_TRAPPED_EVENT = bp.Event(\"Robot fell into trap\");\n" +
"\n" +
"function enterEvent(c,r) {\n" +
"    return bp.Event(\"Enter (\" + c + \",\"  + r + \")\");//, {col:c, row:r});\n" +
"}\n" +
"\n" +
"var anyEntrance = bp.EventSet(\"AnyEntrance\", function(evt){\n" +
"   return evt.name.indexOf(\"Enter\") === 0;\n" +
"});\n" +
"\n" +
"function adjacentCellEntries(col, row) {\n" +
"    return [enterEvent(col + 1, row), enterEvent(col - 1, row),\n" +
"        enterEvent(col, row + 1), enterEvent(col, row - 1)];\n" +
"\n" +
"}\n" +
"\n" +
"var cellWait = [anyEntrance, ROBOT_TRAPPED_EVENT];\n" +
"\n" +
"////////////////////////\n" +
"///// functions \n" +
"function parseMaze(mazeLines) {\n" +
"    for ( var row=0; row<mazeLines.length; row++ ) {\n" +
"        for ( var col=0; col<mazeLines[row].length; col++ ) {\n" +
"            var currentPixel = mazeLines[row].substring(col,col+1);\n" +
"            if ( \" tsh\".indexOf(currentPixel) > -1 ) {\n" +
"                addSpaceCell(col, row);\n" +
"                if ( currentPixel===\"t\" ) addTrapCell(col, row);\n" +
"                if ( currentPixel===\"s\" ) addStartCell(col, row);\n" +
"                if ( currentPixel===\"h\" ) addHotCell(col, row);\n" +
"            }\n" +
"        }\n" +
"    }\n" +
"}\n" +
"\n" +
"\n" +
"/**\n" +
" * A cell the maze solver can enter. Waits for entrances to one of the cell's\n" +
" * neighbours, then requests entrance to itself.\n" +
" * @param {Number} col\n" +
" * @param {Number} row\n" +
" * @returns {undefined}\n" +
" */\n" +
"function addSpaceCell( col, row ) {\n" +
"    bp.registerBThread(\"Space(c:\"+col+\" r:\"+row+\")\",\n" +
"        function() {\n" +
"            while ( true ) {\n" +
"                bp.sync({waitFor:adjacentCellEntries(col, row)});\n" +
"                bp.sync({\n" +
"                    request: enterEvent(col, row),\n" +
"                    waitFor: cellWait\n" +
"                });\n" +
"            }\n" +
"        }\n" +
"    );\n" +
"}\n" +
"\n" +
"/**\n" +
" * Waits for an event signaling the entrance to the \n" +
" * target cell, then blocks everything.\n" +
" * @param {Number} col\n" +
" * @param {Number} row\n" +
" * @returns {undefined}\n" +
" */\n" +
"function addTrapCell(col, row) {\n" +
"    bp.registerBThread(\"Trap(c:\"+col+\" r:\"+row+\")\", function(){\n" +
"       while ( true ) {\n" +
"	       bp.sync({\n" +
"	           waitFor: enterEvent(col, row)\n" +
"	       }); \n" +
"	       \n" +
"	       bp.sync({\n" +
"	           request: ROBOT_TRAPPED_EVENT,\n" +
"	           block: bp.allExcept( ROBOT_TRAPPED_EVENT )\n" +
"	       });\n" +
"       }\n" +
"    });\n" +
"}\n" +
"\n" +
"function addStartCell(col, row) {\n" +
"    bp.registerBThread(\"Starter(c:\"+col+\" r:\"+row+\")\", function() {\n" +
"       bp.sync({\n" +
"          request:enterEvent(col,row) \n" +
"       });\n" +
"    });\n" +
"}\n" +
"\n" +
"function addHotCell( col, row ) {\n" +
"    bp.registerBThread(\"Hot(c:\"+col+\" r:\"+row+\")\", function(){\n" +
"       while (true) {\n" +
"          bp.sync({\n" +
"	           waitFor: enterEvent(col, row)\n" +
"	      });\n" +
"          bp.hot(true).sync({\n" +
"            waitFor: cellWait\n" +
"          });\n" +
"       } \n" +
"    });\n" +
"}\n" +
"\n" +
"parseMaze(maze);\n" +
"\n" +
"bp.registerBThread(\"Robot not falling into trap\", function(){\n" +
"	bp.sync({waitFor:ROBOT_TRAPPED_EVENT});\n" +
"	bp.ASSERT(false,\"The robot fell into the trap.\");\n" +
"});";
}
