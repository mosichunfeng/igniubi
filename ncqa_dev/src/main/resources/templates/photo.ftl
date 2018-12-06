<html>
    <head>

    </head>

    <body>
        <form align="center" action="https://localhost/photo/index" method="POST">
            时间:<input type="text" name="week" value="${week}"></input>
            姓名:<input type="text" name="name" value=""></input>
            班级:
            <select name="class_name">
                <option value="all">全部</option>
                <option value="网络工程15201">网络工程15201</option>
                <option value="网络工程15202">网络工程15202</option>
                <option value="网络工程15203">网络工程15203</option>
                <option value="网络工程15204">网络工程15204</option>
                <option value="物联网15201">物联网15201</option>
                <option value="物联网15202">物联网15202</option>
                <option value="物联网15203">物联网15203</option>
                <option value="物联网15204">物联网15204</option>
            </select>
            查询 <input type="submit"></form>
        </form>
        <a href="https://www.jiandev.cn/user/exportRegister" align="center">导出本周所有签到记录</a>
        <a href="https://www.jiandev.cn/user/exportTodayInRegister">导出今天在校学生签到情况</a>
        <a href="https://www.jiandev.cn/user/exportThisWeekOutRegister">导出本周离校学校签到情况</a>
        <a></a>
        <#if list??>
            <#list list as map>
                <#list map?keys as key>
                    <img src="https://www.jiandev.cn/photo/getOnePhoto?week=${key}&name=${map["${key}"]}" width="256" height="320"></img>
                </#list>
            </#list>
        </#if>

    </body>
</html>