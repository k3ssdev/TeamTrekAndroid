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

public class AvisosAdapter extends BaseAdapter {

    private final Context context;
    private final List<Avisos> avisos;

    public AvisosAdapter(Context context, List<Avisos> avisos) {
        this.context = context;
        this.avisos = avisos;
    }

    @Override
    public int getCount() {
        return avisos.size();
    }

    @Override
    public Object getItem(int position) {
        return avisos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SimpleDateFormat")
    // Solo si estas seguro que el formato es correcto en todos los locales
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_news, parent, false);
        }

        // Obtiene el empleado actual basado en la posición.
        Avisos aviso = avisos.get(position);

        //idAviso, tituloAviso, descripcionAviso, fechaPublicacionAviso, fechaExpiracionAviso, autorIdAviso, activoAviso)
        // Enlaza las vistas de texto con las del layout.
        TextView idValue = convertView.findViewById(R.id.idValue);
        TextView tituloValue = convertView.findViewById(R.id.tituloValue);
        TextView descripcionValue = convertView.findViewById(R.id.descripcionValue);
        TextView fechaPublicacionValue = convertView.findViewById(R.id.fechaPublicacionValue);
        TextView fechaExpiracionValue = convertView.findViewById(R.id.fechaExpiracionValue);
        TextView autorValue = convertView.findViewById(R.id.autorIdValue);
        TextView activoValue = convertView.findViewById(R.id.activoValue);

        // Asigna los valores a las vistas de texto.
        idValue.setText(aviso.getIdAviso());
        tituloValue.setText(aviso.getTitulo());
        descripcionValue.setText(aviso.getDescripcion());
        fechaPublicacionValue.setText(aviso.getFechaPublicacion());
        fechaExpiracionValue.setText(aviso.getFechaExpiracion());
        autorValue.setText(aviso.getAutorId());
        activoValue.setText(aviso.isActivo());


        return convertView;
    }

    public void add(Avisos aviso) {
        avisos.add(aviso);
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

    public void addAll(List<Avisos> avisosList) {
        for (Avisos aviso : avisosList) {
            avisos.add(aviso);
        }
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

    public void clear() {
        avisos.clear(); // Limpia la lista
        notifyDataSetChanged(); // Notifica que los datos han cambiado
    }

}