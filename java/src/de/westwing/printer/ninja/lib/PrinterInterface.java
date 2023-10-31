package de.westwing.printer.ninja.lib;

import de.westwing.printer.ninja.lib.document.DocumentInterface;
import de.westwing.printer.ninja.lib.message.JsonPrintMessageInterface;

/**
 * 
 * @author Omar Tchokhani <omar.tchokhani@westwing.de>
 *
 */
public interface PrinterInterface {
	/**
	 * @throws PrintException
	 */
	public void print(JsonPrintMessageInterface printMessage) throws PrintException;

	/**
	 * @param document
	 */
	public PrinterInterface enqueue(DocumentInterface document);

	/**
	 * @param document
	 */
	public DocumentInterface dequeue(DocumentInterface document);
}
