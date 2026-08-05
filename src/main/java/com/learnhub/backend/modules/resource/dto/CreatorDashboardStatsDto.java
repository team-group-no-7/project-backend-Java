package com.learnhub.backend.modules.resource.dto;

/*
 * CreatorDashboardStatsDto — DTO returning aggregated analytics metrics for creator dashboard.
 */
public class CreatorDashboardStatsDto {

    private long totalResources;
    private long totalLearners;
    private double totalEarnings;

    // Default Constructor (Required by Jackson for JSON serialization)
    public CreatorDashboardStatsDto() {
    }

    // Parameterized Constructor
    public CreatorDashboardStatsDto(long totalResources, long totalLearners, double totalEarnings) {
        this.totalResources = totalResources;
        this.totalLearners = totalLearners;
        this.totalEarnings = totalEarnings;
    }

    // Getters and Setters
    public long getTotalResources() {
        return totalResources;
    }

    public void setTotalResources(long totalResources) {
        this.totalResources = totalResources;
    }

    public long getTotalLearners() {
        return totalLearners;
    }

    public void setTotalLearners(long totalLearners) {
        this.totalLearners = totalLearners;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    @Override
    public String toString() {
        return "CreatorDashboardStatsDto{" +
                "totalResources=" + totalResources +
                ", totalLearners=" + totalLearners +
                ", totalEarnings=" + totalEarnings +
                '}';
    }
}
