import java.util.*;

public class HuffmanCode {
    static class Node {
        Character ch;
        Integer freq;
        Node left;
        Node right;

        Node(Character ch, Integer freq) {
            this.ch = ch;
            this.freq = freq;
        }

        Node(Character ch, Integer freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    }

    private static Node root;
    static StringBuilder sb = new StringBuilder();

    public static void createHuffmanTree(String text) {

        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));

        for (var entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            int sum = left.freq + right.freq;
            pq.add(new Node(null, sum, left, right));
        }

        root = pq.peek();

        Map<Character, String> huffmanCode = new HashMap<>();
        encodeData(root, "", huffmanCode);

        System.out.println("Huffman Codes of the characters are: " + huffmanCode);
        System.out.println("The initial string is: " + text);

        for (char c : text.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }
        System.out.println("The encoded string is: " + sb);

    }

    public static void encodeData(Node root, String str, Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {

            huffmanCode.put(root.ch, str.isEmpty() ? "1" : str);
            return;
        }
        encodeData(root.left, str + '0', huffmanCode);
        encodeData(root.right, str + '1', huffmanCode);
    }

    public static int decodeData(Node root, int index, StringBuilder sb) {
        if (root.left == null && root.right == null) {

            System.out.print(root.ch);
            return index;
        }
        index++;
        root = (sb.charAt(index) == '0') ? root.left : root.right;
        return decodeData(root, index, sb);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the string to perform Huffman coding: ");
        String text = scanner.nextLine();
        scanner.close();
        if (text.length() > 0) {
            createHuffmanTree(text);
        } else {
            System.out.println("The string is empty.");
            return;
        }

        System.out.print("The decoded string is: ");
        if (root.left == null && root.right == null) {

            for (int i = 0; i < root.freq; i++) {
                System.out.print(root.ch);
            }
        } else {
            int index = -1;
            while (index < sb.length() - 1) {
                index = decodeData(root, index, sb);
            }
        }
        System.out.println();
    }
}
