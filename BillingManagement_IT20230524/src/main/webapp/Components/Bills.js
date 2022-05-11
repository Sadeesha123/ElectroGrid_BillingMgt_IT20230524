$(document).on("click", "#btnSave", function(event)
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateBillForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidBillIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "BillsAPI", 
 type : type, 
 data : $("#formBill").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onBillSaveComplete(response.responseText, status); 
 } 
 }); 
});

function onBillSaveComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divBliisGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 }
$("#hidBillIDSave").val(""); 
$("#formBill")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
		{ 
		$("#hidBillIDSave").val($(this).data("billid")); 
		 $("#billName").val($(this).closest("tr").find('td:eq(0)').text()); 
		 $("#billAddress").val($(this).closest("tr").find('td:eq(1)').text());
		 $("#billUnits").val($(this).closest("tr").find('td:eq(2)').text());
		 $("#billUnitPrice").val($(this).closest("tr").find('td:eq(3)').text()); 
		 $("#billTotPrice").val($(this).closest("tr").find('td:eq(4)').text()); 
		 $("#billDate").val($(this).closest("tr").find('td:eq(5)').text()); 
		});




$(document).on("click", ".btnRemove", function(event)
		{ 
		 $.ajax( 
		 { 
		 url : "BillsAPI", 
		 type : "DELETE", 
		 data : "billID=" + $(this).data("billid"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onBillDeleteComplete(response.responseText, status); 
		 } 
		 }); 
		});
		
function onBillDeleteComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divBliisGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}


// CLIENT-MODEL================================================================
function validateBillForm()
{
	// NO OF UNITS
	if ($("#billUnits").val().trim() == "")
	{
	return "Insert Bill Units.";
	}
	// NAME
	if ($("#billName").val().trim() == "")
	{
	return "Insert Bill Name.";
}

//UNIT PRICE-------------------------------
if ($("#billUnitPrice").val().trim() == ""){
	return "Insert Item Price.";
}
		// is numerical value
		var tmpPrice = $("#billUnitPrice").val().trim();
		if (!$.isNumeric(tmpPrice))
	{
	return "Insert a numerical value for Unit Price.";
	}
	
//TOTAL PRICE-------------------------------
if ($("#billTotPrice").val().trim() == ""){
	return "Insert Bill total Price.";
}
		// is numerical value
		var tmpPrice = $("#billTotPrice").val().trim();
		if (!$.isNumeric(tmpPrice))
	{
	return "Insert a numerical value for Total Price.";
	}	
		
// convert to decimal price
$("#billUnitPrice").val(parseFloat(tmpPrice).toFixed(2));
$("#billTotPrice").val(parseFloat(tmpPrice).toFixed(2));

// ADDRESS------------------------
if ($("#billAddress").val().trim() == ""){
	
	return "Insert user Address.";
}
	return true;
}