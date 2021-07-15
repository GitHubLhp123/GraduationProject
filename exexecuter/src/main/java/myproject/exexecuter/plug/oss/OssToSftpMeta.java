package myproject.exexecuter.plug.oss;

public class OssToSftpMeta {

    String sourceDsName;
    String targetDsName;
    String fileNameList;
    String targetFilePath;

    public OssToSftpMeta() {
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }

    public void setTargetFilePath(String targetFilePath) {
        this.targetFilePath = targetFilePath;
    }

    public OssToSftpMeta(String sourceDsName, String targetDsName, String fileNameList, String targetFilePath) {
        this.sourceDsName = sourceDsName;
        this.targetDsName = targetDsName;
        this.fileNameList = fileNameList;
        this.targetFilePath = targetFilePath;
    }

    public String getSourceDsName() {
        return sourceDsName;
    }

    public void setSourceDsName(String sourceDsName) {
        this.sourceDsName = sourceDsName;
    }

    public String getTargetDsName() {
        return targetDsName;
    }

    public void setTargetDsName(String targetDsName) {
        this.targetDsName = targetDsName;
    }

    public String getFileNameList() {
        return fileNameList;
    }

    public void setFileNameList(String fileNameList) {
        this.fileNameList = fileNameList;
    }
}
