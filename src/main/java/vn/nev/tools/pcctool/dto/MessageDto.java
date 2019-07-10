package vn.nev.tools.pcctool.dto;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import vn.nev.tools.pcctool.dto.View.BaseView;

public class MessageDto implements Serializable {

    private static final long serialVersionUID = -46322701639134932L;
    @JsonView(BaseView.class)
    private String messageCd;
    @JsonView(BaseView.class)
    private String messageContent;

    public MessageDto() {
    }

    public MessageDto(String messageCd) {
        this.messageCd = messageCd;
    }

    public MessageDto(String messageCd, String messageContent) {
        this.messageCd = messageCd;
        this.messageContent = messageContent;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
            "messageCd=" + messageCd +
            ", messageContent=" + messageContent +
            "}";
    }

    public String getMessageCd() {
        return messageCd;
    }

    public void setMessageCd(String messageCd) {
        this.messageCd = messageCd;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
