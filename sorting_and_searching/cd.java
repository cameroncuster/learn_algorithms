import java.util.*;
import java.io.*;

class cd {
	static final FS in = new FS();
	static final PrintWriter out = new PrintWriter(System.out);

	public static void main(String[] args) {
		int n = in.nextInt(), m = in.nextInt();
		do {
			HashSet<Integer> cds = new HashSet<>(n);
			for (int i = 0; i < n; i++)
				cds.add(in.nextInt());
			int sell = 0;
			for (int i = 0; i < m; i++)
				if (cds.contains(in.nextInt()))
					sell++;
			out.println(sell);
			n = in.nextInt();
			m = in.nextInt();
		} while (n > 0 || m > 0);
		out.close();
	}

	public static class FS {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		String next(){
			while(!st.hasMoreElements()){
				try{
					st = new StringTokenizer(br.readLine());
				}catch(Exception e){};
			}
			return st.nextToken();
		}
		int nextInt(){
			return Integer.parseInt(next());
		}
		long nextLong(){
			return Long.parseLong(next());
		}
		double nextDouble(){
			return Double.parseDouble(next());
		}
		char nextChar(){
			return next().charAt(0);
		}
	}
}

