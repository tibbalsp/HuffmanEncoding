/**
 * Array heap that is the base of the priority queue
 */
public class ArrayHeap {
    static HuffNode[] heap = new HuffNode[2];
    static int count = 0;
    static int start = 0;

    /**
     * Add a new node to the heap, heapify from bottom to top if the parent is larger than added node
     * @param huffNode node to be added to the heap
     */
    public static void add(HuffNode huffNode) {
        //Expand storage as needed
        if (heap.length == count) {
            HuffNode[] temp = new HuffNode[heap.length * 2];
            for (int i = 0; i <= heap.length - 1; i++) {
                temp[i] = heap[i];
            }
            heap = temp;
        }
        //Add the new node
        heap[count] = huffNode;
        if (count == 0 || heap[((count - 1) / 2)].getMyFrequency() < huffNode.getMyFrequency()) {
            //Do Nothing Parent is smaller
        } else {
            //Parent is larger so we must heapify
            heapifyBtoT((count - 1) / 2);
        }
        count++;
    }

    /**
     * Removes the root node of the heap and heapifies from top to bottom
     * @return The new root node
     */
    public static HuffNode removeMin() {
        HuffNode min = heap[0];
        if (count < 1) {
            return null;
        }
        heap[0] = heap[count - 1];
        heap[count - 1] = null;
        count--;
        heapifyTtoB(0);
        return min;
    }

    /**
     * Heapifies from top to bottom recursively
     * @param startIndex Location of the root or parent node to start from
     */
    static void heapifyTtoB(int startIndex) {
        HuffNode min = heap[startIndex];
        int leftChild = 2 * startIndex + 1;
        int rightChild = 2 * startIndex + 2;
        HuffNode swapChild = null;
        int swapChildIndex = 0;
        if (leftChild < heap.length && rightChild < heap.length) {
            //Swap left
            if (HuffNode.compareTo(heap[leftChild], heap[rightChild]) > 0) {
                if (HuffNode.compareTo(heap[leftChild], min) > 0) {
                    swapChild = heap[leftChild];
                    swapChildIndex = leftChild;
                }
            //Swap right
            } else if (HuffNode.compareTo(heap[leftChild], heap[rightChild]) < 0) {
                if (HuffNode.compareTo(heap[rightChild], min) > 0) {
                    swapChild = heap[rightChild];
                    swapChildIndex = rightChild;
                }
            } else {
                //No swap needed
            }
            //Preform swap and recurse
            if (swapChild != null && swapChild.getMyFrequency() != 0 && swapChild.getMyFrequency() < min.getMyFrequency()) {
                heap[startIndex] = swapChild;
                heap[swapChildIndex] = min;
                heapifyTtoB(swapChildIndex);
            }
        }
    }

    /**
     * Preforms bottom to top heapify recursively
     * @param startIndex Location of the root or parent node to start from
     */
    static void heapifyBtoT(int startIndex) {
        HuffNode min = heap[startIndex];
        int leftChild = 2 * startIndex + 1;
        int rightChild = 2 * startIndex + 2;

        if (leftChild < heap.length && heap[leftChild] != null && heap[leftChild].getMyFrequency() < min.getMyFrequency()) {
            start = leftChild;
            min = heap[leftChild];
        } else if (rightChild < heap.length && heap[rightChild] != null && heap[rightChild].getMyFrequency() < min.getMyFrequency()) {
            start = rightChild;
            min = heap[rightChild];
        }
        HuffNode temp;
        if (min != heap[startIndex]) {
            temp = heap[startIndex];
            heap[startIndex] = min;
            heap[start] = temp;
            heapifyBtoT(((startIndex - 1) / 2));
        }
    }
}


