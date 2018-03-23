package com.cloud.zauth.user.dto;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author weisen.yang@hand-china.com
 * @version 1.0
 * @Date 2017/7/18 10:57
 * @description
 */
public class BaseDTO {
    @Column
    private Date createDate;
    @Column
    private Integer createBy;
    @Column
    private Date lastUpdateDate;
    @Column
    private Integer lastUpdateBy;
    @Column
    private Integer objectVersionNumber;

    public Date getCreateDate() {
        return createDate;
    }

    public BaseDTO setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public BaseDTO setCreateBy(Integer createBy) {
        this.createBy = createBy;
        return this;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public BaseDTO setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public Integer getLastUpdateBy() {
        return lastUpdateBy;
    }

    public BaseDTO setLastUpdateBy(Integer lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    public Integer getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public BaseDTO setObjectVersionNumber(Integer objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
        return this;
    }
}
