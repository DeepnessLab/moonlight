package org.openboxprotocol.protocol.actions;

import org.openboxprotocol.types.SessionID;

public class ActionMapRetrieve implements Action {

	private SessionID sessionID;
	private String mapKey;
	private String metadataKey;
	private boolean hardFailure;
	
	private ActionMapRetrieve(SessionID sessionID, String mapKey, 
			String metadataKey, boolean hardFailure) {
		this.sessionID = sessionID;
		this.mapKey = mapKey;
		this.metadataKey = metadataKey;
		this.hardFailure = hardFailure;
	}
	
	public SessionID getSessionID() {
		return sessionID;
	}
	
	public String getMapKey() {
		return mapKey;
	}
	
	public String getMetadataKey() {
		return metadataKey;
	}
	
	public boolean overwrite() {
		return hardFailure;
	}
	
	public static class Builder implements Action.Builder {

		private SessionID sessionID;
		private String mapKey;
		private String metadataKey;
		private boolean hardFailure;
		
		public Builder(SessionID sessionID, String mapKey, 
				String metadataKey, boolean hardFailure) {
			this.sessionID = sessionID;
			this.mapKey = mapKey;
			this.metadataKey = metadataKey;
			this.hardFailure = hardFailure;
		}
		
		@Override
		public Action build() {
			return new ActionMapRetrieve(sessionID, mapKey, metadataKey, hardFailure);
		}
		
	}

}
