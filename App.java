import java.util.ArrayList;
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
			
			System.out.println(key);
			Problem problem = new Problem(key);
			System.out.println(problem.solve());
			/** Map<Problem, Integer> mem = problem.getMem();
			for (var entry : mem.entrySet()) {
				System.out.println(entry.getKey().getKey());
				System.out.println(entry.getValue());
				System.out.println("")
			} **/
		}
	}
}