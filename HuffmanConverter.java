package HuffmanConverter;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.io.IOException;

public class HuffmanConverter {

    // The # of chars in the ASCII table dictates
    // the size of the count[] & code[] arrays.
    public static final int NUMBER_OF_CHARACTERS = 256;
    
    // the contents of our message...
    private String contents;
    
    // the tree created from the message
    private HuffmanTree huffmanTree;
    
    // tracks how often each character occurs
    private int count[];
    
    // the huffman code for each character
    private String code[];
    
    // stores the # of unique chars in contents
    private int uniqueChars = 0; //(optional)
    
    /** Constructor taking input String to be converted */
    public HuffmanConverter(String input){
        this.contents = input;
        this.count = new int[NUMBER_OF_CHARACTERS];
        this.code = new String[NUMBER_OF_CHARACTERS];
    }

    /**
    * Records the frequencies that each character of our
    * message occurs...
    * I.e., we use 'contents' to fill up the count[] list...
    */
    public void recordFrequencies() {
        for (int i = 0; i < contents.length(); i++){
            count[ (int) contents.charAt(i) ]++;
        }
    }

    /**
    * Converts our frequency list into a Huffman Tree. We do this by
    * taking our count[] list of frequencies, and creating a binary
    * heap in a manner similar to how a heap was made in HuffmanTree's
    * fileToHeap method. Then, we print the heap, and make a call to
    * HuffmanTree.heapToTree() method to get our much desired
    * HuffmanTree object, which we store as huffmanTree.
    */
    public void frequenciesToTree() {
    	
    	// Create ArrayList
        ArrayList<HuffmanNode> huffArrList = new ArrayList<HuffmanNode>();
        
        // Store values that aren't 
        for(int i = 0; i < this.count.length;i++){
        	// If the count for the element isn't 0 add it to the array list
    		if( !(this.count[i] == 0) ){
                HuffmanNode temp = new HuffmanNode("" + (char) i, (double) count[i]);
                huffArrList.add(temp);
            }
        }
        
        // Put the elements of the array list into a regular array
        HuffmanNode[] huffArr = new HuffmanNode[huffArrList.size()];
        for (int i = 0; i < huffArrList.size(); i++) {
        	huffArr[i] = huffArrList.get(i);
        }
        // Create a binary heap using the new array
        BinaryHeap<HuffmanNode> heap = new BinaryHeap<HuffmanNode>(huffArr);

        // Print the heap
        System.out.println("Initial contents of the heap: ");
        heap.printHeap();
        
        System.out.println();
        
        // Create the tree
        huffmanTree = HuffmanTree.createFromHeap(heap);
        
        System.out.println("Legend of the tree created from the heap: ");
        huffmanTree.printLegend();
    }

            
    /**
    * Iterates over the huffmanTree to get the code for each letter.
    * The code for letter i gets stored as code[i]... This method
    * behaves similarly to HuffmanTree's printLegend() method...
    * Warning: Don't forget to initialize each code[i] to ""
    * BEFORE calling the recursive version of treeToCode...
    */
    public void treeToCode() {
        treeToCode(huffmanTree.getRoot(),"");
    }

    /*
    * A private method to iterate over a HuffmanNode t using s, which
    * contains what we know of the HuffmanCode up to node t. This is
    * called by treeToCode(), and resembles the recursive printLegend
    * method in the HuffmanTree class. Note that when t is a leaf node,
    * t's letter tells us which index i to access in code[], and tells
    * us what to set code[i] to...
    */
    private void treeToCode(HuffmanNode t, String s) {
        if (t.getLetter().length() > 1 ) {
        	// Changed to 1 left and 0 right
            treeToCode(t.getLeft(), s + "1");
            treeToCode(t.getRight(), s + "0");
        }
        // If leaf node
        else if (t.getLetter().length() == 1) {
            // s = singular char at the leaf node
            code[ (int) t.getLetter().charAt(0) ] = s;
        }
    }
    
    /**
    * Using the message stored in contents, and the huffman conversions
    * stored in code[], we create the Huffman encoding for our message
    * (a String of 0's and 1's), and return it...
    */
    public String encodeMessage()  {
        String str = "";
        for(int i = 0; i < contents.length(); i++){
            str += code[ (int) contents.charAt(i) ];
        }
        return str;
    }
    /**
    * Reads in the contents of the file named filename and returns
    * it as a String. The main method calls this method on args[0]...
    */
    public static String readContents(String filename) throws IOException {
        File myFile = new File(filename);
        Scanner scn = new Scanner(myFile);
        StringBuilder sb = new StringBuilder();
        while ( scn.hasNextLine() ) {
            sb.append(scn.nextLine());
            sb.append("\n");
        }
        scn.close();
        return sb.toString();
    }

    /**
    * Using the encoded String argument, and the huffman codings,
    * re-create the original message from our
    * huffman encoding and return it...
    * Inefficient way iterating through code[]
    */
    public String decodeMessageIteratingThroughCodeArray(String encodedStr) {
        String result = "";
        int index = 0;
        // Iterate through encoded string
        for(int i = 1; i < encodedStr.length(); i++){
            // Iterate through code[]
            for(int j = 0; j < code.length; j++){
    			// If the substring matches a code, add it to the decoded string and break
                if(encodedStr.substring(index, i).equals(code[j])){
                    result += (char) j;
                    index = i;
                    break;
                }
            }
        }
        result += "\n";
        return result;
    }
    
    /**
    * Using the encoded String argument, and the huffman codings,
    * re-create the original message from our
    * huffman encoding and return it...
    * Better than iterating through code[]
    */
    public String decodeMessage(String encodedStr) {
    	String result = "";
        int index = 0;
        // Iterate through encoded string
        for(int i = 1; i < encodedStr.length(); i++){
        	
        	String potentialCode = encodedStr.substring(index, i);
        	HuffmanNode currentNode = this.huffmanTree.getRoot();
        	for (int j = 0; j < potentialCode.length(); j++) {
        		// If 0 go right, if 1 go left
        		if (potentialCode.charAt(j) == '0') {
        			currentNode = currentNode.getRight();
        		}
        		if (potentialCode.charAt(j) == '1') {
        			currentNode = currentNode.getLeft();
        		}
        	}
        	// If the end node is a leaf node, add the char to the decoded string
        	if (currentNode.getLeft() == null && currentNode.getRight() == null) {
        		result += currentNode.getLetter();
        		index = i;
        	}
            
        }
        result += "\n";
        return result;
    }
    
    /**
    * Uses args[0] as the filename, and reads in its contents. Then
    * instantiates a HuffmanConverter object, using its methods to
    * obtain our results and print the necessary output. Finally,
    * decode the message and compare it to the input file.<p>
    * NOTE: Example method provided below...
    * ANOTHER NOTE: The code doesn't print out \n or \t for special characters but instead
    * will have a tab or skip a line.  For example:
    * t=11001
    * 
    * =11010 means that 11010 is the coded String for \n.
    */
    public static void main(String args[]) throws IOException {
    	//String file="C:/Users/Harrison Du/Downloads/love_poem_58.txt"; debug purposes
    	
        HuffmanConverter convert = new HuffmanConverter(readContents(args[0]));
        convert.recordFrequencies();
        convert.frequenciesToTree();
        System.out.println();
        
        convert.treeToCode();
        
        String encodedMessage = convert.encodeMessage();
        System.out.println("Huffman Encoding: \n" + encodedMessage);
        System.out.println("Message size in ASCII encoding: " + convert.contents.length() * 8);
        System.out.println("Message size in Huffman encoding: " + encodedMessage.length());
        System.out.println("");
        
        System.out.println("Decoded Message: \n" + convert.decodeMessage(encodedMessage));
        
    }

}