<%@ Language="VBSCRIPT" %>
<% 
  Response.Buffer = True
  On Error Resume Next
%>


<html>
  <head>
    <!--#include file="DB.asp"-->
  </head>

  <body>   
    Results:<br>
  <%    
    Dim oCnn 
    Dim oRs
    
    Set oCnn = Server.CreateObject("ADODB.Connection")
    'm_oCnn.Open Application("DB")
    oCnn.Open "Provider=Microsoft.Jet.OLEDB.4.0;" & _
        "Data Source=[PATH TO YOUR DATA SOURCE]" & _
        "Persist Security Info=False"
    
    Set oRs = DbExecute(oCnn, "SELECT [ID],[Name] FROM [cscType]") 
    If Not oRs.BOF Then
      Do While Not oRs.EOF
        Response.Write "..." & oRs("Name") & "<br>"
        oRs.MoveNext
      Loop
    End If
    
    If Not oRs Is Nothing Then
      oRs.Close
      Set oRs = Nothing
    End If
    
    If Not oCnn Is Nothing Then
      oCnn.Close 
      Set oCnn = Nothing
    End If
            
  %>
  </body>
</html>
