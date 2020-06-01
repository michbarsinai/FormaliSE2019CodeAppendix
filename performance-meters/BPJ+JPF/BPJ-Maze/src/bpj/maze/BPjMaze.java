/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bpj.maze;

import bp.BProgram;
import bp.BThread;
import bp.Event;
import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import bp.eventSets.EventSetInterface;
import bp.exceptions.BPJRequestableSetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author michael
 */
public class BPjMaze {

    /**
     * @param argArr the command line arguments
     */
    public static void main(String[] argArr) {
        
        Set<String> args = new HashSet<>(Arrays.asList(argArr));
        BProgram bprog = new BProgram();

        if ( args.contains("jpf-sanity") ) {
            miniBackTrack();
            return;
        }
        if ( args.contains("bpj-sanity") ) {
            miniBProg();
            return;
        }
        boolean explode = args.contains("explode");
        args.remove("explode");
        
        int mazeSize = 7;
        if ( ! args.isEmpty() ) {
            mazeSize = Integer.parseInt(args.iterator().next());
        }     
        int id=0;
        for ( int x=0; x<mazeSize; x++ ) {
            for ( int y=0; y<mazeSize; y++ ) {
                bprog.add(new BTCell(x,y), (double)(++id));
            }
        }        
        
        bprog.add( new StartCell(0,0), (double)(++id) );
        bprog.add( new TrapCell(mazeSize-1,mazeSize-1), (double)(++id) );
        
        if ( explode ) {
            bprog.add(new ExploderBT(), (double)(++id));
        }
        
        
        bprog.startAll();
        bprog.joinAll();
        System.out.println("main DONE");
    }
    
    static class BTCell extends BThread {
        int x;
        int y;

        public BTCell(int x, int y) {
            this.x = x;
            this.y = y;
            setName("c:" + x + "," + y);
        }
        
        @Override
        public void runBThread() throws InterruptedException, BPJRequestableSetException {
            boolean go = true;
            EventSetInterface waitFors = Events.or( Events.TRAPPED, Events.adjacentCellEntries(x, y));
            EventSetInterface waitOnEnter = Events.or(Events.ENTRY_EVENTS, Events.TRAPPED);
            while ( go ) {
                getBProgram().bSync( none, waitFors, none);
                Event e = getBProgram().lastEvent;
                if ( e.equals(Events.TRAPPED) ) {
                    go = false;
                } else {
                    getBProgram().bSync( new Events.EntryEvent(x,y), waitOnEnter, none);
                }
                
                if ( getBProgram().lastEvent.equals(Events.TRAPPED) ) {
                    go = false;
                }
            }
        }
    }
    
    static class StartCell extends BThread {
        int x;
        int y;

        public StartCell(int x, int y) {
            this.x = x;
            this.y = y;
            setName("s:" + x + "," + y);
        }
        
        @Override
        public void runBThread() throws InterruptedException, BPJRequestableSetException {
            getBProgram().bSync( new Events.EntryEvent(x,y), none, none);
            System.out.println("Start done");
        }
    }
    
    static class TrapCell extends BThread {
        int x;
        int y;

        public TrapCell(int x, int y) {
            this.x = x;
            this.y = y;
            setName("s:" + x + "," + y);
        }
        
        @Override
        public void runBThread() throws InterruptedException, BPJRequestableSetException {
            getBProgram().bSync( none, new Events.EntryEvent(x, y), none);
            getBProgram().bSync( Events.TRAPPED, none, Events.ENTRY_EVENTS);
            System.out.println("Trap done");
        }
    }
    
    static class ExploderBT extends BThread {
        
        @Override
        public void runBThread() throws InterruptedException, BPJRequestableSetException {
            getBProgram().bSync( none, Events.TRAPPED, none);
            throw new RuntimeException("Exploded");
        }
    }
    
    static void miniBackTrack() {
        Random rnd = new Random();
        System.out.println("rnd #1: " + rnd.nextInt(4));
    }
    
    static void miniBProg() {
        BProgram bprog = new BProgram();
        bprog.add(new BThread(){
            @Override
            public void runBThread() throws InterruptedException, BPJRequestableSetException {
                getBProgram().bSync(new Event("HOT"), none, none);
            }
        }, 1d);
        bprog.add(new BThread(){
            @Override
            public void runBThread() throws InterruptedException, BPJRequestableSetException {
                getBProgram().bSync(new Event("COLD"), none, none);
            }
        }, 2d);
        bprog.add(new BThread(){
            @Override
            public void runBThread() throws InterruptedException, BPJRequestableSetException {
                String eventsSoFar = "";
                getBProgram().bSync(none, all, none);
                eventsSoFar += getBProgram().lastEvent.getName() + " ";
                getBProgram().bSync(none, all, none);
                eventsSoFar += getBProgram().lastEvent.getName() + " ";
                
                System.out.println("Events so far: " + eventsSoFar);
            }
        }, 3d);
        bprog.startAll();
        bprog.joinAll();
    }
    
}
