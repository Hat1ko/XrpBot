package com.hatiko.ripple.telegram.bot.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeyboardPreparatorImpl implements KeyboardPreparator {

	private final ActionProperties actionProperties;
	private Map<String, KeyboardButton> buttons;

	@Override
	public ReplyKeyboardMarkup startKeyboard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReplyKeyboardMarkup mainKeyboard() {

		return null;
	}

	@Override
	public ReplyKeyboardMarkup logInKeyboard() {
		// TODO Auto-generated method stub
		return null;
	}

	private Map<String, KeyboardButton> getButtons() {

		if (Objects.isNull(buttons)) {

			buttons = new HashMap<String, KeyboardButton>();

			KeyboardButton helloButton = new KeyboardButton(actionProperties.getCommand().getHello());
			helloButton.setText(actionProperties.getButton().getHello());
			buttons.put("hello", helloButton);

			KeyboardButton helpButton = new KeyboardButton(actionProperties.getCommand().getHelp());
			helpButton.setText(actionProperties.getButton().getHelp());
			buttons.put("help", helpButton);

			KeyboardButton logInButton = new KeyboardButton(actionProperties.getCommand().getLogIn());
			helpButton.setText(actionProperties.getButton().getLogIn());
			buttons.put("logIn", logInButton);

			KeyboardButton getBalanceButton = new KeyboardButton(actionProperties.getCommand().getGetBalance());
			getBalanceButton.setText(actionProperties.getButton().getGetBalance());
			buttons.put("getBalance", getBalanceButton);

			KeyboardButton generateMemoButton = new KeyboardButton(actionProperties.getCommand().getGenerateMemo());
			generateMemoButton.setText(actionProperties.getButton().getGenerateMemo());
			buttons.put("generateBalance", generateMemoButton);

			KeyboardButton getTransactionInfoButton = new KeyboardButton(
					actionProperties.getCommand().getGetTransactionInfo());
			getTransactionInfoButton.setText(actionProperties.getButton().getGetTransactionInfo());
			buttons.put("getTransactionInfo", getTransactionInfoButton);

			KeyboardButton withdrawButton = new KeyboardButton(actionProperties.getCommand().getWithdraw());
			withdrawButton.setText(actionProperties.getButton().getWithdraw());
			buttons.put("withdraw", withdrawButton);
		}

		return buttons;
	}
}
