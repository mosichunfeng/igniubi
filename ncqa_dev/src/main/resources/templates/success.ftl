<html>
<head>
SB
<head>
<body>	
<table border="1" cellspacing=0>
  <tr>
    <th>题库编号</th>
    <th>题库名</th>
    <th>题库描述</th>
    <th>开始时间</th>
    <th>结束时间</th>
    <th>可操作列表</>
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
</table>
</body>
</html>