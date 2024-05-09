package com.example.bluetoothapp

import android.bluetooth.BluetoothDevice

typealias BluetoothDeviceDomain = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val address: String
)
