package avl;

import java.util.LinkedList;

public class AVLTree<T extends Comparable<T>> {

	/* For the AVL Lab, find all 'FIXME's and
	 * supply the code asked for.
	 */

	private TreeNode<T> root;
	public int size;
	
	public AVLTree() {
	    this.root = null;
	    this.size = 0;
	}
	
	////////////////////////////////////////////////////////
	
	//
	// exists()
	// Check whether a specified value exists in the set
	//
	public boolean exists(T value) {
	    return existsHelper(value, this.root);
	}
	
	//
	// existsHelper()
	// (Optionally) recursive procedure to traverse a tree
	// rooted at "root" to find a specified value.  
	//
	// RETURNS: true if the value is found, else false
	//
	private boolean existsHelper(T value, TreeNode<T> root) {
		if (root == null) { // not found
			return false;
	    } else {
	    	int comparison = value.compareTo(root.value);
		
	    	if (comparison == 0) { // found
	    		return true;
	    	} else if (comparison < 0) { // still looking - go left
	    		return existsHelper(value, root.left);
	    	} else { // still looking - go right
	    		return existsHelper(value, root.right);
	    	}
	    }
	}
	
	////////////////////////////////////////////////////////
	
	//
	// min()
	// Return the minimum value in the set
	//
	// If the set is empty, result is undefined.
	//
	public T min() {
	    return minValueInSubtree(this.root);
	}
	
	//
	// minValueInSubTree()
	// Find the smallest value in the subtree rooted at
	// the specified node.
	//
	// ASSUMED: root is not null.
	//
	private T minValueInSubtree(TreeNode<T> root) {
	    while (root.left != null)
	    	root = root.left;
	    
	    return root.value;
	}

	//
	// max()
	// Return the maximum value in the set
	//
	// If the set is empty, result is undefined.
	//
	
	public T max() {
	    return maxValueInSubtree(this.root);
	}


	//
	// maxValueInSubTree()
	// Find the largest value in the subtree rooted at
	// the specified node.
	//
	// ASSUMED: root is not null.
	//
	private T maxValueInSubtree(TreeNode<T> root) {
	    while (root.right != null)
	    	root = root.right;
	    
	    return root.value;
	}
	
	////////////////////////////////////////////////////////
	
	//
	// insert()
	// Insert the specified value in the set if it does not
	// already exist.
	//
	// RETURNS: the size of the set after insertion.
	//
	public int insert(T value) {
	    this.root = insertHelper(value, this.root);
	    return size;
	}
	
	//
	// insertHelper()
	// Recursive procedure to insert a value into the subtree
	// rooted at "root".  If value is already present in the
	// tree, nothing is inserted.
	//
	// RETURNS: root node of subtree after insertion
	//
	// FIXME: add the necessary code to this function
	// to maintain height and rebalance the tree when
	// a node is inserted.
	//
	private TreeNode<T> insertHelper(T value,
					 TreeNode<T> root) {
		if (root == null) {
			// add new element as leaf of tree
			TreeNode<T> newNode = new TreeNode<T>(value);
			updateHeight(newNode);
			size++;
			return newNode;
	    } 
		else {
	    	int comparison = value.compareTo(root.value);
		
	    	if (comparison == 0) {
	    		// duplicate element -- return existing node
	    		return root;
	    	} else if (comparison < 0) {
	    		// still looking -- go left
	    		root.setLeft(insertHelper(value, root.left));
	    	} else {
	    		// still looking -- go right
	    		root.setRight(insertHelper(value, root.right));
	    	}
	    	root = rebalance(root);
	    	updateHeight(root.right);
	    	updateHeight(root.left);
	    	updateHeight(root);
	    
	    	return root;
	    }
	}

	////////////////////////////////////////////////////////
	
	//
	// remove()
	// Remove a value from the set if it is present
	//
	public void remove(T value) {
	    this.root = removeHelper(value, this.root);
	}
	
	//
	// removeHelper()
	// Recursive procedure to remove a value from the
	// subtree rooted at "root", if it exists.
	//
	// RETURNS root node of subtree after insertion
	//
	// FIXME: add the necessary code to this function
	// to maintain height and rebalance the tree when
	// a node is removed.
	//
	private TreeNode<T> removeHelper(T value,
					 TreeNode<T> root) {
	    
	    if (root == null) { // did not find element
	    	return null;
	    } 
	    else {
	    	int comparison = value.compareTo(root.value);
		
	    	if (comparison == 0) { // found element to remove
	    		if (root.left == null || root.right == null) {
	    			// base case -- root has at most one subtree,
	    			// so return whichever one is not null (or null
	    			// if both are)
	    			size--;
	    			return (root.left == null ? root.right : root.left);
	    		} else {
	    			// node with two subtrees -- replace key
	    			// with successor and recursively remove
	    			// the successor.
	    			T minValue = minValueInSubtree(root.right);
	    			root.value = minValue;
			
	    			root.setRight(removeHelper(minValue, root.right));
	    		}
	    	} else if (comparison < 0) {
	    		// still looking for element to remove -- go left
	    		root.setLeft(removeHelper(value, root.left));
	    	} else {
	    		// still looking for element to remove -- go right
	    		root.setRight(removeHelper(value, root.right));
	    	}
	    	root = rebalance(root);
	    	updateHeight(root.right);
	    	updateHeight(root.left);
	    	updateHeight(root);
	    	
	    	return root;
	    }
	}

	
	////////////////////////////////////////////////////////
	//
	// INTERNAL METHODS FOR MAINTAINING BALANCE
	//
	
	// updateHeight()
	//
	// Recompute the height of the subtree rooted at "root",
	// assuming that its left and right children (if any)
	// have correct heights for their respective subtrees.
	//
	// EFFECT: Set the root's height field to the updated value
	//
	private void updateHeight(TreeNode<T> root) {
	    // FIXME: fill in the update code
		if (root == null) {
			return;
		}
		int leftHeight = -1;
		if (root.left != null) {
			leftHeight = root.left.height;
		}
		int rightHeight = -1;
		if (root.right != null) {
			rightHeight = root.right.height;
		}
		root.height = Math.max(leftHeight, rightHeight) +1;
	}

	//
	// getBalance()
	// Return the balance factor of a subtree rooted at "root"
	// (left subtree height - right subtree height)
	// *ASSUME HEIGHTS ARE UPDATED BEFORE CALL*
	private int getBalance(TreeNode<T> root) {
	    // FIXME: fill in the balance computation
		if (root == null) {
			// System.out.println("ERROR: root does not exist");
			return -1;
		}
		int leftHeight = -1;
		if (root.left != null) {
			leftHeight = root.left.height;
		}
		int rightHeight = -1;
		if (root.right != null) {
			rightHeight = root.right.height;
		}
		return (leftHeight - rightHeight);
	}

	//
	// rebalance()
	//
	// Rebalance an AVL subtree, rooted at "root", that has possibly
	// been unbalanced by a single node's insertion or deletion.
	//
	// RETURNS: the root of the subtree after rebalancing
	//
	private TreeNode<T> rebalance(TreeNode<T> root) {
	    // FIXME: fill in the rebalancing code
	    if (root == null) {
	    	return null;
	    }
		updateHeight(root);
	    int balanceFactor = getBalance(root);
	    int lilBFactor;
	    if (balanceFactor == -2) {
	    	lilBFactor = getBalance(root.right);
	    	if (lilBFactor == 1) { //right-left case, DOUBLE ROTATE
	    		rightRotate(root.right);
	    	}
	    	return leftRotate(root);
	    }
	    else if (balanceFactor == 2) {
	    	lilBFactor = getBalance(root.left);
	    	if (lilBFactor == -1) { //left-right case, DOUBLE ROTATE
	    		leftRotate(root.left);
	    	}
	    	return rightRotate(root);
	    }
		
		return root;
	}
	
	//
	// rightRotate() (Clockwise)
	// Perform a right rotation on a tree rooted at "root"
	// The tree's root is assumed to have a left child.
    //
	//    Tree Before:                Tree After:
	//         c           (root)          b
	//       /   \                       /   \
	//      b     t4                   a       c
	//     / \                       /  \     /  \
	//   a    t3                    t1  t2   t3  t4
 	//  / \
    // t1  t2
	//
 	// RETURNS: the new root after rotation.
	//
	private TreeNode<T> rightRotate(TreeNode<T> root) {
	    // FIXME: fill in the rotation code
		if (root == null) {
			return null;
		}
	    TreeNode<T> leftRightChild = root.left.right;
	    TreeNode<T> rootParent = root.parent;
	    TreeNode<T> rootLeftChild = root.left;
	    if (root.parent != null) {
	    	if (rootParent.right != null &&
	    			root.value.compareTo(rootParent.right.value) == 0) {
	    		rootParent.setRight(rootLeftChild);
	    	}
	    	else {
	    		rootParent.setLeft(rootLeftChild);
	    	}
//	    	rootParent.left = rootLeftChild;
//	    	rootLeftChild.parent = rootParent;
	    }
	    else { //node is root
	    	this.root = rootLeftChild;
	    	rootLeftChild.parent = null;
	    }
	    root.setLeft(leftRightChild);
	    rootLeftChild.setRight(root);
//	    root.left = leftRightChild;
//	    leftRightChild.parent = root;
//	    rootLeftChild.right = root;
//	    root.parent = rootLeftChild;
//	    
//	    root.height--;
//	    TreeNode<T> rootRightChild = root.right;
//	    while (rootRightChild != null) {
//	    	rootRightChild.height--;
//	    	rootRightChild = 
//	    }
//	    updateHeight(rootParent);
		
		return rootLeftChild;
	}

	//
	// leftRotate()
	// Perform a left rotation on a tree rooted at "root"
	// The tree's root is assumed to have a right child.
	//   
	//    Tree Before:                Tree After:
	//                     (root)          
	//       /   \                       /   \
	//      ?     ?                    ?       ?
	//    ...     ...                 /  \     /  \
	//                               ?    ?    ?  ?
 	//  
    // 
	//
	// RETURNS: the new root after rotation.
	//
	private TreeNode<T> leftRotate(TreeNode<T> root) {
	    // FIXME: fill in the rotation code
		if (root == null) {
			return null;
		}
	    TreeNode<T> rightLeftChild = root.right.left;
	    TreeNode<T> rootParent = root.parent;
	    TreeNode<T> rootRightChild = root.right;
	    if (rootParent != null) {
	    	if (rootParent.right !=null && 
	    			root.value.compareTo(rootParent.right.value) == 0) {
	    		rootParent.setRight(rootRightChild);
	    	}
	    	else {
	    		rootParent.setLeft(rootRightChild);
	    	}
//	    	rootParent.right = rootRightChild;
//	    	rootRightChild.parent = rootParent;
	    }
	    else { //node is root
	    	this.root = rootRightChild;
	    	rootRightChild.parent = null;
	    }
	    root.setRight(rightLeftChild);
	    rootRightChild.setLeft(root);
//	    root.right = rightLeftChild;
//	    rightLeftChild.parent = root;
//	    rootRightChild.left = root;
//	    root.parent = rootRightChild;
		
		return rootRightChild;
	}
	
	/////////////////////////////////////////////////////////////
	//
	// METHODS USED TO VALIDATE CORRECTNESS OF TREE
	// (You should not have to touch these)
	//

	//
	// getRoot()
	// Return the root node of the tree (for validation only!)
	//
	public TreeNode<T> getRoot() {
	    return this.root;
	}
	
		
	//
	// enumerate()
	// Return the contents of the tree as an ordered list
	//
	public LinkedList<T> enumerate() {
	    return enumerateHelper(this.root);
	}
	
	//
	// enumerateHelper()
	// Enumerate the contents of the tree rooted at "root" in order
	// as a linked list
	//
	private LinkedList<T> enumerateHelper(TreeNode<T> root) {
	    if (root == null) {
		    return new LinkedList<T>();
		}
	    else {
		    LinkedList<T> list = enumerateHelper(root.left);
		    list.addLast(root.value);
		    list.addAll(enumerateHelper(root.right));
		    
		    return list;
		}
	}
	
//	public static void main(String[] args) {
//		AVLTree<Integer> tree = new AVLTree<Integer>();
//		tree.insert(8);
//		tree.insert(6);
//		tree.insert(12);
//		tree.insert(5);
//		tree.insert(7);
//		tree.insert(10);
//		tree.insert(13);
//		tree.insert(2);
//		tree.insert(9);
//		LinkedList<Integer> list = tree.enumerate();
//		System.out.println(list);
//		tree.insert(3);
//		list = tree.enumerate();
//		System.out.println(list);
//		
//	}
}
