#!/bin/bash

function comchar() {
    case "$1" in
        java|cpp|c)
            echo '//'
            ;;
        lisp|elisp)
            echo ';'
            ;;
        *)
            echo '#'
            ;;
    esac
}

COMMENT_CHARACTER=$(comchar $2)
TOTAL_LINES=$(cat $1 | wc -l | tr -d ' ')
COMMENTS=$(cat $1 | grep "^\s*$COMMENT_CHARACTER" | wc -l | tr -d ' ')
BLANK_LINES=$(cat $1 | grep "^\s*$" | wc -l | tr -d ' ')
ONLY_BRACES=$(cat $1 | grep "^[{}\s]+$" | wc -l | tr -d ' ')
ACTUAL_CODE=$(($TOTAL_LINES - $COMMENTS - $BLANK_LINES - $ONLY_BRACES))

echo "Total Lines: $TOTAL_LINES"
echo "Comments: $COMMENTS"
echo "Blank Lines: $BLANK_LINES"
echo "Only Braces: $ONLY_BRACES"
echo "Actual Code: $ACTUAL_CODE"