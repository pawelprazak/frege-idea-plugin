package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.fregelang.plugin.idea.framework.Files.allFiles;

public class FregeFilesChooserDescriptor extends FileChooserDescriptor {
    public FregeFilesChooserDescriptor() {
        super(/* chooseFiles */ true, /* chooseFolders */ true, /* chooseJars */ true, /* chooseJarsAsFiles */ true, /* chooseJarContents */ false, /* chooseMultiple*/ false);
        setTitle("Frege SDK Files");
        setDescription("Choose either a Frege SDK directory or Frege jar files (allowed: binaries, sources, docs)");
    }

    @Override
    public boolean isFileSelectable(VirtualFile file) {
        return super.isFileSelectable(file) && file.isDirectory() || Objects.equals(file.getExtension(), "jar");
    }

    @Override
    public void validateSelectedFiles(VirtualFile[] virtualFiles) throws Exception {
        List<File> files = Arrays.asList(virtualFiles).stream()
                .map(VfsUtilCore::virtualToIoFile)
                .collect(Collectors.toList());

        List<File> allFiles = allFiles(files).collect(toList());
        List<Component> components = Component.discoverIn(allFiles);

        FregeSdkDescriptor.from(components);
    }
}
