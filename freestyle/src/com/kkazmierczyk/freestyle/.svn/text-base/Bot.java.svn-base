package com.kkazmierczyk.freestyle;

/**
 *
 * Class representing one bot
 *
 * @version $Id$
 */
public class Bot {

	/** Part of the text which browser agent of bot contains */ 
	private final String agent;
	
	/** Name of bot */
	private final String name;

	public Bot(String agent, String name) {
		this.agent = agent;
		this.name = name;
	}
	
	/** Checks if this bot is represented by given agent */
	public boolean isBot(String agent) {
		return agent.contains(this.agent);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getAgent() {
		return agent;
	}
}
