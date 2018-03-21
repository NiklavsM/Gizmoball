# Gizmoball Features

## Play Mode

* Scoring system implemented - gizmo collisions grant points
 (flippers, balls and walls collisions don't), absorbed balls deduct points
* Side panel with dynamic game statistics - balls in play; balls absorbed
* In-game menu with only the most useful buttons
* Pause menu with translucent background
* Game animation stop and blur on background when pause menu is invoked 
* Nice and smooth JavaFX GUI (60 FPS)
* Multiple balls (limit of 200)
* Music introduced with an option of muting it 
* Game score and other stats
* Supports 10+ language
* Balls get reflected if the absorber is full
* Spinner Gizmmo (Rotates continually)
* Rhombus Gizmo
* Octagon Gizmo 
* Save and Save as
* Game icon
* 3D 
* Shadows

## Editor Mode

### Editing
* Visible Gizmo Drawings
* Status bar with relevant message
* Toggle grid on and off

### Modes
* Custom cursor tool for each mode

**Add tool**

* Allows you to add a gizmo to the map
* Click and drag in any direction to fill space with gizmos
* Gizmo preview before addition to the board (if addition is possible) 
* Gizmo Preview on drag
* Can add balls to absorber
* Adding absorbers on balls

**Move tool**
* Allows you to move a gizmo to any free position
* Can move absorbers with balls in it

**Connect tool**
* Allows you to connect two gizmos together

**Rotate tool**
* Rotates a gizmo by 90 degrees

**Delete tool**
* Remove the select gizmo from the map

**Connect tool**
* Easy gizmo key connection interface

### Properties
* Properties tab for every gizmo
* Ability to change gizmo colour
* 3D Mode (Toggle on and off)
* Shadows (Toggle on and off)

### Undo and Redo
* Undo changes made
* Redo changes made

### Saving and Loading
* Check for .gizmo extension
* Can add comments to .gizmo files
* Can load and save from both play view and edit mode
* Save as
* Save
* Single line comments in the `.gizmo` files (starts with `# `)
* `.gizmo` extension file automatically handled

### Language extension

* lowercase commands

**New Commands**
* ```ClearMap``` - Clear map
* ```Color <id> <hexcolor>``` - Change Gizmo colour
* ```Rhombus <id> <position x> <position y>``` - Create Rhombus Gizmo
* ```Spinner <id> <position x> <position y>```  - Create Spinner Gizmo
* ```Octagon <id> <position x> <position y>``` - Create Octagon Gizmo

### Console
### Console3d
* Let's you change the model with gizmoball commands in real time 
* Uses Gizmoball syntax
* `clear`command for clearing the console
* `undo` the previous command
* `redo` the previous command
* works while playing



### Connection
* Easy gizmo key connection interface
* Connect a key to a gizmo

## Settings
* Saves game settings in an XML file in user home directory
* Automatically creates directory in user_home/Documents/Gizmoball if it doesn't exists
* Edit settings through GUI
* Enable/Disable shadows

## Coming Soon (Almost done)


* Settings (Lanauage, volume)
* Game Launcher
* Custom gizmo actions

## WTF
* jail action