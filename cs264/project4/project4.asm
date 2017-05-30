# Who:  Taylor Thurlow
# What: project4.asm
# Why:  Practicing usage of the stack
# When: 30 May 2017
# How:  Prompts for integers, stores them in the stack sorted, and finally
#       prints them in order.

.data
prompt_quantity:	.asciiz	"Please enter the desired number of integers [1,100]: "
prompt_integer:		.asciiz	"Enter an integer: "
prompt_search:		.asciiz "Search for what integer?: "
smallest_message:	.asciiz "The smallest integer in the list was: "
largest_message:	.asciiz "The largest integer in the list was: "
search_message:		.asciiz "binary_search result: "
success_message:	.asciiz "The specified integer WAS found in the array."
failure_message:	.asciiz "The specified integer WAS NOT found in the array."
array: 				.space 400	# space 100 integers (4 bytes * 100 ints = 400 bytes)

.text
.globl main


main:
	li		$s1, 0					# start for loop iterator at 0
	move	$fp, $sp				# set frame pointer for later

quantity:
	li		$v0, 4					# syscall 4 is print string
	la		$a0, prompt_quantity	# load address of prompt string
	syscall							# call print on string address

	li		$v0, 5					# syscall 5 is read integer
	syscall							# integer read is now in $v0
	move	$s0, $v0				# put integer read into $s0

	blt		$s0, 1, quantity		# re-prompt if too small
	bgt		$s0, 100, quantity		# re-prompt if too large

integers:
	li		$v0, 4					# syscall 4 is print string
	la		$a0, prompt_integer		# load address of prompt string
	syscall							# call print on string address

	li		$v0, 5					# syscall 5 is read integer
	syscall         				# integer read is now in $v0
	move	$a0, $v0				# put integer read in $a0
	jal		insert_integer			# call subroutine to insert integer

	addiu	$s1, $s1, 1				# increment iterator
	bne		$s1, $s0, integers		# continue if not finished
	beq		$s0, 1, print			# skip sort if array length 1

# sorting
outer_loop:							# determines when array iteration is done
	add		$t2, $zero, $zero		# $t2 is flag for complete sort
	move	$t3, $fp				# load frame pointer address into $t3
	addi	$t3, $t3, -4			# set $t3 to start of array
inner_loop:							# iterates array, checking if swap needed
	lw		$t4, 0($t3)				# set $t4 to current array element
	lw		$t5, -4($t3)			# set $t5 to next array element
	slt		$t6, $t4, $t5			# set $t6 to 1 if $t4 < $t5
	beq		$t6, $zero, continue	# break if swap not necessary
	addi	$t2, $zero, 1			# set flag to check again because swap was needed
	sw		$t4, -4($t3)			# perform swap
	sw		$t5, 0($t3)				# perform swap
continue:
	addi	$t3, $t3, -4			# set new iteration start location
	bne		$t3, $t0, inner_loop	# go to inner_loop if we haven't reached the end of the array
	bne 	$t2, $zero, outer_loop	# go to outer_loop if another pass is needed

print:
	li		$s1, 0						# reset iterator
	move 	$s2, $v0					# move start address into $s2

	print_loop:
		move	$t0, $s2				# get return value array start address
		sll		$t1, $s1, 2				# calculate offset from iterator
		addu	$t2, $t0, $t1			# add start address and offset to get element address
		lw		$a0, 0($t2)				# load integer value from that address for printing
		li		$v0, 1					# syscall 1 is print integer
		syscall

		beq		$s1, 0, flag_start		# set low and high on first iteration
		flagging:
		blt		$a0, $s3, flag_smallest	# set integer as smallest if smallest
		continue_after_flag_smallest:
		bgt		$a0, $s4, flag_largest	# set integer as largest if largest
		continue_after_flag_largest:

		la		$a0, ' '				# load space character for list delimiter
		li		$v0, 11					# syscall 11 is print character
		syscall

		addiu	$s1, $s1, 1				# increment iterator
		bne		$s1, $s0, print_loop	# continue if not finished
		j		print_result

	flag_start:
		addiu	$s3, $a0, 0				# always set integer as smallest on first iteration
		addiu	$s4, $a0, 0				# always set integer as largest on first iteration
		j		flagging

	flag_smallest:
		addiu	$s3, $a0, 0				# set smallest integer
		j		continue_after_flag_smallest

	flag_largest:
		addiu	$s4, $a0, 0				# set largest integer
		j		continue_after_flag_largest

print_result:
	la		$a0, '\n'				# load newline character
	li		$v0, 11					# syscall 11 is print character
	syscall

	li		$v0, 4					# syscall 4 is print string
	la		$a0, smallest_message	# load address of prompt string
	syscall

	addiu	$a0, $s3, 0				# load integer value from that address for printing
	li		$v0, 1					# syscall 1 is print integer
	syscall

	la		$a0, '\n'				# load newline character
	li		$v0, 11					# syscall 11 is print character
	syscall

	li		$v0, 4					# syscall 4 is print string
	la		$a0, largest_message	# load address of prompt string
	syscall

	addiu	$a0, $s4, 0				# load integer value from that address for printing
	li		$v0, 1					# syscall 1 is print integer
	syscall

search_median:
	la		$a0, '\n'				# load newline character
	li		$v0, 11					# syscall 11 is print character
	syscall

	li		$v0, 4					# syscall 4 is print string
	la		$a0, prompt_search		# load address of prompt string
	syscall

	li		$v0, 5					# syscall 5 is read integer
	syscall							# integer read is now in $v0
	move	$a0, $v0

	move	$a1, $s2
	li		$a2, 0					# low value index = 0
	addi	$a3, $s0, -1			# high value index = number of integers input - 1

	jal		binary_search

	move	$t0, $v0				# store return value for message printing

	la		$a0, '\n'				# load newline character
	li		$v0, 11					# syscall 11 is print character
	syscall

	beq		$t0, 0, print_failure
	beq		$t0, 1, print_success

	j		print_failure

print_success:
	li		$v0, 4					# syscall 4 is print string
	la		$a0, success_message	# load address of prompt string
	syscall
	j exit

print_failure:
	li		$v0, 4					# syscall 4 is print string
	la		$a0, failure_message	# load address of prompt string
	syscall

exit:
	li		$v0, 10
	syscall

# push an integer onto the top of the stack
# Arguments: $a0: the integer to be stored
# Returns:   $v0: the address of the first, lowest integer (lowest address)
insert_integer:
	addi	$sp, $sp, -4		# allocate bytes words of space on the stack
	sw		$a0, 0($sp)			# store integer

	move	$t0, $fp			# load frame pointer address into $t0
	addi	$t0, $t0, -4		# jump to start of array because there seems
								# to already be a single 32-bit value (1)
	sllv	$t1, $s1, 2			# calculate offset based on current loop iteration
	sub		$t0, $t0, $t1		# subtract offset to $t0 to get to end of array
	move	$v0, $t0			# load return value: address of first array element
	jr		$ra					# end subroutine, return to caller

# perform a search for a specific integer in the array
# Arguments:	$a0: integer value to search for
#				$a1: address of start of array on stack
#				$a2: lowest index in array
#				$a3: highest index in array
# Returns:		$v0: 0 if not found, 1 if found
binary_search:
	addi	$sp, $sp, -4			# room on stack for return address
	sw		$ra, ($sp)

	blt		$a3, $a2, not_found
	add		$t0, $a2, $a3			# compute median index between $a2 and $a3
	li		$t1, 2
	div		$t0, $t1
	mflo	$t0						# store median index in $t0
	sll		$t1, $t0, 2				# store offset for median in $t1

	add		$t1, $t1, $a1			# add offset to address to get median address
	lw		$t2, ($t1)				# load median value into $t2

	beq		$t2, $a0, found			# median matches input value
	bgt		$a0, $t2, val_greater

	addi	$a3, $t0, -1			# start new recursion of binary_search with high = mid - 1
	jal		binary_search
	j		branch

	val_greater:
		addi	$a2, $t0, 1			# start new recursion of binary_search with low = mid + 1
		jal		binary_search
		j		branch

	found:
		li		$v0, 1
		j		branch

	not_found:
		li		$v0, 0
		j		branch

	branch:
		lw		$ra, ($sp)				# load return address from stack
		addi	$sp, $sp, 4				# restore stack pointer
		jr		$ra