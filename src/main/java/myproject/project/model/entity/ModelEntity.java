package myproject.project.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "model", schema = "my_project", catalog = "")
public class ModelEntity {
    private String modelId;
    private String modelName;
    private String dsName;
    private String modelSchema;
    private String modelSchemaDev;
    private String modelLabel;
    private String modelDescr;
    private String logicModelName;
    private String phyModelName;
    private String modelSensLvl;
    private String maskType;
    private String lvl;
    private String topic;
    private String cycleType;
    private String modelSpace;
    private String modelSn;
    private String version;
    private String teamName;
    private String memberName;
    private String openState;
    private String assetSn;
    private String extendItems;
    private String busiCaliber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private Timestamp createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastupd;
    private String createUser;
    private String lastupdUser;
    private String state;
    private Timestamp stateDate;
    private String isShare;
    private String isTran;
    private String modelType;
    private String shareType;
    private Integer recommendCnt;
    private String storageTime;
    private String isPublic;
    private String ownership;
    private String extendClobConf;
    private String parentModelId;

    @Id
    @Column(name = "model_id", nullable = false, length = 128)
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "model_name", nullable = false, length = 64)
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Basic
    @Column(name = "ds_name", nullable = false, length = 32)
    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    @Basic
    @Column(name = "model_schema", nullable = true, length = 64)
    public String getModelSchema() {
        return modelSchema;
    }

    public void setModelSchema(String modelSchema) {
        this.modelSchema = modelSchema;
    }

    @Basic
    @Column(name = "model_schema_dev", nullable = true, length = 64)
    public String getModelSchemaDev() {
        return modelSchemaDev;
    }

    public void setModelSchemaDev(String modelSchemaDev) {
        this.modelSchemaDev = modelSchemaDev;
    }

    @Basic
    @Column(name = "model_label", nullable = true, length = 512)
    public String getModelLabel() {
        return modelLabel;
    }

    public void setModelLabel(String modelLabel) {
        this.modelLabel = modelLabel;
    }

    @Basic
    @Column(name = "model_descr", nullable = true, length = 1024)
    public String getModelDescr() {
        return modelDescr;
    }

    public void setModelDescr(String modelDescr) {
        this.modelDescr = modelDescr;
    }

    @Basic
    @Column(name = "logic_model_name", nullable = true, length = 64)
    public String getLogicModelName() {
        return logicModelName;
    }

    public void setLogicModelName(String logicModelName) {
        this.logicModelName = logicModelName;
    }

    @Basic
    @Column(name = "phy_model_name", nullable = true, length = 64)
    public String getPhyModelName() {
        return phyModelName;
    }

    public void setPhyModelName(String phyModelName) {
        this.phyModelName = phyModelName;
    }

    @Basic
    @Column(name = "model_sens_lvl", nullable = true, length = 64)
    public String getModelSensLvl() {
        return modelSensLvl;
    }

    public void setModelSensLvl(String modelSensLvl) {
        this.modelSensLvl = modelSensLvl;
    }

    @Basic
    @Column(name = "mask_type", nullable = true, length = 16)
    public String getMaskType() {
        return maskType;
    }

    public void setMaskType(String maskType) {
        this.maskType = maskType;
    }

    @Basic
    @Column(name = "lvl", nullable = true, length = 32)
    public String getLvl() {
        return lvl;
    }

    public void setLvl(String lvl) {
        this.lvl = lvl;
    }

    @Basic
    @Column(name = "topic", nullable = true, length = 32)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Basic
    @Column(name = "cycle_type", nullable = true, length = 1024)
    public String getCycleType() {
        return cycleType;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    @Basic
    @Column(name = "model_space", nullable = true, length = 16)
    public String getModelSpace() {
        return modelSpace;
    }

    public void setModelSpace(String modelSpace) {
        this.modelSpace = modelSpace;
    }

    @Basic
    @Column(name = "model_sn", nullable = true, length = 16)
    public String getModelSn() {
        return modelSn;
    }

    public void setModelSn(String modelSn) {
        this.modelSn = modelSn;
    }

    @Basic
    @Column(name = "version", nullable = true, length = 16)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "team_name", nullable = true, length = 64)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    @Column(name = "member_name", nullable = true, length = 128)
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Basic
    @Column(name = "open_state", nullable = true, length = 8)
    public String getOpenState() {
        return openState;
    }

    public void setOpenState(String openState) {
        this.openState = openState;
    }

    @Basic
    @Column(name = "asset_sn", nullable = true, length = 32)
    public String getAssetSn() {
        return assetSn;
    }

    public void setAssetSn(String assetSn) {
        this.assetSn = assetSn;
    }

    @Basic
    @Column(name = "extend_items", nullable = true, length = 512)
    public String getExtendItems() {
        return extendItems;
    }

    public void setExtendItems(String extendItems) {
        this.extendItems = extendItems;
    }

    @Basic
    @Column(name = "busi_caliber", nullable = true, length = 512)
    public String getBusiCaliber() {
        return busiCaliber;
    }

    public void setBusiCaliber(String busiCaliber) {
        this.busiCaliber = busiCaliber;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "lastupd", nullable = true)
    public Timestamp getLastupd() {
        return lastupd;
    }

    public void setLastupd(Timestamp lastupd) {
        this.lastupd = lastupd;
    }

    @Basic
    @Column(name = "create_user", nullable = true, length = 16)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "lastupd_user", nullable = true, length = 16)
    public String getLastupdUser() {
        return lastupdUser;
    }

    public void setLastupdUser(String lastupdUser) {
        this.lastupdUser = lastupdUser;
    }

    @Basic
    @Column(name = "state", nullable = false, length = 16)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "state_date", nullable = true)
    public Timestamp getStateDate() {
        return stateDate;
    }

    public void setStateDate(Timestamp stateDate) {
        this.stateDate = stateDate;
    }

    @Basic
    @Column(name = "is_share", nullable = true, length = 16)
    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    @Basic
    @Column(name = "is_tran", nullable = true, length = 16)
    public String getIsTran() {
        return isTran;
    }

    public void setIsTran(String isTran) {
        this.isTran = isTran;
    }

    @Basic
    @Column(name = "model_type", nullable = true, length = 16)
    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    @Basic
    @Column(name = "share_type", nullable = true, length = 16)
    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    @Basic
    @Column(name = "recommend_cnt", nullable = true)
    public Integer getRecommendCnt() {
        return recommendCnt;
    }

    public void setRecommendCnt(Integer recommendCnt) {
        this.recommendCnt = recommendCnt;
    }

    @Basic
    @Column(name = "storage_time", nullable = true, length = 128)
    public String getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(String storageTime) {
        this.storageTime = storageTime;
    }

    @Basic
    @Column(name = "is_public", nullable = true)
    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    @Basic
    @Column(name = "ownership", nullable = true, length = 64)
    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    @Basic
    @Column(name = "extend_clob_conf", nullable = true, length = -1)
    public String getExtendClobConf() {
        return extendClobConf;
    }

    public void setExtendClobConf(String extendClobConf) {
        this.extendClobConf = extendClobConf;
    }

    @Basic
    @Column(name = "parent_model_id", nullable = true, length = 32)
    public String getParentModelId() {
        return parentModelId;
    }

    public void setParentModelId(String parentModelId) {
        this.parentModelId = parentModelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelEntity that = (ModelEntity) o;
        return Objects.equals(modelId, that.modelId) &&
                Objects.equals(modelName, that.modelName) &&
                Objects.equals(dsName, that.dsName) &&
                Objects.equals(modelSchema, that.modelSchema) &&
                Objects.equals(modelSchemaDev, that.modelSchemaDev) &&
                Objects.equals(modelLabel, that.modelLabel) &&
                Objects.equals(modelDescr, that.modelDescr) &&
                Objects.equals(logicModelName, that.logicModelName) &&
                Objects.equals(phyModelName, that.phyModelName) &&
                Objects.equals(modelSensLvl, that.modelSensLvl) &&
                Objects.equals(maskType, that.maskType) &&
                Objects.equals(lvl, that.lvl) &&
                Objects.equals(topic, that.topic) &&
                Objects.equals(cycleType, that.cycleType) &&
                Objects.equals(modelSpace, that.modelSpace) &&
                Objects.equals(modelSn, that.modelSn) &&
                Objects.equals(version, that.version) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(memberName, that.memberName) &&
                Objects.equals(openState, that.openState) &&
                Objects.equals(assetSn, that.assetSn) &&
                Objects.equals(extendItems, that.extendItems) &&
                Objects.equals(busiCaliber, that.busiCaliber) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(lastupd, that.lastupd) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(lastupdUser, that.lastupdUser) &&
                Objects.equals(state, that.state) &&
                Objects.equals(stateDate, that.stateDate) &&
                Objects.equals(isShare, that.isShare) &&
                Objects.equals(isTran, that.isTran) &&
                Objects.equals(modelType, that.modelType) &&
                Objects.equals(shareType, that.shareType) &&
                Objects.equals(recommendCnt, that.recommendCnt) &&
                Objects.equals(storageTime, that.storageTime) &&
                Objects.equals(isPublic, that.isPublic) &&
                Objects.equals(ownership, that.ownership) &&
                Objects.equals(extendClobConf, that.extendClobConf) &&
                Objects.equals(parentModelId, that.parentModelId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(modelId, modelName, dsName, modelSchema, modelSchemaDev, modelLabel, modelDescr, logicModelName, phyModelName, modelSensLvl, maskType, lvl, topic, cycleType, modelSpace, modelSn, version, teamName, memberName, openState, assetSn, extendItems, busiCaliber, createDate, lastupd, createUser, lastupdUser, state, stateDate, isShare, isTran, modelType, shareType, recommendCnt, storageTime, isPublic, ownership, extendClobConf, parentModelId);
    }
}
