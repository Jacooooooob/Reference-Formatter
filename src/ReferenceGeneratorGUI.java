import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReferenceGeneratorGUI {
    private JFrame frame;
    private List<JTextField> authorLastNameFields = new ArrayList<>();
    private List<JTextField> authorInitialsFields = new ArrayList<>();
    private JTextField yearField, titleField, journalField, volumeField, issueField, pagesField, urlField, accessDayField, accessMonthField, accessYearField;
    private JComboBox<String> authorCountComboBox;
    private JTextPane examplePane, resultPane, codePane;
    private JButton generateButton, resetButton;

    public ReferenceGeneratorGUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Reference Generator (Harvard Style)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        initializeFields();

        // 设置下拉框
        authorCountComboBox = new JComboBox<>(new String[]{"1", "2", "3", "3+ (Use 'et al.)"});
        // 添加事件监听器来响应选择变化
        authorCountComboBox.addActionListener(e -> updateAuthorFieldsEnabled());
        // 由于我们改变了初始化顺序，确保立即更新字段状态以匹配下拉框的默认选择
        updateAuthorFieldsEnabled(); // 初始化时立即更新输入框状态

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));

        inputPanel.add(createInputComponent("Number of Authors:", authorCountComboBox));
        for (int i = 0; i < 3; i++) {
            inputPanel.add(createInputComponent("Author Last Name " + (i + 1) + ":", authorLastNameFields.get(i)));
            inputPanel.add(createInputComponent("Author Initials " + (i + 1) + ":", authorInitialsFields.get(i)));
        }
        inputPanel.add(createInputComponent("Year:", yearField));
        inputPanel.add(createInputComponent("Article Title:", titleField));
        inputPanel.add(createInputComponent("Journal Title:", journalField));
        inputPanel.add(createInputComponent("Volume:", volumeField));
        inputPanel.add(createInputComponent("Issue:", issueField));
        inputPanel.add(createInputComponent("Pages(E.g., 15-23):", pagesField));
        inputPanel.add(createInputComponent("URL:", urlField));
        inputPanel.add(createInputComponent("Accessed Day:", accessDayField));
        inputPanel.add(createInputComponent("Accessed Month:", accessMonthField));
        inputPanel.add(createInputComponent("Accessed Year:", accessYearField));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        generateButton = new JButton("Generate");
        resetButton = new JButton("Reset");
        buttonPanel.add(generateButton);
        buttonPanel.add(resetButton);

        inputPanel.add(buttonPanel);

        frame.add(inputPanel, BorderLayout.NORTH);

        examplePane = new JTextPane();
        examplePane.setContentType("text/html");
        examplePane.setEditable(false);
        examplePane.setText("<html>Format: First Author’s Last name, Initials <i>et al.</i> (Year) 'Article title', <i>Journal title</i>, Volume(Issue), pp. page numbers. Available at: URL (Accessed Day Month Year).<html>" +
                "<br><br>" +
                "<html>Example: Hawke, J. <i>et al.</i> (2006) 'Genetic influences on reading difficulties in boys and girls: the Colorado twin study', <i>Dyslexia</i>, 12(1), pp. 21-29. Available at: https://onlinelibrary.wiley.com/doi/10.1002/dys.301’ (Accessed 10 October 2006).<html>");
        JScrollPane exampleScrollPane = new JScrollPane(examplePane);
        exampleScrollPane.setPreferredSize(new Dimension(430, 600)); // Adjust the example pane size

        resultPane = new JTextPane();
        resultPane.setContentType("text/html");
        resultPane.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultPane);

        codePane = new JTextPane();
        codePane.setContentType("text/html");
        codePane.setEditable(false);
        JScrollPane codeScrollPane = new JScrollPane(codePane);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(resultScrollPane);
        splitPane.setBottomComponent(codeScrollPane);
        splitPane.setResizeWeight(0.5); // The ratio of the divider - 0.5 means the space is shared equally
//        splitPane.setDividerLocation(Integer.MAX_VALUE); // This ensures the divider is at the bottom

        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(exampleScrollPane, BorderLayout.EAST);

        generateButton.addActionListener(e -> generateReference());
        resetButton.addActionListener(e -> resetForm());

        frame.setPreferredSize(new Dimension(1200, 800)); // Or frame.pack() for automatic sizing
        frame.pack(); // Consider using pack() after adding all components for a layout that fits
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateAuthorFieldsEnabled() {
        String selected = (String) authorCountComboBox.getSelectedItem();
        int selectedCount = 0;
        switch (selected) {
            case "1":
                selectedCount = 1;
                break;
            case "2":
                selectedCount = 2;
                break;
            case "3":
            case "3+ (Use 'et al.)":
                selectedCount = 3;
                break;
        }

        // 根据选择的作者数量启用或禁用相应的输入框
        for (int i = 0; i < authorLastNameFields.size(); i++) {
            boolean enabled = i < selectedCount;
            authorLastNameFields.get(i).setEnabled(enabled);
            authorInitialsFields.get(i).setEnabled(enabled);

            if (!enabled) {
                authorLastNameFields.get(i).setText("");
                authorInitialsFields.get(i).setText("");
            }
        }

        // 确保更新UI
        frame.validate();
        frame.repaint();
    }



    private void initializeFields() {
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
        authorCountComboBox = new JComboBox<>(new String[]{"1", "2", "3", "3+ (Use 'et al.)"});
        for (int i = 0; i < 3; i++) {
            authorLastNameFields.add(new JTextField());
            authorInitialsFields.add(new JTextField());
        }
    }

    private JPanel createInputComponent(String label, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(component);
        component.setPreferredSize(new Dimension(200, 24));
        return panel;
    }

    private void generateReference() {
        String authorCountSelection = (String) authorCountComboBox.getSelectedItem();
        StringBuilder authors = new StringBuilder();
        int authorCount = 0;
        switch (authorCountSelection) {
            case "1":
                authorCount = 1;
                break;
            case "2":
                authorCount = 2;
                break;
            case "3":
                authorCount = 3;
                break;
            case "3+ (Use 'et al.)":
                authorCount = Integer.MAX_VALUE;
                break;
        }

        for (int i = 0; i < authorLastNameFields.size(); i++) {
            if (i >= authorCount) break;
            String lastName = authorLastNameFields.get(i).getText().trim();
            String initials = authorInitialsFields.get(i).getText().trim().toUpperCase();
            if (!lastName.isEmpty() && !initials.isEmpty()) {
                if (authors.length() > 0) authors.append(i == authorCount - 1 ? " and " : ", ");
                authors.append(lastName.substring(0, 1).toUpperCase())
                        .append(lastName.substring(1).toLowerCase())
                        .append(", ")
                        .append(initials.charAt(0))
                        .append(".");
            }
        }

        if (authorCount > 3) {
            // Reset authors to only include the first author with et al.
            String firstAuthorLastName = authorLastNameFields.get(0).getText().trim();
            String firstAuthorInitials = authorInitialsFields.get(0).getText().trim().toUpperCase();
            authors = new StringBuilder(firstAuthorLastName.substring(0, 1).toUpperCase() + firstAuthorLastName.substring(1).toLowerCase() + ", " + firstAuthorInitials.charAt(0) + "." + " <i>et al.</i>");
        }

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

        String formattedReference = String.format(
                "%s (%s) '%s', <i>%s</i>, %s(%s), pp. %s. Available at: %s (Accessed %s %s %s).",
                authors, year, articleTitle, journalTitle, volume, issue, pages, url, accessDay, accessMonth, accessYear
        );

        resultPane.setText("<html>" + formattedReference + "</html>");

        String markdownReference = String.format(
                "%s (%s) '%s', *%s*, %s(%s), pp. %s. Available at: %s (Accessed %s %s %s).",
                authors.toString().replaceAll("<i>", "*").replaceAll("</i>", "*"),
                year, articleTitle, journalTitle, volume, issue, pages, url, accessDay, accessMonth, accessYear
        );

        String latexReference = String.format(
                "%s (%s) '%s', \\textit{%s}, %s(%s), pp. %s. Available at: \\url{%s} (Accessed %s %s %s).",
                authors.toString().replaceAll("<i>", "\\textit{").replaceAll("</i>", "}"),
                year, articleTitle, escapeLatex(journalTitle), volume, issue, pages, escapeLatex(url), accessDay, escapeLatex(accessMonth), accessYear
        );

        codePane.setText("<html>Markdown:<br>" + markdownReference + "<br><br>LaTeX:<br>" + latexReference + "</html>");
    }


    private void resetForm() {
        for (JTextField textField : authorLastNameFields) {
            textField.setText("");
        }
        for (JTextField textField : authorInitialsFields) {
            textField.setText("");
        }
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
        authorCountComboBox.setSelectedIndex(0);
        resultPane.setText("");
        codePane.setText("");
    }

    private String escapeLatex(String text) {
        return text.replace("%", "\\%")
                .replace("_", "\\_")
                .replace("&", "\\&")
                .replace("#", "\\#")
                .replace("$", "\\$")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("^", "\\^{}")
                .replace("~", "\\textasciitilde{}")
                .replace("<", "\\textless ")
                .replace(">", "\\textgreater ");
    }
}
