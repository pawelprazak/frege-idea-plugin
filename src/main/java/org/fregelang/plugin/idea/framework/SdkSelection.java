package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.table.TableView;
import org.fregelang.plugin.idea.framework.FregeLibraryDescription.SdkChoice;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.fregelang.plugin.idea.framework.Files.allFiles;

public class SdkSelection {
    public static Optional<FregeSdkDescriptor> chooseScalaSdkFiles(TableView<SdkChoice> parentComponent) {
        try {
            return browse(parentComponent);
        } catch (Exception e) {
            Messages.showErrorDialog(parentComponent, e.getMessage());
            return Optional.empty();
        }
    }

    private static Optional<FregeSdkDescriptor> browse(JComponent parent) {
        VirtualFile[] virtualFiles = FileChooser.chooseFiles(new FregeFilesChooserDescriptor(), parent, null, null);

        List<File> files = Arrays.asList(virtualFiles).stream()
                .map(VfsUtilCore::virtualToIoFile)
                .collect(toList());

        List<File> allFiles = allFiles(files).collect(toList());
        List<Component> components = Component.discoverIn(allFiles);

        if (files.size() > 0) {
            return Optional.of(FregeSdkDescriptor.from(components));
        } else {
            return Optional.empty();
        }
    }
}
