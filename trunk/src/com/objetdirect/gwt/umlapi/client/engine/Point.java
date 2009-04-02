/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;
/**
 * @author  fmounier
 */
public class Point {
    public static Point substract(Point p1, Point p2) {
        return new Point(p1.x - p2.x, p1.y - p2.y); 
    }
    
    /**
     * 
     */
    private int x;
    /**
     * 
     */
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }     
    public Point(int x, double y) {
        this.x = x;
        this.y = (int) y;
    }   
    public Point(double x, int y) {
        this.x = (int) x;
        this.y = y;
    }
    public Point(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }   
    public Point(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }
    /**
     * @return
     * 
     */
    public int getX() {
        return x;
    }
    /**
     * @param x
     * 
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * @return
     * 
     */
    public int getY() {
        return y;
    }
    /**
     * @param y
     * 
     */
    public void setY(int y) {
        this.y = y;
    }

    public void translate(int dx, int dy) {
        x += dx;
        y += dy;
    }
    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";  
    }
}
