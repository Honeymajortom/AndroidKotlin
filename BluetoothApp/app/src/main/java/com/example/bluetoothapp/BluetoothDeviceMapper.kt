package com.example.bluetoothapp

import android.bluetooth.BluetoothDevice

fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain{
   return BluetoothDeviceDomain(
       name = name,

   )
}