import java.io.*;
import java.util.*;

// roadrace class
class roadrace {

	// main func
	public static void main(String args[]) {

		// take input
		Scanner cin = new Scanner(System.in);
		int n = cin.nextInt();
		int L = cin.nextInt();
		int d[] = new int[n];
		for (int i = 0; i < n; i++)
			d[i] = cin.nextInt();

		// solve linear recc.
		double dp[] = new double[L];
		Arrays.fill(dp, -5);
		for (int i = 0; i < n; i++) {
			int s[] = new int[L];
			for (int j = 0; j < L; j++) {
				s[j] = cin.nextInt();
			}

			// memo on the lane {dp[i] = min time ending seg[i], in lane[j]}
			double to[] = new double[L];
			Arrays.fill(to, 1e18);
			for (int j = 0; j < L; j++) {
				for (int k = 0; k < L; k++) {
					to[k] = Double.min(to[k], dp[j] + 3.6 * d[i] / s[k] + (k - j) * (k - j) + 5);
				}
			}

			// clone
			dp = to.clone();
		}

		// c-style format output
		System.out.format("%.2f\n", Arrays.stream(dp).min().getAsDouble());
	}
}
