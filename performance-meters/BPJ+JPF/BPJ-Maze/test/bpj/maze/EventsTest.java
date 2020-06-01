/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bpj.maze;

import bp.eventSets.EventSetInterface;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author michael
 */
public class EventsTest {

    /**
     * Test of adjacentCellEntries method, of class Events.
     */
    @Test
    public void testAdjacentCellEntries() {
        System.out.println("adjacentCellEntries");
        int x = 20;
        int y = 30;
        EventSetInterface sut = Events.adjacentCellEntries(x, y);
        assertTrue( sut.contains( new Events.EntryEvent(20, 31)) );
        assertTrue( sut.contains( new Events.EntryEvent(20, 29)) );
        assertTrue( sut.contains( new Events.EntryEvent(21, 30)) );
        assertTrue( sut.contains( new Events.EntryEvent(19, 30)) );
        
        assertFalse( sut.contains( new Events.EntryEvent(20, 30)) );
        assertFalse( sut.contains( new Events.EntryEvent(21, 31)) );
        assertFalse( sut.contains( new Events.EntryEvent(19, 31)) );
        assertFalse( sut.contains( new Events.EntryEvent(19, 29)) );
        assertFalse( sut.contains( new Events.EntryEvent(21, 29)) );
        assertFalse( sut.contains( new Events.EntryEvent(20, 20)) );
        
    }

    
    public static void main(String[] args) {
        new EventsTest().testAdjacentCellEntries();
    }
}
