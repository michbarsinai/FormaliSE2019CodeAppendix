/* global bp */

/////
//
// A b-thread that should limit the thickness of the batter to [-2, 2],
// but contains an off-by-one error.
//
/////

var THICKNESS_BOUND = 2;

bp.registerBThread("ThicknessLimiter", function(){
    while ( true ) {
        var thicknessEvt=bp.sync({waitFor:THICKNESS_EVENTS});
        var thickness = thicknessEvt.data;
        var block;
        if ( Math.abs(thickness) > THICKNESS_BOUND ) {
            block = (thickness>0) ? ADD_DRY : ADD_WET;
        } else {
            block = bp.none;
        }

        var evt = bp.sync({waitFor:ADDITION_EVENTS, block:block});
    }
});
