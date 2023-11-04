package io.github.k3ssdev.teamtrekandroid.database;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import io.github.k3ssdev.teamtrekandroid.MainActivity;

public class DatabaseHandler {
    private Activity activity;
    public static final String EXTRA_MESSAGE = "io.github.k3ssdev.teamtrekandroid.USERNAME";

    public DatabaseHandler(Activity activity) {
        this.activity = activity;
    }

    public class ValidarUsuario extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            String usuario_apr = params[0];
            String contrasena_apr = params[1];
            //String urlString_apr = "http://192.168.1.227/validacuenta.php"; // IP del servidor XAMPP
            String urlString_apr = "http://10.0.2.2/teamtrek/validacuenta.php"; // localhost para el emulador

            String resultado_apr = null;

            try {
                // Crear la conexión HTTP
                URL url_apr = new URL(urlString_apr);
                HttpURLConnection conexion_apr = (HttpURLConnection) url_apr.openConnection();
                conexion_apr.setRequestMethod("POST");
                conexion_apr.setDoOutput(true);

                // Agrega el token como cabecera a la solicitud, COMENTAR ESTA LÍNEA PARA PROBAR LA APP EN SERVER PROPIO
                //conexion.setRequestProperty("Authorization", "Bearer " + token);

                // Crear los datos del formulario
                String datos_apr = "usuario=" + URLEncoder.encode(usuario_apr, "UTF-8") + "&contrasena=" + URLEncoder.encode(contrasena_apr, "UTF-8");

                // Escribir los datos en el cuerpo de la petición
                OutputStream outputStream_apr = conexion_apr.getOutputStream();
                outputStream_apr.write(datos_apr.getBytes(StandardCharsets.UTF_8));
                outputStream_apr.flush();
                outputStream_apr.close();

                // Leer la respuesta del servidor
                InputStream entrada_apr = conexion_apr.getInputStream();
                BufferedReader lector_apr = new BufferedReader(new InputStreamReader(entrada_apr));
                StringBuilder respuesta_apr = new StringBuilder();
                String linea;
                while ((linea = lector_apr.readLine()) != null) {
                    respuesta_apr.append(linea);
                }

                // Cerrar la conexión HTTP
                entrada_apr.close();
                conexion_apr.disconnect();

                // Procesar la respuesta del servidor
                resultado_apr = respuesta_apr.toString();

                // Parsear la respuesta XML
                Document document = XMLParser.convertStringToXMLDocument(resultado_apr);

                // Obtener el contenido del elemento "estado"
                NodeList estadoNodes = document.getElementsByTagName("estado");
                if (estadoNodes.getLength() > 0) {
                    Node estadoNode_apr = estadoNodes.item(0);
                    String estado_apr = estadoNode_apr.getTextContent();

                    // Asignar el resultado a la variable resultado_apr
                    resultado_apr = estado_apr;
                } else {
                    // Manejar el caso en el que no se pueda encontrar el elemento "estado"
                    resultado_apr = null;
                }

                // Crear un array para guardar el resultado, el usuario y la contraseña
                String[] resultadoYDatos_apr = new String[3];
                resultadoYDatos_apr[0] = resultado_apr; // Resultado de la validación
                resultadoYDatos_apr[1] = usuario_apr;    // Nombre de usuario
                resultadoYDatos_apr[2] = contrasena_apr;  // Contraseña

                return resultadoYDatos_apr; // Devuelve el array

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null; // Devuelve null si hay un error
        }

        @Override
        protected void onPostExecute(String[] resultadoYDatos) {
            if (resultadoYDatos != null) {
                String resultado_apr = resultadoYDatos[0]; // Resultado de la validación
                String usuario_apr = resultadoYDatos[1]; // Nombre de usuario
                String contrasena_apr = resultadoYDatos[2]; // Contraseña

                // Verifica el resultado y realiza las acciones necesarias
                if (resultado_apr.equals("ok")) {
                    // El resultado es "ok", abre la segunda actividad
                    Intent intent = new Intent(activity, MainActivity.class);

                    //Pasar el nombre de usuario a la actividad principal
                    intent.putExtra(EXTRA_MESSAGE, usuario_apr);

                    activity.startActivity(intent);


                } else if (resultado_apr.equals("ko")) {
                    // El resultado es "ko", realiza otra acción
                    Toast.makeText(activity, "Usuario/Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    //SQLiteHandler sqLiteHandler_apr = new SQLiteHandler(activity_apr);

                    // Insertar registro en la base de datos
                    //sqLiteHandler_apr.insertarRegistro(usuario_apr, contrasena_apr);

                    // Abrir LogActivity
                    //Intent intent_apr = new Intent(activity_apr, LogActivity.class);
                    //activity_apr.startActivity(intent_apr);
                }
            } else {
                // El resultado es null, hubo un error en la petición
                Toast.makeText(activity, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface ConsultarEmpleadoCallback {
        void onConsultaCompletada(List<Empleado> resultado);
    }

    public static class ConsultarEmpleadoTask extends AsyncTask<String, Void, List<Empleado>> {

        private ConsultarEmpleadoCallback callback;

        public ConsultarEmpleadoTask(ConsultarEmpleadoCallback callback) {
            this.callback = callback;
        }
        @Override
        protected List<Empleado> doInBackground(String... params) {
            String urlString = "http://10.0.2.2/teamtrek/consultaempleados.php"; // Cambia esto a tu URL

            // Añade el parámetro de nombre de usuario a la URL si se ha proporcionado uno
            if (params != null && params.length > 0 && params[0] != null && !params[0].isEmpty()) {
                try {
                    String username = params[0];
                    urlString += "?username=" + URLEncoder.encode(username, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            // Crea una lista vacía para guardar los empleados
            List<Empleado> empleados = new ArrayList<>();

            // Realiza la petición GET
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                connection.disconnect();

                String xmlString = response.toString();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(xmlString)));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                NodeList empleadoNodes = document.getElementsByTagName("empleado");

                for (int i = 0; i < empleadoNodes.getLength(); i++) {
                    Node empleadoNode = empleadoNodes.item(i);
                    if (empleadoNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element empleadoElement = (Element) empleadoNode;

                        // Identificacion
                        Element identificacion = (Element) empleadoElement.getElementsByTagName("identificacion").item(0);
                        int idEmpleado = Integer.parseInt(identificacion.getElementsByTagName("ID").item(0).getTextContent());
                        String nombreEmpleado = identificacion.getElementsByTagName("Nombre").item(0).getTextContent();
                        String usuarioEmpleado = identificacion.getElementsByTagName("Usuario").item(0).getTextContent();
                        Date fechaIngresoEmpleado = dateFormat.parse(identificacion.getElementsByTagName("FechaIngreso").item(0).getTextContent());

                        // Departamento
                        String nombreDepartamento = empleadoElement.getElementsByTagName("NombreDepartamento").item(0).getTextContent();

                        // Horario
                        String descripcionHorario = empleadoElement.getElementsByTagName("DescripcionHorario").item(0).getTextContent();
                        Date horaInicio = timeFormat.parse(empleadoElement.getElementsByTagName("HoraInicio").item(0).getTextContent());
                        Date horaFin = timeFormat.parse(empleadoElement.getElementsByTagName("HoraFin").item(0).getTextContent());

                        // Datos personales
                        Element datosPersonales = (Element) empleadoElement.getElementsByTagName("datosPersonales").item(0);
                        String telefonoEmpleado = datosPersonales.getElementsByTagName("Telefono").item(0).getTextContent();
                        String direccionEmpleado = datosPersonales.getElementsByTagName("Direccion").item(0).getTextContent();
                        String emailEmpleado = datosPersonales.getElementsByTagName("Email").item(0).getTextContent();
                        Date fechaNacimientoEmpleado = dateFormat.parse(datosPersonales.getElementsByTagName("FechaNacimiento").item(0).getTextContent());
                        String nifEmpleado = datosPersonales.getElementsByTagName("NIF").item(0).getTextContent();

                        // Crear instancia de Empleado
                        Empleado empleado = new Empleado(
                                idEmpleado, nombreEmpleado, fechaIngresoEmpleado, nombreDepartamento,
                                descripcionHorario, horaInicio, horaFin, usuarioEmpleado,
                                telefonoEmpleado, direccionEmpleado, emailEmpleado,
                                fechaNacimientoEmpleado, nifEmpleado
                        );
                        empleados.add(empleado);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

            return empleados;
        }

        @Override
        protected void onPostExecute(List<Empleado> resultado) {
            super.onPostExecute(resultado);
            if (callback != null) {
                callback.onConsultaCompletada(resultado);
            }
        }


}
}