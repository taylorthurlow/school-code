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

i = Float::INFINITY

transition_graph = [
  [0, 3, 5, 7, 6],
  [3, 0, 2, 1, 1],
  [5, 2, 0, 4, 3],
  [7, 1, 4, 0, i],
  [6, 1, 3, i, 0]
]

# transition_graph = [
#   [0, 1, 7, 0, 0],
#   [1, 0, 0, 3, 0],
#   [7, 0, 0, 2, 4],
#   [0, 3, 2, 0, 9],
#   [0, 0, 4, 9, 0]
# ]

nodes = []
transition_graph.length.times do |a|
  nodes << Node.new(a)
end

edges = []
transition_graph.length.times do |a|
  transition_graph[a].each_with_index do |j, k|
    edges << Edge.new(nodes[i], j, nodes[k]) if j.positive? && (j != Float::INFINITY)
  end
end

puts 'Result graph:'
puts "  Num nodes: #{nodes.count}"
puts "  Num edges: #{edges.count / 2}\n\n"

graph = Graph.new(nodes, edges)
kruskals_soln = graph.kruskals

puts 'Solution:'
kruskals_soln.each { |e| puts e }

puts ''
puts 'Sorted edges:'
graph.sorted_edges.each { |e| puts e }

puts ''
puts 'Testing edge_weight:'
puts graph.edge_weight(3, 4)
