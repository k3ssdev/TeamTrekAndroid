package io.github.k3ssdev.teamtrekandroid.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;

import io.github.k3ssdev.teamtrekandroid.R;
import io.github.k3ssdev.teamtrekandroid.SharedViewModel;
import io.github.k3ssdev.teamtrekandroid.database.Avisos;
import io.github.k3ssdev.teamtrekandroid.database.AvisosAdapter;
import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler.AvisosTask;
import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler.ConsultarEmpleadoTask;
import io.github.k3ssdev.teamtrekandroid.database.Empleado;
import io.github.k3ssdev.teamtrekandroid.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private AvisosAdapter avisosAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listView = binding.listView;
        avisosAdapter = new AvisosAdapter(getContext(), new ArrayList<>());
        listView.setAdapter(avisosAdapter);

        // Observar los cambios en el modelo de datos compartido
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), username -> {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

            // Consulta de datos del empleado
            new ConsultarEmpleadoTask(empleadoResult -> {
                // Actualizar la UI con los datos del empleado
                if (!empleadoResult.isEmpty()) {
                    Empleado empleado = empleadoResult.get(0); // Tomamos el primer resultado como ejemplo
                    binding.nombreUsuario.setText(empleado.getNombre());
                    binding.departamentoUsuario.setText(empleado.getNombreDepartamento());
                    binding.emailUsuario.setText(empleado.getEmail());
                    // Y así sucesivamente con el resto de datos del empleado...
                }
            }, sharedPreferences).execute(username);

            // Consulta de avisos
            new AvisosTask(avisosResult -> {
                // Actualizar la UI con los avisos
                if (!avisosResult.isEmpty()) {
                    avisosAdapter.clear();
                    avisosAdapter.addAll(avisosResult);
                    avisosAdapter.notifyDataSetChanged();
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

    // Aquí irían otras funciones y clases necesarias para este fragmento, si las hay...
}
