#!/usr/bin/ruby

require 'csv'
require 'fileutils'

# Make sure we have the proper directories
FileUtils.mkdir_p 'data/train'
FileUtils.mkdir_p 'data/validation'

# Separate our train and validation datasets
validation_percentage = 0.2

Dir['data/images/*'].each do |f|
  name = f.split('/').last

  FileUtils.mkdir_p "data/train/#{name}"
  FileUtils.mkdir_p "data/validation/#{name}"

  images = Dir[f + '/*'].shuffle
  training_count = (images.count * (1 - validation_percentage)).floor

  training_images = images[0..training_count - 1]
  validation_images = images[training_count..images.count - 1]

  training_images.each { |ti| FileUtils.cp(ti, "data/train/#{name}/#{ti.split('/').last}") }
  validation_images.each { |vi| FileUtils.cp(vi, "data/validation/#{name}/#{vi.split('/').last}") }
end
