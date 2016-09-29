package com.example.akka;


import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.example.akka.actors.Commencer;

public class Main {

  public static void main(String[] args) {
    ActorSystem actorSystem = ActorSystem.create("HelloWorldActorSystem");
    ActorRef commencerActorReference = actorSystem.actorOf(Props.create(Commencer.class), "commencer");
    actorSystem.actorOf(Props.create(Terminator.class, commencerActorReference), "terminator");
  }

  public static class Terminator extends AbstractLoggingActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorRef actorRef;

    public Terminator(final ActorRef actorRef) {
      this.actorRef = actorRef;
      getContext().watch(actorRef);
      log.debug("Terminator Actor has been set to watch {}", actorRef.path());
      receive(ReceiveBuilder.
          match(Terminated.class, t -> {
            log.debug("Terminator Actor: {} has terminated, shutting down system", actorRef.path());
            context().system().terminate();
          }).build());
    }
  }

}
