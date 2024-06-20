package org.example;

public class Message {
    public MessageType type; //public MessageType type;: Typ wiadomości (MessageType).
    public String content; //public String content;: Treść wiadomości.

    public Message() {} //public Message() {}: Domyślny konstruktor.

    public Message(MessageType type, String content) { //public Message(MessageType type, String content): Konstruktor z parametrami.
        this.type = type; //this.type = type;: Inicjalizuje pole type.
        this.content = content; //this.content = content;: Inicjalizuje pole content.
    }

}
