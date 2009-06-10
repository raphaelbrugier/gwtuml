package com.google.gwt.user.client;

import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;

/**
 * @author georgopoulos.georgios(at)gmail.com
 */
public abstract class AbstractWindowClosingEvent extends ClosingEvent {
	public static Type<ClosingHandler> getType() {
		return ClosingEvent.getType();
	}
}
