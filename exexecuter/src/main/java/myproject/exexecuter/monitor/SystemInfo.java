package myproject.exexecuter.monitor;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hyperic.sigar.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * ClassName: SystemInfo <br/>
 * Function: 获取服务器信息. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月25日 下午3:50:13 <br/>
 *
 * @author liuweiying
 * @since JDK 1.6
 */
public class SystemInfo {
    public static final Logger logger = Logger.getLogger(SystemInfo.class);
    static HashMap<String, Object> hashMap = new HashMap<>();


    public static void main(String[] args) {
        try {
            System.out.println(System.getProperty("java.library.path"));

            System.out.println("-----------System信息，从jvm获取如下-----------------------");
            // System信息，从jvm获取
            property();

            System.out.println("------------cpu信息如下----------------------");
            // cpu信息
            cpu();

            System.out.println("------------内存信息如下----------------------");
            // 内存信息
            memory();

            System.out.println("------------操作系统信息如下----------------------");
            // 操作系统信息
            os();

            System.out.println("-------------用户信息如下---------------------");
            // 用户信息
            who();

            System.out.println("--------------文件系统信息如下--------------------");
            // 文件系统信息
            file();

            System.out.println("-----------网络信息如下-----------------------");
            // 网络信息
            net();

            System.out.println("------------以太网信息如下----------------------");
            // 以太网信息
            ethernet();
            Gson gson=new Gson();
            System.out.println(gson.toJson(SystemInfo.hashMap));

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * property:(System信息，从jvm获取). <br/>
     *
     * @throws UnknownHostException
     * @author liuweiying
     * @since JDK 1.6
     */
    public static void property() throws UnknownHostException {

        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        InetAddress addr;
        addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        Map<String, String> map = System.getenv();
        String computerName = map.get("COMPUTERNAME");// 获取计算机名
        String userDomain = map.get("USERDOMAIN");// 获取计算机域名
        System.out.println("计算机名:    " + computerName);
        hashMap.put("computerName", computerName);
        System.out.println("计算机域名:    " + userDomain);
        System.out.println("本地ip地址:    " + ip);
        hashMap.put("ip", ip);
        System.out.println("本地主机名:    " + addr.getHostName());
        hashMap.put("hostName", addr.getHostName());
        System.out.println("JVM可以使用的总内存:    " + r.totalMemory());
        hashMap.put("jvmTotalMemory", r.totalMemory() / 1024);
        System.out.println("JVM可以使用的剩余内存:    " + r.freeMemory());
        hashMap.put("jvmFreeMemory", r.freeMemory() / 1024);


    }

    /**
     * memory:(内存信息). <br/>
     *
     * @throws SigarException
     * @author liuweiying
     * @since JDK 1.6
     */
    public static void memory() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        // 内存总量
        System.out.println("内存总量:    " + mem.getTotal() / 1024L + "K av");
        hashMap.put("systemTotalMemory", mem.getTotal() / 1024L);
        // 当前内存使用量
        System.out.println("当前内存使用量:    " + mem.getUsed() / 1024L + "K used");
        hashMap.put("systemUsedMemory", mem.getUsed() / 1024L);

//        Swap swap = sigar.getSwap();
//        // 交换区总量
//        System.out.println("交换区总量:    " + swap.getTotal() / 1024L + "K av");
//        // 当前交换区使用量
//        System.out.println("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used");
//        // 当前交换区剩余量
//        System.out.println("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free");

    }

    /**
     * cpu:(cpu信息). <br/>
     *
     * @throws SigarException
     * @author liuweiying
     * @since JDK 1.6
     */
    public static void cpu() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();

        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
            CpuInfo info = infos[i];
            printCpuPerc(cpuList[i]);
        }
        System.out.println(hashMap.get("cpuUser"));
        System.out.println(hashMap.get("cpuSys"));
        System.out.println(hashMap.get("cpuError"));





        hashMap.put("cpuUser", ((int) hashMap.get("cpuUser") / infos.length));
        hashMap.put("cpuSys", ((int) hashMap.get("cpuSys") / infos.length));
        hashMap.put("cpuError", ((int) hashMap.get("cpuError") / infos.length));


    }

    /**
     * printCpuPerc:(cpu使用信息). <br/>
     *
     * @param cpu
     * @author liuweiying
     * @since JDK 1.6
     */
    public static void printCpuPerc(CpuPerc cpu) {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("CPU用户使用率:    " +cpu.getUser()*100);// 用户使用率
        int cpuUser=((int)(cpu.getUser()*100));
        if (hashMap.containsKey("cpuUser")) {

            hashMap.put("cpuUser", (int) hashMap.get("cpuUser") + cpuUser);
        } else {
            hashMap.put("cpuUser",  cpuUser);
        }
        int cpuSys=((int)(cpu.getSys()*100));
        System.out.println("CPU系统使用率:    " + CpuPerc.format(cpu.getSys()));// 系统使用率
        if (hashMap.containsKey("cpuSys")) {
            hashMap.put("cpuSys", (int) hashMap.get("cpuSys") + cpuSys);
        } else {
            hashMap.put("cpuSys",  cpuSys);
        }
        int cpuError=((int)(cpu.getNice()*100));
        if (hashMap.containsKey("cpuError")) {
            hashMap.put("cpuError",  (int) hashMap.get("cpuError") + cpuError);
        } else {
            hashMap.put("cpuError",cpuError);
        }


    }

    /**
     * os:(操作系统信息). <br/>
     *
     * @author liuweiying
     * @since JDK 1.6
     */
    public static void os() {
//        OperatingSystem OS = OperatingSystem.getInstance();
//        // 操作系统内核类型如： 386、486、586等x86
//        System.out.println("操作系统:    " + OS.getArch());
//        System.out.println("操作系统CpuEndian():    " + OS.getCpuEndian());//
//        System.out.println("操作系统DataModel():    " + OS.getDataModel());//
//        // 系统描述
//        System.out.println("操作系统的描述:    " + OS.getDescription());
//        // 操作系统类型
//        // System.out.println("OS.getName():    " + OS.getName());
//        // System.out.println("OS.getPatchLevel():    " + OS.getPatchLevel());//
//        // 操作系统的卖主
//        System.out.println("操作系统的卖主:    " + OS.getVendor());
//        // 卖主名称
//        System.out.println("操作系统的卖主名:    " + OS.getVendorCodeName());
//        // 操作系统名称
//        System.out.println("操作系统名称:    " + OS.getVendorName());
//        // 操作系统卖主类型
//        System.out.println("操作系统卖主类型:    " + OS.getVendorVersion());
//        // 操作系统的版本号
//        System.out.println("操作系统的版本号:    " + OS.getVersion());
    }

    /**
     * who:(用户信息). <br/>
     *
     * @throws SigarException
     * @author liuweiying
     * @since JDK 1.6
     */
    public static void who() throws SigarException {
//        Sigar sigar = new Sigar();
//        Who who[] = sigar.getWhoList();
//        if (who != null && who.length > 0) {
//            for (int i = 0; i < who.length; i++) {
//                // System.out.println("当前系统进程表中的用户名" + String.valueOf(i));
//                Who _who = who[i];
//                System.out.println("用户控制台:    " + _who.getDevice());
//                System.out.println("用户host:    " + _who.getHost());
//                // System.out.println("getTime():    " + _who.getTime());
//                // 当前系统进程表中的用户名
//                System.out.println("当前系统进程表中的用户名:    " + _who.getUser());
//            }
//        }
    }

    /**
     * file:(文件信息). <br/>
     *
     * @throws Exception
     * @author liuweiying
     * @since JDK 1.6
     */
    public static void file() throws Exception {
        Sigar sigar = new Sigar();
        FileSystem fslist[] = sigar.getFileSystemList();

      //  hashMap.put("diskTotal"+usage.getTotal());
        Long diskTotal=0L;
        Long diskFree=0L;
        for (int i = 0; i < fslist.length; i++) {
            System.out.println("分区的盘符名称" + i);
            FileSystem fs = fslist[i];
            // 分区的盘符名称
            System.out.println("盘符名称:    " + fs.getDevName());
            // 分区的盘符名称
            System.out.println("盘符路径:    " + fs.getDirName());
            System.out.println("盘符标志:    " + fs.getFlags());//
            // 文件系统类型，比如 FAT32、NTFS
            System.out.println("盘符类型:    " + fs.getSysTypeName());
            // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
            System.out.println("盘符类型名:    " + fs.getTypeName());
            // 文件系统类型
            System.out.println("盘符文件系统类型:    " + fs.getType());
            FileSystemUsage usage = null;
            usage = sigar.getFileSystemUsage(fs.getDirName());
            switch (fs.getType()) {
                case 0: // TYPE_UNKNOWN ：未知
                    break;
                case 1: // TYPE_NONE
                    break;
                case 2: // TYPE_LOCAL_DISK : 本地硬盘
                    // 文件系统总大小
                    System.out.println(fs.getDevName() + "总大小:    " + usage.getTotal() + "KB");
                    diskTotal+=(int)(usage.getTotal()/1024);
                    // 文件系统剩余大小
                    System.out.println(fs.getDevName() + "剩余大小:    " + usage.getFree() + "KB");
                    diskFree+=(int)(usage.getFree()/1024);
                    // 文件系统可用大小
                    System.out.println(fs.getDevName() + "可用大小:    " + usage.getAvail() + "KB");
                    // 文件系统已经使用量
                    System.out.println(fs.getDevName() + "已经使用量:    " + usage.getUsed() + "KB");
                    double usePercent = usage.getUsePercent() * 100D;
                    // 文件系统资源的利用率
                    System.out.println(fs.getDevName() + "资源的利用率:    " + usePercent + "%");
                    break;
                case 3:// TYPE_NETWORK ：网络
                    break;
                case 4:// TYPE_RAM_DISK ：闪存
                    break;
                case 5:// TYPE_CDROM ：光驱
                    break;
                case 6:// TYPE_SWAP ：页面交换
                    break;
            }

            hashMap.put("diskTotal",diskTotal);
            hashMap.put("diskFree",diskFree);
            System.out.println(fs.getDevName() + "读出：    " + usage.getDiskReads());
            System.out.println(fs.getDevName() + "写入：    " + usage.getDiskWrites());
        }
        return;
    }

    /**
     * net:(网络信息). <br/>
     *
     * @throws Exception
     * @author liuweiying
     * @since JDK 1.6
     */
    public static void net() throws Exception {
        Sigar sigar = new Sigar();
        String ifNames[] = sigar.getNetInterfaceList();
        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            System.out.println("网络设备名:    " + name);// 网络设备名
            System.out.println("IP地址:    " + ifconfig.getAddress());// IP地址
            System.out.println("子网掩码:    " + ifconfig.getNetmask());// 子网掩码
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                System.out.println("!IFF_UP...skipping getNetInterfaceStat");
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
            System.out.println(name + "接收的总包裹数:" + ifstat.getRxPackets());// 接收的总包裹数
            System.out.println(name + "发送的总包裹数:" + ifstat.getTxPackets());// 发送的总包裹数
            System.out.println(name + "接收到的总字节数:" + ifstat.getRxBytes());// 接收到的总字节数
            System.out.println(name + "发送的总字节数:" + ifstat.getTxBytes());// 发送的总字节数
            System.out.println(name + "接收到的错误包数:" + ifstat.getRxErrors());// 接收到的错误包数
            System.out.println(name + "发送数据包时的错误数:" + ifstat.getTxErrors());// 发送数据包时的错误数
            System.out.println(name + "接收时丢弃的包数:" + ifstat.getRxDropped());// 接收时丢弃的包数
            System.out.println(name + "发送时丢弃的包数:" + ifstat.getTxDropped());// 发送时丢弃的包数
        }
    }

    /**
     * ethernet:(以太网信息). <br/>
     *
     * @throws SigarException
     * @author liuweiying
     * @since JDK 1.6
     */
    public static void ethernet() throws SigarException {
        Sigar sigar = null;
        sigar = new Sigar();
        String[] ifaces = sigar.getNetInterfaceList();
        for (int i = 0; i < ifaces.length; i++) {
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
                continue;
            }
            System.out.println(cfg.getName() + "IP地址:" + cfg.getAddress());// IP地址
            System.out.println(cfg.getName() + "网关广播地址:" + cfg.getBroadcast());// 网关广播地址
            System.out.println(cfg.getName() + "网卡MAC地址:" + cfg.getHwaddr());// 网卡MAC地址
            System.out.println(cfg.getName() + "子网掩码:" + cfg.getNetmask());// 子网掩码
            System.out.println(cfg.getName() + "网卡描述信息:" + cfg.getDescription());// 网卡描述信息
            System.out.println(cfg.getName() + "网卡类型" + cfg.getType());//
        }
    }


}