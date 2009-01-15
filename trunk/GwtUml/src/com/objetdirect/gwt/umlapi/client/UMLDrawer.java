package com.objetdirect.gwt.umlapi.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.ThemeManager.Theme;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassDependencyArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationshipArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.engine.ClassDiagramAnimator;
import com.objetdirect.gwt.umlapi.client.engine.StandardClassEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.IncubatorGfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.tatami.TatamiGfxPlatfrom;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class UMLDrawer implements EntryPoint {

	public void onModuleLoad() {
		Log.setCurrentLogLevel(Log.LOG_LEVEL_WARN);

		Log.info("Starting App");

		HotKeyManager.forceStaticInit();

		VerticalPanel startPanel = new VerticalPanel();
		startPanel.setWidth("100%");
		startPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		startPanel.setSpacing(10);

		RootPanel.get().add(startPanel, 0, 50);

		Button start = new Button("Start New Uml Class Diagram");
		Button startDemo = new Button("Start Demo");
		final ListBox gfxEngineListBox = new ListBox();
		final ListBox themeListBox = new ListBox();
		start.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				ThemeManager.setCurrentTheme(
						ThemeManager.getThemeFromName(
								themeListBox.getItemText(themeListBox.getSelectedIndex())
						)
				);
				
				if(gfxEngineListBox.getItemText(gfxEngineListBox.getSelectedIndex()).equalsIgnoreCase("Tatami Gfx"))
					GfxManager.setInstance(new TatamiGfxPlatfrom());
				else
					GfxManager.setInstance(new IncubatorGfxPlatform());
				RootPanel.get().clear();
				createDrawer();
			}
		});
		startDemo.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				ThemeManager.setCurrentTheme(
						ThemeManager.getThemeFromName(
								themeListBox.getItemText(themeListBox.getSelectedIndex())
						)
				);

				if(gfxEngineListBox.getItemText(gfxEngineListBox.getSelectedIndex()).equalsIgnoreCase("Tatami Gfx"))
					GfxManager.setInstance(new TatamiGfxPlatfrom());
				else
					GfxManager.setInstance(new IncubatorGfxPlatform());
				RootPanel.get().clear();
				createDemoDrawer();
			}
		});
		gfxEngineListBox.addItem("Tatami Gfx");
		gfxEngineListBox.addItem("Incubator GWTCanvas Gfx");

		for(Theme theme : Theme.values())
		{
			themeListBox.addItem(ThemeManager.getThemeName(theme));
		}
		startPanel.add(start);
		startPanel.add(startDemo);
		startPanel.add(gfxEngineListBox);
		startPanel.add(themeListBox);
		
		Log.getDivLogger().moveTo(0, startPanel.getAbsoluteTop() + startPanel.getOffsetHeight() + 50);
		Log.getDivLogger().setSize("100%", "400px");
		

	}

	private void createDrawer() {
		Log.info("Creating drawer");

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setWidth("100%");
		//hPanel.setSpacing(10);
		RootPanel.get().add(hPanel, 10, 10);

		final UMLCanvas gc = new UMLCanvas(800, 600);
		hPanel.add(gc);
		HotKeyManager.setActiveCanvas(gc);
		Log.getDivLogger().moveTo(gc.getAbsoluteLeft(), gc.getAbsoluteTop() + gc.getOffsetHeight() + 10);
		Log.getDivLogger().setSize("100%", "400px");
		ClassArtifact defaultclass = new ClassArtifact("Class1");
		defaultclass.setLocation(400, 300);
		gc.add(defaultclass);

		DOM.setStyleAttribute(gc.getElement(), "border", "2px solid black");

		gc.addUMLElementListener(new ClassDiagramAnimator().
				setClassEditor(new StandardClassEditor())
		);
		Log.info("Init class end\nBegin sidebar");
		VerticalPanel sidePanel = new VerticalPanel();
		sidePanel.setSpacing(5);

		Button addClass = new Button("Add Class (C)");
		Button addNote = new Button("Add Note (N)");
		Button makeLink = new Button("Make Link (L)");

		addClass.setPixelSize(128, 64);
		addNote.setPixelSize(128, 64);
		makeLink.setPixelSize(128, 64);

		addClass.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				gc.addNewClass();	        	
			}
		});

		addNote.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				gc.addNewNote();
			}
		});

		makeLink.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				gc.addNewLink();
			}
		});

		sidePanel.add(addClass);
		sidePanel.add(addNote);
		sidePanel.add(makeLink);

		hPanel.add(sidePanel);

		Log.info("Init end");
	}






	private void createDemoDrawer() {
		Log.info("Creating demodrawer");

		final UMLCanvas gc = new UMLCanvas(1000, 600);
		RootPanel.get().add(gc, 10, 10);	
		Log.getDivLogger().moveTo(gc.getAbsoluteLeft(), gc.getAbsoluteTop() + gc.getOffsetHeight() + 10);
		Log.getDivLogger().getWidget().setSize("100%", "400px");

		ClassArtifact dataManagerClass = new ClassArtifact("DataManager");
		dataManagerClass.setLocation(500, 100);
		ClassArtifact businessObjectClass = new ClassArtifact("BusinessObject");
		businessObjectClass.setLocation(650, 100);
		ClassArtifact serializableClass = new ClassArtifact("Serializable");
		serializableClass.setLocation(700, 200);

		ClassArtifact clientClass = new ClassArtifact("Client");
		clientClass.addAttribute(new Attribute("String", "firstName"));
		clientClass.addAttribute(new Attribute("String", "lastName"));
		clientClass.addMethod(new Method(null, "Client", new Parameter[] {
				new Parameter("String", "firstName"),
				new Parameter("String", "lastName")}));
		clientClass.addMethod(new Method("void", "addProduct", new Parameter[] {
				new Parameter("Product", "product"),
				new Parameter("Date", "when")}));
		clientClass.setLocation(300, 300);

		ClassDependencyArtifact clientDataManager = new ClassDependencyArtifact.Simple(clientClass, dataManagerClass);	
		ClassDependencyArtifact clientBusinessObject = new ClassDependencyArtifact.Extension(clientClass, businessObjectClass);
		ClassDependencyArtifact clientSerializable = new ClassDependencyArtifact.Implementation(clientClass, serializableClass);

		ClassArtifact eventClass = new ClassArtifact("Event");
		eventClass.setLocation(250, 150);
		RelationshipArtifact relClientEvent = new RelationshipArtifact(clientClass, eventClass);
		relClientEvent.setName("client-event");
		relClientEvent.setRightArrow(true);
		relClientEvent.setLeftCardinality("1");
		relClientEvent.setRightCardinality("0..*");
		relClientEvent.setLeftRole("events");
		relClientEvent.setLeftConstraint("{ordered}");
		relClientEvent.setRightRole("client");

		ClassArtifact addressClass = new ClassArtifact("Address");
		addressClass.setLocation(50, 250);
		RelationshipArtifact relClientAddress = new RelationshipArtifact(clientClass, addressClass);
		relClientAddress.setRightArrow(true);
		relClientAddress.setLeftCardinality("1");
		relClientAddress.setLeftConstraint("{ordered}");
		relClientAddress.setRightCardinality("0..*");

		ClassArtifact productClass = new ClassArtifact("Product");
		productClass.setLocation(50, 450);		
		ClassArtifact paymentClass = new ClassArtifact("Payment");
		paymentClass.setLocation(150, 550);		
		RelationshipArtifact relClientProduct = new RelationshipArtifact(clientClass, productClass);
		relClientProduct.setName("client-product");
		relClientProduct.setRightArrow(true);
		relClientProduct.setLeftCardinality("1");
		relClientProduct.setRightCardinality("0..*");
		relClientProduct.setRelationClass(paymentClass);

		NoteArtifact note = new NoteArtifact();
		note.setContent("Ceci est une note\nconcernant le client");
		note.setLocation(400, 500);
		NoteLinkArtifact noteClientLink = new NoteLinkArtifact(note, clientClass);
		NoteLinkArtifact notePaymentLink = new NoteLinkArtifact(note, paymentClass);
		NoteLinkArtifact noteRelationshipLink = new NoteLinkArtifact(note, relClientProduct);
		NoteLinkArtifact noteDependencyLink = new NoteLinkArtifact(note, clientDataManager);

		gc.add(clientClass);
		gc.add(dataManagerClass);
		gc.add(clientDataManager);
		gc.add(businessObjectClass);
		gc.add(clientBusinessObject);
		gc.add(serializableClass);
		gc.add(clientSerializable);
		gc.add(eventClass);
		gc.add(relClientEvent);
		gc.add(addressClass);
		gc.add(relClientAddress);
		gc.add(productClass);
		gc.add(paymentClass);
		gc.add(relClientProduct);
		gc.add(note);
		gc.add(noteClientLink);
		gc.add(notePaymentLink);
		gc.add(noteRelationshipLink);
		gc.add(noteDependencyLink);
		DOM.setStyleAttribute(gc.getElement(), "border", "2px solid black");

		gc.addUMLElementListener(new ClassDiagramAnimator().
				setClassEditor(new StandardClassEditor())
		);
		Log.info("Init demodrawer end");
	}

}
