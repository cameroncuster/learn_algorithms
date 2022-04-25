import java.io.*;
import java.util.*;

// java main class
class weights {
	static final FS in = new FS();
	static final PrintWriter out = new PrintWriter(System.out);

	public static void main(String[] args) {
		// n
		int n = in.nextInt();

		// compute powers of three
		final int E = 20;
		int threes[] = new int[E];
		threes[0] = 1;
		for (int i = 1; i < E; i++)
			threes[i] = threes[i - 1] + threes[i - 1] + threes[i - 1];

		// solve test cases
		for (int t = 0; t < n; t++) {

			// construct the value in base 3
			int x = in.nextInt();
			int freq[] = new int[E];
			for (int i = E - 1; i >= 0; i--) {
				while (x >= threes[i]) {
					x -= threes[i];
					freq[i]++;
				}
			}

			// deconstruct the value such that negatives are allowed, but not base digit 2's
			for (int i = 0; i < E - 1; i++) {
				if (freq[i] == 3) {
					freq[i + 1]++;
					freq[i] = 0;
				} else if (freq[i] == 2) {
					freq[i + 1]++;
					freq[i] = -1;
				}
			}

			// grab the left and right sides for the output spec
			ArrayList<Integer> left = new ArrayList<Integer>();
			ArrayList<Integer> right = new ArrayList<Integer>();
			for (int i = E - 1; i >= 0; i--) {
				if (freq[i] < 0)
					left.add(i);
				else if (freq[i] > 0)
					right.add(i);
			}

			// output
			out.print("left pan:");
			for (Integer i : left)
				out.print(" " + threes[i]);
			out.println();

			out.print("right pan:");
			for (Integer i : right)
				out.print(" " + threes[i]);
			out.println();

			out.println();
		}

		out.close();
	}

	//  fast scanner
	public static class FS {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		String next() {
			while(!st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch(Exception e){};
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
		char nextChar() {
			return next().charAt(0);
		}
	}
}

