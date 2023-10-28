package io.github.k3ssdev.teamtrekandroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.github.k3ssdev.teamtrekandroid.R;
import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;

public class LoginActivity extends AppCompatActivity {
    // Constante para el mensaje extra
    public static final String EXTRA_MESSAGE = "io.github.k3ssdev.loginformsqliteandroid.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Obtiene referencias a los elementos de la interfaz de usuario
        EditText editTextUsername_apr = findViewById(R.id.editTextUsername);
        EditText editTextPassword_apr = findViewById(R.id.editTextPassword);
        Button buttonLogin_apr = findViewById(R.id.buttonLogin);

        // Configura un escuchador de clic para el botón de inicio de sesión
        buttonLogin_apr.setOnClickListener(v -> {
            String usuario_apr = editTextUsername_apr.getText().toString();
            String contrasena_apr = editTextPassword_apr.getText().toString();

            // Valida que usuario y contraseña no estén vacíos
            if (usuario_apr.isEmpty() || contrasena_apr.isEmpty()) {
                // Comprueba que campo esté vacío y muestra un mensaje de error
                if (usuario_apr.isEmpty()) {
                    editTextUsername_apr.setError("El usuario no puede estar vacío");
                }
                if (contrasena_apr.isEmpty()) {
                    editTextPassword_apr.setError("La contraseña no puede estar vacía");
                }
                // Muestra un mensaje toast
                Toast.makeText(this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Valida que usuario solo use letras y números
            if (!usuario_apr.matches("[A-Za-z0-9]+")) {
                // Muestra un mensaje de error
                editTextUsername_apr.setError("El usuario solo puede contener letras y números");
                // Muestra un mensaje toast
                Toast.makeText(this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Valida que usuario tenga entre 4 y 8 caracteres
            if (contrasena_apr.length() < 4 || contrasena_apr.length() > 14) {
                // Muestra un mensaje de error
                editTextPassword_apr.setError("La contraseña debe tener entre 4 y 8 caracteres");
                // Muestra un mensaje toast
                Toast.makeText(this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crea una instancia de WebServiceHandler
            DatabaseHandler webServiceHandler_apr = new DatabaseHandler(this);

            // Llama a la tarea ValidarUsuario con execute
            webServiceHandler_apr.new ValidarUsuario().execute(usuario_apr, contrasena_apr);
        });
    }
}