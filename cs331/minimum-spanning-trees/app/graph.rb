class Graph
  def initialize(nodes, edges)
    @nodes = nodes
    @edges = edges
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

  def kruskals
    # initialize empty result graph
    result_nodes = Set.new
    result_edges = Set.new

    # add a set to that result for each node in the graph
    # now, result_nodes.count == @nodes.count
    @nodes.each do |node|
      result_nodes << Set.new([node])
    end

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

    return result_edges
  end
end
