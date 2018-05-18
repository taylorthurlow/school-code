require_relative 'seek_request'
require_relative 'first_come_first_serve'
require_relative 'shortest_seek_first'
require_relative 'elevator'
require_relative 'custom'

print 'How many seeks to generate?: '
seeks_to_generate = gets.to_i

master_list = Array.new(seeks_to_generate) { rand(1..101) }

print 'FCFS total time: '
puts first_come_first_serve(master_list.dup)

puts '---'

ssf_result = shortest_seek_first(master_list.dup)
puts "SSF total time: #{ssf_result[1]}"
print 'SSF average delay: '
puts ssf_result[0].map(&:delay).sum / ssf_result[0].count
print 'SSF maximum delay: '
puts ssf_result[0].map(&:delay).max
print 'SSF average score: '
puts (ssf_result[0].map(&:score).sum / ssf_result[0].count).round(1)
print 'SSF maximum score: '
puts ssf_result[0].map(&:score).max.round(1)

puts '---'

elevator_result = elevator(master_list.dup)
puts "Elevator total time: #{elevator_result[1]}"
print 'Elevator average delay: '
puts elevator_result[0].map(&:delay).sum / elevator_result[0].count
print 'Elevator maximum delay: '
puts elevator_result[0].map(&:delay).max
print 'Elevator average score: '
puts (elevator_result[0].map(&:score).sum / elevator_result[0].count).round(1)
print 'Elevator maximum score: '
puts elevator_result[0].map(&:score).max.round(1)

puts '---'

custom = custom(master_list.dup)
puts "Custom total time: #{custom[1]}"
print 'Custom average delay: '
puts custom[0].map(&:delay).sum / custom[0].count
print 'Custom maximum delay: '
puts custom[0].map(&:delay).max
print 'Custom average score: '
puts (custom[0].map(&:score).sum / custom[0].count).round(1)
print 'Custom maximum score: '
puts custom[0].map(&:score).max.round(1)
