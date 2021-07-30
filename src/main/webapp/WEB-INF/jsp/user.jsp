<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--<jsp:include page="fragments/headTag.jsp"/>--%>
<body>
<form method="post" action="user">
    <dl>
        <dt>Name</dt>
        <dd><input type="text" name="username" value="${param.username}"></dd>
    </dl>
    <dl>
        <dt>Password:</dt>
        <dd><input type="text" name="password" value="${param.password}"></dd>
    </dl>

    <button type="submit">Save</button>
</form>
</body>
</html>
