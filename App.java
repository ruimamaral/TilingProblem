import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class App {

	public static void main(String args[]) {

		try (Scanner scan = new Scanner(System.in)) {
			List<Integer> key = new ArrayList<Integer>();

			int n = scan.nextInt();
			int m = scan.nextInt();
			boolean isBlank = true;

			for (int i = 1; i <= n; i++) {
				int x = scan.nextInt();
				if (x != 0) {
					isBlank = false;
				}
				key.add(x);
			}

			if (isBlank) {
				System.out.println(0);
				return;
			}
			
			Problem problem = new Problem(key);
			System.out.println(problem.solve());
			Map<Problem, Long> mem = problem.getMem();
			for (var entry : mem.entrySet()) {
				System.out.println(entry.getKey().getKey());
				System.out.println(entry.getValue());
				System.out.println("");
			}
		}
	}

	public static class Problem {
		private List<Integer> key;

		private Map<Problem, Long> mem;

		public Problem(List<Integer> key) {
			this.key = new ArrayList<Integer>(key);
			mem = new HashMap<Problem, Long>();
		}

		public List<Integer> getKey() {
			return Collections.unmodifiableList(this.key);
		}

		public Map<Problem, Long> getMem() {
			return this.mem;
		}

		@Override
		public int hashCode() {
			return this.key.stream().map(n -> n * 6967).reduce(Integer::sum).get();
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Problem && ((Problem) o).getKey().equals(this.key);
		}

		public long solve() {
			System.out.println(this.key);
			if (mem.containsKey(this)) {
				return mem.get(this);
			}
			Collection<Problem> sub = new ArrayList<Problem>();

			long res;
			int sz = this.key.size();
			int start;

			for (start = 0; this.key.get(start) == 0 && start < sz - 1; start++) {}
			if (start >= sz - 1) {
				this.mem.put(this, 1L);
				return 1L;
			}

			int n = sz - start;

			int maxSqSz = Math.min(n, this.key.get(start));

			for (int i = 1; i <= maxSqSz; i++) {
				Problem subProblem = new Problem(this.key);
				subProblem.removeSquare(start, i);
				sub.add(subProblem);
			}

			res = sub.stream()
					.map(p -> p.solve(this.mem)).reduce(Long::sum).get();

			this.mem.put(this, res);
			return res;
		}

		private void removeSquare(int l, int sqSz) {
			int maxLine = l + sqSz - 1;
			for (int i = l ; i <= maxLine; i++) {
				this.key.set(i, this.key.get(i) - sqSz);
			}
		}

		public long solve(Map<Problem, Long> mem) {
			this.mem = mem;
			return this.solve();
		}
	}
}
