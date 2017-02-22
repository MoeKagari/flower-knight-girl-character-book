package patch.api;

import patch.api.getBook.GetBook;
import patch.api.getMaster.GetMaster;

public class ApiResponseFactory {

	public static ApiResponse get(String url) {
		if (url == null) return null;

		if (url.contains(GetMaster.key)) return new GetMaster();
		if (url.contains(GetBook.key)) return new GetBook();
		if (url.contains(Login.key)) return new Login();

		return null;
	}

}