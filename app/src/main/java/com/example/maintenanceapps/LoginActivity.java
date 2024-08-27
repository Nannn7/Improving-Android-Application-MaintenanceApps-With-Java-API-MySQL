package com.example.maintenanceapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.maintenanceapps.API.APIClient;
import com.example.maintenanceapps.API.APIInterface;
import com.example.maintenanceapps.Model.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN_ACTIVITY";

    private TextView tvInfo;
    private EditText mUname, mPassword;
    private CardView btnLogin;
    OnLoginFormActivityListener mLoginFormActivityListener;

    public interface OnLoginFormActivityListener {
        public void performLogin(String nama);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvInfo = (TextView) findViewById(R.id.txt_info);
        mUname = (EditText) findViewById(R.id.user_uname);
        mPassword = (EditText) findViewById(R.id.user_pass);
        btnLogin = (CardView) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String uname = mUname.getText().toString();
        String password = mPassword.getText().toString();

        APIInterface aiData = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseLogin> login = aiData.login(uname, password);

        login.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.body().getKode().equals("1")){
                    if (response.body().getResponse().getStatus().equals("1")) {
                        String userRole = response.body().getResponse().getRole();
                        if (userRole.equals("1") || userRole.equals("0") || userRole.equals("4")) {
                            // maintenance Role
                            MainActivity.prefConfig.writeLoginStatus(true);
                            MainActivity.prefConfig.writeID(response.body().getResponse().getID());
                            MainActivity.prefConfig.writeName(response.body().getResponse().getNama());
                            MainActivity.prefConfig.writeRole(userRole);

                            mUname.setText("");
                            mPassword.setText("");

                            Toast.makeText(LoginActivity.this, "Selamat Datang di Departemen Maintenance", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            if (userRole.equals("4")) {
                                MainActivity.prefConfig.writeLoginStatus(true);
                                MainActivity.prefConfig.writeID(response.body().getResponse().getID());
                                MainActivity.prefConfig.writeName(response.body().getResponse().getNama());
                                MainActivity.prefConfig.writeRole(userRole);

                                mUname.setText("");
                                mPassword.setText("");

                                Toast.makeText(LoginActivity.this, "Selamat Datang di Halaman Riwayat", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(LoginActivity.this, History.class);
                                startActivity(intent1);
                            }
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            alert.setTitle("Anda tidak dapat mengakses aplikasi!");
                            alert.setMessage("Hanya user yang terdaftar sebagai maintenance yang dapat mengakses aplikasi");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            alert.show();
                        }
                    } else {
                        tvInfo.setText("Maaf akun anda dinonaktifkan\nSilahkan hubungi administrator");
                        Toast.makeText(LoginActivity.this, "Anda tidak bisa login dalam aplikasi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tvInfo.setText("Silahkan cek username atau password anda");
                    Toast.makeText(LoginActivity.this, "Gagal login!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
