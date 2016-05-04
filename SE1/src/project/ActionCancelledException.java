package project;

@SuppressWarnings("serial")
public class ActionCancelledException extends Exception {
	public ActionCancelledException(SysApp sys) {
		super("ActionCancelled");
		sys.ui.cancel();
	}
}
