/* global bp */

var ADD_DRY = bp.Event("ADD_DRY");
var ADD_WET = bp.Event("ADD_WET");
var RELEASE = bp.Event("RELEASE_BATTER");
var ADDITION_EVENTS = [ADD_DRY, ADD_WET];

var BATCH_SIZE = 5;

bp.registerBThread("Dry", function(){
    while ( true ) {
        for ( var i=0; i<BATCH_SIZE; i++ ){
            bp.sync({request:ADD_DRY});
        }
        bp.sync({waitFor:RELEASE});
    }
});

bp.registerBThread("Wet", function(){
    while (true) {
        for ( var i=0; i<BATCH_SIZE; i++ ){
            bp.sync({request:ADD_WET});
        }
        bp.sync({waitFor:RELEASE});
    }
});

bp.registerBThread("Releaser", function(){
    var doseCount = 0;
    while ( true ) {
        bp.sync({waitFor:ADDITION_EVENTS});
        doseCount++;
        if ( doseCount === (BATCH_SIZE*2) ) {
            bp.sync({request:RELEASE, block:ADDITION_EVENTS});
            doseCount=0;
        }
    }
});