package io.github.k3ssdev.teamtrekandroid.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.github.k3ssdev.teamtrekandroid.SharedViewModel;
import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;
import io.github.k3ssdev.teamtrekandroid.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Obtener la instancia de HomeViewModel utilizando ViewModelProvider
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Utilizar binding para interactuar con el layout
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtener el SharedViewModel del ámbito de la actividad
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        // Observar los cambios en el modelo de datos compartido y actualizar la UI correspondientemente
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), s -> {
            // Registrar en el log el nombre de usuario observado
            Log.d("HomeFragment", "Username observed: " + s);
            // Suponiendo que 's' es el nombre de usuario, se establece en el TextView
            binding.nombreUsuario.setText(s);
        });

        // Usa databaseHandler para obtener el puesto del empleado y el departamento
        // y establecerlos en los TextView correspondientes



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Anular el binding cuando la vista es destruida
        binding = null;
    }
}
