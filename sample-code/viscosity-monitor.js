/* global bp */

////
//
// A b-thread that measures the thickness of the batter,
// and announces it using an event.
//
////
bp.registerBThread("ViscosityMonitor", function(){
    let viscosity=0;
    while ( true ) {
      let evt = bp.sync({waitFor:ADDITION_EVENTS});
      if ( evt.equals(ADD_DRY) ) viscosity++;
      if ( evt.equals(ADD_WET) ) viscosity--;
      bp.sync({ request:bp.Event("Viscosity", viscosity),
                  block:ADDITION_EVENTS });
  }});
