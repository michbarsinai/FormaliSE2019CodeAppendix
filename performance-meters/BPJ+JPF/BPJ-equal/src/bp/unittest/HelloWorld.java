package bp.unittest;

import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import bp.BProgram;
import bp.BThread;
import bp.Event;
import bp.exceptions.BPJException;

class HelloWorld {
	static class GreetingEvent extends Event {

		public GreetingEvent(String name) {
			this.setName(name);
		}

	}

	static GreetingEvent goodMorning = new GreetingEvent("Good Morning!");
	static GreetingEvent goodEvening = new GreetingEvent("Good Evening!");

	static class SayGoodMorning extends BThread {
		public void runBThread() throws BPJException {
			for (int i = 1; i <= 3; i++) {
				BProgram bp = getBProgram();
				bp.bSync(goodMorning, none, none);
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
				bp.bSync(none, goodMorning, goodEvening);
				bp.bSync(none, goodEvening, goodMorning);
			}
		}
	}

	static class DisplayEvents extends BThread {
		public void runBThread() throws BPJException {
			while (true) {
				BProgram bp = getBProgram();
				bp.bSync(none, all, none);
				System.out.println("Processed:" + bp.lastEvent);
			}
		}
	}

	public static void main(String[] args) {
		BProgram hello = new BProgram();
		hello.add(new SayGoodMorning(), 1.0);
		hello.add(new DisplayEvents(), 2.0);
		hello.add(new SayGoodEvening(), 3.0);
		hello.add(new Interleave(), 4.0);

		hello.startAll();
	}

}
