package com.mirea.kt.ribo.calculator;

import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mirea.kt.ribo.R;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SubnetCalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnet);

        EditText ipInput = findViewById(R.id.input_ip_address);
        EditText maskInput = findViewById(R.id.input_subnet_mask);
        Button calculateButton = findViewById(R.id.calculate_button);

        TextView networkAddressOutput = findViewById(R.id.output_network_address);
        TextView broadcastAddressOutput = findViewById(R.id.output_broadcast_address);
        TextView hostCountOutput = findViewById(R.id.output_host_count);

        calculateButton.setOnClickListener(v -> {
            try {
                String ipAddress = ipInput.getText().toString();
                String subnetMaskStr = maskInput.getText().toString();
                if (ipAddress.isEmpty() || subnetMaskStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Введите IP адрес и маску", Toast.LENGTH_SHORT).show();
                    return;
                }
                int subnetMask = Integer.parseInt(subnetMaskStr);

                String networkAddress = calculateNetworkAddress(ipAddress, subnetMask);
                Log.d("tag", "посчитан сетевой адрес");

                String broadcastAddress = calculateBroadcastAddress(ipAddress, subnetMask);
                Log.d("tag", "посчитан широковещательный адрес");

                int hostCount = calculateHostCount(subnetMask);
                Log.d("tag", "посчитано кол-во доступных хостов");

                String networkOutput = "Сетевой адрес: " + networkAddress;
                String broadcastOutput = "Широковещательный адрес: " + broadcastAddress;
                String hostOutputStr = "Количество доступных хостов: " + hostCount;

                networkAddressOutput.setText(networkOutput);
                broadcastAddressOutput.setText(broadcastOutput);
                hostCountOutput.setText(hostOutputStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Введите корректную маску", Toast.LENGTH_SHORT).show();
            } catch (ArrayIndexOutOfBoundsException e) {
                Toast.makeText(getApplicationContext(), "Укажите IP адрес в правильном формате!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String calculateNetworkAddress(String ipAddress, int subnetMask) throws ArrayIndexOutOfBoundsException {
        int[] ip = convertIpToBinaryArray(ipAddress);
        int[] mask = createSubnetMaskArray(subnetMask);

        int[] networkAddress = new int[4];
        for (int i = 0; i < 4; i++) {
            networkAddress[i] = ip[i] & mask[i];
        }
        return convertBinaryArrayToIp(networkAddress);
    }

    private String calculateBroadcastAddress(String ipAddress, int subnetMask) throws ArrayIndexOutOfBoundsException {
        int[] ip = convertIpToBinaryArray(ipAddress);
        int[] mask = createSubnetMaskArray(subnetMask);

        int[] broadcastAddress = new int[4];
        for (int i = 0; i < 4; i++) {
            broadcastAddress[i] = ip[i] | (~mask[i] & 0xFF);
        }
        return convertBinaryArrayToIp(broadcastAddress);
    }

    private int calculateHostCount(int subnetMask) {
        return (int) Math.pow(2, 32 - subnetMask) - 2;
    }

    private int[] convertIpToBinaryArray(String ipAddress) throws ArrayIndexOutOfBoundsException {
        String[] octets = ipAddress.split("\\.");
        int[] binaryArray = new int[4];
        for (int i = 0; i < 4; i++) {
            binaryArray[i] = Integer.parseInt(octets[i]);
        }
        return binaryArray;
    }

    private int[] createSubnetMaskArray(int subnetMask) {
        int[] mask = new int[4];
        for (int i = 0; i < 4; i++) {
            if (subnetMask >= 8) {
                mask[i] = 255;
                subnetMask -= 8;
            } else {
                mask[i] = (int) (256 - Math.pow(2, 8 - subnetMask));
                subnetMask = 0;
            }
        }
        return mask;
    }

    private String convertBinaryArrayToIp(int[] binaryArray) {
        return binaryArray[0] + "." + binaryArray[1] + "." + binaryArray[2] + "." + binaryArray[3];
    }

}