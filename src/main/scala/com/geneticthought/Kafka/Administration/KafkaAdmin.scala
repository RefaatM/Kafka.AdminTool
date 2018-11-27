/*   *********************************************************************
        (c) Genetic Thought Software Inc.
     *********************************************************************
      Author: Moustafa Refaat
      email: MRefaat@GeneticThought.com
      **********************************************************************/

package com.geneticthought.Kafka.Administration

import java.util.Properties

import org.apache.kafka.clients.admin._

import scala.collection.JavaConverters

class KafkaAdmin(val props: Properties) {


  private val client: AdminClient = AdminClient.create(props)

  def close: Unit = this.client.close()


  /* Cluster operations */
  def descibeCluster: ClusterInfo = new ClusterInfo(this.client.describeCluster())


  /* Topics region */
  def getTopics: Set[String] = JavaConverters.asScalaSet(this.client.listTopics.names.get()).toSet

  def createTopics(topics: List[(String, Int, Short)]): Unit = {
    val kafkaTopics = scala.collection.mutable.ArrayBuffer[NewTopic]()
    for (topic <- topics) kafkaTopics += new NewTopic(topic._1, topic._2, topic._3)
    val opresult = this.client.createTopics(JavaConverters.asJavaCollection(kafkaTopics))
    opresult.all()
  }

  def deleteTopics(topics: List[String]): Unit = {
    val result = this.client.deleteTopics(JavaConverters.asJavaCollection(topics))
    result.all()
  }

  def describeTopics(topics: List[String]): Iterable[TopicDescription] = {
    val describeResult = this.client.describeTopics(JavaConverters.asJavaCollection(topics))
    val topicsdesc = describeResult.all().get()
    JavaConverters.collectionAsScalaIterable(topicsdesc.values())
  }

  def increasePartitions(partitions: Map[String, Int]): Unit = {

    val partionsRequest: scala.collection.mutable.ListMap[String, NewPartitions] =
      new scala.collection.mutable.ListMap[String, NewPartitions]()
    for ((k, v) <- partitions) {
      partionsRequest += (k -> NewPartitions.increaseTo(v))
    }
    val requestReslut = this.client.createPartitions(JavaConverters.mutableMapAsJavaMap(partionsRequest))
    requestReslut.all()
  }

  /* Configuration region */
  /* Records region */
  /* consumers region */
  def listConsumers: List[String] = {
    val consumers = this.client.listConsumerGroups().all().get().toArray()
    consumers.map(x => x.toString).toList
  }

}
