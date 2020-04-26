# Use Cases

## Use Case 1: Add Program Block

### Main Success Scenario

1. The user moves the mouse cursor over a block in the Palette,then presses the left mouse key, then moves the mouse cursor to the Program Area, and then releases the left mouse key.

2. The system adds a new block of the same type to the Program Area.

### Extensions

1a. When the user releases the mouse key, one of the block’s connectors is near a compatible opposite connector of another block.

 1. The system adds a new block of the same type to the ProgramArea; the new block is inserted into an existing group ofconnected blocks at the matching connection point.


2a. Maximum number of blocks is reached.

 1. All blocks disappear from the Palette.


	## Use Case 2: Run Program

	### Main Success Scenario

	1. User presses F5

	2. Next block is higlighted

	3. User presses F5

	4. Highlighted block is executed

	repeat steps 2, 3 and 4 until program is finished.

	### Extensions

	1a. Program Area does not contain exactly one connected block group.

		 1. Execution does not start.

	1b. Program Area contains a condition block.

		 1. Execution does not start.

	1b. Program Area contains a surrounding block without condition.

		 1. Execution does not start


## Use Case 3: Reset Game World

### Main Success Scenario

1. User presses Escape

2. The program stops running

3. The game world resets to the original state

### Extensions

1a. User modifies program while it's running

 1. The program stops running
 
 2. The game world resets to the original state


## Use Case 4: Move Program Block

### Main Succes Scenario
1. The user moves the mouse cursor over a block in the Program Area,then presses the left mouse key, then moves the mouse cursor to another place in the Program Area, and then releases the left mouse key.
2. The Program Block gets removed from the old spot and added to the new spot in the Program Area.

### Extensions

1a. There are blocks connected in the same cavity below the current block

 1. These blocks are moved together with the current block.

1b. When there is a block connected above the current block.

 1. The connection between these blocks is broken apart.

1c. There are blocks connected to the right of the current block

 1. These blocks are moved together with the current block.

1d. When there is a block connected to the left of the current block.

 1. The connection between these blocks is broken apart.

1e. Programming Block is initially inside a While or If block.

 1. The system deattaches the Programming Block and updates the size of the If/While Block.
 
2a. The block is released over the Palette

 1. The block and the connected blocks are removed 