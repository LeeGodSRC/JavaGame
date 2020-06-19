package org.lgdev.game.engine;

import org.lgdev.game.components.Component;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameObject {

    private String name;
    private List<Component> components;
    private Transform transform;
    private int zIndex = 0;

    public GameObject(String name) {
        this(name, new Transform());
    }

    public GameObject(String name, Transform transform) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        for (Component component : components) {
            if (clazz.isAssignableFrom(component.getClass())) {
                try {
                    return clazz.cast(component);
                } catch (ClassCastException ex) {
                    ex.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0 ; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(componentClass)) {
                components.remove(i);
                return;
            }
        }
    }

    public GameObject addComponent(Component component) {
        this.components.add(component);
        component.gameObject = this;
        return this;
    }

    public GameObject zIndex(int zIndex) {
        this.zIndex = zIndex;
        return this;
    }

    public void update(float deltaTime) {
        for (Component component : this.components) {
            component.update(deltaTime);
        }
    }

    public void start() {
        for (Component component : this.components) {
            component.start();
        }
    }

    public void renderUI() {
        for (Component component : this.components) {
            component.renderUI();
        }
    }

}
