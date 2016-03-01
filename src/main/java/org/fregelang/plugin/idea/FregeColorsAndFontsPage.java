package org.fregelang.plugin.idea;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class FregeColorsAndFontsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Brackets", FregeSyntaxHighlighter.FREGE_BRACKETS),
            new AttributesDescriptor("Class", FregeSyntaxHighlighter.FREGE_CLASS),
            new AttributesDescriptor("Comment", FregeSyntaxHighlighter.FREGE_COMMENT),
            new AttributesDescriptor("Curly brackets", FregeSyntaxHighlighter.FREGE_CURLY),
            new AttributesDescriptor("Constructor or Type", FregeSyntaxHighlighter.FREGE_CONSTRUCTOR),
            new AttributesDescriptor("Double colon", FregeSyntaxHighlighter.FREGE_DOUBLE_COLON),
            new AttributesDescriptor("Equal", FregeSyntaxHighlighter.FREGE_EQUAL),
            new AttributesDescriptor("Identifier", FregeSyntaxHighlighter.FREGE_IDENTIFIER),
            new AttributesDescriptor("Keyword", FregeSyntaxHighlighter.FREGE_KEYWORD),
            new AttributesDescriptor("Number", FregeSyntaxHighlighter.FREGE_NUMBER),
            new AttributesDescriptor("Operator", FregeSyntaxHighlighter.FREGE_OPERATOR),
            new AttributesDescriptor("Parenthesis", FregeSyntaxHighlighter.FREGE_PARENTHESIS),
            new AttributesDescriptor("Pragma", FregeSyntaxHighlighter.FREGE_PRAGMA),
            new AttributesDescriptor("Signature", FregeSyntaxHighlighter.FREGE_SIGNATURE),
            new AttributesDescriptor("String", FregeSyntaxHighlighter.FREGE_STRING_LITERAL),
            new AttributesDescriptor("Type", FregeSyntaxHighlighter.FREGE_TYPE)
    };

    @NotNull
    @Override
    public String getDisplayName() {
        return "Frege";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return FregeIcons.FREGE;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new FregeSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "-- line comment\n" +
                "{-\n" +
                "   multi-line comment\n" +
                "-}\n" +
                "{--\n" +
                "   multi-line doc comment\n" +
                "--}\n" +
                "module my.module.Name where\n" +
                "\n" +
                "myFun :: Num a => a -> a -> a\n" +
                "myFun x y = x * y\n";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        Map<String, TextAttributesKey> map = new HashMap<>();

        map.put("brackets", FregeSyntaxHighlighter.FREGE_BRACKETS);
        map.put("class", FregeSyntaxHighlighter.FREGE_CLASS);
        map.put("curly", FregeSyntaxHighlighter.FREGE_CURLY);
        map.put("cons", FregeSyntaxHighlighter.FREGE_CONSTRUCTOR);
        map.put("comment", FregeSyntaxHighlighter.FREGE_COMMENT);
        map.put("dcolon", FregeSyntaxHighlighter.FREGE_DOUBLE_COLON);
        map.put("equal", FregeSyntaxHighlighter.FREGE_EQUAL);
        map.put("id", FregeSyntaxHighlighter.FREGE_IDENTIFIER);
        map.put("keyword", FregeSyntaxHighlighter.FREGE_KEYWORD);
        map.put("number", FregeSyntaxHighlighter.FREGE_NUMBER);
        map.put("operator", FregeSyntaxHighlighter.FREGE_OPERATOR);
        map.put("par", FregeSyntaxHighlighter.FREGE_PARENTHESIS);
        map.put("pragma", FregeSyntaxHighlighter.FREGE_PRAGMA);
        map.put("sig", FregeSyntaxHighlighter.FREGE_SIGNATURE);
        map.put("string", FregeSyntaxHighlighter.FREGE_STRING_LITERAL);
        map.put("type", FregeSyntaxHighlighter.FREGE_TYPE);
        return map;
    }
}
