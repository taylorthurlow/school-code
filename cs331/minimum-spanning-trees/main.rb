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

# test_matrix = [
#   [0],
#   [3, 0],
#   [1, 2, 0],
#   [7, 1, 4, 0],
#   [3, 8, 3, 1, 0],
#   [6, 9, 4, 5, 2, 0]
# ]

# graph = Graph.new(transition_matrix: test_matrix)

500.times do
  graph = Graph.new_random(10, false)

  puts "Num nodes: #{graph.nodes.count}"
  puts "Num edges: #{graph.edges.count}"

  kruskals_soln = graph.kruskals
  puts "Kruskals Solution: (#{kruskals_soln.edges.count} edges, #{kruskals_soln.nodes.count} nodes): #{kruskals_soln.edges.map(&:weight).sum}"
  # kruskals_soln.edges.each { |e| puts e }

  prims_soln = graph.prims
  puts "Prims Solution (#{prims_soln.edges.count} edges, #{prims_soln.nodes.count} nodes): #{prims_soln.edges.map(&:weight).sum}"
  # prims_soln.edges.each { |e| puts e }

  raise 'Solution mismatch error' if kruskals_soln.edges.map(&:weight).sum != prims_soln.edges.map(&:weight).sum

  puts '-------------------------------'
end
