package org.openboxprotocol.protocol.actions;

public class ActionGenerateRandomNumber implements Action {

	private long from, to;
	private String key;
	
	private ActionGenerateRandomNumber(String key, long from, long to) {
		if (from >= to)
			throw new IllegalArgumentException("Invalid range for random number generation");
		this.from = from;
		this.to = to;
		this.key = key;
	}
	
	public long getLowerbound() {
		return from;
	}
	
	public long getUpperbound() {
		return to;
	}
	
	public String getKey() {
		return key;
	}
	
	public static class Builder implements Action.Builder {

		private long from, to;
		private String key;
		
		public Builder(String key, long from, long to) {
			if (from >= to)
				throw new IllegalArgumentException("Invalid range for random number generation");
			this.from = from;
			this.to = to;
			this.key = key;
		}
		
		@Override
		public Action build() {
			return new ActionGenerateRandomNumber(key, from, to);
		}
		
	}
}
