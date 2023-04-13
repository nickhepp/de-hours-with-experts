package com.labs1904

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.immutable.HashMap
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * An ingredient has an amount and a description.
 * @param amount For example, "1 cup"
 * @param description For example, "butter"
 */
case class Ingredient(amount: String, description: String)

object SecretRecipeDecoder {
  val ENCODING: Map[String, String] = HashMap[String, String](
    "y" -> "a",
    "h" -> "b",
    "v" -> "c",
    "x" -> "d",
    "k" -> "e",
    "p" -> "f",
    "z" -> "g",
    "s" -> "h",
    "a" -> "i",
    "b" -> "j",
    "e" -> "k",
    "w" -> "l",
    "u" -> "m",
    "q" -> "n",
    "n" -> "o",
    "l" -> "p",
    "m" -> "q",
    "f" -> "r",
    "o" -> "s",
    "i" -> "t",
    "g" -> "u",
    "j" -> "v",
    "t" -> "w",
    "d" -> "x",
    "r" -> "y",
    "c" -> "z",
    "3" -> "0",
    "8" -> "1",
    "4" -> "2",
    "0" -> "3",
    "2" -> "4",
    "7" -> "5",
    "5" -> "6",
    "9" -> "7",
    "1" -> "8",
    "6" -> "9"
  )

  /**
   * Given a string named str, use the Caeser encoding above to return the decoded string.
   * @param str A caesar-encoded string.
   * @return
   */
  def decodeString(str: String): String = {
    val decodedStr = str.map(c => ENCODING.getOrElse(c.toString, c.toString)).mkString
    decodedStr
  }

  /**
   * Given an ingredient, decode the amount and description, and return a new Ingredient
   * @param line An encoded ingredient.
   * @return
   */
  def decodeIngredient(line: String): Ingredient = {
    val decodedLine = decodeString(line)
    val LINE_SPLITTER = '#'
    val qtyItemTuple = decodedLine.split(LINE_SPLITTER)
    if (qtyItemTuple.length != 2)
      throw new IllegalArgumentException(s"Value in line '$line' is missing the line splitter '$LINE_SPLITTER'.")

    Ingredient(qtyItemTuple(0), qtyItemTuple(1))
  }


  def readFile(filename: String): Seq[String] = {
    val bufferedSource = io.Source.fromFile(filename)
    val lines = (for (line <- bufferedSource.getLines()) yield line).toList
    bufferedSource.close
    lines
  }

  def writeFile(filename: String, lines: Seq[String]): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    for (line <- lines) {
      bw.write(line)
    }
    bw.close()
  }

  /**
   * A program that decodes a secret recipe
   * @param args
   */
  def main(args: Array[String]): Unit = {

    val fileLines = readFile("C:\\git_src\\de-hours-with-experts\\scala\\target\\classes\\secret_recipe.txt")
    val decodedFileLines: ListBuffer[String] = ListBuffer[String]("amount, description\r\n")

    for (fileLine <- fileLines)
      {
        val ingredient: Ingredient = decodeIngredient(fileLine)
        decodedFileLines += s"${ingredient.amount}, ${ingredient.description}\r\n"
      }

    writeFile("C:\\git_src\\de-hours-with-experts\\scala\\target\\classes\\decoded_recipe.txt", decodedFileLines)
  }
}
