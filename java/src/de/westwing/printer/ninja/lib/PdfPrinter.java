package de.westwing.printer.ninja.lib;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import javax.print.PrintService;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;

import com.sun.pdfview.PDFFile;
import de.westwing.printer.ninja.lib.document.DocumentInterface;
import de.westwing.printer.ninja.lib.message.JsonPrintMessageInterface;
import java.awt.print.Paper;

/**
 * 
 * @author Omar Tchokhani <omar.tchokhani@westwing.de>
 */
public class PdfPrinter extends AbstractPrinter {

	protected PrinterJob printerJob;

	protected PDFFileFactory pdfFileFactory;

	protected PDFPrintA4PageFactory pdfPrintA4PageFactory;

	/**
	 * @author Omar Tchokhani <omar.tchokhani@westwing.de>
	 * @param printService
	 */
	public PdfPrinter(PrintService printService, PDFFileFactory pdfFileFactory, PDFPrintA4PageFactory pdfPrintA4PageFactory) {
		this(printService, PrinterJob.getPrinterJob(), pdfFileFactory, pdfPrintA4PageFactory);
	}

	public MediaSizeName getMediaSize(String mediaSize) {
		if (mediaSize != null) {
			if (mediaSize.equalsIgnoreCase("ISO_A0")) {
				return MediaSizeName.ISO_A0;
			} else if (mediaSize.equalsIgnoreCase("ISO_A1")) {
				return MediaSizeName.ISO_A1;
			} else if (mediaSize.equalsIgnoreCase("ISO_A2")) {
				return MediaSizeName.ISO_A2;
			} else if (mediaSize.equalsIgnoreCase("ISO_A3")) {
				return MediaSizeName.ISO_A3;
			} else if (mediaSize.equalsIgnoreCase("ISO_A4")) {
				return MediaSizeName.ISO_A4;
			} else if (mediaSize.equalsIgnoreCase("ISO_A5")) {
				return MediaSizeName.ISO_A5;
			} else if (mediaSize.equalsIgnoreCase("ISO_A6")) {
				return MediaSizeName.ISO_A6;
			} else if (mediaSize.equalsIgnoreCase("ISO_A7")) {
				return MediaSizeName.ISO_A7;
			} else if (mediaSize.equalsIgnoreCase("ISO_A8")) {
				return MediaSizeName.ISO_A8;
			} else if (mediaSize.equalsIgnoreCase("ISO_A9")) {
				return MediaSizeName.ISO_A9;
			} else if (mediaSize.equalsIgnoreCase("ISO_B0")) {
				return MediaSizeName.ISO_B0;
			} else if (mediaSize.equalsIgnoreCase("ISO_B1")) {
				return MediaSizeName.ISO_B1;
			} else if (mediaSize.equalsIgnoreCase("ISO_B2")) {
				return MediaSizeName.ISO_B2;
			} else if (mediaSize.equalsIgnoreCase("ISO_B3")) {
				return MediaSizeName.ISO_B3;
			} else if (mediaSize.equalsIgnoreCase("ISO_B4")) {
				return MediaSizeName.ISO_B4;
			} else if (mediaSize.equalsIgnoreCase("ISO_B5")) {
				return MediaSizeName.ISO_B5;
			} else if (mediaSize.equalsIgnoreCase("ISO_B6")) {
				return MediaSizeName.ISO_B6;
			} else if (mediaSize.equalsIgnoreCase("ISO_B7")) {
				return MediaSizeName.ISO_B7;
			} else if (mediaSize.equalsIgnoreCase("ISO_B8")) {
				return MediaSizeName.ISO_B8;
			} else if (mediaSize.equalsIgnoreCase("ISO_B9")) {
				return MediaSizeName.ISO_B9;
			} else if (mediaSize.equalsIgnoreCase("ISO_B10")) {
				return MediaSizeName.ISO_B10;
			} else if (mediaSize.equalsIgnoreCase("NA_LEGAL")) {
				return MediaSizeName.NA_LEGAL;
			} else if (mediaSize.equalsIgnoreCase("NA_LETTER")) {
				return MediaSizeName.NA_LETTER;
			}
		}

		return MediaSizeName.ISO_A4;
	}

	/**
	 *
	 * @param printService
	 * @param printerJob
	 * @param pdfFileFactory
	 * @param pdfPrintA4PageFactory
	 */
	public PdfPrinter(PrintService printService, PrinterJob printerJob, PDFFileFactory pdfFileFactory, PDFPrintA4PageFactory pdfPrintA4PageFactory) {
		setPrintService(printService);
		setPrinterJob(printerJob);
		setPdfFileFactory(pdfFileFactory);
		setPdfPrintA4PageFactory(pdfPrintA4PageFactory);
	}

	/**
	 * @author Omar Tchokhani <omar.tchokhani@westwing.de>
	 */
	@Override
	public void print(JsonPrintMessageInterface printMessage) throws PrintException {
		if (this.documentsQueue.isEmpty()) {
			throw new PrintException("Printer queue is empty.");
		}

		String paperSize = printMessage.getMediaSizeName();
		String orientation = printMessage.getOrientationRequested();

		try {
			this.printerJob.setPrintService(this.getPrintService());
			PageFormat pageFormat = this.printerJob.defaultPage();
			Paper paper = pageFormat.getPaper();

			MediaSize media = MediaSize.getMediaSizeForName(getMediaSize(paperSize));
			paper.setSize(media.getX(MediaSize.INCH) * 71, media.getY(MediaSize.INCH) * 71);

			if ("LANDSCAPE".equals(orientation)) {
				pageFormat.setOrientation(PageFormat.LANDSCAPE);
			} else if ("PORTRAIT".equals(orientation)) {
				pageFormat.setOrientation(PageFormat.PORTRAIT);
			}

			paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());

			pageFormat = this.printerJob.validatePage(pageFormat);
			pageFormat.setPaper(paper);
			for (DocumentInterface document : this.documentsQueue) {
				PDFFile pdfFile = this.getPdfFileFactory().factory(document);
				PDFPrintA4Page pages = this.getPdfPrintA4PageFactory().factory(pdfFile);

				Book book = new Book();
				book.append(pages, pageFormat, pdfFile.getNumPages());

				this.printerJob.setPageable(book);
				this.printerJob.print();
			}

		} catch (Exception ex) {
			throw new PrintException(ex.getMessage(), ex);
		}
	}

	/**
	 * @author Omar Tchokhani <omar.tchokhani@westwing.de>
	 * @param printerJob
	 */
	public void setPrinterJob(PrinterJob printerJob) {
		this.printerJob = printerJob;
	}

	/**
	 * @author Omar Tchokhani <omar.tchokhani@westwing.de>
	 * @return The printer job to be used.
	 */
	public PrinterJob getPrinterJob() {
		return printerJob;
	}

	/**
	 *
	 * @return PDFFileFactory
	 */
	public PDFFileFactory getPdfFileFactory() {
		return pdfFileFactory;
	}

	/**
	 *
	 * @param pdfFileFactory
	 */
	public void setPdfFileFactory(PDFFileFactory pdfFileFactory) {
		this.pdfFileFactory = pdfFileFactory;
	}

	/**
	 *
	 * @return PDFPrintA4PageFactory
	 */
	public PDFPrintA4PageFactory getPdfPrintA4PageFactory() {
		return pdfPrintA4PageFactory;
	}

	/**
	 *
	 * @param pdfPrintA4PageFactory
	 */
	public void setPdfPrintA4PageFactory(PDFPrintA4PageFactory pdfPrintA4PageFactory) {
		this.pdfPrintA4PageFactory = pdfPrintA4PageFactory;
	}
}
