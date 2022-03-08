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
class maze {
	static class Edge {
		int v, w;

		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}

	// the main function
	public static void main(String args[]) {
		FastScanner stdin = new FastScanner();
		int r = stdin.nextInt();
		int c = stdin.nextInt();

		char grid[][] = new char[r][];
		for (int i = 0; i < r; i++)
			grid[i] = stdin.next().toCharArray();

		// 26 meta nodes for teleporters
		int n = r * c + 26;

		ArrayList<Edge> g[] = new ArrayList[n];
		for (int i = 0; i < n; i++)
			g[i] = new ArrayList<>();

		int s = 0, t = 0;

		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				int u = i * c + j;

				// capture the source
				if (grid[i][j] == '*')
					s = u;

				// capture the destination
				if (grid[i][j] == '$')
					t = u;

				// add undirected edges between adjacent squares with cost 1
				if (grid[i][j] != '!') {
					if (i + 1 < r && grid[i + 1][j] != '!') {
						int v = (i + 1) * c + j;
						g[u].add(new Edge(v, 1));
						g[v].add(new Edge(u, 1));
					}
					if (j + 1 < c && grid[i][j + 1] != '!') {
						int v = i * c + j + 1;
						g[u].add(new Edge(v, 1));
						g[v].add(new Edge(u, 1));
					}
				}
			}
		}

		// add the meta nodes with cost 1/0 to simulate "teleportation"
		int v = r * c;
		for (char ch = 'A'; ch <= 'Z'; ch++, v++) {
			for (int i = 0; i < r; i++) {
				for (int j = 0; j < c; j++) {
					int u = i * c + j;
					if (grid[i][j] == ch) {
						g[u].add(new Edge(v, 1));
						g[v].add(new Edge(u, 0));
					}
				}
			}
		}

		// secret 0-1 BFS technique used by Russian and Belerusian Programmers
		ArrayDeque<Integer> q = new ArrayDeque<>();
		q.add(s);
		int d[] = new int[n];
		boolean seen[] = new boolean[n];
		seen[s] = true;
		while (!q.isEmpty()) {
			int u = q.poll();

			for (Edge edge : g[u]) {
				if (!seen[edge.v]) {
					seen[edge.v] = true;
					d[edge.v] = d[u] + edge.w;
					if (edge.w == 0)
						q.addFirst(edge.v);
					else
						q.add(edge.v);
				}
			}
		}

		if (seen[t])
			System.out.println(d[t]);
		else
			System.out.println(-1);
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
