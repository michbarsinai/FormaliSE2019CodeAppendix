package bp;

//import gov.nasa.jpf.jvm.Verify;

import static bp.eventSets.EventSetConstants.none;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import bp.exceptions.BPJBThreadStartException;
import bp.exceptions.BPJException;
import bp.exceptions.BPJInterruptingEventException;
import bp.exceptions.BPJJavaThreadStartException;
import bp.exceptions.BPJUnregisteredBThreadException;
import bp.exceptions.BPJRequestableSetException;

/**
 * A base class for behavior thread
 */
public abstract class BThread {

	Double priority;
	private String name = this.getClass().getSimpleName();
	private BProgram bp = null;
	private boolean monitorOnly = false;

	/**
	 * Temporary storage for bpSync parameters
	 */
	RequestableInterface requestedEvents;
	EventSetInterface watchedEvents;
	EventSetInterface blockedEvents;

	/**
	 * The set of events that will interrupt this scenario.
	 */
	protected EventSetInterface interruptingEvents = none;

	/**
	 * The thread that executes this scenario
	 */
	Thread thread;

	public BThread() {
	}

	public BThread(String name) {
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The function that implements the BThread. Subclasses of BThread should
	 * override this method.
	 * 
	 * @throws BPJRequestableSetException
	 */

	public abstract void runBThread() throws InterruptedException,
			BPJRequestableSetException;

	/**
	 * @see java.lang.Thread#start()
	 */
	public void startBThread() {
		if (thread != null) throw new BPJBThreadStartException();  
		// Create a thread for the b-thread
		thread = new JavaThreadForBThread(this);
		
		// add the pair to the global table
		getBProgram().threadToBThreadMap.put(thread,this); 

		thread.start();
	}

	boolean isRequested(Event event) {
		return (requestedEvents.contains(event));
	}

	public String toString() {
		return name;
	}

	public BProgram getBProgram() {
		return bp;
	}

	public void setBProgram(BProgram bp) {
		this.bp = bp;
	}

	public void bWait() throws BPJInterruptingEventException {
		try {
			wait();
		} catch (Exception e) {
			throw new BPJInterruptingEventException();

		}
	}

	public void setMonitorOnly(boolean flag) {
		
		if (bp == null) throw new BPJUnregisteredBThreadException(); 
		synchronized (bp.allBThreads) {
			monitorOnly = flag;
		}

	}

	public boolean getMonitorOnly() {
		return monitorOnly;
	}
}

/**
 * A thread that runs a scenario. The main reason for wrapping a BThread within
 * a separate thread is to allow pre and post processing.
 */
class JavaThreadForBThread extends Thread {
	BThread bt;

	/**
	 * Constructor.
	 * 
	 * 
	 */
	public JavaThreadForBThread(BThread sc) {
		super();
		this.bt = sc;
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			Concurrency concurrency = bt.getBProgram().concurrency;
			// Don't count this b-thread as running for concurrency purposes
			if (concurrency.control) {
				(bt.getBProgram()).debugPrint("Permits="
							+ concurrency.semaphore.availablePermits() + " "
							+ bt + " S1");
				concurrency.semaphore.acquire();
					(bt.getBProgram()).debugPrint("Acquired - starting  " + bt + "\n" + 
					"Permits=" 
							+ concurrency.semaphore.availablePermits() + "  "
							+ bt + " S2");
				}
			

			// Run the code of the scenario
			try {
				bt.runBThread();
			} catch (BPJInterruptingEventException ex) {
			}

			// Clear interrupting set
			bt.interruptingEvents = none;

			// Process the next event (if needed) but don't wait for it
			bt.thread = null;
			bt.getBProgram().bSync(none, none, none);

			// Don't count this b-thread as running for concurrency purposes
			if (concurrency.control) {
									(bt.getBProgram()).debugPrint("Permits="
							+ concurrency.semaphore.availablePermits() + " "
							+ bt + " F1");
				concurrency.semaphore.release();
				(bt.getBProgram()).debugPrint("Released - finishing " + bt + "\n" +   
					"Permits="
							+ concurrency.semaphore.availablePermits() + " "
							+ bt + " F2");
				}


		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	}
class BThreadForJavaThread extends BThread {
	public BThreadForJavaThread(Thread th) {
		thread = th; 
	}
	public void runBThread() throws BPJException {
		throw new BPJJavaThreadStartException();  
		}
	}
