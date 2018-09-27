#!/bin/bash

DICT=$(cat /usr/share/dict/words)
NUM_WORDS=$(cat $DICT | wc -l)
INDEX=$RANDOM % $NUM_WORDS 
WORD=$(cat $DICT | head -n $INDEX | tail -n 1)
echo $WORD
