<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线浏览</title>
<style type="text/css">
#container {
	border: 1px solid white;
	width: 100%;
}

#header {
	background-color: gray;
	color: white;
	padding: 0.5em;
}

#left {
	padding: 1em;
	width: 75%;
}

#right {
	float: right;
	padding: 1em;
}


#up, #down, #footer {
	padding: 1em;
}
</style>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1 align="center">在线浏览</h1>
		</div>
		<div id="right">
			<div id="up">窗宽:${dicomData.getWindowWidth() } 窗位:${dicomData.getWindowCenter() }</div>
			<div id="down">
				<p>病人信息</p>
				姓名:${dicomData.getPatientName() }<br>
				年龄:${dicomData.getPatientAge() }<br>
				性别:${dicomData.getPatientSex() }<br>
			</div>
			<div id="footer">
				<p>就诊日期:</p>${dicomData.getStudyDate() }
			</div>
		</div>
	</div>
</body>
</html>