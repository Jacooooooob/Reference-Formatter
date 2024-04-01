# Reference Generator

This project is a Java-based graphical user interface (GUI) application that generates formatted academic references. It allows users to input bibliographic information, which it then formats into a standardized reference entry. The application supports both standard formatting and specific formats like Markdown and LaTeX.

## Features

- GUI for easy input of bibliographic details.
- Generation of references with italicized journal names and "et al.".
- Output includes HTML, Markdown, and LaTeX formatted references.
- Escape special LaTeX characters to ensure the LaTeX code is ready for use.

## Prerequisites

Before running this project, make sure you have Java Development Kit (JDK21) installed on your machine.

## Running the Application

To run the application, compile the Java code and run the main class. The GUI should appear, allowing you to enter the details of the reference you wish to format.

```bash
javac ReferenceGeneratorGUI.java
java ReferenceGeneratorGUI
```

## Usage

Fill in the bibliographic details in the provided text fields:

- Author Last Name
- Author Initials
- Year
- Article Title
- Journal Title
- Volume
- Issue
- Pages
- URL
- Accessed Day
- Accessed Month
- Accessed Year

Once all details are filled, click the "Generate" button to produce the formatted reference. The reference will be displayed in the bottom text panes in HTML, Markdown, and LaTeX formats.

## Contact

For any additional questions or feedback, please open an issue in the project repository.
