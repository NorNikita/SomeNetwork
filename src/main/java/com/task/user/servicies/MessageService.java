package com.task.user.servicies;

import com.task.user.dto.use.modelmapper.MessageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MessageService {

    List<MessageDTO> getAllMessage();

    MessageDTO editMessage(Long id, MessageDTO messageDTO);

    MessageDTO createMessage(String text, String author, MultipartFile image) throws IOException;

    List<MessageDTO> getUserMessages(Long id);

    void deleteMessage(Long id);

    MessageDTO likeThis(Long userId, Long messageId);

    List<MessageDTO> getMessagesFromMySubscribe(Long userId);
}
