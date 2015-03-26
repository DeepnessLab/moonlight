package org.moonlightcontroller.processing;

import org.moonlightcontroller.processing.compression.IDecompressor;
import org.moonlightcontroller.processing.normalization.INormalizer;

public abstract class PayloadHandler implements IProcessingModule {

	public void setDecompressor(IDecompressor decompressor) {
		
	}

	public void setNormalizer(INormalizer normalizer) {
		
	}

}
