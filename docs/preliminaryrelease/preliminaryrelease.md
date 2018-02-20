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
**Purpose:** Test adding gizmo on an empty tile

**Test inputs:** User:
 
 1. Starts at the "Editor mode"
 
 2. Clicks the "Add tool" button in the option menu on the left
  
 3. Selects the icon with the gizmo to be added on the map (square, triangle, circle or flipper)
 
 4. When mouse is moved over the board the corresponding tile lights up green (meaning the tile is free)
 
 5. The mouse is clicked and the desired gizmo appears on the corresponding tile
 
 6. A status label shows that a gizmo has been successfully added at a particular location 
 
 **Expected outputs:** The newly added gizmo is visible on the map
 
 
 ### Test 2
**Purpose:** Test adding gizmo on an occupied tile
 
**Test inputs:** User:
  
 1. Starts at the "Editor mode"
  
 2. Clicks the "Add tool" button in the option menu on the left
   
 3. Selects the icon with the gizmo to be added on the map (square, triangle, circle or flipper)
  
 4. When mouse is moved over the board the corresponding tile lights up red (meaning the tile is occupied by another gizmo)
  
 5. The mouse is clicked and no change is done (tile still occupied by the same gizmo)
 
 6. A status label shows that the gizmo has not been added as this particular location is not empty
  
**Expected outputs:** No visible changes to the map, configurations remain the same
  
 
 ###Test 3
**Purpose:** Test adding absorber on empty space
 
**Test inputs:** User:
 
 1. Starts at the "Editor mode"
 
 2. Clicks "Add tool" button in the menu on the left then selects the absorber icon
 
 3. Mouse is clicked where the start of the gizmo should be and the mouse button is held pressed 
 
 4. Now when mouse is dragged around the bord the tiles light up green (meaning space is not occupied by other gizmos)
 
 5. Mouse is released and the absorber is added in the desired location with the specified size
 
 6. A status label shows that an absorber has been added at a particular location
 
 **Expected outputs:** The newly added absorber is visible on the screen
 
 
  ###Test 4
**Purpose:** Test adding absorber on occupied space (or partly occupied)
  
**Test inputs:** User:
  
  1. Starts at the "Editor mode"
  
  2. Clicks "Add tool" button in the menu on the left then selects the absorber icon
  
  3. Mouse is clicked where the start of the gizmo should be and the mouse button is held pressed 
  
  4. Now when mouse is dragged around the bord the tiles light up red (meaning part of the space is occupied by other gizmos)
  
  5. Mouse is released and no change is visible - absorber is not added
  
  6. If the desired location is completely free the absorber is added to the board, otherwise nothing is changed
 
  7. A status label shows that an absorber has not been added as this particular location is not entirely free 
 
**Expected outputs:** No visible changes to the map, configurations remain the same
 
# TO REFACTOR TESTS 5 AND ONWARDS! 
 
 ###Test 5
 
**Purpose:** Test whether removing gizmo works as expected

**Test inputs:** User:

 1. Starts at the "Editor mode"
 
 2. Clicks "Remove tool" button in the menu on the left
 
 3. When mouse moves over the board the corresponding tile lights up yellow
 
 4. If tile that is not occupied by any gizmo is clicked nothing happens
 
 5. If tile that is occupied by a gizmo (or is part of the gizmo in case of flipper and absorber) the whole gizmo is removed from the board

**Expected outputs:** The gizmo is no longer visible on the board


###Test 6
 
**Purpose:** Test whether connecting gizmo to another gizmo or key press triggering works

**Test inputs:** User:

1. Starts at the "Editor mode"

2. Clicks "Connect tool" button in the menu on the left

3. When mouse moves over the board the corresponding tile lights up yellow

4. When user presses on tile that is occupied by a gizmo it lights up green
 
5. A status label appears at the bottom saying "Click on another gizmo or press a key to create a trigger"

6. If user then clicks on another gizmo or presses a key on the keyboard respectively the action is successfully connected, otherwise nothing changes

7. User then launches the Play mode by pressing the "Play mode" button

8. In Play mode, user can press a key linked to a particular gizmo or wait for a collision with a linked gizmo respectively to see the made connection in action

**Expected outputs:** Whenever the linked key is pressed or linked gizmo is touched by ball the corresponding gizmo performs its action.


###Test 7
 
**Purpose:** Test whether clear playing area functionality works

**Test inputs:** User:

1. Starts at the "Editor mode" 

2. Adds some gizmos to the map

3. Clicks on "Clear playing area" button

**Expected outputs:** All the gizmos are removed from the map leaving it completely empty.


###Test 8 // Discuss this
 
**Purpose:** Test whether adding a ball functionality works

**Test inputs:** User:

1. Starts at the "Editor mode" 

2. Clicks "Add tool" button in the menu on the left then selects the absorber icon

3. Selects the ball icon from the panel on the right 

4. User then clicks on the board where he wishes the ball to be added. 

5. If the place is occupied by another gizmo the tile blinks red and nothing happens, otherwise the ball is added to the board

6. A dialog box should appear asking the user to input the balls starting velocity by specifying the length of x and y vectors

7. In special case when user clicks on the absorber, the ball is placed in its lower right corner and no dialog box appears. The starting velocity is set to 50L/s upwards

**Expected outputs:** Ball starts from the specified position with the correct velocity. In case ball was added inside the absorber in launches when the absorber is triggered
with the velocity 50L/s upwards


###Test 9
 
**Purpose:** Test whether move gizmo functionality works

**Test inputs:** User:

1. Starts at the "Editor mode" 

2. Adds couple of gizmos to the board

3. Selects "Move gizmos tool" button

4. Clicks on a gizmo he wishes to move the tiles that gizmo occupies turns yellow

5. User then clicks on the tile that is occupied by another gizmo the tile turns read indicating the location is not available

6. User clicks on a tile that is free, the gizmo disappears from the previous location and appears where the user had clicked

**Expected outputs:** The gizmo has disappeared from the previous location ad no occupies the new location


###Test 10
 
**Purpose:** Test whether rotate gizmo functionality works

**Test inputs:** User:

1. Starts at the "Editor mode" 

2. Adds some gizmos to the board 

3. Selects "Rotate tool" from the menu on the left

4. Clicks on any gizmo on the board to rotate it by 90° clockwise each click

**Expected outputs:** Each click on the gizmo should rotate it by 90°


###Test 11 
 
**Purpose:** Test whether a newly configurated map can be properly saved   

**Test inputs:** User: 

1. Starts at the "Editor mode"
 
2. Add at least one gizmo of each type to the board

3. Clicks "Save As" icon located in the top toolbar

4. A file explorer pops up prompting the user to choose a desired location for the file 

5. Types in the name of the file to be saved, for example filename.gizmo

6. Clicks "Save".  

7. A status label shows that the game configuration has been successfully saved.

**Expected outputs:** Game configuration has successfully been saved to filename.gizmo located in the specified directory


###Test 12
 
**Purpose:** Test whether an already saved map can be saved (using "Save", not "Save As")    

**Test inputs:** User: 

1. Does steps 1-7 described in Test 9

2. Change the board layout

3. Clicks "Save" icon located in the top toolbar

4. A status label shows that the game configuration have been successfully saved.

**Expected outputs:** Game configuration has successfully been saved, overwriting the file with the new configurations 


###Test 13
 
**Purpose:** Test whether already saved configurations are loaded properly in Editor mode 

**Test inputs:** User:

1. Starts at the "Editor mode"

2. Clicks "Load" icon located in the top toolbar

3. Navigates to the desired location and selects the file to be loaded, for example filename.gizmo

4. Clicks "Load"

5. Game configurations are loaded and displayed on the board

6. The status label shows that the game configurations have been successfully loaded

**Expected outputs:** The configurations are displayed on the screen, ready for further editing


###Test 14
 
**Purpose:** Test whether already saved configurations are loaded properly in Play mode 

**Test inputs:** User:

1. Starts at the "Play mode"

2. Clicks "Menu" icon in the game toolbar 

3. Selects "Load" in the currently displayed pause menu

4. Navigates to the desired location and selects the file to be loaded, for example filename.gizmo

5. Clicks "Load"

6. Game configurations are loaded and displayed on the blurred in the background playing area

7. Exiting the pause menu brings the user back to the game with the newly loaded game configurations

**Expected outputs:** The configurations are displayed on the screen ready for play 


###Test 15
 
**Purpose:** Test whether switching from Editor to Play mode works 

**Test inputs:** User:

1. Starts at the "Editor mode"

2. Clicks "Play" icon in the top toolbar 

3. The Editor window is replaced by a playing area containing the latest configurations made in Editor mode

**Expected outputs:** Play mode is displayed and ready for action  


###Test 16
 
**Purpose:** Test whether switching from Play to Editor mode works 

**Test inputs:** User:

1. Starts at the "Play mode"

2. Clicks "Menu" icon in the game toolbar 

3. Selects "Editor" in the currently displayed pause menu

3. The playing area is replaced by the Editor mode showing the configurations used in Play mode

**Expected outputs:** Editor mode is displayed and ready for further editing  
