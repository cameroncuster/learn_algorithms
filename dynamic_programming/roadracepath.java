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

// roadrace class
class roadracepath {

	// main func
	public static void main(String args[]) {

		// take input
		Scanner cin = new Scanner(System.in);
		int n = cin.nextInt();
		int L = cin.nextInt();
		int d[] = new int[n+1];
		for (int i = 1; i <= n; i++)
			d[i] = cin.nextInt();

		// take the s matrix
		int s[][] = new int[n+1][L];
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j < L; j++) {
				s[i][j] = cin.nextInt();
			}
		}

		// solve linear recc.
		double dp[][] = new double[n+1][L];
		for (int i = 1; i <= n; i++)
			Arrays.fill(dp[i], 1e18);
		Arrays.fill(dp[0], -5);
		for (int i = 1; i <= n; i++) {
			// memo on the lane {dp[i] = min time ending seg[i], in lane[j]}
			for (int j = 0; j < L; j++) {
				for (int k = 0; k < L; k++) {
					dp[i][k] = Double.min(dp[i][k], dp[i-1][j] + 3.6 * d[i] / s[i][k] + (k - j) * (k - j) + 5);
				}
			}
		}

		// get the answer
		double ans = Arrays.stream(dp[n]).min().getAsDouble();

		// solve linear recc.
		boolean is[][] = new boolean[n+1][L];
		for (int i = 0; i < L; i++) {
			if (dp[n][i] == ans) {
				is[n][i] = true;
			}
		}

		// figure out what is possible
		for (int i = n - 1; i >= 1; i--) {
			for (int k = 0; k < L; k++) {
				if (!is[i+1][k]) continue;
				for (int j = 0; j < L; j++) {
					if (dp[i+1][k] == dp[i][j] + 3.6 * d[i+1] / s[i+1][k] + (k - j) * (k - j) + 5) {
						is[i][j] = true;
					}
				}
			}
		}

		// let's take the lexicographically smallest path
		int path[] = new int[n+1];
		for (int i = 0; i < L; i++) {
			if (is[1][i]) {
				path[1] = i;
				break;
			}
		}

		// let's take the earliest (lex smallest thing we can each time) - as long as it's possible
		for (int i = 2; i <= n; i++) {
			int j = path[i-1];
			for (int k = 0; k < L; k++) {
				if (!is[i][k]) continue;
				if (dp[i][k] == dp[i-1][j] + 3.6 * d[i] / s[i][k] + (k - j) * (k - j) + 5) {
					path[i] = k;
					break;
				}
			}
		}

		// let's throw the answer at the terminal!
		for (int i = 1; i <= n; i++)
			System.out.print(path[i] + 1 + " ");
		System.out.println();
	}
}
