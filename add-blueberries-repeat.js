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
