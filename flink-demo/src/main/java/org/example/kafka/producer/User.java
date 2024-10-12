package org.example.kafka.producer;

import java.util.Random;
import java.util.UUID;

public class User {
    public int id;
    public String uuid;
    public long timeStamp;

    public User() {
    }

    public User(int id, String uuid, long timeStamp) {
        this.id = id;
        this.uuid = uuid;
        this.timeStamp = timeStamp;
    }

    public User genUser() {
        User user = new User(genRandomId(), genRandomUUID(), genRandomLocalTime());
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

    public int getId() {
        return id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", timeStamp=" + timeStamp +
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
}
