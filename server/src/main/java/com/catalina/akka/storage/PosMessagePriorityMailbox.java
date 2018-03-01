package com.catalina.akka.storage;

import java.util.Comparator;

import akka.dispatch.Envelope;
import akka.dispatch.PriorityGenerator;
import akka.dispatch.UnboundedPriorityMailbox;

public class PosMessagePriorityMailbox extends UnboundedPriorityMailbox {

	public PosMessagePriorityMailbox(Comparator<Envelope> cmp) {
		super(cmp);
	}
}