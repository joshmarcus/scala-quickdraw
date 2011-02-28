/*

To try out quickdraw, first see how slow it normally is to run a scala script:

# time scala helloworld.scala

... and then run

# quickdraw helloworld.scala helloworld

... and then time the new execution:

# time ./quickdraw

*/

object Main {
  def main( args:Array[String] ) {
    println("hello world ... huzzah")
  }
}
