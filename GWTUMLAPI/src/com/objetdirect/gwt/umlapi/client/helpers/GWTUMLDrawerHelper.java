/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.helpers;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;

/**
 * This class is a static helper for entire application
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class GWTUMLDrawerHelper {

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}

		for (int i = 0; i < strLen; i++) {
			if (!(str.charAt(i) == ' ')) {
				return false;
			}
		}

		return true;
	}

	public static boolean isNotBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isLetterOrDigit(str.charAt(i)) == true)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Decode a string encoded in base64
	 * 
	 * @param input
	 *            The {@link String} to decode
	 * @return The base64 decoded {@link String}
	 */
	public static String decodeBase64(final String input) {
		return GWTUMLDrawerHelper.decode64js(input);
	}

	/**
	 * Encode a string in base64
	 * 
	 * @param input
	 *            The {@link String} to encode
	 * @return The base64 encoded {@link String}
	 */
	public static String encodeBase64(final String input) {
		return GWTUMLDrawerHelper.encode64js(input);
	}

	/**
	 * Useful function that return the maximum of an integer list
	 * 
	 * @param list
	 * @return The max of all integer in list
	 */
	public static int getMaxOf(final List<Integer> list) {
		int max = 0;
		for (final int i : list) {
			max = i > max ? i : max;
		}
		return max;
	}

	/**
	 * Make a pretty object name (<code>name[hash]</code>) from an object
	 * 
	 * @param o
	 *            The object
	 * @return The pretty name of the object
	 */
	public static String getShortName(final Object o) {
		if (o == null) {
			return "null";
		}
		final String objectName = o.getClass().getName() + "[" + o.hashCode() + "]";
		final String[] stringParts = objectName.split("\\.");
		final int lastIndex = stringParts.length;
		if (lastIndex > 1) {
			return stringParts[lastIndex - 1];
		}

		Log.warn("Cannot split " + objectName + " <=" + lastIndex);
		return "" + o;

	}

	private native static String decode64js(String input) /*-{
		var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;

		// remove all characters that are not A-Z, a-z, 0-9, +, /, or =
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

		do {
		enc1 = keyStr.indexOf(input.charAt(i++));
		enc2 = keyStr.indexOf(input.charAt(i++));
		enc3 = keyStr.indexOf(input.charAt(i++));
		enc4 = keyStr.indexOf(input.charAt(i++));

		chr1 = (enc1 << 2) | (enc2 >> 4);
		chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
		chr3 = ((enc3 & 3) << 6) | enc4;

		output = output + String.fromCharCode(chr1);

		if (enc3 != 64) {
		output = output + String.fromCharCode(chr2);
		}
		if (enc4 != 64) {
		output = output + String.fromCharCode(chr3);
		}
		} while (i < input.length);

		return output;
	}-*/;

	// This code was written by Tyler Akins and has been placed in the
	// public domain. It would be nice if you left this header intact.
	// Base64 code from Tyler Akins -- http://rumkin.com

	private native static String encode64js(String input) /*-{
		var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;

		do {
		chr1 = input.charCodeAt(i++);
		chr2 = input.charCodeAt(i++);
		chr3 = input.charCodeAt(i++);

		enc1 = chr1 >> 2;
		enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
		enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
		enc4 = chr3 & 63;

		if (isNaN(chr2)) {
		enc3 = enc4 = 64;
		} else if (isNaN(chr3)) {
		enc4 = 64;
		}

		output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + 
		keyStr.charAt(enc3) + keyStr.charAt(enc4);
		} while (i < input.length);

		return output;
	}-*/;
}
