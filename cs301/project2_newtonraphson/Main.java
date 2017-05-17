public class Main {

    static boolean interminable = false;
    
    public static void main(String[] args) {
        String function = "b";
        double accuracy = 0.00001;

        int iteration = 0;
        double xi = 100;
        double xi1 = xi - (function(function, xi) / derivative(function, xi));
        double err = 999999.0;

        printNewtonRaphsonRowFunction(function, iteration, xi, xi1, 0);

        while (Math.abs(err) > accuracy && iteration < 100 && !interminable) {
            // store xi1 value for err calc
            double prev_xi1 = xi1;

            iteration++;
            xi = xi1;
            xi1 = xi - (function(function, xi) / derivative(function, xi));
            err = Math.abs((xi1 - prev_xi1) / xi1);

            printNewtonRaphsonRowFunction(function, iteration, xi, xi1, err);
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

    private static double derivative(String select, double x) {
        if (select.toLowerCase().equals("a")) {
            // 6x^2 - 23.4x + 17.7
            double term1 = 6 * Math.pow(x, 2);
            double term2 = 23.4 * x;
            double result = term1 - term2 + 17.7;
            if (result == 0) interminable = true;
            return result;
        } else if (select.toLowerCase().equals("b")) {
            // ((50 * sinh(50 / x)) / x) - cosh(50 / x) + 1
            double result = ((50 * Math.sinh(50 / x)) / x) - Math.cosh(50 / x) + 1;
            if (result == 0) interminable = true;
            return result;
        } else {
            // bad
            return 0;
        }
    }

    private static void printNewtonRaphsonRowFunction(String function, int n, double xi, double xi1, double err) {
        String err_text = "";
        if (n == 0) {
            System.out.println(
                    padRight("n", 4) +
                            padRight("xi", 24) +
                            padRight("xi+1", 24) +
                            padRight("f(xi)", 24) +
                            padRight("f(xi+1)", 24) +
                            padRight("apx_err", 24)
            );
            System.out.println("----------------------------------------------------------------------------------------------------------------");
        } else {
            err_text = Double.toString(Math.round(err * 100000000.0) / 100000000.0);
        }


        System.out.println(
                padRight(Integer.toString(n), 4) +
                        padRight(Double.toString(Math.round(xi * 100000000.0) / 100000000.0), 24) +
                        padRight(Double.toString(Math.round(xi1 * 100000000.0) / 100000000.0), 24) +
                        padRight(Double.toString(Math.round(function(function, xi) * 100000000.0) / 100000000.0), 24) +
                        padRight(Double.toString(Math.round(function(function, xi1) * 100000000.0) / 100000000.0), 24) +
                        padRight(err_text, 24)
        );
    }


    private static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
}
