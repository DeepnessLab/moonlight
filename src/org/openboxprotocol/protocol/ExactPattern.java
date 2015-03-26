package org.openboxprotocol.protocol;

public class ExactPattern extends AbstractPattern {

	public ExactPattern(String pattern) {
		super(pattern);
	}

	public ExactPattern(String pattern, int firstStartIndex, int lastStartIndex) {
		super(pattern, firstStartIndex, lastStartIndex);
	}

}
