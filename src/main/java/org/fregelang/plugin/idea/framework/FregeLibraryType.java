package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.libraries.LibraryProperties;
import com.intellij.openapi.roots.libraries.LibraryType;
import com.intellij.openapi.roots.libraries.NewLibraryConfiguration;
import com.intellij.openapi.roots.libraries.ui.LibraryEditorComponent;
import com.intellij.openapi.roots.libraries.ui.LibraryPropertiesEditor;
import com.intellij.openapi.vfs.VirtualFile;
import org.fregelang.plugin.idea.icon.FregeIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FregeLibraryType extends LibraryType<FregeLibraryProperties> {

    public static FregeLibraryType getInstance() {
        return (FregeLibraryType) LibraryType.findByKind(FregeLibraryKind.INSTANCE);
    }

    FregeLibraryType() {
        super(FregeLibraryKind.INSTANCE);
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return FregeIcons.FREGE_SDK;
    }

    @Nullable
    @Override
    public Icon getIcon(@Nullable LibraryProperties properties) {
        return FregeIcons.FREGE_SDK;
    }

    @Nullable
    @Override
    public String getCreateActionName() {
        return "Frege SDK";
    }

    @Nullable
    @Override
    public NewLibraryConfiguration createNewLibrary(@NotNull JComponent parentComponent,
                                                    @Nullable VirtualFile contextDirectory,
                                                    @NotNull Project project) {
        return new FregeLibraryDescription().createNewLibrary(parentComponent, contextDirectory);
    }

    @Nullable
    @Override
    public LibraryPropertiesEditor createPropertiesEditor(@NotNull LibraryEditorComponent<FregeLibraryProperties> editorComponent) {
        return new FregeLibraryPropertiesEditor(editorComponent);
    }
}