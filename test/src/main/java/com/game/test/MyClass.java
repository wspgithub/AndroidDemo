package com.game.test;
import com.sun.javafx.font.directwrite.RECT;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

import org.opencv.core.Core;
import org.opencv.core.Rect;

import javax.imageio.ImageIO;


import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyClass {
    public static int yysWindowWidth = 1200;
    public static int yysWindowHeight = 600;

    public interface User32 extends StdCallLibrary {

        User32 INSTANCE = (User32) Native.loadLibrary("User32",User32.class);

        int PostMessageA(int a,int b,int c,int d);

        int FindWindowA(String a,String b);

        int GetWindowRect(int hwnd, Rect r);

        int ShowWindow(int hwnd, int r);
        int GetForegroundWindow();
        int SetForegroundWindow(int hwnd);
    }

    public static void main(String[] args) {
       // System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        int myhwnd = User32.INSTANCE.FindWindowA(null, "有道云笔记");
        WinDef.HWND hwnd = com.sun.jna.platform.win32.User32.INSTANCE.FindWindow(null, "有道云笔记"); // 第一个参数是Windows窗体的窗体类，第二个参数是窗体的标题。不熟悉windows编程的需要先找一些Windows窗体数据结构的知识来看看，还有windows消息循环处理，其他的东西不用看太多。
        myhwnd = User32.INSTANCE.GetForegroundWindow(); //获取现在前台窗口
        if (myhwnd == 0) {
            System.out.println("is not running");
        }
        else{
            System.out.println("is running");
          //  User32.INSTANCE.ShowWindow(hwnd, 9 );// SW_RESTORE
             User32.INSTANCE.SetForegroundWindow(myhwnd);   // bring to front


            WinDef.RECT qqwin_rect = new  WinDef.RECT();
            com.sun.jna.platform.win32.User32.INSTANCE.GetWindowRect(hwnd, qqwin_rect);

            for (int i = 0; i < 100; i++) {
                click(myhwnd,i+3,i+3);
            }

//            yysWindowWidth = qqwin_rect.right-qqwin_rect.left;
//            yysWindowHeight = qqwin_rect.bottom-qqwin_rect.top;
//            System.out.println( "w" + yysWindowWidth +"h"+yysWindowHeight);
//
//            Rectangle rg = new Rectangle(qqwin_rect.left, qqwin_rect.top, qqwin_rect.right-qqwin_rect.left, qqwin_rect.bottom-qqwin_rect.top);
//            try {
//                BufferedImage buff = new Robot().createScreenCapture(rg);
//
//                File outputfile  = new File("E:/Eclipse Project/save.png");
//                ImageIO.write(buff,"png",outputfile);
//
//            } catch (AWTException | IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }

            // User32.INSTANCE.MoveWindow(hwnd, 100, 100, qqwin_width, qqwin_height, true);

        }
    }

    public static void click(int hwnd, int x, int y) {
        int v = x + (y << 16);
        User32.INSTANCE.PostMessageA(hwnd, 513, 1, v); // 按下
        User32.INSTANCE.PostMessageA(hwnd, 514, 0, v); // 松开
    }


}
