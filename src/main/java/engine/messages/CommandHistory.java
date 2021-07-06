package engine.messages;

import java.util.Stack;

final class CommandHistory {

  private final Stack<Command> history = new Stack<>();

  void push(Command command) {
    history.push(command);
  }

  Command pop() {
    return history.pop();
  }

  boolean isEmpty() { return history.isEmpty(); }

}
