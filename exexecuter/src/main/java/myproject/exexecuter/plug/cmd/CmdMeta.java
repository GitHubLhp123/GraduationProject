package myproject.exexecuter.plug.cmd;

public class CmdMeta {


    String sourceDsName;
    String fileName;
    String cmdLine;

    public String getSourceDsName() {
        return sourceDsName;
    }

    public void setSourceDsName(String sourceDsName) {
        this.sourceDsName = sourceDsName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCmdLine() {
        return cmdLine;
    }

    public void setCmdLine(String cmdLine) {
        this.cmdLine = cmdLine;
    }

    public CmdMeta() {
    }

    public CmdMeta(String sourceDsName, String fileName, String cmdLine) {
        this.sourceDsName = sourceDsName;
        this.fileName = fileName;
        this.cmdLine = cmdLine;
    }



}
