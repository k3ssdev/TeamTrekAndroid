package io.github.k3ssdev.teamtrekandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.List;

import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;
import io.github.k3ssdev.teamtrekandroid.database.Empleado;
import io.github.k3ssdev.teamtrekandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private final MainActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar el enlace con el layout de la actividad
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar la barra de herramientas
        setSupportActionBar(binding.appBarMain.toolbar);

        // Configurar el layout del cajón de navegación (drawer)
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configurar AppBarConfiguration para relacionar el NavController con la AppBar
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_calendar, R.id.nav_employee)
                .setOpenableLayout(drawer)
                .build();

        // Configurar el NavController para la navegación
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Obtener el intent y el nombre de usuario pasado desde LoginActivity
        Intent intent = getIntent();
        String userId = intent.getStringExtra(DatabaseHandler.EXTRA_MESSAGE);

        // Inicializar el SharedViewModel y seleccionar el nombre de usuario como dato actual
        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.select(userId);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Obtener datos del empleado y actualizar el nav_header_main
        new DatabaseHandler.ConsultarEmpleadoTask(new DatabaseHandler.ConsultarEmpleadoCallback() {
            @Override
            public void onConsultaCompletada(List<Empleado> resultado) {
                if (!resultado.isEmpty()) {
                    // Obtiene el primer empleado y actualiza la interfaz de usuario
                    Empleado empleado = resultado.get(0);
                    TextView employeeNameTextView = binding.navView.getHeaderView(0).findViewById(R.id.nav_employee_name);
                    TextView employeeEmailTextView = binding.navView.getHeaderView(0).findViewById(R.id.nav_employee_email);
                    employeeNameTextView.setText(empleado.getNombre());
                    employeeEmailTextView.setText(empleado.getEmail());
                }
            }
        }, sharedPreferences).execute(userId);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú; esto añade elementos a la barra de acción si está presente.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Este método se llama cuando se presiona el botón de subir.
        // Deja que NavigationUI maneje el botón de subir.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Lógica para abrir la configuración fragment settings

            // Navegar al fragment de configuración
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_settings);


            return true;
        } else if (id == R.id.action_logout) {
            // Lógica para cerrar la sesión
            // Esto podría involucrar limpiar datos de sesión, mostrar un diálogo de confirmación, etc.
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        // Crear un AlertDialog para confirmar el cierre de sesión
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión") // Titulo del diálogo
                .setMessage("¿Estás seguro de que quieres cerrar sesión?") // Mensaje de confirmación
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() { // Botón de confirmación
                    public void onClick(DialogInterface dialog, int which) {
                        // Aquí iría la lógica para limpiar los datos de la sesión del usuario

                        // Inicia LoginActivity y finaliza la actividad actual
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Finaliza MainActivity
                    }
                })
                .setNegativeButton("No", null) // Botón de cancelación no hace nada y cierra el diálogo
                .setIcon(android.R.drawable.ic_dialog_alert) // Icono
                .show(); // Mostrar el AlertDialog
    }

}
