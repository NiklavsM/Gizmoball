package model;

import model.gizmo.IEntity;

import java.util.UUID;

public abstract class Entity implements IEntity {

    private final String id;

    public Entity(String id) {
        this.id = id;
    }

    protected static String generateID() {
        return UUID.randomUUID().toString();
    }

    public abstract Type getType();

    @Override
    public String getId() {
        return id;
    }
}
