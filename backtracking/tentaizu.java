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

class tentaizu {
	public static int board[][];

	public static void solve(int r, int c) throws Exception {
		if (c == board.length) {
			// check last column
			if (r > 0 && board[r - 1][c - 1] >= 0) {
				int around = 0;
				r--;
				c--;
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (r + i >= 0 && r + i < 7 && c + j >= 0 && c + j < 7 && board[r + i][c + j] == -2) {
							around++;
						}
					}
				}
				r++;
				c++;

				if (around != board[r - 1][c - 1])
					return;
			}

			c = 0;
			r++;
		}

		if (r == board.length) {
			// check last row
			r--;
			for (c = 0; c < board[r].length; c++) {
				if (board[r][c] >= 0) {
					int around = 0;
					for (int i = -1; i <= 1; i++) {
						for (int j = -1; j <= 1; j++) {
							if (r + i >= 0 && r + i < 7 && c + j >= 0 && c + j < 7 && board[r + i][c + j] == -2) {
								around++;
							}
						}
					}

					if (around != board[r][c])
						return;
				}
			}

			throw new Exception();
		}

		// let's see if we have to place a bomb (or can't place one)
		if (r > 0 && c > 0 && board[r - 1][c - 1] >= 0) {
			int around = 0;
			r--;
			c--;
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (r + i >= 0 && r + i < 7 && c + j >= 0 && c + j < 7 && board[r + i][c + j] == -2) {
						around++;
					}
				}
			}
			r++;
			c++;

			if (around == board[r - 1][c - 1]) {
				// we can't place a bomb at (r, c)
				solve(r, c + 1);
			} else if (around == board[r - 1][c - 1] - 1 && board[r][c] == -1) {
				// we have to place a bomb at (r, c)
				board[r][c] = -2;
				solve(r, c + 1);
				board[r][c] = -1;
			}
			// just try it
		} else {
			// try not placing a bomb
			solve(r, c + 1);

			// try placing a bomb
			if (board[r][c] == -1) {
				board[r][c] = -2;
				solve(r, c + 1);
				board[r][c] = -1;
			}
		}
	}

	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		int t = stdin.nextInt();
		for (int tt = 1; tt <= t; tt++) {
			board = new int[7][7];
			for (int i = 0; i < 7; i++) {
				String line = stdin.next();
				for (int j = 0; j < 7; j++) {
					char ch = line.charAt(j);
					if (ch == '.')
						board[i][j] = -1;
					else
						board[i][j] = ch - '0';
				}
			}
			try {
				solve(0, 0);
			} catch (Exception e) {
				System.out.println("Tentaizu Board #" + tt + ":");
				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						if (board[i][j] == -1)
							System.out.print('.');
						else if (board[i][j] == -2)
							System.out.print('*');
						else
							System.out.print(board[i][j]);
					}
					System.out.println();
				}
				System.out.println();
			}
		}
	}
}
