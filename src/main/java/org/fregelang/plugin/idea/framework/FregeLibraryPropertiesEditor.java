package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.roots.libraries.ui.LibraryEditorComponent;
import com.intellij.openapi.roots.libraries.ui.LibraryPropertiesEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

class FregeLibraryPropertiesEditor extends LibraryPropertiesEditor {
    private final LibraryEditorComponent<FregeLibraryProperties> editorComponent;
    private final FregeLibraryEditorForm form;

    public FregeLibraryPropertiesEditor(LibraryEditorComponent<FregeLibraryProperties> editorComponent) {
        this.editorComponent = editorComponent;
        this.form = new FregeLibraryEditorForm();
    }

    @NotNull
    @Override
    public JComponent createComponent() {
        return form.getComponent();
    }

    @Override
    public boolean isModified() {
        return form.getState() != properties().getState();
    }

    @Override
    public void apply() {
        properties().loadState(form.getState());
    }

    @Override
    public void reset() {
        form.setState(properties().getState());
    }

    private FregeLibraryProperties properties() {
        return editorComponent.getProperties();
    }
}

