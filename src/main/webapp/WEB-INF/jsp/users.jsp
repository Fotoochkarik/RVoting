<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<%--<jsp:include page="fragments/headTag.jsp"/>--%>
<body>
<sesion>
    <a href="${pageContext.request.contextPath}/users/user">Add User</a>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Name</th>
            <th>Password</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <tr>
                <jsp:useBean id="user" scope="page" type="com.project.voting.model.User"/>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td><a href="users/update?id=${user.id}">Update</a></td>
                <td><a href="users/delete?id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</sesion>
</body>
</html>