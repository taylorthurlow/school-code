#!/usr/bin/ruby

require 'csv'

results = CSV.open(ARGV[1], 'w') do |csv|
	csv << ['Id', 'Expected']
	File.open(ARGV[0], 'r') do |file_handle|
		index = 1
		file_handle.each_line do |line|
			csv << ["test_#{index}.jpg", line.to_f.round(2)]
			index += 1
		end
	end
end
