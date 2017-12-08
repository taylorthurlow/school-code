# Require gems
require 'rubygems'
require 'bundler/setup'
Bundler.require(:default)

# Require libraries
require 'set'
require 'benchmark'
require 'ruby-prof'

# Require all ruby files
Dir["#{File.dirname(__FILE__)}/app/*.rb"].each { |f| require f }

puts "=== Kruskal's and Prim's Algorithms Analysis Script ==="
puts ''

[false].each do |sparseness|
  [50].each do |num_nodes|
    # RubyProf.start
    graph = Graph.new_random(num_nodes, sparseness)
    # graph_result = RubyProf.stop
    puts "#{num_nodes} nodes, #{sparseness ? 'dense' : 'sparse'}:"
    # RubyProf::FlatPrinter.new(graph_result).print(STDOUT)

    # RubyProf.start
    # graph.kruskals
    # kruskals_result = RubyProf.stop
    # RubyProf::FlatPrinter.new(kruskals_result).print(STDOUT)

    RubyProf.start
    graph.prims
    prims_result = RubyProf.stop
    RubyProf::FlatPrinter.new(prims_result).print(STDOUT)

    puts '--------------------------------------------------------'
  end
end

# 500.times do
#   graph = Graph.new_random(10, false)
#
#   puts "Num nodes: #{graph.nodes.count}"
#   puts "Num edges: #{graph.edges.count}"
#
#   kruskals_soln = graph.kruskals
#   puts "Kruskals Solution: (#{kruskals_soln.edges.count} edges, #{kruskals_soln.nodes.count} nodes): #{kruskals_soln.edges.map(&:weight).sum}"
#   # kruskals_soln.edges.each { |e| puts e }
#
#   prims_soln = graph.prims
#   puts "Prims Solution (#{prims_soln.edges.count} edges, #{prims_soln.nodes.count} nodes): #{prims_soln.edges.map(&:weight).sum}"
#   # prims_soln.edges.each { |e| puts e }
#
#   raise 'Solution mismatch error' if kruskals_soln.edges.map(&:weight).sum != prims_soln.edges.map(&:weight).sum
#
#   puts '-------------------------------'
# end
