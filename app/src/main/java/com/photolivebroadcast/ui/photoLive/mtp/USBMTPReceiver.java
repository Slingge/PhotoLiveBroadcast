package com.photolivebroadcast.ui.photoLive.mtp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.mtp.MtpConstants;
import android.mtp.MtpDevice;
import android.mtp.MtpObjectInfo;
import android.os.Environment;

import com.github.mjdev.libaums.UsbMassStorageDevice;
import com.github.mjdev.libaums.fs.FileSystem;
import com.github.mjdev.libaums.fs.UsbFile;
import com.github.mjdev.libaums.partition.Partition;
import com.photolivebroadcast.ui.MyApplication;
import com.photolivebroadcast.ui.photoLive.model.CameraPictureBean;
import com.photolivebroadcast.util.SharePreferencesTools;
import com.photolivebroadcast.util.SmartLogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.photolivebroadcast.constant.USBReceiverConstant.EXTERNAL_DEVICE_FILE_KEY;
import static com.photolivebroadcast.constant.USBReceiverConstant.EXTERNAL_DEVICE_FILE_NAME;
import static com.photolivebroadcast.constant.USBReceiverConstant.READ_USB_DEVICE_PERMISSION;
public class USBMTPReceiver extends BroadcastReceiver {

    private UsbDevice mUSBDevice;

    @Override
    public void onReceive(Context context, Intent intent) {
        //SmartToastUtils.showShort(context,"onReceiver start");
        final String action = intent.getAction();
        switch (action)
        {
            case UsbManager.ACTION_USB_DEVICE_ATTACHED://插上USB设备
                SmartLogUtils.showDebug("Activity intent sent when user attaches a USB device.",false);
                SmartToastUtils.showShort(MyApplication.getContext(),"设备已连接");
                checkConnectedDevice();
                break;
            case UsbManager.ACTION_USB_DEVICE_DETACHED://断开USB设备
                SmartLogUtils.showDebug("Broadcast Action: A broadcast for USB device detached event.",false);
                SmartToastUtils.showShort(MyApplication.getContext(),"设备已断开");
                try {
                    if(mUSBDevice!=null){
                        UsbMassStorageDevice usbMassStorageDevice=getUsbMass(mUSBDevice);
                        if(usbMassStorageDevice!=null){
                            usbMassStorageDevice.close();
                        }
                    }
                } catch (Exception e) {
                    SmartToastUtils.showShort(MyApplication.getContext(),"设备断开异常"+e.toString());
                }
                break;
            case READ_USB_DEVICE_PERMISSION: //自定义读取USB 设备信息
                SmartToastUtils.showShort(context,"开始扫描数码相机图片");
                //检查连接的设备
                checkConnectedDevice();
                break;
                default:
                    break;
        }
    }


    /***
     * 检查连接的设备
     * */
    private void checkConnectedDevice() {
        SmartLogUtils.showDebug("开始检查USB连接设备",true);
        //获取Android 设备管理器
        UsbManager usbManager = (UsbManager) MyApplication.getContext().getSystemService(Context.USB_SERVICE);
        //获取所有已连接上的USB列表
        HashMap<String,UsbDevice>  allConnectedUSBDeviceList=usbManager.getDeviceList();

        if(allConnectedUSBDeviceList.size()<=0){
            SmartLogUtils.showDebug("未检测到任何设备连接,请重新插拔连接设备",true);
            return ;
        }else{
            SmartLogUtils.showDebug("当前连接设备个数:"+allConnectedUSBDeviceList.size(),true);
        }
        for(UsbDevice hasConnectedDevice:allConnectedUSBDeviceList.values())
        {
            mUSBDevice=hasConnectedDevice;
            //遍历连接的设备接口
            for (int i=0;i<mUSBDevice.getInterfaceCount();i++){
                //获取连接设备接口
                UsbInterface usbInterface=mUSBDevice.getInterface(i);
                //检查设备连接接口
                switch (usbInterface.getInterfaceClass()){
                    case UsbConstants.USB_CLASS_STILL_IMAGE: //如果是数码相机
                            SmartLogUtils.showDebug("连接设备是数码相机",true);
                            //检查是否有权限
                            if(usbManager.hasPermission(mUSBDevice)){
                                SmartLogUtils.showDebug("数码相机已获取权限",true);

                                if(mUSBDevice==null){
                                    SmartLogUtils.showDebug("设备为空",true);
                                    return ;
                                }else{
                                    SmartLogUtils.showDebug("设备不为空",true);
                                }
                                //打开USB设备连接通道
                                UsbDeviceConnection usbDeviceConnection = usbManager.openDevice(mUSBDevice);
                                //获取MTP设备
                                MtpDevice mtpDevice=new MtpDevice(mUSBDevice);
                                if (!mtpDevice.open(usbDeviceConnection)) {
                                    SmartToastUtils.showShort(MyApplication.getContext(),"数码相机打开失败");
                                    return;
                                }else{
                                    SmartToastUtils.showShort(MyApplication.getContext(),"设备打开成功");
                                }
                                int[] storageIds = mtpDevice.getStorageIds();
                                if (storageIds == null) {
                                    SmartLogUtils.showDebug("数码相机存储卷不可用",true);
                                    return ;
                                }else{
                                    SmartLogUtils.showDebug("数码相机存储卷可用",true);
                                }
                                for (int storageId : storageIds) {
                                    //读取MTP 设备上的数据
                                    SmartLogUtils.showDebug("storageId="+storageId,true);
                                     readAllFileFromMTPDevice(mtpDevice,storageId);
                                }
                                if(usbDeviceConnection!=null){
                                    usbDeviceConnection.close();
                                    if(mtpDevice!=null){
                                        mtpDevice.close();
                                    }
                                }
                            }else{
                                SmartToastUtils.showShort(MyApplication.getContext(),"数码相机未获取权限");
                                ////该代码执行后，系统弹出一个对话框
                                PendingIntent pendingIntent = PendingIntent.getBroadcast( MyApplication.getContext(), 0, new Intent(READ_USB_DEVICE_PERMISSION), 0);
                                usbManager.requestPermission(mUSBDevice, pendingIntent);
                            }
                        break;
                    case UsbConstants.USB_CLASS_MASS_STORAGE://USB class for mass storage devices.
                        SmartToastUtils.showShort(MyApplication.getContext(),"连接设备是大容量USB存储设备");
                        checkUSBDevice(mUSBDevice);
                        break;
                        default:
                            SmartToastUtils.showShort(MyApplication.getContext(),"连接设备不是相机和USB存储设备");
                            break;
                }//end switch
            }
        }
    }

    String USBTempFolder;
    private void readAllFileFromMTPDevice(MtpDevice mtpDevice,int storageId){
        SmartLogUtils.showDebug("readAllFileFromMTPDevice start",true);

        SmartLogUtils.showDebug("storageId="+storageId,true);
        //返回给定存储单元上所有对象的对象句柄列表，具有给定格式和父级。
        //数码相机拍照欧格士都是JPEG
        int[] objectHandles = mtpDevice.getObjectHandles(storageId,MtpConstants.FORMAT_EXIF_JPEG,0);
        if (objectHandles == null) {
            SmartLogUtils.showDebug("获取照片失败,objectHandles is null",true);
            return;
        }
        if(objectHandles.length<=0){
            SmartToastUtils.showShort(MyApplication.getContext(),"没找到图片");
            return ;
        }else{
            USBTempFolder=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"usb_temp_foler";
            //SD Card 文件夹
            File fileFolder=new File(USBTempFolder);
            //文件夹不存在就创建
            if(!fileFolder.exists()){
                fileFolder.mkdir();
                SmartLogUtils.showDebug("文件夹创建成功",true);
            }else{
                SmartLogUtils.showDebug("文件夹已存在",true);
            }
        }
        String saveFilePath;//保存文件名称
        List<CameraPictureBean> readCameraPhoneList=new ArrayList<CameraPictureBean>();
        String scanFilePicName;//扫描到图片名称
        readCameraPhoneList.clear();
        CameraPictureBean cameraPictureBean;
        for (int itemObjectHandles : objectHandles) {
            //每一个mtpObjectInfo 是一个图片对象 检索MtpObjectInfo对象
            MtpObjectInfo mtpObjectInfo = mtpDevice.getObjectInfo(itemObjectHandles);
            if (mtpObjectInfo == null) {
                SmartLogUtils.showDebug("mtpObjectInfo is null",true);
                continue;
            }else{
                SmartLogUtils.showDebug("mtpObjectInfo is not null",true);
            }
            scanFilePicName=mtpObjectInfo.getName();
            //复制图片到SDCard
            try {
                saveFilePath=USBTempFolder+File.separator+scanFilePicName;
                cameraPictureBean=new CameraPictureBean();
                cameraPictureBean.setCameraPicturePath(saveFilePath);
                //添加到List集合
                readCameraPhoneList.add(cameraPictureBean);
                //文件不存在才复制
                if(!new File(saveFilePath).exists()){
                    mtpDevice.importFile(itemObjectHandles,saveFilePath);
                }
            } catch (Exception e) {
                SmartToastUtils.showShort(MyApplication.getContext(),"图片复制出错");
                return ;
            }
        }
        SharePreferencesTools.saveObjectToSharePreferences(EXTERNAL_DEVICE_FILE_NAME,EXTERNAL_DEVICE_FILE_KEY,readCameraPhoneList);
        SmartToastUtils.showLong(MyApplication.getContext(),"扫描到图片"+readCameraPhoneList.size()+"张");
        SmartLogUtils.showDebug("readAllFileFromMTPDevice end",true);
    }

    /**
     * 进行U 盘读写权限的申请
     */
    private void checkUSBDevice(UsbDevice usbDevice) {
        SmartToastUtils.showShort(MyApplication.getContext(), "开始申请设备权限");
        try {
            //获取Android 设备管理器
            UsbManager usbManager = (UsbManager) MyApplication.getContext().getSystemService(Context.USB_SERVICE);

            //获取所有已连接上的USB列表方法二
            SmartToastUtils.showShort(MyApplication.getContext(), "遍历所有已连接的USB设备方法二开始");
            if (usbDevice == null) {
                SmartToastUtils.showShort(MyApplication.getContext(), "USB Device is null");
            } else {
                SmartToastUtils.showShort(MyApplication.getContext(), "USB Device is not null");
            }
            //获取所有已连接的USB设备
            UsbMassStorageDevice[] storageDevices = UsbMassStorageDevice.getMassStorageDevices(MyApplication.getContext());
            if (storageDevices.length == 0) {
                SmartToastUtils.showShort(MyApplication.getContext(), "请插入可用的 U 盘");
                return;
            } else {
                for (UsbMassStorageDevice device : storageDevices) {
                    SmartLogUtils.showError("监测到USB设备:" + device.getUsbDevice(), true);
                    //SmartToastUtils.showShort(MyApplication.getContext(),"监测到USB设备:"+device.getUsbDevice());
                    //检查USB设备是否有权限
                    if (usbManager.hasPermission(device.getUsbDevice())) {
                        //读设备
                        readUSBDevice(device);
                    } else {//申请权限
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getContext(), 0, new Intent(READ_USB_DEVICE_PERMISSION), 0);
                        usbManager.requestPermission(device.getUsbDevice(), pendingIntent);
                    }
                }
            }
            SmartToastUtils.showShort(MyApplication.getContext(), "遍历所有已连接的USB设备方法二结束");
            //方法二结束

            //获取USB设备信息方法三
//            SmartToastUtils.showShort(MyApplication.getContext(),"遍历所有已连接的USB设备方法三开始");
//            if(usbDevice==null){
//                SmartToastUtils.showShort( MyApplication.getContext(), "请插入可用的 USB 设备");
//                return ;
//            }
//            if(usbManager.hasPermission(usbDevice)){
//                //读取设备信息
//                SmartToastUtils.showShort( MyApplication.getContext(), "该USB设备已获取权限");
//                if(usbDevice!=null){
//                    readUSBDevice(getUsbMass(usbDevice));
//                }
//            }else{
//                PendingIntent pendingIntent = PendingIntent.getBroadcast( MyApplication.getContext(), 0, new Intent(READ_USB_DEVICE_PERMISSION), 0);
//                usbManager.requestPermission(usbDevice, pendingIntent);
//            }
//            SmartToastUtils.showShort(MyApplication.getContext(),"遍历所有已连接的USB设备方法三结束");
            //方法三结束
        } catch (Exception e) {
            SmartToastUtils.showShort( MyApplication.getContext(),"申请权限异常,e="+e.toString());
        }
    }


    /**
     * 读取USB 设备
     */
    private void readUSBDevice(UsbMassStorageDevice device) {
        SmartToastUtils.showShort(MyApplication.getContext(),"开始读取设备存储信息");
        if(device==null){
            SmartToastUtils.showShort(MyApplication.getContext(),"没有检测到USB设备");
            return ;
        }
        try {
            device.init();
        } catch (IOException e) {
            SmartToastUtils.showShort(MyApplication.getContext(),"device.init() error:"+e.toString());
            return ;
        }

        // 设备分区
        Partition partition = device.getPartitions().get(0);
        // 文件系统
        FileSystem currentFs = partition.getFileSystem();
        // 获取 U 盘的根目录
        UsbFile mRootFolder = currentFs.getRootDirectory();
//         //获取 U 盘的容量
//        long capacity = currentFs.getCapacity();
//        // 获取 U 盘的剩余容量
//        long freeSpace = currentFs.getFreeSpace();
//        // 获取 U 盘的标识
//        String volumeLabel = currentFs.getVolumeLabel();
        if(mRootFolder.isDirectory()){
            SmartLogUtils.showDebug("这是一个文件夹",true);
        }else{
            SmartLogUtils.showDebug("这不是一个文件夹",true);
        }
        if(mRootFolder.isRoot()){
            SmartLogUtils.showDebug("这是根目录",true);
        }else{
            SmartLogUtils.showDebug("这不是根目录",true);
        }
        readAllPicFileFromUSBDevice(mRootFolder,currentFs);
    }
    /***
     * 读取USB 中的所有图片并复制到手机内存卡中
     * */
    private void readAllPicFileFromUSBDevice(UsbFile usbFile,FileSystem fileSystem){
        try {
            UsbFile[] usbFileList=usbFile.listFiles();
            for (UsbFile usbFileItem:usbFileList
                    ) {
                if(!usbFileItem.isDirectory()){
                    //获取文件后缀
                    String FileEnd = usbFileItem.getName().substring(usbFileItem.getName().lastIndexOf(".") + 1,
                            usbFileItem.getName().length()).toLowerCase();
                    //检查文件后缀是不是图片格式
                    if(FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                            || FileEnd.equals("jpeg")|| FileEnd.equals("bmp")){
                        SmartToastUtils.showShort(MyApplication.getContext(),"找到图片:"+usbFileItem.getName()+",文件原图大小="+usbFileItem.getLength());
                        //保存文件到手机中
                        FileUtils.saveToPhoneDevice(usbFileItem,fileSystem);
                    }
                }else{
                    //如果是文件夹则开始递归遍历
                    readAllPicFileFromUSBDevice(usbFileItem,fileSystem);
                }
            }
        } catch (IOException e) {
            SmartToastUtils.showShort(MyApplication.getContext(),"遍历USB文件异常");
        }
    }
    /**
     * USBDevice 转换成UsbMassStorageDevice 对象
     */
    private UsbMassStorageDevice getUsbMass(UsbDevice usbDevice) {
        UsbMassStorageDevice[] storageDevices = UsbMassStorageDevice.getMassStorageDevices(MyApplication.getContext());
        for (UsbMassStorageDevice device : storageDevices) {
            if (usbDevice.equals(device.getUsbDevice())) {
                return device;
            }
        }
        return null;
    }
}
