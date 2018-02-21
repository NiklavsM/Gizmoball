package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.model.gizmo.IGizmo;

public interface IGizmoFactory {

    IGizmo createGizmo(IGizmo.Type type, double x, double y, String id);

    IGizmo createGizmo(IGizmo.Type type, double x, double y);

    IGizmo createGizmo(IGizmo.Type type, double x1, double y1, double x2, double y2, String id);

    IGizmo createGizmo(IGizmo.Type type, double x1, double y1, double x2, double y2);

}
