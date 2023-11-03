package io.github.k3ssdev.teamtrekandroid.ui.employee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import io.github.k3ssdev.teamtrekandroid.R;
import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;
import io.github.k3ssdev.teamtrekandroid.database.Empleado;
import io.github.k3ssdev.teamtrekandroid.database.EmployeeAdapter;

public class EmployeeFragment extends Fragment {

    private EmployeeAdapter employeeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employee, container, false);

        ListView listView = rootView.findViewById(R.id.listView);

        // Configura el adaptador
        List<Empleado> empleadoList = getEmployeeDataFromDatabase();

        if (empleadoList != null && !empleadoList.isEmpty()) {
            EmployeeAdapter employeeAdapter = new EmployeeAdapter(requireContext(), empleadoList);
            listView.setAdapter(employeeAdapter);
        } else {
            Log.d("EmployeeFragment", "La lista de empleados está vacía o nula.");
        }

        // Resto del código de tu fragment

        return rootView;
    }

    private List<Empleado> getEmployeeDataFromDatabase() {
        DatabaseHandler.ConsultarEmpleadoTask consultarEmpleadoTask = new DatabaseHandler.ConsultarEmpleadoTask();
        try {
            return consultarEmpleadoTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(); // Devolver una lista vacía en caso de error
    }
}
