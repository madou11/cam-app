package com.mac.android.goalmania.utils;



public class Commander {
	
	public static interface Command {
	    public void execute();
	}
	
	public static final class MyWsCommand1 implements Command {
	    @Override
	    public void execute() {
	        // TODO your WS code 1
	    }
	}

	public static final class MyWsCommand2 implements Command {
	    @Override
	    public void execute() {
	        // TODO your WS code 2
	    }
	}
}
