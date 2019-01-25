bp.registerBThread("thickness-verification", function(){
    while ( true ) {
        var evt = bp.sync({waitFor:THICKNESS_EVENTS});
        bp.ASSERT( Math.abs(evt.data) <= THICKNESS_BOUND, "Thickness out of bounds" );
    }
});