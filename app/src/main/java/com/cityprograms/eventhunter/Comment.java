package com.cityprograms.eventhunter;

public class Comment {

    private String objectId;
    private String eventId;
    private String userName;
    private String comment;
    private int mark;

    public Comment(String objectId, String eventId, String userName, String comment, int mark) {
        this.objectId = objectId;
        this.eventId = eventId;
        this.userName = userName;
        this.comment = comment;
        this.mark = mark;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getUserName() {
        return userName;
    }

    public String getComment() {
        return comment;
    }

    public int getMark() {
        return mark;
    }
}
