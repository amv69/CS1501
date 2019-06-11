// CS 1501 Summer 2019
// DLB Trie Node implemented as an external class which
// implements the TrieNodeInt<V> Interface

package TriePackage;

import java.util.*;
public class DLBNode<V> implements TrieNodeInt<V>
{
    protected Nodelet front;
    protected int degree;
	protected V val;
	
    protected class Nodelet
    {
    	protected char cval;
    	protected Nodelet rightSib;
    	protected TrieNodeInt<V> child;
    }

	public DLBNode() {
		val = null;
		degree = 0;
		front = new Nodelet();
	}
	public DLBNode(V data) {
		val = data;
		degree = 0;
		front = new Nodelet();
	}
	public TrieNodeInt<V> getNextNode(char c) {
		Nodelet node = front;
		while(true) {
			if(node.cval == c) return front.child;
			if(node.rightSib == null) node.rightSib = new Nodelet();
			node.rightSib.cval = c;
			node = node.rightSib;
		}
	}

	
	public void setNextNode(char c, TrieNodeInt<V> node) {
		if(front.child == null) degree ++;
		front.child = node;
		front.cval = c;
	}

	
	public V getData() {
		return val;
	}

	
	public void setData(V data) {
		val = data;
	}

	
	public int getDegree() {
		return degree;
	}

	
	public int getSize() {
		return 0;
	}

	
	public Iterable<TrieNodeInt<V>> children() {
		// TODO Auto-generated method stub
		return null;
	}
    		
	// You must supply the methods for this class.  See TrieNodeInt.java for the
	// interface specifications.  You will also need a constructor or two.
}
