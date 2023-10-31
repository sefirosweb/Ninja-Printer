Source
======

- NinjaPrinter.jar
Contains all business logic needed.

- lib/jzebra.jar
Need to be added for the project. Contains the printing descriptor for zebra printing.
Source: https://code.google.com/p/jzebra/downloads/list

- lib/PDFRenderer-0.9.0.jar
Need to be added for the project. Contains the code which is able render PDF into Graphics2D 
which can then be used to print.
Source: https://github.com/katjas/PDFrenderer

Distribution
============

To run the project from the command line, go to the dist folder and
type the following:

  java -jar NinjaPrinter.jar [--debug=on]

the program expects the following JSON message:

```JSON
{
  "printerType=":"PDF|Label",
  "printerName":"NameOfThePrinterToUse",
  "fileContent":"YourFileBase64encodedContent",
  "orientationRequested":"LANDSCAPE",
  "mediaSizeName":"ISO_A4"
}
```

```JSON
{
  "printerType=":"PDF|Label",
  "printerName":"NameOfThePrinterToUse",
  "filePath":"/path/to/your/file|http[s]://url/to/your/file",
  "orientationRequested":"PORTRAIT",
  "mediaSizeName":"ISO_A5"
}
```

Important Note:
If both fileContent and filePath are provided, filePath will be used.
