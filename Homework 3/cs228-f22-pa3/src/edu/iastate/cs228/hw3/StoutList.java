package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
    /**
     * Default number of elements that may be stored in each node.
     */
    private static final int DEFAULT_NODESIZE = 4;

    /**
     * Number of elements that can be stored in each node.
     */
    private final int nodeSize;

    /**
     * Dummy node for head.  It should be private but set to public here only
     * for grading purpose.  In practice, you should always make the head of a
     * linked list a private instance variable.
     */
    public Node head;

    /**
     * Dummy node for tail.
     */
    private Node tail;

    /**
     * Number of elements in the list.
     */
    private int size;

    /**
     * Constructs an empty list with the default node size.
     */
    public StoutList()
    {
        this(DEFAULT_NODESIZE);
    }

    /**
     * Constructs an empty list with the given node size.
     * @param nodeSize number of elements that may be stored in each node, must be
     *   an even number
     */
    public StoutList(int nodeSize)
    {
        if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();

        // dummy nodes
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.previous = head;
        this.nodeSize = nodeSize;
    }

    /**
     * Constructor for grading only.  Fully implemented.
     * @param head
     * @param tail
     * @param nodeSize
     * @param size
     */
    public StoutList(Node head, Node tail, int nodeSize, int size)
    {
        this.head = head;
        this.tail = tail;
        this.nodeSize = nodeSize;
        this.size = size;
    }

    /**
     * returns size of list
     * @return size
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Adds item to end of list
     * returns true if item was added sucessfully false otherwise
     * @param item
     */
    @Override
    public boolean add(E item)
    {
        // TODO Auto-generated method stub
        if (item == null) {
            throw new NullPointerException();
        }

        boolean ans = false;

        // if it is an empty list
        if (size == 0) {
            Node node = new Node(); // creating a new empty node since list is empty
            node.addItem(item); // adding item
            // setting up the pointers
            head.next = node;
            tail.previous = node;
            node.next = tail;
            node.previous = head;
            size++;
            ans = true;
        } // if list is not empty then
        // now I will check whether the last node of the list still has space in the array so if it dosen't
        else if (tail.previous.count >= nodeSize) { // number of elements in the last node is greater than size of node
            // create a new node and add the item
            Node node = new Node();
            node.addItem(item);
            // setting up the pointers
            Node t = tail.previous; // setting up temp node for previous last node
            t.next = node;
            node.previous = t;
            tail.previous = node;
            node.next = tail;
            size++;
            ans = true;
        } else { // if last element of node still has space
            tail.previous.addItem(item);
            size++;
            ans = true;
        }
        return ans;
    }

    /**
     * Adds the item to a specified index which is the pos
     * followed the assingment documentation
     * @param pos
     * @param item adds item to the specified pos in the list
     */
    @Override
    public void add(int pos, E item)
    {
        // if position is null throwing a null pointer exeption
        if (item == null) {
            throw new NullPointerException();
        }
        // if pos < 0
        if (pos < 0) {
            throw new NoSuchElementException();
        }

        // if the list is empty, create a new node and put X at offset 0
        if (head.next == tail) {
            add(item);
        }
        // getting the node information needed
        //
        NodeInfo nodeInfo = find(pos);
        Node currentNode = nodeInfo.node;
        int offset = nodeInfo.offset;

        //  if offset = 0 and one of the following two cases occurs
        if (offset == 0) {
            // if n has a predecessor which has fewer than M elements (and is not the head), put X in n’s
            // predecessor
            if (currentNode.previous != head && currentNode.previous.count < nodeSize) {
                currentNode.previous.addItem(item);
                size++;
                return;
            }
            // if n is the tail node and n’s predecessor has M elements, create a new node and put X at offset
            // 0
            else if (currentNode == tail && currentNode.previous.count == nodeSize) {
                // creating a new node
                Node node = new Node();
                Node temp = tail.previous;
                // setting up the pointers for the new node
                node.next = temp;
                tail.previous = node;
                temp.next = node;
                node.previous = temp;
                node.addItem(0, item);
                size++;
                return;
            }
        }
        // otherwise if there is space in node n, put X in node n at offset off, shifting array elements as
        // necessary
        if (currentNode.count < nodeSize) {
            currentNode.addItem(offset, item);
            size++;
            return;
        }
        // otherwise, perform a split operation: move the last M/2 elements of node n into a new successor
        // node n', and then
        else {
            Node sucessor = new Node();
            // moving half of the data of currentNode in to the node sucessor
            for (int i = 0; i < nodeSize / 2; i++) {
                sucessor.addItem(currentNode.data[(nodeSize / 2)]);
                currentNode.removeItem(nodeSize/2);
            }

            // setting up the pointers for all the nodes since we are inserting this node in the middle of the list
            Node prevSucess = currentNode.next;
            sucessor.next = prevSucess;
            sucessor.previous = currentNode;
            prevSucess.previous = sucessor;
            currentNode.next = sucessor;


            // if off <= M/2 , put X in node n at offset off
            if (offset <= nodeSize / 2) {
                currentNode.addItem(offset, item);
            }


            // if off > M/2 put X in node n's offset  (off − M/2)
            if (offset > nodeSize / 2) {
                sucessor.addItem((offset - (nodeSize / 2)), item);
            }
            size++;
        }
    }

    // helper methods

    /**
     * helper method for specific nodes
     */
    private class NodeInfo
    {
        // offset is index inside the node
        public Node node;
        public int offset;
        public NodeInfo(Node node, int offset)
        {
            this.node = node;
            this.offset = offset;
        }
    }

    /**
     *Locates an specific item or index
     * @param pos
     * @return nodeInfo of specific position in list
     */
    private NodeInfo find(int pos){
        if (pos < 0 || pos > size) {
            throw new NoSuchElementException();
        }
        Node currentNode = head.next;
        int currentItemCount = 0;
        while (currentNode != tail && currentItemCount + currentNode.count <= pos) {
            currentItemCount += currentNode.count;
            currentNode = currentNode.next;
        }

        return new NodeInfo(currentNode, pos - currentItemCount);
    }



    /**
     * Removes item from the position given in the parametre
     * then returns that item
     * @param pos
     * @return item or E
     */
    @Override
    public E remove(int pos)
    {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException();
        }

        // setting up nodeinfo
        NodeInfo nodeInfo = find(pos);
        Node currentNode = nodeInfo.node;
        int offset = nodeInfo.offset;
        E nodeValue = currentNode.data[offset];

        // if the node n containing X is the last node and has only one element, delete it
        if (currentNode.next == tail && currentNode.count == 1) {
            Node predessor = currentNode.previous;
            predessor.next = tail;
            tail.previous = predessor;
            return currentNode.data[0];
        }
        // otherwise, if n is the last node (thus with two or more elements) , or if n has more than M/2
        // elements, remove X from n, shifting elements as necessary;
        else if ((currentNode.next == tail && currentNode.count > 1) || currentNode.count > (nodeSize / 2)) {
            currentNode.removeItem(offset);
        }
        // otherwise (the node n must have at most M/2  elements), look at its successor n' (note that we don’t
        // look at the predecessor of n) and perform a merge operation as follows
        else {
            currentNode.removeItem(offset);
            Node sucessor = currentNode.next;
            // if the successor node n' has more than M/2 elements, move the first element from n' to n.
            // (mini-merge)
            if (sucessor.count > nodeSize / 2) {
                currentNode.addItem(sucessor.data[0]);
                sucessor.removeItem(0);
            }
            // if the successor node n' has M/2 or fewer elements, then move all elements from n' to n and
            // delete n' (full merge)
            else if (sucessor.count <= nodeSize / 2) {
                for (int i = 0; i < sucessor.count; i++) {
                    currentNode.addItem(sucessor.data[i]);
                }
                currentNode.next = sucessor.next;
                sucessor.previous = currentNode;
            }
        }

        // decreasing size since list has 1 value removed
        size--;
        return nodeValue;
    }

    /**
     * Sort all elements in the stout list in the NON-DECREASING order. You may do the following.
     * Traverse the list and copy its elements into an array, deleting every visited node along
     * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting
     * efficiency is not a concern for this project.)  Finally, copy all elements from the array
     * back to the stout list, creating new nodes for storage. After sorting, all nodes but
     * (possibly) the last one must be full of elements.
     *
     * Comparator<E> must have been implemented for calling insertionSort().
     */
    public void sort()
    {
        // creating an array
        E[] arr = (E[]) new Comparable[size];

        //Traverse the list and copy its elements into an array, deleting every visited node along
        // the way
        int index = 0;
        Node curr = head.next;
        while (curr != tail) {
            for (int i = 0; i < curr.count; i++) {
                arr[index] = curr.data[i];
                index++;
            }
            curr = curr.next;
        }
        // deleting the nodes
        head.next = tail;
        tail.previous = head;

        // sort the array by calling the insertionSort() method
        insertionSort(arr, new EleComp<>());

        // copy all elements from the array
        //   * back to the stout list, creating new nodes for storage. After sorting, all nodes but
        //   * (possibly) the last one must be full of elements.
        size = 0;
        for (int i = 0; i < arr.length; i++) {
            add(arr[i]);
        }

    }

    /**
     * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
     * method.  After sorting, all but (possibly) the last nodes must be filled with elements.
     *
     * Comparable<? super E> must be implemented for calling bubbleSort().
     */
    public void sortReverse()
    {
        // just coping the code from the other method
        // creating an array
        E[] arr = (E[]) new Comparable[size];

        //Traverse the list and copy its elements into an array, deleting every visited node along
        // the way
        int index = 0;
        Node curr = head.next;
        while (curr != tail) {
            for (int i = 0; i < curr.count; i++) {
                arr[index] = curr.data[i];
                index++;
            }
            curr = curr.next;
        }
        // deleting the nodes
        head.next = tail;
        tail.previous = head;

        // sort the array by calling the insertionSort() method
        bubbleSort(arr);

        // copy all elements from the array
        //   * back to the stout list, creating new nodes for storage. After sorting, all nodes but
        //   * (possibly) the last one must be full of elements.
        size = 0;
        for (int i = 0; i < arr.length; i++) {
            add(arr[i]);
        }
    }

    @Override
    public Iterator<E> iterator()
    {
        return new StoutListIterator();
    }

    @Override
    public ListIterator<E> listIterator()
    {
        return new StoutListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index)
    {
        return new StoutListIterator(index);
    }

    /**
     * Returns a string representation of this list showing
     * the internal structure of the nodes.
     */
    public String toStringInternal()
    {
        return toStringInternal(null);
    }

    /**
     * Returns a string representation of this list showing the internal
     * structure of the nodes and the position of the iterator.
     *
     * @param iter
     *            an iterator for this list
     */
    public String toStringInternal(ListIterator<E> iter)
    {
        int count = 0;
        int position = -1;
        if (iter != null) {
            position = iter.nextIndex();
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node current = head.next;
        while (current != tail) {
            sb.append('(');
            E data = current.data[0];
            if (data == null) {
                sb.append("-");
            } else {
                if (position == count) {
                    sb.append("| ");
                    position = -1;
                }
                sb.append(data.toString());
                ++count;
            }

            for (int i = 1; i < nodeSize; ++i) {
                sb.append(", ");
                data = current.data[i];
                if (data == null) {
                    sb.append("-");
                } else {
                    if (position == count) {
                        sb.append("| ");
                        position = -1;
                    }
                    sb.append(data.toString());
                    ++count;

                    // iterator at end
                    if (position == size && count == size) {
                        sb.append(" |");
                        position = -1;
                    }
                }
            }
            sb.append(')');
            current = current.next;
            if (current != tail)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }


    /**
     * Node type for this list.  Each node holds a maximum
     * of nodeSize elements in an array.  Empty slots
     * are null.
     */
    private class Node
    {
        /**
         * Array of actual data elements.
         */
        // Unchecked warning unavoidable.
        public E[] data = (E[]) new Comparable[nodeSize];

        /**
         * Link to next node.
         */
        public Node next;

        /**
         * Link to previous node;
         */
        public Node previous;

        /**
         * Index of the next available offset in this node, also
         * equal to the number of elements in this node.
         */
        public int count;

        /**
         * Adds an item to this node at the first available offset.
         * Precondition: count < nodeSize
         * @param item element to be added
         */
        void addItem(E item)
        {
            if (count >= nodeSize)
            {
                return;
            }
            data[count++] = item;
            //useful for debugging
            //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
        }

        /**
         * Adds an item to this node at the indicated offset, shifting
         * elements to the right as necessary.
         *
         * Precondition: count < nodeSize
         * @param offset array index at which to put the new element
         * @param item element to be added
         */
        void addItem(int offset, E item)
        {
            if (count >= nodeSize)
            {
                return;
            }
            for (int i = count - 1; i >= offset; --i)
            {
                data[i + 1] = data[i];
            }
            ++count;
            data[offset] = item;
            //useful for debugging
            //System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
        }

        /**
         * Deletes an element from this node at the indicated offset,
         * shifting elements left as necessary.
         * Precondition: 0 <= offset < count
         * @param offset
         */
        void removeItem(int offset)
        {
            E item = data[offset];
            for (int i = offset + 1; i < nodeSize; ++i)
            {
                data[i - 1] = data[i];
            }
            data[count - 1] = null;
            --count;
        }
    }


    private class StoutListIterator implements ListIterator<E>
    {
        // constants you possibly use ...


        // instance variables ...
        int pos; // pos of iterator
        public E[] datalist; // iterator in the array form
        int lastPos; // last pos of the iterator
        int nextAction = 1;
        int prevAction = 0;


        /**
         * Default constructor
         */
        public StoutListIterator()
        {
            pos = 0;
            lastPos = -1;
            setUpIterator();
        }

        /**
         * Constructor finds node at a given position.
         * @param pos
         */
        public StoutListIterator(int pos)
        {
            this.pos = pos;
            lastPos = -1;
            setUpIterator();

        }

        /**
         * sets up iterator takes the value of the stoutlist and puts it in the iterator data strcuture
         */
        public void setUpIterator() {
            datalist = (E[]) new Comparable[size]; // creating the array for the iterator
            // index of the array
            int index = 0;
            Node currNode = head.next;
            // transfering the value of list to an array type
            while (currNode != tail) {
                for (int i = 0; i < currNode.count; i++) {
                    datalist[index] = currNode.data[i];
                    index++;
                }
                currNode = currNode.next;
            }
        }

        /**
         *
         * @return boolean value true if the iterator has next value, false otherwise
         */
        @Override
        public boolean hasNext()
        {
            if (pos < size) {
                return true;
            }
            return false;
        }

        /**
         *
         * @return the next value of iterator and moves it 1 space
         */
        @Override
        public E next()
        {
            if (hasNext() == false) {
                throw new NoSuchElementException();
            }
            lastPos = nextAction;
            return datalist[pos++];
        }

        /**
         *
         * @return boolean value true if the iterator has previous value, false otherwise
         */
        @Override
        public boolean hasPrevious() {
            if (pos > 0) {
                return true;
            }
            return false;
        }

        /**
         *
         * @returnthe previous value of iterator and moves it back by 1 space
         */
        @Override
        public E previous() {
            if (hasPrevious() == false) {
                throw new NoSuchElementException();
            }
            pos--;
            lastPos = prevAction;
            return datalist[pos];
        }

        /**
         *
         * @return index of the next available element
         */
        @Override
        public int nextIndex() {
            return pos;
        }

        /**
         *
         * @return index of the previous available element
         */
        @Override
        public int previousIndex() {
            return pos - 1;
        }

        /**
         * removes the element last called by next or previous from both
         * the listiterator and the list itself
         */
        @Override
        public void remove()
        {
            if (lastPos == prevAction) {
                StoutList.this.remove(pos);
                setUpIterator();
                lastPos = -1;
            } else if (lastPos == nextAction) {
                StoutList.this.remove(pos - 1);
                setUpIterator();
                lastPos = -1;
                pos--;
                if (pos < 0) {pos = 0;}
            } else {
                throw new IllegalArgumentException();
            }
        }

        /**
         * sets an element at the current pointer of the iterator
         * @param e
         */
        @Override
        public void set(E e) {
            if (lastPos == prevAction) {
                NodeInfo nodeInfo = find(pos);
                nodeInfo.node.data[nodeInfo.offset] = e;
                datalist[pos] = e;
            } else if (lastPos == nextAction) {
                NodeInfo nodeInfo = find(pos - 1);
                nodeInfo.node.data[nodeInfo.offset] = e;
                datalist[pos - 1] = e;
            } else {
                throw new IllegalArgumentException();
            }
        }

        /**
         * adds an element to the end of the list
         * @param e
         */
        @Override
        public void add(E e) {
            if (e == null) {
                throw new NullPointerException();
            }
            StoutList.this.add(pos, e);
            pos++;
            setUpIterator();
            lastPos = -1;
        }

    }


    /**
     * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order.
     * @param arr   array storing elements from the list
     * @param comp  comparator used in sorting
     */
    private void insertionSort(E[] arr, Comparator<? super E> comp)
    {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (comp.compare(arr[j], arr[j-1]) <= 0) {
                    E temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                } else break;
            }
        }
    }

    /**
     * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
     * description of bubble sort please refer to Section 6.1 in the project description.
     * You must use the compareTo() method from an implementation of the Comparable
     * interface by the class E or ? super E.
     * @param arr  array holding elements from the list
     */
    private void bubbleSort(E[] arr)
    {
        boolean swapped;
        for (int i = 0; i < arr.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) < 0) {
                    E temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
                if (swapped == false) break;
            }
        }
    }

    private class EleComp<E extends Comparable<? super E>> implements  Comparator<E>{

        @Override
        public int compare(E o1, E o2) {
            return o1.compareTo(o2);
        }
    }



}
