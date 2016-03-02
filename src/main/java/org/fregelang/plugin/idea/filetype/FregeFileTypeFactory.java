package org.fregelang.plugin.idea.filetype;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.fregelang.plugin.idea.filetype.FregeFileType;
import org.jetbrains.annotations.NotNull;

public class FregeFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(FregeFileType.INSTANCE, FregeFileType.DEFAULT_EXTENSION);
    }
}