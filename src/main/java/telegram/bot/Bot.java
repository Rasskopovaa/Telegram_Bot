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
    String strErr="Введена неверная команда.Пожалуйста, выберите действие.";

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
        /*try {
            execute(new SendMessage().setChatId(update.getMessage().getChatId())
                    .setText("Hi!"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    */
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(chatId);
            String text = message.getText();
            setButtons(response);

            try{
            if(text.equals("Аффирмация")){
                Afirmations afirmations = new Afirmations();
                response.setText(afirmations.getAfirmation());
            }
            if(text.equals("Погода")){
                Weather weather = new Weather();
                response.setText(weather.getWeather());
            }
            if(text.equals("Факт")){
                Facts facts = new Facts();
                response.setText(facts.getFact());
            }
           if(text.equals("/start" )){
               response.setText(text);
           }
           else if (!text.equals("Факт") && !text.equals("Погода")&& !text.equals("Аффирмация")){
               response.setText(strErr);
           }
                execute(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());

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


