import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Problem {
	private List<Integer> key;

	private Map<Problem, Integer> mem;

	public Problem(List<Integer> key) {
		this.key = new ArrayList<Integer>(key);
		mem = new HashMap<Problem, Integer>();
	}

	public List<Integer> getKey() {
		return Collections.unmodifiableList(this.key);
	}

	@Override
	public int hashCode() {
		return this.key.stream().map(n -> n * 6967).reduce(Integer::sum).get();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Problem && ((Problem) o).getKey().equals(this.key);
	}

	public int solve() {
		int res = mem.get(this);
		if (res != 0) {
			return res;
		}
		Collection<Problem> sub = new ArrayList<Problem>();
		Collection<Integer> corners = new ArrayList<Integer>();

		int n = this.key.size();

		corners.add(0); // first line is always going to have a corner
		List<Integer> temp1 = new ArrayList<Integer>(key);
		boolean canRemove = false;
		for (int l = 0; l < n - 1; l++) { // last line doesnt matter
			int currentCol = this.key.get(l);
			if (l == 0 || this.key.get(l - 1) < currentCol) {
				for (int sqSz = 2; sqSz <= currentCol; sqSz++) {
					Problem subProblem = new Problem(temp1);
					subProblem.removeSquare(l, sqSz);
					sub.add(subProblem);
					canRemove = true;
				}
				temp1.set(l, currentCol - 1);
			}
		}
		sub.add(new Problem(temp1));

		if (canRemove == false) {
			res = 1;
		}

		res = sub.stream()
				.map(p -> p.solve(this.mem)).reduce(Integer::sum).get();

		this.mem.put(this, res);
		return res;
	}

	private void removeSquare(int l, int sqSz) {
		int col = this.key.get(l);
		int newCol = col - sqSz;
		int i = l + sqSz - 1;

		while (i >= 0) {
			this.key.set(i, newCol);
			i--;
		}
	}

	public int solve(Map<Problem, Integer> mem) {
		this.mem = mem;
		return this.solve();
	}
}