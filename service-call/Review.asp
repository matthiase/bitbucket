<%@ Language="VBSCRIPT" %>
<% 
  Response.Buffer = True
  On Error Resume Next
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 TRANSITIONAL//EN" "HTTP://WWW.W3.ORG/TR/HTML4/LOOSE.DTD">
<html>
  <head>
    <!--#include file="DB.asp"-->
    <meta http-equiv="content-type" content="text/html" charset="iso-8859-1">
    <title>Service Call Review</title>        
    <link rel="Stylesheet" rev="Stylesheet" href="default.css">
    
    <script language="javascript" type="text/javascript">
    //<!--
	    function postback() {
		    var form;
		    if (window.navigator.appName.toLowerCase().indexOf("microsoft") > -1) {
			    form = document.QueryForm;
		    }
		    else {
			    form = document.forms["QueryForm"];
		    }
		    form.submit();
	    }
    // -->
    </script>    
        
  </head>
  
  <body>
  
  <%
                        
    ' Check if the user has selected a call.
    Dim nSelectedId 
    If Not IsEmpty(Request.QueryString("cn")) Then        
      nSelectedId = CInt(Request.QueryString("cn"))
    End If    
      
    ' Get the temporary cookie from the request object and populate the email field.      
    Dim strEmail    

    If Not IsEmpty(Request.Form("txtEmail")) Then      
      strEmail = Request.Form("txtEmail")
      Response.Cookies("ServiceCall") = strEmail
      Response.Cookies("ServiceCall").Path = "/"      
    End If

    If Not IsEmpty(Response.Cookies("ServiceCall")) Then
      If Len(Request.Cookies("ServiceCall")) > 0 Then        
        strEmail = Request.Cookies("ServiceCall")
      End If
    End If
                          
  %>
  
  
  <div align="center" class="Container">
    
    <!-- SERVICE CALL QUERY -->    
    <form id="QueryForm" name="QueryForm" action="Review.asp" method="post">
      <div style="width:640px; text-align:left;">
        <h4>Service Request Review</h4>
      </div>
      
      <div class="Query" style="text-align:justify; padding:8px 24px 8px 24px;">    
        Please enter the email address used to submit the service request, <b>or</b> a specific service request number:
        <input id="txtEmail" name="txtEmail" type="text" value="<%=strEmail%>" style="border:solid 1px #666666; width:285px;">
        <a href="javascript:postback();">
          <img src="submit.gif" align="absmiddle" border="0" width="65" height="24">
        </a>
      </div>    

      <div style="width:640px; text-align:right;">
        <a href="Default.asp" style="text-align:right; margin-top:24px; font-size:0.9em;">
          <img src="top.gif" align="absmiddle" border="0">
          Back to main menu
        </a>            
      </div>
      
    </form>
    
<%  'The list of calls will be selected based on the user email or a specific call number.  
    If Len(strEmail) > 0 Then    
    
      ' Get a disconnected recordset of the service request matching the user's input.
      Dim oServiceRequest, nCallId, strCallDescription, nRecordCount
      Set oServiceRequest = DbRecordset("SELECT [csc].[ID], [csc].[Open], [cscRez].[Email], [csc].[CallDescription] FROM [csc] INNER JOIN [cscRez] ON [cscRez].[ID] = [csc].[RezID] WHERE [Email] = '" & DbEncode(strEmail) & "'")
                  
      nRecordCount = 0                  
      oServiceRequest.Filter = "Open <> 0"                 
        If Not oServiceRequest.BOF Then  
%>        
          <div class="CallList"> <!-- BEGIN OPEN SERVICE CALLS -->
            <h4>Open Service Calls</h4>        
<%                  
          nRecordCount = nRecordCount + oServiceRequest.RecordCount
          Do While Not oServiceRequest.EOF      
            nCallId = CInt(oServiceRequest("ID"))
            strCallDescription = CStr(oServiceRequest("CallDescription"))
            
            If CInt(nCallId) = CInt(nSelectedId) Then
%>              
            <!-- BEGIN SELECTED CALL -->        
            <div class="Selected">
              <img src="calldetail.gif" align="absmiddle" border="0" height="16" width="16">
              <font color="#990000">
                <%=nCallId%> &nbsp;&nbsp; Submitted: 10/26/2005 &nbsp;&nbsp; Status: Open
              </font>
                          
              <table border="0" class="Container">
                <tr>          
                  <td valign="top" width="200">
                    <!-- CALL PROPERTIES -->
                    <table border="0" class="Container" width="100%" style="text-align:left;" id="Table4">
                      <tr>
                        <td>
                          Call Type: Road Condition
                        </td>
                      </tr> 
                      <tr>
                        <td>
                          Assignee: Martinez, Edgar
                        </td>
                      </tr>           
                      <tr>
                        <td>
                          Scheduled: 11/02/2005
                        </td>
                      </tr>                
                    </table>
                  </td>
                  <!-- CALL DESCRIPTION -->       
                  <td valign="top" style="text-align:justify;">
                    <%=strCallDescription %>
                  </td>
                </tr>                            
              </table>                          
            </div>        
            <!-- END SELECTED CALL -->        
<%    
            Else
%>            
            <!-- BEGIN SERVICE CALL -->
            <div class="ServiceCall">
              <a href="Review.asp?cn=<%=nCallId%>">
                <img src="callsummary.gif" align="absmiddle" border="0" height="16" width="16">
                  <%=nCallId%> &nbsp;&nbsp; Submitted: 10/26/2005 &nbsp;&nbsp; Status: Open
              </a><br>
              <%=strCallDescription%>
            </div>  
            <!-- END SERVICE CALL -->                        
<%            
            End If ' If nCallId = nSelectedId
            oServiceRequest.MoveNext
          Loop 'Do While Not oServiceRequest.EOF
     
        Response.Write "</div> <!-- END OPEN SERVICE CALLS -->"   
        End If 'If Not oServiceRequest.BOF    
            
        oServiceRequest.Filter = "Open = 0"                 
        If Not oServiceRequest.BOF Then          
%>
          <div class="CallList">
            <h4>Closed Service Calls</h4>
<%            
          nRecordCount = nRecordCount + oServiceRequest.RecordCount              
          Do While Not oServiceRequest.EOF      
            nCallId = CInt(oServiceRequest("ID"))
            strCallDescription = CStr(oServiceRequest("CallDescription"))
            
            If CInt(nCallId) = CInt(nSelectedId) Then
%>              
            <!-- BEGIN SELECTED CALL -->        
            <div class="Selected">
              <img src="calldetail.gif" align="absmiddle" border="0" height="16" width="16">
              <font color="#990000">
                <%=nCallId%> &nbsp;&nbsp; Submitted: 10/26/2005 &nbsp;&nbsp; Status: Open
              </font>
                          
              <table border="0" class="Container" id="Table1">
                <tr>          
                  <td valign="top" width="200">
                    <!-- CALL PROPERTIES -->
                    <table border="0" class="Container" width="100%" style="text-align:left;">
                      <tr>
                        <td>
                          Call Type: Road Condition
                        </td>
                      </tr> 
                      <tr>
                        <td>
                          Assignee: Martinez, Edgar
                        </td>
                      </tr>           
                      <tr>
                        <td>
                          Scheduled: 11/02/2005
                        </td>
                      </tr>                
                    </table>
                  </td>
                  <!-- CALL DESCRIPTION -->       
                  <td valign="top" style="text-align:justify;">
                    <%=strCallDescription %>
                  </td>
                </tr>                            
              </table>                          
            </div>        
            <!-- END SELECTED CALL -->        
<%    
            Else
%>            
            <!-- BEGIN SERVICE CALL -->
            <div class="ServiceCall">
              <a href="Review.asp?cn=<%=nCallId%>">
                <img src="callsummary.gif" align="absmiddle" border="0" height="16" width="16">
                  <%=nCallId%> &nbsp;&nbsp; Submitted: 10/26/2005 &nbsp;&nbsp; Status: Open
              </a><br>
              <%=strCallDescription%>
            </div>  
            <!-- END SERVICE CALL -->                        
<%            
            End If ' If nCallId = nSelectedId
            oServiceRequest.MoveNext
          Loop 'Do While Not oServiceRequest.EOF
        End If 'If Not oServiceRequest.BOF    
        Response.Write "</div> <!-- END CLOSED SERVICE CALLS -->"
        
        If CInt(nRecordCount) = 0 Then
          Response.Write "<div align=""center"" class=""Warning"">"
          Response.Write "Unable to find records matching: " & strEmail
          Response.Write "</div>"
        End If ' If CInt(nRecordCount)  
        
        If Not oServiceRequest Is Nothing Then
          oServiceRequest.Close
          Set oServiceRequest = Nothing
        End If
                      
      End If 'If Len(strEmail) > 0  
%>      

    </div>      
  </body>
</html>