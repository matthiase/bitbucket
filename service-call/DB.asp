<script language="vbscript" runat="server">

'---- CursorTypeEnum Values ----
Const adOpenForwardOnly = 0
Const adOpenKeyset = 1
Const adOpenDynamic = 2
Const adOpenStatic = 3

'---- CursorLocationEnum Values ----
Const adUseServer = 2
Const adUseClient = 3

'---- LockTypeEnum Values ----
Const adLockReadOnly = 1
Const adLockPessimistic = 2
Const adLockOptimistic = 3
Const adLockBatchOptimistic = 4

'---- ObjectStateEnum Values ----
Const adStateClosed = &H00000000
Const adStateOpen = &H00000001
Const adStateConnecting = &H00000002
Const adStateExecuting = &H00000004


'======================================================================================

'======================================================================================
Function DbExecute(oCnn, strSql)
  Dim oRs    
  Set oRs = Server.CreateObject("ADODB.Recordset")
  oRs.Open strSql, oCnn, adOpenForwardOnly, adLockReadOnly
  Set DbExecute = oRs  
End Function


'======================================================================================

'======================================================================================
Sub DbExecuteNonQuery(oCnn, strSql)
  oCnn.Execute(strSql)
End Sub  

  
'======================================================================================

'======================================================================================
Function DbRecordset(strSQL)
  Dim oCnn, oRs
  Set oCnn = Server.CreateObject("ADODB.Connection")
  'oCnn.Open Application("DB")
  oCnn.Open "Provider=Microsoft.Jet.OLEDB.4.0;" & _
      "Data Source=[PATH TO YOUR DATA SOURCE]" & _
      "Persist Security Info=False"

  Set oRs = Server.CreateObject("ADODB.Recordset")
  oRs.CursorLocation = adUseClient
  oRs.CursorType = adOpenStatic 
  oRs.Open strSQL, oCnn, adOpenStatic, adLockBatchOptimistic

  ' Disconnect the recordset and return if to the caller.  The caller is responsible
  ' for cleaning up the recordset object.    
  Set oRs.ActiveConnection = Nothing
  Set DbRecordset = oRs

  oCnn.Close
  Set oCnn = Nothing
End Function


'======================================================================================

'======================================================================================
Function DbEncode(strSql)  
  strSql = Replace(strSql, "'", "''")
  DbEncode = strSql
End Function


</script>