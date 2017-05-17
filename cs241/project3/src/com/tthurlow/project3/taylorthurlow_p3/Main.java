import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	private static boolean running = true;
	private static ArrayList<City> cities = new ArrayList<>();
	private static ArrayList<Road> roads = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		/**
		 * Read in data for Cities
		 */
		try (BufferedReader br = new BufferedReader(new FileReader(new File("./city.dat")))) {
			for (String line; (line = br.readLine()) != null; ) {
				if (line.trim().length() > 0) {
					Pattern pattern = Pattern.compile("(\\d+)[ ]+(\\w{2})[ ]+([a-zA-Z]+(?:[\\s-][a-zA-Z]+)*)[ ]+(\\d+)[ ]+(\\d+)");
					Matcher matcher = pattern.matcher(line);
					while (matcher.find()) {
						cities.add(new City(Integer.parseInt(matcher.group(1)),
								matcher.group(2),
								matcher.group(3),
								Integer.parseInt(matcher.group(4)),
								Integer.parseInt(matcher.group(5))));
					}
				}
			}
		}

		/**
		 * Read in data for Roads
		 */
		try (BufferedReader br = new BufferedReader(new FileReader(new File("./road.dat")))) {
			for (String line; (line = br.readLine()) != null; ) {
				if (line.trim().length() > 0) {
					Pattern pattern = Pattern.compile("(\\d+)[ ]+(\\d+)[ ]+(\\d+)");
					Matcher matcher = pattern.matcher(line);
					while (matcher.find()) {

						int from_id = Integer.parseInt(matcher.group(1));
						int to_id = Integer.parseInt(matcher.group(2));
						int distance = Integer.parseInt(matcher.group(3));

						City from = null, to = null;

						for (City c : cities) {
							if (c.getId() == from_id)
								from = c;
							else if (c.getId() == to_id)
								to = c;
						}

						if (from == null || to == null) {
							//System.out.println("NULL: " + from_id + ", " + to_id + ", " + distance);
						} else {
							roads.add(new Road(from, to, distance));
						}
					}
				}
			}
		}

		Digraph<City> graph = new Digraph<>();

		// Add cities to graph
		for (City c : cities)
			graph.add(c);

		// Add roads to graph
		for (Road r : roads)
			graph.add(r.getFrom(), r.getTo(), r.getDistance());

		graph.executeDijkstra(cities.get(4));

		while (running) {
			System.out.print("Command: ");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			char cmd = '?'; // Default to help in case something goes wrong

			try {
				String[] inputStream = bufferedReader.readLine().split(" ");
				cmd = inputStream[0].toCharArray()[0];
				if (inputStream.length > 1)
					cmd = '?'; // Force help text if too many arguments
			} catch (IOException e) {
				e.printStackTrace();
			}


			switch (Character.toLowerCase(cmd)) {
				case 'q':
					System.out.print("City code: ");
					City resultQ = parseSingleCityCodeInput();
					System.out.println(resultQ.toString());
					break;
				case 'd':
					System.out.print("City codes: ");
					City[] resultD = parseTwoCityCodeInput();
					City c1 = resultD[0];
					City c2 = resultD[1];
					graph.executeDijkstra(cities.get(c1.getId() - 1));
					int minDistance = graph.getShortestDistance(cities.get(c2.getId() - 1));
					LinkedList<City> path = graph.getPath(cities.get(c2.getId() - 1));
					System.out.println("The minimum distance between " + c1.getName() + " and " + c2.getName() +
						" is " + minDistance + " through the route: " + prettifyRoute(path));
					break;
				case 'i':
					System.out.print("City codes and distance: ");
					boolean asking = true;
					City queryCity1 = null;
					City queryCity2 = null;
					int distance = -1;
					String code1 = null, code2 = null;

					while (asking) {
						BufferedReader breader = new BufferedReader(new InputStreamReader(System.in));

						try {
							String[] inputStream = breader.readLine().split(" ");
							code1 = inputStream[0];
							code2 = inputStream[1];
							distance = Integer.parseInt(inputStream[2]);
							if (inputStream.length != 3)
								System.out.println("You must input exactly 2 two-character city codes, space separated," +
										"followed by an integer value for the distance.");
						} catch (IOException e) {
							e.printStackTrace();
						}

						if (code1 == null || code2 == null) continue;

						for (City c : cities) {
							if (c.getCode().equals(code1.toUpperCase()))
								queryCity1 = c;
							else if (c.getCode().equals(code2.toUpperCase()))
								queryCity2 = c;

							if (queryCity1 != null && queryCity2 != null)
								asking = false;
						}

						if (code1 == null || code2 == null) {
							System.out.println("You should check that your city codes are correct.");
							break;
						}
					}

					if (graph.isEdge(queryCity1, queryCity2))
						System.out.println("There's already a road between those two cities.");
					else {
						// Edge doesn't exist, we're good to insert the road there
						graph.add(queryCity1, queryCity2, distance);
						System.out.println("You have inserted a road from " + queryCity1.getName() + " to " +
							queryCity2.getName() + " with a distance of " + distance + ".");
					}
					break;
				case 'r':
					System.out.print("City codes: ");
					City[] resultR = parseTwoCityCodeInput();
					City c1r = resultR[0];
					City c2r = resultR[1];

					if (graph.isEdge(c1r, c2r)) {
						graph.removeEdge(c1r, c2r);
						System.out.println("Removed the road from " + c1r.getCode() + " to " + c2r.getCode() + ".");
					} else {
						System.out.println("This road does not exist.");
					}

					break;
				case 'e':
					System.out.println("Exiting...");
					running = false;
					break;
				case 'h':
					System.out.println("Available commands:");
					System.out.println("Q  Query the city information by entering the city code.");
					System.out.println("D  Find the minimum distance between two cities.");
					System.out.println("I  Insert a road by entering two city codes and a distance.");
					System.out.println("R  Remove an existing road by entering two city codes.");
					System.out.println("H  Display this message.");
					System.out.println("E  Exit.");
					break;
				default:
					break;
			}
		}
	}

	public static City parseSingleCityCodeInput() {
		boolean asking = true;
		City queryCity = null;
		while (asking) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String cmd = "?"; // Default to help in case something goes wrong

			try {
				String[] inputStream = bufferedReader.readLine().split(" ");
				cmd = inputStream[0];
				if (inputStream.length != 1)
					System.out.println("You must input exactly 1 two-character city codes.");
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (cmd == "?") continue;

			for (City c : cities) {
				if (c.getCode().equals(cmd)) {
					queryCity = c;
					asking = false;
				}
			}
		}

		return queryCity;
	}

	public static City[] parseTwoCityCodeInput() {
		boolean asking = true;
		City queryCity1 = null;
		City queryCity2 = null;
		String code1 = null, code2 = null;
		while (asking) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

			try {
				String[] inputStream = bufferedReader.readLine().split(" ");
				code1 = inputStream[0];
				code2 = inputStream[1];
				if (inputStream.length != 2)
					System.out.println("You must input exactly 2 two-character city codes, space separated.");
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (code1 == null || code2 == null) continue;

			for (City c : cities) {
				if (c.getCode().equals(code1.toUpperCase()))
					queryCity1 = c;
				else if (c.getCode().equals(code2.toUpperCase()))
					queryCity2 = c;

				if (queryCity1 != null && queryCity2 != null)
					asking = false;
			}
		}
		return new City[]{queryCity1, queryCity2};
	}

	public static String prettifyRoute(LinkedList<City> path) {
		String result = "";
		for (City c : path)
			result += c.getCode() + ", ";

		return result.substring(0, result.length() - 2);
	}
}