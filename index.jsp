<%@ page import="java.util.*,com.library.model.*" %>
<%
List<Book> books=(List<Book>)request.getAttribute("books");
List<Book> availableBooks=(List<Book>)request.getAttribute("availableBooks");
List<Member> members=(List<Member>)request.getAttribute("members");
List<IssuedBook> issues=(List<IssuedBook>)request.getAttribute("issues");
String error=(String)request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Library Management System</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/style.css">
</head>
<body>
<header>
  <div><h1>📚 Library Management</h1><p>Java + JDBC + MySQL</p></div>
  <a class="btn" href="#issue">Issue Book</a>
</header>

<% if(error!=null){ %><div class="alert"><%=error%></div><% } %>

<main>
<section class="stats">
 <div><b><%=books.size()%></b><span>Total Books</span></div>
 <div><b><%=availableBooks.size()%></b><span>Book Titles Available</span></div>
 <div><b><%=members.size()%></b><span>Members</span></div>
 <div><b><%=issues.size()%></b><span>Issue Records</span></div>
</section>

<section class="grid">
<div class="card">
<h2>Add Book</h2>
<form method="post" action="<%=request.getContextPath()%>/library">
<input type="hidden" name="action" value="addBook">
<input name="title" placeholder="Book title" required>
<input name="author" placeholder="Author" required>
<input name="category" placeholder="Category">
<input name="isbn" placeholder="ISBN">
<input name="quantity" type="number" min="1" value="1" required>
<button>Add Book</button>
</form>
</div>

<div class="card">
<h2>Add Member</h2>
<form method="post" action="<%=request.getContextPath()%>/library">
<input type="hidden" name="action" value="addMember">
<input name="name" placeholder="Member name" required>
<input name="email" type="email" placeholder="Email" required>
<input name="phone" placeholder="Phone">
<button>Add Member</button>
</form>
</div>
</section>

<section class="card">
<h2>Books</h2>
<form class="search" method="get" action="<%=request.getContextPath()%>/library">
<input name="search" placeholder="Search title, author or category">
<button>Search</button>
</form>
<table>
<tr><th>ID</th><th>Title</th><th>Author</th><th>Category</th><th>ISBN</th><th>Qty</th><th>Available</th><th>Action</th></tr>
<% for(Book b:books){ %>
<tr>
<td><%=b.getId()%></td><td><%=b.getTitle()%></td><td><%=b.getAuthor()%></td><td><%=b.getCategory()%></td>
<td><%=b.getIsbn()%></td><td><%=b.getQuantity()%></td><td><%=b.getAvailable()%></td>
<td><form method="post" action="<%=request.getContextPath()%>/library"><input type="hidden" name="action" value="deleteBook"><input type="hidden" name="id" value="<%=b.getId()%>"><button class="danger">Delete</button></form></td>
</tr>
<% } %>
</table>
</section>

<section class="card">
<h2>Members</h2>
<table>
<tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Action</th></tr>
<% for(Member m:members){ %>
<tr><td><%=m.getId()%></td><td><%=m.getName()%></td><td><%=m.getEmail()%></td><td><%=m.getPhone()%></td>
<td><form method="post" action="<%=request.getContextPath()%>/library"><input type="hidden" name="action" value="deleteMember"><input type="hidden" name="id" value="<%=m.getId()%>"><button class="danger">Delete</button></form></td></tr>
<% } %>
</table>
</section>

<section class="card" id="issue">
<h2>Issue a Book</h2>
<form method="post" action="<%=request.getContextPath()%>/library">
<input type="hidden" name="action" value="issue">
<select name="bookId" required><option value="">Select available book</option>
<% for(Book b:availableBooks){ %><option value="<%=b.getId()%>"><%=b.getTitle()%> — available <%=b.getAvailable()%></option><% } %>
</select>
<select name="memberId" required><option value="">Select member</option>
<% for(Member m:members){ %><option value="<%=m.getId()%>"><%=m.getName()%> — <%=m.getEmail()%></option><% } %>
</select>
<input type="date" name="dueDate" required>
<button>Issue Book</button>
</form>
</section>

<section class="card">
<h2>Issue / Return Records</h2>
<table>
<tr><th>ID</th><th>Book</th><th>Member</th><th>Issue Date</th><th>Due Date</th><th>Return Date</th><th>Status</th><th>Action</th></tr>
<% for(IssuedBook i:issues){ %>
<tr><td><%=i.getId()%></td><td><%=i.getBookTitle()%></td><td><%=i.getMemberName()%></td>
<td><%=i.getIssueDate()%></td><td><%=i.getDueDate()%></td><td><%=i.getReturnDate()==null?"-":i.getReturnDate()%></td>
<td><span class="badge"><%=i.getStatus()%></span></td>
<td><% if("ISSUED".equals(i.getStatus())){ %>
<form method="post" action="<%=request.getContextPath()%>/library"><input type="hidden" name="action" value="return"><input type="hidden" name="issueId" value="<%=i.getId()%>"><button>Return</button></form>
<% } else { %>—<% } %></td></tr>
<% } %>
</table>
</section>
</main>
<footer>Library Management System • Built with Java Servlet + JDBC + MySQL</footer>
</body>
</html>
