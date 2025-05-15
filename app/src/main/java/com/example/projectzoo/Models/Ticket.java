package com.example.projectzoo.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ticket implements Serializable {

    @SerializedName("ticket_id")
    private int ticketId;

    @SerializedName("price")
    private double price;

    @SerializedName("visit_date")
    private String visitDate;

    @SerializedName("type")
    private String type;

    @SerializedName("is_used")
    private Boolean isUsed;

    @SerializedName("description")
    private String description;

    @SerializedName("userId")
    private Integer userId;

    @SerializedName("idItem")
    private Integer idItem;

    @SerializedName("typeId")
    private Integer typeId;

    @SerializedName("ticketCode")
    private String ticketCode;

    // Геттеры и сеттеры

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", price=" + price +
                ", visitDate='" + visitDate + '\'' +
                ", type='" + type + '\'' +
                ", isUsed=" + isUsed +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", idItem=" + idItem +
                ", typeId=" + typeId +
                ", ticketCode='" + ticketCode + '\'' +
                '}';
    }
}
