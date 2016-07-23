package com.fariddev.wrapperdroid.network;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Ahmed on 9/5/15.
 */
public class Bluetooth {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice mDevice;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Thread workerThread;
    private byte[] readBuffer;
    private int readBufferPosition;
    private int counter;
    private volatile boolean stopWorker;
    private String dataBuffer = "";

    public void connect(String btNameArg)
    {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        if(bluetoothAdapter == null)
        {
            Log.d("FindBT", "No bluetooth adapter available");
        }


        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals(btNameArg))
                {
                    mDevice = device;
                    Log.d("connect", "Bluetooth Device Found");
                    break;
                }
            }
        }

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        try {
            socket = mDevice.createRfcommSocketToServiceRecord(uuid);

            try {
                socket.connect();
                Log.e("", "Connected");
            } catch (IOException e) {
                Log.e("", e.getMessage());
                try {
                    Log.e("", "trying fallback...");

                    socket =(BluetoothSocket) mDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(mDevice,1);
                    socket.connect();

                    Log.e("", "Connected");
                }
                catch (Exception e2) {
                    Log.e("", "Couldn't establish Bluetooth connection!" + e2.getMessage());
                }
            }


            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();

            listenToInput();

            Log.d("OpenBT", "Bluetooth Opened");

        } catch (IOException e) {
            Log.d("connect", "Error occurred: " + e);
           e.printStackTrace();
        }

    }

    public void listenToInput()
    {
        final Handler handler = new Handler();
        final byte delimiter = (byte) '\n';

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = inputStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            inputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    dataBuffer += new String(encodedBytes, "US-ASCII") + "\n";
                                    readBufferPosition = 0;

                                    if(dataBuffer.length() >= 1000)
                                        dataBuffer = "";

                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    public String readLine(){

        int i = dataBuffer.indexOf('\n');

        if(i != -1){
            String tmp = dataBuffer.substring(0,i);

            dataBuffer = dataBuffer.substring(i+1,dataBuffer.length());

            return tmp;
        }

        return "";

    }

    //Send bluetooth data
    public void write(String msg) throws IOException
    {
        msg += "\n";
        outputStream.write(msg.getBytes());
        Log.d("SendBt", "Data Sent: " + msg);
    }

    //Close connection with bluetooth module
    public void close() throws IOException
    {
        stopWorker = true;
        outputStream.close();
        inputStream.close();
        socket.close();
        Log.d("CloseBT", "Bluetooth Closed");
    }
    
}
