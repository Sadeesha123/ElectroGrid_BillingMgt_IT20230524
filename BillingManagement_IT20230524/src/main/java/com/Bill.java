package com; 


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

public class Bill 
{ //A common method to connect to the DB
		private Connection connect(){ 
			
						Connection con = null; 
						
						try{ 
								Class.forName("com.mysql.jdbc.Driver"); 
 
								//Provide the correct details: DBServer/DBName, username, password 
								con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", ""); 
						} 
						catch (Exception e) {
							e.printStackTrace();
							} 
						
						return con; 
			} 
		
		
		public String insertBill(String name, String address, String unit, String uprice,String tprice, String date){ 
			
					String output = ""; 
					
					try
					{ 
						Connection con = connect(); 
						
						if (con == null) 
						{
							return "Error while connecting to the database for inserting."; 
							
						} 
						// create a prepared statement
						
						String query = " insert into billing_table (`billID`,`billName`,`billAddress`,`billUnits`,`billUnitPrice`,`billTotPrice`,`billDate`)"+" values (?, ?, ?, ?, ?, ?, ?)"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						// binding values
						preparedStmt.setInt(1, 0); 
						preparedStmt.setString(2, name); 
						preparedStmt.setString(3, address); 
						preparedStmt.setString(4, unit);
						preparedStmt.setDouble(5, Double.parseDouble(uprice));
						preparedStmt.setDouble(6, Double.parseDouble(tprice));
						preparedStmt.setString(7, date); 
						// execute the statement
 
						preparedStmt.execute(); 
						con.close(); 
						String newBills = readBill(); 
						output = "{\"status\":\"success\",\"data\":\""+newBills+"\"}";
					} 
					
					catch (Exception e) 
					{ 
						output = "{\"status\":\"error\", \"data\":\"Error while inserting the bill.\"}";
						System.err.println(e.getMessage()); 
					} 
					return output; 
			} 
		
		
		
			public String readBill(){ 
				
					String output = ""; 
					
					try{ 
							Connection con = connect(); 
							if (con == null){
								
								return "Error while connecting to the database for reading."; 
								
								} 
							// Prepare the html table to be displayed
							output =					
									"<table border=\"1\" class=\"table\"><tr><th>Name</th>" +
									"<th>Address</th>" + 
									"<th>Units Consumed</th>" +
									"<th>Unit Price</th>" +
									"<th>Total Price</th>" +
									"<th>Bill Date</th>" +
									"<th>Update</th><th>Remove</th></tr>"; 
 
							String query = "select * from billing_table"; 
							Statement stmt = con.createStatement(); 
							ResultSet rs = stmt.executeQuery(query); 
							// iterate through the rows in the result set
							while (rs.next()) 
							{ 
									String billID = Integer.toString(rs.getInt("billID")); 
									String billName = rs.getString("billName"); 
									String billAddress = rs.getString("billAddress");
									String billUnits = rs.getString("billUnits");
									String billUnitPrice = Double.toString(rs.getDouble("billUnitPrice")); 
									String billTotPrice = Double.toString(rs.getDouble("billTotPrice")); 
									String billDate = rs.getString("billDate"); 
									// Add into the html table
									output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='"+billID+"'>"+billName+"</td>";
								
									output += "<td>" + billAddress + "</td>"; 
									output += "<td>" + billUnits + "</td>"; 
									output += "<td>" + billUnitPrice + "</td>";
									output += "<td>" + billTotPrice + "</td>";
									output += "<td>" + billDate + "</td>";
									// buttons
									output += "<td><input name='btnUpdate' type='button' value='Update'"
											+ "class='btnUpdate btn btn-secondary' data-billid='" + billID + "'></td>"
											+ "<td><input name='btnRemove' type='button' value='Remove' "
											+ "class='btnRemove btn btn-danger' data-billid='" + billID + "'></td></tr>"; 
											 
							} 
								con.close(); 
								// Complete the html table
								output += "</table>";
										
						} 
						catch (Exception e){ 
									output = "Error while reading the bill."; 
									System.err.println(e.getMessage()); 
						} 
						return output; 
						} 
			
			
			public String updateBill(String ID,String name, String address, String unit, String uprice,String tprice, String date){ 
				
					String output = ""; 
					
					try{ 
							Connection con = connect(); 
							if (con == null){
								return "Error while connecting to the database for updating.";
								} 
							// create a prepared statement
							String query = "UPDATE billing_table SET billName=?,billAddress=?,billUnits=?,billUnitPrice=?,billTotPrice=?,billDate=? WHERE billID=?"; 
							PreparedStatement preparedStmt = con.prepareStatement(query); 
							// binding values
							preparedStmt.setString(1, name); 
							preparedStmt.setString(2, address);
							preparedStmt.setString(3, unit);
							preparedStmt.setDouble(4, Double.parseDouble(uprice)); 
							preparedStmt.setDouble(5, Double.parseDouble(tprice)); 
							preparedStmt.setString(6, date); 
							preparedStmt.setInt(7, Integer.parseInt(ID)); 
							// execute the statement
							preparedStmt.execute(); 
							con.close(); 
							String newBills = readBill();
							output = "{\"status\":\"success\",\"data\":\""+newBills+"\"}"; 
					} 
					
					catch (Exception e){ 
						
						output = "{\"status\":\"error\",\"data\":\"Error while updating the bill.\"}"; 
						System.err.println(e.getMessage()); 
						
					} 
					
					return output; 
			} 
			
			
			public String deleteBill(String billID){ 
				
					String output = ""; 
					
					try{ 
						Connection con = connect(); 
						
						if (con == null){
							return "Error while connecting to the database for deleting."; 
							} 
						// create a prepared statement
						String query = "delete from billing_table where billID=?"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						// binding values
						preparedStmt.setInt(1, Integer.parseInt(billID)); 
						// execute the statement
						preparedStmt.execute(); 
						con.close(); 
						String newBills = readBill(); 
						 output = "{\"status\":\"success\",\"data\":\""+newBills+"\"}";  
					} 
					
					catch (Exception e){ 
						output = "{\"status\":\"error\",\"data\":\"Error while deleting the bill.\"}"; 
						System.err.println(e.getMessage()); 
					} 
					return output; 
			} 
			
			
			
			
} 