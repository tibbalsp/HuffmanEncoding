class HuffNode {

    public char getMyChar() {
        return myChar;
    }
    public int getMyFrequency() {
        return myFrequency;
    }
    public HuffNode getMyLeft() {
        return myLeft;
    }
    public void setMyLeft(HuffNode myLeft) {
        this.myLeft = myLeft;
    }
    public HuffNode getMyRight() {
        return myRight;
    }
    public void setMyRight(HuffNode myRight) {
        this.myRight = myRight;
    }

    private char myChar;
    private int myFrequency;
    private HuffNode myLeft, myRight;
    HuffNode(char myChar, int myFrequency){
        this.myChar = myChar;
        this.myFrequency = myFrequency;
        this.myLeft = null;
        this.myRight = null;
    }
    static int compareTo(HuffNode curr, HuffNode given){
            if(curr == null){
                return -1;
            }
            if(given == null){
                return 1;
            }

            if(curr.myFrequency < given.myFrequency){
                return 1;
            }else {
                return -1;
            }
    }

}
