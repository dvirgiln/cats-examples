

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

class Stack[T] {

  val MAX = 10
  private val buf = new ListBuffer[T]

  def push(o: T) {
    if (!full)
      buf.prepend(o)
    else
      throw new IllegalStateException("can't push onto a full stack")
  }

  def pop(): T = {
    if (!empty)
      buf.remove(0)
    else
      throw new IllegalStateException("can't pop an empty stack")
  }

  def peek: T = {
    if (!empty)
      buf(0)
    else
      throw new IllegalStateException("can't pop an empty stack")
  }

  def full: Boolean = buf.size == MAX
  def empty: Boolean = buf.size == 0
  def size = buf.size

  override def toString = buf.mkString("Stack(", ", ", ")")
}

trait StackBehaviors { this: FlatSpec with Matchers =>

  def nonEmptyStack(newStack: => Stack[Int], lastItemAdded: Int) {
    import org.scalactic.StringNormalizations._
    it should "be non-empty" in {
      assert(!newStack.empty)
    }

    it should "return the top item on peek" in {
      assert(newStack.peek === lastItemAdded)
    }

    it should "not remove the top item on peek" in {
      val stack = newStack
      val size = stack.size
      assert(stack.peek === lastItemAdded)
      assert(stack.size === size)
    }

    it should "remove the top item on pop" in {
      (Array("Doe", "Ray", "Me") should contain oneOf ("X", "RAY", "BEAM")) (after being lowerCased)
      val stack = newStack
      val size = stack.size
      assert(stack.pop === lastItemAdded)
      assert(stack.size === size - 1)
    }
  }

  def nonFullStack(newStack: => Stack[Int]) {

    it should "not be full" in {

      assert(!newStack.full)
    }

    it should "add to the top on push" in {
      val stack = newStack
      val size = stack.size
      stack.push(7)
      assert(stack.size === size + 1)
      assert(stack.peek === 7)
    }
  }
}


class SharedBehaviorExample extends FlatSpec with Matchers with StackBehaviors {

  // Stack fixture creation methods
  def emptyStack = new Stack[Int]

  def fullStack = {
    val stack = new Stack[Int]
    for (i <- 0 until stack.MAX)
      stack.push(i)
    stack
  }

  def stackWithOneItem = {
    val stack = new Stack[Int]
    stack.push(9)
    stack
  }

  def stackWithOneItemLessThanCapacity = {
    val stack = new Stack[Int]
    for (i <- 1 to 9)
      stack.push(i)
    stack
  }

  val lastValuePushed = 9

  "A Stack (when empty)" should "be empty" in {
    assert(emptyStack.empty)
  }

  it should "complain on peek" in {
    intercept[IllegalStateException] {
      emptyStack.peek
    }
  }

  it should "complain on pop" in {
    intercept[IllegalStateException] {
      emptyStack.pop
    }
  }

  "A Stack (with one item)" should behave like nonEmptyStack(stackWithOneItem, lastValuePushed)

  it should behave like nonFullStack(stackWithOneItem)

  "A Stack (with one item less than capacity)" should behave like nonEmptyStack(stackWithOneItemLessThanCapacity, lastValuePushed)

  it should behave like nonFullStack(stackWithOneItemLessThanCapacity)

  "A Stack (full)" should "be full" in {
    assert(fullStack.full)
  }

  it should behave like nonEmptyStack(fullStack, lastValuePushed)

  it should "complain on a push" in {
    intercept[IllegalStateException] {
      fullStack.push(10)
    }
  }
}