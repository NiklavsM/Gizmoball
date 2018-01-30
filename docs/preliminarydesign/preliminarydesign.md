# Preliminary Design 

**Gizmoball** is an arcade game very similar to pinball. The aim is to keep a ball moving around the playing area and not let it touch the bottom using different types of gizmos. Gizmos can be static objects, such as circles, squares and triangles or flippers which can hit the ball if it is in their reach. The final system would include a graphical user interface with 2 modes - editor mode and play mode. Below is the list of revised specifications in each mode: 

## Table of Contents

<!-- toc -->

- [Revised Specification](#revised-specification)
- [Use cases](#use-cases)
  * [Add Gizmo](#add-gizmo)
  * [Remove Gizmo](#remove-gizmo)
  * [Connect Gizmos](#connect-gizmos)
  * [Clear playing area](#clear-playing-area)
  * [New Ball](#new-ball)
  * [Remove Ball](#remove-ball)
  * [Edit Ball](#edit-ball)
  * [Move Gizmo](#move-gizmo)
  * [Rotate Gizmo](#rotate-gizmo)
  * [Edit gravity and friction](#edit-gravity-and-friction)
  * [Save configurations](#save-configurations)
  * [Load configurations](#load-configurations)
  * [Stop game](#stop-game)
  * [Run game](#run-game)
- [Physics loop (high level):](#physics-loop-high-level)
- [Questions](#questions)

<!-- tocstop -->

## Revised Specification 

**Editor mode**
- Initialise an empty area where layout can be build   

- Re-configure an already constructed layout 

- Add any chosen type of gizmo to the playing area 

- Add a ball to the playing area, specifying its position 

- Move an existing ball to a different position

- Edit the velocity and the diameter of an existing ball

- Remove a ball from tge playing area

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


**Play mode**

- Start a game (release the ball in the playing area) 

- Stop a game (stop the ball in its current position) 

- Press keys that trigger gizmos' actions 

- Save the current state of a stopped game (maybe not???) 

- Load and display a saved game state (maybe not???) 

- Go to editor mode 

- Exit the application 

## Use cases

### Add Gizmo

**Preconditions:** Editor mode selected 

**Triggers:** Gizmo type selected by clicking the button with gizmo shape written on it. 

**Paths:**

1. User clicks on gizmo type. 

2. Select grid position by clicking on the map where gizmo should be added. 

3. If new gizmo overlaps then go to step 2. 

4. Gizmo is added to the map. 

5. Goes to step 2. 

**Postconditions:** New gizmo has been added and is visible on the map.

---

### Remove Gizmo

**Precondition:** Editor mode selected 

**Triggers:** Gizmo selected and delete button has been clicked 

**Paths:**

1. User clicks on gizmo 

2. User Clicks delete button 

3. Gizmo is removed from the map 

**Postconditions:** Gizmo has been removed from the map  

---

### Connect Gizmos

**Description:** Connects a gizmo trigger to a gizmo action. 

**Preconditions:** Editor mode selected 

**Triggers:** Connect switch has been toggled 

**Paths:**

**Main**

1. User selects a gizmo G0 from the grid, the gizmo then becomes highlighted. 

2. ??? Status label "<gizmoID> trigger selected, now connect to an action". ??? 

3. User selects a gizmo Gi from the grid, the gizmo becomes highlighted in a different way.  

4. Go to 3 or stop. 


**A3**

1.  If Gi doesn't have an action, notify user and go back to M3. 

**Postconditions:** G0 trigger is now connected to Gi action where i is in [1..39]. 


--- 

### Clear playing area

**Precondition:** Editor mode selected and at least one action performed

**Triggers:** "Clear Board" button selected

**Path:**

1. User clicks "Clear Board" button

1. All gizmos are removed from the playing area

**Postconditions:** The playing area is empty and ready for editing

All gizmos are cleared from the board

---


---

### New Ball 

**Description:** Placing a new ball in the playing area 

**Preconditions:** Editor mode selected 

**Triggers:** "New ball" button clicked 

**Paths:**

* **Main:** 

1. Ball details input modal appears on the screen, displaying editable default values. 

2. User enters values in the input fields for the velocity (0L/sec to 200L/sec), diameter (default is 0.5L) or chooses to go with the default values. 

3. User selects a grid point to place the ball at. 

* **A2:** 

1. Values are in the wrong format, notify user. 

2. Go back to M2. 

* **A3:**

1. User clicks on an absorber. 

2. Ball is placed in the right bottom corner of the absorber. 

Postconditions: a new ball is now added to the playing area. 

--- 

### Remove Ball

Description: Remove a ball that is already placed in the playing area

Preconditions: Editor mode selected and in remove mode

Triggers: Ball in the playing area selected 

Paths: 

* Main: 

    1. User selects "Remove" button from the edit toolbar and is in remove mode
    
    2. If user clics on ball, it is removed from the board, otherwise go back to M1. 
    

Postconditions: an existing ball is removed from the playing area


---

### Edit Ball

**Description:** Edit the velocity and the diameter of a ball on the board

**Preconditions:** Editor mode selected 

**Triggers:** Ball in the playing area clicked and Properties tab selected  

**Paths:**

* **Main Path:** 

    1. User cicks on ball 
    
    2. User selects Properties tab
    
    3. User enters new values in the input fields for the velocity and the diameter. 
    
    4. Ball is updated with new values
    
* **Alternative Path 1:**  

    1. Values are in the wrong format, notify user. 
    
    2. Go back to M3. 

**Postconditions:** An existing ball is updated with new values


---

### Move Gizmo

---

**Description:** Moving a gizmo from one location to another 

**Preconditions:** Editor mode selected 

**Triggers:** "Move gizmo" button clicked 

**Paths:**

1. User clicks on the gizmo to be moved 

2. User drags gizmo to the new location 

3. If location is already occupied go back to step 1. 

4. If location is free the gizmo moves to the new location 

**Postconditions:** Gizmo has changed its location 

---

### Rotate Gizmo

**Precondition:** Editor mode selected and in rotate mode

**Triggers:** Gizmo selected and delete button has been clicked 

**Paths:**

1. User clicks on gizmo 

2. User Clicks delete button 

3. Gizmo is removed from the map 

**Postconditions:** Gizmo has been removed from the map  


---

### Edit gravity and friction 

**Preconditions:** Editor mode selected 

**Triggers:** Focus any of the g, mu or mu2 fields. 

**Paths:**  

* **Main**
1. User enters a new value in the field or leaves the field unchanged 

2. If the value is in the wrong format, notify user and go back to step 1.

3. User clicks the apply button.  

* **A1:** 

1. If the value is in the wrong format, notify user. 

2. Go back to 2. 

--- 

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

**Preconditions:** Play mode selected, game is running. 

**Triggers:** “Stop” button pressed. 

**Paths:**

1. User presses “Stop” button. 

2. Game has been stopped 

Postconditions: Ball stops (game has been paused) 

--- 

### Run game 

**Preconditions:** Play mode selected, game has been stopped or new game has been loaded. 

**Triggers:** “Run” button pressed 

**Paths:**

1. User presses “Run” button 

2. Ball starts to move with the previous velocity and direction. 

**Postconditions:** Game is running. 


## Physics loop (high level):  

```
for every tick  

    Calculate collision time for all gizmos (and balls if added) inside the map.  
    
    If estimated time until nearest collision is greater than 0.05sec (time until next frame is drawn) then
        Set balls new coordinates where ball will be after this time passes. 
        Apply gravity and friction for that time period. 
    
    If time until next collision is smaller than 0.05, then
        update the balls coordinates taking into account time and velocity, 
        calculate and set the balls velocity after the collision taking into account friction and gravity for that time period. 
        
        If gizmo that ball collides with have trigger then
            trigger the action.   
    
    Redraw the screen
    
```

**Things to address:** Time will slow down when the collisions happen, because while ball will be displayed as it would have moved for time < 0.05, the next frame will be drawn after 0.05 no matter what. 

---

## Class diagram

<insert picture here>

### View hierarchy

Some view contains sub-views.
Each view has it's own interface.
Each view has it's own controller class.
The view Controllers are only handles the events
fired from the actual view and not from it's sub-views.

* Main view
    * Game view
        * Play view
        * Ingame menu view
    * Edit view

### Main
This class is the starting point of the FX application.

### ShapeFactory
This class is responsible to create the representation of a PhysicalBody on the view.

### MainView
This is where the main window and those elements what are presented on all view
are implemented.

### EditView
Contains the UI implementation of the editing mode.

### GameView
Contains the UI implementation of the play mode.
Build up by the PlayView and the InGameMenuView.

### PlayView
This is where the game are presented by drawing out the PhyisicalWorld.
This is also an Observer to the PhysicalWorld, to make sure it's always
representing the current state of the world.
 
### IngameMenuView
This class implementing the menu which can be brought up while the user
is playing in play mode.

### View Interfaces
IEditView, IGameView, IIngameMenuView, IMainView, IPLayView.
These interfaces are for hiding the actuall implementations of the view from
the other part of the application.
Controller -> View

### MainController
This is an implementation of EventHandler interface.
This class required to handle all the events caused by interaction with the MainView.

### EditController
This is an implementation of EvnetHandler interface.
This class handles all the event coming from the EditView.
And also changing the state of the editing area.

### Edit area states
AddGizmo, ConnectGizmo, DeleteGizmo, RotateGizmo.
These event handlers specifing that how the edit area should behave in the givem state
which was set by the EditController (trough an interaction of the EditView).
This contains clicking and drawing events.

### GameController
This controller handles the events coming from the GameView.
And just the GameView itself.

### PlayController
Handles the user interaction with the playing area of the game mode view.

### IngameMenuController
This controller's task is to respond to the events generated with the
playmode's ingame menu.

### EventHandler
Interface from the javafx package.
Main purpose to hide controller implementation and provide
bridge between Controllers and Views.
View -> Controller.

### IPhysicsBody, ISaveHandler, IPhysicsWorld
These three interface meant to represent the Model in other part
of the application, also hiding the implementation behind them.
View -> Model
Controller -> Model

### ITriggerable
This interface is provides details for those objects
which can be triggered by triggers.

### ITrigger
This interface provides unified functions accross all trigger objects
to be able to link triggerable objects to itself which can be triggerd by
the trigger objects.

### SaveHandler
This class purpose to handle the config file IO operations.

### PhysicsBodyFactory
Factory for generating proper object representation from
strings which are contained in the config files.

### PhysicsWorld
The PhysicsWorld class meant to coordinate a world which is built up by PhysicsBodys.
This is where the physics loop takes place.
This class is an Observable class, which meant to provide a way for the Model
to communicate with other part of the application
Model (Observable) -> View (Observer) //Mainly in our case.

### PhysicsBody
Abstract class which provides the implementation of the common
traits of an entity which meant to be placed in our game world
and used in physics simulation.

### Absorber
The entity class which represents an Absorber gizmo with all its own
functionality and traits.
Subclass of the PhysicsBody.

### Ball
The entity class for the game ball.
Subclass of the PhysicsBody.

### Circle
Class for Circle gizmo representation in our Model.
Subclass of the PhysicsBody.

### Flipper
Representation of the Flippers in our Model.
Single flipper class used for both left and right flipper.
The difference between the two was separated with the
FlipperDirection enum class.
Subclass of the PhysicsBody.

### FlipperDirection
Enum class for represeting the posible flipper directions.

### Rectangle
Class for modeling the rectangle gizmo.
Subclass of the PhysicsBody.

### Triangle
Class for modeling the triangle gizmo.
Subclass of the PhysicsBody.

### Wall
Class in the model for representing the walls which are 
building up the boundaries of the physical worlds's boundaries.
Subclass of the PhysicsBody.

---

## Questions 

1. How detailed the requirements should be: 
(Example: 1. Create your own map or 1. Add gizmos to map 2. Delete gizmos to the map 3. Flip gizmos)

1. Save game and/or save map? 

1. Bonus stuff we could add: 

1. Config game
