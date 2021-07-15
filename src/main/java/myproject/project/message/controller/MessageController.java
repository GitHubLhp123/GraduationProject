package myproject.project.message.controller;

import com.google.gson.Gson;
import myproject.project.message.entity.MessageEntity;
import myproject.project.message.entity.MessageInfoEntity;
import myproject.project.message.entity.MessageOperationEntity;
import myproject.project.message.repository.MessageInfoRepository;
import myproject.project.message.repository.MessageOperationRepository;
import myproject.project.message.repository.MessageRepository;
import myproject.project.model.entity.ModelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/message")
@Controller
public class MessageController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageInfoRepository messageInfoRepository;
    @Autowired
    MessageOperationRepository messageOperationRepository;
    @Autowired
    Gson gson;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @ResponseBody
    @GetMapping("/getMessageList")
    public Map<String, Object> getMessageList(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "topic", defaultValue = "") String topic,
            @RequestParam(value = "messageId", defaultValue = "") String messageId,
            @RequestParam(value = "state", defaultValue = "") String state,
            @RequestParam(value = "userName", defaultValue = "") String userName,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex
    ) {
        System.out.println();
        Map<String, Object> map = new HashMap<String, Object>();
        //后期优化  代码太混乱
        String sqlSelectData = "select * from(select id,topic,create_date createDate,create_user_name createUserName,current_user_name currentUserName,message_name messageName,title,state,message_id messageId,type from (SELECT m.*,'已解决' as type from message m where m.state='resolve' and m.create_user_name=@ UNION SELECT m.*,'待解决' as type from message m LEFT JOIN message_operation me on (m.message_id=me.message_id)  where m.state='unResolve' and me.user_name=@ GROUP BY m.message_id) finalyData)finalyData ";
        String sqlSelectCount = "select count(1) from (select id,topic,create_date createDate,create_user_name createUserName,current_user_name currentUserName,message_name messageName,title,state,message_id messageId,type from (SELECT m.*,'已解决' as type from message m where m.state='resolve' and m.create_user_name=@ UNION SELECT m.*,'待解决' as type from message m LEFT JOIN message_operation me on (m.message_id=me.message_id)  where m.state='unresolve' and me.user_name=@ GROUP BY m.message_id) finalyData)finalyData";

        sqlSelectData = sqlSelectData.replaceAll("@", "'" + userName + "'");
        sqlSelectCount = sqlSelectCount.replaceAll("@", "'" + userName + "'");

        String sqlWhere = "";
        sqlWhere += " where 1=1 and  ";
        sqlWhere += " title like'%" + title + "%' and";
        sqlWhere += " topic like'%" + topic + "%' and";
        sqlWhere += " messageId  like '%" + messageId + "%' and";
        sqlWhere += " state  like '%" + state + "%'  ";
        sqlSelectData += sqlWhere;
        sqlSelectCount += sqlWhere;
        sqlSelectData += " order by createDate desc limit " + (pageSize * (pageIndex - 1)) + " , " + pageSize * (pageIndex) + ";";
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sqlSelectData);
        Integer dataLength = jdbcTemplate.queryForObject(sqlSelectCount, Integer.class);
        System.out.println(sqlSelectCount);
        System.out.println("\r\n");
        System.out.println(sqlSelectData);
        map.put("status", 200);
        map.put("data", data);
        map.put("dataLength", dataLength);
        return map;
    }

    @ResponseBody
    @GetMapping("/getMessageInfoList")
    public Map<String, Object> getMessageInfoList(@RequestParam(value = "messageId", defaultValue = "") String messageId) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<MessageInfoEntity> messageInfoEntities = messageInfoRepository.findAllByMessageIdOrderByCreateDateDesc(messageId);

            map.put("data", messageInfoEntities);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @GetMapping("/resolveMessageByMessageId")
    public Map<String, Object> resolveMessageBymessageId(@RequestParam(value = "messageId", defaultValue = "") String messageId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            MessageEntity messageEntity = messageRepository.findByMessageId(messageId);
            messageEntity.setState("resolve");
            messageRepository.save(messageEntity);
            map.put("data", "已处理");
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }

        return map;
    }

    @ResponseBody
    @PostMapping(value = "createMessageInfo")
    public Map<String, Object> createMessageInfo(@RequestBody Map<String, Object> body) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("=======================body");
        System.out.println(body);
        try {
            if (body.get("type").toString().contains("add")) {
                //新增消息  需要同步到三个表上

                MessageEntity messageEntity = gson.fromJson(body.get("createMessage").toString(), MessageEntity.class);
                messageEntity.setState("unResolve");

                messageRepository.save(messageEntity);
                System.out.println(body.get("createMessage").toString());

                MessageInfoEntity messageInfoEntity = gson.fromJson(body.get("messageInfo").toString(), MessageInfoEntity.class);
                messageInfoRepository.save(messageInfoEntity);
                MessageOperationEntity messageOperationEntity1 = new MessageOperationEntity(messageEntity.getId() + "_ce", messageEntity.getMessageId(), messageEntity.getCreateUserName());

                messageOperationRepository.save(messageOperationEntity1);
                if (!messageEntity.getCreateUserName().equals(messageEntity.getCurrentUserName())) {//判断创建任务时 是否指派给自己
                    System.out.println("我进来了");
                    MessageOperationEntity messageOperationEntity2 = new MessageOperationEntity(messageEntity.getId() + "_cu", messageEntity.getMessageId(), messageEntity.getCurrentUserName());
                    System.out.println(messageOperationEntity2.toString());
                    messageOperationRepository.save(messageOperationEntity2);
                }
                map.put("data", "保存成功");
                map.put("status", 200);
            } else if (body.get("type").toString().contains("edit")) {
                MessageOperationEntity messageOperationEntity = gson.fromJson(body.get("messageOperation").toString(), MessageOperationEntity.class);
                messageOperationRepository.save(messageOperationEntity);
                MessageInfoEntity messageInfoEntity = gson.fromJson(body.get("messageInfo").toString(), MessageInfoEntity.class);
                messageInfoRepository.save(messageInfoEntity);
                MessageEntity messageEntity = messageRepository.findByMessageId(messageInfoEntity.getMessageId());
                messageEntity.setCurrentUserName(messageOperationEntity.getUserName());
                messageEntity.setState("unResolve");
                messageRepository.save(messageEntity);
                map.put("data", "保存成功");
                map.put("status", 200);
            }
        } catch (Exception e) {
            map.put("message2", e.toString());
            map.put("message", e.getMessage());
            map.put("status", 100);
        }

        return map;
    }


}
