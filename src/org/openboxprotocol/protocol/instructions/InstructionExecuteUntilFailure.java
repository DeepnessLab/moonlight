package org.openboxprotocol.protocol.instructions;

import java.util.List;

import org.openboxprotocol.protocol.actions.Action;

import com.google.common.collect.ImmutableList;

public class InstructionExecuteUntilFailure extends InstructionExecuteAll {

	protected InstructionExecuteUntilFailure(List<Action> actions) {
		super(actions);
	}
	
	public static class Builder extends InstructionExecuteAll.Builder {

		@Override
		public Instruction build() {
			return new InstructionExecuteUntilFailure(ImmutableList.copyOf(actions));
		}
		
	}
}
