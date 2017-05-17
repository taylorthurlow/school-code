public class Main {
    private static String function = "b";
    private static boolean toleranceSatisfied = false;

    public static void main(String[] args) {
        double accuracy = 0.00001;
        double delta = 0.01;

        int iteration = 0;
        double xim1 = 100;
        double xi   = 200;

        double xip1 = xi - function(function, xi) * ((xi - xim1) / (function(function, xi) - function(function, xim1)));
        double err = 999999.0;

        printSecantRowFunction(function, iteration, xim1, xi, xip1, 0);

        while (!toleranceSatisfied(xi, xim1, delta) && iteration < 100) {
            // store xip1 value for err calc
            double prev_xip1 = xip1;

            iteration++;
            xim1 = xi;
            xi = xip1;
            xip1 = xi - function(function, xi) * ((xi - xim1) / (function(function, xi) - function(function, xim1)));
            err = Math.abs((xip1 - prev_xip1) / xip1);

            printSecantRowFunction(function, iteration, xim1, xi, xip1, err);
        }
    }

    private static double function(String select, double x) {
        if (select.toLowerCase().equals("a")) {
            // 2x^3 - 11.7x^2 + 17.7x - 5
            double term1 = 2 * Math.pow(x, 3);
            double term2 = 11.7 * Math.pow(x, 2);
            double term3 = 17.7 * x;
            return term1 - term2 + term3 - 5;
        } else if (select.toLowerCase().equals("b")) {
            // x + 10 - x * cosh(50 / x)
            return x + 10 - (x * Math.cosh(50 / x));
        } else {
            // bad
            return 0;
        }
    }

    private static boolean toleranceSatisfied(double xi, double xim1, double delta) {
        double term1 = Math.abs(function(function, xi) - function(function, xim1));
        double term2 = delta * Math.abs(function(function, xi));
        return (term1 <= term2);
    }

    private static void printSecantRowFunction(String function, int n, double xim1, double xi, double xip1, double err) {
        String err_text = "";
        if (n == 0) {
            System.out.println(
                    padRight("n", 4) +
                            padRight("xi-1", 24) +
                            padRight("xi", 24) +
                            padRight("xi+1", 24) +
                            padRight("f(xi-1)", 24) +
                            padRight("f(xi)", 24) +
                            padRight("f(xi+1)", 24) +
                            padRight("apx_err", 24)
            );
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        } else {
            err_text = Double.toString(Math.round(err * 100000000.0) / 100000000.0);
        }


        System.out.println(
                padRight(Integer.toString(n), 4) +
                        padRight(Double.toString(Math.round(xim1 * 100000000.0) / 100000000.0), 24) +
                        padRight(Double.toString(Math.round(xi * 100000000.0) / 100000000.0), 24) +
                        padRight(Double.toString(Math.round(xip1 * 100000000.0) / 100000000.0), 24) +
                        padRight(Double.toString(Math.round(function(function, xim1) * 100000000.0) / 100000000.0), 24) +
                        padRight(Double.toString(Math.round(function(function, xi) * 100000000.0) / 100000000.0), 24) +
                        padRight(Double.toString(Math.round(function(function, xip1) * 100000000.0) / 100000000.0), 24) +
                        padRight(err_text, 24)
        );
    }


    private static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
}
