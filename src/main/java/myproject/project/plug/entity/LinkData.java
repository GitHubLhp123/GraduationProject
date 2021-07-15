package myproject.project.plug.entity;

import java.util.List;

public class LinkData {

    private String id;
    private String startId;
    private String endId;
    private List<Integer> startAt;
    private List<Integer> endAt;
    private String meta;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public List<Integer> getStartAt() {
        return startAt;
    }

    public void setStartAt(List<Integer> startAt) {
        this.startAt = startAt;
    }

    public List<Integer> getEndAt() {
        return endAt;
    }

    public void setEndAt(List<Integer> endAt) {
        this.endAt = endAt;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}
