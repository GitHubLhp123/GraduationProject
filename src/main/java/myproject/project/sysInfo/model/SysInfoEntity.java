package myproject.project.sysInfo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sys_info", schema = "my_project", catalog = "")
public class SysInfoEntity {
    private String hostName;
    private Integer cpuUser;
    private Integer systemTotalMemory;
    private String computerName;
    private Integer cpuSys;
    private String ip;
    private Integer cpuError;
    private Integer jvmTotalMemory;
    private Integer jvmFreeMemory;
    private Integer systemUsedMemory;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDt;

    @Basic
    @Column(name = "create_dt", nullable = true)
    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }

    private Integer diskFree;
    private Integer diskTotal;
    @Basic
    @Column(name = "disk_free", nullable = true)
    public Integer getDiskFree() {
        return diskFree;
    }

    public void setDiskFree(Integer diskFree) {
        this.diskFree = diskFree;
    }
    @Basic
    @Column(name = "disk_total", nullable = true)
    public Integer getDiskTotal() {
        return diskTotal;
    }

    public void setDiskTotal(Integer diskTotal) {
        this.diskTotal = diskTotal;
    }





    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
 //   private Timestamp createDt;
    private String id;

    @Basic
    @Column(name = "host_name", nullable = true, length = 128)
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Basic
    @Column(name = "cpu_user", nullable = true)
    public Integer getCpuUser() {
        return cpuUser;
    }

    public void setCpuUser(Integer cpuUser) {
        this.cpuUser = cpuUser;
    }

    @Basic
    @Column(name = "system_total_memory", nullable = true)
    public Integer getSystemTotalMemory() {
        return systemTotalMemory;
    }

    public void setSystemTotalMemory(Integer systemTotalMemory) {
        this.systemTotalMemory = systemTotalMemory;
    }

    @Basic
    @Column(name = "computer_name", nullable = true, length = 128)
    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    @Basic
    @Column(name = "cpu_sys", nullable = true)
    public Integer getCpuSys() {
        return cpuSys;
    }

    public void setCpuSys(Integer cpuSys) {
        this.cpuSys = cpuSys;
    }

    @Basic
    @Column(name = "ip", nullable = true, length = 50)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "cpu_error", nullable = true)
    public Integer getCpuError() {
        return cpuError;
    }

    public void setCpuError(Integer cpuError) {
        this.cpuError = cpuError;
    }

    @Basic
    @Column(name = "jvm_total_memory", nullable = true)
    public Integer getJvmTotalMemory() {
        return jvmTotalMemory;
    }

    public void setJvmTotalMemory(Integer jvmTotalMemory) {
        this.jvmTotalMemory = jvmTotalMemory;
    }

    @Basic
    @Column(name = "jvm_free_memory", nullable = true)
    public Integer getJvmFreeMemory() {
        return jvmFreeMemory;
    }

    public void setJvmFreeMemory(Integer jvmFreeMemory) {
        this.jvmFreeMemory = jvmFreeMemory;
    }

    @Basic
    @Column(name = "system_used_memory", nullable = true)
    public Integer getSystemUsedMemory() {
        return systemUsedMemory;
    }

    public void setSystemUsedMemory(Integer systemUsedMemory) {
        this.systemUsedMemory = systemUsedMemory;
    }

//    @Basic
//    @Column(name = "create_dt", nullable = false)
//    public Timestamp getCreateDt() {
//        return createDt;
//    }
//
//    public void setCreateDt(Timestamp createDt) {
//        this.createDt = createDt;
//    }

    @Id
    @Column(name = "id", nullable = false, length = 200)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysInfoEntity that = (SysInfoEntity) o;
        return Objects.equals(hostName, that.hostName) &&
                Objects.equals(cpuUser, that.cpuUser) &&
                Objects.equals(systemTotalMemory, that.systemTotalMemory) &&
                Objects.equals(computerName, that.computerName) &&
                Objects.equals(cpuSys, that.cpuSys) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(cpuError, that.cpuError) &&
                Objects.equals(jvmTotalMemory, that.jvmTotalMemory) &&
                Objects.equals(jvmFreeMemory, that.jvmFreeMemory) &&
                Objects.equals(systemUsedMemory, that.systemUsedMemory) &&
             //   Objects.equals(createDt, that.createDt) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(hostName, cpuUser, systemTotalMemory, computerName, cpuSys, ip, cpuError, jvmTotalMemory, jvmFreeMemory, systemUsedMemory, id);
    }
}
