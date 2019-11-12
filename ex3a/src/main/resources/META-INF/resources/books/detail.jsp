<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous"/>
	<title><c:out value="${entity.title}"/></title>
</head>

<body>
	<div style="display: flex; flex: column">
		<div style="flex: 1; padding: 15px">
			Title : <c:out value="${entity.title}"/><br/>
			Author : <c:out value="${entity.author.fullname}"/><br/>
			<hr/>
			Comments : 
			<ul>
				<c:forEach var="c" items="${entity.comments}">
					<li class="list-group-item"><c:out value="${c.text}"/></li>
				</c:forEach>
			</ul>
		
		</div>
		
		<div style="flex: 1; padding: 15px">
			<!-- début du formulaire de réservation -->

			<form:form modelAttribute="reservationCommand" method="post" action="/reservations">
				<fieldset>
					<legend>Borrow this book</legend>
					
					<label for="pickupDate">Pickup date :</label> 
					<form:input type="date" path="pickupDate" class="form-control"/>
					<form:errors path="pickupDate"/>
					<br/>
					
					<label for="returnDate">Return date :</label>
					<form:input type="date" path="returnDate" class="form-control"/>
					<form:errors path="returnDate"/>
					<br/>
					
					<label for="username">Username :</label>
					<form:input type="text" path="username" class="form-control"/>
					<form:errors path="username"/>
					<br/>
					
					<button class="btn btn-primary" type="submit" style="margin-top:20px">Borrow</button>
				</fieldset>
			</form:form>

			<!-- fin du formulaire de réservation -->
		</div>
	</div>
</body>
</html>
