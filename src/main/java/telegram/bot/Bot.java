package telegram.bot;

import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parser.Afirmations;
import parser.Facts;
import parser.Weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Bot  extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    final String STRERR = "Введена неверная команда.Пожалуйста, выберите действие.";
    final String CLOUD = EmojiParser.parseToUnicode(":cloud:");
    final String DIZZY = EmojiParser.parseToUnicode(":dizzy:");
    final String BULB = EmojiParser.parseToUnicode(":bulb:");

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            final Message MESSAGE = update.getMessage();
            final SendMessage RESPONSE = new SendMessage();
            final Long CHATId = MESSAGE.getChatId();
            RESPONSE.setChatId(CHATId);
            final String TEXT = MESSAGE.getText();
            setButtons(RESPONSE);

            try{
                if (TEXT.contains("Аффирмация")) {
                Afirmations afirmations = new Afirmations();
                    RESPONSE.setText(DIZZY + afirmations.getAfirmation());
            }
                if (TEXT.equals("Погода")) {
                Weather weather = new Weather();
                    RESPONSE.setText(CLOUD + weather.getWeather());
            }
                if (TEXT.equals("Факт")) {
                Facts facts = new Facts();
                    RESPONSE.setText(BULB + facts.getFact());
            }
                if (TEXT.equals("/start")) {
                    RESPONSE.setText(TEXT);
           } else if (!TEXT.equals("Факт") && !TEXT.equals("Погода") && !TEXT.equals("Аффирмация")) {
                    RESPONSE.setText(STRERR);
           }
                execute(RESPONSE);
                logger.info("Sent message \"{}\" to {}", TEXT, CHATId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", TEXT, CHATId, e.getMessage());

            }
            catch (IOException ex){
                logger.error(ex.getMessage());
            }
        }
    }
        public synchronized void setButtons (SendMessage sendMessage){
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(new KeyboardButton("Погода"));
            KeyboardRow keyboardSecondRow = new KeyboardRow();
            keyboardSecondRow.add(new KeyboardButton("Аффирмация"));
            keyboardSecondRow.add(new KeyboardButton("Факт"));
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);

    }

}


