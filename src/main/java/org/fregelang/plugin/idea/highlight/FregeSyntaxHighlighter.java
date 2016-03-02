package org.fregelang.plugin.idea.highlight;

import com.google.common.collect.ImmutableMap;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.tree.IElementType;
import org.fregelang.plugin.idea.parser.lexer.FregeLexer;
import org.fregelang.plugin.idea.parser.token.FregeLexerTokens;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;
import static java.util.stream.Collectors.toMap;
import static org.fregelang.plugin.idea.psi.FregeTypes.BLOCK_COMMENT;
import static org.fregelang.plugin.idea.psi.FregeTypes.END_OF_LINE_COMMENT;

public class FregeSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey FREGE_BRACKETS
            = createTextAttributesKey("FREGE_BRACKETS", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey FREGE_CLASS
            = createTextAttributesKey("FREGE_CLASS");
    public static final TextAttributesKey FREGE_CONSTRUCTOR
            = createTextAttributesKey("FREGE_CONSTRUCTOR");
    public static final TextAttributesKey FREGE_CURLY
            = createTextAttributesKey("FREGE_CURLY", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey FREGE_DOUBLE_COLON
            = createTextAttributesKey("FREGE_DOUBLE_COLON");
    public static final TextAttributesKey FREGE_EQUAL
            = createTextAttributesKey("FREGE_EQUAL", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey FREGE_KEYWORD
            = createTextAttributesKey("FREGE_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey FREGE_PARENTHESIS
            = createTextAttributesKey("FREGE_PARENTHESIS", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey FREGE_STRING_LITERAL
            = createTextAttributesKey("FREGE_STRING_LITERAL", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey FREGE_SIGNATURE
            = createTextAttributesKey("FREGE_SIGNATURE");
    public static final TextAttributesKey FREGE_COMMENT
            = createTextAttributesKey("FREGE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey FREGE_PRAGMA
            = createTextAttributesKey("FREGE_PAGMA", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey FREGE_NUMBER
            = createTextAttributesKey("FREGE_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey FREGE_TYPE
            = createTextAttributesKey("FREGE_TYPE");
    public static final TextAttributesKey FREGE_OPERATOR
            = createTextAttributesKey("FREGE_OPERATOR", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey FREGE_IDENTIFIER
            = createTextAttributesKey("FREGE_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);

    private static final ImmutableMap<IElementType, TextAttributesKey> KEYS
            = ImmutableMap.<IElementType, TextAttributesKey>builder()
            .put(END_OF_LINE_COMMENT, FREGE_COMMENT)
            .put(BLOCK_COMMENT, FREGE_COMMENT)

            .putAll(FregeLexerTokens.KEYWORDS.stream().collect(toMap(Function.identity(), t -> FREGE_KEYWORD)))

            .put(FregeLexerTokens.PRAGMA, FREGE_PRAGMA)

            .put(FregeLexerTokens.LEFT_PAREN, FREGE_PARENTHESIS)
            .put(FregeLexerTokens.RIGHT_PAREN, FREGE_PARENTHESIS)
            .put(FregeLexerTokens.LEFT_BRACE, FREGE_CURLY)
            .put(FregeLexerTokens.RIGHT_BRACE, FREGE_CURLY)

            .put(FregeLexerTokens.LEFT_BRACKET, FREGE_BRACKETS)
            .put(FregeLexerTokens.RIGHT_BRACKET, FREGE_BRACKETS)

            .put(FregeLexerTokens.DOUBLE_COLON, FREGE_DOUBLE_COLON)
            .put(FregeLexerTokens.EQUALS, FREGE_EQUAL)

            .put(FregeLexerTokens.AT, FREGE_IDENTIFIER)
            .put(FregeLexerTokens.UNDERSCORE, FREGE_IDENTIFIER)
            .put(FregeLexerTokens.VARID, FREGE_IDENTIFIER)
            .put(FregeLexerTokens.QVARID, FREGE_IDENTIFIER)

            .put(FregeLexerTokens.CONID, FREGE_CONSTRUCTOR)
            .put(FregeLexerTokens.QCONID, FREGE_CONSTRUCTOR)
            .put(FregeLexerTokens.OPERATOR_CONS, FREGE_CONSTRUCTOR)
            .put(FregeLexerTokens.COLON, FREGE_CONSTRUCTOR)

            .put(FregeLexerTokens.OPERATOR_ID, FREGE_OPERATOR)
            .put(FregeLexerTokens.MINUS, FREGE_OPERATOR)

            .put(FregeLexerTokens.STRING, FREGE_STRING_LITERAL)
            .put(FregeLexerTokens.CHAR, FREGE_STRING_LITERAL)

            .put(FregeLexerTokens.INTEGER, FREGE_NUMBER)

            .put(FregeLexerTokens.DOUBLE_ARROW, FREGE_CLASS)
            .build();

    public static final ImmutableMap<TextAttributesKey, Pair<String, HighlightSeverity>> DISPLAY_NAMES =
            ImmutableMap.<TextAttributesKey, Pair<String, HighlightSeverity>>builder()
                    .put(FREGE_KEYWORD, new Pair<>("Property value", null))
                    .put(FREGE_COMMENT, new Pair<>("Comment", null))
                    .build();

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FregeLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return SyntaxHighlighterBase.pack(KEYS.get(tokenType));
    }
}