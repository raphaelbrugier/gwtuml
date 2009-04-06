package com.objetdirect.gwt.umlapi.client.artifacts.links;
import java.util.LinkedHashMap;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
/**
 * @author  florian
 */
public class ClassRelationLinkArtifact extends LinkArtifact {
GfxObject line = null;
ClassArtifact classArtifact;
RelationArtifact relation;

    public ClassRelationLinkArtifact(ClassArtifact classArtifact, RelationArtifact relation) {
        this.classArtifact = classArtifact;
        this.classArtifact.addDependency(this, relation);
        this.relation = relation;
        this.relation.addDependency(this, classArtifact);
    }
    public void buildGfxObject() {
        leftPoint = classArtifact.getCenter(); 
        rightPoint = relation.getCenter();
        line = GfxManager.getPlatform().buildLine(leftPoint.getX(), leftPoint.getY(), rightPoint.getX(), rightPoint.getY());
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, line);
        GfxManager.getPlatform().setStroke(line,
                ThemeManager.getForegroundColor(), 1);
        GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
        GfxManager.getPlatform().moveToBack(gfxObject);
        
    }
    public void edit(GfxObject gfxObject, int x, int y) {
        // TODO Auto-generated method stub
    }
    public LinkedHashMap<Command, String> getRightMenu() {
        LinkedHashMap<Command, String> rightMenu = new LinkedHashMap<Command, String>();
        Command doNothing = new Command() {
            public void execute() {
            }
        };

        rightMenu.put(null, "Class relation link");
        return rightMenu;
    }

    public boolean isDraggable() {
        return false;
    }
    public void select() {
        GfxManager.getPlatform().setStroke(line,
                ThemeManager.getHighlightedForegroundColor(), 2);
    }
    public void setCanvas(UMLCanvas canvas) {
        this.canvas = canvas;
    }
    public void setLocation(int x, int y) {
        throw new UMLDrawerException(
        "invalid operation : setLocation on a line");
    }
    public void moveTo(int x, int y) {
        throw new UMLDrawerException(
        "invalid operation : setLocation on a line");
    }
    public void unselect() {
        GfxManager.getPlatform().setStroke(line,
                ThemeManager.getForegroundColor(), 1);
    }
}
