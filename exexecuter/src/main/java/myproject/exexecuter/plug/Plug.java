package myproject.exexecuter.plug;


import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2021-02-21 20:2:29
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Plug implements Comparable<Plug> {

    private List<Integer> coordinate;
    private int height;
    private String id;
    private Meta meta;
    private int width;
    private String label;
    private Map<String,String> data;
    private int num;

    public void setCoordinate(List<Integer> coordinate) {
        this.coordinate = coordinate;
    }

    public List<Integer> getCoordinate() {
        return coordinate;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setData(Map<String,String>  data) {
        this.data = data;
    }

    public Map<String,String>  getData() {
        return data;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public int compareTo(Plug o) {
        return this.num - o.num;
    }
}