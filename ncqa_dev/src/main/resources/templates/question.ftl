<html>

<head>
	<h1>
	问题列表
	</h1>
</head>
	<body>
		<table border="1" cellspacing=0>
  <tr>
    <th>问题编号</th>
    <th>问题内容</th>
    <th>选择类型</th>
    <th>答案A</th>
    <th>答案B</th>
    <th>答案C</th>
    <th>答案D</>
  </tr>
  <#list result as base>
  <tr>
    <td>${base.id}</td>
    <td>${base.name}</td>
    <td>${base.description}</td>
    <td>${base.start_time}</td>
    <td>${base.end_time}</td>
    <td>
   		<a href="http://www.baidu.com">查看</a>
   		<a href="http://www.baidu.com">上传</a>
    	<a href="http://www.baidu.com">修改</a>
    	<a href="http://www.baidu.com">删除</a>
    	<a href="http://www.baidu.com">导出</a>
    </td>
  </tr>
  </#list>
	</body>
</html>