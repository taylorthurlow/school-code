#!/usr/bin/ruby

require 'csv'
require 'fileutils'

# Make sure we have the proper directories
FileUtils.mkdir_p 'train/male'
FileUtils.mkdir_p 'train/female'
FileUtils.mkdir_p 'validation/male'
FileUtils.mkdir_p 'validation/female'

# Separate our train and validation datasets
validation_percentage = 0.2
image_count = Dir[File.join('train', '*')].count { |file| File.file?(file) }
training_count = (image_count * (1 - validation_percentage)).floor
validation_count = image_count - training_count

train_data = CSV.read('train_target.csv').drop(1)
validation_data = train_data.pop(validation_count)
puts "Total Images: #{image_count}"
puts "Training data size: #{train_data.count}"
puts "Validation data size: #{validation_data.count}"

def move(row, source_dir, dest_dir)
  classifier = row[1].to_i == 1 ? 'male' : 'female'
  regex = row[0].match(/#{source_dir}_(\d+)\.(\w+)/)

  if regex.nil?
    puts "Error: #{row.join(',')}"
    puts "Regex: #{source_dir}_(\\d+)\.(\\w+)"
  end

  id = regex[1].to_i
  extension = regex[2]

  FileUtils.mv("#{source_dir}/#{row[0]}", "#{dest_dir}/#{classifier}/#{classifier}#{id}.#{extension}")
end

validation_data.each { |vd| move(vd, 'train', 'validation') }
train_data.each      { |td| move(td, 'train', 'train') }
