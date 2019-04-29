public class Node {

	private char character;
	private int frequency;
	private Node left;
	private Node right;
	
	public Node(int frequency, Node left, Node right) {
		this.frequency = frequency;
		this.left = left;
		this.right = right;
	}
	
	public Node(char character, int frequency) {
		this.character = character;
		this.frequency = frequency;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public Node getRight() {
		return right;
	}
	
	
}
