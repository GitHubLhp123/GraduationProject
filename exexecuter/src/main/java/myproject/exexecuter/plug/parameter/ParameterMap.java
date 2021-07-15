package myproject.exexecuter.plug.parameter;

public class ParameterMap {
    @Override
    public String toString() {
        return "{" +
                "k='" + k + '\'' +
                ", v='" + v + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String k;
    public String v;
    public String type;

    public ParameterMap() {
    }

    public ParameterMap(String data) {
        String[] dataArray = data.split("=|,");
        System.out.println(dataArray[1]);
        System.out.println(dataArray[3]);
        System.out.println(dataArray[5]);
        this.k = dataArray[1];
        this.v = dataArray[3];
        this.type = dataArray[5];

    }

    public ParameterMap(String k, String v, String type) {
        this.k = k;
        this.v = v;
        this.type = type;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public Object getValue() {


        if (this.type.equals("string")) {
            return v + "";
        } else if (this.type.equals("int")) {
            return Integer.parseInt(v);
        } else if (this.type.equals("long")) {
            return Long.parseLong(v);
        } else if (this.type.equals("double")) {
            return Double.parseDouble(v);
        } else {
            return v;
        }


    }

    public void setV(String v) {
        this.v = v;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
