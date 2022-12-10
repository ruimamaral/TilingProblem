import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

	public static void main(String args[]) {

		try (Scanner scan = new Scanner(System.in)) {
			List<Integer> key = new ArrayList<Integer>();

			int n = scan.nextInt();
			int m = scan.nextInt();

			for (int i = 1; i <= n; i++) {
				key.add(scan.nextInt());
			}
			
			System.out.println(key);
			Problem problem = new Problem(key);
			System.out.println(problem.solve());
		}
	}
}