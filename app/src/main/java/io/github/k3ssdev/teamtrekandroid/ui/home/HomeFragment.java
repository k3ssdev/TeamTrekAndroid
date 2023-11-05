package io.github.k3ssdev.teamtrekandroid.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import java.util.List;

import io.github.k3ssdev.teamtrekandroid.SharedViewModel;
import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;
import io.github.k3ssdev.teamtrekandroid.database.Empleado;
import io.github.k3ssdev.teamtrekandroid.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Observar los cambios en el modelo de datos compartido
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), username -> {
            Log.d("HomeFragment", "Username recibido: " + username);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

            // Llama al AsyncTask para obtener los datos del empleado
            new DatabaseHandler.ConsultarEmpleadoTask(new DatabaseHandler.ConsultarEmpleadoCallback() {
                @Override
                public void onConsultaCompletada(List<Empleado> resultado) {
                    // Aquí actualizas tu UI con el resultado
                    // Como es un ejemplo, solo actualizaré el nombre del empleado
                    if (!resultado.isEmpty()) {
                        // Obtiene el primer empleado (en caso de que haya más de uno, ajusta según tu lógica)
                        Empleado empleado = resultado.get(0);
                        binding.nombreUsuario.setText(empleado.getNombre());
                        binding.departamentoUsuario.setText(empleado.getNombreDepartamento());
                        binding.emailUsuario.setText(empleado.getEmail());
                    }
                }
            }, sharedPreferences).execute(username);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // ... Asegúrate de tener definida la interfaz ConsultarEmpleadoCallback y la clase Empleado correctamente
}
