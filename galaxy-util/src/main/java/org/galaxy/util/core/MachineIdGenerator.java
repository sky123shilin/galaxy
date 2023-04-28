package org.galaxy.util.core;

import java.net.NetworkInterface;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Enumeration;

public class MachineIdGenerator {
    private static final int MACHINE_IDENTIFIER_ID = createMachineIdentifier();

    public MachineIdGenerator() {}

    /**
     * 利用物理网卡的Mac地址生成机器ID
     * @return 机器ID的hash值
     */
    public static int generateMachineId() {
        return MACHINE_IDENTIFIER_ID;
    }

    private static int createMachineIdentifier() {
        int machinePiece;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            if (e != null) {
                while(e.hasMoreElements()) {
                    NetworkInterface ni = e.nextElement();
                    sb.append(ni.toString());
                    byte[] mac = ni.getHardwareAddress();
                    if (mac != null) {
                        ByteBuffer bb = ByteBuffer.wrap(mac);

                        try {
                            sb.append(bb.getChar());
                            sb.append(bb.getChar());
                            sb.append(bb.getChar());
                        } catch (BufferUnderflowException var7) {
                            var7.printStackTrace();
                        }
                    }
                }
            }
            machinePiece = sb.toString().hashCode();
        } catch (Throwable var8) {
            machinePiece = (new SecureRandom()).nextInt();
        }
        return machinePiece;
    }
}
