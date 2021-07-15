package myproject.project.ds.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "team_ds", schema = "my_project", catalog = "")
public class TeamDsEntity {
    private String relaId;
    private String dsName;
    private String tenantName;
    private String dsSchema;
    private String extendCfg;
    private String dsAcct;
    private String dsAuth;
    private String dsAuthType;
    private String dsConf;
    private String state;
    private String dsUser;
    private String dsPassword;
    private String dsUrl;
    private String dsType;
    private String teamName;
    private Byte isPush;
    private String dsInstLoc;
    private String dsId;
    private String url;

    @Id
    @Column(name = "rela_id", nullable = false, length = 128)
    public String getRelaId() {
        return relaId;
    }

    public void setRelaId(String relaId) {
        this.relaId = relaId;
    }

    @Basic
    @Column(name = "ds_name", nullable = true, length = 32)
    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    @Basic
    @Column(name = "tenant_name", nullable = true, length = 32)
    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    @Basic
    @Column(name = "ds_schema", nullable = true, length = 256)
    public String getDsSchema() {
        return dsSchema;
    }

    public void setDsSchema(String dsSchema) {
        this.dsSchema = dsSchema;
    }

    @Basic
    @Column(name = "extend_cfg", nullable = true, length = 1024)
    public String getExtendCfg() {
        return extendCfg;
    }

    public void setExtendCfg(String extendCfg) {
        this.extendCfg = extendCfg;
    }

    @Basic
    @Column(name = "ds_acct", nullable = true, length = 64)
    public String getDsAcct() {
        return dsAcct;
    }

    public void setDsAcct(String dsAcct) {
        this.dsAcct = dsAcct;
    }

    @Basic
    @Column(name = "ds_auth", nullable = true, length = 256)
    public String getDsAuth() {
        return dsAuth;
    }

    public void setDsAuth(String dsAuth) {
        this.dsAuth = dsAuth;
    }

    @Basic
    @Column(name = "ds_auth_type", nullable = true, length = 16)
    public String getDsAuthType() {
        return dsAuthType;
    }

    public void setDsAuthType(String dsAuthType) {
        this.dsAuthType = dsAuthType;
    }

    @Basic
    @Column(name = "ds_conf", nullable = true, length = -1)
    public String getDsConf() {
        return dsConf;
    }

    public void setDsConf(String dsConf) {
        this.dsConf = dsConf;
    }

    @Basic
    @Column(name = "state", nullable = true, length = 8)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "ds_user", nullable = true, length = 256)
    public String getDsUser() {
        return dsUser;
    }

    public void setDsUser(String dsUser) {
        this.dsUser = dsUser;
    }

    @Basic
    @Column(name = "ds_password", nullable = true, length = 256)
    public String getDsPassword() {
        return dsPassword;
    }

    public void setDsPassword(String dsPassword) {
        this.dsPassword = dsPassword;
    }

    @Basic
    @Column(name = "ds_url", nullable = true, length = 5000)
    public String getDsUrl() {
        return dsUrl;
    }

    public void setDsUrl(String dsUrl) {
        this.dsUrl = dsUrl;
    }

    @Basic
    @Column(name = "ds_type", nullable = true, length = 10)
    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    @Basic
    @Column(name = "team_name", nullable = true, length = 32)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    @Column(name = "is_push", nullable = true)
    public Byte getIsPush() {
        return isPush;
    }

    public void setIsPush(Byte isPush) {
        this.isPush = isPush;
    }

    @Basic
    @Column(name = "ds_inst_loc", nullable = true, length = 125)
    public String getDsInstLoc() {
        return dsInstLoc;
    }

    public void setDsInstLoc(String dsInstLoc) {
        this.dsInstLoc = dsInstLoc;
    }

    @Basic
    @Column(name = "ds_id", nullable = true, length = 128)
    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId;
    }

    @Basic
    @Column(name = "url", nullable = true, length = 256)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamDsEntity that = (TeamDsEntity) o;
        return Objects.equals(relaId, that.relaId) &&
                Objects.equals(dsName, that.dsName) &&
                Objects.equals(tenantName, that.tenantName) &&
                Objects.equals(dsSchema, that.dsSchema) &&
                Objects.equals(extendCfg, that.extendCfg) &&
                Objects.equals(dsAcct, that.dsAcct) &&
                Objects.equals(dsAuth, that.dsAuth) &&
                Objects.equals(dsAuthType, that.dsAuthType) &&
                Objects.equals(dsConf, that.dsConf) &&
                Objects.equals(state, that.state) &&
                Objects.equals(dsUser, that.dsUser) &&
                Objects.equals(dsPassword, that.dsPassword) &&
                Objects.equals(dsUrl, that.dsUrl) &&
                Objects.equals(dsType, that.dsType) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(isPush, that.isPush) &&
                Objects.equals(dsInstLoc, that.dsInstLoc) &&
                Objects.equals(dsId, that.dsId) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(relaId, dsName, tenantName, dsSchema, extendCfg, dsAcct, dsAuth, dsAuthType, dsConf, state, dsUser, dsPassword, dsUrl, dsType, teamName, isPush, dsInstLoc, dsId, url);
    }
}
