<%@page import="com.Bill"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Bills.js"></script>
</head>

<body>
<div class="container"><div class="row"><div class="col-6"> 
<h1>Bill Management V10.1</h1>
	<div class="container">
		<div class="row">
			<div class="col-6">
				
				<form id="formBill" name="formBill" method="post" action="index.jsp">
					
					Name:
					<input id="billName" name="billName" type="text"
						class="form-control form-control-sm"> <br>
					Address:
					<input id="billAddress" name="billAddress" type="text"
						class="form-control form-control-sm"> <br>
					Units Consumed:
					<input id="billUnits" name="billUnits" type="text"
						class="form-control form-control-sm"> <br>
					Unit Price:
					<input id="billUnitPrice" name="billUnitPrice" type="text"
						class="form-control form-control-sm"> <br>			
					Total Price: 
					<input id="billTotPrice" name="billTotPrice" type="text"
						class="form-control form-control-sm"> <br>
					Bill Date:
					<input id="billDate" name="billDate" type="text"
						class="form-control form-control-sm"> <br>
					<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidBillIDSave" name="hidBillIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divBliisGrid">
					<%
					Bill billObj =  new Bill();
					out.print(billObj.readBill());
					%>
				</div>
			</div>
		</div>
	</div>
</body>

</html>