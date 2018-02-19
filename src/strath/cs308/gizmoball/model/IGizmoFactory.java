package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.model.gizmo.Gizmo;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

public interface IGizmoFactory {

    Gizmo createGizmo(IGizmo.Type type, double x, double y, String id);
    Gizmo createGizmo(IGizmo.Type type, double x, double y);

    Gizmo createGizmo(IGizmo.Type type, double x1, double y1, double x2, double y2, String id);

}
