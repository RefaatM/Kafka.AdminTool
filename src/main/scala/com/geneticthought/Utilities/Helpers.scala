/*   *********************************************************************
        (c) Genetic Thought Software Inc.
     *********************************************************************
      Author: Moustafa Refaat
      email: MRefaat@GeneticThought.com
      **********************************************************************/
package com.geneticthought.Utilities

import java.io.{FileInputStream, InputStream}
import java.util.Properties

object Helpers {

  def getProperties(filePath: String): Properties = {
    val prop: Properties = new Properties()
    val input: InputStream = new FileInputStream(filePath)
    prop.load(input)
    input.close()
    return prop
  }

  def toInt(s: String): Int = {
    try {
      s.toInt
    } catch {
      case e: Exception => 0
    }
  }
}
