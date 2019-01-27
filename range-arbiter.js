/* global bp */

/////
//
// A b-thread that limits the thickness of the batter to [-2, 2].
//
/////

var THICKNESS_BOUND = 2;

bp.registerBThread("RangeArbiter", function(){
    while ( true ) {
        var thicknessEvt=bp.sync({waitFor:THICKNESS_EVENTS});
        var thickness = thicknessEvt.data;
        var block;
        if ( Math.abs(thickness) >= THICKNESS_BOUND ) {
            block = (thickness>0) ? ADD_DRY : ADD_WET;
        } else {
            block = bp.none;
        }

        var evt = bp.sync({waitFor:ADDITION_EVENTS, block:block});
    }
});
