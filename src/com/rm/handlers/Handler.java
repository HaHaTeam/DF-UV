package com.rm.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.rm.entity.DicomData;

@Controller
public class Handler {
	private GetAttributes getAttributes = null;

	@RequestMapping("/upload")
	public String UploadFile(@RequestParam("file") MultipartFile file) {
		File tmp = new File("tmp.dcm");
		try {
			tmp.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(tmp);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream());
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			int i = 0;
			byte[] b = new byte[20480];
			while ((i = bufferedInputStream.read(b)) != -1) {
				bufferedOutputStream.write(b, 0, i);
				System.out.println(i);
				bufferedOutputStream.flush();
			}
			bufferedInputStream.close();
			bufferedOutputStream.close();
			fileOutputStream.close();
			getAttributes = new GetAttributes(tmp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "uploaderror";
		} catch (IOException e) {
			e.printStackTrace();
			return "uploaderror";
		}
		return "success";
	}

	@RequestMapping("/onlineview")
	public String onlineview(Map<String, Object> fileinf) {
		DicomData dicomData = new DicomData();
		Attributes attributes;
		try {
			attributes = getAttributes.getDatasetAttributes();
			dicomData.setPatientName(attributes.getString(Tag.PatientName)==null?"无":attributes.getString(Tag.PatientName));
			dicomData.setPatientAge(attributes.getString(Tag.PatientAge)==null?"无":attributes.getString(Tag.PatientAge));
			dicomData.setPatientSex(attributes.getString(Tag.PatientSex)==null?"无":attributes.getString(Tag.PatientSex));
			dicomData.setStudyDate(attributes.getDate(Tag.StudyDate).toString()==null?"无":attributes.getDate(Tag.StudyDate).toString());
			dicomData.setWindowCenter(attributes.getString(Tag.WindowCenter)==null?"无":attributes.getString(Tag.WindowCenter));
			dicomData.setWindowWidth(attributes.getString(Tag.WindowWidth)==null?"无":attributes.getString(Tag.WindowWidth));
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		fileinf.put("dicomData", dicomData);
		System.out.println(dicomData);
		return "onlineview";
	}

}
