package org.fregelang.plugin.idea.parser.token;

import com.google.common.collect.ImmutableList;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.TokenSet;
import org.fregelang.plugin.idea.psi.FregeTokenType;
import org.fregelang.plugin.idea.psi.FregeTypes;

public class FregeLexerTokens implements FregeTypes {

    public static final ImmutableList<FregeTokenType> KEYWORDS = ImmutableList.of(
            (FregeTokenType) FregeLexerTokens.CASE_KW,
            (FregeTokenType) FregeLexerTokens.CLASS_KW,
            (FregeTokenType) FregeLexerTokens.DATA_KW,
            (FregeTokenType) FregeLexerTokens.DEFAULT_KW,
//            (FregeTokenType) FregeLexerTokens.DERIVE,
            (FregeTokenType) FregeLexerTokens.DO_KW,
            (FregeTokenType) FregeLexerTokens.ELSE_KW,
            (FregeTokenType) FregeLexerTokens.EXPORT,
            (FregeTokenType) FregeLexerTokens.IF_KW,
            (FregeTokenType) FregeLexerTokens.IMPORT_KW,
            (FregeTokenType) FregeLexerTokens.IN_KW,
            (FregeTokenType) FregeLexerTokens.INFIX_KW,
            (FregeTokenType) FregeLexerTokens.INFIXL_KW,
            (FregeTokenType) FregeLexerTokens.INFIXR_KW,
            (FregeTokenType) FregeLexerTokens.INSTANCE_KW,
            (FregeTokenType) FregeLexerTokens.FORALL_KW,
            (FregeTokenType) FregeLexerTokens.FOREIGN_KW,
            (FregeTokenType) FregeLexerTokens.LET_KW,
            (FregeTokenType) FregeLexerTokens.MODULE_KW,
            (FregeTokenType) FregeLexerTokens.OF_KW,
            (FregeTokenType) FregeLexerTokens.THEN_KW,
            (FregeTokenType) FregeLexerTokens.WHERE_KW,
            (FregeTokenType) FregeLexerTokens.TYPE_KW,
            (FregeTokenType) FregeLexerTokens.SAFE,
            (FregeTokenType) FregeLexerTokens.UNSAFE
    );

    public static final ImmutableList<FregeTokenType> OPERATORS = ImmutableList.of(
            (FregeTokenType) FregeLexerTokens.AT,
            (FregeTokenType) FregeLexerTokens.TILDE,
//            (FregeTokenType) FregeLexerTokens.LAMBDA,
            (FregeTokenType) FregeLexerTokens.DOUBLE_ARROW,
            (FregeTokenType) FregeLexerTokens.EXCLAMATION,
            (FregeTokenType) FregeLexerTokens.RIGHT_ARROW,
            (FregeTokenType) FregeLexerTokens.LEFT_ARROW,
            (FregeTokenType) FregeLexerTokens.EQUALS,
            (FregeTokenType) FregeLexerTokens.COMMA,
            (FregeTokenType) FregeLexerTokens.DOT,
            (FregeTokenType) FregeLexerTokens.DOT_DOT,
            (FregeTokenType) FregeLexerTokens.DOUBLE_COLON,
            (FregeTokenType) FregeLexerTokens.LEFT_PAREN,
            (FregeTokenType) FregeLexerTokens.RIGHT_PAREN,
            (FregeTokenType) FregeLexerTokens.LEFT_BRACE,
            (FregeTokenType) FregeLexerTokens.RIGHT_BRACE,
            (FregeTokenType) FregeLexerTokens.LEFT_BRACKET,
            (FregeTokenType) FregeLexerTokens.RIGHT_BRACKET,
            (FregeTokenType) FregeLexerTokens.SEMICOLON,
            (FregeTokenType) FregeLexerTokens.COLON,
            (FregeTokenType) FregeLexerTokens.VERTICAL_BAR,
            (FregeTokenType) FregeLexerTokens.UNDERSCORE
    );

    public static final FregeTokenType BLOCK_COMMENT = new FregeTokenType("COMMENT");
    public static final FregeTokenType END_OF_LINE_COMMENT = new FregeTokenType("--");
    public static final FregeTokenType PRAGMA = new FregeTokenType("PRAGMA");

    public static final FregeTokenType NEW_LINE = new FregeTokenType("NL");

    private static final TokenSet COMMENTS = TokenSet.create(
        END_OF_LINE_COMMENT,
        BLOCK_COMMENT,
        PRAGMA
    );

    private static final TokenSet WHITESPACES = TokenSet.create(TokenType.WHITE_SPACE, NEW_LINE);
}
