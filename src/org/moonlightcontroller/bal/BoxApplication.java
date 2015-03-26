package org.moonlightcontroller.bal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.LogicalMessage;

import org.moonlightcontroller.processing.IProcessingModule;
import org.openboxprotocol.protocol.Priority;
import org.openboxprotocol.protocol.Rule;

public abstract class BoxApplication {
	
	protected String name;
	
	private int priority;
	private List<IProcessingModule> modules;
	private List<Rule> rules;
	
	public BoxApplication(String name) {
		this(name, Priority.PRIORITY_NORMAL);
	}
	
	public BoxApplication(String name, int priority) {
		this.name = name;
		this.priority = priority;
		this.modules = new ArrayList<IProcessingModule>();
	}
	
	public String getName() {
		return name;
	}
	
	public int getPriority() {
		return priority;
	}
	
	protected void addModule(IProcessingModule module) {
		this.modules.add(module);
	}
	
	protected void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	public List<Rule> getRules() {
		return this.rules;
	}
	
	protected void log(LogicalMessage msg) {
		
	}
}
