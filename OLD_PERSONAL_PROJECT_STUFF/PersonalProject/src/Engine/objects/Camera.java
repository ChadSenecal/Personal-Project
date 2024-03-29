package Engine.objects;

import Engine.IO.Input;
import Engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3f position, rotation;
    private float moveSpeed = 0.05f;

    public Camera(Vector3f position, Vector3f rotation){
        this.position = position;
        this.rotation = rotation;
    }

    public void update(){
        if(Input.isKeyDown(GLFW.GLFW_KEY_W)){ position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0)); }
        if(Input.isKeyDown(GLFW.GLFW_KEY_A)){ position = Vector3f.add(position, new Vector3f(-moveSpeed, 0, 0)); }
        if(Input.isKeyDown(GLFW.GLFW_KEY_S)){ position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0)); }
        if(Input.isKeyDown(GLFW.GLFW_KEY_D)){ position = Vector3f.add(position, new Vector3f(moveSpeed, 0, 0)); }
        if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){ position = Vector3f.add(position, new Vector3f(0, 0, -moveSpeed)); }
        if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)){ position = Vector3f.add(position, new Vector3f(0, 0, moveSpeed)); }
    }

    public Vector3f getPosition(){
        return position;
    }

    public Vector3f getRotation(){
        return rotation;
    }
}
