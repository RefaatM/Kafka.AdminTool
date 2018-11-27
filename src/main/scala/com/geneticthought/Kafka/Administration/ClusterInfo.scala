/*   *********************************************************************
        (c) Genetic Thought Software Inc.
     *********************************************************************
      Author: Moustafa Refaat
      email: MRefaat@GeneticThought.com
      **********************************************************************/
package com.geneticthought.Kafka.Administration

import org.apache.kafka.clients.admin._
import org.apache.kafka.common.Node

import scala.collection.JavaConverters

class ClusterInfo(val id: String, val controller: NodeInfo, val nodes: List[NodeInfo]) {
  def this(cluster: DescribeClusterResult) =
    this(cluster.clusterId().get(), new NodeInfo(cluster.controller().get()),
      helpers.convertNodesListToNodeInfoList(JavaConverters.asScalaIterator(cluster.nodes().get().iterator()).toList))


  override def toString: String = {
    val str = new StringBuilder
    str.append(s"Cluster $id with Controller: $controller with Nodes \n")
    for (node <- nodes) str.append(node + "\n")
    return str.mkString
  }
}

class NodeInfo(val id: Int, val host: String, val port: Int, val rack: String = "No Rack") {
  def this(node: Node) = {
    this(node.id(), node.host(), node.port(), if (node.hasRack()) node.rack() else "No Rack")
  }

  override def toString: String = s"Node: $id Host:$host Port:$port Rack:$rack"
}

object helpers {
  def convertNodesListToNodeInfoList(nodes: List[Node]): List[NodeInfo] = nodes.map(a => new NodeInfo(a))
}