def first_come_first_serve(master_list, start_position = 50)
  head_position = start_position
  total_time_taken = 0
  master_list.each do |cylinder|
    total_time_taken += (cylinder - head_position).abs
    head_position = cylinder
  end

  return total_time_taken
end
