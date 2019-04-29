import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class App {
	
	public static final String INPUT_FILE_NAME = "sample.txt";
	public static final String OUTPUT_FILE_NAME = "output.txt";
	
	public static HashMap<Character, Integer> map = new HashMap<Character, Integer>();
	public static HashMap<Character, String> codes = new HashMap<Character, String>();
	public static PriorityQueue<Node> queue = new PriorityQueue<Node>(new Compare());
	
	public static String readFile(String filepath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filepath)));
	}
	
	public static void record(String data) {
		for(char c : data.toCharArray()) {
			Integer val = map.get(c);
			if(val == null)
				map.put(c, 1);
			else
				map.put(c, val+1);
		}
	}
	
	public static void display() {
		System.out.println(Arrays.asList(map));
	}
	
	public static void queue()  {
		for(Entry<Character, Integer> entry : map.entrySet()) {
			Node node = new Node(entry.getKey(), entry.getValue());
			queue.add(node);
		}
	}
	
	public static Node buildHuffmanTree() {
		while(queue.size() > 1 ) {
			Node left = queue.poll();
			Node right = queue.poll();
			int frequency = left.getFrequency() + right.getFrequency();
			Node node = new Node(frequency, left, right);
			queue.add(node);
		}
		return queue.poll();
	}
	
	public static void assignCodes(Node root, String code) {
		if(root.getLeft() == null && root.getRight() == null)
			codes.put(root.getCharacter(), code);
		if(root.getLeft() != null)
			assignCodes(root.getLeft(), code+"0");
		if(root.getRight() != null)
			assignCodes(root.getRight(), code+"1");
	}
	
	public static String write(String data) {
		StringBuilder huffmanCode = new StringBuilder();
		for(char c: data.toCharArray()) {
			huffmanCode.append(codes.get(c));
		}
		return new String(huffmanCode);
	}
	
	public static void compress(String data) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(OUTPUT_FILE_NAME);
		int max = data.length();
		int start = 0;
		int end = 8;	
		while(end <= max) {
			String substring = data.substring(start, end);
			int binumber = Integer.parseInt(substring, 2);
			byte binstring = (byte) binumber;
			outputStream.write(binstring);
			start = end;
			end = end + 8;
		}
		outputStream.close();
	}
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("COMPRESSING.....");
		
		// read input file
		String data = readFile(INPUT_FILE_NAME);
		// assign characters with their corresponding frequencies to a map.
		record(data);
		// input characters into the min heap.
		queue();
		// build huffman tree
		Node root = buildHuffmanTree();
		// assign huffman codes
		assignCodes(root, "");
		// retrieve binary code string
		String huffmanCode = write(data);
		// convert and write to the output file
		compress(huffmanCode);
		
		System.out.println("DONE!");
	}

}
