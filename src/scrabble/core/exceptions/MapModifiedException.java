package scrabble.core.exceptions;

/**
 * This exception will detect bug and crash the program. The exception will be
 * thrown when the user place a brick on an occupied position and the select a
 * brick which is not existing.
 * @author wxp
 * 
 */
public class MapModifiedException extends Exception {

	private static final long serialVersionUID = -6741083751730796644L;

	public MapModifiedException(String msg) {
		super(msg);
	}

}
