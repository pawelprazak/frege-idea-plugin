package org.fregelang.plugin.idea.module;

import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.StdModuleTypes;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.fregelang.plugin.idea.icon.FregeIcons;
import org.fregelang.plugin.idea.sdk.FregeSdkType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class FregeModuleBuilder extends JavaModuleBuilder {

    @Nullable
    @Override
    public String getBuilderId() {
        return "frege.module.builder";
    }

    @Nullable
    @Override
    public ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        return StdModuleTypes.JAVA.modifySettingsStep(settingsStep, this);
    }

    @Override
    public Icon getBigIcon() {
        return FregeIcons.FREGE_BIG;
    }

    @Override
    public String getGroupName() {
        return "Frege";
    }

    @Override
    public String getPresentableName() {
        return "Frege";
    }

    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return getModuleType().createWizardSteps(wizardContext, this, modulesProvider);
    }

    @Override
    public ModuleType getModuleType() {
        return FregeModuleType.INSTANCE;
    }

    @Override
    public void setupRootModel(ModifiableRootModel rootModel) throws ConfigurationException {

        if (myJdk != null) {
            rootModel.setSdk(myJdk);
        } else {
            rootModel.inheritSdk();
        }

        checkNotNull(getContentEntryPath());

        ContentEntry contentEntry = doAddContentEntry(rootModel);
        if (contentEntry != null) {
            String srcPath = getContentEntryPath() + File.separator + "src";
            new File(srcPath).mkdirs();
            VirtualFile sourceRoot = LocalFileSystem.getInstance().refreshAndFindFileByPath(FileUtil.toSystemIndependentName(srcPath));
            if (sourceRoot != null) {
                contentEntry.addSourceFolder(sourceRoot, false, "");
            }

            String name = getName();
            try {
                checkNotNull(name);
                makeMain(srcPath + File.separator + "Main.fr");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void makeMain(String path) throws IOException {
        String text = "module Main where\n" + "\n";

        FileWriter writer = new FileWriter(path);
        writer.write(text);
        writer.close();
    }

    @Override
    public boolean isSuitableSdkType(SdkTypeId sdkType) {
        return sdkType instanceof FregeSdkType;
    }
}


