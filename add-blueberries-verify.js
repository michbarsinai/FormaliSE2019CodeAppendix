/* global bp */

/////////////////////
//  
// Contains a b-thread that ensures
// blueberries are added at least once.
//  
/////////////////////


var BLUEBERRIES = bp.Event("ADD_BLUEBERRIES");
bp.registerBThread("MustAddBlueberries", function(){
    bp.hot(true).sync({waitFor:BLUEBERRIES});
});