class Node
  attr_accessor :label, :edges

  def initialize(label)
    @label = label
    @edges = Set.new
  end

  def to_s
    return @label.to_s
  end
end
