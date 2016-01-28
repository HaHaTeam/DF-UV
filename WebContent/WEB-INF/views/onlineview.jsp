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
	display: table-cell;
	text-align: center;
	vertical-align: middle;
	height: 500px;
	width: 600px;
	float: left;
}

#right {
	padding: 1em;
}

#up, #footer, #down {
	text-align: center;
}
</style>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1 align="center">在线浏览</h1>
		</div>
		<div id="left">
			<img alt="jpg图像" src="getImage">
		</div>

		<div id="right">
			<form action="change" method="post">
				<div id="up">
					<h3>图像调节</h3>
					窗宽:&nbsp;&nbsp;<input type="text"
						value="${dicomData.getWindowWidth()}" name="windowWidth"><br>
					<br> 窗位:&nbsp;&nbsp;<input type="text"
						value="${dicomData.getWindowCenter()}" name="windowCenter"><br>
					<br> <input type="submit" value="提交">
				</div>

			</form>
			<div id="down">
				<h3 align="center">病人信息</h3>
				姓名:${dicomData.getPatientName() }<br>
				年龄:${dicomData.getPatientAge() }<br>
				性别:${dicomData.getPatientSex() }<br>
			</div>
			<div id="footer">
				<h3>就诊日期</h3>
				${dicomData.getStudyDate() }
			</div>
		</div>
	</div>
</body>
</html>