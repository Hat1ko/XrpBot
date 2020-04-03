package com.hatiko.ripple.telegram.bot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.hatiko.ripple.telegram.bot.properties.ActionProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeyboardPreparator {

	private final ActionProperties actionProperties;
	private Map<String, KeyboardButton> buttons;

	@PostConstruct
	private void createButtons() {

		buttons = new HashMap<String, KeyboardButton>();

		KeyboardButton helloButton = new KeyboardButton();
		helloButton.setText(actionProperties.getButtonOperation().getHello());
		buttons.put(actionProperties.getMethodName().getHello(), helloButton);

		KeyboardButton helpButton = new KeyboardButton();
		helpButton.setText(actionProperties.getButtonOperation().getHelp());
		buttons.put(actionProperties.getMethodName().getHelp(), helpButton);

		KeyboardButton nextButton = new KeyboardButton();
		nextButton.setText(actionProperties.getButtonOperation().getNext());
		buttons.put(actionProperties.getMethodName().getNext(), nextButton);

		KeyboardButton mainButton = new KeyboardButton();
		mainButton.setText(actionProperties.getButtonOperation().getMain());
		buttons.put(actionProperties.getMethodName().getMain(), mainButton);

		KeyboardButton registerButton = new KeyboardButton();
		registerButton.setText(actionProperties.getButtonOperation().getRegister());
		buttons.put(actionProperties.getMethodName().getRegister(), registerButton);

		KeyboardButton logInButton = new KeyboardButton();
		logInButton.setText(actionProperties.getButtonOperation().getLogIn());
		buttons.put(actionProperties.getMethodName().getLogIn(), logInButton);

		KeyboardButton logOutButton = new KeyboardButton();
		logOutButton.setText(actionProperties.getButtonOperation().getLogOut());
		buttons.put(actionProperties.getMethodName().getLogOut(), logOutButton);

		KeyboardButton getBalanceButton = new KeyboardButton();
		getBalanceButton.setText(actionProperties.getButtonOperation().getGetBalance());
		buttons.put(actionProperties.getMethodName().getGetBalance(), getBalanceButton);

		KeyboardButton generateMemoButton = new KeyboardButton();
		generateMemoButton.setText(actionProperties.getButtonOperation().getGenerateMemo());
		buttons.put(actionProperties.getMethodName().getGenerateMemo(), generateMemoButton);

		KeyboardButton getTransactionInfoButton = new KeyboardButton(
				actionProperties.getButtonOperation().getGetTransactionInfo());
		getTransactionInfoButton.setText(actionProperties.getButtonOperation().getGetTransactionInfo());
		buttons.put(actionProperties.getMethodName().getGetTransactionInfo(), getTransactionInfoButton);

		KeyboardButton getLastTransactionsButton = new KeyboardButton(
				actionProperties.getButtonOperation().getGetLastTransactions());
		getLastTransactionsButton.setText(actionProperties.getButtonOperation().getGetLastTransactions());
		buttons.put(actionProperties.getMethodName().getGetLastTransactions(), getLastTransactionsButton);

		KeyboardButton withdrawButton = new KeyboardButton(actionProperties.getButtonOperation().getWithdraw());
		withdrawButton.setText(actionProperties.getButtonOperation().getWithdraw());
		buttons.put(actionProperties.getMethodName().getWithdraw(), withdrawButton);
	}

	public ReplyKeyboardMarkup getStartKeyboard() {

		ReplyKeyboardMarkup startKeyboard = configKeyboard(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

		List<KeyboardRow> keyboard = new ArrayList<>();

		KeyboardRow firstRow = new KeyboardRow();
		firstRow.add(buttons.get(actionProperties.getMethodName().getLogIn()));

		KeyboardRow secondRow = new KeyboardRow();
		secondRow.add(buttons.get(actionProperties.getMethodName().getRegister()));

		KeyboardRow thirdRow = new KeyboardRow();
		thirdRow.add(buttons.get(actionProperties.getMethodName().getNext()));

		keyboard.add(firstRow);
		keyboard.add(secondRow);
		keyboard.add(thirdRow);

		startKeyboard.setKeyboard(keyboard);

		return startKeyboard;
	}

	public ReplyKeyboardMarkup getMainKeyboard(Boolean logInStatus) {

		ReplyKeyboardMarkup mainKeyboard = configKeyboard(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

		List<KeyboardRow> keyboard = new ArrayList<>();

		KeyboardRow firstRow = new KeyboardRow();
		firstRow.add(buttons.get(actionProperties.getMethodName().getGetBalance()));
		firstRow.add(buttons.get(actionProperties.getMethodName().getGetLastTransactions()));

		KeyboardRow secondRow = new KeyboardRow();
		secondRow.add(buttons.get(actionProperties.getMethodName().getGetTransactionInfo()));
		secondRow.add(buttons.get(actionProperties.getMethodName().getGenerateMemo()));

		KeyboardRow thirdRow = new KeyboardRow();
		thirdRow.add(buttons.get(actionProperties.getMethodName().getWithdraw()));
		if (logInStatus) {
			thirdRow.add(buttons.get(actionProperties.getMethodName().getLogOut()));
		}

		KeyboardRow forthRow = new KeyboardRow();
		forthRow.add(buttons.get(actionProperties.getMethodName().getRegister()));
		forthRow.add(buttons.get(actionProperties.getMethodName().getLogIn()));

		keyboard.add(firstRow);
		keyboard.add(secondRow);
		keyboard.add(thirdRow);
		if (!logInStatus) {
			keyboard.add(forthRow);
		}

		mainKeyboard.setKeyboard(keyboard);

		return mainKeyboard;
	}

	public ReplyKeyboardMarkup getLogInKeyboard() {

		ReplyKeyboardMarkup logInKeyboard = configKeyboard(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

		List<KeyboardRow> keyboard = new ArrayList<>();

		KeyboardRow firstRow = new KeyboardRow();
		firstRow.add(buttons.get(actionProperties.getMethodName().getLogIn()));
		firstRow.add(buttons.get(actionProperties.getMethodName().getRegister()));

		KeyboardRow secondRow = new KeyboardRow();
		secondRow.add(buttons.get(actionProperties.getMethodName().getMain()));

		keyboard.add(firstRow);
		keyboard.add(secondRow);

		logInKeyboard.setKeyboard(keyboard);

		return logInKeyboard;
	}

	private ReplyKeyboardMarkup configKeyboard(Boolean selective, Boolean resizeKeyboard, Boolean oneTimeKeyboard) {

		ReplyKeyboardMarkup configedKeyboard = new ReplyKeyboardMarkup();

		configedKeyboard.setSelective(selective);
		configedKeyboard.setResizeKeyboard(resizeKeyboard);
		configedKeyboard.setOneTimeKeyboard(oneTimeKeyboard);

		return configedKeyboard;
	}
}
