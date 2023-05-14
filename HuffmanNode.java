package HuffmanConverter;

public class HuffmanNode implements Comparable
{ 
	public String letter;
	public Double frequency;
	public HuffmanNode left, right;
	public HuffmanNode(String letter, Double frequency) {
		this.letter=letter;
		this.frequency=frequency;
	}
	public HuffmanNode(HuffmanNode left, HuffmanNode right) {
		this.letter=left.letter+right.letter;
		this.frequency=left.frequency+right.frequency;
		this.left=left;
		this.right=right;
	}
	@Override
	public int compareTo(Object o) {
		double huffmanFreq = ((HuffmanNode)o).frequency; //gets huffman frequency
		return this.frequency.compareTo(huffmanFreq);
	}
	
	public String toString() {
		return "<"+letter+", "+frequency+">";
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public Double getFrequency() {
		return frequency;
	}
	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}
	public HuffmanNode getLeft() {
		return left;
	}
	public void setLeft(HuffmanNode left) {
		this.left = left;
	}
	public HuffmanNode getRight() {
		return right;
	}
	public void setRight(HuffmanNode right) {
		this.right = right;
	}

	
}
