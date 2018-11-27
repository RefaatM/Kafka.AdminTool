
/*   *********************************************************************
        (c) Genetic Thought Software Inc.
     *********************************************************************
      Author: Moustafa Refaat
      email: MRefaat@GeneticThought.com
      **********************************************************************/

import com.geneticthought.Kafka.Administration.KafkaAdmin
import com.geneticthought.Utilities.{Helpers, Printer}

import scala.io.StdIn

object Controller {


  def mainMenu(admin: KafkaAdmin): Unit = menu(4, admin, Printer.printCommandsMenu)(mainMenuCommands)

  def menu(exCommand: Int, admin: KafkaAdmin, mn: () => Unit)(cmd: (Int, KafkaAdmin) => Unit): Unit = {
    var command: Int = 0
    while (command != exCommand) {
      mn()
      Printer.printBoxedString("Enter selection: ", 2)
      var str = StdIn.readLine()
      command = Helpers.toInt(str)
      cmd(command, admin)
    }
  }

  def mainMenuCommands(cmd: Int, admin: KafkaAdmin): Unit = {
    cmd match {
      case 1 => describeCluster(admin)
      case 2 => TopicsCommand(admin)
      case 3 => ConsumerCommands(admin)
      case 4 => Printer.printBoxedString("Exiting ", 2)
      case _ => Printer.printBoxedString("Invalid selection: ", 2)
    }
  }

  def describeCluster(admin: KafkaAdmin): Unit = {
    val cluster = admin.descibeCluster
    println(cluster.toString)
  }

  def TopicsCommand(admin: KafkaAdmin): Unit = menu(6, admin, Printer.printTopicsMenu)(topicsMenuCommands)

  def topicsMenuCommands(cmd: Int, admin: KafkaAdmin): Unit = {
    cmd match {
      case 1 => listTopics(admin)
      case 2 => describeTopics(admin)
      case 3 => createTopics(admin)
      case 4 => deleteTopics(admin)
      case 5 => increaseTopicPartations(admin)
      case 6 => Printer.printBoxedString("Return to main Menu ", 2)
      case _ => Printer.printBoxedString("Invalid selection: ", 2)
    }
  }

  def listTopics(admin: KafkaAdmin): Unit = {
    val topics = admin.getTopics
    if (topics.size > 0) Printer.printBoxedString("Topics in this cluster are:", 1)
    else Printer.printBoxedString("No Topics in this cluster defined yet", 1)
    for (x <- admin.getTopics) Printer.printBoxedString(s"Topic: $x", 2)
  }

  def describeTopics(admin: KafkaAdmin): Unit = {
    val topics = getTopics
    if (topics.size <= 0) {
      Printer.printBoxedString(s"No Topics to Describe:$topics ", 2)
      return
    }
    Printer.printBoxedString(s"Describe Topics:$topics ", 2)

    val tpdes = admin.describeTopics(topics)
    for (tpd <- tpdes)
      Printer.printBoxedString(tpd.toString, 4)
    Printer.printBoxedString(s"Describe Topics:$topics  Done", 2)
  }

  def createTopics(admin: KafkaAdmin): Unit = {
    val topics = getTopics.map(x => (x, 1, 1.toShort)).toList
    if (topics.size <= 0) {
      Printer.printBoxedString(s"No Topics to Create:$topics ", 2)
      return
    }
    admin.createTopics(topics)

    Printer.printBoxedString(s"Created Topics: ", 2)
    for (t <- topics) Printer.printBoxedString(s"(topicName,Partions,Replication):$t ", 4)

  }

  def deleteTopics(admin: KafkaAdmin): Unit = {
    val topics = getTopics
    if (topics.size <= 0) {
      Printer.printBoxedString(s"No Topics to Delete:$topics ", 2)
      return
    }
    admin.deleteTopics(topics)
    Printer.printBoxedString(s"Deleted Topics:$topics ", 2)
  }

  def increaseTopicPartations(admin: KafkaAdmin): Unit = {
    val topics = getTopics
    if (topics.size <= 0) {
      Printer.printBoxedString(s"No Topics to Increse Partitions:$topics ", 2)
      return
    }
    Printer.printBoxedString("Enter Total Patitions No: ", 2)
    var str = StdIn.readLine()
    val partNo = Helpers.toInt(str)
    if (partNo <= 1) {
      Printer.printBoxedString(s"Partitions Number has to be ?> 1 you entered :$partNo ", 2)
      return
    }
    val topicsIncrease: Map[String, Int] = topics.map(x => x -> partNo).toMap
    admin.increasePartitions(topicsIncrease)
    Printer.printBoxedString(s"Increased Topics $topics partitions to :$partNo ", 2)
  }

  def getTopics: List[String] = {
    var cont = true
    var result: List[String] = null
    do
      try {
        Printer.printBoxedString("Enter Topics names seperated by comma", 1)
        val input = StdIn.readLine() + ","
        result = input.split(",").toList
        cont = false
      }
      catch {
        case _ => Printer.printBoxedString("Invalid Topics List", 2)

      }
    while (cont)

    result
  }

  def ConsumerCommands(admin: KafkaAdmin): Unit = menu(2, admin, Printer.printConsumersMenu)(consumersMenuCommands)

  def consumersMenuCommands(cmd: Int, admin: KafkaAdmin): Unit = {
    cmd match {
      case 1 => listConsumers(admin)
      case 2 => Printer.printBoxedString("Return to main Menu ", 2)
      case _ => Printer.printBoxedString("Invalid selection: ", 2)
    }
  }

  def listConsumers(admin: KafkaAdmin): Unit = {

    val consumers = admin.listConsumers
    Printer.printBoxedString(s"Consumers List: ", 2)
    consumers.foreach(x => Printer.printBoxedString(x, 4))
    Printer.printBoxedString(s"end of Consumers List ", 2)
  }

}
