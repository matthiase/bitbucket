<%@ Language="VBSCRIPT" %>
<% 
  Response.Buffer = True
  On Error Resume Next
  
  Dim m_oCnn 
  Set m_oCnn = Server.CreateObject("ADODB.Connection")
  'm_oCnn.Open Application("DB")
  m_oCnn.Open "Provider=Microsoft.Jet.OLEDB.4.0;" & _
      "Data Source=[PATH TO YOUR DATA SOURCE]" & _
      "Persist Security Info=False"
  
%>


<!doctype html public "-//w3c//dtd html 4.01 transitional//en" "http://www.w3.org/tr/html4/loose.dtd">
<html>
  <head>
    <!--#include file="DB.asp"-->
    <meta http-equiv="content-type" content="text/html" charset="iso-8859-1">
    <title>New Service Request</title>        
    <link rel="Stylesheet" rev="Stylesheet" href="default.css">
                                  
    <script language="javascript" type="text/javascript">
    //<!--
	    function postback() {
		    var form;
		    if (window.navigator.appName.toLowerCase().indexOf("microsoft") > -1) {
			    form = document.RequestForm;
		    }
		    else {
			    form = document.forms["RequestForm"];
		    }
		    	
		    if (validate()) {
		      form.submit();
		    }	    		    
		      		    
	    }
	    
	    function validate() {	      
	      var controls = new Array('txtEmail', 'txtFirstname', 'txtLastname', 'txtAddress', 'txtCity', 'txtZipcode', 'txtDescription');
	      var isValid, message, ctrl;	      	      

        isValid = true;
	      for (var n = 0; n < controls.length; n++) {          
          ctrl = document.getElementById(controls[n]);
          if (ctrl.value.length == 0) {
            isValid = false;
            ctrl.className = 'InvalidTextControl';
          }
	      }

        if (!isValid) {
          ctrl = document.getElementById('ValidationMessage');
          ctrl.innerHTML = 'Please complete all required fields.';
        }
	      	      	      	      
	      return isValid;
	    }	    	    
    // -->
    </script>            
                 
  </head>
  
  <body>
  
      <%
      If UCase(Request.ServerVariables("REQUEST_METHOD")) = "POST" Then
        Process      
      End If
      %>
  
  
    <form id="RequestForm" name="RequestForm" action="AddNew.asp" method="post">
    
   
      <table align="center" border="0" cellpadding="0" cellspacing="0" class="Container">        
        <tr>
          <td colspan="3">
            <h4>
              New Service Request
            </h4>
          </td>
          <td align="right">
            <a href="Default.asp" style="text-align:right; margin-top:24px; font-size:0.9em;" tabindex="11">
              <img src="top.gif" align="absmiddle" border="0">
              Back to main menu
            </a>
          </td>
        </tr>
          
        <tr>
          <td>Service Type:&nbsp;</td>
          <td>
            <select id="lstService" name="lstService" style="width:220px;" tabindex="1">
              <%RenderRequestTypes%>
            </select>          
          </td>      
          <td> &nbsp; </td>  
          <td rowspan="7" style="padding-left:4px;">
            <textarea id="txtDescription" name="txtDescription" class="TextControl" style="width:285px; height:165px;" tabindex="9" onfocus="this.select();">[service description]</textarea>
          </td>          
        </tr>   

        <tr>
          <td>Email Address:&nbsp;</td>
          <td><input id="txtEmail" name="txtEmail" type="text" class="TextControl" style="width:215px;" tabindex="2"></td>        
          <td> &nbsp; </td>  
        </tr>   

        <tr>
          <td>First Name:&nbsp;</td>
          <td><input id="txtFirstname" name="txtFirstname" type="text" class="TextControl" style="width:215px;" tabindex="3"></td>
          <td> &nbsp; </td>  
        </tr>
        
        <tr>
          <td>Last Name:&nbsp;</td>
          <td><input id="txtLastname" name="txtLastname" type="text" class="TextControl" style="width:215px;" tabindex="4"></td>      
          <td> &nbsp; </td>  
        </tr>
            
        <tr>
          <td>Street Address:&nbsp;</td>
          <td><input id="txtAddress" name="txtAddress" type="text" class="TextControl" style="width:215px;" tabindex="5"></td>      
          <td> &nbsp; </td>  
        </tr>           

        <tr>
          <td>City:&nbsp;</td>
          <td><input id="txtCity" name="txtCity" type="text" class="TextControl" style="width:215px;" tabindex="6"></td> 
          <td> &nbsp; </td>  
        </tr>     
        
        <tr>
          <td>State:&nbsp;</td>
          <td>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td align="left">
                  <select id="lstState" name="lstState" class="TextControl" style="width:55px;" tabindex="7">
                    <option value="CO">CO</option>
                  </select>                  
                </td>
                <td> &nbsp; </td>                  
                <td align="right" style="padding-right:1px;">
                  &nbsp; Zip: 
                  <input id="txtZipcode" name="txtZipcode" type="text" class="TextControl" style="width:80px;" tabindex="8">
                </td>                
              </tr>
            </table>
          </td> 
          <td> &nbsp; </td>                          
        </tr>                         
        
        <tr>
          <td id="ValidationMessage" align="right" colspan="3" class="Warning">            
            &nbsp;
          </td>
          <td align="right" style="padding:8px;">
            <a href="javascript:postback();" style="vertical-align:middle;" tabindex="10">
              <img src="submit.gif" border="0" width="65" height="24">
            </a>            
          </td>
        </tr>
                        
      </table>    
    </form>
  </body>
</html>

<%
  If m_oCnn Is Nothing Then
    m_oCnn.Close 
    Set m_oCnn = Nothing
  End If         
%>
  
<script language="vbscript" runat="server">

  '======================================================================================

  '======================================================================================
  Sub RenderRequestTypes()
    Dim oRs, strSql, nId, strName    
    Set oRs = DbExecute(m_oCnn, "SELECT [ID],[Name] FROM [cscType]")     
                                                    
    If Not oRs.BOF Then      
      Do While Not oRs.EOF
        nId = oRs("ID")
        strName = oRs("Name")       
        Response.Write("<option value=""" & nId & """>" & strName & "</option>")                
        oRs.MoveNext
      Loop 
    End If         
     
    If Not oRs Is Nothing Then
      oRs.Close     
      Set oRs = Nothing 
    End If     
                                
  End Sub    
  
  
  '======================================================================================

  '======================================================================================
  Sub Process()
      
    Dim nResidentId, strEmail
    strEmail = Request.Form("txtEmail")
    nResidentId = GetResidentId(strEmail)
        
    If nResidentId = 0 Then    
      nResidentId = InsertResident()
    End If
        
    If nResidentId > 0 Then      
      InsertRequest(nResidentId)
      Response.Redirect("Confirm.asp")
    Else
      Response.Write "An error occurred."
    End If
    
  End Sub
  
  
  '======================================================================================

  '======================================================================================
  Function GetResidentId(strEmail)
      
    Dim  oRs, strSql, nId
    nId = 0

    strSql = "SELECT [ID] FROM [cscRez] WHERE [Email]='" & DbEncode(strEmail) & "'"    
    Set oRs = DbExecute(m_oCnn, strSql)
    
    If Not oRs.BOF Then
      nId = oRs("ID")
    End If
    
    If Not oRs Is Nothing then
      oRs.Close
      Set oRs = Nothing    
    End If
    
    GetResidentId = nId   
  End Function   
  
  
  '======================================================================================

  '======================================================================================
  Function InsertResident()
    Dim strInsert, strFirstname, strLastname, strAddress, strCity, strState
    Dim strZipcode, strPhone, strEmail
    strFirstname = Request.Form("txtFirstname")
    strLastname = Request.Form("txtLastname")
    strAddress = Request.Form("txtAddress")
    strCity = Request.Form("txtCity")
    strZipcode = Request.Form("txtZipcode")
    strState = Request.Form("lstState")
    strEmail = Request.Form("txtEmail")        
    
    strInsert = "INSERT INTO [cscRez]([Name],[FirstName],[Address],[City],[State]," & _
      "[Zip],[Email]) VALUES (" & _
      "'" & DbEscape(strLastname) & "'" & _
      ",'" & DbEscape(strFirstname)  & "'" & _
      ",'" & DbEscape(strAddress) & "'" & _
      ",'" & DbEscape(strCity) & "'" & _
      ",'" & DbEscape(strState) & "'" & _
      ",'" & DbEscape(strZipcode) & "'" & _
      ",'" & DbEscape(strEmail) & "'" & _
      ")"
     
    DbExecuteNonQuery m_oCnn, strInsert
    InsertResident = GetResidentId(strEmail)        
   
  End Function
  
  
  '======================================================================================

  '======================================================================================
  Sub InsertRequest(nResidentId)
    Dim strInsert, nTypeId, strDescription
    strTypeId = CStr(Request.Form("lstService"))
    strDescription = DbEncode(CStr(Request.Form("txtDescription")))
    
    strInsert = "INSERT INTO [csc]([TypeID],[EmpID],[DeptID],[RezID],[CallDate],[CallTime],[CallDescription]) " & _ 
      "SELECT " & strTypeId & _
      ",[EmpID]"  & _
      ",[DeptID]" & _
      "," & CStr(nResidentId) & _      
      ",'" & Date()  & "'" & _
      ",'" & Time()  & "'" & _
      ",'" & strDescription & "'" & _
      " FROM [cscType] WHERE [ID]=" & strTypeId

    DbExecuteNonQuery m_oCnn, strInsert
   
  End Sub
  
  
</script>  
