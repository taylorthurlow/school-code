# Require gems
require 'rubygems'
require 'bundler/setup'
Bundler.require(:default)

# Require all ruby files
Dir["#{File.dirname(__FILE__)}/app/*.rb"].each { |f| require f }

puts "=== Kruskal's and Prim's Algorithms Analysis Script ==="
