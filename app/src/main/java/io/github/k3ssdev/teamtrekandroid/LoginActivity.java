package io.github.k3ssdev.teamtrekandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;

public class LoginActivity extends AppCompatActivity {

    // Definir una clave pública estática para pasar el nombre de usuario como extra en el intent.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establecer el layout correspondiente a la actividad de inicio de sesión.
        setContentView(R.layout.login_activity);

        // Obtener referencias a los elementos de la interfaz de usuario.
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Establecer el escuchador para el evento de clic en el botón de inicio de sesión.
        buttonLogin.setOnClickListener(v -> {
            // Recuperar el nombre de usuario y la contraseña de los campos de texto.
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Validar que los campos de nombre de usuario y contraseña no estén vacíos.
            if (username.isEmpty() || password.isEmpty()) {
                // Si el nombre de usuario está vacío, mostrar un error en ese campo.
                if (username.isEmpty()) {
                    editTextUsername.setError("El usuario no puede estar vacío");
                }
                // Si la contraseña está vacía, mostrar un error en ese campo.
                if (password.isEmpty()) {
                    editTextPassword.setError("La contraseña no puede estar vacía");
                }
                // Mostrar un mensaje al usuario indicando que el inicio de sesión es incorrecto.
                Toast.makeText(this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar que el nombre de usuario solo contenga letras y números.
            if (!username.matches("[A-Za-z0-9]+")) {
                editTextUsername.setError("El usuario solo puede contener letras y números");
                Toast.makeText(this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar que la contraseña tenga entre 4 y 14 caracteres.
            if (password.length() < 4 || password.length() > 14) {
                editTextPassword.setError("La contraseña debe tener entre 4 y 14 caracteres");
                Toast.makeText(this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                return;
            }



            // Crea una instancia de WebServiceHandler
            DatabaseHandler webServiceHandler = new DatabaseHandler(this);

            // Llama a la tarea ValidarUsuario con execute
            webServiceHandler.new ValidarUsuario().execute(username, password);

        });
    }
}
