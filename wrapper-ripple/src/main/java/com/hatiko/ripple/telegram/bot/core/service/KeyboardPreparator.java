package com.hatiko.ripple.telegram.bot.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeyboardPreparator {

	private final ActionProperties actionProperties;
	private Map<String, KeyboardButton> buttons;

	@Getter
	private ReplyKeyboardMarkup startKeyboard;
	@Getter
	private ReplyKeyboardMarkup mainKeyboard;
	@Getter
	private ReplyKeyboardMarkup logInKeyboard;

	@PostConstruct
	private void prepareKeyboards() {
		createButtons();
		createStartKeyboard();
		createMainKeyboard();
		createLogInKeyboard();
	}

	private void createButtons() {

		buttons = new HashMap<String, KeyboardButton>();

		KeyboardButton helloButton = new KeyboardButton();
		helloButton.setText(actionProperties.getButtonOperation().getHello());
		buttons.put("hello", helloButton);

		KeyboardButton helpButton = new KeyboardButton();
		helpButton.setText(actionProperties.getButtonOperation().getHelp());
		buttons.put("help", helpButton);

		KeyboardButton nextButton = new KeyboardButton();
		nextButton.setText(actionProperties.getButtonOperation().getNext());
		buttons.put("next", nextButton);

		KeyboardButton mainButton = new KeyboardButton();
		mainButton.setText(actionProperties.getButtonOperation().getMain());
		buttons.put("main", mainButton);

		KeyboardButton registerButton = new KeyboardButton();
		registerButton.setText(actionProperties.getButtonOperation().getRegister());
		buttons.put("register", registerButton);
		
		KeyboardButton logInButton = new KeyboardButton();
		logInButton.setText(actionProperties.getButtonOperation().getLogIn());
		buttons.put("logIn", logInButton);

		KeyboardButton getBalanceButton = new KeyboardButton();
		getBalanceButton.setText(actionProperties.getButtonOperation().getGetBalance());
		buttons.put("getBalance", getBalanceButton);

		KeyboardButton generateMemoButton = new KeyboardButton();
		generateMemoButton.setText(actionProperties.getButtonOperation().getGenerateMemo());
		buttons.put("generateMemo", generateMemoButton);

		KeyboardButton getTransactionInfoButton = new KeyboardButton(
				actionProperties.getButtonOperation().getGetTransactionInfo());
		getTransactionInfoButton.setText(actionProperties.getButtonOperation().getGetTransactionInfo());
		buttons.put("getTransactionInfo", getTransactionInfoButton);

		KeyboardButton getLastTransactionsButton = new KeyboardButton(
				actionProperties.getButtonOperation().getGetLastTransactions());
		getLastTransactionsButton.setText(actionProperties.getButtonOperation().getGetLastTransactions());
		buttons.put("getLastTransactions", getLastTransactionsButton);

		KeyboardButton withdrawButton = new KeyboardButton(actionProperties.getButtonOperation().getWithdraw());
		withdrawButton.setText(actionProperties.getButtonOperation().getWithdraw());
		buttons.put("withdraw", withdrawButton);
	}

	private void createStartKeyboard() {

		startKeyboard = configKeyboard(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

		List<KeyboardRow> keyboard = new ArrayList<>();

		KeyboardRow firstRow = new KeyboardRow();
		firstRow.add(buttons.get("logIn"));

		KeyboardRow secondRow = new KeyboardRow();
		secondRow.add(buttons.get("next"));

		keyboard.add(firstRow);
		keyboard.add(secondRow);

		startKeyboard.setKeyboard(keyboard);
	}

	private void createMainKeyboard() {
		
		mainKeyboard = configKeyboard(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
		
		List<KeyboardRow> keyboard = new ArrayList<>();
		
		KeyboardRow firstRow = new KeyboardRow();
		firstRow.add(buttons.get("getBalance"));
		firstRow.add(buttons.get("getLastTransactions"));
		
		KeyboardRow secondRow = new KeyboardRow();
		secondRow.add(buttons.get("getTransactionInfo"));
		secondRow.add(buttons.get("generateMemo"));
		
		KeyboardRow thirdRow = new KeyboardRow();
		thirdRow.add(buttons.get("withdraw"));
		
		keyboard.add(firstRow);
		keyboard.add(secondRow);
		keyboard.add(thirdRow);
		
		mainKeyboard.setKeyboard(keyboard);
	}

	private void createLogInKeyboard() {
		logInKeyboard = configKeyboard(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
		
		List<KeyboardRow> keyboard = new ArrayList<>();
		
		KeyboardRow firstRow = new KeyboardRow();
		firstRow.add(buttons.get("logIn"));
		firstRow.add(buttons.get("register"));
		
		KeyboardRow secondRow = new KeyboardRow();
		secondRow.add(buttons.get("main"));
		
		
		keyboard.add(firstRow);
		keyboard.add(secondRow);
		
		logInKeyboard.setKeyboard(keyboard);
	}

	private ReplyKeyboardMarkup configKeyboard(Boolean selective, Boolean resizeKeyboard, Boolean oneTimeKeyboard) {

		ReplyKeyboardMarkup configedKeyboard = new ReplyKeyboardMarkup();

		configedKeyboard.setSelective(selective);
		configedKeyboard.setResizeKeyboard(resizeKeyboard);
		configedKeyboard.setOneTimeKeyboard(oneTimeKeyboard);

		return configedKeyboard;
	}
}
