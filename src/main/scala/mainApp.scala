
/*   *********************************************************************
        (c) Genetic Thought Software Inc.
     *********************************************************************
      Author: Moustafa Refaat
      email: MRefaat@GeneticThought.com
      **********************************************************************/


import com.geneticthought.Kafka.Administration.KafkaAdmin
import com.geneticthought.Utilities.{Helpers, Printer}
import org.slf4j.LoggerFactory

object mainApp extends App {

  val logger = LoggerFactory.getLogger("MainApp")
  logger.info("Starting")
  val props = Helpers.getProperties("environment.properties")
  Printer.printHeader
  Printer.printBoxedString(s"Connecting to server: " + props.getProperty("bootstrap.servers"), 1)
  val admin = new KafkaAdmin(props)
  Printer.printBoxedString(s"Connected to server: " + props.getProperty("bootstrap.servers"), 1)
  Controller.mainMenu(admin)
  Printer.printBoxedString(s"Disconnecting from server: " + props.getProperty("bootstrap.servers"), 1)
  admin.close
}
