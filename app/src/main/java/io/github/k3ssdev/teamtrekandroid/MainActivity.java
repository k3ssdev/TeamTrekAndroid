package io.github.k3ssdev.teamtrekandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;
import io.github.k3ssdev.teamtrekandroid.database.Empleado;
import io.github.k3ssdev.teamtrekandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

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

        // Obtener datos del empleado y actualizar el nav_header_main
//        new DatabaseHandler.ConsultarEmpleadoTask(new DatabaseHandler.ConsultarEmpleadoCallback() {
//            @Override
//            public void onConsultaCompletada(List<Empleado> resultado) {
//                if (!resultado.isEmpty()) {
//                    // Obtiene el primer empleado (en caso de que haya más de uno, ajusta según tu lógica)
//                    Empleado empleado = resultado.get(0);
//                    // Actualiza el nombre y el correo del empleado en el nav_header_main
//                    TextView employeeNameTextView = (TextView) binding.navView.getHeaderView(0).findViewById(R.id.nav_employee_name);
//                    TextView employeeEmailTextView = (TextView) binding.navView.getHeaderView(0).findViewById(R.id.nav_employee_email);
//                    employeeNameTextView.setText(empleado.getNombre());
//                    employeeEmailTextView.setText(empleado.getEmail());
//                }
//            }
//        }).execute(userId);

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
}
