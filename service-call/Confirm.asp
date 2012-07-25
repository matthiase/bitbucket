<%@ Language="VBSCRIPT" %>
<%
  Response.Buffer = True                              
  
  Dim oCnn
  Set oCnn = Server.CreateObject("ADODB.Connection")                            
  oCnn.Open = "Provider=Microsoft.Jet.OLEDB.4.0;" & _
              "Data Source=[PATH TO YOUR DATA SOURCE]" & _
              "Persist Security Info=False"                  
%>


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.01 transitional//en" "http://www.w3.org/tr/html4/loose.dtd">
<html>
  <head>
    <title>
      Service Call Detail
    </title>
    
    <style>
    <!--
      .Container {font-family:verdana, helvetica, sans-serif; font-size:12px;}
      .Container h4 {color:#333333; font-size:16px; margin:0 0 8px 0;}
      .Container a:visited, a:link {text-decoration:none; color:#3333CC;}
      .Container a:hover {text-decoration:underline; color:#F87431;} 
      .BlockHeader {padding:4px 4px 0 4px; text-align:justify; width:640px; border-bottom:dashed 1px #3333CC;}      
      .BlockFooter {padding:16px 4px 0 4px; text-align:right; width:640px; }      
    -->
    </style>
        
  </head>
  
  <body>
    <div align="center" class="Container">
      <div class="BlockHeader">
        <h4>Your request has been processed successfully</h4>
      </div>

      <table border="0" class="Container" style="margin-top:8px; width:640px;" id="Table1">
        <tr>             
          <td valign="top" style="text-align:justify;">
            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
            eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
            minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
            ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
            voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur
            sint occaecat cupidatat non proident, sunt in culpa qui officia
            deserunt mollit anim id est laborum.<br>
          </td>
        </tr>
        <tr>             
          <td valign="top" style="text-align:justify;">
            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
            eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
            minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
            ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
            voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur
            sint occaecat cupidatat non proident, sunt in culpa qui officia
            deserunt mollit anim id est laborum.
          </td>
        </tr>     
        <tr>
          <td>
            <br><br>TRACE OUTPUT:<hr>
            <table cellspacing="2" style="color:#FF0000;padding:16px; text-align:justify;">
              <tr style="background-color:#CCCCCC;">
                <td>email:</td>
                <td>description:</td>
              </tr>
              <% WriteTrace %>
            </table>
          </td>
        </tr>                                                 
      </table>   
      
      <div class="BlockFooter">
        <a href="Default.asp" style="text-align:right; margin-top:24px; font-size:0.9em;">
          <img src="top.gif" border="0"> Back to main menu
        </a>             
      </div>
            
    </div>    
  </body>

</html>

<%
  If Not oCnn Is Nothing Then                                                   
    oCnn.Close
    Set oCnn = Nothing         
  End If       
%>


<script language="vbscript" runat="server">    
  '
  Sub WriteTrace()
    Dim oRs, strSql
    strSql = "SELECT [cscRez].[Email], [csc].[CallDescription] FROM [csc] INNER JOIN [cscRez] ON [cscRez].[ID] = [csc].[RezID] ORDER BY [csc].[ID] DESC"
    Set oRs = Server.CreateObject("ADODB.Recordset")        
    oRs.Open strSql, oCnn
                                                    
    If Not oRs.BOF Then      
      Do While Not oRs.EOF
        Response.Write "<tr style=""background-color:#FFFFCC;""><td>" & oRs("Email") & "</td><td>" & oRs("CallDescription") & "</td></tr>"
        oRs.MoveNext
      Loop 
    End If         
     
    oRs.Close     
    Set oRs = Nothing           
              
  End Sub  
  
  
  If Not oCnn Is Nothing Then                                                   
    oCnn.Close
    Set oCnn = Nothing         
  End If       
  
</script>  