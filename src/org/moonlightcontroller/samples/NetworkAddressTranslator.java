package org.moonlightcontroller.samples;
import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.processing.HeaderLookup;
import org.moonlightcontroller.processing.SessionAnalyzer;
import org.openboxprotocol.protocol.Globals;
import org.openboxprotocol.protocol.HeaderField;
import org.openboxprotocol.protocol.HeaderMatch;
import org.openboxprotocol.protocol.OpenBoxHeaderMatch;
import org.openboxprotocol.protocol.OpenBoxRule;
import org.openboxprotocol.protocol.Priority;
import org.openboxprotocol.protocol.Rule;
import org.openboxprotocol.protocol.actions.Action;
import org.openboxprotocol.protocol.actions.ActionGenerateRandomNumber;
import org.openboxprotocol.protocol.actions.ActionMapRetrieve;
import org.openboxprotocol.protocol.actions.ActionMapStore;
import org.openboxprotocol.protocol.actions.ActionModifyHeader;
import org.openboxprotocol.protocol.actions.ActionOutput;
import org.openboxprotocol.protocol.actions.ActionSpawnRule;
import org.openboxprotocol.protocol.instructions.Instruction;
import org.openboxprotocol.protocol.instructions.InstructionExecuteAll;
import org.openboxprotocol.types.Cookie;
import org.openboxprotocol.types.EthType;
import org.openboxprotocol.types.IpProto;
import org.openboxprotocol.types.Port;
import org.openboxprotocol.types.SessionID;
import org.openboxprotocol.types.TransportPort;
import com.google.common.collect.ImmutableList;

/**
 * Sample NAT implementation over Moonlight
 * 
 * @author Yotam Harchol (yotamhc@cs.huji.ac.il)
 *
 */
public class NetworkAddressTranslator extends BoxApplication {

	public static final String APP_NAME = "SampleNAT";
	
	public static final String METADATA_KEY_SRC_IP = "src_ip";
	public static final String METADATA_KEY_SRC_TCP_PORT = "tcp_src_port";
	public static final String METADATA_KEY_SRC_UDP_PORT = "udp_src_port";
	public static final String METADATA_KEY_NAT_PORT = "nat_port";
	public static final String MAP_KEY_NAT_PORT_TCP = "nat_port_tcp";
	public static final String MAP_KEY_NAT_PORT_UDP = "nat_port_udp";
	public static final String MAP_KEY_NAT_HOST_PORT_TCP = "nat_host_port_tcp";
	public static final String MAP_KEY_NAT_HOST_PORT_UDP = "nat_host_port_udp";
	public static final String MAP_KEY_NAT_HOST = "nat_host";
	public static final String METADATA_KEY_NAT_HOST = "nat_host";
	public static final String METADATA_KEY_NAT_HOST_PORT = "nat_host_port";
	
	public NetworkAddressTranslator(Port inPort, Port outPort) {
		super(APP_NAME);
		super.addModule(new NatHeaderLookup());
		super.addModule(new SessionAnalyzer() {	});
		super.setRules(ImmutableList.of(
				createNatParentRules(inPort, outPort, IpProto.TCP),
				createNatParentRules(inPort, outPort, IpProto.UDP)
		));
	}

	/*
	 * Sample NAT spawning rule:
	 * 
	 * Given: input port, output port
	 * From packet: src IP, src Port
	 * Create rule with match: input port, src IP, IP Proto, src Port
	 * 		With instruction: InstructionExecuteAll
	 * 		Actions: ActionMapRetrieve (Session ID, nat_port)
	 * 				 ActionModifyHeader (src IP -> Globals.OB_OSP_CURRENT_PORT_IP,
	 * 									 src Port -> nat_port)
	 * 				 ActionOutput (output port)
	 * Create rule with match: 
	 * 			input port=original output port, 
	 * 			dst IP = Globals.OB_OSP_CURRENT_PORT_IP, 
	 * 			IP Proto, 
	 * 			dst Port = nat_port
	 * 		With instruction: InstructionExecuteAll
	 * 		Actions: ActionMapRetrieve (Session ID, nat_host, nat_host_port)
	 * 				 ActionModifyHeader (dst IP -> nat_host,
	 * 									 dst Port -> nat_host_port)
	 * 				 ActionOutput (original input port)
	 * 
	 */
	
	private Rule createNatParentRules(Port inPort, Port outPort, IpProto ipProto) {
		// Metadata from packet: src_ip, src_port
		Cookie cookie = Cookie.of((int)(Math.random() * Long.MAX_VALUE));
		
		HeaderField<TransportPort> srcPortField;
		HeaderField<TransportPort> dstPortField;
		String mapKeyNatPort;
		String mapKeyHostPort;
		String metadataKeySrcPort;
		if (ipProto.equals(IpProto.TCP)) {
			srcPortField = HeaderField.TCP_SRC;
			dstPortField = HeaderField.TCP_DST;
			mapKeyNatPort = MAP_KEY_NAT_PORT_TCP;
			mapKeyHostPort = MAP_KEY_NAT_HOST_PORT_TCP;
			metadataKeySrcPort = METADATA_KEY_SRC_TCP_PORT;
		} else if (ipProto.equals(IpProto.UDP)) {
			srcPortField = HeaderField.UDP_SRC;
			dstPortField = HeaderField.UDP_DST;
			mapKeyNatPort = MAP_KEY_NAT_PORT_UDP;
			mapKeyHostPort = MAP_KEY_NAT_HOST_PORT_UDP;
			metadataKeySrcPort = METADATA_KEY_SRC_UDP_PORT;
		} else {
			throw new IllegalArgumentException("Can only create TCP or UDP rules");
		}
		
		Action random = new ActionGenerateRandomNumber.Builder(METADATA_KEY_NAT_PORT, 10000, 65535).build();
		
		Action storeNatPort = new ActionMapStore.Builder(SessionID.CURRENT_SESSION, 
				mapKeyNatPort, METADATA_KEY_NAT_PORT).build();
		
		Action storeNatHost = new ActionMapStore.Builder(SessionID.CURRENT_SESSION, 
				MAP_KEY_NAT_HOST, METADATA_KEY_SRC_IP).build();
		
		Action storeNatHostPort = new ActionMapStore.Builder(SessionID.CURRENT_SESSION, 
				mapKeyHostPort, metadataKeySrcPort).build();
		
		Action spawnOutbound = new ActionSpawnRule.Builder(cookie, Priority.PRIORITY_HIGH)
			.putMatchFieldFromValue(HeaderField.IN_PORT, inPort)
			.putMatchFieldFromValue(HeaderField.ETH_TYPE, EthType.IPv4)
			.putMatchFieldFromValue(HeaderField.IP_PROTO, ipProto)
			.putMatchFieldFromMetadata(HeaderField.IPV4_SRC, METADATA_KEY_SRC_IP)
			.putMatchFieldFromMetadata(srcPortField, metadataKeySrcPort)
			.addInstruction(
				new InstructionExecuteAll.Builder()
					.addAction(new ActionMapRetrieve.Builder(
								SessionID.CURRENT_SESSION,
								mapKeyNatPort,
								METADATA_KEY_NAT_PORT,
								false)
								.build())
					.addAction(new ActionModifyHeader.Builder()
								.setGlobalValue(HeaderField.IPV4_SRC, Globals.OB_OSP_CURRENT_PORT_IP)
								.setMetadataValue(srcPortField, METADATA_KEY_NAT_PORT)
								.build())
					.addAction(new ActionOutput.Builder(outPort).build())
					.build())
			.build();
			
		Action spawnInbound = new ActionSpawnRule.Builder(cookie, Priority.PRIORITY_HIGH)
			.putMatchFieldFromValue(HeaderField.IN_PORT, outPort)
			.putMatchFieldFromValue(HeaderField.ETH_TYPE, EthType.IPv4)
			.putMatchFieldFromValue(HeaderField.IP_PROTO, ipProto)
			.putMatchFieldFromMetadata(HeaderField.IPV4_SRC, METADATA_KEY_SRC_IP)
			.putMatchFieldFromMetadata(srcPortField, METADATA_KEY_NAT_PORT) // Metadata field is created by ActionGenerateRandom
			.addInstruction(
				new InstructionExecuteAll.Builder()
					.addAction(new ActionMapRetrieve.Builder(
								SessionID.CURRENT_SESSION,
								MAP_KEY_NAT_HOST,
								METADATA_KEY_NAT_HOST,
								false)
								.build())
					.addAction(new ActionMapRetrieve.Builder(
								SessionID.CURRENT_SESSION,
								mapKeyHostPort,
								METADATA_KEY_NAT_HOST_PORT,
								false)
								.build())
					.addAction(new ActionModifyHeader.Builder()
								.setMetadataValue(HeaderField.IPV4_DST, METADATA_KEY_NAT_HOST)
								.setMetadataValue(dstPortField, METADATA_KEY_NAT_HOST_PORT)
								.build())
					.addAction(new ActionOutput.Builder(inPort).build())
					.build())
			.build();
		
		Instruction instruction = new InstructionExecuteAll.Builder()
			.addAction(random)
			.addAction(storeNatPort)
			.addAction(storeNatHost)
			.addAction(storeNatHostPort)
			.addAction(spawnOutbound)
			.addAction(spawnInbound)
			.addAction(new ActionModifyHeader.Builder()
				.setGlobalValue(HeaderField.IPV4_SRC, Globals.OB_OSP_CURRENT_PORT_IP)
				.setMetadataValue(srcPortField, metadataKeySrcPort)
				.build())
			.addAction(new ActionOutput.Builder(outPort).build())
			.build();
		
		HeaderMatch match = new OpenBoxHeaderMatch.Builder()
			.setExact(HeaderField.IN_PORT, inPort)
			.setExact(HeaderField.ETH_TYPE, EthType.IPv4)
			.setExact(HeaderField.IP_PROTO, ipProto)
			.build();
		
		return new OpenBoxRule.Builder()
			.setCookie(cookie)
			.setPriority(Priority.PRIORITY_NORMAL)
			.setHeaderMatch(match)
			.addInstruction(instruction)
			.build();
	}

	static class NatHeaderLookup extends HeaderLookup {
		public NatHeaderLookup() {
			super.putField(HeaderField.IPV4_SRC, METADATA_KEY_SRC_IP);
			super.putField(HeaderField.TCP_SRC, METADATA_KEY_SRC_TCP_PORT);
			super.putField(HeaderField.UDP_SRC, METADATA_KEY_SRC_UDP_PORT);
		}
	}
}
