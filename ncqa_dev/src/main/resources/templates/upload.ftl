<html>
    <head>
    </head>

    <body>
        <h1>
            往${base_name}上传题
        </h1>
        <form action="http://localhost:8082/admin/upload" method="post" enctype="multipart/form-data">
            <input type="hidden" name="base_id" value="${base_id}"></input>
            请选择标准格式的excel<input type="file" name="proxyfile"></input><br>
            提交<input type="submit"></input>
        </form>
    </body>
</html>