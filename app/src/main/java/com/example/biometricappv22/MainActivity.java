package com.example.biometricappv22;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 101010;
    ImageButton imageButtonLogin;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButtonLogin=findViewById(R.id.imageButton);

        //access a different interfaces with biometrical tool
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {

            //access to the interfaces depending of the authentication

            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                                "AYUDA: Vuelve a escanear\n" + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "¡Escaneo de huella dactilar\n" +
                                "exitoso! \\n\n" +
                                "Iniciando sesión…", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, pantalla_inicio.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "ERROR: Intente de nuevo",
                                Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(MainActivity.this, pantalla_error.class);
                startActivity(intent);
                return;
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();
        imageButtonLogin.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });


        //Botton to return to home
        Button botonCerrar = findViewById(R.id.button);
        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), pantalla_metodoTradicional.class);
                startActivity(intent3);
            }
        });

    }
}