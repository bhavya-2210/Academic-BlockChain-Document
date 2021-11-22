import Includes.*;
import java.util.*;

public class BlockChain{
	public static final String  start_string = "LabModule5";
	public Block firstblock;
	public Block lastblock;

	public String InsertBlock(List<Pair<String,Integer>> Documents, int inputyear){
		CRF obj = new CRF(64);
		if(firstblock == null) {
			Block B = new Block();
			B.year = inputyear;
			MerkleTree M = new MerkleTree();
			M.Build(Documents);
			B.value = M.rootnode.val + "_" + Integer.toString(M.rootnode.maxleafval);
			B.mtree = M;
			B.dgst = obj.Fn(start_string + "#" + B.value);
			B.previous = null;
			B.next = null;
			lastblock = B;
			firstblock = B;
		}
		else{
			Block B = new Block();
			B.year = inputyear;
			MerkleTree M = new MerkleTree();
			M.Build(Documents);
			B.value = M.rootnode.val + "_" + Integer.toString(M.rootnode.maxleafval);
			lastblock.next = B;
			B.previous = lastblock;
			B.next = null;
			lastblock = B;
			B.mtree = M;
			B.dgst = obj.Fn(B.previous.dgst + "#" + B.value);
		}
		return lastblock.dgst;
	}

	public Pair<List<Pair<String,String>>, List<Pair<String,String>>>
	                                   ProofofScore(int student_id, int year) {
		// assuming a block of that year is present
		Block B = firstblock;
		while (B != null) {
			if (B.year == year) {
				break;
			} else {
				B = B.next;
			}
		}
		// B is the block corresponding to the input year
		// now making the list of value and digest
		List<Pair<String, String>> V2 = new ArrayList<Pair<String, String>>();
		Block C = B;
		while (C != null) {
			String S1 = C.value;
			String S2 = C.dgst;
			Pair<String, String> P = new Pair<String, String>(S1, S2);
			V2.add(P);
			C = C.next;
		}
		// now making the list of path to root
		List<Pair<String, String>> V1 = new ArrayList<Pair<String, String>>();
		V1 = B.mtree.path(student_id);
		Pair<List<Pair<String,String>>, List<Pair<String,String>>> P1 =
				new Pair<List<Pair<String,String>>, List<Pair<String,String>>>(V1,V2);
		return P1;
	}
}
