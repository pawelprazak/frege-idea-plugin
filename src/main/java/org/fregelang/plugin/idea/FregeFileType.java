package org.fregelang.plugin.idea;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FregeFileType extends LanguageFileType {

    public static final FregeFileType INSTANCE = new FregeFileType();
    public static final String DEFAULT_EXTENSION = "fr";

    private FregeFileType() {
        super(FregeLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Frege file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Frege language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return FregeIcons.DEFAULT;
    }

    @Override
    public String getCharset(@NotNull VirtualFile file, @NotNull byte[] content) {
        return "UTF-8";
    }
}
