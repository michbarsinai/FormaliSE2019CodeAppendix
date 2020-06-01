package bp;

import java.math.BigDecimal;

// This class is a node in an execution trace.
// used initially in iterative execution - for 
// iterating through non-deterministic choices

public class ChoicePoint {
	// The first bthread that had a requested event that is not blocked
	// (requester of first non-deterministic choice)
	// Epsilon of non-det requests may be counted from here. see parameter)
	double firstBThread = -1;

	// The event that was selected in THIS run. (of all the non-det choices).
	// Epsilon of non-det requests may be counted from this b-thread too
	// See parameter.
	
	EventChoice currentChoice = null; 
	
	// The next event in the list of non-det choices 
	
	EventChoice nextChoice = null ;

	
	public String toString() {
		return ("(" + "FirstBT=" + firstBThread + ", currentChoice=" + currentChoice + 
				", nextChoice=" + nextChoice+ ")"); 
	} 
}


// This class is used to point to a particular request among
// many non-deterministic choices, in a way that persists across
// iterative runs.
class EventChoice {
	double btID =-1; 			// bthread id
	int eventSetSeq =-1;	// The sequence # of the event set within the
							// requested events set of this b-thread.
	int eventSeq = -1; 		// The seq # of the event within above set
	// String eventString - a toString of the event itself for later printing
	
	public String toString() {
		
		BigDecimal bd = new BigDecimal(btID);
	    bd = bd.setScale(3,BigDecimal.ROUND_HALF_UP);
		return("<" + bd.doubleValue() + "/" + eventSetSeq + "/" + eventSeq + ">");
	}
	
	public EventChoice(double btID, int eventSetSeq, int eventSeq) {
		this.btID = btID;
		this.eventSetSeq = eventSetSeq;
		this.eventSeq = eventSeq;
	}
	
	public EventChoice() {
		}
	public Event getEvent(BProgram bp) {
		BThread bt = (bp.allBThreads).get(btID);
		return ((bt.requestedEvents.get(eventSetSeq)).get(eventSeq)).getEvent();
	}
}
