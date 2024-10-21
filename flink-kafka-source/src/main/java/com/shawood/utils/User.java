package com.shawood.utils;

import java.util.Random;
import java.util.UUID;

public class User {
    public int id;
    public String uuid;
    public long timeStamp;
    public int type = -1;
    public int amount;

    public User() {
    }

    public User(int id, String uuid, long timeStamp) {
        this.id = id;
        this.uuid = uuid;
        this.timeStamp = timeStamp;
    }

    public User(int id, String uuid, long timeStamp, int type, int amount) {
        this.id = id;
        this.uuid = uuid;
        this.timeStamp = timeStamp;
        this.type = type;
        this.amount = amount;
    }

    public User genUser() {
        User user = new User(genRandomId(), genRandomUUID(), genRandomLocalTime(), genRandomType(), genRandomAmount());
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public int getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", timeStamp=" + timeStamp +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }

    public static int genRandomId() {
        Random random = new Random();
        int r = random.nextInt(100000000 - 1);
        if (r > 0) {
            return r;
        } else {
            return r;
        }
    }

    public static String genRandomUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

    public static long genRandomLocalTime() {
        return System.currentTimeMillis();
    }

    public static int genRandomType() {
        Random random = new Random();
        int r = random.nextInt(3);
        return r;
    }

    public static int genRandomAmount() {
        Random random = new Random();
        int r = random.nextInt(9);
        return r*100;
    }
}
