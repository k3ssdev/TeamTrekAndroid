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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        }

        Empleado employee = employees.get(position);

        TextView idTextView = convertView.findViewById(R.id.idEmployeeTextView);
        TextView nameTextView = convertView.findViewById(R.id.nombreEmployeeTextView);
        TextView lastNameTextView = convertView.findViewById(R.id.apellidoEmployeeTextView);
        TextView identificationTextView = convertView.findViewById(R.id.identificacionEmployeeTextView);
        TextView emailTextView = convertView.findViewById(R.id.correoEmployeeTextView);
        TextView phoneTextView = convertView.findViewById(R.id.telefonoEmployeeTextView);
        TextView addressTextView = convertView.findViewById(R.id.direccionEmployeeTextView);
        TextView dateTextView = convertView.findViewById(R.id.fechaContratacionEmployeeTextView);

        idTextView.setText(String.valueOf(employee.getID()));
        nameTextView.setText(employee.getNombre());
        lastNameTextView.setText(employee.getApellido());
        identificationTextView.setText(employee.getIdentificacion());
        emailTextView.setText(employee.getEmail());
        phoneTextView.setText(employee.getTelefono());
        addressTextView.setText(employee.getDireccion());

        // Formatear la fecha y establecerla en el TextView
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Cambia el formato según tus necesidades
        String formattedDate = dateFormat.format(employee.getFechaContratacion());
        dateTextView.setText(formattedDate);

        return convertView;
    }
}
