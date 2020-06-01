bp.registerBThread("stateTitler", function(){
	var stateTitle = "start";
	while ( true ) {
		var e = bp.sync({waitFor:anyEntrance}, stateTitle);
		stateTitle = e.name;
		stateTitle = "@" + stateTitle.substring(6);
	}
});
