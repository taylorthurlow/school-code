# Who:  Taylor Thurlow
# What: project3.asm
# Why:  Practicing usage of the stack
# When: 17 May 2017
# How:  Prompts for integers, stores them in the stack sorted, and finally
#       prints them in order.

.data
prompt_quantity:	.asciiz	"Please enter the desired number of integers [1,100]: "
prompt_integer:		.asciiz	"Enter an integer: "
array: 				.space 400	# space 100 integers (4 bytes * 100 ints = 400 bytes)

.text
.globl main


main:
	li	$s1, 0					# start for loop iterator at 0
	move	$fp, $sp			# set frame pointer for later

quantity:
	li	$v0, 4					# syscall 4 is print string
	la	$a0, prompt_quantity	# load address of prompt string
	syscall						# call print on string address

	li	$v0, 5					# syscall 5 is read integer
	syscall						# integer read is now in $v0
	move	$s0, $v0			# put integer read into $s0

integers:
	li	$v0, 4					# syscall 4 is print string
	la	$a0, prompt_integer		# load address of prompt string
	syscall						# call print on string address

	li	$v0, 5					# syscall 5 is read integer
	syscall         			# integer read is now in $v0
	move	$a0, $v0			# put integer read in $a0
	jal	insert_integer			# call subroutine to insert integer

	addiu	$s1, $s1, 1			# increment iterator
	bne	$s1, $s0, integers		# continue if not finished

print:
	li	$s1, 0					# reset iterator
	move $s2, $v0				# move start address into $s2

	print_loop:
		move	$t0, $s2			# get return value array start address
		sll	$t1, $s1, 2				# calculate offset from iterator
		addu	$t2, $t0, $t1		# add start address and offset to get element address
		lw	$a0, 0($t2)				# load integer value from that address for printing
		li	$v0, 1					# syscall 1 is print integer
		syscall

		la	$a0, ' '				# load space character for list delimiter
		li	$v0, 11					# syscall 11 is print character
		syscall

		addiu	$s1, $s1, 1			# increment iterator
		bne	$s1, $s0, print_loop	# continue if not finished

exit:
	li	$v0, 10
	syscall
	
# Insert an integer onto the stack, and sorts them afterwards
# Arguments: $a0: the integer to be stored
# Returns:   $v0: the address of the first, lowest integer (lowest address)
insert_integer:
	addi	$sp, $sp, -4		# allocate bytes words of space on the stack
	sw	$a0, 0($sp)				# store integer

	# sorting

	beq	$s1, $zero, skip		# skip sorting if first integer

	move	$t0, $fp			# load frame pointer address into $t0
	addi	$t0, $t0, -4		# jump to start of array because there seems
								# to already be a single 8-bit value (1)
	sllv	$t1, $s1, 2			# calculate offset based on current loop iteration
	sub	$t0, $t0, $t1			# subtract offset to $t0 to get to end of array

	outer_loop:						# determines when array iteration is done
		add	$t2, $zero, $zero		# $t2 is flag for complete sort
		move	$t3, $fp			# load frame pointer address into $t3
		addi	$t3, $t3, -4		# set $t3 to start of array
	inner_loop:						# iterates array, checking if swap needed
		lw	$t4, 0($t3)				# set $t4 to current array element
		lw	$t5, -4($t3)			# set $t5 to next array element
		slt	$t6, $t4, $t5			# set $t6 to 1 if $t4 < $t5
		beq	$t6, $zero, continue	# break if swap not necessary
		addi	$t2, $zero, 1		# set flag to check again because swap was needed
		sw	$t4, -4($t3)			# perform swap
		sw	$t5, 0($t3)				# perform swap
	continue:
		addi	$t3, $t3, -4		# set new iteration start location
		bne	$t3, $t0, inner_loop	# go to inner_loop if we haven't reached the end of the array
		bne $t2, $zero, outer_loop	# go to outer_loop if another pass is needed
	skip:

	move	$v0, $t0			# load return value: address of first array element
	jr	$ra						# end subroutine, return to caller
