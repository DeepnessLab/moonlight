package org.openboxprotocol.protocol.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openboxprotocol.protocol.HeaderField;
import org.openboxprotocol.protocol.instructions.Instruction;
import org.openboxprotocol.types.Cookie;
import org.openboxprotocol.types.ValueType;

public class ActionSpawnRule implements Action {
	
	private Cookie cookie;
	private int priority;
	private List<Instruction> instructions;
	private Map<HeaderField<?>, ValueType<?>> matchFieldsFromValue;
	private Map<HeaderField<?>, String> matchFieldsFromGlobals;
	private Map<HeaderField<?>, String> matchFieldsFromMetadata;
	
	private ActionSpawnRule(Cookie cookie, int priority,
			List<Instruction> instructions,
			Map<HeaderField<?>, ValueType<?>> matchFieldsFromValue,
			Map<HeaderField<?>, String> matchFieldsFromGlobals,
			Map<HeaderField<?>, String> matchFieldsFromMetadata) {
		this.cookie = cookie;
		this.priority = priority;
		this.instructions = instructions;
		this.matchFieldsFromValue = matchFieldsFromValue;
		this.matchFieldsFromGlobals = matchFieldsFromGlobals;
		this.matchFieldsFromMetadata = matchFieldsFromMetadata;
	}
	
	public Cookie getCookie() {
		return cookie;
	}

	public int getPriority() {
		return priority;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public Map<HeaderField<?>, ValueType<?>> getMatchFieldsFromValue() {
		return matchFieldsFromValue;
	}

	public Map<HeaderField<?>, String> getMatchFieldsFromGlobals() {
		return matchFieldsFromGlobals;
	}

	public Map<HeaderField<?>, String> getMatchFieldsFromMetadata() {
		return matchFieldsFromMetadata;
	}

	public static class Builder implements Action.Builder {

		private Cookie cookie;
		private int priority;
		private List<Instruction> instructions;
		private Map<HeaderField<?>, ValueType<?>> matchFieldsFromValue;
		private Map<HeaderField<?>, String> matchFieldsFromGlobals;
		private Map<HeaderField<?>, String> matchFieldsFromMetadata;
		
		public Builder(Cookie cookie, int priority) {
			this.cookie = cookie;
			this.priority = priority;
			this.instructions = new ArrayList<Instruction>();
			this.matchFieldsFromValue = new HashMap<>();
			this.matchFieldsFromGlobals = new HashMap<>();
			this.matchFieldsFromMetadata = new HashMap<>();
		}
		
		public Builder addInstruction(Instruction instruction) {
			instructions.add(instruction);
			return this;
		}
		
		public <F extends ValueType<F>> Builder putMatchFieldFromValue(HeaderField<F> field, F value) {
			this.matchFieldsFromValue.put(field, value);
			return this;
		}
		
		public <F extends ValueType<F>> Builder putMatchFieldFromGlobals(HeaderField<F> field, String globalname) {
			this.matchFieldsFromGlobals.put(field, globalname);
			return this;
		}
		
		public <F extends ValueType<F>> Builder putMatchFieldFromMetadata(HeaderField<F> field, String key) {
			this.matchFieldsFromMetadata.put(field, key);
			return this;
		}
		
		@Override
		public Action build() {
			return new ActionSpawnRule(cookie, priority, instructions, 
					matchFieldsFromValue,
					matchFieldsFromGlobals,
					matchFieldsFromMetadata);
		}
		
	}
}
