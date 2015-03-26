package org.openboxprotocol.protocol.actions;

import java.util.ArrayList;
import java.util.List;

import org.openboxprotocol.types.Port;

import com.google.common.collect.ImmutableList;

public class ActionOutput implements Action {
	
	private List<Port> ports;
	
	private ActionOutput(List<Port> ports) {
		this.ports = ports;
	}
	
	public List<Port> getPorts() {
		return ports;
	}
	
	public static class Builder implements Action.Builder {

		List<Port> ports;
		
		public Builder() {
			ports = new ArrayList<Port>();
		}
		
		public Builder(Port port) {
			this();
			ports.add(port);
		}
		
		public Builder addPort(Port port) {
			ports.add(port);
			return this;
		}
		
		public Builder addPorts(List<Port> ports) {
			this.ports.addAll(ports);
			return this;
		}
		
		@Override
		public Action build() {
			return new ActionOutput(ImmutableList.copyOf(ports));
		}
		
	}
}
