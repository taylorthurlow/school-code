#!/usr/bin/env perl

while(<>) { push @lines, $_ }
foreach $line(@lines) {
  @split_line = split /\s+/, $line;
  push @data, @split_line[6] . "   " . join(" ", @split_line[10 .. $#split_line]);
}
print $_ . "\n" foreach (sort @data);
