package org.openboxprotocol.protocol;

import java.util.ArrayList;
import java.util.List;

import org.openboxprotocol.protocol.instructions.Instruction;
import org.openboxprotocol.types.Cookie;

import com.google.common.collect.ImmutableList;

public class OpenBoxRule implements Rule {

	private HeaderMatch headerMatch;
	private PayloadMatch payloadMatch;
	private List<Instruction> instructions;
	private Cookie cookie;
	private int priority;
	
	private OpenBoxRule(HeaderMatch headerMatch, PayloadMatch payloadMatch, List<Instruction> instructions, Cookie cookie, int priority) {
		this.headerMatch = headerMatch;
		this.payloadMatch = payloadMatch;
		this.instructions = instructions;
		if (cookie != null)
			this.cookie = cookie;
		else
			this.cookie = Cookie.EMPTY_COOKIE;
		this.priority = priority;
	}
	
	@Override
	public Cookie getCookie() {
		return cookie;
	}
	
	@Override
	public int getPriority() {
		return priority;
	}
	
	@Override
	public HeaderMatch getHeaderMatch() {
		return headerMatch;
	}
	
	@Override
	public PayloadMatch getPayloadMatch() {
		return payloadMatch;
	}

	@Override
	public List<Instruction> getInstructions() {
		return instructions;
	}
	
	public static class Builder implements Rule.Builder {

		private HeaderMatch headerMatch;
		private PayloadMatch payloadMatch;
		private List<Instruction> instructions;
		private Cookie cookie;
		private int priority;
		
		public Builder() {
			this.instructions = new ArrayList<Instruction>();
		}

		@Override
		public org.openboxprotocol.protocol.Rule.Builder setCookie(Cookie cookie) {
			this.cookie = cookie;
			return this;
		}

		@Override
		public org.openboxprotocol.protocol.Rule.Builder setPriority(int priority) {
			this.priority = priority;
			return this;
		}
		
		@Override
		public org.openboxprotocol.protocol.OpenBoxRule.Builder setHeaderMatch(HeaderMatch headerMatch) {
			this.headerMatch = headerMatch;
			return this;
		}
		
		@Override
		public org.openboxprotocol.protocol.OpenBoxRule.Builder setPayloadMatch(PayloadMatch payloadMatch) {
			this.payloadMatch = payloadMatch;
			return this;
		}

		@Override
		public org.openboxprotocol.protocol.OpenBoxRule.Builder addInstruction(Instruction instruction) {
			this.instructions.add(instruction);
			return this;
		}
		
		@Override
		public org.openboxprotocol.protocol.OpenBoxRule.Builder addInstructions(List<Instruction> instructions) {
			this.instructions.addAll(instructions);
			return this;
		}

		@Override
		public Rule build() throws IllegalStateException {
			if (headerMatch == null)
				throw new IllegalStateException("Must specify a header match for rule");
			return new OpenBoxRule(headerMatch, payloadMatch, ImmutableList.copyOf(instructions), cookie, priority);
		}
		
	}

}
