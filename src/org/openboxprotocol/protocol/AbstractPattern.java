package org.openboxprotocol.protocol;

public abstract class AbstractPattern implements PayloadPattern {

	private String pattern;
	private int firstStartIndex = -1;
	private int lastStartIndex = -1;
	
	public AbstractPattern(String pattern) {
		this.pattern = pattern;
	}

	public AbstractPattern(String pattern, int firstStartIndex, int lastStartIndex) {
		this.pattern = pattern;
		this.firstStartIndex = firstStartIndex;
		this.lastStartIndex = lastStartIndex;
	}
	
	@Override
	public String getPattern() {
		return pattern;
	}

	@Override
	public int getFirstStartIndex() {
		return firstStartIndex;
	}

	@Override
	public int getLastStartIndex() {
		return lastStartIndex;
	}

}
