/**
 * This file contains definitions used in other files.
 */
const ADD_DRY = bp.Event("ADD_DRY");
const ADD_WET = bp.Event("ADD_WET");
const ADDITION_EVENTS = [ADD_DRY, ADD_WET];

const VISCOSITY_EVENTS = bp.EventSet("Viscosity", function(e){
    return e.name.contains("Viscosity");
});
  
const VISCOSITY_BOUND = 2;