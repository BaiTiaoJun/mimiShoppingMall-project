<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
		<title></title>
		<script type="text/javascript">
			$(function () {
				$("input[name=name]").val("").focus();

				$('#btn').click(function () {
					login();
				})

				$(window).keydown(function (event) {
					if (event.keyCode === 13) {
						login();
					}
				})
			})

			function login() {
				let name = $.trim($("input[name=name]").val());
				let pwd = $.trim($("input[name=pwd]").val());

				if (name === "" || pwd === "") {
					$('#msg').html('账号或者密码不能为空！');
					return false;
				}

				$('#loginForm').submit();
			}
		</script>
	</head>

	<body>
		<div id="login">
			<div id="top">
				<img src="${pageContext.request.contextPath}/images/cloud.jpg" /><span>LOGIN</span>
			</div>
			<div id="bottom">
<%--				action="${pageContext.request.contextPath}/admin/login.action"--%>
<%--	method="post"--%>
				<form action="${pageContext.request.contextPath}/admin/login.action" method="post" id="loginForm">
					<table border="0px" id="table">
						<tr>
							<td class="td1">用户名：</td>
							<td><input type="text" placeholder="Username" class="td2" name="name"></td>
						</tr>
						<tr>
							<td></td>
							<td><span id="nameerr"></span></td>
						</tr>
						<tr>
							<td class="td1">密码：</td>
							<td><input type="password" placeholder="Password" class="td2" name="pwd"></td>
						</tr>
						<tr>
							<td></td>
							<td><span id="pwderr"></span></td>
						</tr>
						<tr>
							<td></td>
							<td><button id="btn" class="td3" type="button">登录</button>
								<a href="${pageContext.request.contextPath}/regist.jsp"><input type="button" value="注册" class="td3	"></a>
							</td>
						</tr>
					</table>
				</form>

			<span id="msg">${param.msg}</span>
			</div>
		</div>
	</body>

</html>