package apri.dts.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static apri.dts.login.LoginActivity.FILENAME;

public class HomeActivity extends AppCompatActivity {
    TextView username, email, nama, alamat, asalSekolah;
    Button btnLogout;
    public static final String FILENAME = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Halaman Utama");

        username = findViewById(R.id.tvUsername);
        email = findViewById(R.id.tvEmail);
        nama = findViewById(R.id.tvNamaLengkap);
        asalSekolah = findViewById(R.id.tvAsalSekolah);
        alamat = findViewById(R.id.tvAlamat);
        btnLogout = findViewById(R.id.btnLogout);
        bacaFileLogin();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tampilkanDialogKonformasiLogout();
            }
        });
    }
    void bacaFileLogin(){
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);
        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e){
                System.out.println("Error "+ e.getMessage());
            }
            String data = text.toString();
            String[] dataUser = data.split(";");
            bacaDataUser(dataUser[0]);
        }
    }
    void bacaDataUser(String fileName){
            File sdcard = getFilesDir();
            File file = new File(sdcard, fileName);
            if (file.exists()){
                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line = br.readLine();
                    while (line != null){
                        text.append(line);
                        line = br.readLine();
                    }
                    br.close();
                } catch (IOException e){
                    System.out.println("Error "+e.getMessage());
                }
                String data = text.toString();
                String[] dataUser = data.split(";");
                username.setText(dataUser[0]);
                email.setText(dataUser[2]);
                nama.setText(dataUser[3]);
                asalSekolah.setText(dataUser[4]);
                alamat.setText(dataUser[5]);
            } else {
                Toast.makeText(this, "User tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
    }
    void hapusFile(){
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()){
            file.delete();
        }
    }
    void tampilkanDialogKonformasiLogout(){
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah anda yakin logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hapusFile();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }
}