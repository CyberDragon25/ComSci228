package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * @@author Subham Bhattacharya
 */

/**
 * pretty much helps me make the table I use stacks since this method uses recursions and they use the format
 * LIFO so if I need to go back its easy to pop or push items
 */
public class BinaryCodeFinder {
    /**
     * stack and the boolean variables are static mainly because of recursion so they do not change with the method
     */
    static boolean matchFound = false;
    static Stack<Integer> stack = new Stack<Integer>();


    /**
     *
     * @param tree
     * @param ch
     * @return stack of code of character
     */
    public static Stack<Integer> getBinaryCode(MsgTree tree, char ch) {
        if (tree.payloadChar == ch) {
            matchFound = true;
            return stack;
        }

        if (matchFound == true) {
            return stack;
        }
        if (tree.left == null && tree.right == null) {
            return stack;
        }

        if (!matchFound) {
            stack.push(0);
        }
        getBinaryCode(tree.left,ch);
        if (!matchFound) {
            stack.pop();
            stack.push(1);
        }
        getBinaryCode(tree.right,ch);
        if (!matchFound) {
            stack.pop();
        }
        return stack;

    }
}
