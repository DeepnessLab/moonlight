package org.moonlightcontroller.samples;
import org.moonlightcontroller.alerts.Alert;
import org.moonlightcontroller.alerts.IAlertListener;
import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.logging.ILogListener;
import org.moonlightcontroller.logging.LogMessage;
import org.moonlightcontroller.processing.HeaderLookup;
import org.moonlightcontroller.processing.PayloadHandler;
import org.moonlightcontroller.processing.SessionAnalyzer;
import org.moonlightcontroller.processing.compression.GzipDecompressor;
import org.moonlightcontroller.processing.normalization.HttpNormalizer;
import org.openboxprotocol.protocol.parsing.RuleParser;

/**
 * 
 * @author Yotam Harchol (yotamhc@cs.huji.ac.il)
 *
 */
public class IntrusionPreventionSystem extends BoxApplication implements IAlertListener, ILogListener {

	public static final String APP_NAME = "SampleIPS";
	public static final String RULE_FILE = "sample_ips_rules.txt";
	
	public IntrusionPreventionSystem() {
		super(APP_NAME);
		super.addModule(new HeaderLookup() { });
		super.addModule(new SessionAnalyzer() {	});
		super.addModule(new NipsPayloadHandler());
		
		try {
			super.setRules(new RuleParser(RULE_FILE).read());
		} catch (Exception e) {
			System.err.println("Cannot load SampleIPS rules!");
			e.printStackTrace();
		}
	}
	// TODO: Add L7 protocol analysis to rules

	@Override
	public void handleLog(LogMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleAlert(Alert alert) {
		// TODO Auto-generated method stub
		
	}
	
	private static class NipsPayloadHandler extends PayloadHandler {
		public NipsPayloadHandler() {
			super.setDecompressor(new GzipDecompressor());
			super.setNormalizer(new HttpNormalizer());
		}
	}

}
