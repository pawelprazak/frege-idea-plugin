package org.fregelang.plugin.idea.project;

import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.platform.ProjectTemplate;
import com.intellij.platform.ProjectTemplatesFactory;
import org.fregelang.plugin.idea.icon.FregeIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FregeProjectTemplatesFactory extends ProjectTemplatesFactory {

    public static final String GROUP = "Frege";

    @NotNull
    @Override
    public String[] getGroups() {
        return new String[]{GROUP};
    }

    @NotNull
    @Override
    public ProjectTemplate[] createTemplates(@Nullable String group, WizardContext context) {
        return new ProjectTemplate[]{new FregeProjectTemplate()};
    }

    @Override
    public Icon getGroupIcon(String group) {
        return FregeIcons.DEFAULT;
    }

    @Override
    public int getGroupWeight(String group) {
        return 1;
    }
}
