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
 
 2. Clicks the "Add tool" button in the option menu on the left then selects the icon with the gizmo to be added on the map (square, triangle, circle, flipper or ball)
 
 3. When user moves the mouse over the board the corresponding tile lights up green if the tile is free and red if the tile is already occupied by another gizmo
 
 4. If the user tries to add the gizmo to a occupied tile the tile blinks red
 
 5. If the tile is free the gizmo is added to the board
 
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

**Test inputs:** User



**Expected outputs:**

###Test 5
 
**Purpose:**

**Test inputs:**

**Expected outputs:**

###Test 6
 
**Purpose:**

**Test inputs:**

**Expected outputs:**

###Test 7
 
**Purpose:**

**Test inputs:**

**Expected outputs:**