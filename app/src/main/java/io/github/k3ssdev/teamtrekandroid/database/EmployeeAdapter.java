package io.github.k3ssdev.teamtrekandroid.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import io.github.k3ssdev.teamtrekandroid.R;

public class EmployeeAdapter extends BaseAdapter {

    private Context context;
    private List<Empleado> employees;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_employee, parent, false);
        }

        Empleado employee = employees.get(position);

        TextView idLabel = convertView.findViewById(R.id.idLabel);
        TextView idValue = convertView.findViewById(R.id.idValue);
        TextView nombreLabel = convertView.findViewById(R.id.nombreLabel);
        TextView nombreValue = convertView.findViewById(R.id.nombreValue);
        TextView apellidoLabel = convertView.findViewById(R.id.apellidoLabel);
        TextView apellidoValue = convertView.findViewById(R.id.apellidoValue);
        TextView identificacionLabel = convertView.findViewById(R.id.identificacionLabel);
        TextView identificacionValue = convertView.findViewById(R.id.identificacionValue);
        TextView correoLabel = convertView.findViewById(R.id.correoLabel);
        TextView correoValue = convertView.findViewById(R.id.correoValue);
        TextView telefonoLabel = convertView.findViewById(R.id.telefonoLabel);
        TextView telefonoValue = convertView.findViewById(R.id.telefonoValue);
        TextView direccionLabel = convertView.findViewById(R.id.direccionLabel);
        TextView direccionValue = convertView.findViewById(R.id.direccionValue);
        TextView fechaContratacionLabel = convertView.findViewById(R.id.fechaContratacionLabel);
        TextView fechaContratacionValue = convertView.findViewById(R.id.fechaContratacionValue);

        idLabel.setText("ID: ");
        idValue.setText(String.valueOf(employee.getID()));

        nombreLabel.setText("Nombre: ");
        nombreValue.setText(employee.getNombre());

        apellidoLabel.setText("Apellido: ");
        apellidoValue.setText(employee.getApellido());

        identificacionLabel.setText("Identificación: ");
        identificacionValue.setText(employee.getIdentificacion());

        correoLabel.setText("Correo: ");
        correoValue.setText(employee.getEmail());

        telefonoLabel.setText("Teléfono: ");
        telefonoValue.setText(employee.getTelefono());

        direccionLabel.setText("Dirección: ");
        direccionValue.setText(employee.getDireccion());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(employee.getFechaContratacion());

        fechaContratacionLabel.setText("Antigüedad: ");
        fechaContratacionValue.setText(formattedDate);

        return convertView;
    }
}
