import java.io.*;
import java.util.*;

class poly {
	private int length;
	private long coeff[];
	// Creates a polynomial from the coefficients stored in vals.
	// The polynomial created must store exactly (1<<k) coefficients
	// for some integer k.
	public poly(long[] vals) {
		length = vals.length;
		coeff = Arrays.copyOf(vals, length);
	}
	// Both this and other must be of the same size and the
	// corresponding lengths must be powers of 2.
	// Returns the sum of this and other in a newly created poly.
	public poly add(poly other) {
		long[] vals = new long[length];
		for (int i = 0; i < length; i++)
			vals[i] = coeff[i] + other.coeff[i];
		return new poly(vals);
	}
	// Both this and other must be of the same size and the
	// corresponding lengths must be powers of 2.
	// Returns the difference of this and other in a new poly.
	public poly sub(poly other) {
		long[] vals = new long[length];
		for (int i = 0; i < length; i++)
			vals[i] = coeff[i] - other.coeff[i];
		return new poly(vals);
	}
	// Both this and other must be of the same size and the
	// corresponding lengths must be powers of 2.
	// Returns the product of this and other in a newly created
	// poly, using the regular nested loop algorithm, with the
	// length being the next power of 2.
	public poly multSlow(poly other) {
		long[] vals = new long[length << 1];
		for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++)
				vals[i + j] += coeff[i] * other.coeff[j];
		return new poly(vals);
	}
	// Both this and other must be of the same size and the
	// corresponding lengths must be powers of 2.
	// Returns the product of this and other in a newly created
	// poly, using Karatsuba’s algorithm, with the
	// length being the next power of 2.
	public poly mult(poly other) {
		if (length < 32)
			return multSlow(other);

		// Two recursive cases for "low" half and "high" half of poly.
		poly lowRec = getLeft().mult(other.getLeft());
		poly highRec = getRight().mult(other.getRight());

		// This turns out to be the "middle" part, when we foil.
		// Notice how we use one recursive call instead of two.
		poly aSum = getRight().add(getLeft());
		poly bSum = other.getRight().add(other.getLeft());
		poly midRec = aSum.mult(bSum);
		midRec = midRec.sub(lowRec.add(highRec));

		// Put the result back together, shifting each sub-answer as necessary.
		long[] vals = new long[length << 1];
		for (int i = 0; i < length; i++)
			vals[i] += lowRec.coeff[i];
		for (int i = 0; i < length; i++)
			vals[i + length] += highRec.coeff[i];
		for (int i = 0; i < length; i++)
			vals[i + (length >> 1)] += midRec.coeff[i];
		return new poly(vals);
	}
	// Returns the left half of this poly in its own poly.
	private poly getLeft() {
		return new poly(Arrays.copyOf(coeff, length >> 1));
	}
	// Returns the right half of this poly in its own poly.
	private poly getRight() {
		return new poly(Arrays.copyOfRange(coeff, length >> 1, length));
	}

	static final FS in = new FS();
	static final PrintWriter out = new PrintWriter(System.out);

	public void print() {
		for (int i = 0; i < length - 1; i++)
			out.println(coeff[i]);
	}

	public static void main(String args[]) {
		int n = in.nextInt();
		long coeff[] = new long[1 << n];
		for (int i = 0; i < (1 << n); i++)
			coeff[i] = in.nextLong();

		poly a = new poly(coeff);

		for (int i = 0; i < (1 << n); i++)
			coeff[i] = in.nextLong();

		poly b = new poly(coeff);

		a.mult(b).print();
		out.close();
	}

	public static class FS {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		String next() {
			while(!st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch(Exception e) { };
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
