//Author: Patrick Tibbals

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

//Printer for output file
class Printer {
    private static boolean fileStarted = false;
    private static PrintWriter writer;

    static void printWriter(String toPrint) throws IOException {
        // Start New File
        if (!fileStarted) {
            writer = new PrintWriter("Output.txt", StandardCharsets.UTF_8);
            writer.println(toPrint);
            fileStarted = true;
            // Write to current file
        } else {
            writer.println(toPrint);
        }
    }

    static void endPrint() {
        writer.close();
    }
}

class Driver {
    HuffNode huffRoot;
    HuffmanTree huffmanTree= new HuffmanTree();
    HashMap<Character, Integer> feqMap = new HashMap<>();
    HashMap<Character, String> bitMap = new HashMap<>();
    String temp = "";
    void fileReader() throws IOException {
        var timer = System.nanoTime();
        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        temp = br.readLine();
        temp = br.readLine();

        while(temp != null) {
            //Map each string
            char[] inputArr = temp.toCharArray();
            for (char input : inputArr) {
                if (feqMap.containsKey(input)) {
                    feqMap.replace(input, feqMap.get(input) + 1);
                } else {
                    feqMap.put(input, 1);
                }
            }
            //Send to map to TreeClass
            huffRoot = HuffmanTree.createHuffTree(feqMap);
            //Generate the bit codes
            HuffmanTree.bitCodeGenerator(huffRoot, "", bitMap);
            String encoded = huffmanTree.encode(inputArr, bitMap);
            String decoded = huffmanTree.decode(huffRoot, encoded);
            buildOutput(temp, encoded, decoded);
            temp = br.readLine();
            if(temp != null){
                bitMap = new HashMap<>();
                feqMap = new HashMap<>();
            }else {
                var EndTimer = System.nanoTime();
                System.out.println((EndTimer-timer)/1000000);
                Printer.endPrint();
            }

        }

    }

    void buildOutput(String inputString, String encoded, String decoded) throws IOException {
        Printer.printWriter("[Input Text: " + inputString + "]");
        Printer.printWriter("======================================");
        Printer.printWriter("  CHAR        FREQUENCY        CODE   ");
        Printer.printWriter("--------------------------------------");

            feqMap.forEach((character, integer) -> {
                try {
                    buildStringOutput(character, integer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        Printer.printWriter("======================================");
        Printer.printWriter("[Encoded Bit: "+ encoded +"] ["+ encoded.length() +"]");
        Printer.printWriter("[Decoded String: "+decoded+"] ["+decoded.length()*8+"]");
        Printer.printWriter("[Compression Ratio: "+(((double) (decoded.length()*8)))/((double) (encoded.length()))+"]");
        Printer.printWriter("");
        Printer.printWriter("");

    }
    void buildStringOutput(char c , int i) throws IOException {
            String output = "   "+c+"           "+i+"               "+bitMap.get(c)+"   ";
            Printer.printWriter(output);
    }

    public static void main(String[] args) throws IOException {
        Driver driver = new Driver();
        driver.fileReader();

    }
}









