/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *
 *************************************************************************/

public class LZWmod {
    private static int R = 256;        // number of input chars
    private static int L = 4096;       // number of codewords = 2^W
    private static int W = 12;         // codeword width

    public static void compress(String type) { 
    	double compression = 0;
    	double read = 0;
    	double newR = 0;
    	double oldR = 0;
    	Boolean firstPass = true;
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF
       
        while (input.length() > 0) {
        	String s = st.longestPrefixOf(input);
        	
        	
        //Different Conditions
        	if(type.equals("n")) {
        		if(firstPass) {
        			BinaryStdOut.write('n');
        			firstPass = false;
        		}
        	
	            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
	            int t = s.length();
	            if (t < input.length() && code < L)    // Add s to symbol table.
	                st.put(input.substring(0, t + 1), code++);
	            
	            else if(t < input.length() && W <16) {
	            	W = W + 1;
	            	L = L*2;
	            	st.put(input.substring(0, t + 1), code++);
	            }
	            input = input.substring(t);            // Scan past s in input.
        	}
            else if(type.equals("r")) {
            	if(firstPass) {
            		BinaryStdOut.write('r');
            		firstPass = false;
            	}
            	BinaryStdOut.write(st.get(s.toString()), W);      // Print s's encoding.
	            int t = s.length();
	            read += (t*8);
	            newR = read/compression;
	            //Logic used to monitor when to reset
	            if(W == 16 && code >= 65536 && (oldR/newR) > 1.1) {
	            	W = 12;
	            	L = 4096;
	            	st = new TST<Integer>();
	            	for (int i = 0; i < R; i++)
	                    st.put("" + (char) i, i);
	            	st.put(input.substring(0, t + 1), code++);
	            	read = 0;
	            	compression = 0;
	            	newR = 0;
	            	oldR = 0;
	            }
	            if (t < input.length() && code < L)    // Add s to symbol table.
	                st.put(input.substring(0, t + 1), code++);
	            
	            else if(t < input.length() && W <16) {
	            	W = W + 1;
	            	L = L * 2;
	            	st.put(input.substring(0, t + 1), code++);
	            }
	            /*if(t < input.length() && code == 65536 && W==16) {
	            	st = new TST<Integer>();
	            	for (int i = 0; i < R; i++)
	                    st.put("" + (char) i, i);
	            	W = 12;
	            	L = 4096;
	            	code = R + 1;
	            } */
	            oldR = newR;
	            input = input.substring(t);
            }
        
        }
        
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    } 


    public static void expand() {
    	double compression = 0;
    	double read = 0;
    	double newR = 0;
    	double oldR = 0;
    	//65536 is the max amount of codewords 2^16
        String[] st = new String[65536];
        int i; // next available codeword value
        
        char type = BinaryStdIn.readChar(); // Get type of compression

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if(codeword == R) return;
        String val = st[codeword];
        while (true) {
        	if(type == 'n') {
        		BinaryStdOut.write(val);
        		codeword = BinaryStdIn.readInt(W);
        		if (codeword == R) break;
        		String s = st[codeword];
        		if (i == codeword) s = val + val.charAt(0);   // special case hack
        		if (i < L - 1 ) st[i++] = val + s.charAt(0);
        		else if(W < 16) {
        			W = W + 1;
        			L = L * 2;
        			st[i++] = val + s.charAt(0);
        		}
        		val = s;
        	}
        	
        	else if(type == 'r') {
        		 compression = compression + W;
        	      read = read + (val.length() * 8);
        	      newR = read/compression;

        	      if(W == 16 && i>= 65536 && (oldR/newR) > 1.1){
        	        W = 12;
        	        L = 4096;
        	        st = new String[65536];
        	        for (i = 0; i < R; i++)
        	            st[i] = "" + (char) i;
        	        st[i++] = "";                        // (unused) lookahead for EOF
        	        codeword = BinaryStdIn.readInt(W);
        	        if (codeword == R) break;           // expanded message is empty string
        	        val = st[codeword];
        	        read = 0;
        	        compression = 0;
        	      }
        	      else{
        	        BinaryStdOut.write(val);
        	        codeword = BinaryStdIn.readInt(W);
        	        if (codeword == R) break;
        	          String s = st[codeword];

        	        if (i == codeword) s = val + val.charAt(0);   // special case hack

        	        if (i < L-1) st[i++] = val + s.charAt(0);

        	        else if(W < 16){
        	           W = W + 1;
        	           L = L * 2;
        	           st[i++] = val + s.charAt(0);
        	        }
        	          oldR = newR;
        	          val = s;
        	      }	
        	}
        	
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        if      (args[0].equals("-")) compress(args[1]);
        else if (args[0].equals("+")) expand();
        else throw new RuntimeException("Illegal command line argument");
    }

}

