package org.fregelang.plugin.idea.parser.lexer;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class FregeLexer extends FlexAdapter {
    public FregeLexer() {
        super(new _FregeLexer((Reader) null));
    }
}
