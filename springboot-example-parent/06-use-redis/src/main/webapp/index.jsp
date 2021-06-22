<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<form action="stu/register" method="post">
  姓名：<input type="text" name="name"> <br>
  年龄：<input type="text" name="age"> <br>
  <input type="submit" value="注册">
</form>

<form action="stu/find" method="get">
  学生id：<input type="text" name="id"><br>
  <input type="submit" value="查询">
</form>
<hr>
<a href="stu/studentsCount">查询学生总人数</a>
</body>
</html>
