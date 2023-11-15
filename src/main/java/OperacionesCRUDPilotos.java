import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;

public class OperacionesCRUDPilotos {

    Path rutaBaseDatos = Path.of("src", "db", "f12006sqlite.db");

    //CrearPiloto(), que reciba un objeto Piloto y lo añada a la base de datos
    public boolean crearPiloto(Piloto piloto) {
        boolean creado = false;
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBaseDatos.toString())) {
            String sentenciaSQL = "INSERT INTO drivers (code, forename, surname, dob, nationality, url) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insercion = conexion.prepareStatement(sentenciaSQL);
            insercion.setString(1, piloto.getCode());
            insercion.setString(2, piloto.getForename());
            insercion.setString(3, piloto.getSurname());
            insercion.setString(4, piloto.getDob().toString());
            insercion.setString(5, piloto.getNationality());
            insercion.setString(6, piloto.getUrl());

            insercion.executeUpdate();
            insercion.close();
            creado = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return creado;
    }

    //LeerPiloto(), que reciba un entero y devuelva un objeto Piloto con la información del piloto
    //con el driverid coincidente.
    public Piloto leerPiloto(Integer IDDriver) {
        Piloto piloto = new Piloto();

        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBaseDatos.toString())) {
            String sentenciaSQL = "SELECT *" +
                    "FROM drivers " +
                    "WHERE driverid = ? ";
            PreparedStatement insercion = conexion.prepareStatement(sentenciaSQL);
            insercion.setString(1, IDDriver.toString());

            ResultSet resultados = insercion.executeQuery();

            while (resultados.next()) {

                piloto = new Piloto(
                        resultados.getInt("driverid"),
                        resultados.getString("code"),
                        resultados.getString("forename"),
                        resultados.getString("surname"),
                        resultados.getString("dob"),
                        resultados.getString("nationality"),
                        resultados.getString("url")
                );
            }
            insercion.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return piloto;
    }

    /**
     * LeerPilotos(), que devuelva un listado completo de objetos Piloto
     *
     * @return Lista de objetos Piloto
     */
    public ArrayList<Piloto> leerPilotos() {
        ArrayList<Piloto> pilotos = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBaseDatos.toString())) {
            String sentenciaSQL = "SELECT * FROM drivers";
            PreparedStatement insercion = conexion.prepareStatement(sentenciaSQL);

            ResultSet resultados = insercion.executeQuery();

            while (resultados.next()) {
                Piloto piloto = new Piloto(
                        resultados.getInt("driverid"),
                        resultados.getString("code"),
                        resultados.getString("forename"),
                        resultados.getString("surname"),
                        resultados.getString("dob"),
                        resultados.getString("nationality"),
                        resultados.getString("url")
                );
                pilotos.add(piloto);
            }
            insercion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pilotos;
    }


    //ActualizarPiloto(), que reciba un objeto Piloto y actualice los datos del registro coincidente
    //en la base de datos con el mismo driverid.
    public boolean actualizarPiloto(Piloto piloto) {
        boolean actualizado = false;
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBaseDatos.toString())) {
            String sentenciaSQL = "UPDATE drivers SET code = ?, forename = ?, surname = ?, dob = ?, nationality = ?, url = ? WHERE driverid = ?";
            PreparedStatement insercion = conexion.prepareStatement(sentenciaSQL);

            insercion.setString(1, piloto.getCode());
            insercion.setString(2, piloto.getForename());
            insercion.setString(3, piloto.getSurname());
            insercion.setString(4, piloto.getDob().toString());
            insercion.setString(5, piloto.getNationality());
            insercion.setString(6, piloto.getUrl());
            insercion.setInt(7, piloto.getDriverid());

            insercion.executeUpdate();
            insercion.close();

            actualizado = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actualizado;
    }

    //BorrarPiloto(), que reciba un objeto Piloto y lo elimine de la base de datos.
    public boolean borrarPiloto(Piloto piloto) {
        boolean borrado = false;

        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBaseDatos.toString())) {
            String sentenciaSQL = "DELETE FROM drivers WHERE driverid = ?";

            PreparedStatement insercion = conexion.prepareStatement(sentenciaSQL);

            insercion.setInt(1, piloto.getDriverid());

            insercion.executeUpdate();
            insercion.close();
            borrado = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return borrado;
    }

    /**
     * MostrarClasificacionPiloto(), que muestre la clasificación final del mundial ordenada por
     * puntos de los pilotos.
     */
    public void mostrarClasificacionPiloto() {
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBaseDatos.toString())) {
            String sentenciaSQL =
                    "SELECT drivers.forename, drivers.surname, SUM(results.points) as puntos FROM drivers"+
                            " INNER JOIN results ON drivers.driverid = results.driverid GROUP BY drivers.driverid ORDER BY puntos DESC";

            PreparedStatement insercion = conexion.prepareStatement(sentenciaSQL);

            ResultSet resultados = insercion.executeQuery();
            System.out.println("Clasificación de pilotos:");
            while (resultados.next()) {
                System.out.println(resultados.getString("forename") + " " + resultados.getString("surname") + " " + resultados.getString("puntos"));
            }
            System.out.println();
            insercion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * MostrarClasificacionConstructores(), que muestre la clasificación del mundial ordenada por
     * puntos de los equipos. Tanto en este método como en el anterior tendrás que realizar
     * consultas multitabla.
     */
    public void mostrarClasificacionConstructores() {
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBaseDatos.toString())) {
            String sentenciaSQL =
                    "SELECT constructors.name, SUM(results.points) as puntos FROM (constructors " +
                            "INNER JOIN drivers ON constructors.constructorid = drivers.constructorid)" +
            "INNER JOIN results ON drivers.driverid = results.driverid GROUP BY constructors.constructorid ORDER BY puntos DESC";

            PreparedStatement insercion = conexion.prepareStatement(sentenciaSQL);

            ResultSet resultados = insercion.executeQuery();
            System.out.println("Clasificación de constructores:");

            while (resultados.next()) {
                System.out.println(resultados.getString("name") + " " + resultados.getString("puntos"));
            }
            System.out.println();
            insercion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}
