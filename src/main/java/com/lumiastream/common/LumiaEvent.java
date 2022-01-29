package com.lumiastream.common;

public class LumiaEvent {
  private EventOrigin eventOrigin;
  private LumiaEventType eventType;
  private LumiaEventData eventData;

  public LumiaEvent(final EventOrigin eventOrigin, final LumiaEventType eventType,
      final LumiaEventData eventData) {
    this.eventOrigin = eventOrigin;
    this.eventType = eventType;
    this.eventData = eventData;
  }

  public EventOrigin getEventOrigin() {
    return eventOrigin;
  }

  public void setEventOrigin(final EventOrigin eventOrigin) {
    this.eventOrigin = eventOrigin;
  }

  public LumiaEventType getEventType() {
    return eventType;
  }

  public void setEventType(final LumiaEventType eventType) {
    this.eventType = eventType;
  }

  public LumiaEventData getEventData() {
    return eventData;
  }

  public void setEventData(final LumiaEventData eventData) {
    this.eventData = eventData;
  }
}
