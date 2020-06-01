package bp.unittest;

import bp.BProgram;
import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import bp.BThread;
import bp.Event;
import bp.eventSets.EventSet;
import bp.eventSets.RequestableEventSet;
import bp.exceptions.BPJException;
import bp.exceptions.BPJRequestableSetException;

class HelloWorldNames {

	static class GreetingEvent extends Event {

		public GreetingEvent(String name) {
			this.setName(name);
		}

	}

	static GreetingEvent goodMorning = new GreetingEvent("Good Morning!");
	static GreetingEvent goodEvening = new GreetingEvent("Good Evening!");

	static Event ttt = new Event() {
		public String toString() {
			return "EventForShowingFilterHierarchy";
		}
	};

	static class SayGoodMorning extends BThread {
		public void runBThread() throws BPJException {
			for (int i = 1; i <= 3; i++) {
				BProgram bp = getBProgram();
				bp.bSync(new RequestableEventSet(goodMorning, ttt), none, none);
			}
		}
	}

	static class SayGoodEvening extends BThread {
		public void runBThread() throws BPJException {
			for (int i = 1; i <= 3; i++) {
				BProgram bp = getBProgram();
				bp.bSync(goodEvening, none, none);
			}
		}
	}

	static class Interleave extends BThread {
		public void runBThread() throws BPJException {
			while (true) {
				BProgram bp = getBProgram();
				bp.bSync(none, goodMorning, new EventSet(goodEvening, ttt));
				bp.bSync(none, goodEvening, new EventSet(goodMorning, ttt));
			}
		}
	}

	static class DisplayEvents extends BThread {
		public void runBThread() throws BPJException {
			while (true) {
				BProgram bp = getBProgram();
				bp.bSync(none, all, none);
				System.out.println(getBProgram().lastEvent);
			}
		}
	}

	public static void main(String[] args) {
		BThread bt;
		BProgram hello = new BProgram();
		bt = new SayGoodMorning();
		bt.setName("SayGoodMorning1");
		hello.add(bt, 1.0);
		bt = new DisplayEvents();
		bt.setName("DisplayEvents1");
		hello.add(bt, 2.0);
		bt = new SayGoodEvening();
		bt.setName("SayGoodEvening1");
		hello.add(bt, 3.0);
		bt = new Interleave();
		bt.setName("Interleave1");
		hello.add(bt, 4.0);

		hello.startAll();
	}

}
