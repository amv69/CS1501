// CS 1501 Summer 2019
// HybridTrieST<V> class

package TriePackage;

import java.util.*;
import java.io.*;

public class HybridTrieST<V> {

    private TrieNodeInt<V> root;
    int treeType = 0;
    	// treeType = 0 --> multiway trie
    	// treeType = 1 --> DLB
    	// treeType = 2 --> hybrid
    public HybridTrieST(int treeType) {
    	this.treeType = treeType;
    	if(treeType == 0) root = new MTAlphaNode<V>();
    	else if(treeType == 1) root = new DLBNode<V>();
    	else if(treeType == 2) root = new DLBNode<V>();
    }
    public void put(String key, V val) {
    	System.out.println(key);
    	root = put(root, key, val, 0);
    }
    private TrieNodeInt<V> put(TrieNodeInt<V> x, String key, V val, int d) {
        if (x == null && treeType == 0) x = new MTAlphaNode<V>();
        else if (x == null && treeType == 1) x = new DLBNode<V>();
        if (d == key.length()) {
            x.setData(val);
            return x;
        }
        char c = key.charAt(d);
        x.setNextNode(c, put(x.getNextNode(c), key, val, d+1));
        return x;
    }
    
    public V get(String key){
		TrieNodeInt<V> node = get(root, key, 0);
		if (node == null) return null;
		return node.getData();
    }
    private TrieNodeInt<V> get(TrieNodeInt<V> node, String key, int d) {
        if (node == null) return null;
        if (d == key.length()) return node;
        char c = key.charAt(d);
        return get(node.getNextNode(c), key, d+1);
    }
    
    public int searchPrefix(String key){
		int ans = 0;
		TrieNodeInt<V> curr = root;
		boolean done = false;
		int loc = 0;
		while (curr != null && !done)
		{
			if (loc == key.length())
			{
				if (curr.getData() != null)
				{
					ans += 2;
				}
				if (curr.getDegree() > 0)
				{
					ans += 1;
				}
				done = true;
			}
			else
			{
				curr = curr.getNextNode(key.charAt(loc));
				loc++;
			}
		}
		return ans;
	}
    
    public int getSize() {
    	return root.getSize();
    }
    
    public int [] degreeDistribution() {
    	int [] meme = new int[25];
		return meme;
    }
    //TODO
    public void save(String fileName){
    	Iterable<TrieNodeInt<V>> queue = root.children();
		try {
			FileWriter fw = new FileWriter(new File(fileName));
			LinkedList<TrieNodeInt<V>> haha = (LinkedList<TrieNodeInt<V>>) queue;
			for(int i = 0; i < haha.size(); i++) {
				fw.write(haha.get(i).toString());
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
    public int countNodes(int n) {
    	//if (currentNode instanceof DLBNode<?>)
		return n;
    }
	
	// You must supply the methods for this class.  See test program
	// HybridTrieTest.java for details on the methods and their
	// functionality.  Also see handout TrieSTMT.java for a partial
	// implementation.
}
