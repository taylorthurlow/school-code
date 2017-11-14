# Require gems
require 'rubygems'
require 'bundler/setup'
Bundler.require(:default)

# Require libraries
require 'set'
require 'byebug'

# Require all ruby files
Dir["#{File.dirname(__FILE__)}/app/*.rb"].each { |f| require f }

puts "=== Kruskal's and Prim's Algorithms Analysis Script ==="

# nodes = []
# 5.times { |i| nodes << Node.new(i + 1) }

# edges = []
# 4.times { |i| edges << Edge.new(nodes[i], rand(1..10), nodes[i + 1]) }

transition_graph = [
  [10],
  [5, 2],
  [7, 12, 4],
  [134, 1, 3, 8]
]

graph = Graph.new(nodes, edges)
result = graph.kruskals

debugger

puts '==== end ===='
