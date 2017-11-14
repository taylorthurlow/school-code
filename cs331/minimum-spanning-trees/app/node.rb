class Node
  attr_accessor :label

  def initialize(label)
    @label = label
  end

  def to_s
    return "#{@label}"
  end
end
