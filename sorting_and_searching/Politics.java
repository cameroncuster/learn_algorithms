import java.util.*;
import java.io.*;

class Politics {
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt(), m = stdin.nextInt();
		do {
			// capture the candidates
			HashMap<String, Integer> candidates = new HashMap<>(n);
			for (int i = 0; i < n; i++)
				candidates.put(stdin.next(), i);

			// prepare empty buckets
			ArrayList<ArrayList<String>> buckets = new ArrayList<>(n);
			for (int i = 0; i < n; i++)
				buckets.add(new ArrayList<>());

			for (int i = 0; i < m; i++) {
				String supporter = stdin.next();
				String candidate = stdin.next();

				if (!candidates.containsKey(candidate)) {
					candidates.put(candidate, candidates.size());
					buckets.add(new ArrayList<>());
				}

				// fill the buckets
				buckets.get(candidates.get(candidate)).add(supporter);
			}

			for (int i = 0; i < buckets.size(); i++)
				for (String supporter : buckets.get(i))
					System.out.println(supporter);

			n = stdin.nextInt();
			m = stdin.nextInt();
		} while (n > 0 && m > 0);
	}
}
