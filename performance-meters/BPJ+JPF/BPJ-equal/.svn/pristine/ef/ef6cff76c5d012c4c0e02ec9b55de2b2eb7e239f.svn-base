package bp.eventSets;

import java.util.ArrayList;

import bp.Event;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class RequestableEventSet extends ArrayList<RequestableInterface>
		implements EventSetInterface, RequestableInterface {

	private String name = this.getClass().getSimpleName();

	public RequestableEventSet(RequestableInterface... reqs) {
		for (RequestableInterface r : reqs) {
			add(r);
		}
	}

	public RequestableEventSet(String name, RequestableInterface... reqs) {
		this(reqs);
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public boolean isEvent() {
		return (false);
	}

	public boolean contains(Object o) {
		for (RequestableInterface r : this)
			if (r.contains(o))
				return true;
		return false;
	}

	public Event getEvent() throws BPJRequestableSetException {
		throw new BPJRequestableSetException();
	}

	public ArrayList<Event> getEventList() {
		ArrayList<Event> list = new ArrayList<Event>();
		this.addEventsToList(list);
		return list;
	}

	public void addEventsToList(ArrayList<Event> list) {
		for (RequestableInterface ri : this) {
			ri.addEventsToList(list);
		}
	}
}
