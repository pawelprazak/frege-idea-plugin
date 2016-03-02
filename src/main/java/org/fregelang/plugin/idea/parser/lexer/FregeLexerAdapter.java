package org.fregelang.plugin.idea.parser.lexer;

import com.intellij.lexer.FlexAdapter;
import org.fregelang.plugin.idea.parser.lexer._FregeLexer;

import java.io.Reader;

public class FregeLexerAdapter extends FlexAdapter {
    public FregeLexerAdapter() {
        super(new _FregeLexer((Reader) null));
    }
}
