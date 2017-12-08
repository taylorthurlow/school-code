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

    possible_edges_divisor = dense ? 2 : 10
    max_edges = nodes.count * (nodes.count - 1) / possible_edges_divisor

    while edges.count < max_edges
      from = nodes.to_a.sample
      to = (nodes - [from]).to_a.sample
      exists = edges.any? do |e|
        (e.from == from && e.to == to) || (e.from == to && e.to == from)
      end
      edges << Edge.new(from, rand(0..100), to) unless exists
    end

    return Graph.new(nodes: nodes, edges: edges)
  end

  def node_edges(nodes)
    nodes = Set.new([nodes]) unless nodes.is_a? Set
    edges = Set.new
    nodes.each do |n|
      edges.add(Set.new(@edges.select { |e| e.nodes.include? n }))
    end
    return edges.flatten.uniq.to_set
  end

  def prims
    # initialize empty result graph, add arbitrary root node
    result_nodes = Set.new
    result_edges = Set.new
    result_nodes.add @nodes.first

    while result_nodes.size < @nodes.size
      acceptable_edges = (node_edges(result_nodes).subtract result_edges)
      acceptable_edges.reject! { |ae| ae.nodes & result_nodes == ae.nodes }

      edge = acceptable_edges.min_by(&:weight)
      result_edges << edge
      result_nodes << (edge.nodes - result_nodes).first
    end

    # raise 'Prims result node count mismatch' if result_nodes.sort != @nodes.sort

    return Graph.new(nodes: result_nodes, edges: result_edges)
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

  def edge_weight(from_label, to_label)
    from = @nodes.find { |n| n.label == from_label }
    to = @nodes.find { |n| n.label == to_label }
    return Float::INFINITY if from.nil? || to.nil?
    edge = @edges.find { |e| e.from == from && e.to == to }
    return edge.nil? ? Float::INFINITY : edge.weight
  end
end
