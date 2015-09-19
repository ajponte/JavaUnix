package unix;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.IOException;

/** 
 *  A simple Unix interpreter.
 *  @author Alan Ponte
 */




public class Main {
	static final String USAGE = "unix/usage.txt";
	/** Commands will be entered as soon as the REPL
	 *  starts.  ARGS can contain optional arguments.
	 *  These arguments are:
	 *  --help: Displays a usage message
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			Writer output = new OutputStreamWriter(System.out);
			Shell shell = new Shell(new InputStreamReader(System.in), 
				output, output, new OutputStreamWriter(System.err));
			System.exit(shell.exec());
		} else if (args[0] == "--help") {
			usage();
		}
	}

	/** Prints the contents of the resource named NAME on OUTPUT.
	 *  NAME will typically be a file name based in one of the directories
	 *  in the class path. */
	public static void printHelpResource(String name, PrintWriter output) {
		try {
			InputStream resource = Main.class.getClassLoader().getResourceAsStream(name);
			BufferedReader str = new BufferedReader(new InputStreamReader(resource));
			for (String s=str.readLine(); s != null; s=str.readLine()) {
				output.println(s);
			}
			str.close();
			output.flush();
		} catch (IOException e) {
			output.printf("No help found.");
			output.flush();
		}
	}

       /** Prints the usage text to STDOUT. */
       public static void usage() {
		printHelpResource(USAGE, new PrintWriter(System.err));
       }
    
    /** Performs a "graceful" exit from the interpreter. */
     public static void exitGracefully() {
        	System.exit(0);
     }

    /** Sorts A recusively. */
    public static void sortArray(int[] a) {
	if (a == null) {
	    return;
	}
	if (a.length() == 1) {
	    return a[0];
	}
	return sortArray(a, a.length() - 1);
    }
    
    /** 
     * Sorts the array A[0 ... END] such that 
     * A[i] < A[j] for all i <= j.
     */
    public static void sortArray(int[] a, int end) {
	if (a[0] == a[end]) {
	    return a[0];
	} else if (a[o] > a[end]) {
	    int temp = a[0];
	    a[0] = a[end];
	    // Sort the rest foool
	}
    }
}
