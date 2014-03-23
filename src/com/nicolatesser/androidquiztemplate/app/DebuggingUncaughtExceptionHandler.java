package com.nicolatesser.androidquiztemplate.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

public class DebuggingUncaughtExceptionHandler implements UncaughtExceptionHandler {

	public final static String TAG = "DebuggingUncaughtExceptionHandler";
	
	public static String APPLICATION_NAME= "Application Name";
	
	public static String EMAIL_ADDRESS= "ghiottolino+androidquiztemplate@gmail.com";
	

	private Context ctx;
	private Thread.UncaughtExceptionHandler previousHandler;

	public DebuggingUncaughtExceptionHandler(Context ctx,
			UncaughtExceptionHandler previousHandler) {
		this.ctx = ctx;
		this.previousHandler = previousHandler;
	}

	public void uncaughtException(final Thread t, final Throwable e) {

		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		String stacktrace = result.toString();
		String logs = "";
		String emailText = stacktrace + "\n\n\n" + logs;
		printWriter.close();

		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		String timeStamp = format.format(new Date());

		final Intent emailIntent = new Intent(
				android.content.Intent.ACTION_SEND);

		emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				new String[] { EMAIL_ADDRESS });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"Crash Report for "+ APPLICATION_NAME + timeStamp);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailText);

		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				// Toast.makeText(ctx, "TOAST", Toast.LENGTH_LONG)
				// .show();
				try {
					ctx.startActivity(emailIntent);
				} catch (ActivityNotFoundException e1) {
					previousHandler.uncaughtException(t, e1);
				}
				previousHandler.uncaughtException(t, e);
				Looper.loop();
			}
		}.start();

	}

	public final static String LINE_SEPARATOR = System
			.getProperty("line.separator");
	public final static int LOG_MAX_SIZE = 4000;

	protected StringBuilder readLogs() {
		final StringBuilder log = new StringBuilder();
		try {
			ArrayList<String> commandLine = new ArrayList<String>();
			commandLine.add("logcat");
			commandLine.add("-d");
			// commandLine.add("-t");
			// commandLine.add("-500");

			Process process = Runtime.getRuntime().exec(
					commandLine.toArray(new String[0]));
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				log.append(line);
				log.append(LINE_SEPARATOR);

				// keeping the log under a certain size
				if (log.length() > LOG_MAX_SIZE) {
					log.delete(0, log.length() - LOG_MAX_SIZE);
				}

			}
		} catch (Exception e) {
			Log.e(TAG, "CollectLogTask failed", e);//$NON-NLS-1$
		}

		Log.v(TAG, "Log size=" + log.length());

		return log;
	}

}