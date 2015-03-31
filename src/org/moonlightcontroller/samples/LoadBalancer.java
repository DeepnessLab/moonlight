package org.moonlightcontroller.samples;
import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.processing.HeaderLookup;
import org.moonlightcontroller.processing.SessionAnalyzer;
import org.openboxprotocol.protocol.parsing.RuleParser;
import org.openboxprotocol.types.Port;

/**
 * Sample Layer 7 load balancer implementation over Moonlight
 * 
 * @author Yotam Harchol (yotamhc@cs.huji.ac.il)
 *
 */
public class LoadBalancer extends BoxApplication {

	public static final String APP_NAME = "SampleLB";
	public static final String RULE_FILE = "sample_lb_rules.txt";
	
	public LoadBalancer(Port inPort, Port outPort) {
		super(APP_NAME);
		super.addModule(new HeaderLookup() { });
		super.addModule(new SessionAnalyzer() {	});
		
		try {
			super.setRules(new RuleParser(RULE_FILE).read());
		} catch (Exception e) {
			System.err.println("Cannot load SampleLB rules!");
			e.printStackTrace();
		}
	}
	
}
