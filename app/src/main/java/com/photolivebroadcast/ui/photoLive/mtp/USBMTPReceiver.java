package com.photolivebroadcast.ui.photoLive.mtp;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.mtp.MtpDevice;
import android.provider.MediaStore;

import com.github.mjdev.libaums.UsbMassStorageDevice;
import com.github.mjdev.libaums.fs.FileSystem;
import com.github.mjdev.libaums.fs.UsbFile;
import com.github.mjdev.libaums.partition.Partition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.photolivebroadcast.constant.USBReceiverConstant.READ_USB_DEVICE_PERMISSION;


public class USBMTPReceiver extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
//        SmartToastUtils.showShort(context,"onReceiver start");
        mContext=context;
        final String action = intent.getAction();
        switch (action)
        {
            case UsbManager.ACTION_USB_ACCESSORY_ATTACHED:
                SmartToastUtils.showShort(context,"USB设备ACTION_USB_ACCESSORY_ATTACHED");
                break;
            case UsbManager.ACTION_USB_ACCESSORY_DETACHED:
                SmartToastUtils.showShort(context,"USB设备ACTION_USB_ACCESSORY_DETACHED");
                break;
            case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                SmartToastUtils.showShort(context,"USB设备已连接");
                UsbDevice find_USB_Device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (find_USB_Device != null) {
                    permissionRequest(mContext,find_USB_Device);
                }else{
                    SmartToastUtils.showShort(mContext,"findUsb is null");
                }
                break;
            case UsbManager.ACTION_USB_DEVICE_DETACHED:
                SmartToastUtils.showShort(context,"USB设备已断开");
                try {
                    UsbDevice removedDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if(removedDevice!=null){
                        UsbMassStorageDevice usbMassStorageDevice=getUsbMass(removedDevice);
                        if(usbMassStorageDevice!=null){
                            usbMassStorageDevice.close();
                        }
                    }
                } catch (Exception e) {
                    SmartToastUtils.showShort(mContext,"USB断开异常"+e.toString());
                }
                break;
            case READ_USB_DEVICE_PERMISSION: //已经获取完权限
                SmartToastUtils.showShort(context,"USB已获取权限");
                UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                // 权限申请
                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    if (usbDevice != null) {
                        readDevice(getUsbMass(usbDevice));
                        // connectedMTPService(usbDevice);
                    } else {
                        SmartToastUtils.showShort(mContext, "没有插入U 盘");
                    }
                } else {
                    SmartToastUtils.showShort(mContext, "未获取到 U盘权限");
                }
                break;
            case UsbManager.EXTRA_PERMISSION_GRANTED:
                SmartToastUtils.showShort(context,"USB设备EXTRA_PERMISSION_GRANTED");
                break;
            case UsbManager.EXTRA_DEVICE:
                SmartToastUtils.showShort(context,"USB设备EXTRA_DEVICE");
                break;
            case UsbManager.EXTRA_ACCESSORY:
                SmartToastUtils.showShort(context,"USB设备EXTRA_ACCESSORY");
                break;
            default:
                break;
        }
    }

    /**
     * 进行 U 盘读写权限的申请
     */
    private void permissionRequest(Context context,UsbDevice usbDevice) {
        SmartToastUtils.showShort(context, "开始申请权限");
        try {
            // 设备管理器
            UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
            // 获取 U 盘存储设备
            UsbMassStorageDevice[] storageDevices = UsbMassStorageDevice.getMassStorageDevices(context.getApplicationContext());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, new Intent(READ_USB_DEVICE_PERMISSION), 0);

            if(storageDevices.length == 0){
                SmartToastUtils.showShort(context, "请插入可用的 U 盘");
            }else{
                //可能有几个 一般只有一个 因为大部分手机只有1个otg插口
                for (UsbMassStorageDevice device : storageDevices) {
                    if (usbManager.hasPermission(device.getUsbDevice())) {
                        SmartToastUtils.showShort(context,"USB直接获取权限");
                        readDevice(device);
                    } else {
                        // 进行权限申请
                        usbManager.requestPermission(device.getUsbDevice(), pendingIntent);
                    }
                }
            }
        } catch (Exception e) {
            SmartToastUtils.showShort(context,"申请权限异常,e="+e.toString());
        }

    }

    private void readDevice(UsbMassStorageDevice device) {
        SmartToastUtils.showShort(mContext,"readDevice");
        try {
            device.init();
        } catch (IOException e) {
            SmartToastUtils.showShort(mContext,"device.init() error"+e.toString());
            return ;
        }
        // 设备分区
        Partition partition = device.getPartitions().get(0);
        // 文件系统
        FileSystem currentFs = partition.getFileSystem();
        // 获取 U 盘的根目录
        UsbFile mRootFolder = currentFs.getRootDirectory();
        // 获取 U 盘的容量
        long capacity = currentFs.getCapacity();
        // 获取 U 盘的剩余容量
        long freeSpace = currentFs.getFreeSpace();
        // 获取 U 盘的标识
        String volumeLabel = currentFs.getVolumeLabel();

        device.getPartitions();

        device.getUsbDevice();
        // SmartToastUtils.showShort(mContext,mRootFolder.getName());
        SmartToastUtils.showShort(mContext,"U盘容量"+capacity);
        SmartToastUtils.showShort(mContext,"U盘剩余容量"+freeSpace);
        SmartToastUtils.showShort(mContext," U盘的标识"+volumeLabel);
        SmartToastUtils.showShort(mContext," U盘根目录名称"+mRootFolder.getName());
        SmartToastUtils.showShort(mContext," U盘设备"+device.getUsbDevice());
        SmartToastUtils.showShort(mContext," U盘分区"+device.getPartitions());

        if(mRootFolder.isDirectory()){
            SmartToastUtils.showShort(mContext,"这是一个文件夹");
        }else{
            SmartToastUtils.showShort(mContext,"这不是一个文件夹");
        }
        if(mRootFolder.isRoot()){
            SmartToastUtils.showShort(mContext,"这是根目录");
        }else{
            SmartToastUtils.showShort(mContext,"这不是根目录");
        }
//        try {
//            UsbFile[] usbFileList=mRootFolder.listFiles();
//            SmartToastUtils.showShort(mContext,"总共有"+usbFileList.length+"文件");
//            for (UsbFile usbFileItem:usbFileList
//                 ) {
//                SmartToastUtils.showShort(mContext,"文件名称:"+usbFileItem.getName()+"文件大小"+usbFileItem.getLength());
//            }
//        } catch (IOException e) {
//            SmartToastUtils.showShort(mContext,"遍历USB文件异常");
//        }


    }



    private void connectedMTPService(UsbDevice usbDevice){
        SmartToastUtils.showShort(mContext,"尝试连接MTP协议");
        UsbManager manager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        //枚举设备
        UsbMassStorageDevice[] storageDevices = UsbMassStorageDevice.getMassStorageDevices(mContext);//获取存储设备



        Boolean isOpenMtp=false;
        if (manager.hasPermission(usbDevice)) {
            SmartToastUtils.showShort(mContext,"MTP权限获取成功");
            UsbDeviceConnection usbDeviceConnection = manager.openDevice(usbDevice);

            if(usbDeviceConnection==null){
                SmartToastUtils.showShort(mContext,"usbDeviceConnection为空"+usbDeviceConnection);
            }else{
                SmartToastUtils.showShort(mContext,"usbDeviceConnection不为空"+usbDeviceConnection);

            }
            UsbDevice mUsbDevice = usbDevice;
            MtpDevice mMtpDevice = new MtpDevice(usbDevice);
            try {
                isOpenMtp = mMtpDevice.open(usbDeviceConnection);
                SmartToastUtils.showShort(mContext,"---为什么打开失败？"+mMtpDevice.open(usbDeviceConnection));
            } catch (Exception e) {
                SmartToastUtils.showShort(mContext,"isOpenMTP error in="+e.toString());
            }
            String usbDeviceName = mUsbDevice.getDeviceName();
            SmartToastUtils.showShort(mContext,"MTP状态"+isOpenMtp);
            SmartToastUtils.showShort(mContext,"设备名称"+usbDeviceName);
        } else {
            SmartToastUtils.showShort(mContext,"MTP权限需要申请");
            PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(READ_USB_DEVICE_PERMISSION), 0);
            manager.requestPermission(usbDevice,mPermissionIntent);
            return;
        }

        if(isOpenMtp){
            SmartToastUtils.showShort(mContext,"");
        }else{
            SmartToastUtils.showShort(mContext,"打开MTP失败,请重新插拔USB设备");
        }
//        boolean isOpenMtp=false;
//        MtpDevice mMtpDevice = new MtpDevice(usbDevice);
//        isOpenMtp=mMtpDevice.open(usbDeviceConnection);
//        SmartToastUtils.showShort(mContext,"MTP打开状态:"+isOpenMtp);
//        String deviceSeriNumber = null;
//        if(isOpenMtp){
//            SmartToastUtils.showShort(mContext,"MTP Open Success");
//            if(mMtpDevice!=null){
//                MtpDeviceInfo mtpDeviceInfo=mMtpDevice.getDeviceInfo();
//                if(mtpDeviceInfo!=null){
//                    deviceSeriNumber = mtpDeviceInfo.getSerialNumber();
//                }else{
//                    deviceSeriNumber = "xx";
//                }
//                SmartToastUtils.showShort(mContext,mtpDeviceInfo.getSerialNumber());
//                List list = new ArrayList();
//                //获取USB 设备存储卷
//                int[] storageIds = mMtpDevice.getStorageIds();
//                if (storageIds == null) {
//                    SmartToastUtils.showShort(mContext,"获取相机存储空间失败");
//                }
//
//                for (int storageId : storageIds) {
//                    //遍历设备存储寻找存在的JPEG 图片
//                    int[] objectHandles = mMtpDevice.getObjectHandles(storageId, MtpConstants.FORMAT_EXIF_JPEG, 0);
//                    if(objectHandles==null){
//                        SmartToastUtils.showShort(mContext,"获取照片失败");
//                    }
//                    //寻找
//                    for (int objectHandle : objectHandles) {
//                        MtpObjectInfo mtpobj = mMtpDevice.getObjectInfo(objectHandle);
//                        if (mtpobj == null) {
//                            continue;
//                        }
//                        long dateCreated=mtpobj.getDateCreated();
//                        //获取缓存对象
//                        byte[] bytes = mMtpDevice.getThumbnail(objectHandle);
//                        StringBuilder filePath = new StringBuilder();
//                        filePath.setLength(0);
//                        //获取内置存储路径根目录创建thumCache 文件夹
//                        filePath.append(Environment.getExternalStorageDirectory().getAbsolutePath())
//                                .append(File.separator)
//                                .append("thumbCache")
//                                .append(File.separator)
//                                .append(String.valueOf(dateCreated))
//                                .append(".jpg");
//                        File fileJpg = new File(filePath.toString());
//                        if (!fileJpg.exists() && bytes != null)
//                            FileUtils.bytes2File(bytes, filePath.toString());
//                    }
//                }
//            }
//
//
//        }else{
//            SmartToastUtils.showShort(mContext,"MTP Open Fail");
//        }
    }


    private UsbMassStorageDevice getUsbMass(UsbDevice usbDevice) {
        UsbMassStorageDevice[] storageDevices = UsbMassStorageDevice.getMassStorageDevices(mContext);
        for (UsbMassStorageDevice device : storageDevices) {
            if (usbDevice.equals(device.getUsbDevice())) {
                return device;
            }
        }
        return null;
    }


    private List<FileItem> getAllPhoto() {

        List<FileItem> photos = new ArrayList<>();

        String[] projection = new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DISPLAY_NAME};


        //asc 按升序排列
        //    desc 按降序排列
        //projection 是定义返回的数据，selection 通常的sql 语句，例如  selection=MediaStore.Images.ImageColumns.MIME_TYPE+"=? " 那么 selectionArgs=new String[]{"jpg"};
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");


        String imageId = null;

        String fileName=null;

        String filePath=null;

        FileItem fileItem=null;
        while (cursor.moveToNext()) {

            fileItem =new FileItem();

            imageId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));

            fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));

            filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            SmartToastUtils.showShort(mContext,imageId + " -- " + fileName + " -- " + filePath);

            photos.add(fileItem);

        }
        cursor.close();
        cursor = null;
        return photos;
    }
}