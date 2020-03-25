package com.hatiko.ripple.telegram.bot.core.transformer;

public interface Transformer<FROM, TO> {
	TO transform(FROM chat);
}
