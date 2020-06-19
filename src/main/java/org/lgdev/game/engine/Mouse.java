package org.lgdev.game.engine;

import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private static Mouse instance;

    public static Mouse getInstance() {
        if (instance == null) {
            instance = new Mouse();
        }
        return instance;
    }

    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean dragging;

    private Mouse() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    private void mousePosCallback(long window, double xPos, double yPos) {
        this.lastX = this.xPos;
        this.lastY = this.yPos;
        this.xPos = xPos;
        this.yPos = yPos;
        for (boolean pressed : mouseButtonPressed) {
            if (pressed) {
                this.dragging = true;
                break;
            }
        }
    }

    private void mouseButtonCallback(long window, int button, int action, int mods) {
        if (button >= this.mouseButtonPressed.length) {
            return;
        }

        if (action == GLFW_PRESS) {
            this.mouseButtonPressed[button] = true;
        } else if (action == GLFW_RELEASE) {
            this.mouseButtonPressed[button] = false;
            this.dragging = false;
        }
    }

    private void mouseScrollCallback(long window, double xOffSet, double yOffSet) {
        this.scrollX = xOffSet;
        this.scrollY = yOffSet;
    }

    private void end0() {
        this.scrollX = 0;
        this.scrollY = 0;
        this.lastX = this.xPos;
        this.lastY = this.yPos;
    }

    private void register0(long window) {
        glfwSetCursorPosCallback(window, this::mousePosCallback);
        glfwSetMouseButtonCallback(window, this::mouseButtonCallback);
        glfwSetScrollCallback(window, this::mouseScrollCallback);
    }

    public static void register(long window) {
        Mouse.getInstance().register0(window);
    }

    public static void endFrame() {
        Mouse.getInstance().end0();
    }

    public static float getX() {
        return (float) Mouse.getInstance().xPos;
    }

    public static float getY() {
        return (float) Mouse.getInstance().yPos;
    }

    public static float getDx() {
        return (float) (Mouse.getInstance().lastX - Mouse.getInstance().xPos);
    }

    public static float getDy() {
        return (float) (Mouse.getInstance().lastY - Mouse.getInstance().yPos);
    }

    public static float getScrollX() {
        return (float) Mouse.getInstance().scrollX;
    }

    public static float getScrollY() {
        return (float) Mouse.getInstance().scrollY;
    }

    public static float getOrthoX() {
        float currentX = getX();
        currentX = (currentX / (float) Window.getInstance().getWidth());
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);

        tmp
                .mul(Window.getCurrentScene().getCamera().getInverseProjection())
                .mul(Window.getCurrentScene().getCamera().getInverseView());

        return tmp.x;
    }

    public static float getOrthoY() {
        float currentY = getY();
        currentY = (currentY / (float) Window.getInstance().getHeight());
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);

        tmp
                .mul(Window.getCurrentScene().getCamera().getInverseProjection())
                .mul(Window.getCurrentScene().getCamera().getInverseView());

        return tmp.y;
    }

    public static boolean isDragging() {
        return Mouse.getInstance().dragging;
    }

    public static boolean isMouseButtonDown(int button) {
        if (button < Mouse.getInstance().mouseButtonPressed.length) {
            return Mouse.getInstance().mouseButtonPressed[button];
        }
        return false;
    }
}
