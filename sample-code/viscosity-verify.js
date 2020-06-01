/* global bp */

///////////////////
//
// A b-thread that verifies that batter viscosity is
// within range.
//
///////////////////
const VISCOSITY_BOUND=2;
bp.registerBThread("viscosity-verification", function(){
    while ( true ) {
        let evt = bp.sync({waitFor:VISCOSITY_EVENTS});
        bp.ASSERT( Math.abs(evt.data) <= VISCOSITY_BOUND, "Viscosity out of bounds" );
    }
});