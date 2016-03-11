package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.ui.table.TableView;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.fregelang.plugin.idea.framework.FregeLibraryDescription.SdkChoice;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.String.format;

public class SdkSelectionDialog extends JDialog {
    private static final long serialVersionUID = 0L;

    private static final Logger log = Logger.getInstance(SdkSelectionDialog.class);

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonDownload;
    private JButton buttonBrowse;
    private TableView<SdkChoice> myTable;

    private final JComponent myParent;
    private final Supplier<List<SdkChoice>> myProvider;
    private final SdkTableModel myTableModel = new SdkTableModel();
    private FregeSdkDescriptor mySelectedSdk;

    public SdkSelectionDialog(JComponent parent, Supplier<List<SdkChoice>> provider) {
        super((Window) parent.getTopLevelAncestor());

        myParent = parent;
        myProvider = provider;

        setTitle("Select JAR's for the new Frege SDK");

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonDownload.addActionListener(e -> onDownload());
        buttonBrowse.addActionListener(e -> onBrowse());
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        myTable.getSelectionModel().addListSelectionListener(new SdkSelectionListener());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        updateTable();
    }

    public int count() {
        return myTableModel.getRowCount();
    }

    private void updateTable() {
        List<SdkChoice> sdks = myProvider.get();

        myTableModel.setItems(sdks);
        myTable.setModelAndUpdateColumns(myTableModel);

        if (!sdks.isEmpty()) {
            myTable.getSelectionModel().setSelectionInterval(0, 0);
        }
    }

    private int rowIndexOf(String source, String version) {
        for (int i = 0; i < myTable.getRowCount(); i++) {
            if (source.equals(myTable.getValueAt(i, 0)) && version.equals(myTable.getValueAt(i, 1))) {
                return i;
            }
        }
        return -1;
    }

    private void onDownload() {
        List<String> fregeVersions;
        try {
            fregeVersions = withProgressSynchronously(
                    "Fetching available Frege versions", textSetter -> Versions.loadFregeVersions());
        } catch (Exception e) {
            log.error("Frege SDK download error", e);
            Messages.showErrorDialog(contentPane, e.getMessage(), "Error Downloading Frege Versions");
            return;
        }

        final SelectionDialog<String> dialog = new SelectionDialog<>(
                contentPane, "Download (via Maven)", "Frege version:",
                fregeVersions.toArray(new String[fregeVersions.size()]));

        if (dialog.showAndGet()) {
            final String version = dialog.getSelectedValue();

            try {
                withProgressSynchronously(
                        format("Downloading Frege %s (via Maven)", version),
                        (listener) -> {
                            Downloader.downloadFrege(version, listener);
                            return (Void) null;
                        }
                );
            } catch (Exception e) {
                log.error("Frege SDK download error", e);
                Messages.showErrorDialog(contentPane, e.getMessage(), "Error Downloading Frege " + version);
                return;
            }

            updateTable();

            int rowIndex = rowIndexOf("Maven", version);

            if (rowIndex >= 0) {
                myTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
                onOK();
            }
        }
    }

    private <T> T withProgressSynchronously(String title, Function<Consumer<String>, T> body) {
        ProgressManager progressManager = ProgressManager.getInstance();

        ThrowableComputable<T, RuntimeException> computable = () -> {
            ProgressIndicator progressIndicator = progressManager.getProgressIndicator();
            return body.apply(progressIndicator::setText);
        };

        return progressManager.runProcessWithProgressSynchronously(computable, title, false, null);
    }

    private void onBrowse() {
        Optional<FregeSdkDescriptor> result = SdkSelection.chooseFregeSdkFiles(myTable);

        if (result.isPresent()) {
            mySelectedSdk = result.get();
            dispose();
        }
    }

    private void onOK() {
        if (myTable.getSelectedRowCount() > 0) {
            mySelectedSdk = myTableModel.getItems().get(myTable.getSelectedRow()).getSdk();
        }
        dispose();
    }

    private void onCancel() {
        mySelectedSdk = null;
        dispose();
    }

    public FregeSdkDescriptor open() {
        pack();
        setLocationRelativeTo(myParent.getTopLevelAncestor());
        setVisible(true);
        return mySelectedSdk;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonBrowse = new JButton();
        buttonBrowse.setText("Browse...");
        panel1.add(buttonBrowse, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonDownload = new JButton();
        buttonDownload.setText("Download...");
        panel1.add(buttonDownload, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        myTable = new TableView();
        scrollPane1.setViewportView(myTable);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    private class SdkSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            buttonOK.setEnabled(myTable.getSelectedRow() >= 0);
        }
    }
}
