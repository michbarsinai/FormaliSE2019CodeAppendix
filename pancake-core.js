/* global bp */

var ADD_DRY = bp.Event("ADD_DRY");
var ADD_WET = bp.Event("ADD_WET");

var DOSE_COUNT = 5;

bp.registerBThread("Dry", function(){
    for ( var i=0; i<DOSE_COUNT; i++ ){
        bp.sync({request:ADD_DRY});
}});

bp.registerBThread("Wet", function(){
    for ( var i=0; i<DOSE_COUNT; i++ ){
        bp.sync({request:ADD_WET});
}});