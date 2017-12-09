# Require gems
require 'rubygems'
require 'bundler/setup'
Bundler.require(:default)

# Require libraries
require 'set'
require 'benchmark'

# Require all ruby files
Dir["#{File.dirname(__FILE__)}/app/*.rb"].each { |f| require f }
include SuperMedian

puts '=== SuperMedian Algorithm Analysis Script ==='
puts ''

# puts 'Simple:'
# [100, 1000, 10000].each do |size|
#   sum_times = 0
#   repeats = 100
#   repeats.times do
#     unsorted_list = Array.new(size) { rand(1..1000) }
#     sum_times += Benchmark.realtime do
#       unsorted_list.sort
#     end
#   end
#
#   puts "#{size} entries: #{sum_times / repeats}"
# end

puts "\nSuper Median:"
RubyProf.start
[10000].each do |size|
  sum_times = 0
  repeats = 100
  repeats.times do
    unsorted_list = Array.new(size) { rand(1..1000) }
    sum_times += Benchmark.realtime do
      super_median(unsorted_list)
    end
  end
  # puts "#{size} entries: #{sum_times / repeats}"
end

result = RubyProf.stop
RubyProf::GraphPrinter.new(result).print(STDOUT)
