package project;

public class ActionCancelledException extends Exception {
	public ActionCancelledException(SysApp sys) {
		super("ActionCancelled");
		sys.ui.cancel();
	}
}
