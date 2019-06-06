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

	@Override
	public TrieNodeInt<V> getNextNode(char c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNextNode(char c, TrieNodeInt<V> node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(V data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDegree() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<TrieNodeInt<V>> children() {
		// TODO Auto-generated method stub
		return null;
	}
    		
	// You must supply the methods for this class.  See TrieNodeInt.java for the
	// interface specifications.  You will also need a constructor or two.
}
