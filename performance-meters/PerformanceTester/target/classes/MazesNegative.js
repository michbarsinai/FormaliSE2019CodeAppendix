/* global bp */

var ROBOT_TRAPPED_EVENT = bp.Event("Robot fell into trap");

function enterEvent(c,r) {
    return bp.Event("Enter (" + c + ","  + r + ")");//, {col:c, row:r});
}

var anyEntrance = bp.EventSet("AnyEntrance", function(evt){
   return evt.name.indexOf("Enter") === 0;
});

function adjacentCellEntries(col, row) {
    return [enterEvent(col + 1, row), enterEvent(col - 1, row),
        enterEvent(col, row + 1), enterEvent(col, row - 1)];

}

var cellWait = [anyEntrance, ROBOT_TRAPPED_EVENT];

////////////////////////
///// functions 
function parseMaze(mazeLines) {
    for ( var row=0; row<mazeLines.length; row++ ) {
        for ( var col=0; col<mazeLines[row].length; col++ ) {
            var currentPixel = mazeLines[row].substring(col,col+1);
            if ( " tsh".indexOf(currentPixel) > -1 ) {
                addSpaceCell(col, row);
                if ( currentPixel==="t" ) addTrapCell(col, row);
                if ( currentPixel==="s" ) addStartCell(col, row);
                if ( currentPixel==="h" ) addHotCell(col, row);
            }
        }
    }
}


/**
 * A cell the maze solver can enter. Waits for entrances to one of the cell's
 * neighbours, then requests entrance to itself.
 * @param {Number} col
 * @param {Number} row
 * @returns {undefined}
 */
function addSpaceCell( col, row ) {
    bp.registerBThread("Space(c:"+col+" r:"+row+")",
        function() {
            while ( true ) {
                bp.sync({waitFor:adjacentCellEntries(col, row)});
                bp.sync({
                    request: enterEvent(col, row),
                    waitFor: cellWait
                });
            }
        }
    );
}

/**
 * Waits for an event signaling the entrance to the 
 * target cell, then blocks everything.
 * @param {Number} col
 * @param {Number} row
 * @returns {undefined}
 */
function addTrapCell(col, row) {
    bp.registerBThread("Trap(c:"+col+" r:"+row+")", function(){
       while ( true ) {
	       bp.sync({
	           waitFor: enterEvent(col, row)
	       }); 
	       
	       bp.sync({
	           request: ROBOT_TRAPPED_EVENT,
	           block: bp.allExcept( ROBOT_TRAPPED_EVENT )
	       });
       }
    });
}

function addStartCell(col, row) {
    bp.registerBThread("Starter(c:"+col+" r:"+row+")", function() {
       bp.sync({
          request:enterEvent(col,row) 
       });
    });
}

function addHotCell( col, row ) {
    bp.registerBThread("Hot(c:"+col+" r:"+row+")", function(){
       while (true) {
          bp.sync({
	           waitFor: enterEvent(col, row)
	      });
          bp.hot(true).sync({
            waitFor: cellWait
          });
       } 
    });
}

parseMaze(maze);

bp.registerBThread("Robot not falling into trap", function(){
	bp.sync({waitFor:ROBOT_TRAPPED_EVENT});
	bp.ASSERT(false,"The robot fell into the trap.");
});