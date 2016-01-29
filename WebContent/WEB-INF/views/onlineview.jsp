<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线浏览</title>
<style type="text/css">
#header {
	background-color: gray;
	color: white;
	padding: 1em;
	margin: 0;
}

#con {
	border: 1 solid;
	margin: 0;
	width: 100%;
}

#left {
	padding: 1em;
	text-align: center;
	height: 600px;
	width: 750px;
	float: left;
}

#right {
	padding: 1em;
	font-size: larger;
}

#up, #down {
	text-align: center;
	margin-bottom: 120px;
}

table {
	margin: auto;
}

#footer {
	text-align: center;
	font-size: larger;
	margin-top: 20px;
}

#back {
	text-align: right;
}

span {
	font-weight: bold;
}
</style>
</head>
<body>
	<div id="header">
		<h1 align="center">在线浏览</h1>
	</div>

	<div id="con">
		<div id="left">
			<img alt="jpg图像" src="getImage" width="100%" height="100%">
		</div>

		<div id="right">
			<form action="change" method="post">
				<div id="up">
					<h2>图像调节</h2>
					窗宽:&nbsp;&nbsp;<input type="text"
						value="${dicomData.getWindowWidth()}" name="windowWidth"><br>
					<br> 窗位:&nbsp;&nbsp;<input type="text"
						value="${dicomData.getWindowCenter()}" name="windowCenter"><br>
					<br> <input type="submit" value="提交">

				</div>

			</form>
			
<div id="down">
				<h2 align="center">病人信息</h2>
				<table>
					<tbody>
						<tr>
							<td>姓名:</td>
							<td>${dicomData.getPatientName()}</td>
						</tr>
						<tr>
							<td>年龄:</td>
							<td>${dicomData.getPatientAge() }</td>
						</tr>
						<tr>
							<td>性别:</td>
							<td>${dicomData.getPatientSex() }</td>
						</tr>
					</tbody>
				</table>
			</div>

		</div>
	</div>

	<div id="footer">
		<span>就诊日期:</span>${dicomData.getStudyDate() }
	</div>

	<div id="back">
		<a href="index">返回首页</a>
	</div>
</body>
</html>