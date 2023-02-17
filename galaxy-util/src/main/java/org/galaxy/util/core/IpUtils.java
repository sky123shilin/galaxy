package org.galaxy.util.core;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 关于IP的工具类
 *
 * 包括获取本地IP，可用端口等
 */
public class IpUtils {

    private static final String ANY_HOST = "0.0.0.0";
    private static final String LOCAL_HOST = "127.0.0.1";
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private static volatile InetAddress LOCAL_ADDRESS = null;

    /**
     * 从请求中获取客户端的地址IP
     * @param request  Http Request
     * @return    返回IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (Objects.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (Objects.isNull(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (Objects.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (Objects.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (Objects.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 校验是否是合法IP地址
     * @param address  校验地址
     * @return   布尔值
     */
    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        return (name != null && !ANY_HOST.equals(name)
                && !LOCAL_HOST.equals(name) && IP_PATTERN.matcher(name).matches());
    }

    /**
     * 校验是否是合法的IP6地址
     * @param address   校验地址
     * @return           布尔值
     */
    private static boolean isValidV6Address(Inet6Address address) {
        boolean preferIpv6 = Boolean.getBoolean("java.net.preferIPv6Addresses");
        if (!preferIpv6) {
            return false;
        }
        try {
            return address.isReachable(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static InetAddress normalizeV6Address(Inet6Address address) {
        String addr = address.getHostAddress();
        int i = addr.lastIndexOf('%');
        if (i > 0) {
            try {
                return InetAddress.getByName(addr.substring(0, i) + '%' + address.getScopeId());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return address;
    }

    /**
     * 获取本地的IP InetAddress
     * @return  返回InetAddress对象
     */
    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (localAddress instanceof Inet6Address) {
                Inet6Address address = (Inet6Address) localAddress;
                if (isValidV6Address(address)){
                    return normalizeV6Address(address);
                }
            } else if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (null == interfaces) {
                return localAddress;
            }
            while (interfaces.hasMoreElements()) {
                try {
                    NetworkInterface network = interfaces.nextElement();
                    Enumeration<InetAddress> addresses = network.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        try {
                            InetAddress address = addresses.nextElement();
                            if (address instanceof Inet6Address) {
                                Inet6Address v6Address = (Inet6Address) address;
                                if (isValidV6Address(v6Address)){
                                    return normalizeV6Address(v6Address);
                                }
                            } else if (isValidAddress(address)) {
                                return address;
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return localAddress;
    }

    /**
     * 获取本地的InetAddress
     * @return  返回本地的InetAddress对象
     */
    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getLocalAddress0();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    /**
     * 获取本地IP地址
     * @return  返回IP
     */
    public static String getIp(){
        return getLocalAddress().getHostAddress();
    }

    /**
     * 获得一个可用端口，参数是一个默认端口，从这个端口往上找
     * @param defaultPort   端口
     * @return   返回一个可用端口
     * @throws Exception   如果没找到，则抛出异常
     */
    public static int findAvailablePort(int defaultPort) throws Exception {
        int portTmp = defaultPort;
        while (portTmp < 65535) {
            if (isPortUsed(portTmp)) {
                return portTmp;
            }
            portTmp++;
        }
        portTmp = defaultPort - 1;
        while (portTmp > 0) {
            if (isPortUsed(portTmp)) {
                return portTmp;
            }
            portTmp--;
        }
        throw new Exception("no available port.");
    }

    /**
     * 判断一个端口是否已经被占用
     * @param port   端口
     * @return       返回布尔值，true表示未被占用，false表示被占用
     */
    public static boolean isPortUsed(int port) {
        boolean used = false;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            used = false;
        } catch (IOException e) {
            used = true;
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return !used;
    }
}
