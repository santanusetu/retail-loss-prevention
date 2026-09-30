package com.sjsu.cmpe273.lparilogisticapp.pojo;


public class TripDetail {

    public static final String STATUS_PENDING = "No";
    public static final String STATUS_DELIVERED = "Yes";

    public static final String RISK_LOW = "low";
    public static final String RISK_MEDIUM = "medium";
    public static final String RISK_HIGH = "high";

    private String dropNo;
    private String deliveryTime;
    private String customerName;
    private String custAddress;
    private String phnNo;
    private String completionStatus;
    private String riskLevel;


    public String getDropNo() {
        return dropNo;
    }


    public void setDropNo(String dropNo) {
        this.dropNo = dropNo;
    }


    public String getDeliveryTime() {
        return deliveryTime;
    }


    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCustAddress() {
        return custAddress;
    }


    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }


    public String getPhnNo() {
        return phnNo;
    }


    public void setPhnNo(String phnNo) {
        this.phnNo = phnNo;
    }


    public String getCompletionStatus() {
        return completionStatus;
    }


    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }



    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public boolean isDelivered() {
        return STATUS_DELIVERED.equalsIgnoreCase(completionStatus);
    }
}
