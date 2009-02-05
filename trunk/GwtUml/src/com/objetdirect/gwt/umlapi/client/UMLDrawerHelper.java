package com.objetdirect.gwt.umlapi.client;

import com.allen_sauer.gwt.log.client.Log;

/**
 * This class is a static helper for entire application
 * @author fmounier
 *
 */
public class UMLDrawerHelper {

	/**
	 * Make a pretty object name (<code>name[hash]</code>) from an object
	 * @param o The object 
	 * @return The pretty name of the object
	 */
	public static String getShortName(Object o) {
		if (o == null)
			return "null";
		String objectName = o.getClass().getName() + "[" + o.hashCode() + "]";
		String[] stringParts = objectName.split("\\.");
		int lastIndex = stringParts.length;
		if (lastIndex > 1)
			return stringParts[lastIndex - 1];
		else {
			Log.warn("Cannot split " + objectName + " <=" + lastIndex);
			return "" + o;
		}
	}
}
