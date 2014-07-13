package com.autentia.tutorial.gcm.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;

@XmlRootElement
@XmlType(propOrder = {"title", "message", "badge", "registrationIdsToSend"})
public class Notification {

    private Integer badge;

    private String title;

    private String message;

    private String[] registrationIdsToSend;

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getRegistrationIdsToSend() {
        return registrationIdsToSend;
    }

    public void setRegistrationIdsToSend(String[] registrationIdsToSend) {
        this.registrationIdsToSend = registrationIdsToSend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }

        final Notification other = (Notification) o;
        return new EqualsBuilder().append(this.badge, other.getBadge()).append(this.title, other.getTitle()).
                append(this.message, other.getMessage()).
                append(this.registrationIdsToSend, this.getRegistrationIdsToSend()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.badge).append(this.title).append(this.message).
                append(this.registrationIdsToSend).hashCode();
    }

    @Override
    public String toString() {
        return "Notification{" +
                "badge='" + badge + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", registrationIdsToSend=" + (registrationIdsToSend == null ? null : Arrays.asList(registrationIdsToSend)) +
                '}';
    }
}
