package org.openboxprotocol.protocol.actions;

import java.util.HashMap;
import java.util.Map;

import org.openboxprotocol.protocol.HeaderField;
import org.openboxprotocol.types.ValueType;

import com.google.common.collect.ImmutableMap;

public class ActionModifyHeader implements Action {
	private Map<HeaderField<?>, ValueType<?>> exactValues;
	private Map<HeaderField<?>, String> valuesFromGlobals;
	private Map<HeaderField<?>, String> valuesFromMetadata;
	
	private ActionModifyHeader(Map<HeaderField<?>, ValueType<?>> exactValues,
			Map<HeaderField<?>, String> valuesFromGlobals,
			Map<HeaderField<?>, String> valuesFromMetadata) {
		this.exactValues = exactValues;
		this.valuesFromGlobals = valuesFromGlobals;
		this.valuesFromMetadata = valuesFromMetadata;
	}
	
	@SuppressWarnings("unchecked")
	public <F extends ValueType<F>> F getExactValue(HeaderField<F> field) {
		if (!isExactValue(field))
			throw new IllegalArgumentException("Field is not set with exact value");
		return (F)exactValues.get(field);
	}
	
	public <F extends ValueType<F>> boolean isExactValue(HeaderField<F> field) {
		return exactValues.containsKey(field);
	}
	
	public <F extends ValueType<F>> String getGlobalValue(HeaderField<F> field) {
		if (!isGlobalValue(field))
			throw new IllegalArgumentException("Field is not set with global value");
		return valuesFromGlobals.get(field);
	}
	
	public <F extends ValueType<F>> boolean isGlobalValue(HeaderField<F> field) {
		return valuesFromGlobals.containsKey(field);
	}
	
	public <F extends ValueType<F>> String getMetadataValue(HeaderField<F> field) {
		if (!isMetadataValue(field))
			throw new IllegalArgumentException("Field is not set with metadata value");
		return valuesFromMetadata.get(field);
	}
	
	public <F extends ValueType<F>> boolean isMetadataValue(HeaderField<F> field) {
		return valuesFromMetadata.containsKey(field);
	}
	
	public static class Builder implements Action.Builder {

		private Map<HeaderField<?>, ValueType<?>> exactValues;
		private Map<HeaderField<?>, String> valuesFromGlobals;
		private Map<HeaderField<?>, String> valuesFromMetadata;
		
		public Builder() {
			exactValues = new HashMap<>();
			valuesFromGlobals = new HashMap<>();
			valuesFromMetadata = new HashMap<>();
		}
		
		public <F extends ValueType<F>> Builder setExactValue(HeaderField<F> field, ValueType<F> value) {
			exactValues.put(field, value);
			return this;
		}
		
		public <F extends ValueType<F>> Builder setGlobalValue(HeaderField<F> field, String globalName) {
			valuesFromGlobals.put(field, globalName);
			return this;
		}
		
		public <F extends ValueType<F>> Builder setMetadataValue(HeaderField<F> field, String key) {
			valuesFromMetadata.put(field, key);
			return this;
		}
		
		@Override
		public Action build() {
			return new ActionModifyHeader(
					ImmutableMap.copyOf(exactValues),
					ImmutableMap.copyOf(valuesFromGlobals),
					ImmutableMap.copyOf(valuesFromMetadata)
				);
		}
		
	}
}
