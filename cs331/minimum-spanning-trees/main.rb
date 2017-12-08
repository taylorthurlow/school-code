# Require gems
require 'rubygems'
require 'bundler/setup'
Bundler.require(:default)

# Require libraries
require 'set'
require 'benchmark'

# Require all ruby files
Dir["#{File.dirname(__FILE__)}/app/*.rb"].each { |f| require f }

puts "=== Kruskal's and Prim's Algorithms Analysis Script ==="
puts ''

[false, true].each do |sparseness|
  [25, 100, 250, 500, 1000, 2500, 5000].each do |num_nodes|
    graph = nil
    time_graph = Benchmark.realtime do
      print 'Generating graph...'
      graph = Graph.new_random(num_nodes, sparseness)
    end
    puts " #{time_graph.round(5)} seconds, contains #{num_nodes} nodes, #{graph.edges.count} edges, #{sparseness ? 'dense' : 'sparse'}:"

    time_kruskals = Benchmark.realtime do
      print 'Performing kruskals... '
      graph.kruskals
    end
    puts "#{time_kruskals.round(3)} seconds"

    time_prims = Benchmark.realtime do
      print 'Performing prims... '
      graph.prims
    end
    puts "#{time_prims.round(5)} seconds"

    puts '--------------------------------------------------------'
  end
end
