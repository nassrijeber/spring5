<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous"/>
	<title>Recherche de livres</title>
</head>

<body>

	<div style="display: flex; flex-direction:row">
		<div style="flex: 1; padding: 15px">
			<!--  début du formulaire de recherche -->
			<form:form method="get" modelAttribute="book">
				<label for="title">Title : </label>
					<form:input path="title" class="form-control"/>
					<form:errors path="title"/>
				<br/>
				<label for="authorName">Author : </label>
					<form:input path="author.lastname" class="form-control"/>
					<form:errors path="author.lastname"/>
				<br/>
				<button type="submit" class="btn btn-primary">Search</button>
			</form:form>
			<!-- fin du formulaire de recherche -->
		</div>
		<div style="flex: 2; padding: 15px">
			<!-- liste de résultats -->
			<ul class="list-group">
				<c:forEach var="b" items="${results}">
					<li class="list-group-item">
						<a href="/books/${b.id}"><c:out value="${b.title} - ${b.author.fullname}"/></a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>
