/** Created: Thu 10 Jul 2014 10:55 am
 * Modified: Thu 10 Jul 2014 10:55 am
 * @author Josh Wainwright
 * File name : FileDescriptor.java
 */
package utils;

public class FileDescriptor {

	private int colX;
	private int colY;
	private String separator;

	public FileDescriptor(int colX, int colY, String separator) {
		this.colX = colX;
		this.colY = colY;
		this.separator = separator;
	}

	public void setColX(int colX) {
		this.colX = colX;
	}

	public int getColX() {
		return colX;
	}

	public void setColY(int colY) {
		this.colY = colY;
	}

	public int getColY() {
		return colY;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getSeparator() {
		return separator;
	}

}
