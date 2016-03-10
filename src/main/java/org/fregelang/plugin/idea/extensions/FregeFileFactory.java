package org.fregelang.plugin.idea.extensions;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.psi.FileViewProvider;
import org.fregelang.plugin.idea.psi.FregeFile;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class FregeFileFactory {
    public static ExtensionPointName<FregeFileFactory> EP_NAME = ExtensionPointName.create("org.fregelang.plugin.idea.fregeFileFactory");

    @Nullable
    public abstract Optional<FregeFile> createFile(FileViewProvider provider);
}
