class Edge
  attr_accessor :from, :weight, :to

  def initialize(from, weight, to)
    @from = from
    @weight = weight
    @to = to

    @from.edges << self
    @to.edges << self
  end

  def nodes
    return Set.new([@from, @to])
  end

  def other_node(node)
    return node == @to ? @to : @from
  end

  def to_s
    return "(#{@from},#{@weight},#{@to})"
  end
end
