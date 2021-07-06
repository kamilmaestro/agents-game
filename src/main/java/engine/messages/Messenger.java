package engine.messages;

import engine.UserThread;

import java.util.UUID;

import static engine.Server.getUserThreads;

class Messenger {

  private UUID userUuid;
  private String message;
  private boolean excluding = false;

  void sendMessage() {
    if (!message.isBlank()) {
      for (UserThread thread : getUserThreads()) {
        if (userUuid == null || isForSpecifiedUser(thread) || isForExcludedUser(thread)) {
          thread.sendMessage(message);
        }
      }
    }
  }

  private boolean isForSpecifiedUser(UserThread thread) {
    return !excluding && thread.uuid.equals(userUuid);
  }

  private boolean isForExcludedUser(UserThread thread) {
    return excluding && userUuid != null && !thread.uuid.equals(userUuid);
  }

  void setMessage(String message) {
    this.message = message;
  }

  void withoutRecipient() {
    this.userUuid = null;
  }

  void withUser(UUID userUuid, boolean excluding) {
    this.userUuid = userUuid;
    this.excluding = excluding;
  }

  String getMessage() {
    return message;
  }

}
