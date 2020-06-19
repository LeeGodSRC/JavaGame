package org.lgdev.game.engine;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {

    private static Keyboard instance;

    public static Keyboard getInstance() {
        if (instance == null) {
            instance = new Keyboard();
        }

        return instance;
    }

    private boolean[] keyPressed = new boolean[350];

    private Keyboard() {

    }

    private void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            keyPressed[key] = false;
        }
    }

    private void register0(long window) {
        glfwSetKeyCallback(window, this::keyCallback);
    }

    public static void register(long window) {
        Keyboard.getInstance().register0(window);
    }

    public static boolean isKeyPressed(int keyCode) {
        return Keyboard.getInstance().keyPressed[keyCode];
    }

}
