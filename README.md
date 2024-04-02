# Reference Formatter

This project is a Java-based graphical user interface (GUI) application that generates formatted academic references. It allows users to input bibliographic information, which it then formats into a standardized reference entry. The application supports both standard formatting and specific formats like Markdown and LaTeX.

## Features

- GUI for easy input of bibliographic details.
- Generation of references with italicized journal names and "et al.".
- Output includes HTML, Markdown, and LaTeX formatted references.
- Escape special LaTeX characters to ensure the LaTeX code is ready for use.

## Prerequisites

Before running this project, make sure you have Java Development Kit (JDK21) installed on your machine.

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

# Version Update Log

## 2024-04-02 Update

### Enhancements
- Improved the usability of the Reference Generator (Harvard Style) by dynamically enabling or disabling author input fields based on the selected author count from the dropdown menu. This ensures a more intuitive user interface by preventing input in fields that are not relevant to the selected number of authors.
- Adjusted the default divider location of the `JSplitPane` to position it in the middle of the component, providing a balanced view between the `examplePane` and `resultPane`. This change enhances the application's usability by ensuring that both panels are adequately displayed without the need for manual adjustment.
- Expanded the height of the `examplePane` and `resultPane` to offer a better user experience by displaying more content without the need to scroll. This adjustment makes it easier for users to view examples and results at a glance.

### Bug Fixes
- Fixed an issue where changing the number of authors in the dropdown did not correctly update the enable/disable state of the corresponding input fields. Now, when the number of authors is adjusted, only the relevant fields are enabled for input, and any non-relevant fields are cleared and disabled.
- Addressed a `StringIndexOutOfBoundsException` that occurred when selecting only one author without entering any name. The application now correctly handles empty or partially filled author name fields without crashing.

### Technical Improvements
- Refactored the event handling logic for the dropdown menu to ensure more reliable performance. The application now more efficiently updates the state of author input fields in response to user selections.
- Optimized the layout management for the main application window, ensuring that components are properly sized and positioned according to user expectations. This includes adjustments to the preferred sizes of components and modifications to the layout to better accommodate various screen sizes and resolutions.
