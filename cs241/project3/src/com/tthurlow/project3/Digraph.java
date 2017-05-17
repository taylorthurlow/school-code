package com.tthurlow.project3;

import java.util.*;

public class Digraph<V> {

	private Set<V> unvisitedNodes, visitedNodes;
	private Map<V, V> predecessors;
	private Map<V, Integer> distance;

	public static class Edge<V> {
		private V destination;
		private V source;
		private int weight;

		public Edge(V source, V destination, int weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}

		public V getDestination() {
			return destination;
		}

		public V getSource() {
			return source;
		}

		public int getWeight() {
			return weight;
		}

		@Override
		public String toString() {
			return "edge from " + destination + ", weight is " + weight + ".";
		}

	}

	// Map of all vertices and their associated edges
	private Map<V, List<Edge<V>>> neighbors = new HashMap<>();

	public List<Edge<V>> allEdges() {
		List<Edge<V>> edges = new ArrayList<>();
		for (Map.Entry<V, List<Edge<V>>> entry : neighbors.entrySet()) {
			for (Edge<V> subEntry : entry.getValue()) {
				edges.add(subEntry);
			}
		}
		return edges;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		for (V v : neighbors.keySet())
			s.append("\n    " + v + " -> " + neighbors.get(v));
		return s.toString();
	}

	// Add a vertex, ignores duplicates
	public void add(V vertex) {
		if (neighbors.containsKey(vertex))
			return;
		neighbors.put(vertex, new ArrayList<Edge<V>>());
	}

	// Add an edge, allows duplicates
	public void add(V from, V to, int weight) {
		this.add(from);
		this.add(to);
		neighbors.get(from).add(new Edge<V>(from, to, weight));
	}

	public void removeEdge(V from, V to) {
		List<Edge<V>> adj = neighbors.get(from);

		int indexToRemove = -1;

		for (Iterator <Edge<V>> it = adj.iterator(); it.hasNext(); ) {
			Edge<V> e = it.next();
			if (e.destination == to)
				indexToRemove = neighbors.get(from).indexOf(e);
		}

		if (indexToRemove != -1)
			neighbors.get(from).remove(indexToRemove);
	}

	public List<V> outboundNeighbors(V vertex) {
		List<V> list = new ArrayList<>();
		for(Edge<V> e: neighbors.get(vertex))
			list.add(e.destination);
		return list;
	}

	public boolean isEdge(V from, V to) {
		for(Edge<V> e :  neighbors.get(from)){
			if(e.destination.equals(to))
				return true;
		}
		return false;
	}

	public void executeDijkstra(V source) {
		unvisitedNodes = new HashSet<>();
		visitedNodes = new HashSet<>();
		distance = new HashMap<>();
		predecessors = new HashMap<>();
		distance.put(source, 0);
		visitedNodes.add(source);

		while (visitedNodes.size() > 0) {
			V node = getMinimum(visitedNodes);
			unvisitedNodes.add(node);
			visitedNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	public void findMinimalDistances(V node) {
		List<V> adjacentNodes = outboundNeighbors(node);
		for (V target : adjacentNodes) {
			int distanceNodeToTarget = getDistance(node, target);
			if (getShortestDistance(target) > getShortestDistance(node) + distanceNodeToTarget) {
				distance.put(target, getShortestDistance(node) + distanceNodeToTarget);
				predecessors.put(target, node);
				visitedNodes.add(target);
			}
		}
	}

	public int getDistance(V node, V target) {
		for (Edge edge : allEdges()) {
			if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("this should not happen.");
	}

	public int getShortestDistance(V target) {
		Integer d = distance.get(target);
		if (d == null)
			return Integer.MAX_VALUE;
		else
			return d;
	}

	private V getMinimum(Set<V> vs) {
		V minimum = null;
		for (V vertex : vs) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	public LinkedList<V> getPath(V target) {
		LinkedList<V> path = new LinkedList<>();
		V step = target;

		if (predecessors.get(step) == null) return null;

		path.add(step);

		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}

		Collections.reverse(path);
		return path;
	}
}