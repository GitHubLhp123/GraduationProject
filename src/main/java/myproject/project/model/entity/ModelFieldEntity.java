package myproject.project.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "model_field", schema = "my_project", catalog = "")
public class ModelFieldEntity {
    private String columnId;
    private String columnName;
    private String modelId;
    private String modelName;
    private String modelSn;
    private String columnLabel;
    private String columnDescr;
    private String type;
    private Integer length;
    private Integer precisionVal;
    private Integer scale;
    private String isnullable;
    private Integer fieldPosi;
    private String stdName;
    private String defValue;
    private String primaryKey;
    private String partitionKey;
    private String distributedKey;
    private Integer distributeNum;
    private String distributeSortKey;
    private String fieldSensLvl;
    private String maskType;
    private String maskRule;
    private String sampling;
    private String segmentSeq;
    private String assetSn;
    private String busiCaliber;
    private String summary;
    private String extendItems;
    private String isDatatimeField;

    @Id
    @Column(name = "column_id", nullable = false, length = 32)
    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    @Basic
    @Column(name = "column_name", nullable = false, length = 64)
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Basic
    @Column(name = "model_id", nullable = false, length = 32)
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
    @Column(name = "model_sn", nullable = true, length = 64)
    public String getModelSn() {
        return modelSn;
    }

    public void setModelSn(String modelSn) {
        this.modelSn = modelSn;
    }

    @Basic
    @Column(name = "column_label", nullable = true, length = 256)
    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }

    @Basic
    @Column(name = "column_descr", nullable = true, length = 512)
    public String getColumnDescr() {
        return columnDescr;
    }

    public void setColumnDescr(String columnDescr) {
        this.columnDescr = columnDescr;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 30)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "length", nullable = true)
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Basic
    @Column(name = "precision_val", nullable = true)
    public Integer getPrecisionVal() {
        return precisionVal;
    }

    public void setPrecisionVal(Integer precisionVal) {
        this.precisionVal = precisionVal;
    }

    @Basic
    @Column(name = "scale", nullable = true)
    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    @Basic
    @Column(name = "isnullable", nullable = true, length = 1)
    public String getIsnullable() {
        return isnullable;
    }

    public void setIsnullable(String isnullable) {
        this.isnullable = isnullable;
    }

    @Basic
    @Column(name = "field_posi", nullable = true)
    public Integer getFieldPosi() {
        return fieldPosi;
    }

    public void setFieldPosi(Integer fieldPosi) {
        this.fieldPosi = fieldPosi;
    }

    @Basic
    @Column(name = "std_name", nullable = true, length = 32)
    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    @Basic
    @Column(name = "def_value", nullable = true, length = 64)
    public String getDefValue() {
        return defValue;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    @Basic
    @Column(name = "primary_key", nullable = true, length = 1)
    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Basic
    @Column(name = "partition_key", nullable = true, length = 1)
    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    @Basic
    @Column(name = "distributed_key", nullable = true, length = 1)
    public String getDistributedKey() {
        return distributedKey;
    }

    public void setDistributedKey(String distributedKey) {
        this.distributedKey = distributedKey;
    }

    @Basic
    @Column(name = "distribute_num", nullable = true)
    public Integer getDistributeNum() {
        return distributeNum;
    }

    public void setDistributeNum(Integer distributeNum) {
        this.distributeNum = distributeNum;
    }

    @Basic
    @Column(name = "distribute_sort_key", nullable = true, length = 1)
    public String getDistributeSortKey() {
        return distributeSortKey;
    }

    public void setDistributeSortKey(String distributeSortKey) {
        this.distributeSortKey = distributeSortKey;
    }

    @Basic
    @Column(name = "field_sens_lvl", nullable = true, length = 16)
    public String getFieldSensLvl() {
        return fieldSensLvl;
    }

    public void setFieldSensLvl(String fieldSensLvl) {
        this.fieldSensLvl = fieldSensLvl;
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
    @Column(name = "mask_rule", nullable = true, length = 16)
    public String getMaskRule() {
        return maskRule;
    }

    public void setMaskRule(String maskRule) {
        this.maskRule = maskRule;
    }

    @Basic
    @Column(name = "sampling", nullable = true, length = 128)
    public String getSampling() {
        return sampling;
    }

    public void setSampling(String sampling) {
        this.sampling = sampling;
    }

    @Basic
    @Column(name = "segment_seq", nullable = true, length = 1)
    public String getSegmentSeq() {
        return segmentSeq;
    }

    public void setSegmentSeq(String segmentSeq) {
        this.segmentSeq = segmentSeq;
    }

    @Basic
    @Column(name = "asset_sn", nullable = true, length = 16)
    public String getAssetSn() {
        return assetSn;
    }

    public void setAssetSn(String assetSn) {
        this.assetSn = assetSn;
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
    @Column(name = "summary", nullable = true, length = -1)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
    @Column(name = "is_datatime_field", nullable = true, length = 1)
    public String getIsDatatimeField() {
        return isDatatimeField;
    }

    public void setIsDatatimeField(String isDatatimeField) {
        this.isDatatimeField = isDatatimeField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelFieldEntity that = (ModelFieldEntity) o;
        return Objects.equals(columnId, that.columnId) &&
                Objects.equals(columnName, that.columnName) &&
                Objects.equals(modelId, that.modelId) &&
                Objects.equals(modelName, that.modelName) &&
                Objects.equals(modelSn, that.modelSn) &&
                Objects.equals(columnLabel, that.columnLabel) &&
                Objects.equals(columnDescr, that.columnDescr) &&
                Objects.equals(type, that.type) &&
                Objects.equals(length, that.length) &&
                Objects.equals(precisionVal, that.precisionVal) &&
                Objects.equals(scale, that.scale) &&
                Objects.equals(isnullable, that.isnullable) &&
                Objects.equals(fieldPosi, that.fieldPosi) &&
                Objects.equals(stdName, that.stdName) &&
                Objects.equals(defValue, that.defValue) &&
                Objects.equals(primaryKey, that.primaryKey) &&
                Objects.equals(partitionKey, that.partitionKey) &&
                Objects.equals(distributedKey, that.distributedKey) &&
                Objects.equals(distributeNum, that.distributeNum) &&
                Objects.equals(distributeSortKey, that.distributeSortKey) &&
                Objects.equals(fieldSensLvl, that.fieldSensLvl) &&
                Objects.equals(maskType, that.maskType) &&
                Objects.equals(maskRule, that.maskRule) &&
                Objects.equals(sampling, that.sampling) &&
                Objects.equals(segmentSeq, that.segmentSeq) &&
                Objects.equals(assetSn, that.assetSn) &&
                Objects.equals(busiCaliber, that.busiCaliber) &&
                Objects.equals(summary, that.summary) &&
                Objects.equals(extendItems, that.extendItems) &&
                Objects.equals(isDatatimeField, that.isDatatimeField);
    }

    @Override
    public int hashCode() {

        return Objects.hash(columnId, columnName, modelId, modelName, modelSn, columnLabel, columnDescr, type, length, precisionVal, scale, isnullable, fieldPosi, stdName, defValue, primaryKey, partitionKey, distributedKey, distributeNum, distributeSortKey, fieldSensLvl, maskType, maskRule, sampling, segmentSeq, assetSn, busiCaliber, summary, extendItems, isDatatimeField);
    }
}
