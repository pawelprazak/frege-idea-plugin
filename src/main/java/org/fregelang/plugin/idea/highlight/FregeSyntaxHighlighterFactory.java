package org.fregelang.plugin.idea.highlight;

import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import org.jetbrains.annotations.NotNull;

public class FregeSyntaxHighlighterFactory extends SingleLazyInstanceSyntaxHighlighterFactory {

    @NotNull
    @Override
    protected SyntaxHighlighter createHighlighter() {
        return new FregeSyntaxHighlighter();
    }
}
