package io.github.k3ssdev.teamtrekandroid.ui.employee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.github.k3ssdev.teamtrekandroid.LoginActivity;
import io.github.k3ssdev.teamtrekandroid.R;
import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;
import io.github.k3ssdev.teamtrekandroid.database.Empleado;
import io.github.k3ssdev.teamtrekandroid.database.EmployeeAdapter;

public class EmployeeFragment extends Fragment {

    private ListView listView; // Variable de instancia a nivel de la clase.
    private EmployeeAdapter employeeAdapter;
    private List<Empleado> empleadoList = new ArrayList<>(); // Inicialización aquí.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employee, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView); // Usar variable de instancia.

        getEmployeeDataFromDatabase();

        // Resto del código de tu fragment
        return rootView;
    }

    private void getEmployeeDataFromDatabase() {
        DatabaseHandler.ConsultaCallback callback = new DatabaseHandler.ConsultaCallback() {
            @Override
            public void onConsultaCompletada(JSONObject resultado, String mensaje) {
                if (resultado != null) {
                    try {
                        JSONArray empleadosJsonArray = resultado.getJSONArray("empleados");
                        final List<Empleado> empleadosTemporales = new ArrayList<>();

                        for (int i = 0; i < empleadosJsonArray.length(); i++) {
                            JSONObject empleadoJson = empleadosJsonArray.getJSONObject(i);
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());


                            try {
                                Empleado empleado = parseEmpleado(empleadoJson, formatoFecha, formatoHora);
                                empleadosTemporales.add(empleado);
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    empleadoList.clear();
                                    empleadoList.addAll(empleadosTemporales);
                                    if (employeeAdapter == null) {
                                        employeeAdapter = new EmployeeAdapter(requireContext(), empleadoList);
                                        listView.setAdapter(employeeAdapter);
                                    } else {
                                        employeeAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + mensaje, Toast.LENGTH_LONG).show();
                }
            }

            private Empleado parseEmpleado(JSONObject empleadoJson, SimpleDateFormat formatoFecha, SimpleDateFormat formatoHora) throws JSONException, ParseException {
                // Extrayendo cada objeto del JSONObject principal

                JSONObject identificacion = empleadoJson.getJSONObject("identificacion");
                JSONObject departamento = empleadoJson.getJSONObject("departamento");
                JSONObject horario = empleadoJson.getJSONObject("horario");
                JSONObject datosPersonales = empleadoJson.getJSONObject("datosPersonales");

                String id = identificacion.getString("ID");
                String nombre = identificacion.getString("Nombre");
                Date fechaIngreso = formatoFecha.parse(identificacion.getString("FechaIngreso"));
                String nombreDepartamento = departamento.getString("NombreDepartamento");
                String descripcionHorario = horario.getString("DescripcionHorario");
                Date horaInicio = formatoHora.parse(horario.getString("HoraInicio"));
                Date horaFin = formatoHora.parse(horario.getString("HoraFin"));
                String usuario = identificacion.getString("Usuario");
                String telefono = datosPersonales.getString("Telefono");
                String direccion = datosPersonales.getString("Direccion");
                String email = datosPersonales.getString("Email");
                Date fechaNacimiento = formatoFecha.parse(datosPersonales.getString("FechaNacimiento"));
                String nif = datosPersonales.getString("NIF");

                // Convierte el ID a entero (asegúrate de que el ID sea un entero válido en tu JSON)
                int idInt = Integer.parseInt(id);

                return new Empleado(
                        idInt,
                        nombre,
                        fechaIngreso,
                        nombreDepartamento,
                        descripcionHorario,
                        horaInicio,
                        horaFin,
                        usuario,
                        telefono,
                        direccion,
                        email,
                        fechaNacimiento,
                        nif
                );
            }
        };

        // get intent extra_empleado
        String empleadoid = getActivity().getIntent().getStringExtra(LoginActivity.EXTRA_EMPLEADO);

        DatabaseHandler dbHandler = new DatabaseHandler(getActivity());
        //dbHandler.consultarEmpleado(LoginActivity.EXTRA_EMPLEADO, callback);
        dbHandler.consultarEmpleado(empleadoid, callback);
    }
}
