
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" 
 rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" 
 crossorigin="anonymous">
<title>KeepNote</title>
</head>
<body>
<!-- Create a form which will have text boxes for Note ID, title, content and status along with a Send 
	 button. Handle errors like empty fields -->
	 
	 
	 <form   method="POST" action="<c:url value="/saveNote/"/>" class="form-group col-md-5 offset-md-2">
	 <label for="noteId">Note ID</label>
	 <input type="number" placeholder="Enter the Note ID"  name="noteId" class="form-control mb-2"  value="${noteItem.noteId}"/>
	 <label for="noteTitle">Note Title</label>
	 <input type="text" placeholder="Enter the Note Title" name="noteTitle" class="form-control mb-2" value="${noteItem.noteTitle}"></input>
	 <label for="noteContent">Note Content</label>
	 <input type="text" placeholder="Enter the Note Content" name="noteContent" class="form-control mb-2" value="${noteItem.noteContent}"></input>
	 <label for="noteStatus">Note Status</label>
	 <input type="text" placeholder="Enter the Note Status" name="noteStatus" class="form-control mb-2" value="${noteItem.noteStatus}"></input>
	 
	  <c:if test="${! empty noteItem.noteId}">
		  <button type="submit" class="btn btn-info">Update User</button>
	  </c:if>
	  <c:if test="${empty noteItem.noteId}">
		  <button type="submit" class="btn btn-success">Add User</button>
	  </c:if>
	 </form>
	 
<!-- display all existing notes in a tabular structure with Id, Title,Content,Status, Created Date and Action -->


<table class="table col-md-4 mt-3">
  <thead>
	<tr class="table-warning">
	  <th scope="col">ID</th>
	  <th scope="col">Title</th>
	  <th scope="col">Content</th>
	  <th scope="col">Status</th>
	  <th scope="col">Created Time</th>
	  <th scope="col">Action</th>
	</tr>
  </thead>
  <c:if test="${! empty noteList }">
	  <c:forEach var="note" items="${noteList}">
	  <tbody>
		<tr class="table-primary">
		  <td scope="row">${note.noteId}</td>
		  <th>${note.noteTitle}</th>
		  <td>${note.noteContent}</td>
		  <td>${note.noteStatus}</td>
		  <td>${note.createdAt}</td>
		  <td >
		 
				  <a href="<c:url value="/updatenote/${note.noteId}"/>">
								<button type="button" class="btn btn-primary">Update</button></a>
				  
				  <form action="<c:url value="/deleteNote/"/>" method="get">
					   <input type="hidden" id="noteId" name="noteId" value="${note.noteId}"/>
						<button type="submit" class="btn btn-danger mt-2">Delete</button>
						 <form>
				  
		  </td>
		</tr>
	  </tbody>
	  </c:forEach>
  </c:if>
</table>


</body>
</html>