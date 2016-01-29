package com.rm.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.io.IOUtils;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rm.entity.DicomData;

@Controller
public class DICOMParser {
	private GetAttributes getAttributes = null;
	private File dcmTmp = new File(
			Thread.currentThread().getContextClassLoader().getResource("").getPath() + "tmp.dcm");
	private File jpgTmp = new File(
			Thread.currentThread().getContextClassLoader().getResource("").getPath() + "jpgTmp.jpg");

	private static DicomData dicomData = new DicomData();

	@RequestMapping("/upload")
	public String UploadFile(@RequestParam("file") MultipartFile file, Map<String, Object> fileinf) {

		Attributes attributes;
		try {
			// 先存为临时文件
			FileOutputStream fileOutputStream = new FileOutputStream(dcmTmp);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream());
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			int i = 0;
			byte[] b = new byte[20480];
			while ((i = bufferedInputStream.read(b)) != -1) {
				bufferedOutputStream.write(b, 0, i);
				bufferedOutputStream.flush();
			}
			bufferedInputStream.close();
			bufferedOutputStream.close();
			fileOutputStream.close();

			// 进行文件解析
			getAttributes = new GetAttributes(dcmTmp);
			attributes = getAttributes.getDatasetAttributes();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
			dicomData.setPatientName(
					attributes.getString(Tag.PatientName) == null ? "无" : attributes.getString(Tag.PatientName));
			dicomData.setPatientAge(
					attributes.getString(Tag.PatientAge) == null ? "无" : attributes.getString(Tag.PatientAge));
			dicomData.setPatientSex(
					attributes.getString(Tag.PatientSex) == null ? "无" : attributes.getString(Tag.PatientSex));
			dicomData.setStudyDate((sdf1.format(attributes.getDate(Tag.StudyDate)) + " "
					+ sdf2.format(attributes.getDate(Tag.StudyTime))) == null ? "无"
							: (sdf1.format(attributes.getDate(Tag.StudyDate)) + " "
									+ sdf2.format(attributes.getDate(Tag.StudyTime))));
			dicomData.setWindowCenter(
					attributes.getString(Tag.WindowCenter) == null ? "无" : attributes.getString(Tag.WindowCenter));
			dicomData.setWindowWidth(
					attributes.getString(Tag.WindowWidth) == null ? "无" : attributes.getString(Tag.WindowWidth));
			GetImageBuffer getImageBuffer = new GetImageBuffer(dcmTmp, jpgTmp);
			getImageBuffer.createImage(0,0);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		fileinf.put("dicomData", dicomData);
		// dcmTmp.delete();
		// System.out.println("临时文件删除成功！");
		return "onlineview";
	}

	@RequestMapping("/getImage")
	public void getImage(HttpServletResponse response) {
		response.setContentType("image/jpg");
		GetImageBuffer getImageBuffer = new GetImageBuffer(dcmTmp, jpgTmp);
		byte[] buf = getImageBuffer.getJpgBytes();
		InputStream in1 = new ByteArrayInputStream(buf);
		try {
			IOUtils.copy(in1, response.getOutputStream());
			response.getOutputStream().close();
			in1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@RequestMapping("/change")
	public String change(@RequestParam("windowWidth") float windowWidth,
			@RequestParam("windowCenter") float windowCenter, Map<String, Object> fileinf) {
		if(windowWidth<=0){
			return "error";
		}
		dicomData.setWindowCenter(String.valueOf((int) windowCenter));
		dicomData.setWindowWidth(String.valueOf((int) windowWidth));
		GetImageBuffer getImageBuffer = new GetImageBuffer(dcmTmp, jpgTmp);
		getImageBuffer.createImage(Float.valueOf(dicomData.getWindowWidth()),
				Float.valueOf(dicomData.getWindowCenter()));
		System.out.println(dicomData);
		fileinf.remove("dicomData");
		fileinf.put("dicomData", dicomData);
		return "onlineview";
	}

//	private static ResourceBundle rb = ResourceBundle.getBundle("org.dcm4che3.tool.dcm2jpg.messages");

	@RequestMapping("/change")
	public String changeImage(@RequestParam("windowWidth") float windowWidth,
			@RequestParam("windowCenter") float windowCenter,Map<String, Object> fileinf) {
		Iterator<ImageReader> iteratorReader =ImageIO.getImageReadersByFormatName("DICOM");
		ImageReader imageReader = (ImageReader) iteratorReader.next();
		DicomImageReadParam param =
				(DicomImageReadParam) imageReader.getDefaultReadParam();
		param.setWindowCenter(windowCenter);
		param.setWindowWidth(windowWidth);
		
		dicomData.setWindowCenter(String.valueOf((int)windowCenter));
		dicomData.setWindowWidth(String.valueOf((int)windowWidth));
		fileinf.put("dicomData", dicomData);
		return "onlineview";
		
		
	}
}
