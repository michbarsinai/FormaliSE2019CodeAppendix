/* global bp */

////
// A b-thread that measures the thickness of the batter,
// and announces it using an event.
////

var THICKNESS_EVENTS = bp.EventSet("Thickness", function(e){
    return e.name.contains("Thickness");
});
var ADDITION_EVENTS = [ADD_DRY, ADD_WET];
bp.registerBThread("ThicknessMeter", function(){
    var thickness=0;
    while ( true ) {
        var evt = bp.sync({waitFor:ADDITION_EVENTS});
        if ( evt.equals(ADD_DRY) ) {
            thickness++;
        } else {
            thickness--;
        }
        bp.sync({
            request:bp.Event("Thickness", thickness),
              block:ADDITION_EVENTS
        });
    }
});
