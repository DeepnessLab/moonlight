package org.openboxprotocol.protocol.instructions;

import java.util.ArrayList;
import java.util.List;

import org.openboxprotocol.protocol.actions.Action;

import com.google.common.collect.ImmutableList;

public class InstructionExecuteAll implements Instruction {
	private List<Action> actions;
	
	protected InstructionExecuteAll(List<Action> actions) {
		this.actions = actions;
	}
	
	public List<Action> getActions() {
		return actions;
	}
	
	public static class Builder implements Instruction.Builder {

		protected List<Action> actions;
		
		public Builder() {
			actions = new ArrayList<Action>();
		}

		public Builder addAction(Action action) {
			actions.add(action);
			return this;
		}
		
		public Builder addActions(List<Action> actions) {
			actions.addAll(actions);
			return this;
		}
		
		@Override
		public Instruction build() {
			return new InstructionExecuteAll(ImmutableList.copyOf(actions));
		}
		
	}
}
