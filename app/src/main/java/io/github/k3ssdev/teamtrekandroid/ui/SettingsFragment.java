package io.github.k3ssdev.teamtrekandroid.ui;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import io.github.k3ssdev.teamtrekandroid.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Carga las preferencias desde el recurso XML
        setPreferencesFromResource(R.xml.settings, rootKey);

        // Aquí podrías añadir más lógica para gestionar los cambios de preferencias,
        // por ejemplo, validar la URL o escuchar cambios en el token.
    }

    // Puedes usar el siguiente método para tratar eventos, como clics en una preferencia
    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        // Ejemplo de cómo manejar el clic en una preferencia específica
        String key = preference.getKey();
        if ("pref_database_url".equals(key)) {
            // Manejar el clic en la preferencia de la URL de la base de datos
        } else if ("pref_token".equals(key)) {
            // Manejar el clic en la preferencia del token
        }

        return super.onPreferenceTreeClick(preference);
    }

    // Otras funciones para manejar eventos específicos de preferencias pueden ir aquí
}
