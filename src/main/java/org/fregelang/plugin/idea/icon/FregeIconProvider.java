package org.fregelang.plugin.idea.icon;

import com.intellij.ide.IconProvider;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import org.fregelang.plugin.idea.psi.FregeFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FregeIconProvider extends IconProvider {

    @Nullable
    @Override
    public Icon getIcon(@NotNull PsiElement element, @Iconable.IconFlags int flags) {
        ProgressManager.checkCanceled();

        if (element == null) {
            return null;
        }
        if (element instanceof FregeFile) {
            FregeFile file = (FregeFile) element;
            return getFregIcon(file, flags);
        }
        return null;
    }

    private Icon getFregIcon(FregeFile file, int flags) {
        if (file.getVirtualFile() == null) {
            return FregeIcons.SCRIPT_FILE;
        }
        return FregeIcons.DEFAULT;
    }
}