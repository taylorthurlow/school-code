class Graph
  attr_accessor :nodes, :edges

  def initialize(args)
    @nodes = args[:nodes].nil? ? Set.new : args[:nodes]
    @edges = args[:edges].nil? ? Set.new : args[:edges]

    if args[:transition_matrix]
      args[:transition_matrix].length.times do |a|
        @nodes << Node.new(a)
      end

      args[:transition_matrix].length.times do |a|
        args[:transition_matrix][a].each_with_index do |j, k|
          @edges << Edge.new(@nodes[a], j, @nodes[k]) if j.positive? && (j != Float::INFINITY)
        end
      end
    end
  end

  def self.new_random(num_nodes, dense = false)
    nodes = Set.new
    edges = Set.new
    num_nodes.times do |i|
      new_node = Node.new(i)
      edges.add Edge.new(new_node, rand(1..100), nodes.to_a.sample) unless i.zero?
      nodes.add new_node
    end

    possible_edges_divisor = dense ? 4 : 20
    max_edges = nodes.count * (nodes.count - 1) / possible_edges_divisor

    num_edges = edges.count
    while num_edges < max_edges
      from = nodes.to_a.sample
      to = (nodes - [from]).to_a.sample
      exists = !(from.edges & to.edges).empty?
      # exists = edges.any? do |e|
      #   e.nodes.include?(from) && e.nodes.include?(to)
      #   # (e.from == from && e.to == to) || (e.from == to && e.to == from)
      # end
      unless exists
        edges << Edge.new(from, rand(0..100), to)
        num_edges += 1
      end
    end

    return Graph.new(nodes: nodes, edges: edges)
  end

  def node_edges(nodes)
    edges = nodes.is_a?(Set) ? nodes.map(&:edges) : nodes.edges
    return edges.flatten.uniq.to_set
  end

  def neighbors(node)
    edges = node_edges(node)
    return edges.map { |e| e.other_node(node) }.flatten
  end

  def prims
    costs = []
    @nodes.count.times { costs << 99999 }
    edges = []
    @edges.count.times { edges << nil }

    @nodes.each do |n|
      min_edge = node_edges(n).min_by(&:weight)
      edges[n.label] = min_edge
      costs[n.label] = min_edge.weight
    end

    forest_nodes = Set.new
    forest_edges = Set.new
    unvisited = @nodes.dup

    initial_node = @nodes.first
    costs[initial_node.label] = 0
    unvisited.delete initial_node

    until unvisited.empty?
      # find minimum cost for a vertex that is STILL IN unvisited
      pairs = unvisited.map { |u| [u.label, costs[u.label]] }
      min_index = pairs.min_by { |pair| pair[1] }[0]

      node_to_remove = unvisited.find { |n| n.label == min_index }
      unvisited.delete node_to_remove
      forest_nodes.add node_to_remove
      forest_edges.add edges[node_to_remove.label] if edges[min_index].weight < 99999

      node_edges(node_to_remove).each do |ne|
        other_node = ne.other_node(node_to_remove)
        if unvisited.include?(other_node) && ne.weight < costs[other_node.label]
          costs[other_node.label] = ne.weight
          edges[other_node.label] = ne
        end
      end
    end

    return Graph.new(nodes: forest_nodes, edges: forest_edges)
  end

  def kruskals
    # initialize empty result graph
    result_nodes = Set.new
    result_edges = Set.new

    # add a set to that result for each node in the graph
    # now, result_nodes.count == @nodes.count
    @nodes.each { |node| result_nodes << Set.new([node]) }

    sorted_edges.each do |edge|
      # check if the nodes connected by the edge are already in the same set
      common_set = result_nodes.find do |ds|
        (ds.include? edge.from) && (ds.include? edge.to)
      end

      # skip this edge if they are in the same set
      next unless common_set.nil?

      # the nodes are NOT in the same set, so grab both sets
      set_from = result_nodes.find { |ds| ds.include? edge.from }
      set_to = result_nodes.find { |ds| ds.include? edge.to }
      set_merged = set_from + set_to

      # remove the sets from the result so we can add the merged one
      result_nodes.delete set_from
      result_nodes.delete set_to
      result_nodes.add set_merged

      # add the edge to the final result
      result_edges.add edge
    end

    # raise 'Kruskals result node count mismatch' if result_nodes.flatten.to_a.sort != @nodes.sort

    return Graph.new(nodes: result_nodes.flatten, edges: result_edges)
  end

  def sorted_edges
    return @edges.sort_by(&:weight)
  end

  # def edge_weight(from, to)
  #   edge = @edges.find { |e| e.from.nodes == e.to.nodes }
  #   return edge.nil? ? Float::INFINITY : edge.weight
  # end
end
