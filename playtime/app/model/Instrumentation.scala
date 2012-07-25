package model

import com.netflix.astyanax._
import com.netflix.astyanax.model.ColumnFamily
import com.netflix.astyanax.serializers.StringSerializer

object Instrumentation {

  val CF_INSTRUMENTATION = new ColumnFamily[String, String](
    "Instrumentations",
    StringSerializer.get,
    StringSerializer.get)

  def insert(rowKey:String, columnKey:String, value:String) = {
    val batch = Connection.keyspace.prepareMutationBatch
    batch.withRow(CF_INSTRUMENTATION, rowKey)
      .putColumn(columnKey, value, null)
    batch.execute
  }
}
