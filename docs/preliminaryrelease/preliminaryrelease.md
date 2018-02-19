#Preliminary Release

**Group:** PR_Th1

Bence Sebestyen @xsb15143

Ioan Luca @xqb16141

Lyubomir Ivanov @kwb15150

Martin Kollie @vib15168

Niklavs Meiers @isb15151

## Validation Testing Strategy
*  Test number
*  Purpose of test
*  Test inputs (including GUI options selected) - enough detail that a third party could execute the test.
*  Expected outputs - enough detail that a third party could determine whether the test has completed successfully (included expected change to the GUI, error messages, file changes, ...)
* (^^^ delete latter)


###Test
 
**Purpose:**

**Test inputs:**

**Expected outputs:**

### Test 1
**Purpose:** Test whether adding gizmos functionality works

**Test inputs:** User:
 
 1. Starts at the "Editor mode"
 
 2. Clicks the "Add tool" button in the option menu on the left then selects the icon with the gizmo to be added on the map (square, triangle, circle or flipper)
 
 3. When user moves the mouse over the board the corresponding tile lights up green if the tile is free and red if the tile is already occupied by another Gizmo
 
 4. If the user tries to add the Gizmo to a occupied tile the tile blinks red
 
 5. If the tile is free the Gizmo is added to the board
 

 **Expected outputs:** The newly added gizmo is visible on the map
 
 ###Test 2
 **Purpose:** Test whether adding absorber works
 
 **Test inputs:** User:
 
 1. Starts at the "Editor mode"
 
 2. Clicks "Add tool" button in the menu on the left then selects the absorber icon
 
 3. User clicks on the screen where the start of the gizmo should be.
 
 4. Now when user moves mouse around the bord the tiles light up green if such observer can be added ( no other gizmos are in the selected area) or red otherwise
 
 5. If the user tries to add observer in the area which is occupied by another gizmo the area selected bliks red otherwise the absorber is added to the board.
 
 **Expected outputs:** The newly added absorber is visible on the screen
 
 ###Test 3
 
**Purpose:** Test whether removing gizmo works

**Test inputs:** User:

 1. Starts at the "Editor mode"
 
 2. Clicks "Remove tool" button
 
 3. When mouse moves over the board the corresponding tile lights up yellow
 
 4. If tile that is not occupied by any gizmo is clicked nothing happens
 
 5. If tile that is occupied by a gizmo (or is part of the gizmo in case of flipper and absorber) the whole gizmo is removed from the board

**Expected outputs:** The gizmo is no longer visible on the board

###Test 4
 
**Purpose:** Test whether connecting gizmo to an other gizmo or key press works

**Test inputs:** User:

1. Starts at the "Editor mode"

2. Clicks "Connect tool" button

3. When mouse moves over the board the corresponding tile lights up yellow

4. When user presses on tile that is occupied by a gizmo it lights up green, at the bottom of the screen "Click on another gizmo or press a key to create a trigger"
text appears.

5. User then clicks on another gizmo or presses a key on the keyboard respectively

6. User then launches the play mode by pressing the "Play mode" button. This takes user to the playmode screen that no longer have have all the editing tools

7. User then presses the key that was liked for instance to the flipper gizmo and sees does it start to rotate or in case the gizmo was liked to another gizmo whether
the gizmo performs an action when the liked gizmo is touched by the ball.

**Expected outputs:** Whenever the liked key is pressed or linked gizmo is touched by ball the corresponding gizmo performs its action.

###Test 5
 
**Purpose:** Test whether clear playing area functionality works

**Test inputs:** User:

1. Starts at the "Editor mode" 

2. Adds couple of gizmos on the map

3. Clicks on "Clear playing area" button

**Expected outputs:** All the gizmos are removed from the map leaving it completely empty.

###Test 6 // Discuss this
 
**Purpose:** Test whether adding a ball functionality works

**Test inputs:** User:

1. Starts at the "Editor mode" 

2. Selects and ball gizmo icon

3. User then clicks on the board where he wish the ball to be added. If the place is occupie by another gizmo the tile blinks red if not the ball is added to the board

4. Next dialog box should appear asking the user to input the balls starting velocity by specifying the length of x and y vectors

5. In special case when user clicks on the absorber the ball is placed in its lower right corner and no dialog box appears. The starting velocity is set to 50L/s upwards

6. The user then clicks the "Playing mode" button and launches the game

**Expected outputs:** Ball starts from the specified position with the correct velocity. In case ball was added inside the absorber in launches when the absorber is triggered
with the velocity 50L/s upwards


###Test 7
 
**Purpose:** Test whether move gizmo functionality works

**Test inputs:** User:

1. Starts at the "Editor mode" 

2. Adds couple of gizmos to the board

3. Selects "Move gizmos tool" button

4. Clicks on a gizmo he wishes to move the tiles that gizmo occupies turns yellow

5. User then clicks on the tile that is occupied by another gizmo the tile turns read indicating the location is not available

6. User clicks on a tile that is free, the gizmo disappears from the previous location and appears where the user had clicked

**Expected outputs:** The gizmo has disappeared from the previous location ad no occupies the new location

###Test 8
 
**Purpose:** Test whether rotate gizmo functionality works

**Test inputs:** User:

1. Starts at the "Editor mode" 

2. Adds all the possible gizmos on the board

3. Selects "Rotate tool"

4. Then clicks on each gizmo to see whether it rotates by 90° each click

**Expected outputs:** Each click on the gizmo should rotate it by 90°

###Test 9
 
**Purpose:**

**Test inputs:**

**Expected outputs:**

###Test 10
 
**Purpose:**

**Test inputs:**

**Expected outputs:**
