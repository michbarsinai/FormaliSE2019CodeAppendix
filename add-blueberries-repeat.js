/* global bp */

/////////////////////
//  
// Contains a b-thread for repeatedly adding
// blueberries to the batter, in a panckage 
// server scenario.
//  
/////////////////////

var BLUEBERRIES = bp.Event("ADD_BLUEBERRIES");

/**
 * Add blueberries!
 */
bp.registerBThread("Blueberries", function(){
    while ( true ) {
        bp.sync({request:BLUEBERRIES});
        bp.sync({waitFor:RELEASE});
    }
});
