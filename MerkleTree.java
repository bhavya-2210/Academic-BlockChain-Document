import Includes.*;
import java.util.*;
import java.lang.Math;
public class MerkleTree{
	// Check the TreeNode.java file for more details
	public TreeNode rootnode;
	public int numstudents;

	public String Build(List<Pair<String,Integer>> documents){

		CRF obj = new CRF(64);
		int n = documents.size();
		numstudents = n;
		ArrayList<TreeNode> V = new ArrayList<TreeNode>();
		for(int i = 0; i < n;i++){
			TreeNode T = new TreeNode();
			T.parent = null;
			T.left = null;
			T.right = null;
			T.val = documents.get(i).get_first() + "_" + Integer.toString(documents.get(i).get_second());
			T.isLeaf = true;
			T.numberLeaves = 1;
			T.maxleafval = documents.get(i).get_second();
			V.add(T);
		}
		n = n/2;
		while( n != 0){
			ArrayList<TreeNode> V1 = new ArrayList<TreeNode>();
			for(int i = 0; i < n;i++){
				TreeNode T = new TreeNode();
				T.parent = null;
				T.left = V.get(2*i);
				T.right = V.get(2*i + 1);
				T.left.parent = T;
				T.right.parent = T;
				T.val = obj.Fn(T.left.val + "#" + T.right.val);
				T.isLeaf = false;
				T.numberLeaves = T.left.numberLeaves + T.right.numberLeaves;
				T.maxleafval = Math.max(T.left.maxleafval,T.right.maxleafval);
				V1.add(T);
			}
			V = V1;
			n = n/2;
		}
		rootnode = V.get(0);
		return rootnode.val;
	}

	/*
		Pair is a generic data structure defined in the Pair.java file
		It has two attributes - First and Second
	public void update(TreeNode N){
		CRF obj = new CRF(64);
		if(N.parent != null){
			N.parent.val = obj.Fn(N.parent.left.val +"#" +N.parent.right.val);
			N.parent.maxleafval = Math.max(N.parent.left.maxleafval,N.parent.right.maxleafval);
			update(N.parent);
		}
	}

	 */
	public String UpdateDocument(int student_id, int newScore){
		// Implement Code here
		CRF obj = new CRF(64);
		if(rootnode.left == null){
			int i = rootnode.val.indexOf("_");
			rootnode.val = (rootnode.val.substring(0,i) + "_" + Integer.toString(newScore) );
			rootnode.maxleafval = newScore;
			return rootnode.val;
		}
		if(student_id < numstudents/2){
			MerkleTree l = new MerkleTree();
			l.rootnode = rootnode.left;
			l.rootnode.parent = null;
			l.numstudents = (numstudents)/2;
			l.UpdateDocument(student_id,newScore);
			rootnode.left = l.rootnode;
			rootnode.left.parent = rootnode;
			rootnode.val = obj.Fn(rootnode.left.val +"#" +rootnode.right.val);
			rootnode.maxleafval = Math.max(rootnode.left.maxleafval,rootnode.right.maxleafval);
			return rootnode.val;
		}
		else{
			MerkleTree r = new MerkleTree();
			r.rootnode = rootnode.right;
			r.rootnode.parent =null;
			r.numstudents = (numstudents)/2;
			r.UpdateDocument(student_id - (numstudents/2), newScore);
			rootnode.right = r.rootnode;
			rootnode.right.parent = rootnode;
			rootnode.val = obj.Fn(rootnode.left.val +"#" +rootnode.right.val);
			rootnode.maxleafval = Math.max(rootnode.left.maxleafval,rootnode.right.maxleafval);
			return rootnode.val;
		}
	}
	public List<Pair<String,String>> path(int id){
		if (rootnode.left == null) {
			ArrayList<Pair<String,String>> V = new ArrayList<Pair<String,String>>();
			Pair<String,String> P = new Pair<String,String>(rootnode.parent.left.val,rootnode.parent.right.val);
			V.add(P);
			return V;
		}
		else{
			if(id < numstudents/2){
				MerkleTree l = new MerkleTree();
				l.rootnode = rootnode.left;
				l.numstudents = (numstudents)/2;
				List<Pair<String,String>> V = l.path(id);
				if(rootnode.parent == null ){
					Pair<String,String> P = new Pair<String,String>(rootnode.val,null);
					V.add(P);
					return V;
				}
				else{
					Pair<String,String> P =
							new Pair<String,String>(rootnode.parent.left.val,rootnode.parent.right.val);
					V.add(P);
					return V;
				}
			}
			else{
				MerkleTree r = new MerkleTree();
				r.rootnode = rootnode.right;
				r.numstudents = (numstudents)/2;
				List<Pair<String,String>> V = r.path(id - numstudents/2);
				if(rootnode.parent == null ){
					Pair<String,String> P = new Pair<String,String>(rootnode.val,null);
					V.add(P);
					return V;
				}
				else{
					Pair<String,String> P =
							new Pair<String,String>(rootnode.parent.left.val,rootnode.parent.right.val);
					V.add(P);
					return V;
				}
			}

		}
	}
}
