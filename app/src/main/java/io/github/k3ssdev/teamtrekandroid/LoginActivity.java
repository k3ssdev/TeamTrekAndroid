package io.github.k3ssdev.teamtrekandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;

public class LoginActivity extends AppCompatActivity {

    // Esta es la clave que usarás para pasar el nombre de usuario a través del Intent.
    public static final String EXTRA_EMPLEADO = "io.github.k3ssdev.teamtrekandroid.EXTRA_EMPLEADO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Obtenemos las referencias a los EditText y al Button de la interfaz de usuario.
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Establecer el evento de clic para el botón de inicio de sesión.
        buttonLogin.setOnClickListener(v -> {
            // Obtener el nombre de usuario y contraseña de los EditText.
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Validar que los campos no estén vacíos, que el nombre de usuario sea alfanumérico y que la contraseña tenga entre 4 y 14 caracteres.
            if (username.isEmpty()) {
                editTextUsername.setError("El usuario no puede estar vacío");
                return;
            }
            if (password.isEmpty()) {
                editTextPassword.setError("La contraseña no puede estar vacía");
                return;
            }
            if (!username.matches("[A-Za-z0-9]+")) {
                editTextUsername.setError("El usuario solo puede contener letras y números");
                return;
            }
            if (password.length() < 4 || password.length() > 14) {
                editTextPassword.setError("La contraseña debe tener entre 4 y 14 caracteres");
                return;
            }

            // Instanciamos DatabaseHandler para realizar la validación del usuario.
            DatabaseHandler webServiceHandler = new DatabaseHandler(this);
            // Creamos un callback para manejar las respuestas del proceso de validación.
            DatabaseHandler.ValidacionCallback callback = new DatabaseHandler.ValidacionCallback() {
                @Override
                public void onValidacionCompletada(boolean exito, String empleadoID, String mensaje) {
                    // Si la validación es exitosa, iniciar la actividad que deseas y pasa la información necesaria.
                    if(exito) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(EXTRA_EMPLEADO, empleadoID); // Asegúrate de definir esta constante o usar la clave adecuada para el Intent.
                        startActivity(intent);
                        finish(); // Finalizamos esta actividad para que no se pueda volver a ella.
                    } else {
                        // Si la validación no es exitosa, mostrar el mensaje de error.
                        Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onError(String mensaje) {
                    // Si hay un error, muestra un Toast con el mensaje de error.
                    Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_LONG).show();
                }
            };

// Llamamos al método validarUsuario con el nombre de usuario y contraseña ingresados y el callback creado.
            webServiceHandler.validarUsuario(username, password, callback);

        });
    }
}
