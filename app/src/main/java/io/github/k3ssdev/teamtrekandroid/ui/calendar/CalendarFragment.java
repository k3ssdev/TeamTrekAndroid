package io.github.k3ssdev.teamtrekandroid.ui.calendar;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Locale;

import io.github.k3ssdev.teamtrekandroid.SharedViewModel;
import io.github.k3ssdev.teamtrekandroid.database.DatabaseHandler;
import io.github.k3ssdev.teamtrekandroid.database.Empleado;
import io.github.k3ssdev.teamtrekandroid.databinding.FragmentCalendarBinding;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Observar los cambios en el modelo de datos compartido
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), username -> {
            Log.d("HomeFragment", "Username recibido: " + username);

            // Llama al AsyncTask para obtener los datos del empleado
            new DatabaseHandler.ConsultarEmpleadoTask(new DatabaseHandler.ConsultarEmpleadoCallback() {
                @Override
                public void onConsultaCompletada(List<Empleado> resultado) {
                    // Aquí actualizas tu UI con el resultado
                    // Como es un ejemplo, solo actualizaré el nombre del empleado
                    if (!resultado.isEmpty()) {
                        // Obtiene el primer empleado (en caso de que haya más de uno, ajusta según tu lógica)
                        Empleado empleado = resultado.get(0);
                        binding.labelCalendar.setText(empleado.getNombre());
                        binding.valueCalendar.setText(empleado.getDescripcionHorario());

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

                        String horaInicio = sdf.format(empleado.getHoraInicio());
                        String horaFin = sdf.format(empleado.getHoraFin());

                        binding.textTurno.setText(horaInicio + " - " + horaFin);
                    }
                }
            }).execute(username);
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
