/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bpj.maze;

import bp.Event;
import bp.eventSets.EventSetInterface;

/**
 *
 * @author michael
 */
public class Events {
    
    public static final Event TRAPPED = new Event("TRAPPED");
    
    public static EventSetInterface adjacentCellEntries( int anX, int aY ) {
        return new EventSetInterface() {
            @Override
            public boolean contains(Object o) {
                if ( o instanceof EntryEvent ) {
                    EntryEvent ee = (EntryEvent) o;
                    return (   ((Math.abs(ee.x-anX)==1) && ee.y==aY  )
                            || ((Math.abs(ee.y-aY)==1)  && ee.x==anX ) );
                } else return false;
            }
        };
    }
    
    public static EventSetInterface or(EventSetInterface a, EventSetInterface b) {
        return new EventSetInterface() {
            @Override
            public boolean contains(Object o) {
                return a.contains(o) || b.contains(o);
            }
        };
    }
    
    public static EventSetInterface ENTRY_EVENTS = new EventSetInterface() {
        @Override
        public boolean contains(Object o) {
            return (o instanceof EntryEvent);
        }
    };
    
    public static class EntryEvent extends Event {
        int x,y;
        
        public EntryEvent( int anX, int aY ) {
            x = anX;
            y = aY;
            setName("Entered " + x + "," + y);
        }
        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 97 * hash + this.x;
            hash = 97 * hash + this.y;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final EntryEvent other = (EntryEvent) obj;
            if (this.x != other.x) {
                return false;
            }
            return this.y == other.y;
        }
            
    }
    
}
