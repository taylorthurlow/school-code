This implementation of the guessing game uses netcat. The dealer should be started first.

It is slightly different from the normal implementation in that the player must specify to
the dealer which low and high values to be used for the game. It's pretty simple:

./dealer.bash
./player.bash 1 10000

Where 1 is the low value, and 10000 is the high value. The dealer will pick a winning
number appropriately. You MUST supply these values or the script will tell you what you did
wrong.