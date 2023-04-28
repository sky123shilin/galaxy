package org.galaxy.common.enums;

import javax.net.ServerSocketFactory;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * 枚举中也可存在抽象方法，每个枚举对象必须实现
 * 相当有用
 * 以此类推，每个枚举也可以重写某个非抽象的方法
 */
public enum SocketTypeEnum {

    TCP("tcp"){
        @Override
        protected boolean isPortAvailable(int port) {
            try {
                ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(
                        port, 1, InetAddress.getByName("localhost"));
                serverSocket.close();
                return true;
            }
            catch (Exception ex) {
                return false;
            }
        }
    },
    UDP("udp"){
        @Override
        protected boolean isPortAvailable(int port) {
            try {
                DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("localhost"));
                socket.close();
                return true;
            }

            catch (Exception ex) {
                return false;
            }
        }
    };

    private final String name;

    SocketTypeEnum(String name){
        this.name = name;
    }

    protected abstract boolean isPortAvailable(int port);

}
