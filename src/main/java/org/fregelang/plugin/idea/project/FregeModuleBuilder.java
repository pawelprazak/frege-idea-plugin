package org.fregelang.plugin.idea.project;

import com.intellij.facet.impl.ui.libraries.LibraryCompositionSettings;
import com.intellij.facet.impl.ui.libraries.LibraryOptionsPanel;
import com.intellij.framework.library.FrameworkLibraryVersionFilter;
import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.SettingsStep;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.projectRoot.LibrariesContainer;
import com.intellij.openapi.roots.ui.configuration.projectRoot.LibrariesContainerFactory;
import com.intellij.openapi.util.Disposer;
import org.fregelang.plugin.idea.framework.FregeLibraryDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;

public class FregeModuleBuilder extends JavaModuleBuilder {

    private LibrariesContainer librariesContainer;
    private LibraryCompositionSettings libraryCompositionSettings;

    public FregeModuleBuilder() {
        addModuleConfigurationUpdater(new ModuleConfigurationUpdater() {
            @Override
            public void update(@NotNull Module module, @NotNull ModifiableRootModel rootModel) {
                libraryCompositionSettings.addLibraries(rootModel, new ArrayList<>(), librariesContainer);
            }
        });
    }

    @Nullable
    @Override
    public ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        librariesContainer = LibrariesContainerFactory.createContainer(settingsStep.getContext().getProject());

        return new FregeStep(settingsStep);
    }

    private class FregeStep extends ModuleWizardStep {
        private final ModuleWizardStep javaStep;
        private final LibraryOptionsPanel libraryPanel;

        public FregeStep(SettingsStep settingsStep) {
            this.javaStep = JavaModuleType.getModuleType().modifyProjectTypeStep(settingsStep, FregeModuleBuilder.this);
            this.libraryPanel = new LibraryOptionsPanel(FregeLibraryDescription.INSTANCE, "",
                    FrameworkLibraryVersionFilter.ALL, librariesContainer, false);

            settingsStep.addSettingsField("Frege S\u001BDK:", libraryPanel.getSimplePanel());
        }

        @Override
        public void updateDataModel() {
            FregeModuleBuilder.this.libraryCompositionSettings = libraryPanel.apply();
            this.javaStep.updateDataModel();
        }

        @Override
        public JComponent getComponent() {
            return libraryPanel.getMainPanel();
        }

        @Override
        public void disposeUIResources() {
            super.disposeUIResources();
            javaStep.disposeUIResources();
            Disposer.dispose(libraryPanel);
        }

        @Override
        public boolean validate() throws ConfigurationException {
            return super.validate() && (javaStep == null || javaStep.validate());
        }
    }
}


