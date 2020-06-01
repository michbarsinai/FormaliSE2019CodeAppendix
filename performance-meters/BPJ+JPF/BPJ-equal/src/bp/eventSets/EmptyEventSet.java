package bp.eventSets;

import java.util.ArrayList;
import java.util.Iterator;

import bp.Event;
import bp.exceptions.BPJRequestableSetException;

/**
 * A filter that doesn't match any object.
 */
public class EmptyEventSet implements EventSetInterface, RequestableInterface {
	/**
	 * @see bp.eventSets.EventSetInterface#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return false;
	}

	public Iterator<RequestableInterface> iterator() {
		return new EmptyEventIterator();
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}

	public RequestableInterface get(int index) {
		throw new ArrayIndexOutOfBoundsException();
	}

	public int size() {
		return (0);
	}

	public void addEventsToList(ArrayList<Event> list) {
		// Just return
	}

	public Event getEvent() throws BPJRequestableSetException {
		throw new BPJRequestableSetException();
	}

	public ArrayList<Event> getEventList() {
		return (new ArrayList<Event>()); 
	}
	public boolean isEvent() {
		return false;
	}
}

/**
 * An iterator over an empty set of events.
 */
class EmptyEventIterator implements Iterator<RequestableInterface> {
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public Event next() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
