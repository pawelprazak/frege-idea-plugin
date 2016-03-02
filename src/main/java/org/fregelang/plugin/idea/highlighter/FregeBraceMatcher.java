package org.fregelang.plugin.idea.highlighter;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.fregelang.plugin.idea.parser.token.FregeLexerTokens;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FregeBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[] {
            new BracePair(FregeLexerTokens.OPAREN, FregeLexerTokens.CPAREN, true),
            new BracePair(FregeLexerTokens.OCURLY, FregeLexerTokens.CCURLY, true),
            new BracePair(FregeLexerTokens.OBRACK, FregeLexerTokens.CBRACK, true)
    };

    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }
}