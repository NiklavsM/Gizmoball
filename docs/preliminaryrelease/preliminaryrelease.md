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

### Test 1
**Purpose:** Test whether adding gizmos functionality works

**Test inputs:** User:
 
 1. Starts at the "Editor mode"
 
 2. Clicks the "Add tool" button in the option menu on the left then selects the Gizmo to be added on the map
 
 3. When user moves the mouse over the board the corresponding tile lights up green if the tile is free and red if the tile is already occupied by another Gizmo
 
 4. If the user tries to add the Gizmo to a occupied tile the tile blinks red
 
 5. If the tile is free the Gizmo is added to the board
 
 **Expected outputs:** The newly added Gizmo is visible on the map
