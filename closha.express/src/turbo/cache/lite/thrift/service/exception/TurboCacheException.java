package turbo.cache.lite.thrift.service.exception;

public class TurboCacheException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "turbo cache not loaded..";
	}

}
