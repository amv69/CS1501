// CS 1501 Summer 2019
// MultiWay Trie Node implemented as an external class which
// implements the TrieNodeInt InterfaceAddress.  For this
// class it is assumed that all characters in any key will
// be letters between 'a' and 'z'.

package TriePackage;

import java.util.*;
public class MTAlphaNode<V> implements TrieNodeInt<V>
{
	private static final int R = 26;	// 26 letters in
										// alphabet

    protected V val;
    protected TrieNodeInt<V> [] next;
	protected int degree;
	
	public TrieNodeInt<V> getNextNode(char c) {
		return next[c];
	}
	
	public void setNextNode(char c, TrieNodeInt<V> node) {
		if (next[c] == null) degree ++;
		next[c] = node;
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
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Iterable<TrieNodeInt<V>> children() {
		Queue<TrieNodeInt<V>> queue = new LinkedList<TrieNodeInt<V>>();
		for(int i = 0; i < this.getDegree(); i++){
			if(next[i] != null) queue.add(next[i]);
		}
		return queue;
	}

	// You must supply the methods for this class.  See TrieNodeInt.java
	// for the specifications.  See also handout MTNode.java for a
	// partial implementation.  Don't forget to include the conversion
	// constructor (passing a DLBNode<V> as an argument).
}
