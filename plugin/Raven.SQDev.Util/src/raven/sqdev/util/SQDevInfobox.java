package raven.sqdev.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * A class used to inform the user about something that happened in the SQDev
 * plugin. This will bring a MessageBox up that is scheduled in the UI-thread
 * automatically
 * 
 * @author Raven
 * 		
 */
public class SQDevInfobox {
	
	/**
	 * Defining the style of the infobox
	 */
	protected int style = SWT.NONE;
	
	protected String message;
	
	/**
	 * Creates an instance of this Infobox
	 * 
	 * @param message
	 *            The message to display
	 */
	public SQDevInfobox(String message) {
		this.message = message;
	}
	
	/**
	 * Creates an instance of this Infobox
	 * 
	 * @param message
	 *            The message to display
	 * @param style
	 *            The style of the Infobox. Can be any of
	 *            {@linkplain MessageBox}'s styles
	 */
	public SQDevInfobox(String message, int style) {
		this.message = message;
		this.style = style;
	}
	
	/**
	 * Makes the dialog visible and brings it to the front of the display.
	 * 
	 * @return the ID of the button that was selected to dismiss the message box
	 *         (e.g. SWT.OK, SWT.CANCEL, etc.)
	 */
	public int open() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		
		AtomicInteger result = new AtomicInteger();
		
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox box = new MessageBox(Display.getCurrent().getActiveShell(), style);
				
				box.setText("SQDev Info");
				box.setMessage(message);
				
				result.set(box.open());
				
				Shell active = Display.getCurrent().getActiveShell();
				
				if (!active
						.equals(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell())) {
					active.dispose();
				}
			}
		});
		
		
		return result.get();
	}
	
}
