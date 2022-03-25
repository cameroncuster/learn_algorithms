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

import java.util.*;

// prog class
class sssptt {
	// edge class
	static class Edge {
		public int v, t, p, d;

		public Edge(int v, int t, int p, int d) {
			this.v = v;
			this.t = t;
			this.p = p;
			this.d = d;
		}
	}

	// pq Dijkstra class
	static class Pair implements Comparable<Pair> {
		public int u, w;

		public Pair(int u, int w) {
			this.u = u;
			this.w = w;
		}

		@Override
		public int compareTo(Pair o) {
			return w - o.w;
		}
	}

	// main prog
	public static void main(String args[]) {
		Scanner cin = new Scanner(System.in);
		int n = cin.nextInt();
		int m = cin.nextInt();
		int q = cin.nextInt();
		int s = cin.nextInt();

		// multi-test case
		while (n > 0 || m > 0 || q > 0 || s > 0) {
			ArrayList<Edge> g[] = new ArrayList[n];
			for (int i = 0; i < n; i++)
				g[i] = new ArrayList();

			// construct the graph from the input
			for (int i = 0; i < m; i++) {
				int u = cin.nextInt();
				int v = cin.nextInt();
				int t = cin.nextInt();
				int p = cin.nextInt();
				int d = cin.nextInt();

				g[u].add(new Edge(v, t, p, d));
			}

			// Dijkstra
			int d[] = new int[n];
			Arrays.fill(d, Integer.MAX_VALUE);
			d[s] = 0;
			PriorityQueue<Pair> pq = new PriorityQueue();
			pq.add(new Pair(s, 0));
			while (!pq.isEmpty()) {
				Pair p = pq.poll();

				int u = p.u;

				if (d[u] < p.w)
					continue;

				for (Edge e : g[u]) {
					// Edge modification to account for wait time
					int wait = 0;
					if (p.w <= e.t)
						wait = e.t - p.w;
					else if (e.p > 0) {
						int w = p.w - e.t;
						wait = (e.p - w % e.p) % e.p;
					} else
						continue;

					// alloc the wait time
					int w = p.w + wait + e.d;
					if (w < d[e.v]) {
						pq.add(new Pair(e.v, w));
						d[e.v] = w;
					}
				}
			}

			// read all queries
			while (q-- > 0) {
				int u = cin.nextInt();
				System.out.println(d[u] == Integer.MAX_VALUE ? "Impossible" : d[u]);
			}

			// capture for next test case
			n = cin.nextInt();
			m = cin.nextInt();
			q = cin.nextInt();
			s = cin.nextInt();
		}

		cin.close();
	}
}
