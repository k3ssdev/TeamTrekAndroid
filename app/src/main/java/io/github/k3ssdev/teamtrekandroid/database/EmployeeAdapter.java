package io.github.k3ssdev.teamtrekandroid.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import io.github.k3ssdev.teamtrekandroid.R;

public class EmployeeAdapter extends BaseAdapter {

    private final Context context;
    private final List<Empleado> employees;

    public EmployeeAdapter(Context context, List<Empleado> employees) {
        this.context = context;
        this.employees = employees;
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SimpleDateFormat") // Solo si estas seguro que el formato es correcto en todos los locales
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_employee, parent, false);
        }

        // Obtiene el empleado actual basado en la posición.
        Empleado employee = employees.get(position);

        // Enlaza las vistas de texto con las del layout.
        TextView idValue = convertView.findViewById(R.id.idValue);
        TextView nombreValue = convertView.findViewById(R.id.nombreValue);
        TextView departamentoValue = convertView.findViewById(R.id.departamentoValue);
        TextView horarioValue = convertView.findViewById(R.id.horarioValue);
        TextView usuarioValue = convertView.findViewById(R.id.usuarioValue);
        TextView correoValue = convertView.findViewById(R.id.correoValue);
        TextView telefonoValue = convertView.findViewById(R.id.telefonoValue);
        TextView direccionValue = convertView.findViewById(R.id.direccionValue);
        TextView fechaNacimientoValue = convertView.findViewById(R.id.fechaNacimientoValue);
        TextView fechaIngresoValue = convertView.findViewById(R.id.fechaIngresoValue);

        // Asigna los valores a las vistas de texto.
        idValue.setText(String.valueOf(employee.getID()));
        nombreValue.setText(employee.getNombre());
        departamentoValue.setText(employee.getNombreDepartamento());

        // Formatea las horas de inicio y fin del horario.
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedHoraInicio = timeFormat.format(employee.getHoraInicio());
        String formattedHoraFin = timeFormat.format(employee.getHoraFin());
        horarioValue.setText(formattedHoraInicio + " - " + formattedHoraFin);

        usuarioValue.setText(employee.getUsuario());
        correoValue.setText(employee.getEmail());
        telefonoValue.setText(employee.getTelefono());
        direccionValue.setText(employee.getDireccion());

        // Formatea y asigna las fechas.
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        fechaNacimientoValue.setText(dateFormat.format(employee.getFechaNacimiento()));
        fechaIngresoValue.setText(dateFormat.format(employee.getFechaIngreso()));

        // Retorna la vista completa para mostrarla en la lista.
        return convertView;
    }

}