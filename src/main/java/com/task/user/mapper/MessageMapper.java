package com.task.user.mapper;

import com.task.user.dto.use.modelmapper.MessageDTO;
import com.task.user.entities.Message;
import com.task.user.repositories.UserRepository;
import com.task.user.servicies.UserDetailsServiceImpl;
import com.task.user.utils.MessageUtil;
import com.task.user.utils.ImageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MessageMapper extends AbstractMapper<Message, MessageDTO> {

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public MessageMapper() {
        super(Message.class, MessageDTO.class);
    }

    @PostConstruct
    public void setUp() {
        modelMapper.createTypeMap(Message.class, MessageDTO.class)
                .addMappings(dto -> {
                    dto.skip(MessageDTO::setLogin);
                    dto.skip(MessageDTO::setCountLike);
                    dto.skip(MessageDTO::setLikeThis);
                    dto.skip(MessageDTO::setCreateDate);
                    dto.skip(MessageDTO::setImage);
                })
                .setPostConverter(toDtoConverter());

        modelMapper.createTypeMap(MessageDTO.class, Message.class)
                .addMappings(entity -> {
                    entity.skip(Message::setUser);
                    entity.skip(Message::setLikesOfUsers);
                    entity.skip(Message::setCreateDate);
                })
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificField(Message entity, MessageDTO dto) {
        dto.setLogin(entity.getUser().getLogin());
        dto.setCountLike((long) entity.getLikesOfUsers().size());
        dto.setLikeThis(messageUtil.userAlreadyLikedThis(entity, userDetailsService.getCurrentId()).isPresent());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dto.setCreateDate(entity.getCreateDate().format(formatter));

        if(entity.getImageName() != null) {
            dto.setImage(imageUtil.encodeImageToBase64(entity.getImageName()));
        }
    }

    @Override
    void mapSpecificField(MessageDTO dto, Message entity) {
        entity.setUser(userRepository.findByLogin(dto.getLogin()).orElseThrow(() -> new UsernameNotFoundException(dto.getLogin())));
    }
}
