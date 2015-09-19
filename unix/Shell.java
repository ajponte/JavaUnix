package unix;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Writer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


import static unix.UnixException.error;

/**
 * Shell to execute commands.
 */
class Shell {
	private static String HELP = "unix/Help.txt";
	private static String VERSION = "JavaUnix v.001";
	Shell(Reader input, Writer prompts, Writer output, Writer errOutput) {
		_inp = new Scanner(input);
		_prompter = new PrintWriter(prompts, true);
		_inp.useDelimiter("(?m)$|^|\\p{Blank}");
		_out = new PrintWriter(output, true);
		_err = new PrintWriter(errOutput, true);
	}

	/** Executes a session of the Unix instance. 
	 *  Proceeds until the user exits.  Returns an
	 *  exit code:  0 is normal; any positive quantity
	 *  indicates and error. */
	int exec() {
		_out.println("Welcome to " + VERSION);
		_running = true;
		while (_running) {
			promptForNext();
			readExecuteCommand();
		}
		_out.flush();
		System.exit(0);
		return 0;
	}

	/** Print a prompt and wait for input.  Returns
	 *  true iff there is another token.  */
	private boolean promptForNext() {
		String user = "unix user";
		if (_running) {
			_prompter.printf("%s>", user);
		} else {
			_prompter.printf(">");
		}
		return true;
	}

    /** Send an error message to the user formed from arguments FORMAT
     *  and ARGS, whose meanings are as for printf. */
    void reportError(String format, Object... args) {
        _err.print("Error: ");
        _err.printf(format, args);
        _err.println();
    }

    /** Read and execute one command.  Leave the input at the start of
     *  a line, if there is more input. */
    private void readExecuteCommand() {
        executeCommand(_inp.nextLine());
    }

    /** Gathers arguments and executes the correct command
     *  from the input INP.  Throws UnixException on 
     *  errors. */
    private void executeCommand(String inp) {
    	String[] args = inp.trim().split(" ");
    	String cmd = args[0];
    	try {
    		switch (cmd) {
    			case "\n": case	"\r\n": 
    			return;
    			case "help":
    				help();
    				break;
    			case "quit()":
    				quit();
    				break;
    			default:
    				runProcess(cmd);
    		}
    	} catch (UnixException e) {
    		_err.println(e.getMessage());
    	}
    }

    /** Runs the command CMD as a Java Process. */
    private void runProcess(String cmd) {
    	String s = null;
    	try {
    		Process p = Runtime.getRuntime().exec(cmd);

    		BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));
 
            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
                _out.println(s);
            }

            while ((s = stdError.readLine()) != null) {
                _err.println("Err: " + s);
            } 

    	} catch (IOException e) {
    		throw new UnixException(cmd + " is an unknown command " 
    			+ "on this system.");
    	}


    }
    /** Prints the help text to the user. */
    private void help() {
    	Main.printHelpResource(HELP, _out);
    }

    /** Quits THIS instance of the shell. */
   	private void quit() {
   		_out.flush();
    		_out.close();
    		_err.flush();
    		_err.close();
    		System.exit(0);
   	}

    /** Writer on which to print prompts for input. */
    private final PrintWriter _prompter;
    /** Scanner from current game input.  Initialized to return
     *  newlines as tokens. */
    private final Scanner _inp;
    /** Outlet for responses to the user. */
    private final PrintWriter _out;
    /** Outlet for error responses to the user. */
    private final PrintWriter _err;

    /** True iff a Unix instance is already running. */
    private boolean _running;
}
