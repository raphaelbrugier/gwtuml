package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.LinkedHashMap;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.NoteFieldEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Note;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

public class NoteArtifact extends BoxArtifact {


	private Note note;
	GfxObject borderPath;
	GfxObject contentText;
	GfxObject cornerPath;
	

	public NoteArtifact(String content) {
		note = new Note(content);
		
	}

	public String getContent() {
		return note.getText();
	}

	@Override
	public int getHeight() {
		int height = OptionsManager.getRectangleYTotalPadding() + OptionsManager.getTextYTotalPadding() +
				+  GfxManager.getPlatform().getHeightFor(contentText);
		return height;
	}

	@Override
	public int[] getOpaque() {
		int[] opaque = new int[] { getX(), getY(), getX(),
				getY() + getHeight(), getX() + getWidth(),
				getY() + getHeight(), getX() + getWidth(),
				getY() + getCornerHeight(),
				getX() + getWidth() - getCornerWidth(), getY() };
		return opaque;
	}

	@Override
	public GfxObject getOutline() {
		GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		GfxObject outlineBorderPath = getBorderPath();
		GfxObject outlineCornerPath = getCornerPath();
		GfxManager.getPlatform().addToVirtualGroup(vg, outlineBorderPath);
		GfxManager.getPlatform().addToVirtualGroup(vg, outlineCornerPath);
		GfxManager.getPlatform().setStrokeStyle(outlineBorderPath, GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(outlineCornerPath, GfxStyle.DASH);
		GfxManager.getPlatform().setStroke(outlineBorderPath, ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(outlineCornerPath, ThemeManager.getHighlightedForegroundColor(), 1);
		return vg;
	}

	public LinkedHashMap<String, Command> getRightMenu() {

		LinkedHashMap<String, Command> rightMenu = new LinkedHashMap<String, Command>();

		Command doNothing = new Command() {
			public void execute() {
			}

		};
		Command remove = new Command() {
			public void execute() {
				getCanvas().removeSelected();
			}
		};
		rightMenu.put("Note", doNothing);
		rightMenu.put("-", null);
		rightMenu.put("> Edit", doNothing);
		rightMenu.put("> Delete", remove);
		return rightMenu;
	}
	@Override
	public int getWidth() {
		int width = OptionsManager.getRectangleXTotalPadding() + OptionsManager.getTextXTotalPadding()
				+  GfxManager.getPlatform().getWidthFor(contentText);
		return width;
	}

	public void select() {
		GfxManager.getPlatform().moveToFront(gfxObject);
		GfxManager.getPlatform().setStroke(borderPath, ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(cornerPath, ThemeManager.getHighlightedForegroundColor(), 2);
	}

	public void setContent(String content) {
		note.setText(content);
	}

	public void unselect() {
		GfxManager.getPlatform().setStroke(borderPath, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(cornerPath, ThemeManager.getForegroundColor(), 1);
	}
	void createNoteText() {

		contentText = GfxManager.getPlatform().buildText(note.getText());
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, contentText);
		GfxManager.getPlatform().setFont(contentText, OptionsManager.getFont());
		GfxManager.getPlatform().setFillColor(contentText, ThemeManager.getForegroundColor());
		GfxManager.getPlatform().translate(contentText, OptionsManager.getTextLeftPadding(),
				OptionsManager.getTextTopPadding() +  GfxManager.getPlatform().getHeightFor(contentText));
	}
	@Override
	protected void buildGfxObject() {
		createNoteText();
		borderPath = getBorderPath();	
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, borderPath);
		cornerPath = getCornerPath();
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, cornerPath);
		GfxManager.getPlatform().translate(contentText, OptionsManager.getRectangleLeftPadding(), OptionsManager.getRectangleTopPadding());
		GfxManager.getPlatform().moveToFront(contentText);
	}
	protected GfxObject getBorderPath() {

		GfxObject thisBorderPath = GfxManager.getPlatform().buildPath();		
		GfxManager.getPlatform().moveTo(thisBorderPath, 0, 0);
		GfxManager.getPlatform().lineTo(thisBorderPath, getWidth() - getCornerWidth(), 0);
		GfxManager.getPlatform().lineTo(thisBorderPath, getWidth(), getCornerHeight());
		GfxManager.getPlatform().lineTo(thisBorderPath, getWidth(), getHeight());
		GfxManager.getPlatform().lineTo(thisBorderPath, 0, getHeight());
		GfxManager.getPlatform().lineTo(thisBorderPath, 0, 0);
		GfxManager.getPlatform().setFillColor(thisBorderPath, ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(thisBorderPath, ThemeManager.getForegroundColor(), 1);
		return thisBorderPath;
	}
	protected GfxObject getCornerPath() {

		GfxObject thisCornerPath = GfxManager.getPlatform().buildPath();		
		GfxManager.getPlatform().moveTo(thisCornerPath, getWidth() - getCornerWidth(), 0);
		GfxManager.getPlatform().lineTo(thisCornerPath, getWidth() - getCornerWidth(),getCornerHeight());
		GfxManager.getPlatform().lineTo(thisCornerPath, getWidth(), getCornerHeight());
		GfxManager.getPlatform().setFillColor(thisCornerPath, ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(thisCornerPath, ThemeManager.getForegroundColor(), 1);
		return thisCornerPath;
	}
	private int getCornerHeight() {
		return getHeight() / 3;
	}

	private int getCornerWidth() {
		return getCornerHeight();
	}

	public void edit(GfxObject gfxObject, int x, int y) {
		NoteFieldEditor editor = new NoteFieldEditor(canvas, this);
		editor.startEdition(note.getText(), this.x + OptionsManager.getTextLeftPadding() + OptionsManager.getRectangleLeftPadding(),
				this.y + OptionsManager.getTextYTotalPadding() + OptionsManager.getRectangleTopPadding(), 
				getWidth() - OptionsManager.getTextXTotalPadding() - OptionsManager.getRectangleXTotalPadding());
		
	}
}
