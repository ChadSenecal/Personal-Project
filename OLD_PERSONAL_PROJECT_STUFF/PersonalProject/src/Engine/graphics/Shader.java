package Engine.graphics;

import Engine.maths.Matrix4f;
import Engine.maths.Vector2f;
import Engine.maths.Vector3f;
import Engine.utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL42;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Shader {
    private String vertexFile, fragmentFile;
    private int vertexID, fragmentID, programID;

    public Shader(String vertexPath, String fragmentPath){
        vertexFile = FileUtils.loadAsString(vertexPath);
        fragmentFile = FileUtils.loadAsString(fragmentPath);
    }

    public void create(){
        programID = GL20.glCreateProgram();
        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

        GL20.glShaderSource(vertexID, vertexFile);
        GL20.glCompileShader(vertexID);

        if(GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.err.println("Vertex Shader: " + GL20.glGetShaderInfoLog(vertexID));
            return;
        }
        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        GL20.glShaderSource(fragmentID, fragmentFile);
        GL20.glCompileShader(fragmentID);

        if(GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.err.println("Fragment Shader: " + GL20.glGetShaderInfoLog(fragmentID));
            return;
        }

        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);

        GL20.glLinkProgram(programID);
        if(GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE){
            System.err.println("Program Linking: " + GL20.glGetProgramInfoLog(programID));
            return;
        }

        GL20.glValidateProgram(programID);
        if(GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE){
            System.err.println("Program Validation: " + GL20.glGetProgramInfoLog(programID));
            return;
        }
    }

    public int getUniformLocataion(String name){
        return GL20.glGetUniformLocation(programID, name);
    }

    public void setUniform(String name, float value){
        GL20.glUniform1f(getUniformLocataion(name), value);
    }

    public void setUniform(String name, int value){
        GL20.glUniform1i(getUniformLocataion(name), value);
    }

    public void setUniform(String name, boolean value){
        GL20.glUniform1i(getUniformLocataion(name), value ? 1 : 0);
    }

    public void setUniform(String name, Vector2f value){
        GL20.glUniform2f(getUniformLocataion(name), value.getX(), value.getY());
    }

    public void setUniform(String name, Vector3f value){
        GL20.glUniform3f(getUniformLocataion(name), value.getx(), value.gety(), value.getz());
    }

    public void setUniform(String name, Matrix4f value) {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);
        matrix.put(value.getAll()).flip();
        GL20.glUniformMatrix4fv(getUniformLocataion(name), true, matrix);
    }

    public void bind(){
        GL20.glUseProgram(programID);
    }

    public void unBind(){
        GL20.glUseProgram(0);
    }

    public void destroy(){
        GL20.glDetachShader(programID, vertexID);
        GL20.glDetachShader(programID, fragmentID);
        GL20.glDeleteProgram(programID);
    }

    public int getProgramID(){
        return programID;
    }
}
