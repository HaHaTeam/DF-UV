package com.rm.handlers;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class DicomImage {
	public String getDicomImage(File file){
		BufferedImage myImage=null;
        Iterator<ImageReader> iterator =ImageIO.getImageReadersByFormatName("DICOM");
        ImageReader imageReader = (ImageReader) iterator.next();
        DicomImageReadParam dicomImageReadParam = (DicomImageReadParam) imageReader.getDefaultReadParam();
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            imageReader.setInput(iis,false);
            myImage = imageReader.read(0, dicomImageReadParam);
            iis.close();
            if(myImage == null){
                System.out.println("Could not read image!!");
            }
            System.out.println("Height:" + myImage.getHeight());
            System.out.println("Width:" + myImage.getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        File desFile = new File("/WEB-INF/images/test.jpg");
        try {
			desFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        try {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(desFile));
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
            encoder.encode(myImage);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Completed");
        String imageSrc = "/WEB-INF/images/test.jpg";
        return imageSrc;
	}
}
