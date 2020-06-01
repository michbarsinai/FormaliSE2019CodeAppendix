package bp.exceptions;

import bp.BThread;

@SuppressWarnings("serial")
public class BPJException extends RuntimeException {

	public BPJException() {
		super();
	}

	public BPJException(String s) {
		super(s);
	}

	public BPJException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
