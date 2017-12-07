# Require gems
require 'rubygems'
require 'bundler/setup'
Bundler.require(:default)

# Require libraries
require 'set'

# Require all ruby files
Dir["#{File.dirname(__FILE__)}/app/*.rb"].each { |f| require f }

puts "=== Kruskal's and Prim's Algorithms Analysis Script ==="
puts ''

i = Float::INFINITY

test_matrix = [
  [0],
  [3, 0],
  [5, 2, 0],
  [7, 1, 4, 0],
  [6, 1, 3, i, 0]
]

# graph = Graph.new(test_matrix)

graph = Graph.new_random(10, true)

puts 'Result graph:'
puts "  Num nodes: #{graph.nodes.count}"
puts "  Num edges: #{graph.edges.count}\n\n"

kruskals_soln = graph.kruskals

puts 'Solution:'
kruskals_soln.each { |e| puts e }
