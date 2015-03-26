package org.openboxprotocol.types;

import org.openboxprotocol.protocol.parsing.JSONParseException;

public class IPv6Address implements ValueType<IPv6Address> {

	private long ipLsb;
	private long ipMsb;
	
	public static final IPv6Address EMPTY_MASK = new IPv6Address(0, 0);

	private IPv6Address(long ipLsb, long ipMsb) {
		this.ipLsb = ipLsb;
		this.ipMsb = ipMsb;
	}
	
	@Override
	public IPv6Address applyMask(IPv6Address mask) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		return (int)((this.ipLsb * 19 + this.ipMsb * 23) % (Integer.MAX_VALUE - 1));
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof IPv6Address) && ((IPv6Address)other).ipLsb == this.ipLsb && ((IPv6Address)other).ipMsb == this.ipMsb;
	}
	
	public static IPv6Address fromJson(Object json) throws JSONParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
