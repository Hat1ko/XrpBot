package com.hatiko.ripple.telegram.bot.transformer;

public interface Transformer<FROM, TO> {
	TO transform(FROM chat);
}
