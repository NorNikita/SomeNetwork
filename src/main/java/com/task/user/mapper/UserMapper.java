package com.task.user.mapper;

import com.task.user.dto.use.modelmapper.UserDTO;
import com.task.user.entities.Message;
import com.task.user.entities.User;
import com.task.user.repositories.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
public class UserMapper extends AbstractMapper<User, UserDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageRepository messageRepository;

    public UserMapper() {
        super(User.class, UserDTO.class);
    }

    @PostConstruct
    public void setUp() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(dto -> {
                    dto.skip(UserDTO::setMessages);
                    dto.skip(UserDTO::setLikedMessages);
                    dto.skip(UserDTO::setFollowers);
                    dto.skip(UserDTO::setSubscriptions);
                })
                .setPostConverter(toDtoConverter());

        modelMapper.createTypeMap(UserDTO.class, User.class)
                .addMappings(entity -> {
                    entity.skip(User::setMessageList);
                    entity.skip(User::setLikedMessages);
                    entity.skip(User::setFollowers);
                })
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificField(User entity, UserDTO userDTO) {
        if (entity.getMessageList() != null) {
            userDTO.setMessages(entity.getMessageList().stream().map(message -> messageMapper.toDto(message)).collect(Collectors.toList()));
        }
        if(entity.getLikedMessages() != null) {
            userDTO.setLikedMessages(entity.getLikedMessages().stream().map(Message::getId).collect(Collectors.toList()));
        }
        if(entity.getFollowers() != null) {
            userDTO.setFollowers(entity.getFollowers().stream().map(User::getId).collect(Collectors.toList()));
        }
        if(entity.getSubscriptions() != null) {
            userDTO.setSubscriptions(entity.getSubscriptions().stream().map(User::getId).collect(Collectors.toList()));
        }
    }

    @Override
    void mapSpecificField(UserDTO dto, User entity) {
        if(dto.getMessages() != null) {
            entity.setMessageList(dto.getMessages().stream().map(message -> messageRepository.getOne(message.getId())).collect(Collectors.toList()));
        }
        if(dto.getLikedMessages() != null) {
            entity.setLikedMessages(dto.getLikedMessages().stream().map(id -> messageRepository.getOne(id)).collect(Collectors.toList()));
        }
    }
}
