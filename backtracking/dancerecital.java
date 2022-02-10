/*
 *  ██████  █████  ███    ███ ███████ ██████   ██████  ███    ██      ██████ ██    ██ ███████ ████████ ███████ ██████
 * ██      ██   ██ ████  ████ ██      ██   ██ ██    ██ ████   ██     ██      ██    ██ ██         ██    ██      ██   ██
 * ██      ███████ ██ ████ ██ █████   ██████  ██    ██ ██ ██  ██     ██      ██    ██ ███████    ██    █████   ██████
 * ██      ██   ██ ██  ██  ██ ██      ██   ██ ██    ██ ██  ██ ██     ██      ██    ██      ██    ██    ██      ██   ██
 *  ██████ ██   ██ ██      ██ ███████ ██   ██  ██████  ██   ████      ██████  ██████  ███████    ██    ███████ ██   ██
 *
 * ██    ██  ██████ ███████
 * ██    ██ ██      ██
 * ██    ██ ██      █████
 * ██    ██ ██      ██
 *  ██████   ██████ ██
 *
 *  ██████ ███████ ██ ██
 * ██      ██      ██ ██
 * ██      ███████ ██ ██
 * ██           ██ ██ ██
 *  ██████ ███████ ██ ██
 *
 */
import java.io.*;
import java.util.*;

// solution class
class dancerecital {

	// members for the permutation code
	static int p[];
	static boolean used[];
	static int T[][];

	// recursive backtracker (tries all permutations of dance recitals)
	public static int permute(int idx) {
		int r = p.length;

		// base case (we finished a valid permutation)
		if (idx == r) {
			int ch = 0;
			for (int i = 0; i < r - 1; i++)
				ch += T[p[i]][p[i + 1]];
			return ch;
		}

		// iterations
		int ch = Integer.MAX_VALUE;
		for (int i = 0; i < r; i++) {
			if (!used[i]) {
				used[i] = true;
				p[idx] = i;

				// capture the minimum cost
				ch = Integer.min(ch, permute(idx + 1));
				used[i] = false;
			}
		}

		// return minimum cost
		return ch;
	}

	// main method
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);

		// capture input
		int r = stdin.nextInt();
		char dict[][] = new char[r][];
		for (int i = 0; i < r; i++)
			dict[i] = stdin.next().toCharArray();

		// optimize the base case to be linear in number of dance recitals O(r) by precomuting a cost matrix (T)
		T = new int[r][r];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < r; j++) {
				if (i != j) {
					boolean freq[] = new boolean[26];
					for (char ch : dict[i])
						freq[ch - 'A'] = true;
					for (char ch : dict[j])
						if (freq[ch - 'A'])
							T[i][j]++;
				}
			}
		}

		// solve
		p = new int[r];
		used = new boolean[r];
		System.out.println(permute(0));
	}
}
