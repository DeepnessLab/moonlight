package org.openboxprotocol.protocol.instructions;

import java.util.List;

import org.openboxprotocol.protocol.actions.Action;

import com.google.common.collect.ImmutableList;

public class InstructionExecuteUntilFailurePostFailure extends InstructionExecuteUntilFailure {
	
	private InstructionExecuteUntilFailurePostFailure(List<Action> actions) {
		super(actions);
	}
	
	public static class Builder extends InstructionExecuteAll.Builder {

		@Override
		public Instruction build() {
			return new InstructionExecuteUntilFailurePostFailure(ImmutableList.copyOf(actions));
		}
		
	}
}
