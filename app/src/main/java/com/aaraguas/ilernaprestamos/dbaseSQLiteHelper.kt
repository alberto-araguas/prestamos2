package com.aaraguas.ilernaprestamos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbaseSQLiteHelper (context: Context) : SQLiteOpenHelper(
    context, "prestamos.db", null, 9) {

    private lateinit var db : SQLiteDatabase

    companion object {
        val TABLA_DISCOS = "discos"
        val CAMPO_ID = "_id"
        val CAMPO_PORTADA = "portada"
        val CAMPO_TITULO = "titulo"
        val CAMPO_ARTISTA = "artista"
        val CAMPO_FORMATO = "formato"
        val CAMPO_ESTILO = "estilo"
        val CAMPO_FECHA = "fecha"
        val CAMPO_ESTUDIO = "estudio"
        val CAMPO_LATITUD = "latitud"
        val CAMPO_LONGITUD = "longitud"
        //tabla familia
        val TABLA_FAMILIA = "familia"
        val CAMPO_IDF = "_idF"
        val CAMPO_FNOMBRE = "familia_nombre"
        //tabla lugares
        val TABLA_LUGARES = "lugares"
        val CAMPO_IDL = "_idL"
        val CAMPO_LNOMBRE = "lugares_nombre"
        val CAMPO_LDIRECCION = "lugares_direccion"
        val CAMPO_LTELEFONO = "lugares_telefono"
        val CAMPO_LCORREO = "lugares_correo"
        //tabla proveedores
        val TABLA_PROVEEDORES = "proveedores"
        val CAMPO_IDP = "_idP"
        val CAMPO_PNOMBRE = "proveedores_nombre"
        val CAMPO_PDIRECCION = "proveedores_direccion"
        val CAMPO_PTELEFONO = "proveedores_telefono"
        val CAMPO_PCORREO = "proveedores_correo"
        val CAMPO_PCONTACTO = "proveedores_contacto"
        //tabla proveedores
        val TABLA_USUARIOS = "usuarios"
        val CAMPO_IDUS = "_idUsuario"
        val CAMPO_USNOMBRE = "usuarios_nombre"
        val CAMPO_USDIRECCION = "usuarios_direccion"
        val CAMPO_USTELEFONO = "usuarios_telefono"
        val CAMPO_USCORREO = "usuarios_correo"
        val CAMPO_USDEPARTAMENTO = "usuarios_departamento"
        //tabla equipo
        val TABLA_EQUIPOS = "equipos"
        val CAMPO_IDEQ = "_idEquipo"
        val CAMPO_EQNOMBRE = "equipos_nombre"
        val CAMPO_EQCARAC= "equipos_caracteristicas"
        val CAMPO_EQFECHACOMPRA = "equipos_fechacompra"
        val CAMPO_FKPROVEEDOR = "equipos_idproveedor"
        val CAMPO_FKFAMILIA = "equipos_idfamilia"
        val CAMPO_EQESTADO = "equipos_estado"
        val CAMPO_EQFECHAPRESTAMO = "equipos_fechaprestamo"
        val CAMPO_FKLUGAR = "equipos_idlugar"
        val CAMPO_FKUSUARIO = "equipos_idusuario"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE $TABLA_DISCOS " +
                "($CAMPO_ID INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_PORTADA TEXT, " +
                "$CAMPO_TITULO TEXT, $CAMPO_ARTISTA TEXT, $CAMPO_FORMATO TEXT, " +
                "$CAMPO_ESTILO TEXT, $CAMPO_FECHA TEXT, $CAMPO_ESTUDIO TEXT, " +
                "$CAMPO_LATITUD REAL, $CAMPO_LONGITUD REAL)"
        db!!.execSQL(ordenCreacion)

        val ordenCreacionFamilia = "CREATE TABLE $TABLA_FAMILIA " +
                "($CAMPO_IDF INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_FNOMBRE TEXT )"

        db!!.execSQL(ordenCreacionFamilia)

        val ordenCreacionlugares = "CREATE TABLE $TABLA_LUGARES " +
                "($CAMPO_IDL INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_LNOMBRE TEXT, " +
                "$CAMPO_LDIRECCION TEXT, $CAMPO_LTELEFONO LONG , $CAMPO_LCORREO TEXT)"
        db!!.execSQL(ordenCreacionlugares)

        val ordenCreacionproveedores = "CREATE TABLE $TABLA_PROVEEDORES " +
                "($CAMPO_IDP INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_PNOMBRE TEXT, " +
                "$CAMPO_PDIRECCION TEXT, $CAMPO_PTELEFONO LONG , $CAMPO_PCORREO TEXT, $CAMPO_PCONTACTO TEXT)"
        db!!.execSQL(ordenCreacionproveedores)

        val ordenCreacionusuarios = "CREATE TABLE $TABLA_USUARIOS " +
                "($CAMPO_IDUS INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_EQNOMBRE TEXT, " +
                "$CAMPO_EQCARAC TEXT, $CAMPO_USTELEFONO LONG , $CAMPO_USCORREO TEXT, $CAMPO_USDEPARTAMENTO TEXT)"
        db!!.execSQL(ordenCreacionusuarios)

        val ordenCreacionequipos = "CREATE TABLE $TABLA_EQUIPOS " +
                "($CAMPO_IDEQ INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_USNOMBRE TEXT, " +
                "$CAMPO_USDIRECCION TEXT, $CAMPO_EQFECHACOMPRA TEXT , $CAMPO_FKPROVEEDOR INTEGER, " +
                "$CAMPO_FKFAMILIA INTEGER, $CAMPO_EQESTADO BOOLEAN, $CAMPO_EQFECHAPRESTAMO TEXT, " +
                "$CAMPO_FKLUGAR INTEGER, $CAMPO_FKUSUARIO INTEGER )"
        db!!.execSQL(ordenCreacionequipos)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS discos"
        db!!.execSQL(ordenBorrado)
        val ordenBorradoFamilia = "DROP TABLE IF EXISTS familia"
        db!!.execSQL(ordenBorradoFamilia)
        val ordenBorradoLugares = "DROP TABLE IF EXISTS lugares"
        db!!.execSQL(ordenBorradoLugares)
        val ordenBorradoProveedores = "DROP TABLE IF EXISTS proveedores"
        db!!.execSQL(ordenBorradoProveedores)
        val ordenBorradoUsuario = "DROP TABLE IF EXISTS usuarios"
        db!!.execSQL(ordenBorradoUsuario)
        val ordenBorradoEquipo = "DROP TABLE IF EXISTS equipos"
        db!!.execSQL(ordenBorradoEquipo)
        onCreate(db)
    }

    fun obtenerTodosDiscos(tabla: String, campoOrden: String): Cursor {
        db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tabla ORDER BY $campoOrden", null)
        return cursor
    }

    fun obtenerPorId(tabla: String, id: Int) : Cursor {
        db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tabla WHERE $CAMPO_ID = $id", null)
        cursor.moveToFirst()
        return cursor
    }

    fun obtenerCursor(tabla: String, campo: String, campoValor: String,
                      campoOrden: String): Cursor {
        db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tabla " +
                "WHERE $campo LIKE '%$campoValor%' " +
                "ORDER BY $campoOrden", null)
        return cursor
    }

    fun agregarDisco(portada: String, titulo: String, artista: String, formato: String,
                     estilo: String, fecha: String, estudio: String, latitud: Double, longitud: Double) {
        val datos = ContentValues()
        datos.put(CAMPO_PORTADA, portada)
        datos.put(CAMPO_TITULO, titulo)
        datos.put(CAMPO_ARTISTA, artista)
        datos.put(CAMPO_FORMATO,formato)
        datos.put(CAMPO_ESTILO, estilo)
        datos.put(CAMPO_FECHA, fecha)
        datos.put(CAMPO_ESTUDIO, estudio)
        datos.put(CAMPO_LATITUD, latitud)
        datos.put(CAMPO_LONGITUD, longitud)

        val db = this.writableDatabase
        db.insert(TABLA_DISCOS, null, datos)
        db.close()
    }


    fun editarDisco(id: Int, portada: String, titulo: String, artista: String, formato: String,
                    estilo: String, fecha: String, estudio: String, latitud: Double, longitud: Double) {
        val args = arrayOf(id.toString())
        val datos = ContentValues()
        datos.put(CAMPO_PORTADA, portada)
        datos.put(CAMPO_TITULO, titulo)
        datos.put(CAMPO_ARTISTA, artista)
        datos.put(CAMPO_FORMATO,formato)
        datos.put(CAMPO_ESTILO, estilo)
        datos.put(CAMPO_FECHA, fecha)
        datos.put(CAMPO_ESTUDIO, estudio)
        datos.put(CAMPO_LATITUD, latitud)
        datos.put(CAMPO_LONGITUD, longitud)
        val db = this.writableDatabase
        db.update(TABLA_DISCOS, datos, "$CAMPO_ID = ?", args)
        db.close()
    }

    fun borrarDisco(id: Int) : Int {
        val args = arrayOf(id.toString())
        val db = this.writableDatabase
        val borrados = db.delete(TABLA_DISCOS, "$CAMPO_ID = ?", args)
        db.close()
        return borrados
    }

    //CRUD TABLA FAMILIA
    fun agregarFamilia(familia_nombre: String) {
        val datos = ContentValues()
        datos.put(CAMPO_FNOMBRE, familia_nombre)


        val db = this.writableDatabase
        db.insert(TABLA_FAMILIA, null, datos)
        db.close()
    }

    //CRUD TABLA LUGARES
    fun agregarLugar(lugar_nombre: String,lugar_direccion: String,lugar_telefono: Long,lugar_correo: String) {
        val datos = ContentValues()
        datos.put(CAMPO_LNOMBRE, lugar_nombre)
        datos.put(CAMPO_LDIRECCION, lugar_direccion)
        datos.put(CAMPO_LTELEFONO, lugar_telefono)
        datos.put(CAMPO_LCORREO, lugar_correo)

        val db = this.writableDatabase
        db.insert(TABLA_LUGARES, null, datos)
        db.close()
    }
    //CRUD TABLA PROVEEDORES
    fun agregarProveedor(prov_nombre: String,prov_direccion: String,prov_telefono: Long,prov_correo: String,prov_contacto: String) {
        val datos = ContentValues()
        datos.put(CAMPO_PNOMBRE, prov_nombre)
        datos.put(CAMPO_PDIRECCION, prov_direccion)
        datos.put(CAMPO_PTELEFONO, prov_telefono)
        datos.put(CAMPO_PCORREO, prov_correo)
        datos.put(CAMPO_PCONTACTO, prov_contacto)

        val db = this.writableDatabase
        db.insert(TABLA_PROVEEDORES, null, datos)
        db.close()
    }
    //CRUD TABLA USUARIOS
    fun agregarUsuario(us_nombre: String,us_direccion: String,us_telefono: Long,us_correo: String,us_departamento: String) {
        val datos = ContentValues()
        datos.put(CAMPO_USNOMBRE, us_nombre)
        datos.put(CAMPO_USDIRECCION, us_direccion)
        datos.put(CAMPO_USTELEFONO, us_telefono)
        datos.put(CAMPO_USCORREO, us_correo)
        datos.put(CAMPO_USDEPARTAMENTO, us_departamento)

        val db = this.writableDatabase
        db.insert(TABLA_USUARIOS, null, datos)
        db.close()
    }

    fun cerrarDB() {
        db.close()
    }


}