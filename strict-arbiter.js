/* global bp */

///////////////
//
//  A b-thread that (over) restricts batter
//  thickness during preparation.
//
///////////////


bp.registerBThread("StrictArbiter", function(){
  while (true) {
    bp.sync({waitFor:ADD_WET, block:ADD_DRY});
    bp.sync({waitFor:ADD_DRY, block:ADD_WET});
}});