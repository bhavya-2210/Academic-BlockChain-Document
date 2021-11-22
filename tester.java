import Includes.*;
import java.util.*;
import java.io.*;

public class tester{
	public static void main(String[] args){
		List<Pair<String,Integer>> documents = new ArrayList<Pair<String,Integer>> ();
		for(int i = 0; i < 64; i++){
			documents.add(new Pair <String,Integer> ("String_"+i,i));
		}
		System.out.println("Building Merkle Tree...");
		MerkleTree tree1 = new MerkleTree();

		try {
			tree1.Build(documents);
			System.out.println(tree1.rootnode.val);//F7EDA57BD63F08D92D1E8E09816F1A91FE119BA4371BCD75A5ECFB004B41B636
		} catch (Exception e){
			System.out.println("error");
		}

	}

}

