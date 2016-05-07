package Interface;

import Project.SysApp;

@SuppressWarnings("serial")
public class ActionCancelledException extends Exception {
	public ActionCancelledException(SysApp sys) {
		super("ActionCancelled"); 
		sys.ui.cancel();
	}
}
