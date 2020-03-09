package com.task.user.rest;

import com.task.user.dto.LikeThisMessageDTO;
import com.task.user.dto.use.modelmapper.MessageDTO;
import com.task.user.servicies.MessageService;
import com.task.user.servicies.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/allMessage")
    public ResponseEntity<List<MessageDTO>> getAllMessage() {
        log.info("get allMessages");

        return ResponseEntity.ok(messageService.getAllMessage());
    }

    @PostMapping("/createMessage")
    public ResponseEntity addMessage(@RequestParam("text") String text,
                                     @RequestParam("author") String author,
                                     @RequestParam(value = "file", required = false) MultipartFile image) throws IOException {
        log.info("create new message! author: {} | text: {}\n", author, text);

        MessageDTO message = messageService.createMessage(text, author, image);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/messageUser/{id}")
    public ResponseEntity<List<MessageDTO>> getUserMessages(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getUserMessages(id));
    }

    @DeleteMapping("/deleteMessage/{id}")
    public ResponseEntity deleteMessage(@PathVariable Long id) {
        log.info("delete message with id: {}\n", id);

        messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/likeThisMessage")
    public ResponseEntity<MessageDTO> likeThisMessage(@RequestBody LikeThisMessageDTO dto) {
        log.info("like(or unlike) message with messageId = {}    BY user with id = {} \n", dto.getMessageId(), dto.getUserId());

        return ResponseEntity.ok(messageService.likeThis(dto.getUserId(), dto.getMessageId()));
    }

    @GetMapping("/getMessagesFromMySubscribe/{userId}")
    public ResponseEntity<List<MessageDTO>> getSubsriptionsMessages(@PathVariable Long userId) {
        log.info("get messages from subscription for userId {}", userId);

        return ResponseEntity.ok(messageService.getMessagesFromMySubscribe(userId));
    }

}
