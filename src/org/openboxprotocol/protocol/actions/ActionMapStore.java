package org.openboxprotocol.protocol.actions;

import org.openboxprotocol.types.SessionID;

/**
 * Saves a value from metadata to app map on OSP
 *
 * @param <K> Type of key in app map
 */
public class ActionMapStore implements Action {
	
	private SessionID sessionID;
	private String mapKey;
	private String metadataKey;
	
	private ActionMapStore(SessionID sessionID, String mapKey, String metadataKey) {
		this.sessionID = sessionID;
		this.mapKey = mapKey;
		this.metadataKey = metadataKey;
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
	
	public static class Builder implements Action.Builder {

		private SessionID sessionID;
		private String mapKey;
		private String metadataKey;
		
		public Builder(SessionID sessionID, String mapKey, String metadataKey) {
			this.sessionID = sessionID;
			this.mapKey = mapKey;
			this.metadataKey = metadataKey;
		}
		
		@Override
		public Action build() {
			return new ActionMapStore(sessionID, mapKey, metadataKey);
		}
		
	}
}
