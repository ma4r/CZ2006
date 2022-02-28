package Error;

import Tools.ErrorAlert;

////////////////////////////////////////////////////////////////////////////////////////////////////////
@SuppressWarnings("serial")
public class RequestError extends Exception {

	public RequestError() {
	}

	public RequestError(String arg0) {
		super(arg0);
		ErrorAlert.errorAlert(arg0);
	}

}
