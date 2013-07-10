package com.mac.android.goalmania.utils;

import android.os.AsyncTask;

import com.mac.android.goalmania.utils.Commander.Command;

public class GenericAsyncTaskCommande {

	

	public static class GenericAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	    private Command command;

	    public GenericAsyncTask(Command command) {
	        super();
	        this.command = command;
	    }

	    @Override
	    protected Result doInBackground(Params... params) {
	        // TODO your code
	        command.execute();
	        // TODO your code
	        return null;
	    }

	}
}
