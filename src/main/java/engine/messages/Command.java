package engine.messages;

abstract class Command {

  Messenger messenger;
  private String backup;

  Command(Messenger messenger) {
    this.messenger = messenger;
  }

  void withBackup() {
    this.backup = this.messenger.getMessage();
  }

  void undo() {
    this.messenger.setMessage(this.backup);
  }

  abstract boolean execute();

}
