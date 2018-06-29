<html>
<head>
    <title>student</title>
</head>
<body>
    学生信息：<br>
    学号：${student.id}&nbsp;&nbsp;&nbsp;&nbsp;
	姓名：${student.name}&nbsp;&nbsp;&nbsp;&nbsp;
	住址：${student.address}<br>
    学生列表：
    <table border="1">
        <tr>
            <th>序号</th>
            <th>学号</th>
			<th>姓名</th>
			<th>住址</th>
        </tr>
        <#list studentList as student>
        <#if student_index % 2 == 0>
            <tr bgcolor="red">
        <#else>
            <tr bgcolor="blue">
        </#if>
				<td>${student_index}</td>
				<td>${student.id}</td>
				<td>${student.name}</td>
				<td>${student.address}</td>
			</tr>
        </#list>
    </table><br>
    <!--可以使用？date?datetime-->
    当前日期：${date?string('yyyy/MM/dd HH:mm:ss')}<br>
    null值的处理：${val!"mydefault"}<br>
    <#if val??>
        val有值
    <#else>
        val无值
    </#if>
    <!--引用模板-->
	引用模板:<br>
    <#include "hello.ftl">

</body>
</html>