def shortest_seek_first(master_list, start_position = 50)
  head_position = start_position
  total_time_taken = 0

  request_list = []
  finished_requests = []

  master_list.pop(10).each do |cylinder|
    request_list << SeekRequest.new(total_time_taken, cylinder)
  end

  until master_list.empty? && request_list.empty?
    closest_request = request_list.min { |sr| (sr.cylinder - head_position).abs }
    request_list.delete(closest_request)
    total_time_taken += (closest_request.cylinder - head_position).abs
    head_position = closest_request.cylinder
    closest_request.time_finish = total_time_taken
    finished_requests << closest_request

    next unless request_list.count < 5

    master_list.pop(10).each do |cylinder|
      request_list << SeekRequest.new(total_time_taken, cylinder)
    end
  end

  return finished_requests
end
