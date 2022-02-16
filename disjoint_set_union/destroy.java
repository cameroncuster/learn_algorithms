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

// main program class
class destroy {
	// the main function
	public static void main(String args[]) {
		FastScanner stdin = new FastScanner();

		// capture meta data
		int n = stdin.nextInt();
		int m = stdin.nextInt();
		int k = stdin.nextInt();

		// capture meta graph edges
		int edges[][] = new int[m][2];
		for (int i = 0; i < m; i++) {
			edges[i][0] = stdin.nextInt() - 1;
			edges[i][1] = stdin.nextInt() - 1;
		}

		// capture the destroyed edges
		int destroy[] = new int[k];
		boolean destroyed[] = new boolean[m];
		for (int i = 0; i < k; i++) {
			destroy[i] = stdin.nextInt() - 1;
			destroyed[destroy[i]] = true;
		}

		// add all the edges which are never destroyed
		DSU dsu = new DSU(n);
		for (int i = 0; i < m; i++)
			if (!destroyed[i])
				dsu.union(edges[i][0], edges[i][1]);

		long connectivity[] = new long[k + 1];

		// initial connectivity
		connectivity[k] = dsu.sumOfSquares;

		// handle the edges which are removed by simulating adding edges in reverse order
		for (int i = k - 1; i >= 0; i--) {
			int edge = destroy[i];
			dsu.union(edges[edge][0], edges[edge][1]);
			connectivity[i] = dsu.sumOfSquares;
		}

		// buffered output stream
		PrintWriter out = new PrintWriter(System.out);

		// output the answer to the queries
		for (int i = 0; i <= k; i++)
			out.println(connectivity[i]);
		out.close();
	}

	// disjoint set union object
	static class DSU {
		// parent array
		int p[];
		public long sumOfSquares;

		// construct the DSU
		public DSU(int n) {
			p = new int[n];
			Arrays.fill(p, -1);

			// sum of 1^2 [0, n) = n
			sumOfSquares = n;
		}

		// find the root
		int find(int i) {
			// we found the root
			if (p[i] < 0)
				return i;

			// point all nodes on the path to the root (path compression)
			return p[i] = find(p[i]);
		}

		// the size is stored in the parent array at the root (negated)
		int size(int i) {
			return -p[find(i)];
		}

		// union components i and j
		void union(int i, int j) {
			// same component?
			if ((i = find(i)) == (j = find(j)))
				return;

			// make i bigger set
			if (p[i] > p[j]) {
				int temp = i;
				i = j;
				j = temp;
			}

			// remove old comp sizes squared
			long a = size(i);
			long b = size(j);
			sumOfSquares -= a * a;
			sumOfSquares -= b * b;

			// merge j into i
			p[i] += p[j];
			p[j] = i;

			// add new comp size squared
			long c = size(i);
			sumOfSquares += c * c;
		}
	}

	// buffered IO for speed
	static class FastScanner {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");

		String next() {
			while (!st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (Exception e) { };
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}
	}
}
