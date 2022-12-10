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
		if (mem.containsKey(this)) {
			return mem.get(this);
		}
		Collection<Problem> sub = new ArrayList<Problem>();

		int res;
		int n = this.key.size();
		int maxColIndex = this.getMaxColIndex();
		int maxCol = this.key.get(maxColIndex);
		if (maxCol == 0) {
			return 1;
		}
		int maxSqSz = 1;

		for (int i = maxColIndex + 1; i < n && maxSqSz < maxCol; i++) {
			if (this.key.get(i) != maxCol) {
				break;
			}
			maxSqSz++;
		}
		for (int i = maxSqSz; i > 0; i--) {
			Problem subProblem = new Problem(this.key);
			subProblem.removeSquare(maxColIndex, i);
			sub.add(subProblem);
		}

		res = sub.stream()
				.map(p -> p.solve(this.mem)).reduce(Integer::sum).get();

		this.mem.put(this, res);
		return res;
	}

	private void removeSquare(int l, int sqSz) {
		int col = this.key.get(l);
		int newCol = col - sqSz;
		for (int i = 0; i < sqSz; i++) {
			this.key.set(l + i, newCol);
		}
	}

	private int getMaxColIndex() {
		int sz = this.key.size();
		int max = 0;
		int maxCol = 0;
		for (int i = 0; i < sz; i++){
			int curCol = this.key.get(i);
			if (curCol > maxCol) {
				max = i;
				maxCol = curCol;
			}
		}
		return max;
		
	}

	public int solve(Map<Problem, Integer> mem) {
		this.mem = mem;
		return this.solve();
	}
}