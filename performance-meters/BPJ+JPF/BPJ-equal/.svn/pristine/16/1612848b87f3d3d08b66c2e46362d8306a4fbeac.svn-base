package bp.eventSets;

import java.util.ArrayList;
import java.util.Iterator;

import bp.Event;
import bp.exceptions.BPJRequestableSetException;

/**
 * An interface for what can be requested in bSync call. Currently only facades
 * Iterable<Event>, future implementations may have complex iterators
 * 
 */
public interface RequestableInterface extends Iterable<RequestableInterface> {

	public Iterator<RequestableInterface> iterator();

	public RequestableInterface get(int index);

	public boolean isEvent();

	public int size();

	public boolean contains(Object o);

	public Event getEvent() throws BPJRequestableSetException;
	
	public ArrayList<Event> getEventList();
		
	public void addEventsToList(ArrayList<Event> list);
}
