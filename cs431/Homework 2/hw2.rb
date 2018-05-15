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
print 'SSF average delay: '
puts ssf_result.map(&:delay).inject { |sum, el| sum + el }.to_f / ssf_result.count
print 'SSF maximum delay: '
puts ssf_result.map(&:delay).max
print 'SSF average score: '
puts (ssf_result.map(&:score).inject { |sum, el| sum + el }.to_f / ssf_result.count).round(1)
print 'SSF maximum score: '
puts ssf_result.map(&:score).max.round(1)

puts '---'

elevator_result = elevator(master_list.dup)
print 'Elevator average delay: '
puts elevator_result.map(&:delay).inject { |sum, el| sum + el }.to_f / elevator_result.count
print 'Elevator maximum delay: '
puts elevator_result.map(&:delay).max
print 'Elevator average score: '
puts (elevator_result.map(&:score).inject { |sum, el| sum + el }.to_f / elevator_result.count).round(1)
print 'Elevator maximum score: '
puts elevator_result.map(&:score).max.round(1)

puts '---'

custom = custom(master_list.dup)
print 'Custom average delay: '
puts custom.map(&:delay).inject { |sum, el| sum + el }.to_f / custom.count
print 'Custom maximum delay: '
puts custom.map(&:delay).max
print 'Custom average score: '
puts (custom.map(&:score).inject { |sum, el| sum + el }.to_f / custom.count).round(1)
print 'Custom maximum score: '
puts custom.map(&:score).max.round(1)
