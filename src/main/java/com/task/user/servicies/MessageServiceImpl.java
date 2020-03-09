package com.task.user.servicies;

import com.task.user.dto.use.modelmapper.MessageDTO;
import com.task.user.entities.Message;
import com.task.user.entities.User;
import com.task.user.mapper.MessageMapper;
import com.task.user.repositories.MessageRepository;
import com.task.user.repositories.UserRepository;
import com.task.user.repositories.specification.MessageSpecification;
import com.task.user.servicies.exceptions.MessageNotFoundException;
import com.task.user.utils.MessageUtil;
import com.task.user.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUtil imageUtil;

    @Override
    public List<MessageDTO> getAllMessage() {
        return messageRepository.findAll().stream()
                .sorted(Comparator.comparing(Message::getCreateDate).reversed())
                .map(message -> messageMapper.toDto(message)).collect(Collectors.toList());
    }

    @Override
    public MessageDTO editMessage(Long id, MessageDTO messageDTO) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
        message.setMessage(messageDTO.getMessage());
        return messageMapper.toDto(messageRepository.save(message));
    }

    @Override
    public MessageDTO createMessage(String text, String author, MultipartFile image) throws IOException {
        Message entity = messageMapper.toEntity(new MessageDTO(text, author));
        String name = imageUtil.upload(image);

        entity.setCreateDate(LocalDateTime.now());
        entity.setImageName(name);
        return messageMapper.toDto(messageRepository.save(entity));
    }

    @Override
    public List<MessageDTO> getUserMessages(Long id) {
        return messageRepository.findUserMessages(id).stream()
                .sorted(Comparator.comparing(Message::getCreateDate).reversed())
                .map(message -> messageMapper.toDto(message)).collect(Collectors.toList());
    }

    @Override
    public void deleteMessage(Long id) {
        imageUtil.deleteImage(messageRepository.getOne(id).getImageName());
        messageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MessageDTO likeThis(Long userId, Long messageId) {
        Message message = messageRepository.getOne(messageId);
        Optional<User> user = messageUtil.userAlreadyLikedThis(message, userId);
        boolean likeThis = false;

        if(user.isPresent()) {
            message.getLikesOfUsers().remove(user.get());
        } else {
            User one = userRepository.getOne(userId);
            likeThis = true;

            if (message.getLikesOfUsers() != null) {
                message.getLikesOfUsers().add(one);
            } else {
                message.setLikesOfUsers(Arrays.asList(one));
            }
        }

        MessageDTO messageDTO = messageMapper.toDto(messageRepository.save(message));
        messageDTO.setLikeThis(likeThis);
        return messageDTO;
    }

    @Override
    public List<MessageDTO> getMessagesFromMySubscribe(Long userId) {
        return messageRepository.findAll(MessageSpecification.getMessagesFromSubscribe(userId))
                .stream().map(message -> messageMapper.toDto(message)).collect(Collectors.toList());
    }
}
