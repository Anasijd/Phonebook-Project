package testttt;

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

	    
//	    public void insert(String k, T val) {
//	        BSTNode newNode = new BSTNode(k, val);
//
//	        if (root == null) {
//	            root = newNode;
//	            return;
//	        }
//
//	        BSTNode parent = null;
//	        BSTNode current = root;
//
//	        while (current != null) {
//	            parent = current;
//
//	            if (k.compareTo(current.key) < 0) {
//	                current = current.left;
//	            } else {
//	                current = current.right;
//	            }
//	        }
//
//	        if (k.compareTo(parent.key) < 0) {
//	            parent.left = newNode;
//	        } else {
//	            parent.right = newNode;
//	        }
//	    }
	    
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

	    // public boolean findkey(int tkey) {
	    // BSTNode<T> p = root,q = root;

	    // if (empty())
	    // return false;

	    // while (p != null) {
	    // q = p;
	    // if (p.key == tkey) {
	    // current = p;
	    // return true;
	    // } else if (tkey < p.key)
	    // p = p.left;
	    // else
	    // p = p.right;
	    // }

	    // current = q;
	    // return false;
	    // }

	    // public boolean insert(int k, T val) {
	    // BSTNode<T> p, q = current;

	    // if (findkey(k)) {
	    // current = q; // findkey() modified current
	    // return false; // key already in the BST
	    // }

	    // p = new BSTNode<T>(k, val);
	    // if (empty()) {
	    // root = current = p;
	    // return true;
	    // } else {
	    // // current is pointing to parent of the new key
	    // if (k < current.key)
	    // current.left = p;
	    // else
	    // current.right = p;
	    // current = p;
	    // return true;
	    // }
	    // }

	    ///////////////////////////////////////////////////////////////
	    // public boolean remove_key(int tkey) {
	    // Boolean removed = false;
	    // BSTNode<T> p;
	    // p = remove_aux(tkey, root, removed);
	    // current = root = p;
	    // return removed;
	    // }

	    // private BSTNode<T> remove_aux(int key, BSTNode<T> p, Boolean flag) {
	    // BSTNode<T> q, child = null;
	    // if (p == null)
	    // return null;
	    // if (key < p.key)
	    // p.left = remove_aux(key, p.left, flag); // go left
	    // else if (key > p.key)
	    // p.right = remove_aux(key, p.right, flag); // go right
	    // else {
	    // flag = true;
	    // if (p.left != null && p.right != null) { // two children
	    // q = find_min(p.right);
	    // p.key = q.key;
	    // p.data = q.data;
	    // p.right = remove_aux(q.key, p.right, flag);
	    // } else {
	    // if (p.right == null) // one child
	    // child = p.left;
	    // else if (p.left == null) // one child
	    // child = p.right;
	    // return child;
	    // }
	    // }
	    // return p;
	    // }

	    // private BSTNode<T> find_min(BSTNode<T> p) {
	    // if (p == null)
	    // return null;

	    // while (p.left != null) {
	    // p = p.left;
	    // }

	    // return p;
	    // }

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

	//-------------------------------
//	public <T extends Date> boolean checkDuplicates(BSTNode<T> node, Set<T> seenData, T targetData) {
//	    
//	    if (node == null) {
//	        return false;
//	    }
//
//	    // Check if current node's data is the target data
//	    if (node.data.equals(targetData)) {
//	        
//	        return true;
//	    }
//
//	    // Add current node's data to the seenData set
//	    seenData.add(node.data);
//
//	    // Check left and right subtrees recursively
//	    boolean leftHasDuplicates = checkDuplicates(node.left, seenData, targetData);
//	    boolean rightHasDuplicates = checkDuplicates(node.right, seenData, targetData);
//
//	    // Return true if any subtree has duplicates
//	    return leftHasDuplicates || rightHasDuplicates;
//	}
//	 
//
//
//
//	}
//
}
