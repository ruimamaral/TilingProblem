import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Problem {
	private List<Integer> key;
	private int desc;

	private Map<Integer, Integer> mem;

	public Problem(List<Integer> key, int desc) {
		this.key = key;
		this.desc = desc;
		mem = new HashMap<Integer, Integer>();
	}

	public Integer solve() {
		Integer res = mem.get(this.desc);
		if (res != 0) {
			return res;
		}
		Collection<Problem> sub = new ArrayList<Problem>();
		Collection<Integer> corners = new ArrayList<Integer>();

		int n = this.key.size();

		corners.add(0); // first line always going to have a corner
		for (int c = 0; c < n - 1; c++) { // last line doesnt matter
			if (this.key.get(c - 1) < this.key.get(c)) {
				corners.add(c);
			}
		}

		for (int i = 2; i <= n; i++) {
		}

		res = sub.stream().map(p -> p.solve(mem)).reduce(Integer::sum);

		this.mem.put(this.desc, res);
		return res;
	}

	public int solve(Map<String, Integer> mem) {
		this.mem = mem;
		return this.solve();
	}
}