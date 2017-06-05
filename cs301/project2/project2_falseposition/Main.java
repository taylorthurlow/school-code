public class Main {

	public static void main(String[] args) {
		double accuracy = 0.00001;
		String function = "b";

		int iteration = 0;
		double a = 120;
		double b = 130;
		double c = a - ((function(function, a) * (b - a)) / (function(function, b) - function(function, a)));
		double err = 999999.0;


		printFalsePositionRowFunction(function, iteration, a, b, c, 0);

		while (Math.abs(err) > accuracy && iteration < 100) {
			// store c value for err calc
			double prev_c = c;

			// if root is between a and c, f(a) * f(c) < 0
			// if root is between c and b, f(a) * f(c) > 0
			double result = function(function, a) * function(function, c);
			if (result < 0)
				b = c;
			else if (result > 0)
				a = c;

			iteration++;
			c = a - ((function(function, a) * (b - a)) / (function(function, b) - function(function, a)));
			err = Math.abs((c - prev_c) / c);

			printFalsePositionRowFunction(function, iteration, a, b, c, err);
		}
	}

	private static double function(String select, double x) {
		if (select.toLowerCase().equals("a")) {
			double term1 = 2 * Math.pow(x, 3);
			double term2 = 11.7 * Math.pow(x, 2);
			double term3 = 17.7 * x;
			return term1 - term2 + term3 - 5;
		} else if (select.toLowerCase().equals("b")) {
			return x + 10 - (x * Math.cosh(50 / x));
		} else {
			// bad
			return 0;
		}
	}

	private static void printFalsePositionRowFunction(String function, int n, double a, double b, double c, double err) {
		String err_text = "";
		if (n == 0) {
			System.out.println(
					padRight("n", 4) +
							padRight("a", 16) +
							padRight("b", 16) +
							padRight("c", 16) +
							padRight("f(a)", 16) +
							padRight("f(b)", 16) +
							padRight("f(c)", 16) +
							padRight("apx_err", 16)
			);
			System.out.println("----------------------------------------------------------------------------------------------------------------");
		} else {
			err_text = Double.toString(Math.round(err * 100000000.0) / 100000000.0);
		}


		System.out.println(
				padRight(Integer.toString(n), 4) +
						padRight(Double.toString(Math.round(a * 100000000.0) / 100000000.0), 16) +
						padRight(Double.toString(Math.round(b * 100000000.0) / 100000000.0), 16) +
						padRight(Double.toString(Math.round(c * 100000000.0) / 100000000.0), 16) +
						padRight(Double.toString(Math.round(function(function, a) * 100000000.0) / 100000000.0), 16) +
						padRight(Double.toString(Math.round(function(function, b) * 100000000.0) / 100000000.0), 16) +
						padRight(Double.toString(Math.round(function(function, c) * 100000000.0) / 100000000.0), 16) +
						padRight(err_text, 16)
		);
	}


	private static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}
}
