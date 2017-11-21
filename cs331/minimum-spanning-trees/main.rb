# Require gems
# require 'rubygems'
# require 'bundler/setup'
# Bundler.require(:default)

# Require libraries
require 'set'
# require 'byebug'

# Require all ruby files
Dir["#{File.dirname(__FILE__)}/app/*.rb"].each { |f| require f }

puts "=== Kruskal's and Prim's Algorithms Analysis Script ==="
puts ''

transition_graph = [
  [0, 3, 5, 7, 6],
  [3, 0, 2, 1, 1],
  [5, 2, 0, 4, 3],
  [7, 1, 4, 0, 8],
  [6, 1, 3, 8, 0]
]

# transition_graph = [
#   [0, 1, 7, 0, 0],
#   [1, 0, 0, 3, 0],
#   [7, 0, 0, 2, 4],
#   [0, 3, 2, 0, 9],
#   [0, 0, 4, 9, 0]
# ]

nodes = []
transition_graph.length.times do |i|
  nodes << Node.new(i)
end

edges = []
transition_graph.length.times do |i|
  transition_graph[i].each_with_index do |j, k|
    edges << Edge.new(nodes[i], j, nodes[k]) if j.positive?
  end
end

puts 'Result graph:'
puts "  Num nodes: #{nodes.count}"
puts "  Num edges: #{edges.count / 2}\n\n"

graph = Graph.new(nodes, edges)
kruskals_soln = graph.kruskals

puts 'Solution:'
kruskals_soln.each { |e| puts e }
