package org.openboxprotocol.protocol;

public class RegexPattern extends AbstractPattern {

	public RegexPattern(String pattern) {
		super(pattern);
	}

	public RegexPattern(String pattern, int firstStartIndex, int lastStartIndex) {
		super(pattern, firstStartIndex, lastStartIndex);
	}

	// TODO: Add option to set anchors
}
