<%--
  Created by IntelliJ IDEA.
  User: V
  Date: 04.08.2021
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>

<div class="container">

    <div class="starter-template">
        <h1>403 - Access is denied</h1>
        <div typeof = "text">Hello '${pageContext.request.userPrincipal.name}',
            you do not have permission to access this page.</div>
    </div>

</div>



</body>
</html>
