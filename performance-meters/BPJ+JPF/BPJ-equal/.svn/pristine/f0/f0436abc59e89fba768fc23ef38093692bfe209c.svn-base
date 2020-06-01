//package bp;
//
//import java.util.Iterator;
//
//import bp.eventSets.EventSetInterface;
//import bp.eventSets.RequestableInterface;
//
///**
// * A base class for events
// */
//public class EventOLD 
//// implements EventSetInterface, RequestableInterface {
//
//	private String name = this.getClass().getSimpleName();
//
//	@Override
//	public boolean contains(Object o) {
//		return this.equals(o);
//	}
//
//	@Override
//	public Iterator<Event> iterator() {
//		return new SingleEventIterator(this);
//	}
//
//	public Event() {
//	}
//
//	public Event(String name) {
//		this.name = name;
//	}
//
//	public String toString() {
//		return name;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//	public Event get(int index){ 
//		if (index == 0)
//			return (this); 
//		throw(new ArrayIndexOutOfBoundsException()); 
//	}
//}
//
///**
// * An iterator over a single event object. Allows to view an event as a
// * (singleton) set.
// */
//class SingleEventIteratorOLD implements Iterator<Event> {
//	Event e;
//
//	public SingleEventIteratorOLD(Event e) {
//		this.e = e;
//	}
//
//	@Override
//	public boolean hasNext() {
//		return e != null;
//	}
//
//	@Override
//	public Event next() {
//		Event tmp = e;
//		e = null;
//		return tmp;
//	}
//
//	@Override
//	public void remove() {
//		throw new UnsupportedOperationException();
//	}
//}
