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

// main class
class usingdigits {

	// main func
	public static void main(String args[]) {

		// take input
		Scanner cin = new Scanner(System.in);

		int X = cin.nextInt();
		int Y = cin.nextInt();

		char key[] = cin.next().toCharArray();

		int Z = key.length;

		char grid[][] = new char[Y][];
		for (int i = Y-1; i >= 0; i--)
			grid[i] = cin.next().toCharArray();

		// pq of states
		PriorityQueue<State> pq = new PriorityQueue();

		// init max
		int dist[][][] = new int[Y][X][Z+1];
		for (int i = 0; i < Y; i++)
			for (int j = 0; j < X; j++)
				Arrays.fill(dist[i][j], Integer.MAX_VALUE);

		// source
		dist[0][0][0] = Character.getNumericValue(grid[0][0]);
		pq.add(new State(0, 0, 0, dist[0][0][0]));

		// search
		while (!pq.isEmpty()) {
			State p = pq.poll();

			int i = p.i;
			int j = p.j;
			int k = p.k;

			// ignore
			if (p.w > dist[i][j][k])
				continue;

			// regular transitions
			if (j+1 < X) {
				int w = Character.getNumericValue(grid[i][j+1]);
				if (dist[i][j][k] + w < dist[i][j+1][k]) {
					dist[i][j+1][k] = dist[i][j][k] + w;
					pq.add(new State(i, j+1, k, dist[i][j+1][k]));
				}
			}

			if (i+1 < Y) {
				int w = Character.getNumericValue(grid[i+1][j]);
				if (dist[i][j][k] + w < dist[i+1][j][k]) {
					dist[i+1][j][k] = dist[i][j][k] + w;
					pq.add(new State(i+1, j, k, dist[i+1][j][k]));
				}
			}

			// continue if no digits are remaining
			if (k >= Z) continue;

			// use a digit transitions
			int t = Character.getNumericValue(key[k])+1;
			if (j+t < X) {
				int w = Character.getNumericValue(grid[i][j+t]);
				if (dist[i][j][k] + w < dist[i][j+t][k+1]) {
					dist[i][j+t][k+1] = dist[i][j][k] + w;
					pq.add(new State(i, j+t, k+1, dist[i][j+t][k+1]));
				}
			}

			if (i+t < Y) {
				int w = Character.getNumericValue(grid[i+t][j]);
				if (dist[i][j][k] + w < dist[i+t][j][k+1]) {
					dist[i+t][j][k+1] = dist[i][j][k] + w;
					pq.add(new State(i+t, j, k+1, dist[i+t][j][k+1]));
				}
			}
		}

		// result
		int ans = Integer.MAX_VALUE;
		for (int k = 0; k <= Z; k++)
			ans = Integer.min(ans, dist[Y-1][X-1][k]);

		// output
		System.out.println(ans);
	}

	// state class
	static class State implements Comparable<State> {
		public int i;
		public int j;
		public int k;
		public int w;

		// constructor
		public State(int i, int j, int k, int w) {
			this.i = i;
			this.j = j;
			this.k = k;
			this.w = w;
		}

		// compare
		public int compareTo(State o) {
			return w-o.w;
		}
	}
}
