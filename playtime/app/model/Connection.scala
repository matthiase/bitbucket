package model

import com.netflix.astyanax.AstyanaxContext
import com.netflix.astyanax.AstyanaxContext._
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl
import com.netflix.astyanax.connectionpool._
import com.netflix.astyanax.connectionpool.impl.{ConnectionPoolConfigurationImpl, CountingConnectionPoolMonitor}
import com.netflix.astyanax.thrift.ThriftFamilyFactory

object Connection {
  val context = new AstyanaxContext.Builder()
    .forCluster("Test Cluster")
    .forKeyspace("Playtime")
    .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
      .setDiscoveryType(NodeDiscoveryType.NONE)
    )
    .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("DefaultConnectionPool")
      .setPort(9160)
      .setMaxConnsPerHost(1)
      .setSeeds("127.0.0.1:9160")
    )
    .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
    .buildKeyspace(ThriftFamilyFactory.getInstance())

  context.start
  val keyspace = context.getEntity()
}
