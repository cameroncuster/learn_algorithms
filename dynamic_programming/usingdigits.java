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

		// init max
		int dist[][][] = new int[Y][X][Z+1];
		for (int i = 0; i < Y; i++)
			for (int j = 0; j < X; j++)
				Arrays.fill(dist[i][j], Integer.MAX_VALUE);

		// base case
		dist[0][0][0] = Character.getNumericValue(grid[0][0]);

		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				for (int k = 0; k <= Z; k++) {
					int w = Character.getNumericValue(grid[i][j]);

					// regular transitions
					if (i-1 >= 0 && dist[i-1][j][k] != Integer.MAX_VALUE && dist[i-1][j][k] + w < dist[i][j][k])
						dist[i][j][k] = dist[i-1][j][k] + w;

					if (j-1 >= 0 && dist[i][j-1][k] != Integer.MAX_VALUE && dist[i][j-1][k] + w < dist[i][j][k])
						dist[i][j][k] = dist[i][j-1][k] + w;

					if (k == Z) continue;

					// use a digit transitions
					int t = Character.getNumericValue(key[k])+1;

					if (j-t >= 0 && dist[i][j-t][k] != Integer.MAX_VALUE && dist[i][j-t][k] + w < dist[i][j][k+1])
						dist[i][j][k+1] = dist[i][j-t][k] + w;

					if (i-t >= 0 && dist[i-t][j][k] != Integer.MAX_VALUE && dist[i-t][j][k] + w < dist[i][j][k+1])
						dist[i][j][k+1] = dist[i-t][j][k] + w;
				}
			}
		}

		// get the answer
		System.out.println(Arrays.stream(dist[Y-1][X-1]).min().getAsInt());
	}
}
