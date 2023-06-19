package ru.voting.emails;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.voting.common.Participant;
import ru.voting.common.Poll;

@Component
@AllArgsConstructor
public class PollNotifier {
    private final EmailService emailService;


    private final static String INVITE_SUBJECT = "Вас пригласили пройти опрос";
    private final static String INVITE_TEXT_PATTERN = "Вас пригласил %s пройти опрос. Ваша персональная ссылка: http://localhost:3000/choose_answer?password=%s";
    private final static String POLL_CLOSING_SUBJECT_PATTERN = "Опрос \"%s\" завершен";
    private final static String POLL_CLOSING_TEXT_PATTERN = "Ваш опрос \"%s\" пройден всеми участниками";

    public void notifyPollParticipants(Poll poll) {
        for (Participant participant : poll.getParticipants()) {
            String text = INVITE_TEXT_PATTERN.formatted(poll.getCreatorEmail(), participant.getPassword());
            emailService.sendMessage(participant.getEmail(), INVITE_SUBJECT, text);
        }
    }

    public void notifyPollClosing(Poll poll) {
        String subject = POLL_CLOSING_SUBJECT_PATTERN.formatted(poll.getQuestion());
        String text = POLL_CLOSING_TEXT_PATTERN.formatted(poll.getQuestion());
        emailService.sendMessage(poll.getCreatorEmail(), subject, text);
    }
}
