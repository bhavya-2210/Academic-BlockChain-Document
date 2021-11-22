import Includes.*;
import java.util.*;

public class testerModified {

	public static List<Pair<String, Integer>> doc(int a) {

		List<Pair<String, Integer>> documents = new ArrayList<Pair<String, Integer>>();
		for (int i = 0; i < 64; i++) {

			documents.add(new Pair<String, Integer>("String" + i, i * a));

		}

		return documents;

	}

	public static void main(String[] args) {
		List<Pair<String, Integer>> documents = doc(1);

		System.out.println("Building Merkle Tree...");
		MerkleTree tree = new MerkleTree();

		try {

			tree.Build(documents);
			System.out.println(tree.rootnode.val);
			System.out.println();

		} catch (Exception e) {
			System.out.println("error");
		}

		System.out.println("Updating Documents...");

		try {

			for (int i = 0; i < 64; i += 2) {
				tree.UpdateDocument(i, i + 10);
			}

			System.out.println(tree.rootnode.val);
			System.out.println();

		} catch (Exception e) {
			System.out.println("error");
		}

		System.out.println("Building Block Chain...");

		BlockChain al = new BlockChain();
		try {

			for (int i = 0; i < 10; i++) {
				List<Pair<String, Integer>> dok = doc(i);
				al.InsertBlock(dok, i + 2000);
			}

			System.out.println(al.lastblock.dgst);
			System.out.println();

		} catch (Exception e) {
			System.out.println("error");
		}

		System.out.println("Checking ProofofScore...");

		try {

			CRF crf = new CRF(64);
			String a = "";

			for (int i = 0; i < 10; i++) {

				Pair<List<Pair<String, String>>, List<Pair<String, String>>> obt_path = al.ProofofScore(5, i + 2000);
				List<Pair<String, String>> nodepath = obt_path.First;
				List<Pair<String, String>> blockpath = obt_path.Second;

				String b = "";
				String c = "";
				String d = "";
				String e = "";
				for (int j = 0; j < nodepath.size(); j++) {
					b += nodepath.get(j).First;
					c += nodepath.get(j).Second;
				}
				for (int k = 0; k < blockpath.size(); k++) {
					d += blockpath.get(k).First;
					e += blockpath.get(k).Second;
				}

				a = crf.Fn(a + b + c + d + e);

			}

			System.out.println(a);
			System.out.println();

		} catch (Exception e) {
			System.out.println("error");
		}

	}

}


// output:

// Building Merkle Tree...
// 145C38A990502FACD822E800E68F2C9EB9977258982F14779818A866D44F470E

// Updating Documents...
// 727B28B9C6CA272682DDE5A6FC1C986EE54B55AF7E6C9AEA025EDAAA7C888CB5

// Building Block Chain...
// 66A1D1AEE80905D13D7369CFD069B7261B15EAF8884412B36FC313B6A2226072

// Checking ProofofScore...
// B67735BD4ED85B3AF66B8116C4D1FECED1C03E250255146AB8129CE4A991C8E3