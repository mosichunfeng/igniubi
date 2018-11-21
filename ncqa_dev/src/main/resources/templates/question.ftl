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
    <th>选项A</th>
    <th>选项B</th>
    <th>选项C</th>
    <th>选项D</th>
  </tr>
  <#list result as question>
  <tr>
    <td>${question.id}</td>
    <td>${question.content}</td>
    <td>
        <#if question.select_type ==0>
            单选
        <#else>
            多选
        </#if>
    </td>
    <#list question.answer_list as answer>
    	<td>${answer.answer_content}</td>
    </#list>
  </tr>
  </#list>
	</body>
</html>