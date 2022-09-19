package config.exception;

/**
 * The Class DataBaseSourceException.
 * 
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
public class DataBaseSourceException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new data base source exception.
	 *
	 * @param msg the msg
	 */
	public DataBaseSourceException(String msg) {
		super(msg);
	}
}
