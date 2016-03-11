package org.fregelang.plugin.idea.filetype;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.encoding.EncodingRegistry;
import org.fregelang.plugin.idea.FregeLanguage;
import org.fregelang.plugin.idea.icon.FregeIcons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.nio.charset.Charset;

public class FregeFileType extends LanguageFileType {

    public
    static final FregeFileType INSTANCE = new FregeFileType();
    @NonNls
    public static final String DEFAULT_EXTENSION = "fr";
    @NonNls
    public static final String DOT_DEFAULT_EXTENSION = "." + DEFAULT_EXTENSION;

    private FregeFileType() {
        super(FregeLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Frege";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Frege language files";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return FregeIcons.FILE_TYPE;
    }

    @Override
    public String getCharset(@NotNull VirtualFile file, @NotNull final byte[] content) {
        Charset charset = EncodingRegistry.getInstance().getDefaultCharset();
        return charset.name();
    }
}
