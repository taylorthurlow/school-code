class SeekRequest
  attr_accessor :time_enter, :time_finish, :cylinder

  def initialize(time_enter, cylinder)
    @time_enter = time_enter
    @time_finish = nil
    @cylinder = cylinder
  end

  def delay
    return @time_finish - @time_enter
  end

  def score
    return delay * Math.sqrt(delay)
  end
end
