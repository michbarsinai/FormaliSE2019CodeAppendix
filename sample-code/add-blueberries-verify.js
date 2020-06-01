var BLUEBERRIES = bp.Event("ADD_BLUEBERRIES");
bp.registerBThread("MustAddBlueberries", function(){
    bp.hot(true).sync({waitFor:BLUEBERRIES});
});