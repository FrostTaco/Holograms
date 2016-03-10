package com.sainttx.holograms.parser;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.animation.ItemAnimation;
import com.sainttx.holograms.api.line.AnimatedItemLine;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.util.TextUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimatedItemLineParser implements AnimatedItemLine.Parser {

    private static final Pattern linePattern = Pattern.compile("(animation_item:)(.+)");

    @Override
    public boolean canParse(String text) {
        return linePattern.matcher(text).matches();
    }

    @Override
    public HologramLine parse(Hologram hologram, String text) {
        Matcher matcher = linePattern.matcher(text);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid raw text provided");
        }
        String items = matcher.group(2);
        String[] individualItems = items.split("\\|\\|");
        ItemAnimation animation = new ItemAnimation();
        for (String item : individualItems) {
            animation.addSlide(TextUtil.parseItem(item));
        }
        return new AnimatedItemLine(hologram, animation);
    }
}