# Taylor Thurlow
# CS 264 Spring 2017
# Cal Poly Pomona
# 4/21/2017
# Lab 1

.text

.globl	main
	
main:
	li	$s0, 0			# start for loop iterator at 0

# the loop which grabs integer values from the console and
# stores them into the array
prompt_loop:
	li	$v0, 4			# syscall 4 is print string
	la	$a0, prompt		# load address of prompt string
	syscall				# call print on string address

	li	$v0, 5			# syscall 5 is read integer
	syscall         	# integer read is now in $v0
	move	$t0, $v0	# put integer read in $t0

	la	$a0, array		# load address of array into $a0

	sll	$s1, $s0, 2		# get iterator i from $s0, multiply by 4
						# and place resulting offset in $s1

	addu	$t1, $a0, $s1	# add offset to array address to get
							# the array entry location we want
	sw	$t0, ($t1)		# write integer to array memory location

	addiu	$s0, $s0, 1	# increment iterator
	beq	$s0, 20, exit_prompt	# break loop if last iteration

	j	prompt_loop		# begin loop again

exit_prompt:
	li	$v0, 4			# syscall 4 is print string
	la	$a0, finished	# load address of finished string
	syscall				# call print on string address

	li	$s0, 0			# start for loop iterator at 0

print_loop:
	la	$a0, array		# load address of array into $a0

	sll $s1, $s0, 2		# get iterator i from $s0, multiply by 4
						# and place resulting offset in $s1

	addu	$t1, $a0, $s1	# add offset to array address to get
							# the array entry location we want
	lw	$a0, ($t1)		# load array element into $a0

	li	$v0, 1			# syscall 1 is print integer
	syscall				# print integer in $a0 to console

	li	$v0, 11			# syscall 11 is print character
	la	$a0, ' '		# load space character
	syscall				# call print on character

	addiu	$s0, $s0, 1	# increment iterator
	beq	$s0, 20, exit_print	# break loop if last iteration

	j print_loop

exit_print:
	li	$v0, 10			# syscall 10 is graceful exit
	syscall

.data

# space for integer array of size 20 (4 * 20 = 80)
array: .space 80

prompt:	.asciiz	"Please enter a value: "

finished: .asciiz "\nValue entry complete. Printing stored values.\n\n"
