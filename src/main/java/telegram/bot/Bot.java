package telegram.bot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot  extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        /*try {
            execute(new SendMessage().setChatId(update.getMessage().getChatId())
                    .setText("Hi!"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }*/
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(chatId);
            String text = message.getText();
            response.setText(text);
            try {
                execute(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }
    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}

