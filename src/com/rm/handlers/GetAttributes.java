package com.rm.handlers;

import java.io.File;
import java.io.IOException;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;

public class GetAttributes {
	private File src = null;

	public GetAttributes(File src) {
		this.src = src;
	}

	public void setSrc(File src) {
		this.src = src;
	}

	public Attributes getFileMetaInformationAttributes() throws IOException {
		DicomInputStream dicomInputStream = new DicomInputStream(src);
		Attributes attributes = dicomInputStream.getFileMetaInformation();
		return attributes;
	}

	public Attributes getDatasetAttributes() throws IOException {
		DicomInputStream dicomInputStream = new DicomInputStream(src);
		Attributes attributes = dicomInputStream.readDataset(-1, Tag.PixelData);
		return attributes;
	}

}
