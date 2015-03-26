package org.moonlightcontroller.processing;

import java.util.HashMap;
import java.util.Map;

import org.openboxprotocol.protocol.HeaderField;

public abstract class HeaderLookup implements IProcessingModule {

	private Map<HeaderField<?>, String> fieldsToMetadata;
	
	public HeaderLookup() {
		this.fieldsToMetadata = new HashMap<>();
	}
	
	public void putField(HeaderField<?> field, String metadataKey) {
		this.fieldsToMetadata.put(field, metadataKey);
	}
	
	public Map<HeaderField<?>, String> getFieldsToMetadata() {
		return this.fieldsToMetadata;
	}
	
	public String getMetadataField(HeaderField<?> field) {
		return fieldsToMetadata.get(field);
	}
	
}
