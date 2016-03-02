package org.fregelang.plugin.idea.psi;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.annotations.NotNull;

public class FregeViewProvider extends SingleRootFileViewProvider {

    public FregeViewProvider(PsiManager manager,
                             VirtualFile virtualFile,
                             Boolean eventSystemEnabled,
                             Language language) {
        super(manager, virtualFile, eventSystemEnabled, language);
    }

    @Override
    public boolean supportsIncrementalReparse(@NotNull Language rootLanguage) {
        return false;
    }
}