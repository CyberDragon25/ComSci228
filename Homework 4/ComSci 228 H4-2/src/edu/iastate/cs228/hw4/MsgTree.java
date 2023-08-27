package edu.iastate.cs228.hw4;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Subham Bhattacharya
 */
// The "^" creates a node to the left of the current node and moves to that node. Any other character will assign
// the character to the current node and then move up in the tree to find
// the next ancestor node without a right node, which it then creates a right node at to move to.
public class MsgTree {
    public char payloadChar = Character.MIN_VALUE;
    public MsgTree left;
    public MsgTree right;
    public MsgTree parent;
    public static Map<String, String> map = new HashMap<String, String>();

    //Constructor for a single node with null children
    /**
     *
     * @param payloadChar
     */
    public MsgTree(char payloadChar){
        this.payloadChar = payloadChar;
        this.left = null;
        this.right = null;
    }


    // adds data to current node then goes up an ansestor and checks if it has right node
    // if it does it goes back up until it finds a node without one, once it finds that node
    // it creates a right node, (taken from piazza)
    public MsgTree addChild (char data, Direction direction) {
        MsgTree msgTree = new MsgTree(data);
        msgTree.parent = this;
        msgTree.left = null;
        msgTree.right = null;
        if (direction == Direction.LEFT) {
            this.left = msgTree;
        } else {
            this.right = msgTree;
        }
        return msgTree;
    }

    /**
     *
     * @param currentTree
     * @return Msgtree node of the last ansestor with empty parent
     */
    public MsgTree getEmptyParent (MsgTree currentTree) {
        if (currentTree.parent.right == null) {
            return currentTree.parent;
        }
        while (currentTree.parent.right != null) {
            currentTree = currentTree.parent;
            if (currentTree.parent.right == null) {
                return currentTree.parent;
            }
        }
        return null;
    }



    /**
     * Constructor building the tree from a string
     * @param encodingString
     * my tree is done recurisvely with the about created methods its pretty straightforward
     * I simply use a for loop to go throught the entire string and use the if statments
     */
    public MsgTree(String encodingString){
        MsgTree root = this;
        MsgTree currentTree = root;
        for (int i = 0; i < encodingString.length(); i++) {
            char currentCode = encodingString.charAt(i);
            if (currentCode == '^') {
                currentTree = currentTree.addChild(Character.MIN_VALUE, Direction.LEFT);
            } else {
                currentTree.payloadChar = currentCode;
                if (i == encodingString.length() - 1) break;
                currentTree = currentTree.getEmptyParent(currentTree);
                currentTree = currentTree.addChild(Character.MIN_VALUE, Direction.RIGHT);
            }
        }
    }


    //method to print characters and their binary codes
    /**
     *
     * @param root
     * @param code
     * pretty much prints the table
     */
    public static void printCodes(MsgTree root, String code) {
        System.out.println("character code   ");
        System.out.println("-------------------------");
        BinaryCodeFinder codeFinder = new BinaryCodeFinder();
        for (int i = 0; i < code.length(); i++) {
            BinaryCodeFinder.stack = new Stack<>();
            BinaryCodeFinder.matchFound = false;
            Stack<Integer> stack = codeFinder.getBinaryCode(root, code.charAt(i));
            String ans = "";
            while (!stack.empty()) {
                ans += stack.pop();
            }
            ans = new StringBuilder(ans).reverse().toString();
            map.put(ans, Character.toString(code.charAt(i)));
            System.out.println("    " + (code.charAt(i) == '\n' ? "\\n" : code.charAt(i) + " ") + "    " + ans);
        }
    }


    // decoded message prints decoded message to console
    /**
     *
     * @param codes
     * @param msg
     * prints the message I have used a string builder and hashmap for this function
     */
    public void decode(MsgTree codes, String msg) {
        System.out.println("MESSAGE:");
        StringBuilder sb = new StringBuilder();
        String container = "";
        for (int i = 0; i < msg.length(); i++) {
            container += msg.charAt(i);
            if (map.containsKey(container)) {
                sb.append(map.get(container));
                container = "";
            }
        }

        System.out.println(sb.toString());
    }
}
