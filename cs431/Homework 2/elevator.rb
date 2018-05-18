def elevator(master_list, start_position = 50)
  head_position = start_position
  total_time_taken = 0
  move_direction = :up

  request_list = []
  finished_requests = []

  master_list.pop(10).each do |cylinder|
    request_list << SeekRequest.new(total_time_taken, cylinder)
  end

  until master_list.empty? && request_list.empty?
    valid_requests = if move_direction == :up
                       request_list.select { |r| r.cylinder >= head_position }
                     elsif move_direction == :down
                       request_list.select { |r| r.cylinder <= head_position }
                     end

    if valid_requests.empty?
      move_direction = move_direction == :up ? :down : :up # toggle direction
      next
    end

    selected_request = valid_requests.min do |a, b|
      (a.cylinder - head_position).abs <=> (b.cylinder - head_position).abs
    end

    request_list.delete(selected_request)
    total_time_taken += (selected_request.cylinder - head_position).abs
    head_position = selected_request.cylinder
    selected_request.time_finish = total_time_taken
    finished_requests << selected_request

    next unless request_list.count < 5

    master_list.pop(10).each do |cylinder|
      request_list << SeekRequest.new(total_time_taken, cylinder)
    end
  end

  return finished_requests, total_time_taken
end
