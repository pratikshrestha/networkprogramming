import java.net.*;
import java.util.*;

public class NetworkInterfaceInfo {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        ArrayList<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        System.out.println("Information about present Network Interfaces...\n");

        for (NetworkInterface iface : interfaces) {
            if (iface.isUp()) {
                System.out.println("Interface Name: " + iface.getName());
                System.out.println("Interface display name: " + iface.getDisplayName());
                System.out.println("Hardware Address: " + Arrays.toString(iface.getHardwareAddress()));
                System.out.println("Parent: " + iface.getParent());
                System.out.println("Index: " + iface.getIndex());

                System.out.println("\tInterface addresses: ");
                for (InterfaceAddress addr : iface.getInterfaceAddresses()) {
                    System.out.println("\t\t" + addr.getAddress());
                }

                System.out.println("\tInetAddresses associated with this interface: ");
                Enumeration<InetAddress> en = iface.getInetAddresses();
                while (en.hasMoreElements()) {
                    System.out.println("\t\t" + en.nextElement());
                }

                System.out.println("\tMTU: " + iface.getMTU());
                System.out.println("\tSubinterfaces: " + Collections.list(iface.getSubInterfaces()));
                System.out.println("\this loopback: " + iface.isLoopback());
                System.out.println("\this virtual: " + iface.isVirtual());
                System.out.println("\this point to point: " + iface.isPointToPoint());
                System.out.println("Supports Multicast: " + iface.supportsMulticast());
            }
        }

        NetworkInterface nif = NetworkInterface.getByIndex(1);
        System.out.println("Network interface 1: " + nif);

        NetworkInterface nif2 = NetworkInterface.getByName("eth0");
        InetAddress ip = InetAddress.getByName("localhost");
        NetworkInterface nif3 = NetworkInterface.getByInetAddress(ip);
        System.out.println("\nlocalhost associated with: " + nif3);

        boolean eq = nif.equals(nif2);
        System.out.println("nif==nif2: " + eq);
        System.out.println("Hashcode : " + nif.hashCode());
    }
}
