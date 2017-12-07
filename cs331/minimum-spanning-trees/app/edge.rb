class Edge
  attr_accessor :from, :weight, :to

  def initialize(from, weight, to)
    @from = from
    @weight = weight
    @to = to
  end

  def nodes
    return [@from, @to]
  end

  def to_s
    return "(#{@from},#{@weight},#{@to})"
  end
end
