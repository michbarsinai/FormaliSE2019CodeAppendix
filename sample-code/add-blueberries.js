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
        var thicknessEvt = bp.sync({
              waitFor:THICKNESS_EVENTS,
                block:block,
            interrupt:BLUEBERRIES
        });
        block = (thicknessEvt.data >= 0) ? BLUEBERRIES : bp.none;
    }
});