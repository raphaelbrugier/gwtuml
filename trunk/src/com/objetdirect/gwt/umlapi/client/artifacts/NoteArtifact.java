package com.objetdirect.gwt.umlapi.client.artifacts;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.NoteFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Note;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager.QualityLevel;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class NoteArtifact extends BoxArtifact {
    GfxObject borderPath;
    GfxObject contentText;
    GfxObject cornerPath;
    int height;
    int width;
    private final Note note;

    public NoteArtifact(final String content) {
	height = 0;
	width = 0;
	note = new Note(content);
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final NoteFieldEditor editor = new NoteFieldEditor(canvas, this);
	editor.setHeightForMultiLine(height
		- OptionsManager.getTextYTotalPadding()
		- OptionsManager.getRectangleYTotalPadding());
	editor.startEdition(note.getText(), getLocation().getX()
		+ OptionsManager.getTextLeftPadding()
		+ OptionsManager.getRectangleLeftPadding(), getLocation()
		.getY()
		+ OptionsManager.getTextYTotalPadding()
		+ OptionsManager.getRectangleTopPadding(), width
		- OptionsManager.getTextXTotalPadding()
		- OptionsManager.getRectangleXTotalPadding(), true);

    }

    public String getContent() {
	return note.getText();
    }

    @Override
    public int getHeight() {
	return height;
    }

    @Override
    public int[] getOpaque() {
	final int[] opaque = new int[] { getLocation().getX(),
		getLocation().getY(), getLocation().getX(),
		getLocation().getY() + getHeight(),
		getLocation().getX() + getWidth(),
		getLocation().getY() + getHeight(),
		getLocation().getX() + getWidth(),
		getLocation().getY() + getCornerHeight(),
		getLocation().getX() + getWidth() - getCornerWidth(),
		getLocation().getY() };
	return opaque;
    }

    @Override
    public GfxObject getOutline() {
	if (OptionsManager.qualityLevelIsAlmost(QualityLevel.NORMAL)) {
	    final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
	    final GfxObject outlineBorderPath = getBorderPath();
	    final GfxObject outlineCornerPath = getCornerPath();
	    GfxManager.getPlatform().addToVirtualGroup(vg, outlineBorderPath);
	    GfxManager.getPlatform().addToVirtualGroup(vg, outlineCornerPath);
	    GfxManager.getPlatform().setStrokeStyle(outlineBorderPath,
		    GfxStyle.DASH);
	    GfxManager.getPlatform().setStrokeStyle(outlineCornerPath,
		    GfxStyle.DASH);
	    GfxManager.getPlatform().setStroke(outlineBorderPath,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    GfxManager.getPlatform().setStroke(outlineCornerPath,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    return vg;
	}
	return super.getOutline();

    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName("Note");
	rightMenu.addItem("Edit content", editCommand());
	return rightMenu;
    }

    @Override
    public int getWidth() {
	return width;
    }

    @Override
    public void select() {
	GfxManager.getPlatform().moveToFront(gfxObject);
	GfxManager.getPlatform().setStroke(borderPath,
		ThemeManager.getHighlightedForegroundColor(), 2);
	GfxManager.getPlatform().setStroke(cornerPath,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    public void setContent(final String content) {
	note.setText(content);
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(borderPath,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().setStroke(cornerPath,
		ThemeManager.getForegroundColor(), 1);
    }

    void createNoteText() {
	height = 0;
	width = 0;
	final String[] noteMultiLine = note.getText().split("\n");
	contentText = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(gfxObject, contentText);

	for (final String noteLine : noteMultiLine) {
	    final GfxObject textLine = GfxManager.getPlatform().buildText(
		    noteLine);
	    GfxManager.getPlatform().addToVirtualGroup(contentText, textLine);
	    GfxManager.getPlatform()
		    .setFont(textLine, OptionsManager.getFont());
	    GfxManager.getPlatform().setStroke(textLine,
		    ThemeManager.getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(textLine,
		    ThemeManager.getForegroundColor());
	    int thisLineWidth = GfxManager.getPlatform().getWidthFor(textLine);
	    int thisLineHeight = GfxManager.getPlatform()
		    .getHeightFor(textLine);

	    GfxManager.getPlatform().translate(
		    textLine,
		    new Point(OptionsManager.getTextLeftPadding(),
		    OptionsManager.getTextTopPadding() + height
			    + thisLineHeight));
	    thisLineWidth += OptionsManager.getTextXTotalPadding();
	    thisLineHeight += OptionsManager.getTextYTotalPadding();
	    width = thisLineWidth > width ? thisLineWidth : width;
	    height += thisLineHeight;
	}
	height += OptionsManager.getRectangleYTotalPadding();
	width += OptionsManager.getRectangleXTotalPadding() + getCornerWidth();

    }

    @Override
    protected void buildGfxObject() {
	createNoteText();
	borderPath = getBorderPath();
	GfxManager.getPlatform().addToVirtualGroup(gfxObject, borderPath);
	cornerPath = getCornerPath();
	GfxManager.getPlatform().addToVirtualGroup(gfxObject, cornerPath);
	GfxManager.getPlatform().translate(contentText, new Point(
		OptionsManager.getRectangleLeftPadding(),
		OptionsManager.getRectangleTopPadding()));
	GfxManager.getPlatform().moveToFront(contentText);
    }

    protected GfxObject getBorderPath() {
	final GfxObject thisBorderPath = GfxManager.getPlatform().buildPath();
	GfxManager.getPlatform().moveTo(thisBorderPath, Point.getOrigin());
	GfxManager.getPlatform().lineTo(thisBorderPath,
		new Point(width - getCornerWidth(), 0));
	GfxManager.getPlatform().lineTo(thisBorderPath, new Point(width,
		getCornerHeight()));
	GfxManager.getPlatform().lineTo(thisBorderPath, new Point(width, height));
	GfxManager.getPlatform().lineTo(thisBorderPath, new Point(0, height));
	GfxManager.getPlatform().lineTo(thisBorderPath, Point.getOrigin());
	GfxManager.getPlatform().setFillColor(thisBorderPath,
		ThemeManager.getBackgroundColor());
	GfxManager.getPlatform().setStroke(thisBorderPath,
		ThemeManager.getForegroundColor(), 1);
	return thisBorderPath;
    }

    protected GfxObject getCornerPath() {
	final GfxObject thisCornerPath = GfxManager.getPlatform().buildPath();
	GfxManager.getPlatform().moveTo(thisCornerPath,
		new Point(width - getCornerWidth(), 0));
	GfxManager.getPlatform().lineTo(thisCornerPath,
		new Point(width - getCornerWidth(), getCornerHeight()));
	GfxManager.getPlatform().lineTo(thisCornerPath, new Point(width,
		getCornerHeight()));
	GfxManager.getPlatform().setFillColor(thisCornerPath,
		ThemeManager.getBackgroundColor());
	GfxManager.getPlatform().setStroke(thisCornerPath,
		ThemeManager.getForegroundColor(), 1);
	return thisCornerPath;
    }

    private Command editCommand() {
	return new Command() {
	    public void execute() {
		edit(null);
	    }
	};
    }

    private int getCornerHeight() {
	return OptionsManager.getNoteCornerHeight();
    }

    private int getCornerWidth() {
	return OptionsManager.getNoteCornerWidth();
    }
}
