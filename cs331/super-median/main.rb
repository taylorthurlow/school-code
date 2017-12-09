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
include QuickSort

puts '=== SuperMedian Algorithm Analysis Script ==='
puts ''

array_sizes = [100, 1000, 10000, 100000, 1000000]
max_array_value = 10000

puts 'Ruby (C) Quick Sort:'
array_sizes.each do |size|
  unsorted_list = Array.new(size) { rand(1..max_array_value) }
  total_time = Benchmark.realtime { unsorted_list.sort }
  puts total_time
end

puts "\nRuby Quick Sort:"
array_sizes.each do |size|
  unsorted_list = Array.new(size) { rand(1..max_array_value) }
  total_time = Benchmark.realtime do
    sorted = QuickSort.quick_sort(unsorted_list, 0, size - 1)
    sorted[size / 2]
  end
  puts total_time
end

puts "\nSuper Median:"
array_sizes.each do |size|
  unsorted_list = Array.new(size) { rand(1..max_array_value) }
  total_time = Benchmark.realtime { SuperMedian.super_median(unsorted_list) }
  puts total_time
end
