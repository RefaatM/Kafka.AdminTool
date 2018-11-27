/*   *********************************************************************
        (c) Genetic Thought Software Inc.
     *********************************************************************
      Author: Moustafa Refaat
      email: MRefaat@GeneticThought.com
      **********************************************************************/


package com.geneticthought.Utilities


object Printer {


  val width = 80
  val indents = List(4, 6, 8, 10, 12)
  val astrikLine = "*" * width
  val astrikstart = "*" * 5

  def printBoxedString(str: String, indent: Int): Unit = printBoxedString(str, astrikstart, width, indents(indent))

  def printHeader: Unit = {

    val copyLine = boxString("(c) Genetic Thought Software Inc 2018", astrikstart, width, indents(0))
    val progName = boxString("Kafka Admin Tool", astrikstart, width, indents(0))
    println(astrikLine)
    println(progName)
    println(copyLine)
    println(astrikLine)
    printBoxedString("Note: set Kafak environment in environment.properties", astrikstart, width, indents(0))


  }

  def printCommandsMenu(): Unit = {
    println(astrikLine)
    printBoxedString("Commands:", astrikstart, width, indents(0))
    printBoxedString("1- Describe Cluster", astrikstart, width, indents(1))
    printBoxedString("2- Topics", astrikstart, width, indents(1))
    printBoxedString("3- Consumers", astrikstart, width, indents(1))
    printBoxedString("4- Exit", astrikstart, width, indents(1))
    println(astrikLine)
  }

  def printBoxedString(str: String, border: String, lineWidth: Int, indent: Int): Unit = {
    println(boxString(str, border, lineWidth, indent))
  }

  def boxString(str: String, border: String, lineWidth: Int, indent: Int): String = {
    val trailerSpace = " " * (lineWidth - (str.length + border.length * 2 + indent))
    val whiteIndent = " " * indent
    s"$border$whiteIndent$str$trailerSpace$border"
  }

  def printTopicsMenu(): Unit = {
    println(astrikLine)
    printBoxedString("Topics Commands:", astrikstart, width, indents(0))
    printBoxedString("1- List Topics", astrikstart, width, indents(1))
    printBoxedString("2- Describe Topics", astrikstart, width, indents(1))
    printBoxedString("3- Create Topics", astrikstart, width, indents(1))
    printBoxedString("4- Delete Topic", astrikstart, width, indents(1))
    printBoxedString("5- Increase Topic Partitions", astrikstart, width, indents(1))
    printBoxedString("6- Return", astrikstart, width, indents(1))
    println(astrikLine)
  }

  def printConsumersMenu(): Unit = {
    println(astrikLine)
    printBoxedString("Consumer Commands:", astrikstart, width, indents(0))
    printBoxedString("1- List Consumer Groups", astrikstart, width, indents(1))
    printBoxedString("2- Return", astrikstart, width, indents(1))
    println(astrikLine)
  }



}