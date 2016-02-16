package org.blueo.commons.text;

import org.javatuples.Triplet;

import com.google.common.base.Strings;

public class BlueoStrs {
	
	// content, beforeContent, afterContent
	public static Triplet<String, String, String> parse(String text, char start, char end) {
		if (Strings.isNullOrEmpty(text)) {
			return new Triplet<String, String, String>(null, null, null);
		}
		char[] charArray = text.toCharArray();
		int count = 0;
		Integer startPos = null;
		Integer endPos = null;
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (c == start) {
				count++;
				if (startPos == null) {
					startPos = i;
				}
			} else if (c == end) {
				if (count > 0) {
					count--;
					if (count == 0 && endPos == null) {
						endPos = i;
						break;
					}
				}
			}
		}
		if (startPos == null || endPos == null) {
			return new Triplet<String, String, String>(null, text, null);
		}
		//
		String beforeContent = text.substring(0, startPos);
		String content = text.substring(startPos + 1, endPos);
		String afterContent = text.substring(endPos + 1, text.length());
		return new Triplet<String, String, String>(content, beforeContent, afterContent);
	}

}
