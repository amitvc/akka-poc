package com.catalina.akka;

import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.SupervisorStrategyConfigurator;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

public class RootSupervisionStrategy implements SupervisorStrategyConfigurator {

	private static final SupervisorStrategy supervisorStrategy =
			new OneForOneStrategy(10, Duration.create(1, "minute"),
					new Function<Throwable, Directive> () {

						@Override
						public Directive apply(Throwable arg0) throws Exception {
							return SupervisorStrategy.resume();
						}
				
			});
	
	@Override
	public SupervisorStrategy create() {
		return supervisorStrategy;
	}

}