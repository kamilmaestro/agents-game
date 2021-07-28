package engine;

import engine.messages.MessengerInvoker;
import game.Game;
import game.GameFacade;
import game.Player;
import game.voting.VoteResult;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static engine.ActionType.*;
import static engine.messages.MessengerInvoker.MessageType.BROADCAST;
import static engine.messages.MessengerInvoker.MessageType.SPECIFIED_RECIPIENT;

final class Engine {

  private final GameFacade gameFacade;
  private final ActionsHandler actionsHandler;
  private final MessengerInvoker messengerInvoker;
  private ActionType currentAction;

  Engine(GameFacade gameFacade) {
    this.currentAction = PLAYERS_MARKING_AS_READY;
    this.gameFacade = gameFacade;
    this.actionsHandler = new ActionsHandler(gameFacade);
    messengerInvoker = new MessengerInvoker();
  }

  void handle(String message, UUID uuid) {
    EngineActionResult actionResult = null;
    if (message.equals("t") || message.equals("f")) {
      actionResult = actionsHandler.vote(message, uuid);
    } else {
      actionResult = actionsHandler.choose(message, uuid);
    }
    if (actionResult != null) {
      actionResult.messages
          .forEach(this::sendMessage);
    }
  }

  private void sendMessage(Message messageToSend) {
    messageToSend.getSpecifiedRecipient().ifPresentOrElse(
        recipientUuid -> messengerInvoker.sendMessage(messageToSend.message, recipientUuid, SPECIFIED_RECIPIENT),
        () -> messengerInvoker.sendMessage(messageToSend.message, null, BROADCAST)
    );
  }

}
