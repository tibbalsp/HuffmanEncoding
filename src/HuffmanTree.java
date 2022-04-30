import java.util.HashMap;

/**
 * Class for building and managing the Huffman Tree
 */
class HuffmanTree {
    private static PriorityQueue myQueue;

    /**
     * Creates the HuffTree by dequeueing off the priority queue and creating a parent node
     * @param map Map of characters and frequency
     * @return Root node; Parent node
     */
    static HuffNode createHuffTree(HashMap map) {
        // Create necessary Min Heap based Priority Queue
        map.forEach((character, integer) -> PriorityQueue.addElement((char) character, (int) integer));
        HuffNode temp1 ,temp2;

        //Dequeue first two items
        temp1 = PriorityQueue.removeNext();
        temp2 = PriorityQueue.removeNext();
        while(temp2 != null){
            HuffNode newParent = new HuffNode('\u0000',temp1.getMyFrequency() + temp2.getMyFrequency());
            newParent.setMyLeft(temp1);
            newParent.setMyRight(temp2);
            ArrayHeap.add(newParent);

            temp1 = PriorityQueue.removeNext();
            temp2 = PriorityQueue.removeNext();

        }
        return temp1;
    }

    /**
     * Generate our bit map for each character starting from the root down
     * @param root Starting point for traversal
     * @param bitCode Code(EX. 01011) based on location in tree of a character
     * @param bitMap Map that will be used to construct the bitcode string
     */
    static void bitCodeGenerator(HuffNode root, String bitCode, HashMap bitMap){
        if(root == null){
            return;
        }
        if(root.getMyRight() == null && root.getMyLeft() == null){
            bitMap.put(root.getMyChar(),bitCode);
        }else {
            if (root.getMyLeft() != null) {
                bitCodeGenerator(root.getMyLeft(), bitCode+"0",bitMap);
            }
            if (root.getMyRight() != null) {
                bitCodeGenerator(root.getMyRight(), bitCode+"1", bitMap);
            }
        }
    }

    /**
     * Encodes our string into the encoded bit string
     * @param inputArray The desired phrase to encode
     * @param bitMap The bit patters that were constructed by traversing the tree
     * @return encoded string
     */
    public String encode(char[] inputArray, HashMap bitMap){
        StringBuilder encodedString = new StringBuilder();
        for (char c : inputArray) {
            encodedString.append(bitMap.get(c));
        }
        return encodedString.toString();
    }
    int index = 0;
    private String decodeOutput = "";
    Boolean headSet = false;
    HuffNode head = null;
    String temp ;

    /**
     * Decodes the bit string into a string
     * @param root Starting node for traversal
     * @param input the bit string to decode
     * @return String of characters
     */
    public String decode(HuffNode root,String input){
        if(headSet != true) {
            head = root;
            headSet = true;
        }
        while((root.getMyRight() != null || root.getMyLeft() != null) && index < input.length() ){
            if(input.charAt(index) == '0' ){
                root = root.getMyLeft();
            }else{
                root = root.getMyRight();
            }
            index++;
        }
        if(index < input.length()){
            this.decodeOutput += root.getMyChar();
            decode(head,input);
        }else {
            this.decodeOutput += root.getMyChar();
            index++;
            temp = decodeOutput;
            decodeOutput = "";
            headSet = false;
            return temp;
        }
        index = 0;
        return temp;
    }
}

