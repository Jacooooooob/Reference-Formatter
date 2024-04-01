import javax.swing.*;
import java.awt.*;

public class ReferenceGeneratorGUI {
    private JFrame frame;
    private JTextField authorLastNameField, authorInitialsField, yearField, titleField, journalField,
            volumeField, issueField, pagesField, urlField, accessDayField, accessMonthField, accessYearField;
    private JTextPane examplePane, resultPane, codePane;
    private JButton generateButton, resetButton;

    public ReferenceGeneratorGUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Reference Generator (Harvard Style)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Initialize text fields
        authorLastNameField = new JTextField();
        authorInitialsField = new JTextField();
        yearField = new JTextField();
        titleField = new JTextField();
        journalField = new JTextField();
        volumeField = new JTextField();
        issueField = new JTextField();
        pagesField = new JTextField();
        urlField = new JTextField();
        accessDayField = new JTextField();
        accessMonthField = new JTextField();
        accessYearField = new JTextField();

        // Layout for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
        inputPanel.add(createInputComponent("Author Last Name:", authorLastNameField));
        inputPanel.add(createInputComponent("Author Initials:", authorInitialsField));
        inputPanel.add(createInputComponent("Year:", yearField));
        inputPanel.add(createInputComponent("Article Title:", titleField));
        inputPanel.add(createInputComponent("Journal Title:", journalField));
        inputPanel.add(createInputComponent("Volume:", volumeField));
        inputPanel.add(createInputComponent("Issue:", issueField));
        inputPanel.add(createInputComponent("Pages(E.g., 15-23):", pagesField));
        inputPanel.add(createInputComponent("URL:", urlField));
        inputPanel.add(createInputComponent("Accessed Day:", accessDayField));
        inputPanel.add(createInputComponent("Accessed Month (Don't input number. E.g., 'March', not '3'):", accessMonthField));
        inputPanel.add(createInputComponent("Accessed Year:", accessYearField));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); // 中间参数10代表按钮之间的间隔，5是顶部和底部的间隔JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); // 中间参数10代表按钮之间的间隔，5是顶部和底部的间隔

        // Generate button
        generateButton = new JButton("Generate");
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(generateButton);

        // Reset button
        resetButton = new JButton("Reset");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(resetButton);

        // 在按钮面板中添加按钮
        buttonPanel.add(generateButton);
        buttonPanel.add(Box.createHorizontalStrut(20)); // 在两个按钮之间添加20像素的水平间隔
        buttonPanel.add(resetButton);

        inputPanel.add(buttonPanel);

        // Example pane to show example reference
        examplePane = new JTextPane();
        examplePane.setContentType("text/html");
        examplePane.setEditable(false);
        String exampleReference = "<html>Format: First Author’s Last name, Initials <i>et al.</i> (Year) 'Article title', <i>Journal title</i>, Volume(Issue), pp. page numbers. Available at: URL (Accessed Day Month Year).<html>" +
                "<br><br>" +
                "<html>Example: Hawke, J. <i>et al.</i> (2006) 'Genetic influences on reading difficulties in boys and girls: the Colorado twin study', <i>Dyslexia</i>, 12(1), pp. 21-29. Available at: https://onlinelibrary.wiley.com/doi/10.1002/dys.301’ (Accessed 10 October 2006).<html>";
        examplePane.setText(exampleReference);

        // Result and code panes
        resultPane = new JTextPane();
        resultPane.setContentType("text/html");
        resultPane.setEditable(false);
        codePane = new JTextPane();
        codePane.setContentType("text/html");
        codePane.setEditable(false);

//        exmaplePane.setSize(100, 10);
        // Split pane to hold result and code panes
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(resultPane),
                new JScrollPane(codePane));
        splitPane.setResizeWeight(0.5);

        // Panel to contain the input fields and the generate button
        JPanel inputAndGeneratePanel = new JPanel(new BorderLayout());
        inputAndGeneratePanel.add(inputPanel, BorderLayout.NORTH);

        // Adding example pane to the east
        JScrollPane exampleScrollPane = new JScrollPane(examplePane);
        exampleScrollPane.setPreferredSize(new Dimension(250, frame.getHeight())); // Set the preferred size for the example pane

        // Add panels to frame
        frame.add(inputAndGeneratePanel, BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(exampleScrollPane, BorderLayout.EAST);

        // Event listeners
        generateButton.addActionListener(e -> generateReference());
        resetButton.addActionListener(e -> resetForm());

        // Finalize and show frame
        frame.setPreferredSize(new Dimension(800, 700));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createInputComponent(String label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(textField);
        textField.setPreferredSize(new Dimension(200, 24));
        return panel;
    }

    private void generateReference() {
        String authorLastName = authorLastNameField.getText().trim();
        String authorInitials = authorInitialsField.getText().trim();
        String year = yearField.getText().trim();
        String articleTitle = titleField.getText().trim();
        String journalTitle = journalField.getText().trim();
        String volume = volumeField.getText().trim();
        String issue = issueField.getText().trim();
        String pages = pagesField.getText().trim();
        String url = urlField.getText().trim();
        String accessDay = accessDayField.getText().trim();
        String accessMonth = accessMonthField.getText().trim();
        String accessYear = accessYearField.getText().trim();

        authorLastName = authorLastName.substring(0, 1).toUpperCase() + authorLastName.substring(1);
        if (!authorInitials.isEmpty()) {
            authorInitials = authorInitials.substring(0, 1).toUpperCase();
        }

        String formattedReference = String.format(
                "Reference: %s, %s. <i>et al.</i> (%s) '%s', <i>%s</i>, %s(%s), pp. %s. Available at: %s (Accessed %s %s %s).",
                authorLastName, authorInitials, year, articleTitle, journalTitle, volume, issue, pages, url, accessDay, accessMonth, accessYear
        );

        resultPane.setText("<html>" + formattedReference + "</html>");

        String markdownReference = String.format(
                "Markdown: %s, %s. *et al.* (%s) '%s', *%s*, %s(%s), pp. %s. Available at: %s (Accessed %s %s %s).",
                authorLastName, authorInitials, year, articleTitle, journalTitle, volume, issue, pages, url, accessDay, accessMonth, accessYear
        );

        String latexReference = String.format(
                "LaTeX: %s, %s. \\textit{et al.} (%s) '%s', \\textit{%s}, %s(%s), pp. %s. Available at: \\url{%s} (Accessed %s %s %s).",
                authorLastName, authorInitials, year, articleTitle, escapeLatex(journalTitle), volume, issue, pages,
                escapeLatex(url), accessDay, escapeLatex(accessMonth), accessYear
        );

        codePane.setText("<html>" + markdownReference + "<br>" + latexReference + "</html>");
    }

    private void resetForm() {
        // 清空所有文本字段
        authorLastNameField.setText("");
        authorInitialsField.setText("");
        yearField.setText("");
        titleField.setText("");
        journalField.setText("");
        volumeField.setText("");
        issueField.setText("");
        pagesField.setText("");
        urlField.setText("");
        accessDayField.setText("");
        accessMonthField.setText("");
        accessYearField.setText("");

        // 清除结果和代码展示面板的内容
        resultPane.setText("");
        codePane.setText("");
    }

    private String escapeLatex(String text) {
        // Escape LaTeX special characters
        return text.replace("%", "\\%")
                .replace("_", "\\_")
                .replace("&", "\\&")
                .replace("#", "\\#")
                .replace("$", "\\$")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("^", "\\^")
                .replace("~", "\\textasciitilde")
                .replace("<", "\\textless")
                .replace(">", "\\textgreater");
    }
}
