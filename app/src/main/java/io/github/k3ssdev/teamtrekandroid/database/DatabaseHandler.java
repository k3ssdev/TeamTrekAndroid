package io.github.k3ssdev.teamtrekandroid.database;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DatabaseHandler {
    private Activity activity;
    private SharedPreferences sharedPreferences;
    public static final String EXTRA_MESSAGE = "io.github.k3ssdev.teamtrekandroid.USERNAME";
    public static final String PREFERENCES_FILE = "io.github.k3ssdev.teamtrekandroid.PREFERENCES";
    public static final String JWT_TOKEN_KEY = "JWT_TOKEN";

    public interface ValidacionCallback {
        void onValidacionCompletada(boolean exito, String empleadoID, String mensaje);

        void onError(String mensaje);
    }

    public interface ConsultaCallback {
        void onConsultaCompletada(JSONObject resultado, String mensaje);
    }

    public DatabaseHandler(Activity activity) {
        this.activity = activity;
        this.sharedPreferences = activity.getSharedPreferences(PREFERENCES_FILE, Activity.MODE_PRIVATE);
    }

    public void validarUsuario(String usuario, String contrasena, ValidacionCallback callback) {
        new ValidarUsuarioTask(callback).execute(usuario, contrasena);
    }

    public void consultarEmpleado(String empleadoID, ConsultaCallback callback) {
        String jwtToken = sharedPreferences.getString(JWT_TOKEN_KEY, null);
        if (jwtToken == null || !isTokenValid(jwtToken)) {
            if (callback != null) {
                callback.onConsultaCompletada(null, "Token no válido o expirado.");
            }
            return;
        }

        new ConsultarEmpleadoTask(callback, jwtToken).execute(empleadoID);
    }

    private boolean isTokenValid(String token) {
        try {
            JWT jwt = new JWT(token);
            return !jwt.isExpired(10); // buffer de 10 segundos
        } catch (Exception e) {
            return false;
        }
    }

    private class ValidarUsuarioTask extends AsyncTask<String, Void, String> {
        private ValidacionCallback callback;

        ValidarUsuarioTask(ValidacionCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String usuario = strings[0];
            String contrasena = strings[1];
            try {
                URL url = new URL("http://10.0.2.2/teamtrek/validacuenta_jwt.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                JSONObject authDetails = new JSONObject();
                authDetails.put("usuario", usuario);
                authDetails.put("contrasena", contrasena);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(authDetails.toString());
                writer.flush();
                writer.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    return stringBuilder.toString();
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String jwtToken = jsonObject.getString("token");
                    sharedPreferences.edit().putString(JWT_TOKEN_KEY, jwtToken).apply();

                    if (callback != null) {
                        callback.onValidacionCompletada(true, jwtToken, "Usuario validado exitosamente.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onValidacionCompletada(false, null, "Error en la respuesta del servidor.");
                    }
                }
            } else {
                if (callback != null) {
                    callback.onValidacionCompletada(false, null, "No se pudo conectar con el servidor o la respuesta fue errónea.");
                }
            }
        }
    }

    private class ConsultarEmpleadoTask extends AsyncTask<String, Void, JSONObject> {
        private ConsultaCallback callback;
        private String jwtToken;

        ConsultarEmpleadoTask(ConsultaCallback callback, String jwtToken) {
            this.callback = callback;
            this.jwtToken = jwtToken;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String empleadoID = strings[0];
            try {
                URL url = new URL("http://10.0.2.2/teamtrek/consultaempleados_jwt1.php"); // ?userid=" + empleadoID);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + jwtToken);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    return new JSONObject(stringBuilder.toString());
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject != null) {
                if (callback != null) {
                    callback.onConsultaCompletada(jsonObject, "Consulta completada exitosamente.");
                }
            } else {
                if (callback != null) {
                    callback.onConsultaCompletada(null, "No se pudo obtener la información del empleado.");
                }
            }
        }
    }

    // Métodos para leer y manejar XML

    public String getValueFromXML(String xmlString, String tagName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            NodeList nodes = doc.getElementsByTagName(tagName);
            if (nodes.getLength() > 0) {
                return nodes.item(0).getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Otros métodos relacionados con XML

}
