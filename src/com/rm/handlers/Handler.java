package com.rm.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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
	public String UploadFile(@RequestParam("file") MultipartFile file, Map<String, Object> fileinf) {
		File tmp = new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "tmp.dcm");

		DicomData dicomData = new DicomData();
		Attributes attributes;
		try {

			// 先存为临时文件
			System.out.println(tmp.getCanonicalPath());

			FileOutputStream fileOutputStream = new FileOutputStream(tmp);
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
			getAttributes = new GetAttributes(tmp);
			attributes = getAttributes.getDatasetAttributes();
			dicomData.setPatientName(
					attributes.getString(Tag.PatientName) == null ? "无" : attributes.getString(Tag.PatientName));
			dicomData.setPatientAge(
					attributes.getString(Tag.PatientAge) == null ? "无" : attributes.getString(Tag.PatientAge));
			dicomData.setPatientSex(
					attributes.getString(Tag.PatientSex) == null ? "无" : attributes.getString(Tag.PatientSex));
			dicomData.setStudyDate(attributes.getDate(Tag.StudyDate).toString() == null ? "无"
					: attributes.getDate(Tag.StudyDate).toString());
			dicomData.setWindowCenter(
					attributes.getString(Tag.WindowCenter) == null ? "无" : attributes.getString(Tag.WindowCenter));
			dicomData.setWindowWidth(
					attributes.getString(Tag.WindowWidth) == null ? "无" : attributes.getString(Tag.WindowWidth));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "redirect:error";
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:error";
		}

		fileinf.put("dicomData", dicomData);
		tmp.delete();
		System.out.println("临时文件删除成功！");
		return "redirect:onlineview";
	}
}
