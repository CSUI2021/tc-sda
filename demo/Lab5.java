import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// Source https://www.geeksforgeeks.org/avl-with-duplicate-keys/
// Source https://www.javatpoint.com/avl-tree-program-in-java
// Referensi balancing setelah delete : Bryan Tjandra

import java.util.Stack;

public class Lab5 {

    private static InputReader in;
    static PrintWriter out;
    static AVLTree tree = new AVLTree();

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int numOfInitialPlayers = in.nextInt();
        for (int i = 0; i < numOfInitialPlayers; i++) {
            // TODO: process inputs
            String nama = in.next();
            int val = in.nextInt();
            Node temp = tree.insertNode(tree.root, val, nama);
            tree.root = temp;

        }

        int numOfQueries = in.nextInt();
        for (int i = 0; i < numOfQueries; i++) {
            String cmd = in.next();
            if (cmd.equals("MASUK")) {
                handleQueryMasuk();
            } else {
                handleQueryDuo();
            }
        }

        out.close();
    }

    static void handleQueryMasuk() {
        String nama = in.next();
        int val = in.nextInt();
        tree.root = tree.insertNode(tree.root, val, nama);
        out.println(tree.countLower(tree.root, val));
    }

    static void handleQueryDuo() {
        // TODO
        int layer1 = in.nextInt();
        int layer2 = in.nextInt();

        Node bawah = tree.lowerBound(tree.root, layer1);
        tree.check = false;
        Node atas = tree.upperBound(tree.root, layer2);
        tree.check = false;

        
        String out1 = "";
        String out2 = "";
        if ((bawah!=null) && (atas!=null)){
            if(bawah.key==atas.key){
                if(bawah.urutan.size() < 2){
                    out.println("-1 -1");
                    return;
                }
                else {
                    out1 = bawah.urutan.pop();
                    bawah.size--;
                    out2 = atas.urutan.pop();
                    atas.size--;
                    if(bawah.urutan.size() == 0 ) tree.root = tree.deleteNode(tree.root, bawah.key);
                }
            }
            else if (bawah.key < atas.key){
                out1 = bawah.urutan.pop();
                bawah.size--;
                out2 = atas.urutan.pop();
                atas.size--;
                if (bawah.urutan.size() == 0) tree.root = tree.deleteNode(tree.root, bawah.key);
                if (atas.urutan.size() == 0) tree.root = tree.deleteNode(tree.root, atas.key);
            }
            else {
                out.println("-1 -1");
                return;
            }
        }else {
            out.println("-1 -1");
            return;
        }
        int compare = out1.compareTo(out2);
        if (compare<0){
            out.println(out1 + " " + out2);
        }else out.println(out2 + " " + out1);

    }

    // taken from https://codeforces.com/submissions/Petr
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}


// TODO: modify as needed
class Node {
    int key, height, size;
    Node left, right;
    Stack<String> urutan = new Stack<String>();

    Node(int key) {
        this.key = key;
        this.height = 1;
        this.size = 1;
    }
    
}


class AVLTree {

    Node root = null;
    boolean check = false;
    int count = 0;

    Node rotateWithRightChild(Node node1){  
        Node node2 = node1.right;  
        node1.right = node2.left;  
        node2.left = node1;  
        node1.height = getMaxHeight( getHeight( node1.left ), getHeight( node1.right ) ) + 1;
        node1.size = getMaxSize(node1.left) + getMaxSize(node1.right) + node1.urutan.size();  
        node2.height = getMaxHeight( getHeight( node2.right ), node1.height ) + 1;  
        node2.size = getMaxSize(node2.left) + getMaxSize(node2.right) + node2.urutan.size();  
        return node2;  
    }  

    Node rotateWithLeftChild(Node node2){  
        Node node1 = node2.left;  
        node2.left = node1.right;  
        node1.right = node2;  
        node2.height = getMaxHeight( getHeight( node2.left ), getHeight( node2.right ) ) + 1;  
        node2.size = getMaxSize(node2.left) + getMaxSize(node2.right) + node2.urutan.size(); 
        node1.height = getMaxHeight( getHeight( node1.left ), node2.height ) + 1;  
        node1.height = getMaxHeight( getHeight( node1.left ), getHeight( node1.right ) ) + 1;
        return node1;  
    }  

    Node doubleWithLeftChild(Node node3)  {  
        node3.left = rotateWithRightChild( node3.left );  
        return rotateWithLeftChild( node3 );  
    }  

    // inisiasi size dari setiap node dan melakukan update setiap kali ada insert atau delete agar mengurangi kompleksitas
    int getMaxSize(Node node4){
        if (node4==null) return 0;
        int sLeft = node4.left != null ? node4.left.size : 0;
        int sRight = node4.right != null ? node4.right.size : 0;
        node4.size = sLeft + sRight + node4.urutan.size();
        return node4.size;
    }

    Node doubleWithRightChild(Node node1){  
        node1.right = rotateWithLeftChild( node1.right );  
        return rotateWithRightChild( node1 );  
    }     

    int getMaxHeight(int leftNodeHeight, int rightNodeHeight)  
    {  
    return leftNodeHeight > rightNodeHeight ? leftNodeHeight : rightNodeHeight;  
    }  

    Node insertNode(Node node, int key, String playerName) { 
        if (node == null){
            node = new Node(key); 
            node.urutan.push(playerName);
            return node;
        } 
        else if (key < node.key)  {  
            node.left = insertNode( node.left, key, playerName );  
            if( getHeight( node.left ) - getHeight( node.right ) == 2 )  
                if( key < node.left.key )  
                    node = rotateWithLeftChild( node );  
                else  
                    node = doubleWithLeftChild( node );  
        }  
        else if( key > node.key ){  
            node.right = insertNode( node.right, key , playerName);  
            if( getHeight( node.right ) - getHeight( node.left ) == 2 )  
                if( key > node.right.key)  
                    node = rotateWithRightChild( node );  
                else  
                    node = doubleWithRightChild( node );  
        }  
        else {
            node.urutan.push(playerName);
            node.size++;
        }
        node.height =  getMaxHeight( getHeight( node.left ), getHeight( node.right ) )+1;
        node.size = getMaxSize(node.left) + getMaxSize(node.right) + node.urutan.size(); 
        
        return node;  
    }

    Node deleteNode(Node node, int key) {
        if (node == null) return node;
        if (key < node.key) node.left = deleteNode(node.left, key);
        else if (key > node.key) node.right = deleteNode(node.right, key);
        else {
            if (node.urutan.size() > 0){
                return node;
            }
            else {
                
                if ((node.left == null) || (node.right == null)) {
                    Node temp = node.left != null ? node.left : node.right;

                    // No child case
                    if (temp == null) {
                        node = temp;
                        node = null;
                    }
                    else // One child case
                        node = temp; // Copy the contents of the non-empty child
                }
                else {
                    // node with two children: Get the inorder successor (smallest
                    // in the right subtree)
                    Node temp = minValueNode(node.right);
                    
                    // Copy the inorder successor's data to this node and update the count
                    // if (node == root) {
                    //     root = temp;
                    //     root.left = node.left;
                    // };

                    node.key = temp.key;
                    node.urutan = temp.urutan;

                    temp.urutan = new Stack<String>();
                    // Delete the inorder successor
                    node.right = deleteNode(node.right, temp.key);
                }
            }
        }
        if (node == null) return node;

        node.height = getMaxHeight( getHeight( node.left ), getHeight( node.right ) )+1;  
        node.size = getMaxSize(node.left) + getMaxSize(node.right) + node.urutan.size(); 
    
         if (key < node.key)  {  
            if( getHeight( node.left ) - getHeight( node.right ) >= 2 )  
                if( getHeight(node.left.left) - getHeight(node.left.right) >=0 )   
                    node = rotateWithLeftChild( node );  
                else  
                    node = doubleWithLeftChild( node );  
        }  
        else if( key > node.key ){  
            if( getHeight( node.right ) - getHeight( node.left ) >= 2 )  
                if( getHeight(node.right.left) - getHeight(node.right.right) <= 0)  
                    node = rotateWithRightChild( node );  
                else  
                    node = doubleWithRightChild( node );  
        }  
    
        return node;
    }

    Node minValueNode(Node node)
    {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }
 

    Node lowerBound(Node node, int value) {
        Node temp = null;
        if(node == null) return null;
        if (value == node.key){
            check=true;
            return node;
        }
        else if ((node.left != null) && (value < node.key)) temp = lowerBound(node.left, value);
        else if ((node.right != null) && (value > node.key))temp = lowerBound(node.right, value);

        if (check) return temp;
        else if ((temp==null) && (node.key > value)){
            check = true;
            return node;
        }
        return null;
    }

    Node upperBound(Node node, int value) {
        Node temp = null;
        if(node == null) return null;
        if (value == node.key){
            check=true;
            return node;
        }
        else if ((node.left != null) && (value < node.key)) temp = upperBound(node.left, value);
        else if ((node.right != null) && (value > node.key)) temp = upperBound(node.right, value);

        if (check) return temp;
        else if ((temp==null) && (node.key < value)){
            check = true;
            return node;
        } 
        return null;
    }

    int countLower(Node node, int value){
        if (node == null) return 0;
        if (node.key == value) {
            if (node.left == null) return 0;
            return node.left.size;}
        else if (node.key > value) return countLower(node.left, value);
        else if (node.left == null ) return node.urutan.size() + countLower(node.right, value);
        return node.left.size + node.urutan.size() + countLower(node.right, value);
        }

    // Utility function to get height of node
    int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    // Utility function to get balance factor of node
    int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    void printInorder(Node node) {
        if (node == null)
            return;

        /* first recur on left child */
        printInorder(node.left);

        /* then print the data of node */
        System.out.print(node.key + " " + node.urutan + " ");

        /* now recur on right child */
        printInorder(node.right);
    }
}