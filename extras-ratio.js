/* global bp */

////
// Refills 
////
var ADD_EXTRAS = bp.EventSet("sADD_EXTRAS", function(e){
 return e.name.equals("ADD_EXTRAS");
});

/**
 * Creates a fruit addition event.
 * @param {Number} blueb amount of blueberries to add
 * @param {Number} kales amount of kale leaves to add
 */
function addExtrasEvent( blueb, kales ) {
    return bp.Event("ADD_EXTRAS", {
        blueberries:blueb,
        kales:kales
    });
}

var ADD_BLBR = addExtrasEvent(1,0);
var ADD_KALE = addExtrasEvent(0,1);

bp.registerBThread( "BlueberryAdder", function(){
    var fruitIndex=0;
    while (true) {
        var evt = null;
        if ( fruitIndex < 0 ) {
            evt = bp.hot(true).sync({request:ADD_BLBR,
                                     waitFor:ADD_EXTRAS});
        } else {
            evt = bp.sync({waitFor:ADD_EXTRAS});
        }
        fruitIndex = fruitIndex + 
                    evt.data.blueberries-evt.data.kales;
    }
});

bp.registerBThread( "KaleAdder", function(){
    var fruitIndex=0;
    while (true) {
        var evt = null;
        if ( fruitIndex > 0 ) {
            evt = bp.hot(true).sync({request:ADD_KALE,
                                     waitFor:ADD_EXTRAS});
        } else {
            evt = bp.sync({waitFor:ADD_EXTRAS});
        }
        fruitIndex = fruitIndex + 
                    evt.data.blueberries-evt.data.kales;
    }
});

bp.registerBThread("script", function(){
    bp.sync({request:addExtrasEvent(0,0.5)});
});

if ( true ) {
    bp.registerBThread("indexMonitor", function(){
        var fruitIndex=0;
        while (true) {
            var evt = null;
            evt = bp.sync({waitFor:ADD_EXTRAS});
            fruitIndex = fruitIndex + 
                        evt.data.blueberries-evt.data.kales;
            bp.log.info("fruitIndex: " + fruitIndex);    
        }
    });
}
