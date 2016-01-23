package com.rm.entity;


public class  DicomData{
	String PatientName;
	String PatientSex;
	String PatientAge;
	String WindowCenter;
	String WindowWidth;
	String StudyDate;
	
	
	public String getPatientName() {
		return PatientName;
	}
	public void setPatientName(String patientName) {
		PatientName = patientName;
	}
	public String getPatientSex() {
		return PatientSex;
	}
	public void setPatientSex(String patientSex) {
		PatientSex = patientSex;
	}
	public String getPatientAge() {
		return PatientAge;
	}
	public void setPatientAge(String patientAge) {
		PatientAge = patientAge;
	}
	public String getWindowCenter() {
		return WindowCenter;
	}
	public void setWindowCenter(String windowCenter) {
		WindowCenter = windowCenter;
	}
	public String getWindowWidth() {
		return WindowWidth;
	}
	public void setWindowWidth(String windowWidth) {
		WindowWidth = windowWidth;
	}
	public String getStudyDate() {
		return StudyDate;
	}
	public void setStudyDate(String studyDate) {
		StudyDate = studyDate;
	}
	@Override
	public String toString() {
		return "DicomData [PatientName=" + PatientName + ", PatientSex=" + PatientSex + ", PatientAge=" + PatientAge
				+ ", WindowCenter=" + WindowCenter + ", WindowWidth=" + WindowWidth + ", StudyDate=" + StudyDate + "]";
	}
	
	

}
