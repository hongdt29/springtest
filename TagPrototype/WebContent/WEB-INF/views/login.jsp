<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>Login Page</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body onload='document.loginForm.username.focus();'>
	
<div class="row">
    <div class="col-sm-3"></div>
    <div class="col-sm-6">
        <div class="panel panel-primary">
            <div class="panel-heading">ログイン - サニーポータル</div>
            <div class="panel-body">
            
                <form:form id="loginForm" modelAttribute="login" action="login" method="post">
                    <h5 class="text-danger text-center">${abc}</h5>
                    <div class="form-group">
                        <form:label path="username" class="control-label col-sm-4">ログインID</form:label>
                        <div class="col-sm-8">
                        	<form:input class="form-control" path="username" name="username" id="username" />
                        </div>
                    </div> 
                    <br><br/>
                    <div class="form-group">
                        <form:label path="password" class="control-label col-sm-4">パスワード</form:label>
                        <div class="col-sm-8">
                        	<form:password class="form-control" path="password" name="password" id="password" />
                        </div>
                    </div> 
                   <br><br/>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-8">
                            <form:button id="login" name="login" class="btn btn-primary col-sm-10">ログイン</form:button>
                        </div>
                    </div>
                    <br><br/>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-8">
                            <a href="#" class="text-info">※パスワードを忘れた場合</a>
                        </div>
                    </div>
                </form:form>
                
                
            </div>
        </div>
    </div>
    <div class="col-sm-3"></div>
</div>
</body>
</html>