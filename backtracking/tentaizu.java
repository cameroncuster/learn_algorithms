import java.io.*;
import java.util.*;

class tentaizu {
	public static final int N = 7;
	public static char board[][];

	// counts the number of bombs surrounding (r, c)
	public static int around(int r, int c) {
		int around = 0;
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if (r + i >= 0 && r + i < N && c + j >= 0 && c + j < N &&
						board[r + i][c + j] == '*')
					around++;
		return around;
	}

	// checks that the count of the bombs surrounding (r, c) equals the board's value
	public static boolean check(int r, int c) {
		return Character.getNumericValue(board[r][c]) == around(r, c);
	}

	// recursive backtracker
	public static void solve(int r, int c, int stars) throws Exception {
		if (c == N) {
			// check last column
			if (r > 0 && Character.isDigit(board[r - 1][c - 1]) && !check(r - 1, c - 1))
				return;

			c = 0;
			r++;
		}

		if (r == N) {
			if (stars != 10)
				return;

			// check last row
			for (c = 0; c < N; c++)
				if (Character.isDigit(board[r - 1][c]) && !check(r - 1, c))
					return;

			throw new Exception();
		}

		// this is a value so don't do anything and move on
		if (Character.isDigit(board[r][c])) {
			if (r == 0 || c == 0 || !Character.isDigit(board[r - 1][c - 1]) || check(r - 1, c - 1))
				solve(r, c + 1, stars);
			return;
		}

		if (r == 0 || c == 0 || !Character.isDigit(board[r - 1][c - 1])) {
			// try not placing a bomb
			solve(r, c + 1, stars);

			// try placing a bomb
			board[r][c] = '*';
			solve(r, c + 1, stars + 1);
			board[r][c] = '.';
			return;
		}

		assert(Character.isDigit(board[r - 1][c - 1]));

		if (check(r - 1, c - 1)) {
			// we can't place a bomb so move on
			solve(r, c + 1, stars);
			return;
		}

		if (around(r - 1, c - 1) == Character.getNumericValue(board[r - 1][c - 1]) - 1) {
			// we have to place a bomb here
			board[r][c] = '*';
			solve(r, c + 1, stars + 1);
			board[r][c] = '.';
			return;
		}
	}

	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);

		// solve all test cases
		int t = stdin.nextInt();
		for (int tt = 1; tt <= t; tt++) {
			board = new char[N][N];

			// capture the board
			for (int i = 0; i < N; i++) {
				char[] line = stdin.next().toCharArray();
				for (int j = 0; j < N; j++)
					board[i][j] = line[j];
			}

			// throw exception to break out of our stack when we find the solution (guaranteed to exist)
			try {
				// solve starting at (0, 0)
				solve(0, 0, 0);
			} catch (Exception e) {
				// out the solution we found
				System.out.println("Tentaizu Board #" + tt + ":");
				for (int i = 0; i < N; i++)
					System.out.println(new String(board[i]));
				System.out.println();
			}
		}
	}
}
