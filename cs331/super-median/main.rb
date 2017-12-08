# Require gems
require 'rubygems'
require 'bundler/setup'
Bundler.require(:default)

# Require libraries
require 'set'
require 'benchmark'

# Require all ruby files
Dir["#{File.dirname(__FILE__)}/app/*.rb"].each { |f| require f }

puts '=== SuperMedian Algorithm Analysis Script ==='
puts ''

[100, 1000, 10000, 1000000].each do |size|
  sum_times = 0
  repeats = 100
  repeats.times do
    unsorted_list = Array.new(size) { rand(1..1000) }
    sum_times += Benchmark.realtime do
      unsorted_list.sort
    end
  end

  puts "#{size} entries: #{sum_times / repeats}"
end
