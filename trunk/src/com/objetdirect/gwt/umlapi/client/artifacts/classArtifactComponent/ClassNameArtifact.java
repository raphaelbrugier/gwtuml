package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.NamePartFieldEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  fmounier
 */
public class ClassNameArtifact extends ClassPartArtifact {

    private String className;
    private String stereotype;
    private GfxObject nameText;
    private GfxObject stereotypeText;
    private GfxObject nameRect;

    public ClassNameArtifact(String className) {
        this.className = className;
        height = 0;
        width = 0;
    }
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the stereotype
     */
    public String getStereotype() {
        return stereotype;
    }
    /**
     * @param stereotype the stereotype to set
     */
    public void setStereotype(String stereotype) {
        this.stereotype = stereotype;
    }
    @Override
    public void buildGfxObject() {
        if(textVirtualGroup == null) computeBounds();	
        nameRect = GfxManager.getPlatform().buildRect(classWidth, height);
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, nameRect);
        GfxManager.getPlatform().setFillColor(nameRect,	ThemeManager.getBackgroundColor());
        GfxManager.getPlatform().setStroke(nameRect, ThemeManager.getForegroundColor(), 1);

        //Centering name class :
        GfxManager.getPlatform().translate(textVirtualGroup, OptionsManager.getRectangleLeftPadding() + (classWidth-width)/2, OptionsManager.getRectangleTopPadding());
        GfxManager.getPlatform().moveToFront(textVirtualGroup);
    }
    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }
    @Override
    public void computeBounds() {
        height = 0;
        width = 0;
        textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, textVirtualGroup);
        if(stereotype != null) {
            stereotypeText = GfxManager.getPlatform().buildText(stereotype);
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, stereotypeText);     
            GfxManager.getPlatform().setFont(stereotypeText, OptionsManager.getSmallCapsFont());
            GfxManager.getPlatform().setStroke(stereotypeText, ThemeManager.getBackgroundColor(), 0);
            GfxManager.getPlatform().setFillColor(stereotypeText, ThemeManager.getForegroundColor());
            width =  GfxManager.getPlatform().getWidthFor(stereotypeText);
            height +=  GfxManager.getPlatform().getHeightFor(stereotypeText);

            GfxManager.getPlatform().translate(stereotypeText, OptionsManager.getTextLeftPadding(), OptionsManager.getTextTopPadding() + height);

            width += OptionsManager.getTextXTotalPadding();
            height += OptionsManager.getTextYTotalPadding();
        }
        nameText = GfxManager.getPlatform().buildText(className);
        GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, nameText);		
        GfxManager.getPlatform().setFont(nameText, OptionsManager.getSmallCapsFont());
        GfxManager.getPlatform().setStroke(nameText, ThemeManager.getBackgroundColor(), 0);
        GfxManager.getPlatform().setFillColor(nameText,	ThemeManager.getForegroundColor());
        int thisAttributeWidth =  GfxManager.getPlatform().getWidthFor(nameText) + OptionsManager.getTextXTotalPadding();
        width  =  thisAttributeWidth > width ? thisAttributeWidth : width;
        height +=  GfxManager.getPlatform().getHeightFor(nameText);	
        GfxManager.getPlatform().translate(nameText, OptionsManager.getTextLeftPadding(), OptionsManager.getTextTopPadding() + height);
        height += OptionsManager.getTextYTotalPadding();

        width += OptionsManager.getRectangleXTotalPadding();
        height += OptionsManager.getRectangleYTotalPadding();
        Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);	
    }
    @Override
    public void setClassWidth(int width) {
        this.classWidth = width;
    }
    @Override
    public void edit() {
        if(stereotype == null) {
            stereotype = "<<Abstract>>";
            classArtifact.rebuildGfxObject();
            edit(stereotypeText);
        } else {
            edit(nameText);
        }
        
        
        

    }
    @Override
    public void edit(GfxObject gfxObject) {
        boolean isTheStereotype = gfxObject.equals(stereotypeText);  
        NamePartFieldEditor editor = new NamePartFieldEditor(canvas, this, isTheStereotype);
        String edited;
        if(isTheStereotype)
            edited = stereotype;
        else 
            edited = className;
        editor.startEdition(edited, (classArtifact.getX() + OptionsManager.getTextLeftPadding() + OptionsManager.getRectangleLeftPadding()),
                (classArtifact.getY() + GfxManager.getPlatform().getYFor(gfxObject) - GfxManager.getPlatform().getHeightFor(gfxObject) + OptionsManager.getRectangleTopPadding()),
                classWidth - OptionsManager.getTextXTotalPadding() - OptionsManager.getRectangleXTotalPadding(), false);
    }
    @Override
    public int[] getOpaque() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public GfxObject getOutline() {
        // TODO Auto-generated method stub
        return null;
    }      
    private Command createStereotype() {       
        return new Command() {
            public void execute() {
                edit();
            }
        };
    }
    private Command editCommand(final GfxObject gfxo) {       
        return new Command() {
            public void execute() {
                edit(gfxo);
            }
        };
    }
    private Command deleteStereotype() {       
        return new Command() {
            public void execute() {
                stereotype = null;
                classArtifact.rebuildGfxObject();
            }
        };
    }
    @Override
    public MenuBarAndTitle getRightMenu() {
        MenuBarAndTitle rightMenu = new MenuBarAndTitle();
        rightMenu.setName("Name");
        rightMenu.addItem("Edit Name", editCommand(nameText));
        if(stereotypeText == null)
            rightMenu.addItem("Add stereotype", createStereotype());
        else {
            rightMenu.addItem("Edit Stereotype", editCommand(stereotypeText));
            rightMenu.addItem("Delete Stereotype", deleteStereotype());
        }
        

        return rightMenu;
    }

    @Override
    public int getX() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int getY() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public boolean isDraggable() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void moveTo(int fx, int fy) {
        // TODO Auto-generated method stub

    }
    @Override
    public void select() {
        GfxManager.getPlatform().setStroke(nameRect, ThemeManager.getHighlightedForegroundColor(), 2);
    }

    @Override
    public void unselect() {
        GfxManager.getPlatform().setStroke(nameRect, ThemeManager.getForegroundColor(), 1);	
    }

}
