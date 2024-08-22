package com.example.shipmentservicelight.letcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Device {
    String name;
    String id;
    String endpoint;
    String localIP;
    String allowedIPs;
    String latestHandshake;
    String transferReceived;
    String transferSent;
    boolean active;

    public Device(String name, String id, String endpoint, String localIP, String allowedIPs, String latestHandshake,
                  String transferReceived, String transferSent, boolean active) {
        this.name = name;
        this.id = id;
        this.endpoint = endpoint;
        this.localIP = localIP;
        this.allowedIPs = allowedIPs;
        this.latestHandshake = latestHandshake;
        this.transferReceived = transferReceived;
        this.transferSent = transferSent;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", localIP='" + localIP + '\'' +
                ", allowedIPs='" + allowedIPs + '\'' +
                ", latestHandshake='" + latestHandshake + '\'' +
                ", transferReceived='" + transferReceived + '\'' +
                ", transferSent='" + transferSent + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return active == device.active && Objects.equals(name, device.name) && Objects.equals(id, device.id) && Objects.equals(endpoint, device.endpoint) && Objects.equals(localIP, device.localIP) && Objects.equals(allowedIPs, device.allowedIPs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, endpoint, localIP, allowedIPs, active);
    }

    public List<String> changengedField(Device newDevice){
        List<String> list = new ArrayList<>();
        if(!(newDevice.active == this.active)) {
            list.add("Change active from "+ this.active+" to "+newDevice.active);
        }
        if(!newDevice.endpoint.equals(this.endpoint)) {
            list.add("Change endpoint from "+ this.endpoint+" to "+newDevice.endpoint);
        }
        if(!newDevice.localIP.equals(this.localIP)) {
            list.add("Change localIP from "+ this.localIP+" to "+newDevice.localIP);
        }
        if(!newDevice.allowedIPs.equals(this.allowedIPs)) {
            list.add("Change allowedIPs from "+ this.allowedIPs+" to "+newDevice.allowedIPs);
        }
        return list;
    }
}