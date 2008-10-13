package com.kkazmierczyk.freestyle;

import java.util.ArrayList;
import java.util.Collection;

/** 
 * Class to management of robots 
 *
 * @version $Id$
 */
public class Bots {
	
	/** List of all Bots */
	private Collection<Bot> bots = new ArrayList<Bot>(3);
	
	/** Returns bot name of given agent or <code>null</code> if any */
	public String getBotName(String agent) {
		for (Bot bot : bots) {
			if (bot.isBot(agent)) {
				return bot.getName();
			}
		}
		return null;
	}
	
	/** Adds default bots */
	public Bots() {
		bots.add(new Bot("Yahoo! Slurp", "Yahoo! Slurp"));
		bots.add(new Bot("Googlebot", "Googlebot"));
		bots.add(new Bot("msnbot", "msnbot"));
	}

	/**
	 * @return the bots
	 */
	public Collection<Bot> getBots() {
		return bots;
	}
}
