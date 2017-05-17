import java.util.*;

class Untitled {
	public static void main(String[] args) {
		Scanner input_data = new Scanner(System.in);
		System.out.print("Input degrees celcius to be converted: ");
		double in = input_data.nextDouble();
		double result = in * ((double) 9 / (double) 5) + (double) 32;
		System.out.println("In fahrenheit: " + result);
	}
}