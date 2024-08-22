package com.example.shipmentservicelight.letcode;

import java.util.ArrayList;
import java.util.List;

public class DeviceParser {
    public static void main(String[] args) {
        String input = "Active KOK_MAC:\n" +
                "  ID: 6R+0g9Ti7RHdCkL2n3koi1om+FYdm7T7vIbJ7K7vJxk=\n" +
                "  Endpoint: 188.243.141.197:62061\n" +
                "  Local IP: 10.7.0.5/32\n" +
                "  Allowed IPs: ALL\n" +
                "  Latest Handshake: 47 seconds ago\n" +
                "  Transfer: 661.71 MiB received, 6.13 GiB sent\n" +
                "  Active: True\n" +
                "\n" +
                "Active Vika_MAC2:\n" +
                "  ID: U+R6iv3LP+FZeEsnkCVmb508sEJWxXhTJqs8ohGjiVg=\n" +
                "  Endpoint: 188.243.141.197:59661\n" +
                "  Local IP: 10.7.0.3/32\n" +
                "  Allowed IPs: ALL\n" +
                "  Latest Handshake: 1 minute, 8 seconds ago\n" +
                "  Transfer: 408.44 MiB received, 1.92 GiB sent\n" +
                "  Active: True\n" +
                "\n" +
                "Active ORHON:\n" +
                "  ID: sJ0bK3srEMXVNNmU6St4Y1Tc7/GxZvGUx8aq4RbHtDQ=\n" +
                "  Endpoint: 188.243.141.197:54916\n" +
                "  Local IP: 10.7.0.6/32\n" +
                "  Allowed IPs: ALL\n" +
                "  Latest Handshake: 1 minute, 47 seconds ago\n" +
                "  Transfer: 133.32 MiB received, 698.62 MiB sent\n" +
                "  Active: True\n" +
                "\n" +
                "Active EVGENIY_LAPTOP:\n" +
                "  ID: Z5wZY0Zq1cfubtua+iaNJv/02XmYxhkP2hd7cNVyFng=\n" +
                "  Endpoint: 88.201.251.52:58915\n" +
                "  Local IP: 10.7.0.9/32\n" +
                "  Allowed IPs: ALL\n" +
                "  Latest Handshake: 1 minute, 49 seconds ago\n" +
                "  Transfer: 4.68 GiB received, 3.44 GiB sent\n" +
                "  Active: True\n" +
                "\n" +
                "Inactive ALEX_LAPTOP:\n" +
                "  ID: kr6rsKGyFjx/HHYRCo6zlRYf2US6K9lBRR+4JUPgnQQ=\n" +
                "  Endpoint: 178.71.8.189:47893\n" +
                "  Local IP: 10.7.0.10/32\n" +
                "  Allowed IPs: ALL\n" +
                "  Latest Handshake: 2 days, 52 minutes, 17 seconds ago\n" +
                "  Transfer: 11.47 MiB received, 21.59 MiB sent\n" +
                "  Active: False\n" +
                "\n" +
                "Inactive VIK_IPAD:\n" +
                "  ID: 5QxdigzWU6c/Yd4kD7cETh2Ipz6Fv06YJQBHXkt2bD0=\n" +
                "  Endpoint: 93.92.202.77:62101\n" +
                "  Local IP: 10.7.0.11/32\n" +
                "  Allowed IPs: N/A\n" +
                "  Latest Handshake: 2 days, 1 hour, 42 minutes, 56 seconds ago\n" +
                "  Transfer: 15.65 KiB received, 48.64 KiB sent\n" +
                "  Active: False\n" +
                "\n" +
                "Inactive VIKA_MAC:\n" +
                "  ID: aJNHYf5yTXLNZFzeQ98aVJuhjycK7ci6LoToDVDNulc=\n" +
                "  Endpoint: N/A\n" +
                "  Local IP: 10.7.0.2/32\n" +
                "  Allowed IPs: N/A\n" +
                "  Latest Handshake: N/A\n" +
                "  Transfer: N/A received, N/A sent\n" +
                "  Active: False\n" +
                "\n" +
                "Inactive Vik_mobile:\n" +
                "  ID: RFDqKjluatO9Ro5Utf3hUPq/0UxNXb95D1HBUVTtETU=\n" +
                "  Endpoint: N/A\n" +
                "  Local IP: 10.7.0.4/32\n" +
                "  Allowed IPs: N/A\n" +
                "  Latest Handshake: N/A\n" +
                "  Transfer: N/A received, N/A sent\n" +
                "  Active: False\n" +
                "\n" +
                "Inactive KOK_MOBILE_11:\n" +
                "  ID: lRt/mOUDkaq7TV9s38GZX995F6jxnPKbkMi7xkXHyX4=\n" +
                "  Endpoint: N/A\n" +
                "  Local IP: 10.7.0.8/32\n" +
                "  Allowed IPs: ALL\n" +
                "  Latest Handshake: N/A\n" +
                "  Transfer: N/A received, N/A sent\n" +
                "  Active: False\n" +
                "\n" +
                "Inactive VIK_MOBILE:\n" +
                "  ID: LAk9Axw5uXNOw6B7QXmFaJkL9vFI2OoIMtVpXmX9dkI=\n" +
                "  Endpoint: N/A\n" +
                "  Local IP: 10.7.0.12/32\n" +
                "  Allowed IPs: N/A\n" +
                "  Latest Handshake: N/A\n" +
                "  Transfer: N/A received, N/A sent\n" +
                "  Active: False\n" +
                "\n";

        List<Device> devices = parseDevices(input);
        for (Device device : devices) {
            System.out.println(device);
        }
    }

    public static List<Device> parseDevices(String input) {
        List<Device> devices = new ArrayList<>();
        String[] lines = input.split("\n");
        Device currentDevice = null;

        for (String line : lines) {
            if (line.startsWith("Active ") || line.startsWith("Inactive ")) {
                if (currentDevice != null) {
                    devices.add(currentDevice);
                }
                String[] parts = line.split(" ", 2);
                String name = parts[1].trim();
                currentDevice = new Device(name, null, null, null, null, null, null, null, parts[0].equals("Active"));
            } else if (currentDevice != null) {
                String[] parts = line.split(": ", 2);
                if (parts.length < 2) {
                    continue;
                }
                String key = parts[0].trim();
                String value = parts[1].trim();
                switch (key) {
                    case "ID":
                        currentDevice.id = value;
                        break;
                    case "Endpoint":
                        currentDevice.endpoint = value;
                        break;
                    case "Local IP":
                        currentDevice.localIP = value;
                        break;
                    case "Allowed IPs":
                        currentDevice.allowedIPs = value;
                        break;
                    case "Latest Handshake":
                        currentDevice.latestHandshake = value;
                        break;
                    case "Transfer":
                        String[] transferParts = value.split(", ");
                        currentDevice.transferReceived = transferParts[0].split(" ")[0];
                        currentDevice.transferSent = transferParts[1].split(" ")[0];
                        break;
                }
            }
        }

        if (currentDevice != null) {
            devices.add(currentDevice);
        }

        return devices;
    }
}