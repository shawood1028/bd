package com.shawood.utils;

public class ClientData {
    public String time;
    public String _track_id;
    public String event;
    public String _flush_time;
    public String distinct_id;
    public String login_id;
    public String anonymous_id;
    public Properties properties;
    public String type;
    public Lib lib;
    public String project;
    public String user_id;
    public String flush_time;

    public Lib getLib() {
        return lib;
    }

    public void setLib(Lib lib) {
        this.lib = lib;
    }

    public String get_track_id() {
        return _track_id;
    }

    public void set_track_id(String _track_id) {
        this._track_id = _track_id;
    }

    public String get_flush_time() {
        return _flush_time;
    }

    public void set_flush_time(String _flush_time) {
        this._flush_time = _flush_time;
    }

    public String getDistinct_id() {
        return distinct_id;
    }

    public void setDistinct_id(String distinct_id) {
        this.distinct_id = distinct_id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getAnonymous_id() {
        return anonymous_id;
    }

    public void setAnonymous_id(String anonymous_id) {
        this.anonymous_id = anonymous_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFlush_time() {
        return flush_time;
    }

    public void setFlush_time(String flush_time) {
        this.flush_time = flush_time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public void setEvent(String event) {
        this.event = event;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getEvent() {
        return event;
    }

    public Properties getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "ClientData{" +
                "time='" + time + '\'' +
                ", _track_id='" + _track_id + '\'' +
                ", event='" + event + '\'' +
                ", _flush_time='" + _flush_time + '\'' +
                ", distinct_id='" + distinct_id + '\'' +
                ", login_id='" + login_id + '\'' +
                ", anonymous_id='" + anonymous_id + '\'' +
                ", properties=" + properties +
                ", type='" + type + '\'' +
                ", lib=" + lib +
                ", project='" + project + '\'' +
                ", user_id='" + user_id + '\'' +
                ", flush_time='" + flush_time + '\'' +
                '}';
    }
}
