package project;


import java.util.Date;

public class BST <T extends Comparable<T>> {
	
	

	    BSTNode<T> root, current;

	    /** Creates a new instance of BST */
	    public BST() {
	        root = current = null;
	    }

	    public boolean empty() {
	        return root == null;
	    }

	    public boolean full() {
	        return false;
	    }

	    public T retrieve() {
	        return current.data;
	    }
	    public void findRoot(){
	        current = root;
	    }

	    public boolean findkey(String tkey) {
	        BSTNode<T> p = root, q = root;

	        if (empty())
	            return false;

	        while (p != null) {
	            q = p;
	            if (p.key.equals(tkey)) {
	                current = p;
	                return true;
	            } else if (tkey.compareTo(p.key) < 0)
	                p = p.left;
	            else
	                p = p.right;
	        }

	        current = q;
	        return false;
	    }

	    

	    public boolean insert(String k, T val) {
	        BSTNode<T> p, q = current;

	        if (findkey(k)) {
	            current = q; // findkey() modified current
	            return false; // key already in the BST
	        }

	        p = new BSTNode<T>(k, val);
	        if (empty()) {
	            root = current = p;
	            return true;
	        } else {
	            // current is pointing to parent of the new key
	            if (k.compareTo(current.key) < 0)
	                current.left = p;
	            else
	                current.right = p;
	            current = p;
	            return true;
	        }
	        
	        
	    }

	    public boolean update(String key, T data) {
	        removeKey(key);
	        return insert(key, data);
	    }

	    public boolean removeKey(String k) {
	        String k1 = k;
	        BSTNode<T> p = root;
	        BSTNode<T> q = null;
	        // Parent of p
	        while (p != null) {
	            if (k1.compareTo(p.key) < 0) {
	                q = p;
	                p = p.left;
	            } else if (k1.compareTo(p.key) > 0) {
	                q = p;
	                p = p.right;
	            } else { // Found the key // Check the three cases
	                if ((p.left != null) && (p.right != null)) {
	                    // Case 3: two children // Search for the min in the right subtree
	                    BSTNode<T> min = p.right;
	                    q = p;
	                    while (min.left != null) {
	                        q = min;
	                        min = min.left;
	                    }
	                    p.key = min.key;
	                    p.data = min.data;
	                    k1 = min.key;
	                    p = min; // Now fall back to either case 1 or 2
	                }
	                // The subtree rooted at p will change here
	                if (p.left != null) { // One child
	                    p = p.left;
	                } else { // One or no children
	                    p = p.right;
	                }
	                if (q == null) { // No parent for p, root must change
	                    root = p;
	                } else {
	                    if (k1.compareTo(q.key) < 0) {
	                        q.left = p;
	                    } else {
	                        q.right = p;
	                    }
	                }
	                current = root;
	                return true;
	            }
	        }
	        return false; // Not found
	    }

	    void printAll(BSTNode root) 
	{ 

	    // If node is null, return 
	    if (root == null) 
	        return; 
	   
	    System.out.print(root.data + "\n");
	    // If node is leaf node, print its data
	    if (root.left == null && 
	        root.right == null) 
	    { 
	    	
	        return; 
	    } 

	    // If left child exists, check for leaf 
	    // recursively 
	    if (root.left != null) 
	        printAll(root.left); 

	    // If right child exists, check for leaf 
	    // recursively 
	    if (root.right != null) 
	        printAll(root.right); 
	    
	} 
	    public void preorder(BSTNode node) {
	        if (node != null) {
	            preorder(node.left);
	            System.out.print(node.data + "\n");
	            preorder(node.right);
	        }
	    }
	  
	 
	public boolean findNumber(String number) {
	    BSTNode<T> p = root, q = root;

	    if (empty())
	        return false;

	    while (p != null) {
	        q = p;
	        if (p.key.equals(number)) {
	            current = p;
	            return true;
	        } else if (number.equalsIgnoreCase(p.key))
	            p = p.left;
	        else
	            p = p.right;
	    }

	    current = q;
	    return false;
	}

}
