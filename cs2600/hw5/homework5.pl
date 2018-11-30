#!/usr/bin/env perl

# Parses output of https://nocable.org/tv-listings/bm95-pomona-ca-91768
# to show shows on channels 2.1, 4.1, and 7.1.

while(<>) { push @lines, $_ }
$data = join "", @lines;
# @matches = $data =~ /<b>2\.1<\/b>\s+<\/th>\s+<td[\S\s]+?(?:title)">([\w ]+)/;

@r = matchPage("2\.1");
print("2.1 - " . $r[0]);
print("\n");
@r = matchPage("4\.1");
print("4.1 - " . $r[0]);
print("\n");
@r = matchPage("7\.1");
print("7.1 - " . $r[0]);
print("\n");

sub matchPage {
  return $data =~ /<b>$_[0]<\/b>\s+<\/th>\s+<td[\S\s]+?(?:title)">([\w ]+)/;
}
