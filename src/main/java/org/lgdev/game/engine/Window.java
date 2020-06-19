package org.lgdev.game.engine;

import lombok.Getter;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lgdev.game.scene.EditorScene;
import org.lgdev.game.scene.GameScene;
import org.lgdev.game.scene.Scene;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Getter
public class Window {

    private int width;
    private int height;
    private String title;

    private long windowId;
    private ImGuiLayer imGuiLayer;

    @Getter
    private static Scene currentScene = null;

    private float red, blue, green, alpha;

    private static Window instace;

    public static Window getInstance() {
        if (instace == null) {
            instace = new Window();
        }

        return instace;
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new EditorScene();
                break;
            case 1:
                currentScene = new GameScene();
                break;
            default:
                assert false : "Unknown org.lgdev.game.scene index '" + newScene + "'";
                return;
        }
        currentScene.init();
        currentScene.start();
    }

    private Window() {
        this.width = 1920;
        this.height = 1080;

        this.title = "Hello World!";
        this.rgba(1, 1, 1, 1);
    }


    public void start() {
        this.init();

        this.loop();

        // Free the memory
        glfwFreeCallbacks(this.windowId);
        glfwDestroyWindow(this.windowId);

        // Terminate GLFW
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop() {
        float beginTime = (float) glfwGetTime();
        float deltaTime = -1.0f;
        float endTime;

        while (!glfwWindowShouldClose(this.windowId)) {

            glfwPollEvents();

            glClearColor(this.red, this.green, this.blue, this.alpha);
            glClear(GL_COLOR_BUFFER_BIT);

            if (deltaTime >= 0) {
                currentScene.update(deltaTime);
            }

            this.imGuiLayer.update(deltaTime, currentScene);
            glfwSwapBuffers(this.windowId);

            endTime = (float) glfwGetTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;

        }
    }

    private void init() {
        System.out.println("LWJGL Version: " + Version.getVersion());

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        this.windowId = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (this.windowId == NULL) {
            throw new IllegalStateException("Failed to create the GLFW Window!");
        }

        Mouse.register(this.windowId);
        Keyboard.register(this.windowId);
        glfwSetWindowSizeCallback(windowId, (w, width, height) -> {
            this.width = width;
            this.height = height;
        });

        // OpenGL context current
        glfwMakeContextCurrent(this.windowId);

//        glfwSwapInterval(1); // V-SYNC

        glfwShowWindow(this.windowId);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        this.imGuiLayer = new ImGuiLayer(this.windowId);
        this.imGuiLayer.init();

        Window.changeScene(0);
    }

    public void rgba(float r, float g, float b, float a) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
    }

}
