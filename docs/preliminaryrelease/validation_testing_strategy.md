# Preliminary Release

**Group:** PR_Th1

Bence Sebestyen @xsb15143

Ioan Luca @xqb16141

Lyubomir Ivanov @kwb15150

Martin Kollie @vib15168

Niklavs Meiers @isb15151


## Validation Testing Strategy

### Test 1

**Purpose:** Test adding gizmo on an empty tile

**Test inputs:** 
 
 1. Start at the "Editor mode"
 
 2. Click the "Add tool" button in the option menu on the left
  
 3. Select the icon with the gizmo to be added on the map (square, triangle, circle or flipper)
 
 4. When mouse is moved over the board the corresponding tile lights up green (meaning the tile is free)
 
 5. The mouse is clicked and the desired gizmo appears on the corresponding tile
 
 6. A status label shows that a gizmo has been successfully added at a particular location 
 
**Expected outputs:** The newly added gizmo is visible on the map

 
 
### Test 2

**Purpose:** Test adding gizmo on an occupied tile
 
**Test inputs:**
  
 1. Start at the "Editor mode"
  
 2. Click the "Add tool" button in the option menu on the left
   
 3. Select the icon with the gizmo to be added on the map (square, triangle, circle or flipper)
  
 4. When mouse is moved over the board the corresponding tile lights up red (meaning the tile is occupied by another gizmo)
  
 5. The mouse is clicked and no change is done (tile still occupied by the same gizmo)
 
 6. A status label shows that the gizmo can not been added as this particular location is not empty
  
**Expected outputs:** No visible changes to the map, configurations remain the same
  

 
### Test 3

**Purpose:** Test adding absorber on empty space
 
**Test inputs:**
 
 1. Start at the "Editor mode"
 
 2. Click "Add tool" button in the menu on the left then selects the absorber icon
 
 3. Mouse is clicked where the start of the gizmo should be and the mouse button is held pressed 
 
 4. Now when mouse is dragged around the bord the tiles light up green (meaning space is not occupied by other gizmos)
 
 5. Mouse is released and the absorber is added in the desired location with the specified size
 
 6. A status label shows that an absorber has been added at a particular location
 
**Expected outputs:** The newly added absorber is visible on the screen
 

 
### Test 4
**Purpose:** Test adding absorber on occupied space (or partly occupied)
  
**Test inputs:**
  
  1. Start at the "Editor mode"
  
  2. Click "Add tool" button in the menu on the left then selects the absorber icon
  
  3. Mouse is clicked where the start of the gizmo should be and the mouse button is held pressed 
  
  4. Now when mouse is dragged around the bord the tiles light up red (meaning part of the space is occupied by other gizmos)
  
  5. Mouse is released and no change is visible - absorber is not added
   
  6. A status label shows that an absorber can not been added as this particular location is not entirely free 
 
**Expected outputs:** No visible changes to the map, configurations remain the same
 

 
### Test 5 
**Purpose:** Test removing present gizmo 

**Test inputs:** 

 1. Start at the "Editor mode"
 
 2. Click "Remove tool" button in the menu on the left
 
 3. When mouse moves over the board the corresponding tile lights up yellow
 
 4. Tile with gizmo is selected (or part of a gizmo in case of flipper and absorber) and the whole gizmo is removed from the board

 5. A status label shows that a gizmo has been successfully removed from the playing area 

**Expected outputs:** The gizmo is no longer visible on the board



### Test 6 
**Purpose:** Test removing a gizmo from an empty tile

**Test inputs:** 

 1. Start at the "Editor mode"
 
 2. Click "Remove tool" button in the menu on the left
 
 3. When mouse moves over the board the corresponding tile lights up yellow
 
 4. An empty tile is selected (no part of any gizmo occupies any part of this tile) 
 
 5. Tile lights up red

 6. A status label shows that the selected tile is empty and nothing is removed

**Expected outputs:** No visible changes to the map, configurations remain the same



### Test 7 
**Purpose:** Test connecting gizmo to another gizmo

**Test inputs:** 

 1. Start at the "Editor mode"

 2. Click "Connect tool" button in the menu on the left

 3. When mouse moves over the board the corresponding tile lights up yellow

 4. When tile that is occupied by a gizmo is selected it lights up green
 
 5. A status label appears at the bottom saying "Click on another gizmo to create a trigger"

 6. If then another gizmo is selected it now triggers the first gizmo's action 

 7. Play mode then is launched by pressing the "Play mode" button

 8. In Play mode upon a ball collision with the trigger gizmo, the first gizmo performs its action

**Expected outputs:** Whenever the linked gizmo is touched by ball the corresponding gizmo performs its action



### Test 8
**Purpose:** Test key-press triggering 

**Test inputs:** 

 1. Start at the "Editor mode"

 2. Click "Connect tool" button in the menu on the left

 3. When mouse moves over the board the corresponding tile lights up yellow

 4. When tile that is occupied by a gizmo is selected it lights up green
 
 5. A status label appears at the bottom saying "Press a key to create a trigger"

 6. If then a key is pressed it now triggers the selected gizmo's action 

 7. Play mode then is launched by pressing the "Play mode" button

 8. In Play mode when the bound key is pressed the first gizmo performs its action

**Expected outputs:** Whenever the linked key is pressed the corresponding gizmo performs its action.



### Test 9
**Purpose:** Test clearing playing area 

**Test inputs:** 

 1. Start at the "Editor mode" 

 2. Add some gizmos to the map

 3. Click on "Clear playing area" button

**Expected outputs:** All the gizmos are removed from the map leaving it completely empty.



### Test 10  
**Purpose:** Test adding a ball on an empty tile 

**Test inputs:** 

 1. Start at the "Editor mode" 

 2. Click "Add tool" button in the menu on the left

 3. Select the ball icon from the panel on the right 

 4. Click on a completely empty space on the board  

 5. The ball is added to the board at the desired location

 6. A dialog box should appear asking for input x and y for the ball's starting velocity vector

**Expected outputs:** Ball starts from the specified position with the correct velocity



### Test 11 
**Purpose:** Test adding a ball on an occupied tile

**Test inputs:** 

 1. Start at the "Editor mode" 

 2. Click "Add tool" button in the menu on the left

 3. Select the ball icon from the panel on the right 

 4. Mouse is clicked at a position where part of other gizmo is located 

 5. The corresponding tile lights up red and nothing happens

 6. A dialog box should appear asking for input x and y for the ball's starting velocity vector

**Expected outputs:** No visible changes to the map, configurations remain the same



### Test 12 
**Purpose:** Test adding a ball in an absorber

**Test inputs:** 

 1. Start at the "Editor mode" 

 2. Click "Add tool" button in the menu on the left 

 3. Selects the absorber icon

 4. Do steps 3-6 described in Test3

 5. Select the ball icon from the panel on the right 

 6. Mouse is clicked when it is in the absorber's boundaries
 
 7. The ball is placed visible in the absorber's lower right corner 

 8. No dialog box appears. The starting velocity of the ball is set to 50L/s upwards

**Expected outputs:** Ball is visited inside the absorber ready to launch when the absorber is triggered
with velocity of 50L/s upwards



### Test 13 
**Purpose:** Test moving gizmo to an empty tile 

**Test inputs:** 

 1. Start at the "Editor mode" 

 2. Add couple of gizmos to the board

 3. Select "Move gizmo" tool in the toolbar on the left

 4. Click on the gizmo that is to be moved 

 5. The tile that the particular gizmo occupies (or tiles) turn yellow

 6. A free tile is then selected and in turn the gizmo disappears from the previous location and appears on the last selected tile (or tiles)

**Expected outputs:** The gizmo is no longer visible in the previous location and now occupies the new location



### Test 14
**Purpose:** Test moving gizmo to an occupied tile

**Test inputs:** 

 1. Start at the "Editor mode" 

 2. Add couple of gizmos to the board

 3. Select "Move gizmo" tool in the toolbar on the left

 4. Click on the gizmo that is to be moved 

 5. The tile that the particular gizmo occupies (or tiles) turn yellow

 6. A tile that is occupied by another gizmo is then selected and it lights up red indicating the location is not available

**Expected outputs:** No visible changes to the map, configurations remain the same



### Test 15
**Purpose:** Test rotating gizmo functionality 

**Test inputs:** 

 1. Start at the "Editor mode" 

 2. Add some gizmos to the board 

 3. Select "Rotate tool" from the menu on the left

 4. Click on any gizmo on the board to rotate it by 90° clockwise each click

**Expected outputs:** Each click on the gizmo should rotate it by 90°



### Test 16  
**Purpose:** Test saving newly configurated map 

**Test inputs:**  

 1. Start at the "Editor mode"
 
 2. Add at least one gizmo of each type to the board

 3. Click "Save As" icon located in the top toolbar

 4. A file explorer pops up prompting to specify a desired location for the file 

 5. Type in the name of the file to be saved, for example filename.gizmo

 6. Click "Save".  

 7. A status label shows that the game configuration has been successfully saved.

**Expected outputs:** Game configuration has successfully been saved to filename.gizmo located in the specified directory



### Test 17 
**Purpose:** Test saving an already saved map (using "Save", not "Save As")    

**Test inputs:** 

 1. Do steps 1-7 described in Test 9

 2. Change the board layout

 3. Click "Save" icon located in the top toolbar

 4. A status label shows that the game configuration have been successfully saved.

**Expected outputs:** Game configuration has successfully been saved, overwriting the file with the new configurations 



### Test 18 
**Purpose:** Test loading from saved configurations in Editor mode 

**Test inputs:** 

 1. Start at the "Editor mode"

 2. Click "Load" icon located in the top toolbar

 3. Specify to the desired location and selects the file to be loaded, for example filename.gizmo

 4. Clicks "Load"

 5. Game configurations are loaded and displayed on the board

 6. The status label shows that the game configurations have been successfully loaded

**Expected outputs:** The configurations are displayed on the screen, ready for further editing


### Test 19
**Purpose:** Test loading from saved configurations in Play mode 

**Test inputs:** 

 1. Start at the "Play mode"

 2. Click "Menu" icon in the game toolbar 

 3. Select "Load" in the currently displayed pause menu

 4. Specify the desired location and selects the file to be loaded, for example filename.gizmo

 5. Clicks "Load"

 6. Game configurations are loaded and displayed on the blurred in the background playing area

 7. Exiting the pause menu brings the user back to the game with the newly loaded game configurations

**Expected outputs:** The configurations are displayed on the screen ready for play 



### Test 20 
**Purpose:** Test switching from Editor to Play mode 

**Test inputs:** 

 1. Start at the "Editor mode"

 2. Click "Play" icon in the top toolbar 

 3. The Editor window is replaced by a playing area containing the latest configurations made in Editor mode

**Expected outputs:** Play mode is displayed and ready for action  



### Test 21 
**Purpose:** Test switching from Play to Editor mode  

**Test inputs:** 

 1. Start at the "Play mode"

 2. Click "Menu" icon in the game toolbar 

 3. Select "Editor" in the currently displayed pause menu

 4. The playing area is replaced by the Editor mode showing the configurations used in Play mode

**Expected outputs:** Editor mode is displayed and ready for further editing  



### Test 22
**Purpose:** Test whether the game starts correctly

**Test inputs:** 

 1. Start at the "Play mode" 

 2. Click on the "Play" button on the bottom left

**Expected outputs:** The game should start. The ball should start to move



### Test 23 
**Purpose:** Test whether the game stops correctly

**Test inputs:** 

 1. Start at the "Play mode" 

 2. Click on the "Stop" button on the bottom left

**Expected outputs:** The game should stop. The ball should freeze in its current position



### Test 24 
**Purpose:** Test whether the game ticks correctly

**Test inputs:** 

 1. Start at the "Play mode" 

 2. Click on the "Tick" button on the bottom left

**Expected outputs:** The ball moves one tick then stops



### Test 25
**Purpose:** Test whether the pause menu appears properly 

**Test inputs:** 

 1. Start at the "Play mode" 

 2. Click on the "Menu" button on the bottom left

**Expected outputs:** The game is paused. The background is blurred and a menu overlay is displayed



### Test 26 
**Purpose:** Test whether the game maintains the same game state after closing the menu

**Test inputs:** 

 1. Start at the "Play mode" 

 2. Click on the "Menu" button on the bottom left

 3. Click on the back button

**Expected outputs:** The menu hides, background blur is removed and game continues if appropriate (E.g. if the game was paused it should remain paused after resuming)



### Test 27
**Purpose:** Test whether the game exists gracefully after exit selected in the menu

**Test inputs:** 

 1. Start at the "Play mode" 

 2. Click on the "Menu" button on the bottom left

 3. Click on "Exit"

 4. A confirmation dialog pops up which needs to be confirmed with "Yes" 

**Expected outputs:** The game quits, the whole application is closed



### Test 28 
**Purpose:** Test whether the status bar updates depending on option selected

**Test inputs:** 

 1. Start at the "Editor mode" 

 2. Click on each tool on the left left sidebar

 3. After each click the status bar should read "<MODE> tool: ...."

**Expected outputs:** Appropriate status message in the format <MODE> tool: ....


### Test 29 
**Purpose:** Test whether the game maintains it aspect ratio and draws correctly when the window is resized

**Test inputs:** 

 1. Start at the "Play mode" 

 2. Drag the game window 

 3. Resize the game window

 4. Minimise

 5. Maximise

**Expected outputs:** The game nicely maintains its aspect ratio 
