<!DOCTYPE html>
<html>
<head>
    <title>后台登录</title>
    <!-- for-mobile-apps -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <!-- //for-mobile-apps -->
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />

</head>
<body>
    <!-- main -->
    <div class="main">
        <h1>
            小程序后台管理系统
        </h1>
        <form action="http://localhost:8082/user/adminLogin">
            <input type="text" value="用户名" name="username" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = '用户名';}"
                   required="">
            <input type="password" value="" name="password" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = '';}"
                   required="">
            <input type="submit" value="登录">
        </form>
    </div>
    <!-- //main -->
</body>
</html>