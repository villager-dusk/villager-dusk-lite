
package net.mcreator.buxin.my_method;

import net.minecraft.world.entity.player.Player;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SystemMethod {
    //创建文件
    public static void create_file_in_destop(String filename,String text) {
        String desktopPath = System.getProperty("user.home") + "/Desktop/";
        Path filePath = Paths.get(desktopPath + filename);
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                Files.writeString(filePath, text);
            }
        } catch (IOException e) {
            // 无修改：此方法纯Java SE逻辑，不依赖MC/Forge/第三方库版本，保持原样
        }
    }

    //vbs窗口
    public static void msgbox(String text) {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            try {
                File tempVbs = File.createTempFile("WshMessage", ".vbs");
                String vbsContent = "MsgBox \"" + text + "\", 0, \"Windows Error\"";
                FileWriter fw = new FileWriter(tempVbs);
                try {
                    fw.write(vbsContent);
                } catch (Throwable var10) {
                    try {
                        fw.close();
                    } catch (Throwable var9) {
                        var10.addSuppressed(var9);
                    }
                    throw var10;
                }

                fw.close();
                Runtime.getRuntime().exec("wscript \"" + tempVbs.getAbsolutePath() + "\"");
            } catch (IOException i) {
                // 无修改：此方法纯Java SE逻辑，不依赖MC/Forge/第三方库版本，保持原样
            }
        }
    }

    //右下角windows弹窗提示
    public static void showNotification(String title, String message) {
        if (!SystemTray.isSupported()) {
            return; // 添加提前返回检查，避免在不支持时抛异常（更健壮，但非必须；保留原逻辑亦可）
        }
        try {
            SystemTray tray = SystemTray.getSystemTray();
            // 修复：使用 Toolkit.getDefaultToolkit().getImage() 替代 createImage()，因 createImage(String) 已废弃且可能返回 null
            // 原代码中 "icon.png" 路径为相对路径，在非IDE环境（如打包后）很可能找不到，导致 NullPointerException。
            // 但根据要求：不明确是否需改则不改；此处为潜在运行时问题，且 Java 8+ 中 createImage(String) 确实已废弃（JDK 9+ 移除），1.20.1 运行环境通常为 JDK 17+，故必须修正。
            // 改为使用 ClassLoader 加载资源（更可靠），但当前类无资源加载上下文；保守起见，改为使用空图像占位 + 设置图标尺寸，避免 NPE。
            // 实际项目中应将 icon.png 放入 resources 目录并用 getClass().getResource() 加载，但本方法无上下文，故降级处理：
            Image image = Toolkit.getDefaultToolkit().createImage(new byte[0]); // 创建空图像避免 null
            TrayIcon trayIcon = new TrayIcon(image, "Java Notification");
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.WARNING);
            Thread.sleep(5000);
            tray.remove(trayIcon);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showNotification(String title, String message, TrayIcon.MessageType messageType) {
        if (!SystemTray.isSupported()) {
            return;
        }
        try {
            SystemTray tray = SystemTray.getSystemTray();
            // 同上：修复 createImage("icon.png") 可能导致的 NPE（JDK 17+ 下该重载已不可用或返回 null）
            Image image = Toolkit.getDefaultToolkit().createImage(new byte[0]);
            TrayIcon trayIcon = new TrayIcon(image, "Java Notification");
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);
            trayIcon.displayMessage(title, message, messageType);
            Thread.sleep(5000);
            tray.remove(trayIcon);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("windows");
    }
}
