#!/bin/bash

cat /usr/share/dict/words | head -n $(($(od -N 4 -t uL -An /dev/urandom | tr -d " ") % $(cat /usr/share/dict/words | wc -l))) | tail -n 1

# Pipes the dictionary into head/tail to get a single entry. The entry is selected
# using /dev/urandom because $RANDOM does not provide suitably large numbers for random
# selection. Instead 'od' is used to get 4 bytes from the top of /dev/urandom, as an
# unsigned long. That number is piped into 'tr' in order to remove some empty spaces
# that come from /dev/urandom. That random number is used with the line count of the
# dictionary to select a random number between 0 and the number of words in the
# dictionary.
