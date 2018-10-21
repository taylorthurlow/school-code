#!/bin/bash

GAME_PORT=9999
PLAYER_ADDRESS=127.0.0.1
GAME_OVER=0
ATTEMPTS=0

RESPONSE=$(nc -l $GAME_PORT)

LOWEST_VAL=$(echo $RESPONSE | cut -d ' ' -f1)
HIGHEST_VAL=$(echo $RESPONSE | cut -d ' ' -f2)
RANGE=$(($HIGHEST_VAL - $LOWEST_VAL))
PICK=$(($RANDOM % ($RANGE + 1)))
WINNING_NUMBER=$(($PICK + $LOWEST_VAL))

echo Winning number is $WINNING_NUMBER.

while [[ $GAME_OVER -eq 0 ]]; do
	RESPONSE=$(nc -l $GAME_PORT)
	sleep 1

	echo Player guesses $RESPONSE.
	ATTEMPTS=$((ATTEMPTS + 1))

	if [[ $RESPONSE -eq $WINNING_NUMBER ]]; then
		echo Congrats! That took $ATTEMPTS tries.
		echo "win" | nc $PLAYER_ADDRESS $GAME_PORT
		GAME_OVER=1
	elif [[ $RESPONSE -lt $WINNING_NUMBER ]]; then
		echo 'low' | nc $PLAYER_ADDRESS $GAME_PORT
	elif [[ $RESPONSE -gt $WINNING_NUMBER ]]; then
		echo 'high' | nc $PLAYER_ADDRESS $GAME_PORT
	fi
done
