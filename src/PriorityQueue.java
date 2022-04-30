class PriorityQueue extends ArrayHeap{

    public static void addElement(char elem, int priority){
        HuffNode huffNode = new HuffNode(elem,priority);
        add(huffNode);
    }
   public static HuffNode removeNext(){
        HuffNode temp = removeMin();
        return temp;
    }

}
