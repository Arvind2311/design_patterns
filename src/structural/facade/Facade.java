package structural.facade;

import java.util.ArrayList;
import java.util.List;

public class Facade {
    public static void main(String[] args) {

        // Low-Level Implementation. End User doesn't know and care about this implementation.
        Buffer buffer = new Buffer(30,20);
        ViewPort viewPort = new ViewPort(buffer, 30, 20, 0, 0);;
        Console console = new Console(30, 20);
        console.addViewPort(viewPort);
        console.render();

        // Alternative Implementation using Facade
        Console console1 = Console.newConsle(30, 20);
        console1.render();
    }
}

class Buffer{
    private char[] characters;
    private int lineWidth;

    public Buffer(int lineHeight, int lineWidth) {
        this.lineWidth = lineWidth;
        characters = new char[lineWidth*lineHeight];
    }

    public char charAt(int x,int y){
        return characters[y*lineWidth+x];
    }
}

class ViewPort{

    private final Buffer buffer;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;

    public ViewPort(Buffer buffer, int width, int height, int offsetX, int offsetY) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public char charAt(int x,int y){
        return buffer.charAt(x+offsetX, y+offsetY);
    }
}

class Console{
    private List<ViewPort> viewPorts = new ArrayList<>();

    int width, height;

    public Console(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addViewPort(ViewPort viewPort){
        viewPorts.add(viewPort);
    }


    public static Console newConsle(int width, int height){
        Buffer buffer = new Buffer(width, height);
        ViewPort viewPort = new ViewPort(buffer, width, height, 0, 0);
        Console console = new Console(width, height);
        console.addViewPort(viewPort);
        return console;
    }

    public void render(){
        for(int y=0; y<height; y++){
            for(int x=0;x<width;x++){
                for (ViewPort vp:viewPorts){
                    System.out.print(vp.charAt(x,y));
                }
                System.out.println();
            }
        }
    }
}