#!/bin/bash

if [[ ! $1 ]] || [[ ! $2 ]]; then
	echo 'You must provide a guessing range.'
	echo 'Ex: player.bash 1 100'
	exit
fi

if [[ $1 -ge $2 ]]; then
	echo 'The lower value must actually be lower than the higher value.'
	echo 'Ex: player.bash 1 100'
	exit
fi

GAME_PORT=9999
DEALER_ADDRESS=127.0.0.1
GAME_OVER=0
ATTEMPTS=0
LOWEST_VAL=$1
HIGHEST_VAL=$2

# $1 = lowest guess allowed
# $2 = highest guess allowed
make_guess () {
	RANGE=$(($2 - $1))
	PICK=$(($RANDOM % ($RANGE + 1)))
	echo $(($PICK + $1))
}

# $1 = attempt number
# $2 = new guess
# $3 = response (high or low)
report () {
	if [[ ! "$RESPONSE" ]]; then
		echo Initial guess of $2.
	else
		echo Try \#$1: Action was $3\; I guess $2
	fi
}

echo "$LOWEST_VAL $HIGHEST_VAL" | nc $DEALER_ADDRESS $GAME_PORT

sleep 1

while [[ $GAME_OVER -eq 0 ]]; do
	if [[ "$RESPONSE" == "low" ]]; then
		LOWEST_VAL=$(($GUESS + 1))
	elif [[ "$RESPONSE" == "high" ]]; then
		HIGHEST_VAL=$(($GUESS - 1))
	elif [[ "$RESPONSE" == "win" ]]; then
		GAME_OVER=1
		echo I got it in $ATTEMPTS tries.
		break
	fi

	GUESS=$(make_guess $LOWEST_VAL $HIGHEST_VAL)
	ATTEMPTS=$((ATTEMPTS + 1))
	report $ATTEMPTS $GUESS $RESPONSE
	echo $GUESS | nc $DEALER_ADDRESS $GAME_PORT
	RESPONSE=$(nc -l $GAME_PORT)
	sleep 1
done
