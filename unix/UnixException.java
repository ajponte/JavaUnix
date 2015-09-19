package unix;
class UnixException extends RuntimeException {
	UnixException(String msg) {
		super(msg);
	}

	UnixException() {}

    /** 
     *  Returns an exception containing an error message formatted according
     *  to FORMAT and ARGS, as for printf or String.format. Typically, one uses
     *  this by throwing the result in a context where there is a 'try' block
     *  that handles it by printing the message (esp. via reportError). 
     */
    static UnixException error(String format, Object... args) {
        return new UnixException(String.format(format, args));
    }
}
