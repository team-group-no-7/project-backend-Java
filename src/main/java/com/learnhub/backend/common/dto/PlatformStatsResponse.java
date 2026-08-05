package com.learnhub.backend.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class PlatformStatsResponse {

    @JsonProperty("total_users")
    private Long totalUsers;

    @JsonProperty("total_contents")
    private Long totalContents;

    @JsonProperty("total_revenue")
    private BigDecimal totalRevenue;

    @JsonProperty("health_index")
    private Integer healthIndex = 98;

    public PlatformStatsResponse() {
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getTotalContents() {
        return totalContents;
    }

    public void setTotalContents(Long totalContents) {
        this.totalContents = totalContents;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getHealthIndex() {
        return healthIndex;
    }

    public void setHealthIndex(Integer healthIndex) {
        this.healthIndex = healthIndex;
    }
}
