package io.github.k3ssdev.teamtrekandroid.ui.employee;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import io.github.k3ssdev.teamtrekandroid.R;
import io.github.k3ssdev.teamtrekandroid.SharedViewModel;
import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;
import io.github.k3ssdev.teamtrekandroid.database.Empleado;
import io.github.k3ssdev.teamtrekandroid.database.EmployeeAdapter;

public class EmployeeFragment extends Fragment {

    private EmployeeAdapter employeeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employee, container, false);

        ListView listView = rootView.findViewById(R.id.listView);

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Configura el adaptador
        List<Empleado> empleadoList = getEmployeeDataFromDatabase(sharedViewModel.getSelected().getValue());

        if (empleadoList != null && !empleadoList.isEmpty()) {
            EmployeeAdapter employeeAdapter = new EmployeeAdapter(requireContext(), empleadoList);
            listView.setAdapter(employeeAdapter);
        } else {
            Log.d("EmployeeFragment", "La lista de empleados está vacía o nula.");
        }

        return rootView;
    }

    private List<Empleado> getEmployeeDataFromDatabase(String username) {
        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("io.github.k3ssdev.teamtrekandroid_preferences", Context.MODE_PRIVATE);
        DatabaseHandler.ConsultarEmpleadoTask consultarEmpleadoTask = new DatabaseHandler.ConsultarEmpleadoTask(new DatabaseHandler.ConsultarEmpleadoCallback() {
            @Override
            public void onConsultaCompletada(List<Empleado> resultado) {
                // Asegúrate de que el fragment está todavía en el estado correcto
                if (isAdded() && resultado != null && !resultado.isEmpty()) {
                    employeeAdapter = new EmployeeAdapter(requireContext(), resultado);
                }
            }
        }, sharedPreferences);

        try {
            return consultarEmpleadoTask.execute(username).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(); // Devolver una lista vacía en caso de error
    }
}
