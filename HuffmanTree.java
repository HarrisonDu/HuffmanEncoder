package HuffmanConverter;
import java.util.ArrayList;



public class HuffmanTree {
	HuffmanNode root;
	public HuffmanTree(HuffmanNode huff) {
		this.root=huff;
	}
	
	public HuffmanNode getRoot() {
		return root;
	}

	public void setRoot(HuffmanNode root) {
		this.root = root;
	}

	public void printLegend() {
		printLegend(root,"");
	}
	private void printLegend(HuffmanNode t, String s) { //t is node, s will return string value of binary
		if(t.letter.length()>1) {  //while not a leaf node
			printLegend(t.left,s+"0");
			printLegend(t.right,s+"1");
		}else {//when reaches a leaf node
			System.out.println(t.letter+"="+s);
		}
	}
	public static BinaryHeap <HuffmanNode>legendToHeap(String legend) {
		String data[]=legend.split(" ");  //begining of node algo to add letters and frequencies
		
		//end of algo
		  HuffmanNode[] hArr = new HuffmanNode[data.length / 2];
	        for (int i = 0 ; i < data.length; i += 2) {
	            HuffmanNode tNode = new HuffmanNode( data[i], Double.parseDouble(data[i+1]) );
	            hArr[i/2] = tNode;
	        }
			BinaryHeap <HuffmanNode>tHeap = new BinaryHeap<HuffmanNode>(hArr); //creates binary heap to be returned

		return tHeap;
		
	
		
		
	}
	public static HuffmanTree createFromHeap(BinaryHeap <HuffmanNode>b) {
		BinaryHeap <HuffmanNode>bCopy=b;
		while (bCopy.getSize()>1) { //Huffman Heap algo
			
			HuffmanNode lNode=bCopy.deleteMin();
			//if(lNode==null)System.out.println("Uh oh 1");

			HuffmanNode rNode=bCopy.deleteMin();
			//if(rNode==null)System.out.println("Uh oh 2");

			HuffmanNode tempNode=new HuffmanNode(lNode,rNode);
			
			bCopy.insert(tempNode);
			
			
		}//end of Huffman Heap algo
		HuffmanTree tTree=new HuffmanTree(bCopy.deleteMin());
		//if(tTree.root.right.letter==null)System.out.println("Uh oh");
		return tTree; //placeholder for return
		
	}
	 public static void main(String[] args) {
		 /*public static void main(String[] args) calls legendToHeap() on the legend
string and returns a BinaryHeap (bheap, for example). We then call bheap.printHeap() on
the heap. Next, we call createFromHeap(bheap) on the heap to run our Huffman algorithm
which returns a HuffmanTree, called, here, htree. Finally, we call htree.printLegend() on
this HuffmanTree object to print the binary encodings for each of the letters in our input file.*/
		 String legend="Z 22 A 20 E 24 G 3 H 4 I 17 L 6 N 5 O 10 S 8 V 1 W 2 X 2";
		 BinaryHeap<HuffmanNode> bHeap=legendToHeap(legend);
		 bHeap.printHeap();
		 
		 
		 HuffmanTree hTree = createFromHeap(bHeap);
		 hTree.printLegend();
		 
		 
		 
	 }
	
	  
}
