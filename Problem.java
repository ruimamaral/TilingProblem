import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public Map<Problem, Integer> getMem() {
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

	public int solve() {
		int res;
		if (mem.containsKey(this)) {
			return mem.get(this);
		}
		Collection<Problem> sub = new ArrayList<Problem>();

		int n = this.key.size();

		List<Integer> temp1 = new ArrayList<Integer>(key);
		boolean canRemove = false;
		for (int l = 0; l < n - 1; l++) { // last line doesnt matter
			int currentCol = this.key.get(l);
			if (l == 0 || this.key.get(l - 1) < currentCol) {
				if (currentCol == 0) {
					continue;
				}
				for (int sqSz = 2; sqSz <= currentCol; sqSz++) {
					if (l + sqSz <= n) {
						Problem subProblem = new Problem(temp1);
						subProblem.removeSquare(l, sqSz);
						sub.add(subProblem);
						canRemove = true;
					}
				}
				temp1.set(l, currentCol - 1);
			}
		}
		sub.add(new Problem(temp1));

		if (canRemove == false) {
			mem.put(this, 1);
			return 1;
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
		// System.out.println(String.format("l: %d, sqSz: %d, col: %d, newCol: %d, i: %d", l, sqSz, col, newCol, i));

		while (i >= 0 && this.key.get(i) > newCol) {
			this.key.set(i, newCol);
			i--;
		}
	}

	public int solve(Map<Problem, Integer> mem) {
		this.mem = mem;
		return this.solve();
	}
}