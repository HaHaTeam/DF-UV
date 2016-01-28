package com.rm.handlers;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
public class GetImageBuffer {
	private File dcmTmp = null;
	private File jpgTmp = null;
	
	public GetImageBuffer(File dcmTmp, File jpgTmp) {
		this.dcmTmp = dcmTmp;
		this.jpgTmp = jpgTmp;
	}
	
	public byte[] getJpgBytes(){
		BufferedImage myImage=null;
		Iterator<ImageReader> iterator =ImageIO.getImageReadersByFormatName("DICOM");
		ImageReader imageReader = (ImageReader) iterator.next();
		DicomImageReadParam dicomImageReadParam = (DicomImageReadParam) imageReader.getDefaultReadParam();
		byte[] data = null;
		try {
            ImageInputStream iis = ImageIO.createImageInputStream(dcmTmp);
            imageReader.setInput(iis,false);
            myImage = imageReader.read(0, dicomImageReadParam);
            iis.close();
            
            OutputStream jpgoutputStream = new BufferedOutputStream(new FileOutputStream(jpgTmp));
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(jpgoutputStream);
            encoder.encode(myImage);
            jpgoutputStream.close();
            
           
            FileImageInputStream jpginput = null;
            jpginput = new FileImageInputStream(jpgTmp);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = jpginput.read(buf)) != -1) {
            output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            jpginput.close();
		}catch (IOException e) {
            e.printStackTrace();
        }
		
		return data;
	}
}
