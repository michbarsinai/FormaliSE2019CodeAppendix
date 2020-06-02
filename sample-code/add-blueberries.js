var BLUEBERRIES = bp.Event("ADD_BLUEBERRIES");

/**
 * Add blueberries!
 */
bp.registerBThread("Blueberries", function(){
    bp.sync({request:BLUEBERRIES});
});

/**
 * Can only add blueberries when there's enough batter in 
 * the mixer bowl.
 */
bp.registerBThread("EnoughBatterForBlueberries", function(){
    bp.sync({waitFor:ADDITION_EVENTS, block:BLUEBERRIES});
    bp.sync({waitFor:ADDITION_EVENTS, block:BLUEBERRIES});
    bp.sync({waitFor:ADDITION_EVENTS, block:BLUEBERRIES});
});

/**
 * Can only add blueberries when the batter is thin enough.
 */
bp.registerBThread("BatterThinEnough", function(){
    var block;
    while ( true ) {
        var viscosityEvt = bp.sync({
              waitFor:VISCOSITY_EVENTS,
                block:block,
            interrupt:BLUEBERRIES
        });
        block = (viscosityEvt.data >= 0) ? BLUEBERRIES : bp.none;
    }
});