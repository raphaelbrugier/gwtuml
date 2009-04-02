/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.links.LinkArtifact.LinkAdornment.Shape;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author florian
 *
 */
public abstract class GeometryPlatform {


    public GfxObject buildAdornment(Point target, Point origin, LinkAdornment adornment) {
        GfxObject path = GfxManager.getPlatform().buildPath();	    
        int width = 0, lenght = 0;
        GfxColor foreColor, backColor;
        foreColor = ThemeManager.getForegroundColor();
        if(adornment.isInverted()) {
            backColor = ThemeManager.getForegroundColor();
        } else {
            backColor = ThemeManager.getBackgroundColor();
        }
        
        switch (adornment.getShape()) {
        case DIAMOND:
            width = OptionsManager.getDiamondWidth();
            lenght = OptionsManager.getDiamondLength();
            break;
        case ARROW:
            if(adornment.isSolid()) {
                width = OptionsManager.getSolidArrowWidth();
                lenght = OptionsManager.getSolidArrowLenght(); 
            } else {
                width = OptionsManager.getArrowWidth();
                lenght = OptionsManager.getArrowLenght();
            }
            break;
        }
        
        ArrayList<Point> points = getAdornmentPoints(target, origin, width, lenght);

        GfxManager.getPlatform().moveTo(path, points.get(0).getX(), points.get(0).getY());
        GfxManager.getPlatform().lineTo(path, target.getX(), target.getY());
        GfxManager.getPlatform().lineTo(path, points.get(1).getX(), points.get(1).getY());
        if(adornment == LinkAdornment.WIRE_ARROW)
            GfxManager.getPlatform().lineTo(path, target.getX(), target.getY());      
        else {
            if(adornment.getShape() == Shape.DIAMOND) {
                GfxManager.getPlatform().lineTo(path, points.get(2).getX(), points.get(2).getY());
            }
            GfxManager.getPlatform().lineTo(path, points.get(0).getX(), points.get(0).getY());
        }
        GfxManager.getPlatform().setStroke(path, foreColor, 1);
        GfxManager.getPlatform().setFillColor(path, backColor);
        return path;
    }

    //. origin
    // \     ^
    //  \  /  \
    //   \/    \
    //   /°root \
    //  /        \
    // /__________\. target

    public Point getArrowRoot(Point target, double xDiff, double yDiff, double distance, int lenght) {
        final double thalesConst = lenght / distance;
        Point root = new Point(target);
        root.translate(xDiff * thalesConst, yDiff * thalesConst);
        return root; 
    }


    //       ^
    //     /  \
    //    /    \
    //   /      \
    //  /        \
    // /__________\.
    //      |
    //      |
    //      |
    //      |
    //
    public ArrayList<Point> getAdornmentPoints(Point point1, Point point2, int width, int lenght) {
        ArrayList<Point> arrowPoints = new ArrayList<Point>();

        double xDiff = point2.getX() - point1.getX();
        if(xDiff == 0.) xDiff = 0.00000001;
        final double yDiff = point2.getY() - point1.getY();	    
        final double distance = Math.sqrt(((Math.pow(xDiff, 2) + Math.pow(yDiff, 2))));
        final double slope = yDiff / xDiff; 
        final double yShift = (width / 2.) / Math.sqrt(1. + Math.pow(slope, 2));
        final double xShift = slope * yShift;
        Point root = getArrowRoot(point1, xDiff, yDiff, distance, lenght);
        Point up = new Point(root);
        Point down = new Point(root);
        Point diamondTail = Point.substract(root, Point.substract(point1, root));
        up.translate(-xShift, yShift);
        down.translate(xShift, -yShift);
        arrowPoints.add(up); 
        arrowPoints.add(down);
        arrowPoints.add(diamondTail);
        return arrowPoints;
    }

    public ArrayList<Point> getLineBetween(UMLArtifact firstUMLArtifact, UMLArtifact secondUMLArtifact){
        long t = System.currentTimeMillis();
        ArrayList<Point> pointList = getLineBetweenWithTime(firstUMLArtifact, secondUMLArtifact);
        Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to compute line between " + firstUMLArtifact + " and " + secondUMLArtifact);
        return pointList;


    }
    public Point getPointForLine(UMLArtifact uMLArtifact, Point point) {
        long t = System.currentTimeMillis();
        Point pt = getPointForLineWithTime(uMLArtifact, point);
        Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to compute line between " + uMLArtifact + " and a point");
        return pt;
    }

    public abstract ArrayList<Point> getLineBetweenWithTime(UMLArtifact firstUMLArtifact, UMLArtifact secondUMLArtifact);

    public abstract Point getPointForLineWithTime(UMLArtifact uMLArtifact, Point point);
}
