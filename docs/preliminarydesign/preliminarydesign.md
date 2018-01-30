# Preliminary Design 

**Gizmoball** is an arcade game very similar to pinball. The aim is to keep a ball moving around the playing area and not let it touch the bottom using different types of gizmos. Gizmos can be static objects, such as circles, squares and triangles or flippers which can hit the ball if it is in their reach. The final system would include a graphical user interface with 2 modes - editor mode and play mode. Below is the list of revised specifications in each mode: 

## Table of Contents

<!-- toc -->

- [Revised Specification](#revised-specification)
  * [Editor mode](#editor-mode)
  * [Play mode](#play-mode)
- [Use cases](#use-cases)
  * [Add Gizmos[^1]](#add-gizmos1)
  * [Add Absorber](#add-absorber)
  * [Remove Gizmo](#remove-gizmo)
  * [Connect Gizmos[^2]](#connect-gizmos2)
  * [Clear playing area](#clear-playing-area)
  * [Adding a new ball[^3]](#adding-a-new-ball3)
  * [Remove Ball](#remove-ball)
  * [Edit Ball](#edit-ball)
  * [Move Gizmo](#move-gizmo)
  * [Rotate Gizmo](#rotate-gizmo)
  * [Edit gravity and friction](#edit-gravity-and-friction)
  * [Save configurations](#save-configurations)
  * [Load configurations](#load-configurations)
  * [Stop game](#stop-game)
  * [Run game](#run-game)
- [Physics loop (high level)](#physics-loop-high-level)
- [Questions](#questions)

<!-- tocstop -->

## Revised Specification 

### Editor mode
- Initialise an empty area where the layout can be built

- Re-configure an already constructed layout 

- Add any chosen type of gizmo to the playing area 

- Add a ball to the playing area, specifying its position 

- Move an existing ball to a different position

- Edit the velocity and the diameter of an existing ball

- Remove a ball from the playing area

- Set custom values for gravity and friction

- Move a gizmo from one cell to another  

- Rotate a gizmo 90° clockwise  

- Remove a selected gizmo from the playing area 

- Connect gizmos together (link a certain gizmo's trigger to the action of another gizmo) 

- Make a key press trigger a particular gizmo's action 

- Save the configurations to a file 

- Load particular configurations from a file and display it on the screen 

- Go to play mode 

- Clear playing area

- Exit the application 


### Play mode

- Start a game (release the ball in the playing area) 

- Stop a game (stop the ball in its current position) 

- Press keys that trigger gizmos' actions 

- Save the current state of a stopped game (maybe not???) 

- Load and display a saved game state (maybe not???) 

- Go to editor mode 

- Exit the application 

## Use cases

### Add Gizmos[^1] 

[^1]: triangle, square and circle bumpers

**Precondition:**  Editor mode enabled

**Trigger:** Gizmo type selected from the gizmos panel

**Path:**

1. The `20L x 20L` layout grid becomes highlighted.

2. The status label informs the user that he needs to select one grid location.

3. The user selects a grid square from the layout by clicking it.

4. If the grid square is occupied by another gizmo, go to `3`.
. The status label informs the user that he can now add more gizmos of the same type animated for 2 seconds, go to `2`.

**Postcondition:** One or more gizmos of the same type have been added to the layout.

### Add Absorber

**Precondition:** Editor mode enabled

**Trigger:** Absorber shape selected from the gizmos panel

**Path:**

1. The `20L x 20L` layout grid becomes highlighted.

2. The status label informs the user that he needs to select one grid location representing the right top corner of the absorber.

3. The user selects a grid square from the layout by clicking it.

4. If the grid square is occupied by another gizmo, go to `3`.

5. The status label informs the user that he needs to select one grid location representing the left bottom corner of the absorber, do `3 - 4`and go to `6`.

6. The status label informs the user that he can now add more gizmos of the same type, go to `2`.

**Postcondition**: One or more absorbers of the same type have been added to the layout.

### Remove Gizmo[^5]

[^5]: or an already existing ball 

**Precondition:** Editor mode enabled

**Trigger:** Gizmo selected

**Path:**

1. User clicks delete button.

2. Gizmo is removed from the grid layout.

**Postcondition:** The grid layout does not contain the removed gizmo.

### Connect Gizmos[^2]

[^2]: connects a gizmo trigger to a gizmo action

**Precondition:** Editor mode enabled

**Trigger:** Connect switch has been toggled

**Path:**

1. The user selects a gizmo from the grid, the gizmo then becomes highlighted. If the gizmo 
cannot trigger then notify user through the status label, go to `1`.

1. The user selects a second gizmo from the grid which then becomes highlighted in a different way. If the selected gizmo
doesn't have an action then notify user through the status label, go to `2`.

**Postcondition:** The first gizmo's trigger is now connected to the second gizmo's action.



### Clear playing area

**Precondition:** Editor mode enabled and at least one edit action performed

**Trigger:** "Clear Board" button selected

**Path:**

1. User clicks the "Clear Board" button

1. All gizmos are removed from the playing area

**Postconditions:** All gizmos are cleared from the grid layout. Physics properties 
like gravity and friction are preserved.


### Adding a new ball[^3]

[^3]: placing a new ball in the playing area 

**Precondition:** Editor mode enabled, a ball does not exist in the current grid layout

**Trigger:** "New ball" button clicked 

**Path:**

1. The user enters values in the input fields for the velocity (0L/sec to 200L/sec), diameter (default is 0.5L) 
or chooses to go with the default values. 

1. The user selects a grid location to place the ball at. If the grid location
is occupied, go to `2`, else go to `4`. If The user clicks on an absorber go to `3`.

3. The ball is placed in the right bottom corner of the absorber. 

4. The properties panel shows up and user can adjust the velocity[^4] by dragging a slider. 

**Postcondition:** A new ball is now added to the playing area.

[^4]: ball velocities range from `0L/sec` to `200L/sec`


### Move Gizmo

**Precondition:** Editor mode selected 

**Trigger:** "Move gizmo" button clicked 

**Path:**

1. User clicks on the gizmo to be moved 

2. User drags gizmo to the new location 

3. If location is already occupied go to step `2`. 

**Postcondition:** Gizmo has changed its location;



### Rotate Gizmo

**Precondition:** Editor mode enabled 

**Trigger:** Rotate button has been toggled 

**Path:**

1. The user clicks on a gizmo.

1. If the gizmo doesn't support rotation, go to `1`.

1. The selected gizmo rotates 90 degrees clockwise.

1. Go to `1`.

**Postcondition:** The gizmo/gizmos that the 
user clicked on have been rotated by `n * 90` degrees
clockwise, where `n` is the number of clicks 
on each gizmo.


### Edit physic properties[^6]

[^6]: gravity, friction, etc.

**Precondition:** Editor mode selected 

**Trigger:** Focus any of the g, mu or mu2 fields. 

**Path:**  

1. User enters a new value in the field or leaves the field unchanged 

2. If the value is in the wrong format, notify user and go back to step 1.

3. User clicks the apply button.  

1. If the value is in the wrong format, notify user. 

2. Go back to 2. 


### Save configurations 

**Preconditions:** Editor mode selected  

**Triggers:** "Save" button selected  

**Paths:**

1. User clicks "Save" button 

2. A dialog box appears to specify the save location 

3. User selects the desired location and clicks "Save"  

4. A status label shows that the game configurations have been successfully saved 

Postconditions: Playing area is shown ready for new actions; Game configurations have been saved to a file 

--- 

### Load configurations 

**Preconditions:** Editor mode selected  

**Triggers:** "Load" button selected  

**Paths:**

1. User clicks "Load" button 

2. A dialog box appears to specify where to load the configurations from 

3. User selects the desired location and clicks "Load" 

4. Game configurations are loaded and displayed on the screen;
A status label shows that the game configurations have been successfully loaded 

**Postconditions:** The saved playing area is shown ready for further editing  

Game configurations have been loaded from a file and displayed on the screen 

--- 

### Stop game 

**Preconditions:** Play mode selected, the game is running. 

**Triggers:** “Stop” button pressed. 

**Paths:**

1. User presses “Stop” button. 

2. Game has been stopped 

Postconditions: Ball stops (game has been paused) 

--- 

### Run game 

**Preconditions:** Play mode selected, the game has been stopped or new game has been loaded. 

**Triggers:** “Run” button pressed 

**Paths:**

1. User presses “Run” button 

2. The ball starts to move with the previous velocity and direction. 

**Postconditions:** Game is running. 


## Physics loop (high level)

```
for every tick  

    Calculate collision time for all gizmos (and balls if added) inside the map. The method that does the calculation also returns an object that the ball will collide next.
    
    If estimated time until nearest collision is greater than 0.05sec (time until next frame is drawn) then
        Set balls new coordinates where the ball will be after this time passes. 
        Apply gravity and friction for that time period. 
    
    If time until next collision is smaller than 0.05, then
        update the coordinates of the ball taking into account time and velocity, 
        calculate and set the velocity of the ball after the collision taking into account friction and gravity for that time period. 
        
        If gizmo that ball collides with have trigger then
            trigger the action(calls the trigered() method on the object returned by calculate collisions method).   
    
    Redraw the screen
```

## Questions 

1. How detailed the requirements should be: 
(Example: 1. Create your own map or 1. Add gizmos to map 2. Delete gizmos to the map 3. Flip gizmos)

1. Save game and/or save map? 

1. Bonus stuff we could add: 

1. Config game
