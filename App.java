import java.util.Scanner;

public class App {

	public static void main(String args[]) {

		Scanner scan = new Scanner(System.in);
		int key = 0;

		int n = scan.nextInt();
		int m = scan.nextInt();
		System.out.println(String.format("n: %d, m: %d", n, m));

		for (int i = 1; i <= n; i++) {
			key += scan.nextInt()*Math.pow(10, n - i);
		}
		System.out.println(Problem.solve(key));
	}
}