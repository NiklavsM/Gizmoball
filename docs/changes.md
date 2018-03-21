# Changes

## Views
* FileChooser class added for creating filechoosing dialogs
* ILauncherView interface added for launcher view
* LauncherView this class is added for the laucnher view representation
* ConnectPanelView this view class represents the connection tab in the edtior view

## Utils
Utils package added for utlilti classes
* Logger logger class used for logging and debuginng purposes during our development
* Settings this class introduced for storing the users settings acros ssessions
* KeyConverter introduced for old AWT and jajvafx keycode convertion
* MusicPlayer class for playing music

## Controller

* LaunchEventHandler for handling the events of the launch screen
* SelectGizmoEventHandler strategy added for loading in gizmo properties
* ConnectHandler added for handling the events form the connection tab in edtor mode

## Model
* UndoRedo class added for hadling model history for editor view
* DefaultTrigger and DefaultTrigarable classes added for composition in classes to provide the general baisck impelemntation for ITriggerbale ITrigger Method 
* Octagon, Rhombus, Spinner classes added as they represnetik the new custom gizmos we added
* AbstractGizmoAction Action representation
* ChangeColorAction, CHangeToARandomColor, DestroyerGizmoAction, GoToJailAction, PlaySoundAction, RemoveGizmoAction, RotateAction, TimedColorChange, This classes are predefined actions for gizmos which could be set to activate on trigger.